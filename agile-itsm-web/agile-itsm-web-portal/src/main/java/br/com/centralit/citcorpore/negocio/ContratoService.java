package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;

import br.com.centralit.citcorpore.bean.ComplexidadeDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface ContratoService extends CrudService {

	public Collection findByIdCliente(Integer parm) throws Exception;

	public Collection findByIdFornecedor(Integer parm) throws Exception;

	public Collection findByIdContrato(Integer parm) throws Exception;

	public void deleteByIdCliente(Integer parm) throws Exception;

	/**
	 * Retorna uma lista de complexidade de acordo com o contrato passado.
	 * 
	 * @param idServicoContrato
	 * @return Collection
	 * @throws Exception
	 */
	public Collection<ComplexidadeDTO> listaComplexidadePorContrato(Integer idServicoContrato) throws Exception;

	public Collection listByIdAcordoNivelServicoAndTipo(Integer idAcordoNivelServicoParm, String tipoParm) throws Exception;

	/**
	 * Lista Contratos Ativos (Situação Ativa e DataFim maior que a data Atual).
	 * 
	 * @return Collection<ContratoDTO> - Lista de Contratos Ativos.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 30.10.2013
	 */
	public Collection<ContratoDTO> listAtivos() throws Exception;

	public Collection findByIdGrupo(Integer idGrupo) throws Exception;

	/**
	 * Retorna a Lista de Contratos (Situação Ativa e DataFim maior que a data Atual) que estão relacionados aos Grupos informados.
	 * 
	 * @param listGrupoDto
	 *            - Lista de GrupoDTO.
	 * @return Collection<ContratoDTO> - Lista de Contratos Ativos encontrados.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 30.10.2013
	 */
	public Collection<ContratoDTO> findAtivosByGrupos(Collection<GrupoDTO> listGrupoDto) throws Exception;

	/**
	 * Retorna Lista de Contratos Ativos (Situação Ativa e DataFim maior que a data Atual) que estão relacionados aos Grupos do Empregado informado.
	 * 
	 * @param idEmpregado
	 *            - Identificador do Empregado.
	 * @return Collection<ContratoDTO> - Lista de Contratos Ativos.
	 * @throws Exception
	 * @throws ServiceException
	 * @since 30.10.2013
	 */
	public Collection<ContratoDTO> findAtivosByIdEmpregado(Integer idEmpregado) throws ServiceException, Exception;

	String verificaIdCliente(HashMap mapFields) throws Exception;

	String verificaIdFornecedor(HashMap mapFields) throws Exception;

	/**
	 * Retorna a lista de Contratos com o nome da Razão Social do Cliente do Contrato.
	 * 
	 * @return Collection<ContratoDTO>
	 * @throws ServiceException
	 * @throws Exception
	 * @since 04.06.2014
	 * @author valdoilo.damasceno
	 */
	public Collection<ContratoDTO> listAtivosWithNomeRazaoSocialCliente() throws ServiceException, Exception;

}
