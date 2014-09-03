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
// additional functionality for Strings
String.prototype.startsWith = function(prefix)
{
	if(prefix == null)
		return false;
	if(this.length < prefix.length)
		return false;
	return this.substring(0, prefix.length) == prefix;
};

String.prototype.endsWith = function(suffix)
{
	if(suffix == null)
		return false;
	if(this.length < suffix.length)
		return false;
	return this.substring(this.length - suffix.length, this.length) == suffix;
};

String.prototype.contains = function(part)
{
	return (this.indexOf(part) != -1);
};

// Initializes a new instance of the StringBuilder class
// and appends the given value if supplied
StringBuilder = function(value)
{
    this.strings = new Array("");
    this.append(value);
};

// Appends the given value to the end of this instance.
StringBuilder.prototype.append = function (value)
{
    if (value)
    {
        this.strings.push(value);
    }
};

// Clears the string buffer
StringBuilder.prototype.clear = function ()
{
    this.strings.length = 1;
};

// Converts this instance to a String.
StringBuilder.prototype.toString = function ()
{
    return this.strings.join("");
};