package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.FaturaOSBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class FaturaOSBIServiceEjb extends CrudServiceImpl implements FaturaOSBIService {

    private FaturaOSBIDao dao;

    @Override
    protected FaturaOSBIDao getDao() {
        if (dao == null) {
            dao = new FaturaOSBIDao();
        }
        return dao;
    }

}
