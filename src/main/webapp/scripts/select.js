/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
// @requires("Events")
	
var SELECT_OPEN = false;

Select = function(element)
{
	if(typeof(element) == "string")
		this.element = document.getElementById(element);
	else
		this.element = element;
	
	this.displayParent = document.createElement("div");
	this.element.appendChild(this.displayParent);
	
	this.display = document.createElement("span");
	this.displayParent.appendChild(this.display);
	
	this.list = document.createElement("ul");
	this.element.appendChild(this.list);
	
	this.element.classList.add("select");
	//this.element.classList.add("w36");
	
	this.options = new Array();
	
//	this.display = this.element.children[0];
//	this.list = this.element.children[1];
//	this.options = this.element.children[1].children;
	
	this.isOpen = false;
	this.value = null;
	this.valueIndex = null;
	this.highlightIndex = null;
	this.active = false;
	
	this.unhighlight = function() {
		for(var i = 0; i < this.options.length; i++)
			this.options[i].element.classList.remove("active");
	};
	this.highlight = function(index) {
		if(!index)
			index = this.valueIndex;
		if(index != null && index >= 0 && index < this.options.length)
			this.options[index].element.classList.add("active");
		this.highlightIndex = index;
	};
	this.open = function() {
		this.element.classList.add("open");
		this.isOpen = true;
		this.isActive = true;
		SELECT_OPEN = true;
	};
	this.close = function(stayActive) {
		this.element.classList.remove("open");
		this.isOpen = false;		
		this.isActive = false;
		if(stayActive)
			this.isActive = true;
		SELECT_OPEN = false;
		
		this.unhighlight();
		this.highlight();
	};
	this.getOptionContent = function(option) {
		var content = new Array();
		if(option.image)
		{
			content.push("<img src='");
			content.push(option.image);
			content.push("'>");
		}
		content.push(option.title);
		return content.join("");
	};
	this.onselect = function(oldValue, newValue) {};
	this.select = function(index, skipOnselect) {
		var oldValue = this.value;
		if(index != null && index >= 0 && index < this.options.length)
		{
			var option = this.options[index];
			this.value = option.value;
			this.valueIndex = index;
			this.display.innerHTML = this.getOptionContent(option);
		}
		else
		{
			this.value = null;
			this.valueIndex = null;
			this.display.innerHTML = "&nbsp;";
		}
		this.unhighlight();
		this.highlight();
		if(!skipOnselect)
			this.onselect(oldValue, this.value);
	};
	this.selectByValue = function(value, skipOnselect) {
		// look for a matching value
		for(var o = 0; o < this.options.length; o++)
		{
			if(this.options[o].value == value)
			{
				this.select(o, skipOnselect);
				return;
			}
		}
		this.select(null, skipOnselect);
	};
	this.selectPrevious = function() {
		if(this.valueIndex != null && this.valueIndex > 0)
			this.select(this.valueIndex-1);
		else
			this.select(0);
	};
	this.selectNext = function() {
		if(this.valueIndex != null && this.valueIndex < this.options.length-1)
			this.select(this.valueIndex+1);
		else
			this.select(0);
	};
	this.update = function() {
		while(this.list.children.length > 0)
			this.list.removeChild(this.list.children[0]);

		for(var i = 0; i < this.options.length; i++)
		{
			if(this.options[i].element == null)
			{
				this.options[i].element = document.createElement("li");
				this.options[i].element.innerHTML = this.getOptionContent(this.options[i]);
				this.list.appendChild(this.options[i].element);
				
				this.options[i].element.onclick = function(select, i) {
					return function(event)
					{
						select.select(i);
						select.close(true);// stay active	
					};
				} (this, i);
				this.options[i].element.onmousemove = function(select, i) {
					return function(event)
					{
						select.unhighlight();
						select.highlight(i);
					};
				} (this, i);
			}
		}
	};
	this.element.onclick = function(select) {
		return function(event)
		{
			if(select.isOpen)
			{
				select.close(true);
			}
			else
			{
				select.open();
			}
		};
	} (this);
	Events.addEventListener(Events.CLICK, function(select) {
		return function(event) {
			if(event.target != select.element && event.target != select.display && event.target != select.displayParent)
				select.close();
		};
	} (this));
	Events.addEventListener(Events.KEYDOWN, function(select) {
		return function(event) {
			if(select.isActive)
			{
				switch(event.keyCode) {
					case 37: // left
					case 38: // up
						select.selectPrevious();
						break; 
					case 39: // right
					case 40: // down
						select.selectNext();
						break;
					case 33: // page up
					case 36: // home
						select.select(0);
						break;
					case 34: // page down
					case 35: // end
						select.select(selec.options.length-1);
						break;
					case 9: // tab
						select.select(select.highlightIndex);
						select.close(); // not active
						break;
					case 13: // enter
						select.select(select.highlightIndex);						
					case 27: // escape
						select.close(true); // stay active
						break;
					
					default:
				}
				event.cancelBubble = true;
			}
		};
	} (this));
	Events.addEventListener(Events.KEYUP, function(select) {
		return function(event) {
			event.cancelBubble = true;
		};
	} (this));
	this.select(null);
};