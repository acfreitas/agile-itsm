package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.SlaRequisitoSLADao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 *
 * @author rodrigo.oliveira
 *
 */
public class SlaRequisitoSlaServiceEjb extends CrudServiceImpl implements SlaRequisitoSlaService {

    private SlaRequisitoSLADao dao;

    @Override
    protected SlaRequisitoSLADao getDao() {
        if (dao == null) {
            dao = new SlaRequisitoSLADao();
        }
        return dao;
    }

    @Override
    public Collection findByIdAcordoNivelServico(final Integer idAcordoNivelServico) throws Exception {
        try {
            return this.getDao().findByIdAcordoNivelServico(idAcordoNivelServico);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdAcordoNivelServico(final Integer idAcordoNivelServico) throws Exception {
        try {
            this.getDao().deleteByIdAcordoNivelServico(idAcordoNivelServico);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    public boolean existeAcordoByRequisito(final Integer idRequisito) throws Exception {
        return this.getDao().existeAcordoByRequisito(idRequisito);
    }

}
