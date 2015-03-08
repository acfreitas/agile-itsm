/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author valdoilo.damasceno
 * 
 */
public interface ParametroCorporeService extends CrudService {

	void create(ParametroCorporeDTO parametroBean, HttpServletRequest request) throws ServiceException, LogicException;

	public List<ParametroCorporeDTO> pesquisarParamentro(Integer id, String nomeParametro) throws ServiceException, LogicException, Exception;

	/**
	 * Cria Parâmetros do CITSmart de acordo com o Enum ParametroSistema. Esses parâmetros não podem ser excluídos.
	 * 
	 * @throws Exception
	 * @author valdoilo
	 */
//	public void criarParametros() throws Exception;

	/**
	 * Cria e atualiza o HashMap statico de Parâmetros do CITSMart.
	 * 
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void criarParametrosNovos() throws Exception;

	public ParametroCorporeDTO getParamentroAtivo(Integer id) throws Exception;

	/**
	 * Atualiza Parâmetros utilizando UpdateNotNull. Informar apenas o ID do Parâmetro e o Valor.
	 * 
	 * @param parametroDto
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void atualizarParametros(ParametroCorporeDTO parametroDto) throws Exception;
	
	public void updateNotNull(IDto dto) throws Exception;

	/**
	 * Atualiza o valor do parametro pelo id 
	 * 
	 */
	public void atualizarParametro(Integer id, String valor)  throws Exception;
	
}
