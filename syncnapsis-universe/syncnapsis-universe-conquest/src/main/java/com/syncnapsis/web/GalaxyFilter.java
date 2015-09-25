package com.syncnapsis.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.service.MatchManager;
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
	 * The MatchManager
	 */
	protected MatchManager	matchManager;

	/**
	 * Default Constructor passing this instance as the filter to the super Constructor
	 */
	public GalaxyFilter()
	{
		super(FILTER);
	}

	/**
	 * The MatchManager
	 * 
	 * @return matchManager
	 */
	public MatchManager getMatchManager()
	{
		return matchManager;
	}

	/**
	 * The MatchManager
	 * 
	 * @param matchManager - the MatchManager
	 */
	public void setMatchManager(MatchManager matchManager)
	{
		this.matchManager = matchManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.rpc.RPCFilter#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(matchManager, "matchManager must not be null");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.web.FilePreparationFilter#prepare(java.io.File, java.io.File)
	 */
	@Override
	public synchronized boolean prepare(File realFile, File servletFile)
	{
		logger.info("creating match-galaxy file: " + realFile.getName());

		String nameWithoutExtension = realFile.getName().substring(0, realFile.getName().length() - EXTENSION.length());
		Long id = null;
		try
		{
			id = Long.parseLong(nameWithoutExtension);
		}
		catch(NumberFormatException e)
		{
			logger.error("could not parse file name to match id", e);
			return false;
		}

		Match match;
		try
		{
			match = matchManager.get(id);
		}
		catch(ObjectNotFoundException e)
		{
			logger.error("invalid match id: " + id);
			return false;
		}

		String json = JSONGenerator.toJSON(match, false); // TODO use format?

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
	public static final String EXTENSION = ".json";
	/**
	 * The file path for the galaxy files.
	 */
	public static final String PATH = "\\galaxy";
	
	/**
	 * FileFilter for the galaxy JSON-files
	 */
	public static final FileFilter FILTER = new FileFilter() {
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
