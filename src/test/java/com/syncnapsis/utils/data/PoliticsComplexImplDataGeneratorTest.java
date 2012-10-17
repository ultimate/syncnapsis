package com.syncnapsis.utils.data;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.service.AllianceManager;
import com.syncnapsis.data.service.AllianceMemberRankManager;
import com.syncnapsis.data.service.ContactGroupManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ApplicationContextUtil;

@TestExcludesMethods({ "afterPropertiesSet", "generate*", "set*", "get*" })
public class PoliticsComplexImplDataGeneratorTest extends BaseDaoTestCase
{
	private PoliticsComplexImplDataGenerator	gen	= new PoliticsComplexImplDataGenerator();
	private AllianceManager allianceManager;
	private AllianceMemberRankManager allianceMemberRankManager;
	private ContactGroupManager contactGroupManager;

	@Override
	public void setUp() throws Exception
	{
		super.setUp();

		ApplicationContextUtil.autowire(applicationContext, gen);
		gen.setProjectDirectory(new File(".").getAbsolutePath());
		gen.afterPropertiesSet();
	}

	public void testCreateAlliance() throws Exception
	{
		String name = "new alliance";
		
		Map<String, Map<String, Boolean>> contactAuthoritiesMap = new HashMap<String, Map<String,Boolean>>();
		contactAuthoritiesMap.put("contactGroup1", new HashMap<String, Boolean>());
		contactAuthoritiesMap.put("contactGroup2", new HashMap<String, Boolean>());
		
		Map<String, Map<String, Boolean>> allianceAuthoritiesMap = new HashMap<String, Map<String,Boolean>>();
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
				
		assertNotNull(alliance);
		assertNotNull(alliance.getId());
		assertEquals(name, alliance.getFullName());
		
		assertNotNull(alliance.getAllianceMemberRanks()); 
		assertEquals(2, alliance.getAllianceMemberRanks().size()); 
		assertEquals(allianceMemberRanksMap.get("rank1_full"), alliance.getAllianceMemberRanks().get(0).getRankName());
		assertEquals(allianceMemberRanksMap.get("rank1_weight"), alliance.getAllianceMemberRanks().get(0).getVoteWeight());
		assertEquals(alliance, alliance.getAllianceMemberRanks().get(0).getAlliance());
		assertNull(alliance.getAllianceMemberRanks().get(0).getParent());
		assertEquals(allianceMemberRanksMap.get("rank2_full"), alliance.getAllianceMemberRanks().get(1).getRankName());
		assertEquals(allianceMemberRanksMap.get("rank2_weight"), alliance.getAllianceMemberRanks().get(1).getVoteWeight());
		assertEquals(alliance, alliance.getAllianceMemberRanks().get(1).getAlliance());
		assertEquals(alliance.getAllianceMemberRanks().get(1), alliance.getAllianceMemberRanks().get(1).getParent());
		
		List<ContactGroup> contactGroups = contactGroupManager.getByAlliance(alliance.getId());
		assertNotNull(contactGroups);
		assertEquals(contactAuthoritiesMap.size(), contactGroups.size());
		assertEquals("contactGroup1", contactGroups.get(0).getName());
		assertEquals("contactGroup2", contactGroups.get(1).getName());
//		gen.cre
		fail("not implemented");
	}

	public void testCreateContact() throws Exception
	{
//		gen.
		fail("not implemented");
	}
}

