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
//UI.constants.REG_ACTION = "javascript: client.uiManager.doRegister();";
//UI.constants.REG_VOID = "javascript: return false;";
UI.constants.FORGOT_USERNAME_ID = "forgot_username";
UI.constants.FORGOT_EMAIL_ID = "forgot_email";
UI.constants.FORGOT_MESSAGE_ID = "forgot_message";
UI.constants.FORGOT_ERROR_ID = "forgot_error";
UI.constants.FORGOT_BUTTON_ID = "forgot_button";
//UI.constants.FORGOT_ACTION = "javascript: client.uiManager.forgotPassword();";
//UI.constants.FORGOT_VOID = "javascript: return false;";
UI.constants.PROFILE_USERNAME_ID = "profile_username"; 
UI.constants.PROFILE_BIRTHDAY_ID = "profile_birthday"; 
UI.constants.PROFILE_GENDER_ID = "profile_gender"; 
UI.constants.PROFILE_DATEFORMAT_ID = "profile_dateFormat"; 
UI.constants.PROFILE_TIMEZONEID_REGION_ID = "profile_timeZoneID_region"; 
UI.constants.PROFILE_TIMEZONEID_ID_ID = "profile_timeZoneID_id"; 
UI.constants.PROFILE_REGISTRATIONDATE_ID = "profile_registrationDate"; 
UI.constants.PROFILE_ACCOUNTSTATUS_ID = "profile_accountStatus"; 
UI.constants.PROFILE_ACCOUNTSTATUSEXPIREDATE_ID = "profile_accountStatusExpireDate"; 
UI.constants.PROFILE_MESSAGE_ID = "profile_message"; 
UI.constants.PROFILE_ERROR_ID = "profile_error"; 
UI.constants.PROFILE_BUTTON_ID = "profile_button"; 
UI.constants.PROFILE_EMAIL_ID = "profile_email"; 
UI.constants.PROFILE_EMAIL_MESSAGE_ID = "email_message"; 
UI.constants.PROFILE_EMAIL_ERROR_ID = "email_error"; 
UI.constants.PROFILE_EMAIL_BUTTON_ID = "email_button"; 
UI.constants.PROFILE_PW_OLD_ID = "profile_password_old"; 
UI.constants.PROFILE_PW_NEW_ID = "profile_password_new"; 
UI.constants.PROFILE_PW_CONFIRM_ID = "profile_password_confirm"; 
UI.constants.PROFILE_PW_MESSAGE_ID = "password_message"; 
UI.constants.PROFILE_PW_ERROR_ID = "password_error"; 
UI.constants.PROFILE_PW_BUTTON_ID = "password_button"; 

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
UI.constants.LOG_TOGGLE_ARROW = "log_toggle_arrow";
UI.constants.TOP_FRAME = "bar_top_sub";
UI.constants.TOP_TOGGLE_ARROW = "top_toggle_arrow";
UI.constants.USERINFO_BUTTON = "door";
UI.constants.USERINFO_CONTENT = "userbar";
UI.constants.USERINFO_INDEX_LOGGEDOUT = 0;
UI.constants.USERINFO_INDEX_LOGGEDIN = 1;
UI.constants.MATCH_SELECT_ID = "match_select";
UI.constants.MATCH_FILTER_PREFIX_ID = "match_filter_prefix";
UI.constants.MATCH_FILTER_PARTICIPANTS_ID = "match_filter_participants";
UI.constants.MATCH_FILTER_GALAXY_SELECT_ID = "match_filter_galaxy_select";
UI.constants.MATCH_FILTER_STATE_SELECT_ID = "match_filter_state_select";

UI.constants.LOGIN_USERNAME_ID = "login_username";
UI.constants.LOGIN_PASSWORD_ID = "login_password";

UI.constants.NAV_WIDTH = 300;
UI.constants.USERINFO_HEIGHT = 40;

UI.constants.IMAGE_PATH = "images/";
UI.constants.IMAGE_TYPE = ".png";
UI.constants.FLAGS_PATH = "flags/";
UI.constants.FLAGS_CLASS = "flag";

