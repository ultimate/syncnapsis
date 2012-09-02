var autoCompleterCounter = new Array(); 
var autoCompleterCounterSet = new Array();
var autoCompleterSelect = new Array(); 
var autoCompleterSize = new Array(); 
var autoCompleterKeyCounter = 0;
var maxEntries = 10;
var lineHeight = 20;
var typeDelayAllowed = 250;
var currentNavigation = null;
var firstDelay = 500;
var moreDelays = 100;

function loadAutoCompleter(action, fieldId, fieldName)
{
	setTimeout("loadAutoCompleter2(" + ++autoCompleterKeyCounter + ", \"" + action + "\", \"" + fieldId + "\", \"" + fieldName + "\")", typeDelayAllowed);
}

function loadAutoCompleter2(keys, action, fieldId, fieldName) 
{ 
	if(keys < autoCompleterKeyCounter)
		return;
	if(!autoCompleterCounter[fieldId])
		autoCompleterCounter[fieldId] = 1;
	else
		autoCompleterCounter[fieldId]++;
    var field = document.getElementById(fieldId) 
    if(field.value != "")
    	sendPlainRequest(action + ".html", fieldName + "=" + field.value, TYPE_RETURN_CONTENT, function(content) {updateAutoCompleter(autoCompleterCounter[fieldId], fieldId, content);} );
        //sendRequest(action + ".html", RETURN_CONTENT, SEND_STRING, fieldName + "=" + field.value, null, "updateAutoCompleter(" + autoCompleterCounter[fieldId] + ", \"" + fieldId + "\", {content});"); 
    else 
        updateAutoCompleter(autoCompleterCounter[fieldId], fieldId, null); 
} 

function updateAutoCompleter(counter, fieldId, content) 
{ 
    // um zu verhindern, dass von einen verlagsamten alten request neue ergebnisse ersetzt werden 
    //   wird eine überprüfung der letzten version durchgeführt. 
    if(!autoCompleterCounterSet[fieldId] || counter > autoCompleterCounterSet[fieldId]) 
    { 
        autoCompleterCounterSet[fieldId]=counter; 
        var outputParent = document.getElementById(fieldId + "_c"); 
        var outputElement = document.getElementById(fieldId + "_ci"); 
        if(!content) 
        { 
        	outputParent.style.display = "none"; 
            outputElement.innerHTML = ""; 
        } 
        else 
        { 
            content = content.trim(); 
            var splitted = content.split("\n");
            var newcontent = "";
            var count = 0;
            for(var i = 0; i < splitted.length; i++)
            {
            	if(splitted[i].trim() != "")
            	{
					newcontent += "<div style=\"height: " + lineHeight + "px; background-color:'#FFFFFF'; color: '#000000';\" id=\"" + fieldId + "_ci_" + count + "\">" + splitted[i].trim() + "</div>\n";
					count++;
            	}
            }
            outputParent.style.display = "block"; 
            outputElement.innerHTML = newcontent;
            autoCompleterSize[fieldId] = count;
            if(splitted.length > maxEntries)
            {
            	outputElement.style.overflowY = "scroll";
            	outputElement.style.height = (maxEntries*lineHeight) + "px";
            }
            else
            	outputElement.style.height = (splitted.length*lineHeight) + "px";
            autoCompleterSelect[fieldId] = -1;
			var div = document.getElementById(fieldId + "_ci");	
			div.scrollTop = 0;
        } 
    } 
}

function startNavigateAutoCompleter(e, action, fieldId, fieldName)
{
	var keyNavigate = getKeyCode(e);
	var tempNavigate;
	switch(keyNavigate)
	{
		case 13: updateAutoCompleterField(fieldId);
				break;
		case 33: tempNavigate = -maxEntries;
				break;
		case 34: tempNavigate = maxEntries;
				break;
		case 35: tempNavigate = autoCompleterSize[fieldId];
				break;
		case 36: tempNavigate = -autoCompleterSize[fieldId];
				break;
		case 38: tempNavigate = -1;
				break;
		case 40: tempNavigate = 1;
				break;
		default: loadAutoCompleter(action, fieldId, fieldName); 
				return;
	}
	if(currentNavigation != null)
		return;
	currentNavigation = tempNavigate;
	navigateAutoCompleter(false, fieldId);
	setTimeout("navigateAutoCompleter(true, \"" + fieldId + "\")", firstDelay);
}

