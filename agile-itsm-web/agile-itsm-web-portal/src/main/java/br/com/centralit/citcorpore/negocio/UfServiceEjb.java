package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.integracao.UfDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class UfServiceEjb extends CrudServiceImpl implements UfService {

    private UfDao dao;

    @Override
    protected UfDao getDao() {
        if (dao == null) {
            dao = new UfDao();
        }
        return dao;
    }

    @Override
    public Collection<UfDTO> listByIdRegioes(final UfDTO obj) throws Exception {
        try {
            return this.getDao().listByIdRegioes(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<UfDTO> listByIdPais(final UfDTO obj) throws Exception {
        try {
            return this.getDao().listByIdPais(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
