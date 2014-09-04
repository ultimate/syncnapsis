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
var DEBUG_LEVEL_INFO = 100;
var DEBUG_LEVEL_WARN = 101;
var DEBUG_LEVEL_ERROR = 102;
var debugMessages = new Array();
var debugWindow = null;
var debugOldCommands = new Array();
var debugActCommand = 0;

function debugMessage(msg, level) {
	this.msg = msg;
	this.level = level;
	this.time = new Date();
}

function info(msg) {
	debugMessages.push(new debugMessage(msg, DEBUG_LEVEL_INFO));
	showMessages();
}

function warn(msg) {
	debugMessages.push(new debugMessage(msg, DEBUG_LEVEL_WARN));
	showMessages();
}

function error(msg) {
	debugMessages.push(new debugMessage(msg, DEBUG_LEVEL_ERROR));
	showMessages();
}
function clearMessageBox() {
	var el = debugWindow.messageBox.firstChild;
	while (el != null) {
		debugWindow.messageBox.removeChild(el);
		el = debugWindow.messageBox.firstChild;
	}
	debugWindow.displayedMessages = 0;
}

function showMessages() {
	if (debugWindow == null)
		return;
	for (i = debugWindow.displayedMessages; i < debugMessages.length; i++) {
		if (debugWindow.showDebug[debugMessages[i].level] == false)
			continue;
		var newLine = document.createElement("div");
		newLine.style.borderBottom = "#000000 solid 1px";
		newLine.style.cursor = "default";
		var stacktrace = document.createElement("div");
		switch (debugMessages[i].level) {
		case DEBUG_LEVEL_INFO:
			newLine.style.background = "#00cd2b";
			stacktrace.style.background = "#33fe5e";
			break;
		case DEBUG_LEVEL_WARN:
			newLine.style.background = "#ffde00";
			stacktrace.style.background = "#ffff33";
			break;
		case DEBUG_LEVEL_ERROR:
			newLine.style.background = "#d80101";
			stacktrace.style.background = "#fb3434";
			break;

		}
		var time = debugMessages[i].time.toLocaleString().split(" ");
		var pre = document.createElement("pre");
		pre.style.padding = '0px';
		pre.style.margin = '0px';
		newLine.style.margin = '0px';
		newLine.appendChild(pre);
		pre.appendChild(document.createTextNode(time[time.length - 1] + " - "));
		pre.appendChild(document.createTextNode(debugMessages[i].msg));

		if (!window.event) {
			var call = showMessages.caller.caller;
			while (call != null) {
				var fname = (String(call)).substring((String(call))
						.indexOf(" "));
				fname = fname.substr(0, fname.indexOf("("));
				if (fname.length == 1)
					fname = " unbekannt";
				stacktrace.appendChild(document.createTextNode(fname + "()\n"));
				// stacktrace.appendChild(document.createElement("br"));
				try {
					if (call != call.caller)
						call = call.caller;
					else
						call = null;
				} catch (e) {
					stacktrace.appendChild(document.createTextNode(" ?\n"));
					// stacktrace.appendChild(document.createElement("br"));
					call = null;
				}
			}

			stacktrace.style.display = "none";

			addStackTraceListener(newLine, stacktrace);

			pre.appendChild(stacktrace);
		}
		debugWindow.messageBox.insertBefore(newLine,
				debugWindow.messageBox.firstChild);
		debugWindow.displayedMessages = i + 1;
	}
	// debugWindow.messageBox.scrollTop = 1000;

}
function addStackTraceListener(newLine, stacktrace) {

	if (document.addEventListener) {
		newLine.addEventListener("click", function() {
			DEBUG_mouseClickForShowHide(stacktrace);
		}, true);
	}

	// if(document.attachEvent) {
	// newLine.attachEvent("onclick",function()
	// {DEBUG_mouseClickForShowHide(stacktrace);});
	// }
}

