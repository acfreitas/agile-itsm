package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.rh.bean.AtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.negocio.AtitudeIndividualService;
import br.com.citframework.service.ServiceLocator;

public class AutoCompletePerspectivaComportamental extends AbstractAutoComplete {

    @Override
    public Class<AtitudeIndividualDTO> getBeanClass() {
        return AtitudeIndividualDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        // Corrige o enconding do parâmetro desejado.
        final String consulta = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");
        final AtitudeIndividualService atitudeIndividualService = (AtitudeIndividualService) ServiceLocator.getInstance().getService(
                AtitudeIndividualService.class, null);

        final Collection colRetorno = atitudeIndividualService.findByNome(consulta);
        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        final List<String> lst = new ArrayList<>();
        final List<Integer> lstVal = new ArrayList<>();

        if (colRetorno != null) {
            for (final Iterator it = colRetorno.iterator(); it.hasNext();) {
                final AtitudeIndividualDTO atitudeIndividualDTO = (AtitudeIndividualDTO) it.next();
                if (atitudeIndividualDTO.getIdAtitudeIndividual() != null) {
                    lst.add(atitudeIndividualDTO.getDescricao());
                    lstVal.add(atitudeIndividualDTO.getIdAtitudeIndividual());
                }
            }
        }
        autoCompleteDTO.setQuery(consulta);
        autoCompleteDTO.setSuggestions(lst);
        autoCompleteDTO.setData(lstVal);

        final String json = getGSON().toJson(autoCompleteDTO);
        request.setAttribute("json_response", json);
    }

}
