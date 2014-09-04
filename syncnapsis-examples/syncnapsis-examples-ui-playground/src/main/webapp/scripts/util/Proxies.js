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
//@requires("Reflections")
//@requires("Strings")

// Proxy-Package
var Proxies = {};

// for Proxies InvocationHandlers are required
// the following Class shows how a InvocationHandler must look like to be usable for the Proxy-Class

// similar to java.lang.reflect.InvocationHandler
Proxies.InvocationHandler = function()
{
};

Proxies.InvocationHandler.prototype.invoke = function(proxy, method, args)
{
};

// now the true Proxy-Class
// param stub - the "interface" (an empty object with methods) to generate the proxy for
// param invocationHandler - the InvocationHandler that handles the Method calls
Proxies.newProxyInstance = function(stub, invocationHandler)
{
	var proxy = {};
	// set the invocationHandler for the stub
	proxy.invocationHandler = invocationHandler;
	// set the stub for this proxy
	// (the stub is the "original" object this proxy will replace)
	// check wether the stub is a constructor
	if(typeof stub == Reflections.type.FUNCTION)
	{
		// if so create a new instance
		proxy.stub = new (stub)();
	}
	else
	{
		// an object has been given
		// simply use this one
		proxy.stub = stub;
	}	
	
	var func;
	var args;
	var funcDecl;
	for(var prop in proxy.stub)
	{
		if(typeof proxy.stub[prop] == Reflections.type.FUNCTION)
		{
			// create a new function definition in the
			// proxy using the information of the stub
			
			// this is the old function
			func = proxy.stub[prop];
			// the arguments of the function
			args = Reflections.arguments(func);
			
			funcDecl = new StringBuilder();
			// create a new function declaration
			// starting with "(" to enable eval
			funcDecl.append("(function(");
			// append the arguments
			for(var i = 0; i < args.length; i++)
			{
				if(i > 0)
					funcDecl.append(", ");
				funcDecl.append(args[i]);
			}
			// start the method body
			funcDecl.append(")\n");
			funcDecl.append("{\n");
			// declare the variables for the InvocationHandler
			funcDecl.append("  var object = this;\n");
			funcDecl.append("  var method = this." + prop + ";\n")
			funcDecl.append("  var args = new Array();\n");
			// add all the arguments to the array (named arguments)
			for(var i = 0; i < args.length; i++)
			{
				funcDecl.append("  args[args.length] = " + args[i] + ";\n");
			}
			// add also unnamed (additional) arguments to the array
			funcDecl.append("  if(arguments.length > args.length)\n");
			funcDecl.append("  {\n");
			funcDecl.append("    for(var i = args.length; i < arguments.length; i++)\n");
			funcDecl.append("    {\n");
			funcDecl.append("      args[args.length] = arguments[i];\n");
			funcDecl.append("    }\n");
			funcDecl.append("  }\n");
			// do the forwarding to the InvocationHandler
			funcDecl.append("  return this.invocationHandler.invoke(object, method, args);\n")
			funcDecl.append("})"); // no semicolon, or the eval will not work!
			
//			console.log(funcDecl.toString());
			
			// add the newly created function
			proxy[prop] = eval(funcDecl.toString());
		}
		else
		{
			// simply copy the property
			proxy[prop] = proxy.stub[prop];
		}
	}
	
	return proxy;
};