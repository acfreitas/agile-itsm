package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.FuncionarioDTO;
import br.com.centralit.citcorpore.rh.negocio.FuncionarioService;
import br.com.citframework.service.ServiceLocator;

/**
 * @author david.silva
 *
 */
public class AutoCompleteFuncionario extends AbstractAutoComplete {

    @Override
    public Class<FuncionarioDTO> getBeanClass() {
        return FuncionarioDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String consulta = request.getParameter("query");
        final FuncionarioService service = (FuncionarioService) ServiceLocator.getInstance().getService(FuncionarioService.class, null);

        final Collection<FuncionarioDTO> funcionarios = service.findByNome(consulta);

        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        final List<String> lst = new ArrayList<>();
        final List<Integer> lstVal = new ArrayList<>();

        if (funcionarios != null && !funcionarios.isEmpty()) {
            for (final FuncionarioDTO funcionario : funcionarios) {
                lst.add(funcionario.getNome());
                lstVal.add(funcionario.getIdEmpregado());
            }
        }

        autoCompleteDTO.setQuery(consulta);
        autoCompleteDTO.setSuggestions(lst);
        autoCompleteDTO.setData(lstVal);

        final String json = getGSON().toJson(autoCompleteDTO);
        request.setAttribute("json_response", json);
    }

}
