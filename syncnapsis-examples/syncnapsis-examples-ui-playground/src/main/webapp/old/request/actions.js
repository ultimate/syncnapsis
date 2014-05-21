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
var actions = new Array();

function c_action(name, title, returnType, windowType, evalForm, execOnSuccessVal, execOnFailureVal, windowParams)
{
	this.action = name;
	this.title = title;
	this.returnType = returnType;
	this.windowType = windowType;
	this.evalForm = evalForm;
	this.execOnSuccessVal = execOnSuccessVal;
	this.execOnFailureVal = execOnFailureVal;
	this.windowParams = windowParams;
	
	this.execute = function(params, executingWindow)
	{
		return sendActionRequest(this, params, executingWindow);
	};
	
	this.openWindow = function()
	{
		if(this.windowType == TYPE_OPENING_WINDOW)
		{
			var win = createWindow(this, null);
			return win;
		}
		else
			return null;
	};
	
	this.updateWindow = function(win, content)
	{
		if(this.windowType == TYPE_OPENING_WINDOW || this.windowType == TYPE_CLOSING_WINDOW)
			win.setContent(content);
	};
	
	this.closeWindow = function(win)
	{
		if(this.windowType == TYPE_CLOSING_WINDOW)
			win.close();
	};
	
	this.execOnSuccess = function(content)
	{
		if(this.execOnSuccessVal)
			eval(this.execOnSuccessVal.replace(/{content}/, "content"));	
	};
	
	this.execOnFailure = function(content)
	{
		if(this.execOnFailureVal)
			eval(this.execOnFailureVal.replace(/{content}/, "content"));
	};
}

function c_windowParams(height, heightMax, heightMin, width, widthMax, widthMin, xPosition, xPositionMax, xPositionMin, yPosition, yPositionMax, yPositionMin,scrollableX, scrollableY, resizable, closable, moveable, minimizable, minimized, alwaysOnTop, allowedAlwaysOnTop, allowedMultiple)
{
	this.height 			= Number(height);
	this.heightMax 			= heightMax;
	this.heightMin 			= heightMin;
	this.width 				= Number(width);
	this.widthMax 			= widthMax;
	this.widthMin 			= widthMin;
	this.xPosition 			= Number(xPosition);
	this.xPositionMax 		= xPositionMax;
	this.xPositionMin 		= xPositionMin;
	this.yPosition 			= Number(yPosition);
	this.yPositionMax 		= yPositionMax;
	this.yPositionMin 		= yPositionMin;
	this.scrollableX		= scrollableX;
	this.scrollableY		= scrollableY;
	this.resizable			= resizable;
	this.closable			= closable;
	this.moveable			= moveable;
	this.minimizable		= minimizable;
	this.minimized			= minimized;
	this.alwaysOnTop		= alwaysOnTop;
	this.allowedAlwaysOnTop	= allowedAlwaysOnTop;
	this.allowedMultiple	= allowedMultiple;
	
	this.copy = function() { 
		return new c_windowParams(this.height, this.heightMax, this.heightMin, this.width, this.widthMax, this.widthMin,this.xPosition, this.xPositionMax, this.xPositionMin, this.yPosition, this.yPositionMax,this.scrollableX, this.scrollableY, this.resizable, this.closable,this.moveable, this.minimizable, this.minimized,this.alwaysOnTop,this.allowedAlwaysOnTop, this.allowedMultiple);
	}

}

function registerAction(name, title, returnType, windowType, evalForm, execOnSuccessVal, execOnFailureVal, createWindowParams, height, heightMax, heightMin, width, widthMax, widthMin, xPosition, xPositionMax, xPositionMin, yPosition, yPositionMax, yPositionMin,scrollableX, scrollableY, resizable, closable, moveable, minimizable, minimized, alwaysOnTop, allowedAlwaysOnTop, allowedMultiple)
{
	var windowParams;
	if(createWindowParams)
		windowParams = new c_windowParams(height, heightMax, heightMin, width, widthMax, widthMin, xPosition, xPositionMax, xPositionMin, yPosition, yPositionMax, yPositionMin,scrollableX, scrollableY,  resizable, closable, moveable, minimizable, minimized, alwaysOnTop, allowedAlwaysOnTop, allowedMultiple)
	else
		windowParams = null;
	var action = new c_action(name, title, returnType, windowType, evalForm, execOnSuccessVal, execOnFailureVal, windowParams);
	actions[name] = action;
}

function executeAction(name, params, executingWindow)
{
	actions[name].execute(params, executingWindow);
}