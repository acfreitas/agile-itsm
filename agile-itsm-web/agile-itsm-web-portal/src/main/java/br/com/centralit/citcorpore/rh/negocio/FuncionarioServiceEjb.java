package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.FuncionarioDao;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author david.silva
 *
 */
@SuppressWarnings("rawtypes")
public class FuncionarioServiceEjb extends CrudServiceImpl implements FuncionarioService {

    private FuncionarioDao dao;

    @Override
    protected FuncionarioDao getDao() {
        if (dao == null) {
            dao = new FuncionarioDao();
        }
        return dao;
    }

    @Override
    public Collection findByNome(final String nome) throws Exception {
        return this.getDao().findByNome(nome);
    }

}
