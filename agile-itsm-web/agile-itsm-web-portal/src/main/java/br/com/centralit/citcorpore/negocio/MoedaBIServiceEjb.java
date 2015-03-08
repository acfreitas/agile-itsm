package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.MoedaBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class MoedaBIServiceEjb extends CrudServiceImpl implements MoedaBIService {

    private MoedaBIDao dao;

    @Override
    protected MoedaBIDao getDao() {
        if (dao == null) {
            dao = new MoedaBIDao();
        }
        return dao;
    }

}
