package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.PlanoMelhoriaDao;
import br.com.citframework.service.CrudServiceImpl;

public class PlanoMelhoriaServiceEjb extends CrudServiceImpl implements PlanoMelhoriaService {

    private PlanoMelhoriaDao dao;

    @Override
    protected PlanoMelhoriaDao getDao() {
        if (dao == null) {
            dao = new PlanoMelhoriaDao();
        }
        return dao;
    }

}
