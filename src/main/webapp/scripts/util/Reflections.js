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
var Reflections = {};

Reflections.type = {};
Reflections.type.UNDEFINED 	= "undefined";
Reflections.type.NULL 		= "null";
Reflections.type.BOOLEAN 	= "boolean";
Reflections.type.STRING 	= "string";
Reflections.type.NUMBER 	= "number";
Reflections.type.FUNCTION	= "function";

Reflections.arguments = function(func)
{
	if(typeof func != "function")
		throw new Error("This function requires a function as an argument to determine it's arguments.");

	// convert function to a String (to read the arguments from)
	var str = "" + func;

	// only handle the part of the arguments (between the first braces)
	str = str.substring(str.indexOf("(") + 1, str.indexOf(")"));

	var arguments = new Array();

	if(str.trim().length > 0)
	{
		arguments = str.split(",");

		for( var i = 0; i < arguments.length; i++)
		{
			arguments[i] = arguments[i].trim();
		}
	}

	return arguments;
};

Reflections.numberOfArguments = function(func)
{
	return Reflections.arguments(func).length;
};

Reflections.hasReturnValue = function(func)
{
	if(typeof func != "function")
		throw new Error("This function requires a function as an argument to determine it's arguments.");

	// convert function to a String (to read the return part from)
	var str = "" + func;
	var start = str.indexOf("return") + "return".length;
	var end = str.indexOf(";", start);

	var returnValue = str.substring(start, end).trim();
	return returnValue.length > 0;
};

Reflections.resolveName = function(object, objectHolder)
{
	if(!objectHolder)
		objectHolder = window;
	var names = new Array();
	for( var prop in objectHolder)
	{
		if(objectHolder[prop] == object)
			names[names.length] = prop;
	}
	if(names.length == 0)
		throw new Error("Object not found in ObjectHolder");
	else if(names.length == 1)
		return names[0];
	else
		return names;
};

Reflections.call = function(object, method, args)
{
	// since args is an array it will be passed as a single argument when using
	// method.call(object, args);
	// we would like the array to be splitted:
	// method.call(object, args[0], args[1], ... , args[n]);
	// we could simply use this line with a fixed number of args
	// but this way we would not really be flexible
	
	// so we dynamicly build the command for the number of given arguments
	var callSB = new StringBuilder();
	callSB.append("method.call(object");
	for(i = 0; i < args.length; i++)
		callSB.append(",args[" + i + "]");
	callSB.append(")");
	
	return eval(callSB.toString());
};