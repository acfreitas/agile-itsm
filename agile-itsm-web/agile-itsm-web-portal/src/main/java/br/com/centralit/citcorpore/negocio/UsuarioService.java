package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ADUserDTO;
import br.com.centralit.citcorpore.bean.LoginDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface UsuarioService extends CrudService {

	/**
	 * Restaura Usu�rio por Login.
	 * 
	 * @param login
	 * @return
	 * @throws ServiceException
	 * @throws LogicException
	 */
	public UsuarioDTO restoreByLogin(String login) throws ServiceException, LogicException;
	
	public UsuarioDTO restoreByID(Integer id) throws ServiceException, LogicException;

	public UsuarioDTO restoreByLogin(String login, String senha) throws ServiceException, LogicException;

	public void deleteByIdEmpregado(Integer idEmpregado) throws ServiceException, Exception;

	public UsuarioDTO restoreByIdEmpregado(Integer idEmpregado) throws ServiceException, Exception;

	public IDto createFirs(IDto model) throws ServiceException, LogicException;

	public UsuarioDTO listStatus(UsuarioDTO obj) throws Exception;

	public UsuarioDTO listLogin(UsuarioDTO obj) throws Exception;
	
	public UsuarioDTO listUsuarioExistente(UsuarioDTO obj) throws Exception;

	
	public boolean listSeVazio() throws Exception;
	
	void updateNotNull(IDto dto);

	/**
	 * Sincroniza Usu�rio que logou no sistema.
	 * 
	 * @param usuarioAd
	 * @param login
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void sincronizaUsuarioAD(ADUserDTO usuarioAd, LoginDTO login, Boolean isImport) throws ServiceException, Exception;

	/**
	 * Sincroniza Usu�rio pela rotina de Schedule.
	 * 
	 * @param usuarioAd
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void sincronizaUsuarioAD(ADUserDTO usuarioAd, Integer idGrupoSolicitante) throws ServiceException, Exception;

	public UsuarioDTO restoreByIdEmpregadosDeUsuarios(Integer idEmpregado) throws Exception;

	/**
	 * Verifica se usu�rio informado � um usu�rio do AD.
	 * 
	 * @param usuarioDto
	 * @return true - Usu�rio do AD; false - Usu�rio cadastrado pelo sistema.
	 * @throws Exception
	 */
	public boolean usuarioIsAD(UsuarioDTO usuarioDto) throws Exception;

	/**
	 * Gera carga de Usu�rios do AD atrav�s de Arquivo .csv
	 * 
	 * @param arquivo
	 * @throws IOException
	 * @author Vadoilo Damasceno
	 */
	public void gerarCarga(File arquivo) throws IOException;
	
	public Collection listAtivos() throws Exception;
	
	public Collection<UsuarioDTO> consultarUsuarioPorNomeAutoComplete(String nome) throws Exception;
	
	/**
	 * Retorna a quantidade de usu�rios ativos no sistema
	 * 
	 * @return Long
	 * @throws Exception
	 * @author renato.jesus
	 */
	public Long retornaQuantidadeUsuariosAtivos() throws Exception;
}
