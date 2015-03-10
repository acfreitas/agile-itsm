package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.ServicoCorporeBIDTO;
import br.com.centralit.citcorpore.negocio.ServicoCorporeBIService;
import br.com.citframework.service.ServiceLocator;

public class AutoCompleteServicoCorporeBI extends AbstractAutoComplete {

    @Override
    public Class<ServicoCorporeBIDTO> getBeanClass() {
        return ServicoCorporeBIDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String consulta = request.getParameter("query");

        Collection<ServicoCorporeBIDTO> listServicoCorporeBIDTO = new ArrayList<ServicoCorporeBIDTO>();

        final ServicoCorporeBIService servicoCorporeBIService = (ServicoCorporeBIService) ServiceLocator.getInstance().getService(
                ServicoCorporeBIService.class, null);
        listServicoCorporeBIDTO = servicoCorporeBIService.findByNome(consulta);

        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();

        final List<String> listNome = new ArrayList<>();
        final List<Integer> listIdServico = new ArrayList<>();

        if (listServicoCorporeBIDTO != null) {
            for (final ServicoCorporeBIDTO servicoCorporeBIDTO : listServicoCorporeBIDTO) {
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
            json = getGSON().toJson(listServicoCorporeBIDTO);
        } else {
            json = getGSON().toJson(autoCompleteDTO);
        }

        request.setAttribute("json_response", json);
    }

}
