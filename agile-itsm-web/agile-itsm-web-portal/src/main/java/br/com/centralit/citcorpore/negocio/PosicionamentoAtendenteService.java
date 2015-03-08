package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.PosicionamentoAtendenteDTO;
import br.com.centralit.citcorpore.bean.result.PosicionamentoAtendenteResultDTO;
import br.com.citframework.service.CrudService;

/**
 * Application Services para {@link PosicionamentoAtendenteDTO}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 06/10/2014
 *
 */
public interface PosicionamentoAtendenteService extends CrudService {

    /**
     * Lista o último posicionamento dos atendentes, juntamente com informações (se existente) da última solicitação atendimento
     *
     * @param posicionamentoAtendente
     *            objetos contendo os filtros a serem considerados
     * @return {@code List<PosicionamentoAtendenteDTO>} lista de posicionamentos
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 28/10/2014
     */
    List<PosicionamentoAtendenteResultDTO> listLastLocationWithSolicitationInfo(final PosicionamentoAtendenteDTO posicionamentoAtendente) throws Exception;

}
