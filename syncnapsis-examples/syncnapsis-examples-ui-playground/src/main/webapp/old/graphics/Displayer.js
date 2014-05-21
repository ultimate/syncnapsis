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
function Displayer()
{
	this.viewport = null;
	this.drawables = new Array();
	this.autoDraw = false;
	this.pointsize = 0;
	this.cameras = new Array();
	this.activeCamera = -1;
	
	this.cameraTransformation = TransformationScaling(1,1,1);
	
	this.createViewport = function(x, y, z, w, h, color, parentElement)
	{
		this.viewport = new Viewport(x, y, z, w, h, color, parentElement);
		this.viewport.displayer = this;
	};

	this.setViewportPosition = function(x, y, z)
	{
		this.viewport.posX = x;
		this.viewport.posY = y;
		this.viewport.posZ = z;
		this.viewport.div.setX(x);
		this.viewport.div.setY(y);
		this.viewport.div.setZ(z);
	};
	
	this.setViewportSize = function(w, h)
	{
		this.viewport.width = w;
		this.viewport.height = h;
		this.viewport.div.setWidth(w);
		this.viewport.div.setHeight(h);
	};
	
	this.setViewportColor = function(color)
	{
		this.viewport.color = color;
		this.viewport.div.setColor(color);
	};
	
	this.setZScale = function(zScale)
	{
		this.viewport.zScale = zScale;
	};
	
	this.addDrawable = function(drawable)
	{
		if(!drawable)
			throw new Error("drawable is null!");
		this.drawables[this.drawables.length] = drawable;
		if(this.autoDraw)
			this.viewport.drawElement(drawable);
	};
	
	this.clearDrawables = function()
	{
		this.drawables = new Array();
		this.viewport.clearListeners();
		if(this.autoDraw)
			this.redraw();
	};
	
	this.redraw = function()
	{
		this.calculateCameraTransformation();
		this.viewport.redraw();
	};
	
	this.calculateCameraTransformation = function()
	{
		var tmp = this.cameras[this.activeCamera].getTransformation();
		
		var scale = Math.min(this.viewport.width/2, this.viewport.height/2)
		
		var t_viewportScaling = TransformationScaling(scale, scale, scale);
		tmp = matrixProduct(t_viewportScaling, tmp);			

		//var t_viewportTranslation = TransformationTranslation(this.viewport.width/2, this.viewport.height/2, 0);
		//tmp = matrixProduct(t_viewportTranslation, tmp);			

		this.cameraTransformation = tmp;		
	};
	
	this.addCamera = function(camera, index)
	{
		this.cameras[index] = camera;
	};
	
	this.setActiveCamera = function(index)
	{
		this.activeCamera = index;
	};
}
