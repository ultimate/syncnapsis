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
function createWindow(action, content) {
  
	var newWindow;
//id, name, top, left, height, width, minDimension, maxDimension, resizeable, closeable, moveable, minimizeable	
	// main window container
	newWindow					= document.createElement('div');
	newWindow.isWindow			= true;
	newWindow.id 				= action.action;
	newWindow.action 			= action;
	newWindow.windowParams 		= action.windowParams.copy();

  	newWindow.setContent 	= newWindowSetContent;
	newWindow.close 	 	= newWindowCloseWindow;
	newWindow.minimize   	= newWindowMinimizeWindow;
	newWindow.maximize   	= newWindowMaximizeWindow;	
	newWindow.isMinimized	= newWindowIsMinimized;

	with (newWindow.style) {
		position 	= 'absolute';
 		zIndex 		= ++lastZ;
  		top 		= newWindow.windowParams.yPosition+'px';
  		left 		= newWindow.windowParams.xPosition+'px';
  		width 		= newWindow.windowParams.width+'px';
  		height 		= newWindow.windowParams.height+'px';
		border 		= "2px solid #000000";
	}
	

  	// titlebar
	newWindow.titleBar = document.createElement("div");
  	with (newWindow.titleBar.style) {
		position = "absolute";
		backgroundColor = "#CCCCCC"
		borderBottom = "1px solid #000000";
	}
	setX(newWindow.titleBar,0);
	setY(newWindow.titleBar,0);
	setHeight(newWindow.titleBar, 25);
	setWidth(newWindow.titleBar, getWidth(newWindow));
	newWindow.appendChild(newWindow.titleBar);
	addListener(newWindow.titleBar, "mousedown", newWindowMouseDownOnTitleBar);
	
	// title text
	newWindow.titleBar.titleText = document.createElement("div");
	with(newWindow.titleBar.titleText.style) {
		color = "#000000";
		position="absolute";
		top="2px";
		left = "5px";
		bottom = "2px";
		verticalAlign="middel";
	}
	newWindow.titleBar.titleText.appendChild(document.createTextNode(action.title));
	newWindow.titleBar.appendChild(newWindow.titleBar.titleText);
	
	// buttons
	newWindow.titleBar.button = new Array();
	//close
	newWindow.titleBar.button.close = document.createElement("div");
	with(newWindow.titleBar.button.close.style) {
		position = "absolute";
		top = "5px";
		height = "15px";
		width="15px";
		border = "1px solid #000000";
		backgroundColor = "#777777";
		cursor = "default";
	}
	addListener(newWindow.titleBar.button.close, "click", newWindowClickOnCloseButton);
	addListener(newWindow.titleBar.button.close, "mouseover", newWindowMouseOverCloseButton);
	addListener(newWindow.titleBar.button.close, "mouseout", newWindowMouseOutCloseButton);	

	newWindow.titleBar.button.close.appendChild(document.createTextNode("X"));
	newWindow.titleBar.appendChild(newWindow.titleBar.button.close);
	
	// minimize
	
	newWindow.titleBar.button.minimize = document.createElement("div");
	with(newWindow.titleBar.button.minimize.style) {
		position = "absolute";
		top = "5px";
		height = "15px";
		width="15px";
		border = "1px solid #000000";
		backgroundColor = "#777777";
		cursor = "default";
	}
	addListener(newWindow.titleBar.button.minimize, "click", newWindowClickOnMinimizeButton);
	addListener(newWindow.titleBar.button.minimize, "mouseover", newWindowMouseOverMinimizeButton );
	addListener(newWindow.titleBar.button.minimize, "mouseout", newWindowMouseOutMinimizeButton);	

	newWindow.titleBar.button.minimize.appendChild(document.createTextNode("^"));
	newWindow.titleBar.appendChild(newWindow.titleBar.button.minimize);
	
	// selektierbarkeit der titleleiste abschalten
	disableSelectForAll(newWindow.titleBar);
	
	// set button positions
	var i = 0;
	for(var key in newWindow.titleBar.button) {
		newWindow.titleBar.button[key].style.right= (5+i++*20)+"px";
	}
	// content field
	
	newWindow.contentField = document.createElement("div");
	with(newWindow.contentField.style) {
		color = "#DDDDDD";
		position = "absolute";
		top = "25px";
		left = "0px";
		right="0px";
		bottom = "0px";
		backgroundColor = "#445566";
		overflow = "scroll";
	}
	newWindow.appendChild(newWindow.contentField);
  
  	// loading bar
	if(content == null) {
		newWindow.loadingBar = document.createElement("div");
		with (newWindow.loadingBar.style) {
			position = "absolute";
			top = "0px";
			left = "0px";
			right = "0px";
			bottom = "0px";
			textAlign = "center";
			verticalAlign = "middel";
			backgroundColor = "#990000";
		}
		newWindow.loadingBar.appendChild(document.createTextNode("loading"));
		newWindow.contentField.appendChild(newWindow.loadingBar);
		disableSelect(newWindow.loadingBar);
	}
	
/*  
	if(maxDimension[0] == 'max')
		maxDimension[0] = getDocumentWidth()-1;
	if(maxDimension[1] == 'max')
		maxDimension[1] = getDocumentHeight()-1;
	if(maxDimension[2] == 'max')
		maxDimension[2] = getDocumentWidth()-1;
	if(maxDimension[3] == 'max')
 		maxDimension[4] = getDocumentHeight()-1;
	newWindow.maxDim	= maxDimension;  
	*/
  
  
  	// window functions
	
	
											
	document.body.appendChild(newWindow);

	return newWindow;
}

