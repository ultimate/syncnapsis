package com.syncnapsis.utils.reflections;

import java.util.List;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ReflectionsUtil;

@SuppressWarnings("unused")
@TestExcludesMethods({"toString", "get*" })
public class FieldTest extends LoggerTestCase
{
	@TestCoversMethods({"set", "get"})
	public void testField_valid() throws Exception
	{
		Object valid = new Object() {
			private int	x;

			public int getX()
			{
				return x;

			}

			public void setX(int x)
			{
				this.x = x;
			}
		};

		List<Field> fields = ReflectionsUtil.findDefaultFields(valid.getClass());
		assertEquals(1, fields.size());

		Field f = fields.get(0);
		
		int value = (int) (Math.random()*1000);
		f.set(valid, value);
		assertEquals(value, f.get(valid));
	}

	public void testField_invalid() throws Exception
	{
		Object valid = new Object() {
			private int	x;

			public String getX()
			{
				return "" + x;

			}

			public void setX(int x)
			{
				this.x = x;
			}
		};

		List<Field> fields = ReflectionsUtil.findDefaultFields(valid.getClass());
		assertEquals(0, fields.size());
	}
}
