package br.com.centralit.citcorpore.negocio;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.FormaPagamentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author geber.costa
 *
 */
public interface FormaPagamentoService extends CrudService{
	
	/**
	 * Deletar a Forma de Pagamento
	 * 
	 * @param model
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void deletarFormaPagamento(IDto model, DocumentHTML document,HttpServletRequest request) throws ServiceException, Exception;
	
	
	/**
	 * Encontrar forma de pagamento pelo id
	 * 
	 * @param formaPagamentoDto
	 * @return
	 * @throws Exception
	 */
	public boolean consultarFormaPagamento(FormaPagamentoDTO obj) throws Exception;
	
	
}
