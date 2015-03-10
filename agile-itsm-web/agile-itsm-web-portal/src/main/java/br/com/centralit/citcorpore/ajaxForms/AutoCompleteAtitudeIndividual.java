package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.rh.bean.AtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.negocio.AtitudeIndividualService;
import br.com.citframework.service.ServiceLocator;

public class AutoCompleteAtitudeIndividual extends AbstractAutoComplete {

    @Override
    public Class<AtitudeIndividualDTO> getBeanClass() {
        return AtitudeIndividualDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String consulta = request.getParameter("query");
        final AtitudeIndividualService service = (AtitudeIndividualService) ServiceLocator.getInstance().getService(AtitudeIndividualService.class, null);

        final Collection<AtitudeIndividualDTO> atitudesIndividuais = service.findByNome(consulta);

        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        final List<String> lst = new ArrayList<>();
        final List<Integer> lstVal = new ArrayList<>();

        if (atitudesIndividuais != null && !atitudesIndividuais.isEmpty()) {
            for (final AtitudeIndividualDTO atitude : atitudesIndividuais) {
                lst.add(atitude.getDescricao());
                lstVal.add(atitude.getIdAtitudeIndividual());
            }
        }

        autoCompleteDTO.setQuery(consulta);
        autoCompleteDTO.setSuggestions(lst);
        autoCompleteDTO.setData(lstVal);

        final String json = getGSON().toJson(autoCompleteDTO);
        request.setAttribute("json_response", json);
    }

}
