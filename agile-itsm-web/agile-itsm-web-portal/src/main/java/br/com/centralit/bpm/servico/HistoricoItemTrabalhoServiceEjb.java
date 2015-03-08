package br.com.centralit.bpm.servico;

import java.util.Collection;

import br.com.centralit.bpm.integracao.HistoricoItemTrabalhoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class HistoricoItemTrabalhoServiceEjb extends CrudServiceImpl implements HistoricoItemTrabalhoService {

    private HistoricoItemTrabalhoDao historicoItemTrabalhoDao;

    @Override
    protected HistoricoItemTrabalhoDao getDao() {
        if (historicoItemTrabalhoDao == null) {
            historicoItemTrabalhoDao = new HistoricoItemTrabalhoDao();
        }
        return historicoItemTrabalhoDao;
    }

    @Override
    public Collection findByIdItemTrabalho(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdItemTrabalho(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdUsuario(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdUsuario(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
