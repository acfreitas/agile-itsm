package br.com.centralit.citajax.framework;

import java.util.List;

import javax.servlet.ServletContext;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.citframework.util.Constantes;

public class CITObjectProcess {
	/**
	 * Processa o objeto passado como parametro e retorna uma string javascript
	 * @param path
	 * @return
	 * @throws Exception 
	 */
	public String process(String path, ServletContext ctx) throws Exception{
		String strAux = "";
		String javaScriptObject = getObjectName(path);
		if (javaScriptObject == null) return null;
		Class classe = null;
		try{
			classe = Class.forName(Constantes.getValue("BEAN_LOCATION_PACKAGE") + "." + javaScriptObject);
		}catch (Exception e) {
			for(int i = 2; i < 100; i++){
				if (Constantes.getValue("BEAN_LOCATION_PACKAGE" + i) == null || Constantes.getValue("BEAN_LOCATION_PACKAGE" + i).equalsIgnoreCase("")){
					throw new Exception("CLASSE NAO ENCONTRADA: " + javaScriptObject);
				}
				try{
					classe = Class.forName(Constantes.getValue("BEAN_LOCATION_PACKAGE" + i) + "." + javaScriptObject);
					if (classe != null){ //Se conseguiu instanciar em algum dos pacotes corretamente, entao finaliza.
						break;
					}
				}catch (Exception e2) {
				}
			}
		}
		if (classe != null){
			Object objeto = classe.newInstance();
			List listaGets = CitAjaxReflexao.findGets(objeto);
			
			strAux = "function CIT_" + javaScriptObject + "(";
			String strAux2 = "";
			for(int i = 0; i < listaGets.size(); i++){
				if (((String)listaGets.get(i)).equalsIgnoreCase("class")) continue;
				
				Class  r = CitAjaxReflexao.getReturnType(objeto,(String)listaGets.get(i));
				if (!(r.getName().indexOf("Collection")>-1) && !(r.getName().indexOf("List")>-1)){
					strAux2 += "\tthis." + CitAjaxUtil.convertePrimeiraLetra((String)listaGets.get(i), "L") + " = '';\n";
				}
			}
			strAux2 += "\tthis.idControleCITFramework = null;\n";
			
			strAux += "){\n";
			strAux += strAux2;
			strAux += "} ";
		}
		
		return strAux;
	}
	public String getObjectName(String path){
		if (path.length() - 3 <= 0) return "";
		String strResult = "";
		for(int i = path.length() - 4; i >= 0; i--){
			if (path.charAt(i) == '/'){
				return strResult;
			}else{
				strResult = path.charAt(i) + strResult; 
			}
		}
		return strResult;
	}
}
