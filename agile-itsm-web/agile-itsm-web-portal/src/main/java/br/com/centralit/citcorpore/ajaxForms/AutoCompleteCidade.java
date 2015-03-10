package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.citframework.service.ServiceLocator;

public class AutoCompleteCidade extends AbstractAutoComplete {

    @Override
    public Class<CidadesDTO> getBeanClass() {
        return CidadesDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        // Corrige o enconding do parâmetro desejado.
        final String consulta = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");
        final CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
        Collection<CidadesDTO> cidades = new ArrayList<>();

        final String idCidadeS = request.getParameter("idEstado");
        Integer idEstado = null;
        if (idCidadeS != null && !idCidadeS.isEmpty()) {
            idEstado = Integer.parseInt(idCidadeS);
            cidades = cidadesService.findByIdEstadoAndNomeCidade(idEstado, consulta);
        } else {
            cidades = cidadesService.findByNome(consulta);
        }

        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        final List<String> lst = new ArrayList<>();
        final List<Integer> lstVal = new ArrayList<>();

        if (cidades != null && !cidades.isEmpty()) {
            for (final CidadesDTO cidade : cidades) {
                if (idCidadeS != null && !idCidadeS.isEmpty()) {
                    lst.add(cidade.getNomeCidade());
                } else {
                    lst.add(cidade.getNomeCidade() + " - " + cidade.getNomeUf());
                }
                lstVal.add(cidade.getIdCidade());
            }
        }

        autoCompleteDTO.setQuery(consulta);
        autoCompleteDTO.setSuggestions(lst);
        autoCompleteDTO.setData(lstVal);

        final String json = getGSON().toJson(autoCompleteDTO);
        request.setAttribute("json_response", json);
    }

}
