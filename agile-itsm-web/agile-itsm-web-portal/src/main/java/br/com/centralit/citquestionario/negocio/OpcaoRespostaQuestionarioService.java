package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface OpcaoRespostaQuestionarioService extends CrudService {

    Collection listByIdQuestaoQuestionario(final Integer idQuestaoQuestionario) throws Exception;

}
