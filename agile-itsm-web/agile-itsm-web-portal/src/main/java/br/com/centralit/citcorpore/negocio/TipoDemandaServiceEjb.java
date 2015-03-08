package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.TipoDemandaDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class TipoDemandaServiceEjb extends CrudServiceImpl implements TipoDemandaService {

    private TipoDemandaDao dao;

    @Override
    protected TipoDemandaDao getDao() {
        if (dao == null) {
            dao = new TipoDemandaDao();
        }
        return dao;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

}
