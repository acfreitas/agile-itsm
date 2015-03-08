package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.centralit.citquestionario.integracao.AplicacaoQuestionarioDao;
import br.com.citframework.service.CrudServiceImpl;

public class AplicacaoQuestionarioServiceBean extends CrudServiceImpl implements AplicacaoQuestionarioService {

    private AplicacaoQuestionarioDao dao;

    @Override
    protected AplicacaoQuestionarioDao getDao() {
        if (dao == null) {
            dao = new AplicacaoQuestionarioDao();
        }
        return dao;
    }

    @Override
    public Collection listByIdQuestionarioAndAplicacao(final Integer idQuestionario, final String aplicacao) throws Exception {
        return this.getDao().listByIdQuestionarioAndAplicacao(idQuestionario, aplicacao);
    }

}
