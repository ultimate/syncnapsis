/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
//@requires("Styles")
//@requires("Events")
//@requires("Lang")
//@requires("Tabs")
//@requires("Select")
//@requires("Pinboard")
//@requires("application-base")
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
UI.constants.REG_USERNAME_ID = "reg_username";
UI.constants.REG_EMAIL_ID = "reg_email";
UI.constants.REG_PASSWORD_ID = "reg_password";
UI.constants.REG_PASSWORD2_ID = "reg_password2";
UI.constants.REG_MESSAGE_ID = "reg_message";
UI.constants.REG_ERROR_ID = "reg_error";
UI.constants.REG_BUTTON_ID = "reg_button";
UI.constants.REG_ACTION = "javascript: client.uiManager.doRegister();";
UI.constants.REG_VOID = "javascript: return false;";

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
UI.constants.USERINFO_BUTTON = "door";
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
UI.constants.FLAGS_CLASS = "flag";

UI.constants.MESSAGE_SHOW_CLASS = "show";
UI.constants.BUTTON_DISABLED_CLASS = "disabled";
UI.constants.MENU_HIDDEN_CLASS = "menu_hidden";

UI.constants.DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

UI.constants.PINBOARD_NEWS = "pinboard_news";
UI.constants.PINBOARD_CHAT = "pinboard_chat";
UI.constants.PINBOARD_EVENTS = "pinboard_events";

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
	this.log.select = function(uiManager)
	{
		return function(index)
		{
			if(!uiManager.logOpen)
				uiManager.showLog();
			this.select0(index);
		};
	}(this);
	// init the user info with a dummy tab selector (the "door")
	// dummy is used since we need at least one tab selector
	// TODO don't make door a tab-selector // just a simple button
	this.userInfo = new Tabs(null, TABS_HORIZONTAL, UI.constants.USERINFO_CONTENT, TABS_VERTICAL);// , UI.constants.NAV_WIDTH, UI.constants.USERINFO_HEIGHT);
	// overwrite the door's onSelect to perform the login
	this.door = document.getElementById(UI.constants.USERINFO_BUTTON).children[0]; // this is the 'a' tag
	this.door.onclick = function(uiManager)
	{
		return function()
		{
			if(uiManager.currentPlayer == null)
				uiManager.doLogin();
			else
				uiManager.doLogout();
		}
	}(this);

	this.localeChooser = new Select(UI.constants.LOCALE_CHOOSER_ID);
	this.localeChooser.onselect = function(oldValue, newValue)
	{
		console.log("changing value from " + oldValue + " -> " + newValue);
		if(newValue != oldValue)
			server.uiManager.selectLocale(newValue);
	};

	console.log("initializing Windows");

	this.window_register = new Styles.Window("register", "menu.register", "content_register");
	this.window_register.setSize(400, 193);
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
	
	console.log("creating pinboard(s)");
	this.pinboards = [];
	this.pinboards.push(new Pinboard(document.getElementById(UI.constants.PINBOARD_NEWS), "testboard", "blog", "frame", false, true));
	this.pinboards.push(new Pinboard(document.getElementById(UI.constants.PINBOARD_CHAT), "testboard", "chat", null, false, false));
	this.pinboards.push(new Pinboard(document.getElementById(UI.constants.PINBOARD_EVENTS), "testboard", "eventlog", null, false, true));

	console.log("showing UI");

	Events.fireEvent(window, Events.ONRESIZE);

	this.onLogout(); // just in case ;-)
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
		server.entityManager.load(player.user, Events.wrapEventHandler(this, this.onUserLoaded), true);
		// switch ui to logged-in menu
		this.userInfo.select(UI.constants.USERINFO_INDEX_LOGGEDIN);
		// switch menu to logged-in view
		document.getElementById(UI.constants.NAV_TABS).classList.remove(UI.constants.MENU_HIDDEN_CLASS);
		document.getElementById(UI.constants.NAV_CONTENT).classList.remove(UI.constants.MENU_HIDDEN_CLASS);
		// hide welcome
		this.hideWelcome();
	}
	else
	{
		this.showErrorMessage(document.getElementById(UI.constants.LOGIN_USERNAME_ID), null);
		this.showErrorMessage(document.getElementById(UI.constants.LOGIN_PASSWORD_ID), null);
	}
};

