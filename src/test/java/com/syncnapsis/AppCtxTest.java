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
