package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.CursoDao;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class CursoServiceEjb extends CrudServiceImpl implements CursoService {

    private CursoDao dao;

    @Override
    protected CursoDao getDao() {
        if (dao == null) {
            dao = new CursoDao();
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
