/*
 * Created on 20/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citcorpore.exception;


import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Tellus SA
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Writer extends PrintWriter {

	/**
	 * @param out
	 */
	public Writer( ) {
		super(new StringWriter() );

	}
	
	public StringBuffer getBuffer() {
		return ((StringWriter)out).getBuffer();

	} 




}
