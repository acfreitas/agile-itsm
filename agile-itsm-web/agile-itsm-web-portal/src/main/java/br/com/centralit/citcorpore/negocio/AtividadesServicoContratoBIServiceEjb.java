package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.AtividadesServicoContratoBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class AtividadesServicoContratoBIServiceEjb extends CrudServiceImpl implements AtividadesServicoContratoBIService {

    private AtividadesServicoContratoBIDao dao;

    @Override
    protected AtividadesServicoContratoBIDao getDao() {
        if (dao == null) {
            dao = new AtividadesServicoContratoBIDao();
        }
        return dao;
    }

}
