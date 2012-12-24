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
package com.syncnapsis.utils.dev;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.utils.FileUtil;
import com.syncnapsis.utils.ReflectionsUtil;

public abstract class CodeGenerator
{
	public static final String	EXTENSION_JAVA					= ".java";
	public static final String	EXTENSION_TEMPLATE				= ".template";

	public static final String	DIRECTORY_MAIN					= "\\src\\main\\java";
	public static final String	DIRECTORY_TEST					= "\\src\\test\\java";

	private static final String	IDENTIFIER_PACKAGE_END			= ";";
	private static final String	IDENTIFIER_PACKAGE_SEPARATOR	= ".";
	private static final String	IDENTIFIER_FOLDER_SEPARATOR		= "\\";
	private static final String	IDENTIFIER_DATA_PACKAGE			= ".data";
	private static final String	IDENTIFIER_TEST_CLASS			= "Test";
	private static final String	IDENTIFIER_PACKAGE				= "package ";
	private static final String	IDENTIFIER_INTERFACE			= " interface ";
	private static final String	IDENTIFIER_CLASS				= " class ";
	private static final String	IDENTIFIER_END_1				= " ";
	private static final String	IDENTIFIER_END_2				= "<";

	public static final String	PLACEHOLDER_MODEL				= "${model}";
	public static final String	PLACEHOLDER_ENTITY				= "${entity}";
	public static final String	PLACEHOLDER_PACKAGE				= "${package}";
	public static final String	PLACEHOLDER_PKTYPE				= "${pkType}";

	public static void main(String[] args)
	{
		File projectDir = null;
		File templateDir = null;
		boolean overwrite = false;
		for(String arg : args)
		{
			if(arg.equals("-h"))
			{
				System.out.println("usage: java -cp %CP% CodeGenerator [-p=<projectDir>] [-t=<templateDir>] [-e=[overwrite|skip]]");
				System.out.println("  -h                    print this help");
				System.out.println("  -p=<projectDir>       path to the project directory (if not specified current directory will be used)");
				System.out.println("  -t=<templateDir>      path to the template directory (if not specified project directory will be used)");
				System.out.println("  -e=[overwrite|skip]   how to handle existing files (overwrite or skip, default=skip)");
				System.exit(0);
			}
			else if(arg.startsWith("-p="))
			{
				String dir = arg.substring(3);
				if(dir.startsWith("\"") && dir.endsWith("\""))
					dir = dir.substring(1, dir.length() - 1);
				projectDir = new File(arg.substring(3));
			}
			else if(arg.startsWith("-t="))
			{
				String dir = arg.substring(3);
				if(dir.startsWith("\"") && dir.endsWith("\""))
					dir = dir.substring(1, dir.length() - 1);
				templateDir = new File(arg.substring(3));
			}
			else if(arg.startsWith("-e="))
			{
				overwrite = arg.substring(3).equals("overwrite");
			}
		}

		if(projectDir == null)
			projectDir = new File("");
		if(templateDir == null)
			templateDir = projectDir;

		File mainDir = new File(projectDir.getAbsolutePath() + DIRECTORY_MAIN);
		File testDir = new File(projectDir.getAbsolutePath() + DIRECTORY_TEST);

		int generated = generateCode(mainDir, testDir, templateDir, overwrite);
		System.out.println("Total files generated: " + generated);
	}

	public static int generateCode(File mainDir, File testDir, File templateDir, boolean overwrite)
	{
		System.out.println("Generating code...");
		System.out.println("  main-src:       " + mainDir.getPath());
		System.out.println("  test-src:       " + testDir.getPath());
		System.out.println("  templates:      " + templateDir.getPath());
		System.out.println("  existing files: " + (overwrite ? "overwrite" : "skip"));

		System.out.println("loading templates...");
		Map<String, String> templates = loadTemplates(templateDir);
		if(templates.size() == 0)
		{
			System.out.println("  --> no templates found!");
			return 0;
		}
		else
		{
			for(String template : templates.keySet())
			{
				System.out.println("  - " + template);
			}
			System.out.println("  templates loaded: " + templates.size());
		}

		System.out.println("loading models...");
		Map<String, String> models = findModels(mainDir);
		if(templates.size() == 0)
		{
			System.out.println("  --> no models found!");
			return 0;
		}
		else
		{
			for(String model : models.keySet())
			{
				System.out.println("  - " + model);
			}
			System.out.println("  models loaded: " + models.size());
		}

		int generated = 0;

		System.out.println("generating code...");
		for(Entry<String, String> model : models.entrySet())
		{
			System.out.println("  " + model.getKey());
			generated += generateCodeForModel(model.getKey(), model.getValue(), templates, mainDir, testDir, overwrite);
		}

		return generated;
	}

