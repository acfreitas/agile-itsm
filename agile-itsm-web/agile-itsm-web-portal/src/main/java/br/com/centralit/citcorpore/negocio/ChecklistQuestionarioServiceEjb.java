package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ChecklistQuestionarioDao;
import br.com.citframework.service.CrudServiceImpl;

public class ChecklistQuestionarioServiceEjb extends CrudServiceImpl implements ChecklistQuestionarioService {

    private ChecklistQuestionarioDao dao;

    @Override
    protected ChecklistQuestionarioDao getDao() {
        if (dao == null) {
            dao = new ChecklistQuestionarioDao();
        }
        return dao;
    }

}
