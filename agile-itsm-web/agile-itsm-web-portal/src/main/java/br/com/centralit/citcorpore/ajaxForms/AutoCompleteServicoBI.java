package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.ServicoBIDTO;
import br.com.centralit.citcorpore.negocio.ServicoBIService;
import br.com.citframework.service.ServiceLocator;

public class AutoCompleteServicoBI extends AbstractAutoComplete {

    @Override
    public Class<ServicoBIDTO> getBeanClass() {
        return ServicoBIDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String consulta = request.getParameter("query");

        final String idConexaoBIStr = request.getParameter("idConexaoBI");
        Integer idConexaoBI = null;
        if (idConexaoBIStr != null && !idConexaoBIStr.equals("-1")) {
            idConexaoBI = Integer.parseInt(idConexaoBIStr);
        }

        Collection<ServicoBIDTO> listServicoBIDTO = new ArrayList<>();

        if (idConexaoBI != null) {
            final ServicoBIService servicoBIService = (ServicoBIService) ServiceLocator.getInstance().getService(ServicoBIService.class, null);
            listServicoBIDTO = servicoBIService.findByNomeEconexaoBI(consulta, idConexaoBI);
        }

        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();

        final List<String> listNome = new ArrayList<>();
        final List<Integer> listIdServico = new ArrayList<>();

        if (listServicoBIDTO != null) {
            for (final ServicoBIDTO servicoBIDTO : listServicoBIDTO) {
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
            json = getGSON().toJson(listServicoBIDTO);
        } else {
            json = getGSON().toJson(autoCompleteDTO);
        }

        request.setAttribute("json_response", json);
    }

}