// static window functions
function newWindowGetWindow(element) {
	if(element.isWindow == true)
		return element;
	return newWindowGetWindow(element.parentNode);
}

// window functions
function newWindowSetContent(content) {
	this.contentField.innerHTML = content;
}
function newWindowCloseWindow() {
	document.body.removeChild(this);
	delete this;
}
function newWindowMinimizeWindow() {
	hide(this.contentField);
	this.windowParams.height = getHeight(this);
	setHeight(this, getHeight(this.titleBar));
	this.windowParams.minimized = true;
}	
function newWindowMaximizeWindow() {
	show(this.contentField);
	setHeight(this, this.windowParams.height);
	this.windowParams.minimized = false;
}	
function newWindowIsMinimized() {
	return this.windowParams.minimized;	
}

// titlebar functions

function newWindowMouseDownOnTitleBar(e) {
	var win = newWindowGetWindow(getSrcElement(e));
	
	// mouseDown auf die Buttons ausschließen
	if(getSrcElement(e) == win.titleBar || getSrcElement(e) == win.titleBar.titleText) {
		addListener(document, "mousemove", newWindowMouseMoveOnTitleBar);
		addListener(document, "mouseup", newWindowMouseUpOnTitleBar);
		document.newWindowMovingWindow = win;
		document.mousedownX = getMouseX(e);
		document.mousedownY = getMouseY(e);
		setZ(win,++lastZ);
	}
}
function newWindowMouseUpOnTitleBar(e) {
	removeListener(document, "mousemove", newWindowMouseMoveOnTitleBar);
	removeListener(document, "mouseup", newWindowMouseUpOnTitleBar);

	document.newWindowMovingWindow.windowParams.xPosition = getX(document.newWindowMovingWindow);
	document.newWindowMovingWindow.windowParams.yPosition = getY(document.newWindowMovingWindow);	
	// TODO änderung der position an die datenbank übergeben	
}
function newWindowMouseMoveOnTitleBar(e) {
	var x = getMouseX(e);
	var y = getMouseY(e);
	setX(document.newWindowMovingWindow, (document.newWindowMovingWindow.windowParams.xPosition+x-document.mousedownX));
	setY(document.newWindowMovingWindow, (document.newWindowMovingWindow.windowParams.yPosition+y-document.mousedownY));	
}

// close button functions
function newWindowClickOnCloseButton(e) {
	this.parentNode.parentNode.close();
}
function newWindowMouseOverCloseButton(e) {
	this.style.backgroundColor = "#999999";
}
function newWindowMouseOutCloseButton(e) {
	this.style.backgroundColor = "#777777";
}

// minimize button functions
function newWindowClickOnMinimizeButton(e) {
	var win = newWindowGetWindow(this);
	if(win.isMinimized())
		win.maximize();
	else
		win.minimize();
}
function newWindowMouseOverMinimizeButton(e) {
	this.style.backgroundColor = "#999999";
}
function newWindowMouseOutMinimizeButton(e) {
	this.style.backgroundColor = "#777777";
}

