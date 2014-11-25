/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
// @requires("Elements")
// @requires("Strings")

	
ComboSelect = function(sourceElement, targetElement)
{
	if(typeof(sourceElement) == "string")
		this.sourceElement = document.getElementById(sourceElement);
	else
		this.sourceElement = sourceElement;

	if(typeof(targetElement) == "string")
		this.targetElement = document.getElementById(targetElement);
	else
		this.targetElement = targetElement;
	
	this.sourceList = document.createElement("ul");
	this.sourceList.classList.add("comboselect");
	this.sourceList.classList.add("source");
	this.sourceElement.appendChild(this.sourceList);
	
	this.targetList = document.createElement("ul");
	this.targetList.classList.add("comboselect");
	this.targetList.classList.add("target");
	this.targetElement.appendChild(this.targetList);
	
	// Note: li.value is occupied for numbers for <ol> lists
	// -> hence "val" is used instead

	this.highlightIndex = null;
	
	var _disabled = false;
	this.setDisabled = function(disabled) {
		_disabled = disabled;
		if(disabled)
		{
			this.sourceList.classList.add("disabled");
			this.targetList.classList.add("disabled");
		}
		else
		{
			this.sourceList.classList.remove("disabled");
			this.targetList.classList.remove("disabled");
		}
	};
	this.isDisabled = function() {
		return _disabled;
	};
	
	this.unhighlight = function() {
		for(var i = 0; i < this.sourceList.children.length; i++)
			this.sourceList.children[i].classList.remove("active");
		for(var i = 0; i < this.targetList.children.length; i++)
			this.targetList.children[i].classList.remove("active");
	};
	this.highlight = function(option) {
		if(option != null)
			option.classList.add("active");
	};
	this.getOptionContent = function(option) {
		return option;
	};
	this.selectSource = function(indexes, deselectOthers) {
		for(var i = 0; i < this.sourceList.children.length; i++)
		{
			if(indexes.indexOf(i) != -1)
				this.sourceList.children[i].classList.add("selected");
			else if(deselectOthers)
				this.sourceList.children[i].classList.remove("selected");
		}
	};
	this.selectSourceByValue = function(values, deselectOthers) {
		// look for a matching value
		for(var i = 0; i < this.sourceList.children.length; i++)
		{
			if(values.indexOf(this.sourceList.children[i].val) != -1)
				this.sourceList.children[i].classList.add("selected");
			else if(deselectOthers)
				this.sourceList.children[i].classList.remove("selected");
		}
	};
	this.selectTarget = function(indexes, deselectOthers) {
		for(var i = 0; i < this.targetList.children.length; i++)
		{
			if(indexes.indexOf(i) != -1)
				this.targetList.children[i].classList.add("selected");
			else if(deselectOthers)
				this.targetList.children[i].classList.remove("selected");
		}
	};
	this.selectTargetByValue = function(values, deselectOthers) {
		// look for a matching value
		for(var i = 0; i < this.targetList.children.length; i++)
		{
			if(values.indexOf(this.targetList.children[i].val) != -1)
				this.targetList.children[i].classList.add("selected");
			else if(deselectOthers)
				this.targetList.children[i].classList.remove("selected");
		}
	};
	var _apply = function(from, to) {
		for(var i = 0; i < from.children.length; i++)
		{
			if(from.children[i].classList.contains("selected"))
			{
				from.children[i].classList.remove("selected");
				to.appendChild(from.children[i]);
				i--; // child is immediately removed, hence following indexes are shifted by 1
			}
		}
	};
	this.applySource = function() {
		this.unhighlight();
		_apply(this.sourceList, this.targetList);
	};
	this.applyTarget = function() {
		this.unhighlight();
		_apply(this.targetList, this.sourceList);
	};
	var _filter = function(what, matcher) {
		for(var i = 0; i < what.children.length; i++)
		{
			if(matcher(what.children[i].val))
				what.children[i].classList.remove("hidden");
			else
				what.children[i].classList.add("hidden");
		}
	};
	this.filterSource = function(matcher) {
		_filter(this.sourceList, matcher);
	};
	this.filterTarget = function(matcher) {
		_filter(this.targetList, matcher);
	};
	this.addOptions = function(options) {
		for(var o = 0; o < options.length; o++)
		{
			console.log("adding option: " + options[o]);
			
			var option = document.createElement("li");
			option.val = options[o];
			option.innerHTML = this.getOptionContent(options[o]);
			
			var add = document.createElement("span");
			add.innerHTML = "+";
			add.classList.add("add");
			add.onclick = function(comboselect) {
				return function(event) {
					comboselect.unhighlight();
					this.parentElement.classList.remove("selected");
					comboselect.targetList.appendChild(this.parentElement);
				};
			} (this);
			option.appendChild(add);
			
			var remove = document.createElement("span");
			remove.innerHTML = "-";
			remove.classList.add("remove");
			remove.onclick = function(comboselect) {
				return function(event) {
					comboselect.unhighlight();
					this.parentElement.classList.remove("selected");
					comboselect.sourceList.appendChild(this.parentElement);
				};
			} (this);
			option.appendChild(remove);
			
			this.sourceList.appendChild(option);

			option.onclick = function(comboselect)
			{
				return function(event)
				{
					if(event.target != this) //not element itselft, maybe add/remove button
						return;
					
					if(Events.hasModifiers(event, Events.KEY_CTRL))
					{
						if(this.classList.contains("selected"))
							this.classList.remove("selected");
						else
							this.classList.add("selected");
					}
					else if(Events.hasModifiers(event, Events.KEY_SHIFT))
					{
						// select all from last to this
						var lastFound, thisFound;
						for(var i = 0; i <= this.parentElement.children.length; i++)
						{
							if(this.parentElement.children[i] == this.parentElement.lastSelected)
								lastFound = true;
							else if(this.parentElement.children[i] == this)
								thisFound = true;
							
							if(lastFound || thisFound)
								this.parentElement.children[i].classList.add("selected");
							else
								this.parentElement.children[i].classList.remove("selected");
							
							if(lastFound && thisFound)
								break;
						}
					}
					else
					{
						// deselect all others
						for(var i = 0; i < this.parentElement.children.length; i++)
						{
							this.parentElement.children[i].classList.remove("selected");
						}
						// select this one
						this.classList.add("selected");
					}
					this.parentElement.lastSelected = this;
				}
			} (this);
			option.onmousemove = function(comboselect) {
				return function(event)
				{
					comboselect.unhighlight();
					comboselect.highlight(this);
				};
			} (this);
		}
	};
	
	Events.addEventListener(Events.MOUSEMOVE, function(comboselect) {
		return function(event) {
			if(Elements.isChildOrSame(comboselect.sourceList, event.target))
				return;
			if(Elements.isChildOrSame(comboselect.targetList, event.target))
				return;
			comboselect.unhighlight();
		};
	} (this));
};