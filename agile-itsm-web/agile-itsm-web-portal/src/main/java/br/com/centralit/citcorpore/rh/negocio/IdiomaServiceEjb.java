package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.IdiomaDao;
import br.com.citframework.service.CrudServiceImpl;

public class IdiomaServiceEjb extends CrudServiceImpl implements IdiomaService {

    private IdiomaDao dao;

    @Override
    protected IdiomaDao getDao() {
        if (dao == null) {
            dao = new IdiomaDao();
        }
        return dao;
    }

    @Override
    public Collection findByNome(final String nome) throws Exception {
        return this.getDao().findByNome(nome);
    }

    @Override
    public Collection findByNotIdFuncao(final Integer idFuncao) throws Exception {
        return this.getDao().findByNotIdFuncao(idFuncao);
    }

}
