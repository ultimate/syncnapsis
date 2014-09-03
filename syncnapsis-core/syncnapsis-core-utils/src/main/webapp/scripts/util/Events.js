/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
// Events-Package
var Events = {};

// Event-Names
Events.CLICK = "click";
Events.KEYUP = "keyup";
Events.KEYDOWN = "keydown";
Events.KEYPRESS = "keypress";
Events.ONRESIZE = "resize";
Events.MOUSEDOWN = "mousedown";
Events.MOUSEUP = "mouseup";
Events.MOUSEMOVE = "mousemove";
Events.MOUSEOVER = "mouseover";
Events.MOUSEOUT = "mouseout";

// fix IE Events Names
// do that before defining any further constants or functions!!!
if(!document.addEventListener)
	for(e in Events)
		Events[e] = "on" + Events[e];

// Event-Keys
Events.KEY_ALT = (1 << 0);
Events.KEY_CTRL = (1 << 1);
Events.KEY_SHIFT = (1 << 2);
Events.KEY_ENTER = 13;
// Event-Mouse-Buttons
Events.BUTTON_LEFT = 0;
Events.BUTTON_CENTER = 1;
Events.BUTTON_RIGHT = 2;

// Browser-dependant hack...
if(document.addEventListener)
{
	Events.addEventListener = function(event, eventListener, component)
	{
		if(!component)
			component = document;
		component.addEventListener(event, eventListener, false);
	};

	Events.getKeyCode = function(event)
	{
		return event.which;
	};
}
else
{
	Events.addEventListener = function(event, eventListener, component)
	{
		if(!component)
			component = document;
		component.attachEvent(event, eventListener);
	};

	Events.getKeyCode = function(event)
	{
		return event.keyCode;
	};
}

Events.hasModifiers = function(event, modifiers)
{
	var hasAll = true;
	if(modifiers & Events.KEY_ALT)
		hasAll = hasAll && event.altKey;
	else
		hasAll = hasAll && !event.altKey;
	if(modifiers & Events.KEY_CTRL)
		hasAll = hasAll && event.ctrlKey;
	else
		hasAll = hasAll && !event.ctrlKey;
	if(modifiers & Events.KEY_SHIFT)
		hasAll = hasAll && event.shiftKey;
	else
		hasAll = hasAll && !event.shiftKey;
	return hasAll;
};

Events.addKeyEvent = function(key, modifiers, eventListener)
{
	if(key.length > 1)
		throw new Error("key Event should only be bound to single key + modifiers");

	var keyCode = key.toUpperCase().charCodeAt(0);

	Events.addEventListener(Events.KEYUP, function(e)
	{
		if((Events.getKeyCode(e) == keyCode) && Events.hasModifiers(e, modifiers))
		{
			eventListener.call(null, e);
		}
	});
};

Events.fireEvent = function(component, eventname)
{
	if(document.createEvent)
	{
		var evt = document.createEvent("Events");
		evt.initEvent(eventname, true, true);
		component.dispatchEvent(evt);
	}
	else if(document.createEventObject)
	{
		var evt = document.createEventObject();
		component.fireEvent(eventname, evt);
	}
};

Events.EventHandler = function(handlerObject, handlerMethod)
{
	this._handlerObject = handlerObject;
	this._handlerMethod = handlerMethod;
};

Events.EventHandler.prototype.onEvent = function(e)
{
	return this._handlerMethod.call(this._handlerObject, e);
};

Events.EventHandler.prototype.anonymous = function()
{
	// return Events.wrapEventHandler(this._handlerObject, this._handlerMethod);
	return Events.wrapEventHandler(this, this.onEvent);
};

Events.wrapEventHandler = function(handlerObject, handlerMethod)
{
	return function(e)
	{
		return handlerMethod.call(handlerObject, e);
	};
};

Events.emptyEventHandler = function()
{
	return Events.wrapEventHandler(null, function()
	{
	});
};