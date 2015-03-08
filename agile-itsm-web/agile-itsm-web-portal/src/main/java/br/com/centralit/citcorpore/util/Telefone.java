package br.com.centralit.citcorpore.util;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class Telefone {
	public static String numeroMascarado(String numero, boolean sql){
		StringBuilder resultado = new StringBuilder();
		if (numero.length()<8){
			resultado.append(numero);
		} else {
			switch (numero.length()) {
			case 8:
				resultado.append(format("####-####", numero));
				break;
			case 9:
				resultado.append(format("#####-####", numero));
				break;
			case 10:
				if (!sql){
					resultado.append(format("(0##) ####-####", numero));
				} else {
					resultado.append(format("##) ####-####", numero));
				}
				break;
			case 11:
				if (!sql){
					resultado.append(format("(###) ####-####", numero));
				} else {
					resultado.append(format("###) ####-####", numero));
				}
				break;
			case 12:
				if (!sql){
					resultado.append(format("(###) #####-####", numero));
				} else {
					resultado.append(format("###) #####-####", numero));
				}				
				break;
			case 13:
				if (!sql){
					resultado.append(format("+0# (###) #####-####", numero));
				} else {
					resultado.append(format("# (###) #####-####", numero));
				}
				break;				
			default:
				if (!sql){
					resultado.append(format("+## (###) #####-####", numero));
				} else {
					resultado.append(format("## (###) #####-####", numero));
				}
				break;
			}
		}
		return resultado.toString();
	}
	
	public static String mascaraProcuraSql(String numero){
		StringBuilder resultado = new StringBuilder();
		if (numero.length()<8){
			resultado.append("='"+numero+"'");
		} else {
			resultado.append("like '%");
			resultado.append(numeroMascarado(numero,true));
			resultado.append("'");
		}
		return resultado.toString();
	}
	
	private static String format(String pattern, Object value) {
        MaskFormatter mask;
        try {
			mask = new MaskFormatter(pattern);
	        mask.setValueContainsLiteralCharacters(false);
	        return mask.valueToString(value);
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
    }
	
}
