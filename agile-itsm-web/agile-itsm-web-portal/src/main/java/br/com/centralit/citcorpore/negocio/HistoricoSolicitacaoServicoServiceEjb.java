package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.HistoricoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.RelatorioCargaHorariaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.HistoricoSolicitacaoServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;

public class HistoricoSolicitacaoServicoServiceEjb extends CrudServiceImpl implements HistoricoSolicitacaoServicoService {

    private HistoricoSolicitacaoServicoDao dao;

    @Override
    protected HistoricoSolicitacaoServicoDao getDao() {
        if (dao == null) {
            dao = new HistoricoSolicitacaoServicoDao();
        }
        return dao;
    }

    @Override
    public boolean findHistoricoSolicitacao(final Integer idSolicitacaoServico) throws Exception {
        return this.getDao().findHistoricoSolicitacao(idSolicitacaoServico);
    }

    @Override
    public Collection<HistoricoSolicitacaoServicoDTO> restoreHistoricoServico(final Integer idSolicitacaoServico) throws Exception {
        return this.getDao().restoreHistoricoServico(idSolicitacaoServico);
    }

    @Override
    public Collection<HistoricoSolicitacaoServicoDTO> findResponsavelAtual(final Integer idSolicitacaoServico) throws Exception {
        return this.getDao().findResponsavelAtual(idSolicitacaoServico);
    }

    @Override
    public Collection<RelatorioCargaHorariaDTO> imprimirCargaHorariaUsuario(final SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception {
        return this.getDao().imprimirCargaHorariaUsuario(solicitacaoServicoDTO);
    }

    @Override
    public Collection<SolicitacaoServicoDTO> imprimirSolicitacaoEncaminhada(final SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception {
        return this.getDao().imprimirSolicitacaoEncaminhada(solicitacaoServicoDTO);
    }

    @Override
    public Collection<SolicitacaoServicoDTO> imprimirSolicitacaoEncaminhadaFilhas(final SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception {
        return this.getDao().imprimirSolicitacaoEncaminhadaFilhas(solicitacaoServicoDTO);
    }

    @Override
    public Collection<RelatorioCargaHorariaDTO> imprimirCargaHorariaGrupo(final SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception {
        return this.getDao().imprimirCargaHorariaGrupo(solicitacaoServicoDTO);
    }

    public static IDto create(final HistoricoSolicitacaoServicoDTO historicoSolicitacaoServicoDTO, final TransactionControler tc) throws Exception {
        IDto historico = new HistoricoSolicitacaoServicoDTO();
        final HistoricoSolicitacaoServicoDao historicoSolicitacaoServicoDao = new HistoricoSolicitacaoServicoDao();
        if (tc != null) {
            historicoSolicitacaoServicoDao.setTransactionControler(tc);
            historico = historicoSolicitacaoServicoDao.create(historicoSolicitacaoServicoDTO);
        }
        return historico;
    }

    public static void update(final HistoricoSolicitacaoServicoDTO historicoSolicitacaoServicoDTO, final TransactionControler tc) throws Exception {
        final HistoricoSolicitacaoServicoDao historicoSolicitacaoServicoDao = new HistoricoSolicitacaoServicoDao();
        if (tc != null) {
            historicoSolicitacaoServicoDao.setTransactionControler(tc);
            historicoSolicitacaoServicoDao.update(historicoSolicitacaoServicoDTO);
        }
    }

}
