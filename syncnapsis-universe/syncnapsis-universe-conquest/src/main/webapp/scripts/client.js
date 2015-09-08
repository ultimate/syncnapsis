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
//@requires("ComboSelect")
//@requires("Pinboard")
//@requires("application-base")
//@requires("constants")
var UI = {};

UI.constants = {};
UI.constants.AD_WIDTH = 170;
UI.constants.AD_BORDER = 2;
UI.constants.BAR_HEIGHT = 24;
UI.constants.BAR_OUTER_WIDTH = 300;

UI.constants.KEY_SHOW_WELCOME = "showWelcomeOnLoad";
UI.constants.KEY_LAST_MATCH_SELECTED = "lastMatchSelected";

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

UI.constants.VIEW_ID = "view";
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
UI.constants.MATCH_FILTER_CREATOR_ID = "match_filter_creator";
UI.constants.MATCH_FILTER_PARTICIPANTS_ID = "match_filter_participants";
UI.constants.MATCH_FILTER_GALAXY_SELECT_ID = "match_filter_galaxy_select";
UI.constants.MATCH_FILTER_STATE_SELECT_ID = "match_filter_state_select";
UI.constants.MATCH_CREATE_BUTTON_ID = "match_create_button";
UI.constants.MATCH_RANK_TABLE_ID = "match_rank_table";
UI.constants.MATCH_TITLE_ID = "match_title_$";
UI.constants.MATCH_GALAXY_SELECT_ID = "match_galaxy_select_$";
UI.constants.MATCH_SEED_ID = "match_seed_$";
UI.constants.MATCH_SPEED_SELECT_ID = "match_speed_select_$";
UI.constants.MATCH_STARTCONDITION_SELECT_ID = "match_startCondition_select_$";
UI.constants.MATCH_STARTDATE_ID = "match_startDate_$";
UI.constants.MATCH_STARTSYSTEMSELECTIONENABLED_SELECT_ID = "match_startSystemSelectionEnabled_select_$";
UI.constants.MATCH_STARTSYSTEMCOUNT_ID = "match_startSystemCount_$";
UI.constants.MATCH_STARTPOPULATION_ID = "match_startPopulation_$";
UI.constants.MATCH_VICTORYCONDITION_SELECT_ID = "match_victoryCondition_select_$";
UI.constants.MATCH_VICTORYPARAMETER_SELECT_ID = "match_victoryParameter_select_$";
UI.constants.MATCH_VICTORYPARAMETER_CUSTOM_ID = "match_victoryParameter_custom_$";
UI.constants.MATCH_PLANNEDJOINTYPE_SELECT_ID = "match_plannedJoinType_select_$";
UI.constants.MATCH_STARTEDJOINTYPE_SELECT_ID = "match_startedJoinType_select_$";
UI.constants.MATCH_PARTICIPANTSMAX_ID = "match_participantsMax_$";
UI.constants.MATCH_PARTICIPANTSMIN_ID = "match_participantsMin_$";
UI.constants.MATCH_PARTICIPANTS_SOURCE_ID = "match_participants_source_$";
UI.constants.MATCH_PARTICIPANTS_TARGET_ID = "match_participants_target_$";
UI.constants.MATCH_BUTTONS_ID = "match_buttons_$";
UI.constants.SEND_ROW_ID = "send_row_$";
UI.constants.SEND_POP_ID = "send_pop_$";
UI.constants.SEND_AVAILABLE_ID = "send_available_$";
UI.constants.SEND_GIVEUP_ID = "send_giveup_$";
UI.constants.SEND_SPEED_ID = "send_speed_$";

UI.constants.MATCH_SYSTEM_INFO_ID = "match_system_info";

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
UI.constants.INVISIBLE_CLASS = "hidden";

UI.constants.DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

UI.constants.PINBOARD_NEWS = "pinboard_news";
UI.constants.PINBOARD_CHAT = "pinboard_chat";
UI.constants.PINBOARD_EVENTS = "pinboard_events";

UI.constants.PLACEHOLDER = "$";
UI.constants.PLACEHOLDER_ATTRIBUTES = ["id", "for", "href", "onchange"];
// channel constants analog to UniverseConquestConstants
UI.constants.CHANNEL_ID_PLACEHOLDER	= "$";
UI.constants.CHANNEL_MATCH_CREATED = "match_created";
UI.constants.CHANNEL_MATCH_RANKS = "match_" + UI.constants.CHANNEL_ID_PLACEHOLDER + "_ranks";
UI.constants.CHANNEL_MATCH_SYSTEMS = "match_" + UI.constants.CHANNEL_ID_PLACEHOLDER + "_systems";
UI.constants.CHANNEL_MATCH_MOVEMENTS = "match_" + UI.constants.CHANNEL_ID_PLACEHOLDER + "_movements";

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
	// disable until matches are loaded
	this.matchSelect.setDisabled(true);
	// overwrite renderer
	this.matchSelect.getOptionContent = function(uiManager) {
		return function(option) {
			var match = option.value;
			var content = new Array();
			// TODO image?
			content.push("<span class='col_1'>");
			content.push(match.id);
			content.push("</span><span class='col_2'>");
			content.push(match.title);
			content.push("</span><span class='col_3'>");
			content.push("(");
			content.push(uiManager.getLabel("EnumMatchState." + match.state));
			content.push(")");
			content.push("</span>");
			return content.join("");
		};
	} (this);
	// overwrite onselect
	// need to do this after populate, since populate overwrites onselect, too
	this.matchSelect.onselect = function(uiManager) {
		return function(oldValue, newValue)
		{
			uiManager.onMatchSelect(oldValue, newValue);
		};
	} (this);
	
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

	// initialize state select for match filter
	this.matchFilterStateSelect = new Select(UI.constants.MATCH_FILTER_STATE_SELECT_ID);
	// populate with enum
	this.populateEnumSelect(this.matchFilterStateSelect, lang.EnumMatchState, true);
	
	// initialize selects for match info
	this.matchInfoGalaxySelect = new Select(UI.constants.MATCH_GALAXY_SELECT_ID.replace(UI.constants.PLACEHOLDER, "info"));
	this.matchInfoGalaxySelect.getOptionContent = this.matchFilterGalaxySelect.getOptionContent;
	this.matchInfoGalaxySelect.setDisabled(true);
	this.matchInfoSpeedSelect = new Select(UI.constants.MATCH_SPEED_SELECT_ID.replace(UI.constants.PLACEHOLDER, "info"));
	this.populateEnumSelect(this.matchInfoSpeedSelect, lang.EnumMatchSpeed, false);
	this.matchInfoSpeedSelect.setDisabled(true);
	this.matchInfoVictoryConditionSelect = new Select(UI.constants.MATCH_VICTORYCONDITION_SELECT_ID.replace(UI.constants.PLACEHOLDER, "info"));
	this.populateEnumSelect(this.matchInfoVictoryConditionSelect, lang.EnumVictoryCondition, false);
	this.matchInfoVictoryConditionSelect.setDisabled(true);
	document.getElementById(UI.constants.MATCH_STARTDATE_ID.replace(UI.constants.PLACEHOLDER, "info")).disabled = "disabled";
	document.getElementById(UI.constants.MATCH_VICTORYPARAMETER_CUSTOM_ID.replace(UI.constants.PLACEHOLDER, "info")).disabled = "disabled";

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

	server.matchManager.getAll(); // load matches
	server.galaxyManager.getAll(); // load galaxies
	server.empireManager.getAll(); // load empires
	
	console.log("creating 3D-view");
	this.stats = new Stats();
	document.body.appendChild( this.stats.domElement );	
	this.view = new View(document.getElementById(UI.constants.VIEW_ID), this.stats, false);
	this.view.eventManager.onSelectionChange = function(uiManager) {
		return function(system, isTarget, changeType)
		{
			uiManager.updateSelectionInfo(system, isTarget, changeType);
		};
	} (this);
	this.view.animate();
	
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
	
	// subscribe to channel(s)
	server.conquestManager.subscribe(UI.constants.CHANNEL_MATCH_CREATED);

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
		// show some buttons
		document.getElementById(UI.constants.MATCH_CREATE_BUTTON_ID).classList.remove(UI.constants.INVISIBLE_CLASS);
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
	// hide some buttons
	document.getElementById(UI.constants.MATCH_CREATE_BUTTON_ID).classList.add(UI.constants.INVISIBLE_CLASS);
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
	this.galaxies = galaxies;
	this.populateSelect(this.matchFilterGalaxySelect, galaxies, true);
	this.populateSelect(this.matchInfoGalaxySelect, galaxies, true);
};

