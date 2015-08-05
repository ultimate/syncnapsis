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
if(!Array.prototype.indexOf)
{
	// Find the index of an element
	Array.prototype.indexOf = function(what)
	{ 
		for(var i=0; i<this.length;i++)
		{
			if(this[i] == what) return i;
		}
		return -1;
	};
}

// Sort a numerical Array
Array.prototype.numSort = function()
{
	this.sort(function(a,b) {return a-b;});
};

// delete an object
Array.prototype.remove = function(what)
{
	var index = this.indexOf(what); 
	if(index != -1)
		this.removeAt(index);
	return this;
};

// delete an object
Array.prototype.removeAt = function(index)
{
	this.splice(index, 1);
	return this;
};

// insert an object at the specified index
Array.prototype.insertAt = function(what, index)
{
	this.splice(index, 0, what);
	return this;
};

// perform a binary search for the given entry
Array.prototype.binaryIndexOf = function(what, compFunc)
{
	'use strict';

	var minIndex = 0;
	var maxIndex = this.length - 1;
	var currentIndex;
	var currentElement;
	var resultIndex;

	while (minIndex <= maxIndex) {
		resultIndex = currentIndex = (minIndex + maxIndex) / 2 | 0;
		currentElement = this[currentIndex];

		if (compFunc ? compFunc(currentElement, what) < 0 : currentElement < what) {
			minIndex = currentIndex + 1;
		}
		else if (compFunc ? compFunc(currentElement, what) > 0 : currentElement > what) {
			maxIndex = currentIndex - 1;
		}
		else {
			return currentIndex;
		}
	}

	return ~currentIndex;
};

// perform a binary search for the given array for an element with the given id
Array.prototype.binaryIndexOfId = function(whatId)
{
	return this.binaryIndexOf({id: whatId}, function(o1, o2) { return o1.id - o2.id; });
};