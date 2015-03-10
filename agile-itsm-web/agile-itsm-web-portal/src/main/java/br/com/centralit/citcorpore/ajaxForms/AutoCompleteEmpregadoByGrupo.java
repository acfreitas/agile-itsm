package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.citframework.service.ServiceLocator;

/**
 * AutoComplete para {@link EmpregadoDTO} que considera na consulta o {@link GrupoDTO}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 31/10/2014
 *
 */
public class AutoCompleteEmpregadoByGrupo extends AbstractAutoComplete {

    @Override
    public Class<EmpregadoDTO> getBeanClass() {
        return EmpregadoDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String nomeEmpregado = request.getParameter("query");
        final String idGrupoStr = request.getParameter("idGrupo");
        final Integer idGrupo = StringUtils.isNotBlank(idGrupoStr) ? Integer.parseInt(idGrupoStr.trim()) : 0;

        final Collection<EmpregadoDTO> empregados = getService().findByNomeEmpregadoAndGrupo(nomeEmpregado, idGrupo);

        final List<Integer> listIDs = new ArrayList<>();
        final List<String> listNames = new ArrayList<>();

        for (final EmpregadoDTO empregado : empregados) {
            listIDs.add(empregado.getIdEmpregado());
            listNames.add(empregado.getNome());
        }

        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        autoCompleteDTO.setQuery(nomeEmpregado);
        autoCompleteDTO.setSuggestions(listNames);
        autoCompleteDTO.setData(listIDs);

        request.setAttribute("json_response", getGSON().toJson(autoCompleteDTO));
    }

    private static EmpregadoService service;

    private EmpregadoService getService() throws Exception {
        if (service == null) {
            service = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
        }
        return service;
    }

}
