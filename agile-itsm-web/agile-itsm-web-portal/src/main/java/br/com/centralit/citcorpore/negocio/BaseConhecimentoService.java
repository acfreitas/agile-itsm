/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.fileupload.FileUploadException;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ComentariosDTO;
import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.service.CrudService;

/**
 * Service de BaseConhecimento.
 * 
 * @author valdoilo.damasceno
 */
public interface BaseConhecimentoService extends CrudService {

	/**
	 * Atributo utilizado em Gerência de Problemas.
	 * 
	 * @return Integer
	 */
	public Integer getIdBaseConhecimento();

	/**
	 * Inclui uma Nova Base de Conhecimento.
	 * 
	 * @param baseConhecimentoBean
	 * @param arquivosUpados
	 * @return BaseConhecimentoDTO
	 * @throws LogicException
	 * @throws ServiceException
	 * @throws IOException
	 * @throws FileUploadException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @param aprovaBaseConhecimento
	 * @param isAprovaBaseConhecimento
	 * @author valdoilo.damasceno
	 */
	public BaseConhecimentoDTO create(BaseConhecimentoDTO baseConhecimentoBean, Collection<UploadDTO> arquivosUpados, Integer idEmpresa, UsuarioDTO usuarioDto) throws Exception;

	/**
	 * Atualiza Base de Conhecimento de acordo com Status selecionado e o perfil de acesso do usuário logado.
	 * 
	 * @param baseConhecimentoBean
	 * @param arquivosUpados
	 * @param aprovaBaseConhecimento
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void update(BaseConhecimentoDTO baseConhecimentoBean, Collection<UploadDTO> arquivosUpados, Integer idEmpresa, UsuarioDTO usuarioDto) throws Exception;

	/**
	 * Exclui Base de Conhecimento.
	 * 
	 * @param baseConhecimentoBean
	 * @param isAprovaBaseConhecimento
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void excluir(BaseConhecimentoDTO baseConhecimentoBean, boolean isAprovaBaseConhecimento) throws Exception;

	/**
	 * Lista Conhecimentos por Pasta.
	 * 
	 * @param pastaDto
	 * @return Collection<BaseConhecimentoDTO>
	 * @throws Exception
	 */
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoByPasta(PastaDTO pastaDto) throws Exception;

	public Double calcularNota(Integer idBaseConhecimento) throws Exception;

	public Long contarVotos(Integer idBaseConhecimento) throws Exception;

	/**
	 * Verifica se Tipo Item Configuração informada existe.
	 * 
	 * @param grupo
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSeBaseConhecimentoJaPossuiNovaVersao(BaseConhecimentoDTO baseConhecimento) throws Exception;

	/**
	 * Verifica base conhecimento existente
	 * 
	 * @param BaseConhecimentoDTO
	 * @return retorna true se caso exista
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public boolean verificaBaseConhecimentoExistente(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception;

	public List<BaseConhecimentoDTO> validaNota(BaseConhecimentoDTO baseconhecimento) throws Exception;

	/**
	 * Retorna uma lista com algumas informações da baseconhecimento
	 * 
	 * @param baseConhecimento
	 * @return
	 * @throws Exception
	 */
	public Collection<BaseConhecimentoDTO> listaBaseConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception;

	/**
	 * Verifica se Base de Conhcimento informada possui versão anterior;
	 * 
	 * @param baseConhecimento
	 * @return true - existe; false - não existe;
	 * @throws Exception
	 */
	public boolean verificarSeBaseConhecimentoPossuiVersaoAnterior(BaseConhecimentoDTO baseConhecimento) throws Exception;

	/**
	 * Retonar lista de ultimas versão do sistema
	 * 
	 * @param baseConhecimento
	 * @return
	 * @throws Exception
	 */
	public Collection<BaseConhecimentoDTO> listaBaseConhecimentoUltimasVersoes(BaseConhecimentoDTO baseConhecimento) throws Exception;

