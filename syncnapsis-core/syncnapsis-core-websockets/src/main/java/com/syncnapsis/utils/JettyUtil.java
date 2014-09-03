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

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util used to determine and check the Jetty version in the Classpath versus the version required.
 * 
 * @author ultimate
 */
public abstract class JettyUtil
{
	/**
	 * Logger-Instanz
	 */
	private static final Logger	logger				= LoggerFactory.getLogger(JettyUtil.class);
	/**
	 * The Jetty version this library is build with an which is required to use Jetty dependant
	 * classes in the library.
	 */
	public static final String	versionRequired		= "8.1.9.v20130131";
	/**
	 * The Jetty version found in the Classpath
	 */
	public static final String	versionFound		= getJettyVersion();
	/**
	 * Constant value if no Jetty version could be found
	 */
	public static final String	versionUnknown		= "no Version found";

	/**
	 * The Package of the Jetty Server
	 */
	public static final String	jettyServerPackage	= "org.eclipse.jetty.server";

	/**
	 * Determine the Jetty version of the Jetty library in Classpath
	 * 
	 * @return the version
	 */
	private static String getJettyVersion()
	{
		String jettyVersion = null;
		try
		{
			jettyVersion = Server.getVersion();
		}
		catch(NoClassDefFoundError e)
		{
			// if running in a Jetty the Server.class is NOT visible for the webapplication
			// in this case we try to get the version from the package info
			// (like Jetty does it as well)
			Package p = Package.getPackage(jettyServerPackage);
			if(p != null)
				jettyVersion = p.getImplementationVersion();
		}
		if(jettyVersion == null)
			jettyVersion = versionUnknown;
		logger.info("Library is build with Jetty version: " + versionRequired);
		logger.info("Jetty version found in Classpath:    " + jettyVersion);
		return jettyVersion;
	}

	/**
	 * Check the required Jetty version versus the version found.
	 * 
	 * @return true if the versions match, false otherwise
	 */
	public static boolean checkJettyVersion()
	{
		return versionRequired.equals(versionFound);
	}

	/**
	 * Print an error when a non matching Jetty Version was found.
	 * 
	 * @param dependantClass - the Class that is dependant by Jetty
	 */
	public static void printError(Class<?> dependantClass)
	{
		logger.warn("Differing Jetty Versions may result in incompatability using '" + dependantClass.getName() + "'!");
	}

	/**
	 * Has a Jetty version been found in Classpath?
	 * 
	 * @return true or false
	 */
	public static boolean jettyFound()
	{
		return !versionUnknown.equals(versionFound);
	}
}
