package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.PostDAO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author Flávio.santana
 *
 */
public class PostServiceEjb extends CrudServiceImpl implements PostService {

    private PostDAO dao;

    @Override
    protected PostDAO getDao() {
        if (dao == null) {
            dao = new PostDAO();
        }
        return dao;
    }

    @SuppressWarnings("rawtypes")
    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @SuppressWarnings("rawtypes")
    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public Collection listNotNull() throws Exception {
        return this.getDao().listNotNull();
    }

}
