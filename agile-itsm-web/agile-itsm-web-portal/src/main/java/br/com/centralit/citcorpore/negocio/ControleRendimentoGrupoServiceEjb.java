package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ControleRendimentoGrupoDao;
import br.com.citframework.service.CrudServiceImpl;

public class ControleRendimentoGrupoServiceEjb extends CrudServiceImpl implements ControleRendimentoGrupoService {

    private ControleRendimentoGrupoDao dao;

    @Override
    protected ControleRendimentoGrupoDao getDao() {
        if (dao == null) {
            dao = new ControleRendimentoGrupoDao();
        }
        return dao;
    }

}
