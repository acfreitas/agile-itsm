package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.ServicoCorporeBIDTO;
import br.com.centralit.citcorpore.negocio.ServicoCorporeBIService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

@SuppressWarnings("rawtypes")
public class AutoCompleteServicoCorporeBI extends AjaxFormAction {

	public Class getBeanClass() {
		return ServicoCorporeBIDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String consulta = request.getParameter("query");

		Collection<ServicoCorporeBIDTO> listServicoCorporeBIDTO = new ArrayList<ServicoCorporeBIDTO>();

		ServicoCorporeBIService servicoCorporeBIService = (ServicoCorporeBIService) ServiceLocator.getInstance().getService(ServicoCorporeBIService.class, null);
		listServicoCorporeBIDTO = servicoCorporeBIService.findByNome(consulta);

		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();

		List<String> listNome = new ArrayList<String>();
		List<Integer> listIdServico = new ArrayList<Integer>();

		if (listServicoCorporeBIDTO != null) {
			for (ServicoCorporeBIDTO servicoCorporeBIDTO : listServicoCorporeBIDTO) {
				if (servicoCorporeBIDTO.getIdServicoCorpore() != null) {
					listNome.add(servicoCorporeBIDTO.getNomeServico());
					listIdServico.add(servicoCorporeBIDTO.getIdServicoCorpore());
				}
			}
		}
		autoCompleteDTO.setQuery(consulta);
		autoCompleteDTO.setSuggestions(listNome);
		autoCompleteDTO.setData(listIdServico);

		String json = "";

		if (request.getParameter("colection") != null) {
			json = gson.toJson(listServicoCorporeBIDTO);
		} else {
			json = gson.toJson(autoCompleteDTO);
		}

		request.setAttribute("json_response", json);
	}
}