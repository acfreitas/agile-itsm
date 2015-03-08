/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaServicoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface RequisicaoMudancaServicoService extends CrudService {
	public ArrayList<RequisicaoMudancaServicoDTO> listByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception;
	public RequisicaoMudancaServicoDTO restoreByChaveComposta(RequisicaoMudancaServicoDTO dto) throws ServiceException, Exception;
	
}
