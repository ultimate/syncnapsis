package com.syncnapsis.exec.dev;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.syncnapsis.utils.FileUtil;

public abstract class Assembler extends DevUtil
{
	public static final String jarGroupName = null;
	
	public static final List<String> excludedExtensions = Arrays.asList(new String[] {".zip"});
	
	public static void main(String[] args)
	{
		Map<String, File> modules = findModules();
		logger.info(modules.size() + " modules found: ");
		for(Entry<String, File> module: modules.entrySet())
		{
			logger.info("--> " + module.getKey() + " (" + module.getValue().getAbsolutePath() + ")");
		}
		copyRequiredModules(modules);
	}
	
	public static void copyRequiredModules(Map<String, File> modulesFound)
	{		
		File docBase = getDocBase();
		File lib = new File(docBase.getAbsolutePath() + "/WEB-INF/lib");
		String moduleName;
		for(File jarFile: lib.listFiles())
		{
			if(!jarFile.getName().endsWith(".jar"))
				continue;
			moduleName = getModuleName(jarFile);
			if(modulesFound.containsKey(moduleName))
			{
				copyRequiredModule(modulesFound.get(moduleName));
			}			
		}
	}
	
	public static void copyRequiredModule(File module)
	{
		logger.info("copying module: " + module.getName());
		
		File docBase = getDocBase();

		File classesFrom = new File(module.getAbsolutePath() + "/target/classes");
		File classesTo = new File(docBase.getAbsolutePath() + "/WEB-INF/classes");
		
		if(classesFrom.exists())
		{
			copyFiles(classesFrom, classesTo);
		}

		File webappFrom = new File(module.getAbsolutePath() + "/src/main/webapp");
		File webappTo = docBase;
		
		if(webappFrom.exists())
		{
			copyFiles(webappFrom, webappTo);
		}
	}
	
	public static void copyFiles(File source, File target)
	{
		if(!source.exists())
			return;
		if(!target.exists())
			target.mkdirs();
		
		File childTarget;
		for(File childSource: source.listFiles())
		{
			childTarget = new File(target.getAbsolutePath() + "/" + childSource.getName()); 
			if(childSource.isHidden())
				continue;
			if(childSource.isDirectory())
			{
				copyFiles(childSource, childTarget);
			}
			else
			{
				if(excludedExtensions.contains(FileUtil.getExtension(childSource)))
					continue;
				try
				{
					FileUtil.copyFile(childSource, childTarget);
				}
				catch(IOException e)
				{
					logger.error(e.getMessage());
				}
			}
		}
	}
	
	public static String getModuleName(File jarFile)
	{
		String name = jarFile.getName();
		for(int i = 0; i < name.length()-1; i++)
		{
			if((name.charAt(i) == '-') && Character.isDigit(name.charAt(i+1)))
			{
				name = name.substring(0, i);
				break;
			}
		}
		if(jarGroupName != null && name.startsWith(jarGroupName + "-"))
			name = name.substring(jarGroupName.length()+1);
		return name;
	}
	
	public static Map<String, File> findModules()
	{
		File base = new File("").getAbsoluteFile().getParentFile();
		return findModules(base);
	}
	
	public static Map<String, File> findModules(File file)
	{
		Map<String, File> modules = new TreeMap<String, File>();
		for(File child: file.listFiles())
		{
			if(child.isDirectory())
			{
				if(isModule(child))
				{
					modules.put(child.getName(), child);
				}
				else
				{
					modules.putAll(findModules(child));
				}
			}
		}
		return modules;
	}
	
	public static boolean isModule(File file)
	{
		if(!file.isDirectory())
			return false;
		boolean pomFound = false;
		boolean srcFound = false;
		for(File child: file.listFiles())
		{
			if(child.isDirectory())
			{
				if(child.getName().equals("src"))
					srcFound = true;				
			}
			else
			{
				if(child.getName().equals("pom.xml"))
					pomFound = true;				
			}
		}
		return pomFound && srcFound;
	}
}
