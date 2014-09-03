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
package com.syncnapsis.utils.constants;

import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.utils.constants.Constant;

/**
 * ConstantLoader with raw type String for loading parameters.
 * 
 * @author ultimate
 */
public class ParameterConstantLoader extends ConstantLoader<String>
{
	/**
	 * The ParameterManager
	 */
	protected ParameterManager	parameterManager;

	/**
	 * Construct a new ConstantLoader with raw type String for loading parameters.
	 * 
	 * @param parameterManager - the ParameterManager
	 */
	public ParameterConstantLoader(ParameterManager parameterManager)
	{
		super(String.class);
		this.parameterManager = parameterManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.data.ConstantLoader#load(com.syncnapsis.utils.data.Constant)
	 */
	@Override
	public void load(Constant<String> constant)
	{
		constant.define(parameterManager.getString(constant.getName()));
	}
}
