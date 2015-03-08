package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.rh.bean.AtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.negocio.AtitudeIndividualService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AutoCompleteAtitudeIndividual extends AjaxFormAction {
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String consulta = request.getParameter("query");
		AtitudeIndividualService service = (AtitudeIndividualService) ServiceLocator.getInstance().getService(AtitudeIndividualService.class, null);

		Collection<AtitudeIndividualDTO> atitudesIndividuais = new ArrayList<AtitudeIndividualDTO>();
		atitudesIndividuais = service.findByNome(consulta);

		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();
		List lst = new ArrayList();
		List lstVal = new ArrayList();

		if (atitudesIndividuais != null && !atitudesIndividuais.isEmpty()) {

			for (AtitudeIndividualDTO atitude : atitudesIndividuais) {
				lst.add(atitude.getDescricao());
				lstVal.add(atitude.getIdAtitudeIndividual());
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
		return AtitudeIndividualDTO.class;
	}

}