UIManager.prototype.onEmpiresLoaded = function(empires)
{
	// Note: load empires instead of users but display them by username anyway
	// this way pseudo-users/players are not sbown (e.g. system-user)
	console.log("empires loaded!");
	this.empires = empires;
	server.entityManager.loadProperty(empires, "player.user", function(empires) {
		console.log("empire users loaded!");
	});
};

UIManager.prototype.onMatchesLoaded = function(matches)
{
	console.log("matches loaded!");
	this.populateSelect(this.matchSelect, matches, false);
	
	// preload sub-entities for filtering
	// TODO optimize? this is a lot of data!
	server.entityManager.loadProperty(matches, "participants.empire.player.user", function(uiManager) {
		return function(matches) {
			console.log("matches loaded with properties!");
			// show previously selected match (if any, else random)
			var matchId = localStorage.getItem(UI.constants.KEY_LAST_MATCH_SELECTED);
			if(matchId != null)
				matchId = Number(matchId);
			var matchIndex = Math.round(Math.random()*uiManager.matchSelect.options.length);
			for(var o in uiManager.matchSelect.options)
			{
				if(uiManager.matchSelect.options[o].value.id == matchId)
				{
					matchIndex = o;
					break;
				}
			}
			uiManager.matchSelect.setDisabled(false);
			uiManager.matchSelect.select(matchIndex);
		};
	} (this));
	server.entityManager.loadProperty(matches, "creator.user", function(matches) {
		console.log("match creators loaded!");
	});
};

UIManager.prototype.onMatchSelect = function(oldMatch, newMatch)
{
	console.log("showing match #" + newMatch.id);
	this.currentMatch = newMatch;
	
	// update info
	this.matchInfoGalaxySelect.selectById(newMatch.galaxy.id);
	this.matchInfoSpeedSelect.selectByValue("value" + newMatch.speed);
	this.matchInfoVictoryConditionSelect.selectByValue(newMatch.victoryCondition);
	document.getElementById(UI.constants.MATCH_STARTDATE_ID.replace(UI.constants.PLACEHOLDER, "info")).value = newMatch.startDate;
	document.getElementById(UI.constants.MATCH_VICTORYPARAMETER_CUSTOM_ID.replace(UI.constants.PLACEHOLDER, "info")).value = newMatch.victoryParameter
	
	// fetch galaxy from info (this way we do not need to search by id again)
	this.currentMatch.galaxy = this.matchInfoGalaxySelect.value;
	
	// unsubscribe previous match channels
	if(oldMatch != null && oldMatch.id != null)
	{
		server.conquestManager.unsubscribe(UI.constants.CHANNEL_MATCH_RANKS.replace(UI.constants.CHANNEL_ID_PLACEHOLDER, oldMatch.id));
		server.conquestManager.unsubscribe(UI.constants.CHANNEL_MATCH_SYSTEMS.replace(UI.constants.CHANNEL_ID_PLACEHOLDER, oldMatch.id));
		server.conquestManager.unsubscribe(UI.constants.CHANNEL_MATCH_MOVEMENTS.replace(UI.constants.CHANNEL_ID_PLACEHOLDER, oldMatch.id));
	}
	// subscribe to match channels
	server.conquestManager.subscribe(UI.constants.CHANNEL_MATCH_RANKS.replace(UI.constants.CHANNEL_ID_PLACEHOLDER, newMatch.id));
	server.conquestManager.subscribe(UI.constants.CHANNEL_MATCH_SYSTEMS.replace(UI.constants.CHANNEL_ID_PLACEHOLDER, newMatch.id));
	server.conquestManager.subscribe(UI.constants.CHANNEL_MATCH_MOVEMENTS.replace(UI.constants.CHANNEL_ID_PLACEHOLDER, newMatch.id));
	
	// load galaxy info for this match in background
	// doShowMatch will update 3D-view
	AJAX.sendRequest("galaxy/" + newMatch.id + ".json", null, HTTP.GET, null, this.doShowMatch, null, this);
	
	// save match-ID as last selection
	localStorage.setItem(UI.constants.KEY_LAST_MATCH_SELECTED, newMatch.id);
};

