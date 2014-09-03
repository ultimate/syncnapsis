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
package com.syncnapsis.utils.serialization;

import java.lang.reflect.Array;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.exceptions.DeserializationException;
import com.syncnapsis.exceptions.SerializationException;

/**
 * Abstract base for Serializers.
 * This Serializer will prepare entities using SerializationUtil before serialization regarding the
 * given authority. On Deserialization a Map is created from the serialization an afterwards coverted
 * (in)to the entity.
 * 
 * @see Serializer
 * @author ultimate
 * @param <F> - the Type of the Format that is used for (de)-serialization
 */
public abstract class BaseSerializer<F> implements Serializer<F>, InitializingBean
{
	/**
	 * Logger-Instance
	 */
	protected final Logger	logger	= LoggerFactory.getLogger(getClass());

	/**
	 * The Mapper used to transform entities to maps and vice versa.
	 */
	protected Mapper			mapper;

	/**
	 * Construct a new Serializer.
	 */
	public BaseSerializer()
	{
		this.mapper = new BaseMapper();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Serializer#getMapper()
	 */
	@Override
	public Mapper getMapper()
	{
		return mapper;
	}

	/**
	 * The Mapper used to transform entities to maps and vice versa.
	 * 
	 * @param mapper - the Mapper
	 */
	public void setMapper(Mapper mapper)
	{
		this.mapper = mapper;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(mapper, "mapper must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Serializer#serialize(java.lang.Object, java.lang.Object[])
	 */
	@Override
	public F serialize(Object entity, Object... authorities) throws SerializationException
	{
		return serialize(mapper.prepare(entity, authorities));
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Serializer#deserialize(java.lang.Object, java.lang.Object,
	 * java.lang.Object[])
	 */
	@Override
	public <T> T deserialize(F serialization, T entity, Object... authorities) throws DeserializationException
	{
		if(entity == null)
			throw new NullPointerException();
		Object result = deserialize(serialization);
		return (T) mapper.merge(entity, result, authorities);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Serializer#deserialize(java.lang.Object, java.lang.Class,
	 * java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(F serialization, Class<T> entityClass, Object... authorities) throws DeserializationException
	{
		try
		{
			T entity;
			if(!entityClass.isArray())
				entity = entityClass.newInstance();
			else
				entity = (T) Array.newInstance(entityClass.getComponentType(), 0);
			return deserialize(serialization, entity, authorities);
		}
		catch(InstantiationException e)
		{
			throw new DeserializationException("InstantiationException", e);
		}
		catch(IllegalAccessException e)
		{
			throw new DeserializationException("IllegalAccessException", e);
		}
	}

	/**
	 * Serialize the given entity.
	 * Called by {@link BaseSerializer#serialize(Mapable, Object)}
	 * 
	 * @param prepared - the entity to serialize
	 * @return the serialization of the entity
	 * @throws SerializationException - if serialization fails
	 */
	public abstract F serialize(Object prepared) throws SerializationException;

	/**
	 * Deserialize the entity represented by the given serialization.
	 * 
	 * @param serialization - the serialization of the entity
	 * @return the deserialized entity
	 * @throws DeserializationException - if deserialization fails
	 */
	public abstract Object deserialize(F serialization) throws DeserializationException;
}