UI.constants.NO_ACTION = function(){};

UI.constants.MESSAGE_SHOW_CLASS = "show";
UI.constants.BUTTON_DISABLED_CLASS = "disabled";
UI.constants.BUTTON_DISABLED_ACTION = "javascript: UI.constants.NO_ACTION();";
UI.constants.ERROR_CLASS = "error";
UI.constants.MENU_HIDDEN_CLASS = "menu_hidden";
UI.constants.HIDDEN_CLASS = "hidden";

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

	// initialize locale chooser
	this.localeChooser = new Select(UI.constants.LOCALE_CHOOSER_ID);
	// overwrite onselect
	// need to do this after populate, since populate overwrites onselect, too
	this.localeChooser.onselect = function(oldValue, newValue)
	{
		console.log("changing value from " + oldValue + " -> " + newValue);
		if(newValue != oldValue)
			server.uiManager.selectLocale(newValue);
	};
	
	// initialize form selects
	this.genderSelect = new Select(UI.constants.PROFILE_GENDER_ID);
	this.populateEnumSelect(this.genderSelect, lang.EnumGender, false);
	
	// initialize match select
	this.matchSelect = new Select(UI.constants.MATCH_SELECT_ID);
	// overwrite renderer
	this.matchSelect.getOptionContent = function(option) {
		var match = option.value;
		var content = new Array();
		// TODO image?
		content.push("<span class='col_1'>");
		content.push(match.id);
		content.push("</span><span class='col_2'>");
		content.push(match.title);
		content.push("</span><span class='col_3'>");
		content.push("(<label key='EnumMatchState." + match.state + "'></label>)"); // TODO localize
		content.push("</span>");
		return content.join("");
	};
	server.matchManager.getAll(); // load matches
	
	// initialize galaxy select for match filter
	this.matchFilterGalaxySelect = new Select(UI.constants.MATCH_FILTER_GALAXY_SELECT_ID);
	// overwrite renderer
	this.matchFilterGalaxySelect.getOptionContent = function(option) {
		var galaxy = option.value;
		if(galaxy == null)
			return "";
		var content = new Array();
		content.push("<span class='col_1'>");
		content.push(galaxy.id);
		content.push("</span><span class='col_2'>");
		content.push(galaxy.name);
		content.push("</span><span class='col_3'>");
		content.push("[" + galaxy.size.x + "x" + galaxy.size.y + "x" + galaxy.size.z + "]");
		content.push("</span>");
		return content.join("");
	};
	server.galaxyManager.getAll(); // load galaxies
	
	// initialize state select for match filter
	this.matchFilterStateSelect = new Select(UI.constants.MATCH_FILTER_STATE_SELECT_ID);
	// populate with enum
	this.populateEnumSelect(this.matchFilterStateSelect, lang.EnumMatchState, true);

	console.log("initializing Windows");

	this.window_register = new Styles.Window("register", "menu.register", "content_register");
	this.window_register.setSize(400, 193);
	this.window_register.center();
	this.window_register.setMovable(false);

	this.window_forgot = new Styles.Window("forgot", "menu.reset_password", "content_forgot_password");
	this.window_forgot.setSize(400, 150);
	this.window_forgot.center();
	this.window_forgot.setMovable(false);

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
	this.pinboards.push(new Pinboard(document.getElementById(UI.constants.PINBOARD_NEWS), "testboard", "blog", "frame", PINBOARD_INPUT_NONE, false));
	this.pinboards.push(new Pinboard(document.getElementById(UI.constants.PINBOARD_CHAT), "testboard", "chat", null, PINBOARD_INPUT_SINGLE_LINE, false));
	this.pinboards.push(new Pinboard(document.getElementById(UI.constants.PINBOARD_EVENTS), "testboard", "eventlog", null, PINBOARD_INPUT_NONE, false));

	console.log("showing UI");

	Events.fireEvent(window, Events.ONRESIZE);

	this.onLogout(); // just in case ;-)
	this.updateLabels();
	this.populateLocaleChooser();
	this.updateLinks();

	this.updateShowWelcomeOnLoad(false);
	if(this.showWelcomeOnLoad)
		this.showWelcome();
	
	// check for the current user
	server.playerManager.getCurrent();
	// will login if user found 

	this.hideOverlay();
};

