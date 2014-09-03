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
package com.syncnapsis.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Util-Klasse, die das Suchen von Klassen innerhalb eines Packages anhand einer
 * gegebenen Superklasse erm�glicht. Dies ist z.B. hilfreich um entweder alle
 * Klassen oder nur Klassen eines bestimmten Typs herauszufinden. Die Suche
 * arbeitet dabei auf dem Dateisystem und durchsucht alle *.class-Dateien.
 * 
 * @author ultimate
 */
public abstract class ClassUtil
{
	/**
	 * Sucht alle Subklassen von 'superClass' im gegebenen Package an der durch
	 * die URL spezifizierten Ressource. Auf diese Weise k�nnen auch jar-Archive
	 * durchsucht werden.
	 * 
	 * @param <T> - der Typ der Superklasse
	 * @param url - die Ressource an der sich das Package befindet
	 * @param packageName - der Name des Packages
	 * @param superClass - die Superklasse
	 * @return die Liste der gefundenen Klassen
	 */
	public static <T> List<Class<T>> findClasses(URL url, String packageName, Class<T> superClass)
	{
		List<Class<T>> classes = new ArrayList<Class<T>>();

		String file = url.getFile();
		file = file.replace("%20", " ");

		if(!url.toString().startsWith("jar"))
		{
			File directory = new File(file);
			if(directory.exists())
			{
				// Get the list of the files contained in the package and all
				// subpackages
				String[] files = FileUtil.listFilesIncludingSubfoldersAsString(directory);
				for(int i = 0; i < files.length; i++)
				{
					Class<T> cls = getClassFromPath(packageName + "." + files[i]);
					if(isSubclass(superClass, cls))
						classes.add(cls);
				}
			}
		}
		else
		{
			String packageJarEntryName = file.substring(file.indexOf("!") + 2);
			String jarFileName = file.substring(0, file.indexOf("!"));
			if(jarFileName.startsWith("file:/"))
				jarFileName = jarFileName.substring("file:/".length());

			File jarFile = new File(jarFileName);
			JarFile jar = null;
			try
			{
				jar = new JarFile(jarFile);
				Enumeration<JarEntry> jes = jar.entries();
				while(jes.hasMoreElements())
				{
					JarEntry je = jes.nextElement();
					if(je.getName().startsWith(packageJarEntryName))
					{
						// entry is inside the package or a subpackage
						Class<T> cls = getClassFromPath(je.getName());
						if(isSubclass(superClass, cls))
							classes.add(cls);
					}
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(jar != null)
				{
					try
					{
						jar.close();
					}
					catch(IOException e)
					{
						// ignore
					}
				}
			}
		}
		return classes;
	}

	/**
	 * Sucht alle Subklassen von 'superClass' im gegebenen Package. Dabei wird
	 * die Default-Ressource f�r das Package nachgeschlagen.
	 * 
	 * @see ClassUtil#findClasses(URL, String, Class)
	 * @param <T> - der Typ der Superklasse
	 * @param packageName - der Name des Packages
	 * @param superClass - die Superklasse
	 * @return die Liste der gefundenen Klassen
	 */
	public static <T> List<Class<T>> findClasses(String packageName, Class<T> superClass)
	{
		// refactor packageName from '.' to '/'
		String fileName = packageName.replace('.', '/');

		URL url = superClass.getClassLoader().getResource(fileName);

		return findClasses(url, packageName, superClass);
	}

	/**
	 * Sucht alle Subklassen von 'superClass' im gegebenen Package innerhalb
	 * einer jar-Datei. Dabei werden alle Pfade innerhalb des jar-Archivs als
	 * Ressourcen betrachtet.
	 * 
	 * @see ClassUtil#findClasses(URL, String, Class)
	 * @param <T> - der Typ der Superklasse
	 * @param File - das jar-Archiv
	 * @param superClass - die Superklasse
	 * @return die Liste der gefundenen Klassen
	 */
	public static <T> List<Class<T>> findClasses(File jarFile, Class<T> superClass)
	{
		List<Class<T>> classes = new ArrayList<Class<T>>();

		JarFile jar = null;
		try
		{
			jar = new JarFile(jarFile);

			Enumeration<JarEntry> jes = jar.entries();
			while(jes.hasMoreElements())
			{
				JarEntry je = jes.nextElement();

				// cut off / or \ at the end
				String name = je.getName();
				if(name.endsWith("/") || name.endsWith("\\"))
					name = name.substring(0, name.length() - 1);

				// ignore META-INF folder
				if(name.equalsIgnoreCase("META-INF"))
					continue;
				// only handle main folders no subfolders
				// subfolders contain / or \
				if(name.contains("/"))
					continue;
				if(name.contains("\\"))
					continue;
				URL url = new URL("jar:file:/" + jarFile.getAbsolutePath() + "!/" + name);
				classes.addAll(findClasses(url, name, superClass));
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(jar != null)
			{
				try
				{
					jar.close();
				}
				catch(IOException e)
				{
					// ignore
				}
			}
		}

		return classes;
	}

	/**
	 * Pr�ft, ob eine Klasse Subklasse einer anderen Klasse ist.
	 * 
	 * @see Class#isAssignableFrom(Class)
	 * @param superClass - die Superklasse
	 * @param cls - die zu pr�fende Klasse
	 * @return true oder false
	 */
	public static boolean isSubclass(Class<?> superClass, Class<?> cls)
	{
		if(superClass == null)
			throw new IllegalArgumentException("superClass must not be null!");
		return (cls != null) && (superClass.isAssignableFrom(cls));
	}

	/**
	 * Check wether a class is a Number Class (including primitives)
	 * 
	 * @param cls - the class to check
	 * @return true or false
	 */
	public static boolean isNumber(Class<?> cls)
	{
		if(Number.class.isAssignableFrom(cls))
			return true;
		if(cls == int.class)
			return true;
		if(cls == long.class)
			return true;
		if(cls == double.class)
			return true;
		if(cls == float.class)
			return true;
		if(cls == byte.class)
			return true;
		if(cls == short.class)
			return true;
		return false;
	}

	/**
	 * L�dt eine Klasse mit Class.forName(..) anhand eines Dateipfads. Dabei
	 * wird der Pfad zun�chst in einem Packagenamen umgewandelt. Die Methode
	 * gibt null zur�ck, wenn die Klasse nicht geladen werden konnte.
	 * 
	 * @param <T> - der Typ der Klasse
	 * @param path - der Pfad zur Class-Datei
	 * @return das Class-Objekt
	 */
	@SuppressWarnings("unchecked")
	protected static <T> Class<T> getClassFromPath(String path)
	{
		// we are only interested in .class files
		if(path.endsWith(".class"))
		{
			// removes the .class extension
			String classname = path.substring(0, path.length() - 6);
			classname = classname.replace("/", ".");
			classname = classname.replace("\\", ".");
			try
			{
				Class<T> cls = (Class<T>) Class.forName(classname);
				return cls;
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch(NoClassDefFoundError e)
			{
			}
		}
		return null;
	}
}