package com.syncnapsis.data.model;

import java.util.Date;

import com.syncnapsis.data.model.base.BaseObject;

public class Colony extends BaseObject<Long>
{
	protected SolarSystem	solarSystem;

	protected Participant	participant;

	protected Date			colonizationDate;

	protected int			population;

	protected int			infrastructure;

	protected int			growthRate;		// calculated from defaultGrowthRate & connections?
}
