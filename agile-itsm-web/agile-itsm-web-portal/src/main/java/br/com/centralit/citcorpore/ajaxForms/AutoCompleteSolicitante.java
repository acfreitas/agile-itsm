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
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.citframework.service.ServiceLocator;

public class AutoCompleteSolicitante extends AbstractAutoComplete {

    @Override
    public Class<EmpregadoDTO> getBeanClass() {
        return EmpregadoDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        // Corrige o enconding do parâmetro desejado.
        if (request.getParameter("query") != null) {
            final String consulta = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");

            final String idContratoStr = request.getParameter("contrato");
            Integer idContrato = null;
            if (StringUtils.isNotBlank(idContratoStr) && !idContratoStr.equals("-1")) {
                idContrato = Integer.parseInt(idContratoStr);
            }

            Collection<EmpregadoDTO> listEmpregadoDto = new ArrayList<>();

            final String idUnidadeStr = request.getParameter("unidade");
            Integer idUnidade = null;
            if (StringUtils.isNotBlank(idUnidadeStr) && !idUnidadeStr.equals("-1")) {
                idUnidade = Integer.parseInt(idUnidadeStr);
            }

            if (idContrato != null) {
                listEmpregadoDto = getEmpregadoService().findSolicitanteByNomeAndIdContratoAndIdUnidade(consulta, idContrato, idUnidade);
            }

            final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();

            final List<String> listNome = new ArrayList<>();
            final List<Integer> listIdEmpregado = new ArrayList<>();

            if (listEmpregadoDto != null && !listEmpregadoDto.isEmpty()) {
                for (final EmpregadoDTO empregadoDto : listEmpregadoDto) {
                    if (empregadoDto.getIdEmpregado() != null) {
                        listNome.add(empregadoDto.getNome());
                        listIdEmpregado.add(empregadoDto.getIdEmpregado());
                    }
                }
            }
            autoCompleteDTO.setQuery(consulta);
            autoCompleteDTO.setSuggestions(listNome);
            autoCompleteDTO.setData(listIdEmpregado);

            String json = "";

            if (request.getParameter("colection") != null) {
                json = getGSON().toJson(listEmpregadoDto);
            } else {
                json = getGSON().toJson(autoCompleteDTO);
            }

            request.setAttribute("json_response", json);
        }
    }

    private EmpregadoService empregadoService;

    private EmpregadoService getEmpregadoService() throws Exception {
        if (empregadoService == null) {
            empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
        }
        return empregadoService;
    }

}
