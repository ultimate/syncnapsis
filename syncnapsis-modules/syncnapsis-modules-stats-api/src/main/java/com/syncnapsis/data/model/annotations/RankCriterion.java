/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.data.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for marking criterions when subclassing Rank.<br>
 * Rank offers generic functionality to scan the subclass for criterions marked by this Annotation.
 * 
 * @author ultimate
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RankCriterion
{
	/**
	 * The default category
	 */
	public static final String	defaultCategory	= "main";

	/**
	 * Get the category for a rating criterion. The criterions are grouped in categories to be able
	 * to evaluate or display them together.
	 * 
	 * @return category
	 */
	String category() default defaultCategory;

	/**
	 * Marking of the primary criterion within a category. There should only be one primary
	 * criterion per category.
	 * 
	 * @return primary
	 */
	boolean primary() default false;

	/**
	 * Is this the average value of another criterion?
	 * 
	 * @return average
	 */
	boolean average() default false;

	/**
	 * The criterion this criterion is the average for.
	 * 
	 * @return averageFor
	 */
	String averageFor() default "";

	/**
	 * Marking of this criterion for the amount used for average calculation.
	 * 
	 * @return averageAmount
	 */
	boolean averageAmount() default false;
}
