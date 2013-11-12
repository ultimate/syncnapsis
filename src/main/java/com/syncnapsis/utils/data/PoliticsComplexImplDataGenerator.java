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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.util.Assert;

import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.AllianceMemberRank;
import com.syncnapsis.data.model.AllianceRank;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.ContactExtension;
import com.syncnapsis.data.model.contacts.AllianceAllianceContact;
import com.syncnapsis.data.model.contacts.EmpireAllianceContact;
import com.syncnapsis.data.model.contacts.EmpireEmpireContact;
import com.syncnapsis.data.service.AllianceAllianceContactManager;
import com.syncnapsis.data.service.AllianceManager;
import com.syncnapsis.data.service.AllianceMemberRankManager;
import com.syncnapsis.data.service.AllianceRankManager;
import com.syncnapsis.data.service.ContactGroupManager;
import com.syncnapsis.data.service.EmpireAllianceContactManager;
import com.syncnapsis.data.service.EmpireEmpireContactManager;
import com.syncnapsis.data.service.EmpireManager;

public class PoliticsComplexImplDataGenerator extends PoliticsDataGenerator
{
	protected AuthorityGenericImplDataGenerator	authorityDataGenerator;
	protected StatsBaseImplDataGenerator		statsDataGenerator;

	protected AllianceManager					allianceManager;
	protected AllianceMemberRankManager			allianceMemberRankManager;
	protected AllianceRankManager				allianceRankManager;
	protected ContactGroupManager				contactGroupManager;
	protected EmpireManager						empireManager;
	protected AllianceAllianceContactManager	allianceAllianceContactManager;
	protected EmpireAllianceContactManager		empireAllianceContactManager;
	protected EmpireEmpireContactManager		empireEmpireContactManager;

	protected Map<String, Alliance>				alliances	= new TreeMap<String, Alliance>();

	protected Map<String, Map<String, Boolean>>	defaultContactAuthorities;
	protected Map<String, Map<String, Boolean>>	defaultAllianceAuthorities;
	protected Map<String, String>				defaultAllianceMemberRanks;

	public AuthorityGenericImplDataGenerator getAuthorityDataGenerator()
	{
		return authorityDataGenerator;
	}

	public void setAuthorityDataGenerator(AuthorityGenericImplDataGenerator authorityDataGenerator)
	{
		this.authorityDataGenerator = authorityDataGenerator;
	}

	public StatsBaseImplDataGenerator getStatsDataGenerator()
	{
		return statsDataGenerator;
	}

	public void setStatsDataGenerator(StatsBaseImplDataGenerator statsDataGenerator)
	{
		this.statsDataGenerator = statsDataGenerator;
	}

	public AllianceManager getAllianceManager()
	{
		return allianceManager;
	}

	public void setAllianceManager(AllianceManager allianceManager)
	{
		this.allianceManager = allianceManager;
	}

	public AllianceMemberRankManager getAllianceMemberRankManager()
	{
		return allianceMemberRankManager;
	}

	public void setAllianceMemberRankManager(AllianceMemberRankManager allianceMemberRankManager)
	{
		this.allianceMemberRankManager = allianceMemberRankManager;
	}

	public AllianceRankManager getAllianceRankManager()
	{
		return allianceRankManager;
	}

	public void setAllianceRankManager(AllianceRankManager allianceRankManager)
	{
		this.allianceRankManager = allianceRankManager;
	}

	public ContactGroupManager getContactGroupManager()
	{
		return contactGroupManager;
	}

	public void setContactGroupManager(ContactGroupManager contactGroupManager)
	{
		this.contactGroupManager = contactGroupManager;
	}

	public EmpireManager getEmpireManager()
	{
		return empireManager;
	}

	public void setEmpireManager(EmpireManager empireManager)
	{
		this.empireManager = empireManager;
	}

	public AllianceAllianceContactManager getAllianceAllianceContactManager()
	{
		return allianceAllianceContactManager;
	}

	public void setAllianceAllianceContactManager(AllianceAllianceContactManager allianceAllianceContactManager)
	{
		this.allianceAllianceContactManager = allianceAllianceContactManager;
	}

	public EmpireAllianceContactManager getEmpireAllianceContactManager()
	{
		return empireAllianceContactManager;
	}

	public void setEmpireAllianceContactManager(EmpireAllianceContactManager empireAllianceContactManager)
	{
		this.empireAllianceContactManager = empireAllianceContactManager;
	}

