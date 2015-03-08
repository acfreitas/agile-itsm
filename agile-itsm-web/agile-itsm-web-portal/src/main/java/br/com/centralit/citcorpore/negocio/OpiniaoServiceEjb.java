package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.OpiniaoDTO;
import br.com.centralit.citcorpore.integracao.OpiniaoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class OpiniaoServiceEjb extends CrudServiceImpl implements OpiniaoService {

    private OpiniaoDao dao;

    @Override
    protected OpiniaoDao getDao() {
        if (dao == null) {
            dao = new OpiniaoDao();
        }
        return dao;
    }

    @SuppressWarnings("rawtypes")
    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @SuppressWarnings("rawtypes")
    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public Collection findByTipoAndPeriodo(final String tipo, final Integer idContrato, final Date dataInicial, final Date dataFinal) throws Exception {
        return this.getDao().findByTipoAndPeriodo(tipo, idContrato, dataInicial, dataFinal);
    }

    @Override
    public OpiniaoDTO findByIdSolicitacao(final Integer idSolicitacao) throws Exception {
        return this.getDao().findByIdSolicitacao(idSolicitacao);
    }

}
