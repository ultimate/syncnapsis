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
var Elements = {};

Elements.isBelow = function(parent, element)
{
	var node = element;
	var below = 0;
	
	do
	{
		if(node == parent)
			return below;
		node = node.parentElement;
		below++;
	} while(node != null);
	return -1;
};

Elements.isChild = function(parent, element, directOnly)
{
	var below = this.isBelow(parent, element); 
	if(directOnly)
		return below == 1;
	else
		return below >= 1;
};

Elements.isChildOrSame = function(parent, element)
{
	var below = this.isBelow(parent, element); 
	return below >= 0;
};