package br.com.centralit.citcorpore.teste;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.PartialResultException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class Teste {

	public static void main(String[] args) {
		String userName = "emauri";

		try {
			// Hashtable stores LDAP connection specifics.
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://10.100.100.2:389");
			env
					.put(Context.SECURITY_PRINCIPAL,
							"dc=centralit,dc=com,dc=br");
			//env.put(Context.SECURITY_CREDENTIALS, "");

			System.out.println("Creating Context.");
			DirContext ctx = new InitialDirContext(env);

			// Specify the ids of the attributes to return
			//String[] attrIDs = { "postOfficeBox" };
			SearchControls ctls = new SearchControls();
			//ctls.setReturningAttributes(attrIDs);
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// Specify the search filter to match
			String filter = "(&(sAMAccountName=" + userName
					+ "))";

			// Search the subtree for objects by using the filter
			System.out.println("Running Search...");
			NamingEnumeration answer = ctx.search("DC=centralit,DC=com,DC=br", filter,
					ctls);

			try {
				while (answer.hasMore()) {
					SearchResult sr = (SearchResult) answer.next();
					System.out.println(">>>" + sr.getName());
					Attributes attrs = sr.getAttributes();
					System.out.println(attrs.get("postOfficeBox").get());
				}
			} catch (PartialResultException e) {
				// e.printStackTrace();
			}
			answer.close();
			ctx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}