UIManager.prototype.onLogout = function(success)
{
	// clear entity cache
	server.entityManager.clearCache();
	// switch ui to logged-out menu
	this.userInfo.select(UI.constants.USERINFO_INDEX_LOGGEDOUT);
	// switch menu to logged-out view
	document.getElementById(UI.constants.NAV_TABS).classList.add(UI.constants.MENU_HIDDEN_CLASS);
	document.getElementById(UI.constants.NAV_CONTENT).classList.add(UI.constants.MENU_HIDDEN_CLASS);
	// "unload" user
	this.currentPlayer = null;
	this.onUserLoaded(null);
};

UIManager.prototype.onUserLoaded = function(user)
{
	if(user)
	{
		// set welcome username
		document.getElementById(UI.constants.LABEL_ID_PLAYERNAME).innerHTML = user.username;
		// change language to users selection
		this.localeChooser.selectByValue(user.locale);
	}
	else
	{
		// set welcome username
		document.getElementById(UI.constants.LABEL_ID_PLAYERNAME).innerHTML = "anonymous";
	}
	// set user for pinboards
	for(var i = 0; i < this.pinboards.length; i++)
	{
		this.pinboards[i].setUser(user);
	}
};

UIManager.prototype.enableRegisterButton = function(enable)
{
	if(enable)
	{
		document.getElementById(UI.constants.REG_BUTTON_ID).children[0].href = UI.constants.REG_ACTION;
		document.getElementById(UI.constants.REG_BUTTON_ID).classList.remove(UI.constants.BUTTON_DISABLED_CLASS);
	}
	else
	{
		document.getElementById(UI.constants.REG_BUTTON_ID).children[0].href = UI.constants.REG_VOID;
		document.getElementById(UI.constants.REG_BUTTON_ID).classList.add(UI.constants.BUTTON_DISABLED_CLASS);
	}
};

UIManager.prototype.onRegister = function(player, username, password)
{
	if(player != null && server.entityManager.getType(player) == Types.Player)
	{
		// auto login
		setTimeout(function(uiManager) { return function() {
			uiManager.hideRegister();
			server.playerManager.login(username, password);
		}; } (this), 5000);
		
		// enable register button
		setTimeout(function(uiManager) {
			return function() {
				uiManager.enableRegisterButton(true);
			};
		} (this), 10000);

		// TODO show success message
		this.showErrorMessage(null, document.getElementById(UI.constants.REG_MESSAGE_ID), "welcome.title");
	}
	else if(player != null && player.exceptionClass != null)
	{
		console.log("error: " + player.message);
		// show error message
		this.showErrorMessage(null, document.getElementById(UI.constants.REG_ERROR_ID), player.message);
			
			
		// highlight erronous fields
		if(player.message == "error.username_exists" || player.message == "error.invalid_username")
		{
			this.showErrorMessage(document.getElementById(UI.constants.REG_USERNAME_ID), null);
		}
		if(player.message == "error.email_exists" || player.message == "error.invalid_email")
		{
			this.showErrorMessage(document.getElementById(UI.constants.REG_EMAIL_ID), null);
		}
		if(player.message == "error.password_mismatch")
		{
			this.showErrorMessage(document.getElementById(UI.constants.REG_PASSWORD_ID), null);
			this.showErrorMessage(document.getElementById(UI.constants.REG_PASSWORD2_ID), null);
		}
		
		// enable register button
		this.enableRegisterButton(true);
	}
};

UIManager.prototype.hideOverlay = function()
{
	var overlay = document.getElementById(UI.constants.OVERLAY_ID);
	overlay.className += " invisible";
	setTimeout(function()
	{
		overlay.className += " hidden";
	}, 3000);
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
		if(typeof (lang.EnumLocale[i]) == Reflections.type.FUNCTION)
			continue;
		option = {
			value : i,
			image : UI.constants.IMAGE_PATH + UI.constants.FLAGS_PATH + i + UI.constants.IMAGE_TYPE,
			imageClass : UI.constants.FLAGS_CLASS,
			title : lang.EnumLocale[i]
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
	// force reload oof the user (since the locale has been saved)
	if(this.currentPlayer)
		server.entityManager.load(this.currentPlayer.user, null, true);
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
		// remove all current children
		while(elements[i].firstChild)
			elements[i].removeChild(elements[i].firstChild);
		elements[i].appendChild(document.createTextNode(this.getString(elements[i].getAttribute(UI.constants.LOCALE_KEY_ATTRIBUTE))));
	}
	// buttons
	elements = document.getElementsByTagName(UI.constants.LOCALE_BUTTON_TAGNAME);
	for( var i = 0; i < elements.length; i++)
	{
		if(elements[i].getAttribute("type") == "button")
			elements[i].value = this.getString(elements[i].getAttribute(UI.constants.LOCALE_KEY_ATTRIBUTE));
	}
	// static pages
	document.getElementById(UI.constants.STATIC_FRAME_ID).contentWindow.location.reload(true)
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

	var error = false
	if(username == "" || username == null)
	{
		this.showErrorMessage(document.getElementById(UI.constants.LOGIN_USERNAME_ID), null);
		error = true;
	}
	if(password == "" || password == null)
	{
		this.showErrorMessage(document.getElementById(UI.constants.LOGIN_PASSWORD_ID), null);
		error = true;
	}
	if(error)
		return;
	console.log("login as: " + username + ":" + password);
	server.playerManager.login(username, password);
};

