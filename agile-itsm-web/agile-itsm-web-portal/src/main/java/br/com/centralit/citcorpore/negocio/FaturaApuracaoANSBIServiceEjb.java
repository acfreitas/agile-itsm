package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.FaturaApuracaoANSBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class FaturaApuracaoANSBIServiceEjb extends CrudServiceImpl implements FaturaApuracaoANSBIService {

    private FaturaApuracaoANSBIDao dao;

    @Override
    protected FaturaApuracaoANSBIDao getDao() {
        if (dao == null) {
            dao = new FaturaApuracaoANSBIDao();
        }
        return dao;
    }

}
