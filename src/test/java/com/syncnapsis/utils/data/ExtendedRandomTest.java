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
package com.syncnapsis.utils.data;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.syncnapsis.enums.EnumGender;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.MathUtil;
import com.syncnapsis.utils.TimeZoneUtil;

@TestCoversClasses({ ExtendedRandom.class, DefaultData.class })
@TestExcludesMethods({ "toRegExpString", "nextDouble", "hashCode" })
public class ExtendedRandomTest extends LoggerTestCase
{
	private static final int			BOOLEAN_CYCLES	= 100000;
	private static final double			DELTA			= 0.01;

	private static final ExtendedRandom	random			= new ExtendedRandom();

	@TestCoversMethods({ "getSeed", "getInitialSeed" })
	public void testGetSeed() throws Exception
	{
		long seed = 1234;
		ExtendedRandom r1 = new ExtendedRandom(seed);
		ExtendedRandom r2 = new ExtendedRandom(seed);
		ExtendedRandom r3 = new ExtendedRandom();

		assertEquals(seed, r1.getInitialSeed());
		assertEquals(seed, r2.getInitialSeed());
		assertTrue(seed != r3.getInitialSeed());

		// the "random" initial seed used, when not providing a seed is scrambled in
		// java.lang.Random
		// before being set. Let's check wether reiniting a random with the scrambled seed results
		// in
		// an equal random
		assertEquals(r3.getInitialSeed(), new ExtendedRandom(r3.getInitialSeed()).getInitialSeed());

		assertTrue(r1.getSeed() == r2.getSeed());
		assertFalse(r1.getSeed() == r3.getSeed());

		r1.nextBoolean();
		assertFalse(r1.getSeed() == r2.getSeed());

		r2.nextBoolean();
		assertTrue(r1.getSeed() == r2.getSeed());
	}

	@TestCoversMethods({ "equals", "matchGaussian" })
	public void testEquals() throws Exception
	{
		long seed = 1234;
		ExtendedRandom r1 = new ExtendedRandom(seed);
		ExtendedRandom r2 = new ExtendedRandom(seed);
		ExtendedRandom r3 = new ExtendedRandom();

		assertTrue(r1.matchGaussian(r2));
		assertTrue(r1.matchGaussian(r3));
		assertTrue(r1.equals(r2));
		assertFalse(r1.equals(r3));

		r1.nextGaussian();
		assertFalse(r1.matchGaussian(r2));
		assertFalse(r1.matchGaussian(r3));
		assertFalse(r1.equals(r2));
		assertFalse(r1.equals(r3));

		r2.nextGaussian();
		assertTrue(r1.matchGaussian(r2));
		assertFalse(r1.matchGaussian(r3));
		assertTrue(r1.equals(r2));
		assertFalse(r1.equals(r3));
	}

	public void testNextString() throws Exception
	{
		String source = DefaultData.STRING_ASCII_LETTERS_LOWER + DefaultData.STRING_ASCII_LETTERS_UPPER;
		String regexp = "[" + source + "]+";
		String r;
		for(int i = 0; i < 20; i++)
		{
			r = random.nextString(10, source);
			assertTrue(r.matches(regexp));
		}
	}

	public void testNextDomain() throws Exception
	{
		String regexp = DefaultData.REGEXP_DOMAIN;
		String r;
		for(int i = 0; i < 20; i++)
		{
			r = random.nextDomain();
			assertTrue(r.matches(regexp));
		}
	}

	public void testNextEmail() throws Exception
	{
		String regexp = DefaultData.REGEXP_EMAIL;
		String r;
		for(int i = 0; i < 20; i++)
		{
			r = random.nextEmail(i + 20);
			assertTrue(r.matches(regexp));
		}
	}

	public void testNextBoolean() throws Exception
	{
		int t = 0;
		int f = 0;
		boolean b;

		for(int i = 0; i < BOOLEAN_CYCLES; i++)
		{
			b = random.nextBoolean();
			if(b)
				t++;
			else
				f++;
		}
		logger.debug("generated " + t + " x true and " + f + " x false");
		assertEquals(0.5, t / (double) (t + f), DELTA);
		assertEquals(0.5, f / (double) (t + f), DELTA);

		t = 0;
		f = 0;

		for(int i = 0; i < BOOLEAN_CYCLES; i++)
		{
			b = random.nextBoolean(3, 7);
			if(b)
				t++;
			else
				f++;
		}
		logger.debug("generated " + t + " x true and " + f + " x false");
		assertEquals(0.3, t / (double) (t + f), DELTA);
		assertEquals(0.7, f / (double) (t + f), DELTA);
	}

