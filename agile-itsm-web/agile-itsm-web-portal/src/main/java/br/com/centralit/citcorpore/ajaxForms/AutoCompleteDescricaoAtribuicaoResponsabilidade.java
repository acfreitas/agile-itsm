package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoAtribuicaoResponsabilidadeDTO;
import br.com.centralit.citcorpore.rh.negocio.DescricaoAtribuicaoResponsabilidadeService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

@SuppressWarnings({"rawtypes","unchecked"})
public class AutoCompleteDescricaoAtribuicaoResponsabilidade extends AjaxFormAction{

	
	@Override
	public Class getBeanClass() {
		return DescricaoAtribuicaoResponsabilidadeDTO.class;
	}
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		String consulta = request.getParameter("query");
		DescricaoAtribuicaoResponsabilidadeService service = (DescricaoAtribuicaoResponsabilidadeService) ServiceLocator.getInstance().getService(DescricaoAtribuicaoResponsabilidadeService.class, null);
		
		Collection<DescricaoAtribuicaoResponsabilidadeDTO> descricoes =  new ArrayList<DescricaoAtribuicaoResponsabilidadeDTO>();
		descricoes = service.findByNome(consulta);
		
		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();
		List lst = new ArrayList();
		List lstVal = new ArrayList();
		
		if (descricoes != null && !descricoes.isEmpty()){

			for(DescricaoAtribuicaoResponsabilidadeDTO descricao : descricoes) {
				lst.add(descricao.getDescricao()); 
				lstVal.add(descricao.getIdDescricao());
			}
				
		}
		
		autoCompleteDTO.setQuery(consulta);
		autoCompleteDTO.setSuggestions(lst);
		autoCompleteDTO.setData(lstVal);
		
		String json = gson.toJson(autoCompleteDTO);
		request.setAttribute("json_response", json);
		
		
	}

}
