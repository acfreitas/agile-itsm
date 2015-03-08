/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudService;

public interface HistoricoItemConfiguracaoService extends CrudService {
	public List<HistoricoItemConfiguracaoDTO> listHistoricoItemByIditemconfiguracao(Integer idItemconfiguracao) throws Exception;
	public List<HistoricoItemConfiguracaoDTO> listHistoricoItemCfValorByIdHistoricoIC(Integer idHistoricoIC) throws Exception;
	public void updateNotNull(IDto obj) throws Exception ;
	public HistoricoItemConfiguracaoDTO maxIdHistorico(ItemConfiguracaoDTO itemConfiguracao) throws Exception;
	
}