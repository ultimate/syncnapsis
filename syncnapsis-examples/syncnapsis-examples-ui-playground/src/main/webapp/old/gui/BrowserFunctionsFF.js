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
	return Number(e.pageX);
}
function getMouseY(e) {
	return Number(e.pageY);
}
function getMouseButton(e) {
	return Number(e.which);	
}
function getKeyCode(e) {
	return Number(e.which);
}
function getDocumentWidth() {
	return Number(window.innerWidth);
}
function getDocumentHeight() {
	return Number(window.innerHeight);
}

function addListener(comp, lis, fun) {
	comp.addEventListener(lis,fun, true);
}
function removeListener(comp, lis, fun) {
	comp.removeEventListener(lis,fun, true);
}


function show(comp) {
  comp.style.display = null;	
}
function hide(comp) {
  comp.style.display = 'none';	
}
function getSrcElement(e) {
	return e.target;
}
function disableSelect(target) {
	if(target.style)
		target.style.MozUserSelect = 'none';
}

function setCursorPosition(textField, position){
	if(position < 0)
		position = 0;
	if(position > textField.value.length)
		position = textField.value.length;
	textField.selectionStart = position;
	textField.selectionEnd = position;
}

function setOpacity(comp, opac) {
	comp.style.opacity = (opac/100);
}

function getOpacity(comp) {
	if(!comp.style.opacity)
		return 100;
	return Number(comp.style.opacity*100);
}

function disableSelectForAll(startElement) {
	if(startElement == null) return;
	disableSelect(startElement);
	for(var el = startElement.nextSibling; el != null; el = el.nextSibling)
		disableSelectForAll(el);
	disableSelectForAll(startElement.firstChild);

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