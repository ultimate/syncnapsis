package com.syncnapsis.data.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.enums.EnumVictoryCondition;

@Entity
@Table(name = "match")
public class Match extends BaseObject<Long>
{
	protected String				name;

	protected Galaxy				galaxy;

	protected Player				creator;

	protected Date					creationDate;

	protected Date					startDate;

	protected Date					finishedDate;

	protected int					maxEmpires;

	protected int					minEmpires;

	// @Column(nullable = true, length = LENGTH_ENUM)
	// @Enumerated(value = EnumType.STRING)
	protected EnumVictoryCondition	victoryCondition;

	protected List<Participant>		participants;
}
