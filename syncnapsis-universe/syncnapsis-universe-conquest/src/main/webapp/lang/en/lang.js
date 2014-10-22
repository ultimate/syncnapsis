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
var lang = {};

lang.current = "EnumLocale.EN";
lang.unicode_test = "ÄäÖöÜüßÁáÀàÂâÉéÈèÊêÍíÌìÎîÓóÒòÔôÚúÙùÛû";

lang.EnumLocale = {};
lang.EnumLocale.EN = "English";
lang.EnumLocale.DE = "Deutsch";

lang.EnumGender = {};
lang.EnumGender.female 		= "female";
lang.EnumGender.machine 	= "machine";
lang.EnumGender.male		= "male";
lang.EnumGender.transsexual	= "transsexual";
lang.EnumGender.unknown		= "unknown";

lang.EnumMatchState = {};
lang.EnumMatchState.planned 	= "planned";
lang.EnumMatchState.active 		= "active";
lang.EnumMatchState.finished	= "finished";
lang.EnumMatchState.canceled	= "canceled";

lang.error = {};
lang.error.error				= "Error";
lang.error.email_exists 		= lang.error.error + ": e-mail-address is already registered!";
lang.error.invalid_email 		= lang.error.error + ": invalid e-mail-address!";
lang.error.invalid_empirename 	= lang.error.error + ": invalid empirename!";
lang.error.invalid_username 	= lang.error.error + ": invalid username!";
lang.error.max_empires 			= lang.error.error + ": Maximum number of empires exceeded!";
lang.error.no_password			= lang.error.error + ": no password provided!";
lang.error.password_mismatch 	= lang.error.error + ": passwords do not match!";
lang.error.username_exists 		= lang.error.error + ": username is already registered!";

lang.general = {};
lang.general.apply_changes		= "Apply changes";
lang.general.prefix				= "Starting with";
lang.general.save_as_default	= "Save as default";
lang.general.suffix				= "Ending with";

lang.match = {};
lang.match.creator				= "Creator";
lang.match.galaxy				= "Galaxy";
lang.match.participant			= "Participant";
lang.match.participants			= "Participants";
lang.match.state				= "Status";
lang.match.title				= "Title";

lang.message = {};
lang.message.error						= "An error occurred! Please try again...";
lang.message.email_update				= "E-mail-address update requested! Please follow the instruction in the e-mail sent to you...";
lang.message.email_update_failure		= "E-mail-address update failed! Illegal e-mail-address?";
lang.message.password_change			= "Password successfully changed!";
lang.message.password_change_failure	= "Password change failed! Invalid password?";
lang.message.password_reset				= "Password reset requested! Please follow the instruction in the e-mail sent to you...";
lang.message.password_reset_failure		= "Password reset failed! User and/or e-mail-address invalid...";
lang.message.register					= "Registration successful! You will be logged in automatically...";

lang.menu = {};
lang.menu.about 				= "?";
lang.menu.about_tooltip 		= "About syncnapsis";
lang.menu.account_status		= "Account status";
lang.menu.cancel				= "Cancel";
lang.menu.contact 				= "Contact";
lang.menu.contact_tooltip 		= "Contact informationen / Site notice";
lang.menu.create 				= "Create";
lang.menu.disclaimer 			= "Legal Stuff";
lang.menu.disclaimer_tooltip 	= "Site policy / Disclaimer";
lang.menu.email					= "E-mail-address";
lang.menu.forgot_password		= "Forgot password?";
lang.menu.login 				= "Login";
lang.menu.login_info			= "Hello";
lang.menu.logout 				= "Logout";
lang.menu.match					= "Match info";
lang.menu.match_administrate	= "Administrate match";
lang.menu.match_cancel			= "Cancel match";
lang.menu.match_create 			= "Create new match";
lang.menu.match_filter			= "Filter matches";
lang.menu.match_plan			= "Create match and start later";
lang.menu.match_reset_filters	= "Reset filters";
lang.menu.match_select			= "Match selection";
lang.menu.match_start			= "Start match immediately";
lang.menu.news 					= "News";
lang.menu.password				= "Password";
lang.menu.pinboard 				= "Pinboard";
lang.menu.pinboard_tooltip		= lang.menu.pinboard;
lang.menu.profile 				= "Profile";
lang.menu.quick					= "Quick nav";
lang.menu.register 				= "Register";
lang.menu.reset_password		= "Reset password";
lang.menu.security				= "Security settings";
lang.menu.start					= "Start";
lang.menu.stats 				= "Stats";

lang.log = {};
lang.log.title					= "Event-Log";
lang.log.pinboard				= "Pinboard";
lang.log.match					= "Match";

lang.participant = {};
lang.participant.name			= "Playername";
lang.participant.rank			= "Rank";
lang.participant.rankValue		= "%";
lang.participant.rankRawValue	= "Points";

lang.profile = {};
lang.profile.accountStatus		= "Status";
lang.profile.accountStatusExpireDate = "Status expires";
lang.profile.birthday			= "Birthday";
lang.profile.dateFormat			= "Date format";
lang.profile.email				= "E-mail";
lang.profile.gender				= "Gender";
lang.profile.password			= "Password";
lang.profile.password_confirm	= "Repeat " + lang.profile.password;
lang.profile.password_new		= "New " + lang.profile.password;
lang.profile.password_old		= "Old " + lang.profile.password;
lang.profile.registrationDate	= "Registered since";
lang.profile.timeZoneID			= "Timezone";
lang.profile.username			= "Username";

lang.profile.change_password	= "Change " + lang.profile.password;
lang.profile.change_email		= "Change " + lang.profile.email;

lang.welcome = {};
lang.welcome.headLine			= "News:";
lang.welcome.showOnLoad			= "Show this window on start?";
lang.welcome.title				= "Welcome to syncnapsis";
lang.welcome.toggle1_active		= lang.menu.register + "!";
lang.welcome.toggle1_inactive	= "New here?";
lang.welcome.toggle2_active		= lang.menu.about_tooltip + "!";
lang.welcome.toggle2_inactive	= "Curious?";
