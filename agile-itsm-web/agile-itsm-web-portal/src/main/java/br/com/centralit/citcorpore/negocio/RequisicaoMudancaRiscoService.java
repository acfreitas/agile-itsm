package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaRiscoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface RequisicaoMudancaRiscoService extends CrudService{

	public ArrayList<RequisicaoMudancaRiscoDTO> listByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception;
	public RequisicaoMudancaRiscoDTO restoreByChaveComposta(RequisicaoMudancaRiscoDTO dto) throws ServiceException, Exception;
	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception;
	
}
