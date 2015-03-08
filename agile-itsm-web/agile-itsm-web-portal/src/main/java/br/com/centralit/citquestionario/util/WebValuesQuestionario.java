package br.com.centralit.citquestionario.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citquestionario.bean.RespostaItemAuxiliarDTO;
import br.com.citframework.util.Constantes;

public class WebValuesQuestionario {
	/**
	 * Atribui para a Collection os valores da requisicao.
	 * @return
	 */	
	public static Collection getFormValues(HttpServletRequest req){
		Collection colRespostas = new ArrayList();
		Enumeration en = req.getParameterNames();
		String[] strValores;
		String aux;
		int i;
		if ("S".equalsIgnoreCase(Constantes.getValue("IMPRIMIR_FORMULARIO_POSTADO_QUEST"))){
			System.out.println("****** >INFORMACOES DO FORMULARIO POSTADO ******");
		}
		while(en.hasMoreElements()) {
			String nomeCampo  = (String)en.nextElement();
			strValores = req.getParameterValues(nomeCampo);
			
			RespostaItemAuxiliarDTO respostaItem = new RespostaItemAuxiliarDTO();
			if (strValores.length == 0){
				respostaItem.setFieldName(nomeCampo.toUpperCase());
				respostaItem.setFieldValue(req.getParameter(nomeCampo));
				respostaItem.setMultiple(false);
				
				colRespostas.add(respostaItem);
				if ("S".equalsIgnoreCase(Constantes.getValue("IMPRIMIR_FORMULARIO_POSTADO_QUEST"))){
					System.out.println("===> " + nomeCampo + " : " + req.getParameter(nomeCampo));
				}
			} else {
				aux = "";
				for (i = 0; i < strValores.length; i++){
					if (!aux.equals("")){
						aux = aux + ConstantesQuestionario.CARACTER_SEPARADOR;
					}
					aux = aux + strValores[i]; 			
				}
				respostaItem.setFieldName(nomeCampo.toUpperCase());
				respostaItem.setFieldValue(aux);
				respostaItem.setMultiple(true);
				
				colRespostas.add(respostaItem);
				if ("S".equalsIgnoreCase(Constantes.getValue("IMPRIMIR_FORMULARIO_POSTADO_QUEST"))){
					System.out.println("===> " + nomeCampo + " : " + aux);
				}
			}
		}
		if ("S".equalsIgnoreCase(Constantes.getValue("IMPRIMIR_FORMULARIO_POSTADO_QUEST"))){
			System.out.println("***********************************************");
		}
		return colRespostas;
	}

}
