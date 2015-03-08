package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoMidiaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RequisicaoLiberacaoMidiaServiceEjb extends CrudServiceImpl implements RequisicaoLiberacaoMidiaService {

    private RequisicaoLiberacaoMidiaDao dao;

    @Override
    protected RequisicaoLiberacaoMidiaDao getDao() {
        if (dao == null) {
            dao = new RequisicaoLiberacaoMidiaDao();
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
    public Collection findByIdLiberacaoEDataFim(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdLiberacaoEDataFim(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
