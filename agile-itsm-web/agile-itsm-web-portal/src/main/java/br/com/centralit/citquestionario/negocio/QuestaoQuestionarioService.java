package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.citframework.service.CrudService;

public interface QuestaoQuestionarioService extends CrudService {

    Collection listByIdGrupoQuestionario(final Integer idGrupoQuestionario) throws Exception;

    Collection listByIdGrupoQuestionarioComAgrupadoras(final Integer idGrupoQuestionario) throws Exception;

    QuestaoQuestionarioDTO findBySiglaAndIdQuestionario(final String sigla, final Integer idQuestionario) throws Exception;

    Collection listByTipoQuestaoAndIdQuestionario(final String tipoQuestao, final Integer idQuestionario) throws Exception;

    Collection listByTipoAndIdQuestionario(final String tipo, final Integer idQuestionario) throws Exception;

    Collection listByIdQuestaoAndContrato(final Integer idQuestao, final Integer idContrato) throws Exception;

    Collection listByIdQuestaoAndContratoOrderDataASC(final Integer idQuestao, final Integer idContrato) throws Exception;

    Collection listByIdQuestaoAgrupadora(final Integer idQuestaoAgrupadora) throws Exception;

}
