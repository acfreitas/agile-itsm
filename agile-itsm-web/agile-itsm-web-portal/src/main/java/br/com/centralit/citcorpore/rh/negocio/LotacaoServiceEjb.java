package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.rh.integracao.LotacaoDao;
import br.com.citframework.service.CrudServiceImpl;

public class LotacaoServiceEjb extends CrudServiceImpl implements LotacaoService {

    private LotacaoDao dao;

    @Override
    protected LotacaoDao getDao() {
        if (dao == null) {
            dao = new LotacaoDao();
        }
        return dao;
    }

}
