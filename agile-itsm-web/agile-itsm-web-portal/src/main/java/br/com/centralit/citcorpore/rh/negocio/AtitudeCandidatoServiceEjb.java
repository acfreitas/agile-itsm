package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.AtitudeCandidatoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class AtitudeCandidatoServiceEjb extends CrudServiceImpl implements AtitudeCandidatoService {

    private AtitudeCandidatoDao dao;

    @Override
    protected AtitudeCandidatoDao getDao() {
        if (dao == null) {
            dao = new AtitudeCandidatoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdEntrevista(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdEntrevista(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdEntrevista(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdEntrevista(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
