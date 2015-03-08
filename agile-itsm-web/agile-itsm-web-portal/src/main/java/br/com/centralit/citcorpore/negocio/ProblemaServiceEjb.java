package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CategoriaProblemaDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoProblemaDTO;
import br.com.centralit.citcorpore.bean.ContatoProblemaDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.JustificativaProblemaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaProblemaDTO;
import br.com.centralit.citcorpore.bean.PesquisaProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.centralit.citcorpore.bean.ProblemaRelacionadoDTO;
import br.com.centralit.citcorpore.bean.RelatorioProblemaIncidentesDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoProblemaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoProblemaDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.BaseConhecimentoDAO;
import br.com.centralit.citcorpore.integracao.CategoriaProblemaDAO;
import br.com.centralit.citcorpore.integracao.ConhecimentoProblemaDao;
import br.com.centralit.citcorpore.integracao.ContatoProblemaDAO;
import br.com.centralit.citcorpore.integracao.ContratoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.OcorrenciaProblemaDAO;
import br.com.centralit.citcorpore.integracao.PermissoesFluxoDao;
import br.com.centralit.citcorpore.integracao.ProblemaDAO;
import br.com.centralit.citcorpore.integracao.ProblemaItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.ProblemaMudancaDAO;
import br.com.centralit.citcorpore.integracao.ProblemaRelacionadoDao;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoProblemaDao;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoProblema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ProblemaServiceEjb extends CrudServiceImpl implements ProblemaService {

	private ProblemaDAO dao;
	private ProblemaItemConfiguracaoDAO problemaItemConfiguracaoDao;
	private ProblemaMudancaDAO problemaMudancaDao;
	private BaseConhecimentoService baseConhecimentoService;

	protected ProblemaDAO getDao() {
		if (dao == null) {
			dao = new ProblemaDAO();
		}
		return dao;
	}

	private BaseConhecimentoService getBaseConhecimentoService() throws ServiceException, Exception {
		if (baseConhecimentoService == null) {
			baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		}
		return baseConhecimentoService;
	}

	private ProblemaItemConfiguracaoDAO getProblemaItemConfiguracaoDao() {
		if (problemaItemConfiguracaoDao == null) {
			problemaItemConfiguracaoDao = new ProblemaItemConfiguracaoDAO();
		}
		return problemaItemConfiguracaoDao;
	}

	private ProblemaMudancaDAO getProblemaMudancaDao() {
		if (problemaMudancaDao == null) {
			problemaMudancaDao = new ProblemaMudancaDAO();
		}
		return problemaMudancaDao;
	}

	protected void validaCreate(Object arg0) throws Exception {
		ProblemaDTO dto = (ProblemaDTO) arg0;
		dto.setDataHoraInicio(new Timestamp(new java.util.Date().getTime()));
	}

	public void reativa(UsuarioDTO usuarioDto, ProblemaDTO problemaDto) throws Exception {

		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		try {
			tc.start();

			reativa(usuarioDto, problemaDto, tc);

			tc.commit();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		} finally {
			try {
				tc.close();
			} catch (PersistenceException e) {
				e.printStackTrace();
			}
		}
	}

	public void reativa(UsuarioDTO usuarioDto, ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		ProblemaDTO problemaAuxDto = restoreAll(problemaDto.getIdProblema(), tc);
		new ExecucaoProblemaServiceEjb().reativa(usuarioDto, problemaAuxDto, tc);
	}

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		// TODO DTO
		ContatoProblemaDTO contatoProblemaDto = new ContatoProblemaDTO();
		OcorrenciaProblemaDTO ocorrenciaProblemaDto = new OcorrenciaProblemaDTO();
		CategoriaProblemaDTO categoriaProblemaDto = new CategoriaProblemaDTO();
		ProblemaRelacionadoDTO problemaRelacionadoDto = null;
		ProblemaDTO problema = (ProblemaDTO) model;
		// TODO ServiceEjb
		ExecucaoProblemaServiceEjb execucaoProblemaServiceEjb = new ExecucaoProblemaServiceEjb();
		// TODO DAO
		ProblemaDAO problemaDao = this.getDao();
		SolicitacaoServicoProblemaDao solicitacaoServicoProblemaDao = new SolicitacaoServicoProblemaDao();
		ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
		ProblemaItemConfiguracaoDAO problemaItemConfiguracaoDao = new ProblemaItemConfiguracaoDAO();
		ProblemaMudancaDAO problemaMudancaDao = new ProblemaMudancaDAO();
		OcorrenciaProblemaDAO ocorrenciaProblemaDao = new OcorrenciaProblemaDAO();
		CategoriaProblemaDAO categoriaProblemaDao = new CategoriaProblemaDAO();
		ProblemaRelacionadoDao problemaRelacionadoDao = new ProblemaRelacionadoDao();

		TransactionControler tc = null;

		try {

			if (problemaDao != null && problemaDao.getTransactionControler() != null) {
				tc = problemaDao.getTransactionControler();
			} else {
				tc = new TransactionControlerImpl(problemaDao.getAliasDB());
			}

			if (!tc.isStarted()) {
				tc.start();
			}

			validaCreate(model);

			problemaDao.setTransactionControler(tc);
			solicitacaoServicoProblemaDao.setTransactionControler(tc);
			servicoContratoDao.setTransactionControler(tc);
			problemaItemConfiguracaoDao.setTransactionControler(tc);
			problemaMudancaDao.setTransactionControler(tc);
			ocorrenciaProblemaDao.setTransactionControler(tc);
			categoriaProblemaDao.setTransactionControler(tc);
			problemaRelacionadoDao.setTransactionControler(tc);

			contatoProblemaDto = this.criarContatoProblema(problema, tc);

			if (contatoProblemaDto.getIdContatoProblema() != null) {
				problema.setIdContatoProblema(contatoProblemaDto.getIdContatoProblema());
			}

			// Bruno.Aquino: comentado o setIdProprietario pois ao criar a solicitação o usuário logado estava ficando como responsável do problema criado, fugindo da regra de
			// negócio.

			problema.setDataHoraCaptura(UtilDatas.getDataHoraAtual());
			problema.setDataHoraSolicitacao(UtilDatas.getDataHoraAtual());
			// problema.setIdProprietario(problema.getUsuarioDto().getIdUsuario());
			problema.setStatus(SituacaoRequisicaoProblema.Registrada.name());

			if (problema.getDataHoraLimiteSolucionar() != null) {
				problema.setDataHoraLimite(transformaHoraFinal(problema.getDataHoraLimiteSolucionar()));
				// this.determinaPrazo(problema, problema.getIdCalendario());
			} else {
				problema.setPrazoHH(00);
				problema.setPrazoMM(00);
			}

			if (problema.getIdCategoriaProblema() != null) {

				categoriaProblemaDto.setIdCategoriaProblema(problema.getIdCategoriaProblema());
				categoriaProblemaDto = (CategoriaProblemaDTO) categoriaProblemaDao.restore(categoriaProblemaDto);
			}

			if (problema.getIdGrupo() == null) {
				if (categoriaProblemaDto != null) {
					if (categoriaProblemaDto.getIdGrupoExecutor() != null) {
						// problema.setIdGrupoNivel1(categoriaProblemaDto.getIdGrupoExecutor()); Não existe GrupoNivel1 e GrupoAtual na entidade Problema. Verificar DAO da entidade. valdoilo.damasceno
						problema.setIdGrupo(categoriaProblemaDto.getIdGrupoExecutor());
					} else {
						if (problema.getIdGrupoNivel1() == null || problema.getIdGrupoNivel1().intValue() <= 0) {
							Integer idGrupoNivel1 = null;
							String idGrupoN1 = ParametroUtil.getValor(ParametroSistema.ID_GRUPO_PADRAO_NIVEL1, tc, null);
							if (idGrupoN1 != null && !idGrupoN1.trim().equalsIgnoreCase("")) {
								try {
									idGrupoNivel1 = new Integer(idGrupoN1);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							if (idGrupoNivel1 == null || idGrupoNivel1.intValue() <= 0)
								throw new LogicException(i18nMessage("solicitacaoservico.validacao.servicolocalizado"));
							problema.setIdGrupoNivel1(idGrupoNivel1);
							problema.setIdGrupo(idGrupoNivel1);
						}
					}
				}
			}

			// Não existe GrupoNivel1 e GrupoAtual na entidade Problema. Verificar DAO da entidade. valdoilo.damasceno
			// else {
			// problema.setIdGrupoNivel1(problema.getIdGrupo());
			// problema.setIdGrupo(problema.getIdGrupo());
			// problema.setIdGrupoAtual(problema.getIdGrupo());
			// }

			if (!problema.getTitulo().equalsIgnoreCase("Problema Criado por Rotina automática")) {
				boolean resultado = validacaoGrupoExecutor(problema);
				if (resultado == false) {
					throw new LogicException(i18nMessage("Problema.grupoSemPermissao"));
				}
			} else {
				problema.setRegistroexecucao("Problema Criado por Rotina automática");
			}

			problemaRelacionadoDao.deleteByIdProblema(problema.getIdProblema());
			if (problema.getListProblemaRelacionadoDTO() != null && problema.getListProblemaRelacionadoDTO().size() > 0) {
				for (ProblemaDTO beanProblema : problema.getListProblemaRelacionadoDTO()) {
					problemaRelacionadoDto = new ProblemaRelacionadoDTO();
					problemaRelacionadoDto.setIdProblema(problema.getIdProblema());
					problemaRelacionadoDto.setIdProblemaRelacionado(beanProblema.getIdProblema());
					problemaRelacionadoDao.create(problemaRelacionadoDto);
				}
			}

			problema = (ProblemaDTO) problemaDao.create(problema);

			if (problema != null && problema.getInformacoesComplementares() != null) {
				TemplateSolicitacaoServicoDTO templateDto = new TemplateSolicitacaoServicoServiceEjb().recuperaTemplateProblema(problema);
				if (templateDto != null && templateDto.getNomeClasseServico() != null) {
					ComplemInfProblemaServicoService complemInfProblemaServicoService = (ComplemInfProblemaServicoService) this.getInformacoesComplementaresService(templateDto.getNomeClasseServico());
					complemInfProblemaServicoService.create(tc, problema, problema.getInformacoesComplementares());
				}
			}

			if (problema != null) {
				this.criarOcorrenciaProblema(problema, tc);
			}

			Source source = null;
			if (problema != null) {
				source = new Source(problema.getRegistroexecucao());
			}
			if (source != null) {
				problema.setRegistroexecucao(source.getTextExtractor().toString());
			}

			if (problema != null && problema.getRegistroexecucao() != null && !problema.getRegistroexecucao().trim().equalsIgnoreCase("")) {
				ocorrenciaProblemaDto.setIdProblema(problema.getIdProblema());
				ocorrenciaProblemaDto.setDataregistro(UtilDatas.getDataAtual());
				ocorrenciaProblemaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
				ocorrenciaProblemaDto.setTempoGasto(0);
				ocorrenciaProblemaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
				ocorrenciaProblemaDto.setDataInicio(UtilDatas.getDataAtual());
				ocorrenciaProblemaDto.setDataFim(UtilDatas.getDataAtual());
				ocorrenciaProblemaDto.setInformacoesContato("não se aplica");
				ocorrenciaProblemaDto.setRegistradopor(problema.getUsuarioDto().getLogin());
				try {
					ocorrenciaProblemaDto.setDadosProblema(new Gson().toJson(problema));
				} catch (Exception e) {
					e.printStackTrace();
				}
				ocorrenciaProblemaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
				ocorrenciaProblemaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Criacao.getSigla());
				ocorrenciaProblemaDto.setOcorrencia(problema.getRegistroexecucao());
				ocorrenciaProblemaDao.create(ocorrenciaProblemaDto);
			}

			if (problema != null) {
				if (problema.getListIdSolicitacaoServico() != null && problema.getListIdSolicitacaoServico().size() > 0) {
					solicitacaoServicoProblemaDao.deleteByIdProblema(problema.getIdProblema());
					for (SolicitacaoServicoDTO solicitacaoServicoDTO : problema.getListIdSolicitacaoServico()) {
						SolicitacaoServicoProblemaDTO solicitacaoServicoProblemaDTO = new SolicitacaoServicoProblemaDTO();

						solicitacaoServicoProblemaDTO.setIdProblema(problema.getIdProblema());
						solicitacaoServicoProblemaDTO.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());
						solicitacaoServicoProblemaDao.create(solicitacaoServicoProblemaDTO);
					}
				}

				if (problema.getListProblemaItemConfiguracaoDTO() != null) {
					problemaItemConfiguracaoDao.deleteByIdProblemaItemConfiguracao(problema.getIdProblema());
					for (ProblemaItemConfiguracaoDTO ic : problema.getListProblemaItemConfiguracaoDTO()) {
						ic.setIdProblema(problema.getIdProblema());
						problemaItemConfiguracaoDao.create(ic);
					}
				}

				if (problema.getListProblemaMudancaDTO() != null) {
					problemaMudancaDao.deleteByIdProblemaMudanca(problema.getIdProblema());
					for (ProblemaMudancaDTO problemaRequisicao : problema.getListProblemaMudancaDTO()) {
						problemaRequisicao.setIdProblema(problema.getIdProblema());

						problemaMudancaDao.create(problemaRequisicao);
					}
				}
			}
			/**
			 * Adicionado gravar anexo
			 *
			 * @author mario.junior
			 * @since 31/10/2013 08:21
			 */
			if (problema != null && problema.getColArquivosUpload() != null && problema.getColArquivosUpload().size() > 0) {
				gravaInformacoesGED(problema.getColArquivosUpload(), 1, problema, tc);
			}

			execucaoProblemaServiceEjb.registraProblema(problema, tc);

			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			e.printStackTrace();
		}

		return model;
	}

	private void criarOcorrenciaProblema(ProblemaDTO problema, TransactionControler tc) throws Exception {
		OcorrenciaProblemaDAO ocorrenciaProblemaDao = new OcorrenciaProblemaDAO();
		ocorrenciaProblemaDao.setTransactionControler(tc);
		OcorrenciaProblemaDTO ocorrenciaProblemaDto = new OcorrenciaProblemaDTO();
		ocorrenciaProblemaDto.setIdProblema(problema.getIdProblema());
		ocorrenciaProblemaDto.setDataregistro(UtilDatas.getDataAtual());
		ocorrenciaProblemaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
		ocorrenciaProblemaDto.setTempoGasto(0);
		ocorrenciaProblemaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
		ocorrenciaProblemaDto.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaProblemaDto.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaProblemaDto.setInformacoesContato("não se aplica");
		ocorrenciaProblemaDto.setRegistradopor(problema.getUsuarioDto().getLogin());
		try {
			ocorrenciaProblemaDto.setDadosProblema(new Gson().toJson(problema));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ocorrenciaProblemaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaProblemaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Criacao.getSigla());
		ocorrenciaProblemaDao.create(ocorrenciaProblemaDto);

	}

	@Override
	public void update(IDto model) throws ServiceException, LogicException {
		// TODO DTO
		ContatoProblemaDTO contatoProblemaDto = new ContatoProblemaDTO();
		OcorrenciaProblemaDTO ocorrenciaProblemaDto = new OcorrenciaProblemaDTO();
		ProblemaRelacionadoDTO problemaRelacionadoDto = null;
		ProblemaDTO problema = (ProblemaDTO) model;
		// TODO ServiceEjb
		ExecucaoProblemaServiceEjb execucaoProblemaServiceEjb = new ExecucaoProblemaServiceEjb();
		// TODO DAO
		ProblemaDAO problemaDao = this.getDao();
		SolicitacaoServicoProblemaDao solicitacaoServicoProblemaDao = new SolicitacaoServicoProblemaDao();
		ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
		ProblemaItemConfiguracaoDAO problemaItemConfiguracaoDao = new ProblemaItemConfiguracaoDAO();
		ProblemaMudancaDAO problemaMudancaDao = new ProblemaMudancaDAO();
		OcorrenciaProblemaDAO ocorrenciaProblemaDao = new OcorrenciaProblemaDAO();
		ProblemaRelacionadoDao problemaRelacionadoDao = new ProblemaRelacionadoDao();

		try {
			problema.setDataHoraInicio(getProblemaAtual(problema.getIdProblema()).getDataHoraInicio());
			problema.setDataHoraCaptura(getProblemaAtual(problema.getIdProblema()).getDataHoraCaptura());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		TransactionControler tc = new TransactionControlerImpl(problemaDao.getAliasDB());

		try {
			tc.start();

			problemaDao.setTransactionControler(tc);
			solicitacaoServicoProblemaDao.setTransactionControler(tc);
			servicoContratoDao.setTransactionControler(tc);
			problemaItemConfiguracaoDao.setTransactionControler(tc);
			problemaMudancaDao.setTransactionControler(tc);
			ocorrenciaProblemaDao.setTransactionControler(tc);
			problemaRelacionadoDao.setTransactionControler(tc);

			if (problema != null && problema.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Cancelada.getDescricao())) {
				if (problema.getFechamento() == null || problema.getFechamento().equalsIgnoreCase("")) {
					throw new LogicException(i18nMessage("citcorpore.comum.informeFechamento"));
				}
			}

			if (problema != null && problema.getAcaoFluxo().equalsIgnoreCase("E")) {

				if (problema.getStatus() != null && problema.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.EmInvestigacao.getDescricao())) {
					if (problema.getCausaRaiz() != null) {
						if (problema.getCausaRaiz().equalsIgnoreCase("")) {
							throw new LogicException(i18nMessage("problema.campoObrigatorioCausaRaiz"));
						}
					}
					if (problema.getSolucaoContorno() != null) {
						if (problema.getSolucaoContorno().equalsIgnoreCase("")) {
							throw new LogicException(i18nMessage("problema.campoObrigatorioSolucaoContorno"));
						}
					}

				}

				if (problema.getStatus() != null && problema.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Resolucao.getDescricao())) {
					if (problema.getFechamento() == null || problema.getFechamento().equalsIgnoreCase("")) {
						throw new LogicException(i18nMessage("citcorpore.comum.informeFechamento"));
					}
				}
			}

			if (problema != null && !problema.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Cancelada.getDescricao())) {
				if (problema.getAlterarSituacao().equalsIgnoreCase("N")) {
					problema.setStatus(getStatusAtual(problema.getIdProblema()));
				}
			}

			if (problema != null) {
				problema.setIdProprietario(problema.getUsuarioDto().getIdUsuario());
			}

			if (problema != null && problema.getDataHoraLimiteSolucionar() != null) {
				problema.setDataHoraLimite(transformaHoraFinal(problema.getDataHoraLimiteSolucionar()));
				// this.determinaPrazo(problema, problema.getIdCalendario());
			} else {
				if (problema != null) {
					problema.setPrazoHH(00);
					problema.setPrazoMM(00);
				}
			}

			contatoProblemaDto = this.criarContatoProblema(problema, tc);

			if (contatoProblemaDto.getIdContatoProblema() != null) {
				problema.setIdContatoProblema(contatoProblemaDto.getIdContatoProblema());
			}

			problemaDao.update(problema);

			this.updateConteudoBaseConhecimento(problema, tc);

			if (problema != null) {
				if (problema.getListIdSolicitacaoServico() != null && problema.getListIdSolicitacaoServico().size() > 0) {
					solicitacaoServicoProblemaDao.deleteByIdProblema(problema.getIdProblema());
					for (SolicitacaoServicoDTO solicitacaoServicoDTO : problema.getListIdSolicitacaoServico()) {
						SolicitacaoServicoProblemaDTO solicitacaoServicoProblemaDTO = new SolicitacaoServicoProblemaDTO();

						solicitacaoServicoProblemaDTO.setIdProblema(problema.getIdProblema());
						solicitacaoServicoProblemaDTO.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());
						solicitacaoServicoProblemaDao.create(solicitacaoServicoProblemaDTO);
					}
				} else {
					SolicitacaoServicoProblemaDTO solicitacaoServicoProblemaDTO = new SolicitacaoServicoProblemaDTO();
					SolicitacaoServicoProblemaService solicitacaoServicoProblemaService = (SolicitacaoServicoProblemaService) ServiceLocator.getInstance().getService(
							SolicitacaoServicoProblemaService.class, null);
					solicitacaoServicoProblemaDTO = solicitacaoServicoProblemaService.restoreByIdProblema(problema.getIdProblema());
					if (solicitacaoServicoProblemaDTO != null && solicitacaoServicoProblemaDTO.getIdProblema() != null && solicitacaoServicoProblemaDTO.getIdProblema().intValue() > 0) {
						solicitacaoServicoProblemaDao.deleteByIdProblema(solicitacaoServicoProblemaDTO.getIdProblema());
					}

				}

				if (problema.getListProblemaItemConfiguracaoDTO() != null && problema.getListProblemaItemConfiguracaoDTO().size() > 0) {
					problemaItemConfiguracaoDao.deleteByIdProblema(problema.getIdProblema());
					for (ProblemaItemConfiguracaoDTO ic : problema.getListProblemaItemConfiguracaoDTO()) {
						ic.setIdProblema(problema.getIdProblema());
						problemaItemConfiguracaoDao.create(ic);
					}
				} else {
					ProblemaItemConfiguracaoDTO problemaItemConfiguracaoRestoreDTO = new ProblemaItemConfiguracaoDTO();
					ProblemaItemConfiguracaoService problemaItemConfiguracaoService = (ProblemaItemConfiguracaoService) ServiceLocator.getInstance().getService(ProblemaItemConfiguracaoService.class,
							null);
					problemaItemConfiguracaoRestoreDTO = (ProblemaItemConfiguracaoDTO) problemaItemConfiguracaoService.restoreByIdProblema(problema.getIdProblema());
					if (problemaItemConfiguracaoRestoreDTO != null && problemaItemConfiguracaoRestoreDTO.getIdProblema() != null && problemaItemConfiguracaoRestoreDTO.getIdProblema().intValue() > 0) {
						problemaItemConfiguracaoDao.deleteByIdProblema(problemaItemConfiguracaoRestoreDTO.getIdProblema());
					}
				}

				if (problema.getListProblemaMudancaDTO() != null) {
					problemaMudancaDao.deleteByIdProblema(problema.getIdProblema());
					for (ProblemaMudancaDTO problemaRequisicao : problema.getListProblemaMudancaDTO()) {
						problemaRequisicao.setIdProblema(problema.getIdProblema());

						problemaMudancaDao.create(problemaRequisicao);
					}
				} else {
					ProblemaMudancaDTO problemaMudancaRestoreDTO = new ProblemaMudancaDTO();
					ProblemaMudancaService problemaMudancaService = (ProblemaMudancaService) ServiceLocator.getInstance().getService(ProblemaMudancaService.class, null);
					problemaMudancaRestoreDTO = problemaMudancaService.restoreByIdProblema(problema.getIdProblema());
					if (problemaMudancaRestoreDTO != null && problemaMudancaRestoreDTO.getIdProblema() != null && problemaMudancaRestoreDTO.getIdProblema().intValue() > 0) {
						problemaMudancaDao.deleteByIdProblema(problemaMudancaRestoreDTO.getIdProblema());
					}
				}

				problemaRelacionadoDao.deleteByIdProblema(problema.getIdProblema());
				if (problema.getListProblemaRelacionadoDTO() != null && problema.getListProblemaRelacionadoDTO().size() > 0) {
					for (ProblemaDTO beanProblema : problema.getListProblemaRelacionadoDTO()) {
						problemaRelacionadoDto = new ProblemaRelacionadoDTO();
						problemaRelacionadoDto.setIdProblema(problema.getIdProblema());
						problemaRelacionadoDto.setIdProblemaRelacionado(beanProblema.getIdProblema());
						problemaRelacionadoDao.create(problemaRelacionadoDto);
					}
				}

			}
			if (problema != null && problema.getPrecisaMudanca() != null && problema.getPrecisaMudanca().equalsIgnoreCase("N")) {
				problemaMudancaDao.deleteByIdProblema(problema.getIdProblema());
			}

			if (problema != null && problema.getIdTarefa() == null) {
				problemaDao.update(problema);
			} else {

				if (problema.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Cancelada.getDescricao())) {
					execucaoProblemaServiceEjb.encerra(problema.getUsuarioDto(), problema, tc);

				} else {
					execucaoProblemaServiceEjb.executa(problema, problema.getIdTarefa(), problema.getAcaoFluxo(), tc);
				}

			}

			if (problema.getInformacoesComplementares() != null) {
				TemplateSolicitacaoServicoDTO templateDto = new TemplateSolicitacaoServicoServiceEjb().recuperaTemplateProblema(problema, tc);
				if (templateDto != null && templateDto.getNomeClasseServico() != null) {
					ComplemInfProblemaServicoService complemInfProblemaServicoService = (ComplemInfProblemaServicoService) this.getInformacoesComplementaresService(templateDto.getNomeClasseServico());
					complemInfProblemaServicoService.update(tc, problema, problema.getInformacoesComplementares());
				}
			}

			Source source = new Source(problema.getRegistroexecucao());
			problema.setRegistroexecucao(source.getTextExtractor().toString());

			if (problema.getRegistroexecucao() != null && !problema.getRegistroexecucao().trim().equalsIgnoreCase("")) {
				ocorrenciaProblemaDto.setIdProblema(problema.getIdProblema());
				ocorrenciaProblemaDto.setDataregistro(UtilDatas.getDataAtual());
				ocorrenciaProblemaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
				ocorrenciaProblemaDto.setTempoGasto(0);
				ocorrenciaProblemaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
				ocorrenciaProblemaDto.setDataInicio(UtilDatas.getDataAtual());
				ocorrenciaProblemaDto.setDataFim(UtilDatas.getDataAtual());
				ocorrenciaProblemaDto.setInformacoesContato("não se aplica");
				ocorrenciaProblemaDto.setRegistradopor(problema.getUsuarioDto().getLogin());
				try {
					ocorrenciaProblemaDto.setDadosProblema(new Gson().toJson(problema));
				} catch (Exception e) {
					e.printStackTrace();
				}
				ocorrenciaProblemaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
				ocorrenciaProblemaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Criacao.getSigla());
				ocorrenciaProblemaDto.setOcorrencia(problema.getRegistroexecucao());
				ocorrenciaProblemaDao.create(ocorrenciaProblemaDto);
			}

			if (problema.getAcaoFluxo().equalsIgnoreCase("E")) {
				if (problema.getStatus() != null
						&& (problema.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Resolucao.getDescricao()) || problema.getStatus().equalsIgnoreCase(
								SituacaoRequisicaoProblema.Concluida.getDescricao()))) {
					fechaRelacionamentoProblema(problema, tc);

				}
			}
			/**
			 * Adicionado para gravar anexo
			 *
			 * @author mario.junior
			 * @since 31/10/2013 08:21
			 */
			if (problema.getColArquivosUpload() != null && problema.getColArquivosUpload().size() > 0) {
				gravaInformacoesGED(problema.getColArquivosUpload(), 1, problema, tc);
			}

			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			e.printStackTrace();
		}

	}

	@Override
	public void create(ProblemaDTO problemaDto, TransactionControler tc) throws Exception {

		this.getDao().setTransactionControler(tc);

		this.create(problemaDto);

	}

	// Falta arrumar o Create
	// David
	public void createAll(ProblemaDTO problema, ArrayList<ProblemaItemConfiguracaoDTO> ics, ArrayList<ProblemaMudancaDTO> rdm, UsuarioDTO usuarioDto) throws ServiceException, LogicException {
		BaseConhecimentoDTO baseAux = null;
		ProblemaDTO problemabean = new ProblemaDTO();
		SolicitacaoServicoProblemaDao solicitacaoServicoProblemaDao = new SolicitacaoServicoProblemaDao();
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());

		ContatoProblemaDTO contatoProblemaDto = new ContatoProblemaDTO();

		ExecucaoProblemaServiceEjb execucaoProblemaServiceEjb = new ExecucaoProblemaServiceEjb();

		if (problema != null) {
			problema.setDataHoraCaptura(UtilDatas.getDataHoraAtual());
			problema.setDataHoraSolicitacao(UtilDatas.getDataHoraAtual());
			problema.setIdProprietario(usuarioDto.getIdUsuario());
		}

		try {
			this.getDao().setTransactionControler(tc);
			this.getProblemaItemConfiguracaoDao().setTransactionControler(tc);
			solicitacaoServicoProblemaDao.setTransactionControler(tc);

			tc.start();

			contatoProblemaDto = criarContatoProblema(problema, tc);

			if (problema != null) {
				problema.setIdContatoProblema(contatoProblemaDto.getIdContatoProblema());
			}

			problemabean = problema;

			if ((problema != null) && (problema.getIdGrupoNivel1() == null || problema.getIdGrupoNivel1().intValue() <= 0)) {
				Integer idGrupoNivel1 = null;
				String idGrupoN1 = ParametroUtil.getValor(ParametroSistema.ID_GRUPO_PADRAO_NIVEL1, tc, null);
				if (idGrupoN1 != null && !idGrupoN1.trim().equalsIgnoreCase("")) {
					try {
						idGrupoNivel1 = new Integer(idGrupoN1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (idGrupoNivel1 == null || idGrupoNivel1.intValue() <= 0)
					throw new LogicException(i18nMessage("solicitacaoservico.validacao.servicolocalizado"));
				problema.setIdGrupoNivel1(idGrupoNivel1);
				problema.setIdGrupo(idGrupoNivel1);
			}

			problema = (ProblemaDTO) this.getDao().create(problema);

			if (problemabean != null) {
				if (problemabean.getListIdSolicitacaoServico() != null && problemabean.getListIdSolicitacaoServico().size() > 0) {
					solicitacaoServicoProblemaDao.deleteByIdProblema(problema.getIdProblema());
					for (SolicitacaoServicoDTO solicitacaoServicoDTO : problemabean.getListIdSolicitacaoServico()) {
						SolicitacaoServicoProblemaDTO solicitacaoServicoProblemaDTO = new SolicitacaoServicoProblemaDTO();

						solicitacaoServicoProblemaDTO.setIdProblema(problema.getIdProblema());
						solicitacaoServicoProblemaDTO.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());
						solicitacaoServicoProblemaDao.create(solicitacaoServicoProblemaDTO);
					}
				}
			}

			if (ics != null) {
				for (ProblemaItemConfiguracaoDTO ic : ics) {
					ic.setIdProblema(problema.getIdProblema());

					getProblemaItemConfiguracaoDao().create(ic);
				}
			}

			if (rdm != null) {
				for (ProblemaMudancaDTO problemaRequisicao : rdm) {
					problemaRequisicao.setIdProblema(problema.getIdProblema());

					getProblemaMudancaDao().create(problemaRequisicao);
				}
			}

			// ///////////////// ID EMPRESA SETADO MANUALMENTE POR NÃO HAVER AINDA TRATAMENTO DE MULTIPLAS EMPRESAS /////////////////////////////////
			// //////////////// APROVA BASE SETADO TRUE MANUALMENTE POIS QUEM REGISTRA PROBLEMA DEVE TE-LA INDUBITAVELMENTE /////////////////////
			if (problema.getAdicionarBDCE() != null && problema.getAdicionarBDCE().trim().equalsIgnoreCase("S")) {
				baseAux = getBaseConhecimentoService().create(getBeanErroConhecidoBaseConhecimento(problema), null, 1, usuarioDto);
				problema.setIdBaseConhecimento(baseAux.getIdBaseConhecimento());
				getDao().update(problema);
			}

			execucaoProblemaServiceEjb.registraProblema(problema, tc);

			tc.commit();
			tc.close();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			e.printStackTrace();
		}
	}

	public ProblemaDTO restoreAll(Integer idProblema) throws Exception {
		return restoreAll(idProblema, null);
	}

	public ProblemaDTO restoreAll(Integer idProblema, TransactionControler tc) throws Exception {
		if (tc != null)
			getDao().setTransactionControler(tc);

		ProblemaDTO problemaDto = new ProblemaDTO();
		problemaDto.setIdProblema(idProblema);
		problemaDto = (ProblemaDTO) getDao().restore(problemaDto);

		try {

		} catch (Exception e) {
			throw new Exception(i18nMessage("solicitacaoservico.erro.recuperardadosolicitacao") + " " + idProblema);
		}

		if (problemaDto != null) {
			// Isso eh necessario!!!
			if (problemaDto.getDataHoraLimite() != null) {
				problemaDto.setDataHoraLimiteStr(problemaDto.getDataHoraLimiteStr());
			}

			problemaDto.setIdGrupoAtual(problemaDto.getIdGrupo());
			problemaDto.setIdGrupoNivel1(problemaDto.getIdGrupo());

			problemaDto.setNomeServico(problemaDto.getServico());
			if (problemaDto.getNomeUnidadeSolicitante() != null && !problemaDto.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
				problemaDto.setSolicitanteUnidade(problemaDto.getSolicitante() + " (" + problemaDto.getNomeUnidadeSolicitante() + ")");

			if (problemaDto.getNomeUnidadeResponsavel() != null && !problemaDto.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
				problemaDto.setResponsavel(problemaDto.getResponsavel() + " (" + problemaDto.getNomeUnidadeResponsavel() + ")");

			if (problemaDto.getIdSolicitante() != null) {
				UsuarioDTO usuarioDto = new UsuarioDTO();
				UsuarioDao usuarioDao = new UsuarioDao();
				usuarioDto.setIdEmpregado(problemaDto.getIdSolicitante());
				usuarioDto = (UsuarioDTO) usuarioDao.restoreByIdEmpregado(usuarioDto.getIdEmpregado());
				if (usuarioDto != null) {
					problemaDto.setSolicitante(usuarioDto.getNomeUsuario());
				}
			}

			if (problemaDto.getIdProprietario() != null) {
				UsuarioDTO usuarioDto = new UsuarioDTO();
				UsuarioDao usuarioDao = new UsuarioDao();
				usuarioDto.setIdUsuario(problemaDto.getIdProprietario());
				usuarioDto = (UsuarioDTO) usuarioDao.restore(usuarioDto);
				if (usuarioDto != null) {
					problemaDto.setResponsavel(usuarioDto.getLogin());
				}
			}

			if (problemaDto.getIdGrupo() != null) {
				GrupoDao grupoDao = new GrupoDao();
				GrupoDTO grupoDto = new GrupoDTO();
				grupoDto.setIdGrupo(problemaDto.getIdGrupo());
				grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
				if (grupoDto != null)
					problemaDto.setNomeGrupoAtual(grupoDto.getSigla());
			}

			if (problemaDto.getIdContrato() != null) {
				ContratoDTO contratoDto = new ContratoDTO();
				ContratoDao contratoDao = new ContratoDao();
				contratoDto.setIdContrato(problemaDto.getIdContrato());
				contratoDto = (ContratoDTO) contratoDao.restore(contratoDto);
				if (contratoDto != null)
					problemaDto.setContrato(contratoDto.getNumero());
			}

			if (problemaDto.getIdContatoProblema() != null) {
				ContatoProblemaDTO contatoProblemaDto = new ContatoProblemaDTO();
				ContatoProblemaDAO contatoProblemaDao = new ContatoProblemaDAO();

				if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER"))
					if (tc != null)
						contatoProblemaDao.setTransactionControler(tc);

				contatoProblemaDto.setIdContatoProblema(problemaDto.getIdContatoProblema());
				contatoProblemaDto = (ContatoProblemaDTO) contatoProblemaDao.restore(contatoProblemaDto);
				if (contatoProblemaDto != null) {
					problemaDto.setNomeContato(contatoProblemaDto.getNomecontato());
					problemaDto.setEmailContato(contatoProblemaDto.getEmailcontato());
					problemaDto.setTelefoneContato(contatoProblemaDto.getTelefonecontato());
					problemaDto.setRamal(contatoProblemaDto.getRamal());
					problemaDto.setObservacao(contatoProblemaDto.getObservacao());
					problemaDto.setIdLocalidade(contatoProblemaDto.getIdLocalidade());
				}
			}

		}
		return verificaSituacaoSLA(problemaDto, tc);
	}

	public ProblemaDTO verificaSituacaoSLA(ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		long atrasoSLA = 0;

		if (problemaDto == null) {
			return problemaDto;
		}

		if (problemaDto.getDataHoraLimite() == null)
			determinaPrazoLimite(problemaDto, null, tc);

		if (problemaDto.getDataHoraLimite() != null) {
			Timestamp dataHoraLimite = problemaDto.getDataHoraLimite();
			Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
			if (problemaDto.encerrada())
				dataHoraComparacao = problemaDto.getDataHoraFim();
			if (dataHoraComparacao != null) {
				if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
					atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraComparacao, dataHoraLimite) / 1000;
				}
			}
		}

		problemaDto.setAtrasoSLA(atrasoSLA);
		return problemaDto;
	}

	public void determinaPrazoLimite(ProblemaDTO problemaDto, Integer idCalendarioParm, TransactionControler tc) throws Exception {
		new ExecucaoProblemaServiceEjb().determinaPrazoLimite(problemaDto, idCalendarioParm, tc);
	}

	public void updateAll(ProblemaDTO problema, ArrayList<ProblemaItemConfiguracaoDTO> ics, ArrayList<ProblemaMudancaDTO> rdm, UsuarioDTO usuarioDto) throws ServiceException, LogicException {

		BaseConhecimentoDTO baseAux = null;
		ProblemaDTO problemabean = new ProblemaDTO();
		SolicitacaoServicoProblemaDao solicitacaoServicoProblemaDao = new SolicitacaoServicoProblemaDao();
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());

		SolicitacaoServicoServiceEjb solicitacaoServicoServiceEjb = new SolicitacaoServicoServiceEjb();
		ServicoContratoDao servicoContratoDao = new ServicoContratoDao();

		SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
		if (problema != null) {
			solicitacaoServicoDto.setIdSolicitante(problema.getIdSolicitacaoServico());
		}
		solicitacaoServicoDto.setUsuarioDto(usuarioDto);
		if (problema != null) {
			solicitacaoServicoDto.setIdContrato(problema.getIdContrato());
			solicitacaoServicoDto.setIdServico(problema.getIdServico());
		}
		try {
			tc.start();
			ProblemaDTO problemaAux = (ProblemaDTO) getDao().restore(problema);
			if (problemaAux == null) {
				problemaAux = new ProblemaDTO();
			}

			if (problema != null && solicitacaoServicoDto != null && usuarioDto != null) {
				ServicoContratoDTO servicoContratoDTO = servicoContratoDao.findByIdContratoAndIdServico(problema.getIdContrato(), problema.getIdServico());
				solicitacaoServicoDto.setIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
				solicitacaoServicoDto.setDataHoraSolicitacao(problemaAux.getDataHoraSolicitacao());
				problema.setIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
				solicitacaoServicoDto.setUrgencia(problema.getUrgencia().substring(0, 1));
				solicitacaoServicoDto.setImpacto(problema.getImpacto().substring(0, 1));
				if (solicitacaoServicoDto.getDataHoraSolicitacao() == null) {
					solicitacaoServicoDto.setDataHoraSolicitacao(UtilDatas.getDataHoraAtual());
				}
				solicitacaoServicoServiceEjb.determinaPrioridadeEPrazo(solicitacaoServicoDto, tc);
				problema.setIdPrioridade(solicitacaoServicoDto.getIdPrioridade());
				problema.setPrazoHH(solicitacaoServicoDto.getPrazoHH());
				problema.setPrazoMM(solicitacaoServicoDto.getPrazoMM());
				problema.setSlaACombinar(solicitacaoServicoDto.getSlaACombinar());
				problema.setDataHoraLimite(solicitacaoServicoDto.getDataHoraLimite());
				problema.setIdProprietario(usuarioDto.getIdUsuario());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new LogicException(e1);
		}

		try {
			getDao().setTransactionControler(tc);
			getProblemaItemConfiguracaoDao().setTransactionControler(tc);
			getProblemaMudancaDao().setTransactionControler(tc);
			solicitacaoServicoProblemaDao.setTransactionControler(tc);

			tc.start();
			problemabean = problema;
			getDao().update(problema);

			if (problemabean != null) {
				if (problemabean.getListIdSolicitacaoServico() != null && problemabean.getListIdSolicitacaoServico().size() > 0) {
					solicitacaoServicoProblemaDao.deleteByIdProblema(problema.getIdProblema());
					for (SolicitacaoServicoDTO solicitacaoServicoDTO : problemabean.getListIdSolicitacaoServico()) {
						SolicitacaoServicoProblemaDTO solicitacaoServicoProblemaDTO = new SolicitacaoServicoProblemaDTO();

						solicitacaoServicoProblemaDTO.setIdProblema(problema.getIdProblema());
						solicitacaoServicoProblemaDTO.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());
						solicitacaoServicoProblemaDao.create(solicitacaoServicoProblemaDTO);
					}
				}
			}

			if (ics != null) {
				ProblemaItemConfiguracaoDTO aux = null;

				for (ProblemaItemConfiguracaoDTO ic : ics) {
					aux = (ProblemaItemConfiguracaoDTO) getProblemaItemConfiguracaoDao().findByIdItemConfiguracaoEIdProblema(ic.getIdItemConfiguracao(), problema.getIdProblema());
					if (aux == null) {
						ic.setIdProblema(problema.getIdProblema());
						getProblemaItemConfiguracaoDao().create(ic);
					} else {
						ic.setIdProblemaItemConfiguracao(aux.getIdProblemaItemConfiguracao());
						ic.setIdProblema(problema.getIdProblema());
						getProblemaItemConfiguracaoDao().update(ic);
					}
				}
			}

			if (problema != null && problema.getListProblemaMudancaDTO() != null) {
				problemaMudancaDao.deleteByIdProblema(problema.getIdProblema());
				for (ProblemaMudancaDTO dto : problema.getListProblemaMudancaDTO()) {
					dto.setIdProblema(problema.getIdProblema());
					problemaMudancaDao.create(dto);
				}
			}

			if (problema != null) {
				getProblemaItemConfiguracaoDao().deletaOsQueNaoEstaoNaListaByIdProblema(problema.getIdProblema(), ics);
			}
			// getProblemaMudancaDao().deleteByIdProblema(problema.getIdProblema());

			if (problema != null && problema.getAdicionarBDCE() != null && problema.getAdicionarBDCE().trim().equalsIgnoreCase("S")) {
				if (problema.getIdBaseConhecimento() != null) {
					baseAux = getBeanErroConhecidoBaseConhecimento(problema);

					baseAux.setIdBaseConhecimento(problema.getIdBaseConhecimento());

					// ///////////////// ID EMPRESA SETADO MANUALMENTE POR NÃO HAVER AINDA TRATAMENTO DE MULTIPLAS EMPRESAS /////////////////////////////////
					// //////////////// APROVA BASE SETADO TRUE MANUALMENTE POIS QUEM REGISTRA PROBLEMA DEVE TE-LA INDUBITAVELMENTE //////////////////////
					getBaseConhecimentoService().update(baseAux, null, 1, usuarioDto);

					// faz o update e gera uma nova versão. Pega o id da nova versão e salva
					if (getBaseConhecimentoService().getIdBaseConhecimento() != null) {
						problema.setIdBaseConhecimento(getBaseConhecimentoService().getIdBaseConhecimento());
					}
					getDao().updateNotNull(problema);
				} else {

					// ///////////////// ID EMPRESA SETADO MANUALMENTE POR NÃO HAVER AINDA TRATAMENTO DE MULTIPLAS EMPRESAS /////////////////////////////////
					// //////////////// APROVA BASE SETADO TRUE MANUALMENTE POIS QUEM REGISTRA PROBLEMA DEVE TE-LA INDUBITAVELMENTE //////////////////////
					baseAux = getBaseConhecimentoService().create(getBeanErroConhecidoBaseConhecimento(problema), null, 1, usuarioDto);

					// faz o update e gera uma nova versão. Pega o id da nova versão e salva
					if (baseAux.getIdBaseConhecimento() != null) {
						problema.setIdBaseConhecimento(baseAux.getIdBaseConhecimento());
					}
					getDao().updateNotNull(problema);
				}
			}

			tc.commit();
			tc.close();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			e.printStackTrace();
		}
	}

	public List<ProblemaDTO> listProblema(ProblemaDTO bean) throws ServiceException, LogicException {

		try {
			return (List<ProblemaDTO>) getDao().listProblema(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteAll(ProblemaDTO problema) throws ServiceException, LogicException {
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());

		try {
			getDao().setTransactionControler(tc);
			getProblemaItemConfiguracaoDao().setTransactionControler(tc);

			tc.start();

			getDao().delecaoLogica(problema);

			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			e.printStackTrace();
		}
	}

	/**
	 * Grava relacionamento com base conhecimento.
	 *
	 * @param problema
	 */
	private BaseConhecimentoDTO getBeanErroConhecidoBaseConhecimento(ProblemaDTO problema) {
		BaseConhecimentoDTO baseDtoAux = new BaseConhecimentoDTO();

		if (problema.getAdicionarBDCE() != null && problema.getAdicionarBDCE().trim().equalsIgnoreCase("S")) {
			baseDtoAux.setTitulo(problema.getTitulo());
			baseDtoAux.setIdPasta(problema.getIdPastaBaseConhecimento());
			baseDtoAux.setStatus(problema.getStatusBaseConhecimento());
			baseDtoAux.setOrigem(problema.getOrigem());
			baseDtoAux.setConteudo("Causa Raiz: <br />" + problema.getCausaRaiz() + " <br /><br /> Solução de Contorno:<br />" + problema.getSolucaoContorno());

			baseDtoAux.setDataInicio(UtilDatas.getDataAtual());

			baseDtoAux.setDataExpiracao(problema.getDataHoraLimiteSolucionar());
		}

		return baseDtoAux;
	}

	public Collection findByIdProblema(Integer parm) throws Exception {
		try {
			return getDao().findByIdProblema(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdProblema(Integer parm) throws Exception {
		try {
			getDao().deleteByIdProblema(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByStatus(String parm) throws Exception {
		try {
			return getDao().findByStatus(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByStatus(String parm) throws Exception {
		try {
			getDao().deleteByStatus(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByPrioridade(Integer parm) throws Exception {
		try {
			return getDao().findByPrioridade(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByPrioridade(Integer parm) throws Exception {
		try {
			getDao().deleteByPrioridade(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdCriador(Integer parm) throws Exception {
		try {
			return getDao().findByIdCriador(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdCriador(Integer parm) throws Exception {
		try {
			getDao().deleteByIdCriador(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdProprietario(Integer parm) throws Exception {
		try {
			return getDao().findByIdProprietario(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdProprietario(Integer parm) throws Exception {
		try {
			getDao().deleteByIdProprietario(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByTitulo(String parm) throws Exception {
		try {
			return getDao().findByTitulo(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByTitulo(String parm) throws Exception {
		try {
			getDao().deleteByTitulo(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByDescricao(String parm) throws Exception {
		try {
			return getDao().findByDescricao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByDescricao(String parm) throws Exception {
		try {
			getDao().deleteByDescricao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdCategoriaProblema(Integer parm) throws Exception {
		try {
			return getDao().findByIdCategoriaProblema(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdCategoriaProblema(Integer parm) throws Exception {
		try {
			getDao().deleteByIdCategoriaProblema(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByImpacto(String parm) throws Exception {
		try {
			return getDao().findByImpacto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByImpacto(String parm) throws Exception {
		try {
			getDao().deleteByImpacto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByUrgencia(String parm) throws Exception {
		try {
			return getDao().findByUrgencia(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByUrgencia(String parm) throws Exception {
		try {
			getDao().deleteByUrgencia(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByProativoReativo(String parm) throws Exception {
		try {
			return getDao().findByProativoReativo(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByProativoReativo(String parm) throws Exception {
		try {
			getDao().deleteByProativoReativo(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByMsgErroAssociada(String parm) throws Exception {
		try {
			return getDao().findByMsgErroAssociada(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByMsgErroAssociada(String parm) throws Exception {
		try {
			getDao().deleteByMsgErroAssociada(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdProblemaItemConfiguracao(Integer parm) throws Exception {
		try {
			return getDao().findByIdProblemaItemConfiguracao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdProblemaItemConfiguracao(Integer parm) throws Exception {
		try {
			getDao().deleteByIdProblemaItemConfiguracao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdErrosConhecidos(Integer parm) throws Exception {
		try {
			return getDao().findByIdErrosConhecidos(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdErrosConhecidos(Integer parm) throws Exception {
		try {
			getDao().deleteByIdErrosConhecidos(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdProblemaMudanca(Integer parm) throws Exception {
		try {
			return getDao().findByIdProblemaMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdProblemaMudanca(Integer parm) throws Exception {
		try {
			getDao().deleteByIdProblemaMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdProblemaIncidente(Integer parm) throws Exception {
		try {
			return getDao().findByIdProblemaIncidente(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdProblemaIncidente(Integer parm) throws Exception {
		try {
			getDao().deleteByIdProblemaIncidente(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdSolictacaoServico(Integer parm) throws ServiceException, LogicException {
		SolicitacaoServicoProblemaDao solicitacaoServicoProblemaDao = new SolicitacaoServicoProblemaDao();
		Collection<ProblemaDTO> colProblema = new ArrayList<ProblemaDTO>();
		ProblemaDTO problema = new ProblemaDTO();
		try {
			Collection<SolicitacaoServicoProblemaDTO> colAux = solicitacaoServicoProblemaDao.findByIdSolictacaoServico(parm);
			if (colAux != null) {
				for (SolicitacaoServicoProblemaDTO sol : colAux) {
					problema.setIdProblema(sol.getIdProblema());
					colProblema.add((ProblemaDTO) getDao().restore(problema));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return colProblema;
	}

	public Collection findBySolictacaoServico(ProblemaDTO bean) throws ServiceException, LogicException {
		try {
			return getDao().listProblemaByIdSolicitacao(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<ProblemaDTO> listProblemaByIdItemConfiguracao(Integer idItemConfiguracao) throws PersistenceException {
		try {
			return getDao().listProblemaByIdItemConfiguracao(idItemConfiguracao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<ProblemaDTO> quantidadeProblemaPorBaseConhecimento(ProblemaDTO problema) throws Exception {
		return getDao().quantidadeProblemaPorBaseConhecimento(problema);
	}

	@Override
	public Collection<ProblemaDTO> listaProblemaPorBaseConhecimento(ProblemaDTO problema) throws Exception {

		Collection<ProblemaDTO> listaProblemaPorBaseConhecimento = null;
		try {
			listaProblemaPorBaseConhecimento = getDao().listaProblemasPorBaseConhecimento(problema);
			for (ProblemaDTO problemaDTO : listaProblemaPorBaseConhecimento) {

				Source source = new Source(problemaDTO.getDescricao());
				problemaDTO.setDescricao(source.getTextExtractor().toString());

				if (StringUtils.contains(StringUtils.upperCase(problemaDTO.getStatus()), StringUtils.upperCase("SolucaoContornoDefinida"))) {
					problemaDTO.setStatus("Solução Contorno Definida");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaProblemaPorBaseConhecimento;

	}

	@Override
	public Collection findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, LogicException {
		try {
			return getDao().findByConhecimento(baseConhecimentoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Collection<ProblemaDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefas) throws Exception {
		Collection<ProblemaDTO> listProblemaDto = new ArrayList();

		listProblemaDto = getDao().listByTarefas(listTarefas);

		if (listProblemaDto != null && !listProblemaDto.isEmpty()) {

			for (ProblemaDTO problemaDto : listProblemaDto) {

				if (problemaDto != null) {
					problemaDto.setDataHoraCapturaStr(problemaDto.getDataHoraCapturaStr());
					problemaDto.setDataHoraLimiteStr(problemaDto.getDataHoraLimiteStr());
					problemaDto.setDataHoraInicioSLA(problemaDto.getDataHoraInicioSLA());

					problemaDto.setNomeServico(problemaDto.getServico());
					if (problemaDto.getNomeUnidadeSolicitante() != null && !problemaDto.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
						problemaDto.setSolicitanteUnidade(problemaDto.getSolicitante() + " (" + problemaDto.getNomeUnidadeSolicitante() + ")");

					if (problemaDto.getNomeUnidadeResponsavel() != null && !problemaDto.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
						problemaDto.setResponsavel(problemaDto.getResponsavel() + " (" + problemaDto.getNomeUnidadeResponsavel() + ")");
				}
			}
		}
		return listProblemaDto;
	}

	public ProblemaDTO restoreByIdInstanciaFluxo(Integer idInstanciaFluxo) throws Exception {
		ProblemaDTO problemaDTO = null;
		try {
			problemaDTO = (ProblemaDTO) getDao().restoreByIdInstanciaFluxo(idInstanciaFluxo);
		} catch (Exception e) {

			System.out.println("CITSMART - Erro na recuperação dos dados da solicitação da instância fluxo" + " " + idInstanciaFluxo);
		}

		if (problemaDTO != null) {
			// Isso eh necessario!!!
			problemaDTO.setDataHoraLimiteStr(problemaDTO.getDataHoraLimiteStr());

			problemaDTO.setNomeServico(problemaDTO.getServico());
			if (problemaDTO.getNomeUnidadeSolicitante() != null && !problemaDTO.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
				problemaDTO.setSolicitanteUnidade(problemaDTO.getSolicitante() + " (" + problemaDTO.getNomeUnidadeSolicitante() + ")");

			if (problemaDTO.getNomeUnidadeResponsavel() != null && !problemaDTO.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
				problemaDTO.setResponsavel(problemaDTO.getResponsavel() + " (" + problemaDTO.getNomeUnidadeResponsavel() + ")");
		}
		return verificaSituacaoSLA(problemaDTO);
	}

	public ProblemaDTO verificaSituacaoSLA(ProblemaDTO problemaDto) throws Exception {
		return verificaSituacaoSLA(problemaDto, null);
	}

	public void suspende(UsuarioDTO usuarioDto, ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		ProblemaDTO problemaAuxDto = restoreAll(problemaDto.getIdProblema(), tc);

		problemaAuxDto.setIdJustificativaProblema(problemaDto.getIdJustificativaProblema());
		problemaAuxDto.setComplementoJustificativa(problemaDto.getComplementoJustificativa());
		new ExecucaoProblemaServiceEjb().suspende(usuarioDto, problemaAuxDto, tc);
	}

	public void suspende(UsuarioDTO usuarioDto, ProblemaDTO problemaDto) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		suspende(usuarioDto, problemaDto, tc);
		tc.close();
	}

	public ContatoProblemaDTO criarContatoProblema(ProblemaDTO problemaDto, TransactionControler tc) throws ServiceException, LogicException {
		ContatoProblemaDAO contatoProblemaDao = new ContatoProblemaDAO();
		ContatoProblemaDTO contatoProblemaDto = new ContatoProblemaDTO();

		ProblemaDAO proplemaDao = this.getDao();

		try {
			contatoProblemaDao.setTransactionControler(tc);
			proplemaDao.setTransactionControler(tc);

			if (problemaDto.getIdContatoProblema() != null) {

				contatoProblemaDto.setIdContatoProblema(problemaDto.getIdContatoProblema());
				contatoProblemaDto.setNomecontato(problemaDto.getNomeContato());
				contatoProblemaDto.setTelefonecontato(problemaDto.getTelefoneContato());
				contatoProblemaDto.setRamal(problemaDto.getRamal());
				contatoProblemaDto.setEmailcontato(problemaDto.getEmailContato().trim());
				contatoProblemaDto.setObservacao(problemaDto.getObservacao());
				contatoProblemaDto.setIdLocalidade(problemaDto.getIdLocalidade());
				contatoProblemaDao.update(contatoProblemaDto);

			} else {
				contatoProblemaDto.setNomecontato(problemaDto.getNomeContato());
				contatoProblemaDto.setTelefonecontato(problemaDto.getTelefoneContato());
				contatoProblemaDto.setRamal(problemaDto.getRamal());
				if (problemaDto.getEmailContato() != null) {
					contatoProblemaDto.setEmailcontato(problemaDto.getEmailContato().trim());
				}
				contatoProblemaDto.setObservacao(problemaDto.getObservacao());
				contatoProblemaDto.setIdLocalidade(problemaDto.getIdLocalidade());
				contatoProblemaDto = (ContatoProblemaDTO) contatoProblemaDao.create(contatoProblemaDto);
			}

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}
		return contatoProblemaDto;
	}

	private String getStatusAtual(Integer id) throws ServiceException, Exception {
		ProblemaDTO problemaDto = new ProblemaDTO();
		problemaDto.setIdProblema(id);
		problemaDto = (ProblemaDTO) getDao().restore(problemaDto);
		String res = problemaDto.getStatus();
		return res;

	}

	private Timestamp transformaHoraFinal(Date data) throws ParseException {
		String dataHora = data + " 00:00:00";
		String pattern = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date d = sdf.parse(dataHora);
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(d.getTime());
		return sqlDate;
	}

	private ProblemaDTO getProblemaAtual(Integer id) throws Exception {
		ProblemaDTO problemaDto = new ProblemaDTO();
		problemaDto.setIdProblema(id);
		problemaDto = (ProblemaDTO) getDao().restore(problemaDto);
		return problemaDto;
	}

	/**
	 * Método responsável por validar a dinâmica do fluxo com relação ao registro de erros conhecidos. Esse método deve garantir que o usuário só avance a partir da fase de avaliação e diagnóstico
	 * para a fase de resolução após ter registrado pelo menos um erro (fase de registro de erros conhecidos).
	 *
	 * @autor thiago.monteiro
	 * @param ProblemaDTO
	 *            problemaDTO
	 * @param TransactionControler
	 *            tc
	 * @throws Exception
	 * @return
	 */
	public boolean validacaoAvancaFluxo(ProblemaDTO problemaDTO, TransactionControler tc) throws Exception {
		boolean avancarFluxo = false;

		if (problemaDTO != null && problemaDTO.getIdProblema() != null) {

			// 1 - Obtendo o conhecimento sobre um problema.

			ConhecimentoProblemaDTO conhecimentoProblemaDTO = new ConhecimentoProblemaDTO();

			ConhecimentoProblemaDao conhecimentoProblemaDAO = new ConhecimentoProblemaDao();

			if (conhecimentoProblemaDTO != null && conhecimentoProblemaDAO != null) {

				if (tc != null) {
					conhecimentoProblemaDAO.setTransactionControler(tc);
				}

				Collection listaConhecimentoProblemaDTO = conhecimentoProblemaDAO.findByIdProblema(problemaDTO.getIdProblema());

				BaseConhecimentoDAO baseConhecimentoDAO = new BaseConhecimentoDAO();

				if (listaConhecimentoProblemaDTO != null && baseConhecimentoDAO != null) {

					Collection listaBaseConhecimentoDTO = null;

					List condicao = new ArrayList();

					condicao.add(new Condition("idBaseConhecimento", "=", 0));

					for (ConhecimentoProblemaDTO conhecimentoProblemaDTOAux : (Collection<ConhecimentoProblemaDTO>) listaConhecimentoProblemaDTO) {

						// Alterando o valor da condição para o id da base conhecimento.
						((Condition) condicao.get(0)).setValue(conhecimentoProblemaDTOAux.getIdBaseConhecimento());

						listaBaseConhecimentoDTO = baseConhecimentoDAO.findByCondition(condicao, null);

						for (Object baseConhecimentoDTOAux : listaBaseConhecimentoDTO) {

							if (((BaseConhecimentoDTO) baseConhecimentoDTOAux).getErroConhecido().equalsIgnoreCase("S")) {
								avancarFluxo = true;
							}
						}
					}
				}
			}
		}

		return avancarFluxo;
	}

	/**
	 * Método responsável por notificar que o prazo de contorno/solução de um problema expirou. O prazo de contorno/solução irá expirar quando a data hora atual for posterior a data hora limite do
	 * problema. A notificação deve ser feita para o proprietário (quem capturou o problema) e o grupo de interessados (grupo executor).
	 *
	 * @autor thiago.monteiro
	 * @param ProblemaDTO
	 *            problemaDTO
	 * @throws Exception
	 * @return
	 */
	public int contarProblemasComPrazoLimiteContornoExpirado(UsuarioDTO usuarioDTO) throws Exception {
		return getDao().contarProblemasComPrazoLimiteContornoExpirado(usuarioDTO);
	}

	public void notificarPrazoSolucionarProblemaExpirou(ProblemaDTO problemaDTO) throws Exception {

	}

	@Override
	public void updateNotNull(IDto obj) throws Exception {
		getDao().updateNotNull(obj);
	}

	/**
	 * Método responsável de fazer alteração no campo conteudo do erro conhecido do problema passado dentro da entidade base de conhecimento.
	 *
	 * @param ProblemaDTO
	 *            problemaDTO
	 * @param TransactionControler
	 *            tc
	 * @throws Exception
	 * @return
	 * @autor thays.araujo
	 */
	public void updateConteudoBaseConhecimento(ProblemaDTO problemaDto, TransactionControler tc) throws Exception {

		if (problemaDto != null && problemaDto.getIdProblema() != null) {

			ConhecimentoProblemaDTO conhecimentoProblemaDTO = new ConhecimentoProblemaDTO();
			BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

			ConhecimentoProblemaDao conhecimentoProblemaDAO = new ConhecimentoProblemaDao();
			BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();

			if (conhecimentoProblemaDTO != null && conhecimentoProblemaDAO != null) {

				if (tc != null) {
					conhecimentoProblemaDAO.setTransactionControler(tc);
					baseConhecimentoDao.setTransactionControler(tc);
				}

				Collection<ConhecimentoProblemaDTO> listaConhecimentoProblemaDTO = conhecimentoProblemaDAO.findByIdProblema(problemaDto.getIdProblema());

				if (listaConhecimentoProblemaDTO != null) {

					for (ConhecimentoProblemaDTO conhecimentoProblemaDTOAux : listaConhecimentoProblemaDTO) {

						if (conhecimentoProblemaDTOAux.getIdBaseConhecimento() != null) {
							baseConhecimentoDto.setIdBaseConhecimento(conhecimentoProblemaDTOAux.getIdBaseConhecimento());
							baseConhecimentoDto = (BaseConhecimentoDTO) baseConhecimentoDao.restore(baseConhecimentoDto);
							if (baseConhecimentoDto != null) {
								if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
									if (baseConhecimentoDto.getErroConhecido() != null && baseConhecimentoDto.getErroConhecido().equalsIgnoreCase("S")) {
										baseConhecimentoDto.setConteudo("<strong>Causa raiz:</strong><br type='_moz' />" + problemaDto.getCausaRaiz()
												+ "<br/><br/><strong>&nbsp;Solu&ccedil;&atilde;o contorno:</strong><br />" + problemaDto.getSolucaoContorno());
										baseConhecimentoDao.update(baseConhecimentoDto);
									}
								}

							}
						}
					}
				}
			}
		}

	}

	@Override
	public Collection<ProblemaDTO> listaProblemasComPrazoLimiteContornoExpirado(UsuarioDTO usuarioDTO) throws Exception {
		return getDao().listaProblemasComPrazoLimiteContornoExpirado(usuarioDTO);
	}

	public Collection<ProblemaDTO> listProblemaByCriterios(ProblemaDTO requisicaoMudancaDto) throws Exception {
		return null;
	}

	public Collection<PesquisaProblemaDTO> listProblemaByCriterios(PesquisaProblemaDTO pesquisaProblemaDto) throws Exception {

		Collection<PesquisaProblemaDTO> listProblemaByCriterios = getDao().listProblemaByCriterios(pesquisaProblemaDto);

		if (listProblemaByCriterios != null) {
			for (PesquisaProblemaDTO problema : listProblemaByCriterios) {

				Source source = new Source(problema.getDescricao());

				problema.setDescricao(source.getTextExtractor().toString());
			}
		}

		return listProblemaByCriterios;
	}

	@Override
	public String getUrlInformacoesComplementares(ProblemaDTO problemaDto) throws Exception {
		String url = "";

		TemplateSolicitacaoServicoDTO templateDto = new TemplateSolicitacaoServicoServiceEjb().recuperaTemplateProblema(problemaDto);
		if (templateDto != null) {
			url = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.URL_Sistema, "");
			url += templateDto.getUrlRecuperacao();
			url += "?";

			url = url.replaceAll("\n", "");
			url = url.replaceAll("\r", "");

			String editar = "S";
			if (problemaDto.getIdProblema() != null && problemaDto.getIdProblema().intValue() > 0) {
				url += "idProblema=" + problemaDto.getIdProblema() + "&";
				if (problemaDto.getIdTarefa() == null)
					editar = "N";
				else
					url += "idTarefa=" + problemaDto.getIdTarefa() + "&";
			}
			url += "idCategoriaProblema=" + problemaDto.getIdCategoriaProblema() + "&idContrato=" + problemaDto.getIdContrato();
			url += "&editar=" + editar;
		}
		return url;
	}

	@Override
	public void deserializaInformacoesComplementares(ProblemaDTO problemaDto) throws Exception {
		if (problemaDto.getInformacoesComplementares_serialize() != null && !problemaDto.getInformacoesComplementares_serialize().equalsIgnoreCase("")) {
			TemplateSolicitacaoServicoDTO templateDto = new TemplateSolicitacaoServicoServiceEjb().recuperaTemplateProblema(problemaDto);
			if (templateDto != null && templateDto.getNomeClasseServico() != null) {
				ComplemInfProblemaServicoService informacoesComplementaresService = getInformacoesComplementaresService(templateDto.getNomeClasseServico());
				IDto informacoesComplementares = informacoesComplementaresService.deserializaObjeto(problemaDto.getInformacoesComplementares_serialize());
				problemaDto.setInformacoesComplementares(informacoesComplementares);
			}
			problemaDto.setInformacoesComplementares_serialize(null);
		}

	}

	public ComplemInfProblemaServicoService getInformacoesComplementaresService(String nomeClasse) throws Exception {
		ComplemInfProblemaServicoService informacoesComplementaresService = (ComplemInfProblemaServicoService) Class.forName(nomeClasse).newInstance();
		return informacoesComplementaresService;
	}

	@Override
	public Collection<RelatorioProblemaIncidentesDTO> listProblemasIncidentes(RelatorioProblemaIncidentesDTO relatorioProblemaIcidentesDto) throws Exception {
		Collection<RelatorioProblemaIncidentesDTO> listFinal = new ArrayList<RelatorioProblemaIncidentesDTO>();
		// Retorna quais problemas tem relacionamentos com incidentes;
		Collection<RelatorioProblemaIncidentesDTO> listAux = getDao().listProblemaIncidentes(relatorioProblemaIcidentesDto);

		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoByProblema = new ArrayList<SolicitacaoServicoDTO>();

		SolicitacaoServicoProblemaDao solicitacaoDao = new SolicitacaoServicoProblemaDao();
		if (listAux != null) {
			for (RelatorioProblemaIncidentesDTO obj : listAux) {
				// retorna coleção de incidentes relacionaodo ao problema
				listSolicitacaoServicoByProblema = solicitacaoDao.listSolicitacaoServicoByProblema(obj.getIdProblema());
				if (listSolicitacaoServicoByProblema != null) {
					obj.setColSolicitacaoServico(listSolicitacaoServicoByProblema);
					obj.setTotalSolicitacaoServicoPorProblema(listSolicitacaoServicoByProblema.size());
				}

				listFinal.add(obj);
			}
			return listFinal;
		}
		return null;
	}

	public ProblemaDTO restoreAll(ProblemaDTO problema) throws Exception {
		return restoreAll(problema, null);
	}

	public ProblemaDTO restoreAll(ProblemaDTO problema, TransactionControler tc) throws Exception {
		ProblemaDTO problemaDto = new ProblemaDTO();

		problemaDto = (ProblemaDTO) getDao().restoreTela(problema);

		try {

		} catch (Exception e) {
			throw new Exception(i18nMessage("solicitacaoservico.erro.recuperardadosolicitacao") + " " + problema.getIdProblema());
		}

		if (problemaDto != null) {
			// Isso eh necessario!!!
			if (problemaDto.getDataHoraLimite() != null) {
				problemaDto.setDataHoraLimiteStr(problemaDto.getDataHoraLimiteStr());
			}

			problemaDto.setIdGrupoAtual(problemaDto.getIdGrupo());
			problemaDto.setIdGrupoNivel1(problemaDto.getIdGrupo());

			problemaDto.setNomeServico(problemaDto.getServico());
			if (problemaDto.getNomeUnidadeSolicitante() != null && !problemaDto.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
				problemaDto.setSolicitanteUnidade(problemaDto.getSolicitante() + " (" + problemaDto.getNomeUnidadeSolicitante() + ")");

			if (problemaDto.getNomeUnidadeResponsavel() != null && !problemaDto.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
				problemaDto.setResponsavel(problemaDto.getResponsavel() + " (" + problemaDto.getNomeUnidadeResponsavel() + ")");

			if (problemaDto.getIdSolicitante() != null) {
				UsuarioDTO usuarioDto = new UsuarioDTO();
				UsuarioDao usuarioDao = new UsuarioDao();
				usuarioDto.setIdEmpregado(problemaDto.getIdSolicitante());
				usuarioDto = (UsuarioDTO) usuarioDao.restoreByIdEmpregado(usuarioDto.getIdEmpregado());
				if (usuarioDto != null) {
					problemaDto.setSolicitante(usuarioDto.getNomeUsuario());
				}
			}

			if (problemaDto.getIdProprietario() != null) {
				UsuarioDTO usuarioDto = new UsuarioDTO();
				UsuarioDao usuarioDao = new UsuarioDao();
				usuarioDto.setIdUsuario(problemaDto.getIdProprietario());
				usuarioDto = (UsuarioDTO) usuarioDao.restore(usuarioDto);
				if (usuarioDto != null) {
					problemaDto.setResponsavel(usuarioDto.getLogin());
				}
			}

			if (problemaDto.getIdGrupo() != null) {
				GrupoDao grupoDao = new GrupoDao();
				GrupoDTO grupoDto = new GrupoDTO();
				grupoDto.setIdGrupo(problemaDto.getIdGrupo());
				grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
				if (grupoDto != null)
					problemaDto.setNomeGrupoAtual(grupoDto.getSigla());
			}

			if (problemaDto.getIdContrato() != null) {
				ContratoDTO contratoDto = new ContratoDTO();
				ContratoDao contratoDao = new ContratoDao();
				contratoDto.setIdContrato(problemaDto.getIdContrato());
				contratoDto = (ContratoDTO) contratoDao.restore(contratoDto);
				if (contratoDto != null)
					problemaDto.setContrato(contratoDto.getNumero());
			}

			if (problemaDto.getIdContatoProblema() != null) {
				ContatoProblemaDTO contatoProblemaDto = new ContatoProblemaDTO();
				ContatoProblemaDAO contatoProblemaDao = new ContatoProblemaDAO();
				contatoProblemaDto.setIdContatoProblema(problemaDto.getIdContatoProblema());
				contatoProblemaDto = (ContatoProblemaDTO) contatoProblemaDao.restore(contatoProblemaDto);
				if (contatoProblemaDto != null) {
					problemaDto.setNomeContato(contatoProblemaDto.getNomecontato());
					problemaDto.setEmailContato(contatoProblemaDto.getEmailcontato());
					problemaDto.setTelefoneContato(contatoProblemaDto.getTelefonecontato());
					problemaDto.setRamal(contatoProblemaDto.getRamal());
					problemaDto.setObservacao(contatoProblemaDto.getObservacao());
					problemaDto.setIdLocalidade(contatoProblemaDto.getIdLocalidade());
				}
			}

		}
		return verificaSituacaoSLA(problemaDto, tc);
	}

	@Override
	public ProblemaDTO restauraTodos(ProblemaDTO param) throws Exception {
		return (ProblemaDTO) getDao().restauraTodos(param);
	}

	@Override
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorSituacao(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		return getDao().listaQuantidadeProblemaPorSituacao(relatorioQuantitativoProblemaDto);
	}

	@Override
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorGrupo(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		return getDao().listaQuantidadeProblemaPorGrupo(relatorioQuantitativoProblemaDto);
	}

	@Override
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorOrigem(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		return getDao().listaQuantidadeProblemaPorOrigem(relatorioQuantitativoProblemaDto);
	}

	@Override
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorSolicitante(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		return getDao().listaQuantidadeProblemaPorSolicitante(relatorioQuantitativoProblemaDto);
	}

	@Override
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorPrioridade(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		return getDao().listaQuantidadeProblemaPorPrioridade(relatorioQuantitativoProblemaDto);
	}

	@Override
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorCategoria(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		return getDao().listaQuantidadeProblemaPorCategoria(relatorioQuantitativoProblemaDto);
	}

	@Override
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorProprietario(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		return getDao().listaQuantidadeProblemaPorProprietario(relatorioQuantitativoProblemaDto);
	}

	@Override
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorImpacto(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		return getDao().listaQuantidadeProblemaPorImpacto(relatorioQuantitativoProblemaDto);
	}

	@Override
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorUrgencia(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		return getDao().listaQuantidadeProblemaPorUrgencia(relatorioQuantitativoProblemaDto);
	}

	public void encerra(ProblemaDTO problemaDTO, TransactionControler tc) throws Exception {
		ProblemaDTO problemaDTOAux = restoreAll(problemaDTO.getIdProblema(), tc);
		new ExecucaoProblemaServiceEjb().encerra(problemaDTOAux, tc);
	}

	public boolean verificaPermissaoGrupoCancelar(Integer idTipoProblema, Integer idGrupo) throws ServiceException, Exception {
		boolean isOk = false;
		CategoriaProblemaService categoriaProblemaService = (CategoriaProblemaService) ServiceLocator.getInstance().getService(CategoriaProblemaService.class, null);
		CategoriaProblemaDTO categoriaProblemaDTO = new CategoriaProblemaDTO();
		PermissoesFluxoDao permissoesDao = new PermissoesFluxoDao();

		/* Desenvolvedor: euler.ramos Data: 25/10/2013 Horário: 10h20min ID Citsmart: 120393 Motivo/Comentário: idTipoProblema era null e causava problema na hora do restore */
		if ((idTipoProblema != null) && (idTipoProblema.intValue() > 0)) {
			categoriaProblemaDTO.setIdCategoriaProblema(idTipoProblema);
			categoriaProblemaDTO = (CategoriaProblemaDTO) categoriaProblemaService.restore(categoriaProblemaDTO);
			if (categoriaProblemaDTO != null) {
				PermissoesFluxoDTO permissoesDto = permissoesDao.permissaoGrupoCancelar(idGrupo, categoriaProblemaDTO.getIdTipoFluxo());
				if (permissoesDto != null && permissoesDto.getCancelar() != null && permissoesDto.getCancelar().equalsIgnoreCase("S"))
					isOk = true;
			}
		}
		return isOk;
	}

	public boolean validacaoGrupoExecutor(ProblemaDTO problemaDto) throws Exception {
		boolean resultado = false;

		if (problemaDto != null && problemaDto.getIdGrupoAtual() == null)
			problemaDto.setIdGrupoAtual(problemaDto.getIdGrupo());

		if (problemaDto != null && problemaDto.getIdGrupoAtual() != null && problemaDto.getIdCategoriaProblema() != null) {
			Integer idGrupoExecutor = problemaDto.getIdGrupoAtual();
			Integer idTipoProblema = problemaDto.getIdCategoriaProblema();

			PermissoesFluxoService permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);

			resultado = permissoesFluxoService.permissaoGrupoExecutorProblema(idTipoProblema, idGrupoExecutor);
		}
		return resultado;
	}

	public void fechaProblemaERelacionamento(ProblemaDTO problemaDTO, TransactionControler tc) {
		try {
			this.encerra(problemaDTO, tc);
			this.fechaRelacionamentoProblema(problemaDTO, tc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
	}

	public void fechaRelacionamentoProblema(ProblemaDTO problemaDTO, TransactionControler tc) {
		try {
			if (problemaDTO != null && problemaDTO.getFecharItensRelacionados() != null) {
				this.fechaSolicitacaoServicoVinculadaProblema(problemaDTO, tc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
	}

	public void fechaSolicitacaoServicoVinculadaProblema(ProblemaDTO problemaDTO, TransactionControler tc) {
		try {
			SolicitacaoServicoProblemaDao solicitacaoServicoProblemaDao = new SolicitacaoServicoProblemaDao();
			solicitacaoServicoProblemaDao.setTransactionControler(tc);
			List<SolicitacaoServicoDTO> listSolicitacaoServicoDTO = (List<SolicitacaoServicoDTO>) solicitacaoServicoProblemaDao.listSolicitacaoServicoByIdProblema(problemaDTO.getIdProblema()
					.intValue());
			if (listSolicitacaoServicoDTO != null && listSolicitacaoServicoDTO.size() > 0) {
				for (SolicitacaoServicoDTO solicitacaoServicoDTO : listSolicitacaoServicoDTO) {
					new SolicitacaoServicoServiceEjb().fechaSolicitacaoServicoVinculadaByProblemaOrMudanca(solicitacaoServicoDTO, tc);
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fechaItemConfiguracaoVinculadoProblema(ProblemaDTO problemaDTO, TransactionControler tc) {
		try {
			ProblemaItemConfiguracaoDAO problemaItemConfiguracaoDao = new ProblemaItemConfiguracaoDAO();
			problemaItemConfiguracaoDao.setTransactionControler(tc);
			List<ItemConfiguracaoDTO> listItemConfiguracaoDTO = problemaItemConfiguracaoDao.listItemConfiguracaoByIdProblema(problemaDTO.getIdProblema().intValue());
			if (listItemConfiguracaoDTO != null && listItemConfiguracaoDTO.size() > 0) {
				for (ItemConfiguracaoDTO itemConfiguracaoDTO : listItemConfiguracaoDTO) {
					new ItemConfiguracaoServiceEjb().finalizarItemConfiguracao(itemConfiguracaoDTO, tc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fechaErroConhecidodoProblema(ProblemaDTO problemaDTO, TransactionControler tc) {
		ConhecimentoProblemaDao conhecimentoProblemaDao = new ConhecimentoProblemaDao();
		try {
			List<BaseConhecimentoDTO> listaBaseConhecimentoDTO = (List<BaseConhecimentoDTO>) conhecimentoProblemaDao.listaErroConhecidoByIdProblema(problemaDTO.getIdProblema());

			if (listaBaseConhecimentoDTO != null && listaBaseConhecimentoDTO.size() > 0) {
				for (BaseConhecimentoDTO baseConhecimentoDTO : listaBaseConhecimentoDTO) {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateTimeAction(Integer idGrupoRedirect, Integer idPrioridadeRedirect, Integer idProblema) throws ServiceException, LogicException {
		ExecucaoProblemaServiceEjb execucaoProblemaService = new ExecucaoProblemaServiceEjb();
		ProblemaDAO problemaDao = this.getDao();
		OcorrenciaProblemaDAO ocorrenciaProblemaDao = new OcorrenciaProblemaDAO();

		TransactionControler tc = new TransactionControlerImpl(problemaDao.getAliasDB());

		try {
			tc.start();

			// Faz validacao, caso exista.

			problemaDao.setTransactionControler(tc);
			ocorrenciaProblemaDao.setTransactionControler(tc);

			List<ProblemaDTO> listaProblema = new ArrayList<ProblemaDTO>();

			ProblemaDTO problemaAuxDto = new ProblemaDTO();
			problemaAuxDto.setIdProblema(idProblema);

			listaProblema = (List<ProblemaDTO>) problemaDao.find(problemaAuxDto);
			if (listaProblema != null) {
				problemaAuxDto = listaProblema.get(0);
			}

			ProblemaDTO problemaDto = new ProblemaDTO();

			problemaDto.setIdGrupoAtual(idGrupoRedirect);
			problemaDto.setIdGrupo(idGrupoRedirect);
			problemaDto.setPrioridade(idPrioridadeRedirect);
			problemaDto.setIdProblema(idProblema);

			problemaDao.updateNotNull(problemaDto);
			execucaoProblemaService.direcionaAtendimentoAutomatico(problemaDto, tc);

			String strOcorr = "\nEscalação automática.";

			JustificativaProblemaDTO justificativaDto = new JustificativaProblemaDTO();
			justificativaDto.setIdJustificativaProblema(problemaDto.getIdJustificativaProblema());
			justificativaDto.setDescricaoProblema(problemaDto.getComplementoJustificativa());

			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setLogin("Automático");

			OcorrenciaProblemaServiceEjb.create(problemaDto, null, strOcorr, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Atualizacao, null, CategoriaOcorrencia.Atualizacao.getDescricao(),
					usuarioDTO.getLogin(), 0, justificativaDto, tc);

			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}
	}

	/**
	 * Adicionado para gravar anexo
	 *
	 * @author mario.junior
	 * @since 31/10/2013 09:00
	 */
	public void gravaInformacoesGED(Collection colArquivosUpload, int idEmpresa, ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		// Setando a transaction no GED
		ControleGEDDao controleGEDDao = new ControleGEDDao();
		if (tc != null) {
			controleGEDDao.setTransactionControler(tc);
		}

		String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "");
		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "";
		}

		if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
		}

		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "/ged";
		}
		String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");
		if (PRONTUARIO_GED_INTERNO == null) {
			PRONTUARIO_GED_INTERNO = "S";
		}
		String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");
		if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados))
			prontuarioGedInternoBancoDados = "N";
		String pasta = "";
		if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
			pasta = controleGEDDao.getProximaPastaArmazenar();
			File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
		}

		for (Iterator it = colArquivosUpload.iterator(); it.hasNext();) {
			UploadDTO uploadDTO = (UploadDTO) it.next();
			ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
			controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_PROBLEMA);
			controleGEDDTO.setId(problemaDto.getIdProblema());
			controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
			controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());
			controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));
			controleGEDDTO.setPasta(pasta);
			controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile());

			if (!uploadDTO.getTemporario().equalsIgnoreCase("S")) { // Se nao //
																	// for //
																	// temporario
				continue;
			}

			if (PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados.trim())) { // Se
				// utiliza
				// GED
				// interno e eh BD
				controleGEDDTO.setPathArquivo(uploadDTO.getPath()); // Isso vai
																	// fazer a
																	// gravacao
																	// no BD.
																	// dento do
																	// create
																	// abaixo.
			} else {
				controleGEDDTO.setPathArquivo(null);
			}
			controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);
			if (controleGEDDTO != null) {
				uploadDTO.setIdControleGED(controleGEDDTO.getIdControleGED());
			}
			if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se
																															// utiliza
																															// GED
				// interno e nao eh BD
				if (controleGEDDTO != null) {
					try {
						File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
								+ Util.getFileExtension(uploadDTO.getNameFile()));
						CriptoUtils.encryptFile(uploadDTO.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", System
								.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
						arquivo.delete();
					} catch (Exception e) {

					}

				}
			} /*
			 * else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { // Se // utiliza // GED // externo // FALTA IMPLEMENTAR!!! }
			 */
		}
		Collection<ControleGEDDTO> colAnexo = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_PROBLEMA, problemaDto.getIdProblema());
		if (colAnexo != null) {
			for (ControleGEDDTO dtoGed : colAnexo) {
				boolean b = false;
				for (Iterator it = colArquivosUpload.iterator(); it.hasNext();) {
					UploadDTO uploadDTO = (UploadDTO) it.next();
					if (uploadDTO.getIdControleGED() == null) {
						b = true;
						break;
					}
					if (uploadDTO.getIdControleGED().intValue() == dtoGed.getIdControleGED().intValue()) {
						b = true;
					}
					if (b) {
						break;
					}
				}
				if (!b) {
					controleGEDDao.delete(dtoGed);
				}
			}
		}
	}

	public Collection findByProblemaRelacionado(ProblemaRelacionadoDTO bean) throws Exception {
		ProblemaRelacionadoDao problemaRelacionadoDao = new ProblemaRelacionadoDao();
		return problemaRelacionadoDao.findByIdProblema(bean.getIdProblema());
	}

}
