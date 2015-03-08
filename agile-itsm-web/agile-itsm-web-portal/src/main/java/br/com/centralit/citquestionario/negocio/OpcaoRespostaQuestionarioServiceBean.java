package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.centralit.citquestionario.integracao.OpcaoRespostaQuestionarioDao;
import br.com.citframework.service.CrudServiceImpl;

public class OpcaoRespostaQuestionarioServiceBean extends CrudServiceImpl implements OpcaoRespostaQuestionarioService {

    private OpcaoRespostaQuestionarioDao dao;

    @Override
    protected OpcaoRespostaQuestionarioDao getDao() {
        if (dao == null) {
            dao = new OpcaoRespostaQuestionarioDao();
        }
        return dao;
    }

    @Override
    public Collection listByIdQuestaoQuestionario(final Integer idQuestaoQuestionario) throws Exception {
        return this.getDao().listByIdQuestaoQuestionario(idQuestaoQuestionario);
    }

}
