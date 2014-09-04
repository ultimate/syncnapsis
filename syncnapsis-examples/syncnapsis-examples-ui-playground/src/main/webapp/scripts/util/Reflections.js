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
var Reflections = {};

Reflections.type = {};
Reflections.type.UNDEFINED 	= "undefined";
Reflections.type.NULL 		= "null";
Reflections.type.BOOLEAN 	= "boolean";
Reflections.type.STRING 	= "string";
Reflections.type.NUMBER 	= "number";
Reflections.type.OBJECT		= "object";
Reflections.type.FUNCTION	= "function";

Reflections.typeMask = {};
Reflections.typeMask.UNDEFINED 	= 1;
Reflections.typeMask.NULL 		= 2;
Reflections.typeMask.BOOLEAN 	= 4;
Reflections.typeMask.STRING 	= 8;
Reflections.typeMask.NUMBER 	= 16;
Reflections.typeMask.OBJECT		= 32;
Reflections.typeMask.FUNCTION	= 64;
Reflections.typeMask.NONE		= 0x00;
Reflections.typeMask.VARIABLE	= ~Reflections.typeMask.FUNCTION & ~Reflections.typeMask.UNDEFINED & 0xFF;
Reflections.typeMask.ALL		= 0xFF

Reflections.getTypeMask = function(type)
{
	for(var t in Reflections.type)
	{
		if(Reflections.type[t] == type)
			return Reflections.typeMask[t];
	}
	return 0;
};

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

Reflections.addProperty = function(object, name, value, writable)
{
	var accessorName = name.substring(0, 1).toUpperCase() + name.substring(1);
	var setter = "set" + accessorName;
	var getter = "get" + accessorName;
	if(typeof value == Reflections.type.BOOLEAN)
		getter = "is" + accessorName;

	var _value = value;
	
	object[getter] = function()
	{
		return _value;
	};
	if(writable != false)
	{
		object[setter] = function(value)
		{
			_value = value;
		};
	}
	else if(object[setter] != undefined)
	{
		delete object[setter]; // = undefined;
	}
};

Reflections.addListProperty = function(object, listName, value, writable)
{
	var entryName;
	if(listName.endsWith("ies"))
		entryName = listName.substring(0, listName.length-3) + "y";
	else if(listName.endsWith("s"))
		entryName = listName.substring(0, listName.length-1);
	else
		entryName = listName;
	
	var _value = value;
	if(!(value instanceof Array))
		_value = [ value ];
	
	Reflections.addProperty(object, listName, _value, writable);
	
	var accessorName = entryName.substring(0, 1).toUpperCase() + entryName.substring(1);
	var adder = "add" + accessorName;
	var remover = "remove" + accessorName;

	if(writable != false)
	{
		object[adder] = function(value)
		{
			_value.push(value);
		};
		object[remover] = function(value)
		{
			if(_value.indexOf(value) != -1)
				_value.splice(_value.indexOf(value), 1);
		};
	}
};