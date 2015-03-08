package br.com.citframework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.citframework.dto.FaixaEtariaDTO;
import br.com.citframework.excecao.LogicException;

public class UtilStrings {
	public static String	DIREITA		= "D";
	public static String	ESQUERDA	= "E";
	
	/**
	 * Remove caracteres especiais.
	 * @param string (retorna string formatada sem caracteres especiais)
	 * @return
	 */
	public static String removeCaracteresEspeciais(String string) {
		string = string.replaceAll("á", "a");
		string = string.replaceAll("é", "e");
		string = string.replaceAll("í", "i");
		string = string.replaceAll("ó", "o");
		string = string.replaceAll("ú", "u");

		string = string.replaceAll("Á", "A");
		string = string.replaceAll("É", "E");
		string = string.replaceAll("Í", "I");
		string = string.replaceAll("Ó", "O");
		string = string.replaceAll("Ú", "U");

		string = string.replaceAll("à", "a");
		string = string.replaceAll("è", "e");
		string = string.replaceAll("ì", "i");
		string = string.replaceAll("ò", "o");
		string = string.replaceAll("ù", "u");

		string = string.replaceAll("À", "A");
		string = string.replaceAll("È", "E");
		string = string.replaceAll("Ì", "I");
		string = string.replaceAll("Ò", "O");
		string = string.replaceAll("Ù", "U");

		string = string.replaceAll("â", "a");
		string = string.replaceAll("ê", "e");
		string = string.replaceAll("î", "i");
		string = string.replaceAll("ô", "o");
		string = string.replaceAll("û", "u");

		string = string.replaceAll("Â", "A");
		string = string.replaceAll("Ê", "E");
		string = string.replaceAll("Î", "I");
		string = string.replaceAll("Ô", "O");
		string = string.replaceAll("Û", "U");

		string = string.replaceAll("ã", "a");
		string = string.replaceAll("õ", "o");

		string = string.replaceAll("Ã", "A");
		string = string.replaceAll("Õ", "O");

		string = string.replaceAll("ç", "c");
		string = string.replaceAll("Ç", "C");
  
		return string;
	}
	/**
	 * Retorna vazio ("") caso a string passada seja null,
	 * caso contrario, retorna a propria string.
	 * @param string
	 * @return
	 */
	public static String nullToVazio(String string){
		return string == null ? "" : string;
	}
	
	public static String nullToNaoDisponivel(String string){
		return string == null ? "N/A" : string;
	}
	
	
	
