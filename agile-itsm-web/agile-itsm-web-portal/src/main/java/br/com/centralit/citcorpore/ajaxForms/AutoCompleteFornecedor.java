package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

@SuppressWarnings("rawtypes")
public class AutoCompleteFornecedor extends AjaxFormAction {
	
	public Class getBeanClass() {
		return FornecedorDTO.class;
	}

	
	@SuppressWarnings("unchecked")
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String consulta = request.getParameter("query");
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		
		Collection<FornecedorDTO> fornecedores =  new ArrayList<FornecedorDTO>();
		fornecedores = fornecedorService.consultarFornecedorPorRazaoSocialAutoComplete(consulta);
		
		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();
		List lst = new ArrayList();
		List lstVal = new ArrayList();
		
		if (fornecedores != null && !fornecedores.isEmpty()){

			for(FornecedorDTO fornecedor : fornecedores) {
				lst.add(fornecedor.getRazaoSocial()); 
				lstVal.add(fornecedor.getIdFornecedor());
			}
				
		}
		
		autoCompleteDTO.setQuery(consulta);
		autoCompleteDTO.setSuggestions(lst);
		autoCompleteDTO.setData(lstVal);
		
		String json = gson.toJson(autoCompleteDTO);
		request.setAttribute("json_response", json);
		
	}
}