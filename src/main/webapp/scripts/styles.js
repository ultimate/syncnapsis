/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MECHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Plublic License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 */
// @requires("Events")
Styles = {};

Styles.constant = {};
Styles.constant.WIDTH = "width";
Styles.constant.HEIGHT = "height";
Styles.constant.LEFT = "left";
Styles.constant.RIGHT = "right";
Styles.constant.TOP = "top";
Styles.constant.BOTTOM = "bottom";

Styles.layout = {};
Styles.layout.HORIZONTAL = 1;
Styles.layout.VERTICAL = 2;

Styles.FillLayout = function(elements, elementSizes, direction)
{
	if(elements.length != elementSizes.length)
		throw new Error("elements and elementSizes must have same length!");
	this.elements = new Array();
	this.elementParent = null;
	for( var i = 0; i < elements.length; i++)
	{
		if(elements[i] == null)
			throw new Error("elementIds must not contain null values!");
		if(typeof (elements[i]) == Reflections.type.STRING)
			this.elements[i] = document.getElementById(elements[i]);
		else
			this.elements[i] = elements[i];
		if(this.elementParent == null)
			this.elementParent = this.elements[i].parentNode;
		else if(this.elements[i].parentNode != this.elementParent)
			throw new Error("elements must have same parent!");
	}
	this.elementSizes = elementSizes;
	this.nullSizes = 0;
	for( var i = 0; i < elementSizes.length; i++)
	{
		if(elementSizes[i] == null)
			this.nullSizes++;
	}
	if(direction != Styles.layout.HORIZONTAL && direction != Styles.layout.VERTICAL)
		throw new Error("Illegal direction '" + direction + "'!");
	this.direction = direction;

	this._handlerObject = this;
	this._handlerMethod = this.fill;
	Events.addEventListener(Events.ONRESIZE, this.anonymous(), window);
}

Styles.FillLayout.prototype = new Events.EventHandler();

Styles.FillLayout.prototype.fill = function()
{
	var coordinateTarget;
	if(this.direction == Styles.layout.HORIZONTAL)
		coordinateTarget = this.elementParent.offsetWidth;
	else
		coordinateTarget = this.elementParent.offsetHeight;
	var coordinateTotal = 0;
	var coordinateSum = 0;
	for( var i = 0; i < this.elements.length; i++)
	{
		if(this.elementSizes[i] != null)
			coordinateTotal += this.elementSizes[i];
	}
	var nullSize = (coordinateTarget - coordinateTotal) / this.nullSizes;
	var size;
	for( var i = 0; i < this.elements.length; i++)
	{
		if(this.elementSizes[i] != null)
			size = this.elementSizes[i];
		else
			size = nullSize;

		if(this.direction == Styles.layout.HORIZONTAL)
		{
			this.elements[i].style.left = coordinateSum + "px";
			this.elements[i].style.width = size + "px";
			// this.elements[i].style.height = this.elementParent.offsetHeight +
			// "px";
			coordinateSum += size;
		}
		else
		{
			this.elements[i].style.top = coordinateSum + "px";
			this.elements[i].style.height = size + "px";
			// this.elements[i].style.width = this.elementParent.offsetWidth +
			// "px";
			coordinateSum += size;
		}
	}
};

Styles.window = {};
Styles.window.TITLE_HEIGHT = 30;

Styles.activeElement = null;
Styles.offsetX = 0;
Styles.offsetY = 0;

Styles.mouseDown = function(event, element)
{
	Styles.activeElement = element;
	Styles.offsetX = event.layerX;
	Styles.offsetY = event.layerY;
};

Styles.move = function(event)
{
	if(Styles.activeElement != null)
	{
		var x = event.pageX - Styles.offsetX;
		var y = event.pageY - Styles.offsetY;

		if(Styles.activeElement.setPosition)
			Styles.activeElement.setPosition(x, y);
		else
		{
			Styles.activeElement.style.left = x + "px";
			Styles.activeElement.style.top = y + "px";
		}
	}
};

