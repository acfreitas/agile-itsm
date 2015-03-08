package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.MidiaDAO;
import br.com.citframework.service.CrudServiceImpl;

public class MidiaServiceEjb extends CrudServiceImpl implements MidiaService {

    private MidiaDAO dao;

    @Override
    protected MidiaDAO getDao() {
        if (dao == null) {
            dao = new MidiaDAO();
        }
        return dao;
    }

}
