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
package com.syncnapsis;
import org.springframework.context.ApplicationContext;

import com.syncnapsis.utils.ApplicationContextUtil;

/**
 * Just a simple Test-Class for checking if your application-context-configuration is valid and
 * working...<br>
 * Simply run the main-method and hope spring won't throw errors ;-)
 * 
 * @author ultimate
 */
public class AppCtxTest
{
	/**
	 * Just a simple Test-Class for checking if your application-context-configuration is valid and
	 * working...<br>
	 * Simply run the main-method and hope spring won't throw errors ;-)
	 * 
	 * @param args - nothing required
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		// String[] locations = ApplicationContextUtil.getDefaultConfigLocations();
		// @formatter:off
		String[] locations = new String[] {
				"classpath:/applicationContext-hibernate.xml",
				"classpath:/applicationContext-dao.xml",
				"classpath:/applicationContext-security.xml",
				"classpath:/applicationContext-service.xml",
				"WEB-INF/websockets.xml" };
		// @formatter:on
		ApplicationContext ctx = ApplicationContextUtil.createApplicationContext(locations);
		System.out.println("ready");
		
	}
}
