package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ObjetoInstanciaFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.ObjetoInstanciaFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ExecucaoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.ParamRecuperacaoTarefasDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoSolicitacao;
import br.com.centralit.citcorpore.integracao.ExecucaoSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.FluxoServicoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

@SuppressWarnings("unchecked")
public class ExecucaoSolicitacaoServiceEjb extends CrudServiceImpl implements ExecucaoSolicitacaoService {

	private static List<TarefaFluxoDTO> listTarefas;

	private ExecucaoSolicitacaoDao dao;

	/**
	 * Service de Contrato.
	 *
	 * @author valdoilo.damasceno
	 */
	private ContratoService contratoService;

	@Override
	protected ExecucaoSolicitacaoDao getDao() {
		if (dao == null) {
			dao = new ExecucaoSolicitacaoDao();
		}
		return dao;
	}

	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(final String loginUsuario) throws Exception {
		return new ExecucaoSolicitacao().recuperaTarefas(loginUsuario);
	}

	@Override
	public TarefaFluxoDTO recuperaTarefa(final String loginUsuario, final Integer idTarefa) throws Exception {
		TarefaFluxoDTO result = null;
		final List<TarefaFluxoDTO> lstTarefas = new ExecucaoSolicitacao().recuperaTarefas(loginUsuario, idTarefa);
		if (!lstTarefas.isEmpty()) {
			for (final TarefaFluxoDTO tarefaDto : lstTarefas) {
				if (tarefaDto.getIdItemTrabalho().intValue() == idTarefa.intValue()) {
					result = tarefaDto;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public void delegaTarefa(final String loginUsuario, final Integer idTarefa, final String usuarioDestino, final String grupoDestino) throws Exception {
		if (idTarefa == null) {
			return;
		}
		final TarefaFluxoDTO tarefaDto = this.recuperaTarefa(idTarefa);
		final ExecucaoSolicitacaoDTO execucaoSolicitacaoDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());

		final SolicitacaoServicoDTO solicitacaoDto = new SolicitacaoServicoServiceEjb().restoreAll(execucaoSolicitacaoDto.getIdSolicitacaoServico());

		final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

		try {
			tc.start();

			final ExecucaoSolicitacao execucaoSolicitacao = this.getExecucaoSolicitacao(solicitacaoDto, tc);
			execucaoSolicitacao.delega(loginUsuario, solicitacaoDto, idTarefa, usuarioDestino, grupoDestino);

			tc.commit();

		} catch (final Exception e) {
			tc.rollback();
		} finally {
			try {
				tc.close();
			} catch (final PersistenceException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public TarefaFluxoDTO recuperaTarefa(final Integer idTarefa) throws Exception {
		final TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
		TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
		tarefaFluxoDto.setIdItemTrabalho(idTarefa);
		tarefaFluxoDto = (TarefaFluxoDTO) tarefaFluxoDao.restore(tarefaFluxoDto);
		final ElementoFluxoDTO elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
		tarefaFluxoDto.setElementoFluxoDto(elementoDto);
		return tarefaFluxoDto;
	}

	@Override
	public void trataCamposTarefa(final Map<String, String> params, final Collection<CamposObjetoNegocioDTO> colCampos, final Map<String, Object> map, final String principal,
			final TransactionControler tc) throws Exception {
		if (colCampos == null) {
			return;
		}

		final Gson gson = new Gson();

		final ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
		objetoNegocioDao.setTransactionControler(tc);
		final HashMap<Integer, ObjetoNegocioDTO> mapObjetos = new HashMap<>();

		for (final CamposObjetoNegocioDTO campoDto : colCampos) {
			final String value = params.get(campoDto.getNome());
			if (value == null) {
				return;
			}

			String nomeTabelaBD = "";

			if (campoDto.getNomeTabelaDB() != null) {
				nomeTabelaBD = campoDto.getNomeTabelaDB();
				nomeTabelaBD = campoDto.getNomeTabelaDB().toLowerCase();
			} else if (campoDto.getIdObjetoNegocio() != null) {
				ObjetoNegocioDTO objetoNegocioDto = mapObjetos.get(campoDto.getIdObjetoNegocio());
				if (objetoNegocioDto == null) {
					objetoNegocioDto = new ObjetoNegocioDTO();
					objetoNegocioDto.setIdObjetoNegocio(campoDto.getIdObjetoNegocio());
					objetoNegocioDto = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDto);
					if (objetoNegocioDto != null) {
						mapObjetos.put(campoDto.getIdObjetoNegocio(), objetoNegocioDto);
					}
				}
				if (objetoNegocioDto != null) {
					nomeTabelaBD = objetoNegocioDto.getNomeTabelaDB();
				}
			}

			final ObjetoInstanciaFluxoDTO objetoInstanciaDto = new ObjetoInstanciaFluxoDTO();
			objetoInstanciaDto.setIdObjetoNegocio(campoDto.getIdObjetoNegocio());
			objetoInstanciaDto.setObjetoPrincipal(principal);
			objetoInstanciaDto.setCampoChave(campoDto.getPk());
			objetoInstanciaDto.setNomeObjeto(campoDto.getNome().toLowerCase());
			objetoInstanciaDto.setNomeTabelaBD(nomeTabelaBD);
			objetoInstanciaDto.setNomeCampoBD(campoDto.getNomeDB());
			objetoInstanciaDto.setTipoCampoBD(campoDto.getTipoDB());
			objetoInstanciaDto.setNomeClasse(String.class.getName());
			objetoInstanciaDto.setValor(gson.toJson(value));

			map.put(objetoInstanciaDto.getNomeObjeto(), objetoInstanciaDto);
		}
	}

	@Override
	public Collection<GrupoVisaoCamposNegocioDTO> findCamposTarefa(final Integer idTarefa) throws Exception {
		final TarefaFluxoDTO tarefaDto = this.recuperaTarefa(idTarefa);
		if (tarefaDto == null) {
			return null;
		}

		Collection<GrupoVisaoCamposNegocioDTO> result = null;
		result = new ArrayList<>();
		final Collection<ObjetoInstanciaFluxoDTO> colCampos = this.getObjetoInstanciaFluxoDAO().findByIdTarefa(idTarefa);
		if (colCampos != null) {
			final Gson gson = new Gson();
			for (final ObjetoInstanciaFluxoDTO campoTarefaDto : colCampos) {
				if (campoTarefaDto.getObjetoPrincipal().equalsIgnoreCase("S") && campoTarefaDto.getCampoChave().equalsIgnoreCase("S")) {
					final GrupoVisaoCamposNegocioDTO grupoCampoDto = new GrupoVisaoCamposNegocioDTO();
					final CamposObjetoNegocioDTO campoDto = new CamposObjetoNegocioDTO();
					campoDto.setIdObjetoNegocio(campoTarefaDto.getIdObjetoNegocio());
					campoDto.setNome(campoTarefaDto.getNomeObjeto());
					campoDto.setNomeDB(campoTarefaDto.getNomeCampoBD());
					campoDto.setPk("S");
					campoDto.setValue(gson.fromJson(campoTarefaDto.getValor(), String.class));
					campoDto.setTipoDB(campoTarefaDto.getTipoCampoBD());
					grupoCampoDto.setCamposObjetoNegocioDto(campoDto);
					result.add(grupoCampoDto);
				}
			}
		} else {
			final ExecucaoSolicitacaoDTO execucaoSolicitacaoDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
			if (execucaoSolicitacaoDto != null) {
				final ObjetoNegocioDTO objetoNegocioDto = new ObjetoNegocioDao().findByNomeObjetoNegocio("SolicitacaoServico");
				if (objetoNegocioDto == null) {
					return null;
				}

				final GrupoVisaoCamposNegocioDTO grupoCampoDto = new GrupoVisaoCamposNegocioDTO();
				final CamposObjetoNegocioDTO campoDto = new CamposObjetoNegocioDTO();
				campoDto.setNome("IDSOLICITACAOSERVICO");
				campoDto.setNomeDB("IDSOLICITACAOSERVICO");
				campoDto.setIdObjetoNegocio(objetoNegocioDto.getIdObjetoNegocio());
				campoDto.setPk("S");
				campoDto.setValue(execucaoSolicitacaoDto.getIdSolicitacaoServico());
				grupoCampoDto.setCamposObjetoNegocioDto(campoDto);
				result.add(grupoCampoDto);
			}
		}
		return result;
	}

	public TipoFluxoDTO recuperaFluxoServico(final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc) throws Exception {
		Integer idServicoContrato = solicitacaoServicoDto.getIdServicoContrato();
		if (solicitacaoServicoDto.getIdServicoContrato() == null || solicitacaoServicoDto.getIdServicoContrato().intValue() <= 0) {
			SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoDTO();
			solicitacaoAuxDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
			if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("sqlserver")) {
				if (tc != null) {
					this.getSolicitacaoDAO().setTransactionControler(tc);
				}
			}
			solicitacaoAuxDto = (SolicitacaoServicoDTO) this.getSolicitacaoDAO().restore(solicitacaoAuxDto);
			idServicoContrato = solicitacaoAuxDto.getIdServicoContrato();
		}
		TipoFluxoDTO tipoFluxoDto = null;
		if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("sqlserver")) {
			if (tc != null) {
				this.getTipoFluxoDAO().setTransactionControler(tc);
			}
		}
		final FluxoServicoDTO fluxoServicoDto = new FluxoServicoDao().findPrincipalByIdServicoContrato(idServicoContrato);
		if (fluxoServicoDto != null) {
			tipoFluxoDto = new TipoFluxoDTO();
			tipoFluxoDto.setIdTipoFluxo(fluxoServicoDto.getIdTipoFluxo());
			tipoFluxoDto = (TipoFluxoDTO) this.getTipoFluxoDAO().restore(tipoFluxoDto);
		} else {
			final String fluxoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NomeFluxoPadraoServicos, "SolicitacaoServico");
			if (fluxoPadrao != null) {
				tipoFluxoDto = this.getTipoFluxoDAO().findByNome(fluxoPadrao);
			}
		}
		if (tipoFluxoDto == null) {
			throw new Exception("O fluxo associado ao serviço não foi parametrizado");
		}
		return tipoFluxoDto;
	}

	public ExecucaoSolicitacao getExecucaoSolicitacao(final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc) throws Exception {
		final TipoFluxoDTO tipoFluxoDto = this.recuperaFluxoServico(solicitacaoServicoDto, tc);
		if (tipoFluxoDto.getNomeClasseFluxo() != null && !tipoFluxoDto.getNomeClasseFluxo().trim().equals("")) {
			try {
				final ExecucaoSolicitacao execucaoSolicitacao = (ExecucaoSolicitacao) Class.forName(tipoFluxoDto.getNomeClasseFluxo()).newInstance();
				execucaoSolicitacao.setTransacao(tc);
				execucaoSolicitacao.setObjetoNegocioDto(solicitacaoServicoDto);
				return execucaoSolicitacao;
			} catch (final Exception e) {
				if((Class.forName(tipoFluxoDto.getNomeClasseFluxo()).newInstance() instanceof ExecucaoSolicitacao) == false){
					throw new Exception("execucaosolicitacao.invalido.fluxodeproblema");
				}
				e.printStackTrace();
				return null;
			}
		} else {
			return new ExecucaoSolicitacao(solicitacaoServicoDto, tc);
		}
	}

	@Override
	public ExecucaoSolicitacao registraSolicitacao(final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc) throws Exception {
		final ExecucaoSolicitacao execucaoSolicitacao = this.getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		if(execucaoSolicitacao == null)
			return null;
		execucaoSolicitacao.inicia();
		return execucaoSolicitacao;
	}


	@Override
	public void executa(final UsuarioDTO usuarioDto, final TransactionControler tc, final Integer idFluxo, final Integer idTarefa, final String acaoFluxo, final Map<String, String> params,
			final Collection<CamposObjetoNegocioDTO> colCamposTodosPrincipal, final Collection<CamposObjetoNegocioDTO> colCamposTodosVinc) throws Exception {
		final HashMap<String, Object> map = new HashMap<>();
		this.trataCamposTarefa(params, colCamposTodosPrincipal, map, "S", tc);
		this.trataCamposTarefa(params, colCamposTodosVinc, map, "N", tc);
		final Integer idSolicitacao = new Integer((String) map.get("IDSOLICITACAOSERVICO"));
		final SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoServiceEjb().restoreAll(idSolicitacao, tc);

		final ExecucaoSolicitacao execucaoSolicitacao = this.getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.executa(usuarioDto.getLogin(), solicitacaoServicoDto, idTarefa, acaoFluxo, map);
	}

	public SolicitacaoServicoDTO executar(final UsuarioDTO usuarioDto, final Integer idTarefa, final String acaoFluxo, TransactionControler tc) throws Exception {
		final TarefaFluxoDTO tarefaDto = this.recuperaTarefa(idTarefa);
		if (tarefaDto == null) {
			throw new Exception("Problemas na recuperação da Tarefa ");
		}

		if (tc == null) {
			tc = new TransactionControlerImpl(this.getDao().getAliasDB());
		}

		final ExecucaoSolicitacaoDTO execucaoSolicitacaoDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
		if (execucaoSolicitacaoDto == null) {
			throw new Exception("Problemas na recuperação da Solicitação ");
		}

		final SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
		solicitacaoServicoDto.setIdSolicitacaoServico(execucaoSolicitacaoDto.getIdSolicitacaoServico());
		solicitacaoServicoDto.setUsuarioDto(usuarioDto);
		this.executa(solicitacaoServicoDto, idTarefa, acaoFluxo, tc);

		return solicitacaoServicoDto;
	}

	@Override
	public void executa(final UsuarioDTO usuarioDto, final Integer idTarefa, final String acaoFluxo) throws Exception {
		final TarefaFluxoDTO tarefaDto = this.recuperaTarefa(idTarefa);
		if (tarefaDto == null) {
			return;
		}

		final ExecucaoSolicitacaoDTO execucaoSolicitacaoDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
		if (execucaoSolicitacaoDto == null) {
			return;
		}

		final SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
		solicitacaoServicoDto.setIdSolicitacaoServico(execucaoSolicitacaoDto.getIdSolicitacaoServico());
		solicitacaoServicoDto.setUsuarioDto(usuarioDto);

		final TransactionControlerImpl tc = new TransactionControlerImpl(this.getDao().getAliasDB());
		try {

			tc.start();

			this.executa(solicitacaoServicoDto, idTarefa, acaoFluxo, tc);

			tc.commit();

		} catch (final Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		} finally {
			try {
				tc.close();
			} catch (final PersistenceException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void cancelaTarefa(final String loginUsuario, final Integer idTarefa, final String motivo, final TransactionControler tc) throws Exception {
		final TarefaFluxoDTO tarefaDto = this.recuperaTarefa(idTarefa);
		if (tarefaDto == null) {
			return;
		}

		final ExecucaoSolicitacaoDTO execucaoSolicitacaoDto = this.getDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
		if (execucaoSolicitacaoDto == null) {
			return;
		}

		final SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoServiceEjb().restoreAll(execucaoSolicitacaoDto.getIdSolicitacaoServico(), null);
		final ExecucaoSolicitacao execucaoSolicitacao = this.getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.cancelaTarefa(loginUsuario, solicitacaoServicoDto, tarefaDto, motivo);
	}

	@Override
	public void executa(final SolicitacaoServicoDTO solicitacaoServicoDto, final Integer idTarefa, final String acaoFluxo, final TransactionControler tc) throws Exception {
		final HashMap<String, Object> objetos = new HashMap<>();
		final SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), tc);
		final ExecucaoSolicitacao execucaoSolicitacao = this.getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.executa(solicitacaoServicoDto.getUsuarioDto().getLogin(), solicitacaoAuxDto, idTarefa, acaoFluxo, objetos);
	}

	public void direcionaAtendimento(final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc) throws Exception {
		if (solicitacaoServicoDto.getIdGrupoAtual() == null) {
			return;
		}

		final SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), tc);

		if (solicitacaoAuxDto == null) {
			throw new Exception("Problemas na recuperação da solicitação");
		}

		if (solicitacaoAuxDto.getGrupoAtual() == null || solicitacaoAuxDto.getGrupoAtual().length() == 0) {
			throw new Exception("Grupo executor não encontrado");
		}

		this.getExecucaoSolicitacao(solicitacaoAuxDto, tc).direcionaAtendimento(solicitacaoServicoDto.getUsuarioDto().getLogin(), solicitacaoAuxDto, solicitacaoAuxDto.getGrupoAtual());
	}

	public void direcionaAtendimentoAutomatico(final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc) throws Exception {
		if (solicitacaoServicoDto.getIdGrupoAtual() == null) {
			return;
		}

		final SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), tc);

