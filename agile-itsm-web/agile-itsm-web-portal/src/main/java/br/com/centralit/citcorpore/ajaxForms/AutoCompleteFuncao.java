package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

public class AutoCompleteFuncao extends AjaxFormAction {
	public Class getBeanClass() {
		return CargosDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//Corrige o enconding do par�metro desejado.
		String consulta = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");
		CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
		
		Collection colRetorno = cargosService.findByNome(consulta);
		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();
		List lst = new ArrayList();
		List lstVal = new ArrayList();
		
		if (colRetorno != null){
			for (Iterator it = colRetorno.iterator(); it.hasNext();){
				CargosDTO cargosDTO = (CargosDTO) it.next();
				if (cargosDTO.getIdCargo()!= null) {
					lst.add(cargosDTO.getNomeCargo()); 
					lstVal.add(cargosDTO.getIdCargo());
				}
			}
		}
		autoCompleteDTO.setQuery(consulta);
		autoCompleteDTO.setSuggestions(lst);
		autoCompleteDTO.setData(lstVal);
		
		String json = gson.toJson(autoCompleteDTO);
		request.setAttribute("json_response", json);
	}
}