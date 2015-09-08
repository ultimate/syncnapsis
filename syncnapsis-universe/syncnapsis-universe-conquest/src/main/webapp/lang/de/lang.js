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

lang.current = "EnumLocale.DE";
lang.unicode_test = "ÄäÖöÜüßÁáÀàÂâÉéÈèÊêÍíÌìÎîÓóÒòÔôÚúÙùÛû";

lang.EnumLocale = {};
lang.EnumLocale.EN = "English";
lang.EnumLocale.DE = "Deutsch";

lang.EnumEnabled = {};
lang.EnumEnabled.value0		= "Deaktiviert";
lang.EnumEnabled.value1		= "Aktiviert";

lang.EnumDifficulty_domination = {};
lang.EnumDifficulty_domination.value40		= "40% (Sehr einfach)";
lang.EnumDifficulty_domination.value50		= "50% (Einfach)";
lang.EnumDifficulty_domination.value60		= "60% (Normal)";
lang.EnumDifficulty_domination.value70		= "70% (Schwer)";
lang.EnumDifficulty_domination.value80		= "80% (Sehr schwer)";
lang.EnumDifficulty_domination.custom		= "Benutzerdefiniert";
lang.EnumDifficulty_extermination = {};
lang.EnumDifficulty_extermination.value80	= "80% (Sehr einfach)";
lang.EnumDifficulty_extermination.value90	= "90% (Einfach)";
lang.EnumDifficulty_extermination.value95	= "95% (Normal)";
lang.EnumDifficulty_extermination.value99	= "99% (Schwer)";
lang.EnumDifficulty_extermination.value100	= "100% (Sehr schwer)";
lang.EnumDifficulty_extermination.custom	= "Benutzerdefiniert";
lang.EnumDifficulty_vendetta = {};
lang.EnumDifficulty_vendetta.value 			= "automatisch";

lang.EnumGender = {};
lang.EnumGender.female 		= "weiblich";
lang.EnumGender.machine 	= "Maschine";
lang.EnumGender.male		= "männlich";
lang.EnumGender.transsexual	= "transsexuell";
lang.EnumGender.unknown		= "unbekannt";

lang.EnumJoinType = {};
lang.EnumJoinType.invitationsOnly 	= "Nur per Einladung";
lang.EnumJoinType.joiningEnabled	= "Jeder";
lang.EnumJoinType.none				= "Niemand";

lang.EnumManually = {};
lang.EnumManually.value0	= "Automatisch";
lang.EnumManually.value1	= "Manuell";

lang.EnumMatchSpeed = {}; // no true enum; for usability only
lang.EnumMatchSpeed.value0		= "Zeitlupe (x1)";
lang.EnumMatchSpeed.value1 		= "Entspannt (x10)";
lang.EnumMatchSpeed.value2 		= "Normal (x100)";
lang.EnumMatchSpeed.value3 		= "Hektisch (x1k)";
lang.EnumMatchSpeed.value4		= "Zeiraffer (x10k)";
lang.EnumMatchSpeed.value5		= "Lichtgeschwindigkeit (x100k)";

lang.EnumMatchState = {};
lang.EnumMatchState.planned 	= "geplant";
lang.EnumMatchState.active 		= "aktiv";
lang.EnumMatchState.finished	= "beendet";
lang.EnumMatchState.canceled	= "abgebrochen";

lang.EnumPopulationPriority = {};
lang.EnumPopulationPriority.balanced		= "ausbalanciert";
lang.EnumPopulationPriority.infrastructure	= "Infrastruktur";
lang.EnumPopulationPriority.population		= "Bevölkerung";

lang.EnumStartCondition = {};
lang.EnumStartCondition.immediately	= "sofort";
lang.EnumStartCondition.manually 	= "manuell";
lang.EnumStartCondition.planned		= "geplant";

