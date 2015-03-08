package br.com.citframework.security;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class AccessConfig {

	private static final Logger LOGGER = Logger.getLogger(AccessConfig.class);
	static HashMap accessMap;
	
	private AccessConfig() {}

	public static Access getAccess(String path) {
		if(accessMap != null){
			Access access = (Access) accessMap.get(path);
			LOGGER.debug("Access for path '" + path + "': " + access);
			return access;			
		}
		return null;

	}
	
}
