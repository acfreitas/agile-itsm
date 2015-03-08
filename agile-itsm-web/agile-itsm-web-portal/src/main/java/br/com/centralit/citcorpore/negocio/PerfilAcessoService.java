package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface PerfilAcessoService extends CrudService {

	/**
	 * Restaura GRID de Perfis de Acesso.
	 * 
	 * @param document
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void gerarGridPerfilAcesso(DocumentHTML document, Collection<PerfilAcessoDTO> perfisDeAcesso) throws Exception;

	/**
	 * Consulta perfis de Acesso Ativos.
	 * 
	 * @return perfisDeAcessoAtivo
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<PerfilAcessoDTO> consultarPerfisDeAcesso(PastaDTO pastaBean) throws ServiceException, Exception;

	public PerfilAcessoDTO listByName(PerfilAcessoDTO obj) throws Exception;

	/**
	 * Exclui Perfil de Acesso se o mesmo não estiver sendo utilizado.
	 * 
	 * @param perfilAcessoDto
	 * @return true - está sendo utilizado; false - não está sendo utilizado.
	 * @throws ServiceException
	 * @throws LogicException
	 * @throws Exception
	 */
	public boolean excluirPerfilDeAcesso(PerfilAcessoDTO perfilAcessoDto) throws ServiceException, LogicException, Exception;

	/**
	 * Verifica se PerfilAcesso informado existe.
	 * 
	 * @param perfilAcesso
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSePerfilAcessoExiste(PerfilAcessoDTO perfilAcesso) throws PersistenceException;

	/**
	 * Consulta Perfil de Acesso Ativos.
	 * 
	 * @return Collection<PerfilAcessoDTO>
	 * @throws Exception
	 */
	public Collection<PerfilAcessoDTO> consultarPerfisDeAcessoAtivos() throws Exception;

	
	/**
	 * Metodo responsasvel por 
	 * 
	 * @param perfilAcessoDTO
	 * @return
	 * @throws Exception 
	 */
	public PerfilAcessoDTO findByIdPerfilAcesso(PerfilAcessoDTO perfilAcessoDTO) throws Exception;
	
	/**
	 * Método para retornar se o usuário pode acessar o citsmart. Caso nenhum grupo possua acesso, retorna "N"
	 * 30/12/2014
	 * @author thyen.chang
	 * @param idUsuario
	 * @return
	 * @throws PersistenceException
	 * @throws ServiceException
	 */
	public String getAcessoCitsmartByUsuario(Integer idUsuario) throws PersistenceException, ServiceException;
	

}