UIManager.prototype.filterMatches = function()
{
	var galaxy = this.matchFilterGalaxySelect.value;
	var prefix = document.getElementById(UI.constants.MATCH_FILTER_PREFIX_ID).value.toLowerCase();
	var state = this.matchFilterStateSelect.value;
	var creator = document.getElementById(UI.constants.MATCH_FILTER_CREATOR_ID).value;
	var participants = document.getElementById(UI.constants.MATCH_FILTER_PARTICIPANTS_ID).value;
	// split participants into IDs/names
	// assuming usernames don't contain the split chars "," and ";" or any whitespace
	var participantIDs = participants.split(/[\s,;]+/);
	
	
	var matcher = function(match)
	{
		if(galaxy != null && match.galaxy.id != galaxy.id)
			return false;
		if(prefix != "" && !match.title.toLowerCase().startsWith(prefix))
			return false;
		if(state != null && match.state != state)
			return false;
		if(creator != "" && match.creator.id != creator && match.creator.user.username.toLowerCase() != creator.toLowerCase())
			return false;
		if(participants != "")
		{
			var allFound = true;
			for(var p = 0; p < participantIDs.length; p++)
			{
				if(participantIDs[p] == "")
					continue; // empty token
				
				var pFound = false;
				for(var i = 0; i < match.participants.length; i++)
				{
					// by ID
					pFound = pFound || (match.participants[i].empire.player.id == participantIDs[p]);
					// by name (requires preloading of participant.player.user.username)
					pFound = pFound || (match.participants[i].empire.player.user.username.toLowerCase() == participantIDs[p].toLowerCase()); 
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
	this.matchFilterStateSelect.select(null);

	document.getElementById(UI.constants.MATCH_FILTER_PREFIX_ID).value = "";
	document.getElementById(UI.constants.MATCH_FILTER_CREATOR_ID).value = "";
	document.getElementById(UI.constants.MATCH_FILTER_PARTICIPANTS_ID).value = "";
	
	// do filter with cleared inputs
	this.filterMatches();
};

UIManager.prototype.updateMatchRankTable = function(match)
{
	var table = document.getElementById(UI.constants.MATCH_RANK_TABLE_ID);
	var oldTbody = table.children[1];
	var newTbody = document.createElement("tbody");
	
	// helper method for adding a single cell to a row
	var addCell = function(tr, content)
	{
		var td = document.createElement("td");
		td.innerHTML = content;
		tr.appendChild(td);
	};
	
	// sort participants
	match.participants.sort(function(p1, p2) {
		return p1.rank - p2.rank;
	});
	
	var tr, td;
	for(var p = 0; p < match.participants.length; p++)
	{
		tr = document.createElement("tr");
		
		addCell(tr, match.participants[p].rank);
		addCell(tr, match.participants[p].empire.player.user.username);
		addCell(tr, match.participants[p].rankRawValue);
		addCell(tr, match.participants[p].rankValue.toFixed(0));
		
		newTbody.appendChild(tr);
	}
	
	table.replaceChild(newTbody, oldTbody);
};

UIManager.prototype.showMatch = function(match)
{
	var titleKey;
	var id;
	if(match == null)
	{
		match = this.loadLocalObject("match_default");
		if(match == null) // no default
		{
			match = {
					title: "",
					speed: 2,
					seed: new Date().getTime(),
					startCondition: "immediately",
					startDate: 0,
					startSystemSelectionEnabled: false,
					startSystemCount: 3,
					startPopulation: 1e10,
					victoryCondition: "extermination",
					participantsMin: 2,
					participantsMax: 5,
					plannedJoinType: "invitationsOnly",
					startedJoinType: "none",
			};
		}
		match.id = null; // new match
		match.j_type = Types.Match;
		match.creator = this.currentPlayer;
		match.participants = [];
		
		titleKey = "menu.match_create";
		id = "new";
	}
	else
	{
		titleKey = "menu.match";
		id = match.id;
	}
	var win = this.getWindowFromTemplate(id, "content_manage_match", titleKey);
	win.setSize(600,500);
	win.center();
	win.setMovable(true);
	win.matchId = id;
	
	if(!win.matchGalaxySelect)
	{
		win.matchGalaxySelect = new Select(UI.constants.MATCH_GALAXY_SELECT_ID.replace(UI.constants.PLACEHOLDER, id));
		win.matchGalaxySelect.getOptionContent = this.matchFilterGalaxySelect.getOptionContent; // set renderer
		this.populateSelect(win.matchGalaxySelect, this.galaxies, false);
	}
	if(!win.matchSpeedSelect)
	{
		win.matchSpeedSelect = new Select(UI.constants.MATCH_SPEED_SELECT_ID.replace(UI.constants.PLACEHOLDER, id));
		this.populateEnumSelect(win.matchSpeedSelect, lang.EnumMatchSpeed, false);
	}
	if(!win.matchStartConditionSelect)
	{
		win.matchStartConditionSelect = new Select(UI.constants.MATCH_STARTCONDITION_SELECT_ID.replace(UI.constants.PLACEHOLDER, id));
		win.matchStartConditionSelect.onselect = function(uiManager, id) {
			return function(oldValue, newValue)
			{
				var dateTF = document.getElementById(UI.constants.MATCH_STARTDATE_ID.replace(UI.constants.PLACEHOLDER, id));
				if(newValue == "immediately")
				{
					dateTF.setAttribute("disabled", "disabled");
					win.matchPlannedJoinTypeSelect.setDisabled(true);
				}
				else if(newValue == "manually")
				{
					dateTF.setAttribute("disabled", "disabled");
					win.matchPlannedJoinTypeSelect.setDisabled(false);
				}
				else if(newValue == "planned")
				{
					dateTF.removeAttribute("disabled");
					win.matchPlannedJoinTypeSelect.setDisabled(false);
				}
			};
		} (this, id);
		this.populateEnumSelect(win.matchStartConditionSelect, lang.EnumStartCondition, false);
	}
	if(!win.matchStartSystemSelectionEnabledSelect)
	{
		win.matchStartSystemSelectionEnabledSelect = new Select(UI.constants.MATCH_STARTSYSTEMSELECTIONENABLED_SELECT_ID.replace(UI.constants.PLACEHOLDER, id));
		this.populateEnumSelect(win.matchStartSystemSelectionEnabledSelect, lang.EnumManually, false);
	}
	if(!win.matchVictoryParameterSelect)
	{
		win.matchVictoryParameterSelect = new Select(UI.constants.MATCH_VICTORYPARAMETER_SELECT_ID.replace(UI.constants.PLACEHOLDER, id));
		win.matchVictoryParameterSelect.onselect = function(uiManager, id) {
			return function(oldValue, newValue)
			{
				var paramTF = document.getElementById(UI.constants.MATCH_VICTORYPARAMETER_CUSTOM_ID.replace(UI.constants.PLACEHOLDER, id));
				if(newValue != "custom")
				{
					var val = (newValue != null && newValue.length > 5 ? newValue.substring(5) : "");
					paramTF.value = val;
					paramTF.setAttribute("disabled", "disabled");
				}
				else
				{
					paramTF.removeAttribute("disabled");
				}
			};
		} (this, id);
		this.populateEnumSelect(win.matchVictoryParameterSelect, lang.EnumDifficulty_domination, false);
		// don't populate (is populated on victoryCondition select)
	}
	if(!win.matchVictoryConditionSelect)
	{
		win.matchVictoryConditionSelect = new Select(UI.constants.MATCH_VICTORYCONDITION_SELECT_ID.replace(UI.constants.PLACEHOLDER, id));
		win.matchVictoryConditionSelect.onselect = function(uiManager) {
			return function(oldValue, newValue)
			{
				if(oldValue != newValue)
				{				
					if(newValue == "domination")
					{
						uiManager.populateEnumSelect(win.matchVictoryParameterSelect, lang.EnumDifficulty_domination, false);
						win.matchVictoryParameterSelect.setDisabled(false);
					}
					else if(newValue == "extermination")
					{
						uiManager.populateEnumSelect(win.matchVictoryParameterSelect, lang.EnumDifficulty_extermination, false);
						win.matchVictoryParameterSelect.setDisabled(false);
					}
					else
					{
						uiManager.populateEnumSelect(win.matchVictoryParameterSelect, lang.EnumDifficulty_vendetta, false);
						win.matchVictoryParameterSelect.setDisabled(true);
					}
					// the "normal" value is at index 2
					// select this if possible (otherwise select last value)
					win.matchVictoryParameterSelect.select(Math.min(2, win.matchVictoryParameterSelect.options.length));
				}
			};
		} (this);
		this.populateEnumSelect(win.matchVictoryConditionSelect, lang.EnumVictoryCondition, false);
	}
	if(!win.matchPlannedJoinTypeSelect)
	{
		win.matchPlannedJoinTypeSelect = new Select(UI.constants.MATCH_PLANNEDJOINTYPE_SELECT_ID.replace(UI.constants.PLACEHOLDER, id));
		this.populateEnumSelect(win.matchPlannedJoinTypeSelect, lang.EnumJoinType, false);
	}
	if(!win.matchStartedJoinTypeSelect)
	{
		win.matchStartedJoinTypeSelect = new Select(UI.constants.MATCH_STARTEDJOINTYPE_SELECT_ID.replace(UI.constants.PLACEHOLDER, id));
		this.populateEnumSelect(win.matchStartedJoinTypeSelect, lang.EnumJoinType, false);
	}
	if(!win.matchParticipantComboSelect)
	{
		var sourceID = UI.constants.MATCH_PARTICIPANTS_SOURCE_ID.replace(UI.constants.PLACEHOLDER, id);
		var targetID = UI.constants.MATCH_PARTICIPANTS_TARGET_ID.replace(UI.constants.PLACEHOLDER, id);
		win.matchParticipantComboSelect = new ComboSelect(sourceID, targetID);
		// select participants by empire
		win.matchParticipantComboSelect.getOptionContent = function(empire) {
			var content = new Array();
			content.push("<span class='col_1'>");
			content.push(empire.player.user.id);
			content.push("</span><span class='col_2'>");
			content.push(empire.player.user.username);
			content.push("</span>");
			return content.join("");
		};
		win.matchParticipantComboSelect.addOptions( this.empires );
	}
	
	// populate form
	document.getElementById(UI.constants.MATCH_TITLE_ID.replace(UI.constants.PLACEHOLDER, id)).value = match.title;
	win.matchGalaxySelect.selectById(match.galaxy ? match.galaxy.id : null);
	document.getElementById(UI.constants.MATCH_SEED_ID.replace(UI.constants.PLACEHOLDER, id)).value = match.seed;
	win.matchSpeedSelect.select(match.speed);
	win.matchStartConditionSelect.selectByValue(match.startCondition);
	document.getElementById(UI.constants.MATCH_STARTDATE_ID.replace(UI.constants.PLACEHOLDER, id)).value = match.startDate;
	win.matchStartSystemSelectionEnabledSelect.selectByValue(match.startSystemSelectionEnabled ? "value1" : "value0");
	document.getElementById(UI.constants.MATCH_STARTSYSTEMCOUNT_ID.replace(UI.constants.PLACEHOLDER, id)).value = match.startSystemCount;
	document.getElementById(UI.constants.MATCH_STARTPOPULATION_ID.replace(UI.constants.PLACEHOLDER, id)).value = match.startPopulation / 1e9;
	win.matchVictoryConditionSelect.selectByValue(match.victoryCondition);
	document.getElementById(UI.constants.MATCH_VICTORYPARAMETER_CUSTOM_ID.replace(UI.constants.PLACEHOLDER, id)).value = match.victoryParameter;
	win.matchVictoryParameterSelect.selectByValue("custom");
	if(match.victoryParameter)
	{
		for(var o = 0; o < win.matchVictoryParameterSelect.options.length; o++)
		{
			if(win.matchVictoryParameterSelect.options[o].value == "value" + match.victoryParameter)
				win.matchVictoryParameterSelect.selectByValue(win.matchVictoryParameterSelect.options[o].value);
		}
	}
	else
	{
		win.matchVictoryParameterSelect.select(2);
	}
	document.getElementById(UI.constants.MATCH_PARTICIPANTSMAX_ID.replace(UI.constants.PLACEHOLDER, id)).value = match.participantsMax;
	document.getElementById(UI.constants.MATCH_PARTICIPANTSMIN_ID.replace(UI.constants.PLACEHOLDER, id)).value = match.participantsMin;
	win.matchPlannedJoinTypeSelect.selectByValue(match.plannedJoinType);
	win.matchStartedJoinTypeSelect.selectByValue(match.startedJoinType);

	if(match.id == null)
	{
		// add current player as creator
		// use first available empire
		var playerEmpire = this.currentPlayer.empires[0];
		win.matchParticipantComboSelect.selectSourceById(playerEmpire.id);
		win.matchParticipantComboSelect.applySource();
		// disable creator
		win.matchParticipantComboSelect.targetList.children[0].classList.add("disabled");
	}
	else
	{
		var participant;
		for(var p = 0; p < match.participants.length; p++)
		{
			participant = match.participants[p];
			if(participant != null)
			{
				var type = server.entityManager.getType(participant); 
				// allow both empire and participant
				if(type == Types.Participant) 
					win.matchParticipantComboSelect.selectSourceById(participant.empire.id);
				else if(type == Types.Empire) // participant is empire
					win.matchParticipantComboSelect.selectSourceById(participant.id);
			}
		}
		win.matchParticipantComboSelect.applySource();
	}
	
	if(match.id != null)
	{
		// disable all
		document.getElementById(UI.constants.MATCH_TITLE_ID.replace(UI.constants.PLACEHOLDER, id)).disabled = "disabled";
		win.matchGalaxySelect.setDisabled(true);
		document.getElementById(UI.constants.MATCH_SEED_ID.replace(UI.constants.PLACEHOLDER, id)).disabled = "disabled";
		win.matchSpeedSelect.setDisabled(true);
		win.matchStartConditionSelect.setDisabled(true);
		document.getElementById(UI.constants.MATCH_STARTDATE_ID.replace(UI.constants.PLACEHOLDER, id)).disabled = "disabled";
		win.matchStartSystemSelectionEnabledSelect.setDisabled(true);
		document.getElementById(UI.constants.MATCH_STARTSYSTEMCOUNT_ID.replace(UI.constants.PLACEHOLDER, id)).disabled = "disabled";
		document.getElementById(UI.constants.MATCH_STARTPOPULATION_ID.replace(UI.constants.PLACEHOLDER, id)).disabled = "disabled";
		win.matchVictoryConditionSelect.setDisabled(true);
		win.matchVictoryParameterSelect.setDisabled(true);
		document.getElementById(UI.constants.MATCH_VICTORYPARAMETER_CUSTOM_ID.replace(UI.constants.PLACEHOLDER, id)).disabled = "disabled";
		document.getElementById(UI.constants.MATCH_PARTICIPANTSMAX_ID.replace(UI.constants.PLACEHOLDER, id)).disabled = "disabled";
		document.getElementById(UI.constants.MATCH_PARTICIPANTSMIN_ID.replace(UI.constants.PLACEHOLDER, id)).disabled = "disabled";
		win.matchPlannedJoinTypeSelect.setDisabled(true);
		win.matchParticipantComboSelect.setDisabled(true);
	}
	
	// disable some future features
	win.matchStartConditionSelect.setDisabled(true);
	win.matchStartSystemSelectionEnabledSelect.setDisabled(true);
	win.matchStartedJoinTypeSelect.setDisabled(true);
	
	// show the right buttons
	var buttons = document.getElementById(UI.constants.MATCH_BUTTONS_ID.replace(UI.constants.PLACEHOLDER, id));
	for(var i = 0; i < buttons.children.length; i++)
	{
		if(match.id == null)
		{
			// hide "existing" buttons
			// show "new" or "neutral" buttons
			if(buttons.children[i].classList.contains("existing"))
				buttons.children[i].classList.add("hidden");
			else
				buttons.children[i].classList.remove("hidden");
		}
		else if(this.currentPlayer != null && this.currentPlayer.id == match.creator.id)
		{
			// hide "new" buttons
			// show "existing" or "neutral" buttons
			if(buttons.children[i].classList.contains("new"))
				buttons.children[i].classList.add("hidden");
			else
				buttons.children[i].classList.remove("hidden");
		}
		else
		{
			// hide both if current player is not creator (and match is not new)
			// only show "neutral" buttons
			if(buttons.children[i].classList.contains("existing") || buttons.children[i].classList.contains("new")) // hide both buttons
				buttons.children[i].classList.add("hidden");
			else
				buttons.children[i].classList.remove("hidden");
		}
	}

	// force label update
	this.updateLabels(win);
	// show window
	win.setVisible(true);
};

UIManager.prototype.getMatchFromWindow = function(win)
{
	if(win == null)
		return null;
	var id = win.matchId;
	if(id == null)
		return null;

	var match = {};
	
	match.title = document.getElementById(UI.constants.MATCH_TITLE_ID.replace(UI.constants.PLACEHOLDER, id)).value;
	match.galaxy = win.matchGalaxySelect.value;
	match.seed = document.getElementById(UI.constants.MATCH_SEED_ID.replace(UI.constants.PLACEHOLDER, id)).value;
	match.speed = win.matchSpeedSelect.valueIndex;
	match.startCondition = win.matchStartConditionSelect.value;
	match.startDate = Number(document.getElementById(UI.constants.MATCH_STARTDATE_ID.replace(UI.constants.PLACEHOLDER, id)).value);
	match.startSystemSelectionEnabled = (win.matchStartSystemSelectionEnabledSelect.value == "value1");
	match.startSystemCount = Number(document.getElementById(UI.constants.MATCH_STARTSYSTEMCOUNT_ID.replace(UI.constants.PLACEHOLDER, id)).value);
	match.startPopulation = Number(document.getElementById(UI.constants.MATCH_STARTPOPULATION_ID.replace(UI.constants.PLACEHOLDER, id)).value) * 1e9;
	match.victoryCondition = win.matchVictoryConditionSelect.value;
	match.victoryParameter = Number(document.getElementById(UI.constants.MATCH_VICTORYPARAMETER_CUSTOM_ID.replace(UI.constants.PLACEHOLDER, id)).value);
	match.participantsMax = Number(document.getElementById(UI.constants.MATCH_PARTICIPANTSMAX_ID.replace(UI.constants.PLACEHOLDER, id)).value);
	match.participantsMin = Number(document.getElementById(UI.constants.MATCH_PARTICIPANTSMIN_ID.replace(UI.constants.PLACEHOLDER, id)).value);
	match.plannedJoinType = win.matchPlannedJoinTypeSelect.value;
	match.startedJoinType = win.matchStartedJoinTypeSelect.value;
	
	// get participants as empires 
	// -> MatchManager.create(..) required empire IDs anyway
	match.empireIds = [];
	for(var i = 0; i < win.matchParticipantComboSelect.targetList.children.length; i++)
	{
		match.empireIds.push(win.matchParticipantComboSelect.targetList.children[i].val.id);
	}
	
	return match;
};

UIManager.prototype.createMatch = function(id)
{
	var winId = this.getWindowId(id, "content_manage_match");
	var win = document.getElementById(winId);
	
	var match = this.getMatchFromWindow(win);
	
	console.log("creating match '" + match.title + "'");
	
	// validate input
	var error = false;
	if(match.title == null || match.title == "")
	{
		this.showErrorMessage(document.getElementById(UI.constants.MATCH_TITLE_ID.replace(UI.constants.PLACEHOLDER, id)), null);
		error = true;
	}
	if(match.galaxy == null)
	{
		this.showErrorMessage(document.getElementById(UI.constants.MATCH_GALAXY_SELECT_ID.replace(UI.constants.PLACEHOLDER, id)), null);
		error = true;
	}
	if(match.victoryParameter == null || match.victoryParameter == "")
	{
		this.showErrorMessage(document.getElementById(UI.constants.MATCH_VICTORYPARAMETER_SELECT_ID.replace(UI.constants.PLACEHOLDER, id)), null);
		this.showErrorMessage(document.getElementById(UI.constants.MATCH_VICTORYPARAMETER_CUSTOM_ID.replace(UI.constants.PLACEHOLDER, id)), null);
		error = true;
	}
	if(match.participantsMin > match.participantsMax)
	{
		this.showErrorMessage(document.getElementById(UI.constants.MATCH_PARTICIPANTSMIN_ID.replace(UI.constants.PLACEHOLDER, id)), null);
		this.showErrorMessage(document.getElementById(UI.constants.MATCH_PARTICIPANTSMAX_ID.replace(UI.constants.PLACEHOLDER, id)), null);
		error = true;
	}
	else if(match.empireIds.length > match.participantsMax)
	{
		this.showErrorMessage(document.getElementById(UI.constants.MATCH_PARTICIPANTSMAX_ID.replace(UI.constants.PLACEHOLDER, id)), null);
		this.showErrorMessage(win.matchParticipantComboSelect.targetList, null);
		error = true;
	}
	else if(match.empireIds.length < match.participantsMin)
	{
		this.showErrorMessage(document.getElementById(UI.constants.MATCH_PARTICIPANTSMIN_ID.replace(UI.constants.PLACEHOLDER, id)), null);
		this.showErrorMessage(win.matchParticipantComboSelect.targetList, null);
		error = true;
	}
	
	if(error)
		return;
	
	// everything valid -> create match
	
//	this.createMatch = function(title, galaxyId, speed, seedString, startCondition, startDate,
//			startSystemSelectionEnabled, startSystemCount, startPopulation, victoryCondition,
//			victoryParameter, participantsMax, participantsMin, empireIds, plannedJoinType,
//			startedJoinType)
	server.matchManager.createMatch(
			match.title,
			match.galaxy.id,
			match.speed,
			match.seed,
			match.startCondition,
			match.startDate,
			match.startSystemSelectionEnabled,
			match.startSystemCount,
			match.startPopulation,
			match.victoryCondition,
			match.victoryParameter,
			match.participantsMax,
			match.participantsMin,
			match.empireIds,
			match.plannedJoinType,
			match.startedJoinType
	);
};

UIManager.prototype.onMatchCreated = function(match)
{
	// ignore match here since it is pushed by server anyway
	console.log("match '" + match.title + "' created: ID=" + match.id);
	
	// close new match window
	var winId = this.getWindowId("new", "content_manage_match");
	var win = document.getElementById(winId);

	win.close();
};

UIManager.prototype.discardMatch = function(id)
{
	var winId = this.getWindowId(id, "content_manage_match");
	var win = document.getElementById(winId);

	win.close();
};

UIManager.prototype.saveMatchDefault = function(id)
{
	var winId = this.getWindowId(id, "content_manage_match");
	var win = document.getElementById(winId);
	
	var match = this.getMatchFromWindow(win);
	
	// clear references to ID only
	if(match.galaxy)
		match.galaxy = {id: match.galaxy.id};
	
	console.log("saving match '" + id + "' as default");
	console.log(match);
	
	this.saveLocalObject("match_default", match);
};

UIManager.prototype.getWindowId = function(contentId, templateNodeId)
{
	return "win_" + templateNodeId + "_" + contentId;
};

UIManager.prototype.getWindowFromTemplate = function(contentId, templateNodeId, titleKey)
{
	var windowId = this.getWindowId(contentId, templateNodeId);
	var win = document.getElementById(windowId);
	if(win == null)
		win = this.createWindowFromTemplate(contentId, templateNodeId, titleKey);
	// force label update
	this.updateLabels(win);
	
	
	return win;
};

UIManager.prototype.createWindowFromTemplate = function(contentId, templateNodeId, titleKey)
{
	// create the windowId
	var windowId = this.getWindowId(contentId, templateNodeId);
	// clone the template node
	var clone = document.getElementById(templateNodeId).cloneNode(true);
	// replace $ with ID
	this.replacePlaceholder(clone, UI.constants.PLACEHOLDER, contentId, UI.constants.PLACEHOLDER_ATTRIBUTES);
	// create window with the cloned node as the content
	return new Styles.Window(windowId, titleKey, clone);
};

UIManager.prototype.replacePlaceholder = function(element, placeholder, value, attributes)
{
	for(var i = 0; i < attributes.length; i++)
	{
		attributeName = attributes[i];
		if(element.getAttribute(attributes[i]) != null && typeof(element.getAttribute(attributes[i])) == Reflections.type.STRING)
			element.setAttribute(attributes[i], element.getAttribute(attributes[i]).replace(placeholder, value));
	}
	
	for(var e = 0; e < element.children.length; e++)
	{
		this.replacePlaceholder(element.children[e], placeholder, value, attributes);
	}
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

UIManager.prototype.getLabelElement = function(key)
{
	var label = document.createElement("label");
	label.setAttribute("key", key);
	return label;
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

UIManager.prototype.saveLocalObject = function(key, object)
{
	for(var prop in object)
	{
		if(typeof(object[prop]) == Reflections.type.FUNCTION)
		{
			continue;
		}
		else if(typeof(object[prop]) == Reflections.type.OBJECT && object[prop] != null)
		{
			this.saveLocalObject(key + "." + prop, object[prop]);
		}
		else
		{
			console.log("saving local object: " + key + "." + prop + " = " + object[prop]);
			localStorage.setItem(key + "." + prop, object[prop]);
		}
	}
};

UIManager.prototype.loadLocalObject = function(key)
{
	var object = {};
	var keyFound = false;
	for(var prop in localStorage)
	{
		if(prop.startsWith(key + "."))
		{
			var subKey = prop.substring(key.length+1); 
			if(subKey.contains("."))
			{
				var subKey = subKey.substring(0, subKey.indexOf("."));
				object[subKey] = this.loadLocalObject(key + "." + subKey)
			}
			else
			{
				var value = localStorage[prop];
				if(value == "true")
					object[subKey] = true;
				else if(value == "false")
					object[subKey] = false;
				else if(value == "null")
					object[subKey] = null;
				else if(Number(value).toString() == value) // parsing to number successfull
					object[subKey] = Number(value);
				else
					object[subKey] = value;
			}
			keyFound = true;
		}
	}
	if(keyFound)
		return object;
	else
		return null;
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
	var val = (localStorage.getItem(UI.constants.KEY_SHOW_WELCOME) != "false");
	// toggle
	if(toggle)
		val = !val;
	localStorage.setItem(UI.constants.KEY_SHOW_WELCOME, val);

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

// parse the static galaxy info for a match
UIManager.prototype.parseGalaxy = function(json)
{
	var arr;
	eval("arr=" + json);
	var systems = [];
	for(var i = 0; i < arr.length; i++)
	{
		//      					     id		    x		   y		  z		     size		heat
		systems.push(new ViewUtil.System(arr[i][0], arr[i][1], arr[i][2], arr[i][3], arr[i][4], arr[i][5]));
	}
	return new ViewUtil.Galaxy(systems);
};

// parse the system live update list for a match
UIManager.prototype.parseSystems = function(json)
{
	var arr;
	eval("arr=" + json);
	for(var i = 0; i < arr.length; i++)
	{
		// arr[i] = [id		hab		inf		id0		pop0	id1		pop1	...]
		var index = this.view.galaxy.systems.binaryIndexOfId(arr[i][0]);
		if(index >= 0)
		{
			var sys = this.view.galaxy.systems[index];
			sys.habitability.target = arr[i][1];
			sys.maxPopulation.target = sys.habitability.target * sys.size.target * 1e6;
			sys.infrastructure.target = arr[i][2];
			sys.populations.length = 0; // clear populations
			for(var p = 3; p < arr[i].length-1; p += 2)
			{
				sys.populations.push({ empire: arr[i][p], population: arr[i][p+1] });
			}
			// TODO update system info view if system is currently selected
		}
		else
		{
			console.warn("no matching system found with id " + arr[i][0]);
		}
	}
};

UIManager.prototype.doShowMatch = function(request)
{
	console.log(request);
	// parse galaxy
	var galaxy = this.parseGalaxy(request.responseText);
	console.log(galaxy);
	// update 3D-View
	this.view.load(galaxy);
};

UIManager.prototype.updateSelectionInfo = function(system, isTarget, changeType)
{
	var out = [];
	for(var i = 0; i < this.view.galaxy.selections.length; i++)
	{
		if(this.view.galaxy.selections[i])
		{
			out.push(JSON.stringify(this.view.galaxy.selections[i].coords.value));
			if(this.view.galaxy.selections[i].infoElement != null)
				out.push("E");
		}
		else
		{
			out.push("{slot empty}");
		}
	}
	console.log(out.join(" "));
	console.log(changeType + " -> " + JSON.stringify(system.coords.value) + (system.infoElement ? "E" : ""));
	
	
	var info = document.getElementById(UI.constants.MATCH_SYSTEM_INFO_ID);
	if(changeType == ViewUtil.SELECTION_ADD)
	{
		if(system.infoElement) // remove previous element (just to be sure)
		{
			console.warn("previous element found");
			info.removeChild(system.infoElement);
		}

		var element = this.createSystemInfo(system, isTarget);
		system.infoElement = element; // store info element
		if(isTarget)
		{
			// check if at least one other selection is preset -> display action
			if(info.children.length > 0)
			{
				info.insertBefore(this.createSystemAction(), info.children[0]);
				info.insertBefore(element, info.children[0]);
			}
			else
			{
				info.appendChild(element);
			}
		}
		else
		{
			// check if target is already selected and this is first selection -> display action
			if(this.view.galaxy.selections[0] != null && this.view.galaxy.selections[1] == system)
				info.appendChild(this.createSystemAction());
			info.appendChild(element);
		}
		
	}
	else if(changeType == ViewUtil.SELECTION_REMOVE)
	{
		if(system.infoElement) // check just to be sure
			info.removeChild(system.infoElement);
		else
			console.warn("no element found");
		system.infoElement = null; // reset stored info element
		// check if the target was removed -> hide action
		if(isTarget)
		{
			if(info.children.length > 0)
				info.removeChild(info.children[0]);
		}
		// check if last other selection was removed -> hide action
		else if(this.view.galaxy.selections[1] == null) // TODO not working
		{
			if(info.children.length > 1)
				info.removeChild(info.children[info.children.length-1]);
		}
	}
	else // if(changeType == ViewUtil.SELECTION_CHANGE)
	{
		// ignore
	}
	
	this.updateLabels(info);
};

UIManager.prototype.createSystemInfo = function(system, isTarget)
{
	var pos = system.coords.value;
	
	var row = function(cols) {
		var tr = document.createElement("tr");
		var td;
		for(var c = 0; c < cols.length; c++)
		{
			td = document.createElement("td");
			if(cols[c] instanceof HTMLElement || cols[c] instanceof Text)
				td.appendChild(cols[c]);
			else
				td.appendChild(document.createTextNode(cols[c]));
			tr.appendChild(td);
		}
		return tr;
	};

	var div = document.createElement("div");
	var table = document.createElement("table");
	if(isTarget)
	{
		div.classList.add("red_frame");
		table.classList.add("target");
	}
	else
	{
		div.classList.add("frame");
	}
	// system title (coordinates)
	table.appendChild(row(["(\u00A0" + pos.x + "\u00A0|\u00A0" + pos.y + "\u00A0|\u00A0" + pos.z + "\u00A0)"]));
	table.children[0].children[0].colSpan = 3;
	// max population & infrastruction
	table.appendChild(row([this.getLabelElement("system.maxPopulation"),Math.round(system.maxPopulation.value),"+-"]));
	table.appendChild(row([this.getLabelElement("system.infrastructure"),Math.round(system.infrastructure.value),"+-"]));
	// populations
	var username;
	for(var p = 0; p < system.populations.length; p++)
	{
		var index = this.empires.binaryIndexOfId(system.populations[p].empire);
		if(index >= 0)
			username = this.empires[index].player.user.username;
		else
			username = "unknown user";
		table.appendChild(row([username, Math.round(system.populations[p].population),"icons"]));
	}
	// TODO movements
	
	div.appendChild(table);
	return div;
};

UIManager.prototype.createSystemAction = function()
{
	var div = document.createElement("div");
	var button = document.createElement("div");
	button.classList.add("frame");
	button.classList.add("button");
	var buttonA = document.createElement("a");
	buttonA.onclick = function(uiManager) {
		return function() {
			uiManager.showSendPopulation();
		};
	} (this);
	var label = this.getLabelElement("population.send");
	buttonA.appendChild(label);
	button.appendChild(buttonA);
	div.appendChild(button);
	return div;
};

UIManager.prototype.showSendPopulation = function()
{
	var id = new Date().getTime();
	// create a new dialog each time
	var win = this.getWindowFromTemplate(id, "content_match_send_population", "population.send");
	win.setSize(800,200);
	win.center();
	win.setMovable(true);
	win.dialogId = id;
	win.target = this.view.galaxy.selections[0];
	
	var rowId = UI.constants.SEND_ROW_ID.replace(UI.constants.PLACEHOLDER, id);
	var giveupId = UI.constants.SEND_GIVEUP_ID.replace(UI.constants.PLACEHOLDER, id);
	var row;
	var system;
	var ownPop;
	var ownPopHome;
	for(var i = 0; i < ViewUtil.SELECTIONS_MAX-1; i++)
	{
		system = this.view.galaxy.selections[i+1];
		row = document.getElementById(rowId + "_" + i);
		if(system != null)
		{
			console.log("displaying selection #" + i);
			ownPop = 0;
			for(var p = 0; p < system.populations.length; p++)
			{
				if(system.populations[p].empire == this.currentPlayer.empires[0].id)
				{
					ownPop = system.populations[p].population;
					ownPopHome = (p == 0);
					break;
				}
			}
			row.value = system; 
			row.children[0].innerHTML = "(\u00A0" + system.coords.value.x + "\u00A0|\u00A0" + system.coords.value.y + "\u00A0|\u00A0" + system.coords.value.z + "\u00A0)";
			//row.children[1].innerHTML = (ownPopHome ? system.infrastructure.value : 0);
			row.children[1].innerHTML = system.infrastructure.value;
			row.children[2].innerHTML = ownPop;
			row.children[3].children[0].value = ownPop;
			row.children[3].children[0].max = ownPop;
			
			row.giveupSelect = new Select(giveupId + "_" + i);
			this.populateEnumSelect(row.giveupSelect, lang.EnumYesNo, false);
			row.giveupSelect.select(0);

			if(ownPop == 0)
			{
				// TODO disable inputs when ownPop == 0
				row.children[3].children[0].disabled = "true";
				row.giveupSelect.setDisabled(true);
				row.children[5].children[0].disabled = "true";
			}

			// TODO populate window
			
			// calculate arrival time
			this.doSendPopulation(id, "update", i);
		}
		else
		{
			row.classList.add(UI.constants.HIDDEN_CLASS);
		}
	}
	
	// force label update
	this.updateLabels(win);
	// show window
	win.setVisible(true);
};

UIManager.prototype.doSendPopulation = function(id, cmd, rowIndex)
{
	var winId = this.getWindowId(id, "content_match_send_population");
	var win = document.getElementById(winId);

	var rowId = UI.constants.SEND_ROW_ID.replace(UI.constants.PLACEHOLDER, id);
	var availableId = UI.constants.SEND_AVAILABLE_ID.replace(UI.constants.PLACEHOLDER, id);
	var popId = UI.constants.SEND_POP_ID.replace(UI.constants.PLACEHOLDER, id);
	var giveupId = UI.constants.SEND_GIVEUP_ID.replace(UI.constants.PLACEHOLDER, id);
	var speedId = UI.constants.SEND_SPEED_ID.replace(UI.constants.PLACEHOLDER, id);
	
	if(cmd == "giveup_all" || cmd == "giveup_none")
	{
		var index = (cmd == "giveup_all" ? 1 : 0);
		
		var row;
		for(var i = 0; i < ViewUtil.SELECTIONS_MAX-1; i++)
		{
			row = document.getElementById(rowId + "_" + i);
			if(row.giveupSelect && !row.giveupSelect.isDisabled())
			{
				row.giveupSelect.select(index);
			}
		}
	}
	else if(cmd == "arrive_asap")
	{
		var speedInput;
		for(var i = 0; i < ViewUtil.SELECTIONS_MAX-1; i++)
		{
			speedInput = document.getElementById(speedId + "_" + i);
			speedInput.value = 100;
		}
		cmd = "update";
	}
	else if(cmd == "arrive_synced")
	{
		var row;
		var refTime = 0;
		var refIndex;
		var speed;
		var speedInput;
		// determine nearest system
		for(var i = 0; i < ViewUtil.SELECTIONS_MAX-1; i++)
		{
			row = document.getElementById(rowId + "_" + i);
			if(row.value)
			{
//				speedInput = document.getElementById(speedId + "_" + i);
//				speed = Number(speedInput.value);
				speed = 100;
				var travelTime = client.conquestManager.calculateTravelTime(this.currentMatch, row.value, win.target, speed);
				if(travelTime > refTime)
				{
					refTime = travelTime;
					refIndex = i;
				}
			}
		}
		for(var i = 0; i < ViewUtil.SELECTIONS_MAX-1; i++)
		{
			row = document.getElementById(rowId + "_" + i);
			if(row.value)
			{
				var time100 = client.conquestManager.calculateTravelTime(this.currentMatch, row.value, win.target, 100);
				if(time100 < refTime)
					speed = Math.round((100 * time100 / refTime)*10)/10;
				else
					speed = 100;
				speedInput = document.getElementById(speedId + "_" + i);
				speedInput.value = speed;
			}
		}
		cmd = "update";
	}
	
	// do not use else if in order to be able to call update after one of the cases above
	
	if(cmd == "update")
	{
		console.log("winId: " + id);
		console.log("row:   " + rowIndex);

		var row;
		var available = 0;
		var pop = 0;
		var popInput;
		var speed;
		var speedInput;
		var travelTime;
		for(var i = 0; i < ViewUtil.SELECTIONS_MAX-1; i++)
		{
			row = document.getElementById(rowId + "_" + i);
			if(row.value)
			{
				available += Number(row.children[2].innerHTML);
				popInput = document.getElementById(popId + "_" + i);
				pop += Number(popInput.value);
				speedInput = document.getElementById(speedId + "_" + i);
				speed = Number(speedInput.value);
				
				travelTime = client.conquestManager.calculateTravelTime(this.currentMatch, row.value, win.target, speed);
				row.children[6].innerHTML = "T -" + travelTime + " ms";
			}
		}
		
		console.log("available = " + available);
		console.log("selected  = " + pop);
		
		document.getElementById(availableId + "_total").innerHTML = available;
		document.getElementById(popId + "_total").innerHTML = pop;
	}
	else // if(cmd == "send")
	{
		// TODO submit
		
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

ConquestManager = function()
{
};

// this is the only relevant client function!
//public void update(String channel, Object value)
ConquestManager.prototype.update = function(channel, value)
{
	console.log("received updated for channel '" + channel + "': " + value);

	if(value == null)
		return; // value can be null on server restart!
	
	if(channel == UI.constants.CHANNEL_MATCH_CREATED)
	{
		// a new match was created -> add it to the match list
		console.log("add new match to select: " + value.id);
		var select = client.uiManager.matchSelect;
		var option = {};
		option.value = value;
		select.options[select.options.length] = option;
		// update DOM element
		select.update();
		// update labels
		client.uiManager.updateLabels(select.element);
		
		// preload match
		server.entityManager.loadProperty(value, "participants.empire.player.user", function(match) {
			console.log("match participants loaded!")
		});
		server.entityManager.loadProperty(value, "creator.user", function(match) {
			console.log("match creator loaded!");
		});
	}
	else if(channel == UI.constants.CHANNEL_MATCH_SYSTEMS)
	{
		// update for the system populations and infrastructures received
		client.uiManager.parseSystems(value);
	}
	
	// TODO other channels
};

ConquestManager.prototype.calculateMaxTravelDistance = function(origin, movedPopulation, exodus)
{
	var travelMaxFactor = UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR;
	var travelExodusFactor = UniverseConquestConstants.PARAM_TRAVEL_EXODUS_FACTOR;
	var maxPopulation =   UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX
						* UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX
						* UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR;

	var infrastructure = origin.infrastructure.value;
	if(infrastructure == 0)
		infrastructure = 1;

	var dist = Math.log10(infrastructure / movedPopulation);

	if(exodus)
		dist = (dist / Math.log10(maxPopulation) + 1) * travelExodusFactor / 2;
	else
		dist = (dist / Math.log10(maxPopulation) + 1) * travelMaxFactor / 2;

	return dist * this.currentMatch.galaxy.maxGap;
};

ConquestManager.prototype.calculateMaxMovablePopulation = function(origin, travelDistance)
{
	var travelMaxFactor = UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR;
	var maxPopulation =   UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX
						* UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX
						* UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR;

	var pop = (travelDistance / this.currentMatch.galaxy.maxGap) * 2 / travelMaxFactor - 1;
	pop = origin.infrastructure.value / Math.pow(10, pop * Math.log10(maxPopulation));

	// TODO get population for the current player only!
//	if(pop > origin.getPopulation())
//		pop = origin.getPopulation();

	return Math.floor(pop);
};

ConquestManager.prototype.calculateTravelTime = function(match, origin, target, travelSpeed)
{
	// see Calculator#calculateTravelTime
	var stdTravelTime = this.calculateStandardTravelTime(match, travelSpeed);
	
	var p1 = origin.coords.value;
	var p2 = target.coords.value;
	var dist = Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y) + (p1.z-p2.z)*(p1.z-p2.z));
	
	var stdDist = match.galaxy.maxGap;

	return Math.round((dist / stdDist) * stdTravelTime); 
};

ConquestManager.prototype.calculateStandardTravelTime = function(match, travelSpeed)
{
	// see Calculator#calculateStandardTravelTime
	var CORRECTION_TRAVEL_TIME = 100000;
//	var minSpeed = UniverseConquestConstants.PARAM_TRAVEL_SPEED_MIN;
//	var maxSpeed = UniverseConquestConstants.PARAM_TRAVEL_SPEED_MAX;
	var facBuild = UniverseConquestConstants.PARAM_FACTOR_BUILD;
	var travelTimeFactor = UniverseConquestConstants.PARAM_TRAVEL_TIME_FACTOR;
	var maxPopulation =   UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX
						* UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX
						* UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR;

//	if(travelSpeed < minSpeed || travelSpeed > maxSpeed)
//		throw new Error("travelSpeed out of bounds: [" + minSpeed + ", " + maxSpeed + "]");

	var speedFac = Math.pow(10, match.speed);
	
	var timeBase = maxPopulation / facBuild;
	var travelTime = travelTimeFactor * timeBase / CORRECTION_TRAVEL_TIME;
	travelTime /= travelSpeed; // on client side speed already is given in percent
//	travelTime /= (travelSpeed / maxSpeed); // speed in percent
	travelTime /= speedFac; // match speed

	return travelTime;		
};






// TODO for debugging only

UIManager.prototype.randomPops = function()
{
	var playerIDs = [1, 10, 20];
	var pops = [];
	var sys;
	var sysPops;
	for(var s = 0; s < this.view.galaxy.systems.length; s++)
	{
		sys = this.view.galaxy.systems[s];
		sysPops = [sys.id];
		var newMaxValue = sys.maxPopulation.value + Math.round((Math.random()*2-1)*1e10);
		if(newMaxValue < 0)
			newMaxValue = 0;
		else if(newMaxValue > 1e12)
			newMaxValue = 1e12;
		sysPops.push(newMaxValue);
		var newInfValue = sys.infrastructure.value + Math.round((Math.random()*2-1)*1e10);
		if(newInfValue < 0)
			newInfValue = 0;
		else if(newInfValue > 1e12)
			newInfValue = 1e12;
		sysPops.push(newInfValue);
		var presentPlayers = [];
		var newPopValue;
		// update present players
		for(var i = 0; i < sys.populations.length; i++)
		{
			presentPlayers.push(sys.populations[i].empire);
			newPopValue = sys.populations[i].population + Math.round((Math.random()*2-1)*1e10);
			if(newPopValue > 1e12)
				newPopValue = 1e12;
			if(newPopValue > 0)
			{
				sysPops.push(sys.populations[i].empire);
				sysPops.push(newPopValue);
			}
		}
		// randomly add new players
		for(var i = 0; i < playerIDs.length; i++)
		{
			if(presentPlayers.indexOf(playerIDs[i]) == -1)
			{
				newPopValue = Math.round((Math.random()*2-1)*1e10);
				if(newPopValue > 0)
				{
					sysPops.push(playerIDs[i]);
					sysPops.push(newPopValue);
				}
			}
		}
		pops.push(sysPops);
	}
	return pops;
};

UIManager.prototype.randomPopUpdate = function()
{
	var pops = this.randomPops();
	var json = JSON.stringify(pops);
	client.conquestManager.update(UI.constants.CHANNEL_MATCH_SYSTEMS, json);
};