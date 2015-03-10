package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.citframework.service.ServiceLocator;

public class AutoCompleteServico extends AbstractAutoComplete {

    @Override
    public Class<EmpregadoDTO> getBeanClass() {
        return EmpregadoDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        // Corrige o enconding do parâmetro desejado.
        final String consulta = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");

        final String tipoDemanda = request.getParameter("tipoDemanda");
        Integer idTipoDemanda = null;
        if (StringUtils.isNotBlank(tipoDemanda)) {
            idTipoDemanda = Integer.parseInt(tipoDemanda);
        }

        final String contrato = request.getParameter("contrato");
        Integer idContrato = null;
        if (StringUtils.isNotBlank(contrato)) {
            idContrato = Integer.parseInt(contrato);
        }

        final String categoria = request.getParameter("categoria");
        Integer idCategoria = null;
        if (StringUtils.isNotBlank(categoria)) {
            idCategoria = Integer.parseInt(categoria);
        }

        final Collection colRetorno = getServicoService().findByNomeAndContratoAndTipoDemandaAndCategoria(idTipoDemanda, idContrato, idCategoria, consulta);
        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();

        final List<String> lst = new ArrayList<>();
        final List<Integer> lstVal = new ArrayList<>();

        if (colRetorno != null) {
            for (final Iterator it = colRetorno.iterator(); it.hasNext();) {
                final ServicoDTO servicoDto = (ServicoDTO) it.next();
                if (servicoDto.getIdServico() != null) {
                    lst.add(servicoDto.getNomeServico());
                    lstVal.add(servicoDto.getIdServico());
                }
            }
        }
        autoCompleteDTO.setQuery(consulta);
        autoCompleteDTO.setSuggestions(lst);
        autoCompleteDTO.setData(lstVal);

        final String json = getGSON().toJson(autoCompleteDTO);
        request.setAttribute("json_response", json);
    }

    private ServicoService servicoService;

    private ServicoService getServicoService() throws Exception {
        if (servicoService == null) {
            servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
        }
        return servicoService;
    }

}
