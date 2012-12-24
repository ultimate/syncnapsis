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
function ListenerPoint_Square(x,y,r)
{
	this.xC = x;
	this.yC = y; 
	this.r = r;
	
	this.isIn = function(x,y)
	{
		return ((x <= this.xC+this.r) && (x >= this.xC-this.r) && (y <= this.yC+this.r) && (y >= this.yC-this.r));
	};
}

function ListenerPoint_Circle(x,y,r)
{
	this.xC = x;
	this.yC = y; 
	this.r = r;
	
	this.isIn = function(x,y)
	{		
		return ((x-this.xC)*(x-this.xC) + (y-this.yC)*(y-this.yC)) <= this.r*this.r;
	};
}

function ListenerArea(xArray, yArray, zArray)
{
	this.xArray = xArray;
	this.yArray = yArray;
	this.zArray = zArray;
}