/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
@SuppressWarnings("rawtypes")
public interface RequisicaoMudancaItemConfiguracaoService extends CrudService {
	public RequisicaoMudancaItemConfiguracaoDTO restoreByChaveComposta(RequisicaoMudancaItemConfiguracaoDTO dto) throws ServiceException, Exception;
	
	public ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception;
	
	
	public Collection findByIdItemConfiguracao(Integer parm) throws Exception;
}
