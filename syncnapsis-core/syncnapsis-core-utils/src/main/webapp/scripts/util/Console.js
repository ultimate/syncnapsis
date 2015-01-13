/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
//@requires("Events")
//@requires("DateFormat")

// Available Console Functions		Console.js		Firefox (Firebug)	Opera (Dragonfly)	Chrome,IE,Safari
//	assert													x					x					-
//	clear													x					- (global)			-
//	count													x					x					-
//	debug								x					x					x					-
//	dir														x					x					-
//	dirxml													x					x					-
//	error								x					x					x					-
//	exception							x					x					-					-
//	group								x					x					x					-
//	groupCollapsed						x					x					x					-
//	groupEnd							x					x					x					-
//	info								x					x					x					-
//	log									x					x					x					-
//	memoryProfile											x					-					-
//	memoryProfileEnd										x					-					-
//	profile													x					x					-
//	profileEnd												x					x					-
//	table													x					x					-
//	time								x					x					x					-
//	timeEnd								x					x					x					-
//	timeStamp												x					-					-
//	trace													x					x					-
//	warn								x					x					x					-

// Console-Package
var Console = {};

// LEVEL-Constants
Console.LEVEL_DEBUG = 0;
Console.LEVEL_INFO = 1;
Console.LEVEL_WARN = 2;
Console.LEVEL_ERROR = 3;

Console.LEVEL_NAMES = {
	0 : "[DEBUG]",
	1 : "[INFO] ",
	2 : "[WARN] ",
	3 : "[ERROR]"
};

//internal Constants
Console.dateFormat = "yyyy-MM-dd HH:mm:ss.l";
Console.windowWidth = "800px";
Console.windowHeight = "400px";
Console.groupIndent = 4;

// html-version of console
Console.Window = function()
{
	this.window = document.createElement("div");

	this.window.style.border = "solid #AAAAAA 3px";
	this.window.style.background = "#000000";
	this.window.style.color = "#AAAAAA";
	this.window.style.position = "absolute";
	this.window.style.top = "0px";
	this.window.style.left = "0px";
	this.window.style.width = Console.windowWidth;
	this.window.style.height = Console.windowHeight;
	this.window.style.overflowY = "scroll";
	this.window.style.cursor = "default";

	this.cursor = this.window;
	this.indent = 0;

	this.show = function()
	{
		if(this.window.parent == null)
			document.body.appendChild(this.window);
		this.window.style.display = "block";
		this.window.visible = true;
	};

	this.hide = function()
	{
		this.window.style.display = "none";
		this.window.visible = false;
	};

	this.showOrHide = function()
	{
		if(this.visible)
			this.hide();
		else
			this.show();
	};

	this.group = function(message, collapsed)
	{
		// surrounding pre
		var pre = document.createElement("pre");
		pre.style.padding = "0px";
		pre.style.margin = "0px";
		
		// pre for the indent
		var ind = document.createElement("pre");
		ind.innerHTML = this.indentMessage("");
		ind.style.padding = "0px";
		ind.style.margin = "0px";
		ind.style.display = "inline";
		pre.appendChild(ind);		
		
		// pre for the +/-
		var node = document.createElement("pre");
		node.style.padding = "0px";
		node.style.margin = "0px";
		node.style.border = "1px solid";
		node.style.width = "10px"; 
		node.style.height = node.style.width; 
		node.style.display = "inline";
		node.style.overflow = "hidden";		
		pre.appendChild(node);
		
		var txt = document.createElement("pre");
		if(message)
			txt.innerHTML = " " + message;
		txt.style.padding = "0px";
		txt.style.margin = "0px";
		txt.style.display = "inline";
		pre.appendChild(txt);
				
		// div for the children
		var div = document.createElement("div");
		div.style.padding = "0px";
		div.style.margin = "0px";
		pre.appendChild(div);		

		pre.toggle = function()
		{
			if (this.lastChild.style.display == "none")
			{
				this.firstChild.nextSibling.innerHTML = "-";
				this.lastChild.style.display = "";
			}
			else
			{
				this.firstChild.nextSibling.innerHTML = "+";
				this.lastChild.style.display = "none";
			}
		};

		Events.addEventListener(Events.CLICK, function(e)
		{
			pre.toggle();
		}, node);
		
		pre.toggle(); // collapse
		if(!collapsed)
			pre.toggle(); // open

		this.cursor.appendChild(pre);
		this.cursor = div;
		this.indent += Console.groupIndent;
	};

	this.groupEnd = function()
	{
		if(this.cursor != this.window)
		{
			this.cursor = this.cursor.parent.parent;
			this.indent -= Console.groupIndent;
		}
	};

	this.appendMessage = function(message)
	{
		message = this.indentMessage(message);
		
		var pre = document.createElement("pre");
		pre.innerHTML = message;
		pre.style.padding = "0px";
		pre.style.margin = "0px";

		this.cursor.appendChild(pre);
		this.window.scrollTop = this.window.scrollHeight;
	};
	
	this.indentMessage = function(message)
	{
		for(var i = 0; i < this.indent; i++)
			message = " "  + message;
		return message;
	};

	this.hide();
};

