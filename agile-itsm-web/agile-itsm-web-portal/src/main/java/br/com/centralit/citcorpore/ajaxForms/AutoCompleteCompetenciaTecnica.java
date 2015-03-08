package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.rh.bean.CompetenciaTecnicaDTO;
import br.com.centralit.citcorpore.rh.negocio.CompetenciaTecnicaService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

@SuppressWarnings({"rawtypes","unchecked"})
public class AutoCompleteCompetenciaTecnica extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {

		String consulta = request.getParameter("query");
		CompetenciaTecnicaService service = (CompetenciaTecnicaService) ServiceLocator.getInstance().getService(CompetenciaTecnicaService.class, null);
		
		Collection<CompetenciaTecnicaDTO> competencias =  new ArrayList<CompetenciaTecnicaDTO>();
		competencias = service.findByNome(consulta);
		
		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();
		List lst = new ArrayList();
		List lstVal = new ArrayList();
		
		if (competencias != null && !competencias.isEmpty()){

			for(CompetenciaTecnicaDTO competencia : competencias) {
				lst.add(competencia.getDescricao()); 
				lstVal.add(competencia.getIdCompetencia());
			}
				
		}
		
		autoCompleteDTO.setQuery(consulta);
		autoCompleteDTO.setSuggestions(lst);
		autoCompleteDTO.setData(lstVal);
		
		String json = gson.toJson(autoCompleteDTO);
		request.setAttribute("json_response", json);
		
	}

	@Override
	public Class getBeanClass() {
		return CompetenciaTecnicaDTO.class;
	}

}
