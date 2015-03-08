package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.AtividadesOSBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class AtividadesOSBIServiceEjb extends CrudServiceImpl implements AtividadesOSBIService {

    private AtividadesOSBIDao dao;

    @Override
    protected AtividadesOSBIDao getDao() {
        if (dao == null) {
            dao = new AtividadesOSBIDao();
        }
        return dao;
    }

}
