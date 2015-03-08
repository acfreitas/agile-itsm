package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaLiberacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RequisicaoMudancaLiberacaoServiceEjb extends CrudServiceImpl implements RequisicaoMudancaLiberacaoService {

    private RequisicaoLiberacaoDao dao;
    private RequisicaoMudancaLiberacaoDao liberacaoDao;

    @Override
    protected RequisicaoLiberacaoDao getDao() {
        if (dao == null) {
            dao = new RequisicaoLiberacaoDao();
        }
        return dao;
    }

    public RequisicaoMudancaLiberacaoDao getLiberacaoDao() {
        if (liberacaoDao == null) {
            liberacaoDao = new RequisicaoMudancaLiberacaoDao();
        }
        return liberacaoDao;
    }

    @Override
    public Collection findByIdRequisicaoMudanca(final Integer parm) throws Exception {
        try {
            return this.getLiberacaoDao().findByIdRequisicaoMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
