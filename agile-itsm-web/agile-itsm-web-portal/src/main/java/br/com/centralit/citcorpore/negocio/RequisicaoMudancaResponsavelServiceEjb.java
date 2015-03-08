package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RequisicaoMudancaResponsavelDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RequisicaoMudancaResponsavelServiceEjb extends CrudServiceImpl implements RequisicaoMudancaResponsavelService {

    private RequisicaoMudancaResponsavelDao dao;

    @Override
    protected RequisicaoMudancaResponsavelDao getDao() {
        if (dao == null) {
            dao = new RequisicaoMudancaResponsavelDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdMudanca(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdMudancaEDataFim(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdMudancaEDataFim(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdMudanca(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdRequisicaoMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
