package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RevisarSlaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @since 14/06/2013
 * @author rodrigo.oliveira
 *
 */
public class RevisarSlaServiceEjb extends CrudServiceImpl implements RevisarSlaService {

    private RevisarSlaDao dao;

    @Override
    protected RevisarSlaDao getDao() {
        if (dao == null) {
            dao = new RevisarSlaDao();
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

}
