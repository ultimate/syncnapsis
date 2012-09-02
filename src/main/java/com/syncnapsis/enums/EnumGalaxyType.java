package com.syncnapsis.enums;

/**
 * Enum für die Spezifizierung eines Galaxie-Typs. Die Galaxie-Typen sind den
 * folgenden Quellen entnommen. 'x' steht dabei für einen Platzhalter, der bei
 * der Generierung durch Parameter ersetzt wird.
 * http://de.wikipedia.org/wiki/Galaxie
 * http://upload.wikimedia.org/wikipedia/commons/8/8a/Hubble_sequence_photo.png
 * 
 * @author ultimate
 */
public enum EnumGalaxyType
{
	/**
	 * Elliptische Galaxien
	 */
	Ex,
	/**
	 * Lentikuläre (linsenförmige) Galaxien
	 */
	S0,
	/**
	 * Balkengalaxien (als Grundlage für SBx)
	 */
	SB0,
	/**
	 * Spiralgalaxien
	 */
	Sx,
	/**
	 * Balkenspiralgalaxien
	 */
	SBx,
	/**
	 * Irreguläre (unregelmäßige) Galaxien
	 */
	Rx,
	/**
	 * zusätzliche Bogensegmente für die Gestaltung von Galaxien
	 */
	Ax;
}
