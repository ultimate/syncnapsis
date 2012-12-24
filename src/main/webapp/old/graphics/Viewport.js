/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
var viewport_global = new Array();
var viewport_global_i = 0;
var canvasInited = false;

function Viewport(posX, posY, posZ, width, height, color, parentElement) 
{
	this.posX = posX;
	this.posY = posY;
	this.posZ = posZ;
	this.width = width;
	this.height = height;
	this.w_2 = width/2;
	this.h_2 = height/2;
	this.color = color;
	this.enableToolTips = false;
	this.displayer = null;
	this.div = null;
	this.canvas = null;
	this.ctx = null;
	this.firstClear = true;
	this.zScale = 1000;
	
	this.mouseX = 0;
	this.mouseY = 0;	
		
	this.parentElement = parentElement;
	if(!parentElement)
		this.parentElement = document.body;	
		
	this.clearListeners = function()
	{
		this.dragListeners = new Array();
		this.clickListeners = new Array();
		this.hoverListeners = new Array();
	};
	
	this.clear = function()
	{
		if(this.ctx == null)
			this.initContainer(0);
		if(this.firstClear)
		{
			this.ctx.save();
			this.firstClear = false;
		}
		else
		{
			this.ctx.translate(-this.w_2, -this.h_2);
			this.ctx.restore();
		}
		
		this.ctx.clearRect(0,0,this.width,this.height);
		this.ctx.fillStyle = this.color;
		this.ctx.fillRect(0,0,this.width,this.height);
		this.ctx.translate(this.w_2, this.h_2);
		return;
	};
	
	this.drawElement = function(drawable)
	{
		drawable.draw(this.ctx);
	};
	
	this.redraw = function()
	{
		this.clear();
		this.drawablesT = new Array();
		this.drawablesZ = new Array();
		for(var i = 0; i < this.displayer.drawables.length; i++)
		{
			this.displayer.drawables[i].transform(this.displayer.cameraTransformation, this);
			var z = Math.round(this.displayer.drawables[i].averageZ*this.zScale);
			if(!isNaN(z))
			{
				if(this.drawablesT[z] == null)
				{
					 this.drawablesT[z] = new Array();
					 this.drawablesZ[this.drawablesZ.length] = z;
				}
				this.drawablesT[z][this.drawablesT[z].length] = this.displayer.drawables[i];
			}	
			
			//this.drawElement(this.displayer.drawables[i]);
		}		
		
		this.drawablesZ.numSort();
		for(var i = 0; i < this.drawablesZ.length; i++)
		{
			var z = this.drawablesZ[i];
			for(var i2 = 0; i2 < this.drawablesT[z].length; i2++)
			{
				this.drawElement(this.drawablesT[z][i2]);
			}
		}
		
		
		for(var i = 0; i < this.hoverListeners.length; i++)
		{
			if(this.hoverListeners[i].isInArea(this.mouseX, this.mouseY))
			{
				this.hoverListeners[i].event(this.mouseX, this.mouseY, this.ctx);
			}
		}
		for(var i = 0; i < this.dragListeners.length; i++)
		{
			if(this.dragListeners[i].isActive())
			{
				this.dragListeners[i].event(this.mouseX, this.mouseY, this.ctx);
			}
		}
		
		// ieworkaround damit die letzt fläche nicht fehlt ...
		this.ctx.fillStyle = "rgba(1,1,1,0)";
		this.ctx.fillRect(0,0,0,0);	
	};
	
	this.initializeEventListening = function()
	{
		this.clearListeners();
		var vp = this;
		addListener(this.div, "mousedown", 	function(e){ viewportMouseDown(e, vp); });	
		addListener(this.div, "mouseup", 	function(e){   viewportMouseUp(e, vp); });	
		addListener(this.div, "mousemove", 	function(e){ viewportMouseMove(e, vp); });	
	};
	
	this.initContainer = function(i)
	{
		try
		{
			this.ctx = this.canvas.getContext('2d');
		  	info("Canvas-Context inited: " + this.ctx);
			this.clear();
			return;			
		}
		catch(e)
		{
			
		}
		if(i == 0)
		{			
			error("Canvas not supported!");
			return;
		}
		setTimeout("containerInit(" + this.global_i + ", " + --i + ");", 50);
	}; 
	
	this.createContainer = function()
	{		
		this.div = createDiv(this.posX, this.posY, this.posZ, this.width, this.height, this.color);
		this.w_2 = this.width/2;
		this.h_2 = this.height/2;
		this.parentElement.appendChild(this.div);
		this.div.viewport = this;
		
		this.canvas = document.createElement("canvas");
		this.canvas.setAttribute('width', this.width);
		this.canvas.setAttribute('height', this.height);
		this.canvas.setWidth(this.width);
		this.canvas.setHeight(this.height);
		this.div.appendChild(this.canvas);
		
		this.global_i = viewport_global_i++;
		viewport_global[this.global_i] = this;
		
		this.id = "vp_" + this.global_i;
		this.div.id = "vp_div_" + this.global_i;
		this.canvas.id = "vp_canvas_" + this.global_i;

		this.initializeEventListening();	
		ieCanvasInit();
		this.initContainer(10);	
	};
	
	this.removeContainer = function()
	{		
		this.div.removeChild(this.canvas);
	};
	
	this.createContainer();
}

function containerInit(vi, i)
{
	viewport_global[vi].initContainer(i);
}

function ieCanvasInit()
{
	if(canvasInited)
		return;
	if(browser == "IE7")
	{
		info("Performing IE-Canvas-Init...");
		
		/* Add VML includes and namespace */
		document.namespaces.add("v");
		var oVml = document.createElement('object');
		oVml.id = 'VMLRender';
		oVml.codebase = 'vgx.dll';
		oVml.classid = 'CLSID:10072CEC-8CC1-11D1-986E-00A0C955B42E';
		document.body.appendChild(oVml);
	
		/* Add required css rules */
		var htcFile = scriptPath + 'graphics/iecanvas/iecanvas.htc';
		var oStyle = document.createStyleSheet();
		oStyle.addRule('canvas', "behavior: url('" + htcFile + "');");
		oStyle.addRule('v\\:*', "behavior: url(#VMLRender);");
		
		info("IE-Canvas-Init Done!");
	}
	canvasInited = true;
}