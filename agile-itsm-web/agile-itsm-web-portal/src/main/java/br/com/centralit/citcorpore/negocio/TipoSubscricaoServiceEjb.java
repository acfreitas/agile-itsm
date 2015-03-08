package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.TipoSubscricaoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author Pedro
 *
 */
public class TipoSubscricaoServiceEjb extends CrudServiceImpl implements TipoSubscricaoService {

    private TipoSubscricaoDao dao;

    @Override
    protected TipoSubscricaoDao getDao() {
        if (dao == null) {
            dao = new TipoSubscricaoDao();
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