function stopNavigateAutoCompleter(e, fieldId)
{
	var keyNavigate = getKeyCode(e);
	var tempNavigate;
	switch(keyNavigate)
	{
		case 33: tempNavigate = -maxEntries;
				break;
		case 34: tempNavigate = maxEntries;
				break;
		case 35: tempNavigate = autoCompleterSize[fieldId];
				break;
		case 36: tempNavigate = -autoCompleterSize[fieldId];
				break;
		case 38: tempNavigate = -1;
				break;
		case 40: tempNavigate = 1;
				break;
		default: return;
	}
	var textField = document.getElementById(fieldId);
	setCursorPosition(textField, textField.value.length);
	if(tempNavigate == currentNavigation)
		currentNavigation = null;
}

function navigateAutoCompleter(recursive, fieldId)
{
	//alert("FieldID: " + fieldId + "\nCurrentNavigation: " + currentNavigation + "\nautoCompleterSelect: " + autoCompleterSelect[fieldId]);
	if(currentNavigation == null)
		return;
		
	autoCompleterSelect[fieldId] += currentNavigation;
	if(autoCompleterSelect[fieldId] < 0 && autoCompleterSelect[fieldId]-currentNavigation >= 0)
	{
		autoCompleterSelect[fieldId] = -1;
		if(currentNavigation > 1 || currentNavigation < -1)
		{
			autoCompleterSelect[fieldId] = 0;
		}
		recursive = false;
	}
	else if(autoCompleterSelect[fieldId] < 0)
	{
		autoCompleterSelect[fieldId] = autoCompleterSize[fieldId]-1;
		if(currentNavigation > 1 || currentNavigation < -1)
		{
		}
		recursive = false;
	}	
	else if(autoCompleterSelect[fieldId] == autoCompleterSize[fieldId])
	{
		if(currentNavigation > 1 || currentNavigation < -1)
		{
			autoCompleterSelect[fieldId] = autoCompleterSize[fieldId]-1;
		}
		recursive = false;
	}
	else if(autoCompleterSelect[fieldId] > autoCompleterSize[fieldId])
	{
		autoCompleterSelect[fieldId] = -1;
		if(currentNavigation > 1 || currentNavigation < -1)
		{
			autoCompleterSelect[fieldId] = autoCompleterSize[fieldId]-1;
		}
		recursive = false;
	}

	//alert("FieldID: " + fieldId + "\nCurrentNavigation: " + currentNavigation + "\nautoCompleterSelect: " + autoCompleterSelect[fieldId]);
	showAutoCompleterSelect(fieldId);
	
	if(recursive)
		setTimeout("navigateAutoCompleter(true, \"" + fieldId + "\")", moreDelays);
}

function showAutoCompleterSelect(fieldId)
{
	var line;
	for(var i = 0; i < autoCompleterSize[fieldId]; i++)
	{
		line = document.getElementById(fieldId + "_ci_" + i);
		if(i == autoCompleterSelect[fieldId])
		{
			line.style.backgroundColor = "#000000";
			line.style.color = "#FFFFFF";
		}
		else
		{
			line.style.backgroundColor = "#FFFFFF";
			line.style.color = "#000000";
		}	
	}
	var div = document.getElementById(fieldId + "_ci");	
	if(div.scrollTop + maxEntries*lineHeight <= autoCompleterSelect[fieldId]*lineHeight)
		div.scrollTop = (autoCompleterSelect[fieldId]-maxEntries+1)*lineHeight;
	if(div.scrollTop > autoCompleterSelect[fieldId]*lineHeight)
		div.scrollTop = (autoCompleterSelect[fieldId]-1)*lineHeight;
}

function autoCompleterBlur(fieldId)
{
	//updateAutoCompleter(autoCompleterCounterSet[fieldId]+1, fieldId, null);
}

function updateAutoCompleterField(fieldId)
{
	var textField = document.getElementById(fieldId);
	var value = document.getElementById(fieldId + "_ci_" + autoCompleterSelect[fieldId]).innerHTML.trim();
	textField.value = value;	
}

// ENTER        = 13
// BILD-HOCH 	= 33
// BILD-RUNTER 	= 34
// ENDE 		= 35
// POS1 		= 36
// LINKS 		= 37
// HOCH 		= 38
// RECHTS 		= 39
// RUNTER 		= 40