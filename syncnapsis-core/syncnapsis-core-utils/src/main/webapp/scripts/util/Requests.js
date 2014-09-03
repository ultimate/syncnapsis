/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MECHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Plublic License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 */
var HTTP = {};

// HTTP STATUS CODES
// 1XX ? Informationen
HTTP.STATUS_CONTINUE = 100;
HTTP.STATUS_SWITCHING_PROTOCOLS = 101;
HTTP.STATUS_PROCESSING = 102;
// 2XX ? Erfolgreiche Operation
HTTP.STATUS_OK = 200;
HTTP.STATUS_CREATED = 201;
HTTP.STATUS_ACCEPTED = 202;
HTTP.STATUS_NON_AUTHORITATIVE_INFORMATION = 203;
HTTP.STATUS_NO_CONTENT = 204;
HTTP.STATUS_RESET_CONTENT = 205;
HTTP.STATUS_PARTIAL_CONTENT = 206;
HTTP.STATUS_MULTI_STATUS = 207;
// 3XX ? Umleitung
HTTP.STATUS_MULTIPLE_CHOICE = 300;
HTTP.STATUS_MOVED_PERMANENTLY = 301;
HTTP.STATUS_FOUND = 302;
HTTP.STATUS_SEE_OTHER = 303;
HTTP.STATUS_NOT_MODIFIED = 304;
HTTP.STATUS_USE_PROXY = 305;
HTTP.STATUS_SWITCH_PROXY = 306;
HTTP.STATUS_TEMPORARY_REDIRECT = 307;
// 4XX ? Client-Fehler
HTTP.STATUS_BAD_REQUEST = 400;
HTTP.STATUS_UNAUTHORIZED = 401;
HTTP.STATUS_PAYMENT_REQUIRED = 402;
HTTP.STATUS_FORBIDDEN = 403;
HTTP.STATUS_NOT_FOUND = 404;
HTTP.STATUS_METHOD_NOT_ALLOWED = 405;
HTTP.STATUS_NOT_ACCEPTABLE = 406;
HTTP.STATUS_PROXY_AUTHENTICATION_REQUIRED = 407;
HTTP.STATUS_REQUEST_TIMEOUT = 408;
HTTP.STATUS_CONFLICT = 409;
HTTP.STATUS_GONE = 410;
HTTP.STATUS_LENGTH_REQUIRED = 411;
HTTP.STATUS_PRECONDITION_FAILED = 412;
HTTP.STATUS_REQUEST_ENTITY_TOO_LARGE = 413;
HTTP.STATUS_REQUEST_URI_TOO_LONG = 414;
HTTP.STATUS_UNSUPPORTED_MEDIA_TYPE = 415;
HTTP.STATUS_REQUEST_RANGE_NOT_SATISFIABLE = 416;
HTTP.STATUS_EXPECTATION_FAILED = 417;
HTTP.STATUS_I_M_A_TEAPOT = 418;
HTTP.STATUS_TOO_MANY_CONNECTIONS_FROM_YOUR_ADDRESS = 421;
HTTP.STATUS_UNPROCESSABLE_ENTITY = 422;
HTTP.STATUS_LOCKED = 423;
HTTP.STATUS_FAILED_DEPENDENCY = 424;
HTTP.STATUS_UNORDERED_COLLECTION = 425;
HTTP.STATUS_UPGRADE_REQUIRED = 426;
// 5XX ? Server-Fehler
HTTP.STATUS_INTERNAL_SERVER_ERROR = 500;
HTTP.STATUS_NOT_IMPLEMENTED = 501;
HTTP.STATUS_BAD_GATEWAY = 502;
HTTP.STATUS_SERVICE_UNAVAILABLE = 503;
HTTP.STATUS_GATEWAY_TIMEOUT = 504;
HTTP.STATUS_HTTP_VERSION_NOT_SUPPORTED = 505;
HTTP.STATUS_VARIANT_ALSO_NEGOTIATES = 506;
HTTP.STATUS_INSUFFICIENT_STORAGE = 507;
HTTP.STATUS_BANDWIDTH_LIMIT_EXCEEDED = 509;
HTTP.STATUS_NOT_EXTENDED = 510;

// HTTP METHODS
HTTP.GET = "GET";
HTTP.POST = "POST";
HTTP.HEAD = "HEAD";
HTTP.PUT = "PUT";
HTTP.DELETE = "DELETE";
HTTP.TRACE = "TRACE";
HTTP.OPTIONS = "OPTIONS";
HTTP.CONNECT = "CONNECT";

