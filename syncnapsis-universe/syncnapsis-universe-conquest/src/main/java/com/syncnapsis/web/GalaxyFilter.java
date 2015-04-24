package com.syncnapsis.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.exceptions.ObjectNotFoundException;
import com.syncnapsis.utils.FileUtil;
import com.syncnapsis.utils.serialization.JSONGenerator;

/**
 * Filter which checks whether the JSON-file for a galaxy exists when requested and which triggers
 * the generation otherwise. This way the JSON-files are both, dynamically generated and statically
 * accessible, which is important for the clients to be able to cache those contents properly.
 * 
 * @author ultimate
 */
public class GalaxyFilter extends FilePreparationFilter implements InitializingBean
{
	/**
	 * The GalaxyManager
	 */
	protected GalaxyManager	galaxyManager;

	/**
	 * Default Constructor passing this instance as the filter to the super Constructor
	 */
	public GalaxyFilter()
	{
		super(FILTER);
	}

	/**
	 * The GalaxyManager
	 * 
	 * @return galaxyManager
	 */
	public GalaxyManager getGalaxyManager()
	{
		return galaxyManager;
	}

	/**
	 * The GalaxyManager
	 * 
	 * @param galaxyManager - the GalaxyManager
	 */
	public void setGalaxyManager(GalaxyManager galaxyManager)
	{
		this.galaxyManager = galaxyManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.rpc.RPCFilter#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(galaxyManager, "galaxyManager must not be null");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.web.FilePreparationFilter#prepare(java.io.File, java.io.File)
	 */
	@Override
	public synchronized boolean prepare(File realFile, File servletFile)
	{
		logger.info("creating galaxy file: " + realFile.getName());

		String nameWithoutExtension = realFile.getName().substring(0, realFile.getName().length() - EXTENSION.length());
		Long id = null;
		try
		{
			id = Long.parseLong(nameWithoutExtension);
		}
		catch(NumberFormatException e)
		{
			logger.error("could not parse file name to galaxy id", e);
			return false;
		}

		Galaxy galaxy;
		try
		{
			galaxy = galaxyManager.get(id);
		}
		catch(ObjectNotFoundException e)
		{
			logger.error("invalid galaxy id: " + id);
			return false;
		}

		String json = JSONGenerator.toJSON(galaxy);

		try
		{
			BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(realFile));
			os.write(json.getBytes());
			os.flush();
			os.close();
		}
		catch(IOException e)
		{
			logger.error("could not write json to file", e);
			return false;
		}

		return true;
	}


	
	/**
	 * The file extension for the galaxy files.
	 */
	private static final String EXTENSION = ".json";
	/**
	 * The file path for the galaxy files.
	 */
	private static final String PATH = "\\galaxy";
	
	/**
	 * FileFilter for the galaxy JSON-files
	 */
	private static final FileFilter FILTER = new FileFilter() {
		// @formatter:off
		
		/*
		 * (non-Javadoc)
		 * @see java.io.FileFilter#accept(java.io.File)
		 */
		@Override
		public boolean accept(File file)
		{
			if(!EXTENSION.equalsIgnoreCase(FileUtil.getExtension(file)))
				return false;
			if(!PATH.equalsIgnoreCase(file.getParent()))
				return false;
			return true;
		}
	};
}
