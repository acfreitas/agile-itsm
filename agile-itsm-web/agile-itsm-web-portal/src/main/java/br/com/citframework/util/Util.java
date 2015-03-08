package br.com.citframework.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import br.com.citframework.excecao.LogicException;

/**
 * Classe de utilitarios Geral
 * 		Caso procure algo especifico, procure as demais classes de utilitarios.
 * @author ney
 */
public class Util {

    /**
     * Retorna uma lista de programções semanais
     * @param hrIncial String
     * @param hrFinal String
     * @param intervalo BigDecimal
     * @return ArrayList
     * @throws LogicException
     * @throws ParseException
     */
    public static ArrayList getHorasProgramacaoSemanal(String hrIncial,
    		String hrFinal,BigDecimal intervalo) throws LogicException{

        Date horaInicial = UtilFormatacao.formataHoraBigDecimalToDate(hrIncial);
        Date horaFinal   = UtilFormatacao.formataHoraBigDecimalToDate(hrFinal);

        GregorianCalendar calendarHoraInicial = new GregorianCalendar();
        calendarHoraInicial.setTime(horaInicial);

        GregorianCalendar calendarHoraFinal = new GregorianCalendar();
        ArrayList horas = new ArrayList();
        String minuto = String.valueOf(intervalo);

        calendarHoraFinal.setTime(horaFinal);
        while (true) {
            horas.add(horaInicial);
            calendarHoraInicial.add(Calendar.MINUTE, Integer.valueOf(minuto)
                    .intValue());
            horaInicial = calendarHoraInicial.getTime();
            if (horaInicial.getTime() >= horaFinal.getTime()) {
            	horas.add(horaInicial);
                break;
            }
        }
        return horas;
    }

	public static Object getValorAtributo(Object obj, String atrib) {
		Object result = null;
		try {
			result = Reflexao.getPropertyValue(obj, atrib);
		}catch(Exception e){
			return result;
		}
		return result;

	}

	public static final Boolean isVersionFree(HttpServletRequest request)throws Exception{
		/* O arquivo free.txt e' criado ao gerar o build_free.xml e existe apenas na versao gratuita. */
		File arquivo = new File(System.getProperty("user.dir") + "/pages/citsmartFree/free.txt");
		try (InputStream is = new FileInputStream(arquivo)) {
			is.read();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}

	public static final void setValorAtributo(String atributo, Object obj, Object valor) throws Exception {

		StringTokenizer str = new StringTokenizer(atributo, ".");
		int elementos = str.countTokens();
		String elemento = "";
		if (valor instanceof BigDecimal)
			valor = UtilNumbersAndDecimals.changeBigDecimalToDouble((BigDecimal) valor);
		try {

			while (str.hasMoreElements()) {
				elemento = elemento + str.nextElement().toString();
				if (elementos > 1) {

					// Verificar se ja foi instanciado
					Object objAux = Reflexao.getPropertyValue(obj, elemento);
					if (objAux == null) {
						Reflexao.setPropertyValue(obj, elemento, Reflexao.getReturnType(obj, elemento).newInstance());
					}
					elementos = elementos - 1;
					elemento = elemento + ".";

				} else
					Reflexao.setPropertyValue(obj, elemento, valor);
			}
		} catch (IllegalArgumentException iaex) {
			throw new Exception("O tipo de dado do atributo " + elemento + " da classe " + obj.getClass().getName() + " Ã© diferente do tipo usado no banco de dados " + valor.getClass().getName());
		}

	}

	public static final Object getObjectValue(Collection lista, String atributo, Object valor) {
		Iterator it = lista.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			Object val = getValorAtributo(obj, atributo);
			if (val.equals(valor)) {
				return obj;
			}
		}
		return null;
	}

