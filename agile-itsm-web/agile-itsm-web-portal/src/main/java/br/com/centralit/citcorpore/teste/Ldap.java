package br.com.centralit.citcorpore.teste;

import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * @author BeOnTime
 */
public class Ldap {
	private static HashMap common;
	
	static {
		common = new HashMap();
		common.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		common.put("java.naming.ldap.version", "3");
	}
	
    private static final Ldap INSTANCE = new Ldap();

    private Ldap() {
    }

    public static Ldap getInstance() {
        return INSTANCE;
    }


    private NamingEnumeration search(String base, Attributes matchAttrs) throws NamingException {
        Hashtable env = new Hashtable(common);
        StringBuilder url = new StringBuilder("ldap://");
        url.append("10.100.100.2:389");
        env.put(Context.PROVIDER_URL, url.toString());
       
        DirContext ctx = new InitialDirContext(env);

        return ctx.search(base, matchAttrs);
    }

    
    public boolean testLoginPwd(String login, String password) {
    	if ("".equals(password))
    		return false;

        Hashtable env = new Hashtable(common);
        StringBuilder tmp = new StringBuilder("ldap://");
        tmp.append("10.100.100.2:389");
        env.put(Context.PROVIDER_URL, tmp.toString());
        tmp = new StringBuilder("uid=");
        tmp.append(login).append(",");
        tmp.append("Users");
        env.put(Context.SECURITY_PRINCIPAL, tmp.toString());
        env.put(Context.SECURITY_CREDENTIALS, password);
       
        try {
			DirContext ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			// mot de passe invalide
			return false;
		}

        return true;
    }
	
	public static boolean testConnection(String host, String port, String dnBase) {
		Hashtable env = new Hashtable(common);
		StringBuilder url = new StringBuilder("ldap://");
		url.append(host).append(":");
		url.append(port);
		env.put(Context.PROVIDER_URL, url.toString());
		
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			System.err.println("L'hte ou le port est invalide : " + e.getMessage());
			return false;
		}
		
		try {
			ctx.search(dnBase, null);
		} catch (NamingException e) {
			System.err.println("La base '"+dnBase+"' est invalide : " + e.getMessage());
			return false;
		}

		return true;
	}
}
