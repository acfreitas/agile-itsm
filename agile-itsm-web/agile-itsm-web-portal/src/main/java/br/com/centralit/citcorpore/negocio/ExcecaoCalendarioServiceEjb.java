package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ExcecaoCalendarioDTO;
import br.com.centralit.citcorpore.integracao.ExcecaoCalendarioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ExcecaoCalendarioServiceEjb extends CrudServiceImpl implements ExcecaoCalendarioService {

    private ExcecaoCalendarioDao dao;

    @Override
    protected ExcecaoCalendarioDao getDao() {
        if (dao == null) {
            dao = new ExcecaoCalendarioDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdCalendario(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCalendario(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCalendario(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCalendario(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ExcecaoCalendarioDTO findByIdCalendarioAndData(final Integer idCalendario, final Date data) throws Exception {
        try {
            return this.getDao().findByIdCalendarioAndData(idCalendario, data);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
