var TYPE_RETURN_CONFIRM = 11;
var TYPE_RETURN_CONTENT = 12;
var TYPE_NO_WINDOW = 20;
var TYPE_OPENING_WINDOW = 21;
var TYPE_CLOSING_WINDOW = 22;

var HTTP_OK = 200;

var scriptPath = basePath + "scripts/";
var templatePath = scriptPath + "gui/jstemplates/";

function sendActionRequest(action, params, executingWindow)
{
	if(!action)
		throw new Error("Action is null!");
	if(action.evalForm)
	{
		formBody = getRequestBody(evalForm);
		if(!params)
			params = formBody;
		else
			params = params + "&" + formBody;
	}
	
	var win = action.openWindow();	
	execOnReadyState = function(content, success)
	{
		if(success)
			action.execOnSuccess(content);
		else
			action.execOnFailure(content);			
		action.updateWindow(win, content);
		action.closeWindow(executingWindow);
	}
	
	var url = action.action + ".html";		
	return sendPlainRequest(url, params, action.returnType, execOnReadyState);
}

function sendPlainRequest(url, params, reqType, execOnReadyState)
{
	var req = createXMLHttp();
	req.open("POST", url, true);
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	req.onreadystatechange = function(){ processRequest(req, reqType, execOnReadyState) };
	req.send(params);
	return req;
}

function processRequest(req, reqType, execOnReadyState)
{
	if (req.readyState == 4)
	{
		var success = checkRequest(req, reqType);
		var response = req.responseText;
		if(execOnReadyState)
			execOnReadyState.call(null, response, success);
	}
}

function checkRequest(req, reqType)
{
	if(req.status == HTTP_OK)
	{
		if(reqType == TYPE_RETURN_CONTENT)
			return true;
		else if(req.responseText == "success")
			return true;
	}

	// Hier erfolgt ein Error-Handling falls der Request fehlschlug
	error("Error completing request:\nHTTP-Response-Code: " + req.status + "\nContent: " + req.responseText);
			
	return false;
}

function createXMLHttp()
{
	if (typeof XMLHttpRequest != "undefined")
		return new XMLHttpRequest();
	else if (window.ActiveXObject)
	{
		var httpVersions = new Array("Microsoft.XmlHttp", "MSXML2.XmlHttp", "MSXML2.XmlHttp.3.0", "MSXML2.XmlHttp.4.0", "MSXML2.XmlHttp.5.0");
		for (var i = httpVersions.length -1; i >= 0; i--)
		{
			try
			{
				httpObj = new ActiveXObject(httpVersions[i]);
				return httpObj;
			}
			catch(e)
			{
				// nothing (versuche weiter mit naechstem Typ)
			}
		}
	}
	throw new Error("XMLHttp (AJAX) not supported");
}

function getRequestBody(formName)
{
	var oForm = document.forms[formName];
    var aParams = new Array();
    
	var e;
    for (var i = 0 ; i < oForm.elements.length; i++) {
		e = oForm.elements[i];
		if( e.type=="text" ||
			e.type=="password" ||
			e.type=="file" ||
			e.type=="image" ||
			e.type=="hidden")
		{
	        var sParam = encodeURIComponent(e.name);
	        sParam += "=";
	        sParam += encodeURIComponent(e.value);
	        aParams.push(sParam);
		}
		else if(e.type=="checkbox")
		{
	        var sParam = encodeURIComponent(e.name);
	        sParam += "=";
	        sParam += (e.checked?"on":"off");
	        aParams.push(sParam);
		}
		else if(e.type=="radio")
		{
			if(e.checked)
			{
		        var sParam = encodeURIComponent(e.name);
		        sParam += "=";
				sParam += encodeURIComponent(e.value);
		        aParams.push(sParam);
			}
		}
		else if(e.type=="select-one")
		{
			if(e.selectedIndex >= 0)
			{
				var sParam = encodeURIComponent(e.name);
				sParam += "=";
				sParam += encodeURIComponent(e.options[e.selectedIndex].value);
				aParams.push(sParam);
			}
		}
		else if(e.type=="select-multiple")
		{
			var count = 0;
			for(var j = 0; j < e.options.length; j++)
			{
				if(e.options[j].selected)
				{
					var sParam = encodeURIComponent(e.name) + "[" + count++ + "]";
					sParam += "=";
					sParam += encodeURIComponent(e.options[j].value);
					aParams.push(sParam);
				}
			}
		}
    } 
    return aParams.join("&");
}

// vars for scripts
var scriptsToLoad     		= new Array();
var scriptBrowser	  		= new Array();
var scriptIsLoading   		= new Array();
var scriptsToSetReady 		= new Array();
var scriptIsReady     		= new Array();
var scriptsNeeded     		= new Array();
var scriptExternal    		= new Array();

function addScriptToLoad(name, browser, scriptsToSetReadyForThis, scriptsNeededForThis, external)
{
	scriptsToLoad[name]					 				= name;
	scriptBrowser[name]					 				= browser;
	scriptIsLoading[name]					 			= false;
	scriptsToSetReady[name]						 		= scriptsToSetReadyForThis;
	scriptIsReady[name] 								= false;
	scriptsNeeded[name]					 				= scriptsNeededForThis;
	scriptExternal[name]				 				= external;
}

function loadScript(scriptName, external)
{
	sendPlainRequest((external ? "" : scriptPath ) + scriptName, null, TYPE_RETURN_CONTENT, function(content, success) { addScript(scriptName, content); } ); 
}

function addScript(scriptName, content)
{
	var newScriptNode = document.createElement("script");
	newScriptNode.type = "text/javascript";
	newScriptNode.text = content;
	document.getElementsByTagName("head")[0].appendChild(newScriptNode);
	STSR = scriptsToSetReady[scriptName].split(",");
	for(var j in STSR)
	{
		scriptIsReady[STSR[j]] = true;
	}
}