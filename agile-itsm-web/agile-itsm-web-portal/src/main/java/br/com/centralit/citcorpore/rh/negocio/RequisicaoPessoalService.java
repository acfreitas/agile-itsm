package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.ComplemInfSolicitacaoServicoService;

public interface RequisicaoPessoalService extends ComplemInfSolicitacaoServicoService {

    @Override
    void preparaSolicitacaoParaAprovacao(final SolicitacaoServicoDTO solicitacaoDto, final ItemTrabalho itemTrabalho, final String aprovacao, final Integer idJustificativa,
            final String observacoes) throws Exception;

}
