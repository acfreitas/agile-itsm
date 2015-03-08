package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CategoriaGaleriaImagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface CategoriaGaleriaImagemService extends CrudService {

	/**
	 * Exclui Categoria Imagem caso o mesmo não possua empregado associado.
	 * 
	 * @param model
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void deletarCategoriaImagem(IDto model, DocumentHTML document) throws ServiceException, Exception;

	/**
	 * Consultar Categoria Imagem Ativos
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public boolean consultarCategoriaImagemAtivos(CategoriaGaleriaImagemDTO obj) throws Exception;
}