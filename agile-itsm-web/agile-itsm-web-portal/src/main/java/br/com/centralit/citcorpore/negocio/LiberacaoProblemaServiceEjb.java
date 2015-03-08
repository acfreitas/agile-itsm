package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.LiberacaoMudancaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class LiberacaoProblemaServiceEjb extends CrudServiceImpl implements LiberacaoProblemaService {

    private LiberacaoMudancaDao dao;

    @Override
    protected LiberacaoMudancaDao getDao() {
        if (dao == null) {
            dao = new LiberacaoMudancaDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdLiberacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdLiberacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdLiberacao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdLiberacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