	public static final void geraXml(String arquivo, List lista) throws FileNotFoundException {
		XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(arquivo)));
		e.writeObject(lista);
		e.close();

	}

	public static final List recuperaXML(String arquivo) throws FileNotFoundException {
		XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)));
		Object result = d.readObject();
		d.close();
		return (List) result;
	}

	public static List converteExcessao(Throwable e) {

		List result = new ArrayList();
		StackTraceElement[] ste = e.getStackTrace();

		SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");
		result.add(e.getClass() + ": " + e.getMessage() + ": " + spd.format(new Date()));
		for (int i = 0; i < ste.length; i++) {
			result.add("	at " + ste[i].getClassName() + "." + ste[i].getMethodName() + "(" + ste[i].getFileName() + ":" + ste[i].getLineNumber() + ')');
		}

		return result;

	}

	public static Collection getResultadoImplosao(String conta, String mascara) {
		String contaMask = setMascaraContabil(conta, mascara);
		List lista = new ArrayList();
		StringTokenizer tok = new StringTokenizer(contaMask, ".");
		String contaImplosao = "";
		while (tok.hasMoreTokens()) {
			contaImplosao = contaImplosao + tok.nextToken();
			lista.add(ajustaContaContabil(contaImplosao, mascara));
		}
		return lista;

	}

	public static String setMascaraContabil(String conta, String mascara) {

		String result = "";
		StringTokenizer tok = new StringTokenizer(mascara, ".");
		conta = ajustaContaContabil(conta, mascara);
		int anterior = 0;
		while (tok.hasMoreTokens()) {
			String elemento = tok.nextToken();
			if (anterior != 0) {
				result = result + "." + conta.substring(anterior, anterior + elemento.length());
			} else
				result = result + conta.substring(anterior, anterior + elemento.length());

			anterior = anterior + elemento.length();

		}

		return result;

	}

	public static String ajustaContaContabil(String conta, String mascara) {

		StringTokenizer tok = new StringTokenizer(mascara, ".");
		String tmp = "";
		String result = conta;
		while (tok.hasMoreTokens()) {
			String elemento = tok.nextToken();
			tmp = tmp + elemento;
		}

		int iTamanhoConta = conta.length();
		int iTamanhoMascara = tmp.length();
		if (iTamanhoConta < iTamanhoMascara) {
			int resto = iTamanhoMascara - iTamanhoConta;

			for (int i = 0; i < resto; i++) {
				result = result + "0";
			}

		}

		return result;

	}

	public static String getJavaType(int type) {
		String result = null;
		switch (type) {
		case Types.BIGINT:
			result = "Integer";
			break;
		case Types.DATE:

			result = "java.sql.Date";
			break;
		case Types.BLOB:

			result = "java.sql.Blob";
			break;
		case Types.BOOLEAN:

			result = "Boolean";
			break;
		case Types.CHAR:

			result = "String";
			break;
		case Types.DOUBLE:

			result = "Double";
			break;
		case Types.FLOAT:

			result = "Float";
			break;
		case Types.INTEGER:

			result = "Integer";
			break;
		case Types.JAVA_OBJECT:

			result = "Object";
			break;
		case Types.LONGVARCHAR:

			result = "String";
			break;
		case Types.NUMERIC:

			result = "Double";
			break;
		case Types.REAL:

			result = "Double";
			break;
		case Types.SMALLINT:

			result = "Integer";
			break;
		case Types.TIME:

			result = "java.sql.Timestamp";
			break;

		case Types.TIMESTAMP:

			result = "java.sql.Timestamp";
			break;
		case Types.TINYINT:

			result = "Integer";
			break;

		case Types.VARCHAR:
			result = "String";
			break;
		default:
			result = null;
			break;
		}

		return result;

	}

	public static Integer[] convertStrToArrayInteger(String strParaConvert, String token) {
		if (strParaConvert == null)
			return null;
		if (strParaConvert.equalsIgnoreCase(""))
			return null;
		String strAux[] = strParaConvert.split(token);
		if (strAux == null)
			return null;
		Integer[] intRetorno = new Integer[strAux.length];

		for (int i = 0; i < strAux.length; i++) {
			try {
				if ("".equalsIgnoreCase(strAux[i].trim())){
					intRetorno[i] = new Integer(0);
				}else{
					intRetorno[i] = new Integer(Integer.parseInt(strAux[i]));
				}
			} catch (Exception e) {
				intRetorno[i] = null;
			}
		}
		return intRetorno;
	}


	public static BigDecimal[] convertStrToArrayBigDecimal(String strParaConvert, String token) {
		if (strParaConvert == null)
			return null;
		if (strParaConvert.equalsIgnoreCase(""))
			return null;
		String strAux[] = strParaConvert.split(token);
		if (strAux == null)
			return null;
		BigDecimal[] intRetorno = new BigDecimal[strAux.length];

		for (int i = 0; i < strAux.length; i++) {
			try {
				if ("".equalsIgnoreCase(strAux[i].trim())){
					double x = 0;
					intRetorno[i] = new BigDecimal(x);
				}else{
					intRetorno[i] = new BigDecimal(strAux[i]);
				}
			} catch (Exception e) {
				intRetorno[i] = null;
			}
		}
		return intRetorno;
	}

	public static String getTituloHistoricoExtintor(Integer id) {
		return getMapHistoricoExtintor().get(id.toString()).toString();
	}

	private static Map getMapHistoricoExtintor() {
		Map mapHistoricoExtintor = new HashMap();
		mapHistoricoExtintor.put("2", "Inspecionado");
		mapHistoricoExtintor.put("3", "Reparado");
		mapHistoricoExtintor.put("4", "Instrucao");
		mapHistoricoExtintor.put("5", "Incendio");
		mapHistoricoExtintor.put("2", "Inspecionado");
		return mapHistoricoExtintor;
	}

	/**
	 * Retorna lista de uma String separada por um token qualquer
	 *
	 * @param acessos
	 * @return
	 * @author wagner.filho
	 */
	public static List getListaDeToken(String riscos, String token) {
		List listaRetorno = new ArrayList();
		StringTokenizer st = new StringTokenizer(riscos, token);
		while (st.hasMoreTokens()) {
			listaRetorno.add(st.nextToken());
		}
		return listaRetorno;
	}


    public static String comparacaoSQLString (String campo, String comparacao, String parametro,List listaParametros){

    	String funcaoUpper = Constantes.getValue("FUNC_UPPER");
    	if(funcaoUpper!=null && funcaoUpper.trim().length()>0){
    	   funcaoUpper= funcaoUpper.replaceAll("<FIELD>", campo);
    	   listaParametros.add(parametro.toUpperCase());
    	   return " "+funcaoUpper+" "+comparacao+" ?";
    	}else{
    		listaParametros.add(parametro);
    		return " "+campo+" "+comparacao+" ?";
    	}
    }

    public static String getNomeClasseSemPacote(Class classe){

    	String str = classe.getName();
    	StringTokenizer st =new StringTokenizer(str,".");
    	String result = "";
    	while(st.hasMoreTokens()){
    		result = st.nextToken();
    	}

    	return result;



    }

	public static String geraSenhaAleatoria(int numCaracteres){
		String result="";
		for(int i=1;i<=numCaracteres;i++){
			result+=getCaracterAleatorio();
		}
		return result;
	}

	public static char getCaracterAleatorio(){
		char[] str = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','w','v','x','y','z','1','2','3','4','5','6','7','8','9','0'};
		return str[new Double(62*Math.random()).intValue()];

	}

	public static int geraNumeroAleatorio(int limite){

		return new Double(limite*Math.random()).intValue();

	}



}