function DebugWindow() {
	if (debugWindow != null && debugWindow.show == false) {
		document.body.appendChild(debugWindow);
		debugWindow.show = true;
		return;
	}
	if (debugWindow == null) {
		var oldCommandsString = document.cookie;
		oldCommandsString = oldCommandsString.substring(oldCommandsString
				.indexOf("debugOldCommands="));
		oldCommandsString = oldCommandsString.substr(0, oldCommandsString
				.indexOf(";"));
		oldCommandsString = oldCommandsString.substring(oldCommandsString
				.indexOf("=") + 1);
		if (oldCommandsString.indexOf("^^°^^") > -1) {
			var oldCommands = oldCommandsString.split("^^°^^");
			for ( var i = 0; i < oldCommands.length - 1; i++) {
				debugOldCommands.push(unescape(oldCommands[i]));
				debugActCommand++;
			}
		}
		debugWindow = document.createElement("div");
		debugWindow.showDebug = new Array();
		debugWindow.showDebug[DEBUG_LEVEL_ERROR] = true;
		debugWindow.showDebug[DEBUG_LEVEL_WARN] = true;
		debugWindow.showDebug[DEBUG_LEVEL_INFO] = true;
		debugWindow.displayedMessages = 0;
		with (debugWindow.style) {
			position = "absolute";
			right = '0px';
			top = '0px';
			height = '500px';
			width = '400px';
			border = 'solid 2px #999999';
			background = '#FFFFFF';
			color = '#000000';
			fontFamily = 'Verdana';
		}

		var titel = document.createElement("div");
		with (titel.style) {
			position = "absolute";
			top = '0px';
			left = '0px';
			width = '400px';
			height = '20px';
			borderBottom = 'solid 2px #999999';
			color = "#FFFFFF";
			background = '#444444';
			cursor = 'default';
			padding = "0px";
			margin = "0px";
		}
		var closeButton = document.createElement("div");
		with (closeButton.style) {
			position = "absolute";
			textAlign = "right";
			top = '-2px';
			right = '0px';
			height = '10px';
			width = '20px';
			cursor = 'default';
			padding = "0px";
			margin = "0px";
		}
		closeButton.appendChild(document.createTextNode("X"));
		titel.appendChild(closeButton);

		debugWindow.messageBox = document.createElement("div");
		with (debugWindow.messageBox.style) {
			position = "absolute";
			top = '44px';
			right = '0px';
			left = '0px';
			bottom = '0px';
			overflowY = 'scroll';
		}
		debugWindow.appendChild(debugWindow.messageBox);

		debugWindow.inputBox = document.createElement("input");

		with (debugWindow.inputBox.style) {
			position = "absolute";
			top = "20px";
			// height = "25px";
			width = "320px";

		}
		debugWindow.inputBox.value = "Befehl ausführen...";
		debugWindow.appendChild(debugWindow.inputBox);

		var optionBox = document.createElement("div");

		with (optionBox.style) {
			border = "1px solid #000000";
			position = "absolute";
			bottom = '27px';
			height = '100px';
			left = '0px';
			right = '0px';
			backgroundColor = "#FFFFFF";
			display = "none";
		}
		debugWindow.appendChild(optionBox);

		var errorOptionLabel = document.createElement("label");
		var errorOption;
		if (window.event)
			errorOption = document.createElement("<input type='checkbox'>");
		else {
			errorOption = document.createElement("input");
			errorOption.type = "checkbox";
		}
		errorOptionLabel.appendChild(errorOption);
		errorOptionLabel.appendChild(document.createTextNode("Level: Error"));
		errorOption.checked = true;
		optionBox.appendChild(errorOptionLabel);
		optionBox.appendChild(document.createElement("br"));

		var warnOptionLabel = document.createElement("label");
		var warnOption;
		if (window.event)
			warnOption = document.createElement("<input type='checkbox'>");
		else {
			warnOption = document.createElement("input");
			warnOption.type = "checkbox";
		}

		warnOptionLabel.appendChild(warnOption);
		warnOptionLabel.appendChild(document.createTextNode("Level: warn"));

		warnOption.checked = true;
		optionBox.appendChild(warnOptionLabel);
		optionBox.appendChild(document.createElement("br"));

		var infoOptionLabel = document.createElement("label");
		var infoOption;
		if (window.event)
			infoOption = document.createElement("<input type='checkbox'>");
		else {
			infoOption = document.createElement("input");
			infoOption.type = "checkbox";
		}
		infoOptionLabel.appendChild(infoOption);
		infoOptionLabel.appendChild(document.createTextNode("Level: info"));
		infoOption.checked = true;
		optionBox.appendChild(infoOptionLabel);
		optionBox.appendChild(document.createElement("br"));

		var optionButton = document.createElement("div");
		optionButton.appendChild(document.createTextNode("Optionen"));
		with (optionButton.style) {
			position = "absolute";
			top = "20px";
			right = "0px";
			height = "20px";
			width = "75px";
			textAlign = "center";
			border = "1px solid #000000";
			cursor = "default";
		}
		debugWindow.appendChild(optionButton);

		var clearButton = document.createElement("div");
		with (clearButton.style) {
			height = "22px";
			width = "140px";
			textAlign = "center";
			border = "1px solid #000000";
			cursor = "default";
		}
		clearButton.appendChild(document.createTextNode("Verlauf leeren"));
		optionBox.appendChild(clearButton);

		if (document.addEventListener) {
			titel.style.MozUserSelect = 'none';
			titel.addEventListener("mousedown", DEBUG_mouseDownForMoveFF, true);
			closeButton.addEventListener("click", DebugWindow, true);
			debugWindow.inputBox.addEventListener("focus", function() {
				debugWindow.inputBox.value = "";
				debugActCommand = debugOldCommands.length;
			}, true);
			debugWindow.inputBox.addEventListener("blur", function() {
				debugWindow.inputBox.value = "Befehl ausführen...";
			}, true);
			debugWindow.inputBox.addEventListener("keyup", function(e) {
				if (e.which == 38)
					DEBUG_commandUp();
				if (e.which == 40)
					DEBUG_commandDown();
				if (e.which == 13) {
					DEBUG_executeCommand(debugWindow.inputBox.value);
					debugWindow.inputBox.value = "";
				}
			}, true);

			optionButton.addEventListener("click", function() {
				DEBUG_mouseClickForShowHide(optionBox)
			}, true);
			errorOption.addEventListener("change", function() {
				debugWindow.showDebug[DEBUG_LEVEL_ERROR] = errorOption.checked;
				clearMessageBox();
				showMessages();
			}, true);
			warnOption.addEventListener("change", function() {
				debugWindow.showDebug[DEBUG_LEVEL_WARN] = warnOption.checked;
				clearMessageBox();
				showMessages();
			}, true);
			infoOption.addEventListener("change", function() {
				debugWindow.showDebug[DEBUG_LEVEL_INFO] = infoOption.checked;
				clearMessageBox();
				showMessages();
			}, true);
			clearButton.addEventListener("click", function() {
				debugMessages = new Array();
				clearMessageBox();
				showMessages();
			}, true);
		}
		if (document.attachEvent) {
			titel.onselectstart = function() {
				return false;
			};
			titel.attachEvent("onmousedown", DEBUG_mouseDownForMoveIE);
			closeButton.attachEvent("onclick", DebugWindow);
			debugWindow.inputBox.attachEvent("onfocus", function() {
				debugWindow.inputBox.value = "";
				debugActCommand = debugOldCommands.length;
			});
			debugWindow.inputBox.attachEvent("onblur", function() {
				debugWindow.inputBox.value = "Befehl ausführen...";
			});
			debugWindow.inputBox.attachEvent("onkeyup", function() {
				if (event.keyCode == 38)
					DEBUG_commandUp();
				if (event.keyCode == 40)
					DEBUG_commandDown();
				if (event.keyCode == 13) {
					DEBUG_executeCommand(debugWindow.inputBox.value);
					debugWindow.inputBox.value = "";
				}
			});
			optionButton.attachEvent("onclick", function() {
				DEBUG_mouseClickForShowHide(optionBox)
			});
			errorOption.attachEvent("onchange", function() {
				debugWindow.showDebug[DEBUG_LEVEL_ERROR] = errorOption.checked;
				clearMessageBox();
				showMessages();
			});
			warnOption.attachEvent("onchange", function() {
				debugWindow.showDebug[DEBUG_LEVEL_WARN] = warnOption.checked;
				clearMessageBox();
				showMessages();
			});
			infoOption.attachEvent("onchange", function() {
				debugWindow.showDebug[DEBUG_LEVEL_INFO] = infoOption.checked;
				clearMessageBox();
				showMessages();
			});
			clearButton.attachEvent("onclick", function() {
				debugMessages = new Array();
				clearMessageBox();
				showMessages();
			});
		}

		debugWindow.appendChild(titel);
		debugWindow.show = false;
	}

	showMessages();
	if (debugWindow.show == true) {
		document.body.removeChild(debugWindow);
		debugWindow.show = false;
	}
}
function DEBUG_commandUp() {
	if (debugOldCommands.length == 0)
		return;
	debugActCommand = Math.max(debugActCommand - 1, 0);
	debugWindow.inputBox.value = debugOldCommands[debugActCommand];
}

