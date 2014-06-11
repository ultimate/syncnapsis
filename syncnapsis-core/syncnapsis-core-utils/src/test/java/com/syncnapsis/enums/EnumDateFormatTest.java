package com.syncnapsis.enums;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.syncnapsis.tests.LoggerTestCase;

public class EnumDateFormatTest extends LoggerTestCase
{
	public void testJSON() throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer();
		ObjectReader reader = mapper.reader().withType(POJO.class);

		EnumDateFormat df = EnumDateFormat.yMd24_minus;
		POJO p = new POJO();
		p.df = df;

		String expected = "{\"df\":\"" + df.toString() + "\"}";

		String result = writer.writeValueAsString(p);
		
		logger.debug("expected: " + expected);
		logger.debug("result:   " + result);

		assertEquals(expected, result);

		assertEquals(p, reader.readValue(result));
	}

	public static class POJO
	{
		public EnumDateFormat	df;

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((df == null) ? 0 : df.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			POJO other = (POJO) obj;
			if(df != other.df)
				return false;
			return true;
		}
	}
}