var console; // define the variable to be able to check if it is available

Console.Console = function()
{
	var _console = this;

	// check availability of console
	if(console)
		_console = console;

	// backup all available variables (and functions) to 'private' variables
	// must be one of the first steps, otherwise we would backup our new
	// features...
	for( var x in _console)
	{
		_console["_" + x] = _console[x];
	}

//	// Opera clear
//	if(!_console.clear)
//		_console.clear = clear

		// function for formating messages
	_console.format = function(level, message)
	{
		if(!message)
			return "";
		return new Date().format(Console.dateFormat) + " " + Console.LEVEL_NAMES[level] + " | " + message;
	};

	// create the html-window of the console
	_console.window = new Console.Window();

	// generic log-function
	_console.doLog = function(message, logFunc, level)
	{
		if(typeof message == "string" || message instanceof String)
		{
			message = this.format(level, message);
			if(logFunc)
				logFunc.call(this, message);
			this.window.appendMessage(message);			
		}
		else
		{
			if(logFunc)
			{
				logFunc.call(this, this.format(level, message));
				logFunc.call(this, message);
			}
			message = this.format(level, message);
			this.window.appendMessage(message);			
		}
	};
	
	// override the functions needed	
	
	// logging functions (debug, info, warn, error, log)
	_console.debug = function(message)
	{
		this.doLog(message, this._debug, Console.LEVEL_DEBUG);
	};
	_console.info = function(message)
	{
		this.doLog(message, this._info, Console.LEVEL_INFO);
	};
	_console.warn = function(message)
	{
		this.doLog(message, this._warn, Console.LEVEL_WARN);
	};
	_console.error = function(message)
	{
		this.doLog(message, this._error, Console.LEVEL_ERROR);
	};
	_console.log = _console.debug;
	_console.exception = _console.error;
	
	// grouping
	_console.group = function(message)
	{
		message = this.format(Console.LEVEL_DEBUG, message);
		this.window.group(message, false);
		if(this._group)
		{
			if(message != null)
				this._group(message);
			else
				this._group();
		}
	};
	_console.groupCollapsed = function(message)
	{
		message = this.format(Console.LEVEL_DEBUG, message);
		this.window.group(message, true);
		if(this._groupCollapsed)
		{
			if(message != null)
				this._groupCollapsed(message);
			else
				this._groupCollapsed();
		}
	};
	_console.groupEnd = function()
	{
		this.window.groupEnd(false);
		if(this._groupEnd)
			this._groupEnd();
	};
	
	// timing
	_console._timers = {};
	_console.time = function(timerName)
	{
		this._timers[timerName] = (new Date()).getTime();
		this.log("Started: " + timerName);
		if(this._time)
			this._time(timerName);
		return this._timers[timerName];
	};
	_console.timeEnd = function(timerName)
	{
		var duration;
		if(this._timers[timerName])
		{
			duration = (new Date()).getTime() - this._timers[timerName];
			this.log(timerName + ": " + duration + "ms");
			this._timers[timerName] = null;
		}
		if(this._timeEnd)
			this._timeEnd(timerName);
		return duration;
	};

	return _console;
};

console = new Console.Console();

Events.addKeyEvent("D", Events.KEY_CTRL | Events.KEY_ALT, function(e)
{
	console.window.showOrHide();
});

Events.consoleDebugEventHandler = function()
{
	return Events.wrapEventHandler(console, console.debug);
};

Events.consoleInfoEventHandler = function()
{
	return Events.wrapEventHandler(console, console.info);
};

Events.consoleWarnEventHandler = function()
{
	return Events.wrapEventHandler(console, console.warn);
};

Events.consoleErrorEventHandler = function()
{
	return Events.wrapEventHandler(console, console.error);
};