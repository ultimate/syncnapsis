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
DependencyManager.addDependency("lib/raphael-min.js");
DependencyManager.addDependency("ui/Component.js");

Desktop = function(theme)
{
	this.theme = new Theme();
	this.setTheme(theme);
		
	this.paper = new Raphael();
	
	this.components = new Array();
};

Desktop.prototype = Component;

Desktop.prototype.setTheme = function(theme)
{
	if(Themes.isValid(theme))
	{
		this.theme = theme;
	}
	else
	{
		throw new Error("invalid theme: " + theme);
	}
};