package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;

import com.google.gson.Gson;

public class AutoCompleteTarefaAtual extends AjaxFormAction {
	
	@SuppressWarnings("rawtypes")
	public Class getBeanClass() {
		return ElementoFluxoDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//Corrige o enconding do parâmetro desejado.
		String consulta = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");
		ElementoFluxoDao elementoFluxoDao = new ElementoFluxoDao();
		Gson gson = new Gson();
		String json = "";
		
		List<ElementoFluxoDTO> lista;
		lista = elementoFluxoDao.listaElementoFluxo(consulta);
		if(lista == null)
			lista = new ArrayList<ElementoFluxoDTO>();

		json = gson.toJson(lista);
		
		request.setAttribute("json_response", json);
	}
}