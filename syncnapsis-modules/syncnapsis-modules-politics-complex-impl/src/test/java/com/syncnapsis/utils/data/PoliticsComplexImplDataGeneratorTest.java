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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.base.ContactExtension;
import com.syncnapsis.data.service.ContactGroupManager;
import com.syncnapsis.data.service.EmpireManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ApplicationContextUtil;

@TestExcludesMethods({ "afterPropertiesSet", "generate*", "set*", "get*", "initiate" })
public class PoliticsComplexImplDataGeneratorTest extends BaseDaoTestCase
{
	private PoliticsComplexImplDataGenerator	gen						= new PoliticsComplexImplDataGenerator();
	private AuthorityGenericImplDataGenerator	authorityDataGenerator	= new AuthorityGenericImplDataGenerator();
	private StatsBaseImplDataGenerator			statsDataGenerator		= new StatsBaseImplDataGenerator();

	private ContactGroupManager					contactGroupManager;
	private EmpireManager						empireManager;

	@Override
	public void setUp() throws Exception
	{
		super.setUp();

		ApplicationContextUtil.autowire(applicationContext, authorityDataGenerator);
		authorityDataGenerator.setProjectDirectory(new File(".").getAbsolutePath());
		authorityDataGenerator.afterPropertiesSet();

		ApplicationContextUtil.autowire(applicationContext, statsDataGenerator);
		statsDataGenerator.setProjectDirectory(new File(".").getAbsolutePath());
		statsDataGenerator.afterPropertiesSet();

		ApplicationContextUtil.autowire(applicationContext, gen);
		gen.setProjectDirectory(new File(".").getAbsolutePath());
		gen.setAuthorityDataGenerator(authorityDataGenerator);
		gen.setStatsDataGenerator(statsDataGenerator);
		gen.afterPropertiesSet();
	}

	public void testCreateAlliance() throws Exception
	{
		String name = "NEW_ALL";

		Map<String, Map<String, Boolean>> contactAuthoritiesMap = new HashMap<String, Map<String, Boolean>>();
		contactAuthoritiesMap.put("contactGroup1", new HashMap<String, Boolean>());
		contactAuthoritiesMap.put("contactGroup2", new HashMap<String, Boolean>());

		Map<String, Map<String, Boolean>> allianceAuthoritiesMap = new HashMap<String, Map<String, Boolean>>();
		allianceAuthoritiesMap.put("rank1", new HashMap<String, Boolean>());
		allianceAuthoritiesMap.put("rank2", new HashMap<String, Boolean>());

		Map<String, String> allianceMemberRanksMap = new HashMap<String, String>();
		allianceMemberRanksMap.put("rank1_parent", "-");
		allianceMemberRanksMap.put("rank1_full", "rank #1");
		allianceMemberRanksMap.put("rank1_weight", "1");
		allianceMemberRanksMap.put("rank2_parent", "rank1");
		allianceMemberRanksMap.put("rank2_full", "rank #2");
		allianceMemberRanksMap.put("rank2_weight", "0");

		Alliance alliance = gen.createAlliance(name, contactAuthoritiesMap, allianceAuthoritiesMap, allianceMemberRanksMap);
		
		flush();

		assertNotNull(alliance);
		assertNotNull(alliance.getId());
		assertEquals(name, alliance.getShortName());

		assertNotNull(alliance.getAllianceMemberRanks());
		assertEquals(2, alliance.getAllianceMemberRanks().size());
		
		assertEquals(allianceMemberRanksMap.get("rank2_full"), alliance.getAllianceMemberRanks().get(0).getRankName());
		assertEquals(Integer.parseInt(allianceMemberRanksMap.get("rank2_weight")), alliance.getAllianceMemberRanks().get(0).getVoteWeight());
		assertEquals(alliance, alliance.getAllianceMemberRanks().get(0).getAlliance());
		assertEquals(alliance.getAllianceMemberRanks().get(1), alliance.getAllianceMemberRanks().get(0).getParent());
		
		assertEquals(allianceMemberRanksMap.get("rank1_full"), alliance.getAllianceMemberRanks().get(1).getRankName());
		assertEquals(Integer.parseInt(allianceMemberRanksMap.get("rank1_weight")), alliance.getAllianceMemberRanks().get(1).getVoteWeight());
		assertEquals(alliance, alliance.getAllianceMemberRanks().get(1).getAlliance());
		assertNull(alliance.getAllianceMemberRanks().get(1).getParent());

		List<ContactGroup> contactGroups = contactGroupManager.getByAlliance(alliance.getId());
		assertNotNull(contactGroups);
		assertEquals(contactAuthoritiesMap.size(), contactGroups.size());
		assertEquals("contactGroup1", contactGroups.get(0).getName());
		assertEquals("contactGroup2", contactGroups.get(1).getName());
	}

	public void testCreateContact() throws Exception
	{
		Empire contact1 = empireManager.getByName("E1");
		Empire contact2 = empireManager.getByName("E2");
		
		Map<String, Boolean> authoritiesMap1 = new HashMap<String, Boolean>();
		Map<String, Boolean> authoritiesMap2 = authoritiesMap1;

		ContactExtension<Empire, Empire> contact = gen.createContact(contact1, contact2, authoritiesMap1, authoritiesMap2);

		assertNotNull(contact);
		assertNotNull(contact.getId());
		
		assertEquals(contact1, contact.getContact1());
		assertEquals(contact2, contact.getContact2());
		assertNotNull(contact.getContactAuthorities1());
		assertNotNull(contact.getContactAuthorities2());
	}
}
