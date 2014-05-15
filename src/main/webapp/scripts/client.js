/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
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
// @requires("Styles")
// @requires("Events")
// @requires("Lang")
// @requires("Tabs")
// @requires("Select")
// @requires("application-base")
var UI = {};

UI.constants = {};
UI.constants.AD_WIDTH = 170;
UI.constants.AD_BORDER = 2;
UI.constants.BAR_HEIGHT = 24;
UI.constants.BAR_OUTER_WIDTH = 300;

UI.constants.KEY_SWOW_WELCOME = "showWelcomeOnLoad";

UI.constants.LABEL_ID_PLAYERNAME = "userbar_playername";
UI.constants.OVERLAY_ID = "overlay";
UI.constants.STATIC_FRAME_ID = "static_frame";
UI.constants.SHOW_WELCOME_ID = "showWelcomeOnLoad";
UI.constants.WELCOME_TOOGLE1_ID = "welcome_toggle1";
UI.constants.WELCOME_TOOGLE2_ID = "welcome_toggle2";

UI.constants.LOCALE_CHOOSER_ID = "locale_chooser";
UI.constants.LOCALE_LABEL_TAGNAME = "label";
UI.constants.LOCALE_BUTTON_TAGNAME = "input";
UI.constants.LOCALE_LINK_TAGNAME = "a";
UI.constants.LOCALE_KEY_ATTRIBUTE = "key";
UI.constants.LOCALE_KEY_TOOLTIP_SUFFIX = "_tooltip";
UI.constants.LOCALE_STRING_VARIABLE = "lang";
UI.constants.LOCALE_PREFIX = "EnumLocale.";

UI.constants.NAV_TABS = "nav";
UI.constants.NAV_CONTENT = "menu_content";
UI.constants.LOG_TABS = "log";
UI.constants.LOG_CONTENT = "log_content";
UI.constants.LOG_FRAME = "bar_bottom";
UI.constants.USERINFO_TABS = "door"; // just use this as a dummy selector we will overwrite the select for
UI.constants.USERINFO_CONTENT = "userbar";
UI.constants.USERINFO_INDEX_LOGGEDOUT = 0;
UI.constants.USERINFO_INDEX_LOGGEDIN = 1;

UI.constants.LOGIN_USERNAME_ID = "login_username";
UI.constants.LOGIN_PASSWORD_ID = "login_password";

UI.constants.NAV_WIDTH = 300;
UI.constants.USERINFO_HEIGHT = 40;

UI.constants.IMAGE_PATH = "images/";
UI.constants.IMAGE_TYPE = ".png";
UI.constants.FLAGS_PATH = "flags/";
UI.constants.FLAGS_CLASS = "flag"

UIManager = function()
{
	this.currentPlayer = null;
	this.logCls = document.getElementById(UI.constants.LOG_FRAME).className;
	this.logOpen = false;
	
	console.log("initializing UIManager");
	console.log("initializing Tabs");

	// init the nav ("normally")
	this.nav = new Tabs(UI.constants.NAV_TABS, TABS_HORIZONTAL, UI.constants.NAV_CONTENT, TABS_VERTICAL);
	// init the log with overwritten select which opens the log window on click
	this.log = new Tabs(UI.constants.LOG_TABS, TABS_HORIZONTAL, UI.constants.LOG_CONTENT, TABS_HORIZONTAL);
	// overwrite the log-select to open the log on select
	this.log.select0 = this.log.select;
	this.log.select = function(uiManager) {
		return function(index) {
			if(!uiManager.logOpen)
				uiManager.showLog();
			this.select0(index);
		};
	}(this);
	// init the user info with a dummy tab selector (the "door")
	// dummy is used since we need at least one tab selector
	this.userInfo = new Tabs(UI.constants.USERINFO_TABS, TABS_HORIZONTAL, UI.constants.USERINFO_CONTENT, TABS_VERTICAL);//, UI.constants.NAV_WIDTH, UI.constants.USERINFO_HEIGHT);
	// overwrite the door's onSelect to perform the login
	this.door = document.getElementById(UI.constants.USERINFO_TABS).children[0]; // this is the 'a' tag
	this.door.onclick = function(uiManager) {
		return function() {
			if(uiManager.currentPlayer == null)
				uiManager.doLogin();
			else
				uiManager.doLogout();
		}
	} (this);
	
	this.localeChooser = new Select(UI.constants.LOCALE_CHOOSER_ID);
	this.localeChooser.onselect = function(oldValue, newValue) {
		console.log("changing value from " + oldValue + " -> " + newValue);
		if(newValue != oldValue)
			server.uiManager.selectLocale(newValue);
	};
	
	console.log("initializing Windows");
	
	this.window_register = new Styles.Window("register", "menu.register", "content_register");
	this.window_register.setSize(400, 300);
	this.window_register.center();
	this.window_register.setMovable(false);
	
	this.window_welcome = new Styles.Window("welcome", "welcome.title", "content_welcome");
	this.window_welcome.setSize(500, 500);
	this.window_welcome.center();
	this.window_welcome.setMovable(false);
	
	this.window_static = new Styles.Window("static", "", "content_static");
	this.window_static.setSize(600, 400);
	this.window_static.center();
	this.window_static.setMovable(false);
	
	this.welcome_toggle1 = new Styles.HoverButton(UI.constants.WELCOME_TOOGLE1_ID, 0, 1);
	this.welcome_toggle2 = new Styles.HoverButton(UI.constants.WELCOME_TOOGLE2_ID, 0, 1);

	console.log("showing UI");

	Events.fireEvent(window, Events.ONRESIZE);

	this.onLogout();	// just in case ;-)
	this.updateLabels();
	this.populateLocaleChooser();
	this.updateLinks();
	
	this.updateShowWelcomeOnLoad(false);
	if(this.showWelcomeOnLoad)
		this.showWelcome();
	
	this.hideOverlay();
};

