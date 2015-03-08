/*
 * Created on 11/01/2005
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.citframework.tld;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.bean.WriteTag;

/**
 * @author ney
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FormatDate extends WriteTag{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 254679665210265708L;
	private String mask;
	
	public FormatDate(){
		
		setFormatKey("DATA_FORMAT");
	}
	
	
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	
	
	protected String formatValue(Object value) throws JspException {
		
		if(value==null || value.toString().trim().length()==0){
			return "";
		}
		
		if(value.toString().indexOf("/")>-1){
		    return value.toString();
		}
		Date data = null;
		SimpleDateFormat spd =null;
		if(value instanceof String){
		    spd = new SimpleDateFormat("yyyy-MM-dd");
		    try {
                data = spd.parse(value.toString());
            } catch (ParseException e1) {
                
                throw new JspException("Mascara:"+getMask()+", Valor:"+value+", campo:"+property,e1);
            } 
		}else if(value instanceof Date){
		    data = (Date)value;
		    
		}else{
		    throw new JspException("Tipo do atributo "+getProperty()+" inválido pata conversão de Data");
		}
		    
		    
		    
		 spd = new SimpleDateFormat(getMask().toString());
		
		
		try {
            return spd.format(data);
        } catch (Exception e) {
            
           throw new JspException("Mascara:"+getMask()+", Valor:"+value+", campo:"+property,e);
        }
	}
}
