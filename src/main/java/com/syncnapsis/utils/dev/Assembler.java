package com.syncnapsis.utils.dev;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.syncnapsis.utils.FileUtil;

public class Assembler
{
	public static final List<String>	excludedExtensions	= Arrays.asList(new String[] { ".zip" });

	protected transient final Logger	logger				= LoggerFactory.getLogger(getClass());

	protected File						parentDir			= null;
	protected File						webappDir			= null;

	protected String					jarNamePrefix		= null;

	public Assembler(File parentDir, File webappDir)
	{
		this(parentDir, webappDir, null);
	}

	public Assembler(File parentDir, File webappDir, String jarNamePrefix)
	{
		super();
		Assert.notNull(parentDir, "parentDir must not be null!");
		Assert.isTrue(parentDir.exists(), "parentDir not existing!");
		Assert.notNull(webappDir, "webappDir must not be null!");
		Assert.isTrue(webappDir.exists(), "webappDir not existing!");
		this.parentDir = parentDir;
		this.webappDir = webappDir;
		this.jarNamePrefix = jarNamePrefix;
	}

	public String getJarNamePrefix()
	{
		return jarNamePrefix;
	}

	public File getWebappDir()
	{
		return webappDir;
	}

	public void copyRequiredModules()
	{
		copyRequiredModules(findModules());
	}

	public void copyRequiredModules(Map<String, File> modulesFound)
	{
		File lib = new File(webappDir.getAbsolutePath() + "/WEB-INF/lib");
		String moduleName;
		for(File jarFile : lib.listFiles())
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

	public void copyRequiredModule(File module)
	{
		logger.info("copying module: " + module.getName());

		File classesFrom = new File(module.getAbsolutePath() + "/target/classes");
		File classesTo = new File(webappDir.getAbsolutePath() + "/WEB-INF/classes");

		if(classesFrom.exists())
		{
			copyFiles(classesFrom, classesTo);
		}

		File webappFrom = new File(module.getAbsolutePath() + "/src/main/webapp");

		if(webappFrom.exists())
		{
			copyFiles(webappFrom, webappDir);
		}
	}

	public void copyFiles(File source, File target)
	{
		if(!source.exists())
			return;
		if(!target.exists())
			target.mkdirs();

		File childTarget;
		for(File childSource : source.listFiles())
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

	public String getModuleName(File jarFile)
	{
		String name = jarFile.getName();
		for(int i = 0; i < name.length() - 1; i++)
		{
			if((name.charAt(i) == '-') && Character.isDigit(name.charAt(i + 1)))
			{
				name = name.substring(0, i);
				break;
			}
		}
		if(jarNamePrefix != null && name.startsWith(jarNamePrefix + "-"))
			name = name.substring(jarNamePrefix.length() + 1);
		return name;
	}

	public Map<String, File> findModules()
	{
		return findModules(parentDir);
	}

	public Map<String, File> findModules(File file)
	{
		Map<String, File> modules = new TreeMap<String, File>();
		for(File child : file.listFiles())
		{
			if(child.isDirectory())
			{
				if(isModule(child))
				{
					modules.put(child.getName(), child);
					logger.info("module found: " + child.getName());
				}
				else
				{
					modules.putAll(findModules(child));
				}
			}
		}
		return modules;
	}

	public boolean isModule(File file)
	{
		if(!file.isDirectory())
			return false;
		boolean pomFound = false;
		boolean srcFound = false;
		for(File child : file.listFiles())
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

	public static void main(String[] args)
	{
		Assembler a = null;
		if(args.length == 2)
			a = new Assembler(new File(args[0]), new File(args[1]));
		else if(args.length == 3)
			a = new Assembler(new File(args[0]), new File(args[1]), args[2]);
		else
		{
			System.out.println("usage:");
			System.out.println("  java -cp [cp] Assembler [parentDir] [webappDir]");
			System.out.println("or");
			System.out.println("  java -cp [cp] Assembler [parentDir] [webappDir] [jarNamePrefix]");
			System.exit(0);
		}
		a.copyRequiredModules();
	}
}
