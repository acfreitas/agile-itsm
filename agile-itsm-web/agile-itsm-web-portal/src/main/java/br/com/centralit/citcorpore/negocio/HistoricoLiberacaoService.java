/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudService;

public interface HistoricoLiberacaoService extends CrudService {
	public List<HistoricoLiberacaoDTO> listHistoricoLiberacaoByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws Exception;
	/*public List<HistoricoItemConfiguracaoDTO> listHistoricoItemCfValorByIdHistoricoIC(Integer idHistoricoIC) throws Exception;*/
	public void updateNotNull(IDto obj) throws Exception ;
	public HistoricoLiberacaoDTO maxIdHistorico(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception;
	
}