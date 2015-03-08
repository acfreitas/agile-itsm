package br.com.citframework.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import br.com.citframework.excecao.LogicException;

/**
 * Esta classe eh utilizada para formatacoes.
 * 		Veja a classe UtilDatas para ver as formatacoes de Datas.
 * @author emauri
 *
 */
public class UtilFormatacao {
	public static String	CHAR_SIM	= "S";
	public static String	CHAR_NAO	= "N";
	
    public static Date formataHoraBigDecimalToDate(String hora) throws LogicException{
    	Date horaRetorno=null;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String h = hora.substring(0,2);
        String m = hora.substring(2,4); 
        try {
        	horaRetorno= format.parse(h+":"+m);
		} catch (ParseException e) {
			throw new LogicException( Mensagens.getValue("MSG03"));
		}
		return horaRetorno;
    }  
    /**
     * Formata um Double
     * @param valor
     * @param decimal
     * @return
     */
	public static final String formatDouble(Double valor, int decimal) {
		if (valor == null) {
			return null;
		}
		NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
		format.setMaximumFractionDigits(decimal);
		format.setMinimumFractionDigits(decimal);
		return format.format(valor);
	}
	/**
	 * Formata um Double, Sem os pontos
	 * @param valor
	 * @param decimal
	 * @return
	 */
	public static final String formatDoubleSemPontos(Double valor, int decimal) {
		if (valor == null) {
			return null;
		}
		NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
		format.setMaximumFractionDigits(decimal);
		format.setMinimumFractionDigits(decimal);
		String str = format.format(valor);
		str = str.replaceAll("\\.", "");
		return str;
	}
	/**
	 * Formata um Big Decimal
	 * @param valor
	 * @param decimal
	 * @return
	 */
	public static final String formatBigDecimal(BigDecimal valor, int decimal) {
		if (valor == null) {
			return null;
		}
		NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
		format.setMaximumFractionDigits(decimal);
		format.setMinimumFractionDigits(decimal);
		return format.format(valor);
	}
	/**
	 * Formata uma String em CNPJ.
	 * @param cnpj
	 * @return
	 */
	public static String formataCnpj(String cnpj){
		if(cnpj == null || cnpj.trim().length() == 0){ 
			return "";
		}
		cnpj = cnpj.trim();
		cnpj = UtilStrings.removeSubstring(cnpj, ".");
		cnpj = UtilStrings.removeSubstring(cnpj, "-");
		cnpj = UtilStrings.removeSubstring(cnpj, "/");
		
		while(cnpj.length() < 14){ 
			cnpj = "0" + cnpj;
		}		
		if(cnpj.length() > 0){ 
			cnpj = cnpj.substring(0, 14);
		}

		int f = cnpj.length();
		String p4 = cnpj.substring(f - 2);
		String p3 = cnpj.substring(f - 6, f - 2);
		String p2 = cnpj.substring(f - 9, f - 6);
		String p1 = cnpj.substring(f - 12, f - 9);
		String p0 = cnpj.substring(f - 14, f - 12);
		
		cnpj = p0 + "." + p1 + "." + p2 + "/" + p3 + "-" + p4; 
		
		return cnpj;
	}
	/**
	 * Formata uma String em CPF.
	 * @param cpf
	 * @return
	 */
	public static String formataCpf(String cpf){
		if(cpf == null || cpf.trim().length() == 0){ 
			return "";
		}
		cpf = cpf.trim();
		cpf = UtilStrings.removeSubstring(cpf, ".");
		cpf = UtilStrings.removeSubstring(cpf, "-");
		cpf = UtilStrings.removeSubstring(cpf, "/");

		while(cpf.length() < 11){ 
			cpf = "0" + cpf;
		}		
		if(cpf.length() > 0){ 
			cpf = cpf.substring(0, 11);
		}

		int f = cpf.length();
		String p3 = cpf.substring(f - 2, f - 0);
		String p2 = cpf.substring(f - 5, f - 2);
		String p1 = cpf.substring(f - 8, f - 5);
		String p0 = cpf.substring(f - 11, f - 8);
		
		cpf = p0 + "." + p1 + "." + p2 + "-" + p3; 
		
		return cpf;
	}	
	/**
	 * Formata sim ou nao, de acordo com o parametro.
	 * @param valor
	 * @return
	 */
	public static String formataSimNao(String valor) {
		boolean campoNaoVazio = ((valor != null) && (valor.trim().length()>0));
		return ((campoNaoVazio&&(valor.equalsIgnoreCase(CHAR_SIM)))?"Sim":"Não");
	}
	/**
	 * Formato um inteiro de acordo com o formato passado como parametro.
	 * @param numero
	 * @param formato
	 * @return
	 */
	public static String formatInt(int numero, String formato){
		String retorno;
		
		NumberFormat formatoAux = new DecimalFormat(formato);
		retorno = formatoAux.format(numero);
		return retorno;
	}
	/**
	 * Formata um decimal, de acordo com a mascara.
	 * @param value
	 * @param mask
	 * @return
	 */
	public static String formatDecimal(double value, String mask){
	    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	    dfs.setDecimalSeparator(',');
	    dfs.setGroupingSeparator('.');
	    
	    DecimalFormat df = new DecimalFormat(mask, dfs);
	    return df.format(value);
	}
	/**
	 * Formata uma string em CEP
	 * @param cep
	 * @return
	 */
	public static String formataCep(String cep){
		if (cep != null){
			cep = cep.replaceAll("-", "");
			cep = StringUtils.rightPad(cep.trim(), 8, "0");
			String p1 = cep.substring(0, 5);
			String p2 = cep.substring(5, 8);
			cep = p1 + "-" + p2;
			return cep;
		}else{
			return "";
		}
		
	}
	/**
	 * Gera o percentual.
	 * @param fracao
	 * @param total
	 * @return
	 */
	public  static String percentual(Integer fracao,Integer total){
		String retorno ="";
		if(fracao.intValue()>0&&total.intValue()>0){
			Double percentual = new Double((fracao.doubleValue() * 100)
					/ total.doubleValue());
			retorno = formatDouble(percentual, 2);
			if(retorno.length()<6){
				StringUtils.leftPad(retorno, 6);
			}
		}else{
			retorno = "  0.00";
		}
		return "("+retorno+"%)";
	}
	/**
	 * Formata uma String hora "0000" em "00:00".
	 * 	Exemplo: "300" retorna "03:00"
	 * @param hora (String)
	 * @return
	 */	
	public static String formataHoraHHMM(String hora){
		if (hora == null) return "";
		String horaAux = hora;
		
		horaAux = UtilStrings.removeSubstring(horaAux, ":");
		horaAux = UtilStrings.removeSubstring(horaAux, ".");
		horaAux = UtilStrings.removeSubstring(horaAux, "-");
		horaAux = UtilStrings.removeSubstring(horaAux, "/");
		
		while(horaAux.length() < 4){ 
			horaAux = "0" + horaAux;
		}	
		String hh = horaAux.substring(0,2);
		String mm = horaAux.substring(2,4);
		
		return hh + ":" + mm;
	}
	/**
	 * Formata uma String na Mascara mask.
	 * @param str
	 * @param mask
	 * @return
	 */
	public static String formataString(String str, String mask){
		if (mask == null){
			return str;
		}
		if (str == null){
			return str;
		}
		if (mask.length() < str.length()){
			return str;
		}
		String result = "";
		int iControleStr = 0;
		for(int i = 0; i < mask.length(); i++){
			if (mask.charAt(i) != '0' && mask.charAt(i) != '9'){
				result += mask.charAt(i);
			}else{
				if (iControleStr > (str.length() - 1)){
					break;
				}
				result += str.charAt(iControleStr);
				iControleStr++;
			}
		}
		return result;
	}
}
