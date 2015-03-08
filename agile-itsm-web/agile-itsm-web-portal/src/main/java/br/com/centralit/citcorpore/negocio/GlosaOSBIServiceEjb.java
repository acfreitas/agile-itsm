package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.GlosaOSBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class GlosaOSBIServiceEjb extends CrudServiceImpl implements GlosaOSBIService {

    private GlosaOSBIDao dao;

    @Override
    protected GlosaOSBIDao getDao() {
        if (dao == null) {
            dao = new GlosaOSBIDao();
        }
        return dao;
    }

}