var AJAX = {};

AJAX.HEADER_CONTENT_TYPE = "Content-Type";
AJAX.CONTENT_TYPE_URL_ENCODED = "application/x-www-form-urlencoded";

AJAX.sendRequestUrlEncoded = function(url, content, method, doOnSuccess, doOnFailure, handler)
{
	var headers = {};
	headers[AJAX.HEADER_CONTENT_TYPE] = AJAX.CONTENT_TYPE_URL_ENCODED;
	return AJAX.sendRequest(url, content, method, headers, doOnSuccess, doOnFailure, handler);
};

AJAX.sendRequest = function(url, content, method, headers, doOnSuccess, doOnFailure, handler)
{
	var request = new AJAX.createXMLHttp();
	// for DEBUGGING
	if(true)
	{
		if(!AJAX.requests)
			AJAX.requests = new Array();
		AJAX.requests[AJAX.requests.length] = request;
		request.url = url;
		request.method = method;
		request.content = content;
		request.headers = headers;
	}
	request.open(method, url, true);
	for(h in headers)
	{
		if((typeof headers[h]) != "function")
			request.setRequestHeader(h, headers[h]);
	}
	request.onreadystatechange = function()
	{
		AJAX.processRequest(request, doOnSuccess, doOnFailure, handler);
	};
	request.send(content);
	return request;
};

AJAX.createXMLHttp = function()
{
	if(typeof XMLHttpRequest != "undefined")
		return new XMLHttpRequest();
	else if(window.ActiveXObject)
		return new new ActiveXObject("Microsoft.XMLHTTP");
	throw new Error("XMLHttp (AJAX) not supported");
};

AJAX.processRequest = function(request, doOnSuccess, doOnFailure, handler)
{
	if(request.readyState == 4)
	{
		if((request.status >= HTTP.STATUS_OK) && (request.status < HTTP.STATUS_MULTIPLE_CHOICE))
		{
			if(doOnSuccess)
				doOnSuccess.call(handler == undefined ? null : handler, request);
			else
				AJAX.defaultDoOnSuccess(request);
		}
		else
		{
			if(doOnFailure)
				doOnFailure.call(handler == undefined ? null : handler, request);
			else
				AJAX.defaultDoOnFailure(request);
		}
	}
};

AJAX.defaultDoOnSuccess = function(request)
{
	if(console)
		console.debug("request successful: " + request.url);
};

AJAX.defaultDoOnFailure = function(request)
{
	if(console)
		console.warn("request failed: " + request.url);
};

var DependencyManager = {};

DependencyManager.registrationIsDone = false;
DependencyManager.instantLoad = true;
DependencyManager.scriptSubpath = "scripts/";
DependencyManager.interval = 100;
DependencyManager.annotationText = "@requires";

DependencyManager.doOnLoadingFinished = new Array();
DependencyManager.doOnLoadingProgressed = new Array();

DependencyManager.scripts = new Array();
DependencyManager.scriptPaths = new Array();
DependencyManager.scriptVoid = new Array();
DependencyManager.scriptAdded = new Array();
DependencyManager.scriptContents = new Array();
DependencyManager.scriptContentChanged = new Array();
DependencyManager.scriptDependencies = new Array();
DependencyManager.scriptDoOnReload = new Array();

DependencyManager.scriptsLoaded = 1; // because of this script

// add this script
DependencyManager.scripts[0] = "Requests";
DependencyManager.scriptPaths[0] = null; // don't know this script's path
DependencyManager.scriptAdded[0] = false;
DependencyManager.scriptAdded[0] = true;
DependencyManager.scriptContents[0] = "";
DependencyManager.scriptContentChanged[0] = false;
DependencyManager.scriptDependencies[0] = {};
DependencyManager.scriptDoOnReload[0] = null;

DependencyManager.register = function(scriptName, scriptPath, scriptVoid, external, dependencies)
{
	if(DependencyManager.indexOf(scriptName) == -1)
	{
		var scriptIndex = DependencyManager.scripts.length;

		DependencyManager.scripts[scriptIndex] = scriptName;
		DependencyManager.scriptPaths[scriptIndex] = (external ? "" : DependencyManager.scriptSubpath) + scriptPath;
		DependencyManager.scriptVoid[scriptIndex] = (scriptVoid ? true : false);
		DependencyManager.scriptAdded[scriptIndex] = false;
		DependencyManager.scriptContents[scriptIndex] = null;
		DependencyManager.scriptDependencies[scriptIndex] = dependencies;

		if(DependencyManager.instantLoad)
			DependencyManager.loadScript(scriptIndex);
	}
};

