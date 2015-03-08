/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.centralit.nagios.livestatus.tables;

/**
 * 
 * @author adenir
 */
public class LivestatusSeparator {

	protected static final int P_SEP = '\n';
	protected static final int S_SEP = '|';
	protected static final int T_SEP = '>';
	protected static final int Q_SEP = '}';

	public static String getSeparators() {
		return String.format("%d %d %d %d", P_SEP, S_SEP, T_SEP, Q_SEP);
	}

	/**
	 * Get the value of SEP1 (\n)
	 * 
	 * @return the value of P_SEP
	 */
	public static String SEP1() {
		return String.format("%c", P_SEP); // \n
	}

	/**
	 * Get the value of SEP2 (|)
	 * 
	 * @return the value of S_SEP
	 */
	public static String SEP2() {
		return String.format("\\%c", S_SEP); // |
	}

	/**
	 * Get the value of SEP3 (>)
	 * 
	 * @return the value of T_SEP
	 */
	public static String SEP3() {
		return String.format("%c", T_SEP); // >
	}

	/**
	 * Get the value of SEP4 (})
	 * 
	 * @return the value of Q_SEP
	 */
	public static String SEP4() {
		return String.format("%c", Q_SEP); // }
	}
}