function DEBUG_commandDown() {
	if (debugOldCommands.length == 0)
		return;
	debugActCommand = Math.min(debugActCommand + 1, debugOldCommands.length);
	if (debugActCommand == debugOldCommands.length)
		debugWindow.inputBox.value = "";
	else
		debugWindow.inputBox.value = debugOldCommands[debugActCommand];
}

function DEBUG_executeCommand(command) {
	if (command.length == 0)
		return;
	try {
		var msg = eval(command);
		if (msg)
			info(msg);
	} catch (e) {
		error(e.name + ": " + e.message);
		// lastError = e;
	}
	if (debugOldCommands[debugOldCommands.length - 1] != command) {
		debugActCommand = debugOldCommands.push(command);
		var cookieString = "";
		for ( var k = 0; k < debugOldCommands.length; k++) {
			cookieString += escape(debugOldCommands[k]) + "^^°^^";
		}
		document.cookie = "debugOldCommands=" + cookieString;
	} else {
		debugActCommand = debugOldCommands.length;
	}
}

function DEBUG_mouseMoveForMoveFF(e) {
	with (debugWindow.style) {

		top = (Math.max(Number(debugWindow.oldTop) - Number(debugWindow.mouseY)
				+ Number(e.pageY), 0))
				+ 'px';
		right = (Math.max(Number(debugWindow.oldRight)
				+ Number(debugWindow.mouseX) - Number(e.pageX), 0))
				+ 'px';
	}
}
function DEBUG_mouseDownForMoveFF(e) {
	debugWindow.oldTop = debugWindow.style.top.replace(/px/g, "");
	debugWindow.oldRight = debugWindow.style.right.replace(/px/g, "");
	debugWindow.mouseX = e.pageX;
	debugWindow.mouseY = e.pageY;
	document.addEventListener("mousemove", DEBUG_mouseMoveForMoveFF, true);
	document.addEventListener("mouseup", DEBUG_mouseUpForMoveFF, true);
}
function DEBUG_mouseUpForMoveFF(e) {
	document.removeEventListener("mousemove", DEBUG_mouseMoveForMoveFF, true);
	document.removeEventListener("mouseup", DEBUG_mouseUpForMoveFF, true);
}

