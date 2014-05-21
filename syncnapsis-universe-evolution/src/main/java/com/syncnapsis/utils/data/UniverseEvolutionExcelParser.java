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
package com.syncnapsis.utils.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.syncnapsis.constants.BaseApplicationConstants;
import com.syncnapsis.constants.BaseGameConstants;
import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.AllianceMemberRank;
import com.syncnapsis.data.model.AllianceRank;
import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.EmpireRank;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.contacts.AllianceAllianceContact;
import com.syncnapsis.data.model.contacts.EmpireAllianceContact;
import com.syncnapsis.data.model.contacts.EmpireEmpireContact;

public class UniverseEvolutionExcelParser
{
	/**
	 * Map zum zwischenspeichern der geladenen AllianceAuthorities
	 */
	private static Map<String, Map<String, Boolean>>	allianceAuthorities;
	/**
	 * Map zum zwischenspeichern der AllianceAuthorities-Spalten
	 */
	private static Map<Integer, String>					allianceAuthorities_colToName;								;
	/**
	 * Map zum zwischenspeichern der Allianz-Rang-Hierarchie
	 */
	private static Map<String, String>					allianceMemberRanks;
	/**
	 * Map zum zwischenspeichern der geladenen ContactAuthorities
	 */
	private static Map<String, Map<String, Boolean>>	contactAuthorities;
	/**
	 * Map zum zwischenspeichern der ContactAuthorities-Spalten
	 */
	private static Map<Integer, String>					contactAuthorities_colToName;								;

	/**
	 * Liest die in der Konfiguration angegebene Excel-Datei ein und erstellt
	 * daraus Testdaten.
	 * 
	 * @param properties - die Einstellungen für das Erstellen der Testdaten
	 */
	public static void createDataFromExcel(Properties properties) throws IOException
	{
		String fileName = properties.getProperty("excelData.fileName");
		String key_sheet_players = properties.getProperty("excelData.sheet.players");
		String key_sheet_empires = properties.getProperty("excelData.sheet.empires");
		String key_sheet_allianceMemberships = properties.getProperty("excelData.sheet.allianceMemberships");
		String key_sheet_diplomacy_aa = properties.getProperty("excelData.sheet.diplomacy.aa");
		String key_sheet_diplomacy_ae = properties.getProperty("excelData.sheet.diplomacy.ae");
		String key_sheet_diplomacy_ee = properties.getProperty("excelData.sheet.diplomacy.ee");
		String key_sheet_authorities_a = properties.getProperty("excelData.sheet.authorities.a");
		String key_sheet_authorities_c = properties.getProperty("excelData.sheet.authorities.c");

		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("src/main/resources/" + fileName));

