package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.GrupoNivelAutoridadeDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class GrupoNivelAutoridadeServiceEjb extends CrudServiceImpl implements GrupoNivelAutoridadeService {

    private GrupoNivelAutoridadeDao dao;

    @Override
    protected GrupoNivelAutoridadeDao getDao() {
        if (dao == null) {
            dao = new GrupoNivelAutoridadeDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdNivelAutoridade(final Integer parm) throws ServiceException, LogicException {
        try {
            return this.getDao().findByIdNivelAutoridade(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdNivelAutoridade(final Integer parm) throws ServiceException, LogicException {
        try {
            this.getDao().deleteByIdNivelAutoridade(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
