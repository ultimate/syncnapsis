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
package com.syncnapsis.tests;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.tests.annotations.Untested;
import com.syncnapsis.utils.ClassUtil;
import org.slf4j.LoggerFactory;

public class CoverageTestCase extends LoggerTestCase
{
	private static final String	generalPackage	= "com.syncnapsis";
	private static final String	mainClassesDir	= "target/classes/";
	private static final String	testClassesDir	= "target/test-classes/";
	private static Class<?>		modelClass;

	static
	{
		try
		{
			modelClass = Class.forName(generalPackage + ".data.model.base.Model");
			LoggerFactory.getLogger(CoverageTestCase.class).info("Model-Class loaded!");
		}
		catch(ClassNotFoundException e)
		{
			LoggerFactory.getLogger(CoverageTestCase.class).info("No Model-Class found!");
		}
	}

	private List<Class<?>>		specialClasses	= new LinkedList<Class<?>>();
	private List<String>		specialMethods	= new LinkedList<String>();

	protected void addSpecialClass(Class<?> cls)
	{
		this.specialClasses.add(cls);
	}

	protected void addSpecialMethod(String m)
	{
		this.specialMethods.add(m);
	}

	public void testCoverage() throws Exception
	{
		performCoverageTest(generalPackage);
	}

	public void performCoverageTest(String packageName) throws Exception
	{
		Map<String, List<Class<TestCase>>> testAssociations = loadAssociations(packageName);

		File classesDir = new File(getDir(mainClassesDir, packageName));
		List<Class<Object>> allClasses = ClassUtil.findClasses(classesDir.toURI().toURL(), packageName, Object.class);

		int classCounter = 0;
		int classCoveragePartial = 0;
		int classCoverageFull = 0;
		int classCoverageIgnored = 0;
		int methodCounter = 0;
		int methodCoverage = 0;

		List<Class<TestCase>> testClasses;
		List<String> excludedMethods;
		Method[] testMethods;
		Method[] methods;
		boolean coverage;
		boolean testFound;

		for(Class<Object> cls : allClasses)
		{
			classCounter++;

			testClasses = testAssociations.get(cls.getName());

			if(ignoreSpecialClass(packageName, cls) != null)
			{
				logger.info(cls.getName() + " -> ignoring because of special reason: " + ignoreSpecialClass(packageName, cls));
				classCoverageIgnored++;
				continue;
			}
			else if(testClasses == null || testClasses.size() == 0)
			{
				logger.warn(cls.getName() + " -> no Test-Class found");
				continue;
			}

			logger.info(cls.getName() + " -> Test-Class found");
			coverage = true;
			excludedMethods = new LinkedList<String>();

			for(Class<TestCase> testCls : testClasses)
			{
				if(testCls.isAnnotationPresent(TestExcludesMethods.class))
				{
					for(String excludedMethod : testCls.getAnnotation(TestExcludesMethods.class).value())
					{
						excludedMethods.add(excludedMethod);
					}
				}

				testMethods = testCls.getDeclaredMethods();
				methods = cls.getDeclaredMethods();

				for(Method m : methods)
				{
					methodCounter++;
					if(ignoreSpecialMethod(m) != null)
					{
						logger.info(m.getName() + " -> ignoring because of special reason: " + ignoreSpecialMethod(m));
						methodCoverage++;
						continue;
					}
					if(isExcludedMethod(m, excludedMethods))
					{
						logger.info(cls.getName() + " -> " + m.getName() + "()" + " -> excluded");
						methodCoverage++;
						continue;
					}

					testFound = false;

					for(Method tm : testMethods)
					{
						if(tm.isAnnotationPresent(TestCoversMethods.class))
						{
							for(String method : tm.getAnnotation(TestCoversMethods.class).value())
							{
								if(matchesMethodName(m, method))
								{
									testFound = true;
									break;
								}
							}
							if(testFound)
								break;
						}
						else
						{
							if(matchesMethodName(m, getTestetMethod(tm)))
							{
								testFound = true;
								break;
							}
						}
					}

					if(testFound)
					{
						logger.info(cls.getName() + " -> " + m.getName() + "()" + " -> Test found");
						methodCoverage++;
					}
					else
					{
						logger.warn(cls.getName() + " -> " + m.getName() + "()" + " -> no Test found");
					}
					coverage = coverage && testFound;
				}
			}
			if(coverage)
				classCoverageFull++;
			else
				classCoveragePartial++;
		}

		logger.info("Class-Coverage  ->" + " classes: " + classCounter + " (full: " + classCoverageFull + " partial: " + classCoveragePartial
				+ " uncovered: " + (classCounter - (classCoverageFull + classCoveragePartial + classCoverageIgnored)) + " ignored: "
				+ classCoverageIgnored +
				// " total: " + (classCoverageFull + classCoveragePartial + classCoverageIgnored) +
				")");
		logger.info("Method-Coverage -> " + " methods: " + methodCounter + " covered: " + methodCoverage);
		boolean fail = false;
		String message = "";
		try
		{
			assertEquals(classCounter, classCoverageFull + classCoveragePartial + classCoverageIgnored);
		}
		catch(AssertionFailedError e)
		{
			fail = true;
			message += "\nNot all classes covered!";
		}
		try
		{
			assertEquals(methodCounter, methodCoverage);
		}
		catch(AssertionFailedError e)
		{
			fail = true;
			message += "\nNot all methods covered!";
		}
		if(fail)
		{
			fail(message);
		}
	}

