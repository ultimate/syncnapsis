package com.syncnapsis.data.dao;

import java.util.Date;

import com.syncnapsis.data.dao.hibernate.SolarSystemPopulationDaoHibernate;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.enums.EnumPopulationPriority;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ SolarSystemPopulationDao.class, SolarSystemPopulationDaoHibernate.class })
public class SolarSystemPopulationDaoTest extends GenericDaoTestCase<SolarSystemPopulation, Long>
{
	private ParticipantDao					participantDao;
	private SolarSystemInfrastructureDao	solarSystemInfrastructureDao;
	private SolarSystemPopulationDao		solarSystemPopulationDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = solarSystemPopulationDao.getAll().get(0).getId();

		SolarSystemPopulation solarSystemPopulation = new SolarSystemPopulation();
		solarSystemPopulation.setAttackPriority(EnumPopulationPriority.balanced);
		solarSystemPopulation.setBuildPriority(EnumPopulationPriority.balanced);
		solarSystemPopulation.setColonizationDate(new Date(timeProvider.get()));
		solarSystemPopulation.setDestructionDate(new Date(timeProvider.get()));
		solarSystemPopulation.setDestructionType(EnumDestructionType.destroyed);
		solarSystemPopulation.setInfrastructure(solarSystemInfrastructureDao.getAll().get(0));
		solarSystemPopulation.setParticipant(participantDao.getAll().get(0));
		solarSystemPopulation.setPopulation(200);
		// set individual properties here

		setEntity(solarSystemPopulation);

		setEntityProperty("population");
		setEntityPropertyValue(100);

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(solarSystemPopulationDao);
	}
	// insert individual Tests here
}