	public static Map<String, String> loadTemplates(File directory)
	{
		Map<String, String> templates = new TreeMap<String, String>();
		List<File> files = FileUtil.listFilesIncludingSubfoldersAsList(directory);
		for(File file : files)
		{
			if(file.isDirectory())
				continue;
			if(file.getName().endsWith(EXTENSION_TEMPLATE))
			{
				String tmplt = file.getName().substring(0, file.getName().length() - EXTENSION_TEMPLATE.length());
				try
				{
					templates.put(tmplt, load(file));
				}
				catch(IOException e)
				{
					// System.out.println("Could not load template " + file.getName());
				}
			}
		}
		return templates;
	}

	public static Map<String, String> findModels(File directory)
	{
		Map<String, String> models = new TreeMap<String, String>();
		List<File> files = FileUtil.listFilesIncludingSubfoldersAsList(directory);
		for(File file : files)
		{
			if(file.isDirectory())
				continue;
			if(file.getName().endsWith(EXTENSION_JAVA))
			{
				int packStart = file.getAbsolutePath().lastIndexOf(DIRECTORY_MAIN) + DIRECTORY_MAIN.length() + 1;
				int packEnd = file.getAbsolutePath().lastIndexOf(IDENTIFIER_FOLDER_SEPARATOR) + 1;
				String pack = file.getAbsolutePath().substring(packStart, packEnd);
				String clss = pack.replace(IDENTIFIER_FOLDER_SEPARATOR, IDENTIFIER_PACKAGE_SEPARATOR)
						+ file.getName().substring(0, file.getName().length() - EXTENSION_JAVA.length());

				try
				{
					Class<?> cls = Class.forName(clss);
					if(BaseObject.class.isAssignableFrom(cls))
					{
						models.put(clss, load(file));
						// System.out.println("Model loaded: '" + clss + "'");
					}
				}
				catch(ClassNotFoundException e)
				{
					// System.out.println("Class '" + clss +
					// "' not found on classpath -> ignoring");
				}
				catch(IOException e)
				{
					// System.out.println("Could not load model file for '" + clss + "'");
				}
			}
		}
		return models;
	}

	public static Map<String, String> load(File directory, String extension, boolean recursive)
	{
		Map<String, String> files = new TreeMap<String, String>();
		for(File file : directory.listFiles())
		{
			if(file.isDirectory())
			{
				if(recursive)
					files.putAll(load(file, extension, recursive));
				else
					continue;
			}
			if(file.getName().endsWith(extension))
			{
				try
				{
					files.put(file.getName().substring(0, file.getName().length() - extension.length()), load(file));
				}
				catch(IOException e)
				{
					System.out.println("Error loading file: " + file.getAbsolutePath() + " - " + e.getMessage());
				}
			}
		}
		return files;
	}

