package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.ParceiroDTO;
import br.com.centralit.citcorpore.negocio.ParceiroService;
import br.com.citframework.service.ServiceLocator;

/**
 * @author david.silva
 *
 */
public class AutoCompleteParceiro extends AbstractAutoComplete {

    @Override
    public Class<ParceiroDTO> getBeanClass() {
        return ParceiroDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        final String consulta = request.getParameter("query");
        final ParceiroService parceiroService = (ParceiroService) ServiceLocator.getInstance().getService(ParceiroService.class, null);

        Collection<ParceiroDTO> parceiros = new ArrayList<ParceiroDTO>();
        parceiros = parceiroService.consultarFornecedorPorRazaoSocialAutoComplete(consulta);

        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        final List<String> lst = new ArrayList<>();
        final List<Integer> lstVal = new ArrayList<>();

        if (parceiros != null && !parceiros.isEmpty()) {
            for (final ParceiroDTO parceiro : parceiros) {
                lst.add(parceiro.getNome());
                lstVal.add(parceiro.getIdParceiro());
            }
        }

        autoCompleteDTO.setQuery(consulta);
        autoCompleteDTO.setSuggestions(lst);
        autoCompleteDTO.setData(lstVal);

        final String json = getGSON().toJson(autoCompleteDTO);
        request.setAttribute("json_response", json);
    }

}
