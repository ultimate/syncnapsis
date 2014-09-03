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
