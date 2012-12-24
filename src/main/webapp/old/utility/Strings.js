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
// Leerzeichen am Anfang loeschen
String.prototype.leftTrim = function ()
{
	return (this.replace(/^\s+/,""));
};
//Leerzeichen am Ende loeschen  
String.prototype.rightTrim = function ()
{
    return (this.replace(/\s+$/,""));
};
//Leerzeichen am Anfang und Ende loeschen
String.prototype.trim = function ()
{
	return (this.replace(/\s+$/,"").replace(/^\s+/,""));
};
//Leerzeichen am Anfang und Ende und mehrfach in der Mitte loeschen
String.prototype.superTrim = function ()
{
	return(this.replace(/\s+/g," ").replace(/\s+$/,"").replace(/^\s+/,""));
};
//Alle Leerzeichen loeschen
String.prototype.removeWhiteSpaces = function ()
{
    return (this.replace(/\s+/g,""));
};