/*
 * Created on 11/01/2005
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.citframework.tld;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.bean.WriteTag;

import br.com.citframework.util.converter.ConverterUtils;

/**
 * @author ney
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FormatNumber extends WriteTag{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6823703333271138561L;
	private String decimal;
	private String digitos;
	
	
	
	public String getDigitos() {
		return digitos;
	}


	public void setDigitos(String digitos) {
		this.digitos = digitos;
	}


	public FormatNumber(){
		
		setFormatKey("DOUBLE_KEY");
	}
	

	protected String formatValue(Object value) throws JspException {
		
		if(value==null || value.toString().trim().length()==0){
			return null;
		}
		
		if(ConverterUtils.possuiMascara(value.toString())){
			   value =  ConverterUtils.retiraMascara(value.toString());
			}
		
		Integer dec = new Integer(getDecimal());
		NumberFormat nf = NumberFormat.getInstance(new Locale("pt","BR"));
		nf.setMaximumFractionDigits(dec.intValue());
		nf.setMinimumFractionDigits(dec.intValue());
		if(getDigitos()!=null && getDigitos().trim().length()>0){
			nf.setMinimumIntegerDigits(new Integer(getDigitos()).intValue());
		}
		value = nf.format(new Double(value.toString().trim()));
		
		if(getDecimal().equals("0")){
				//System.out.print("Atrb_ant: "+getProperty()+" Valor:"+tmp+" ind "+toString().indexOf("."));
		    if( value.toString().indexOf(".")>-1){
						StringTokenizer tok = new StringTokenizer(value.toString(),".");
						String tmp2 ="";
						while(tok.hasMoreTokens())
							tmp2+=tok.nextToken();
						if(tmp2.length()>0){
						    value = tmp2;
						}		        
		    }

				if(getProperty().toUpperCase().indexOf("CNPJ")>-1){
						String  mascaraCnpj = "##.###.###/####-##";
						value = ConverterUtils.aplicaMascara(value.toString(),mascaraCnpj);
						
					}else if(getProperty().toUpperCase().indexOf("CPF")>-1){
						String  mascaraCpf = "###.###.###-##";
						value = ConverterUtils.aplicaMascara(value.toString(),mascaraCpf);
					}
				
				return value.toString();
				
		}
				
	

		
		
		try {
            return value.toString();
        } catch (Exception e) {
            
            throw new JspException("Erro ao converter propriedade "+getProperty()+" = "+value+". "+e.getMessage());
        }
	}
	
	
	public String getDecimal() {
		return decimal;
	}
	public void setDecimal(String decimal) {
		this.decimal = decimal;
	}

}
