package com.syncnapsis.utils.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.util.Assert;

import com.syncnapsis.utils.TimeZoneUtil;

/**
 * Klasse zum Erzeugen zufälliger Werte für Standard-Datentypen. Das erzeugen
 * zufälliger Modell-Objekte übernimmt RandomModels.
 * 
 * @author ultimate
 */
public abstract class RandomData
{
	/**
	 * Zentrales Random-Objekt für die Erzeugung von Zufallszahlen
	 */
	private static Random	random	= new Random();

	/**
	 * Erzeugt einen zufälligen String der geforderten Länge unter Verwendung
	 * des vorgegeben Zeichensatzes.
	 * 
	 * @param length - die geforderte Länge des Strings
	 * @param source - der vorgegebene Zeichensatz
	 * @return ein zufälliger String
	 */
	public static String randomString(int length, String source)
	{
		Assert.isTrue(length >= 0, "length must be >= 0");
		Assert.hasLength(source, "source must not be empty");
		StringBuilder sb = new StringBuilder();
		Random rand = random;
		for(int i = 0; i < length; i++)
		{
			sb.append(source.charAt(rand.nextInt(source.length())));
		}
		return sb.toString();
	}

	/**
	 * Erzeuge eine zufällige gültige Domain.
	 * 
	 * @return eine zufällige gültige Domain
	 */
	public static String randomDomain()
	{
		String topLevelDomain;
		if(randomBoolean())
			topLevelDomain = DefaultData.STRING_TOP_LEVEL_DOMAIN_EXTENSIONS_LIST.get(randomInt(0,
					DefaultData.STRING_TOP_LEVEL_DOMAIN_EXTENSIONS_LIST.size() - 1));
		else
			topLevelDomain = randomString(2, DefaultData.STRING_ASCII_LETTERS_LOWER + DefaultData.STRING_ASCII_LETTERS_UPPER);

		String domain = randomString(1, DefaultData.STRING_ASCII_LETTERS_LOWER + DefaultData.STRING_ASCII_LETTERS_UPPER
				+ DefaultData.STRING_ASCII_NUMBERS)
				+ randomString(randomInt(0, 256 - topLevelDomain.length() - 1), DefaultData.STRING_ASCII_LETTERS_LOWER
						+ DefaultData.STRING_ASCII_LETTERS_UPPER + DefaultData.STRING_ASCII_NUMBERS + "-")
				+ randomString(1, DefaultData.STRING_ASCII_LETTERS_LOWER + DefaultData.STRING_ASCII_LETTERS_UPPER + DefaultData.STRING_ASCII_NUMBERS);

		return domain + "." + topLevelDomain;
	}

	/**
	 * Erzeuge eine zufällige gültige e-Mail-Adresse.
	 * 
	 * @param maxLength - the maximum length of the e-Mail
	 * @return eine zufällige gültige e-Mail-Adresse
	 */
	public static String randomEmail(int maxLength)
	{
		int domainLengthReservated = 10;
		int l1 = randomInt(1, maxLength - domainLengthReservated);
		int l2 = randomInt(-10, maxLength - domainLengthReservated - l1);
		int l3 = randomInt(-10, maxLength - domainLengthReservated - l1 - (l2 < 0 ? 0 : l2));
		int l4 = maxLength - domainLengthReservated - l1 - (l2 < 0 ? 0 : l2) - (l3 < 0 ? 0 : l3);

		String s1 = null;
		String s2 = null;
		String s3 = null;
		String s4 = null;

		s1 = randomString(l1, DefaultData.STRING_EMAIL_COMPLETE_NO_DOT_NO_AT);
		if(l2 > 0)
			s2 = randomString(l2, DefaultData.STRING_EMAIL_COMPLETE_NO_DOT_NO_AT);
		if(l3 > 0)
			s3 = randomString(l3, DefaultData.STRING_EMAIL_COMPLETE_NO_DOT_NO_AT);
		if(l4 > 0)
			s4 = randomString(l4, DefaultData.STRING_EMAIL_COMPLETE_NO_DOT_NO_AT);

		String name = s1 + (l2 > 0 ? "." + s2 : "") + (l3 > 0 ? "." + s3 : "") + (l4 > 0 ? "." + s4 : "");
		String domain;
		do
		{
			domain = randomDomain();
		} while(domain.length() + 1 + name.length() > maxLength);

		return name + "@" + domain;
	}

	/**
	 * Erzeugt einen zufälligen boolschen Wert mit 50%-Wahrscheinlichkeit für
	 * true und false
	 * 
	 * @return ein zufälligen boolscher Wert
	 */
	public static boolean randomBoolean()
	{
		return randomBoolean(1, 1);
	}

