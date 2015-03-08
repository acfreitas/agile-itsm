package br.com.citframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Label {
	
	private static Properties props = null;
	public static final String fileName = "Label.properties";
	
	static{
		
		props = new Properties();
		ClassLoader load = Label.class.getClassLoader();
		
		InputStream is = load.getResourceAsStream(fileName);		
		if (is == null){
			is = ClassLoader.getSystemResourceAsStream(fileName);
		}
		if (is == null){
			is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
		}		

		//InputStream is = ClassLoader.getSystemResourceAsStream(fileName);
		try {
			if (is == null){
				throw new Exception("Arquivo de recursos nao encontrado: " + fileName);
			}
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String value) {
		return props.getProperty(value);
	}
	
	public static void setProp(InputStream is){
		try {
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
