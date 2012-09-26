package com.syncnapsis.utils.data;

import java.util.Date;
import java.util.TimeZone;

import com.syncnapsis.enums.EnumGender;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.TimeZoneUtil;

@TestCoversClasses({ RandomData.class, DefaultData.class })
@TestExcludesMethods("toRegExpString")
public class RandomDataTest extends LoggerTestCase
{

	public void testRandomString() throws Exception
	{
		String source = DefaultData.STRING_ASCII_LETTERS_LOWER + DefaultData.STRING_ASCII_LETTERS_UPPER;
		String regexp = "[" + source + "]+";
		String r;
		for(int i = 0; i < 20; i++)
		{
			r = RandomData.randomString(10, source);
			assertTrue(r.matches(regexp));
		}
	}

	public void testRandomDomain() throws Exception
	{
		String regexp = DefaultData.REGEXP_DOMAIN;
		String r;
		for(int i = 0; i < 20; i++)
		{
			r = RandomData.randomDomain();
			assertTrue(r.matches(regexp));
		}
	}

	public void testRandomEmail() throws Exception
	{
		String regexp = DefaultData.REGEXP_EMAIL;
		String r;
		for(int i = 0; i < 20; i++)
		{
			r = RandomData.randomEmail(i + 20);
			assertTrue(r.matches(regexp));
		}
	}

	public void testRandomBoolean() throws Exception
	{
		double delta = 0.01;
		int t = 0;
		int f = 0;
		boolean b;

		for(int i = 0; i < 10000; i++)
		{
			b = RandomData.randomBoolean();
			if(b)
				t++;
			else
				f++;
		}
		logger.debug("generated " + t + " x true and " + f + " x false");
		assertEquals(0.5, t / (double) (t + f), delta);
		assertEquals(0.5, f / (double) (t + f), delta);

		t = 0;
		f = 0;

		for(int i = 0; i < 10000; i++)
		{
			b = RandomData.randomBoolean(3, 7);
			if(b)
				t++;
			else
				f++;
		}
		logger.debug("generated " + t + " x true and " + f + " x false");
		assertEquals(0.3, t / (double) (t + f), delta);
		assertEquals(0.7, f / (double) (t + f), delta);
	}

	public void testRandomInt() throws Exception
	{
		int min, max, r;

		// min < max
		for(int i = -100; i <= 100; i++)
		{
			min = i - (int) (Math.random() * 20);
			max = i + (int) (Math.random() * 20);
			r = RandomData.randomInt(min, max);
			assertTrue(r >= min);
			assertTrue(r <= max);
		}
		// min > max (swapped)
		for(int i = -100; i <= 100; i++)
		{
			max = i - (int) (Math.random() * 20);
			min = i + (int) (Math.random() * 20);
			r = RandomData.randomInt(min, max);
			assertTrue(r >= max);
			assertTrue(r <= min);
		}

		r = RandomData.randomInt(-1, -1);
		assertEquals(-1, r);

		r = RandomData.randomInt(0, 0);
		assertEquals(0, r);

		r = RandomData.randomInt(1, 1);
		assertEquals(1, r);
	}

	public void testRandomLong() throws Exception
	{
		long min, max, r;

		// min < max
		for(long i = -100; i <= 100; i++)
		{
			min = i * Integer.MAX_VALUE - (long) (Math.random() * 20);
			max = i * Integer.MAX_VALUE + (long) (Math.random() * 20);
			r = RandomData.randomLong(min, max);
			assertTrue(r >= min);
			assertTrue(r <= max);
		}
		// min > max (swapped)
		for(long i = -100; i <= 100; i++)
		{
			max = i * Integer.MAX_VALUE - (long) (Math.random() * 20);
			min = i * Integer.MAX_VALUE + (long) (Math.random() * 20);
			r = RandomData.randomLong(min, max);
			assertTrue(r >= max);
			assertTrue(r <= min);
		}

		r = RandomData.randomLong(-1, -1);
		assertEquals(-1, r);

		r = RandomData.randomLong(0, 0);
		assertEquals(0, r);

		r = RandomData.randomLong(1, 1);
		assertEquals(1, r);
	}

	public void testRandomDate() throws Exception
	{
		Date from, until;
		Date r;
		for(int i = 0; i < 100; i++)
		{
			from = new Date(RandomData.randomLong(0, System.currentTimeMillis() / 2));
			until = new Date(RandomData.randomLong(System.currentTimeMillis() / 2, System.currentTimeMillis()));
			r = RandomData.randomDate(from, until);
			assertTrue(from.before(r));
			assertTrue(until.after(r));
		}
	}

	public void testRandomEnum() throws Exception
	{
		int male = 0;
		int female = 0;
		int transsexual = 0;
		int machine = 0;
		int unknown = 0;

		int total = 10000;

		EnumGender r;
		for(int i = 0; i < total; i++)
		{
			r = RandomData.randomEnum(EnumGender.class);
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
		double delta = 0.01;

		assertEquals(prob, male / (double) total, delta);
		assertEquals(prob, female / (double) total, delta);
		assertEquals(prob, transsexual / (double) total, delta);
		assertEquals(prob, machine / (double) total, delta);
		assertEquals(prob, unknown / (double) total, delta);
	}

	public void testRandomTimeZoneId() throws Exception
	{
		String r;
		TimeZone t;
		for(int i = 0; i < 100; i++)
		{
			r = RandomData.randomTimeZoneId();
			t = TimeZoneUtil.getTimeZone(r);
			assertNotNull(t);
		}
	}
}
