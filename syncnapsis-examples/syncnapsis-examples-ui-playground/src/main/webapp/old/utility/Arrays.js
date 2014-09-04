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
// finde Index eines Elements in Array
function find(what, where)
{
	for(var i=0; i<where.length;i++)
	{
		if(where[i] == what) return i;
	}
	return -1;
}

function NumCompare(a,b)
{
	return a-b;
}

Array.prototype.find = function(what) { return find(what, this); };
Array.prototype.numSort = function() { this.sort(NumCompare); };