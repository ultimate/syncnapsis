package com.syncnapsis.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.InitializingBean;

import com.syncnapsis.utils.serialization.JSONGenerator;

/**
 * Filter which checks whether the JSON-file for a galaxy exists when requested and which triggers
 * the generation otherwise. This way the JSON-files are both, dynamically generated and statically
 * accessible, which is important for the clients to be able to cache those contents properly.
 * 
 * @author ultimate
 */
public class ConstantsFilter extends FilePreparationFilter implements InitializingBean
{
	/**
	 * Default Constructor passing this instance as the filter to the super Constructor
	 */
	public ConstantsFilter()
	{
		super(null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.rpc.RPCFilter#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.web.FilePreparationFilter#prepare(java.io.File, java.io.File)
	 */
	@Override
	public synchronized boolean prepare(File realFile, File servletFile)
	{
		logger.info("creating constants file: " + realFile.getName());

		String constants = JSONGenerator.createConstantsJS();
		try
		{
			BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(realFile));
			os.write(constants.toString().getBytes());
			os.flush();
			os.close();
		}
		catch(IOException e)
		{
			logger.error("could not write constants to file", e);
			return false;
		}

		return true;
	}
}
