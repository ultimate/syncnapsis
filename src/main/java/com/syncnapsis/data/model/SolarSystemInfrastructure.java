package com.syncnapsis.data.model;

import java.util.Date;
import java.util.List;

import com.syncnapsis.data.model.base.ActivatableInstance;

public class SolarSystemInfrastructure extends ActivatableInstance<Long>
{
	protected SolarSystem					solarSystem;

	protected Date							colonizationDate;

	protected int							infrastructure;

	protected List<SolarSystemPopulation>	populations;
}