lang.EnumVictoryCondition = {};
lang.EnumVictoryCondition.domination 	= "Vorherrschaft";
lang.EnumVictoryCondition.extermination	= "Vernichtung";
lang.EnumVictoryCondition.vendetta		= "Vendetta";

lang.EnumYesNo = {};
lang.EnumYesNo.value0		= "Nein";
lang.EnumYesNo.value1		= "Ja";

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

lang.general = {};
lang.general.apply_changes		= "Änderungen übernehmen";
lang.general.max				= "max.";
lang.general.min				= "min.";
lang.general.prefix				= "Beginnend mit";
lang.general.save_as_default	= "Als Standard speichern";
lang.general.suffix				= "Endend mit";

lang.match = {};
lang.match.creator						= "Ersteller";
lang.match.galaxy						= "Galaxie";
lang.match.participant					= "Mitspieler";
lang.match.participant_selection		= "Mitspieler-Auswahl";
lang.match.participants					= "Mitspieler";
lang.match.participants_count			= "Mitspieler-Anzahl";
lang.match.plannedJoinType				= "Beitritt (vor Start)";
lang.match.speed						= "Geschwindigkeit";
lang.match.seed							= "Seed";
lang.match.startCondition				= "Start-Bedingung";
lang.match.startDate					= "Start-Datum";
lang.match.startedJoinType				= "Beitritt (laufend)";
lang.match.startPopulation				= "Start-Bevölkerung";
lang.match.startSystemCount				= "Anzahl Start-Systeme";
lang.match.startSystemSelectionEnabled	= "Start-System-Auswahl";
lang.match.state						= "Status";
lang.match.title						= "Titel";
lang.match.victoryCondition				= "Sieg-Bedingung";
lang.match.victoryParameter				= "Sieg-Parameter";

lang.message = {};
lang.message.error						= "Es ist ein Fehler aufgetreten! Bitte versuche es erneut...";
lang.message.email_update				= "E-Mail-Adress-Änderung angefordert! Bitte folge den Anweisungen in der die zugesendeten e-Mail...";
lang.message.email_update_failure		= "E-Mail-Adress-Änderung fehlgeschlagen! E-Mail-Adresse ungültig?";
lang.message.password_change			= "Passwort erfolgreich geändert!";
lang.message.password_change_failure	= "Passwort-Änderung fehlgeschlagen! Ungültiges Password?";
lang.message.password_reset				= "Passwort-Zurücksetzen angefordert! Bitte folge den Anweisungen in der die zugesendeten e-Mail...";
lang.message.password_reset_failure		= "Passwort-Zurücksetzen fehlgeschlagen! Benuter und/oder e-Mail-Adresse ungültig...";
lang.message.register					= "Registrierung erfolgreich! Du wirst automatisch eingeloggt...";

lang.menu = {};
lang.menu.about 				= "?";
lang.menu.about_tooltip 		= "Über syncnapsis";
lang.menu.account_status		= "Konto-Status";
lang.menu.cancel				= "Abbrechen";
lang.menu.contact 				= "Kontakt";
lang.menu.contact_tooltip 		= "Kontaktinformationen / Impressum";
lang.menu.create 				= "Erstellen";
lang.menu.disclaimer 			= "Rechtliches";
lang.menu.disclaimer_tooltip 	= "Nutzungsbedingungen / Haftungsbeschränkung";
lang.menu.email					= "E-Mail-Adresse";
lang.menu.forgot_password		= "Passwort vergessen?";
lang.menu.login 				= "Anmelden";
lang.menu.login_info			= "Hallo";
lang.menu.logout 				= "Abmelden";
lang.menu.match					= "Spiel-Info";
lang.menu.match_administrate	= "Spiel verwalten";
lang.menu.match_cancel			= "Spiel abbrechen";
lang.menu.match_create 			= "Neues Spiel erstellen";
lang.menu.match_discard			= "Spiel verwerfen";
lang.menu.match_filter			= "Spiele filtern";
//lang.menu.match_plan			= "Spiel anlegen und später starten";
lang.menu.match_reset_filters	= "Filter zurücksetzen";
lang.menu.match_select			= "Spiel-Auswahl";
//lang.menu.match_start			= "Spiel sofort starten";
lang.menu.match_start_now		= "Spiel jetzt starten";
lang.menu.news 					= "Neuigkeiten";
lang.menu.password				= "Passwort"
lang.menu.pinboard 				= "Pinnwand";
lang.menu.pinboard_tooltip		= lang.menu.pinboard;
lang.menu.profile 				= "Profil";
lang.menu.quick					= "Schnell-Navigation"; 
lang.menu.register 				= "Registrieren";
lang.menu.reset_password		= "Passwort zurücksetzen";
lang.menu.security				= "Sicherheits-Einstellungen";
lang.menu.start					= "Starten";
lang.menu.stats 				= "Statistik";

