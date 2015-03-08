package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.PesquisaSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.RelatorioCausaSolucaoDTO;
import br.com.centralit.citcorpore.bean.RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO;
import br.com.centralit.citcorpore.bean.RelatorioEficaciaTesteDTO;
import br.com.centralit.citcorpore.bean.RelatorioIncidentesNaoResolvidosDTO;
import br.com.centralit.citcorpore.bean.RelatorioKpiProdutividadeDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoRetornoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO;
import br.com.centralit.citcorpore.bean.RelatorioSolicitacaoPorExecutanteDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface SolicitacaoServicoService extends CrudService {

    void deserializaInformacoesComplementares(final SolicitacaoServicoDTO solicitacaoServicoDto, final SolicitacaoServicoQuestionarioDTO solQuestionarioDto) throws Exception;

    void encerra(final SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

    /**
     * Retorna Solicitações de Serviço associados ao conhecimento informado.
     *
     * @param baseConhecimentoDto
     * @return Collection
     * @throws ServiceException
     * @throws LogicException
     * @author Vadoilo Damasceno
     */
    Collection findByConhecimento(final BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, LogicException;

    Collection findByIdSolictacaoServico(final Integer parm) throws ServiceException, LogicException;

    Collection<SolicitacaoServicoDTO> findByServico(final Integer idServico) throws Exception;

    Collection<SolicitacaoServicoDTO> findSolicitacoesNaoResolvidasNoPrazoKPI(final RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDTO) throws Exception;

    Collection<SolicitacaoServicoDTO> findByServico(final Integer idServico, final String nome) throws Exception;

    List<SolicitacaoServicoDTO> findSolicitacoesServicosUsuario(final Integer idUsuario, final Integer idItemConfiguracao);

    boolean hasSolicitacoesServicosUsuario(final Integer idUsuario, final String status) throws Exception;

    List<SolicitacaoServicoDTO> findSolicitacoesServicosUsuario(final Integer idUsuario, final String status, final String campoBusca) throws Exception;

    Collection<SolicitacaoServicoDTO> getHistoricoByIdSolicitacao(final Integer idSolicitacao) throws Exception;

    ItemTrabalho getItemTrabalho(final Integer idItemTrabalho) throws Exception;

    Integer getQuantidadeByIdServico(final int idServico) throws Exception;

    Integer getQuantidadeByIdServicoContrato(final int idServicoContrato) throws Exception;

    String getUrlInformacoesComplementares(final SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

    void gravaInformacoesGED(final Collection colArquivosUpload, final int idEmpresa, final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc)
            throws Exception;

    Collection<SolicitacaoServicoDTO> listAll() throws Exception;

    Collection<SolicitacaoServicoDTO> listAllIncidentes(final Integer idUsuario) throws Exception;

    Collection<SolicitacaoServicoDTO> listAllServicos() throws Exception;

    Collection<SolicitacaoServicoDTO> listAllServicosLikeNomeServico(final String nome) throws Exception;

    /**
     * Metodo retornar uma lista com a quantidade de solicitação por fase
     *
     * @param solicitacaoDto
     * @return
     * @throws Exception
     */
    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorFase(final SolicitacaoServicoDTO solicitacaoDto) throws Exception;

    /**
     * Metodo retornar uma lista com a quantidade de solicitação por grupoSolucionador.
     *
     * @return
     * @throws Exception
     * @author Thays.araujo
     */
    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorGrupo(final HttpServletRequest request, final SolicitacaoServicoDTO solicitacaoDto)
            throws Exception;

    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorHoraAbertura(final SolicitacaoServicoDTO solicitacaoDto) throws Exception;

    /**
     * Metodo retornar uma lista com a quantidade de solicitação por Item Configuração.
     *
     * @return
     * @throws Exception
     * @author Thays.araujo
     */
    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorItemConfiguracao(final SolicitacaoServicoDTO solicitacaoDto) throws Exception;

    /**
     * Metodo retornar uma lista com a quantidade de solicitação por Origem
     *
     * @param solicitacaoDto
     * @return
     * @throws Exception
     */
    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorOrigem(final SolicitacaoServicoDTO solicitacaoDto) throws Exception;

    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorPesquisaSatisfacao(final HttpServletRequest request, final SolicitacaoServicoDTO solicitacaoDto)
            throws Exception;

    /**
     * Metodo retornar uma lista com a quantidade de solicitação por prioridade
     *
     * @param solicitacaoDto
     * @return
     * @throws Exception
     */
    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorPrioridade(final SolicitacaoServicoDTO solicitacaoDto) throws Exception;

    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorResponsavel(final SolicitacaoServicoDTO solicitacaoDto) throws Exception;

    /**
     * Metodo retornar uma lista com a quantidade de solicitação por serviço
     *
     * @param solicitacaoDto
     * @return
     * @throws Exception
     */
    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorServico(final SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

    /**
     * Metodo retornar os serviços aprovados e abertos
     *
     * @param solicitacaoDto
     * @return
     * @throws Exception
     */
    Collection<RelatorioQuantitativoSolicitacaoDTO> listaServicosAbertosAprovados(final SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

    /**
     * Metodo retornar uma lista com a quantidade de solicitação por situação
     *
     * @param solicitacaoDto
     * @return
     * @throws Exception
     */
    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSituacao(final SolicitacaoServicoDTO solicitacaoDto) throws Exception;

    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSituacaoSLA(final HttpServletRequest request, final SolicitacaoServicoDTO solicitacaoDto)
            throws Exception;

    /**
     * Metodo retornar uma lista com a quantidade de solicitação por solicitante.
     *
     * @return
     * @throws Exception
     * @author Thays.araujo
     */
    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSolicitante(final SolicitacaoServicoDTO solicitacaoDto) throws Exception;

    /**
     * Metodo retornar uma lista com a quantidade de solicitação por tipo
     *
     * @param solicitacaoDto
     * @return
     * @throws Exception
     */
    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorTipo(final SolicitacaoServicoDTO solicitacaoDto) throws Exception;

    Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorTipoServico(final SolicitacaoServicoDTO solicitacaoDto) throws Exception;

    String listaServico(final Integer idSolicitacaoservico) throws PersistenceException, Exception;

    /**
     * Retorna uma lista de idSolicitacaoServico
     *
     * @param solicitacao
     * @return
     * @throws Exception
     */
    Collection<SolicitacaoServicoDTO> listaSolicitacaoPorBaseConhecimento(final SolicitacaoServicoDTO solicitacao) throws Exception;

    /**
     * Retorna Solicitações serviço de acordo com os criterios passados
     *
     * @param pesquisaSolicitacaoServicoDto
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios(final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto) throws Exception;

    Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriteriosPaginado(final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto, final String paginacao,
            final Integer pagAtual, final Integer pagAtualAux, final Integer totalPag, final Integer quantidadePaginator, final String campoPesquisa) throws Exception;

    Collection listaSolicitacoesSemPesquisaSatisfacao() throws Exception;

    SolicitacaoServicoDTO listIdentificacao(final Integer idItemConfiguracao) throws Exception;

    Collection<SolicitacaoServicoDTO> listIncidentesNaoFinalizados() throws Exception;

    SolicitacaoServicoDTO listInformacaoContato(final String nomeContato) throws Exception;;

    /**
     * Retorna Solicitaï¿½ï¿½es de Serviços de acordo com o Tipo de Demanda e Usuï¿½rio.
     *
     * @param tipoDemandaServico
     * @param grupoSeguranca
     * @param usuario
     * @return
     * @throws Exception
     */
    Collection<SolicitacaoServicoDTO> listSolicitacaoServico(final String tipoDemandaServico, final GrupoDTO grupoSeguranca, final UsuarioDTO usuario, final Date dataInicio,
            final Date dataFim, final String situacao) throws Exception;

    Collection<SolicitacaoServicoDTO> listSolicitacaoServicoByCriterios(final Collection colCriterios) throws Exception;

    List<SolicitacaoServicoDTO> listSolicitacaoServicoByItemConfiguracao(final Integer idItemConfiguracao) throws Exception;

    Collection<SolicitacaoServicoDTO> listSolicitacaoServicoEmAndamento(final Integer idSolicitacaoServico);

    Collection<SolicitacaoServicoDTO> listSolicitacaoServicoNaoFinalizadas() throws Exception;

    Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionada(final int idSolicitacaoPai);

    Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionadaPai(final int idSolicitacaoPai);

    Collection<SolicitacaoServicoDTO> listSolicitacoesFilhas() throws Exception;

    SolicitacaoServicoDTO obterQuantidadeSolicitacoesServico(final Integer idServicoContrato, final java.util.Date dataInicio, final java.util.Date dataFim) throws Exception;

    /**
     * Retorna quantidade
     *
     * @param solicitacao
     * @return Integer
     * @throws Exception
     * @author Thays
     */
    Collection<SolicitacaoServicoDTO> quantidadeSolicitacaoPorBaseConhecimento(final SolicitacaoServicoDTO solicitacao) throws Exception;

    void reabre(final UsuarioDTO usuarioDto, final SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

    void reativa(final UsuarioDTO usuarioDto, final SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

    FluxoDTO recuperaFluxo(final SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

    Collection<SolicitacaoServicoDTO> relatorioControleSla(final SolicitacaoServicoDTO solicitacao) throws Exception;

    SolicitacaoServicoDTO restoreAll(final Integer idSolicitacaoServico) throws Exception;

    /**
     * Retorna SolicitacaoServico com Item de Configuraï¿½ï¿½o do Solicitante.
     *
     * @param login
     * @return SolicitacaoServicoDTO
     * @throws Exception
     */
    SolicitacaoServicoDTO retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(final String login) throws Exception;

    void suspende(final UsuarioDTO usuarioDto, final SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

    boolean temSolicitacaoServicoAbertaDoEmpregado(final Integer idEmpregado);

    IDto updateInfo(final IDto model) throws ServiceException, LogicException;

    IDto updateInfoCollection(final IDto model) throws ServiceException, LogicException;

    void updateNotNull(final IDto obj) throws Exception;

    void updateSLA(final IDto model) throws ServiceException, LogicException;

    void updateSolicitacaoPai(final int idSolicitacaoPai, final int idSolicitacao);

    /**
     * Retornar verdadeiro ou falso
     *
     * @param idUnidade
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    boolean verificarExistenciaDeUnidade(final Integer idUnidade) throws Exception;

    /**
     * Verifica se solicitação serviço possui Solicitação Filho.
     *
     * @param idSolicitacaoServico
     * @return true = possui; false = não possui.
     * @throws Exception
     */
    boolean verificarExistenciaSolicitacaoFilho(final Integer idSolicitacaoServico) throws Exception;

    SolicitacaoServicoDTO verificaSituacaoSLA(final SolicitacaoServicoDTO solicitacaoDto) throws Exception;

    Collection incidentesPorContrato(final Integer idContrato) throws Exception;

    Collection<SolicitacaoServicoDTO> listarSLA() throws Exception;

    Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorServicoContrato(final Integer idServicoContratoContabil) throws Exception;

    String calculaSLA(final SolicitacaoServicoDTO solicitacaoServicoDto, final HttpServletRequest request) throws Exception;

    SolicitacaoServicoDTO findByIdSolicitacaoServico(final Integer idSolicitacaoServico) throws Exception;

    /**
     * Retorna uma lista de solicitacao serviço de acordo com os parametro passados com o principal objetivo de trazer somente solicitações fechadas ou canceladas.
     *
     * @param relatorioSolicitacaoPorSolucionarDto
     * @return Collection
     * @throws Exception
     * @author thays.araujo
     */
    Collection<RelatorioSolicitacaoPorExecutanteDTO> listaSolicitacaoPorExecutante(final RelatorioSolicitacaoPorExecutanteDTO relatorioSolicitacaoPorExecutanteDto)
            throws Exception;

    Collection<RelatorioCausaSolucaoDTO> listaCausaSolicitacao(final RelatorioCausaSolucaoDTO relatorioCausaSolicitacao) throws Exception;

    Collection<RelatorioCausaSolucaoDTO> listaSolucaoSolicitacao(final RelatorioCausaSolucaoDTO relatorioCausaSolicitacao) throws Exception;

    Collection<RelatorioCausaSolucaoDTO> listaCausaSolucaoAnalitico(final RelatorioCausaSolucaoDTO relatorioCausaSolicitacao) throws Exception;

    /**
     * Retorna uma lista de Serviços que estejam associada a uma solicitação serviço.
     *
     * @param relatorioAnaliseServicoDto
     * @return Collection<RelatorioAnaliseServicoDTO>
     * @throws Exception
     * @author thays.araujo
     */
    Collection<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO> listaServicoPorSolicitacaoServico(
            final RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO relatorioAnaliseServicoDto) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdGrupo(final Integer idGrupo) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdGrupoEDataBaixa(final Integer idGrupo, final Date dataInicio, final Date dataFim) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdGrupoEDataMedia(final Integer idGrupo, final Date dataInicio, final Date dataFim) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAlta(final Integer idGrupo, final Date dataInicio, final Date dataFim) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdGrupoEDataTotal(final Integer idGrupo, final Date dataInicio, final Date dataFim) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasBaixa(final Integer idGrupo, final Date dataInicio, final Date dataFim) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasMedia(final Integer idGrupo, final Date dataInicio, final Date dataFim) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasAlta(final Integer idGrupo, final Date dataInicio, final Date dataFim) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdGrupoEDataSuspensasTotal(final Integer idGrupo, final Date dataInicio, final Date dataFim) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdPessoaEDataAtendidas(final Integer idGrupo, final String login, final String nome, final Date dataInicio, final Date dataFim)
            throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdPessoaEData(final Integer idGrupo, final String login, final String nome, final Date dataInicio, final Date dataFim) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdPessoaEDataNaoAtendidas(final Integer idGrupo, final Date dataInicio, final Date dataFim) throws Exception;

    Collection<RelatorioQuantitativoRetornoDTO> listaServicosRetorno(final SolicitacaoServicoDTO solicitacaoServicoDTO, final String grupoRetorno) throws Exception;

    Collection<RelatorioQuantitativoRetornoDTO> listaServicosRetornoNomeResponsavel(final RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception;

    SolicitacaoServicoDTO listaIdItemTrabalho(final Integer idInstancia) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasTotal(final Integer idGrupo, final Date dataInicio, final Date dataFim) throws Exception;

    Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtrasadasTotal(final Integer idGrupo, final Date dataInicio, final Date dataFim) throws Exception;

    IDto create(final IDto model, final TransactionControler tc, final boolean determinaPrioridadePrazo, final boolean determinaHoraInicio,
            final boolean determinaDataHoraSolicitacao) throws Exception;

    Collection<SolicitacaoServicoDTO> listaSolicitacoesPorIdEmpregado(final Integer pgAtual, final Integer qtdPaginacao, final GerenciamentoServicosDTO gerenciamentoBean,
            final Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception;

    Collection<SolicitacaoServicoDTO> listaSolicitacoesPorIdEmpregado(final Integer pgAtual, final Integer qtdPaginacao, final GerenciamentoServicosDTO gerenciamentoBean,
            final Collection<ContratoDTO> listContratoUsuarioLogado, final String[] filtro) throws Exception;

    Collection<TipoDemandaServicoDTO> resumoTipoDemandaServico(final List<TarefaFluxoDTO> listTarefas) throws Exception;

    RelatorioQuantitativoRetornoDTO servicoRetorno(final RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception;

    boolean validaQuantidadeRetorno(final RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception;

    RelatorioQuantitativoRetornoDTO retornarIdEncerramento(final String encerramento, final RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception;

    /**
     * @param solicitacaoServicoDTO
     * @return Retorna Solicitacao com porcentagem de sla no prazo fora do prazo com referencia a prioridade
     * @throws Exception
     * @author maycon.fernandes
     */
    SolicitacaoServicoDTO relatorioControlePercentualQuantitativoSla(final SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception;

    boolean confirmaEncerramento(final RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO, final Integer idElemento) throws Exception;

    Collection<SolicitacaoServicoDTO> findByCodigoExterno(final String codigoExterno) throws Exception;

    SolicitacaoServicoDTO restoreByIdInstanciaFluxo(final Integer idInstanciaFluxo) throws Exception;

    Collection<SolicitacaoServicoDTO> listByTarefas(final Collection<TarefaFluxoDTO> listTarefas, final TipoSolicitacaoServico[] tiposSolicitacao) throws Exception;

    Collection<PrioridadeDTO> resumoPrioridade(final List<TarefaFluxoDTO> listTarefas) throws Exception;

    HashMap resumoPorPrazoLimite(final Collection<TarefaFluxoDTO> listTarefas) throws Exception;

    ComplemInfSolicitacaoServicoService getInformacoesComplementaresService(final ItemTrabalho itemTrabalho) throws Exception;

    boolean existeSolicitacaoServico(final SolicitacaoServicoDTO solicitacaoservico) throws Exception;

    boolean permissaoGrupoExecutorServico(final int idGrupoExecutor, final int idTipoFluxoSolicitacaoServico) throws Exception;

    Collection<SolicitacaoServicoDTO> listarSolicitacoesAbertasEmAndamentoPorGrupo(final int idGrupoAtual, final String situacaoSla) throws Exception;

    Collection<SolicitacaoServicoDTO> listarSolicitacoesMultadasSuspensasPorGrupo(final int idGrupoAtual, final String situacaoSla) throws Exception;

    Collection<SolicitacaoServicoDTO> listaServicosPorResponsavelNoPeriodo(final Date dataIncio, final Date dataFim, final int idFuncionario, final boolean mostrarIncidentes,
            final boolean mostrarRequisicoes, final String situacao) throws Exception;

    Collection<SolicitacaoServicoDTO> listaServicosPorSolicitanteNoPeriodoEnviadosAoteste(final Date dataIncio, final Date dataFim, final int idFuncionario,
            final boolean mostrarIncidentes, final boolean mostrarRequisicoes) throws Exception;

    Collection<SolicitacaoServicoDTO> listaServicosPorAbertosParaDocumentacao(final Date dataIncio, final Date dataFim, final boolean mostrarIncidentes,
            final boolean mostrarRequisicoes) throws Exception;

    Collection<SolicitacaoServicoDTO> listaServicosPorResponsavelNoPeriodoDocumentacao(final Date dataIncio, final Date dataFim, final int idFuncionario,
            final boolean mostrarIncidentes, final boolean mostrarRequisicoes) throws Exception;

    Collection<SolicitacaoServicoDTO> listaServicosPorResponsavelNoPeriodoDocumentacaoPorServico(final Date dataIncio, final Date dataFim, final int idFuncionario,
            final boolean mostrarIncidentes, final boolean mostrarRequisicoes, final String listaIdsServicosHomologacaoDocumentacao) throws Exception;

    Collection<SolicitacaoServicoDTO> listaServicosPorAbertosPelotesteParaValidacao(final Date dataIncio, final Date dataFim, final boolean mostrarIncidentes,
            final boolean mostrarRequisicoes) throws Exception;

    SolicitacaoServicoDTO buscarNumeroItemTrabalhoPorNumeroSolicitacao(final int idSolicitacao) throws Exception;

    Collection<RelatorioEficaciaTesteDTO> listaSolicitacaoPorServicosAbertosNoPerido(final Date dataIncio, final Date dataFim, final List<ServicoDTO> listaServicos)
            throws Exception;

    Collection<RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO> listaQtdSolicitacoesCanceladasFinalizadasporServicoNoPeriodo(final Date dataIncio,
            final Date dataFim, final List<ServicoDTO> listaServicos) throws Exception;

    /**
     * lista com os quantitativos por empregado de solicitações serviços emcaminhadas e foram concluidas com exito.
     *
     * @param relatorioKpiProdutividadeDto
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    Collection<RelatorioKpiProdutividadeDTO> listaQuantitativaEmpregadoSolicitacoesEmcaminhaExito(final RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDto) throws Exception;

    Collection<SolicitacaoServicoDTO> findSolicitacoesNaoResolvidasEntrePrazoKPI(final RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDTO) throws Exception;

    boolean verificaPermGestorSolicitanteRH(final Integer idSolicitante) throws PersistenceException;

    Collection<SolicitacaoServicoDTO> listSolicitacoesFilhasFiltradas(final GerenciamentoServicosDTO gerenciamentoBean, final Collection<ContratoDTO> listContratoUsuarioLogado)
            throws Exception;

    Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriteriosPaginado(final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto, final String paginacao,
            final Integer pagAtual, final Integer pagAtualAux, final Integer totalPag, final Integer quantidadePaginator, final String campoPesquisa,
            final Collection<UnidadeDTO> unidadesColaborador) throws Exception;

    /**
     * Método para listar número de solicitações fora do período fornecido pelo usuário
     *
     * @param relatorioIncidentesNaoResolvidosDTO
     * @return
     * @throws PersistenceException
     * @throws ServiceException
     * @author thyen.chang
     * @throws LogicException
     * @throws Exception
     */
    Integer numeroSolicitacoesForaPeriodo(final RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDTO) throws PersistenceException, ServiceException;

    /**
     * @author thyen.chang
     */
    Long listaRelatorioGetQuantidadeRegistros(final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto) throws ParseException, PersistenceException, ServiceException,
            LogicException, Exception;

    List<SolicitacaoServicoDTO> listRelatorioGetListaPaginada(final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDTO, final Integer paginaAtual,
            final Integer quantidadePorPagina) throws Exception;

    void determinaPrazoLimite(final SolicitacaoServicoDTO solicitacao, final Integer idCalendario, final TransactionControler tc) throws Exception;

	IDto create(IDto model) throws ServiceException, LogicException;
}
