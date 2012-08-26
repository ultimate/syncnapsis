package com.syncnapsis.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hilfsklasse um nachträglich Ressourcen dem Classpath hinzuzufügen
 */
public abstract class ClassPathUtil
{
	/**
	 * Logger-Instanz
	 */
	private static final Logger logger = LoggerFactory.getLogger(ClassPathUtil.class);

	/**
	 * Hilfsvariable für URLClassLoader.class.getDeclaredMethod("addURL", parameters);
	 */
	@SuppressWarnings("rawtypes")
	private static final Class[]	parameters	= new Class[] { URL.class };

	/**
	 * Füge eine Datei per Pfad zum Classpath hinzu
	 * 
	 * @param file - der Dateipfad
	 * @throws IOException - wenn die Datei nicht existiert oder der Dateiname ungültig ist
	 */
	public static void addFileToClassPath(String file) throws IOException
	{
		addURLToClassPath(new File(file).toURI().toURL());
	}

	/**
	 * Füge eine Datei per File zum Classpath hinzu
	 * 
	 * @param file - die Datei
	 * @throws IOException - wenn die Datei nicht existiert oder der Dateiname ungültig ist
	 */
	public static void addFileToClassPath(File f) throws IOException
	{
		addURLToClassPath(f.toURI().toURL());
	}

	/**
	 * Füge eine Ressource per URL zum Classpath hinzu
	 * 
	 * @param u - die Ressource als URL
	 * @throws IOException - wenn die Ressource nicht existiert oder ungültig ist
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addURLToClassPath(URL u) throws IOException
	{
		logger.info("adding URL to classpath: " + u);
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;

		Method method = null;
		boolean old = false;
		try
		{
			// HACK THE CLASSPATH ;-)
			method = sysclass.getDeclaredMethod("addURL", parameters);
			old = ReflectionsUtil.setAccessible(method, true);
			method.invoke(sysloader, new Object[] { u });
		}
		catch(NoSuchMethodException e)
		{
			e.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader: " + e.getMessage());
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader: " + e.getMessage());
		}
		catch(IllegalAccessException e)
		{
			e.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader: " + e.getMessage());
		}
		catch(InvocationTargetException e)
		{
			e.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader: " + e.getMessage());
		}
		finally
		{
			if(method != null)
				ReflectionsUtil.setAccessible(method, old);
		}
	}
}