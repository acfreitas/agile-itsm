package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.RelEscalonamentoProblemaDao;
import br.com.citframework.service.CrudServiceImpl;

public class RelEscalonamentoProblemaServiceEjb extends CrudServiceImpl implements RelEscalonamentoProblemaService {

    private RelEscalonamentoProblemaDao dao;

    @Override
    protected RelEscalonamentoProblemaDao getDao() {
        if (dao == null) {
            dao = new RelEscalonamentoProblemaDao();
        }
        return dao;
    }

}
