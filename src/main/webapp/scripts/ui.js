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
//@requires("Styles")
//@requires("Events")
//@requires("Locale")

var UI = {};

UI.constants = {};
UI.constants.AD_WIDTH = 170;
UI.constants.AD_BORDER = 2;
UI.constants.BAR_HEIGHT = 24;
UI.constants.BAR_OUTER_WIDTH = 300;

UI.init = function()
{
	UI.layout_horizontal = new Styles.FillLayout([ "ad_left", "center", "ad_right" ], [ UI.constants.AD_WIDTH, null, UI.constants.AD_WIDTH ], Styles.layout.HORIZONTAL);
	UI.layout_vertical = new Styles.FillLayout([ "bar_top", "content", "bar_bottom" ], [ UI.constants.BAR_HEIGHT, null, UI.constants.BAR_HEIGHT ], Styles.layout.VERTICAL);
	
	UI.layout_ad_left = new Styles.FillLayout([ "ad_left_inner", "ad_left_border" ], [ null, UI.constants.AD_BORDER ], Styles.layout.HORIZONTAL);
	UI.layout_ad_right = new Styles.FillLayout([ "ad_right_border", "ad_right_inner" ], [ UI.constants.AD_BORDER, null ], Styles.layout.HORIZONTAL);
	
	UI.layout_bar_top = new Styles.FillLayout([ "bar_top_left", "bar_top_center", "bar_top_right" ], [ UI.constants.BAR_OUTER_WIDTH, null, UI.constants.BAR_OUTER_WIDTH ], Styles.layout.HORIZONTAL);
	UI.layout_bar_bottom = new Styles.FillLayout([ "bar_bottom_left", "bar_bottom_center", "bar_bottom_right" ], [ UI.constants.BAR_OUTER_WIDTH, null, UI.constants.BAR_OUTER_WIDTH ], Styles.layout.HORIZONTAL);
	
	Events.fireEvent(window, Events.ONRESIZE);
	
	UI.onLogout();
	Locale.update();
	Locale.populateChooser();
};

UI.onLogin = function(player)
{
	// TODO change language to users selection
	document.getElementById("bar_top_left_loggedin").style.display = "block";
	document.getElementById("bar_top_right_loggedin").style.display = "block";
	document.getElementById("bar_top_left_loggedout").style.display = "none";
	document.getElementById("bar_top_right_loggedout").style.display = "none";
};

UI.onLogout = function(success)
{
	document.getElementById("bar_top_left_loggedin").style.display = "none";
	document.getElementById("bar_top_right_loggedin").style.display = "none";
	document.getElementById("bar_top_left_loggedout").style.display = "block";
	document.getElementById("bar_top_right_loggedout").style.display = "block";
};