UIManager.prototype.onLogin = function(player)
{
	if(player != null)
	{		
		// clear password field
		document.getElementById(UI.constants.LOGIN_PASSWORD_ID).value = "";
		// store the current player
		this.currentPlayer = player;
		// set the player name in the top bar (after loading the user)
		server.entityManager.load(player.user, Events.wrapEventHandler(this, this.onUserLoaded));
		// change language to users selection
		server.uiManager.selectLocale(player.user.locale);
		// switch ui to logged-in menu
		this.userInfo.select(UI.constants.USERINFO_INDEX_LOGGEDIN);
	}
	else
	{
		this.showErrorMessage(document.getElementById(UI.constants.LOGIN_USERNAME_ID), null);
		this.showErrorMessage(document.getElementById(UI.constants.LOGIN_PASSWORD_ID), null);
	}
};

UIManager.prototype.onLogout = function(success)
{	
	// switch ui to logged-out menu
	this.userInfo.select(UI.constants.USERINFO_INDEX_LOGGEDOUT);
	// "unload" user
	this.currentPlayer = null;
	this.onUserLoaded({username: "anonymous"});
};

UIManager.prototype.onUserLoaded = function(user)
{
	console.log("user loaded: " + user.username);
	document.getElementById(UI.constants.LABEL_ID_PLAYERNAME).innerHTML = user.username;
	if(user && user.locale)
		this.localeChooser.selectByValue(user.locale);
//	server.uiManager.selectLocale(user.locale);
};

UIManager.prototype.hideOverlay = function()
{
	var overlay = document.getElementById(UI.constants.OVERLAY_ID);
	overlay.className += " invisible";
	setTimeout(function() {overlay.className += " hidden";}, 3000);
};

UIManager.prototype.populateLocaleChooser = function()
{
	// clear children
	this.localeChooser.options.length = 0;
	// add options
	var option, img, text;
	var selected = 0;
	var index = 0;
	for( var i in lang.EnumLocale)
	{
		if(typeof(lang.EnumLocale[i]) == Reflections.type.FUNCTION)
			continue;
		option = {
				value: i,
				image: UI.constants.IMAGE_PATH + UI.constants.FLAGS_PATH + i + UI.constants.IMAGE_TYPE,
				imageClass: UI.constants.FLAGS_CLASS,
				title: lang.EnumLocale[i]
		};
		if(("EnumLocale." + i) == lang.current)
			selected = index; 
		this.localeChooser.options[this.localeChooser.options.length] = option;
		index++;
	}
	this.localeChooser.update();
	this.localeChooser.selectByValue(lang.current.substring(11), true);
};

UIManager.prototype.getString = function(key)
{
	if(key == null || key == "")
		return key; // otherwise the following eval would fail!
	return eval(UI.constants.LOCALE_STRING_VARIABLE + "." + key);
};

UIManager.prototype.reloadLocale = function()
{
	console.log("reloading locale");
	// just reload the corresponding lang-script
	// DependencyManager will call the update-function after reload
	DependencyManager.reloadScript("Lang", Events.wrapEventHandler(this, this.updateLabels));
};

UIManager.prototype.updateLabels = function(parent)
{
	if(parent == undefined)
		parent = document;
	else if(parent.getElementsByTagName == undefined)
		parent = document;
	var elements;
	// labels
	elements = parent.getElementsByTagName(UI.constants.LOCALE_LABEL_TAGNAME);
	for( var i = 0; i < elements.length; i++)
	{
		elements[i].innerHTML = this.getString(elements[i].getAttribute(UI.constants.LOCALE_KEY_ATTRIBUTE));
	}
	// buttons
	elements = document.getElementsByTagName(UI.constants.LOCALE_BUTTON_TAGNAME);
	for( var i = 0; i < elements.length; i++)
	{
		if(elements[i].getAttribute("type") == "button")
			elements[i].value = this.getString(elements[i].getAttribute(UI.constants.LOCALE_KEY_ATTRIBUTE));
	}
};

