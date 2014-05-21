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
package com.syncnapsis.utils.constants;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

/**
 * @author ultimate
 * 
 */
@TestCoversClasses({Constant.class, StringConstant.class})
public class StringConstantTest extends LoggerTestCase
{
	@TestCoversMethods({ "define", "defineNull", "as*", "raw", "getName" })
	public void testDefine()
	{
		String name = "a name";
		StringConstant c = new StringConstant(name);

		assertEquals(name, c.getName());

		assertNull(c.raw());
		assertNull(c.asString());
		//@formatter:off
		try { c.asBoolean(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asByte(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asChar(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asDouble(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asFloat(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asInt(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asLong(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asShort(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		//@formatter:on

		String raw = "2";
		c.define(raw);
		assertEquals(raw, c.raw());
		assertEquals(raw, c.asString());
		assertEquals(true, c.asBoolean());
		assertEquals((byte) 2, c.asByte());
		assertEquals((char) 2, c.asChar());
		assertEquals(2.0, c.asDouble());
		assertEquals(2.0F, c.asFloat());
		assertEquals(2, c.asInt());
		assertEquals(2L, c.asLong());
		assertEquals((short) 2, c.asShort());

		raw = "abc";
		c.define(raw);
		assertEquals(raw, c.raw());
		assertEquals(raw, c.asString());
		//@formatter:off
		try { c.asBoolean(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asByte(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asChar(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asDouble(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asFloat(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asInt(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asLong(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		try { c.asShort(); fail("expected exception not occurred!"); } catch(NullPointerException e) { assertNotNull(e); }
		//@formatter:on
	}
}
