package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.ServicoBIDTO;
import br.com.centralit.citcorpore.negocio.ServicoBIService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

@SuppressWarnings("rawtypes")
public class AutoCompleteServicoBI extends AjaxFormAction {

	public Class getBeanClass() {
		return ServicoBIDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String consulta = request.getParameter("query");

		String idConexaoBIStr = request.getParameter("idConexaoBI");
		Integer idConexaoBI = null;
		if (idConexaoBIStr != null && !idConexaoBIStr.equals("-1")) {
			idConexaoBI = Integer.parseInt(idConexaoBIStr);
		}

		Collection<ServicoBIDTO> listServicoBIDTO = new ArrayList<ServicoBIDTO>();

		if (idConexaoBI != null) {
			ServicoBIService servicoBIService = (ServicoBIService) ServiceLocator.getInstance().getService(ServicoBIService.class, null);
			listServicoBIDTO = servicoBIService.findByNomeEconexaoBI(consulta, idConexaoBI);
		}

		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();

		List<String> listNome = new ArrayList<String>();
		List<Integer> listIdServico = new ArrayList<Integer>();

		if (listServicoBIDTO != null) {
			for (ServicoBIDTO servicoBIDTO : listServicoBIDTO) {
				if (servicoBIDTO.getIdServico() != null) {
					listNome.add(servicoBIDTO.getNomeServico());
					listIdServico.add(servicoBIDTO.getIdServico());
				}
			}
		}
		autoCompleteDTO.setQuery(consulta);
		autoCompleteDTO.setSuggestions(listNome);
		autoCompleteDTO.setData(listIdServico);

		String json = "";

		if (request.getParameter("colection") != null) {
			json = gson.toJson(listServicoBIDTO);
		} else {
			json = gson.toJson(autoCompleteDTO);
		}

		request.setAttribute("json_response", json);
	}
}