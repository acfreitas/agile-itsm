package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.ParceiroDTO;
import br.com.centralit.citcorpore.negocio.ParceiroService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

/**
 * @author david.silva
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class AutoCompleteParceiro extends AjaxFormAction{

	@Override
	public Class getBeanClass() {
		return ParceiroDTO.class;
	}
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String consulta = request.getParameter("query");
		ParceiroService parceiroService = (ParceiroService) ServiceLocator.getInstance().getService(ParceiroService.class, null);
		
		Collection<ParceiroDTO> parceiros =  new ArrayList<ParceiroDTO>();
		parceiros = parceiroService.consultarFornecedorPorRazaoSocialAutoComplete(consulta);
		
		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();
		List lst = new ArrayList();
		List lstVal = new ArrayList();
		
		if (parceiros != null && !parceiros.isEmpty()){

			for(ParceiroDTO parceiro : parceiros) {
				lst.add(parceiro.getNome()); 
				lstVal.add(parceiro.getIdParceiro());
			}
				
		}
		
		autoCompleteDTO.setQuery(consulta);
		autoCompleteDTO.setSuggestions(lst);
		autoCompleteDTO.setData(lstVal);
		
		String json = gson.toJson(autoCompleteDTO);
		request.setAttribute("json_response", json);
	}

}