UIManager.prototype.onLogin = function(player)
{
	if(player != null && player.j_type == Types.Player)
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
		
		// update user form
		// standard input
		document.getElementById(UI.constants.PROFILE_USERNAME_ID).value = user.username;
		document.getElementById(UI.constants.PROFILE_BIRTHDAY_ID).value = user.birthday; 
		document.getElementById(UI.constants.PROFILE_DATEFORMAT_ID).value = user.dateFormat; // TODO use select
		document.getElementById(UI.constants.PROFILE_REGISTRATIONDATE_ID).value = user.registrationDate; 
		document.getElementById(UI.constants.PROFILE_ACCOUNTSTATUS_ID).value = user.accountStatus; 
		document.getElementById(UI.constants.PROFILE_ACCOUNTSTATUSEXPIREDATE_ID).value = user.accountStatusExpireDate; 
		// selects
		this.genderSelect.selectByValue(user.gender); // TODO use select
		// timeZoneID split into region and ID
		var split = user.timeZoneID.indexOf("/");
		document.getElementById(UI.constants.PROFILE_TIMEZONEID_REGION_ID).value = user.timeZoneID.substring(0,split); // TODO use select
		document.getElementById(UI.constants.PROFILE_TIMEZONEID_ID_ID).value = user.timeZoneID.substring(split+1,user.timeZoneID.length); // TODO use select
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

UIManager.prototype.onGalaxiesLoaded = function(galaxies)
{
	console.log("galaxies loaded!");
	this.populateSelect(this.matchFilterGalaxySelect, galaxies, true);
};

UIManager.prototype.onMatchesLoaded = function(matches)
{
	console.log("matches loaded!");
	this.populateSelect(this.matchSelect, matches, false);
};

UIManager.prototype.filterMatches = function()
{
	var galaxy = this.matchFilterGalaxySelect.value;
	var prefix = document.getElementById(UI.constants.MATCH_FILTER_PREFIX_ID).value.toLowerCase();
	var state = this.matchFilterStateSelect.value;
	var creator = null;
	var participants = document.getElementById(UI.constants.MATCH_FILTER_PARTICIPANTS_ID).value;
	
	
	var matcher = function(match)
	{
		if(galaxy != null && match.galaxy.id != galaxy.id)
			return false;
		if(prefix != "" && !match.title.toLowerCase().startsWith(prefix))
			return false;
		if(state != null && match.state != state)
			return false;
		if(creator != null && match.creator.id != creator)
			return false;
		if(participants != "")
		{
			// split participants into IDs/names
			// assuming usernames don't contain the split chars "," and ";" or any whitespace
			var participantIDs = participants.split(/[\s,;]+/);
			
			var allFound = true;
			for(var p = 0; p < participantIDs.length; p++)
			{
				if(participantIDs[p] == "")
					continue; // empty token
				
				var pFound = false;
				for(var i = 0; i < match.participants.length; i++)
				{
					// by ID
					pFound = pFound || (match.participants[i].id == participantIDs[p]);
					// TODO by name (requires preloading of participant.player.user.username)
					//pFound = pFound || (match.participants[i].player.user.username.toLowerCase() == participantIDs[p].toLowerCase()); 
				}

				allFound = allFound && pFound;
			}
			
			if(!allFound)
				return false;
		}
		// TODO other properties
		return true;
	};
	
	this.matchSelect.filter(matcher);
};

UIManager.prototype.resetMatchFilters = function()
{
	this.matchFilterGalaxySelect.select(null);
	
	// do filter with cleared inputs
	this.filterMatches();
};

UIManager.prototype.disableButton = function(id, duration)
{
	var button = document.getElementById(id);
	var action = button.children[0].href;
	
	button.action = action;
	button.children[0].href = UI.constants.BUTTON_DISABLED_ACTION;
	button.classList.add(UI.constants.BUTTON_DISABLED_CLASS);
	
	if(duration)
	{
		setTimeout(function(uiManager) {
			return function() {
				uiManager.enableButton(id);
			}
		} (this), duration);
	}
};

UIManager.prototype.enableButton = function(id)
{
	var button = document.getElementById(id);
	var action = button.action;
	
	if(action)
		button.children[0].href = action;
	button.classList.remove(UI.constants.BUTTON_DISABLED_CLASS);
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
		
		// (re)enable register button
		setTimeout(function(uiManager) {
			return function() {
				uiManager.enableButton(UI.constants.REG_BUTTON_ID);
			};
		} (this), 10000);

		// show success message
		this.showErrorMessage(null, document.getElementById(UI.constants.REG_MESSAGE_ID), "message.register");
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
		
		// (re)enable register button
		this.enableButton(UI.constants.REG_BUTTON_ID);
	}
};

