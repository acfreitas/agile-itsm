package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.GrupoAtvPeriodicaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class GrupoAtvPeriodicaServiceEjb extends CrudServiceImpl implements GrupoAtvPeriodicaService {

    private GrupoAtvPeriodicaDao dao;

    @Override
    protected GrupoAtvPeriodicaDao getDao() {
        if (dao == null) {
            dao = new GrupoAtvPeriodicaDao();
        }
        return dao;
    }

    @Override
    public Collection findByDescGrupoAtvPeriodica(final String parm) throws Exception {
        try {
            return this.getDao().findByDescGrupoAtvPeriodica(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByDescGrupoAtvPeriodica(final String parm) throws Exception {
        try {
            this.getDao().deleteByDescGrupoAtvPeriodica(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listGrupoAtividadePeriodicaAtiva() throws Exception {
        try {
            return this.getDao().listGrupoAtividadePeriodicaAtiva();
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