	public void testNextInt() throws Exception
	{
		int min, max, r;

		// min < max
		for(int i = -100; i <= 100; i++)
		{
			min = i - (int) (Math.random() * 20);
			max = i + (int) (Math.random() * 20);
			r = random.nextInt(min, max);
			assertTrue(r >= min);
			assertTrue(r <= max);
		}
		// min > max (swapped)
		for(int i = -100; i <= 100; i++)
		{
			max = i - (int) (Math.random() * 20);
			min = i + (int) (Math.random() * 20);
			r = random.nextInt(min, max);
			assertTrue(r >= max);
			assertTrue(r <= min);
		}

		r = random.nextInt(-1, -1);
		assertEquals(-1, r);

		r = random.nextInt(0, 0);
		assertEquals(0, r);

		r = random.nextInt(1, 1);
		assertEquals(1, r);
	}

	public void testNextLong() throws Exception
	{
		long min, max, r;

		// min < max
		for(long i = -100; i <= 100; i++)
		{
			min = i * Integer.MAX_VALUE - (long) (Math.random() * 20);
			max = i * Integer.MAX_VALUE + (long) (Math.random() * 20);
			r = random.nextLong(min, max);
			assertTrue(r >= min);
			assertTrue(r <= max);
		}
		// min > max (swapped)
		for(long i = -100; i <= 100; i++)
		{
			max = i * Integer.MAX_VALUE - (long) (Math.random() * 20);
			min = i * Integer.MAX_VALUE + (long) (Math.random() * 20);
			r = random.nextLong(min, max);
			assertTrue(r >= max);
			assertTrue(r <= min);
		}

		r = random.nextLong(-1, -1);
		assertEquals(-1, r);

		r = random.nextLong(0, 0);
		assertEquals(0, r);

		r = random.nextLong(1, 1);
		assertEquals(1, r);
	}

	public void testNextDate() throws Exception
	{
		Date from, until;
		Date r;
		for(int i = 0; i < 100; i++)
		{
			from = new Date(random.nextLong(0, System.currentTimeMillis() / 2));
			until = new Date(random.nextLong(System.currentTimeMillis() / 2, System.currentTimeMillis()));
			r = random.nextDate(from, until);
			assertTrue(from.before(r));
			assertTrue(until.after(r));
		}
	}

	public void testNextEnum() throws Exception
	{
		int male = 0;
		int female = 0;
		int transsexual = 0;
		int machine = 0;
		int unknown = 0;

		int total = BOOLEAN_CYCLES;

		EnumGender r;
		for(int i = 0; i < total; i++)
		{
			r = random.nextEnum(EnumGender.class);
			if(r == EnumGender.male)
				male++;
			else if(r == EnumGender.female)
				female++;
			else if(r == EnumGender.transsexual)
				transsexual++;
			else if(r == EnumGender.machine)
				machine++;
			else if(r == EnumGender.unknown)
				unknown++;
			else
				fail("unexpected enum value: " + r);
		}

		double prob = 1 / (double) EnumGender.values().length;

		assertEquals(prob, male / (double) total, DELTA);
		assertEquals(prob, female / (double) total, DELTA);
		assertEquals(prob, transsexual / (double) total, DELTA);
		assertEquals(prob, machine / (double) total, DELTA);
		assertEquals(prob, unknown / (double) total, DELTA);
	}

	public void testNextTimeZoneId() throws Exception
	{
		String r;
		TimeZone t;
		for(int i = 0; i < 100; i++)
		{
			r = random.nextTimeZoneId();
			t = TimeZoneUtil.getTimeZone(r);
			assertNotNull(t);
		}
	}

	public void testNextEntry()
	{
		Integer[] array = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		List<Integer> list = Arrays.asList(array);

		Integer entry;
		for(int i = 0; i < 100; i++)
		{
			entry = random.nextEntry(array);
			// use the list instead of the array since array has no contains(..)
			assertTrue(list.contains(entry));

			entry = random.nextEntry(list);
			assertTrue(list.contains(entry));
		}
	}

