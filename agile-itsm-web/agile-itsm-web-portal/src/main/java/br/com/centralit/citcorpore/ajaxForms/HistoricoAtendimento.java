package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.HistoricoAtendimentoDTO;
import br.com.centralit.citcorpore.bean.result.HistoricoAtendimentoResultDTO;
import br.com.centralit.citcorpore.negocio.HistoricoAtendimentoService;
import br.com.centralit.citcorpore.negocio.HistoricoAtendimentoServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServicoNaRota;
import br.com.citframework.util.UtilI18N;

/**
 * Controlador para visualização de histórico de atendimento
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 06/10/2014
 */
public class HistoricoAtendimento extends AbstractGestaoForcaAtendimento<HistoricoAtendimentoDTO, HistoricoAtendimentoResultDTO> {

    @Override
    public Class<HistoricoAtendimentoDTO> getBeanClass() {
        return HistoricoAtendimentoDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        super.load(document, request, response);

        this.loadComboSituacoes(document, request);

        this.loadComboUFs(document, request, response);

        final List<HTMLSelect> combos = new ArrayList<>();
        combos.add(document.getSelectById(ID_CIDADE));
        combos.add(document.getSelectById(ID_GRUPO));
        combos.add(document.getSelectById(ID_CONTRATO));
        this.inicializarCombosOnLoad(combos, request);
    }

    @Override
    protected List<HistoricoAtendimentoResultDTO> getListResultSearch(final DocumentHTML document, final HttpServletRequest request) throws Exception {
        final HistoricoAtendimentoDTO normalizedFilter = this.normalizeDates((HistoricoAtendimentoDTO) document.getBean());
        return this.getService().listHistoricoAtendimentoWithSolicitationInfo(normalizedFilter);
    }

    private void loadComboSituacoes(final DocumentHTML document, final HttpServletRequest request) throws Exception {
        final HTMLSelect comboSituacoes = document.getSelectById("idSituacao");
        this.inicializaCombo(comboSituacoes, request);

        final SituacaoSolicitacaoServicoNaRota[] situacoes = SituacaoSolicitacaoServicoNaRota.values();

        for (final SituacaoSolicitacaoServicoNaRota situacao : situacoes) {
            comboSituacoes.addOption(situacao.getId().toString(), UtilI18N.internacionaliza(request, situacao.getDescription()));
        }
    }

    private HistoricoAtendimentoService service;

    private HistoricoAtendimentoService getService() {
        if (service == null) {
            service = new HistoricoAtendimentoServiceEjb();
        }
        return service;
    }

}
