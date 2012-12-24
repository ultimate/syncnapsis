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

Element.prototype.getX = function() {return getX(this);}
Element.prototype.getY = function() {return getY(this);}
Element.prototype.getZ = function() {return getZ(this);}

Element.prototype.setX = function(x) {setX(this,x);}
Element.prototype.moveToX = function(x, timeInMs) {moveToX(x,timeInMs);};
Element.prototype.setY = function(y) {setY(this,y);}
Element.prototype.moveToY = function(y, timeInMs) {moveToX(y,timeInMs);};
Element.prototype.setZ = function(z) {setZ(this,z);}

Element.prototype.getWidth = function() {return getWidth(this);};
Element.prototype.getHeight = function() {return getHeight(this);};

Element.prototype.setWidth = function(width) {return setWidth(this, width);};
Element.prototype.setHeight = function(height) {return setHeight(this, height);};

Element.prototype.setPosition = function(pos) {this.style.position = pos;};
Element.prototype.fadeIn = function(timeInMs) {fadeIn(this,timeInMs); };

Element.prototype.fadeOut = function(timeInMs) {fadeOut(this,timeInMs); };

Element.prototype.setColor = function(color) {this.style.backgroundColor = color;};