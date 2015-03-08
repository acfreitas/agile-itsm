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
import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.negocio.CertificacaoCurriculoService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

public class AutoCompleteCertificacaoCurriculo extends AjaxFormAction {
	public Class getBeanClass() {
		return CertificacaoCurriculoDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//Corrige o enconding do parâmetro desejado.
		String consulta = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");
		CertificacaoCurriculoService certificacaoCurriculoService = (CertificacaoCurriculoService) ServiceLocator.getInstance().getService(CertificacaoCurriculoService.class, null);
		
		Collection colRetorno = certificacaoCurriculoService.findByNome(consulta);
		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();
		List lst = new ArrayList();
		List lstVal = new ArrayList();
		
		if (colRetorno != null){
			for (Iterator it = colRetorno.iterator(); it.hasNext();){
				CertificacaoCurriculoDTO certificacao = (CertificacaoCurriculoDTO) it.next();
				if (certificacao.getIdCertificacao()!= null) {
					lst.add(certificacao.getDescricao()); 
					lstVal.add(certificacao.getIdCertificacao());
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