	/**
	 * Erzeugt einen zufälligen boolschen Wert gegebener
	 * Wahrscheinlichkeitsverteilung. Für die Berechnung der Warhscheinlichkeit
	 * werden die angegeben relativen Verteilungswerte ins Verhältnis zu ihrer
	 * Summe gesetzt:
	 * p(true) = probabilityTrue/(probabilityTrue + probabilityFalse)
	 * p(false) = probabilityFalse/(probabilityTrue + probabilityFalse)
	 * 
	 * @return ein zufälligen boolscher Wert
	 */
	public static boolean randomBoolean(int probabilityTrue, int probabilityFalse)
	{
		Assert.isTrue(probabilityTrue >= 0, "probabilityTrue must be >= 0");
		Assert.isTrue(probabilityFalse >= 0, "probabilityFalse must be >= 0");
		int val = randomInt(-probabilityFalse, probabilityTrue - 1);
		return val >= 0;
	}

	/**
	 * Erzeute eine zufällige Ganzzahl als Integer im Interval [min; max].<br>
	 * Falls min und max vertauscht werden, werden die Werte intern vertauscht um das Interval zu
	 * bilden.
	 * 
	 * @param min - die untere Grenze des Intervals
	 * @param max - die obere Grenze des Intervals
	 * @return ein zufälliger Integer
	 */
	public static int randomInt(int min, int max)
	{
		if(min == max)
			return min;
		if(min < max)
			return random.nextInt(max - min + 1) + min;
		else
			return random.nextInt(min - max + 1) + max;
	}

	/**
	 * Erzeute eine zufällige Ganzzahl als Long im Interval [min; max].<br>
	 * Falls min und max vertauscht werden, werden die Werte intern vertauscht um das Interval zu
	 * bilden.
	 * 
	 * @param min - die untere Grenze des Intervals
	 * @param max - die obere Grenze des Intervals
	 * @return ein zufälliger Long
	 */
	public static long randomLong(long min, long max)
	{
		if(min == max)
			return min;
		if(min < max)
			return ((long) (random.nextDouble() * (max - min + 1)) + min);
		else
			return ((long) (random.nextDouble() * (min - max + 1)) + max);
	}

	/**
	 * Erzeugt ein zufälliges Datum in einem Zeitbereich. Die Methode verwendet
	 * randomLong:
	 * return new Date(randomLong(from.getTime(), until.getTime()));
	 * 
	 * @param from - Anfang des Zeitbereichs
	 * @param until - Ende des Zeitbereichs
	 * @return ein zufälliges Datum
	 */
	public static Date randomDate(Date from, Date until)
	{
		return new Date(randomLong(from.getTime(), until.getTime()));
	}

	/**
	 * Erzeugt einen zufälligen Enum-Wert zu einer gegebenen Enum-Class
	 * 
	 * @return ein zufälliger Enum-Wert
	 */
	@SuppressWarnings("unchecked")
	public static <T> T randomEnum(Class<T> cls)
	{
		Assert.isTrue(cls.isEnum(), "Class must be Enum-Class");
		try
		{
			Method valuesMethod = cls.getMethod("values");
			T[] values = (T[]) valuesMethod.invoke(null);
			int i = randomInt(0, values.length - 1);
			return values[i];
		}
		catch(NoSuchMethodException e)
		{
			throw new IllegalArgumentException("values() not found for " + cls.getName());
		}
		catch(IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Could not invoke values() for " + cls.getName() + ": " + e.getMessage());
		}
		catch(IllegalAccessException e)
		{
			throw new IllegalArgumentException("Could not invoke values() for " + cls.getName() + ": " + e.getMessage());
		}
		catch(InvocationTargetException e)
		{
			throw new IllegalArgumentException("Could not invoke values() for " + cls.getName() + ": " + e.getMessage());
		}
	}

	/**
	 * Erzeugt eine zufällige TimeZoneId für die Verwendung mit TimeZoneUtil.
	 * Die TimeZoneId besteht aus Region und ID
	 * 
	 * @return eine zufällige TimeZoneId
	 */
	public static String randomTimeZoneId()
	{
		List<String> regions = TimeZoneUtil.getRegions();
		int r = randomInt(0, regions.size() - 1);
		List<String> ids = TimeZoneUtil.getIdsByRegions(regions.get(r));
		int i = randomInt(0, ids.size() - 1);
		return regions.get(r) + "/" + ids.get(i);
	}
}
