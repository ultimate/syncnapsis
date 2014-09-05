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
// no requirements
var TABS_HORIZONTAL = 1;
var TABS_VERTICAL = 2;

Tabs = function(barId, barMode, containerId, containerMode, selectedOverwriteWidth, selectedOverwriteHeight)
{
	var bar = barId ? document.getElementById(barId) : null;
	var container = document.getElementById(containerId);

	var childElem;
	var childElemType;

	if(containerMode)
	{
		// we will reattach all children into a proper child element
		// this way we can assure all childs are handled equally
		if(containerMode == TABS_HORIZONTAL)
		{
			container.classList.add("tabcontainer_horizontal");
			childElemType = "span";
		}
		else if(containerMode == TABS_VERTICAL)
		{
			container.classList.add("tabcontainer_vertical");
			childElemType = "div";
		}
		var containerChildren = container.children[0].children;
		var childrenCount = containerChildren.length;
		for( var i = 0; i < childrenCount; i++)
		{
			childElem = document.createElement(childElemType);
			// always remove first child (since new elements are added at the end)
			childElem.appendChild(containerChildren[0]);
			container.children[0].appendChild(childElem);
		}
	}


	
	this.onSelect = function(index)
	{
		//placeholder
	};

	this.select = function(index)
	{
		console.log("select " + index + " (" + bar + ", " + container + ")");
		if(bar)
		{
			for( var i = 0; i < bar.children.length; i++)
			{
				if(i == index)
					bar.children[i].className = "selected";
				else
					bar.children[i].className = "";
			}
		}
		for( var i = 0; i < container.children[0].children.length; i++)
		{
			if(i == index)
			{
				container.children[0].children[i].className = "selected";
				if(selectedOverwriteWidth)
				{
					console.log("setting width to " + selectedOverwriteWidth + "px")
					container.children[0].children[i].style.width = selectedOverwriteWidth + "px";
					container.children[0].children[i].style.maxWidth = selectedOverwriteWidth + "px";
					container.children[0].children[i].style.minWidth = selectedOverwriteWidth + "px";
				}
				if(selectedOverwriteHeight)
				{
					console.log("setting height to " + selectedOverwriteHeight + "px")
					container.children[0].children[i].style.height = selectedOverwriteHeight + "px";
					container.children[0].children[i].style.maxHeight = selectedOverwriteHeight + "px";
					container.children[0].children[i].style.minHeight = selectedOverwriteHeight + "px";
				}
			}
			else
			{
				container.children[0].children[i].className = "";
				if(selectedOverwriteWidth)
				{
					container.children[0].children[i].style.width = "";
					container.children[0].children[i].style.maxWidth = "";
					container.children[0].children[i].style.minWidth = "";
				}
				if(selectedOverwriteHeight)
				{
					container.children[0].children[i].style.height = "";
					container.children[0].children[i].style.maxHeight = "";
					container.children[0].children[i].style.minHeight = "";
				}
			}
		}
		this.onSelect(index);
	};

	if(bar)
	{
		var barSize;
		var barSizeParam;
		if(barMode == TABS_HORIZONTAL)
		{
			barSize = bar.offsetWidth;
			barSizeParam = "width";
			bar.classList.add("tabbar_horizontal");
		}
		else if(barMode == TABS_VERTICAL)
		{
			barSize = bar.offsetHeight;
			barSizeParam = "height";
			bar.classList.add("tabbar_vertical");
		}
		
		var childrenCount = bar.children.length;
		barSize -= ((childrenCount - 1) * 3 + 2);
		var s0 = Math.floor(barSize / childrenCount);
		var gap = barSize - s0 * childrenCount;
		
		console.log("barSize=" + barSize + "  children=" + childrenCount + "  s0=" + s0 + "  gap = " + gap);

		for( var i = 0; i < childrenCount; i++)
		{
			bar.children[i].href = "#"; // add this if you want a "hand" cursor instead of an "arrow"
			bar.children[i].onclick = function(tabs, index)
			{
				return function()
				{
					tabs.select(index);
				};
			}(this, i);
			if(gap == 0)
				bar.children[i].style[barSizeParam] = s0 + "px";
			else
			{
				// in case 2: shift i by 0.5 to be centered
				// but since there may be the case gap is uneven and children not
				// we reshift it by 0.4 to weight one direction more than the other
				if(Math.abs(childrenCount / 2 - (i + 1 - 0.4)) <= gap / 2)
					bar.children[i].style[barSizeParam] = (s0 + 1) + "px";
				else
					bar.children[i].style[barSizeParam] = (s0) + "px";
			}
			bar.children[i].style.lineHeight = bar.children[i].offsetHeight + "px";
			// console.log("width set child #" + i + ": " +
			// children[i].style[barSizeParam]);
		}
	}
	
	this.select(0);
};