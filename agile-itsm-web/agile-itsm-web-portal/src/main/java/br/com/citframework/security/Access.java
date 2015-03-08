package br.com.citframework.security;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class Access {

	private String path;
	private List accessingTransactionList;
	private static final String CERQUILHA = "#";
	static final String PUBLIC = "PUBLIC";

	public String getPath() {
		return path;
	}

	void setPath(String path) {
		this.path = path;
	}

	void setAccessingTransactionList(List accessingTransactionList) {
		this.accessingTransactionList = accessingTransactionList;
	}

	public boolean hasAccess(String userTransactions) {
		if (accessingTransactionList.indexOf(PUBLIC) != -1)
			return true;
		if (StringUtils.isBlank(userTransactions))
			return false;
		StringTokenizer tokenizer = new StringTokenizer(userTransactions, CERQUILHA);
		while (tokenizer.hasMoreTokens()) {
			String userTransaction = tokenizer.nextToken().toUpperCase();
			if (accessingTransactionList.indexOf(userTransaction) != -1)
				return true;
		}
		return false;
	}

	public String toString() {
		return path == null ? "'null' path" : path;
	}

	/*
	public boolean equals(Object obj) {
		return path != null && obj != null && obj instanceof Access && path.equals(((Access) obj).path);
	}
	 */
	
	
	public List getAccessingTransactionList() {
		return accessingTransactionList;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Access other = (Access) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

}
