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