lang.number = {};
lang.number.hundret 	= "Hundert";
lang.number.thousand 	= "Tausend";
lang.number.million 	= "Millionen";
lang.number.billion 	= "Milliarden";

lang.log = {};
lang.log.title					= "Ereignis-Log";
lang.log.pinboard				= "Pinnwand";
lang.log.match					= "Spiel";

lang.participant = {};
lang.participant.name			= "Spielername";
lang.participant.rank			= "Rang";
lang.participant.rankValue		= "%";
lang.participant.rankRawValue	= "Punkte";

lang.population = {};
lang.population.infrastructure	= "Infrastruktur";
lang.population.population		= "Bevölkerung";
lang.population.travelSpeed		= "Geschw.";
lang.population.travelSpeedUnit = "%";
lang.population.system			= "System";

lang.population.arrivalTime		= "Ankunftszeit";
lang.population.arriveASAP		= "100%";
lang.population.arriveSynced	= "Sync";
//lang.population.arriveASAP	= "Geschwindigkeit maximieren";
//lang.population.arriveSynced	= "Ankunft synchronisieren";
lang.population.exodus			= "Sys. aufgeben";
lang.population.giveupAll		= "J";
lang.population.giveupNone		= "N";
//lang.population.giveupAll		= "Alle aufgeben";
//lang.population.giveupNone	= "Keine aufgeben";
lang.population.status			= "Status";
lang.population.selected		= "Ausgewählt";
lang.population.send			= "Angreifen / Siedeln";
lang.population.total			= "Gesamt-Bevölkerung";

lang.profile = {};
lang.profile.accountStatus		= "Status";
lang.profile.accountStatusExpireDate = "Status läuft ab";
lang.profile.birthday			= "Geburtstag";
lang.profile.dateFormat			= "Datums-Format";
lang.profile.email				= "E-Mail";
lang.profile.gender				= "Geschlecht";
lang.profile.password			= "Passwort";
lang.profile.password_confirm	= lang.profile.password + " wiederholen";
lang.profile.password_new		= "Neues " + lang.profile.password;
lang.profile.password_old		= "Altes " + lang.profile.password;
lang.profile.registrationDate	= "Registriert seit";
lang.profile.timeZoneID			= "Zeitzone";
lang.profile.username			= "Benutzername";

lang.profile.change_password	= lang.profile.password + " ändern";
lang.profile.change_email		= lang.profile.email + " ändern";

lang.system = {};
lang.system.buildPriority		= "";
lang.system.infrastructure		= "Infrastruktur";
lang.system.maxPopulation		= "Max. Kapazität";

lang.welcome = {};
lang.welcome.headLine			= "Neuigkeiten:";
lang.welcome.showOnLoad			= "Dieses Fenster beim Start anzeigen?";
lang.welcome.title				= "Willkommen bei syncnapsis";
lang.welcome.toggle1_active		= lang.menu.register + "!";
lang.welcome.toggle1_inactive	= "Neu hier?";
lang.welcome.toggle2_active		= lang.menu.about_tooltip + "!";
lang.welcome.toggle2_inactive	= "Neugierig?";
