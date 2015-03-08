package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaParecerDTO;
import br.com.citframework.service.CrudService;

/**
 * @author breno.guimaraes
 *
 */
public interface JustificativaParecerService extends CrudService {

    Collection<JustificativaParecerDTO> listAplicaveisRequisicao() throws Exception;

    Collection<JustificativaParecerDTO> listAplicaveisRequisicaoViagem() throws Exception;

    Collection<JustificativaParecerDTO> listAplicaveisCotacao() throws Exception;

    Collection<JustificativaParecerDTO> listAplicaveisInspecao() throws Exception;

    boolean consultarJustificativaAtiva(final JustificativaParecerDTO justificativa) throws Exception;

}
