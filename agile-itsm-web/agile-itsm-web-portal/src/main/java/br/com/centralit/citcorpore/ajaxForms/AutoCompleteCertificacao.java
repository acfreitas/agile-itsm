package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.centralit.citcorpore.rh.negocio.CertificacaoService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

@SuppressWarnings({"rawtypes","unchecked"})
public class AutoCompleteCertificacao extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		String consulta = request.getParameter("query");
		CertificacaoService service = (CertificacaoService) ServiceLocator.getInstance().getService(CertificacaoService.class, null);
		
		Collection<CertificacaoDTO> certificacoes =  new ArrayList<CertificacaoDTO>();
		certificacoes = service.findByNome(consulta);
		
		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();
		List lst = new ArrayList();
		List lstVal = new ArrayList();
		
		if (certificacoes != null && !certificacoes.isEmpty()){

			for(CertificacaoDTO certificacao : certificacoes) {
				lst.add(certificacao.getDescricao()); 
				lstVal.add(certificacao.getIdCertificacao());
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
		return CertificacaoDTO.class;
	}

}
