/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoItemConfiguracaoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
@SuppressWarnings("rawtypes")
public interface RequisicaoLiberacaoItemConfiguracaoService extends CrudService {
	public RequisicaoLiberacaoItemConfiguracaoDTO restoreByChaveComposta(RequisicaoLiberacaoItemConfiguracaoDTO dto) throws ServiceException, Exception;
	
	public ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> listByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws ServiceException, Exception;
	
	
	public Collection findByIdItemConfiguracao(Integer parm) throws Exception;
	public Collection findByIdRequisicaoLiberacao(Integer parm) throws Exception;
	public RequisicaoLiberacaoItemConfiguracaoDTO findByIdReqLiberacao(Integer parm) throws Exception;
}
