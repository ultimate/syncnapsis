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

lang.current = "EnumLocale.DE";
lang.unicode_test = "ÄäÖöÜüßÁáÀàÂâÉéÈèÊêÍíÌìÎîÓóÒòÔôÚúÙùÛû";

lang.EnumLocale = {};
lang.EnumLocale.EN = "English";
lang.EnumLocale.DE = "Deutsch";

lang.error = {};
lang.error.error				= "Fehler";
lang.error.email_exists 		= lang.error.error + ": e-Mail-Adresse ist bereits registriert!";
lang.error.invalid_email 		= lang.error.error + ": ungültige e-Mail-Adresse!";
lang.error.invalid_empirename 	= lang.error.error + ": ungültiger Imperiumsname!";
lang.error.invalid_username 	= lang.error.error + ": ungültiger Benutzername!";
lang.error.max_empires 			= lang.error.error + ": Überschreitung der maximalen Anzahl an Imperien!";
lang.error.no_password			= lang.error.error + ": kein Passwort angegeben!";
lang.error.password_mismatch 	= lang.error.error + ": Nicht-Übereinstimmung der Passwörter!";
lang.error.username_exists 		= lang.error.error + ": Benutzername ist bereits registriert!";

lang.message = {};
lang.message.register			= "Registrierung erfolgreich! Du wirst automatisch eingeloggt...";

lang.menu = {};
lang.menu.about 				= "?";
lang.menu.about_tooltip 		= "Über syncnapsis";
lang.menu.cancel				= "Abbrechen";
lang.menu.contact 				= "Kontakt";
lang.menu.contact_tooltip 		= "Kontaktinformationen / Impressum";
lang.menu.disclaimer 			= "Rechtliches";
lang.menu.disclaimer_tooltip 	= "Nutzungsbedingungen / Haftungsbeschränkung";
lang.menu.forgot_password		= "Passwort vergessen?";
lang.menu.login 				= "Anmelden";
lang.menu.login_info			= "Hallo";
lang.menu.logout 				= "Abmelden";
lang.menu.news 					= "Neuigkeiten";
lang.menu.pinboard 				= "Pinnwand";
lang.menu.pinboard_tooltip		= lang.menu.pinboard;
lang.menu.profile 				= "Profil";
lang.menu.register 				= "Registrieren";
lang.menu.stats 				= "Statistik";

lang.log = {};
lang.log.title					= "Ereignis-Log";
lang.log.pinboard				= "Pinnwand";
lang.log.match					= "Spiel";

lang.profile = {};
lang.profile.email				= "E-Mail";
lang.profile.password			= "Passwort";
lang.profile.password_confirm	= lang.profile.password + " wiederholen";
lang.profile.username			= "Benutzername";

lang.welcome = {};
lang.welcome.headLine			= "Neuigkeiten:";
lang.welcome.showOnLoad			= "Dieses Fenster beim Start anzeigen?";
lang.welcome.title				= "Willkommen bei syncnapsis";
lang.welcome.toggle1_active		= lang.menu.register + "!";
lang.welcome.toggle1_inactive	= "Neu hier?";
lang.welcome.toggle2_active		= lang.menu.about_tooltip + "!";
lang.welcome.toggle2_inactive	= "Neugierig?";



