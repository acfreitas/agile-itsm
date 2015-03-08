package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.centralit.citcorpore.integracao.TipoMovimFinanceiraViagemDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author ronnie.lopes
 *
 */
@SuppressWarnings("rawtypes")
public class TipoMovimFinanceiraViagemServiceEjb extends CrudServiceImpl implements TipoMovimFinanceiraViagemService {

    private TipoMovimFinanceiraViagemDao dao;

    @Override
    protected TipoMovimFinanceiraViagemDao getDao() {
        if (dao == null) {
            dao = new TipoMovimFinanceiraViagemDao();
        }
        return dao;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public List<TipoMovimFinanceiraViagemDTO> listByClassificacao(final String classificacao) throws Exception {
        return this.getDao().listByClassificacao(classificacao);
    }

    @Override
    public TipoMovimFinanceiraViagemDTO findByMovimentacao(final Long idtipoMovimFinanceiraViagem) throws Exception {
        return this.getDao().findByMovimentacao(idtipoMovimFinanceiraViagem);
    }

    @Override
    public List<TipoMovimFinanceiraViagemDTO> recuperaTipoAtivos() throws Exception {
        return this.getDao().recuperaTiposAtivos();
    }

    @Override
    public TipoMovimFinanceiraViagemDTO findByMovimentacaoEstadoAdiantamento(final Long idtipoMovimFinanceiraViagem, final String adiantamento) throws Exception {
        return this.getDao().findByMovimentacaoEstadoAdiantamento(idtipoMovimFinanceiraViagem, adiantamento);
    }

}
