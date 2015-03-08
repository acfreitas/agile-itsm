package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.PesquisaProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.centralit.citcorpore.bean.ProblemaRelacionadoDTO;
import br.com.centralit.citcorpore.bean.RelatorioProblemaIncidentesDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoProblemaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface ProblemaService extends CrudService {

	public void createAll(ProblemaDTO problema, ArrayList<ProblemaItemConfiguracaoDTO> ics, ArrayList<ProblemaMudancaDTO> rdm, UsuarioDTO usuarioDto) throws ServiceException, LogicException;

	public void updateAll(ProblemaDTO problema, ArrayList<ProblemaItemConfiguracaoDTO> ics, ArrayList<ProblemaMudancaDTO> rdm, UsuarioDTO usuarioDto) throws ServiceException, LogicException;

	public void deleteAll(ProblemaDTO problema) throws ServiceException, LogicException;

	public Collection findByIdProblema(Integer parm) throws Exception;

	public void deleteByIdProblema(Integer parm) throws Exception;

	public Collection findByStatus(String parm) throws Exception;

	public void deleteByStatus(String parm) throws Exception;

	public Collection findByPrioridade(Integer parm) throws Exception;

	public void deleteByPrioridade(Integer parm) throws Exception;

	public Collection findByIdCriador(Integer parm) throws Exception;

	public void deleteByIdCriador(Integer parm) throws Exception;

	public Collection findByIdProprietario(Integer parm) throws Exception;

	public void deleteByIdProprietario(Integer parm) throws Exception;

	public Collection findByTitulo(String parm) throws Exception;

	public void deleteByTitulo(String parm) throws Exception;

	public Collection findByDescricao(String parm) throws Exception;

	public void deleteByDescricao(String parm) throws Exception;

	public Collection findByIdCategoriaProblema(Integer parm) throws Exception;

	public void deleteByIdCategoriaProblema(Integer parm) throws Exception;

	public Collection findByImpacto(String parm) throws Exception;

	public void deleteByImpacto(String parm) throws Exception;

	public Collection findByUrgencia(String parm) throws Exception;

	public void deleteByUrgencia(String parm) throws Exception;

	public Collection findByProativoReativo(String parm) throws Exception;

	public void deleteByProativoReativo(String parm) throws Exception;

	public Collection findByMsgErroAssociada(String parm) throws Exception;

	public void deleteByMsgErroAssociada(String parm) throws Exception;

	public Collection findByIdProblemaItemConfiguracao(Integer parm) throws Exception;

	public void deleteByIdProblemaItemConfiguracao(Integer parm) throws Exception;

	public Collection findByIdErrosConhecidos(Integer parm) throws Exception;

	public void deleteByIdErrosConhecidos(Integer parm) throws Exception;

	public Collection findByIdProblemaMudanca(Integer parm) throws Exception;

	public void deleteByIdProblemaMudanca(Integer parm) throws Exception;

	public Collection findByIdProblemaIncidente(Integer parm) throws Exception;

	public void deleteByIdProblemaIncidente(Integer parm) throws Exception;

	public List<ProblemaDTO> listProblema(ProblemaDTO bean) throws ServiceException, LogicException;

	// public Collection<ProblemaDTO> listProblemasFilhos() throws Exception;

	public Collection findBySolictacaoServico(ProblemaDTO bean) throws ServiceException, LogicException;

	/**
	 * Retorna Problemas associados ao conhecimento informado.
	 * 
	 * @param baseConhecimentoDto
	 * @return Collection
	 * @throws ServiceException
	 * @throws LogicException
	 * @author Vadoilo Damasceno
	 */
	public Collection findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, LogicException;

	public List<ProblemaDTO> listProblemaByIdItemConfiguracao(Integer idItemConfiguracao) throws PersistenceException;

	public Collection<ProblemaDTO> quantidadeProblemaPorBaseConhecimento(ProblemaDTO problema) throws Exception;

	public Collection<ProblemaDTO> listaProblemaPorBaseConhecimento(ProblemaDTO problema) throws Exception;

	public ProblemaDTO restoreAll(Integer idProblema) throws Exception;

	public void suspende(UsuarioDTO usuarioDto, ProblemaDTO problemaDto) throws Exception;

	public void reativa(UsuarioDTO usuarioDto, ProblemaDTO problemaDto) throws Exception;

	public boolean validacaoAvancaFluxo(ProblemaDTO problemaDTO, TransactionControler tc) throws Exception;

	public void notificarPrazoSolucionarProblemaExpirou(ProblemaDTO problemaDTO) throws Exception;

	/**
	 * 
	 * @param obj
	 * @throws Exception
	 * 
	 */
	public void updateNotNull(IDto obj) throws Exception;

	/**
	 * Retorna a quantidade de problema com prazo limite experado
	 * 
	 * @param usuarioDTO
	 * @return
	 * @throws Exception
	 * @author thiago
	 */
	public int contarProblemasComPrazoLimiteContornoExpirado(UsuarioDTO usuarioDTO) throws Exception;

	/**
	 * Retorna uma lista de problemas com prazo limite expeirado.
	 * 
	 * @param UsuarioDTO
	 *            - usuarioDTO
	 * @return Collection
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<ProblemaDTO> listaProblemasComPrazoLimiteContornoExpirado(UsuarioDTO usuarioDTO) throws Exception;

	/**
	 * @param parm
	 * @return Collection
	 * @throws ServiceException
	 * @throws LogicException
	 *             @ riubbe
	 */
	public Collection findByIdSolictacaoServico(Integer parm) throws ServiceException, LogicException;

	/**
	 * @param pesquisaProblemaDto
	 * @return Collection
	 * @throws Exception
	 * @author geber
	 */
	public Collection<PesquisaProblemaDTO> listProblemaByCriterios(PesquisaProblemaDTO pesquisaProblemaDto) throws Exception;

	/**
	 * Retorna a url
	 * 
	 * @param solicitacaoServicoDto
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public String getUrlInformacoesComplementares(ProblemaDTO problemaDto) throws Exception;

	/**
	 * @param solicitacaoServicoDto
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void deserializaInformacoesComplementares(ProblemaDTO problemaDto) throws Exception;

	/**
	 * @param relatorioProblemaIcidentesDto
	 * @return Collection
	 * @throws Exception
	 * @author riubbe.oliveira Retorna um objeto(RelatorioProblemaIncidentesDTO) contendo todos os incidentes relacionados ao problema
	 */
	public Collection<RelatorioProblemaIncidentesDTO> listProblemasIncidentes(RelatorioProblemaIncidentesDTO relatorioProblemaIcidentesDto) throws Exception;

	public ProblemaDTO restoreAll(ProblemaDTO problema) throws Exception;

	public ProblemaDTO restauraTodos(ProblemaDTO param) throws Exception;

	/**
	 * Retorna uma lista quantitativa de problema por situação
	 * 
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorSituacao(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception;

	/**
	 * Retorna uma lista quantitativa de problema por grupo
	 * 
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorGrupo(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception;

	/**
	 * Retorna uma lista quantitativa de problema por origem
	 * 
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorOrigem(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception;

	/**
	 * Retorna uma lista quantitativa de problema por solicitante
	 * 
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorSolicitante(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception;

	/**
	 * Retorna uma lista quantitativa de problema por prioridade
	 * 
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorPrioridade(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception;

	/**
	 * Retorna uma lista quantitativa de problema por categoria problema
	 * 
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorCategoria(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception;

	/**
	 * Retorna uma lista quantitativa de problema por proprietario
	 * 
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorProprietario(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception;

	/**
	 * Retorna uma lista quantitativa de problema por impacto
	 * 
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorImpacto(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception;

	/**
	 * Retorna uma lista quantitativa de problema por urgencia
	 * 
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorUrgencia(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception;

	public boolean verificaPermissaoGrupoCancelar(Integer idTipoProblema, Integer idGrupo) throws ServiceException, Exception;

	public Collection findByProblemaRelacionado(ProblemaRelacionadoDTO bean) throws Exception;

	/**
	 * Método criado para que o create de Problema compartilhe da mesma transação que está sendo utilizada em outra rotina ou entidade.
	 * 
	 * @param problemaDto
	 *            - ProblemaDTO
	 * @param tc
	 *            - TransactionControler
	 * @author valdoilo.damasceno
	 * @throws Exception
	 * @since 05.12.2014
	 */
	public void create(ProblemaDTO problemaDto, TransactionControler tc) throws Exception;

}
