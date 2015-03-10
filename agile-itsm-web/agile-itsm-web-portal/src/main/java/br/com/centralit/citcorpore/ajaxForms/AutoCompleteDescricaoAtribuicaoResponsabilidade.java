package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoAtribuicaoResponsabilidadeDTO;
import br.com.centralit.citcorpore.rh.negocio.DescricaoAtribuicaoResponsabilidadeService;
import br.com.citframework.service.ServiceLocator;

public class AutoCompleteDescricaoAtribuicaoResponsabilidade extends AbstractAutoComplete {

    @Override
    public Class<DescricaoAtribuicaoResponsabilidadeDTO> getBeanClass() {
        return DescricaoAtribuicaoResponsabilidadeDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String consulta = request.getParameter("query");
        final DescricaoAtribuicaoResponsabilidadeService service = (DescricaoAtribuicaoResponsabilidadeService) ServiceLocator.getInstance().getService(
                DescricaoAtribuicaoResponsabilidadeService.class, null);

        final Collection<DescricaoAtribuicaoResponsabilidadeDTO> descricoes = service.findByNome(consulta);

        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        final List<String> lst = new ArrayList<>();
        final List<Integer> lstVal = new ArrayList<>();

        if (descricoes != null && !descricoes.isEmpty()) {
            for (final DescricaoAtribuicaoResponsabilidadeDTO descricao : descricoes) {
                lst.add(descricao.getDescricao());
                lstVal.add(descricao.getIdDescricao());
            }
        }

        autoCompleteDTO.setQuery(consulta);
        autoCompleteDTO.setSuggestions(lst);
        autoCompleteDTO.setData(lstVal);

        final String json = getGSON().toJson(autoCompleteDTO);
        request.setAttribute("json_response", json);
    }

}
