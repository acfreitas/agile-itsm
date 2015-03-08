package br.com.citframework.util;

import java.util.Collection;

public class UtilTest {
	

	public String testNotNull(Object obj) throws Exception {
		if (obj != null) {
			return "Passou";
		} else{
			return "Não Passou";
		}
	}

	public String testEquals(Object obj, Object obj2) throws Exception {
		if (obj == obj2) {
			return "Passou";
		} else	{
			return "Não Passou";
		}
	}

	
	public String testObj (Object obj, Object obj2) throws Exception{
		if (obj != null && obj2 != null){
			return "passou";
		}else{
			return "não passou";
		}
	}

	
	public String testNull (Object obj) throws Exception{
		if(obj == null){
			return "Nulo";
		}else{
			return "Preenchido";
		}
	}
	
	public String testNotNullString (String stg, String stg2) throws Exception{
		if (stg != null && stg2 != null){
			return "Passou";
		}else{
			return "Não Passou";
		}
	}
	
	public String testCollection (Collection col) throws Exception{
		if (col != null){
			return "Passou";
		}else{
			return "Não passou";
		}
	}

}
