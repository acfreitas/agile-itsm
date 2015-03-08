package br.com.centralit.citquestionario.negocio;

import br.com.centralit.citquestionario.integracao.CategoriaQuestionarioDao;
import br.com.citframework.service.CrudServiceImpl;

public class CategoriaQuestionarioServiceBean extends CrudServiceImpl implements CategoriaQuestionarioService {

    private CategoriaQuestionarioDao dao;

    @Override
    protected CategoriaQuestionarioDao getDao() {
        if (dao == null) {
            dao = new CategoriaQuestionarioDao();
        }
        return dao;
    }

}
