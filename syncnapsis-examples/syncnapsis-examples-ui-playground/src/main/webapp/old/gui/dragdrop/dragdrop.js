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
var dropFields = new Array();

function enableDrag(component, dropFieldIds, copy, copyNode) {
	component.copyNode = copyNode;
	component.copy = copy;
	component.dragEnabled = true;
	component.dropFieldIds = dropFieldIds;
	component.mouseDownListener = function(e) {mouseDownDrag(e, component)};
	component.mouseUpListener = function(e) {mouseUpDrag(e, component)};
	component.mouseMoveListener = function(e) {mouseMoveDrag(e, component)};
	component.dropField == null;
	component.dragging = false;
	component.save = saveOldPosition;
	component.restore = restoreOldPosition;
	component.style.overflow = "visible";
	addListener(component, "mousedown", component.mouseDownListener);
	addListener(document, "mouseup", component.mouseUpListener);
	addListener(document, "mousemove", component.mouseMoveListener);	
}


function saveOldPosition() {
	this.oldPosition = new Array();
	this.oldPosition.position = this.style.position;
	this.oldPosition.left = getX(this);
	this.oldPosition.top = getY(this);
	this.oldPosition.parent = this.parentNode;
	this.oldPosition.zindex = getZ(this);
}

function restoreOldPosition() {
	this.style.position = this.oldPosition.position;
	setX(this,this.oldPosition.left);
	setY(this, this.oldPosition.top);
	this.parentNode.removeChild(this);
	this.oldPosition.parent.appendChild(this);
	setZ(this, this.oldPosition.zindex);
}


function disableDrag(component) {
	if(component.dragEnabled) {
		component.dragEnabled = false;
		removeListener(component, "mousedown", component.mouseDownListener);
		removeListener(document, "mouseup", component.mouseUpListener);
		removeListener(document, "mousemove", component.mouseMoveListener);	
	}
}
function getDropEnabeldParent(comp) {
	var tmp = comp;
	while(tmp != document.body && !tmp.dragEnabled)
		tmp = tmp.parentNode;
		
	return tmp;	
}
function mouseDownDrag(e, component) {
	var component2 = getDropEnabeldParent(getSrcElement(e));
	if(isMouseOver(e,component) && component2 == component && getMouseButton(e) == 1) {

		if(component.isDropField == true) {
			disableDrop(component);
		}
		component.dragging = true;
		component.oldX = getXAbs(component);
		component.oldY = getYAbs(component);
		component.oldZ = getZ(component);
		setZ(component, ++lastZ);
		component.mouseX = getMouseX(e);
		component.mouseY = getMouseY(e);
		if(component.copyNode == null) {
			component.dragCopy = component.cloneNode(true);
		}
		else {
			component.dragCopy = component.copyNode.cloneNode(true);
		}
		setX(component.dragCopy,getXAbs(component));
		setY(component.dragCopy,getYAbs(component));		
		setZ(component.dragCopy, ++lastZ);
		document.body.appendChild(component.dragCopy);
		hide(component.dragCopy);
		setOpacity(component.dragCopy, 50);
	}
		
}

function mouseMoveDrag(e, component) {
	if(component.dragging == true) {
		show(component.dragCopy);	
		setX(component.dragCopy, getMouseX(e)-getWidth(component.dragCopy)/2);
		setY(component.dragCopy, getMouseY(e)-getHeight(component.dragCopy)/2);
		var dropElement = getDropElementUnderMouse(e,component);

		if(dropElement != null) {
			if(dropElement.size > 0) {
				var insertPos = dropElement.getInsertPosition(e);
				dropElement.showInsertPosition(insertPos.pos, component.dragCopy, insertPos.lineBegin);
			}
			else
				dropElement.showInsertPosition(0,component, false);
		}
		else {
			hideAllInsertPositions();
		}
		
	}
}

function mouseUpDrag(e, component) {
	if(component.dragging == true) {
		var dropElement = getDropElementUnderMouse(e,component);
		var checkIDs = -1;
		if(dropElement != null) {
			checkIDs = find(dropElement.dropFieldId, component.dropFieldIds);			
			if(checkIDs > -1)
			{
				var insertPos = dropElement.getInsertPosition(e);
				if(component.copy == true) {
					dropElement.addElement(component.dragCopy, insertPos.pos);
					setOpacity(component.dragCopy, getOpacity(component));
					enableDrag(component.dragCopy, component.dropFieldIds, false);
				}
				else
					dropElement.addElement(component, insertPos.pos);
			}
		}
		if(component.isDropField == true) {
			component.dropEnabled = true;
			dropFields.push(component);
		}
		if (component.copy == false || dropElement == null || checkIDs == -1) 
			document.body.removeChild(component.dragCopy);
		component.dragging = false;
	}
	hideAllInsertPositions();
}


