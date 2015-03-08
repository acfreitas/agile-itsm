package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.SistemaOperacionalDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author ygor.magalhaes
 *
 */
@SuppressWarnings("rawtypes")
public class SistemaOperacionalServiceEjb extends CrudServiceImpl implements SistemaOperacionalService {

    private SistemaOperacionalDao dao;

    @Override
    protected SistemaOperacionalDao getDao() {
        if (dao == null) {
            dao = new SistemaOperacionalDao();
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