	public static String vazioToNull(String string){
		if(string!=null &&string.trim().length()==0)
			return null;
		return string;
	}
	/**
	 * Verifica se uma string esta vazia
	 * @param valor
	 * @return
	 */
	public static final boolean stringVazia(String valor) {
		if (valor != null && valor.length() > 0)
			return false;
		return true;
	}
	/**
	 * Completa uma string
	 * @param origem (String de origem)
	 * @param complemento (String a completar)
	 * @param tamanho (Tamanho que deve ficar a string final)
	 * @param direcao (direcao do complemento - UtilStrings.DIREITA, UtilStrings.ESQUERDA)
	 * @return
	 */
	public static String completaString(String origem, String complemento, int tamanho, String direcao) {

		for (int i = origem.length(); i < tamanho; i++) {

			if (direcao.equals(DIREITA))
				origem = origem + complemento;
			else if (direcao.equals(ESQUERDA))
				origem = complemento + origem;
		}

		return origem;

	}
	/**
	 * Remove uma substring da string de origem e devolve a String origem
	 * formatada
	 * 
	 * @return
	 */
	public static String removeSubstring(String origem, String substring) {
		StringBuilder str = new StringBuilder(origem);
		int idx = str.indexOf(substring);

		while (idx > -1) {
			str = str.delete(idx, idx + substring.length());
			idx = str.indexOf(substring);
		}

		return str.toString();
	}
	/**
	 * Verifica o tamanho de uma string, lancando excessao
	 * @param valor
	 * @param label
	 * @param tamanho
	 * @throws LogicException
	 */
	public static void verificaTamanho(String valor, String label, int tamanho) throws LogicException {

		if (valor != null && valor.length() > tamanho) {
			throw new LogicException("Tamanho do campo " + label + "(" + valor.length() + ") e maior do que o permitido(" + tamanho + ").");
		}

	}
	/**
	 * Verifica se uma string esta vazia, lancando excessao
	 * @param valor
	 * @param label
	 * @throws LogicException
	 */
	public static void verificaBranco(String valor, String label) throws LogicException {

		if (valor == null || valor.trim().length() == 0) {
			throw new LogicException(label + " : Campo Obrigatório");
		}

	}
	/**
	 * Converte a 1.a letra em maiscula ou minuscula, de acordo com o parametro
	 * @param str
	 * @param tipo - U ou L (Upper ou Lower)
	 * @return
	 */
	public static String convertePrimeiraLetra(String str, String tipo) {
		// str = str.toLowerCase();
		if (StringUtils.isBlank(str)){
			return "";
		}
		if (tipo.equals("U"))
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		else
			return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	/**
	 * Gera as inicias de um nome em maiusculas.
	 * 	Exemplo: EMAURI GOMES --> Emauri Gomes, ou:
	 * 			 emauri gomes --> Emauri Gomes.
	 * @param str
	 * @return
	 */
	public static String iniciaisMaiusculas(String str) {
		String ultimaLetra=" ";
		String novaLetra="";
		String resultado = "";
		for (int i=0;i < str.length();++i){
			if (ultimaLetra.equals(" ")){
				novaLetra = str.substring(i, i+1).toUpperCase();
			}else{	
				novaLetra = str.substring(i, i+1).toLowerCase();
			}
			ultimaLetra = str.substring(i, i+1);
			resultado = resultado + novaLetra;
		}
		return resultado;
	}
	/**
	 * Gera um noma de busca
	 * 	Exemplo: JOÃO TÍBURSÚLO  --> JOAOTIBURSULO
	 * @param nomePar
	 * @return
	 */
	public static String generateNomeBusca(String nomePar){
		if (nomePar == null) return null;
	    int i;
	    String strSaida = "";
	    for (i =0; i < nomePar.length(); i++){
	        if ( isValidCharFind(nomePar.charAt(i)) ){
	            strSaida = strSaida + nomePar.charAt(i);
	        }else{
	            strSaida = strSaida + changeCharInvalid(nomePar.charAt(i));
	        }
	    }
	    return strSaida;
	}
	/**
	 * Gera um noma de busca, mas não exclui o caracter coringa.
	 * 	Exemplo: %JOÃO TÍBURSÚLO  --> %JOAOTIBURSULO
	 * @param nomePar
	 * @return
	 */	
	public static String generateNomeBuscaComCaracCoringa(String nomePar){
		if (nomePar == null) return null;
	    int i;
	    String strSaida = "";
	    for (i =0; i < nomePar.length(); i++){
	        if ( nomePar.charAt(i) == '%' || isValidCharFind(nomePar.charAt(i)) ){
	            strSaida = strSaida + nomePar.charAt(i);
	        }else{
	            strSaida = strSaida + changeCharInvalid(nomePar.charAt(i));
	        }
	    }
	    return strSaida;
	}	
	/**
	 * Verifica se um caracter eh valido.
	 * @param c
	 * @return
	 */
	public static boolean isValidCharFind(char c){
	    switch(c){
	    	case 'a':
	    	case 'b':
	    	case 'c':
	    	case 'd':
	    	case 'e':
	    	case 'f':
	    	case 'g':
	    	case 'h':
	    	case 'i':
	    	case 'j':
	    	case 'k':
	    	case 'l':
	    	case 'm':
	    	case 'n':
	    	case 'o':
	    	case 'p':
	    	case 'q':
	    	case 'r':
	    	case 's':
	    	case 't':
	    	case 'u':
	    	case 'v':
	    	case 'w':
	    	case 'x':
	    	case 'y':
	    	case 'z':
	    	case 'A':
	    	case 'B':
	    	case 'C':
	    	case 'D':
	    	case 'E':
	    	case 'F':
	    	case 'G':
	    	case 'H':
	    	case 'I':
	    	case 'J':
	    	case 'K':
	    	case 'L':
	    	case 'M':
	    	case 'N':
	    	case 'O':
	    	case 'P':
	    	case 'Q':
	    	case 'R':
	    	case 'S':
	    	case 'T':
	    	case 'U':
	    	case 'V':
	    	case 'W':
	    	case 'X':
	    	case 'Y':
	    	case 'Z':
	    	    return true;
	    }
	    return false;
	}
	
	/**
	 * Substitui um caracter especial por um caracter valido.
	 */
	public static String changeCharInvalid(char c){
		if (c == 'á' || c == 'â' || c == 'ã'){
			return "a";
		}
		else if (c == 'Á' || c == 'Â' || c == 'Ã'){ 
			return "A";
		}
		else if (c == 'é' || c == 'ê'){
			return "e";
		}
		else if (c == 'É' || c == 'Ê'){
			return "E";
		}
		else if (c == 'í' || c == 'î'){
			return "i";
		}
		else if (c == 'Í' || c == 'Î'){
			return "I";
		}
		else if (c == 'ó' || c == 'ô' || c == 'õ'){
			return "o";
		}
		else if (c == 'Ó' || c == 'Ô' || c == 'Õ'){
			return "O";
		}
		else if (c == 'ú' || c == 'û'){
			return "u";
		}
		else if (c == 'Ú' || c == 'Û'){
			return "U";
		}
		else if (c == 'Ç'){
			return "C";
		}
		else if (c == 'ç'){
			return "c";
		}
		else {
		    return "";
		}
	}
	/**
	 * Verifica se nao eh vazio.
	 * @param valor
	 * @return
	 */
	public static boolean isNotVazio(String valor) {
		return valor != null && !valor.equals("");
	}
	/**
	 * Retira codigo invalido (exemplo %20 que vem na URL)
	 * @param s
	 * @return
	 */
	public static String retiraCodigoInvalido(String s){
		if (s == null) return "";
		String result = s.replaceAll("%20", " ");
		return result;
	}
	/**
	 * Pega uma string e retorna apenas os numeros.
	 * 	Retirando letras e caracteres especiais.
	 * @param num
	 * @return
	 */
	public static String apenasNumeros(String num){
		if (num == null) return "";
		String retorno = "";
		for(int i =0; i < num.length(); i++){
			if (num.charAt(i) >= '0' && num.charAt(i) <= '9'){
				retorno += num.charAt(i);
			}
		}
		return retorno;
	}
	
	
	/**
	 * @param texto
	 * @return
	 * @author euler.ramos
	 */
	public static boolean soContemNumeros(String texto) {    
	    return texto.matches("^[0-9]*$");
	}
	
	
	/**
	 * Decodifica caracteres especiais vindos do JavaScript.
	 * @param str
	 * @return
	 */
	public static String decodeCaracteresEspeciais(String strParm){
		if (strParm == null) return null;
		String str = new String(strParm);
		str = str.replaceAll("\\[\\[\\[cedilhamin\\]\\]\\]", "ç");
		str = str.replaceAll("\\[\\[\\[cedilhamai\\]\\]\\]", "Ç");
		str = str.replaceAll("\\[\\[\\[aagudomin\\]\\]\\]", "á");
		str = str.replaceAll("\\[\\[\\[aagudomai\\]\\]\\]", "Á");
		str = str.replaceAll("\\[\\[\\[acrasemin\\]\\]\\]", "à");
		str = str.replaceAll("\\[\\[\\[acrasemai\\]\\]\\]", "À");
		str = str.replaceAll("\\[\\[\\[eagudomin\\]\\]\\]", "é");
		str = str.replaceAll("\\[\\[\\[eagudomai\\]\\]\\]", "É");
		str = str.replaceAll("\\[\\[\\[iagudomin\\]\\]\\]", "í");
		str = str.replaceAll("\\[\\[\\[iagudomai\\]\\]\\]","Í");
		str = str.replaceAll("\\[\\[\\[oagudomin\\]\\]\\]","ó");
		str = str.replaceAll("\\[\\[\\[oagudomai\\]\\]\\]","Ó");
		str = str.replaceAll("\\[\\[\\[uagudomin\\]\\]\\]","ú");
		str = str.replaceAll("\\[\\[\\[uagudomai\\]\\]\\]","Ú");
		str = str.replaceAll("\\[\\[\\[acircmin\\]\\]\\]","â");
		str = str.replaceAll("\\[\\[\\[acircmai\\]\\]\\]","Â");
		str = str.replaceAll("\\[\\[\\[ecircmin\\]\\]\\]","ê");
		str = str.replaceAll("\\[\\[\\[ecircmai\\]\\]\\]","Ê");
		str = str.replaceAll("\\[\\[\\[icircmin\\]\\]\\]","î");
		str = str.replaceAll("\\[\\[\\[icircmai\\]\\]\\]","Î");
		str = str.replaceAll("\\[\\[\\[ocircmin\\]\\]\\]","ô");
		str = str.replaceAll("\\[\\[\\[ocircmai\\]\\]\\]","Ô");
		str = str.replaceAll("\\[\\[\\[ucircmin\\]\\]\\]","û");
		str = str.replaceAll("\\[\\[\\[ucircmai\\]\\]\\]","Û");
		str = str.replaceAll("\\[\\[\\[atilmin\\]\\]\\]","ã");
		str = str.replaceAll("\\[\\[\\[atilmai\\]\\]\\]","Ã");
		str = str.replaceAll("\\[\\[\\[otilmin\\]\\]\\]","õ");
		str = str.replaceAll("\\[\\[\\[otilmai\\]\\]\\]","Õ");
		str = str.replaceAll("\\[\\[\\[ehcomercial\\]\\]\\]","&");		
		str = str.replaceAll("[\\u2018]","'");	
		str = str.replaceAll("[\\u2019]","'");	
		str = str.replaceAll("[\\u201C]","\"");	
		str = str.replaceAll("[\\u201D]","\"");	
		
		return str;
	}
	
	public static String negritaPalavraHtml(String frase,String palavra){
		
		String result= "";
		StringBuilder tmpAux = new StringBuilder(frase);
		StringBuilder tmpFrase = new StringBuilder(UtilStrings.removeCaracteresEspeciais(frase).toUpperCase()); 
		String tmpPalavra = UtilStrings.removeCaracteresEspeciais(palavra).toUpperCase();
		
		while(tmpFrase.indexOf(tmpPalavra)>-1){
			
			int indice = tmpFrase.indexOf(tmpPalavra);
			tmpFrase = new StringBuilder(tmpFrase.substring(indice+tmpPalavra.length()));
			String wk = tmpAux.substring(indice, indice+tmpPalavra.length());
			wk = "<b>"+wk+"</b>";
			tmpAux =  tmpAux.insert(indice+tmpPalavra.length(), "</b>");
			tmpAux =  tmpAux.insert(indice, "<b>");
			result+= tmpAux.substring(0,tmpAux.indexOf(wk)+wk.length());			
			tmpAux= new StringBuilder(tmpAux.substring(tmpAux.indexOf(wk)+wk.length()));

		}
		
		return result+tmpAux;

	}
	
	/**
     * Substitui o parâmetro '{0}' que está na String pelo parâmetro informado.
     * @param str - String a ser ajustada.
     * @param param - parâmetro a ser aplicado.
     * @return String ajustada com o parâmetro informado.
     */
    public static String setParametro(final String str, final String param)
    {
        if(isNotVazio(str))
            return str.replaceAll("[{][0][}]", param);
        return "";
    }
    
    /**
     * Substitui os parâmetros '{0}' e '{1}' que estão na String pelos 
     * parâmetros informados.
     * @param str - String a ser ajustada.
     * @param param0 - parâmetro a ser aplicado.
     * @param param1 - parâmetro a ser aplicado.
     * @return String ajustada com os parâmetros informados.
     */
    public static String setParametros(final String str, final String param0, 
            final String param1)
    {
        if(isNotVazio(str))
            return setParametro(str, param0).replaceAll("[{][1][}]", param1);
        return "";
    }
    
    /**
     * Substitui os parâmetros '{0}', '{1}' e '{2}' que estão na String pelos 
     * parâmetros informados.
     * @param str - String a ser ajustada.
     * @param param0 - parâmetro a ser aplicado.
     * @param param1 - parâmetro a ser aplicado.
     * @param param2 - parâmetro a ser aplicado.
     * @return String ajustada com os parâmetros informados.
     */
    public static String setParametros(final String str, final String param0, 
            final String param1, final String param2)
    {
        if(UtilStrings.isNotVazio(str))
            return setParametros(str, param0, param1).replaceAll("[{][2][}]", 
                    param2);
        return "";
    }
    
    /**
     * Ajusta o index informado para a expressão regular no padrão 
     * 1 = [1]; 123 = [1][2][3]
     * @param index
     * @return
     */
    private static final String ajustarIndexParametros(final int index)
    {
        String ret = "";
        String num = String.valueOf(index);
        for(int i = 0; i < num.length(); i++){
            ret += "[" + num.charAt(i) + "]";
        }
        return ret;
    }
    
    /**
     * Substitui os parâmetros '{0}', '{1}' ... '{n}' que estão na String pelos 
     * parâmetros informados.
     * @param str - String a ser ajustada.
     * @param params - array de parâmetros a serem aplicados.
     * @return String ajustada com os parâmetros informados.
     */
    public static String setParametros(final String str, final String[] params)
    {
        String aux = "";
        if(UtilStrings.isNotVazio(str) && params != null && params.length > 0){
            aux = new String(str);
            for(int i = 0; i < params.length; i++){
                aux = aux.replaceAll("[{]" + ajustarIndexParametros(i) 
                        + "[}]", params[i]);
            }
        }
        return aux;
    }
    
    /**
     * Substitui os parâmetros '{0}', '{1}' ... '{n}' que estão na String pelos 
     * parâmetros informados.
     * @param str - String a ser ajustada.
     * @param params - lista de parâmetros a serem aplicados.
     * @return String ajustada com os parâmetros informados.
     */
    public static String setParametros(final String str, final List params)
    {
        String aux = "";
        if(UtilStrings.isNotVazio(str) && params != null && !params.isEmpty()){
            aux = new String(str);
            for(int i = 0; i < params.size(); i++){
                aux = aux.replaceAll("[{]" + ajustarIndexParametros(i) 
                        + "[}]", params.get(i).toString());
            }
        }
        return aux;
    }
    
    public static Collection getFaixasEtariasFromString(String faixas){
    	if (faixas == null || faixas.trim().equalsIgnoreCase("")){
    		return null;
    	}
    	Collection colRetorno = new ArrayList();
    	String[] strAux = faixas.split(";");
    	if (strAux != null){
    		for(int i = 0; i < strAux.length; i++){
    			if (strAux[i] != null && !strAux[i].trim().equalsIgnoreCase("")){
	    			String[] str = strAux[i].trim().split("-");
	    			FaixaEtariaDTO faixa = new FaixaEtariaDTO();
	    			faixa.setInicio(new Integer(-1));
	    			faixa.setFim(new Integer(999));
	    			if (str != null){
	    				if (str[0] != null && !str[0].trim().equalsIgnoreCase("")){
	    					try{
	    						faixa.setInicio(new Integer(str[0]));
	    					}catch (Exception e) {
							}
	    				}
	    				if (str[1] != null && !str[1].trim().equalsIgnoreCase("")){
	    					if (str[1].trim().equalsIgnoreCase("*")){
	    						faixa.setFim(new Integer(999));
	    					}else{
	    						try{
	    							faixa.setFim(new Integer(str[1]));
	    						}catch (Exception e) {
								}
	    					}
	    				}
	    				colRetorno.add(faixa);
	    			}
    			}    			
    		}
    	}
    	return colRetorno;
    }
    
    public static String getClassName(String bufferClasse){
    	if (bufferClasse == null || bufferClasse.equalsIgnoreCase("")){
    		return "";
    	}
    	int index = bufferClasse.indexOf("class ");
    	String auxBuffer = bufferClasse;
    	String className = "";
    	if (index > -1){
    		auxBuffer = auxBuffer.substring(index);
    		auxBuffer = auxBuffer.replaceAll("class ", "");
    		for(int i = 0; i < auxBuffer.length(); i++){
    			if (auxBuffer.charAt(i) == '{'){
    				break;
    			}else{
    				className += auxBuffer.charAt(i);
    			}
    		}
    	}
    	className = className.replaceAll("\n", "");
    	className = className.replaceAll("\r", "");
    	className = className.trim();
    	return className;
    }
	public static String replaceInvalid(String string) {
		String retorno = "";
		retorno = string.replace((char) 10, ' ');
		retorno = retorno.replace((char) 7, ' ');
		retorno = retorno.replace((char) 9, ' ');
		retorno = retorno.replace((char) 150, '-');
		
		retorno = retorno.replace((char) 147, '"');
		retorno = retorno.replace((char) 148, '"');
		retorno = retorno.replace((char) 8220, '"');
		retorno = retorno.replace((char) 8221, '"');
		retorno = retorno.replace((char) 8211, '-');
		retorno = retorno.replace((char) 8217, ' ');
		String auxRetorno = "";
		for (int i = 0; i < retorno.length(); i++){
			int auxChar = retorno.charAt(i);
			if (auxChar < 254 && auxChar > 31){
				auxRetorno = auxRetorno + retorno.charAt(i); 
			}
		}

		return auxRetorno;
	}    
    public static String retiraApostrofe(String str){
    	if (str == null){
    		return "";
    	}
    	return str.replaceAll("'", "");
    }
    public static String retiraAspas(String str){
    	if (str == null){
    		return "";
    	}
    	return str.replaceAll("\"", "");
    }  
    public static String retiraAspasApostrofe(String str){
    	return retiraAspas(retiraApostrofe(str));
    }    
    /**
     * @author flavio.santana
     * Remove Caracteres especiais
     * @param parameter
     * @return
     */
    public static String getParameter(String parameter) { 
    	return parameter.replaceAll("([^À-úA-Za-z0-9!\\s/@$%&*()#\\-_+º|<,.>;:?=]+)",""); 
	}
    
    public static String retiraCaracteresEspeciais(String parameter) { 
    	return parameter.replaceAll("([^À-úA-Za-z0-9!\\s/@$%&*()#-_+º|<,.>;:?=]+)",""); 
	}
    
    public static String retiraEspacoPorUnderline(String str){
    	if (str == null){
    		return "";
    	}
    	return str.replaceAll(" ", "_");
    }
    
    /**
     * Retirado de uma implementação do github
     * O método unescapeJavaScript da classe StringEscapeUtils se faz necesssário para os dados antigos.
     * A nova implementação não usa o escapeJavascript para inserção no banco então apenas retorna os dados
     * Unescapes a string that contains standard Java escape sequences.
     */
    public static String unescapeJavaString(String st) {
        try {
        	return StringEscapeUtils.unescapeJavaScript(st);
        } catch(Exception e) {
        	e.printStackTrace();
        	return st;
        }
    }

    public static String limitarTamanho(String texto, Integer tamanho, boolean executaTrim) {
	if ((texto != null) && (texto.length() > 0)) {
	    if (executaTrim) {
		texto = texto.trim();
	    }
	    if (texto.length() > tamanho) {
		texto = texto.substring(0, tamanho);
	    }
	}
	return texto;
    }
    
    /**
     * @author cristian.guedes
     * Embora o nome desta classe seja auto-explicativo, esta função converte uma array de String para uma String com os valores delimitados com vírgula
     * e cada um entre aspas simples.
     */
    public static String StringArrayParaStringDelimitadaComVirgula(String[] qualArray) {
    	StringBuilder sb = new StringBuilder();
    	for (String n : qualArray) { 
    	    if (sb.length() > 0) sb.append(',');
    	    sb.append("'").append(n).append("'");
    	}
    	return sb.toString();    	
    }

}