UIManager.prototype.showLog = function()
{
	if(this.logOpen)
		return;
	document.getElementById(UI.constants.LOG_FRAME).className += " open";
	this.logOpen = true;
};

UIManager.prototype.hideLog = function()
{
	if(!this.logOpen)
		return;
	document.getElementById(UI.constants.LOG_FRAME).className = this.logCls;
	this.logOpen = false;
};

UIManager.prototype.toggleLog = function()
{
	if(this.logOpen)
		this.hideLog();
	else
		this.showLog();
};

UIManager.prototype.doLogin = function()
{
	var username = document.getElementById(UI.constants.LOGIN_USERNAME_ID).value;
	var password = document.getElementById(UI.constants.LOGIN_PASSWORD_ID).value;
	if(username == "" || username == null || password == "" || password == null)
	{
		if(username == "" || username == null)
			this.showErrorMessage(document.getElementById(UI.constants.LOGIN_USERNAME_ID), null);
		if(password == "" || password == null)
			this.showErrorMessage(document.getElementById(UI.constants.LOGIN_PASSWORD_ID), null);
		return;
	}
	console.log("login as: " + username + ":" + password);
	server.playerManager.login(username, password);
};

UIManager.prototype.doLogout = function()
{
	server.playerManager.logout();
};

UIManager.prototype.showRegister = function()
{
	this.window_register.setVisible(true);
};

UIManager.prototype.hideRegister = function()
{
	this.window_register.setVisible(false);
};

UIManager.prototype.showWelcome = function()
{
	this.window_welcome.setVisible(true);
};

UIManager.prototype.hideWelcome = function()
{
	this.window_welcome.setVisible(false);
};

UIManager.prototype.updateShowWelcomeOnLoad = function(toggle)
{
	// use eval since localStorage returns string
	var val = (localStorage.getItem(UI.constants.KEY_SWOW_WELCOME) != "false");
	// toggle
	if(toggle)
		val = !val;
	localStorage.setItem(UI.constants.KEY_SWOW_WELCOME, val);
	
	this.showWelcomeOnLoad = val;
	document.getElementById(UI.constants.SHOW_WELCOME_ID).checked = val;
	
	return true;
};

UIManager.prototype.doRegister = function()
{
//	var username = document.getElementById("register_username").value;
//	var password = document.getElementById("register_password").value;
//	// TODO password confirm
//	// TODO email
//	if(username == "" || password == "")
//	{
//		if(username == "" || username == null)
//			this.showErrorMessage(document.getElementyById("register_username"), document.getElementById("message_register_invalid_username"));
//		if(password == "" || password == null)
//			this.showErrorMessage(document.getElementyById("register_password"), document.getElementById("message_register_invalid_password"));
//		return;
//	}
//	server.playerManager.register(............);
};

UIManager.prototype.showStatic = function(key)
{
	this.window_static.setTitleKey(key);
	this.window_static.setVisible(true);
};

UIManager.prototype.hideStatic = function()
{
	this.window_static.setVisible(false);
};

UIManager.prototype.showErrorMessage = function(textfield, messagefield)
{
	if(textfield != null)
	{
//		var bg = textfield.style.background;
//		textfield.style.background = UI.constants.TF_ERROR_BACKGROUND;
//		setTimeout(function() {textfield.style.background = bg;}, 3000);
		var cls = textfield.className;
		textfield.className = cls + " error";
		setTimeout(function() {textfield.className = cls;}, 3000);
	}
	if(messagefield != null)
	{
		var disp = messagefield.style.display;
		messagefield.style.display = "none";
		setTimeout(function() {messagefield.style.display = disp;}, 3000);
	}
};

UIManager.prototype.updateLinks = function()
{
	var links = document.getElementsByTagName("a");
	for( var i = 0; i < links.length; i++)
	{
		if(links[i].target == "_blank")
		{
			links[i].target = UI.constants.STATIC_FRAME_ID;
			links[i].onclick = (function(link, uiManager) 
			{
				return function() {
					uiManager.showStatic(link.getElementsByTagName(UI.constants.LOCALE_LABEL_TAGNAME)[0].getAttribute(UI.constants.LOCALE_KEY_ATTRIBUTE));
				};
			})(links[i], this);
		}
	}
};

ServerUIManager = function()
{
};

ServerUIManager.prototype.selectLocale = function(selection)
{
	// stub for server-entity
};

MessageManager = function()
{
};

MessageManager.prototype.updatePinboard = function(pinboardId, messages)
{
	console.log("updating pinboard #" + pinboardId);
	for(var i = 0; i < messages.length; i++)
	{
		console.log(messages[i]);
	}
};

ServerMessageManager = function()
{
};

ServerMessageManager.prototype.postPinboardMessage = function(pinboardId, title, message)
{
	// stub for server-entity
};

ServerMessageManager.prototype.requestPinboardUpdate = function(pinboardId, messageCount)
{
	// stub for server-entity
};