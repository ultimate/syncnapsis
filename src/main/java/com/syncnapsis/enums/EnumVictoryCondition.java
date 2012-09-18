package com.syncnapsis.enums;

/**
 * Enum representing differing victory conditions for matches.<br>
 * The condition is used to decide when a match is finished and a winner can be determined.
 * 
 * @author ultimate
 */
public enum EnumVictoryCondition
{
	/**
	 * Rule over xx% of the galaxy for a specified duration
	 */
	domination,
	/**
	 * Destroy all populations/colonies of all other participants
	 */
	extermination,
	/**
	 * Destroy the populations/colonies of your specified randomly chosen rivals
	 */
	vendetta,
	// some more?
}
