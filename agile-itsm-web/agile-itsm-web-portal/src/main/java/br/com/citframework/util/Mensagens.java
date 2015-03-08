package br.com.citframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Mensagens {
	
	
	private static Properties props = null;
	private static final String fileName = "Mensagens.properties";
	
	public static InputStream inputStreamSettedInLoad = null;
	
	static{
		
		props = new Properties();
		ClassLoader load = Mensagens.class.getClassLoader();
		InputStream is = load.getResourceAsStream(fileName);
		if (is == null){
			is = ClassLoader.getSystemResourceAsStream(fileName);
		}
		if (is == null){
			is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
		}		
		try {
			if (is == null){
				is = inputStreamSettedInLoad;
			}	
			if (is != null){
				props.load(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String value) {
//		if (props == null){
//			InputStream is = inputStreamSettedInLoad;
//			if (is != null){
//				try {
//					props.load(is);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}	
//			if (props == null){ //Se ainda continuar nulo.
//				return null;
//			}
//		}
		
		if(props == null){
			return null;
		}
		return props.getProperty(value);
	}

}
