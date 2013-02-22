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
var UI = {};

UI.constants = {};
UI.constants.AD_WIDTH = 170;
UI.constants.AD_BORDER = 2;
UI.constants.BAR_HEIGHT = 24;
UI.constants.BAR_OUTER_WIDTH = 300;

UI.constants.LABEL_ID_PLAYERNAME = "bar_top_playername";
UI.constants.OVERLAY_ID = "overlay";

UI.constants.LOCALE_CHOOSER_ID = "locale_chooser";
UI.constants.LOCALE_LABEL_TAGNAME = "label";
UI.constants.LOCALE_BUTTON_TAGNAME = "input";
UI.constants.LOCALE_KEY_ATTRIBUTE = "key";
UI.constants.LOCALE_STRING_VARIABLE = "lang";

UIManager = function()
{
	this.currentPlayer = null;

	this.localeChooser = document.getElementById(UI.constants.LOCALE_CHOOSER_ID);

	this.layout_horizontal = new Styles.FillLayout([ "ad_left", "center", "ad_right" ], [ UI.constants.AD_WIDTH, null, UI.constants.AD_WIDTH ], Styles.layout.HORIZONTAL);
	this.layout_vertical = new Styles.FillLayout([ "bar_top", "content", "bar_bottom" ], [ UI.constants.BAR_HEIGHT, null, UI.constants.BAR_HEIGHT ], Styles.layout.VERTICAL);

	this.layout_ad_left = new Styles.FillLayout([ "ad_left_inner", "ad_left_border" ], [ null, UI.constants.AD_BORDER ], Styles.layout.HORIZONTAL);
	this.layout_ad_right = new Styles.FillLayout([ "ad_right_border", "ad_right_inner" ], [ UI.constants.AD_BORDER, null ], Styles.layout.HORIZONTAL);

	this.layout_bar_top = new Styles.FillLayout([ "bar_top_left", "bar_top_center", "bar_top_right" ], [ UI.constants.BAR_OUTER_WIDTH, null, UI.constants.BAR_OUTER_WIDTH ], Styles.layout.HORIZONTAL);
	this.layout_bar_bottom = new Styles.FillLayout([ "bar_bottom_left", "bar_bottom_center", "bar_bottom_right" ], [ UI.constants.BAR_OUTER_WIDTH, null, UI.constants.BAR_OUTER_WIDTH ], Styles.layout.HORIZONTAL);

	this.window_login = new Styles.Window("login", "menu.login", "content_login");
	this.window_login.setSize(300, 200);
	this.window_login.center();
	
	this.window_register = null; // TODO

	Events.fireEvent(window, Events.ONRESIZE);

	this.onLogout();
	this.updateLabels();
	this.populateLocaleChooser();
	this.hideOverlay();
};

UIManager.prototype.onLogin = function(player)
{
	if(player != null)
	{		
		// store the current player
		this.currentPlayer = player;
		// set the player name in the top bar
		document.getElementById(UI.constants.LABEL_ID_PLAYERNAME).innerHTML = player.user.username;
		// change language to users selection
		server.uiManager.selectLocale(player.user.locale);
		// switch ui to logged-in menu
		document.getElementById("bar_top_left_loggedin").style.display = "block";
		document.getElementById("bar_top_right_loggedin").style.display = "block";
		document.getElementById("bar_top_left_loggedout").style.display = "none";
		document.getElementById("bar_top_right_loggedout").style.display = "none";
		
		this.hideLogin();
	}
	else
	{
		this.showErrorMessage(null, document.getElementById("message_login_failed"));
	}
};

UIManager.prototype.onLogout = function(success)
{
	document.getElementById("bar_top_left_loggedin").style.display = "none";
	document.getElementById("bar_top_right_loggedin").style.display = "none";
	document.getElementById("bar_top_left_loggedout").style.display = "block";
	document.getElementById("bar_top_right_loggedout").style.display = "block";
};

UIManager.prototype.hideOverlay = function()
{
	var overlay = document.getElementById(UI.constants.OVERLAY_ID);
	overlay.className += " hidden";
};

UIManager.prototype.populateLocaleChooser = function()
{
	// clear children
	while(this.localeChooser.hasChildNodes())
	{
		this.localeChooser.removeChild(this.localeChooser.lastChild);
	}
	// add options
	var option;
	for( var i in lang.EnumLocale)
	{
		if(typeof(lang.EnumLocale[i]) == Reflections.type.FUNCTION)
			continue;
		option = document.createElement("option");
		option.value = i;
		option.text = lang.EnumLocale[i];
		if("EnumLocale." + i == lang.current)
		{
			option.setAttribute("selected", "selected");
		}
		this.localeChooser.appendChild(option);
	}
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

UIManager.prototype.updateLabels = function()
{
	var elements;
	// labels
	elements = document.getElementsByTagName(UI.constants.LOCALE_LABEL_TAGNAME);
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

UIManager.prototype.showLogin = function()
{
	this.window_login.setVisible(true);
};

UIManager.prototype.hideLogin = function()
{
	this.window_login.setVisible(false);
};

UIManager.prototype.doLogin = function()
{
	var username = document.getElementById("login_username").value;
	var password = document.getElementById("login_password").value;
	if(username == "" || username == null || password == "" || password == null)
	{
		if(username == "" || username == null)
			this.showErrorMessage(document.getElementById("login_username"), document.getElementById("message_login_invalid_username"));
		if(password == "" || password == null)
			this.showErrorMessage(document.getElementById("login_password"), document.getElementById("message_login_invalid_password"));
		return;
	}
	console.log("login as: " + username + ":" + password);
	server.playerManager.login(username, password);
};

UIManager.prototype.showRegister = function()
{
	this.window_register.setVisible(true);
};

UIManager.prototype.hideRegister = function()
{
	this.window_register.setVisible(false);
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

UIManager.prototype.showErrorMessage = function(textfield, messagefield)
{
	if(textfield != null)
	{
		var bg = textfield.style.background;
		textfield.style.background = "#FFEE88";
		setTimeout(function() {textfield.style.background = bg;}, 3000);
	}
	if(messagefield != null)
	{
		var disp = messagefield.style.display;
		messagefield.style.display = "none";
		setTimeout(function() {messagefield.style.display = disp;}, 3000);
	}
};

ServerUIManager = function()
{
};

ServerUIManager.prototype.selectLocale = function(selection)
{
	// stub for server-entity
};