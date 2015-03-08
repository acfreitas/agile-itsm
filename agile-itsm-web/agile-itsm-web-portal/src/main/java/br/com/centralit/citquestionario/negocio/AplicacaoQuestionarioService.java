package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface AplicacaoQuestionarioService extends CrudService {

    Collection listByIdQuestionarioAndAplicacao(final Integer idQuestionario, final String aplicacao) throws Exception;

}
