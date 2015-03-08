package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.GrupoRecursosDao;
import br.com.citframework.service.CrudServiceImpl;

public class GrupoRecursosServiceEjb extends CrudServiceImpl implements GrupoRecursosService {

    private GrupoRecursosDao dao;

    @Override
    protected GrupoRecursosDao getDao() {
        if (dao == null) {
            dao = new GrupoRecursosDao();
        }
        return dao;
    }

}