	private Map<String, List<Class<TestCase>>> loadAssociations(String packageName) throws MalformedURLException
	{
		Map<String, List<Class<TestCase>>> testAssociations = new TreeMap<String, List<Class<TestCase>>>();

		File classesDir = new File(getDir(testClassesDir, packageName));
		List<Class<TestCase>> allTestClasses = ClassUtil.findClasses(classesDir.toURI().toURL(), packageName, TestCase.class);

		for(Class<TestCase> testCls : allTestClasses)
		{
			if(Modifier.isAbstract(testCls.getModifiers()))
			{
				logger.info(testCls.getName() + " -> ignored: abstract");
				continue;
			}

			Class<?>[] coveredClasses;
			Class<?> coveredClass;

			if(testCls.isAnnotationPresent(TestCoversClasses.class))
			{
				coveredClasses = testCls.getAnnotation(TestCoversClasses.class).value();
			}
			else
			{
				try
				{
					coveredClass = Class.forName(testCls.getName().substring(0, testCls.getName().length() - 4));
					coveredClasses = new Class<?>[1];
					coveredClasses[0] = coveredClass;
				}
				catch(Exception e)
				{
					coveredClasses = new Class<?>[0];
				}
			}
			for(Class<?> cls : coveredClasses)
			{
				if(testAssociations.get(cls.getName()) == null)
					testAssociations.put(cls.getName(), new LinkedList<Class<TestCase>>());
				testAssociations.get(cls.getName()).add(testCls);
			}
		}

		return testAssociations;
	}

	protected String ignoreSpecialClass(String packageName, Class<?> c)
	{
		if(c.isInterface())
			return "Interface";
		if(c.isAnnotation())
			return "Annotation";
		if(c.isEnum())
			return "Enum";
		if(c.getName().contains("$"))
			return "Inner class";
		if(TestCase.class.isAssignableFrom(c))
			return "TestCase-Class";
		if(c.isAnnotationPresent(Untested.class))
			return "Untested (by annotation)";
		if(this.specialClasses.contains(c))
			return "Special-Class-Ignore-List";
		if(c.getName().startsWith(packageName + ".constants"))
			return "Constants-Class";
		if(modelClass != null && modelClass.isAssignableFrom(c))
			return "Model-Class";
		if(c.getName().startsWith(packageName + ".mock"))
			return "Mock-class";

		return null;
	}

	protected String ignoreSpecialMethod(Method m)
	{
		if(m.getName().startsWith("access$"))
			return "Internal access Method";
		if(m.getName().equals("readObject") && m.getParameterTypes().length == 1 && m.getParameterTypes()[0].equals(ObjectInputStream.class))
			return "Serializable Method";
		if(m.getName().equals("writeObject") && m.getParameterTypes().length == 1 && m.getParameterTypes()[0].equals(ObjectOutputStream.class))
			return "Serializable Method";
		if(m.isAnnotationPresent(Untested.class))
			return "Untested (by annotation)";
		if(m.getName().startsWith("$SWITCH_TABLE$"))
			return "Enum-Switch-Table";
		if(this.specialMethods.contains(m.getName()))
			return "Special-Method-Ignore-List";
		return null;
	}

	private boolean isExcludedMethod(Method m, List<String> excludes)
	{
		for(String ex : excludes)
		{
			if(matchesMethodName(m, ex))
				return true;
		}
		return false;
	}

	private String getTestetMethod(Method m)
	{
		return m.getName().substring(4, 5).toLowerCase() + m.getName().substring(5);
	}

	private boolean matchesMethodName(Method m, String name)
	{
		if(name.equals(m.getName()))
			return true;

		if(name.contains("*"))
		{
			if(name.indexOf("*") == name.lastIndexOf("*"))
			{
				if(m.getName().startsWith(name.substring(0, name.indexOf("*"))) && m.getName().endsWith(name.substring(name.indexOf("*") + 1)))
					return true;
			}
			else
			{
				logger.warn("only one wildcard (*) is allowed: " + name);
			}
		}

		return false;
	}

	protected String getDir(String classesDir, String packageName)
	{
		return classesDir + packageName.replace(".", "/");
	}
}