	public void testNextPerm() throws Exception
	{
		int[] result, result2;

		int length = 10;
		long seed1 = 1234;
		long seed2 = 5678;
		ExtendedRandom random11 = new ExtendedRandom(seed1);
		ExtendedRandom random12 = new ExtendedRandom(seed1);
		ExtendedRandom random2 = new ExtendedRandom(seed2);

		result = random11.nextPerm(length);
		logger.debug("perm = " + arrayPrint(result));
		assertEquals(length, result.length);
		boolean[] found = new boolean[length];
		for(int p : result)
			found[p] = true;
		for(boolean f : found)
			if(!f)
				fail("index not existing in array!");

		result2 = random12.nextPerm(length);
		logger.debug("perm2 = " + arrayPrint(result2));
		assertTrue(arrayEquals(result, result2));

		result2 = random2.nextPerm(length);
		logger.debug("perm2 = " + arrayPrint(result2));
		assertFalse(arrayEquals(result, result2));

		int[] original = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		for(int i = 0; i < length * 2; i++)
			logger.debug("perm #" + i + " = " + arrayPrint(MathUtil.applyPerm(original, result)));

		// check reoccurring of numbers
		Map<Integer, Integer> reoccurrences = new HashMap<Integer, Integer>();
		for(int i = 0; i < 100; i++)
		{
			int[] perm = random2.nextPerm(10);
			int[] arr = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			int reoccurred;
			int count = 0;
			do
			{
				count++;
				reoccurred = 0;
				MathUtil.applyPerm(arr, perm);
				for(int j = 0; j < arr.length; j++)
				{
					if(j == arr[j])
						reoccurred++;
				}
			} while(reoccurred < arr.length);
			if(reoccurrences.get(count) == null)
				reoccurrences.put(count, 1);
			else
				reoccurrences.put(count, reoccurrences.get(count) + 1);
		}
		logger.debug("" + reoccurrences);
		// when using perm a cycle varies in length
		assertFalse(1 == reoccurrences.size());
	}

	public void testNextPerm2() throws Exception
	{
		int[] result, result2;

		int length = 10;
		long seed1 = 1234;
		long seed2 = 5678;
		ExtendedRandom random11 = new ExtendedRandom(seed1);
		ExtendedRandom random12 = new ExtendedRandom(seed1);
		ExtendedRandom random2 = new ExtendedRandom(seed2);

		result = random11.nextPerm2(length);
		logger.debug("perm = " + arrayPrint(result));
		assertEquals(length, result.length);
		boolean[] found = new boolean[length];
		for(int p : result)
			found[p] = true;
		for(boolean f : found)
			if(!f)
				fail("index not existing in array!");

		result2 = random12.nextPerm2(length);
		logger.debug("perm2 = " + arrayPrint(result2));
		assertTrue(arrayEquals(result, result2));

		result2 = random2.nextPerm2(length);
		logger.debug("perm2 = " + arrayPrint(result2));
		assertFalse(arrayEquals(result, result2));

		int[] original = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		for(int i = 0; i < length * 2; i++)
			logger.debug("perm #" + i + " = " + arrayPrint(MathUtil.applyPerm(original, result)));
		// check reoccurring of numbers
		Map<Integer, Integer> reoccurrences = new HashMap<Integer, Integer>();
		for(int i = 0; i < 100; i++)
		{
			int[] perm = random2.nextPerm2(10);
			int[] arr = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			int reoccurred;
			int count = 0;
			do
			{
				count++;
				reoccurred = 0;
				MathUtil.applyPerm(arr, perm);
				for(int j = 0; j < arr.length; j++)
				{
					if(j == arr[j])
						reoccurred++;
				}
			} while(reoccurred < perm.length);
			if(reoccurrences.get(count) == null)
				reoccurrences.put(count, 1);
			else
				reoccurrences.put(count, reoccurrences.get(count) + 1);
		}
		logger.debug("" + reoccurrences);
		// when using perm2 a cycle is always of length 10
		assertEquals(1, reoccurrences.size());
		assertEquals(100, (int) reoccurrences.get(10));
	}

	private String arrayPrint(int[] arr)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < arr.length; i++)
		{
			if(i > 0)
				sb.append(", ");
			sb.append(arr[i]);
		}
		sb.append("]");
		return sb.toString();
	}

	private boolean arrayEquals(int[] a1, int[] a2)
	{
		if(a1.length != a2.length)
			return false;
		for(int i = 0; i < a1.length; i++)
			if(a1[i] != a2[i])
				return false;
		return true;
	}
}
