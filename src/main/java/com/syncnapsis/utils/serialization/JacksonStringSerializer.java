package com.syncnapsis.utils.serialization;

import java.io.IOException;
import java.util.Map;

import com.syncnapsis.exceptions.DeserializationException;
import com.syncnapsis.exceptions.SerializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Jackson Implementation of MapableSerializer being capable of serializing Maps or Mapable Objects
 * to JSON and vice-versa.
 * 
 * @author ultimate
 */
public class JacksonStringSerializer extends BaseSerializer<String> implements InitializingBean
{
	/**
	 * The ObjectWriter used for Serialization
	 */
	private ObjectWriter	writer;
	/**
	 * The ObjectReader used for Deserialization
	 */
	private ObjectReader	reader;

	/**
	 * Construct a new JacksonStringSerializer with a default ObjectMapper.
	 * 
	 * @see JacksonStringSerializer#JacksonStringSerializer(ObjectMapper)
	 * @see BaseSerializer#BaseSerializer()
	 */
	public JacksonStringSerializer()
	{
		this(new ObjectMapper());
	}

	/**
	 * Construct a new JacksonStringSerializer with the given ObjectMapper used to create the
	 * required ObjectReader and ObjectWriter.
	 * 
	 * @see JacksonStringSerializer#JacksonStringSerializer(ObjectWriter, ObjectReader)
	 * @see BaseSerializer#BaseSerializer()
	 * @param mapper - the ObjectMapper
	 */
	public JacksonStringSerializer(ObjectMapper mapper)
	{
		this(mapper.writer(), mapper.reader());
	}

	/**
	 * Construct a new JacksonStringSerializer with the given ObjectReader and ObjectWriter used for
	 * (De)-Serialization.
	 * 
	 * @see BaseSerializer#BaseSerializer()
	 * @param writer - The ObjectWriter used for Serialization
	 * @param reader - The ObjectReader used for Deserialization
	 */
	public JacksonStringSerializer(ObjectWriter writer, ObjectReader reader)
	{
		super();
		this.writer = writer;
		this.reader = reader.withType(new TypeReference<Map<String, Object>>() {});
	}

	/**
	 * The ObjectWriter used for Serialization
	 * 
	 * @return - the writer
	 */
	public ObjectWriter getWriter()
	{
		return writer;
	}

	/**
	 * The ObjectWriter used for Serialization
	 * 
	 * @param writer - the ObjectWriter
	 */
	public void setWriter(ObjectWriter writer)
	{
		this.writer = writer;
	}

	/**
	 * The ObjectReader used for Deserialization
	 * 
	 * @return the reader
	 */
	public ObjectReader getReader()
	{
		return reader;
	}

	/**
	 * The ObjectReader used for Deserialization
	 * 
	 * @param reader - the ObjectReader
	 */
	public void setReader(ObjectReader reader)
	{
		this.reader = reader;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(this.writer, "writer must not be null!");
		Assert.notNull(this.reader, "reader must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.BaseSerializer#serialize(java.lang.Object)
	 */
	@Override
	public String serialize(Object prepared) throws SerializationException
	{
		try
		{
			return writer.writeValueAsString(prepared);
		}
		catch(JsonGenerationException e)
		{
			throw new SerializationException("JsonGenerationException", e);
		}
		catch(JsonMappingException e)
		{
			throw new SerializationException("JsonMappingException", e);
		}
		catch(IOException e)
		{
			throw new SerializationException("IOException", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.BaseSerializer#deserialize(java.lang.Object)
	 */
	@Override
	public Map<String, Object> deserialize(String serialization) throws DeserializationException
	{
		try
		{
			return reader.readValue(serialization);
		}
		catch(JsonParseException e)
		{
			throw new DeserializationException("JsonParseException", e);
		}
		catch(JsonMappingException e)
		{
			throw new DeserializationException("JsonMappingException", e);
		}
		catch(IOException e)
		{
			throw new DeserializationException("IOException", e);
		}
	}
}