	public EmpireEmpireContactManager getEmpireEmpireContactManager()
	{
		return empireEmpireContactManager;
	}

	public void setEmpireEmpireContactManager(EmpireEmpireContactManager empireEmpireContactManager)
	{
		this.empireEmpireContactManager = empireEmpireContactManager;
	}

	public Map<String, Map<String, Boolean>> getDefaultContactAuthorities()
	{
		return defaultContactAuthorities;
	}

	public Map<String, Map<String, Boolean>> getDefaultAllianceAuthorities()
	{
		return defaultAllianceAuthorities;
	}

	public Map<String, String> getDefaultAllianceMemberRanks()
	{
		return defaultAllianceMemberRanks;
	}

	public void initiate(Map<String, Map<String, Boolean>> defaultContactAuthorities, Map<String, Map<String, Boolean>> defaultAllianceAuthorities,
			Map<String, String> defaultAllianceMemberRanks)
	{
		this.defaultContactAuthorities = defaultContactAuthorities;
		this.defaultAllianceAuthorities = defaultAllianceAuthorities;
		this.defaultAllianceMemberRanks = defaultAllianceMemberRanks;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		// @formatter:off
		this.setExcludeTableList(new String[] {
				});
		// @formatter:on
		
		super.afterPropertiesSet();

		Assert.notNull(authorityDataGenerator, "authorityDataGenerator must not be null!");
		Assert.notNull(statsDataGenerator, "statsDataGenerator must not be null!");

		Assert.notNull(allianceManager, "allianceManager must not be null!");
		Assert.notNull(allianceMemberRankManager, "allianceMemberRankManager must not be null!");
		Assert.notNull(allianceRankManager, "allianceRankManager must not be null!");
		Assert.notNull(contactGroupManager, "contactGroupManager must not be null!");
		Assert.notNull(empireManager, "empireManager must not be null!");
		Assert.notNull(allianceAllianceContactManager, "allianceAllianceContactManager must not be null!");
		Assert.notNull(empireAllianceContactManager, "empireAllianceContactManager must not be null!");
		Assert.notNull(empireEmpireContactManager, "empireEmpireContactManager must not be null!");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <C1 extends BaseObject<?>, C2 extends BaseObject<?>> ContactExtension<C1, C2> createContact(C1 contact1, C2 contact2,
			Map<String, Boolean> authoritiesMap1, Map<String, Boolean> authoritiesMap2)
	{
		ContactExtension<C1, C2> contact;

		if(contact1 instanceof Alliance && contact2 instanceof Alliance)
			contact = (ContactExtension<C1, C2>) new AllianceAllianceContact();
		else if(contact1 instanceof Empire && contact2 instanceof Alliance)
			contact = (ContactExtension<C1, C2>) new EmpireAllianceContact();
		else if(contact1 instanceof Empire && contact2 instanceof Empire)
			contact = (ContactExtension<C1, C2>) new EmpireEmpireContact();
		else
			throw new IllegalArgumentException("contacts must be Alliance or Empire");

		contact.setApprovedByContact1(true);
		contact.setApprovedByContact2(true);
		contact.setChangedContactAuthorities1(null);
		contact.setChangedContactAuthorities2(null);
		contact.setChangesApprovedByContact1(true);
		contact.setChangesApprovedByContact2(true);
		contact.setContact1(contact1);
		contact.setContact2(contact2);
		contact.setContactGroups(new ArrayList<ContactGroup>());
		contact.setDefaultVisible1(true);
		contact.setDefaultVisible2(true);

		boolean equal = true;
		for(Entry<String, Boolean> e : authoritiesMap1.entrySet())
		{
			equal = equal && (e.getValue() == authoritiesMap2.get(e.getKey()));
		}
		contact.setEqualContactAuthorities(equal);
		if(equal)
		{
			AuthoritiesGenericImpl contactAuthorities = authorityDataGenerator.createAuthorities(authoritiesMap1);
			contact.setContactAuthorities1(contactAuthorities);
			contact.setContactAuthorities2(contactAuthorities);
		}
		else
		{
			contact.setContactAuthorities1(authorityDataGenerator.createAuthorities(authoritiesMap1));
			contact.setContactAuthorities2(authorityDataGenerator.createAuthorities(authoritiesMap2));
		}

		if(contact1 instanceof Alliance && contact2 instanceof Alliance)
			return (ContactExtension<C1, C2>) allianceAllianceContactManager.save((AllianceAllianceContact) contact);
		if(contact1 instanceof Empire && contact2 instanceof Alliance)
			return (ContactExtension<C1, C2>) empireAllianceContactManager.save((EmpireAllianceContact) contact);
		if(contact1 instanceof Empire && contact2 instanceof Empire)
			return (ContactExtension<C1, C2>) empireEmpireContactManager.save((EmpireEmpireContact) contact);
		return null;
	}

	public Alliance createAlliance(String name, Map<String, Map<String, Boolean>> contactAuthoritiesMap,
			Map<String, Map<String, Boolean>> allianceAuthoritiesMap, Map<String, String> allianceMemberRanksMap)
	{
		Alliance alliance = new Alliance();
		alliance.setActivated(true);
		alliance.setDescription(random.nextString(random.nextInt(20, 2000), DefaultData.STRING_ASCII_COMPLETE_NO_CONTROLCHARS));
		alliance.setFoundationDate(new Date(timeProvider.get()));
		alliance.setFullName("full_" + name);
		alliance.setImageURL(null);
		alliance.setPrimaryColor("#FF0000");
		alliance.setSecondaryColor("#FF0000");
		alliance.setShortName(name);

		alliance = allianceManager.save(alliance);

		AllianceRank allianceRank = statsDataGenerator.createRankSimple(AllianceRank.class, alliance, 0);
		allianceRank = (AllianceRank) allianceRankManager.save(allianceRank);

		if(contactAuthoritiesMap != null)
		{
			ContactGroup contactGroup;
			for(Entry<String, Map<String, Boolean>> e : contactAuthoritiesMap.entrySet())
			{
				contactGroup = new ContactGroup();
				contactGroup.setAllianceContacts(null);
				contactGroup.setDefaultVisible(true);
				contactGroup.setDescription(e.getKey());
				contactGroup.setEmpireContacts(null);
				contactGroup.setName(e.getKey());
				contactGroup.setOwnerAlliance(alliance);
				contactGroup.setOwnerEmpire(null);

				contactGroup = contactGroupManager.save(contactGroup);

				logger.debug("created contact group '" + e.getKey() + "' for alliance '" + alliance.getShortName() + "'");
				
				// TODO process authorities?
			}
		}

		Map<String, AllianceMemberRank> processedRanks = new HashMap<String, AllianceMemberRank>();
		processedRanks.put("-", null);

		AllianceMemberRank rank;
		String shortname, fullname, parent;
		int voteWeight;
		boolean created = true;
		while(created)
		{
			created = false;
			for(Entry<String, String> e : allianceMemberRanksMap.entrySet())
			{
				// rang selbst nicht vorhanden, elternrang vorhanden
				if(e.getKey().endsWith("_parent"))
				{
					shortname = e.getKey().substring(0, e.getKey().indexOf("_parent"));
					parent = e.getValue();
					fullname = allianceMemberRanksMap.get(shortname + "_full");
					voteWeight = Integer.parseInt(allianceMemberRanksMap.get(shortname + "_weight"));
					if(!processedRanks.containsKey(shortname) && processedRanks.containsKey(parent))
					{
						rank = new AllianceMemberRank();
						rank.setAlliance(alliance);
						rank.setAuthorities(authorityDataGenerator.createAuthorities(allianceAuthoritiesMap.get(shortname)));
						rank.setEmpires(new ArrayList<Empire>());
						rank.setParent(processedRanks.get(parent));
						rank.setRankName(fullname);
						rank.setVisible(true);
						rank.setVoteWeight(voteWeight);

						rank = allianceMemberRankManager.save(rank);
						processedRanks.put(shortname, rank);

						logger.debug("created rank '" + shortname + "' ('" + fullname + "') for alliance '" + alliance.getShortName() + "'");

						created = true;
					}
				}
			}
		}

		processedRanks.remove("-");
		List<AllianceMemberRank> allianceMemberRanks = new ArrayList<AllianceMemberRank>();
		allianceMemberRanks.addAll(processedRanks.values());

		alliance.setAllianceMemberRanks(allianceMemberRanks);

		return alliance;
	}

	public Alliance getOrCreateAlliance(String alliancename)
	{
		if(!alliances.containsKey(alliancename))
			alliances.put(alliancename,
					createAlliance(alliancename, defaultContactAuthorities, defaultAllianceAuthorities, defaultAllianceMemberRanks));
		return alliances.get(alliancename);
	}
}
