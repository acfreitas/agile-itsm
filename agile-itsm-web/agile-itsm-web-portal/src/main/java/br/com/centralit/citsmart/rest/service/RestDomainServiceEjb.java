package br.com.centralit.citsmart.rest.service;

import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestDomainDTO;
import br.com.centralit.citsmart.rest.dao.RestDomainDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RestDomainServiceEjb extends CrudServiceImpl implements RestDomainService {

    private RestDomainDao dao;

    @Override
    protected RestDomainDao getDao() {
        if (dao == null) {
            dao = new RestDomainDao();
        }
        return dao;
    }

    @Override
    public Collection<RestDomainDTO> findByIdRestOperation(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdRestOperation(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdRestOperation(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdRestOperation(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
