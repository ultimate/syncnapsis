package com.syncnapsis.data.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "participant")
public class Participant
{
	protected Match				match;

	protected Empire			empire;

	protected int				rank;

	protected Date				joinDate;

	protected Date				defeatedDate;

	protected List<Participant>	rivals; // for EnumVictoryCondition.vendetta
	
	protected List<SolarSystem>	ownedSystems;

	protected List<SolarSystem>	disputedSystems;

	protected List<Colony>		colonies;
}
