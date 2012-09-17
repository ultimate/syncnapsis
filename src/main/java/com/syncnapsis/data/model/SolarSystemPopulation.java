package com.syncnapsis.data.model;

import java.util.Date;

import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.enums.EnumDestructionType;

public class SolarSystemPopulation extends ActivatableInstance<Long>
{
	protected SolarSystemInfrastructure	infrastructure;

	protected Participant				participant;

	protected Date						colonizationDate;

	protected Date						destuctionDate;

	protected EnumDestructionType		destructionType;

	protected int						population;

	protected int						populationMax;

	protected Date						populationMaxDate;
}
