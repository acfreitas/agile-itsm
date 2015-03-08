package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.centralit.citquestionario.bean.QuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface QuestionarioService extends CrudService {

    Collection listByIdEmpresa(final Integer idEmpresa) throws Exception;

    Collection listByIdEmpresaAndAplicacao(final Integer idEmpresa, final String aplicacao) throws Exception;

    void copyGroup(final IDto model) throws ServiceException, LogicException;

    QuestionarioDTO restoreByIdOrigem(final Integer idQuestionarioOrigem) throws Exception;

    void updateOrdemGrupos(final IDto model) throws ServiceException, LogicException;

    void updateNomeGrupo(final IDto model) throws ServiceException, LogicException;

    Collection listOpcoesRespostaItemQuestionarioOpcoes(final Integer idRespostaItemQuestionario) throws Exception;

    boolean existeQuestaoObrigatoria(final Integer idQuestionario) throws Exception;

}
