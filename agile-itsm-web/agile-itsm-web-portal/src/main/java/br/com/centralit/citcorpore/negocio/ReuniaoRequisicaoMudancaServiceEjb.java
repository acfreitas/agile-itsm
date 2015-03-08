package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ReuniaoRequisicaoMudancaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ReuniaoRequisicaoMudancaServiceEjb extends CrudServiceImpl implements ReuniaoRequisicaoMudancaService {

    private ReuniaoRequisicaoMudancaDAO dao;

    @Override
    protected ReuniaoRequisicaoMudancaDAO getDao() {
        if (dao == null) {
            dao = new ReuniaoRequisicaoMudancaDAO();
        }
        return dao;
    }

    @Override
    public Collection findByIdRequisicaoMudanca(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdRequisicaoMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