UIManager.prototype.doRegister = function()
{
	// clear errors
	document.getElementById(UI.constants.REG_ERROR_ID).innerHTML = "";
	document.getElementById(UI.constants.REG_MESSAGE_ID).innerHTML = "";

	// get input
	var username = document.getElementById(UI.constants.REG_USERNAME_ID).value;
	var email = document.getElementById(UI.constants.REG_EMAIL_ID).value;
	var password = document.getElementById(UI.constants.REG_PASSWORD_ID).value;
	var password2 = document.getElementById(UI.constants.REG_PASSWORD2_ID).value;
	
	// validate input
	var error = false
	if(!error && (username == "" || username == null))
	{
		this.showErrorMessage(document.getElementById(UI.constants.REG_USERNAME_ID), document.getElementById(UI.constants.REG_ERROR_ID), "error.invalid_username");
		error = true;
	}
	if(!error && (email == "" || email == null))
	{
		this.showErrorMessage(document.getElementById(UI.constants.REG_EMAIL_ID), document.getElementById(UI.constants.REG_ERROR_ID), "error.invalid_email");
		error = true;
	}
	if(!error && (password == "" || password == null ||  password != password2))
	{
		this.showErrorMessage(null, document.getElementById(UI.constants.REG_ERROR_ID), "error.password_mismatch");
		this.showErrorMessage(document.getElementById(UI.constants.REG_PASSWORD_ID), null);
		this.showErrorMessage(document.getElementById(UI.constants.REG_PASSWORD2_ID), null);
		error = true;
	}
	if(error)
		return;

	// disable register button
	this.enableRegisterButton(false);

	// we do not use the default callback, since we want to keep the password for auto-login
	var callback = function(username, password)
	{
		return function(player)
		{
			client.uiManager.onRegister(player, username, password);
		};
	}(username, password);

	server.playerManager.register(username, email, password, password2, callback);
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

UIManager.prototype.showStatic = function(key)
{
	this.window_static.setTitleKey(key);
	this.window_static.setVisible(true);
};

UIManager.prototype.hideStatic = function()
{
	this.window_static.setVisible(false);
};

UIManager.prototype.showErrorMessage = function(inputfield, messagefield, message)
{
	if(inputfield != null)
	{
		var cls = inputfield.className;
		inputfield.className = cls + " error";
		setTimeout(function()
		{
			inputfield.className = cls;
		}, 3000);
	}
	if(messagefield != null)
	{
		if(message)
			messagefield.innerHTML = this.getString(message);
		
		messagefield.classList.add(UI.constants.MESSAGE_SHOW_CLASS);
		setTimeout(function()
		{
			messagefield.classList.remove(UI.constants.MESSAGE_SHOW_CLASS);
		}, 3000);
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
				return function()
				{
					uiManager.showStatic(link.getElementsByTagName(UI.constants.LOCALE_LABEL_TAGNAME)[0].getAttribute(UI.constants.LOCALE_KEY_ATTRIBUTE));
				};
			})(links[i], this);
		}
	}
};

MessageManager = function()
{
};

//public void updatePinboard(Long pinboardId, List<PinboardMessage> messages);
MessageManager.prototype.updatePinboard = function(pinboardId, messages)
{
	console.log("updating pinboard #" + pinboardId + " with " + messages.length + " messages");

	for(var i = 0; i < client.uiManager.pinboards.length; i++)
	{
		if(client.uiManager.pinboards[i].pinboard.id == pinboardId)
		{
			console.log("updating pinboard instance with index #" + i);
			client.uiManager.pinboards[i].update(messages);
		}
	}
};