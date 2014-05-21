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
function getMouseX(e) {
	return Number(window.event.clientX+document.body.scrollLeft);
}
function getMouseY(e) {
	return Number(window.event.clientY+document.body.scrollTop);
}
function getMouseButton(e) {
	if(window.event.button == 2) return 3;
		return Number(window.event.button);	
}
function getKeyCode(e) {
	return Number(window.event.keyCode);
}
function getDocumentWidth() {
	return Number(document.documentElement.clientWidth);
}
function getDocumentHeight() {
	return Number(document.documentElement.clientHeight);
}

function addListener(comp, lis, fun) {
	comp.attachEvent("on"+lis, fun);
}
function removeListener(comp, lis, fun) {
	comp.detachEvent("on"+lis, fun);
}


function show(comp) {
  comp.style.display = 'inline';	
}
function hide(comp) {
  comp.style.display = 'none';	
}

function getSrcElement(e) {
	return e.srcElement;
}
function disableSelect(target) {
	target.onselectstart = function() {return false;};
}

function setCursorPosition(textField, position){
	if(position < 0)
		position = 0;
	if(position > textField.value.length)
		position = textField.value.length;
	var range = textField.createTextRange();
	range.moveStart('textedit');
	range.moveStart('character', position);
	range.moveEnd('textedit');
	range.moveEnd('character', -(textField.value.length-position));
	range.select();
}


function setOpacity(comp, opac) {
	comp.style.filter = "alpha(opacity=" + opac + ")";
}

function getOpacity(comp) {
	if(!comp.style.filter)
		return 100;
	var result = comp.style.filter.replace(/alpha/g,"");
	result = result.replace(/\(opacity=/g, "");
	result = Number(result.replace(/\)/g, ""));							  
	return result ;

}

function disableSelectForAll(startElement) {
		disableSelect(startElement);
}

/* IE7 Workaround */

function attachFunctions(elem)
{
	elem.getX = function() {return getX(this);}
	elem.getY = function() {return getY(this);}
	elem.getZ = function() {return getZ(this);}
	
	elem.setX = function(x) {setX(this,x);}
	elem.moveToX = function(x, timeInMs) {moveToX(x,timeInMs);};
	elem.setY = function(y) {setY(this,y);}
	elem.moveToY = function(y, timeInMs) {moveToX(y,timeInMs);};
	elem.setZ = function(z) {setZ(this,z);}
	
	elem.getWidth = function() {return getWidth(this);};
	elem.getHeight = function() {return getHeight(this);};
	
	elem.setWidth = function(width) {return setWidth(this, width);};
	elem.setHeight = function(height) {return setHeight(this, height);};
	
	elem.setPosition = function(pos) {this.style.position = pos;};
	elem.fadeIn = function(timeInMs) {fadeIn(this,timeInMs); };
	
	elem.fadeOut = function(timeInMs) {fadeOut(this,timeInMs); };
	
	elem.setColor = function(color) {this.style.backgroundColor = color;};
}

function ElementWorkaround()
{ 
	var _createElement = document.createElement;
	document.createElement = function(tag)
	{
		var _elem = _createElement(tag);
		attachFunctions(_elem);
		return _elem;
	}
	
	var _getElementById = document.getElementById;
	document.getElementById = function(id)
	{
		var _elem = _getElementById(id);
		attachFunctions(_elem);
		return _elem;
	}
	
	var _getElementsByTagName = document.getElementsByTagName;
	document.getElementsByTagName = function(tag)
	{
		var _arr = _getElementsByTagName(tag);
		for(var _elem=0;_elem<_arr.length;_elem++)
			attachFunctions(_arr[_elem]);
		return _arr;
	}
	
	info("IE7 ElementWorkaround successful!");
}

ElementWorkaround();
