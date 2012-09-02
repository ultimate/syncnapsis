package com.syncnapsis.websockets.security;
//package com.syncnapsis.websockets.security;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.eclipse.jetty.server.Authentication.User;
//import com.syncnapsis.providers.TimeProvider;
//import com.syncnapsis.utils.collections.LimitedQueue;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.util.Assert;
//
//
//// TODO Funktionen aus dem Security-Paket, die zukünftig in die WebSocket-Unterstützung integriert
//// werden sollen, werden hier zwischenzeitlich gelagert...
//public class SecuritySupport
//{
//	protected Logger		logger	= LoggerFactory.getLogger(getClass());
//
//	// will be null
//	private TimeProvider	timeProvider;									
//
//	private class LoginFilter implements InitializingBean
//	{
//		/**
//		 * Die Länge der Queue für die Speicherung der letzten Logins
//		 */
//		private int					loginQueueSize							= 5;
//		/**
//		 * Das Zeitlimit für die Queue für die Speicherung der letzten Logins. Innerhalb dieser Zeit
//		 * dürfen maximal loginQueueSize Logins stattgefunden haben.
//		 */
//		private int					loginQueueTime							= 300;
//
//		/**
//		 * "Logged in too frequently"
//		 */
//		public static final String	AUTHENTICATE_LOGGED_IN_TOO_FREQUENTLY	= "Logged in too frequently!";
//
//		/**
//		 * Die Länge der Queue für die Speicherung der letzten Logins
//		 * 
//		 * @param loginQueueSize - die Größe
//		 */
//		public void setLoginQueueSize(int loginQueueSize)
//		{
//			this.loginQueueSize = loginQueueSize;
//		}
//
//		/**
//		 * Das Zeitlimit für die Queue für die Speicherung der letzten Logins. Innerhalb dieser Zeit
//		 * dürfen maximal loginQueueSize Logins stattgefunden haben.
//		 * 
//		 * @see LoginFilter#setLoginQueueSize
//		 * @param loginQueueTime - die Zeit in Sekunden
//		 */
//		public void setLoginQueueTime(int loginQueueTime)
//		{
//			this.loginQueueTime = loginQueueTime;
//		}
//
//		/**
//		 * Prüfung von loginQueueSize und loginQueueTime auf > 0.
//		 */
//		@Override
//		protected void afterPropertiesSet() throws Exception
//		{
//			Assert.isTrue(loginQueueSize > 0, "loginQueueSize must be greater than 0");
//			Assert.isTrue(loginQueueTime > 0, "loginQueueTime must be greater than 0");
//		}
//
//		protected boolean applyFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
//		{
//			logger.debug("processing login");
//
//			HttpSession session = request.getSession();
//
//			@SuppressWarnings("unchecked")
//			LimitedQueue<Long> loginQueue = (LimitedQueue<Long>) session.getAttribute(SecurityConstants.SESSION_LAST_LOGINS_KEY);
//			Long now = timeProvider.get();
//
//			// initialisierung der Queue falls noch nicht geschehen...
//			if(loginQueue == null)
//			{
//				loginQueue = new LimitedQueue<Long>(this.loginQueueSize);
//				session.setAttribute(SecurityConstants.SESSION_LAST_LOGINS_KEY, loginQueue);
//			}
//
//			// wenn Queue voll (d.h. es haben schon mind. loginQueueSize Logins stattgefunden),
//			// dann prüfe ob der erste von den Logins länger als loginQueueTime Sekunden her ist
//			if((loginQueue.size() == this.loginQueueSize) && (loginQueue.peek() < now - loginQueueTime * 1000))
//			{
//				// logged in too frequently
//				logger.info("Login-Error: " + AUTHENTICATE_LOGGED_IN_TOO_FREQUENTLY);
//				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//				response.addHeader(HTTPHeaders.WWW_AUTHENTICATE, AUTHENTICATE_LOGGED_IN_TOO_FREQUENTLY);
//			}
//			else
//			{
//				// check login
//				String username = request.getParameter(SecurityConstants.REQUEST_USERNAME_KEY);
//				if(username == null)
//					username = "";
//
//				String password = request.getParameter(SecurityConstants.REQUEST_PASSWORD_KEY);
//				if(password == null)
//					password = "";
//
//				User user;
//				Empire empire;
//				try
//				{
//					if(HibernateUtil.currentSession().getTransaction() == null)
//						HibernateUtil.currentSession().beginTransaction();
//					else if(!HibernateUtil.currentSession().getTransaction().isActive())
//						HibernateUtil.currentSession().getTransaction().begin();
//					user = userManager.checkUserLogin(username, password);
//					empire = user.getCurrentEmpire();
//
//					session.setAttribute(SecurityConstants.SESSION_USER_KEY, user.getId());
//					session.setAttribute(SecurityConstants.SESSION_INFINITE_KEY, user.isUsingInfiniteSession());
//					session.setAttribute(SecurityConstants.SESSION_TIMEOUT_KEY, user.getSessionTimeout());
//					session.setAttribute(SecurityConstants.SESSION_LOCALE_KEY, user.getLocale());
//					if(empire != null)
//						session.setAttribute(SecurityConstants.SESSION_EMPIRE_KEY, empire.getId());
//					else
//						session.setAttribute(SecurityConstants.SESSION_EMPIRE_KEY, null);
//
//					response.setStatus(HttpServletResponse.SC_OK);
//				}
//				catch(UserNotFoundException e)
//				{
//					logger.info("Login-Error: " + e.getMessage());
//					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//					response.addHeader(HTTPHeaders.WWW_AUTHENTICATE, e.getMessage());
//				}
//			}
//
//			loginQueue.add(now);
//			return false;
//		}
//	}
//
//	private class LogoutFilter
//	{
//		protected boolean applyFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
//		{
//			logger.debug("processing logout");
//
//			HttpSession session = request.getSession();
//
//			session.setAttribute(SecurityConstants.SESSION_USER_KEY, null);
//			session.setAttribute(SecurityConstants.SESSION_INFINITE_KEY, null);
//			session.setAttribute(SecurityConstants.SESSION_TIMEOUT_KEY, null);
//			// muss nicht resettet werden
//			// session.setAttribute(SecurityConstants.SESSION_LOCALE_KEY, null);
//			session.setAttribute(SecurityConstants.SESSION_EMPIRE_KEY, null);
//
//			response.setStatus(HttpServletResponse.SC_OK);
//			return false;
//		}
//	}
//}
