package br.com.centralit.citcorpore.bpm.negocio;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.AtribuicaoFluxoDTO;
import br.com.centralit.bpm.dto.ElementoFluxoTarefaDTO;
import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.dto.ObjetoNegocioFluxoDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.dto.UsuarioBpmDTO;
import br.com.centralit.bpm.integracao.AtribuicaoFluxoDao;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.bpm.integracao.ItemTrabalhoFluxoDao;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.bpm.negocio.ExecucaoFluxo;
import br.com.centralit.bpm.negocio.InstanciaFluxo;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.bpm.negocio.UsuarioGrupo;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.AlcadaProcessoNegocioDTO;
import br.com.centralit.citcorpore.bean.CalculoJornadaDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ExecucaoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.HistoricoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ParamRecuperacaoTarefasDTO;
import br.com.centralit.citcorpore.bean.ReaberturaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TarefaUsuarioDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TempoAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.AcordoServicoContratoDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.ExecucaoSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.FluxoServicoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.ModeloEmailDao;
import br.com.centralit.citcorpore.integracao.OcorrenciaSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.PermissoesFluxoDao;
import br.com.centralit.citcorpore.integracao.ReaberturaSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.TarefaUsuarioDao;
import br.com.centralit.citcorpore.integracao.TemplateSolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.TempoAcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.CalendarioServiceEjb;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.HistoricoSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoServiceEjb;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.PageImpl;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ExecucaoSolicitacao extends ExecucaoFluxo {

	protected UsuarioDTO usuarioDTO = null;
	protected SolicitacaoServicoService solicitacaoServicoService;
	protected ExecucaoSolicitacaoDTO execucaoSolicitacaoDto;
	private String reabre = "";

	protected Double valorMensalUsoInterno = 0.0;
	protected Double valorAnualUsoInterno = 0.0;
	protected Double valorMensalAtendCliente = 0.0;
	protected Double valorAnualAtendCliente = 0.0;

	public Double getValorMensal() {
		return valorMensalUsoInterno + valorMensalAtendCliente;
	}

	public Double getValorAnual() {
		return valorAnualUsoInterno + valorAnualAtendCliente;
	}

	public Double getValorMensalUsoInterno() {
		return valorMensalUsoInterno;
	}

	public Double getValorAnualUsoInterno() {
		return valorAnualUsoInterno;
	}

	public Double getValorMensalAtendCliente() {
		return valorMensalAtendCliente;
	}

	public Double getValorAnualAtendCliente() {
		return valorAnualAtendCliente;
	}

	public String i18n_Message(UsuarioDTO usuario, String key) {
		if (usuario != null) {
			if (UtilI18N.internacionaliza(usuario.getLocale(), key) != null) {
				return UtilI18N.internacionaliza(usuario.getLocale(), key);
			}
			return key;
		}
		return key;
	}

	public ExecucaoSolicitacao(TransactionControler tc) {
		super(tc);
	}

	public ExecucaoSolicitacao() {
		super();
	}

	public ExecucaoSolicitacao(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) {
		super(solicitacaoServicoDto, tc);
	}

	@Override
	public InstanciaFluxo inicia(String nomeFluxo, Integer idFase) throws Exception {
		TipoFluxoDao tipoFluxoDao = new TipoFluxoDao();
		TipoFluxoDTO tipoFluxoDto = tipoFluxoDao.findByNome(nomeFluxo);
		if (tipoFluxoDto == null)
			throw new Exception("Fluxo " + nomeFluxo + " não existe");

		return inicia(new FluxoDao().findByTipoFluxo(tipoFluxoDto.getIdTipoFluxo()), idFase);
	}

	@Override
	public InstanciaFluxo inicia() throws Exception {
		InstanciaFluxo result = null;
		FluxoServicoDTO fluxoServicoDto = new FluxoServicoDao().findPrincipalByIdServicoContrato(getSolicitacaoServicoDto().getIdServicoContrato());
		if (fluxoServicoDto != null) {
			result = inicia(new FluxoDao().findByTipoFluxo(fluxoServicoDto.getIdTipoFluxo()), null);
		} else {
			String fluxoPadrao = ParametroUtil.getValor(ParametroSistema.NomeFluxoPadraoServicos, getTransacao(), null);
			if (fluxoPadrao == null)
				throw new Exception("Fluxo padrão não parametrizado");
			String idFaseStr = ParametroUtil.getValor(ParametroSistema.IDFaseExecucaoServicos, getTransacao(), null);
			if (idFaseStr == null)
				throw new Exception("Fase padrão de execução não encontrada");
			result = inicia(fluxoPadrao, new Integer(idFaseStr));
		}

		try {
			String enviarNotificacao = ParametroUtil.getValor(ParametroSistema.NOTIFICAR_GRUPO_RECEPCAO_SOLICITACAO, getTransacao(), "N");

			String enviarNotificacaoSolicitacoesVinculadas = ParametroUtil.getValor(ParametroSistema.RECEBER_NOTIFICACAO_ENCERRAR_ESCALONAR_SOLICITACOES_VINCULADAS, getTransacao(), "S");

			if (enviarNotificacao.equalsIgnoreCase("S") && getSolicitacaoServicoDto().escalada()) {

				if (enviarNotificacaoSolicitacoesVinculadas.equalsIgnoreCase("S")) {
					if (getSolicitacaoServicoDto().getIdSolicitacaoRelacionada() != null) {
						SolicitacaoServicoDTO solicitacaoServicoRelacionada = new SolicitacaoServicoDTO();
						solicitacaoServicoRelacionada.setIdSolicitacaoServico(getSolicitacaoServicoDto().getIdSolicitacaoRelacionada());
						solicitacaoServicoRelacionada = (SolicitacaoServicoDTO) getSolicitacaoServicoDAO().restore(solicitacaoServicoRelacionada);

						String idModeloEmailCriacao = ParametroUtil.getValor(ParametroSistema.ID_MODELO_EMAIL_CRIACAO_GRUPO_EXECUTOR_SOLICITACAO_RELACIONADA, getTransacao(), "84");
						if (idModeloEmailCriacao != null) {
							enviaEmailGrupo(Integer.parseInt(idModeloEmailCriacao), solicitacaoServicoRelacionada.getIdGrupoAtual());
						} else {
							throw new Exception("Não há modelo de e-mail setado nos parâmetros.");
						}
					}
				}
				enviaEmailGrupo(Integer.parseInt(ParametroUtil.getValor(ParametroSistema.ID_MODELO_EMAIL_GRUPO_DESTINO, getTransacao(), null)), getSolicitacaoServicoDto().getIdGrupoAtual());
			}
		} catch (NumberFormatException e) {
			System.out.println("Não há modelo de e-mail setado nos parâmetros.");
		}
		return result;
	}

	@Override
	public InstanciaFluxo inicia(FluxoDTO fluxoDto, Integer idFase) throws Exception {
		if (fluxoDto == null)
			throw new Exception("Fluxo não encontrado");

		Integer idFaseFluxo = idFase;
		this.fluxoDto = fluxoDto;

		if (idFaseFluxo == null) {
			FluxoServicoDTO fluxoServicoDto = new FluxoServicoDTO();
			FluxoServicoDao fluxoServicoDao = new FluxoServicoDao();
			fluxoServicoDto = fluxoServicoDao.findByIdServicoContratoAndIdTipoFluxo(getSolicitacaoServicoDto().getIdServicoContrato(), fluxoDto.getIdTipoFluxo());
			if (fluxoServicoDto == null)
				throw new Exception("Fluxo " + fluxoDto.getNomeFluxo() + " não está associado a este tipo de solicitação");
			idFaseFluxo = fluxoServicoDto.getIdFase();
		}

		atualizaFaseSolicitacao(idFaseFluxo);

		AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
		AcordoNivelServicoDTO acordoNivelServicoDto = new AcordoNivelServicoDTO();
		setTransacaoDao(acordoNivelServicoDao);
		acordoNivelServicoDto = acordoNivelServicoDao.findAtivoByIdServicoContrato(getSolicitacaoServicoDto().getIdServicoContrato(), "T");
		if (acordoNivelServicoDto == null) {
			if (reabre == "S") {
				AcordoNivelServicoDTO acordoNivelServicoAux = acordoNivelServicoDao.findByIdAcordoNivelServicoEServicoContrato(getSolicitacaoServicoDto().getIdAcordoNivelServico(),
						getSolicitacaoServicoDto().getIdServicoContrato());
				if (acordoNivelServicoAux != null) {
					acordoNivelServicoDto = acordoNivelServicoAux;
				}
			}
			if (acordoNivelServicoDto == null) {
				// Se nao houver acordo especifico, ou seja, associado direto ao
				// servicocontrato, entao busca um acordo geral que esteja vinculado
				// ao servicocontrato.
				AcordoServicoContratoDTO acordoServicoContratoDTO = new AcordoServicoContratoDao().findAtivoByIdServicoContrato(getSolicitacaoServicoDto().getIdServicoContrato(), "T");
				if (acordoServicoContratoDTO == null) {
					throw new Exception("solicitacaoservico.validacao.tempoacordo");
				}
				// Apos achar a vinculacao do acordo com o servicocontrato, entao
				// faz um restore do acordo de nivel de servico.
				acordoNivelServicoDto = new AcordoNivelServicoDTO();
				acordoNivelServicoDto.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
				acordoNivelServicoDto = (AcordoNivelServicoDTO) acordoNivelServicoDao.restore(acordoNivelServicoDto);
				if (acordoNivelServicoDto == null) {
					// Se nao houver acordo especifico, ou seja, associado direto ao
					// servicocontrato
					throw new Exception("solicitacaoservico.validacao.tempoacordo");
				}
			}
		}

		TempoAcordoNivelServicoDTO tempoDto = new TempoAcordoNivelServicoDTO();
		TempoAcordoNivelServicoDao tempoDao = new TempoAcordoNivelServicoDao();
		setTransacaoDao(tempoDao);
		tempoDto.setIdAcordoNivelServico(acordoNivelServicoDto.getIdAcordoNivelServico());
		tempoDto.setIdPrioridade(getSolicitacaoServicoDto().getIdPrioridade());
		tempoDto.setIdFase(getSolicitacaoServicoDto().getIdFaseAtual());
		tempoDto = (TempoAcordoNivelServicoDTO) tempoDao.restore(tempoDto);
		if (tempoDto == null)
			throw new Exception("Não existem prazos de atendimento associados ao serviço/prioridade desta solicitação");

		HashMap<String, Object> map = new HashMap();
		mapObjetoNegocio(map);
		InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, map);

		ExecucaoSolicitacaoDTO execucaoDto = new ExecucaoSolicitacaoDTO();
		execucaoDto.setIdSolicitacaoServico(getSolicitacaoServicoDto().getIdSolicitacaoServico());
		execucaoDto.setIdFase(getSolicitacaoServicoDto().getIdFaseAtual());
		execucaoDto.setIdFluxo(instanciaFluxo.getIdFluxo());
		execucaoDto.setIdInstanciaFluxo(instanciaFluxo.getIdInstancia());
		Integer seqReabertura = 0;
		if (getSolicitacaoServicoDto().getSeqReabertura() != null && getSolicitacaoServicoDto().getSeqReabertura().intValue() > 0)
			seqReabertura = getSolicitacaoServicoDto().getSeqReabertura();
		if (seqReabertura.intValue() > 0)
			execucaoDto.setSeqReabertura(getSolicitacaoServicoDto().getSeqReabertura());

		execucaoDto.setPrazoHH(tempoDto.getTempoHH());
		execucaoDto.setPrazoMM(tempoDto.getTempoMM());

		ExecucaoSolicitacaoDao execucaoDao = new ExecucaoSolicitacaoDao();
		setTransacaoDao(execucaoDao);
		execucaoFluxoDto = (ExecucaoSolicitacaoDTO) execucaoDao.create(execucaoDto);

		if (seqReabertura.intValue() == 0 && getSolicitacaoServicoDto().getEnviaEmailCriacao() != null && getSolicitacaoServicoDto().getEnviaEmailCriacao().equalsIgnoreCase("S")) {
			ServicoContratoDTO servicoContratoDto = recuperaServicoContrato();
			enviaEmail(servicoContratoDto.getIdModeloEmailCriacao());
		}
		reabre = "";
		return instanciaFluxo;
	}

	@Override
	public void executa(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, Integer idItemTrabalho, String acao, HashMap<String, Object> map) throws Exception {
		if (acao.equals(Enumerados.ACAO_DELEGAR))
			return;

		TarefaFluxoDTO tarefaFluxoDto = recuperaTarefa(idItemTrabalho);
		if (tarefaFluxoDto == null)
			return;

		OcorrenciaSolicitacaoDTO ocorrenciaSolicitacao = new OcorrenciaSolicitacaoDTO();
		ExecucaoSolicitacaoDao execucaoSolicitacaoDao = new ExecucaoSolicitacaoDao();
		setTransacaoDao(execucaoSolicitacaoDao);
		ExecucaoSolicitacaoDTO execucaoSolicitacaoDto = execucaoSolicitacaoDao.findByIdInstanciaFluxo(tarefaFluxoDto.getIdInstancia());
		if (execucaoSolicitacaoDto == null)
			return;

		recuperaFluxo(execucaoSolicitacaoDto.getIdFluxo());

		this.objetoNegocioDto = objetoNegocioDto;
		getSolicitacaoServicoDto().setIdTarefa(tarefaFluxoDto.getIdItemTrabalho());
		getSolicitacaoServicoDto().setNomeTarefa(tarefaFluxoDto.getElementoFluxoDto().getDocumentacao());

		if (getSolicitacaoServicoDto().getIdGrupoNivel1() == null || getSolicitacaoServicoDto().getIdGrupoNivel1().intValue() == 0)
			throw new LogicException("Grupo nível 1 não informado ou erro na recuperação do atributo");

		usuarioDTO = new UsuarioDTO();
		usuarioDTO = new UsuarioDao().restoreByLogin(loginUsuario);

		InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, tarefaFluxoDto.getIdInstancia());
		mapObjetoNegocio(instanciaFluxo.getObjetos(map));

		if (acao.equals(Enumerados.ACAO_INICIAR)) {
			instanciaFluxo.iniciaItemTrabalho(loginUsuario, tarefaFluxoDto.getIdItemTrabalho(), map);
			ocorrenciaSolicitacao = OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), tarefaFluxoDto, "Execução da tarefa \"" + tarefaFluxoDto.getElementoFluxoDto().getDocumentacao()
					+ "\"", OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Execucao, "não se aplica", CategoriaOcorrencia.Execucao.getDescricao(), usuarioDTO, 0, null, getTransacao());
			popularHistorico(getSolicitacaoServicoDto(), ocorrenciaSolicitacao, "Executa", usuarioDTO);
		} else if (acao.equals(Enumerados.ACAO_EXECUTAR)) {
			instanciaFluxo.executaItemTrabalho(loginUsuario, tarefaFluxoDto.getIdItemTrabalho(), map);

			Integer tempo = 0;
			try {
				Integer idCalendario = getIdCalendario(getSolicitacaoServicoDto());
				CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendario, tarefaFluxoDto.getDataHoraCriacao());
				calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, UtilDatas.getDataHoraAtual(), getTransacao());
				tempo = calculoDto.getTempoDecorridoHH() * 60 + calculoDto.getTempoDecorridoMM();
			} catch (Exception e) {
				System.out.println("#### Erro no cálculo do tempo decorrido da tarefa");
				e.printStackTrace();
			}
			ocorrenciaSolicitacao = OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), tarefaFluxoDto, "Execução da tarefa \"" + tarefaFluxoDto.getElementoFluxoDto().getDocumentacao()
					+ "\"", OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Execucao, "não se aplica", CategoriaOcorrencia.Execucao.getDescricao(), usuarioDTO, tempo.intValue(), null, getTransacao());
			popularHistorico(getSolicitacaoServicoDto(), ocorrenciaSolicitacao, "Executa", usuarioDTO);
		}

		if (getSolicitacaoServicoDto() != null && getSolicitacaoServicoDto().getIdSolicitacaoServico() != null) {
			this.atualizaidResponsalvelAtualSolicitacao();
		}

		if (getSolicitacaoServicoDto().getEnviaEmailAcoes() != null && getSolicitacaoServicoDto().getEnviaEmailAcoes().equalsIgnoreCase("S")) {
			getSolicitacaoServicoDto().setNomeTarefa(tarefaFluxoDto.getElementoFluxoDto().getDocumentacao());
			ServicoContratoDTO servicoContratoDto = recuperaServicoContrato();

			String enviarNotificacaoSolicitacoesVinculadas = ParametroUtil.getValor(ParametroSistema.RECEBER_NOTIFICACAO_ENCERRAR_ESCALONAR_SOLICITACOES_VINCULADAS, getTransacao(), "S");

			if (enviarNotificacaoSolicitacoesVinculadas.equalsIgnoreCase("S")) {
				if (getSolicitacaoServicoDto().getIdSolicitacaoRelacionada() != null) {
					SolicitacaoServicoDTO solicitacaoServicoRelacionada = new SolicitacaoServicoDTO();
					solicitacaoServicoRelacionada.setIdSolicitacaoServico(getSolicitacaoServicoDto().getIdSolicitacaoRelacionada());
					solicitacaoServicoRelacionada = (SolicitacaoServicoDTO) getSolicitacaoServicoDAO().restore(solicitacaoServicoRelacionada);

					String idModeloEmailAcoes = ParametroUtil.getValor(ParametroSistema.ID_MODELO_EMAIL_ACOES_GRUPO_EXECUTOR_SOLICITACAO_RELACIONADA, getTransacao(), "85");
					if (idModeloEmailAcoes != null) {
						enviaEmailGrupo(Integer.parseInt(idModeloEmailAcoes), solicitacaoServicoRelacionada.getIdGrupoAtual());
					} else {
						throw new Exception("Não há modelo de e-mail setado nos parâmetros.");
					}

				}
			}

			enviaEmail(servicoContratoDto.getIdModeloEmailAcoes());
		}

		if (tarefaFluxoDto.getElementoFluxoDto().getContabilizaSLA() == null || tarefaFluxoDto.getElementoFluxoDto().getContabilizaSLA().equalsIgnoreCase("S")) {
			if (getSolicitacaoServicoDto().getDataHoraCaptura() == null) {
				getSolicitacaoServicoDto().setDataHoraCaptura(UtilDatas.getDataHoraAtual());
				setTransacaoDao(getSolicitacaoServicoDAO());
				getSolicitacaoServicoDAO().atualizaDataHoraCaptura(getSolicitacaoServicoDto());
			}
		}

	}

	@Override
	public void delega(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, Integer idItemTrabalho, String usuarioDestino, String grupoDestino) throws Exception {
		TarefaFluxoDTO tarefaFluxoDto = recuperaTarefa(idItemTrabalho);
		if (tarefaFluxoDto == null)
			return;

		InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, tarefaFluxoDto.getIdInstancia());
		instanciaFluxo.delegaItemTrabalho(loginUsuario, idItemTrabalho, usuarioDestino, grupoDestino);

		this.objetoNegocioDto = objetoNegocioDto;

		usuarioDTO = new UsuarioDTO();
		usuarioDTO = new UsuarioDao().restoreByLogin(loginUsuario);

		UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
		UsuarioBpmDTO usuarioBpmDto = usuarioGrupo.recuperaUsuario(usuarioDestino);

		String ocorr = "Compartilhamento da tarefa \"" + tarefaFluxoDto.getElementoFluxoDto().getDocumentacao() + "\"";
		if (usuarioDestino != null)
			ocorr += " com o usuário " + usuarioBpmDto.getNome();
		if (grupoDestino != null)
			ocorr += " com o grupo " + grupoDestino;

		OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), tarefaFluxoDto, ocorr, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Compartilhamento, "não se aplica",
				CategoriaOcorrencia.Compartilhamento.getDescricao(), usuarioDTO, 0, null, getTransacao());
	}

	@Override
	public void direcionaAtendimento(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, String grupoAtendimento) throws Exception {
		if (getSolicitacaoServicoDto() == null)
			return;

		if (grupoAtendimento == null)
			return;

		GrupoDTO grupoAtendimentoDto = new GrupoDao().restoreBySigla(grupoAtendimento);
		if (grupoAtendimentoDto == null)
			return;

		UsuarioDTO usuarioRespDto = new UsuarioDTO();
		usuarioRespDto.setIdUsuario(getSolicitacaoServicoDto().getIdResponsavel());
		usuarioRespDto = (UsuarioDTO) new UsuarioDao().restore(usuarioRespDto);

		this.objetoNegocioDto = objetoNegocioDto;

		Collection<ExecucaoSolicitacaoDTO> colExecucao = new ExecucaoSolicitacaoDao().listByIdSolicitacao(getSolicitacaoServicoDto().getIdSolicitacaoServico());
		if (colExecucao != null) {
			ItemTrabalhoFluxoDao itemTrabalhoFluxoDao = new ItemTrabalhoFluxoDao();
			setTransacaoDao(itemTrabalhoFluxoDao);
			OcorrenciaSolicitacaoDao ocorrenciaSolicitacaoDao = new OcorrenciaSolicitacaoDao();
			setTransacaoDao(ocorrenciaSolicitacaoDao);
			for (ExecucaoSolicitacaoDTO execucaoSolicitacaoDto : colExecucao) {
				InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, execucaoSolicitacaoDto.getIdInstanciaFluxo());
				Collection<ItemTrabalhoFluxoDTO> colItens = itemTrabalhoFluxoDao.findDisponiveisByIdInstancia(execucaoSolicitacaoDto.getIdInstanciaFluxo());
				if (colItens != null) {
					for (ItemTrabalhoFluxoDTO itemTrabalhoFluxoDto : colItens) {
						ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(instanciaFluxo, itemTrabalhoFluxoDto.getIdItemTrabalho());
						itemTrabalho.redireciona(loginUsuario, null, grupoAtendimento);

						usuarioDTO = new UsuarioDTO();
						usuarioDTO = new UsuarioDao().restoreByLogin(loginUsuario);

						String ocorr = "Direcionamento da tarefa \"" + itemTrabalho.getElementoFluxoDto().getDocumentacao() + "\"";
						ocorr += " para o grupo " + grupoAtendimento;

						OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), itemTrabalhoFluxoDto, ocorr, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Direcionamento, "não se aplica",
								CategoriaOcorrencia.Direcionamento.getDescricao(), usuarioDTO, 0, null, getTransacao());
					}
				}
			}
		}

		try {
			String enviarNotificacao = ParametroUtil.getValor(ParametroSistema.NOTIFICAR_GRUPO_RECEPCAO_SOLICITACAO, getTransacao(), "N");
			if (enviarNotificacao.equalsIgnoreCase("S")) {
				enviaEmailGrupo(Integer.parseInt(ParametroUtil.getValor(ParametroSistema.ID_MODELO_EMAIL_GRUPO_DESTINO, getTransacao(), null)), grupoAtendimentoDto.getIdGrupo());
			}
		} catch (NumberFormatException e) {
			System.out.println("Não há modelo de e-mail setado nos parâmetros.");
		}
	}

	@Override
	public void mapObjetoNegocio(Map<String, Object> map) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) objetoNegocioDto;
		SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), getTransacao());
		if (solicitacaoAuxDto != null) {
			solicitacaoServicoDto.setGrupoAtual(solicitacaoAuxDto.getGrupoAtual());
			solicitacaoServicoDto.setGrupoNivel1(solicitacaoAuxDto.getGrupoNivel1());
		}

		adicionaObjeto("solicitacaoServico", solicitacaoServicoDto, map);
		if (usuarioDTO != null)
			adicionaObjeto("usuario", usuarioDTO, map);
		else if (solicitacaoServicoDto.getUsuarioDto() != null)
			adicionaObjeto("usuario", solicitacaoServicoDto.getUsuarioDto(), map);

		adicionaObjeto("execucaoFluxo", this, map);
		adicionaObjeto("solicitacaoServicoService", new SolicitacaoServicoServiceEjb(), map);
	}

	public SolicitacaoServicoDTO getSolicitacaoServicoDto() {
		return (SolicitacaoServicoDTO) objetoNegocioDto;
	}

	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario, Integer idTarefa) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getJdbcAliasBPM());
		try {
			setTransacao(tc);
			List<TarefaFluxoDTO> result = null;
			List<TarefaFluxoDTO> listTarefas = super.recuperaTarefas(loginUsuario, idTarefa);
			if (listTarefas != null) {
				result = new ArrayList();
				SolicitacaoServicoServiceEjb solicitacaoServicoService = new SolicitacaoServicoServiceEjb();

				Collection<SolicitacaoServicoDTO> listSolicitacaoServicoDto = solicitacaoServicoService.listByTarefas(listTarefas, tc);

				Collection<SolicitacaoServicoDTO> listSolicitacoesFilhas = solicitacaoServicoService.listSolicitacoesFilhas(tc);

				if (listSolicitacaoServicoDto != null && !listSolicitacaoServicoDto.isEmpty()) {
					for (SolicitacaoServicoDTO solicitacaoServicoDto : listSolicitacaoServicoDto) {
						for (TarefaFluxoDTO tarefaDto : listTarefas) {
							if (solicitacaoServicoDto.getIdInstanciaFluxo().equals(tarefaDto.getIdInstancia())) {

								boolean possuiFilho = false;

								if (listSolicitacoesFilhas != null && !listSolicitacoesFilhas.isEmpty()) {
									for (SolicitacaoServicoDTO solicitacaoServicoDTO2 : listSolicitacoesFilhas) {
										if (solicitacaoServicoDto.getIdSolicitacaoServico().equals(solicitacaoServicoDTO2.getIdSolicitacaoPai())) {
											possuiFilho = true;
											break;
										}
									}
								}

								solicitacaoServicoDto.setPossuiFilho(possuiFilho);

								tarefaDto.setSolicitacaoDto(solicitacaoServicoDto);
								tarefaDto.setDataHoraLimite(solicitacaoServicoDto.getDataHoraLimite());
								result.add(tarefaDto);
							}
						}
					}
				}
				if (result != null) {
					Collections.sort(result, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.ASC));
				}
			}
			return result;
		} finally {
			tc.closeQuietly();
		}
	}

	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario, TipoSolicitacaoServico[] tiposSolicitacao, String somenteEmAprovacao) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getJdbcAliasBPM());
		try {
			setTransacao(tc);
			List<TarefaFluxoDTO> result = null;
			List<TarefaFluxoDTO> tarefasDoUsuario = super.recuperaTarefas(loginUsuario);
			if (tarefasDoUsuario != null) {
				TemplateSolicitacaoServicoDao templateSolicitacaoServicoDao = new TemplateSolicitacaoServicoDao();
				setTransacaoDao(templateSolicitacaoServicoDao);
				List<TarefaFluxoDTO> listTarefas = new ArrayList();
				if (somenteEmAprovacao != null && somenteEmAprovacao.equalsIgnoreCase("S")) {
					List<TemplateSolicitacaoServicoDTO> templates = templateSolicitacaoServicoDao.listComAprovacao();
					HashMap<String, TemplateSolicitacaoServicoDTO> mapTemplates = new HashMap<String, TemplateSolicitacaoServicoDTO>();
					if (templates != null) {
						for (TemplateSolicitacaoServicoDTO templateDto : templates) {
							mapTemplates.put(templateDto.getIdentificacao().toUpperCase(), templateDto);
						}
					}
					for (TarefaFluxoDTO tarefaDto : tarefasDoUsuario) {
						if (tarefaDto.getElementoFluxoDto().getTemplate() != null && !tarefaDto.getElementoFluxoDto().getTemplate().equals("")) {
							if (mapTemplates.get(tarefaDto.getElementoFluxoDto().getTemplate().toUpperCase()) != null)
								listTarefas.add(tarefaDto);
						}
					}
				} else {
					listTarefas = tarefasDoUsuario;
				}

				if (listTarefas.size() == 0)
					return null;

				result = new ArrayList();
				SolicitacaoServicoServiceEjb solicitacaoServicoService = new SolicitacaoServicoServiceEjb();

				Collection<SolicitacaoServicoDTO> listSolicitacaoServicoDto = solicitacaoServicoService.listByTarefas(listTarefas, tiposSolicitacao, tc);

				Collection<SolicitacaoServicoDTO> listSolicitacoesFilhas = solicitacaoServicoService.listSolicitacoesFilhas(tc);

				if (listSolicitacaoServicoDto != null && !listSolicitacaoServicoDto.isEmpty()) {
					for (SolicitacaoServicoDTO solicitacaoServicoDto : listSolicitacaoServicoDto) {
						for (TarefaFluxoDTO tarefaDto : listTarefas) {
							if (solicitacaoServicoDto.getIdInstanciaFluxo().equals(tarefaDto.getIdInstancia())) {

								boolean possuiFilho = false;

								if (listSolicitacoesFilhas != null && !listSolicitacoesFilhas.isEmpty()) {
									for (SolicitacaoServicoDTO solicitacaoServicoDTO2 : listSolicitacoesFilhas) {
										if (solicitacaoServicoDto.getIdSolicitacaoServico().equals(solicitacaoServicoDTO2.getIdSolicitacaoPai())) {
											possuiFilho = true;
											break;
										}
									}
								}

								solicitacaoServicoDto.setPossuiFilho(possuiFilho);

								tarefaDto.setSolicitacaoDto(solicitacaoServicoDto);
								tarefaDto.setDataHoraLimite(solicitacaoServicoDto.getDataHoraLimite());
								result.add(tarefaDto);
							}
						}
					}
				}
				Collections.sort(result, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.ASC));
			}
			return result;
		} finally {
			tc.closeQuietly();
		}
	}

	public List<TarefaFluxoDTO> recuperaTarefas(Integer qtdAtual, Integer qtdAPaginacao, String login) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getJdbcAliasBPM());
		try {
			setTransacao(tc);
			List<TarefaFluxoDTO> result = null;
			List<TarefaFluxoDTO> listTarefas = super.recuperaTarefas(login);
			if (listTarefas != null) {
				result = new ArrayList();
				SolicitacaoServicoServiceEjb solicitacaoServicoService = new SolicitacaoServicoServiceEjb();

				Collection<SolicitacaoServicoDTO> listSolicitacaoServicoDto = solicitacaoServicoService.listByTarefas(listTarefas, qtdAtual, qtdAPaginacao, tc);

				Collection<SolicitacaoServicoDTO> listSolicitacoesFilhas = solicitacaoServicoService.listSolicitacoesFilhas(tc);

				if (listSolicitacaoServicoDto != null && !listSolicitacaoServicoDto.isEmpty()) {
					for (SolicitacaoServicoDTO solicitacaoServicoDto : listSolicitacaoServicoDto) {
						for (TarefaFluxoDTO tarefaDto : listTarefas) {
							if (solicitacaoServicoDto.getIdInstanciaFluxo().equals(tarefaDto.getIdInstancia())) {

								boolean possuiFilho = false;

								if (listSolicitacoesFilhas != null && !listSolicitacoesFilhas.isEmpty()) {
									for (SolicitacaoServicoDTO solicitacaoServicoDTO2 : listSolicitacoesFilhas) {
										if (solicitacaoServicoDto.getIdSolicitacaoServico().equals(solicitacaoServicoDTO2.getIdSolicitacaoPai())) {
											possuiFilho = true;
											break;
										}
									}
								}

								solicitacaoServicoDto.setPossuiFilho(possuiFilho);

								tarefaDto.setSolicitacaoDto(solicitacaoServicoDto);
								tarefaDto.setDataHoraLimite(solicitacaoServicoDto.getDataHoraLimite());
								result.add(tarefaDto);
							}
						}
					}
				}
				Collections.sort(result, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.ASC));
			}
			return result;
		} finally {
			tc.closeQuietly();
		}
	}

	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario, String campoOrdenacao, Boolean asc) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getJdbcAliasBPM());
		try {
			setTransacao(tc);
			List<TarefaFluxoDTO> result = null;
			List<TarefaFluxoDTO> listTarefas = super.recuperaTarefas(loginUsuario);
			if (listTarefas != null) {
				result = new ArrayList();
				SolicitacaoServicoServiceEjb solicitacaoServicoService = new SolicitacaoServicoServiceEjb();
				for (TarefaFluxoDTO tarefaDto : listTarefas) {
					SolicitacaoServicoDTO solicitacaoServicoDto = solicitacaoServicoService.restoreByIdInstanciaFluxo(tarefaDto.getIdInstancia(), tc);
					if (solicitacaoServicoDto != null) {
						tarefaDto.setSolicitacaoDto(solicitacaoServicoDto);
						tarefaDto.setDataHoraLimite(solicitacaoServicoDto.getDataHoraLimite());
						result.add(tarefaDto);
					}
				}
			}
			return result;
		} finally {
			tc.closeQuietly();
		}
	}

	public Integer totalPaginas(Integer itensPorPagina, String loginUsuario) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getJdbcAliasBPM());
		try {
			setTransacao(tc);
			Integer total = 0;
			List<TarefaFluxoDTO> listTarefas = super.recuperaTarefas(loginUsuario);
			if (listTarefas != null) {
				total = getSolicitacaoServicoDAO().totalDePaginas(itensPorPagina, listTarefas);
			}
			return total;
		} finally {
			tc.closeQuietly();
		}
	}

	private void atualizaFaseSolicitacao(Integer idFase) throws Exception {
		setTransacaoDao(getSolicitacaoServicoDAO());
		getSolicitacaoServicoDto().setIdFaseAtual(idFase);
		getSolicitacaoServicoDAO().updateNotNull(getSolicitacaoServicoDto());
	}

	private void atualizaidResponsalvelAtualSolicitacao() throws Exception {
		try {
			if (this.getSolicitacaoServicoDto() != null && this.getSolicitacaoServicoDto().getIdSolicitacaoServico() != null) {
				SolicitacaoServicoDTO solicitacaoServicoDTO = this.getSolicitacaoServicoDto();
				getSolicitacaoServicoDAO().setTransactionControler(this.getTransacao());
				List<SolicitacaoServicoDTO> listSolicitacaoServico = (List<SolicitacaoServicoDTO>) getSolicitacaoServicoDAO().findResponsavelAtual(solicitacaoServicoDTO.getIdSolicitacaoServico());

				if (listSolicitacaoServico != null) {
					solicitacaoServicoDTO.setIdUsuarioResponsavelAtual(listSolicitacaoServico.get(0).getIdUsuarioResponsavelAtual());
					getSolicitacaoServicoDAO().atualizaIdUsuarioResponsavel(solicitacaoServicoDTO);
				} else {
					getSolicitacaoServicoDAO().atualizaIdUsuarioResponsavel(solicitacaoServicoDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void enviaEmail(String identificador) throws Exception {
		if (identificador == null)
			return;

		ModeloEmailDTO modeloEmailDto = new ModeloEmailDao().findByIdentificador(identificador);
		if (modeloEmailDto != null)
			enviaEmail(modeloEmailDto.getIdModeloEmail());
	}

	public boolean isEmailHabilitado() throws Exception {
		String enviaEmail = ParametroUtil.getValor(ParametroSistema.EnviaEmailFluxo, getTransacao(), "N");
		return enviaEmail.equalsIgnoreCase("S");
	}

	public String getRemetenteEmail() throws Exception {
		String remetente = ParametroUtil.getValor(ParametroSistema.RemetenteNotificacoesSolicitacao, getTransacao(), null);
		if (remetente == null)
			throw new LogicException("Remetente para notificações de solicitação de serviço não foi parametrizado");
		return remetente;
	}

	public void complementaInformacoesEmail(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		String urlSistema = ParametroUtil.getValor(ParametroSistema.URL_Sistema, getTransacao(), "");
		if (solicitacaoServicoDto != null) {
			String idHashValidacao = CriptoUtils.generateHash("CODED" + solicitacaoServicoDto.getIdSolicitacaoServico(), "MD5");
			solicitacaoServicoDto.setHashPesquisaSatisfacao(idHashValidacao);
			solicitacaoServicoDto.setUrlSistema(urlSistema);
			solicitacaoServicoDto.setLinkPesquisaSatisfacao("<a href=\"" + urlSistema + "/pages/pesquisaSatisfacao/pesquisaSatisfacao.load?idSolicitacaoServico="
					+ solicitacaoServicoDto.getIdSolicitacaoServico() + "&hash=" + idHashValidacao + "\">Clique aqui para fazer a avaliação do Atendimento</a>");
		}
	}

	@Override
	public void enviaEmail(String identificador, String[] destinatarios) throws Exception {
		if (identificador == null)
			return;

		if (destinatarios == null || destinatarios.length == 0)
			return;

		if (!isEmailHabilitado())
			return;

		ModeloEmailDTO modeloEmailDto = new ModeloEmailDao().findByIdentificador(identificador);
		if (modeloEmailDto == null)
			return;

		/*
		 * String para = destinatarios[0]; String cc = null; if (destinatarios.length > 1) { cc = ""; for (int i = 1; i < destinatarios.length; i++) { cc += destinatarios[i] + ";"; } }
		 */
		String remetente = getRemetenteEmail();

		SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(getSolicitacaoServicoDto().getIdSolicitacaoServico(), getTransacao());
		if (getSolicitacaoServicoDto().getNomeTarefa() != null && !getSolicitacaoServicoDto().getNomeTarefa().trim().equals("")) {
			solicitacaoAuxDto.setNomeTarefa(getSolicitacaoServicoDto().getNomeTarefa());
		} else if (getSolicitacaoServicoDto().getIdTarefa() != null) {
			TarefaFluxoDTO tarefaDto = recuperaTarefa(getSolicitacaoServicoDto().getIdTarefa());
			if (tarefaDto != null && tarefaDto.getElementoFluxoDto() != null)
				solicitacaoAuxDto.setNomeTarefa(tarefaDto.getElementoFluxoDto().getDocumentacao());
		}

		complementaInformacoesEmail(solicitacaoAuxDto);
		/* Decodifica a mensagem a ser enviada */
		/*
		 * comentei a linha abaixo porque estava dando problema no caracter \ e "
		 */
		// solicitacaoAuxDto.setDescricao(StringEscapeUtils.unescapeJavaScript(solicitacaoAuxDto.getDescricao()));
		solicitacaoAuxDto.setResposta(UtilStrings.unescapeJavaString(solicitacaoAuxDto.getResposta()));

		// System.out.println("#################### ENVIANDO EMAIL ####################");
		// System.out.println("### Modelo: " + identificador);
		// System.out.println("### No. solicitação: " + solicitacaoAuxDto.getIdSolicitacaoServico());
		// if (solicitacaoAuxDto.getNomeTarefa() != null)
		// System.out.println("### Tarefa: " + solicitacaoAuxDto.getNomeTarefa());

		MensagemEmail mensagem = new MensagemEmail(modeloEmailDto.getIdModeloEmail(), new IDto[] { solicitacaoAuxDto });
		try {
			for (String para : destinatarios) {
				// System.out.println("### Destinatário: " + para);
				mensagem.envia(para, null, remetente);
				Thread.sleep(50);
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void enviaEmail(Integer idModeloEmail) throws Exception {
		if (idModeloEmail == null)
			return;

		if (!isEmailHabilitado())
			return;

		String remetente = getRemetenteEmail();

		SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(getSolicitacaoServicoDto().getIdSolicitacaoServico(), getTransacao());
		if (solicitacaoAuxDto != null) {
			if (getSolicitacaoServicoDto().getNomeTarefa() != null && !getSolicitacaoServicoDto().getNomeTarefa().trim().equals("")) {
				solicitacaoAuxDto.setNomeTarefa(getSolicitacaoServicoDto().getNomeTarefa());
			} else if (getSolicitacaoServicoDto().getIdTarefa() != null) {
				TarefaFluxoDTO tarefaDto = recuperaTarefa(getSolicitacaoServicoDto().getIdTarefa());
				if (tarefaDto != null && tarefaDto.getElementoFluxoDto() != null)
					solicitacaoAuxDto.setNomeTarefa(tarefaDto.getElementoFluxoDto().getDocumentacao());
			}
		}

		complementaInformacoesEmail(solicitacaoAuxDto);
		/* Decodifica a mensagem a ser enviada */
		if (solicitacaoAuxDto != null) {
			/*
			 * comentei a linha abaixo porque estava dando problema no caracter \ e "
			 */
			// solicitacaoAuxDto.setDescricao(StringEscapeUtils.unescapeJavaScript(solicitacaoAuxDto.getDescricao()));
			solicitacaoAuxDto.setResposta(UtilStrings.unescapeJavaString(solicitacaoAuxDto.getResposta()));

		}

		SolicitacaoServicoDTO solicitacaoAuxiliarEmail = new SolicitacaoServicoDTO();
		if (solicitacaoAuxDto != null) {
			solicitacaoAuxiliarEmail = new SolicitacaoServicoServiceEjb().restoreInfoEmails(solicitacaoAuxDto.getIdSolicitacaoServico(), getTransacao());
		}

		if (solicitacaoAuxiliarEmail != null) {

			if (solicitacaoAuxiliarEmail.getDataRegistroOcorrencia() != null) {
				SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
				solicitacaoAuxiliarEmail.setDataRegistroOcorrenciaStr(simple.format(solicitacaoAuxiliarEmail.getDataRegistroOcorrencia()));
			}

			solicitacaoAuxDto.setRegistradoPor(solicitacaoAuxiliarEmail.getRegistradoPor());
			solicitacaoAuxDto.setRegistroexecucao(solicitacaoAuxiliarEmail.getRegistroexecucao());
			solicitacaoAuxDto.setDataRegistroOcorrenciaStr(solicitacaoAuxiliarEmail.getDataRegistroOcorrenciaStr());
			solicitacaoAuxDto.setHoraRegistroOcorrencia(solicitacaoAuxiliarEmail.getHoraRegistroOcorrencia());
		}

		if (solicitacaoAuxiliarEmail != null && solicitacaoAuxiliarEmail.getRegistroexecucao() == null) {
			solicitacaoAuxDto.setRegistroexecucao("--");
			solicitacaoAuxDto.setRegistradoPor("--");
			solicitacaoAuxDto.setDataRegistroOcorrenciaStr("");
			solicitacaoAuxDto.setHoraRegistroOcorrencia("--");
		}

		if (solicitacaoAuxiliarEmail != null && solicitacaoAuxiliarEmail.getCategoriaOcorrencia() != null && !solicitacaoAuxiliarEmail.getCategoriaOcorrencia().equalsIgnoreCase("Execucao")) {
			solicitacaoAuxDto.setRegistroexecucao("--");
			solicitacaoAuxDto.setRegistradoPor("--");
			solicitacaoAuxDto.setDataRegistroOcorrenciaStr("");
			solicitacaoAuxDto.setHoraRegistroOcorrencia("--");
		}

		// Esse registro de execução na verdade deve ser as informações da ocorrência da solicitação, caso comece com "Execução da Tarefa" quer dizer que foi gerado pelo sistema,
		// logo, não tem importância
		if (solicitacaoAuxiliarEmail != null && solicitacaoAuxiliarEmail.getRegistroexecucao() != null && solicitacaoAuxiliarEmail.getRegistroexecucao().startsWith("Execução da tarefa")) {
			solicitacaoAuxDto.setRegistroexecucao("--");
			solicitacaoAuxDto.setRegistradoPor("--");
			solicitacaoAuxDto.setDataRegistroOcorrenciaStr("");
			solicitacaoAuxDto.setHoraRegistroOcorrencia("--");
		}

		// Cálculo do sla para enviar para o e-mail
		if (solicitacaoAuxDto != null && solicitacaoAuxiliarEmail != null) {
			if ((solicitacaoAuxDto.getPrazoHH() != null && solicitacaoAuxDto.getPrazoHH() != 0) || (solicitacaoAuxDto.getPrazoMM() != null && solicitacaoAuxDto.getPrazoMM() != 0)) {
				if (solicitacaoAuxDto.getPrazoHH() == 0) {
					solicitacaoAuxDto.setSla(solicitacaoAuxDto.getPrazoHH() + "0:" + solicitacaoAuxDto.getPrazoMM());
				} else if (solicitacaoAuxDto.getPrazoMM() == 0) {
					solicitacaoAuxDto.setSla(solicitacaoAuxDto.getPrazoHH() + ":" + solicitacaoAuxDto.getPrazoMM() + "0");
				} else {
					solicitacaoAuxDto.setSla(solicitacaoAuxDto.getPrazoHH() + ":" + solicitacaoAuxDto.getPrazoMM());
				}
			} else {
				solicitacaoAuxDto.setSla("Sla à combinar");
			}
		}

		MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] { solicitacaoAuxDto });
		try {
			if (solicitacaoAuxDto != null) {
				mensagem.envia(solicitacaoAuxDto.getEmailcontato(), null, remetente);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Notifica todos os Empregados de um grupo.
	 *
	 * @param idModeloEmail
	 * @throws Exception
	 */
	public void enviaEmailGrupo(Integer idModeloEmail, Integer idGrupoDestino) throws Exception {
		MensagemEmail mensagem = null;

		if (idGrupoDestino == null) {
			return;
		}

		if (idModeloEmail == null) {
			return;
		}

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		List<String> emails = null;

		try {
			emails = (List<String>) grupoService.listarEmailsPorGrupo(idGrupoDestino);
		} catch (Exception e) {
			return;
		}

		if (emails == null || emails.isEmpty()) {
			return;
		}

		String remetente = ParametroUtil.getValor(ParametroSistema.RemetenteNotificacoesSolicitacao, getTransacao(), null);
		if (remetente == null)
			throw new LogicException("Remetente para notificações de solicitação de serviço não foi parametrizado");

		SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(getSolicitacaoServicoDto().getIdSolicitacaoServico(), getTransacao());
		if (solicitacaoAuxDto == null) {
			return;
		}
		if (getSolicitacaoServicoDto().getNomeTarefa() != null && !getSolicitacaoServicoDto().getNomeTarefa().trim().equals("")) {
			solicitacaoAuxDto.setNomeTarefa(getSolicitacaoServicoDto().getNomeTarefa());
		} else if (getSolicitacaoServicoDto().getIdTarefa() != null) {
			TarefaFluxoDTO tarefaDto = recuperaTarefa(getSolicitacaoServicoDto().getIdTarefa());
			if (tarefaDto != null && tarefaDto.getElementoFluxoDto() != null)
				solicitacaoAuxDto.setNomeTarefa(tarefaDto.getElementoFluxoDto().getDocumentacao());
		}

		/* Decodifica a mensagem a ser enviada */
		/*
		 * comentei a linha abaixo porque estava dando problema no caracter \ e "
		 */
		// solicitacaoAuxDto.setDescricao(StringEscapeUtils.unescapeJavaScript(solicitacaoAuxDto.getDescricao()));
		solicitacaoAuxDto.setResposta(UtilStrings.unescapeJavaString(solicitacaoAuxDto.getResposta()));

		try {

			// EmpregadoDTO aux = null;
			for (String email : emails) {
				int posArroba = email.indexOf("@");
				if (posArroba > 0 && email.substring(posArroba).contains(".")) {
					try {
						mensagem = new MensagemEmail(idModeloEmail, new IDto[] { solicitacaoAuxDto });

						// aux = (EmpregadoDTO)
						// getEmpregadoService().restore(e);
						// if(aux != null && aux.getEmail() != null &&
						// !aux.getEmail().trim().equalsIgnoreCase("") ){
						mensagem.envia(email, null, remetente);
						Thread.sleep(50);
					} catch (Exception e) {
						// faz nada
					}
				}
				// }
			}
		} catch (Exception e) {
		}
	}

	private ServicoContratoDTO recuperaServicoContrato() throws Exception {
		ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
		setTransacaoDao(servicoContratoDao);
		ServicoContratoDTO servicoContratoDto = new ServicoContratoDTO();
		if (getSolicitacaoServicoDto().getIdServicoContrato() != null)
			servicoContratoDto.setIdServicoContrato(getSolicitacaoServicoDto().getIdServicoContrato());
		else {
			setTransacaoDao(getSolicitacaoServicoDAO());
			SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) objetoNegocioDto;
			SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoDTO();
			solicitacaoAuxDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
			solicitacaoAuxDto = (SolicitacaoServicoDTO) getSolicitacaoServicoDAO().restore(solicitacaoAuxDto);
			servicoContratoDto.setIdServicoContrato(solicitacaoAuxDto.getIdServicoContrato());
		}
		servicoContratoDto = (ServicoContratoDTO) servicoContratoDao.restore(servicoContratoDto);
		if (servicoContratoDto == null)
			throw new LogicException("Serviço contrato não localizado");
		return servicoContratoDto;
	}

	@Override
	public void encerra() throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = getSolicitacaoServicoDto();
		if (solicitacaoServicoDto == null)
			throw new Exception("Solicitação de serviço não encontrada");

		if (solicitacaoServicoDto.encerrada())
			return;

		validaEncerramento();

		// if (!solicitacaoServicoDto.atendida() &&
		// !solicitacaoServicoDto.reclassificada())
		// throw new
		// Exception("Solicitação de serviço não permite encerramento");

		Collection<ExecucaoSolicitacaoDTO> colExecucao = new ExecucaoSolicitacaoDao().listByIdSolicitacao(getSolicitacaoServicoDto().getIdSolicitacaoServico());
		if (colExecucao != null) {
			for (ExecucaoSolicitacaoDTO execucaoSolicitacaoDto : colExecucao) {
				InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, execucaoSolicitacaoDto.getIdInstanciaFluxo());
				instanciaFluxo.encerra();
			}
		}

		if (!solicitacaoServicoDto.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Cancelada.name()))
			solicitacaoServicoDto.setSituacao(SituacaoSolicitacaoServico.Fechada.name());
		solicitacaoServicoDto.setDataHoraFim(UtilDatas.getDataHoraAtual());
		calculaTempoCaptura(solicitacaoServicoDto);
		calculaTempoAtendimento(solicitacaoServicoDto);
		calculaTempoAtraso(solicitacaoServicoDto);
		setTransacaoDao(getSolicitacaoServicoDAO());
		getSolicitacaoServicoDAO().updateNotNull(solicitacaoServicoDto);

		OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO = new OcorrenciaSolicitacaoDTO();
		usuarioDTO = new UsuarioDTO();
		usuarioDTO.setLogin("Automático");
		ocorrenciaSolicitacaoDTO = OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), null, null, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Encerramento, null,
				CategoriaOcorrencia.Encerramento.getDescricao(), usuarioDTO, 0, null, getTransacao());

		popularHistorico(solicitacaoServicoDto, ocorrenciaSolicitacaoDTO, "Encerra", usuarioDTO);
		if (getSolicitacaoServicoDto().getEnviaEmailFinalizacao() != null && getSolicitacaoServicoDto().getEnviaEmailFinalizacao().equalsIgnoreCase("S")) {
			ServicoContratoDTO servicoContratoDto = recuperaServicoContrato();

			String enviarNotificacaoSolicitacoesVinculadas = ParametroUtil.getValor(ParametroSistema.RECEBER_NOTIFICACAO_ENCERRAR_ESCALONAR_SOLICITACOES_VINCULADAS, getTransacao(), "S");

			if (enviarNotificacaoSolicitacoesVinculadas.equalsIgnoreCase("S")) {
				if (getSolicitacaoServicoDto().getIdSolicitacaoRelacionada() != null) {
					SolicitacaoServicoDTO solicitacaoServicoRelacionada = new SolicitacaoServicoDTO();
					solicitacaoServicoRelacionada.setIdSolicitacaoServico(getSolicitacaoServicoDto().getIdSolicitacaoRelacionada());
					solicitacaoServicoRelacionada = (SolicitacaoServicoDTO) getSolicitacaoServicoDAO().restore(solicitacaoServicoRelacionada);

					String idModeloEmailEncerramento = ParametroUtil.getValor(ParametroSistema.ID_MODELO_EMAIL_ENCERRAMENTO_GRUPO_EXECUTOR_SOLICITACAO_RELACIONADA, getTransacao(), "86");
					if (idModeloEmailEncerramento != null) {
						enviaEmailGrupo(Integer.parseInt(idModeloEmailEncerramento), solicitacaoServicoRelacionada.getIdGrupoAtual());
					} else {
						throw new Exception("Não há modelo de e-mail setado nos parâmetros.");
					}

				}
			}

			enviaEmail(servicoContratoDto.getIdModeloEmailFinalizacao());
		}

		Collection<SolicitacaoServicoDTO> colRelacionados = getSolicitacaoServicoDAO().findByIdSolicitacaoPai(solicitacaoServicoDto.getIdSolicitacaoServico());
		if (colRelacionados != null) {
			SolicitacaoServicoServiceEjb solicitacaoService = new SolicitacaoServicoServiceEjb();
			for (SolicitacaoServicoDTO solicitacaoRelacionadaDto : colRelacionados) {
				if (solicitacaoRelacionadaDto.getIdSolicitacaoServico().intValue() == solicitacaoServicoDto.getIdSolicitacaoServico().intValue())
					continue;
				SolicitacaoServicoDTO solicitacaoAuxDto = solicitacaoService.restoreAll(solicitacaoRelacionadaDto.getIdSolicitacaoServico(), getTransacao());
				solicitacaoAuxDto.setIdCausaIncidente(solicitacaoServicoDto.getIdCausaIncidente());
				solicitacaoAuxDto.setResposta(solicitacaoServicoDto.getResposta());
				ExecucaoSolicitacao execucaoSolicitacao = new ExecucaoSolicitacao(solicitacaoAuxDto, getTransacao());
				execucaoSolicitacao.encerra();
			}
		}
	}

	@Override
	public void reabre(String loginUsuario) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = getSolicitacaoServicoDto();
		if (solicitacaoServicoDto == null)
			throw new Exception("Solicitação de Serviço não encontrada");

		if (!solicitacaoServicoDto.encerrada())
			throw new Exception("Solicitação de Serviçoo não permite reabertura");

		usuarioDTO = new UsuarioDTO();
		usuarioDTO = new UsuarioDao().restoreByLogin(loginUsuario);

		int seqReabertura = 1;
		ReaberturaSolicitacaoDao reaberturaSolicitacaoDao = new ReaberturaSolicitacaoDao();
		setTransacaoDao(reaberturaSolicitacaoDao);
		Collection colReabertura = reaberturaSolicitacaoDao.findByIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
		if (colReabertura != null)
			seqReabertura = colReabertura.size() + 1;

		ReaberturaSolicitacaoDTO reaberturaSolicitacaoDto = new ReaberturaSolicitacaoDTO();
		reaberturaSolicitacaoDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
		reaberturaSolicitacaoDto.setSeqReabertura(seqReabertura);
		reaberturaSolicitacaoDto.setIdResponsavel(usuarioDTO.getIdUsuario());
		reaberturaSolicitacaoDto.setDataHora(UtilDatas.getDataHoraAtual());
		reaberturaSolicitacaoDao.create(reaberturaSolicitacaoDto);

		solicitacaoServicoDto.setSituacao(SituacaoSolicitacaoServico.Reaberta.name());
		solicitacaoServicoDto.setSeqReabertura(new Integer(seqReabertura));
		setTransacaoDao(getSolicitacaoServicoDAO());
		getSolicitacaoServicoDAO().update(solicitacaoServicoDto);

		OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), null, null, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Reabertura, null, CategoriaOcorrencia.Reabertura.getDescricao(),
				usuarioDTO, 0, null, getTransacao());
		reabre = "S";

		inicia();
	}

	@Override
	public void suspende(String loginUsuario) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = getSolicitacaoServicoDto();
		if (solicitacaoServicoDto == null)
			throw new LogicException("solicitacaoservico.validacao.solicitacaoservico");

		if (!solicitacaoServicoDto.emAtendimento())
			throw new LogicException("citcorpore.comum.validarSuspensaoSolicitacao");

		OcorrenciaSolicitacaoDTO ocorrenciaSolicitacao = new OcorrenciaSolicitacaoDTO();

		solicitacaoServicoDto.setDataHoraSuspensao(UtilDatas.getDataHoraAtual());
		solicitacaoServicoDto.setDataHoraReativacao(null);
		suspendeSLA(solicitacaoServicoDto);

		SolicitacaoServicoDao solicitacaoDao = new SolicitacaoServicoDao();
		setTransacaoDao(solicitacaoDao);
		solicitacaoServicoDto.setSituacao(SituacaoSolicitacaoServico.Suspensa.name());
		solicitacaoDao.update(solicitacaoServicoDto);

		JustificativaSolicitacaoDTO justificativaDto = new JustificativaSolicitacaoDTO();
		justificativaDto.setIdJustificativa(solicitacaoServicoDto.getIdJustificativa());
		justificativaDto.setComplementoJustificativa(solicitacaoServicoDto.getComplementoJustificativa());

		usuarioDTO = new UsuarioDTO();
		usuarioDTO = new UsuarioDao().restoreByLogin(loginUsuario);

		ocorrenciaSolicitacao = OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), null, null, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Suspensao, null,
				CategoriaOcorrencia.Suspensao.getDescricao(), usuarioDTO, 0, justificativaDto, getTransacao());

		popularHistorico(getSolicitacaoServicoDto(), ocorrenciaSolicitacao, "Suspende", usuarioDTO);
	}

	@Override
	public void reativa(String loginUsuario) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = getSolicitacaoServicoDto();
		if (solicitacaoServicoDto.getSituacaoSLA().equals("M")) {
			solicitacaoServicoDto.setSituacaoSLA("A");
		}

		if (!solicitacaoServicoDto.suspensa())
			throw new Exception("Solicitação de Serviço não permite reativação");

		setTransacaoDao(getSolicitacaoServicoDAO());

		Timestamp tsAtual = UtilDatas.getDataHoraAtual();
		solicitacaoServicoDto.setSituacao(SituacaoSolicitacaoServico.EmAndamento.name());
		solicitacaoServicoDto.setDataHoraSuspensao(null);
		solicitacaoServicoDto.setDataHoraReativacao(tsAtual);
		reativaSLA(solicitacaoServicoDto);
		getSolicitacaoServicoDAO().update(solicitacaoServicoDto);
		OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO = new OcorrenciaSolicitacaoDTO();
		usuarioDTO = new UsuarioDTO();
		usuarioDTO = new UsuarioDao().restoreByLogin(loginUsuario);

		ocorrenciaSolicitacaoDTO = OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), null, null, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Reativacao, null,
				CategoriaOcorrencia.Reativacao.getDescricao(), usuarioDTO, 0, null, getTransacao());

		popularHistorico(solicitacaoServicoDto, ocorrenciaSolicitacaoDTO, "Reativa", usuarioDTO);
	}

	private Integer getIdCalendario(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		Integer idCalendario = solicitacaoServicoDto.getIdCalendario();
		if (solicitacaoServicoDto.getIdCalendario() == null) {
			ServicoContratoDTO servicoContratoDto = new ServicoContratoDao().findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
			if (servicoContratoDto == null)
				throw new LogicException("Serviço não localizado para o contrato");
			idCalendario = servicoContratoDto.getIdCalendario();
		}
		return idCalendario;
	}

	public void calculaTempoCaptura(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		solicitacaoServicoDto.setTempoCapturaHH(0);
		solicitacaoServicoDto.setTempoCapturaMM(0);

		if (solicitacaoServicoDto.getDataHoraCaptura() == null)
			return;

		if (solicitacaoServicoDto.getDataHoraInicioSLA() == null)
			return;

		if (solicitacaoServicoDto.getDataHoraInicioSLA().compareTo(solicitacaoServicoDto.getDataHoraCaptura()) > 0)
			return;

		Integer idCalendario = getIdCalendario(solicitacaoServicoDto);

		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendario, solicitacaoServicoDto.getDataHoraInicioSLA());
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, solicitacaoServicoDto.getDataHoraCaptura(), getTransacao());

		solicitacaoServicoDto.setTempoCapturaHH(calculoDto.getTempoDecorridoHH().intValue());
		solicitacaoServicoDto.setTempoCapturaMM(calculoDto.getTempoDecorridoMM().intValue());
	}

	public void calculaTempoCapturaNovo(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		solicitacaoServicoDto.setTempoCapturaHH(0);
		solicitacaoServicoDto.setTempoCapturaMM(0);

		if (solicitacaoServicoDto.getDataHoraCaptura() == null)
			return;

		if (solicitacaoServicoDto.getDataHoraInicioSLA() == null)
			return;

		if (solicitacaoServicoDto.getDataHoraInicioSLA().compareTo(solicitacaoServicoDto.getDataHoraCaptura()) > 0)
			return;

		Integer idCalendario = getIdCalendario(solicitacaoServicoDto);

		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendario, solicitacaoServicoDto.getDataHoraInicioSLA());
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorridoNovo(calculoDto, solicitacaoServicoDto.getDataHoraCaptura(), getTransacao());

		solicitacaoServicoDto.setTempoCapturaHH(calculoDto.getTempoDecorridoHH().intValue());
		solicitacaoServicoDto.setTempoCapturaMM(calculoDto.getTempoDecorridoMM().intValue());
		solicitacaoServicoDto.setTempoCapturaSS(calculoDto.getTempoDecorridoSS());
	}

	public void calculaTempoAtendimento(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		if (solicitacaoServicoDto.getDataHoraInicioSLA() == null)
			return;

		Integer idCalendario = getIdCalendario(solicitacaoServicoDto);

		Timestamp tsAtual = UtilDatas.getDataHoraAtual();
		if (solicitacaoServicoDto.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Fechada.name()))
			tsAtual = solicitacaoServicoDto.getDataHoraFim();

		Timestamp tsInicial = solicitacaoServicoDto.getDataHoraInicioSLA();
		if (solicitacaoServicoDto.getDataHoraReativacao() != null)
			tsInicial = solicitacaoServicoDto.getDataHoraReativacao();

		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendario, tsInicial);
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, tsAtual, getTransacao());

		if (solicitacaoServicoDto.getTempoDecorridoHH() == null) {
			solicitacaoServicoDto.setTempoDecorridoHH(0);
		}
		if (solicitacaoServicoDto.getTempoDecorridoMM() == null) {
			solicitacaoServicoDto.setTempoDecorridoMM(0);
		}

		// int horasAux = (solicitacaoServicoDto.getTempoDecorridoMM().intValue() + calculoDto.getTempoDecorridoMM().intValue()) / 60;
		// int minAux = (solicitacaoServicoDto.getTempoDecorridoMM().intValue() + calculoDto.getTempoDecorridoMM().intValue()) % 60;

		// Foi verificado que o tempo de atendimento é o tempo decorrido, ele estava calculando duas vezes.
		solicitacaoServicoDto.setTempoAtendimentoHH(new Integer(calculoDto.getTempoDecorridoHH().intValue()));
		solicitacaoServicoDto.setTempoAtendimentoMM(new Integer(calculoDto.getTempoDecorridoMM()));
	}

	public void calculaTempoAtraso(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		solicitacaoServicoDto.setTempoAtrasoHH(0);
		solicitacaoServicoDto.setTempoAtrasoMM(0);
		if (solicitacaoServicoDto.getDataHoraLimite() != null) {
			Timestamp dataHoraLimite = solicitacaoServicoDto.getDataHoraLimite();
			Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
			if (solicitacaoServicoDto.encerrada())
				dataHoraComparacao = solicitacaoServicoDto.getDataHoraFim();
			if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
				long atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraComparacao, dataHoraLimite) / 1000;

				String hora = Util.getHoraStr(new Double(atrasoSLA) / 3600);
				int tam = hora.length();
				solicitacaoServicoDto.setTempoAtrasoHH(new Integer(hora.substring(0, tam - 2)));
				solicitacaoServicoDto.setTempoAtrasoMM(new Integer(hora.substring(tam - 2, tam)));
			}
		}
	}

	public EmpregadoDTO recuperaSolicitante(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		if (solicitacaoServicoDto == null || solicitacaoServicoDto.getIdSolicitante() == null)
			return null;

		return new EmpregadoDao().restoreByIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
	}

	public StringBuilder recuperaLoginResponsaveis() throws Exception {
		StringBuilder result = new StringBuilder();
		SolicitacaoServicoDTO solicitacaoDto = getSolicitacaoServicoDto();
		UsuarioDao usuarioDao = new UsuarioDao();
		UsuarioDTO usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(solicitacaoDto.getIdSolicitante());
		if (usuarioDto != null)
			result.append(usuarioDto.getLogin());
		usuarioDto = usuarioDao.restoreAtivoByIdEmpregado(solicitacaoDto.getIdResponsavel());
		if (usuarioDto != null) {
			if (result.length() > 0)
				result.append(";");
			result.append(usuarioDto.getLogin());
		}
		return result;
	}

	@Override
	public void executaEvento(EventoFluxoDTO eventoFluxoDto) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getSolicitacaoServicoDAO().getAliasDB());

		try {
			tc.start();
			setTransacao(tc);
			setTransacaoDao(getSolicitacaoServicoDAO());

			SolicitacaoServicoDTO solicitacaoServicoDto = getSolicitacaoServicoDAO().restoreByIdInstanciaFluxo(eventoFluxoDto.getIdInstancia());
			if (solicitacaoServicoDto == null)
				throw new LogicException("Solicitação de serviço do evento " + eventoFluxoDto.getIdItemTrabalho() + " não encontrada");

			setObjetoNegocioDto(solicitacaoServicoDto);
			InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, eventoFluxoDto.getIdInstancia());

			HashMap<String, Object> map = instanciaFluxo.getMapObj();
			instanciaFluxo.getObjetos(map);
			mapObjetoNegocio(map);
			instanciaFluxo.executaEvento(eventoFluxoDto.getIdItemTrabalho(), map);

			tc.commit();

		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		} finally {
			tc.closeQuietly();
		}
	}

	public void cancelaTarefa(String loginUsuario, SolicitacaoServicoDTO solicitacaoServicoDto, TarefaFluxoDTO tarefaFluxoDto, String motivo) throws Exception {
		InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, tarefaFluxoDto.getIdInstancia());
		instanciaFluxo.cancelaItemTrabalho(loginUsuario, tarefaFluxoDto.getIdItemTrabalho());

		String ocorrencia = "Cancelamento da tarefa \"" + tarefaFluxoDto.getElementoFluxoDto().getDocumentacao() + "\"";
		if (motivo != null && motivo.trim().length() > 0)
			ocorrencia += ". Motivo: " + motivo;

		Long tempo = new Long(0);
		if (tarefaFluxoDto.getDataHoraFinalizacao() != null)
			tempo = (tarefaFluxoDto.getDataHoraFinalizacao().getTime() - tarefaFluxoDto.getDataHoraCriacao().getTime()) / 1000 / 60;

		usuarioDTO = new UsuarioDTO();
		usuarioDTO.setLogin("Sistema");
		OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), tarefaFluxoDto, ocorrencia, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.CancelamentoTarefa, "não se aplica",
				CategoriaOcorrencia.CancelamentoTarefa.getDescricao(), usuarioDTO, tempo.intValue(), null, getTransacao());
	}

	@Override
	public void validaEncerramento() throws Exception {
	}

	public String recuperaGrupoAprovador() throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = getSolicitacaoServicoDto();
		if (solicitacaoServicoDto == null)
			throw new Exception("Solicitação de Serviço não encontrada.");

		ServicoContratoDTO servicoContratoDto = recuperaServicoContrato();
		if (servicoContratoDto.getIdGrupoAprovador() == null)
			throw new Exception("Grupo aprovador não parametrizado. Verifique o serviço do contrato.");

		GrupoDao grupoDao = new GrupoDao();
		setTransacaoDao(grupoDao);
		GrupoDTO grupoDto = new GrupoDTO();
		grupoDto.setIdGrupo(servicoContratoDto.getIdGrupoAprovador());
		grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
		if (grupoDto == null)
			throw new Exception("Grupo aprovador não encontrado.");

		return grupoDto.getSigla();
	}

	@Override
	public void verificaSLA(ItemTrabalho itemTrabalho) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = getSolicitacaoServicoDto();
		if (solicitacaoServicoDto == null)
			throw new Exception("Solicitação de Serviço não encontrada.");

		boolean bContabilizaSLA = true;
		if (itemTrabalho.getContabilizaSLA() != null)
			bContabilizaSLA = itemTrabalho.getContabilizaSLA().equalsIgnoreCase("S");

		boolean bGravar = false;
		if (bContabilizaSLA) {
			if (solicitacaoServicoDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.N.name())) {
				iniciaSLA(solicitacaoServicoDto);
				bGravar = true;
			}
			if (solicitacaoServicoDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.S.name())) {
				reativaSLA(solicitacaoServicoDto);
				bGravar = true;
			}
		} else if (solicitacaoServicoDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.A.name())) {
			suspendeSLA(solicitacaoServicoDto);
			bGravar = true;
		}
		if (bGravar) {
			setTransacaoDao(getSolicitacaoServicoDAO());
			getSolicitacaoServicoDAO().updateNotNull(solicitacaoServicoDto);
		}
	}

	public void determinaPrazoLimiteSolicitacaoACombinarReclassificada(SolicitacaoServicoDTO solicitacaoDto, Integer idCalendario) throws Exception {
		Timestamp tsAtual = UtilDatas.getDataHoraAtual();

		if (idCalendario == null || idCalendario.intValue() == 0) {
			ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
			setTransacaoDao(servicoContratoDao);
			ServicoContratoDTO servicoContratoDto = servicoContratoDao.findByIdContratoAndIdServico(solicitacaoDto.getIdContrato(), solicitacaoDto.getIdServico());
			if (servicoContratoDto == null)
				throw new LogicException(i18n_Message(solicitacaoDto.getUsuarioDto(), "solicitacaoservico.validacao.servicolocalizado"));
			idCalendario = servicoContratoDto.getIdCalendario();
		}

		if (solicitacaoDto.getPrazoHH() == null)
			solicitacaoDto.setPrazoHH(0);
		if (solicitacaoDto.getPrazoMM() == null)
			solicitacaoDto.setPrazoMM(0);

		CalculoJornadaDTO calculoDto = null;

		if (solicitacaoDto.getHouveMudanca() != null && solicitacaoDto.getHouveMudanca().equalsIgnoreCase("S") && solicitacaoDto.getDataHoraReativacaoSLA() != null
				&& solicitacaoDto.getTempoDecorridoHH() != null
				&& ((solicitacaoDto.getPrazoHH() * 100) + solicitacaoDto.getPrazoMM()) > (solicitacaoDto.getTempoDecorridoHH() * 100) + solicitacaoDto.getTempoDecorridoMM()) {
			Integer novoPrazoHH = solicitacaoDto.getPrazoHH() - solicitacaoDto.getTempoDecorridoHH();
			Integer novoPrazoMM = solicitacaoDto.getPrazoMM() - solicitacaoDto.getTempoDecorridoMM();

			calculoDto = new CalculoJornadaDTO(idCalendario, solicitacaoDto.getDataHoraReativacaoSLA(), novoPrazoHH, novoPrazoMM);
			calculoDto = new CalendarioServiceEjb().calculaDataHoraFinal(calculoDto, true, getTransacao());
		} else {
			if (solicitacaoDto.getDataHoraReativacaoSLA() != null && solicitacaoDto.getTempoDecorridoHH() == 0 && solicitacaoDto.getTempoDecorridoMM() == 0) {
				calculoDto = new CalculoJornadaDTO(idCalendario, solicitacaoDto.getDataHoraReativacaoSLA(), solicitacaoDto.getPrazoHH(), solicitacaoDto.getPrazoMM());
			} else {
				solicitacaoDto.setDataHoraInicioSLA(tsAtual);
				calculoDto = new CalculoJornadaDTO(idCalendario, solicitacaoDto.getDataHoraInicioSLA(), solicitacaoDto.getPrazoHH(), solicitacaoDto.getPrazoMM());
			}
			calculoDto = new CalendarioServiceEjb().calculaDataHoraFinal(calculoDto, true, getTransacao());
		}

		solicitacaoDto.setDataHoraLimite(calculoDto.getDataHoraFinal());
	}

	public void iniciaSLA(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		if (!solicitacaoDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.N.name()))
			return;

		solicitacaoDto.setDataHoraInicioSLA(UtilDatas.getDataHoraAtual());
		solicitacaoDto.setSituacaoSLA(SituacaoSLA.A.name());
		new SolicitacaoServicoServiceEjb().determinaPrazoLimite(solicitacaoDto, null, getTransacao());

		usuarioDTO = new UsuarioDTO();
		usuarioDTO.setLogin("Automático");

		OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), null, null, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.InicioSLA, null, CategoriaOcorrencia.InicioSLA.getDescricao(),
				usuarioDTO, 0, null, getTransacao());
	}

	public void suspendeSLA(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		if (!solicitacaoDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.A.name()))
			return;

		Timestamp tsAtual = UtilDatas.getDataHoraAtual();
		Timestamp tsInicial = solicitacaoDto.getDataHoraInicioSLA();
		if (solicitacaoDto.getDataHoraReativacaoSLA() != null)
			tsInicial = solicitacaoDto.getDataHoraReativacaoSLA();
		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(solicitacaoDto.getIdCalendario(), tsInicial);
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, tsAtual, getTransacao());

		if (solicitacaoDto.getTempoDecorridoHH() == null) {
			solicitacaoDto.setTempoDecorridoHH(0);
		}
		if (solicitacaoDto.getTempoDecorridoMM() == null) {
			solicitacaoDto.setTempoDecorridoMM(0);
		}

		int horasAux = (solicitacaoDto.getTempoDecorridoMM().intValue() + calculoDto.getTempoDecorridoMM().intValue()) / 60;
		int minAux = (solicitacaoDto.getTempoDecorridoMM().intValue() + calculoDto.getTempoDecorridoMM().intValue()) % 60;

		solicitacaoDto.setSituacaoSLA(SituacaoSLA.S.name());
		solicitacaoDto.setTempoDecorridoHH(new Integer(solicitacaoDto.getTempoDecorridoHH().intValue() + calculoDto.getTempoDecorridoHH().intValue() + horasAux));
		solicitacaoDto.setTempoDecorridoMM(new Integer(minAux));
		solicitacaoDto.setDataHoraSuspensaoSLA(tsAtual);
		solicitacaoDto.setDataHoraReativacaoSLA(null);

		usuarioDTO = new UsuarioDTO();
		usuarioDTO.setLogin("Automático");

		OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), null, null, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.SuspensaoSLA, null,
				CategoriaOcorrencia.SuspensaoSLA.getDescricao(), usuarioDTO, 0, null, getTransacao());
	}

	public void reativaSLA(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		if (!solicitacaoDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.S.name()))
			return;

		Timestamp tsAtual = UtilDatas.getDataHoraAtual();
		double prazo = solicitacaoDto.getPrazoHH() + new Double(solicitacaoDto.getPrazoMM()).doubleValue() / 60;
		double tempo = solicitacaoDto.getTempoDecorridoHH() + new Double(solicitacaoDto.getTempoDecorridoMM()).doubleValue() / 60;
		prazo = prazo - tempo;
		if (prazo > 0) {
			CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(solicitacaoDto.getIdCalendario(), tsAtual, Util.getHora(prazo), Util.getMinuto(prazo));
			calculoDto = new CalendarioServiceEjb().calculaDataHoraFinal(calculoDto, false, getTransacao());
			solicitacaoDto.setDataHoraLimite(calculoDto.getDataHoraFinal());
		}

		solicitacaoDto.setSituacaoSLA(SituacaoSLA.A.name());
		solicitacaoDto.setDataHoraSuspensaoSLA(null);
		solicitacaoDto.setDataHoraReativacaoSLA(tsAtual);
		usuarioDTO = new UsuarioDTO();
		usuarioDTO.setLogin("Automático");
		OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), null, null, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.ReativacaoSLA, null,
				CategoriaOcorrencia.ReativacaoSLA.getDescricao(), usuarioDTO, 0, null, getTransacao());
	}

	private void popularHistorico(SolicitacaoServicoDTO solicitacaoServicoDto, OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO, String status, UsuarioDTO usuarioDTO) throws Exception {
		HistoricoSolicitacaoServicoDTO historicoSolicitacaoServicoDTO = new HistoricoSolicitacaoServicoDTO();
		HistoricoSolicitacaoServicoService historicoSolicitacaoServicoService = (HistoricoSolicitacaoServicoService) ServiceLocator.getInstance().getService(HistoricoSolicitacaoServicoService.class,
				null);
		Collection<HistoricoSolicitacaoServicoDTO> listaHistorico = new ArrayList<HistoricoSolicitacaoServicoDTO>();
		Collection<HistoricoSolicitacaoServicoDTO> responsavelAtual = new ArrayList<HistoricoSolicitacaoServicoDTO>();

		historicoSolicitacaoServicoDTO.setIdSolicitacaoServico(ocorrenciaSolicitacaoDTO.getIdSolicitacaoServico());
		historicoSolicitacaoServicoDTO.setIdOcorrencia(ocorrenciaSolicitacaoDTO.getIdOcorrencia());
		historicoSolicitacaoServicoDTO.setDataCriacao(UtilDatas.getDataHoraAtual());

		if (solicitacaoServicoDto.getIdGrupoAtual() != null)
			historicoSolicitacaoServicoDTO.setIdGrupo(solicitacaoServicoDto.getIdGrupoAtual());

		historicoSolicitacaoServicoDTO.setStatus(status);
		responsavelAtual = historicoSolicitacaoServicoService.findResponsavelAtual(historicoSolicitacaoServicoDTO.getIdSolicitacaoServico());
		if (responsavelAtual != null && !responsavelAtual.isEmpty()) {
			for (HistoricoSolicitacaoServicoDTO historicoSolicitacaoServicoResponsavel : responsavelAtual) {
				historicoSolicitacaoServicoDTO.setIdResponsavelAtual(historicoSolicitacaoServicoResponsavel.getIdResponsavelAtual());
			}
		} else {
			historicoSolicitacaoServicoDTO.setIdResponsavelAtual(usuarioDTO.getIdUsuario());
		}
		historicoSolicitacaoServicoDTO.setIdServicoContrato(solicitacaoServicoDto.getIdServico());
		historicoSolicitacaoServicoDTO.setIdCalendario(solicitacaoServicoDto.getIdCalendario());
		try {
			if (historicoSolicitacaoServicoService.findHistoricoSolicitacao(ocorrenciaSolicitacaoDTO.getIdSolicitacaoServico()) && !status.equalsIgnoreCase("Suspende")
					&& !status.equalsIgnoreCase("Encerra")) {
				historicoSolicitacaoServicoDTO.setHorasTrabalhadas(0.0);
				if (status.equalsIgnoreCase("Executa")) {
					historicoSolicitacaoServicoDTO.setIdResponsavelAtual(usuarioDTO.getIdUsuario());
				}
				historicoSolicitacaoServicoDTO.setDataFinal(null);
				historicoSolicitacaoServicoService.create(historicoSolicitacaoServicoDTO);
			} else {
				listaHistorico = historicoSolicitacaoServicoService.restoreHistoricoServico(ocorrenciaSolicitacaoDTO.getIdSolicitacaoServico());
				if (listaHistorico != null && !listaHistorico.isEmpty()) {
					for (HistoricoSolicitacaoServicoDTO historicoSolicitacaoServicoDTO2 : listaHistorico) {
						historicoSolicitacaoServicoDTO2.setDataFinal(UtilDatas.getDataHoraAtual());
						historicoSolicitacaoServicoDTO2.setHorasTrabalhadas(calcularHoraTrabalhada(historicoSolicitacaoServicoDTO2));
						historicoSolicitacaoServicoService.update(historicoSolicitacaoServicoDTO2);
					}
				}
				if (status.equalsIgnoreCase("Executa")) {
					historicoSolicitacaoServicoDTO.setHorasTrabalhadas(0.0);
					if (status.equalsIgnoreCase("Executa")) {
						historicoSolicitacaoServicoDTO.setIdResponsavelAtual(usuarioDTO.getIdUsuario());
					}
					historicoSolicitacaoServicoDTO.setDataFinal(null);
					historicoSolicitacaoServicoService.create(historicoSolicitacaoServicoDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private double calcularHoraTrabalhada(HistoricoSolicitacaoServicoDTO historicoSolicitacaoServicoDTO) throws Exception {
		Timestamp tsAtual = UtilDatas.getDataHoraAtual();
		Timestamp tsInicial = historicoSolicitacaoServicoDTO.getDataCriacao();
		CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(historicoSolicitacaoServicoDTO.getIdCalendario(), tsInicial);
		calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, tsAtual, getTransacao());
		int horasAux = (calculoDto.getTempoDecorridoHH().intValue()) * 60;
		int minAux = (calculoDto.getTempoDecorridoMM().intValue()) % 60;
		return (horasAux + minAux);
	}

	/**
	 * Retorna a Lista de TarefaDTO com SolicitacaoServidoDTO de acordo com o Login e a Lista de Contratos do Usuário Logado.
	 *
	 * @param pgAtual
	 * @param qtdAPaginacao
	 * @param login
	 * @param gerenciamentoBean
	 * @param listContratoUsuarioLogado
	 * @return
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 05.11.2013
	 */
	public List<TarefaFluxoDTO> recuperaTarefas(Integer qtdAtual, Integer qtdAPaginacao, String login, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado,
			List<TarefaFluxoDTO> listTarefas) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getJdbcAliasBPM());
		try {
			setTransacao(tc);
			List<TarefaFluxoDTO> result = new ArrayList();

			if (listTarefas == null || listTarefas.isEmpty()) {
				// RECUPERA TAREFAS SEM SOLICITACAOSERVICODTO
				listTarefas = super.recuperaTarefas(login);
			}

			if (listTarefas != null) {
				SolicitacaoServicoServiceEjb solicitacaoServicoServiceEjb = new SolicitacaoServicoServiceEjb();

				Collection<SolicitacaoServicoDTO> listSolicitacaoServico = solicitacaoServicoServiceEjb.listByTarefas(listTarefas, qtdAtual, qtdAPaginacao, gerenciamentoBean,
						listContratoUsuarioLogado, tc);

				Collection<SolicitacaoServicoDTO> listSolicitacoesFilhas = solicitacaoServicoServiceEjb.listSolicitacoesFilhasFiltradas(gerenciamentoBean, listContratoUsuarioLogado, tc);

				if (listSolicitacaoServico != null && !listSolicitacaoServico.isEmpty()) {

					int cont = 0;

					for (SolicitacaoServicoDTO solicitacaoServicoDto : listSolicitacaoServico) {

						for (TarefaFluxoDTO tarefaDto : listTarefas) {

							if (cont == qtdAPaginacao) {
								break;
							}

							if (solicitacaoServicoDto.getIdTarefa().equals(tarefaDto.getIdItemTrabalho())) {

								boolean possuiFilho = false;

								if (listSolicitacoesFilhas != null && !listSolicitacoesFilhas.isEmpty()) {
									for (SolicitacaoServicoDTO solicitacaoServicoDTO2 : listSolicitacoesFilhas) {
										if (solicitacaoServicoDto.getIdSolicitacaoServico().equals(solicitacaoServicoDTO2.getIdSolicitacaoPai())) {
											possuiFilho = true;
											break;
										}
									}
								}

								solicitacaoServicoDto.setPossuiFilho(possuiFilho);

								tarefaDto.setSolicitacaoDto(solicitacaoServicoDto);
								tarefaDto.setDataHoraLimite(solicitacaoServicoDto.getDataHoraLimite());
								result.add(tarefaDto);

								cont++;
							}
						}
					}
				}
			}
			return result;
		} finally {
			tc.closeQuietly();
		}
	}

	/**
	 * Utilizado para a RENDERIZAÇÃO do GRÁFICO, pois no Gráfico não é necessário a utilização de Paginação. Esta consulta considera o Login do Usuário Logado, os Contratos em que está inserido e os
	 * Filtros Selecionados na tela de Gerenciamento de Serviços.
	 *
	 * @param loginUsuario
	 * @param gerenciamentoBean
	 * @return List<TarefaFluxoDTO - Com SolicitacaoServicoDTO recuperados.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 05.11.2013
	 */
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado, List<TarefaFluxoDTO> listTarefas)
			throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getJdbcAliasBPM());
		try {
			setTransacao(tc);
			List<TarefaFluxoDTO> result = new ArrayList();

			if (listTarefas == null || listTarefas.isEmpty()) {
				// RECUPERA TAREFAS SEM SOLICITACAOSERVICODTO
				listTarefas = super.recuperaTarefas(loginUsuario);
			}

			if (listTarefas != null) {
				SolicitacaoServicoServiceEjb solicitacaoServicoServiceEjb = new SolicitacaoServicoServiceEjb();

				Collection<SolicitacaoServicoDTO> listSolicitacaoServicoDto = solicitacaoServicoServiceEjb.listByTarefas(listTarefas, gerenciamentoBean, listContratoUsuarioLogado, tc);

				Collection<SolicitacaoServicoDTO> listSolicitacoesFilhas = solicitacaoServicoServiceEjb.listSolicitacoesFilhas(tc);

				if (listSolicitacaoServicoDto != null && !listSolicitacaoServicoDto.isEmpty()) {

					for (SolicitacaoServicoDTO solicitacaoServicoDto : listSolicitacaoServicoDto) {

						for (TarefaFluxoDTO tarefaDto : listTarefas) {

							if (solicitacaoServicoDto.getIdTarefa().equals(tarefaDto.getIdItemTrabalho())) {

								boolean possuiFilho = false;

								if (listSolicitacoesFilhas != null && !listSolicitacoesFilhas.isEmpty()) {

									for (SolicitacaoServicoDTO solicitacaoServicoDTO2 : listSolicitacoesFilhas) {

										if (solicitacaoServicoDto.getIdSolicitacaoServico().equals(solicitacaoServicoDTO2.getIdSolicitacaoPai())) {
											possuiFilho = true;
											break;
										}
									}
								}

								solicitacaoServicoDto.setPossuiFilho(possuiFilho);

								tarefaDto.setSolicitacaoDto(solicitacaoServicoDto);
								tarefaDto.setDataHoraLimite(solicitacaoServicoDto.getDataHoraLimite());
								result.add(tarefaDto);
							}
						}
					}
				}

				if (result != null) {
					Collections.sort(result, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.ASC));
				}
			}
			return result;
		} finally {
			tc.closeQuietly();
		}
	}

	public boolean permiteAprovacaoAlcada(AlcadaProcessoNegocioDTO alcadaProcessoNegocioDto, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		return true;
	}

	public void calculaValorAprovadoAnual(CentroResultadoDTO centroResultadoDto, int anoRef, TransactionControler tc) throws Exception {
		valorAnualAtendCliente = 0.0;
		valorAnualUsoInterno = 0.0;
	}

	public void calculaValorAprovadoMensal(CentroResultadoDTO centroResultadoDto, int mesRef, int anoRef, TransactionControler tc) throws Exception {
		valorMensalAtendCliente = 0.0;
		valorMensalUsoInterno = 0.0;
	}

	public double calculaValorAprovado(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		return 0.0;
	}

	public boolean isAtendimentoCliente(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		return false;
	}

	public ExecucaoSolicitacaoDTO getExecucaoSolicitacaoDto() {
		return execucaoSolicitacaoDto;
	}

	public void setExecucaoSolicitacaoDto(ExecucaoSolicitacaoDTO execucaoSolicitacaoDto) {
		this.execucaoSolicitacaoDto = execucaoSolicitacaoDto;
	}

	public void setValorMensalUsoInterno(Double valorMensalUsoInterno) {
		this.valorMensalUsoInterno = valorMensalUsoInterno;
	}

	public void setValorAnualUsoInterno(Double valorAnualUsoInterno) {
		this.valorAnualUsoInterno = valorAnualUsoInterno;
	}

	public void setValorMensalAtendCliente(Double valorMensalAtendCliente) {
		this.valorMensalAtendCliente = valorMensalAtendCliente;
	}

	public void setValorAnualAtendCliente(Double valorAnualAtendCliente) {
		this.valorAnualAtendCliente = valorAnualAtendCliente;
	}

	public void cancelarRequisicao() {
		getSolicitacaoServicoDto().setSituacao(br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Cancelada.getDescricao());
	}

	protected String getJdbcAliasBPM() throws Exception {
		String jdbcAlias = CITCorporeUtil.JDBC_ALIAS_BPM;
		if (jdbcAlias == null || jdbcAlias.trim().equals(""))
			jdbcAlias = Constantes.getValue("DATABASE_ALIAS");
		return jdbcAlias;
	}

	private SolicitacaoServicoDao solicitacaoServicoDao;

	private SolicitacaoServicoDao getSolicitacaoServicoDAO() {
		if (solicitacaoServicoDao == null) {
			solicitacaoServicoDao = new SolicitacaoServicoDao();
		}
		return solicitacaoServicoDao;
	}

	/**
	 * Consulta utilizada para a RENDERIZAÇÃO da LISTAGEM SOLICITACAO SERVIÇO. Retorna a Lista de TarefaDTO com SolicitacaoServidoDTO de acordo com po aramRecuperacaoTarefasDto.
	 *
	 * @param ParamRecuperacaoTarefasDTO
	 *            paramRecuperacaoTarefasDto
	 * @return List<TarefaFluxoDTO - Com SolicitacaoServicoDTO recuperados.
	 * @throws Exception
	 * @author carlos.santos
	 * @since 27.01.2015 - Operação Usain Bolt.
	 */
	public Page<TarefaFluxoDTO> recuperaTarefas(ParamRecuperacaoTarefasDTO paramRecuperacaoTarefas, Pageable pageable) throws Exception {
		List<TarefaFluxoDTO> tarefas = new ArrayList<>();

		Page<TarefaFluxoDTO> resultPage = new PageImpl<>(tarefas, pageable);

		if (paramRecuperacaoTarefas.getLoginUsuario() == null)
			return resultPage;

		TransactionControler tc = new TransactionControlerImpl(getJdbcAliasBPM());
		try {
			setTransacao(tc);
			UsuarioDao usuarioDao = new UsuarioDao();
			setTransacaoDao(usuarioDao);
			UsuarioDTO usuarioDto = usuarioDao.restoreByLogin(paramRecuperacaoTarefas.getLoginUsuario());
			if (usuarioDto == null)
				return resultPage;

			GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
			setTransacaoDao(grupoEmpregadoDao);
			usuarioDto.setColGrupoEmpregado(grupoEmpregadoDao.findByIdEmpregado(usuarioDto.getIdEmpregado()));
			paramRecuperacaoTarefas.setUsuarioDto(usuarioDto);

			TarefaUsuarioDao tarefaUsuarioDao = new TarefaUsuarioDao();
			setTransacaoDao(tarefaUsuarioDao);
			Page<TarefaUsuarioDTO> pageTarefasUsuario = tarefaUsuarioDao.recuperaTarefas(paramRecuperacaoTarefas, pageable);
			List<TarefaUsuarioDTO> tarefasUsuario = pageTarefasUsuario.getContent();
			if (tarefasUsuario == null || tarefasUsuario.isEmpty()) {
				resultPage = new PageImpl<>(tarefas, pageable, pageTarefasUsuario.getTotalElements());
				return resultPage;
			}

			SolicitacaoServicoServiceEjb solicitacaoServicoService = new SolicitacaoServicoServiceEjb();
			for (TarefaUsuarioDTO tarefaUsuarioDto : tarefasUsuario) {
				TarefaFluxoDTO tarefaFluxo = tarefaUsuarioDto.converteTarefaFluxoDto();
				SolicitacaoServicoDTO solicitacaoDto = (SolicitacaoServicoDTO) tarefaFluxo.getSolicitacaoDto();
				tarefas.add(tarefaFluxo);

				if (!paramRecuperacaoTarefas.isSomenteTotalizacao())
					solicitacaoDto = solicitacaoServicoService.verificaSituacaoSLA(solicitacaoDto, tc);
			}

			if (!paramRecuperacaoTarefas.isSomenteTotalizacao()) {
				Map<String, ElementoFluxoTarefaDTO> mapElementos = new HashMap<>();
				Map<String, PermissoesFluxoDTO> mapPermissoes = new HashMap<>();
				Map<String, UsuarioDTO> mapUsuarios = new HashMap<>();
				Map<String, GrupoDTO> mapGrupos = new HashMap<>();

				PermissoesFluxoDao permissoesFluxoDao = new PermissoesFluxoDao();
				setTransacaoDao(permissoesFluxoDao);

				GrupoDao grupoDao = new GrupoDao();
				setTransacaoDao(grupoDao);

				ElementoFluxoDao elementoFluxoDao = new ElementoFluxoDao();
				setTransacaoDao(elementoFluxoDao);

				AtribuicaoFluxoDao atribuicaoTarefaDao = new AtribuicaoFluxoDao();
				setTransacaoDao(atribuicaoTarefaDao);

				for (TarefaFluxoDTO tarefaFluxo : tarefas) {
					String executar = "N";
					String delegar = "N";
					String suspender = "N";
					String reativar = "N";
					String alterarSLA = "N";

					if (usuarioDto.getColGrupoEmpregado() != null && !tarefaFluxo.isSomenteAcompanhamento()) {
						PermissoesFluxoDTO permissoesFluxo = null;
						for (GrupoEmpregadoDTO grupoEmpregadoDto : usuarioDto.getColGrupoEmpregado()) {
							String chave = grupoEmpregadoDto.getIdGrupo() + "|" + tarefaFluxo.getIdTipoFluxo();
							permissoesFluxo = mapPermissoes.get(chave);
							if (permissoesFluxo == null) {
								permissoesFluxo = new PermissoesFluxoDTO();
								permissoesFluxo.setIdTipoFluxo(tarefaFluxo.getIdTipoFluxo());
								permissoesFluxo.setIdGrupo(grupoEmpregadoDto.getIdGrupo());
								permissoesFluxo = (PermissoesFluxoDTO) permissoesFluxoDao.restore(permissoesFluxo);
								if (permissoesFluxo == null)
									continue;
								mapPermissoes.put(chave, permissoesFluxo);
							}
							if (!executar.equalsIgnoreCase("S") && permissoesFluxo.getExecutar() != null && permissoesFluxo.getExecutar().equals("S"))
								executar = "S";
							if (!delegar.equalsIgnoreCase("S") && permissoesFluxo.getDelegar() != null && permissoesFluxo.getDelegar().equals("S"))
								delegar = "S";
							if (!suspender.equalsIgnoreCase("S") && permissoesFluxo.getSuspender() != null && permissoesFluxo.getSuspender().equals("S"))
								suspender = "S";
							if (!reativar.equalsIgnoreCase("S") && permissoesFluxo.getReativar() != null && permissoesFluxo.getReativar().equals("S"))
								reativar = "S";
							if (!alterarSLA.equalsIgnoreCase("S") && permissoesFluxo.getAlterarSLA() != null && permissoesFluxo.getAlterarSLA().equals("S"))
								alterarSLA = "S";
						}
					}
					tarefaFluxo.setExecutar(executar);
					tarefaFluxo.setDelegar(delegar);
					tarefaFluxo.setSuspender(suspender);
					tarefaFluxo.setReativar(reativar);
					tarefaFluxo.setAlterarSLA(alterarSLA);

					ElementoFluxoTarefaDTO elementoFluxo = mapElementos.get("" + tarefaFluxo.getIdElemento());
					if (elementoFluxo == null) {
						try {
							elementoFluxo = (ElementoFluxoTarefaDTO) elementoFluxoDao.restore(tarefaFluxo.getIdElemento());
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (elementoFluxo == null)
							throw new LogicException("Erro na recuperação do elemento de fluxo com ID " + tarefaFluxo.getIdElemento() + " na instância " + tarefaFluxo.getIdInstancia());
						mapElementos.put("" + tarefaFluxo.getIdElemento(), elementoFluxo);
					}

					tarefaFluxo.setElementoFluxoDto(elementoFluxo);
					tarefaFluxo.setCompartilhamento("");
					AtribuicaoFluxoDTO atribuicaoDelegacao = recuperaDelegacao(tarefaFluxo.getIdItemTrabalho(), atribuicaoTarefaDao);
					if (atribuicaoDelegacao == null)
						continue;
					if (atribuicaoDelegacao.getIdUsuario() != null) {
						if (!atribuicaoDelegacao.getIdUsuario().equals(usuarioDto.getIdUsuario())) {
							UsuarioDTO usuarioAtualDto = mapUsuarios.get("" + atribuicaoDelegacao.getIdUsuario());
							if (usuarioAtualDto == null) {
								usuarioAtualDto = usuarioDao.restoreByID(atribuicaoDelegacao.getIdUsuario());
								if (usuarioAtualDto == null)
									continue;
								mapUsuarios.put("" + atribuicaoDelegacao.getIdUsuario(), usuarioAtualDto);
							}
							tarefaFluxo.setCompartilhamento(usuarioAtualDto.getNomeUsuario());
						}
					} else {
						GrupoDTO grupoAtual = mapGrupos.get("" + atribuicaoDelegacao.getIdGrupo());
						if (grupoAtual == null) {
							grupoAtual = new GrupoDTO();
							grupoAtual.setIdGrupo(atribuicaoDelegacao.getIdGrupo());
							grupoAtual = (GrupoDTO) grupoDao.restore(grupoAtual);
							if (grupoAtual == null)
								continue;
							mapGrupos.put("" + atribuicaoDelegacao.getIdGrupo(), grupoAtual);
						}
						tarefaFluxo.setCompartilhamento(grupoAtual.getSigla());
					}
				}
			}
			resultPage = new PageImpl<>(tarefas, pageable);
			return resultPage;
		} finally {
			tc.closeQuietly();
		}
	}

}
