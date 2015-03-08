package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.RelEscalonamentoMudancaDao;
import br.com.citframework.service.CrudServiceImpl;

public class RelEscalonamentoMudancaServeEjb extends CrudServiceImpl implements RelEscalonamentoMudancaService {

    private RelEscalonamentoMudancaDao dao;

    @Override
    protected RelEscalonamentoMudancaDao getDao() {
        if (dao == null) {
            dao = new RelEscalonamentoMudancaDao();
        }
        return dao;
    }

}
