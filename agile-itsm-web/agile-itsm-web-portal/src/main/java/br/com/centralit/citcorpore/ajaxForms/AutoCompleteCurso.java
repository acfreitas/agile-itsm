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
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.negocio.CursoService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

public class AutoCompleteCurso extends AjaxFormAction {
	public Class getBeanClass() {
		return CursoDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//Corrige o enconding do parâmetro desejado.
		String consulta = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");
		CursoService cursoService = (CursoService) ServiceLocator.getInstance().getService(CursoService.class, null);
		
		Collection colRetorno = cursoService.findByNome(consulta);
		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();
		List lst = new ArrayList();
		List lstVal = new ArrayList();
		
		if (colRetorno != null){
			for (Iterator it = colRetorno.iterator(); it.hasNext();){
				CursoDTO cursoDTO = (CursoDTO) it.next();
				if (cursoDTO.getIdCurso()!= null) {
					lst.add(cursoDTO.getDescricao()); 
					lstVal.add(cursoDTO.getIdCurso());
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