function enableDrop(component, dropFieldId, layout, multiline, autoResize, elementAddedEvent, elementRemovedEvent, elementMovedEvent, smoothInsert) {
	component.smoothInsert = smoothInsert;
	component.elementAddedEvent = elementAddedEvent;
	component.elementMovedEvent = elementMovedEvent;	
	component.elementRemovedEvent = elementRemovedEvent;
	component.autoResize = autoResize;
	component.dropEnabled = true;
	component.isDropField = true;
	component.dropFieldId = dropFieldId;
	dropFields.push(component);
	component.layout = layout;
	component.multiline = multiline;
	component.firstElement = null;
	component.lastElement = null;
	component.addElement = addElementToDropField;
	component.removeElement = removeElementFromDropField;
	component.getInsertPosition = layout.getDropFieldInsertPosition;
	component.showInsertPosition = layout.showInsertPosInDropField;
	component.reorder = layout.reorder;
	component.isFull = layout.layoutFull;
	component.size = 0;
	setZ(component, ++lastZ);
	component.borderX = 2;
	component.borderY = 2;
	component.gapX = 0;
	component.gapY = 0;
	
}
function removeElementFromDropField(element) {
	if(element.dropField != this) return;
	if(this.size == 0) return;
	if(this.size == 1) {
		this.firstElement = null;
		this.lastElement = null;
	} else {
		if(this.firstElement == element && this.lastElement != element) {
			this.firstElement = element.nextElement;
			element.nextElement.prevElement = null;
			info("first element removed");
		} else {
			if(this.firstElement != element && this.lastElement == element) {
				this.lastElement = element.prevElement;
				element.prevElement.nextElement = null;
				info("last element removed");
			} else {
				element.prevElement.nextElement = element.nextElement;
				element.nextElement.prevElement = element.prevElement;
				info("other element removed");
			}
		}
	}
	this.removeChild(element);
	setX(element, getX(element)+getX(element.dropField));
	setY(element, getY(element)+getY(element.dropField));
//	document.body.appendChild(element);
	element.nextElement = null;
	element.prevElement = null;
	element.dropField = null;
	
	this.size--;
	if(this.elementMoved == false)
		this.reorder();
	
	if(this.elementRemovedEvent && this.elementMoved == false)
		this.elementRemovedEvent(element.pos);
	
}


function hideAllInsertPositions() {
	for(var i=0; i<dropFields.length; i++) {
		if(dropFields[i].insertPos != null) {
			hide(dropFields[i].insertPos);
		}
	}
}
	

function addElementToDropField(element, pos) {
	var elementMoved = false;
	var movedFrom = -1;
	var movedTo = pos;
	if (element.copy == false && element.parentNode != null) 
		element.save();	
	if(element.dropField == this) {
		var i=0;
		for(var el = this.firstElement; el != null; el = el.nextElement) {
			if (el == element) {
				movedFrom = i;
				break;
			}
			i++;
		}
		if(i < pos)
			pos -= 1;
		var tmp = this.elementRemovedEvent;
		this.elementRemovedEvent = null;
		this.elementMoved = true;
		this.removeElement(element);
		this.elementRemovedEvent = tmp;
		this.elementMoved = true;
		
	}
	else {
  		if(element.dropField != null) {
			element.dropField.removeElement(element);
		}
	}
		
	if(this.size == 0) {
		this.firstElement = element;
		this.lastElement = element;
		element.prevElement = null;
		element.nextElement = null;
		this.size = 1;
	}
	else {
		if(pos < this.size) {
			var el = this.firstElement;
			for(var i = 0; i<pos && el != this.lastElement; i++) {
				el = el.nextElement;
			}
			element.prevElement = el.prevElement;
			element.nextElement = el;
			
			
			if(pos == 0) {
				this.firstElement = element;
			}
			else {
				el.prevElement.nextElement = element;
			}
			el.prevElement = element;
		}
		else {
			element.prevElement = this.lastElement;
			element.nextElement = null;
			this.lastElement.nextElement = element;
			this.lastElement = element;
		}
		this.size++;
	}
	element.dropField = this;
	
	if(!this.reorder() || this.isFull()) {
		var tmp = element.elementRemovedEvent;
		element.elementRemovedEvent = null;
		this.removeElement(element);
		element.elementRemovedEvent = tmp;
		if (element.copy == false) 		
			element.restore();
		this.reorder();
		return false;
	}
	element.pos = pos;
	if(this.elementAddedEvent && !elementMoved)
		this.elementAddedEvent(element.id.substr(element.id.lastIndexOf(".")+1), pos);
	if(this.elementMovedEvent && elementMoved && movedFrom != pos)
		this.elementMovedEvent(movedFrom, pos);
		
	this.elementMoved = false;
	return true;
}


function disableDrop(component) {
	component.dropEnabled = false;
	dropFields.splice(find(component, dropFields),1);
}



function getDropElementUnderMouse(e) {
	var result = null;
	var maxZ = 0;
	for(var i = 0; i < dropFields.length; i++) {
		if(isMouseOver(e, dropFields[i]) && getZ(dropFields[i]) >  maxZ) {
			result = dropFields[i];
			maxZ = getZ(dropFields[i]);
		}
		
	}
	return result;
}

function isMouseOver(e, comp) {
	var x = getMouseX(e);
	var y = getMouseY(e);
	return(x > getXAbs(comp) && x < getXAbs(comp)+getWidth(comp) && y > getYAbs(comp) && y < getYAbs(comp)+getHeight(comp));
}