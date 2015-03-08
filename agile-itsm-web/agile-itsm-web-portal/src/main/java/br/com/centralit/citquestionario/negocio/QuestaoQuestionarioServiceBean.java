package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.centralit.citquestionario.integracao.QuestaoQuestionarioDao;
import br.com.citframework.service.CrudServiceImpl;

public class QuestaoQuestionarioServiceBean extends CrudServiceImpl implements QuestaoQuestionarioService {

    private QuestaoQuestionarioDao dao;

    @Override
    protected QuestaoQuestionarioDao getDao() {
        if (dao == null) {
            dao = new QuestaoQuestionarioDao();
        }
        return dao;
    }

    @Override
    public Collection listByIdGrupoQuestionario(final Integer idGrupoQuestionario) throws Exception {
        return this.getDao().listByIdGrupoQuestionario(idGrupoQuestionario);
    }

    @Override
    public Collection listByIdGrupoQuestionarioComAgrupadoras(final Integer idGrupoQuestionario) throws Exception {
        return dao.listByIdGrupoQuestionarioComAgrupadoras(idGrupoQuestionario);
    }

    @Override
    public Collection listByIdQuestaoAgrupadora(final Integer idQuestaoAgrupadora) throws Exception {
        return this.getDao().listByIdQuestaoAgrupadora(idQuestaoAgrupadora);
    }

    public Collection listCabecalhosLinha(final Integer idQuestaoAgrupadora) throws Exception {
        return this.getDao().listCabecalhosLinha(idQuestaoAgrupadora);
    }

    public Collection listCabecalhosColuna(final Integer idQuestaoAgrupadora) throws Exception {
        return this.getDao().listCabecalhosColuna(idQuestaoAgrupadora);
    }

    @Override
    public QuestaoQuestionarioDTO findBySiglaAndIdQuestionario(final String sigla, final Integer idQuestionario) throws Exception {
        return this.getDao().findBySiglaAndIdQuestionario(sigla, idQuestionario);
    }

    @Override
    public Collection listByTipoQuestaoAndIdQuestionario(final String tipoQuestao, final Integer idQuestionario) throws Exception {
        return dao.listByTipoQuestaoAndIdQuestionario(tipoQuestao, idQuestionario);
    }

    @Override
    public Collection listByTipoAndIdQuestionario(final String tipo, final Integer idQuestionario) throws Exception {
        return this.getDao().listByTipoAndIdQuestionario(tipo, idQuestionario);
    }

    @Override
    public Collection listByIdQuestaoAndContrato(final Integer idQuestao, final Integer idContrato) throws Exception {
        return this.getDao().listByIdQuestaoAndContrato(idQuestao, idContrato);
    }

    @Override
    public Collection listByIdQuestaoAndContratoOrderDataASC(final Integer idQuestao, final Integer idContrato) throws Exception {
        return this.getDao().listByIdQuestaoAndContratoOrderDataASC(idQuestao, idContrato);
    }

}