DependencyManager.loadScript = function(scriptIndex)
{
	AJAX.sendRequestUrlEncoded(DependencyManager.scriptPaths[scriptIndex], null, HTTP.GET, function(request)
	{
		DependencyManager.scriptLoaded(scriptIndex, request.responseText);
	}, function(request)
	{
		DependencyManager.scriptNotLoaded(scriptIndex);
	}, null);
};

DependencyManager.reloadScript = function(scriptName, doOnReload)
{
	var scriptIndex = DependencyManager.indexOf(scriptName);
	DependencyManager.scriptDoOnReload[scriptIndex] = doOnReload;

	AJAX.sendRequestUrlEncoded(DependencyManager.scriptPaths[scriptIndex], null, HTTP.GET, function(request)
	{
		DependencyManager.scriptReloaded(scriptIndex, request.responseText);
	}, function(request)
	{
		DependencyManager.scriptNotLoaded(scriptIndex);
	}, null);
};

DependencyManager.eval = function(func)
{
	if(typeof (func) == "function")
		func.call(null);
	else if(typeof (func) == "string")
		eval(func);
};

DependencyManager.scriptLoaded = function(scriptIndex, content)
{
	console.log("script loaded: " + scriptIndex);
	DependencyManager.scriptContents[scriptIndex] = content;
	DependencyManager.scriptsLoaded++;
	DependencyManager.loadingProgressed();

	DependencyManager.addDependencies(scriptIndex, content);
	DependencyManager.checkScripts();
};

DependencyManager.scriptReloaded = function(scriptIndex, content)
{
	DependencyManager.scriptContents[scriptIndex] = content;
	if(DependencyManager.dependenciesSatisfied(scriptIndex))
		DependencyManager.addScript(scriptIndex);
	DependencyManager.eval(DependencyManager.scriptDoOnReload[scriptIndex]);
};

DependencyManager.scriptNotLoaded = function(scriptIndex)
{
	throw new Error("could not load script: '" + DependencyManager.scripts[scriptIndex] + "'");
};

DependencyManager.indexOf = function(scriptName)
{
	for( var i = 0; i < DependencyManager.scripts.length; i++)
	{
		if(DependencyManager.scripts[i] == scriptName)
			return i;
	}
	return -1;
};

DependencyManager.addScript = function(scriptIndex)
{
	if(!DependencyManager.scriptVoid[scriptIndex])
	{
		var id = "script_" + scriptIndex;

		var content = DependencyManager.scriptContents[scriptIndex];
		var scriptNode = document.getElementById(id);
		if(scriptNode == null)
		{
			scriptNode = document.createElement("script");
			scriptNode.id = id;
			scriptNode.type = "text/javascript";
			scriptNode.setAttribute("name", DependencyManager.scripts[scriptIndex]);
			if(console)
				console.debug("adding script_" + scriptIndex + ": " + DependencyManager.scripts[scriptIndex]);
			document.getElementsByTagName("head")[0].appendChild(scriptNode);
		}
		else
		{
			if(console)
				console.debug("updating script_" + scriptIndex + ": " + DependencyManager.scripts[scriptIndex]);
			// re-evaluating script is required (script content is not live updated)
			// ATTENTION: use window.eval(x) or eval.call(window, x) for global scope
			// (otherwise reloaded content is only available inside this function)
			window.eval(content);
		}
		scriptNode.text = content;
	}
	// mark script loaded
	DependencyManager.scriptAdded[scriptIndex] = true;
};

DependencyManager.addDependencies = function(scriptIndex, content)
{
	var dependencies = new Array();
	var currentIndex = 0;
	var end = 0;
	while((currentIndex = content.indexOf(DependencyManager.annotationText, currentIndex)) > 0)
	{
		currentIndex += DependencyManager.annotationText.length;
		end = currentIndex;
		if(content.charAt(currentIndex + 1) == "\"")
		{
			currentIndex = currentIndex + 2;
			end = content.indexOf("\"", currentIndex);
		}
		else if(content.charAt(currentIndex + 1) == "'")
		{
			currentIndex = currentIndex + 2;
			end = content.indexOf("'", currentIndex);
		}
		else
		{
			currentIndex = currentIndex + 1;
			end = content.indexOf(")", currentIndex);
		}
		dependencies[dependencies.length] = content.substring(currentIndex, end);
	}
	DependencyManager.scriptDependencies[scriptIndex] = dependencies;
};

