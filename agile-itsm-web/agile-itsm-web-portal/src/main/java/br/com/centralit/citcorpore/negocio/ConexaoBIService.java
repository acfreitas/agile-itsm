package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.BICitsmartResultRotinaDTO;
import br.com.centralit.citcorpore.bean.ConexaoBIDTO;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;


public interface ConexaoBIService extends CrudService {
	@SuppressWarnings("rawtypes")
	public Collection listAll() throws Exception;
	@SuppressWarnings("rawtypes")
	public Collection findByIdConexao(ConexaoBIDTO conexaoBIDTO) throws Exception;
	@SuppressWarnings("rawtypes")
	public Collection listarConexoesPaginadas(Collection<ConexaoBIDTO> conexaoBIDTO, Integer pgAtual, Integer qtdPaginacao) throws Exception;
	@SuppressWarnings("rawtypes")
	public Collection listarConexoesPaginadasFiltradas(ConexaoBIDTO conexaoBIDTO, Integer pgAtual, Integer qtdPaginacao) throws Exception;
	public boolean jaExisteRegistroComMesmoNome(ConexaoBIDTO conexaoBIDTO) throws Exception;
	public boolean jaExisteRegistroComMesmoLink(ConexaoBIDTO conexaoBIDTO) throws Exception;
	public Integer obterTotalDePaginas(Integer itensPorPagina, String loginUsuario, ConexaoBIDTO conexaoBIBean) throws Exception;
	public ConexaoBIDTO findByIdProcessBatch(Integer idProcessamentoBatch) throws Exception;
	public ArrayList<ConexaoBIDTO> listarConexoesAutomaticasSemAgendEspOuExcecao() throws ServiceException, Exception;
	public String getIdProcEspecificoOuExcecao() throws Exception;
	public java.util.Date getProxDtExecucao(ConexaoBIDTO conexaoBIDto) throws ServiceException, Exception;
	public java.util.Date getProxDtExecucaoPadraoOuEspecifica(ConexaoBIDTO conexaoBIDto) throws ServiceException, Exception;
	public BICitsmartResultRotinaDTO validaAgendamentoExcecao (ConexaoBIDTO conexaoBIDTO, ProcessamentoBatchDTO processamentoBatchDTO)  throws ServiceException, Exception;
}