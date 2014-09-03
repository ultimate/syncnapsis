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
package com.syncnapsis.security.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.syncnapsis.enums.EnumLogicalOperator;
import com.syncnapsis.security.Validator;
import com.syncnapsis.utils.BooleanUtil;

/**
 * Validator implementation that uses logical operations to determine the validation from multiple
 * other validators.<br>
 * If the list of validators is empty this Validator will return it's
 * {@link LogicalValidator#defaultState}
 * 
 * @see EnumLogicalOperator
 * 
 * @author ultimate
 * 
 */
public class LogicalValidator<T> implements Validator<T>
{
	/**
	 * The logical operator used.
	 */
	protected EnumLogicalOperator	operator;
	/**
	 * The List of Validators used to validate values.
	 */
	protected List<Validator<T>>	validators;

	/**
	 * The default state used, if the list of validators is empty.
	 */
	protected boolean				defaultState	= true;

	/**
	 * Construct a new LogicalValidator with the given logical operator.
	 * 
	 * @see EnumLogicalOperator
	 * @param operator - The logical operator used.
	 */
	public LogicalValidator(EnumLogicalOperator operator)
	{
		setOperator(operator);
		setValidators(new ArrayList<Validator<T>>(0));
	}

	/**
	 * The logical operator used.
	 * 
	 * @return operator
	 */
	public EnumLogicalOperator getOperator()
	{
		return operator;
	}

	/**
	 * The logical operator used.
	 * 
	 * @param operator - the EnumLogicalOperator
	 */
	public void setOperator(EnumLogicalOperator operator)
	{
		Assert.notNull(operator, "operator must nut be null!");
		this.operator = operator;
	}

	/**
	 * The List of Validators used to validate values.
	 * 
	 * @return validators
	 */
	public List<Validator<T>> getValidators()
	{
		return validators;
	}

	/**
	 * The List of Validators used to validate values.
	 * 
	 * @param validators - the List
	 */
	public void setValidators(List<Validator<T>> validators)
	{
		Assert.notNull(validators, "validators must not be null!");
		this.validators = validators;
	}

	/**
	 * The default state used, if the list of validators is empty.
	 * 
	 * @return defaultState
	 */
	public boolean getDefaultState()
	{
		return defaultState;
	}

	/**
	 * The default state used, if the list of validators is empty.
	 * 
	 * @param defaultState - the default state
	 */
	public void setDefaultState(boolean defaultState)
	{
		this.defaultState = defaultState;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.Validator#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(T value)
	{
		if(validators.size() == 0)
			return defaultState;
		boolean[] b = new boolean[validators.size()];
		int i = 0;
		for(Validator<T> v: validators)
		{
			b[i++] = v.isValid(value);
		}
		return BooleanUtil.logical(operator, b);
	}

}
