package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ComandoDTO;
import br.com.centralit.citcorpore.integracao.ComandoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author ygor.magalhaes
 *
 */
public class ComandoServiceEjb extends CrudServiceImpl implements ComandoService {

    private ComandoDao dao;

    @Override
    protected ComandoDao getDao() {
        if (dao == null) {
            dao = new ComandoDao();
        }
        return dao;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public ComandoDTO listItemCadastrado(final String descricao) throws Exception {
        return this.getDao().listItemCadastrado(descricao);
    }

}
