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
package com.syncnapsis.test;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.base.Identifiable;
import com.syncnapsis.tests.annotations.Untested;
import com.syncnapsis.utils.ReflectionsUtil;

@Untested
public class TestSomething
{
	public static void main(String[] args) throws Exception
	{	
		for(int i = 0; i < 20; i++)
			System.out.println((i*5)%7+1);
		
		for(byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++)
			System.out.println((b+256) % 256);
		
		ReflectionsUtil.getActualTypeArguments(Player.class, Identifiable.class); // warm up
		long start = System.nanoTime();
		Type[] t = ReflectionsUtil.getActualTypeArguments(Player.class, Identifiable.class);
		long end = System.nanoTime();
		System.out.println(t[0]);
		System.out.println(end-start);
		
		Map<Class<?>, Class<?>> idTypes = new HashMap<Class<?>, Class<?>>();
		idTypes.put(Player.class, (Class<?>) t[0]);
		idTypes.containsKey(Player.class); // warm up
		idTypes.get(Player.class); // warm up
		start = System.nanoTime();
		idTypes.containsKey(Player.class);
		Type t1 = idTypes.get(Player.class);
		end = System.nanoTime();
		System.out.println(t1);
		System.out.println(end-start);
	}
}
