package br.com.citframework.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import br.com.citframework.excecao.LogicException;

public class UtilNumbersAndDecimals {
	/**
	 * Converte uma String em Integer.
	 * @param strParaConvert
	 * @return
	 */
	public static Integer convertStringToInteger(String strParaConvert) {
		if (strParaConvert == null)
			return new Integer(0);
		if (strParaConvert.trim().equalsIgnoreCase(""))
			return new Integer(0);

		try {
			int aux = Integer.parseInt(strParaConvert);
			return new Integer(aux);
		} catch (Exception e) {
			return new Integer(0);
		}
	}	
	/**
	 * Converte um Big Decimal em Double
	 * @param big
	 * @return
	 */
	public static Double changeBigDecimalToDouble(BigDecimal big) {
		if (big != null)
			return new Double(big.doubleValue());
		else
			return null;
	}
	/**
	 * Pega o valor maior entre 2
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int getMaxValue(int val1, int val2) {
		if (val1 > val2)
			return val1;
		else
			return val2;
	}
	/**
	 * Pega o valor menor entre 2
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static int getMinValue(int val1, int val2) {
		if (val1 < val2)
			return val1;
		else
			return val2;
	}
	/**
	 * Trunca um double
	 * @param numero
	 * @return
	 */
	public static final int trunca(double numero) {
		String str = new Double(numero).toString();
		StringTokenizer tk = new StringTokenizer(str, ".");
		str = tk.nextToken();
		return new Integer(str).intValue();
	}
	/**
	 * Obtem a fracao
	 * @param numero
	 * @return
	 */
	public static final double frac(double numero) {
		String str = new Double(numero).toString();
		StringTokenizer tk = new StringTokenizer(str, ".");
		str = tk.nextToken();
		if (tk.hasMoreElements()) {
			if (numero <= 0) {
				str = "-0." + tk.nextToken();
			} else {
				str = "0." + tk.nextToken();
			}
		}
		return new Double(str).doubleValue();

	}
	/**
	 * Arredonda.
	 * @param valor
	 * @param decimal
	 * @return
	 */
	public static final double setRound(double valor, int decimal) {
		NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
		format.setMaximumFractionDigits(decimal);
		format.setMaximumFractionDigits(decimal);
		StringBuilder resultTmp = new StringBuilder(format.format(valor));
		int ponto = resultTmp.indexOf(".");
		while (ponto > -1) {
			resultTmp = resultTmp.deleteCharAt(ponto);
			ponto = resultTmp.indexOf(".");
		}
		return new Double(resultTmp.toString().replaceAll(",", ".")).doubleValue();
	}
	/**
	 * Compara numeros inteiros. Lancando execessao.
	 * @param inic
	 * @param fim
	 * @param descValores
	 * @throws LogicException
	 */
	public static void comparaInteiros(Integer inic, Integer fim, String descValores) throws LogicException {
		if (inic != null && fim != null)
			if (inic.intValue() > fim.intValue()) {
				throw new LogicException(descValores + " inicial nao pode ser maior que " + descValores + " final");
			}
	}
	/**
	 * Gera um Double a partir de uma String
	 * @param valor
	 * @return
	 */
	public static final Double strFormatToDouble(String valor) {

		if (valor == null || valor.length() == 0) {
			return null;
		}

		StringBuilder str = new StringBuilder(valor);

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '.') {
				str = str.deleteCharAt(i);
			}
		}

		String tmp = str.toString().replace(',', '.');
		Double result = new Double(tmp);
		return result;

	}
	/**
	 * Verifica se um numero eh inteiro. 
	 * @param s
	 * @return
	 */
	public static final boolean isInteger(final String s) {
		if (s == null){
			return false;
		}
		boolean flag = false;
		for (int x = 0; x < s.length(); x++) {
			final char c = s.charAt(x);
			if (x == 0 && (c == '-')) continue;  // negative
			if ((c >= '0') && (c <= '9')) {flag=true; continue;}  // 0 - 9
			return false; // invalid
		}
		return flag; // valid
	}	
	/**
	 * Funcao que Verifica se tem numero na palavra. 
	 * @param s
	 * @return
	 */	
	public static final boolean hasNumberInWord(final String s) {
		if (s == null){
			return false;
		}		
		for (int x = 0; x < s.length(); x++) {
			final char c = s.charAt(x);
			if ((c >= '0') && (c <= '9')) {return true;}  // 0 - 9
		}
		return false; // valid		
	}
	/**
	 * Converte um Tipo de dado para Integer (Pode ser String, BigDecimal, ...).
	 * @param objNum
	 * @return
	 */	
	public static final Integer convertToInteger(Object objNum){
	    	Integer num = null;
		if (BigDecimal.class.isInstance(objNum)){
		    BigDecimal auxBig = (BigDecimal) objNum;
		    num = new Integer(auxBig.intValue());
		}else if (BigInteger.class.isInstance(objNum)){
		    BigInteger auxBig = (BigInteger) objNum;
		    num = new Integer(auxBig.intValue());
		}else if (Double.class.isInstance(objNum)){
		    Double auxBig = (Double) objNum;
		    num = new Integer(auxBig.intValue());		    
		}else if (Long.class.isInstance(objNum)){
		    Long auxBig = (Long) objNum;
		    num = new Integer(auxBig.intValue());
		}else if (Short.class.isInstance(objNum)){
			Short sh = (Short) objNum;
			num = new Integer(sh.intValue());
		}else if (String.class.isInstance(objNum)){
		    String str = (String) objNum;
		    return convertStringToInteger(str);
		}else{
		    num = (Integer) objNum;
		}
		return num;
	}
}
