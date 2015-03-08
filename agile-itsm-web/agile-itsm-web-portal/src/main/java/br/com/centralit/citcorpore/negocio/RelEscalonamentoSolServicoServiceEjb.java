package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.RelEscalonamentoSolServicoDao;
import br.com.citframework.service.CrudServiceImpl;

public class RelEscalonamentoSolServicoServiceEjb extends CrudServiceImpl implements RelEscalonamentoSolServicoService {

    private RelEscalonamentoSolServicoDao dao;

    @Override
    protected RelEscalonamentoSolServicoDao getDao() {
        if (dao == null) {
            dao = new RelEscalonamentoSolServicoDao();
        }
        return dao;
    }

}
