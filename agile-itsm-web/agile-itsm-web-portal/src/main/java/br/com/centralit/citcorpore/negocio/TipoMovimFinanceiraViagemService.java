package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.citframework.service.CrudService;

/**
 * @author ronnie.lopes
 *
 */
public interface TipoMovimFinanceiraViagemService extends CrudService {
	public List<TipoMovimFinanceiraViagemDTO> listByClassificacao(String classificacao) throws Exception;
	public TipoMovimFinanceiraViagemDTO findByMovimentacao(Long idtipoMovimFinanceiraViagem) throws Exception;
	public TipoMovimFinanceiraViagemDTO findByMovimentacaoEstadoAdiantamento(Long idtipoMovimFinanceiraViagem, String adiantamento) throws Exception;
	public List<TipoMovimFinanceiraViagemDTO> recuperaTipoAtivos() throws Exception;
}
