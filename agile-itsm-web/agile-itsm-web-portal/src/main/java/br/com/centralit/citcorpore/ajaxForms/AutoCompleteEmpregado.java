package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.citframework.service.ServiceLocator;

public class AutoCompleteEmpregado extends AbstractAutoComplete {

    @Override
    public Class<EmpregadoDTO> getBeanClass() {
        return EmpregadoDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String consulta = request.getParameter("query");
        final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

        final Collection<EmpregadoDTO> empregados = empregadoService.consultarNomeEmpregadoSemAdministrador(consulta);

        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        final List<String> lst = new ArrayList<>();
        final List<Integer> lstVal = new ArrayList<>();

        if (empregados != null && !empregados.isEmpty()) {
            for (final EmpregadoDTO empregado : empregados) {
                lst.add(empregado.getNome());
                lstVal.add(empregado.getIdEmpregado());
            }
        }

        autoCompleteDTO.setQuery(consulta);
        autoCompleteDTO.setSuggestions(lst);
        autoCompleteDTO.setData(lstVal);

        final String json = getGSON().toJson(autoCompleteDTO);
        request.setAttribute("json_response", json);
    }

}