		if (solicitacaoAuxDto != null && solicitacaoAuxDto.getIdResponsavel() == null) {
			solicitacaoAuxDto.setIdResponsavel(1);
		}

		if (solicitacaoAuxDto == null) {
			throw new Exception("Problemas na recuperação da solicitação");
		}

		if (solicitacaoAuxDto.getIdGrupoAtual() == null || solicitacaoAuxDto.getIdGrupoAtual() == 0) {
			throw new Exception("Grupo executor não encontrado");
		}

		this.getExecucaoSolicitacao(solicitacaoAuxDto, tc).direcionaAtendimento("admin", solicitacaoAuxDto, solicitacaoAuxDto.getGrupoAtual());
	}

	public void encerra(final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc) throws Exception {
		final ExecucaoSolicitacao execucaoSolicitacao = this.getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.encerra();
	}

	public void reabre(final UsuarioDTO usuarioDto, final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc) throws Exception {
		final ExecucaoSolicitacao execucaoSolicitacao = this.getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.reabre(usuarioDto.getLogin());
	}

	public void suspende(final UsuarioDTO usuarioDto, final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc) throws Exception {
		final ExecucaoSolicitacao execucaoSolicitacao = this.getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.suspende(usuarioDto.getLogin());
	}

	public void reativa(final UsuarioDTO usuarioDto, final SolicitacaoServicoDTO solicitacaoServicoDto, final TransactionControler tc) throws Exception {
		final ExecucaoSolicitacao execucaoSolicitacao = this.getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.reativa(usuarioDto.getLogin());
	}

	public void determinaPrazoLimiteSolicitacaoACombinarReclassificada(final SolicitacaoServicoDTO solicitacaoServicoDto, final Integer idCalendario, final TransactionControler tc) throws Exception {
		final ExecucaoSolicitacao execucaoSolicitacao = this.getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		if (execucaoSolicitacao != null) {
			execucaoSolicitacao.determinaPrazoLimiteSolicitacaoACombinarReclassificada(solicitacaoServicoDto, idCalendario);
		}
	}

	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(final Integer pgAtual, final Integer qtdAPaginacao, final String login) throws Exception {
		return new ExecucaoSolicitacao().recuperaTarefas(pgAtual, qtdAPaginacao, login);
	}

	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(final String loginUsuario, final TipoSolicitacaoServico[] tiposSolicitacao, final String somenteEmAprovacao) throws Exception {
		return new ExecucaoSolicitacao().recuperaTarefas(loginUsuario, tiposSolicitacao, somenteEmAprovacao);
	}

	@Override
	public Integer totalPaginas(final Integer itensPorPagina, final String loginUsuario) throws Exception {
		return new ExecucaoSolicitacao().totalPaginas(itensPorPagina, loginUsuario);
	}

	public List<TarefaFluxoDTO> getListTarefas() {
		return listTarefas;
	}

	@Override
	public Integer obterTotalDePaginas(final Integer itensPorPagina, final String loginUsuario, final GerenciamentoServicosDTO gerenciamentoBean,
			final Collection<ContratoDTO> listContratoUsuarioLogado, final boolean isPortal) throws Exception {
		Integer total = 0;

		// ESSA LISTA DE TAREFAS JÁ ESTÁ VINDO COM A SOLICITACAOSERVICODTO E NÃO DEVERIA VIR. CRIAR MÉTODO PARA TRAZER APENAS AS TAREFAS COM O IDINSTANCIA, QUE É A ÚNICA INFORMAÇÃO
		// UTILIZADA NA
		// CONSULTA ABAIXO.
		// List<TarefaFluxoDTO> listTarefasComSolicitacaoServico = recuperaTarefas(loginUsuario);
		//
		// listTarefas = listTarefasComSolicitacaoServico;
		// Comentado para centalizar o método abaixo

		if (listTarefas != null) {
			total = this.getSolicitacaoDAO().totalDePaginas(itensPorPagina, this.getListTarefas(), gerenciamentoBean, listContratoUsuarioLogado);
		}
		if (isPortal) {
			// atualizaListaTarefas(loginUsuario, gerenciamentoBean);
			total = this.getSolicitacaoDAO().totalDePaginasPortal(itensPorPagina, null, gerenciamentoBean, listContratoUsuarioLogado);
			listTarefas = null;
		}

		return total;
	}

	@Override
	public Integer atualizarListaTarefasAndReturnTotalPaginas(UsuarioDTO usuarioLogado, GerenciamentoServicosDTO gerenciamentoServicos, Pageable pageable) throws Exception {
	    Integer totalPaginas = 0;

		if (usuarioLogado != null) {
			String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");

			if (COLABORADORES_VINC_CONTRATOS == null) {
				COLABORADORES_VINC_CONTRATOS = "N";
			}

			Collection<ContratoDTO> listContratoUsuarioLogado = null;

			if (COLABORADORES_VINC_CONTRATOS != null && COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
				listContratoUsuarioLogado = getContratoService().findAtivosByIdEmpregado(usuarioLogado.getIdEmpregado());
			} else {
				listContratoUsuarioLogado = getContratoService().listAtivos();
			}

			/** Alterado para chamar o novo método recuperaTarefas. 29.01.2015. Operação Usain Bolt. valdoilo.damasceno */
			ParamRecuperacaoTarefasDTO paramRecuperacaoTarefasDTO = new ParamRecuperacaoTarefasDTO(usuarioLogado.getLogin(), gerenciamentoServicos, listContratoUsuarioLogado);

			paramRecuperacaoTarefasDTO.setSomenteTotalizacao(true);
			Page<TarefaFluxoDTO> page = this.recuperaTarefas(paramRecuperacaoTarefasDTO, pageable);

			totalPaginas = page.getTotalPages();
			gerenciamentoServicos.setTotalPaginas(1);
		}
		return totalPaginas;
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
	public Page<TarefaFluxoDTO> recuperaTarefas(ParamRecuperacaoTarefasDTO paramRecuperacaoTarefasDto, Pageable pageable) throws Exception {
		listTarefas = null;
		return new ExecucaoSolicitacao().recuperaTarefas(paramRecuperacaoTarefasDto, pageable);
	}

	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(final Integer pgAtual, final Integer qtdAPaginacao, final String login, final GerenciamentoServicosDTO gerenciamentoBean,
			final Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception {

		final List<TarefaFluxoDTO> listTarefaFluxo = new ExecucaoSolicitacao().recuperaTarefas(pgAtual, qtdAPaginacao, login, gerenciamentoBean, listContratoUsuarioLogado, this.getListTarefas());

		return listTarefaFluxo;
	}

	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(final String loginUsuario, final GerenciamentoServicosDTO gerenciamentoBean, final Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception {

		final List<TarefaFluxoDTO> listTarefaFluxo = new ExecucaoSolicitacao().recuperaTarefas(loginUsuario, gerenciamentoBean, listContratoUsuarioLogado, this.getListTarefas());

		listTarefas = null;

		return listTarefaFluxo;
	}

	@Override
	public ExecucaoSolicitacaoDTO findByIdInstanciaFluxo(final Integer idInstanciaFluxo) throws Exception {
		return this.getDao().findByIdInstanciaFluxo(idInstanciaFluxo);
	}

	private ObjetoInstanciaFluxoDao objetoInstanciaFluxoDAO;
	private SolicitacaoServicoDao solicitacaoDAO;
	private TipoFluxoDao tipoDAO;

	private ObjetoInstanciaFluxoDao getObjetoInstanciaFluxoDAO() {
		if (objetoInstanciaFluxoDAO == null) {
			objetoInstanciaFluxoDAO = new ObjetoInstanciaFluxoDao();
		}
		return objetoInstanciaFluxoDAO;
	}

	private SolicitacaoServicoDao getSolicitacaoDAO() {
		if (solicitacaoDAO == null) {
			solicitacaoDAO = new SolicitacaoServicoDao();
		}
		return solicitacaoDAO;
	}

	private TipoFluxoDao getTipoFluxoDAO() {
		if (tipoDAO == null) {
			tipoDAO = new TipoFluxoDao();
		}
		return tipoDAO;
	}

	/**
	 * Retorna instância do Service de Contrato.
	 *
	 * @return ContratoService
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 27.01.2015 - Operação Usain Bolt
	 */
	private ContratoService getContratoService() throws Exception {
		if (contratoService == null) {
			contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		}
		return contratoService;
	}

}
