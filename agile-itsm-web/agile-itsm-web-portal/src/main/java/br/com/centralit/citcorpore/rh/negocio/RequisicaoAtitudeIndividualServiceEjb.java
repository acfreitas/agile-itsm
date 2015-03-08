package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.RequisicaoAtitudeIndividualDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RequisicaoAtitudeIndividualServiceEjb extends CrudServiceImpl implements RequisicaoAtitudeIndividualService {

    private RequisicaoAtitudeIndividualDao dao;

    @Override
    protected RequisicaoAtitudeIndividualDao getDao() {
        if (dao == null) {
            dao = new RequisicaoAtitudeIndividualDao();
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
