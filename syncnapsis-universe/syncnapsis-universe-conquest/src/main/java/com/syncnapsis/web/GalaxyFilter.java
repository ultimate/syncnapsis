package com.syncnapsis.web;

import java.io.File;
import java.io.FileFilter;

import com.syncnapsis.utils.FileUtil;

/**
 * Filter which checks whether the JSON-file for a galaxy exists when requested and which triggers
 * the generation otherwise. This way the JSON-files are both, dynamically generated and statically
 * accessible, which is important for the clients to be able to cache those contents properly.
 * 
 * @author ultimate
 */
public class GalaxyFilter extends FilePreparationFilter
{
	/**
	 * Default Constructor passing this instance as the filter to the super Constructor
	 */
	public GalaxyFilter()
	{
		super(FILTER);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.web.FilePreparationFilter#prepare(java.io.File)
	 */
	@Override
	public boolean prepare(File file)
	{
		logger.info("name: " + file.getName());
		// TODO Auto-generated method stub
		return false;
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
