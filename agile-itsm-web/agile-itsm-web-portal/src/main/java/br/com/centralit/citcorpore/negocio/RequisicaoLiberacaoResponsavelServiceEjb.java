package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoResponsavelDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RequisicaoLiberacaoResponsavelServiceEjb extends CrudServiceImpl implements RequisicaoLiberacaoResponsavelService {

    private RequisicaoLiberacaoResponsavelDao dao;

    @Override
    protected RequisicaoLiberacaoResponsavelDao getDao() {
        if (dao == null) {
            dao = new RequisicaoLiberacaoResponsavelDao();
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

    @Override
    public void deleteByIdLiberacao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdRequisicaoLiberacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
