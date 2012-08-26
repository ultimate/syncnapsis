package com.syncnapsis.utils.serialization;

import java.util.Map;

import com.syncnapsis.exceptions.DeserializationException;
import com.syncnapsis.exceptions.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

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
		Map<String, Object> result = deserialize(serialization);
		return (T) mapper.merge(entity, result, authorities);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Serializer#deserialize(java.lang.Object, java.lang.Class,
	 * java.lang.Object[])
	 */
	@Override
	public <T> T deserialize(F serialization, Class<T> entityClass, Object... authorities) throws DeserializationException
	{
		try
		{
			T entity = entityClass.newInstance();
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
	 * Serialize the given Map.
	 * Called by {@link BaseSerializer#serialize(Mapable, Object)}
	 * 
	 * @param map - the Map to serialize
	 * @return the serialization of the Map
	 * @throws SerializationException - if serialization fails
	 */
	public abstract F serialize(Object prepared) throws SerializationException;

	/**
	 * Deserialize the Map represented by the given serialization.
	 * 
	 * @param serialization - the serialization of the Map
	 * @return the deserialized Map
	 * @throws DeserializationException - if deserialization fails
	 */
	public abstract Map<String, Object> deserialize(F serialization) throws DeserializationException;
}
