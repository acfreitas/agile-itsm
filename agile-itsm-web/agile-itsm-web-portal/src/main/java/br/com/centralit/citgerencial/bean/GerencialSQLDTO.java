package br.com.centralit.citgerencial.bean;

import br.com.citframework.dto.IDto;

public class GerencialSQLDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4096190794278568225L;
	private String sql;
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
}
