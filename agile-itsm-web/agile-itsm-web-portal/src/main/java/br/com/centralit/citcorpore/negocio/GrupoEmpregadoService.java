package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.RelatorioGruposUsuarioDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface GrupoEmpregadoService extends CrudService {

	public Collection<GrupoEmpregadoDTO> findByIdGrupo(Integer idGrupo) throws Exception;

	public Collection<GrupoEmpregadoDTO> findUsariosGrupo() throws Exception;

	public void gerarGridEmpregados(DocumentHTML document, Collection<GrupoEmpregadoDTO> grupoEmpregados) throws Exception;

	/**
	 * Fazer uma cole��o de empregado se idEmpregado;
	 * 
	 * @param grupo
	 * @return true - existe; false - n�o existe;
	 * @throws PersistenceException
	 */
	public Collection findByIdEmpregado(Integer idEmpregado) throws Exception;

	/**
	 * Deleta Grupo Empregado por IdGrupo e IdEmpregado.
	 * 
	 * @param idGrupo
	 * @param idEmpregado
	 * @throws Exception
	 */
	public void deleteByIdGrupoAndEmpregado(Integer idGrupo, Integer idEmpregado) throws Exception;

	/**
	 * Deleta todos empregados vinculados ao idGrupo informado
	 * 
	 * @param idGrupo
	 * @throws Exception
	 */
	public void deleteTodosEmpregados(Integer idGrupo) throws Exception;
	/**
	 * Retorna GrupoEmpregado do Tipo HelpDesk de acordo com o ID Contrato informado.
	 * 
	 * @param idContrato
	 *            - Identificador do contrato.
	 * @return Collection<GrupoEmpregadoDTO>
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<GrupoEmpregadoDTO> findGrupoEmpregadoHelpDeskByIdContrato(Integer idContrato);

	/**
	 * Retorna Lista de GrupoEmpregadoDTO com informa��es do Grupo e Empregados.
	 * 
	 * @param idGrupo
	 *            - Identificador �nico do Grupo.
	 * @return listGrupoEmpregadoDTO - Lista de GrupoEmpregadoDTO com informa��es do empregado.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<GrupoEmpregadoDTO> findGrupoAndEmpregadoByIdGrupo(Integer idGrupo) throws Exception;
	public Collection<RelatorioGruposUsuarioDTO> listaRelatorioGruposUsuario(Integer idColaborador) throws Exception;
	public Collection findByIdEmpregadoNome(Integer idEmpregado) throws Exception;
	
	public Integer calculaTotalPaginas(Integer itensPorPagina, Integer idGrupo) throws Exception;	
	public Collection<GrupoEmpregadoDTO> paginacaoGrupoEmpregado(Integer idGrupo, Integer pgAtual, Integer qtdPaginacao) throws Exception;
	
	
	/**
	 * Metodo que valida na tabela gruposempregados a existencia de registro com os parametros informados
	 * 
	 * @param idEmpregado
	 * @param idGrupo
	 * 
	 * @return TRUE: Existe registro com os parametros informados || FALSE: N�o existe registro com os parametros informados
	 * 
	 * @throws Exception
	 */
	public boolean grupoempregado (Integer idEmpregado, Integer idGrupo) throws Exception;
	
	public Collection<GrupoEmpregadoDTO> findEmpregado(Integer idGrupo, Integer idEmpregado) throws Exception;

	public Collection<GrupoEmpregadoDTO> verificacaoResponsavelPorSolicitacao(Integer idGrupo, Integer idEmpregado) throws Exception;
	
	
	
}
