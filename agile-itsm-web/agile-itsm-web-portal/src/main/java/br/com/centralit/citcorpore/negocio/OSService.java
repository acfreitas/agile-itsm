package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.RelatorioAcompanhamentoDTO;
import br.com.centralit.citcorpore.bean.RelatorioOrdemServicoUstDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface OSService extends CrudService {

	public void updateSituacao(Integer idOs, Integer situacao, Collection colGlosasOS, Collection colItens, String obsFinalizacao) throws Exception;

	public void updateSituacao(OSDTO os, Collection colGlosas, Collection colItens) throws Exception;

	public Collection findByIdContrato(Integer parm) throws Exception;

	public void deleteByIdContrato(Integer parm) throws Exception;

	public Collection findByIdClassificacaoOS(Integer parm) throws Exception;

	public void deleteByIdClassificacaoOS(Integer parm) throws Exception;

	public Collection findByAno(Integer parm) throws Exception;

	public void deleteByAno(Integer parm) throws Exception;

	public Collection findByIdContratoAndSituacao(Integer parm, Integer sit) throws Exception;
	
	public Collection findByIdContratoAndPeriodoAndSituacao(Integer idContrato, Date dataInicio, Date dataFim, Integer[] situacao) throws Exception;
	
	public Collection findByIdContratoAndPeriodoAndSituacao(Integer idContrato, Date dataInicio, Date dataFim, Integer[] situacao, Integer idospai) throws Exception;

	public Collection listOSHomologadasENaoAssociadasFatura(Integer idContrato) throws Exception;

	public Collection listOSByIds(Integer idContrato, Integer[] idOSs) throws Exception;

	public Collection listOSAssociadasFatura(Integer idFatura) throws Exception;

	public void duplicarOS(Integer idOS) throws Exception;

	public Collection listOSByIdAtividadePeriodica(Integer parm) throws Exception;

	public Collection listAtividadePeridodicaByIdos(Integer idos) throws Exception;

	public void retornaAtividadeCadastradaByPai(OSDTO osDTO) throws Exception;

	/**
	 * Retornar uma lista de custo atividade por periodo
	 * 
	 * @param relatorio
	 * @return
	 * @throws Exception
	 */
	public Collection<RelatorioOrdemServicoUstDTO> listaCustoAtividadeOrdemServicoPorPeriodo(RelatorioOrdemServicoUstDTO relatorio) throws Exception;

	/**
	 * lista anos das ordem de servico
	 * 
	 * @return
	 * @throws Exception
	 */
	public Collection<RelatorioOrdemServicoUstDTO> listaAnos() throws Exception;

	/**
	 * Retonar uma lista de cussto atividade por perido do contrato;
	 * 
	 * @param relatorio
	 * @return
	 * @throws Exception
	 */
	public Collection<RelatorioAcompanhamentoDTO> listaAcompanhamentoPorPeriodoDoContrato(RelatorioAcompanhamentoDTO relatorio) throws Exception;
	
	public boolean retornaRegistroOsPai(OSDTO osDTO) throws Exception;

	/**
	 * Retonar uma lista com as informações do contrato.
	 * @param relatorio
	 * @return
	 * @throws Exception
	 */
//	public Collection<RelatorioAcompanhamentoDTO> listaInformcoesDoContrato(RelatorioAcompanhamentoDTO contrato) throws Exception;
	
	/**
	 * Cancela as OS filhas (RAs) gerados se houver. 
	 * @param osDTO
	 * @throws Exception
	 */
	public void cancelaOsFilhas(OSDTO osDTO) throws Exception;
	
	public Collection retornaSeExisteOSFilha(OSDTO osDTO) throws Exception;
}
