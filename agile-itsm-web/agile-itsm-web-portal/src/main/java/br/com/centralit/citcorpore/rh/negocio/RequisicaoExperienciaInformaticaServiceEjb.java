package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.RequisicaoExperienciaInformaticaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RequisicaoExperienciaInformaticaServiceEjb extends CrudServiceImpl implements RequisicaoExperienciaInformaticaService {

    private RequisicaoExperienciaInformaticaDao dao;

    @Override
    protected RequisicaoExperienciaInformaticaDao getDao() {
        if (dao == null) {
            dao = new RequisicaoExperienciaInformaticaDao();
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
