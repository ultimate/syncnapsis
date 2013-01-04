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

UI.constants.LOCALE_CHOOSER_ID = "locale_chooser";
UI.constants.LOCALE_LABEL_TAGNAME = "label";
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
	this.layout_bar_bottom = new Styles.FillLayout([ "bar_bottom_left", "bar_bottom_center", "bar_bottom_right" ], [ UI.constants.BAR_OUTER_WIDTH, null, UI.constants.BAR_OUTER_WIDTH ],
			Styles.layout.HORIZONTAL);

	Events.fireEvent(window, Events.ONRESIZE);

	this.onLogout();
	this.updateLabels();
	this.populateLocaleChooser();
};

UIManager.prototype.onLogin = function(player)
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
};

UIManager.prototype.onLogout = function(success)
{
	document.getElementById("bar_top_left_loggedin").style.display = "none";
	document.getElementById("bar_top_right_loggedin").style.display = "none";
	document.getElementById("bar_top_left_loggedout").style.display = "block";
	document.getElementById("bar_top_right_loggedout").style.display = "block";
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
	var elements = document.getElementsByTagName(UI.constants.LOCALE_LABEL_TAGNAME);
	for( var i = 0; i < elements.length; i++)
	{
		elements[i].innerHTML = this.getString(elements[i].getAttribute(UI.constants.LOCALE_KEY_ATTRIBUTE));
	}
};

UIManager.prototype.selectLocale = function(selection)
{
	// stub for server-entity
};