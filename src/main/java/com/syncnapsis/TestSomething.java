package com.syncnapsis;

import java.util.List;

import com.syncnapsis.utils.ReflectionsUtil;
import com.syncnapsis.utils.reflections.Field;
import com.syncnapsis.utils.serialization.BaseMapper;

public class TestSomething
{
	public static void main(String[] args) throws SecurityException, NoSuchFieldException, NoSuchMethodException
	{
		
		List<Field> fields = ReflectionsUtil.findFields(TestEntity.class, BaseMapper.SERIALIZABLE);

		for(Field f : fields)
		{
			System.out.println(f);
		}
	}
}
