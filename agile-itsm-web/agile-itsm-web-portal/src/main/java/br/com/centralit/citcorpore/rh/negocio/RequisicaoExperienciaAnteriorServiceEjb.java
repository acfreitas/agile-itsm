package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.RequisicaoExperienciaAnteriorDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RequisicaoExperienciaAnteriorServiceEjb extends CrudServiceImpl implements RequisicaoExperienciaAnteriorService {

    private RequisicaoExperienciaAnteriorDao dao;

    @Override
    protected RequisicaoExperienciaAnteriorDao getDao() {
        if (dao == null) {
            dao = new RequisicaoExperienciaAnteriorDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdSolicitacaoServico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdSolicitacaoServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdSolicitacaoServico(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdSolicitacaoServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
