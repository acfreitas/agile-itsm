package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.PosicionamentoAtendenteDTO;
import br.com.centralit.citcorpore.bean.result.PosicionamentoAtendenteResultDTO;
import br.com.centralit.citcorpore.negocio.PosicionamentoAtendenteService;
import br.com.citframework.service.ServiceLocator;

/**
 * Controlador para visualização do posionamento dos atendentes
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 06/10/2014
 *
 */
public class PosicionamentoAtendente extends AbstractGestaoForcaAtendimento<PosicionamentoAtendenteDTO, PosicionamentoAtendenteResultDTO> {

    @Override
    public Class<PosicionamentoAtendenteDTO> getBeanClass() {
        return PosicionamentoAtendenteDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        super.load(document, request, response);

        this.loadComboContratos(document, request, response);

        final List<HTMLSelect> combos = new ArrayList<>();
        combos.add(document.getSelectById(ID_GRUPO));
        this.inicializarCombosOnLoad(combos, request);
    }

    @Override
    protected List<PosicionamentoAtendenteResultDTO> getListResultSearch(final DocumentHTML document, final HttpServletRequest request) throws Exception {
        final PosicionamentoAtendenteDTO normalizedFilter = this.normalizeDates((PosicionamentoAtendenteDTO) document.getBean());
        return this.getPosicionamentoAtendenteService().listLastLocationWithSolicitationInfo(normalizedFilter);
    }

    private PosicionamentoAtendenteService posicionamentoAtendenteService;

    private PosicionamentoAtendenteService getPosicionamentoAtendenteService() throws Exception {
        if (posicionamentoAtendenteService == null) {
            posicionamentoAtendenteService = (PosicionamentoAtendenteService) ServiceLocator.getInstance().getService(PosicionamentoAtendenteService.class, null);
        }
        return posicionamentoAtendenteService;
    }

}
