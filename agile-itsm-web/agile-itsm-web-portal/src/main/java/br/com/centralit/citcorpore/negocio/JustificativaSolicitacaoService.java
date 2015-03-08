package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.citframework.service.CrudService;

/**
 * @author breno.guimaraes
 *
 */
public interface JustificativaSolicitacaoService extends CrudService {

    Collection<JustificativaSolicitacaoDTO> listAtivasParaSuspensao() throws Exception;

    Collection<JustificativaSolicitacaoDTO> listAtivasParaAprovacao() throws Exception;

    /**
     * Retorna uma lista de justificativas ativas para requisicao viagem
     *
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    Collection<JustificativaSolicitacaoDTO> listAtivasParaViagem() throws Exception;

}
