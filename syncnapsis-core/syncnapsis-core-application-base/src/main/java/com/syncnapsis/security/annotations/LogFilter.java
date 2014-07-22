package com.syncnapsis.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation may be used if arguments should be filtered for the RPC logging
 * 
 * @author ultimate
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogFilter
{
	/**
	 * A list of arguments to filter
	 */
	int[] filteredArgs();
}
