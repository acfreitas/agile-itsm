package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.CausaIncidenteDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.negocio.CausaIncidenteService;
import br.com.citframework.service.ServiceLocator;

public class AutoCompleteCausa extends AbstractAutoComplete {

    @Override
    public Class<EmpregadoDTO> getBeanClass() {
        return EmpregadoDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        // Corrige o enconding do parâmetro desejado.
        final String descricaoCausa = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");
        final CausaIncidenteService causaIncidenteService = (CausaIncidenteService) ServiceLocator.getInstance().getService(CausaIncidenteService.class, null);

        final Collection colRetorno = causaIncidenteService.listaCausaByDescricaoCausa(descricaoCausa);
        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        final List<String> lst = new ArrayList<>();
        final List<Integer> lstVal = new ArrayList<>();

        if (colRetorno != null) {
            for (final Iterator it = colRetorno.iterator(); it.hasNext();) {
                final CausaIncidenteDTO causaIncidenteDto = (CausaIncidenteDTO) it.next();
                if (causaIncidenteDto.getIdCausaIncidente() != null) {
                    lst.add(causaIncidenteDto.getDescricaoCausa());
                    lstVal.add(causaIncidenteDto.getIdCausaIncidente());
                }
            }
        }
        autoCompleteDTO.setQuery(descricaoCausa);
        autoCompleteDTO.setSuggestions(lst);
        autoCompleteDTO.setData(lstVal);

        final String json = getGSON().toJson(autoCompleteDTO);
        request.setAttribute("json_response", json);
    }

}
