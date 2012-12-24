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
//@requires("Requests")
//@requires("Strings")

WebSockets = {};
WebSockets.CONTEXT_SEPARATOR = "/";
WebSockets.CONTEXT_PARENT_DIR = WebSockets.CONTEXT_SEPARATOR + "..";
WebSockets.PROTOCOL_END = "://";

WebSockets.getBaseURL = function()
{
	// this is the current URL
	var loc = location.href;
	// remove "http" and replace it with "ws"
	// this way we keep the "s" for https and wss
	var url = "ws" + loc.substring(4);
	// remove all context paths after first "/" (except "://")
	var domainStart = url.indexOf(WebSockets.PROTOCOL_END) + WebSockets.PROTOCOL_END.length;
	var domainEnd = url.indexOf(WebSockets.CONTEXT_SEPARATOR, domainStart);
	if(domainEnd == -1)
		domainEnd = url.length;
	url = url.substring(0, domainEnd);
	
	return url;
};

WebSockets.getContext = function()
{
	// this is the current URL
	var url = location.href;
	// get the context
	var domainStart = url.indexOf(WebSockets.PROTOCOL_END) + WebSockets.PROTOCOL_END.length;
	var domainEnd = url.indexOf(WebSockets.CONTEXT_SEPARATOR, domainStart);
	if(domainEnd == -1)
		domainEnd = url.length;	
	var context = url.substring(domainEnd);
	// check if context is not empty
	if(context.length == 0)
		context = WebSockets.CONTEXT_SEPARATOR;
	// check context not ending with "/"
	if(context.endsWith(WebSockets.CONTEXT_SEPARATOR))
		context = context.substring(0, context.length-1);
	
	return context;
};

WebSockets.getRelativeURL = function(path)
{
	// get the base url
	var baseURL = WebSockets.getBaseURL();
	// get the current context
	var context = WebSockets.getContext();
	// add a leading "/" to path if necessary
	if(!path.startsWith(WebSockets.CONTEXT_SEPARATOR))
		path = WebSockets.CONTEXT_SEPARATOR + path;
	// if path targets parent dir ("/.."), remove last directory of current url
	// repeat for multiple parent dirs
	// if context does not contain enough directories all remaining "/.." will be removed and the context will be cleared
	while(path.startsWith(WebSockets.CONTEXT_PARENT_DIR + WebSockets.CONTEXT_SEPARATOR))
	{
		if(context.contains(WebSockets.CONTEXT_SEPARATOR))
			context = context.substring(0, context.lastIndexOf(WebSockets.CONTEXT_SEPARATOR));
		path = path.substring(WebSockets.CONTEXT_PARENT_DIR.length);
	}
	return baseURL + context + path;
};