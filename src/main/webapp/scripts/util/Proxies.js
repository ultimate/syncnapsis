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
	var proxy;
	// check wether the stub is a constructor
	if(typeof stub == "function")
	{
		// if so create a new instance
		proxy = new (stub)();
	}
	else
	{
		// an object has been given
		// simply use this one
		proxy = stub;
	}
		
	// set the invocationHandler for the stub
	proxy.invocationHandler = invocationHandler;
	
	var func;
	var args;
	var funcDecl;
	for(var prop in proxy)
	{
		if(typeof proxy[prop] == "function")
		{
			// this is the old function
			func = proxy[prop];
			// the arguments of the function
			args = Reflections.arguments(func);
			
			funcDecl = new StringBuilder();
			// create a new function declaration
			funcDecl.append("function(");
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
			funcDecl.append("};");
			
//			console.log(funcDecl.toString());
			
			// override the old function
			proxy[prop] = eval(funcDecl.toString());
		}
	}
	
	return proxy;
};