	/**
	 * Cria Importância Conhecimento Usuário.
	 * 
	 * @param baseConhecimentoDto
	 * @param transactionControler
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void criarImportanciaConhecimentoUsuario(BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws ServiceException, Exception;

	/**
	 * Cria Importância Conhecimento Grupo.
	 * 
	 * @param baseConhecimentoDto
	 * @param transactionControler
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void criarImportanciaConhecimentoGrupo(BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws Exception;

	/**
	 * Cria Relacionamento em Conhecimentos.
	 * 
	 * @param baseConhecimentoDto
	 * @param transactionControler
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void criarRelacionamentoEntreConhecimentos(BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws Exception;

	/**
	 * Cria Notificação
	 * 
	 * @param baseConhecimentoDto
	 * @param transactionControler
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public NotificacaoDTO criarNotificacao(BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws ServiceException, Exception;

	/**
	 * Lista Base de Conhecimento do Tipo FAQ da Pasta informada.
	 * 
	 * @param pasta
	 * @return Collection<BaseConhecimentoDTO>
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoFAQByPasta(PastaDTO pasta) throws Exception;

	/**
	 * Retorna uma base de conhecimento de acordo com os parametros passados
	 * 
	 * @param baseConhecimento
	 * @return BaseConhecimentoDTO
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public BaseConhecimentoDTO getBaseConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception;

	/**
	 * Retrorna uma lista de base de conhecimento de acordo com os parametros passados
	 * 
	 * @param baseConhecimento
	 * @return
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public Collection<BaseConhecimentoDTO> listPesquisaBaseConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception;

	/**
	 * Obtem BaseConhecimento com Grau de Importância para o Usuário.
	 * 
	 * @param baseConhecimentoDto
	 * @param usuarioDto
	 * @return Integer
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public Integer obterGrauDeImportanciaParaUsuario(BaseConhecimentoDTO baseConhecimentoDto, UsuarioDTO usuarioDto) throws Exception;

	/**
	 * Arquiva Conhecimento da Base de Conhecimento;
	 * 
	 * @param baseConhecimentoDto
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void arquivarConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws Exception;

	/**
	 * Restaura Conhecimento Arquivado da Base de Conhecimento;
	 * 
	 * @param baseConhecimentoDto
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void restaurarConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws Exception;

	/**
	 * Retorna uma lista de base de conhecimento para relatorio
	 * 
	 * @param pasta
	 * @return Collection<BaseConhecimentoDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoByPastaRelatorio(PastaDTO pasta) throws Exception;
	
	/**
	 * Retorna Lista de Versões Anteriores da Base de Conhecimento informada.
	 * 
	 * @param baseConhecimento
	 * @return Collection<BaseConhecimentoDTO>
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public Collection<BaseConhecimentoDTO> obterHistoricoDeVersoes(BaseConhecimentoDTO baseConhecimento) throws Exception;
	
	public void gravarSolicitacoesConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception;
	
	public void gravarProblemasConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception;
	
	public void gravarMudancaConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception;
	
	public void gravarLiberacaoConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception;
	
	public void gravarICConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception;
	
	public void criarRelacionamentoEntreEventoMonitConhecimento(BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws ServiceException, Exception;
	
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoByIds(Integer[] ids) throws Exception;
	
	public void updateNotNull(IDto obj) throws Exception;
	
	/**
	 * 
	 * @param baseConhecimentoDTO
	 * @return Collection<BaseconhecimentoDTO>
	 * @throws Exception
	 * @author rodrigo.oliveira 1.0
	 */
	public Collection<BaseConhecimentoDTO> quantidadeBaseConhecimentoPorPeriodo(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception;
	
	/**
	 * 
	 * @param baseConhecimentoDTO
	 * @return Collection<ComentariosDTO>
	 * @throws Exception
	 * @author rodrigo.oliveira 1.0
	 */
	public Collection<ComentariosDTO> consultaConhecimentosAvaliados(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception;
	
	/**
	 * 
	 * @param baseConhecimentoDTO
	 * @return Collection<BaseconhecimentoDTO>
	 * @throws Exception
	 * @author rodrigo.oliveira 1.0
	 */
	public Collection<BaseConhecimentoDTO> consultaConhecimentosPorAutores(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception;
	
	/**
	 * 
	 * @param baseConhecimentoDTO
	 * @return Collection<BaseconhecimentoDTO>
	 * @throws Exception
	 * @author rodrigo.oliveira 1.0
	 */
	public Collection<BaseConhecimentoDTO> consultaConhecimentosPorAprovadores(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception;
	
	/**
	 * 
	 * @param baseConhecimentoDTO
	 * @return Collection<BaseconhecimentoDTO>
	 * @throws Exception
	 * @author rodrigo.oliveira 1.0
	 */
	public Collection<BaseConhecimentoDTO> consultaConhecimentosPublicadosPorOrigem(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception;
	
	/**
	 * 
	 * @param baseConhecimentoDTO
	 * @return Collection<BaseconhecimentoDTO>
	 * @throws Exception
	 * @author rodrigo.oliveira 1.0
	 */
	public Collection<BaseConhecimentoDTO> consultaConhecimentosNaoPublicadosPorOrigem(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception;
	
	/**
	 * Lista Base de Conhecimento do Tipo Erro Conhecido da pasta informada.
	 * 
	 * @param pasta
	 * @return Collection<BaseConhecimentoDTO>
	 * @throws Exception
	 * @author Thays
	 */
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoErroConhecidoByPasta(PastaDTO pasta) throws Exception;

	/**
	 * 
	 * @param baseConhecimentoDTO
	 * @return Collection<BaseconhecimentoDTO>
	 * @throws Exception
	 * @author rodrigo.oliveira 1.0
	 */
	public Collection<BaseConhecimentoDTO> consultaConhecimentoQuantitativoEmLista(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception;
	
	/**
	 * 
	 * @param baseConhecimentoDTO
	 * @return Collection<BaseconhecimentoDTO>
	 * @throws Exception
	 * @author euler.ramos 1.0
	 */	
	public Collection findByServico(SolicitacaoServicoDTO solicitacaoServicoDto) throws ServiceException, LogicException;
	
	
	/**
	 * Lista simples de base de conhecimento do tipo FAQ
	 * @return Collection<BaseconhecimentoDTO>
	 * @throws Exception
	 * @author Pedro
	 */
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoFAQ() throws Exception;

	public Collection<BaseConhecimentoDTO> listarBasesConhecimentoPublicadas() throws Exception;
	
	String verificaIdScriptOrientacao(HashMap mapFields) throws Exception;
	
	/**
	 * Retorna a lista de base de conhecimento do portal paginada
	 * 
	 * @author thyen.chang
	 * @since 06/02/2015 - OPERAÇÃO USAIN BOLT
	 * @param pageable - Objeto que contém qual página e número de elementos a serem pesquisados
	 * @param isTotalizacao - Se a busca é para buscar o número total de elementos da consulta
	 * @return
	 * @throws PersistenceException
	 */
	public Page<BaseConhecimentoDTO> listarBaseConhecimentoPortal(final Pageable pageable, final boolean isTotalizacao) throws PersistenceException;
	
	/**
	 * Retorna a lista de FAQ do portal paginada
	 * 
	 * @author thyen.chang
	 * @since 06/02/2015 - OPERAÇÃO USAIN BOLT
	 * @param pageable - Objeto que contém qual página e número de elementos a serem pesquisados
	 * @param isTotalizacao - Se a busca é para buscar o número total de elementos da consulta
	 * @return
	 * @throws PersistenceException
	 */
	public Page<BaseConhecimentoDTO> listarBaseConhecimentoFAQPortal(final Pageable pageable, final boolean isTotalizacao) throws PersistenceException;

	/**
	 * Retorna a lista de Base de Conhecimento pesquisada pelo titulo
	 * 
	 * @author thyen.chang
	 * @since 09/02/2015
	 * @param pageable - Objeto que contém qual página e número de elementos a serem pesquisados
	 * @param isTotalizacao - Se a busca é para buscar o número total de elementos da consulta
	 * @param titulo - Título da base de conhecimento a ser pesquisada
	 * @return
	 * @throws PersistenceException
	 */
	public Page<BaseConhecimentoDTO> pesquisaBaseConhecimentoPortal(final Pageable pageable, final boolean isTotalizacao, String titulo) throws PersistenceException;
	
	/**
	 * Retorna a lista de FAQ pesquisada pelo titulo
	 * 
	 * @author thyen.chang
	 * @since 09/02/2015
	 * @param pageable - Objeto que contém qual página e número de elementos a serem pesquisados
	 * @param isTotalizacao - Se a busca é para buscar o número total de elementos da consulta
	 * @param titulo - Título da base de conhecimento a ser pesquisada
	 * @return
	 * @throws PersistenceException
	 */
	public Page<BaseConhecimentoDTO> pesquisaBaseConhecimentoFAQPortal(final Pageable pageable, final boolean isTotalizacao, String titulo) throws PersistenceException;

	
}