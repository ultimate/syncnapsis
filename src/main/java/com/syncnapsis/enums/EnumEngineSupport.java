package com.syncnapsis.enums;

/**
 * Enum for defining the support of children engines.
 * 
 * @author ultimate
 */
public enum EnumEngineSupport
{
	/**
	 * This engine requires children.
	 * It does not handle incoming connections itself but may be able to handle incoming connections
	 * as a fallback to the childrens handling.
	 */
	CHILDREN_REQUIRED,
	/**
	 * This engine does not support children.
	 * It handles incoming connections itself or redirects them to the parent engine.
	 * Engines not supporting children will require a service mapping either directly or indirectly
	 * via a parent.
	 */
	NOT_SUPPORTED;
}