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
var runningInit = false;
var InitInterval;
var browser;

function BrowserChooserInit(runAfterInit) {
	if(!runningInit) {
		runningInit = true;					
		addScriptToLoad('utility/Arrays.js', //filename
						'all', // browser
						'utility/Arrays.js', // setReady
						'', // scripts needed
						false);													
		addScriptToLoad('utility/HtmlElements.js', //filename
						'all', // browser
						'utility/HtmlElements.js', // setReady
						'', // scripts needed
						false);													
		addScriptToLoad('utility/Strings.js', //filename
						'all', // browser
						'utility/Strings.js', // setReady
						'', // scripts needed
						false);													
		addScriptToLoad('utility/Math.js', //filename
						'all', // browser
						'utility/Math.js', // setReady
						'', // scripts needed
						false);													
		addScriptToLoad('gui/settings.js',
						'all',
						'gui/settings.js',
						'utility/Arrays.js,utility/HtmlElements.js,utility/Strings.js',
						false);
		addScriptToLoad('gui/BrowserFunctionsIE7.js',
						'IE7',
						'gui/BrowserFunctionsIE7.js,gui/BrowserFunctionsFF.js,gui/BrowserFunctionsIE8.js',
						'gui/settings.js',
						false);
		addScriptToLoad('gui/BrowserFunctionsIE8.js',
						'IE8',
						'gui/BrowserFunctionsIE7.js,gui/BrowserFunctionsFF.js,gui/BrowserFunctionsIE8.js',
						'gui/settings.js',
						'',
						false);		
		addScriptToLoad('gui/BrowserFunctionsFF.js',
						'FF',
						'gui/BrowserFunctionsIE7.js,gui/BrowserFunctionsFF.js,gui/BrowserFunctionsIE8.js',
						'gui/settings.js',
						false);
		addScriptToLoad('gui/windowFunctions.js',
						'all',
						'gui/windowFunctions.js',
						'gui/BrowserFunctionsFF.js',
						false);
		addScriptToLoad('gui/quicklaunch.js',
						'all',
						'gui/quicklaunch.js',
						'gui/BrowserFunctionsFF.js',
						false);
		addScriptToLoad('gui/menu.js',
						'all',
						'gui/menu.js',
						'gui/BrowserFunctionsFF.js',
						false);
		addScriptToLoad('gui/showMain.js',
						'all',
						'gui/showMain.js',
						'gui/BrowserFunctionsFF.js,gui/quicklaunch.js,gui/menu.js,gui/jstemplates/menu.js,gui/jstemplates/backgroundframe.js',
						false);
		addScriptToLoad('request/cookie.js',
						'all',
						'request/cookie.js',
						'',
						false);
		addScriptToLoad('request/autocompleter.js',
						'all',
						'request/autocompleter.js',
						'gui/BrowserFunctionsFF.js',
						false);
		addScriptToLoad('gui/dragdrop/dragdrop.js',
						'all',
						'gui/dragdrop/dragdrop.js',
						'gui/windowFunctions.js',
						false);
		addScriptToLoad('gui/dragdrop/layout/vertical.js',
						'all',
						'gui/dragdrop/layout/vertical.js',
						'gui/windowFunctions.js',
						false);
		addScriptToLoad('gui/dragdrop/layout/horizontal.js',
						'all',
						'gui/dragdrop/layout/horizontal.js',
						'gui/windowFunctions.js',
						false);
		addScriptToLoad('gui/jstemplates/menu.js',
						'all',
						'gui/jstemplates/menu.js',
						'gui/windowFunctions.js,gui/dragdrop/layout/horizontal.js,gui/dragdrop/layout/vertical.js',
						false);
		addScriptToLoad('debug.js',
						'all',
						'debug.js',
						'',
						false);
		addScriptToLoad('request/actions.js',
						'all',
						'request/actions.js',
						'',
						false);
		addScriptToLoad('actions.html',
						'all',
						'actions.html',
						'request/actions.js',
						true);
		addScriptToLoad('gui/jstemplates/window.js',
						'all',
						'gui/jstemplates/window.js',
						'gui/BrowserFunctionsFF.js',
						false);
		addScriptToLoad('gui/jstemplates/backgroundframe.js',
						'all',
						'gui/jstemplates/backgroundframe.js',
						'gui/BrowserFunctionsFF.js',
						false);
		addScriptToLoad('graphics/listeners/ListenerArea.js',
						'all',
						'graphics/listeners/ListenerArea.js',
						'utility/Math.js',
						false);
		addScriptToLoad('graphics/listeners/ViewportAreaListener.js',
						'all',
						'graphics/listeners/ViewportAreaListener.js',
						'utility/Math.js,graphics/listeners/ListenerArea.js',
						false);
		addScriptToLoad('graphics/listeners/ViewportListener.js',
						'all',
						'graphics/listeners/ViewportListener.js',
						'utility/Math.js',
						false);
		addScriptToLoad('graphics/drawables/EventPoint.js',
						'all',
						'graphics/drawables/EventPoint.js',
						'utility/Math.js,graphics/listeners/ViewportAreaListener.js',
						false);
		addScriptToLoad('graphics/drawables/Path.js',
						'all',
						'graphics/drawables/Path.js',
						'utility/Math.js',
						false);
		addScriptToLoad('graphics/drawables/Polygon.js',
						'all',
						'graphics/drawables/Polygon.js',
						'utility/Math.js',
						false);
		addScriptToLoad('graphics/Camera.js',
						'all',
						'graphics/Camera.js',
						'utility/Math.js',
						false);							
		addScriptToLoad('graphics/Viewport.js',
						'all',
						'graphics/Viewport.js',
						'utility/Math.js,graphics/Camera.js,graphics/listeners/ViewportListener.js',
						false);
		addScriptToLoad('graphics/Displayer.js',
						'all',
						'graphics/Displayer.js',
						'utility/Math.js,graphics/Viewport.js',
						false);
		
		if (navigator.appName == "Microsoft Internet Explorer") {
			if(typeof Element == "undefined")
				browser = 'IE7';
			else 
				browser = 'IE8';
		}
		else {
			browser = 'FF';
		}
		
		InitInterval = window.setInterval("BrowserChooserInit("+runAfterInit+")", 100);
		newDiv = document.createElement('div');
		newDiv.id = 'loadingScreen';
		newDiv.top = '0px';
		newDiv.left = '0px';
		newDiv.bottom = '0px';
		newDiv.right = '0px';
		newDiv.backgroundColor = '#777777';
		newDiv.valign = 'middle';
		newDiv.textalign = 'center';
		newDiv.innerHTML = 'Loading';
		document.getElementsByTagName('body')[0].appendChild(newDiv);
	}
	for(var i in scriptsToLoad) {
		if(!scriptIsReady[i] && !scriptIsLoading[i] && (scriptBrowser[i] == 'all' || scriptBrowser[i] == browser )) {
			var allReady = true;
			var s = scriptsNeeded[i].split(",");
			if(s[0] != "") {
				for(var j=0;j<s.length; j++) {
					allReady = allReady && scriptIsReady[s[j]];
				}
			}
			if(allReady) {
				scriptIsLoading[i] = true;
				loadScript(scriptsToLoad[i], scriptExternal[i]);
			}
		}
	}
	if(BrowserChooserReady())  {
		window.clearInterval(InitInterval);
		document.getElementsByTagName('body')[0].removeChild(document.getElementById('loadingScreen'));
		if(runAfterInit)
			runAfterInit.call(null);
	}
	
}

function BrowserChooserReady() {
	for(var i in scriptIsReady) {
		if(scriptIsReady[i] == false) return false;
	}
	return true;
}