		parseContactAuthorities(workbook, key_sheet_authorities_c);
		parseAllianceAuthorities(workbook, key_sheet_authorities_a);
		parseUsers(workbook, key_sheet_players);
		parseEmpires(workbook, key_sheet_empires);
		parseAlliancesMemberships(workbook, key_sheet_allianceMemberships);
		parseDiplomacy_AA(workbook, key_sheet_diplomacy_aa);
		parseDiplomacy_AE(workbook, key_sheet_diplomacy_ae);
		parseDiplomacy_EE(workbook, key_sheet_diplomacy_ee);
	}

	/**
	 * Laden der Spalten für die Kontakt-Rechte
	 * 
	 * @param workbook - das Workbook
	 * @param key_sheet_authorities_c - der Key für das Sheet
	 */
	public static void parseContactAuthorities(HSSFWorkbook workbook, String key_sheet_authorities_c)
	{
		HSSFSheet sheet_authorities_c = workbook.getSheet(key_sheet_authorities_c);

		// sheet_authorities_c -> Laden der Spalten für die Kontakt-Rechte
		HSSFRow row = sheet_authorities_c.getRow(0);
		for(int i = 1; i < 255; i++)
		{
			if(row.getCell(i) == null)
				break;
			contactAuthorities_colToName.put(i, row.getCell(i).getStringCellValue());
		}
		logger.debug("contact-authority-columns loaded: " + contactAuthorities_colToName.size());

		String name;

		// sheet_authorities_c -> Laden der Kontakt-Rechte
		int rowNum = 2;
		while((row = sheet_authorities_c.getRow(rowNum++)) != null)
		{
			try
			{
				name = row.getCell(0).getStringCellValue();
				contactAuthorities.put(name, new TreeMap<String, Boolean>());

				for(int i = 1; i < 255; i++)
				{
					if(row.getCell(i) == null)
						break;
					contactAuthorities.get(name).put(contactAuthorities_colToName.get(i), row.getCell(i).getNumericCellValue() == 1);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("error: " + key_sheet_authorities_c + " at line " + rowNum);
			}
		}
		logger.debug("contact-authorities loaded: " + contactAuthorities.size());
	}

	/**
	 * Laden der Spalten für die Allianz-Rechte
	 * 
	 * @param workbook - das Workbook
	 * @param key_sheet_authorities_a - der Key für das Sheet
	 */
	public static void parseAllianceAuthorities(HSSFWorkbook workbook, String key_sheet_authorities_a)
	{
		HSSFSheet sheet_authorities_a = workbook.getSheet(key_sheet_authorities_a);

		// sheet_authorities_a -> Laden der Spalten für die Allianz-Rechte
		HSSFRow row = sheet_authorities_a.getRow(0);
		for(int i = 3; i < 255; i++)
		{
			if(row.getCell(i) == null)
				break;
			allianceAuthorities_colToName.put(i, row.getCell(i).getStringCellValue());
		}
		logger.debug("alliance-authority-columns loaded: " + allianceAuthorities_colToName.size());

		String name, fullname, parent, ref, weight;

		// sheet_authorities_a -> Laden der Allianz-Rechte
		int rowNum = 2;
		while((row = sheet_authorities_a.getRow(rowNum++)) != null)
		{
			try
			{
				name = row.getCell(0).getStringCellValue();
				if(name == null || name.isEmpty())
					break;
				fullname = row.getCell(1).getStringCellValue();
				parent = row.getCell(2).getStringCellValue();
				weight = "" + (int) row.getCell(3).getNumericCellValue();
				allianceAuthorities.put(name, new TreeMap<String, Boolean>());
				allianceMemberRanks.put(name + "_full", fullname);
				allianceMemberRanks.put(name + "_parent", parent);
				allianceMemberRanks.put(name + "_weight", weight);

				for(int i = 5; i < 255; i++)
				{
					if(row.getCell(i) == null)
						break;
					allianceAuthorities.get(name).put(allianceAuthorities_colToName.get(i), row.getCell(i).getNumericCellValue() == 1);
				}

				ref = row.getCell(4).getStringCellValue(); // contactauthorities
				allianceAuthorities.get(name).putAll(contactAuthorities.get(ref));
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("error: " + key_sheet_authorities_a + " at line " + rowNum);
			}
		}
		logger.debug("alliance-authorities loaded: " + allianceAuthorities.size());
		logger.debug("alliance-ranks loaded: " + allianceMemberRanks.size());
	}

	/**
	 * Erstellen aller Benutzer
	 * 
	 * @param workbook - das Workbook
	 * @param key_sheet_players - der Key für das Sheet
	 */
	public static void parseUsers(HSSFWorkbook workbook, String key_sheet_players)
	{
		HSSFSheet sheet_players = workbook.getSheet(key_sheet_players);

		HSSFRow row;
		Cell cell;
		String username, rolename, username1, username2;
		String playerrole, userrole;
		Player player;

		// sheet_players -> erstelle alle Benutzer
		int rowNum = 1;
		while((row = sheet_players.getRow(rowNum++)) != null)
		{
			try
			{
				username = row.getCell(0).getStringCellValue();
				if(username == null || username.isEmpty())
					break;
				rolename = row.getCell(1).getStringCellValue();

				if(rolename.contains("NORMAL"))
				{
					playerrole = BaseGameConstants.ROLE_NORMAL_PLAYER;
					userrole = BaseApplicationConstants.ROLE_NORMAL_USER;
				}
				else if(rolename.contains("MODERATOR"))
				{
					playerrole = BaseGameConstants.ROLE_PREMIUM_PLAYER;
					userrole = BaseApplicationConstants.ROLE_MODERATOR;
				}
				else if(rolename.contains("ADMIN"))
				{
					playerrole = BaseGameConstants.ROLE_PREMIUM_PLAYER;
					userrole = BaseApplicationConstants.ROLE_ADMIN;
				}
				else
				// if (rolename.contains("DEMO"))
				{
					playerrole = BaseGameConstants.ROLE_DEMO_PLAYER;
					userrole = BaseApplicationConstants.ROLE_DEMO_USER;
				}

				getOrCreatePlayer(username, playerrole, userrole);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("error: " + key_sheet_players + " at line " + rowNum);
			}
		}
		logger.debug("players created: " + players.size());

		rowNum = 1;
		int colNum;
		int count = 0, sittercount = 0;
		boolean sitter1, sitter2;
		while((row = sheet_players.getRow(rowNum++)) != null)
		{
			try
			{
				colNum = rowNum + 1;

				username1 = row.getCell(0).getStringCellValue();
				if(username1 == null || username1.isEmpty())
					break;

				while((cell = sheet_players.getRow(0).getCell(colNum++)) != null)
				{
					username2 = cell.getStringCellValue();
					if(username2 == null || username2.isEmpty())
						break;
					sitter1 = (row.getCell(colNum - 1) != null && row.getCell(colNum - 1).getNumericCellValue() == 1);
					sitter2 = (sheet_players.getRow(colNum - 2).getCell(rowNum) != null && sheet_players.getRow(colNum - 2).getCell(rowNum)
							.getNumericCellValue() == 1);
					if(sitter1 || sitter2)
					{
						RandomModels.createUserContact(getOrCreatePlayer(username1, null, null).getUser(), getOrCreatePlayer(username2, null, null)
								.getUser());
						count++;
					}
					if(sitter1)
					{
						player = getOrCreatePlayer(username1, null, null);
						player.getSitters().add(getOrCreatePlayer(username2, null, null));
						player = playerManager.save(player);
						players.put(username1, player);
						sittercount++;
					}
					if(sitter2)
					{
						player = getOrCreatePlayer(username2, null, null);
						player.getSitters().add(getOrCreatePlayer(username1, null, null));
						player = playerManager.save(player);
						players.put(username2, player);
						sittercount++;
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("error: " + key_sheet_players + " at line " + rowNum);
			}
		}
		logger.debug("user-contacts created: " + count);
		logger.debug("user-sitters created: " + sittercount);
	}

	/**
	 * Erstellen aller Imperien
	 * 
	 * @param workbook - das Workbook
	 * @param key_sheet_empires - der Key für das Sheet
	 */
	public static void parseEmpires(HSSFWorkbook workbook, String key_sheet_empires)
	{
		HSSFSheet sheet_empires = workbook.getSheet(key_sheet_empires);

		HSSFRow row;
		String empirename, playername;
		Player player;
		Empire empire;
		int blocks, colonies, x, y, z, r, level;

		// sheet_empires -> erstelle alle Imperien
		int rowNum = 1;
		while((row = sheet_empires.getRow(rowNum++)) != null)
		{
			try
			{
				empirename = row.getCell(0).getStringCellValue();
				if(empirename == null || empirename.isEmpty())
					break;
				playername = row.getCell(1).getStringCellValue();
				blocks = (int) row.getCell(2).getNumericCellValue();
				colonies = (int) row.getCell(3).getNumericCellValue();
				x = (int) row.getCell(4).getNumericCellValue();
				y = (int) row.getCell(5).getNumericCellValue();
				z = (int) row.getCell(6).getNumericCellValue();
				r = (int) row.getCell(7).getNumericCellValue();
				level = (int) row.getCell(8).getNumericCellValue();

				logger.debug("creating empire: " + empirename + " [player = " + playername + "]" + " blocks/colonies=" + blocks + "/" + colonies
						+ " @ (" + x + "|" + y + "|" + z + ") r=" + r);

				player = getOrCreatePlayer(playername, null, null);
				empire = getOrCreateEmpire(empirename, player, blocks, colonies, x, y, z, r, level);

				if(player.getCurrentEmpire() == null)
				{
					player.setCurrentEmpire(empire);
					player = playerManager.save(player);
					players.put(playername, player);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("error: " + key_sheet_empires + " at line " + rowNum);
			}
		}
		logger.debug("empires created: " + empires.size());
	}

	/**
	 * Erstellen aller Allianzen und der Mitgliedschaften
	 * 
	 * @param workbook - das Workbook
	 * @param key_sheet_allianceMemberships - der Key für das Sheet
	 */
	public static void parseAlliancesMemberships(HSSFWorkbook workbook, String key_sheet_allianceMemberships)
	{
		HSSFSheet sheet_allianceMemberships = workbook.getSheet(key_sheet_allianceMemberships);

		HSSFRow row;
		Cell cell;
		String alliancename, empirename, name, fullname;
		Alliance alliance;
		AllianceRank allianceRank;
		Empire empire;
		EmpireRank empireRank;
		int rowNum;

		List<Empire> members;

		// sheet_allianceMemberships -> Allianzen und Mitgliedschaften erstellen
		int colNum = 1;
		while((cell = sheet_allianceMemberships.getRow(0).getCell(colNum++)) != null)
		{
			try
			{
				alliancename = cell.getStringCellValue();

				logger.debug("creating alliance: " + alliancename);

				alliance = getOrCreateAlliance(alliancename);

				logger.debug("alliance has " + alliance.getAllianceMemberRanks().size() + " ranks");

				members = new LinkedList<Empire>();

				// Mitglieder den Rängen zuweisen
				rowNum = 1;
				while((row = sheet_allianceMemberships.getRow(rowNum++)) != null)
				{
					empirename = row.getCell(0).getStringCellValue();
					if(empirename == null || empirename.isEmpty())
						break;

					empire = getOrCreateEmpire(empirename, null);
					name = row.getCell(colNum - 1).getStringCellValue();
					fullname = allianceMemberRanks.get(name + "_full");

					if(fullname != null)
					{
						members.add(empire);
						logger.debug("empire '" + empirename + "' has rank '" + name + "' ('" + fullname + "')");
						for(AllianceMemberRank rank : alliance.getAllianceMemberRanks())
						{
							if(rank.getRankName().equals(fullname))
							{
								rank.getEmpires().add(empire);
								break;
							}
						}
					}
				}
				for(AllianceMemberRank rank : alliance.getAllianceMemberRanks())
				{
					allianceMemberRankManager.save(rank);
				}

				logger.debug("alliance has " + members.size() + " members");

				allianceRank = (AllianceRank) allianceRankManager.getByEntity(alliance.getId());
				for(Empire member : members)
				{
					empireRank = (EmpireRank) empireRankManager.getByEntity(member.getId());

					allianceRank.setEconomy(allianceRank.getEconomy() + empireRank.getEconomy());
					allianceRank.setMilitary(allianceRank.getMilitary() + empireRank.getMilitary());
					allianceRank.setScience(allianceRank.getScience() + empireRank.getScience());
					allianceRank.setTotal(allianceRank.getTotal() + empireRank.getTotal());
				}
				allianceRank.setNumberOfEmpires(members.size());
				allianceRank.setAverageEconomy(allianceRank.getEconomy() / allianceRank.getNumberOfEmpires());
				allianceRank.setAverageMilitary(allianceRank.getMilitary() / allianceRank.getNumberOfEmpires());
				allianceRank.setAverageScience(allianceRank.getScience() / allianceRank.getNumberOfEmpires());
				allianceRank.setAverageTotal(allianceRank.getTotal() / allianceRank.getNumberOfEmpires());
				allianceRank = (AllianceRank) allianceRankManager.save(allianceRank);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("error: " + key_sheet_allianceMemberships + " at column " + colNum);
			}
		}
		logger.debug("alliances created: " + alliances.size());
	}

	/**
	 * Erstellen der Diplomatie zwischen Allianz und Allianz
	 * 
	 * @param workbook - das Workbook
	 * @param key_sheet_diplomacy_aa - der Key für das Sheet
	 */
	public static void parseDiplomacy_AA(HSSFWorkbook workbook, String key_sheet_diplomacy_aa)
	{
		HSSFSheet sheet_diplomacy_aa = workbook.getSheet(key_sheet_diplomacy_aa);

		HSSFRow row;
		String contact1, contact2, name1, name2;
		int colNum;
		AllianceAllianceContact allianceAllianceContact;

		// sheet_diplomacy_aa -> Abkommen erstellen
		int rowNum = 1;
		int count = 0;
		while((row = sheet_diplomacy_aa.getRow(rowNum++)) != null)
		{
			try
			{
				colNum = rowNum;

				contact1 = row.getCell(0).getStringCellValue();
				if(contact1 == null || contact1.isEmpty())
					break;

				while((contact2 = sheet_diplomacy_aa.getRow(0).getCell(colNum++).getStringCellValue()) != null)
				{
					if(contact2 == null || contact2.isEmpty())
						break;

					name1 = row.getCell(colNum - 1).getStringCellValue();
					name2 = sheet_diplomacy_aa.getRow(colNum - 1).getCell(rowNum - 1).getStringCellValue();
					if(contactAuthorities.get(name1) == null && contactAuthorities.get(name2) == null)
						continue;
					if(contactAuthorities.get(name1) == null || contactAuthorities.get(name2) == null)
						throw new Exception("both authorities must be set: " + contact1 + "(" + name1 + ") <-> " + contact2 + "(" + name2 + ")");

					allianceAllianceContact = (AllianceAllianceContact) RandomModels.createContact(getOrCreateAlliance(contact1),
							getOrCreateAlliance(contact2), contactAuthorities.get(name1), contactAuthorities.get(name2));
					for(ContactGroup contactGroup : contactGroupManager.getByAlliance(allianceAllianceContact.getContact1().getId()))
					{
						if(contactGroup.getName().equals(name1))
						{
							allianceAllianceContact.getContactGroups().add(contactGroup);
						}
					}
					for(ContactGroup contactGroup : contactGroupManager.getByAlliance(allianceAllianceContact.getContact2().getId()))
					{
						if(contactGroup.getName().equals(name2))
						{
							allianceAllianceContact.getContactGroups().add(contactGroup);
						}
					}
					allianceAllianceContact = allianceAllianceContactManager.save(allianceAllianceContact);
					count++;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("error: " + key_sheet_diplomacy_aa + ": " + e.getMessage());
			}
		}
		logger.debug("contacts (alliance-alliance) created: " + count);
	}

	/**
	 * Erstellen der Diplomatie zwischen Allianz und Imperium
	 * 
	 * @param workbook - das Workbook
	 * @param key_sheet_diplomacy_ae - der Key für das Sheet
	 */
	public static void parseDiplomacy_AE(HSSFWorkbook workbook, String key_sheet_diplomacy_ae)
	{
		HSSFSheet sheet_diplomacy_ae = workbook.getSheet(key_sheet_diplomacy_ae);

		HSSFRow row;
		String contact1, contact2, name;
		int colNum;
		EmpireAllianceContact empireAllianceContact;

		// sheet_diplomacy_ae -> Abkommen erstellen
		int rowNum = 1;
		int count = 0;
		while((row = sheet_diplomacy_ae.getRow(rowNum++)) != null)
		{
			try
			{
				colNum = 1;

				contact1 = row.getCell(0).getStringCellValue();
				if(contact1 == null || contact1.isEmpty())
					break;

				while((contact2 = sheet_diplomacy_ae.getRow(0).getCell(colNum++).getStringCellValue()) != null)
				{
					if(contact2 == null || contact2.isEmpty())
						break;

					name = row.getCell(colNum - 1).getStringCellValue();
					if(contactAuthorities.get(name) == null)
						continue;

					empireAllianceContact = (EmpireAllianceContact) RandomModels.createContact(getOrCreateEmpire(contact1, null),
							getOrCreateAlliance(contact2), contactAuthorities.get(name), contactAuthorities.get(name));
					for(ContactGroup contactGroup : contactGroupManager.getByEmpire(empireAllianceContact.getContact1().getId()))
					{
						if(contactGroup.getName().equals(name))
						{
							empireAllianceContact.getContactGroups().add(contactGroup);
						}
					}
					for(ContactGroup contactGroup : contactGroupManager.getByAlliance(empireAllianceContact.getContact2().getId()))
					{
						if(contactGroup.getName().equals(name))
						{
							empireAllianceContact.getContactGroups().add(contactGroup);
						}
					}
					empireAllianceContact = empireAllianceContactManager.save(empireAllianceContact);
					count++;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("error: " + key_sheet_diplomacy_ae + ": " + e.getMessage());
			}
		}
		logger.debug("contacts (empire-alliance) created: " + count);
	}

	/**
	 * Erstellen der Diplomatie zwischen Imperium und Imperium
	 * 
	 * @param workbook - das Workbook
	 * @param key_sheet_diplomacy_ee - der Key für das Sheet
	 */
	public static void parseDiplomacy_EE(HSSFWorkbook workbook, String key_sheet_diplomacy_ee)
	{
		HSSFSheet sheet_diplomacy_ee = workbook.getSheet(key_sheet_diplomacy_ee);

		HSSFRow row;
		String contact1, contact2, name1, name2;
		int colNum;
		EmpireEmpireContact empireEmpireContact;

		// sheet_diplomacy_ee -> Abkommen erstellen
		int rowNum = 1;
		int count = 0;
		while((row = sheet_diplomacy_ee.getRow(rowNum++)) != null)
		{
			try
			{
				colNum = rowNum;

				contact1 = row.getCell(0).getStringCellValue();
				if(contact1 == null || contact1.isEmpty())
					break;

				while((contact2 = sheet_diplomacy_ee.getRow(0).getCell(colNum++).getStringCellValue()) != null)
				{
					if(contact2 == null || contact2.isEmpty())
						break;

					name1 = row.getCell(colNum - 1).getStringCellValue();
					name2 = sheet_diplomacy_ee.getRow(colNum - 1).getCell(rowNum - 1).getStringCellValue();
					if(contactAuthorities.get(name1) == null && contactAuthorities.get(name2) == null)
						continue;
					if(contactAuthorities.get(name1) == null || contactAuthorities.get(name2) == null)
						throw new Exception("both authorities must be set: " + contact1 + "(" + name1 + ") <-> " + contact2 + "(" + name2 + ")");

					empireEmpireContact = (EmpireEmpireContact) RandomModels.createContact(getOrCreateEmpire(contact1, null),
							getOrCreateEmpire(contact2, null), contactAuthorities.get(name1), contactAuthorities.get(name2));
					for(ContactGroup contactGroup : contactGroupManager.getByEmpire(empireEmpireContact.getContact1().getId()))
					{
						if(contactGroup.getName().equals(name1))
						{
							empireEmpireContact.getContactGroups().add(contactGroup);
						}
					}
					for(ContactGroup contactGroup : contactGroupManager.getByEmpire(empireEmpireContact.getContact1().getId()))
					{
						if(contactGroup.getName().equals(name2))
						{
							empireEmpireContact.getContactGroups().add(contactGroup);
						}
					}
					empireEmpireContact = empireEmpireContactManager.save(empireEmpireContact);
					count++;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("error: " + key_sheet_diplomacy_ee + ": " + e.getMessage());
			}
		}
		logger.debug("contacts (empire-empire) created: " + count);
	}

}
