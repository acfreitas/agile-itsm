/*
 * Created on 17/06/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citcorpore.exception;

import java.util.ResourceBundle;

/**
 * @author Tellus SA
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TradutorExcecao {
	private ResourceBundle tradutor;
	private static TradutorExcecao instance = null; 
	private static String 
	   TRADUTOR_FILE    = 
				  "Excecoes";

	/**
	 * Construtor
	 */
    public TradutorExcecao(String arquivo){
		try {
		  tradutor = ResourceBundle.getBundle(arquivo);
		} catch(Exception exc) {
		  exc.printStackTrace();
		}
    }

	/**
	 * Classe singleton
	 * @return CfgXml
	 */
	public static TradutorExcecao getInstance(){
		if (instance == null){
			instance = new TradutorExcecao(TRADUTOR_FILE);
		}
		return instance;
	}
	
	public String getMensagem(String code){
		String result = "";
		try{
			result = tradutor.getString(code);	
		}catch (Exception e) {
			result = "Erro inesperado.";
		}
		return result;
	}
}