	protected static String load(File file) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
		try
		{
			byte[] buf = new byte[1024];
			int i = 0;
			while((i = fis.read(buf)) != -1)
			{
				sb.append(new String(buf, 0, i));
			}
		}
		catch(IOException e)
		{
			throw e;
		}
		finally
		{
			if(fis != null)
				fis.close();
		}
		return sb.toString();
	}

	public static int generateCodeForModel(String modelName, String modelSrc, Map<String, String> templates, File mainDir, File testDir,
			boolean overwrite)
	{
		String model = modelName.substring(modelName.lastIndexOf(IDENTIFIER_PACKAGE_SEPARATOR) + 1);
		String entity = Character.toLowerCase(model.charAt(0)) + model.substring(1);
		String pckg;
		if(modelName.indexOf(IDENTIFIER_DATA_PACKAGE) != -1)
			pckg = modelName.substring(0, modelName.indexOf(IDENTIFIER_DATA_PACKAGE));
		else
			pckg = modelName.substring(0, modelName.lastIndexOf(IDENTIFIER_PACKAGE_SEPARATOR));
		String pkType = null;
		try
		{
			Class<?> cls = Class.forName(modelName);
			pkType = ((Class<?>) ReflectionsUtil.getActualTypeArguments(cls, BaseObject.class)[0]).getSimpleName();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			return 0;
		}

		Map<String, String> placeholderValues = new HashMap<String, String>();
		placeholderValues.put(PLACEHOLDER_MODEL, model);
		placeholderValues.put(PLACEHOLDER_ENTITY, entity);
		placeholderValues.put(PLACEHOLDER_PACKAGE, pckg);
		placeholderValues.put(PLACEHOLDER_PKTYPE, pkType);

		int generated = 0;
		String generatedCode;
		int start, end;
		String targetPackage;
		String targetClass;
		File baseDir;
		File targetFile;
		boolean exists;
		for(Entry<String, String> template : templates.entrySet())
		{
			generatedCode = applyTemplate(template.getValue(), placeholderValues);

			start = generatedCode.indexOf(IDENTIFIER_PACKAGE) + IDENTIFIER_PACKAGE.length();
			end = generatedCode.indexOf(IDENTIFIER_PACKAGE_END, start);
			targetPackage = generatedCode.substring(start, end);

			start = generatedCode.indexOf(IDENTIFIER_CLASS) + IDENTIFIER_CLASS.length();
			if(start == IDENTIFIER_CLASS.length() - 1)
				start = generatedCode.indexOf(IDENTIFIER_INTERFACE) + IDENTIFIER_INTERFACE.length();
			end = generatedCode.indexOf(IDENTIFIER_END_2, start);
			if(end == -1)
				end = generatedCode.indexOf(IDENTIFIER_END_1, start);
			else
				end = Math.min(end, generatedCode.indexOf(IDENTIFIER_END_1, start));
			targetClass = generatedCode.substring(start, end);

			System.out.print("    " + targetPackage + IDENTIFIER_PACKAGE_SEPARATOR + targetClass);

			baseDir = targetClass.endsWith(IDENTIFIER_TEST_CLASS) ? testDir : mainDir;
			targetFile = new File(baseDir.getAbsolutePath() + IDENTIFIER_FOLDER_SEPARATOR
					+ targetPackage.replace(IDENTIFIER_PACKAGE_SEPARATOR, IDENTIFIER_FOLDER_SEPARATOR) + IDENTIFIER_FOLDER_SEPARATOR + targetClass + EXTENSION_JAVA);

			exists = targetFile.exists();
			if(!exists || overwrite)
			{
				if(write(targetFile, generatedCode))
				{
					generated++;
					if(exists)
						System.out.println("\tOVERWRITTEN");
					else
						System.out.println("\tCREATED");
				}
				else
				{
					System.out.println("\tERROR");
				}
			}
			else
			{
				System.out.println("\tEXISTS");
			}
		}

		return generated;
	}

	public static String applyTemplate(String template, Map<String, String> placeholderValues)
	{
		String result = template;
		for(Entry<String, String> placeholderValue : placeholderValues.entrySet())
		{
			result = result.replace(placeholderValue.getKey(), placeholderValue.getValue());
		}
		return result;
	}

	protected static boolean write(File file, String content)
	{
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		OutputStream os = null;
		boolean success = true;
		try
		{
			os = new BufferedOutputStream(new FileOutputStream(file));
			os.write(content.getBytes());
			os.flush();
		}
		catch(IOException e)
		{
			success = false;
		}
		finally
		{
			if(os != null)
			{
				try
				{
					os.close();
				}
				catch(IOException e)
				{
				}
			}
		}
		return success;
	}
}
