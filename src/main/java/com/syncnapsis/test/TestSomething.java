/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.test;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.data.service.RPCLogManager;
import com.syncnapsis.data.service.SolarSystemManager;
import com.syncnapsis.tests.annotations.Untested;
import com.syncnapsis.utils.ApplicationContextUtil;
import com.syncnapsis.utils.ServletUtil;
import com.syncnapsis.websockets.service.rpc.RPCCall;

@Untested
public class TestSomething
{
	public static void main(String[] args) throws Exception
	{
		// new config: fixed again!!!
		String[] locations = new String[] {
				"classpath*:/ctx-core-utils.xml",
	            "classpath*:/ctx-core-data.xml",
//	            "classpath*:/ctx-hibernate-default.xml",
	            "classpath*:/ctx-core-websockets.xml",
	            "classpath*:/ctx-core-application-base.xml",
	            "classpath*:/ctx-core-game-base.xml",
	            "classpath*:/ctx-stats-base-impl.xml",
	            "classpath*:/ctx-universe-conquest.xml",
		};
		// old config: working!!!
//		String[] locations = new String[] {
//	            "classpath:/applicationContext-dao.xml",
//	            "classpath:/applicationContext-security.xml",
//	            "classpath:/applicationContext-service.xml",
//	            "classpath:/applicationContext-hibernate.xml",
//				"classpath:/websockets.xml"
//		};
		ApplicationContext ctx = ApplicationContextUtil.createApplicationContext(locations);
		ApplicationContextUtil.getInstance().setApplicationContext(ctx);
		
		GalaxyManager galaxyManager = ApplicationContextUtil.getBean(GalaxyManager.class);

		SolarSystemManager solarSystemManager = ApplicationContextUtil.getBean(SolarSystemManager.class);
		SolarSystem s = new SolarSystem();
		s.setGalaxy(galaxyManager.getAll().get(0));
		s.setCoordinateX(1);
		s.setCoordinateY(2);
		s.setCoordinateZ(3);
		s.setName("new g");
		s.setActivated(true);
		s.setHabitability(123);
		s.setSize(321);
		solarSystemManager.save(s);

		
		RPCLogManager rpcLogManager = ctx.getBean("rpcLogManager", RPCLogManager.class);

		RPCCall rpcCall = new RPCCall("object", "method", new Object[] { "1", 2 });
		User user = null;
		String result = "a text";
		Exception ex = new IllegalAccessException("not allowed");
		Object[] authorities = new Object[0];
		Date executionDate = new Date();
		String addr = "1.2.3.4";
		String agent = "browser";
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_ADDR, addr);
		session.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_HOST, addr);
		session.setAttribute(ServletUtil.ATTRIBUTE_USER_AGENT, agent);

		rpcLogManager.log(rpcCall, result, executionDate, user, session, authorities);
		
		/*
		 * System.out.println(StringUtil.encodePassword("admin", "SHA"));
		 * System.out.println(StringUtil.encodePassword("user1", "SHA"));
		 * System.out.println(StringUtil.encodePassword("user2", "SHA"));
		 * System.out.println(Base64.encodeObject(new Object[]{}));
		 * System.out.println(new File("").getAbsolutePath());
		 * System.out.println(new File(".").getAbsolutePath());
		 * System.out.println(new File("/").getAbsolutePath());
		 * System.out.println(new File("\\").getAbsolutePath());
		 * System.out.println(new File("a").getAbsolutePath());
		 * System.out.println(new File("/a").getAbsolutePath());
		 * System.out.println(new File("\\a").getAbsolutePath());
		 * System.out.println(new File("..").getAbsolutePath());
		 * System.out.println(new File("/..").getAbsolutePath());
		 * System.out.println(new File("\\..").getAbsolutePath());
		 * System.out.println(new File("../a").getAbsolutePath());
		 * System.out.println(new File("..\\a").getAbsolutePath());
		 * System.out.println(new File("/../a").getAbsolutePath());
		 * System.out.println(new File("\\..\\a").getAbsolutePath());
		 * System.out.println(new File("../syncnapsis-universe-evolution").getPath());
		 * System.out.println(new File("../syncnapsis-universe-evolution").getAbsolutePath());
		 * System.out.println(new File("../syncnapsis-universe-evolution").exists());
		 * System.out.println(new File("../../syncnapsis-modules").getPath());
		 * System.out.println(new File("../../syncnapsis-modules").getAbsolutePath());
		 * System.out.println(new File("../../syncnapsis-modules").exists());
		 */
	}
}
