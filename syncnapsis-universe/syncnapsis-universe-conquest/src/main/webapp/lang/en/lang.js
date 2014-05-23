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
var lang = {};

lang.current = "EnumLocale.EN";

lang.EnumLocale = {};
lang.EnumLocale.EN = "English";
lang.EnumLocale.DE = "Deutsch";

lang.error = {};
lang.error.error				= "Error";
lang.error.email_exists 		= lang.error + ": e-mail-address is already registered!";
lang.error.invalid_email 		= lang.error + ": invalid e-mail-address!";
lang.error.invalid_empirename 	= lang.error + ": invalid empirename!";
lang.error.invalid_username 	= lang.error + ": invalid username!";
lang.error.max_empires 			= lang.error + ": Maximum number of empires exceeded!";
lang.error.password_mismatch 	= lang.error + ": passwords do not match!";
lang.error.username_exists 		= lang.error + ": username is already registered!";

lang.menu = {};
lang.menu.about 				= "?";
lang.menu.about_tooltip 		= "About Syncnapsis";
lang.menu.cancel				= "Cancel";
lang.menu.contact 				= "Contact";
lang.menu.contact_tooltip 		= "Contact informationen / Site notice"
lang.menu.disclaimer 			= "Legal Stuff";
lang.menu.disclaimer_tooltip 	= "Site policy / Disclaimer";
lang.menu.forgot_password		= "Forgot password?";
lang.menu.login    				= "Login";
lang.menu.login_info			= "Hello";
lang.menu.logout   				= "Logout";
lang.menu.news					= "News";
lang.menu.pinboard 				= "Pinboard";
lang.menu.pinboard_tooltip		= lang.menu.pinboard;
lang.menu.profile 				= "Profile";
lang.menu.register 				= "Register";
lang.menu.stats					= "Stats";

lang.log = {};
lang.log.title					= "Event-Log";
lang.log.pinboard				= "Pinboard";
lang.log.match					= "Match";
	
lang.profile = {};
lang.profile.email				= "E-mail";
lang.profile.password			= "Password";
lang.profile.password_confirm	= "Repeat " + lang.profile.password;
lang.profile.username			= "Username";

lang.welcome = {};
lang.welcome.headLine			= "News:";
lang.welcome.showOnLoad			= "Show this window on start?";
lang.welcome.title				= "Welcome to syncnapsis";
lang.welcome.toggle1_active		= lang.menu.register + "!";
lang.welcome.toggle1_inactive	= "New here?";
lang.welcome.toggle2_active		= lang.menu.about_tooltip + "!";
lang.welcome.toggle2_inactive	= "Curious?";