UIManager.prototype.hideOverlay = function()
{
	var overlay = document.getElementById(UI.constants.OVERLAY_ID);
	overlay.classList.add("invisible");
	setTimeout(function()
	{
		overlay.classList.add(UI.constants.HIDDEN_CLASS);
	}, 3000);
};

UIManager.prototype.populateSelect = function(select, options, nullOption)
{
	// append an onSelect listener to the select that updates the labels
	if(!select.onselect0)
	{
		select.onselect0 = select.onselect;
		select.onselect = function(uiManager) {
			return function(oldValue, newValue) {
				uiManager.updateLabels(this.element);
				this.onselect0(oldValue, newValue);
			};
		} (this);
	}
	// clear all previous options
	select.options.length = 0;
	// add null option
	if(nullOption == true)
	{
		option = {};
		option.value = null;
		select.options[select.options.length] = option;
	}
	// add new options
	var option;
	for(var i in options)
	{
		if(typeof (options[i]) == Reflections.type.FUNCTION)
			continue;
		option = {};
		option.value = options[i];
		select.options[select.options.length] = option;
	}
	// update DOM element
	select.update();
	// update labels
	this.updateLabels(select.element);
};

UIManager.prototype.populateEnumSelect = function(select, options, nullOption, imageClass, imagePath, convertImagePathToLowerCase)
{
	// append an onSelect listener to the select that updates the labels
	if(!select.onselect0)
	{
		select.onselect0 = select.onselect;
		select.onselect = function(uiManager) {
			return function(oldValue, newValue) {
				uiManager.updateLabels(this.element);
				this.onselect0(oldValue, newValue);
			};
		} (this);
	}
	// resolve the enum name from the lang variable
	var name = Reflections.resolveName(options, lang);
	// clear all previous options
	select.options.length = 0;
	// add null option
	if(nullOption == true)
	{
		option = {};
		option.value = null;
		option.title = "";
		select.options[select.options.length] = option;
	}
	// add new options
	var option;
	for(var i in options)
	{
		if(typeof (options[i]) == Reflections.type.FUNCTION)
			continue;
		option = {};
		option.value = i;
		option.title = this.getLabel(name + "." + i);
		if(imageClass)
			option.imageClass = imageClass;
		if(imagePath)
		{
			option.image = UI.constants.IMAGE_PATH + imagePath + i + UI.constants.IMAGE_TYPE;
			if(convertImagePathToLowerCase)
				option.image = option.image.toLowerCase();
		}
		select.options[select.options.length] = option;
	}
	// update DOM element
	select.update();
	// update labels
	this.updateLabels(select.element);
};

