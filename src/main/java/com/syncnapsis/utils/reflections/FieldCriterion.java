/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
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
package com.syncnapsis.utils.reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Interface zum prüfen von Fields in ReflectionsUtil.findFields.
 * Anhand der Methode isValidField kann geprüft werden, ob es sich um ein gültiges Field handelt. Im
 * Normalfall müssten dazu Getter und Setter vorhanden sein.
 * 
 * @author ultimate
 */
public abstract class FieldCriterion
{
	/**
	 * Prüft obe ein Field gültig ist. Es werden die gefundenen Getter und Setter übergeben um
	 * prüfen zu können, ob diese vorhanden sind oder z.B. geforderte Annotations aufweisen.
	 * 
	 * @see FieldCriterion#isValidField(com.syncnapsis.utils.reflections.Field)
	 * @param field - das Field das Field, Getter und Setter enthält
	 * @return true oder false
	 */
	public boolean isValidField(com.syncnapsis.utils.reflections.Field field)
	{
		return isValidField(field.getField(), field.getGetter(), field.getSetter());
	}

	/**
	 * Prüft obe ein Field gültig ist. Es werden die gefundenen Getter und Setter übergeben um
	 * prüfen zu können, ob diese vorhanden sind oder z.B. geforderte Annotations aufweisen.
	 * 
	 * @param field - das Field
	 * @param getter - die Get-Methode
	 * @param setter - die Set-Methode
	 * @return true oder false
	 */
	public abstract boolean isValidField(Field field, Method getter, Method setter);

	/**
	 * Default FieldCriterion, das nur prüft ob Get- und Set-Methode vorhanden sind.
	 */
	public static final FieldCriterion	DEFAULT	= new FieldCriterion() {
													/*
													 * (non-Javadoc)
													 * @see
													 * com.syncnapsis.utils.reflections.FieldCriterion
													 * #
													 * isValidField(java.lang.reflect.Field,
													 * java.lang.reflect.Method,
													 * java.lang.reflect.Method)
													 */
													@Override
													public boolean isValidField(Field field, Method getter, Method setter)
													{
														if(Modifier.isStatic(field.getModifiers()))
															return false;
														return getter != null && setter != null;
													}
												};

	/**
	 * FieldCriterion, das prüft ob typische Annotations für Spalten in einer Entity vorhanden sind:
	 * 
	 * @see Column
	 * @see OneToMany
	 * @see OneToOne
	 * @see ManyToOne
	 * @see ManyToMany
	 */
	public static final FieldCriterion	ENTITY	= new FieldCriterion() {
													/*
													 * (non-Javadoc)
													 * @see
													 * com.syncnapsis.utils.reflections.FieldCriterion
													 * #
													 * isValidField(java.lang.reflect.Field,
													 * java.lang.reflect.Method,
													 * java.lang.reflect.Method)
													 */
													@Override
													public boolean isValidField(Field field, Method getter, Method setter)
													{
														if(!DEFAULT.isValidField(field, getter, setter))
															return false;
														return getter.isAnnotationPresent(Column.class) || getter.isAnnotationPresent(Id.class)
																|| getter.isAnnotationPresent(OneToMany.class)
																|| getter.isAnnotationPresent(OneToOne.class)
																|| getter.isAnnotationPresent(ManyToOne.class)
																|| getter.isAnnotationPresent(ManyToMany.class);
													}
												};

	/**
	 * Default FieldCriterion, checking if the field is static
	 */
	public static final FieldCriterion	STATIC	= new FieldCriterion() {
													/*
													 * (non-Javadoc)
													 * @see
													 * com.syncnapsis.utils.reflections.FieldCriterion
													 * #
													 * isValidField(java.lang.reflect.Field,
													 * java.lang.reflect.Method,
													 * java.lang.reflect.Method)
													 */
													@Override
													public boolean isValidField(Field field, Method getter, Method setter)
													{
														return Modifier.isStatic(field.getModifiers());
													}
												};
	/**
	 * Default FieldCriterion, checking if the field is final
	 */
	public static final FieldCriterion	FINAL	= new FieldCriterion() {
													/*
													 * (non-Javadoc)
													 * @see
													 * com.syncnapsis.utils.reflections.FieldCriterion
													 * #
													 * isValidField(java.lang.reflect.Field,
													 * java.lang.reflect.Method,
													 * java.lang.reflect.Method)
													 */
													@Override
													public boolean isValidField(Field field, Method getter, Method setter)
													{
														return Modifier.isFinal(field.getModifiers());
													}
												};
}
