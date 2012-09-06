package com.syncnapsis.data.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.base.SummaryRank;

/**
 * Rang-Objekt für die Bewertung von Imperien auf Basis von GenericRank.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "alliancerank")
public class AllianceRank extends SummaryRank<Alliance>
{
}
