package com.syncnapsis.data.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.syncnapsis.data.model.base.SummaryRank;

/**
 * Rang-Objekt für die Bewertung von Spielern auf Basis von GenericRank.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "playerrank")
public class PlayerRank extends SummaryRank<Player>
{
}