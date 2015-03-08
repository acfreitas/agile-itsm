package br.com.citframework.security;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class SessionPool {
	
	private static class SessionData {
		HttpSession session;
		long lastAccess;
		
		SessionData(HttpSession session, long lastAccess) {
			this.session = session;
			this.lastAccess = lastAccess;
		}
	}
	
	private static final HashMap SESSION_MAP = new HashMap();
	
	public static synchronized void addSession(String key, HttpSession session) {
		SESSION_MAP.put(key, new SessionData(session, System.currentTimeMillis()));
	}

}
