package br.com.citframework.integracao;

import java.io.Serializable;

public class Condition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -94317735780450660L;
	public static final int AND =1;
	public static final int OR =2;
	
	
	private String filedClass;
	private String comparator;
	private Object value;
	private int operator = AND;
	
	
	
	public Condition(int operator, String filedClass, String comparator, Object value) {
		super();
		this.operator = operator;
		this.filedClass = filedClass;
		this.comparator = comparator;
		this.value = value;
	}
	
	
	
	public Condition(String filedClass, String comparator, Object value) {
		super();
		this.filedClass = filedClass;
		this.comparator = comparator;
		this.value = value;
	}


	public String getFiledClass() {
		return filedClass;
	}
	public void setFiledClass(String campo) {
		this.filedClass = campo;
	}
	public String getComparator() {
		return comparator;
	}
	public void setComparator(String comparacao) {
		this.comparator = comparacao;
	}
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operador) {
		this.operator = operador;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object valor) {
		this.value = valor;
	}
	
	
	
	
	
	
	
	
	

}
