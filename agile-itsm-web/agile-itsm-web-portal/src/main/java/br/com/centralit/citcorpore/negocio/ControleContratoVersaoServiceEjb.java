package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.ControleContratoVersaoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author Pedro
 *
 */
@SuppressWarnings("rawtypes")
public class ControleContratoVersaoServiceEjb extends CrudServiceImpl implements ControleContratoVersaoService {

    private ControleContratoVersaoDao dao;

    @Override
    protected ControleContratoVersaoDao getDao() {
        if (dao == null) {
            dao = new ControleContratoVersaoDao();
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
