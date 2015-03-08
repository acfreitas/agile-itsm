package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ControleImportarDadosDao;
import br.com.citframework.service.CrudServiceImpl;

public class ControleImportarDadosServiceEjb extends CrudServiceImpl implements ControleImportarDadosService {

    private ControleImportarDadosDao dao;

    @Override
    protected ControleImportarDadosDao getDao() {
        if (dao == null) {
            dao = new ControleImportarDadosDao();
        }
        return dao;
    }

}
