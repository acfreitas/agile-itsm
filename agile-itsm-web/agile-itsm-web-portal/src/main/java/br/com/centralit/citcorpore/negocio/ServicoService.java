package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;

import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface ServicoService extends CrudService {
	public Collection findByIdCategoriaServico(Integer parm) throws Exception;

	public void deleteByIdCategoriaServico(Integer parm) throws Exception;

	public Collection findByIdSituacaoServico(Integer parm) throws Exception;
	
	public void deleteByIdSituacaoServico(Integer parm) throws Exception;

	public Collection findByIdTipoDemandaAndIdCategoria(Integer idTipoDemanda, Integer idCategoria) throws Exception;

	public Collection findByIdServicoAndIdTipoDemandaAndIdCategoria(Integer idServico, Integer idTipoDemanda, Integer idCategoria) throws Exception;

	/**
	 * Retorna Sigla do Serviço por idOs.
	 * 
	 * @param idOs
	 * @return
	 * @throws Exception
	 */
	public String retornarSiglaPorIdOs(Integer idOs) throws Exception;

	public Collection findByIdTipoDemandaAndIdContrato(Integer idTipoDemanda, Integer idContrato, Integer idCategoria) throws Exception;

	public Collection<ServicoDTO> findByServico(Integer idServico) throws Exception;

	public Collection<ServicoDTO> findByServico(Integer idServico, String nome) throws Exception;

	public Collection<ServicoDTO> listaQuantidadeServicoAnalitico(ServicoDTO servicoDTO) throws Exception;

	public ServicoDTO findByIdServico(Integer idServico) throws Exception;

	public Collection<ServicoDTO> listAtivos() throws Exception;

	public void desvincularServicosRelacionadosTemplate(Integer idTemplate) throws Exception;
	
	public Collection findByNomeAndContratoAndTipoDemandaAndCategoria(Integer idTipoDemanda, Integer idContrato, Integer idCategoria, String nome) throws Exception;

	public ServicoDTO findById(Integer idServico) throws Exception;

	String verificaIdCategoriaServico(HashMap mapFields) throws Exception;
	/**
	 * Retorna uma lista de servicos ativos que ainda não foram adicionados a este contrato
	 * @param servicoDto
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<ServicoDTO> listAtivosDiferenteContrato(ServicoDTO servicoDto) throws Exception;
}
