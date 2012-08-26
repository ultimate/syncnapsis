package com.syncnapsis.enums;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.syncnapsis.tests.LoggerTestCase;

public class EnumTest extends LoggerTestCase
{
	public void testEnumDateFormat() throws Exception
	{
		logger.debug("testing EnumDateFormat...");

		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.YEAR, 2008);
		gc.set(Calendar.MONTH, 5);
		gc.set(Calendar.DAY_OF_MONTH, 14);
		gc.set(Calendar.HOUR_OF_DAY, 6);
		gc.set(Calendar.MINUTE, 27);
		gc.set(Calendar.SECOND, 35);
		gc.set(Calendar.MILLISECOND, 0);
		Date date1 = gc.getTime();
		gc.set(Calendar.HOUR_OF_DAY, 18);
		Date date2 = gc.getTime();
		String[][] dateS = new String[][] { new String[] { "14.06.2008 06:27:35", "14.06.2008 18:27:35" },
				new String[] { "14.06.2008 06:27:35 AM", "14.06.2008 06:27:35 PM" }, new String[] { "14/06/2008 06:27:35", "14/06/2008 18:27:35" },
				new String[] { "14/06/2008 06:27:35 AM", "14/06/2008 06:27:35 PM" }, new String[] { "06.14.2008 06:27:35", "06.14.2008 18:27:35" },
				new String[] { "06.14.2008 06:27:35 AM", "06.14.2008 06:27:35 PM" }, new String[] { "06/14/2008 06:27:35", "06/14/2008 18:27:35" },
				new String[] { "06/14/2008 06:27:35 AM", "06/14/2008 06:27:35 PM" }, new String[] { "2008.06.14 06:27:35", "2008.06.14 18:27:35" },
				new String[] { "2008.06.14 06:27:35 AM", "2008.06.14 06:27:35 PM" }, new String[] { "2008/06/14 06:27:35", "2008/06/14 18:27:35" },
				new String[] { "2008/06/14 06:27:35 AM", "2008/06/14 06:27:35 PM" }, new String[] { "2008-06-14 06:27:35", "2008-06-14 18:27:35" },
				new String[] { "2008-06-14 06:27:35 AM", "2008-06-14 06:27:35 PM" }, };

		EnumDateFormat edf;
		DateFormat df;
		for(int i = 0; i < EnumDateFormat.values().length; i++)
		{
			edf = EnumDateFormat.values()[i];
			logger.debug("... testing \"" + edf + "\"");
			df = edf.getDateFormat();
			assertNotNull(df);

			assertEquals(date1, df.parse(dateS[i][0]));
			assertEquals(date1.getTime(), df.parse(dateS[i][0]).getTime());
			assertEquals(date2, df.parse(dateS[i][1]));
			assertEquals(date2.getTime(), df.parse(dateS[i][1]).getTime());

			assertEquals(dateS[i][0], df.format(date1));
			assertEquals(dateS[i][1], df.format(date2));
		}
	}

	public void testEnumLocale() throws Exception
	{
		logger.debug("testing EnumLocale...");
		for(EnumLocale l : EnumLocale.values())
		{
			logger.debug("... testing \"" + l + "\"");
			assertNotNull(l.getLocale());
			assertTrue(l.getLocale() instanceof Locale);
			assertEquals(l.toString().toLowerCase(), l.getLocale().getLanguage());
		}
		
		assertEquals(EnumLocale.EN, EnumLocale.valueOfIgnoreCase("EN"));
		assertEquals(EnumLocale.EN, EnumLocale.valueOfIgnoreCase("en"));
		assertEquals(EnumLocale.DE, EnumLocale.valueOfIgnoreCase("DE"));
		assertEquals(EnumLocale.DE, EnumLocale.valueOfIgnoreCase("de"));
	}
}