UIManager.prototype.populateLocaleChooser = function()
{
	this.populateEnumSelect(this.localeChooser, lang.EnumLocale, false, UI.constants.FLAGS_CLASS, UI.constants.FLAGS_PATH, true);
	// select current locale
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

UIManager.prototype.getLabel = function(key)
{
	var buff = new Array();
	buff.push("<label key='");
	buff.push(key);
	buff.push("'></label>");
	return buff.join("");
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
	// TODO selects
	// static pages
	document.getElementById(UI.constants.STATIC_FRAME_ID).contentWindow.location.reload(true)
};

UIManager.prototype.showLog = function()
{
	if(this.logOpen)
		return;
	document.getElementById(UI.constants.LOG_FRAME).classList.add("open");
	document.getElementById(UI.constants.LOG_TOGGLE_ARROW).classList.remove("flip-vertical");
	this.logOpen = true;
};

UIManager.prototype.hideLog = function()
{
	if(!this.logOpen)
		return;
	document.getElementById(UI.constants.LOG_FRAME).classList.remove("open");
	document.getElementById(UI.constants.LOG_TOGGLE_ARROW).classList.add("flip-vertical");
	this.logOpen = false;
};

UIManager.prototype.toggleLog = function()
{
	if(this.logOpen)
		this.hideLog();
	else
		this.showLog();
};

UIManager.prototype.showTop = function()
{
	if(this.logOpen)
		return;
	document.getElementById(UI.constants.TOP_FRAME).classList.add("open");
	document.getElementById(UI.constants.TOP_TOGGLE_ARROW).classList.add("flip-vertical");
	this.logOpen = true;
};

UIManager.prototype.hideTop = function()
{
	if(!this.logOpen)
		return;
	document.getElementById(UI.constants.TOP_FRAME).classList.remove("open");
	document.getElementById(UI.constants.TOP_TOGGLE_ARROW).classList.remove("flip-vertical");
	this.logOpen = false;
};

UIManager.prototype.toggleTop = function()
{
	if(this.logOpen)
		this.hideTop();
	else
		this.showTop();
};

UIManager.prototype.doLogin = function()
{
	var username = document.getElementById(UI.constants.LOGIN_USERNAME_ID).value;
	var password = document.getElementById(UI.constants.LOGIN_PASSWORD_ID).value;

	var error = false;
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
		return false;
	console.log("login as: " + username + ":" + password);
	server.playerManager.login(username, password);
	
	return false;
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
	this.disableButton(UI.constants.REG_BUTTON_ID, 0);

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

UIManager.prototype.forgotPassword = function()
{
	// clear errors
	document.getElementById(UI.constants.FORGOT_ERROR_ID).innerHTML = "";
	document.getElementById(UI.constants.FORGOT_MESSAGE_ID).innerHTML = "";

	// get input
	var username = document.getElementById(UI.constants.FORGOT_USERNAME_ID).value;
	var email = document.getElementById(UI.constants.FORGOT_EMAIL_ID).value;
	
	// validate input
	var error = false
	if(!error && (username == "" || username == null))
	{
		this.showErrorMessage(document.getElementById(UI.constants.FORGOT_USERNAME_ID), document.getElementById(UI.constants.FORGOT_ERROR_ID), "error.invalid_username");
		error = true;
	}
	if(!error && (email == "" || email == null))
	{
		this.showErrorMessage(document.getElementById(UI.constants.FORGOT_EMAIL_ID), document.getElementById(UI.constants.FORGOT_ERROR_ID), "error.invalid_email");
		error = true;
	}
	if(error)
		return;

	// disable forgot button for 5 seconds
	this.disableButton(UI.constants.FORGOT_BUTTON_ID, 5000);

	server.userManager.requestPasswordReset(username, email);
};

UIManager.prototype.updateUser = function()
{
	// clear errors
	document.getElementById(UI.constants.PROFILE_ERROR_ID).innerHTML = "";
	document.getElementById(UI.constants.PROFILE_MESSAGE_ID).innerHTML = "";
	
	var user_update = {
		// type and ID
		j_type: Types.User,
		id: this.currentPlayer.user.id,
		// get updated fields from form
		gender: this.genderSelect.value,
	}; 
	
	console.log("setting user.gender -> " + user_update.gender);
	
	// validate input
	var error = false
//	if(!error && (password_old == "" || password_old == null))
//	{
//		this.showErrorMessage(document.getElementById(UI.constants.PROFILE_PW_OLD_ID), document.getElementById(UI.constants.PROFILE_PW_ERROR_ID), "lang.error.no_password");
//		error = true;
//	}
	if(error)
		return;
	
	// update user from input
//	this.currentPlayer.user.gender = gender;

	// disable button for 5 seconds
	this.disableButton(UI.constants.PROFILE_BUTTON_ID, 5000);
	
	// use entityManager instead of userManager
	server.entityManager.save(user_update, Events.wrapEventHandler(this, this.onUserLoaded));
	//server.userManager.save(this.currentPlayer.user);
};

UIManager.prototype.changePassword = function()
{
	// clear errors
	document.getElementById(UI.constants.PROFILE_PW_ERROR_ID).innerHTML = "";
	document.getElementById(UI.constants.PROFILE_PW_MESSAGE_ID).innerHTML = "";
	
	// get input
	var password_old = document.getElementById(UI.constants.PROFILE_PW_OLD_ID).value;
	var password_new = document.getElementById(UI.constants.PROFILE_PW_NEW_ID).value;
	var password_confirm = document.getElementById(UI.constants.PROFILE_PW_CONFIRM_ID).value;
	
	// validate input
	var error = false
	if(!error && (password_old == "" || password_old == null))
	{
		this.showErrorMessage(document.getElementById(UI.constants.PROFILE_PW_OLD_ID), document.getElementById(UI.constants.PROFILE_PW_ERROR_ID), "lang.error.no_password");
		error = true;
	}
	if(!error && (password_new == "" || password_new == null ||  password_new != password_confirm))
	{
		this.showErrorMessage(null, document.getElementById(UI.constants.PROFILE_PW_ERROR_ID), "error.password_mismatch");
		this.showErrorMessage(document.getElementById(UI.constants.PROFILE_PW_NEW_ID), null);
		this.showErrorMessage(document.getElementById(UI.constants.PROFILE_PW_CONFIRM_ID), null);
		error = true;
	}
	if(error)
		return;

	// disable button for 5 seconds
	this.disableButton(UI.constants.PROFILE_PW_BUTTON_ID, 5000);
	
	// clear inputs
	document.getElementById(UI.constants.PROFILE_PW_OLD_ID).value = "";
	document.getElementById(UI.constants.PROFILE_PW_NEW_ID).value = "";
	document.getElementById(UI.constants.PROFILE_PW_CONFIRM_ID).value = "";

	server.userManager.changePassword(password_old, password_new, password_confirm);
};

UIManager.prototype.changeEmail = function()
{
	// clear errors
	document.getElementById(UI.constants.PROFILE_EMAIL_ERROR_ID).innerHTML = "";
	document.getElementById(UI.constants.PROFILE_EMAIL_MESSAGE_ID).innerHTML = "";
	
	// get input
	var email_new = document.getElementById(UI.constants.PROFILE_EMAIL_ID).value;
	
	// validate input
	var error = false
	if(!error && (email_new == "" || email_new == null))
	{
		this.showErrorMessage(document.getElementById(UI.constants.PROFILE_EMAIL_ID), document.getElementById(UI.constants.PROFILE_PW_ERROR_ID), "lang.error.invalid_email");
		error = true;
	}
	if(error)
		return;

	// disable button for 5 seconds
	this.disableButton(UI.constants.PROFILE_EMAIL_BUTTON_ID, 5000);
	
	// clear inputs
	//document.getElementById(UI.constants.PROFILE_EMAIL_ID).value = "";

	server.userManager.updateMailAddress(email_new);
};

UIManager.prototype.showRegister = function()
{
	this.window_register.setVisible(true);
};

UIManager.prototype.hideRegister = function()
{
	this.window_register.setVisible(false);
};

UIManager.prototype.showForgot = function()
{
	this.window_forgot.setVisible(true);
};

UIManager.prototype.hideForgot = function()
{
	this.window_forgot.setVisible(false);
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
		inputfield.classList.add(UI.constants.ERROR_CLASS);
		setTimeout(function()
		{
			inputfield.classList.remove(UI.constants.ERROR_CLASS);
		}, 3000);
	}
	if(messagefield != null)
	{
		if(message)
		{
			while(messagefield.firstChild)
				messagefield.removeChild(messagefield.firstChild);
			messagefield.appendChild(document.createTextNode(this.getString(message)));
		}
		
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