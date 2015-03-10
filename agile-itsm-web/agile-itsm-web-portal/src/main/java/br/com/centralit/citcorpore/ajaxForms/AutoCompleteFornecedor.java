package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.citframework.service.ServiceLocator;

public class AutoCompleteFornecedor extends AbstractAutoComplete {

    @Override
    public Class<FornecedorDTO> getBeanClass() {
        return FornecedorDTO.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String consulta = request.getParameter("query");
        final FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);

        final Collection<FornecedorDTO> fornecedores = fornecedorService.consultarFornecedorPorRazaoSocialAutoComplete(consulta);

        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        final List<String> lst = new ArrayList<>();
        final List<Integer> lstVal = new ArrayList<>();

        if (fornecedores != null && !fornecedores.isEmpty()) {
            for (final FornecedorDTO fornecedor : fornecedores) {
                lst.add(fornecedor.getRazaoSocial());
                lstVal.add(fornecedor.getIdFornecedor());
            }
        }

        autoCompleteDTO.setQuery(consulta);
        autoCompleteDTO.setSuggestions(lst);
        autoCompleteDTO.setData(lstVal);

        final String json = getGSON().toJson(autoCompleteDTO);
        request.setAttribute("json_response", json);
    }

}