Styles.moveHandler = new Events.EventHandler(Styles, Styles.move);
Events.addEventListener(Events.MOUSEMOVE, Styles.moveHandler.anonymous(), window);
Events.addEventListener(Events.MOUSEUP, function()
{
	Styles.activeElement = null;
}, window);

Styles.Window = function(id, titleKey, contentDiv)
{
	var frame = document.getElementById(id);
	if(frame != null)
		return frame;
	
	frame = document.createElement("div");
	frame.style.position = "absolute";
	frame.style.overflow = "hidden";
	frame.className = "window";
	frame.id = id;

	frame._titleBar = document.createElement("div");
	frame._titleBar.style.position = "absolute";
	frame._titleBar.style.width = "100%";
	frame._titleBar.className = "window_title";

	frame._titleLabel = document.createElement("label");
	frame._titleLabel.setAttribute("key", titleKey);
	frame._titleBar.appendChild(frame._titleLabel);

	frame._closeButton = document.createElement("span");
	frame._closeButton.innerHTML = "x";
	frame._closeButton.className = "window_close";
	frame._titleBar.appendChild(frame._closeButton);

	frame._contentFrame = document.createElement("div");
	frame._contentFrame.style.position = "absolute";
	frame._contentFrame.style.width = "100%";
	frame._contentFrame.className = "window_content";

	frame.appendChild(frame._titleBar);
	frame.appendChild(frame._contentFrame);

	frame._centered = false;

	frame.setContent = function(contentDiv)
	{
		if(typeof (contentDiv) == Reflections.type.STRING)
			this._contentFrame.appendChild(document.getElementById(contentDiv));
		else
			this._contentFrame.appendChild(contentDiv);
	};
	
	frame.setTitleKey = function(titleKey)
	{
		this._titleLabel.setAttribute("key", titleKey);
		if(client && client.uiManager) // reverse dependency check...
			client.uiManager.updateLabels(this);
	};

	frame.setVisible = function(visible)
	{
		if(visible)
		{
			this.style.display = "block";
			if(this._centered)
				this.center();
		}
		else
			this.style.display = "none";
	};

	frame.setClosable = function(closable)
	{
		if(closable)
			this._closeButton.style.display = "block";
		else
			this._closeButton.style.display = "none";
	};

	frame.setMovable = function(movable)
	{
		this.movable = movable;
	};

	frame.setSize = function(width, height)
	{
		this.style.width = width + "px";
		this.style.height = height + "px";
		this._fill.fill();
	};

	frame.setPosition = function(x, y)
	{
		this.style.left = x + "px";
		this.style.top = y + "px";
		this._centered = false;
	};
	
	frame.center = function()
	{
//		var x = (document.body.offsetWidth - frame.offsetWidth) / 2;
//		var y = (document.body.offsetHeight - frame.offsetHeight) / 2;
		var x = (window.innerWidth - this.offsetWidth) / 2;
		var y = (window.innerHeight - this.offsetHeight) / 2;
		this.setPosition(x, y);
		this._centered = true;
	};

	frame.close = function()
	{
		this.setVisible(false);
	};

	var closeHandler = new Events.EventHandler(frame, frame.close);
	Events.addEventListener(Events.CLICK, closeHandler.anonymous(), frame._closeButton);

	Events.addEventListener(Events.MOUSEDOWN, function(event)
	{
		if(frame.movable)
			Styles.mouseDown(event, frame);
	}, frame._titleBar);

	frame._fill = new Styles.FillLayout([ frame._titleBar, frame._contentFrame ], [ Styles.window.TITLE_HEIGHT, null ], Styles.layout.VERTICAL)

	frame.setContent(contentDiv);
	frame.setMovable(false);
	frame.setClosable(true);
	frame.setVisible(false);

	document.body.appendChild(frame);

	return frame;
};
