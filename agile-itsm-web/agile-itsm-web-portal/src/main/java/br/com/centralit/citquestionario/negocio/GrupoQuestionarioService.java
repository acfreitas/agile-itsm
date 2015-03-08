package br.com.centralit.citquestionario.negocio;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.citframework.service.CrudService;

public interface GrupoQuestionarioService extends CrudService {

    Collection listByIdQuestionario(final Integer idQuestionario) throws Exception;

    Collection geraImpressao(final Collection colQuestoes);

    Collection geraImpressaoFormatadaHTML(final Collection colQuestoes, final Date dataQuestionario, final Integer idSolicitacaoServico, final Integer idProfissional);

    Collection geraImpressaoFormatadaHTMLQuestao(final QuestaoQuestionarioDTO questaoDto);

}
