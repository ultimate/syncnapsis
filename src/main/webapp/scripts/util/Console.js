//@requires("Events")
//@requires("DateFormat")

// Available Console Functions		Firefox (Firebug)	Opera (Dragonfly)	Chrome,IE,Safari
//	assert									x					x					-
//	clear									x					- (global)			-
//	count									x					x					-
//	debug									x					x					-
//	dir										x					x					-
//	dirxml									x					x					-
//	error									x					x					-
//	exception								x					-					-
//	group									x					x					-
//	groupCollapsed							x					x					-
//	groupEnd								x					x					-
//	info									x					x					-
//	log										x					x					-
//	memoryProfile							x					x					-
//	memoryProfileEnd						x					x					-
//	profile									x					x					-
//	profileEnd								x					x					-
//	table									x					x					-
//	time									x					x					-
//	timeEnd									x					x					-
//	timeStamp								x					x					-
//	trace									x					x					-
//	warn									x					x					-

// Levels
var Levels = {};

// LEVEL-Constants
Levels.LEVEL_DEBUG = 0;
Levels.LEVEL_INFO = 1;
Levels.LEVEL_WARN = 2;
Levels.LEVEL_ERROR = 3;

Levels.LEVEL_NAMES = {
	0 : "DEBUG",
	1 : "INFO ",
	2 : "WARN ",
	3 : "ERROR"
};

ConsoleWindow = function()
{
	// init html-version of console
	this._window = document.createElement("div");

	this._window.style.border = "solid #AAAAAA 3px";
	this._window.style.background = "#000000";
	this._window.style.color = "#AAAAAA";
	this._window.style.position = "absolute";
	this._window.style.top = "0px";
	this._window.style.left = "0px";
	this._window.style.width = this._windowWidth;
	this._window.style.height = this._windowHeight;
	this._window.style.overflowY = "scroll";

	this._window.show = function()
	{
		if(this._window.parent == null)
			document.body.appendChild(this._window);
		this._window.style.display = "block";
		this._window.visible = true;
	};

	this._window.hide = function()
	{
		this._window.style.display = "none";
		this._window.visible = false;
	};

	this._window.showOrHide = function()
	{
		if(this._window.visible)
			this._window.hide();
		else
			this._window.show();
	};

	this._window.appendMessage = function(message)
	{
		var pre = document.createElement("pre");
		pre.innerHTML = message;
		pre.style.padding = "0px";
		pre.style.margin = "0px";

		this._window.appendChild(pre);
		this._window.scrollTop = this._window.scrollHeight;
	};

	this._window.hide();

	return this._window;
};

Console = function()
{
	var _console = this;

	// check availability of console
	try
	{
		if(console)
			_console = console;
	}
	catch(e)
	{
		// console not available
	}

	// backup all available variables (and functions) to 'private' variables
	// must be the first step, otherwise we would backup our new features...
	for(var x in _console)
	{
		_console["_" + x] = _console[x];
	}
	
	// function for formating messages
	_console.format = function(level, message)
	{
		return new Date().format(Logging.dateFormat) + " [" + Levels.LEVEL_NAMES[level] + "] | " + message;
	};

	// create the html-window of the console
	_console.window = new ConsoleWindow();

	return _console;
};

console = new Console();

Events.addKeyEvent("D", Events.KEY_CTRL | Events.KEY_ALT, function(e)
{
	console.window.showOrHide()
});