DependencyManager.dependenciesSatisfied = function(scriptIndex)
{
	var script = DependencyManager.scripts[scriptIndex];
	var dependencies = DependencyManager.scriptDependencies[scriptIndex];
	var allDependenciesSatisfied = true;
	var dependencyIndex = -1;
	for( var i = 0; i < dependencies.length; i++)
	{
		dependencyIndex = DependencyManager.indexOf(dependencies[i]);
		if(dependencyIndex == -1)
			throw new Error("dependency '" + dependencies[i] + "' is undefined but required for '" + script + "'");
		allDependenciesSatisfied = allDependenciesSatisfied && DependencyManager.scriptAdded[dependencyIndex];
	}
	return allDependenciesSatisfied;
};

DependencyManager.checkScripts = function()
{
	if(!DependencyManager.registrationIsDone)
		return;
	var newScriptsAdded = 0;
	var allScriptsAdded = true;
	do
	{
		newScriptsAdded = 0;
		allScriptsAdded = true;
		for( var i = 0; i < DependencyManager.scripts.length; i++)
		{
			allScriptsAdded = allScriptsAdded && DependencyManager.scriptAdded[i];
			if(DependencyManager.scriptContents[i] != null)
			{
				if(!DependencyManager.scriptAdded[i])
				{
					// script loaded but not yet added
					// check dependencies before adding
					if(DependencyManager.dependenciesSatisfied(i))
					{
						DependencyManager.addScript(i);
						newScriptsAdded++;
					}
				}
			}
		}
	}
	while(newScriptsAdded > 0);
	if(allScriptsAdded)
		DependencyManager.loadingFinished();
};

DependencyManager.onLoadingProgressed = function(doOnLoadingProgressed)
{
	if(typeof (doOnLoadingProgressed) != "function" && typeof (doOnLoadingProgressed) != "string")
		throw new Error("callback must be either function or string for evaluation");
	DependencyManager.doOnLoadingProgressed[DependencyManager.doOnLoadingProgressed.length] = doOnLoadingProgressed;
};

DependencyManager.onLoadingFinished = function(doOnLoadingFinished)
{
	if(typeof (doOnLoadingFinished) != "function" && typeof (doOnLoadingFinished) != "string")
		throw new Error("callback must be either function or string for evaluation");
	DependencyManager.doOnLoadingFinished[DependencyManager.doOnLoadingFinished.length] = doOnLoadingFinished;
};

DependencyManager.registrationDone = function(doOnLoadingFinished)
{
	if(doOnLoadingFinished)
		DependencyManager.onLoadingFinished(doOnLoadingFinished);
	
	if(!DependencyManager.instantLoad)
	{
		// load scripts (except of 0 which is this one...)
		for(var i = 1; i < DependencyManager.scripts.length; i++)
		{
			DependencyManager.loadScript(i);
		}
	}

	DependencyManager.registrationIsDone = true;
	DependencyManager.checkScripts();
};

DependencyManager.loadingFinished = function()
{
	if(console)
		console.debug("loading finished!");
	for( var i = 0; i < DependencyManager.doOnLoadingFinished.length; i++)
	{
		DependencyManager.eval(DependencyManager.doOnLoadingFinished[i]);
	}
	DependencyManager.doOnLoadingFinished = new Array();
};

DependencyManager.loadingProgressed = function()
{
	for( var i = 0; i < DependencyManager.doOnLoadingProgressed.length; i++)
	{
		DependencyManager.eval(DependencyManager.doOnLoadingProgressed[i]);
	}
};

DependencyManager.getProgress = function()
{
	return Math.round(100*DependencyManager.scriptsLoaded / DependencyManager.scripts.length);
};

DependencyManager.defaultOnLoadingProgressed = function(progressBarID, progressFieldID)
{
	return function() {
		var progressBar = document.getElementById(progressBarID);
		var progressField = document.getElementById(progressFieldID);
		
//		var loaded = DependencyManager.scriptsLoaded;
//		var total  = DependencyManager.scripts.length;
//		var progress = Math.round(100*loaded/total) + "%";
		var progress = DependencyManager.getProgress() + "%";
		progressBar.style.width = progress;
		progressField.innerHTML = progress;
		
		if(console)
			console.log("progress: '" + progress + "'");
	};
};