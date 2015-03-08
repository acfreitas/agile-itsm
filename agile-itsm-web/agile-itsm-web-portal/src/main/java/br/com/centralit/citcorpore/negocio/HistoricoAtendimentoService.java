package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoAtendimentoDTO;
import br.com.centralit.citcorpore.bean.result.HistoricoAtendimentoResultDTO;

/**
 * Application Services para {@link HistoricoAtendimentoDTO}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 28/10/2014
 *
 */
public interface HistoricoAtendimentoService {

    /**
     * Lista de histórico de atendimentos por atendente
     *
     * @param historicoAtendimento
     *            objetos contendo os filtros a serem considerados
     * @return {@code List<HistoricoAtendimentoResultDTO>} lista de posicionamentos
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 12/11/2014
     */
    List<HistoricoAtendimentoResultDTO> listHistoricoAtendimentoWithSolicitationInfo(final HistoricoAtendimentoDTO historicoAtendimento) throws Exception;

}
