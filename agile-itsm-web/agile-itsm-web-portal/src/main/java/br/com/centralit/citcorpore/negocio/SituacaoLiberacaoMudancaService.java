package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.SituacaoLiberacaoMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * 
 * @author geber.costa
 *
 */

public interface SituacaoLiberacaoMudancaService extends CrudService{
	public boolean consultaExistenciaSituacao(SituacaoLiberacaoMudancaDTO obj) throws Exception;
	public void deletarSituacao(IDto model, DocumentHTML document,HttpServletRequest request) throws ServiceException, Exception;
	public Collection<SituacaoLiberacaoMudancaDTO> listAll() throws ServiceException, Exception ;
}
