package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

@SuppressWarnings("rawtypes")
public class AutoCompleteCidade extends AjaxFormAction {
	
	public Class getBeanClass() {
		return CidadesDTO.class;
	}

	
	@SuppressWarnings("unchecked")
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//Corrige o enconding do parâmetro desejado.
		String consulta = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");   
		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
		Collection<CidadesDTO> cidades =  new ArrayList<CidadesDTO>();
		
		String idCidadeS = request.getParameter("idEstado");
		Integer idEstado = null;
		if(idCidadeS != null && !idCidadeS.isEmpty()){
			idEstado = Integer.parseInt(idCidadeS);
			cidades = cidadesService.findByIdEstadoAndNomeCidade(idEstado, consulta);
		}else {
			cidades = cidadesService.findByNome(consulta);			
		}
		
		
		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();
		List lst = new ArrayList();
		List lstVal = new ArrayList();
		
		if (cidades != null && !cidades.isEmpty()){

			for(CidadesDTO cidade : cidades) {
				if(idCidadeS != null && !idCidadeS.isEmpty())
					lst.add(cidade.getNomeCidade());
				else
					lst.add(cidade.getNomeCidade() + " - " + cidade.getNomeUf()); 
				lstVal.add(cidade.getIdCidade());
			}
				
		}
		
		autoCompleteDTO.setQuery(consulta);
		autoCompleteDTO.setSuggestions(lst);
		autoCompleteDTO.setData(lstVal);
		
		String json = gson.toJson(autoCompleteDTO);
		request.setAttribute("json_response", json);
		
	}
}