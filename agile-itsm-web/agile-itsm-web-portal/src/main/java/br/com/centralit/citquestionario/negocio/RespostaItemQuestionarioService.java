package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

public interface RespostaItemQuestionarioService extends CrudService {

    void deleteByIdQuestaoAndIdentificadorResposta(final Integer idQuestaoQuestionario, final Integer idIdentificadorResposta) throws Exception;

    Collection listByIdIdentificadorAndIdQuestao(final Integer idIdentificadorResposta, final Integer idQuestaoQuestionario) throws Exception;

    Collection getRespostasOpcoesByIdRespostaItemQuestionario(final Integer idRespostaItemQuestionario) throws Exception;

    void deleteByIdIdentificadorResposta(final RespostaItemQuestionarioDTO resposta) throws ServiceException, LogicException;

    void deleteByIdIdentificadorResposta(final RespostaItemQuestionarioDTO resposta, final TransactionControler tc) throws Exception;

}
