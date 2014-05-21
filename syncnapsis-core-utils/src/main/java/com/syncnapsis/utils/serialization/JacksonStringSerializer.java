/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.utils.serialization;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.syncnapsis.exceptions.DeserializationException;
import com.syncnapsis.exceptions.SerializationException;

/**
 * Jackson Implementation of MapableSerializer being capable of serializing Maps or Mapable Objects
 * to JSON and vice-versa.
 * 
 * @author ultimate
 */
public class JacksonStringSerializer extends BaseSerializer<String> implements InitializingBean
{
	/**
	 * The start token for array serializations
	 */
	public static final String	ARRAY_START		= "[";
	/**
	 * For deserializing array using a map-Type-Serializer a surrounding dummy map is required.
	 * This is the key used for the array in the map.
	 */
	public static final String	DUMMY_KEY		= "value";
	/**
	 * For deserializing array using a map-Type-Serializer a surrounding dummy map is required.
	 * This is the start token for the map.
	 */
	public static final String	DUMMY_MAP_START	= "{\"" + DUMMY_KEY + "\":";
	/**
	 * For deserializing array using a map-Type-Serializer a surrounding dummy map is required.
	 * This is the end token for the map.
	 */
	public static final String	DUMMY_MAP_END	= "}";
	/**
	 * The ObjectWriter used for Serialization
	 */
	private ObjectWriter		writer;
	/**
	 * The ObjectReader used for Deserialization
	 */
	private ObjectReader		reader;
	/**
	 * The ObjectReader used for Deserialization of Entities and Maps
	 */
	private ObjectReader		mapReader;

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
		this.setWriter(writer);
		this.setReader(reader);
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
		this.mapReader = reader.withType(new TypeReference<Map<String, Object>>() {});
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
	@SuppressWarnings("unchecked")
	@Override
	public Object deserialize(String serialization) throws DeserializationException
	{
		try
		{
			if(serialization.trim().startsWith(ARRAY_START))
				return ((Map<String, Object>) mapReader.readValue(DUMMY_MAP_START + serialization + DUMMY_MAP_END)).get(DUMMY_KEY);
			else
				return mapReader.readValue(serialization);
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