function DEBUG_mouseClickForShowHide(obj) {
	if (obj.style.display == "none")
		obj.style.display = "";
	else
		obj.style.display = "none";
}

function DEBUG_mouseMoveForMoveIE() {
	with (debugWindow.style) {

		top = (Math.max(Number(debugWindow.oldTop) - Number(debugWindow.mouseY)
				+ Number(window.event.clientY + document.body.scrollTop), 0))
				+ 'px';
		right = (Math.max(Number(debugWindow.oldRight)
				+ Number(debugWindow.mouseX)
				- Number(window.event.clientX + document.body.scrollLeft), 0))
				+ 'px';
	}
}

function DEBUG_mouseDownForMoveIE() {
	debugWindow.oldTop = debugWindow.style.top.replace(/px/g, "");
	debugWindow.oldRight = debugWindow.style.right.replace(/px/g, "");
	debugWindow.mouseX = window.event.clientX + document.body.scrollLeft;
	debugWindow.mouseY = window.event.clientY + document.body.scrollTop;
	document.attachEvent("onmousemove", DEBUG_mouseMoveForMoveIE);
	document.attachEvent("onmouseup", DEBUG_mouseUpForMoveIE);
}
function DEBUG_mouseUpForMoveIE() {
	document.detachEvent("onmousemove", DEBUG_mouseMoveForMoveIE);
	document.detachEvent("onmouseup", DEBUG_mouseUpForMoveIE);
}
if (document.addEventListener) {
	document.addEventListener("keyup", function(e) {
		if (e.which == 19)
			DebugWindow();
	}, true);
}
if (document.attachEvent) {
	document.attachEvent("onkeyup", function() {
		if (event.keyCode == 19)
			DebugWindow();
	});
}
DebugWindow();

info("Debugger gestartet");