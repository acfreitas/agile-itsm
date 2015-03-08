/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.bpm.negocio.Tarefa;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CalculoJornadaDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ContatoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ControleQuestionariosDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ExecucaoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.FaseServicoDTO;
import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ItemCfgSolicitacaoServDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PesquisaSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.PrioridadeServicoUnidadeDTO;
import br.com.centralit.citcorpore.bean.PrioridadeServicoUsuarioDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RelatorioCausaSolucaoDTO;
import br.com.centralit.citcorpore.bean.RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO;
import br.com.centralit.citcorpore.bean.RelatorioEficaciaTesteDTO;
import br.com.centralit.citcorpore.bean.RelatorioIncidentesNaoResolvidosDTO;
import br.com.centralit.citcorpore.bean.RelatorioKpiProdutividadeDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoRetornoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO;
import br.com.centralit.citcorpore.bean.RelatorioSolicitacaoPorExecutanteDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoEvtMonDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoMudancaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoProblemaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TempoAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoSolicitacao;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.AcordoServicoContratoDao;
import br.com.centralit.citcorpore.integracao.BaseConhecimentoDAO;
import br.com.centralit.citcorpore.integracao.ConhecimentoSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.ContatoSolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.ContratoDao;
import br.com.centralit.citcorpore.integracao.ControleQuestionariosDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.ExecucaoSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.FaseServicoDao;
import br.com.centralit.citcorpore.integracao.FluxoServicoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.ItemCfgSolicitacaoServDAO;
import br.com.centralit.citcorpore.integracao.MatrizPrioridadeDAO;
import br.com.centralit.citcorpore.integracao.ModeloEmailDao;
import br.com.centralit.citcorpore.integracao.OcorrenciaSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.PastaDAO;
import br.com.centralit.citcorpore.integracao.PrioridadeAcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.PrioridadeServicoUnidadeDao;
import br.com.centralit.citcorpore.integracao.PrioridadeServicoUsuarioDao;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoEvtMonDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoMudancaDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoProblemaDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoQuestionarioDao;
import br.com.centralit.citcorpore.integracao.TempoAcordoNivelServicoDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioDao;
import br.com.centralit.citquestionario.negocio.RespostaItemQuestionarioServiceBean;
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
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;

@SuppressWarnings({ "unchecked", "rawtypes", "unused", "static-access" })
public class SolicitacaoServicoServiceEjb extends CrudServiceImpl implements SolicitacaoServicoService {

	private SolicitacaoServicoDao solicitacaoServicoDao;

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
		try {
			tc.start();
			model = create(model, tc, true, true, true);
			if(model == null)
				throw new Exception();
			tc.commit();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		} finally {
			try {
				tc.close();
			} catch (PersistenceException e) {
				e.printStackTrace();
			}
		}
		return model;
	}
	

	public IDto create(IDto model, TransactionControler tc, boolean determinaPrioridadePrazo, boolean determinaHoraInicio, boolean determinaDataHoraSolicitacao) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) model;

		ExecucaoSolicitacaoServiceEjb execucaoSolicitacaoService = new ExecucaoSolicitacaoServiceEjb();
		ContatoSolicitacaoServicoDao contatoSolicitacaoServicoDao = new ContatoSolicitacaoServicoDao();
		ContatoSolicitacaoServicoDTO contatoSolicitacaoServicoDTO = new ContatoSolicitacaoServicoDTO();
		SolicitacaoServicoProblemaDao solicitacaoServicoProblemaDao = new SolicitacaoServicoProblemaDao();
		SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();
		ConhecimentoSolicitacaoDao conhecimentoSolicitacaoDao = new ConhecimentoSolicitacaoDao();
		ItemCfgSolicitacaoServDAO itemCfgSolicitacaoServDAO = new ItemCfgSolicitacaoServDAO();
		SolicitacaoServicoEvtMonDao solicitacaoServicoEvtMonDao = new SolicitacaoServicoEvtMonDao();

		ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
		GrupoDao grupoDao = new GrupoDao();

			// Faz validacao, caso exista.
			validaCreate(model);

			this.getDao().setTransactionControler(tc);
			contatoSolicitacaoServicoDao.setTransactionControler(tc);
			contatoSolicitacaoServicoDTO.setNomecontato(solicitacaoServicoDto.getNomecontato());
			contatoSolicitacaoServicoDTO.setEmailcontato(solicitacaoServicoDto.getEmailcontato());
			contatoSolicitacaoServicoDTO.setTelefonecontato(solicitacaoServicoDto.getTelefonecontato());
			contatoSolicitacaoServicoDTO.setObservacao(solicitacaoServicoDto.getObservacao());
			contatoSolicitacaoServicoDTO.setRamal(solicitacaoServicoDto.getRamal());
			solicitacaoServicoProblemaDao.setTransactionControler(tc);
			solicitacaoServicoMudancaDao.setTransactionControler(tc);
			conhecimentoSolicitacaoDao.setTransactionControler(tc);
			itemCfgSolicitacaoServDAO.setTransactionControler(tc);

			solicitacaoServicoEvtMonDao.setTransactionControler(tc);
			servicoContratoDao.setTransactionControler(tc);
			grupoDao.setTransactionControler(tc);

			if (solicitacaoServicoDto.getIdLocalidade() != null) {
				contatoSolicitacaoServicoDTO.setIdLocalidade(solicitacaoServicoDto.getIdLocalidade());
			}
			contatoSolicitacaoServicoDTO = (ContatoSolicitacaoServicoDTO) contatoSolicitacaoServicoDao.create(contatoSolicitacaoServicoDTO);

			ServicoContratoDTO servicoContratoDto = servicoContratoDao.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());

			if (servicoContratoDto == null)
				throw new LogicException(i18nMessage("solicitacaoservico.validacao.servicolocalizado"));

			if (solicitacaoServicoDto.getIdServicoContrato() == null) {
				solicitacaoServicoDto.setIdServicoContrato(servicoContratoDto.getIdServicoContrato());
			}

			UsuarioDTO usuarioDto = solicitacaoServicoDto.getUsuarioDto();
			solicitacaoServicoDto.setIdResponsavel(usuarioDto.getIdUsuario());

			solicitacaoServicoDto.setIdCalendario(servicoContratoDto.getIdCalendario());
			solicitacaoServicoDto.setTempoDecorridoHH(new Integer(0));
			solicitacaoServicoDto.setTempoDecorridoMM(new Integer(0));
			solicitacaoServicoDto.setDataHoraSuspensao(null);
			solicitacaoServicoDto.setDataHoraReativacao(null);
			solicitacaoServicoDto.setDataHoraInicioSLA(null);
			solicitacaoServicoDto.setSituacaoSLA(SituacaoSLA.N.name());

			if (solicitacaoServicoDto.getIdGrupoNivel1() == null || solicitacaoServicoDto.getIdGrupoNivel1().intValue() <= 0) {
				Integer idGrupoNivel1 = null;
				if (servicoContratoDto.getIdGrupoNivel1() != null && servicoContratoDto.getIdGrupoNivel1().intValue() > 0) {
					idGrupoNivel1 = servicoContratoDto.getIdGrupoNivel1();
				} else {
				String idGrupoN1 = ParametroUtil.getValor(ParametroSistema.ID_GRUPO_PADRAO_NIVEL1, null, null);
					if (idGrupoN1 != null && !idGrupoN1.trim().equalsIgnoreCase("")) {
						try {
							idGrupoNivel1 = new Integer(idGrupoN1);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if (idGrupoNivel1 == null || idGrupoNivel1.intValue() <= 0)
					throw new LogicException(i18nMessage("solicitacaoservico.validacao.grupoatendnivel"));
				GrupoDTO grupoDto = new GrupoDTO();
				grupoDto.setIdGrupo(idGrupoNivel1);
				grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
				if (grupoDto == null || grupoDto.getDataFim() != null)
					throw new LogicException(i18nMessage("solicitacaoservico.validacao.grupoatendnivel"));
				solicitacaoServicoDto.setIdGrupoNivel1(idGrupoNivel1);
			}

			FluxoServicoDao fluxoServicoDao = new FluxoServicoDao();
			TipoFluxoDao tipoFluxoDao = new TipoFluxoDao();
			TipoFluxoDTO tipoFluxoDto = new TipoFluxoDTO();
			FluxoServicoDTO fluxoServicoDto = fluxoServicoDao.findPrincipalByIdServicoContrato(servicoContratoDto.getIdServicoContrato());

			int idTipoFluxoSolicitacaoServico = 0;

			// Verifica se há fluxo associado ao serviço contrato
			if (fluxoServicoDto != null && fluxoServicoDto.getIdTipoFluxo() != null) {
				idTipoFluxoSolicitacaoServico = fluxoServicoDto.getIdTipoFluxo();
			} else {
				// Verifica o fluxo padrão para Solicitação Serviço definido em Parâmetro
				String nomeFluxoPadraoSolicitacaoServico = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NomeFluxoPadraoServicos, "SolicitacaoServico");

				if (nomeFluxoPadraoSolicitacaoServico != null) {
					tipoFluxoDto = tipoFluxoDao.findByNome(nomeFluxoPadraoSolicitacaoServico);

					if (tipoFluxoDto != null && tipoFluxoDto.getIdTipoFluxo() != null) {
						idTipoFluxoSolicitacaoServico = tipoFluxoDto.getIdTipoFluxo();
					}
				}
			}

			int idGrupo = 0;
			if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdGrupoAtual() != null) {
				idGrupo = solicitacaoServicoDto.getIdGrupoAtual();
		/* Inserido por Carlos Santos em 05/11/2013 -> Antes de testar o nível 1, deve ser testado o grupo executor do contrato */
			} else if (servicoContratoDto != null && servicoContratoDto.getIdGrupoExecutor() != null){
				idGrupo = servicoContratoDto.getIdGrupoExecutor();
			}else if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdGrupoNivel1() != null) {
				idGrupo = solicitacaoServicoDto.getIdGrupoNivel1();
			}

			if (idGrupo > 0 && idTipoFluxoSolicitacaoServico > 0) {

				boolean resultado = permissaoGrupoExecutorServico(idGrupo, idTipoFluxoSolicitacaoServico);

				if (resultado == false) {
					throw new LogicException(i18nMessage("solicitacaoServico.grupoSemPermissao"));
				}
			} else {
				throw new LogicException(i18nMessage("fluxo.fluxoserviconaodefinido "));
			}

			solicitacaoServicoDto.setIdContatoSolicitacaoServico(contatoSolicitacaoServicoDTO.getIdcontatosolicitacaoservico());
			if (determinaDataHoraSolicitacao) {
				solicitacaoServicoDto.setDataHoraSolicitacao(new Timestamp(new java.util.Date().getTime()));
			}

			if (determinaPrioridadePrazo) {
				determinaPrioridadeEPrazo(solicitacaoServicoDto, tc);
			}
			if (determinaHoraInicio) {
				solicitacaoServicoDto.setDataHoraInicio(new Timestamp(new java.util.Date().getTime()));
			}

			solicitacaoServicoDto.setSeqReabertura(new Integer(0));

			if (solicitacaoServicoDto.escalada()) {
				String tipoCaptura = ParametroUtil.getValor(ParametroSistema.TIPO_CAPTURA_SOLICITACOES, tc, "1");
				if (tipoCaptura.equals("2"))
					solicitacaoServicoDto.setDataHoraCaptura(solicitacaoServicoDto.getDataHoraInicio());
			}

			solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoDao.create(solicitacaoServicoDto);

			if (solicitacaoServicoDto.getColItensProblema() != null) {
				for (Iterator it = solicitacaoServicoDto.getColItensProblema().iterator(); it.hasNext();) {
					ProblemaDTO problemaDTO = (ProblemaDTO) it.next();
					SolicitacaoServicoProblemaDTO solicitacaoServicoProblemaDTO = new SolicitacaoServicoProblemaDTO();
					solicitacaoServicoProblemaDTO.setIdProblema(problemaDTO.getIdProblema());
					solicitacaoServicoProblemaDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
					solicitacaoServicoProblemaDao.create(solicitacaoServicoProblemaDTO);
				}
			}

			if (solicitacaoServicoDto.getColItensMudanca() != null) {

				for (Iterator it = solicitacaoServicoDto.getColItensMudanca().iterator(); it.hasNext();) {
					RequisicaoMudancaDTO requisicaoMudancaDTO = (RequisicaoMudancaDTO) it.next();

					SolicitacaoServicoMudancaDTO solicitacaoServicoMudancaDTO = new SolicitacaoServicoMudancaDTO();
					solicitacaoServicoMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
					solicitacaoServicoMudancaDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
					solicitacaoServicoMudancaDao.create(solicitacaoServicoMudancaDTO);
				}
			}

			if (solicitacaoServicoDto.getColItensICSerialize() != null) {
				for (ItemConfiguracaoDTO bean : solicitacaoServicoDto.getColItensICSerialize()) {
					ItemCfgSolicitacaoServDTO dto = new ItemCfgSolicitacaoServDTO();
					dto.setIdItemConfiguracao(bean.getIdItemConfiguracao());
					dto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
					dto.setDataInicio(Util.getSqlDataAtual());

					itemCfgSolicitacaoServDAO.create(dto);
				}
			}

			if (solicitacaoServicoDto.getColItensBaseConhecimento() != null) {
				for (Iterator it = solicitacaoServicoDto.getColItensBaseConhecimento().iterator(); it.hasNext();) {
					BaseConhecimentoDTO baseConhecimentoDTO = (BaseConhecimentoDTO) it.next();

					ConhecimentoSolicitacaoDTO conhecimentoSolicitacaoDTO = new ConhecimentoSolicitacaoDTO();
					conhecimentoSolicitacaoDTO.setIdBaseConhecimento(baseConhecimentoDTO.getIdBaseConhecimento());
					conhecimentoSolicitacaoDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
					conhecimentoSolicitacaoDao.create(conhecimentoSolicitacaoDTO);
				}
			}

			if (solicitacaoServicoDto.getInformacoesComplementares() != null || solicitacaoServicoDto.getSolicitacaoServicoQuestionarioDTO() != null) {
				TemplateSolicitacaoServicoDTO templateDto = new TemplateSolicitacaoServicoServiceEjb().recuperaTemplateServico(solicitacaoServicoDto);
				if (templateDto != null) {
					if (templateDto.isQuestionario()) {
						atualizaInformacoesQuestionario(solicitacaoServicoDto, tc);
					} else if (templateDto.getNomeClasseServico() != null) {
						ComplemInfSolicitacaoServicoService informacoesComplementaresService = getInformacoesComplementaresService(templateDto.getNomeClasseServico());
						informacoesComplementaresService.create(tc, solicitacaoServicoDto, solicitacaoServicoDto.getInformacoesComplementares());
					}
				}
			}

			try {
				OcorrenciaSolicitacaoServiceEjb.create(solicitacaoServicoDto, null, null, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Criacao, new Gson().toJson(contatoSolicitacaoServicoDTO),
						CategoriaOcorrencia.Criacao.getDescricao(), usuarioDto, 0, null, tc);
			} catch (Exception e) {
				e.printStackTrace();
			}

			ExecucaoSolicitacao execucaoSolicitacao = execucaoSolicitacaoService.registraSolicitacao(solicitacaoServicoDto, tc);
			if(execucaoSolicitacao == null){
				tc.rollback();
				return null;
			}

			if (solicitacaoServicoDto.getColArquivosUpload() != null && solicitacaoServicoDto.getColArquivosUpload().size() > 0) {
				gravaInformacoesGED(solicitacaoServicoDto.getColArquivosUpload(), 1, solicitacaoServicoDto, tc);
			}

			Source source = new Source(solicitacaoServicoDto.getRegistroexecucao());
			solicitacaoServicoDto.setRegistroexecucao(source.getTextExtractor().toString());

			if (solicitacaoServicoDto.getRegistroexecucao() != null && !solicitacaoServicoDto.getRegistroexecucao().trim().equalsIgnoreCase("")) {
				TarefaFluxoDTO tarefaDto = null;
				if (solicitacaoServicoDto.getIdTarefa() != null) {
					tarefaDto = new TarefaFluxoDTO();
					tarefaDto.setIdItemTrabalho(solicitacaoServicoDto.getIdTarefa());
				}

				OcorrenciaSolicitacaoServiceEjb.create(solicitacaoServicoDto, tarefaDto, solicitacaoServicoDto.getRegistroexecucao(), OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Execucao, null,
						CategoriaOcorrencia.Execucao.getDescricao(), usuarioDto, 0, null, tc);
			}

			if (solicitacaoServicoDto.getColSolicitacaoServicoEvtMon() != null) {
				for (Iterator it = solicitacaoServicoDto.getColSolicitacaoServicoEvtMon().iterator(); it.hasNext();) {
					SolicitacaoServicoEvtMonDTO solicitacaoServicoEvtMonDTO = (SolicitacaoServicoEvtMonDTO) it.next();
					solicitacaoServicoEvtMonDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
					solicitacaoServicoEvtMonDao.create(solicitacaoServicoEvtMonDTO);
				}
			}

			if (solicitacaoServicoDto != null && solicitacaoServicoDto.getBeanBaseConhecimento() != null && solicitacaoServicoDto.getBeanBaseConhecimento().getTitulo() != null
					&& !solicitacaoServicoDto.getBeanBaseConhecimento().getTitulo().isEmpty()) {
				this.InserirNaBaseConhecimento(solicitacaoServicoDto, tc);
			}

		return solicitacaoServicoDto;
	}

	// Criado por Bruno.Aquino
	// Se a Situação estiver como "Resolvida" Capturo: a descricao do problema e a Solução/Resposta
	public void InserirNaBaseConhecimento(SolicitacaoServicoDTO solicitacaoServicoDTO, TransactionControler tc) throws ServiceException, Exception {

		BaseConhecimentoDTO beanBaseConhecimento = solicitacaoServicoDTO.getBeanBaseConhecimento();
		beanBaseConhecimento.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());

		BaseConhecimentoDAO baseConhecimentoDAO = new BaseConhecimentoDAO();
		PastaDAO pastaDao = new PastaDAO();

		pastaDao.setTransactionControler(tc);
		baseConhecimentoDAO.setTransactionControler(tc);

		BaseConhecimentoDTO baseAux = baseConhecimentoDAO.findByIdSolicitacaoServico(solicitacaoServicoDTO);

		// verifica se já não existe uma registro na base de conhecimento referente a essa solicitação, só pode armazenar um.
		if (baseAux == null) {

			PastaServiceEjb pastaEjb = new PastaServiceEjb();
			PastaDTO pastaBean = new PastaDTO();
			int cont = 0;

			// verifica se a pasta existe
			Collection<PastaDTO> lista = pastaEjb.consultarPastasAtivas();
			for (PastaDTO p : lista) {
				if (p.getNome() != null) {
					if (p.getNome().equals(
							ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PASTA_SALVA_DESCRICAO_RESPOSTA_DE_SOLICITACAOSERVICO_EM_BASECONHECIMENTO,
									"Descrição_Resposta_Para_BaseConhecimento"))) {
						pastaBean.setId(p.getId());
						cont++;
					}
				}
			}

			// se a pasta não existir, pasta vai ser criada pelo parametro ou com o nome do padrão
			if (cont == 0) {
				pastaBean.setNome(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PASTA_SALVA_DESCRICAO_RESPOSTA_DE_SOLICITACAOSERVICO_EM_BASECONHECIMENTO,
						"Descrição_Resposta_Para_BaseConhecimento"));
				pastaDao.create(pastaBean);

				Collection<PastaDTO> lista2 = pastaEjb.consultarPastasAtivas();
				for (PastaDTO p2 : lista2) {
					if (p2.getNome() != null) {
						if (p2.getNome().equals(
								ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PASTA_SALVA_DESCRICAO_RESPOSTA_DE_SOLICITACAOSERVICO_EM_BASECONHECIMENTO,
										"Descrição_Resposta_Para_BaseConhecimento"))) {
							pastaBean.setId(p2.getId());
						}
					}
				}
			}

			beanBaseConhecimento.setIdPasta(pastaBean.getId());
			baseConhecimentoDAO.create(beanBaseConhecimento);
		}
	}

	public void PersistirItemBaseConhecimento(SolicitacaoServicoDTO solicitacaoServicoDto, ConhecimentoSolicitacaoDao conhecimentoSolicitacaoDao) throws Exception {
		List<ConhecimentoSolicitacaoDTO> listaTelaConhecimentoDTO = solicitacaoServicoDto.getColConhecimentoSolicitacaoSerialize();
		if (listaTelaConhecimentoDTO != null) {
			ConhecimentoSolicitacaoDTO dto;
			// Inserindo no Banco de Dados os Itens da lista ainda não cadastrados
			for (ConhecimentoSolicitacaoDTO bean : listaTelaConhecimentoDTO) {
				dto = new ConhecimentoSolicitacaoDTO();
				dto.setIdBaseConhecimento(bean.getIdBaseConhecimento());
				dto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
				if (!conhecimentoSolicitacaoDao.seCadastrada(dto)) {
					conhecimentoSolicitacaoDao.create(dto);
				}
			}
			// Apagando Itens no banco que não estão na lista informada
			Collection<ConhecimentoSolicitacaoDTO> listaDBConhecimentoSolicitacaoDTO = conhecimentoSolicitacaoDao.findByidSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
			Boolean encontrou;
			for (ConhecimentoSolicitacaoDTO elemento : listaDBConhecimentoSolicitacaoDTO) {
				encontrou = new Boolean("False");
				for (ConhecimentoSolicitacaoDTO itemBaseConhecimento : listaTelaConhecimentoDTO) {
					if (elemento.getIdBaseConhecimento().equals(itemBaseConhecimento.getIdBaseConhecimento())) {
						encontrou = true;
						break;
					}
				}
				if (!encontrou) {
					conhecimentoSolicitacaoDao.delete(elemento);
				}
			}
			// Para o Garbage Collection agir mais rápido
			listaTelaConhecimentoDTO = null;
			dto = null;
			listaDBConhecimentoSolicitacaoDTO = null;
			encontrou = null;
		}
	}

	@Override
	public void deserializaInformacoesComplementares(SolicitacaoServicoDTO solicitacaoServicoDto, SolicitacaoServicoQuestionarioDTO solQuestionarioDto) throws Exception {
		if (solicitacaoServicoDto.getInformacoesComplementares_serialize() != null) {
			TemplateSolicitacaoServicoDTO templateDto = new TemplateSolicitacaoServicoServiceEjb().recuperaTemplateServico(solicitacaoServicoDto);
			if (templateDto != null && templateDto.getNomeClasseServico() != null) {
				if (templateDto.isQuestionario()) {
					solicitacaoServicoDto.setSolicitacaoServicoQuestionarioDTO(solQuestionarioDto);
				} else {
					ComplemInfSolicitacaoServicoService informacoesComplementaresService = getInformacoesComplementaresService(templateDto.getNomeClasseServico());
					IDto informacoesComplementares = informacoesComplementaresService.deserializaObjeto(solicitacaoServicoDto.getInformacoesComplementares_serialize());
					solicitacaoServicoDto.setInformacoesComplementares(informacoesComplementares);
				}
			}
			solicitacaoServicoDto.setInformacoesComplementares_serialize(null);
		}
	}

	public void determinaPrazoLimiteSolicitacaoACombinarReclassificada(SolicitacaoServicoDTO solicitacaoDto, Integer idCalendarioParm, TransactionControler tc) throws Exception {
		new ExecucaoSolicitacaoServiceEjb().determinaPrazoLimiteSolicitacaoACombinarReclassificada(solicitacaoDto, idCalendarioParm, tc);
	}

	public void determinaPrioridadeEPrazo(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		if (solicitacaoServicoDto.getIdSolicitante() != null) {
			EmpregadoDao empregadoDao = new EmpregadoDao();
			if (tc != null)
				empregadoDao.setTransactionControler(tc);

			EmpregadoDTO empregadoDTO = null;
			empregadoDTO = (EmpregadoDTO) empregadoDao.restoreByIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
			if (empregadoDTO != null && empregadoDTO.getIdUnidade() != null) {
				if (solicitacaoServicoDto.getIdUnidade() == null || solicitacaoServicoDto.getIdUnidade().intValue() == 0) {
					solicitacaoServicoDto.setIdUnidade(empregadoDTO.getIdUnidade());
				}
				PrioridadeServicoUnidadeDao prioridadeServicoUnidadeDao = new PrioridadeServicoUnidadeDao();
				if (tc != null)
					prioridadeServicoUnidadeDao.setTransactionControler(tc);
				PrioridadeServicoUnidadeDTO prioridadeServicoUnidadeDto = prioridadeServicoUnidadeDao.restore(solicitacaoServicoDto.getIdServicoContrato(), empregadoDTO.getIdUnidade());
				if (prioridadeServicoUnidadeDto != null)
					solicitacaoServicoDto.setIdPrioridade(prioridadeServicoUnidadeDto.getIdPrioridade());

			}
		}

		AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
		if (tc != null)
			acordoNivelServicoDao.setTransactionControler(tc);

		AcordoNivelServicoDTO acordoNivelServicoDto = acordoNivelServicoDao.findAtivoByIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato(), "T");
		if (acordoNivelServicoDto == null) {
			AcordoServicoContratoDao acordoServicoContratoDao = new AcordoServicoContratoDao();
			if (tc != null)
				acordoServicoContratoDao.setTransactionControler(tc);

			// Se nao houver acordo especifico, ou seja, associado direto ao
			// servicocontrato, entao busca um acordo geral que esteja vinculado
			// ao servicocontrato.
			AcordoServicoContratoDTO acordoServicoContratoDTO = acordoServicoContratoDao.findAtivoByIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato(), "T");
			if (acordoServicoContratoDTO == null) {
				throw new Exception(i18nMessage("solicitacaoservico.validacao.tempoacordo"));
			}
			// Apos achar a vinculacao do acordo com o servicocontrato, entao
			// faz um restore do acordo de nivel de servico.
			acordoNivelServicoDto = new AcordoNivelServicoDTO();
			acordoNivelServicoDto.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
			acordoNivelServicoDto = (AcordoNivelServicoDTO) acordoNivelServicoDao.restore(acordoNivelServicoDto);
			if (acordoNivelServicoDto == null) {
				// Se nao houver acordo especifico, ou seja, associado direto ao
				// servicocontrato
				throw new Exception(i18nMessage("solicitacaoservico.validacao.tempoacordo"));
			}
			solicitacaoServicoDto.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());

			// Consulta prioridade do usuário de acordo com sla global
			PrioridadeServicoUsuarioDao prioridadeServicoUsuarioDao = new PrioridadeServicoUsuarioDao();
			if (tc != null)
				prioridadeServicoUsuarioDao.setTransactionControler(tc);
			PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO = prioridadeServicoUsuarioDao.findByIdAcordoNivelServicoAndIdUsuario(solicitacaoServicoDto.getIdAcordoNivelServico(),
					solicitacaoServicoDto.getIdSolicitante());
			if (prioridadeServicoUsuarioDTO != null)
				solicitacaoServicoDto.setIdPrioridade(prioridadeServicoUsuarioDTO.getIdPrioridade());

			// Consulta prioridade da unidade do usuário de acordo com sla global
			PrioridadeAcordoNivelServicoDao prioridadeAcordoNivelServicoDao = new PrioridadeAcordoNivelServicoDao();
			if (tc != null)
				prioridadeAcordoNivelServicoDao.setTransactionControler(tc);
			PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO = prioridadeAcordoNivelServicoDao.findByIdAcordoNivelServicoAndIdUnidade(solicitacaoServicoDto.getIdAcordoNivelServico(),
					solicitacaoServicoDto.getIdUnidade());
			if (prioridadeAcordoNivelServicoDTO != null)
				solicitacaoServicoDto.setIdPrioridade(prioridadeAcordoNivelServicoDTO.getIdPrioridade());

		} else {
			solicitacaoServicoDto.setIdAcordoNivelServico(acordoNivelServicoDto.getIdAcordoNivelServico());
		}

		if (solicitacaoServicoDto.getUrgencia() == null || solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("")) {
			solicitacaoServicoDto.setUrgencia("B");
		}
		if (solicitacaoServicoDto.getImpacto() == null || solicitacaoServicoDto.getImpacto().equalsIgnoreCase("")) {
			solicitacaoServicoDto.setImpacto("B");
		}

		String calcularDinamicamente = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CALCULAR_PRIORIDADE_SOLICITACAO_DINAMICAMENTE, "N");

		if (!calcularDinamicamente.trim().equalsIgnoreCase("S")) {
			if (solicitacaoServicoDto.getIdPrioridade() == null) { // Aqui determina
				// prazo pela
				// Urgencia e
				// Impacto -
				// Como mando o
				// ITIL.
				if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("B") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("B")) {
					solicitacaoServicoDto.setIdPrioridade(5);
				}
				if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("B") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("M")) {
					solicitacaoServicoDto.setIdPrioridade(4);
				}
				if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("B") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("A")) {
					solicitacaoServicoDto.setIdPrioridade(3);
				}
				if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("M") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("B")) {
					solicitacaoServicoDto.setIdPrioridade(4);
				}
				if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("M") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("M")) {
					solicitacaoServicoDto.setIdPrioridade(3);
				}
				if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("M") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("A")) {
					solicitacaoServicoDto.setIdPrioridade(2);
				}
				if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("A") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("B")) {
					solicitacaoServicoDto.setIdPrioridade(3);
				}
				if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("A") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("M")) {
					solicitacaoServicoDto.setIdPrioridade(2);
				}
				if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("A") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("A")) {
					solicitacaoServicoDto.setIdPrioridade(1);
				}
			}
		} else {
			MatrizPrioridadeDAO matrizPrioriDao = new MatrizPrioridadeDAO();
			if (tc != null)
				matrizPrioriDao.setTransactionControler(tc);

			String siglaImpacto = solicitacaoServicoDto.getImpacto();
			String siglaUrgencia = solicitacaoServicoDto.getUrgencia();
			Integer valorPrioridade = matrizPrioriDao.consultaValorPrioridade(siglaImpacto.trim().toUpperCase(), siglaUrgencia.trim().toUpperCase());
			solicitacaoServicoDto.setIdPrioridade(valorPrioridade);
		}

		if (solicitacaoServicoDto.getIdPrioridade() == null) {// Se ainda a
																// prioridade
																// estiver NULA,
																// vai a padrao
			solicitacaoServicoDto.setIdPrioridade(acordoNivelServicoDto.getIdPrioridadePadrao());
		}

		if (solicitacaoServicoDto.getIdPrioridade() == null)
			throw new Exception(i18nMessage("solicitacaoservico.validacao.prioridadesolicitacao"));

		int prazoCapturaHH = 0;
		int prazoCapturaMM = 0;
		int prazoHH = 0;
		int prazoMM = 0;
		TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();
		if (tc != null)
			tempoAcordoNivelServicoDao.setTransactionControler(tc);

		Collection<TempoAcordoNivelServicoDTO> colTempos = tempoAcordoNivelServicoDao.findByIdAcordoAndIdPrioridade(acordoNivelServicoDto.getIdAcordoNivelServico(),
				solicitacaoServicoDto.getIdPrioridade());
		if (colTempos != null) {
			FluxoServicoDao fluxoServicoDao = new FluxoServicoDao();
			if (tc != null)
				fluxoServicoDao.setTransactionControler(tc);

			String idFasePadraoStr = ParametroUtil.getValor(ParametroSistema.IDFaseExecucaoServicos, tc, null);
			if (idFasePadraoStr == null)
				throw new Exception(i18nMessage("solicitacaoservico.validacao.padraoexecucao"));

			FaseServicoDao faseServicoDao = new FaseServicoDao();
			if (tc != null)
				faseServicoDao.setTransactionControler(tc);
			Collection<FaseServicoDTO> colFases = faseServicoDao.list();
			HashMap<String, FaseServicoDTO> mapFasesCaptura = new HashMap();
			if (colFases != null) {
				for (FaseServicoDTO faseDto : colFases) {
					if (faseDto.getFaseCaptura() != null && faseDto.getFaseCaptura().equalsIgnoreCase("S"))
						mapFasesCaptura.put("" + faseDto.getIdFase(), faseDto);
				}
			}

			Collection colFluxos = fluxoServicoDao.findByIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato());
			boolean bExisteFluxo = colFluxos != null && !colFluxos.isEmpty();

			for (TempoAcordoNivelServicoDTO tempoAcordoDto : colTempos) {
				boolean bAcumula = false;
				if (bExisteFluxo) {
					colFluxos = fluxoServicoDao.findByIdServicoContratoAndIdFase(solicitacaoServicoDto.getIdServicoContrato(), tempoAcordoDto.getIdFase());
					bAcumula = colFluxos != null && !colFluxos.isEmpty();
				} else
					bAcumula = tempoAcordoDto.getIdFase().intValue() == new Integer(idFasePadraoStr).intValue();

				/*
				 * -- RETIRADO POR EMAURI EM 13/08/2012. NAO JUSTIFICA A EXCLUSAO DO CALCULO DO SLA. if (!bAcumula) continue;
				 */

				if (tempoAcordoDto.getTempoHH() != null) {
					if (mapFasesCaptura.get("" + tempoAcordoDto.getIdFase()) != null)
						prazoCapturaHH += tempoAcordoDto.getTempoHH().intValue();
					prazoHH += tempoAcordoDto.getTempoHH().intValue();
				}
				if (tempoAcordoDto.getTempoMM() != null) {
					if (mapFasesCaptura.get("" + tempoAcordoDto.getIdFase()) != null)
						prazoCapturaMM += tempoAcordoDto.getTempoMM().intValue();
					prazoMM += tempoAcordoDto.getTempoMM().intValue();
				}
			}
		}

		if ((prazoHH + prazoMM) == 0) {
			if (solicitacaoServicoDto.getIdPrioridade() != null && solicitacaoServicoDto.getIdPrioridade().intValue() == 5) {
				solicitacaoServicoDto.setPrazoCapturaHH(0);
				solicitacaoServicoDto.setPrazoCapturaMM(0);
				solicitacaoServicoDto.setPrazoHH(0);
				solicitacaoServicoDto.setPrazoMM(0);
				solicitacaoServicoDto.setSlaACombinar("S");
			} else {
				throw new Exception(i18nMessage("solicitacaoservico.prazoacordonivel") + " " + solicitacaoServicoDto.getIdPrioridade());
			}
		}

		while (prazoCapturaMM > 60) {
			prazoCapturaMM = prazoCapturaMM - 60;
			prazoCapturaHH += 1;
		}
		while (prazoMM > 60) {
			prazoMM = prazoMM - 60;
			prazoHH += 1;
		}

		solicitacaoServicoDto.setPrazoCapturaHH(prazoCapturaHH);
		solicitacaoServicoDto.setPrazoCapturaMM(prazoCapturaMM);
		solicitacaoServicoDto.setPrazoHH(prazoHH);
		solicitacaoServicoDto.setPrazoMM(prazoMM);

		//tratamento especial para solicitações a combinar reclassificadas
		SolicitacaoServicoDTO solAux = null;
		if(solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() != null){
			solAux = (SolicitacaoServicoDTO) this.getDao().restore(solicitacaoServicoDto);
			if(solAux.getDataHoraLimiteStr()!=null){
				if(solAux.getDataHoraLimiteStr().contains("--") && solAux.getSlaACombinar().equalsIgnoreCase("S")){
					determinaPrazoLimiteSolicitacaoACombinarReclassificada(solicitacaoServicoDto, null, tc);
					return;
				}
			}

		}

		if(solicitacaoServicoDto.getDataHoraInicio() != null && solicitacaoServicoDto.getDataHoraInicioSLA() == null){
			solicitacaoServicoDto.setDataHoraInicioSLA(UtilDatas.getDataHoraAtual());
		}

		determinaPrazoLimite(solicitacaoServicoDto, null, tc);
	}

	@Override
	public void encerra(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		try {
			tc.start();

			encerra(solicitacaoServicoDto, tc);

			tc.commit();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}finally {
			try {
				tc.close();
			} catch (PersistenceException e) {
				e.printStackTrace();
		}
	}
	}

	public void encerra(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		SolicitacaoServicoDTO solicitacaoAuxDto = restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), tc);
		new ExecucaoSolicitacaoServiceEjb().encerra(solicitacaoAuxDto, tc);
	}

	public void fechaSolicitacaoServicoVinculadaByProblemaOrMudanca(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		SolicitacaoServicoDTO solicitacaoAuxDto = restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), tc);
		new ExecucaoSolicitacaoServiceEjb().encerra(solicitacaoAuxDto, tc);
		this.getDao().setTransactionControler(tc);
		if (solicitacaoAuxDto != null && solicitacaoAuxDto.getIdSolicitacaoServico() != null) {
			solicitacaoAuxDto.setSituacao("Resolvida");
			this.getDao().updateNotNull(solicitacaoAuxDto);
		}
	}

	@Override
	public Collection findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, LogicException {
		try {
			return this.getDao().findByConhecimento(baseConhecimentoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Collection findByIdSolictacaoServico(Integer parm) throws ServiceException, LogicException {
		SolicitacaoServicoProblemaDao solicitacaoServicoProblemaDao = new SolicitacaoServicoProblemaDao();

		try {
			return solicitacaoServicoProblemaDao.findByIdSolictacaoServico(parm);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByServico(Integer idServico) throws Exception {
		return this.getDao().findByServico(idServico);
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByServico(Integer idServico, String nome) throws Exception {
		return this.getDao().findByServico(idServico, nome);
	}

	/**
	 * @author breno.guimaraes
	 * @return Resumo das solicitaï¿½ï¿½es relacionadas ao clinte passado como argumento.
	 */
	public ArrayList<SolicitacaoServicoDTO> findSolicitacoesServicosUsuario(Integer idUsuario, Integer idItemConfiguracao) {
		Collection<SolicitacaoServicoDTO> solicitacoesSimplificada = null;
		ArrayList<SolicitacaoServicoDTO> solicitacoesCompleta = new ArrayList<SolicitacaoServicoDTO>();

		List condicoes = new ArrayList();
		if (idUsuario != null) {
			condicoes.add(new Condition("idSolicitante", "=", idUsuario.intValue()));
		}

		if (idItemConfiguracao != null) {
			condicoes.add(new Condition("idItemConfiguracao", "=", idItemConfiguracao.intValue()));
		}

		try {
			solicitacoesSimplificada = ((SolicitacaoServicoDao) getDao()).findByCondition(condicoes, null);
			int i = 0;
			if (solicitacoesSimplificada != null) {
				for (SolicitacaoServicoDTO s : solicitacoesSimplificada) {
					solicitacoesCompleta.add(this.restoreAll(s.getIdSolicitacaoServico()));
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return solicitacoesCompleta;
	}

	public ArrayList<SolicitacaoServicoDTO> findSolicitacoesServicosUsuario(Integer idUsuario, String status, String campoBusca) throws Exception {
		ArrayList<SolicitacaoServicoDTO> solicitacoesRetorno = new ArrayList<SolicitacaoServicoDTO>();
		if (status == null || status.isEmpty()) {
			status = "EmAndamento";
		}
		ArrayList<SolicitacaoServicoDTO> solicitacoes = getSolicitacaoServicoDao().findSolicitacoesServicosUsuario(idUsuario, status, campoBusca);
		;
		if (solicitacoes != null) {
			for (SolicitacaoServicoDTO solicitacao : solicitacoes) {
				solicitacoesRetorno.add(this.restoreAll(solicitacao.getIdSolicitacaoServico()));
			}
		}
		return solicitacoesRetorno;
	}

	public boolean hasSolicitacoesServicosUsuario(Integer idUsuario, String status) throws Exception {
		return getSolicitacaoServicoDao().hasSolicitacoesServicosUsuario(idUsuario, status);
	}

	protected SolicitacaoServicoDao getDao() {
		if(solicitacaoServicoDao == null) {
			solicitacaoServicoDao = new SolicitacaoServicoDao();
		}
		return solicitacaoServicoDao;
	}

	public Collection<SolicitacaoServicoDTO> getHistoricoByIdSolicitacao(Integer idSolicitacao) throws Exception {
		try {
			return this.getSolicitacaoServicoDao().getHistoricoByIdSolicitacao(idSolicitacao);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ComplemInfSolicitacaoServicoService getInformacoesComplementaresService(ItemTrabalho itemTrabalho) throws Exception {
		TemplateSolicitacaoServicoDTO templateDto = new TemplateSolicitacaoServicoServiceEjb().recuperaTemplateServico(itemTrabalho);
		if (templateDto != null) {
			return getInformacoesComplementaresService(templateDto.getNomeClasseServico());
		} else {
			return null;
		}
	}

	public ComplemInfSolicitacaoServicoService getInformacoesComplementaresService(String nomeClasse) throws Exception {
		ComplemInfSolicitacaoServicoService informacoesComplementaresService = (ComplemInfSolicitacaoServicoService) Class.forName(nomeClasse).newInstance();
		return informacoesComplementaresService;
	}

	public ItemTrabalho getItemTrabalho(Integer idItemTrabalho) throws Exception {
		return new Tarefa().getItemTrabalho(idItemTrabalho);
	}

	public Integer getQuantidadeByIdServico(int idServico) throws Exception {
		try {
			return this.getDao().getQuantidadeByIdServico(idServico);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Integer getQuantidadeByIdServicoContrato(int idServicoContrato) throws Exception {
		try {
			return this.getDao().getQuantidadeByIdServicoContrato(idServicoContrato);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Retorna DAO de solicitacao servico.
	 *
	 * @return SolicitacaoServicoDao
	 * @throws ServiceException
	 * @author valdoilo
	 */
	public SolicitacaoServicoDao getSolicitacaoServicoDao() throws ServiceException {
		return (SolicitacaoServicoDao) this.getDao();
	}

	@Override
	public String getUrlInformacoesComplementares(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		String url = "";

		TemplateSolicitacaoServicoDTO templateDto = new TemplateSolicitacaoServicoServiceEjb().recuperaTemplateServico(solicitacaoServicoDto);
		if (templateDto != null) {
			url = Constantes.getValue("CONTEXTO_APLICACAO");
			// url = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.URL_Sistema, "");
			if (templateDto.isQuestionario()) {
				SolicitacaoServicoQuestionarioDTO solicitacaoServicoQuestionarioDto = null;
				if (solicitacaoServicoDto.getIdSolicitacaoServico() != null)
					solicitacaoServicoQuestionarioDto = new SolicitacaoServicoQuestionarioDao().findByIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());

				url += "/pages/visualizacaoQuestionario/visualizacaoQuestionario.load?tabela100=true";

				if (solicitacaoServicoQuestionarioDto != null && solicitacaoServicoQuestionarioDto.getIdSolicitacaoQuestionario() != null) {
					// if (solicitacaoServicoDto.getIdTarefa() == null) {
					url += "&idQuestionario=" + solicitacaoServicoQuestionarioDto.getIdQuestionario();
					url += "&idIdentificadorResposta=" + solicitacaoServicoQuestionarioDto.getIdSolicitacaoQuestionario();
					if (solicitacaoServicoQuestionarioDto.getSituacao().equalsIgnoreCase("F"))
						url += "&modo=somenteleitura";
					else
						url += "&modo=edicao";
					// }else{
					// url += "&idQuestionarioOrigem=" + templateDto.getIdQuestionario();
					// url += "&modo=edicao";
					// }
				} else {
					url += "&idQuestionarioOrigem=" + templateDto.getIdQuestionario();
					url += "&modo=edicao";
				}
				url += "&";
			} else {
				url += templateDto.getUrlRecuperacao();
				url += "?";
			}
			String editar = "S";
			if (solicitacaoServicoDto.getIdSolicitacaoServico() != null && solicitacaoServicoDto.getIdSolicitacaoServico().intValue() > 0) {
				url += "idSolicitacaoServico=" + solicitacaoServicoDto.getIdSolicitacaoServico() + "&";
				if (solicitacaoServicoDto.getIdTarefa() == null)
					editar = "N";
				else
					url += "idTarefa=" + solicitacaoServicoDto.getIdTarefa() + "&";
			}
			url += "idServico=2007&idContrato=" + solicitacaoServicoDto.getIdContrato();
			url += "&editar=" + editar;
		}
		return url;
	}

	public void gravaInformacoesGED(Collection colArquivosUpload, int idEmpresa, SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
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
			controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_SOLICITACAOSERVICO);
			controleGEDDTO.setId(solicitacaoServicoDto.getIdSolicitacaoServico());
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
		Collection<ControleGEDDTO> colAnexo = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_SOLICITACAOSERVICO, solicitacaoServicoDto.getIdSolicitacaoServico());
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

	@Override
	public Collection<SolicitacaoServicoDTO> listAll() throws Exception {
		return null;
	}

	@Override
	public Collection<SolicitacaoServicoDTO> listAllIncidentes(Integer idEmpregado) throws Exception {
		return this.getDao().listAllIncidentes(idEmpregado);
	}

	@Override
	public Collection<SolicitacaoServicoDTO> listAllServicos() throws Exception {
		return this.getDao().listAllServicos();
	}

	@Override
	public Collection<SolicitacaoServicoDTO> listAllServicosLikeNomeServico(String nome) throws Exception {
		return this.getDao().listAllServicosLikeNomeServico(nome);
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorFase(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		return this.getDao().listaQuantidadeSolicitacaoPorFase(solicitacaoDto);
	}

	@Override
	public Collection<SolicitacaoServicoDTO> listarSLA() throws Exception {
		return this.getDao().listarSLA();
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorGrupo(HttpServletRequest request, SolicitacaoServicoDTO solicitacaoDto) throws Exception {

		Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorGrupo = this.getDao().listaQuantidadeSolicitacaoPorGrupo(solicitacaoDto);

		List<RelatorioQuantitativoSolicitacaoDTO> lista = new ArrayList<RelatorioQuantitativoSolicitacaoDTO>();
		lista.addAll(listaQuantidadeSolicitacaoPorGrupo);

		if (!lista.isEmpty()) {
			String grupoRegistroAnterior = lista.get(0).getGrupo();
			int totalGrupo = 0;
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getGrupo() != null) {
					if (lista.get(i).getGrupo().equals(grupoRegistroAnterior)) {
						totalGrupo += lista.get(i).getQuantidadeServico();
					} else {
						RelatorioQuantitativoSolicitacaoDTO relatorioQuantitativoSolicitacaoDTO = new RelatorioQuantitativoSolicitacaoDTO();
						relatorioQuantitativoSolicitacaoDTO.setServico(UtilI18N.internacionaliza(request, "citcorpore.comum.totalGrupo"));
						relatorioQuantitativoSolicitacaoDTO.setQuantidadeServico(totalGrupo);
						relatorioQuantitativoSolicitacaoDTO.setGrupo(grupoRegistroAnterior);
						lista.add(i++, relatorioQuantitativoSolicitacaoDTO);

						totalGrupo = lista.get(i).getQuantidadeServico();
						grupoRegistroAnterior = lista.get(i).getGrupo();
					}
				}
			}
			RelatorioQuantitativoSolicitacaoDTO relatorioQuantitativoSolicitacaoDTO = new RelatorioQuantitativoSolicitacaoDTO();
			relatorioQuantitativoSolicitacaoDTO.setServico(UtilI18N.internacionaliza(request, "citcorpore.comum.totalGrupo"));
			relatorioQuantitativoSolicitacaoDTO.setQuantidadeServico(totalGrupo);
			relatorioQuantitativoSolicitacaoDTO.setGrupo(grupoRegistroAnterior);

			lista.add(relatorioQuantitativoSolicitacaoDTO);
		}

		return lista;
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorHoraAbertura(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		return this.getDao().listaQuantidadeSolicitacaoPorHoraAbertura(solicitacaoDto);
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorItemConfiguracao(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		return this.getDao().listaQuantidadeSolicitacaoPorItemConfiguracao(solicitacaoDto);
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorOrigem(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		return this.getDao().listaQuantidadeSolicitacaoPorOrigem(solicitacaoDto);
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorPesquisaSatisfacao(HttpServletRequest request, SolicitacaoServicoDTO solicitacaoDto) throws Exception {

		Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorPesquisaSatisfacao = this.getDao().listaQuantidadeSolicitacaoPorPesquisaSatisfacao(solicitacaoDto);

		List<RelatorioQuantitativoSolicitacaoDTO> lista = new ArrayList<RelatorioQuantitativoSolicitacaoDTO>();
		lista.addAll(listaQuantidadeSolicitacaoPorPesquisaSatisfacao);

		if (!lista.isEmpty()) {
			String grupoRegistroAnterior = lista.get(0).getGrupoPesquisaSatisfacao();
			int totalGrupo = 0;
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getGrupoPesquisaSatisfacao() != null && lista.get(i).getGrupoPesquisaSatisfacao().equals(grupoRegistroAnterior)) {
					totalGrupo += lista.get(i).getQuantidadePesquisaSatisfacao();
				} else {
					RelatorioQuantitativoSolicitacaoDTO relatorioQuantitativoSolicitacaoDTO = new RelatorioQuantitativoSolicitacaoDTO();

					relatorioQuantitativoSolicitacaoDTO.setServicoPesquisaSatisfacao(UtilI18N.internacionaliza(request, "citcorpore.comum.totalGrupo"));

					relatorioQuantitativoSolicitacaoDTO.setQuantidadePesquisaSatisfacao(totalGrupo);
					relatorioQuantitativoSolicitacaoDTO.setGrupoPesquisaSatisfacao(grupoRegistroAnterior);
					lista.add(i++, relatorioQuantitativoSolicitacaoDTO);

					totalGrupo = lista.get(i).getQuantidadePesquisaSatisfacao();
					grupoRegistroAnterior = lista.get(i).getGrupoPesquisaSatisfacao();
				}
			}
			RelatorioQuantitativoSolicitacaoDTO relatorioQuantitativoSolicitacaoDTO = new RelatorioQuantitativoSolicitacaoDTO();
			relatorioQuantitativoSolicitacaoDTO.setServicoPesquisaSatisfacao(UtilI18N.internacionaliza(request, "citcorpore.comum.totalGrupo"));
			relatorioQuantitativoSolicitacaoDTO.setQuantidadePesquisaSatisfacao(totalGrupo);
			relatorioQuantitativoSolicitacaoDTO.setGrupoPesquisaSatisfacao(grupoRegistroAnterior);

			lista.add(relatorioQuantitativoSolicitacaoDTO);
		}

		return lista;
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorPrioridade(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		return this.getDao().listaQuantidadeSolicitacaoPorPrioridade(solicitacaoDto);
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorResponsavel(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		return this.getDao().listaQuantidadeSolicitacaoPorResponsavel(solicitacaoDto);
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorServico(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		return this.getDao().listaQuantidadeSolicitacaoPorServico(solicitacaoServicoDto);
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaServicosAbertosAprovados(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		return this.getDao().listaServicosAbertosAprovados(solicitacaoServicoDto);
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSituacao(SolicitacaoServicoDTO solicitacaoDto) throws Exception {

		Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSituacao = null;
		try {
			listaQuantidadeSolicitacaoPorSituacao = ((SolicitacaoServicoDao) getDao()).listaQuantidadeSolicitacaoPorSituacao(solicitacaoDto);
			if (listaQuantidadeSolicitacaoPorSituacao != null) {
				for (RelatorioQuantitativoSolicitacaoDTO relatorioQuantitativoSolicitacaoDTO : listaQuantidadeSolicitacaoPorSituacao) {
					if (StringUtils.contains(StringUtils.upperCase(relatorioQuantitativoSolicitacaoDTO.getSituacao()), StringUtils.upperCase("EmAndamento"))) {
						relatorioQuantitativoSolicitacaoDTO.setSituacao("Em Andamento");
					}
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaQuantidadeSolicitacaoPorSituacao;
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSituacaoSLA(HttpServletRequest request, SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		List<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSituacaoSLA = this.getDao().listaQuantidadeSolicitacaoPorSituacaoSLA(solicitacaoDto);

		// A LISTA DEVE TER APENAS DOIS REGISTROS: QUANTIDADE DE ATRAZOS DE SLA (PRIMEIRO) E QUANTIDADE DE SLA DENTRO DO PRAZO (SEGUNDO)
		if (listaQuantidadeSolicitacaoPorSituacaoSLA != null && listaQuantidadeSolicitacaoPorSituacaoSLA.size() == 2) {
			listaQuantidadeSolicitacaoPorSituacaoSLA.get(0).setSituacaoSLA(UtilI18N.internacionaliza(request, "citcorpore.comum.comAtraso"));
			listaQuantidadeSolicitacaoPorSituacaoSLA.get(1).setSituacaoSLA(UtilI18N.internacionaliza(request, "citcorpore.comum.semAtraso"));
		}

		return listaQuantidadeSolicitacaoPorSituacaoSLA;
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSolicitante(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		return this.getDao().listaQuantidadeSolicitacaoPorSolicitante(solicitacaoDto);
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorTipo(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		return this.getDao().listaQuantidadeSolicitacaoPorTipo(solicitacaoDto);
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorTipoServico(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		return this.getDao().listaQuantidadeSolicitacaoPorTipoServico(solicitacaoDto);
	}

	@Override
	public String listaServico(Integer idSolicitacaoservico) throws Exception {
		return this.getDao().listaServico(idSolicitacaoservico);
	}

	@Override
	public Collection<SolicitacaoServicoDTO> listaSolicitacaoPorBaseConhecimento(SolicitacaoServicoDTO solicitacao) throws Exception {

		Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorBaseConhecimento = null;
		try {
			listaSolicitacaoServicoPorBaseConhecimento = ((SolicitacaoServicoDao) getDao()).listaSolicitacaoPorBaseConhecimento(solicitacao);
			for (SolicitacaoServicoDTO solicitacaoServicoDto : listaSolicitacaoServicoPorBaseConhecimento) {
				Source source = new Source(solicitacaoServicoDto.getDescricao());
				solicitacaoServicoDto.setDescricao(source.getTextExtractor().toString());

				if (StringUtils.contains(StringUtils.upperCase(solicitacaoServicoDto.getSituacao()), StringUtils.upperCase("EmAndamento"))) {
					solicitacaoServicoDto.setSituacao("Em Andamento");
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaSolicitacaoServicoPorBaseConhecimento;

	}

	@Override
	public Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios(PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto) throws Exception {
		Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios = null;

		try {

			listaSolicitacaoServicoPorCriterios = ((SolicitacaoServicoDao) getDao()).listaSolicitacaoServicoPorCriterios(pesquisaSolicitacaoServicoDto);
			// Retirando devido ao stress que o mesmo gerava
			/*
			 * Timestamp t = UtilDatas.getDataHoraAtual(); for (SolicitacaoServicoDTO solicitacaoServicoDto : listaSolicitacaoServicoPorCriterios) { Source source = new
			 * Source(solicitacaoServicoDto.getDescricao()); solicitacaoServicoDto.setDescricao(source.getTextExtractor().toString());
			 *
			 * if (solicitacaoServicoDto.getSituacao().equalsIgnoreCase("Fechada")) { SolicitacaoServicoDTO solicitacao = new SolicitacaoServicoDTO();
			 * solicitacao.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico()); solicitacao = (SolicitacaoServicoDTO) this.restore(solicitacao);
			 * solicitacaoServicoDto.setTempoAtendimentoHH(solicitacao.getTempoAtendimentoHH()); solicitacaoServicoDto.setTempoAtendimentoMM(solicitacao.getTempoAtendimentoMM()); }
			 *
			 * if (StringUtils.contains(StringUtils.upperCase(solicitacaoServicoDto.getSituacao()), StringUtils.upperCase("EmAndamento"))) { solicitacaoServicoDto.setSituacao("Em Andamento"); }
			 *
			 * } Timestamp y = UtilDatas.getDataHoraAtual(); System.out.println("Tempo Execução For: " + UtilDatas.calculaDiferencaTempoEmMilisegundos(y, t) );
			 */
			// Retirando devido ao stress que o mesmo gerava
			// for (SolicitacaoServicoDTO solicitacaoServicoDto : listaSolicitacaoServicoPorCriterios) {
			// Source source = new Source(solicitacaoServicoDto.getDescricao());
			// solicitacaoServicoDto.setDescricao(source.getTextExtractor().toString());
			/*
			 * if (solicitacaoServicoDto.getSituacao().equalsIgnoreCase("Fechada")) { SolicitacaoServicoDTO solicitacao = new SolicitacaoServicoDTO();
			 * solicitacao.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico()); solicitacao = (SolicitacaoServicoDTO) this.restore(solicitacao);
			 * solicitacaoServicoDto.setTempoAtendimentoHH(solicitacao.getTempoAtendimentoHH()); solicitacaoServicoDto.setTempoAtendimentoMM(solicitacao.getTempoAtendimentoMM()); }
			 *
			 * if (StringUtils.contains(StringUtils.upperCase(solicitacaoServicoDto.getSituacao()), StringUtils.upperCase("EmAndamento"))) { solicitacaoServicoDto.setSituacao("Em Andamento"); }
			 */

			// }

		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaSolicitacaoServicoPorCriterios;
	}

	@Override
	public Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorServicoContrato(Integer idServicoContratoContabil) throws Exception {
		try {
			return this.getDao().listaSolicitacaoServicoPorServicoContrato(idServicoContratoContabil);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection listaSolicitacoesSemPesquisaSatisfacao() throws Exception {
		return this.getDao().listaSolicitacoesSemPesquisaSatisfacao();
	}

	public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefas, TransactionControler tc) throws Exception {
		if (tc != null)
			this.getDao().setTransactionControler(tc);

		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoDto = new ArrayList();

		listSolicitacaoServicoDto = this.getDao().listByTarefas(listTarefas);

		if (listSolicitacaoServicoDto != null && !listSolicitacaoServicoDto.isEmpty()) {

			for (SolicitacaoServicoDTO solicitacaoDto : listSolicitacaoServicoDto) {

				if (solicitacaoDto != null) {
					solicitacaoDto.setDataHoraLimiteStr(solicitacaoDto.getDataHoraLimiteStr());
					solicitacaoDto.setDataHoraInicioSLA(solicitacaoDto.getDataHoraInicioSLA());

					solicitacaoDto.setNomeServico(solicitacaoDto.getServico());
					if (solicitacaoDto.getNomeUnidadeSolicitante() != null && !solicitacaoDto.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
						solicitacaoDto.setSolicitanteUnidade(solicitacaoDto.getSolicitante() + " (" + solicitacaoDto.getNomeUnidadeSolicitante() + ")");

					if (solicitacaoDto.getNomeUnidadeResponsavel() != null && !solicitacaoDto.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
						solicitacaoDto.setResponsavel(solicitacaoDto.getResponsavel() + " (" + solicitacaoDto.getNomeUnidadeResponsavel() + ")");

					solicitacaoDto = this.verificaSituacaoSLA(solicitacaoDto, tc);
				}
			}
		}
		return listSolicitacaoServicoDto;
	}

	public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefas, TipoSolicitacaoServico[] tiposSolicitacao, TransactionControler tc) throws Exception {
		if (tc != null)
			this.getDao().setTransactionControler(tc);

		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoDto = new ArrayList();

		listSolicitacaoServicoDto = this.getDao().listByTarefas(listTarefas, tiposSolicitacao);

		if (listSolicitacaoServicoDto != null && !listSolicitacaoServicoDto.isEmpty()) {

			for (SolicitacaoServicoDTO solicitacaoDto : listSolicitacaoServicoDto) {

				if (solicitacaoDto != null) {
					solicitacaoDto.setDataHoraLimiteStr(solicitacaoDto.getDataHoraLimiteStr());
					solicitacaoDto.setDataHoraInicioSLA(solicitacaoDto.getDataHoraInicioSLA());

					solicitacaoDto.setNomeServico(solicitacaoDto.getServico());
					if (solicitacaoDto.getNomeUnidadeSolicitante() != null && !solicitacaoDto.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
						solicitacaoDto.setSolicitanteUnidade(solicitacaoDto.getSolicitante() + " (" + solicitacaoDto.getNomeUnidadeSolicitante() + ")");

					if (solicitacaoDto.getNomeUnidadeResponsavel() != null && !solicitacaoDto.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
						solicitacaoDto.setResponsavel(solicitacaoDto.getResponsavel() + " (" + solicitacaoDto.getNomeUnidadeResponsavel() + ")");

					solicitacaoDto = this.verificaSituacaoSLA(solicitacaoDto, tc);
				}
			}
		}
		return listSolicitacaoServicoDto;
	}

	@Override
	public SolicitacaoServicoDTO listIdentificacao(Integer idItemConfiguracao) throws Exception {

		try {
			return this.getDao().listIdentificacao(idItemConfiguracao);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection<SolicitacaoServicoDTO> listIncidentesNaoFinalizados() throws Exception {
		try {
			return this.getSolicitacaoServicoDao().listIncidentesNaoFinalizados();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public SolicitacaoServicoDTO listInformacaoContato(String nomeContato) throws Exception {
		try {
			return this.getDao().retornaSolicitacaoServicoComInformacoesDoContato(nomeContato);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Retorna Solicitaï¿½ï¿½es de Serviços de acordo com o Tipo de Demanda e Usuï¿½rio.
	 *
	 * @param tipoDemandaServico
	 * @param grupoSeguranca
	 * @param usuario
	 * @return
	 * @throws Exception
	 */
	@Override
	public Collection<SolicitacaoServicoDTO> listSolicitacaoServico(String tipoDemandaServico, GrupoDTO grupoSeguranca, UsuarioDTO usuario, Date dataInicio, Date dataFim, String situacao)
			throws Exception {
		try {
			return this.getSolicitacaoServicoDao().listSolicitacaoServico(tipoDemandaServico, grupoSeguranca, usuario, dataInicio, dataFim, situacao);
		} catch (Exception e) {
			throw e;
		}
	}

	public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoByCriterios(Collection colCriterios) throws Exception {
		Collection<SolicitacaoServicoDTO> listaSolicitacaoServico = null;

		try {

			listaSolicitacaoServico = ((SolicitacaoServicoDao) getDao()).listSolicitacaoServicoByCriterios(colCriterios);

			/*
			 * for (SolicitacaoServicoDTO solicitacaoServicoDto : listaSolicitacaoServico) { Source source = new Source(solicitacaoServicoDto.getDescricao()); solicitacaoServicoDto
			 * .setDescricao(source.getTextExtractor().toString());
			 *
			 * if (solicitacaoServicoDto.getSituacao().equalsIgnoreCase("Fechada")) { SolicitacaoServicoDTO solicitacao = new SolicitacaoServicoDTO();
			 * solicitacao.setIdSolicitacaoServico(solicitacaoServicoDto .getIdSolicitacaoServico()); solicitacao = (SolicitacaoServicoDTO) this.restore(solicitacao); solicitacaoServicoDto
			 * .setTempoAtendimentoHH(solicitacao.getTempoAtendimentoHH()); solicitacaoServicoDto .setTempoAtendimentoMM(solicitacao.getTempoAtendimentoMM()); }
			 *
			 * if (StringUtils.contains(StringUtils.upperCase(solicitacaoServicoDto .getSituacao()), StringUtils.upperCase("EmAndamento"))) { solicitacaoServicoDto.setSituacao("Em Andamento"); }
			 *
			 * }
			 */

		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaSolicitacaoServico;
	}

	@Override
	public List<SolicitacaoServicoDTO> listSolicitacaoServicoByItemConfiguracao(Integer idItemConfiguracao) throws Exception {
		return this.getDao().listSolicitacaoServicoByItemConfiguracao(idItemConfiguracao);
	}

	public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoEmAndamento(Integer idSolicitacaoServico) {
		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoEmAndamento = new ArrayList<SolicitacaoServicoDTO>();

		try {

			listSolicitacaoServicoEmAndamento = this.getDao().listSolicitacaoServicoEmAndamento(idSolicitacaoServico);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return listSolicitacaoServicoEmAndamento;
	}

	public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoNaoFinalizadas() throws Exception {
		try {
			return this.getSolicitacaoServicoDao().listSolicitacaoServicoNaoFinalizadas();
		} catch (Exception e) {
			throw e;
		}
	}

	public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionada(int idSolicitacaoPai) {

		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionada = new ArrayList<SolicitacaoServicoDTO>();

		try {

			listSolicitacaoServicoRelacionada = this.getDao().listSolicitacaoServicoRelacionada(idSolicitacaoPai);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return listSolicitacaoServicoRelacionada;
	}

	public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionadaPai(int idSolicitacaoPai) {

		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionada = new ArrayList<SolicitacaoServicoDTO>();

		try {

			listSolicitacaoServicoRelacionada = this.getDao().listSolicitacaoServicoRelacionadaPai(idSolicitacaoPai);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return listSolicitacaoServicoRelacionada;
	}

	public Collection<SolicitacaoServicoDTO> listSolicitacoesFilhas(TransactionControler tc) throws Exception {
		if (tc != null)
			this.getDao().setTransactionControler(tc);
		return this.getDao().listSolicitacoesFilhas();
	}

	public SolicitacaoServicoDTO obterQuantidadeSolicitacoesServico(Integer idServicoContrato, java.util.Date dataInicio, java.util.Date dataFim) throws Exception {
		try {
			return this.getDao().obterQuantidadeSolicitacoesServico(idServicoContrato, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> quantidadeSolicitacaoPorBaseConhecimento(SolicitacaoServicoDTO solicitacao) throws Exception {
		return this.getDao().quantidadeSolicitacaoPorBaseConhecimento(solicitacao);
	}

	@Override
	public void reabre(UsuarioDTO usuarioDto, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		try {
			tc.start();

			reabre(usuarioDto, solicitacaoServicoDto, tc);

			tc.commit();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}finally {
			try {
				tc.close();
			} catch (PersistenceException e) {
				e.printStackTrace();
		}
	}
	}

	public void reabre(UsuarioDTO usuarioDto, SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		SolicitacaoServicoDTO solicitacaoAuxDto = restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), tc);
		new ExecucaoSolicitacaoServiceEjb().reabre(usuarioDto, solicitacaoAuxDto, tc);
	}

	@Override
	public void reativa(UsuarioDTO usuarioDto, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		try {
			tc.start();

			reativa(usuarioDto, solicitacaoServicoDto, tc);

			tc.commit();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}finally {
			try {
				tc.close();
			} catch (PersistenceException e) {
				e.printStackTrace();
		}
	}
	}

	public void reativa(UsuarioDTO usuarioDto, SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		if(solicitacaoServicoDto.getSituacaoSLA()!=null && (solicitacaoServicoDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.M.name()) || solicitacaoServicoDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.A.name()))){
			new ExecucaoSolicitacaoServiceEjb().reativa(usuarioDto, solicitacaoServicoDto, tc);
		}
		else{
			SolicitacaoServicoDTO solicitacaoAuxDto = restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), tc);
			new ExecucaoSolicitacaoServiceEjb().reativa(usuarioDto, solicitacaoAuxDto, tc);
		}

	}

	@Override
	public FluxoDTO recuperaFluxo(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		return recuperaFluxo(solicitacaoServicoDto, null);
	}

	public FluxoDTO recuperaFluxo(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		if (solicitacaoServicoDto == null || solicitacaoServicoDto.getIdSolicitacaoServico() == null)
			throw new Exception(i18nMessage("solicitacaoservico.validacao.solicitacaoservico"));

		FluxoDTO fluxoDto = null;
		ExecucaoSolicitacaoDao execucaoSolicitacaoDao = new ExecucaoSolicitacaoDao();
		if (tc != null)
			execucaoSolicitacaoDao.setTransactionControler(tc);
		Collection<ExecucaoSolicitacaoDTO> colExecucao = execucaoSolicitacaoDao.listByIdSolicitacao(solicitacaoServicoDto.getIdSolicitacaoServico());
		if (colExecucao != null && !colExecucao.isEmpty()) {
			fluxoDto = new FluxoDTO();
			ExecucaoSolicitacaoDTO execucaoDto = (ExecucaoSolicitacaoDTO) ((List) colExecucao).get(0);
			fluxoDto.setIdFluxo(execucaoDto.getIdFluxo());
			fluxoDto = (FluxoDTO) new FluxoDao().restore(fluxoDto);
		}

		return fluxoDto;
	}

	public Collection<SolicitacaoServicoDTO> relatorioControleSla(SolicitacaoServicoDTO solicitacao) throws Exception {
		return this.getDao().relatorioControleSla(solicitacao);
	}

	/**
	 * @param solicitacaoServicoDTO
	 * @return Coleção de Solicitação de serviço com datas horas de sla da solicitacao serviço.
	 * @throws Exception
	 */
	public SolicitacaoServicoDTO relatorioControlePercentualQuantitativoSla(SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoBean = solicitacaoServicoDTO;

		List<SolicitacaoServicoDTO> listSolicitacaoservico = (List<SolicitacaoServicoDTO>) this.getDao().relatorioControleSla(solicitacaoServicoDTO);

		/*for (SolicitacaoServicoDTO solicitacaoServicoDTO2 : listSolicitacaoservico) {
			if(solicitacaoServicoDTO2.getPrazoHH()!= null && solicitacaoServicoDTO2.getPrazoMM()!=null && solicitacaoServicoDTO2.getPrazoHH()==0 && solicitacaoServicoDTO2.getPrazoMM()==0){
				solicitacaoServicoDTO2.setAtrasoSLA(0);
				solicitacaoServicoDTO2.setDataHoraLimiteStr("");
				solicitacaoServicoDTO2.setDataHoraLimite(null);
			}
		}
		*/
		verificaSituacaoSLA(listSolicitacaoservico);

		solicitacaoServicoBean.setMapPorcentagemSla(this.calculaProcentagemSLAComAtraso(listSolicitacaoservico));

		return solicitacaoServicoBean;
	}

	public Map<String, Object> calculaProcentagemSLAComAtraso(List<SolicitacaoServicoDTO> listSolicitacaoservico) {

		int[] totalPrioridade = { 0, 0, 0, 0, 0 };
		int[] totalPrioridadeDentroPrazoSla = { 0, 0, 0, 0, 0 };
		int[] totalPrioridadeForaPrazoSla = { 0, 0, 0, 0, 0 };

		double totalPrioridadeDentroPrazo = 0;
		double totalPrioridadeForaDoPrazo = 0;
		double totalPrioridadeGeral = 0;

		double[] percentualPrioridadeDentroSla = { 0, 0, 0, 0, 0 };
		double[] percentualPrioridadeForaSla = { 0, 0, 0, 0, 0 };
		double percentualTotalPrioridadeDentroSla = 0;
		double percentualTotalPrioridadeForaSla = 0;

		Map<String, Object> parametros = new HashMap<String, Object>();

		if (listSolicitacaoservico != null && listSolicitacaoservico.size() > 0) {
			for (SolicitacaoServicoDTO solicitacaoServicoDTO : listSolicitacaoservico) {

				if (solicitacaoServicoDTO.getIdPrioridade() != null) {
					switch (solicitacaoServicoDTO.getIdPrioridade().intValue()) {
					case 1:
						totalPrioridade[0]++;
						if ((solicitacaoServicoDTO.getAtrasoSLAStr() != null && solicitacaoServicoDTO.getAtrasoSLAStr().equalsIgnoreCase("S")) || solicitacaoServicoDTO.getAtrasoSLA() > 0) {
							totalPrioridadeForaPrazoSla[0]++;
						} else {
							totalPrioridadeDentroPrazoSla[0]++;
						}
						break;
					case 2:
						totalPrioridade[1]++;
						if ((solicitacaoServicoDTO.getAtrasoSLAStr() != null && solicitacaoServicoDTO.getAtrasoSLAStr().equalsIgnoreCase("S")) || solicitacaoServicoDTO.getAtrasoSLA() > 0) {
							totalPrioridadeForaPrazoSla[1]++;
						} else {
							totalPrioridadeDentroPrazoSla[1]++;
						}
						break;
					case 3:
						totalPrioridade[2]++;
						if ((solicitacaoServicoDTO.getAtrasoSLAStr() != null && solicitacaoServicoDTO.getAtrasoSLAStr().equalsIgnoreCase("S")) || solicitacaoServicoDTO.getAtrasoSLA() > 0) {
							totalPrioridadeForaPrazoSla[2]++;
						} else {
							totalPrioridadeDentroPrazoSla[2]++;
						}
						break;
					case 4:
						totalPrioridade[3]++;
						if ((solicitacaoServicoDTO.getAtrasoSLAStr() != null && solicitacaoServicoDTO.getAtrasoSLAStr().equalsIgnoreCase("S")) || solicitacaoServicoDTO.getAtrasoSLA() > 0) {
							totalPrioridadeForaPrazoSla[3]++;
						} else {
							totalPrioridadeDentroPrazoSla[3]++;
						}
						break;
					case 5:
						totalPrioridade[4]++;
						if ((solicitacaoServicoDTO.getAtrasoSLAStr() != null && solicitacaoServicoDTO.getAtrasoSLAStr().equalsIgnoreCase("S")) || solicitacaoServicoDTO.getAtrasoSLA() > 0) {
							totalPrioridadeForaPrazoSla[4]++;
						} else {
							totalPrioridadeDentroPrazoSla[4]++;
						}

						break;
					default:
						break;
					}
				}
			}

			// Calcula Porcentagem dentro e fora do Prazo individual
			for (int i = 0; totalPrioridade.length > i; i++) {
				double prioridadeDentroPrazoSla = 0;
				double totalPrioridadeSla = 0;
				double prioridadeForaPrazoSla = 0;
				int prioridade = 1;
				prioridade += i;
				if (totalPrioridade[i] > 0) {
					// Verifica se existe sla dentro do pazo e calcula porcentagem
					if (totalPrioridadeDentroPrazoSla[i] > 0) {
						prioridadeDentroPrazoSla = totalPrioridadeDentroPrazoSla[i];
						totalPrioridadeSla = totalPrioridade[i];
						parametros.put("percentDentroSlaPrio" + prioridade, UtilFormatacao.formatDouble(((prioridadeDentroPrazoSla / totalPrioridadeSla) * 100), 2));
						parametros.put("quantDentroSlaPrio" + prioridade, UtilFormatacao.formatDouble(prioridadeDentroPrazoSla, 0));
						parametros.put("quantDentroSlaPrio" + prioridade, UtilFormatacao.formatDouble(prioridadeDentroPrazoSla, 0));
						totalPrioridadeDentroPrazo += prioridadeDentroPrazoSla;
					} else {
						parametros.put("percentDentroSlaPrio" + prioridade, "" + 0);
						parametros.put("quantDentroSlaPrio" + prioridade, UtilFormatacao.formatDouble(prioridadeDentroPrazoSla, 0));
					}
					// Verifica se existe sla fora do pazo e calcula porcentagem
					if (totalPrioridadeForaPrazoSla[i] > 0) {
						prioridadeForaPrazoSla = totalPrioridadeForaPrazoSla[i];
						totalPrioridadeSla = totalPrioridade[i];

						parametros.put("percentForaSlaPrio" + prioridade, UtilFormatacao.formatDouble(((prioridadeForaPrazoSla / totalPrioridadeSla) * 100), 2));
						parametros.put("quantForaSlaPrio" + prioridade, UtilFormatacao.formatDouble(prioridadeForaPrazoSla, 0));

						totalPrioridadeForaDoPrazo += prioridadeForaPrazoSla;
					} else {
						parametros.put("percentForaSlaPrio" + prioridade, "" + 0);
						parametros.put("quantForaSlaPrio" + prioridade, "" + 0);
					}
					parametros.put("totalPercentPri" + prioridade, UtilFormatacao.formatDouble(100.00, 0));
				} else {
					parametros.put("percentDentroSlaPrio" + prioridade, "" + 0);
					parametros.put("percentForaSlaPrio" + prioridade, "" + 0);
					parametros.put("quantDentroSlaPrio" + prioridade, "" + 0);
					parametros.put("quantForaSlaPrio" + prioridade, "" + 0);
					parametros.put("totalPercentPri" + prioridade, "" + 0);
				}

				parametros.put("totalQuantPri" + prioridade, UtilFormatacao.formatDouble((prioridadeForaPrazoSla + prioridadeDentroPrazoSla), 0));
			}
			// Calcula porcentagem dentro e fora do prazo total
			totalPrioridadeGeral = listSolicitacaoservico.size();
			percentualTotalPrioridadeDentroSla = (totalPrioridadeDentroPrazo / totalPrioridadeGeral) * 100;
			percentualTotalPrioridadeForaSla = (totalPrioridadeForaDoPrazo / totalPrioridadeGeral) * 100;

			// Carrega objeto de valores.
			parametros.put("totalPrioridadeDentroPrazo", "" + UtilFormatacao.formatDouble(totalPrioridadeDentroPrazo, 0));
			parametros.put("totalPrioridadeForaDoPrazo", "" + UtilFormatacao.formatDouble(totalPrioridadeForaDoPrazo, 0));
			parametros.put("totalPrioridadeGeral", "" + UtilFormatacao.formatDouble(totalPrioridadeGeral, 0));
			parametros.put("percentualTotalPrioridadeDentroSla", UtilFormatacao.formatDouble(percentualTotalPrioridadeDentroSla, 2));
			parametros.put("percentualTotalPrioridadeForaSla", UtilFormatacao.formatDouble(percentualTotalPrioridadeForaSla, 2));
			parametros.put("percentualTotalPrioridadeSla", UtilFormatacao.formatDouble((percentualTotalPrioridadeForaSla + percentualTotalPrioridadeDentroSla), 2));

		}
		return parametros;
	}

	public Collection<SolicitacaoServicoDTO> listarSLA(SolicitacaoServicoDTO solicitacao) throws Exception {
		return this.getDao().relatorioControleSla(solicitacao);
	}

	public SolicitacaoServicoDTO restoreAll(Integer idSolicitacaoServico) throws Exception {
		return restoreAll(idSolicitacaoServico, null);
	}

	public SolicitacaoServicoDTO restoreAll(Integer idSolicitacaoServico, TransactionControler tc) throws Exception {
		if (tc != null) {
			this.getDao().setTransactionControler(tc);
		}
		SolicitacaoServicoDTO solicitacaoDto = null;
		try {
			solicitacaoDto = (SolicitacaoServicoDTO) this.getDao().restoreAll(idSolicitacaoServico);
		} catch (Exception e) {
			throw new Exception(i18nMessage("solicitacaoservico.erro.recuperardadosolicitacao") + " " + idSolicitacaoServico);
		}

		if (solicitacaoDto != null) {
			// Parece estranho, mas isto executa o metodo interno do DTO. Isto
			// eh necessario!!!
			solicitacaoDto.setDataHoraLimiteStr(solicitacaoDto.getDataHoraLimiteStr());

			solicitacaoDto.setNomeServico(solicitacaoDto.getServico());
			if (solicitacaoDto.getNomeUnidadeSolicitante() != null && !solicitacaoDto.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
				solicitacaoDto.setSolicitanteUnidade(solicitacaoDto.getSolicitante() + " (" + solicitacaoDto.getNomeUnidadeSolicitante() + ")");
			else
				solicitacaoDto.setSolicitanteUnidade(solicitacaoDto.getSolicitante());
			if (solicitacaoDto.getNomeUnidadeResponsavel() != null && !solicitacaoDto.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
				solicitacaoDto.setResponsavel(solicitacaoDto.getResponsavel() + " (" + solicitacaoDto.getNomeUnidadeResponsavel() + ")");
		}
		return verificaSituacaoSLA(solicitacaoDto, tc);
	}

	public SolicitacaoServicoDTO restoreByIdInstanciaFluxo(Integer idInstanciaFluxo, TransactionControler tc) throws Exception {
		if (tc != null)
			this.getDao().setTransactionControler(tc);
		SolicitacaoServicoDTO solicitacaoDto = null;
		try {
			solicitacaoDto = (SolicitacaoServicoDTO) this.getDao().restoreByIdInstanciaFluxo(idInstanciaFluxo);
		} catch (Exception e) {
			System.out.println("CITSMART - Erro na recuperação dos dados da solicitação da instância fluxo" + " " + idInstanciaFluxo);

			e.printStackTrace();
		}

		if (solicitacaoDto != null) {
			// Parece estranho, mas isto executa o metodo interno do DTO. Isto eh necessario!!!
			solicitacaoDto.setDataHoraLimiteStr(solicitacaoDto.getDataHoraLimiteStr());

			solicitacaoDto.setNomeServico(solicitacaoDto.getServico());
			if (solicitacaoDto.getNomeUnidadeSolicitante() != null && !solicitacaoDto.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
				solicitacaoDto.setSolicitanteUnidade(solicitacaoDto.getSolicitante() + " (" + solicitacaoDto.getNomeUnidadeSolicitante() + ")");

			if (solicitacaoDto.getNomeUnidadeResponsavel() != null && !solicitacaoDto.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
				solicitacaoDto.setResponsavel(solicitacaoDto.getResponsavel() + " (" + solicitacaoDto.getNomeUnidadeResponsavel() + ")");
		}
		return verificaSituacaoSLA(solicitacaoDto, tc);
	}

	public SolicitacaoServicoDTO restoreByIdInstanciaFluxo(Integer idInstanciaFluxo) throws Exception {
		return restoreByIdInstanciaFluxo(idInstanciaFluxo, null);
	}

	@Override
	public SolicitacaoServicoDTO retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(String login) throws Exception {
		try {
			return this.getDao().retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(login);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void suspende(UsuarioDTO usuarioDto, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		try {
			tc.start();
			suspende(usuarioDto, solicitacaoServicoDto, tc);
			tc.commit();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}finally {
			try {
				tc.close();
			} catch (PersistenceException e) {
				e.printStackTrace();
		}
	}
	}

	public void suspende(UsuarioDTO usuarioDto, SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		if(solicitacaoServicoDto.getSituacaoSLA()!=null && (solicitacaoServicoDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.M.name()) || solicitacaoServicoDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.A.name()))){
			new ExecucaoSolicitacaoServiceEjb().suspende(usuarioDto, solicitacaoServicoDto, tc);
		}else{
			SolicitacaoServicoDTO solicitacaoAuxDto = restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), tc);
			solicitacaoAuxDto.setIdJustificativa(solicitacaoServicoDto.getIdJustificativa());
			solicitacaoAuxDto.setComplementoJustificativa(solicitacaoServicoDto.getComplementoJustificativa());
		}
	}

	public boolean temSolicitacaoServicoAbertaDoEmpregado(Integer idEmpregado) {
		List retorno = null;
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("idSolicitante", "=", idEmpregado));
		try {
			retorno = (List) ((SolicitacaoServicoDao) getDao()).findByCondition(condicoes, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (retorno != null && retorno.size() > 0) {
			return retorno.get(0) == null ? false : true;
		} else {
			return false;
		}
	}

	public void update(IDto model) throws LogicException, ServiceException {
		ExecucaoSolicitacaoServiceEjb execucaoSolicitacaoService = new ExecucaoSolicitacaoServiceEjb();
		TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
		try {
			tc.start();

			// Faz validacao, caso exista.
			validaUpdate(model);

			this.getDao().setTransactionControler(tc);
			this.getDao().updateNotNull(model);

			// Executa operacoes pertinentes ao negocio.
			SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) model;

			//valida email
			if(solicitacaoServicoDto.getEmailcontato() != null && !Util.isValidEmailAddress(solicitacaoServicoDto.getEmailcontato())){
				throw new LogicException(i18nMessage("citcorpore.validacao.emailInvalido"));
			}

			if (solicitacaoServicoDto.getIdTarefa() != null)
				execucaoSolicitacaoService.executa(solicitacaoServicoDto, solicitacaoServicoDto.getIdTarefa(), solicitacaoServicoDto.getAcaoFluxo(), tc);

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

	public void updateSimples(IDto model) throws LogicException, ServiceException {
		TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
		try {
			tc.start();

			// Faz validacao, caso exista.
			validaUpdate(model);

			this.getDao().setTransactionControler(tc);
			this.getDao().update(model);

			// Executa operacoes pertinentes ao negocio.
			SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) model;

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

	public IDto updateInfo(IDto model) throws ServiceException, LogicException {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) model;
		TransactionControler tc = new TransactionControlerImpl( this.getDao().getAliasDB());
		try {
			tc.start();
			validaCreate(model);
			updateInfo(solicitacaoServicoDto, tc, true);
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
		return solicitacaoServicoDto;
	}


	/**
	 * Faz a atualizacao de informacoes da solicitacao, mas nao altera as informacoes da classificacao.
	 *
	 * @param model
	 * @return
	 * @throws ServiceException
	 * @throws LogicException
	 */
	 public SolicitacaoServicoDTO updateInfo(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc, boolean atualizarRelacionados) throws Exception{
		boolean mudancaDescricao = false;
		ExecucaoSolicitacaoServiceEjb execucaoSolicitacaoService = new ExecucaoSolicitacaoServiceEjb();
		ContatoSolicitacaoServicoDao contatoSolicitacaoServicoDao = new ContatoSolicitacaoServicoDao();
		SolicitacaoServicoProblemaDao solicitacaoServicoProblemaDao = new SolicitacaoServicoProblemaDao();
		SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();
		ConhecimentoSolicitacaoDao conhecimentoSolicitacaoDao = new ConhecimentoSolicitacaoDao();
		ItemCfgSolicitacaoServDAO itemCfgSolicitacaoServDAO = new ItemCfgSolicitacaoServDAO();
		ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
		GrupoDao grupoDao = new GrupoDao();

		ContatoSolicitacaoServicoDTO contatoSolicitacaoServicoDTO = new ContatoSolicitacaoServicoDTO();

			SolicitacaoServicoDTO dtoAux = this.restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());

			if (dtoAux != null && solicitacaoServicoDto != null && dtoAux.getDescricao() != null && StringUtils.isNotEmpty(dtoAux.getDescricao()) && solicitacaoServicoDto.getDescricao() != null
					&& StringUtils.isNotEmpty(solicitacaoServicoDto.getDescricao())) {
				if(solicitacaoServicoDto.getDescricao().hashCode() == dtoAux.getDescricao().hashCode()){
					mudancaDescricao = true;
				}
			}

			this.getDao().setTransactionControler(tc);
			contatoSolicitacaoServicoDao.setTransactionControler(tc);
			solicitacaoServicoProblemaDao.setTransactionControler(tc);
			itemCfgSolicitacaoServDAO.setTransactionControler(tc);
			solicitacaoServicoMudancaDao.setTransactionControler(tc);
			conhecimentoSolicitacaoDao.setTransactionControler(tc);
			servicoContratoDao.setTransactionControler(tc);
			grupoDao.setTransactionControler(tc);

			//valida email
			if(solicitacaoServicoDto.getEmailcontato() != null && !Util.isValidEmailAddress(solicitacaoServicoDto.getEmailcontato())){
				throw new LogicException(i18nMessage("citcorpore.validacao.emailInvalido"));
			}

			// Executa operacoes pertinentes ao negocio.
			solicitacaoServicoDto.setIdContatoSolicitacaoServico(contatoSolicitacaoServicoDTO.getIdcontatosolicitacaoservico());
			String nomeServicoAnterior = "";
			String nomeContratoAnterior = "";
			String strDescricaoAnterior = "";
			SolicitacaoServicoDTO solicitacaoServicoAux = (SolicitacaoServicoDTO) this.getDao().restore(solicitacaoServicoDto);
			if (solicitacaoServicoAux != null && solicitacaoServicoDto.getIdGrupoNivel1() == null)
				solicitacaoServicoDto.setIdGrupoNivel1(solicitacaoServicoAux.getIdGrupoNivel1());

			if (solicitacaoServicoDto.getReclassificar() != null && solicitacaoServicoDto.getReclassificar().equalsIgnoreCase("S")) {
				ServicoContratoDTO servicoContratoDto = servicoContratoDao.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
				if (servicoContratoDto == null)
					throw new LogicException(i18nMessage("solicitacaoservico.validacao.servicolocalizado"));

				if (solicitacaoServicoDto.getIdServicoContrato() == null) {
					solicitacaoServicoDto.setIdServicoContrato(servicoContratoDto.getIdServicoContrato());
				}

				if (solicitacaoServicoDto.getIdGrupoNivel1() == null || solicitacaoServicoDto.getIdGrupoNivel1().intValue() <= 0) {
					Integer idGrupoNivel1 = null;
					if (servicoContratoDto.getIdGrupoNivel1() != null && servicoContratoDto.getIdGrupoNivel1().intValue() > 0) {
						idGrupoNivel1 = servicoContratoDto.getIdGrupoNivel1();
					} else {
						String idGrupoN1 = ParametroUtil.getValor(ParametroSistema.ID_GRUPO_PADRAO_NIVEL1, tc, null);
						if (idGrupoN1 != null && !idGrupoN1.trim().equalsIgnoreCase("")) {
							try {
								idGrupoNivel1 = new Integer(idGrupoN1);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					if (idGrupoNivel1 == null || idGrupoNivel1.intValue() <= 0)
						throw new LogicException(i18nMessage("solicitacaoservico.validacao.grupoatendnivel"));
					GrupoDTO grupoDto = new GrupoDTO();
					grupoDto.setIdGrupo(idGrupoNivel1);
					grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
					if (grupoDto == null || grupoDto.getDataFim() != null)
						throw new LogicException(i18nMessage("solicitacaoservico.validacao.grupoatendnivel"));
					solicitacaoServicoDto.setIdGrupoNivel1(idGrupoNivel1);
				}

				if (solicitacaoServicoAux != null) {

					solicitacaoServicoDto.setDataHoraSolicitacao(solicitacaoServicoAux.getDataHoraSolicitacao());
					solicitacaoServicoDto.setDataHoraInicio(solicitacaoServicoAux.getDataHoraInicio());
					solicitacaoServicoDto.setDataHoraCaptura(solicitacaoServicoAux.getDataHoraCaptura());
					strDescricaoAnterior = solicitacaoServicoAux.getDescricao();

					ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
					servicoContratoDTO.setIdServicoContrato(solicitacaoServicoAux.getIdServicoContrato());
					servicoContratoDTO = (ServicoContratoDTO) servicoContratoDao.restore(servicoContratoDTO);
					if (servicoContratoDTO != null) {
						ServicoDao servicoDao = new ServicoDao();
						servicoDao.setTransactionControler(tc);
						ServicoDTO servicoDto = new ServicoDTO();
						servicoDto.setIdServico(servicoContratoDTO.getIdServico());
						servicoDto = (ServicoDTO) servicoDao.restore(servicoDto);
						if (servicoDto != null) {
							nomeServicoAnterior = i18nMessage("citcorpore.comum.codigo") + ": " + servicoDto.getIdServico() + " - " + i18nMessage("citcorpore.comum.nome") + ": "
									+ servicoDto.getNomeServico();
						}
						ContratoDao contratoDao = new ContratoDao();
						contratoDao.setTransactionControler(tc);
						ContratoDTO contratoDto = new ContratoDTO();
						contratoDto.setIdContrato(servicoContratoDTO.getIdContrato());
						contratoDto = (ContratoDTO) contratoDao.restore(contratoDto);
						if (contratoDto != null) {
							nomeContratoAnterior = i18nMessage("citcorpore.comum.codigo") + ": " + contratoDto.getIdContrato() + " - " + i18nMessage("citcorpore.comum.numero") + ": "
									+ contratoDto.getNumero();
						}
					}
				}

				if (solicitacaoServicoDto.getIdGrupoNivel1() == null || solicitacaoServicoDto.getIdGrupoNivel1().intValue() <= 0) {
					Integer idGrupoNivel1 = null;
					if (servicoContratoDto.getIdGrupoNivel1() != null) {
						idGrupoNivel1 = servicoContratoDto.getIdGrupoNivel1();
					} else {
						String idGrupoN1 = ParametroUtil.getValor(ParametroSistema.ID_GRUPO_PADRAO_NIVEL1, tc, null);
						if (idGrupoN1 != null && !idGrupoN1.trim().equalsIgnoreCase("")) {
							try {
								idGrupoNivel1 = new Integer(idGrupoN1);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					if (idGrupoNivel1 == null || idGrupoNivel1.intValue() <= 0)
						throw new LogicException(i18nMessage("solicitacaoservico.validacao.grupoatendnivel"));
					solicitacaoServicoDto.setIdGrupoNivel1(idGrupoNivel1);
				}

				if (solicitacaoServicoDto.getIdGrupoNivel1() == null || solicitacaoServicoDto.getIdGrupoNivel1().intValue() <= 0) {
					Integer idGrupoNivel1 = null;
					if (servicoContratoDto.getIdGrupoNivel1() != null) {
						idGrupoNivel1 = servicoContratoDto.getIdGrupoNivel1();
					} else {
						String idGrupoN1 = ParametroUtil.getValor(ParametroSistema.ID_GRUPO_PADRAO_NIVEL1, tc, null);
						if (idGrupoN1 != null && !idGrupoN1.trim().equalsIgnoreCase("")) {
							try {
								idGrupoNivel1 = new Integer(idGrupoN1);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					if (idGrupoNivel1 == null || idGrupoNivel1.intValue() <= 0)
						throw new LogicException("Grupo de atendimento nivel 1 não parametrizado");
					solicitacaoServicoDto.setIdGrupoNivel1(idGrupoNivel1);
				}

				determinaPrioridadeEPrazo(solicitacaoServicoDto, tc);
			} else {
				solicitacaoServicoDto.setIdServico(null);
				solicitacaoServicoDto.setIdServicoContrato(null);
				solicitacaoServicoDto.setIdPrioridade(null);
				solicitacaoServicoDto.setPrazoCapturaHH(null);
				solicitacaoServicoDto.setPrazoCapturaMM(null);
				solicitacaoServicoDto.setPrazoHH(null);
				solicitacaoServicoDto.setPrazoMM(null);
				solicitacaoServicoDto.setDataHoraInicio(null);
				solicitacaoServicoDto.setDataHoraFim(null);
				solicitacaoServicoDto.setDataHoraLimite(null);
				solicitacaoServicoDto.setDataHoraSolicitacao(null);
				solicitacaoServicoDto.setIdTipoDemandaServico(null);
				solicitacaoServicoDto.setIdFaseAtual(null);
				solicitacaoServicoDto.setSlaACombinar(null);
			}

			if (solicitacaoServicoAux != null && !solicitacaoServicoAux.escalada() && solicitacaoServicoDto.escalada()) {
				String tipoCaptura = ParametroUtil.getValor(ParametroSistema.TIPO_CAPTURA_SOLICITACOES, tc, "1");
				if (tipoCaptura.equals("2"))
					solicitacaoServicoDto.setDataHoraCaptura(solicitacaoServicoDto.getDataHoraInicio());
			}

			this.getDao().updateNotNull(solicitacaoServicoDto);

			if (solicitacaoServicoAux != null && solicitacaoServicoAux.getIdTarefaEncerramento() == null && solicitacaoServicoDto.atendida()) {
			if (solicitacaoServicoDto.getIdTarefa() != null
					&& (solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase(br.com.centralit.bpm.util.Enumerados.ACAO_INICIAR) || solicitacaoServicoDto.getAcaoFluxo().equalsIgnoreCase(
							br.com.centralit.bpm.util.Enumerados.ACAO_EXECUTAR))) {
					solicitacaoServicoDto.setIdTarefaEncerramento(solicitacaoServicoDto.getIdTarefa());
					this.getDao().atualizaIdTarefaEncerramento(solicitacaoServicoDto);
				}
			} else if (solicitacaoServicoAux != null && solicitacaoServicoAux.atendida() && !solicitacaoServicoDto.atendida()) {
				solicitacaoServicoDto.setIdTarefaEncerramento(null);
				this.getDao().atualizaIdTarefaEncerramento(solicitacaoServicoDto);
			}

			if (solicitacaoServicoDto.getInformacoesComplementares() != null || solicitacaoServicoDto.getSolicitacaoServicoQuestionarioDTO() != null) {
				TemplateSolicitacaoServicoDTO templateDto = new TemplateSolicitacaoServicoServiceEjb().recuperaTemplateServico(solicitacaoServicoDto);
				if (templateDto != null) {
					if (templateDto.isQuestionario()) {
						atualizaInformacoesQuestionario(solicitacaoServicoDto, tc);
					} else if (templateDto.getNomeClasseServico() != null) {
						ComplemInfSolicitacaoServicoService informacoesComplementaresService = getInformacoesComplementaresService(templateDto.getNomeClasseServico());
						informacoesComplementaresService.update(tc, solicitacaoServicoDto, solicitacaoServicoDto.getInformacoesComplementares());
					}
				}
			}

			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setLogin(solicitacaoServicoDto.getRegistradoPor());
			if (solicitacaoServicoDto.getReclassificar() != null && solicitacaoServicoDto.getReclassificar().equalsIgnoreCase("S")) {
				String strRecl = i18nMessage("citcorpore.comum.reclassificaosolicitacao");
				strRecl += "\n" + i18nMessage("citcorpore.comum.servicoanterior") + ": " + nomeServicoAnterior;
				strRecl += "\n" + i18nMessage("citcorpore.comum.contratoanterior") + ": " + nomeContratoAnterior;
				strRecl += "\n" + i18nMessage("citcorpore.comum.descricaoanterior") + ": " + strDescricaoAnterior;
				OcorrenciaSolicitacaoServiceEjb.create(solicitacaoServicoDto, null, strRecl, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Reclassificacao, null,
						CategoriaOcorrencia.Reclassificacao.getDescricao(), usuarioDTO, 0, null, tc);
			}
			if (solicitacaoServicoDto.getRegistroexecucao() != null && !solicitacaoServicoDto.getRegistroexecucao().trim().equalsIgnoreCase("")) {
				OcorrenciaSolicitacaoServiceEjb.create(solicitacaoServicoDto, null, solicitacaoServicoDto.getRegistroexecucao(), OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Execucao, null,
						CategoriaOcorrencia.Execucao.getDescricao(), usuarioDTO, 0, null, tc);
			}

			if(atualizarRelacionados){
				contatoSolicitacaoServicoDTO.setNomecontato(solicitacaoServicoDto.getNomecontato());
				contatoSolicitacaoServicoDTO.setEmailcontato(solicitacaoServicoDto.getEmailcontato());
				contatoSolicitacaoServicoDTO.setTelefonecontato(solicitacaoServicoDto.getTelefonecontato());
				contatoSolicitacaoServicoDTO.setObservacao(solicitacaoServicoDto.getObservacao());
				contatoSolicitacaoServicoDTO.setRamal(solicitacaoServicoDto.getRamal());

				if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER")) {
					if (tc != null) {
						contatoSolicitacaoServicoDao.setTransactionControler(tc);
					}
				}

				if (solicitacaoServicoDto.getIdLocalidade() != null) {
					contatoSolicitacaoServicoDTO.setIdLocalidade(solicitacaoServicoDto.getIdLocalidade());
				}

				if (solicitacaoServicoDto.getIdContatoSolicitacaoServico() != null) {
					contatoSolicitacaoServicoDTO.setIdcontatosolicitacaoservico(solicitacaoServicoDto.getIdContatoSolicitacaoServico());
					contatoSolicitacaoServicoDao.update(contatoSolicitacaoServicoDTO);
				} else {
					contatoSolicitacaoServicoDTO = (ContatoSolicitacaoServicoDTO) contatoSolicitacaoServicoDao.create(contatoSolicitacaoServicoDTO);
				}

				solicitacaoServicoProblemaDao.deleteByIdSolictacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
				if (solicitacaoServicoDto.getColItensProblema() != null) {
					solicitacaoServicoProblemaDao.deleteByIdSolictacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
					for (Iterator it = solicitacaoServicoDto.getColItensProblema().iterator(); it.hasNext();) {
						ProblemaDTO problemaDTO = (ProblemaDTO) it.next();
						SolicitacaoServicoProblemaDTO solicitacaoServicoProblemaDTO = new SolicitacaoServicoProblemaDTO();
						solicitacaoServicoProblemaDTO.setIdProblema(problemaDTO.getIdProblema());
						solicitacaoServicoProblemaDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
						solicitacaoServicoProblemaDao.create(solicitacaoServicoProblemaDTO);
					}

					// solicitacaoServicoProblemaDao
				}

				solicitacaoServicoMudancaDao.deleteByIdSolictacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
				if (solicitacaoServicoDto.getColItensMudanca() != null) {
					for (Iterator it = solicitacaoServicoDto.getColItensMudanca().iterator(); it.hasNext();) {
						RequisicaoMudancaDTO requisicaoMudancaDTO = (RequisicaoMudancaDTO) it.next();

						SolicitacaoServicoMudancaDTO solicitacaoServicoMudancaDTO = new SolicitacaoServicoMudancaDTO();
						solicitacaoServicoMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
						solicitacaoServicoMudancaDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
						solicitacaoServicoMudancaDao.create(solicitacaoServicoMudancaDTO);
					}

					// solicitacaoServicoProblemaDao
				}

				conhecimentoSolicitacaoDao.deleteByIdSolictacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
				if (solicitacaoServicoDto.getColItensBaseConhecimento() != null) {
					for (Iterator it = solicitacaoServicoDto.getColItensBaseConhecimento().iterator(); it.hasNext();) {
						BaseConhecimentoDTO baseConhecimentoDTO = (BaseConhecimentoDTO) it.next();

						ConhecimentoSolicitacaoDTO conhecimentoSolicitacaoDTO = new ConhecimentoSolicitacaoDTO();
						conhecimentoSolicitacaoDTO.setIdBaseConhecimento(baseConhecimentoDTO.getIdBaseConhecimento());
						conhecimentoSolicitacaoDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
						conhecimentoSolicitacaoDao.create(conhecimentoSolicitacaoDTO);
					}

					// solicitacaoServicoProblemaDao
				}

				//PersistirItemBaseConhecimento(solicitacaoServicoDto, conhecimentoSolicitacaoDao);

				itemCfgSolicitacaoServDAO.deleteByIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());

				if (solicitacaoServicoDto.getColItensICSerialize() != null) {
					for (ItemConfiguracaoDTO bean : solicitacaoServicoDto.getColItensICSerialize()) {
						ItemCfgSolicitacaoServDTO dto = new ItemCfgSolicitacaoServDTO();
						dto.setIdItemConfiguracao(bean.getIdItemConfiguracao());
						dto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
						dto.setDataInicio(Util.getSqlDataAtual());

						itemCfgSolicitacaoServDAO.create(dto);

						solicitacaoServicoDto = this.verificaSituacaoSLA(solicitacaoServicoDto, tc);
					}
				}

				if (solicitacaoServicoDto.getColArquivosUpload() != null) {
					gravaInformacoesGED(solicitacaoServicoDto.getColArquivosUpload(), 1, solicitacaoServicoDto, tc);
				}

				if (solicitacaoServicoDto.getSituacao() != null && solicitacaoServicoDto.getSituacao().equalsIgnoreCase("Resolvida")) {
					if (solicitacaoServicoDto.getBeanBaseConhecimento().getTitulo() != null && !solicitacaoServicoDto.getBeanBaseConhecimento().getTitulo().isEmpty()) {
						this.InserirNaBaseConhecimento(solicitacaoServicoDto, tc);
					}
				}
			}

			if (solicitacaoServicoDto.getIdTarefa() != null)
				execucaoSolicitacaoService.executa(solicitacaoServicoDto, solicitacaoServicoDto.getIdTarefa(), solicitacaoServicoDto.getAcaoFluxo(), tc);

			if (solicitacaoServicoAux != null && solicitacaoServicoDto.getIdGrupoAtual() != null
					&& UtilStrings.nullToVazio(solicitacaoServicoDto.getAcaoFluxo()).equals(br.com.centralit.bpm.util.Enumerados.ACAO_EXECUTAR)
					&& (!solicitacaoServicoAux.escalada() || solicitacaoServicoAux.getIdGrupoAtual().intValue() != solicitacaoServicoDto.getIdGrupoAtual().intValue())) {
				execucaoSolicitacaoService.direcionaAtendimento(solicitacaoServicoDto, tc);
			}

			/*
			 * Bruno.aquino 29/05/2014
			 *
			 * Se ocorrerem alguma alteraçao na solicitação, é enviado um email ao solicitante informando que ocorreu alguma alteração. Se a alteração foi feita na Descrição da solicitação, será enviado
			 * por email a descrição também.
			 */
			EmpregadoDao empregadoDao = new EmpregadoDao();
			if (dtoAux != null && dtoAux.getIdTipoDemandaServico().intValue() == 3
					&& ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ATIVAR_ENVIO_EMAIL_UPDATE_INCIDENTE, "N").equalsIgnoreCase("S")) {
				SolicitacaoServicoDTO aux = solicitacaoServicoDto;
				EmpregadoDTO empregatoDto = empregadoDao.restoreByIdEmpregado(aux.getIdSolicitante());
				String emailPara = "";
				if(empregatoDto!=null){
					emailPara = empregatoDto.getEmail();
				}

				String remetente = ParametroUtil.getValor(ParametroSistema.RemetenteNotificacoesSolicitacao);
				if (remetente != null && emailPara!=null && StringUtils.isNotEmpty(emailPara) && StringUtils.isNotEmpty(remetente) && empregatoDto != null) {
					if(!mudancaDescricao){
						ModeloEmailDTO modeloEmailDto = new ModeloEmailDao().findByIdentificador("alterSolServDesc");
						if(modeloEmailDto!=null){
							MensagemEmail mensagem = new MensagemEmail(modeloEmailDto.getIdModeloEmail(), new IDto[] { aux });
							if(mensagem!=null){
								mensagem.envia(emailPara, null, remetente);
							}

						}
					}else{
						ModeloEmailDTO modeloEmailDto = new ModeloEmailDao().findByIdentificador("alterSolServico");
						if(modeloEmailDto!=null){
							MensagemEmail mensagem = new MensagemEmail(modeloEmailDto.getIdModeloEmail(), new IDto[] { aux });
							if(mensagem!=null){
								mensagem.envia(emailPara, null, remetente);
							}
						}

					}
				}
			}


		return solicitacaoServicoDto;
	}

	/**
	 * Faz a atualizacao dos anexos da solicitacao, mas nao altera as informacoes da soliciação.
	 *
	 * @param model
	 * @return
	 * @throws ServiceException
	 * @throws LogicException
	 */

	public IDto updateInfoCollection(IDto model) throws ServiceException, LogicException {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) model;

		return solicitacaoServicoDto;
	}

	@Override
	public void updateNotNull(IDto obj) throws Exception {
		this.getDao().updateNotNull(obj);

	}

	/**
	 * Faz a mudanca de SLA
	 *
	 * @param model
	 * @return
	 * @throws ServiceException
	 * @throws LogicException
	 */
	public void updateSLA(IDto model) throws ServiceException, LogicException {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) model;
		ExecucaoSolicitacaoServiceEjb execucaoSolicitacaoService = new ExecucaoSolicitacaoServiceEjb();
		TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
		try {
			tc.start();

			// Faz validacao, caso exista.
			validaUpdate(model);

			this.getDao().setTransactionControler(tc);
			solicitacaoServicoDto.setDataHoraInicioSLA(null);
			solicitacaoServicoDto.setDataHoraReativacao(null);

			if (solicitacaoServicoDto.getTempoDecorridoHH() == null) {
				solicitacaoServicoDto.setTempoDecorridoHH(new Integer(0));
			}
			if (solicitacaoServicoDto.getTempoDecorridoMM() == null) {
				solicitacaoServicoDto.setTempoDecorridoMM(new Integer(0));
			}

			if (solicitacaoServicoDto.getSlaACombinar().equalsIgnoreCase("S")) {
				solicitacaoServicoDto.setPrazoCapturaHH(0);
				solicitacaoServicoDto.setPrazoCapturaMM(0);
				solicitacaoServicoDto.setPrazoHH(0);
				solicitacaoServicoDto.setPrazoMM(0);
			} else {
				SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoDTO();
				solicitacaoAuxDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
				solicitacaoAuxDto = (SolicitacaoServicoDTO) solicitacaoServicoDao.restore(solicitacaoAuxDto);
				solicitacaoServicoDto.setDataHoraInicioSLA(solicitacaoAuxDto.getDataHoraInicioSLA());
				solicitacaoServicoDto.setDataHoraReativacaoSLA(solicitacaoAuxDto.getDataHoraReativacaoSLA());
				determinaPrazoLimite(solicitacaoServicoDto, solicitacaoServicoDto.getIdCalendario(), tc);
			}

			solicitacaoServicoDao.updateNotNull(model);
			solicitacaoServicoDao.limpaDataReativacao(solicitacaoServicoDto);

			String strOcorr = "\n" + i18nMessage("gerenciaservico.mudarsla.tiposla") + ": ";
			if (solicitacaoServicoDto.getSlaACombinar().equalsIgnoreCase("S")) {
				strOcorr += i18nMessage("citcorpore.comum.acombinar");
			} else {
				strOcorr += i18nMessage("citcorpore.comum.definicaonovotempo");
			}

			// SolicitacaoServicoDTO solicitacaoAuxDto =
			// restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
			strOcorr += "\n " + i18nMessage("gerenciamento.mudarsla.prazoanterior");
			if (solicitacaoServicoDto.getPrazohhAnterior() != null) {
				strOcorr += solicitacaoServicoDto.getPrazohhAnterior() + "h ";
			}
			if (solicitacaoServicoDto.getPrazommAnterior() != null) {
				strOcorr += solicitacaoServicoDto.getPrazommAnterior() + "m ";
			}

			JustificativaSolicitacaoDTO justificativaDto = new JustificativaSolicitacaoDTO();
			justificativaDto.setIdJustificativa(solicitacaoServicoDto.getIdJustificativa());
			justificativaDto.setComplementoJustificativa(solicitacaoServicoDto.getComplementoJustificativa());

			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setLogin(solicitacaoServicoDto.getRegistradoPor());
			OcorrenciaSolicitacaoServiceEjb.create(solicitacaoServicoDto, null, strOcorr, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.MudancaSLA, null, CategoriaOcorrencia.MudancaSLA.getDescricao(),
					usuarioDTO, 0, justificativaDto, tc);

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

	/**
	 *
	 * @author breno.guimaraes
	 */
	public void updateSolicitacaoPai(int idSolicitacaoPai, int idSolicitacao) {
		try {
			((SolicitacaoServicoDao) getDao()).updateSolicitacaoPai(idSolicitacao, idSolicitacaoPai);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateTimeAction(Integer idGrupoRedirect, Integer idPrioridadeRedirect, Integer idSolicitacaoServicoParm, TransactionControler tc) throws ServiceException, LogicException {

		ExecucaoSolicitacaoServiceEjb execucaoSolicitacaoService = new ExecucaoSolicitacaoServiceEjb();
		OcorrenciaSolicitacaoDao ocorrenciaSolicitacaoDao = new OcorrenciaSolicitacaoDao();

		if(tc == null)
			tc = new TransactionControlerImpl(this.getDao().getAliasDB());

		try {
			if(!tc.isStarted())
			tc.start();

			// Faz validacao, caso exista.

			this.getDao().setTransactionControler(tc);
			ocorrenciaSolicitacaoDao.setTransactionControler(tc);

			List<SolicitacaoServicoDTO> listaSolicitacao = new ArrayList<SolicitacaoServicoDTO>();

			SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoDTO();
			solicitacaoAuxDto.setIdSolicitacaoServico(idSolicitacaoServicoParm);

			listaSolicitacao = (List<SolicitacaoServicoDTO>) this.getDao().find(solicitacaoAuxDto);
			if (listaSolicitacao != null) {
				solicitacaoAuxDto = listaSolicitacao.get(0);
			}
			// if(solicitacaoAuxDto.getIdGrupoAtual() != null){
			// return;
			// }

			SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();

			solicitacaoServicoDto.setIdGrupoAtual(idGrupoRedirect);
			solicitacaoServicoDto.setIdPrioridade(idPrioridadeRedirect);
			solicitacaoServicoDto.setIdSolicitacaoServico(idSolicitacaoServicoParm);

			this.getDao().updateNotNull(solicitacaoServicoDto);
			execucaoSolicitacaoService.direcionaAtendimentoAutomatico(solicitacaoServicoDto, tc);
			String strOcorr = "\nEscalação automática.";

			// SolicitacaoServicoDTO solicitacaoAuxDto =
			// restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());

			JustificativaSolicitacaoDTO justificativaDto = new JustificativaSolicitacaoDTO();
			justificativaDto.setIdJustificativa(solicitacaoServicoDto.getIdJustificativa());
			justificativaDto.setComplementoJustificativa(solicitacaoServicoDto.getComplementoJustificativa());

			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setLogin("Automático");

			OcorrenciaSolicitacaoServiceEjb.create(solicitacaoServicoDto, null, strOcorr, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Atualizacao, null,
					CategoriaOcorrencia.Atualizacao.getDescricao(), usuarioDTO, 0, justificativaDto, tc);

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

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	@Override
	public boolean verificarExistenciaDeUnidade(Integer idUnidade) throws Exception {
		return this.getDao().verificarExistenciaDeUnidade(idUnidade);
	}

	@Override
	public boolean verificarExistenciaSolicitacaoFilho(Integer idSolicitacaoServicoPai) throws Exception {

		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoFilho = new ArrayList<SolicitacaoServicoDTO>();

		listSolicitacaoServicoFilho = this.getDao().findByIdSolicitacaoPai(idSolicitacaoServicoPai);

		if (listSolicitacaoServicoFilho != null && !listSolicitacaoServicoFilho.isEmpty()) {

			return true;

		} else {

			return false;

		}
	}

	@Override
	public SolicitacaoServicoDTO verificaSituacaoSLA(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
		return verificaSituacaoSLA(solicitacaoDto, null);
	}

	public SolicitacaoServicoDTO verificaSituacaoSLA(SolicitacaoServicoDTO solicitacaoDto, TransactionControler tc) throws Exception {
 		long atrasoSLA = 0;

		if (solicitacaoDto == null)
			return null;
		/**
		 * Não calcula o atraso dos SLAs a combinar
		 */
		boolean slaACombinar = (solicitacaoDto.getPrazoHH() == null || solicitacaoDto.getPrazoHH() == 0) && (solicitacaoDto.getPrazoMM() == null || solicitacaoDto.getPrazoMM() == 0);

		if (solicitacaoDto.getDataHoraLimite() == null)
			determinaPrazoLimite(solicitacaoDto, null, tc);

		if (solicitacaoDto.getDataHoraLimite() != null && !slaACombinar) {
			boolean bCalcula = true;
			if (solicitacaoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.name())) {
				bCalcula = solicitacaoDto.getDataHoraSuspensao().compareTo(solicitacaoDto.getDataHoraLimite()) > 0;
			}else if (solicitacaoDto.getSituacao().equals(SituacaoSolicitacaoServico.Cancelada.name())) {
				bCalcula = false;
			}else if (solicitacaoDto.getDataHoraSuspensaoSLA() != null && solicitacaoDto.getSituacaoSLA() != null && solicitacaoDto.getSituacaoSLA().equalsIgnoreCase("S")) {
				bCalcula = solicitacaoDto.getDataHoraSuspensaoSLA().compareTo(solicitacaoDto.getDataHoraLimite()) > 0;
			}
			if (bCalcula) {
				Timestamp dataHoraLimite = solicitacaoDto.getDataHoraLimite();
				Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
				if (solicitacaoDto.encerrada())
					dataHoraComparacao = solicitacaoDto.getDataHoraFim();
				if (dataHoraComparacao != null) {
					if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
						atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraComparacao, dataHoraLimite) / 1000;
					}
				}
			}
		}

		solicitacaoDto.setAtrasoSLA(atrasoSLA);
		return solicitacaoDto;
	}

	/**
	 * Método que calcula o atraso das SLAs que não estão a combinar e com situação diferentes de suspensa e cancelada
	 *
	 * @param solicitacaoDto
	 * @param tc
	 * @return SolicitacaoServicoDTO
	 * @throws Exception
	 * @author rodrigo.acorse
	 */
	@Deprecated
	public SolicitacaoServicoDTO verificaSituacaoSLAsValidas(SolicitacaoServicoDTO solicitacaoDto, TransactionControler tc) throws Exception {
		long atrasoSLA = 0;

		if (solicitacaoDto == null)
			return null;

		boolean slaACombinar = (solicitacaoDto.getPrazoHH() == null || solicitacaoDto.getPrazoHH() == 0) && (solicitacaoDto.getPrazoMM() == null || solicitacaoDto.getPrazoMM() == 0);

		if (solicitacaoDto.getDataHoraLimite() == null)
			determinaPrazoLimite(solicitacaoDto, null, tc);

		if (solicitacaoDto.getDataHoraLimite() != null && !slaACombinar && !solicitacaoDto.getSituacao().equalsIgnoreCase("Suspensa") && !solicitacaoDto.getSituacao().equalsIgnoreCase("Cancelada")) {
			boolean bCalcula = true;
			if (solicitacaoDto.getSituacaoSLA() != null && solicitacaoDto.getSituacaoSLA().equalsIgnoreCase("S"))
				bCalcula = solicitacaoDto.getDataHoraSuspensaoSLA().compareTo(solicitacaoDto.getDataHoraLimite()) > 0;
			if (bCalcula) {
				Timestamp dataHoraLimite = solicitacaoDto.getDataHoraLimite();
				Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
				if (solicitacaoDto.encerrada())
					dataHoraComparacao = solicitacaoDto.getDataHoraFim();
				if (dataHoraComparacao != null) {
					if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
						atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraComparacao, dataHoraLimite) / 1000;
					}
				}
			}
		}

		solicitacaoDto.setAtrasoSLA(atrasoSLA);
		return solicitacaoDto;
	}

	@Override
	public Collection incidentesPorContrato(Integer idContrato) throws Exception {
		return this.getDao().incidentesPorContrato(idContrato);

	}

	public void atualizaInformacoesQuestionario(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		ControleQuestionariosDao controleQuestionariosDao = new ControleQuestionariosDao();
		SolicitacaoServicoQuestionarioDao solicitacaoServicoQuestionarioDao = new SolicitacaoServicoQuestionarioDao();
		RespostaItemQuestionarioDao respostaItemDao = new RespostaItemQuestionarioDao();
		RespostaItemQuestionarioServiceBean respostaItemQuestionarioServiceBean = new RespostaItemQuestionarioServiceBean();

		controleQuestionariosDao.setTransactionControler(tc);
		solicitacaoServicoQuestionarioDao.setTransactionControler(tc);
		respostaItemDao.setTransactionControler(tc);

		SolicitacaoServicoQuestionarioDTO solicitacaoServicoQuestionarioDto = solicitacaoServicoDto.getSolicitacaoServicoQuestionarioDTO();
		if (solicitacaoServicoQuestionarioDto.getIdSolicitacaoQuestionario() != null && solicitacaoServicoQuestionarioDto.getIdSolicitacaoQuestionario().intValue() > 0) {
			solicitacaoServicoQuestionarioDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
			solicitacaoServicoQuestionarioDto.setDataHoraGrav(UtilDatas.getDataHoraAtual());
			solicitacaoServicoQuestionarioDao.updateNotNull(solicitacaoServicoQuestionarioDto);

			respostaItemDao.deleteByIdIdentificadorResposta(solicitacaoServicoQuestionarioDto.getIdSolicitacaoQuestionario());
			respostaItemQuestionarioServiceBean.processCollection(tc, solicitacaoServicoQuestionarioDto.getColValores(), solicitacaoServicoQuestionarioDto.getColAnexos(),
					solicitacaoServicoQuestionarioDto.getIdSolicitacaoQuestionario(), null);
		} else {
			ControleQuestionariosDTO controleQuestionariosDto = new ControleQuestionariosDTO();
			controleQuestionariosDto = (ControleQuestionariosDTO) controleQuestionariosDao.create(controleQuestionariosDto);

			solicitacaoServicoQuestionarioDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
			solicitacaoServicoQuestionarioDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
			solicitacaoServicoQuestionarioDto.setIdTarefa(solicitacaoServicoDto.getIdTarefa());
			if (solicitacaoServicoQuestionarioDto.getDataQuestionario() == null)
				solicitacaoServicoQuestionarioDto.setDataQuestionario(UtilDatas.getDataAtual());
			solicitacaoServicoQuestionarioDto.setSituacao("E");
			solicitacaoServicoQuestionarioDto.setIdSolicitacaoQuestionario(controleQuestionariosDto.getIdControleQuestionario());
			solicitacaoServicoQuestionarioDto.setDataHoraGrav(UtilDatas.getDataHoraAtual());
			SolicitacaoServicoQuestionarioDTO solQuestionariosDTO = (SolicitacaoServicoQuestionarioDTO) solicitacaoServicoQuestionarioDao.create(solicitacaoServicoQuestionarioDto);

			Integer idIdentificadorResposta = solQuestionariosDTO.getIdSolicitacaoQuestionario();
			respostaItemQuestionarioServiceBean.processCollection(tc, solQuestionariosDTO.getColValores(), solQuestionariosDTO.getColAnexos(), idIdentificadorResposta, null);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriteriosPaginado(PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto, String paginacao, Integer pagAtual,
			Integer pagAtualAux, Integer totalPag, Integer quantidadePaginator, String campoPesquisa) throws Exception {
		try {
			return this.getDao().findByIdContratoPaginada(pesquisaSolicitacaoServicoDto, paginacao, pagAtual, pagAtualAux, totalPag, quantidadePaginator, campoPesquisa);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriteriosPaginado(PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto, String paginacao, Integer pagAtual,
			Integer pagAtualAux, Integer totalPag, Integer quantidadePaginator, String campoPesquisa, Collection<UnidadeDTO> unidadesColaborador) throws Exception {
		try {



			return this.getDao().findByIdContratoPaginada(pesquisaSolicitacaoServicoDto, paginacao, pagAtual, pagAtualAux, totalPag, quantidadePaginator, campoPesquisa, unidadesColaborador);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


	@Override
	public String calculaSLA(SolicitacaoServicoDTO solicitacaoServicoDto, HttpServletRequest request) throws Exception {
		Integer prazoHH = 0;
		Integer prazoMM = 0;

		TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();
		AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
		AcordoServicoContratoDao acordoServicoContratoDao = new AcordoServicoContratoDao();
		PrioridadeServicoUnidadeDao prioridadeServicoUnidadeDao = new PrioridadeServicoUnidadeDao();
		EmpregadoDao empregadoDao = new EmpregadoDao();
		MatrizPrioridadeDAO matrizPrioriDao = new MatrizPrioridadeDAO();
		ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
		PrioridadeServicoUsuarioDao prioridadeServicoUsuarioDao = new PrioridadeServicoUsuarioDao();
		PrioridadeAcordoNivelServicoDao prioridadeAcordoNivelServicoDao = new PrioridadeAcordoNivelServicoDao();

		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		if (tc != null) {
			try {
			tc.start();

			tempoAcordoNivelServicoDao.setTransactionControler(tc);
			acordoNivelServicoDao.setTransactionControler(tc);
			acordoServicoContratoDao.setTransactionControler(tc);
			prioridadeServicoUnidadeDao.setTransactionControler(tc);
			empregadoDao.setTransactionControler(tc);
			matrizPrioriDao.setTransactionControler(tc);
			servicoContratoDao.setTransactionControler(tc);
			prioridadeServicoUsuarioDao.setTransactionControler(tc);
			prioridadeAcordoNivelServicoDao.setTransactionControler(tc);

		Integer idPrioridade = null;

		ServicoContratoDTO servicoContratoDto = servicoContratoDao.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
		if(servicoContratoDto != null) {
			Integer idServicoContrato = servicoContratoDto.getIdServicoContrato();

			if (solicitacaoServicoDto.getIdSolicitante() != null) {
				EmpregadoDTO empregadoDTO = null;
				empregadoDTO = (EmpregadoDTO) empregadoDao.restoreByIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
				if (empregadoDTO != null && empregadoDTO.getIdUnidade() != null) {
					PrioridadeServicoUnidadeDTO prioridadeServicoUnidadeDto = prioridadeServicoUnidadeDao.restore(idServicoContrato, empregadoDTO.getIdUnidade());
					if (prioridadeServicoUnidadeDto != null) {
						idPrioridade = prioridadeServicoUnidadeDto.getIdPrioridade();
					}
				}
			}

			String calcularDinamicamente = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CALCULAR_PRIORIDADE_SOLICITACAO_DINAMICAMENTE, "N");

			if (!calcularDinamicamente.trim().equalsIgnoreCase("S")) {
				if (idPrioridade == null) {
					if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("B") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("B")) {
						idPrioridade = 5;
					} else if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("B") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("M")) {
						idPrioridade = 4;
					} else if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("B") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("A")) {
						idPrioridade = 3;
					} else if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("M") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("B")) {
						idPrioridade = 4;
					} else if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("M") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("M")) {
						idPrioridade = 3;
					} else if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("M") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("A")) {
						idPrioridade = 2;
					} else if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("A") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("B")) {
						idPrioridade = 3;
					} else if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("A") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("M")) {
						idPrioridade = 2;
					} else if (solicitacaoServicoDto.getUrgencia().equalsIgnoreCase("A") && solicitacaoServicoDto.getImpacto().equalsIgnoreCase("A")) {
						idPrioridade = 1;
					}
				}
			} else {
				String siglaImpacto = solicitacaoServicoDto.getImpacto();
				String siglaUrgencia = solicitacaoServicoDto.getUrgencia();
				Integer valorPrioridade = matrizPrioriDao.consultaValorPrioridade(siglaImpacto.trim().toUpperCase(), siglaUrgencia.trim().toUpperCase());
				idPrioridade = valorPrioridade;
			}

			AcordoNivelServicoDTO acordoNivelServicoDto = acordoNivelServicoDao.findAtivoByIdServicoContrato(idServicoContrato, "T");
			if (acordoNivelServicoDto == null) {
				acordoNivelServicoDto = new AcordoNivelServicoDTO();
				AcordoServicoContratoDTO acordoServicoContratoDTO = acordoServicoContratoDao.findAtivoByIdServicoContrato(idServicoContrato, "T");
				if (acordoServicoContratoDTO != null) {
					acordoNivelServicoDto.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
				}
				// Consulta prioridade do usuário de acordo com sla global
				PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO = prioridadeServicoUsuarioDao.findByIdAcordoNivelServicoAndIdUsuario(acordoNivelServicoDto.getIdAcordoNivelServico(),
						solicitacaoServicoDto.getIdSolicitante());
				if (prioridadeServicoUsuarioDTO != null && prioridadeServicoUsuarioDTO.getIdPrioridade() != null) {
					idPrioridade = prioridadeServicoUsuarioDTO.getIdPrioridade();
				}
				// Consulta prioridade da unidade do usuário de acordo com sla global
				PrioridadeAcordoNivelServicoDTO prioridadeAcordoNivelServicoDTO = prioridadeAcordoNivelServicoDao.findByIdAcordoNivelServicoAndIdUnidade(acordoNivelServicoDto.getIdAcordoNivelServico(),
						solicitacaoServicoDto.getIdUnidade());
				if (prioridadeAcordoNivelServicoDTO != null && prioridadeAcordoNivelServicoDTO.getIdPrioridade() != null) {
					idPrioridade = prioridadeAcordoNivelServicoDTO.getIdPrioridade();
				}
			}
			if (idPrioridade == null) {
				idPrioridade = acordoNivelServicoDto.getIdPrioridadePadrao();
			}

			Collection<TempoAcordoNivelServicoDTO> colTempos = tempoAcordoNivelServicoDao.findByIdAcordoAndIdPrioridade(acordoNivelServicoDto.getIdAcordoNivelServico(), idPrioridade);
			if (colTempos != null) {
				for (TempoAcordoNivelServicoDTO tempoAcordoDto : colTempos) {
					if (tempoAcordoDto.getTempoHH() != null) {
						prazoHH += tempoAcordoDto.getTempoHH().intValue();
					}
					if (tempoAcordoDto.getTempoMM() != null) {
						prazoMM += tempoAcordoDto.getTempoMM().intValue();
					}
				}
				while(prazoMM >= 60){
					prazoHH = prazoHH + 1;
					prazoMM = prazoMM - 60;
				}
					}
			if (prazoHH.equals(0) && prazoMM.equals(0)) {
				return UtilI18N.internacionaliza(request, "citcorpore.comum.aCombinar");
			} else {
				String hh = prazoHH.toString();
				String mm = prazoMM.toString();

				if (hh.length() == 1)
					hh = "0" + hh;
				if (mm.length() == 1)
					mm = "0" + mm;

				return hh + ":" + mm;
			}
		}
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					tc.close();
				} catch (PersistenceException e) {
					e.printStackTrace();
				}
			}
		}

		return "";
	}

	public SolicitacaoServicoDTO findByIdSolicitacaoServico(Integer idSolicitacaoServico) throws Exception {
		try {
			return this.getDao().findByIdSolicitacaoServico(idSolicitacaoServico);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdGrupo(Integer idGrupo) throws Exception {
		try {
			return this.getDao().findByIdGrupo(idGrupo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataBaixa(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdGrupoEDataBaixa(idGrupo, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataMedia(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdGrupoEDataMedia(idGrupo, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAlta(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdGrupoEDataAlta(idGrupo, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasBaixa(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdGrupoEDataAtendidasBaixa(idGrupo, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasMedia(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdGrupoEDataAtendidasMedia(idGrupo, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasAlta(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdGrupoEDataAtendidasAlta(idGrupo, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataTotal(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdGrupoEDataTotal(idGrupo, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataSuspensasTotal(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdGrupoEDataSuspensasTotal(idGrupo, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdPessoaEDataAtendidas(Integer idGrupo, String login, String nome, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdPessoaEDataAtendidas(idGrupo, login, nome, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdPessoaEData(Integer idGrupo, String login, String nome, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdPessoaEData(idGrupo, login, nome, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdPessoaEDataNaoAtendidas(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdPessoaEDataNaoAtendidas(idGrupo, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<RelatorioSolicitacaoPorExecutanteDTO> listaSolicitacaoPorExecutante(RelatorioSolicitacaoPorExecutanteDTO relatorioSolicitacaoPorExecutanteDto) throws Exception {
		return getSolicitacaoServicoDao().listaSolicitacaoPorExecutante(relatorioSolicitacaoPorExecutanteDto);
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasTotal(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdGrupoEDataAtendidasTotal(idGrupo, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtrasadasTotal(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
		try {
			return this.getDao().findByIdGrupoEDataAtrasadasTotal(idGrupo, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO> listaServicoPorSolicitacaoServico(RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO relatorioAnaliseServicoDto)
			throws Exception {

		Collection<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO> listFinal = new ArrayList<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO>();

		Collection<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO> listAux = getSolicitacaoServicoDao().listaServicoPorSolicitacaoServico(relatorioAnaliseServicoDto);

		Collection<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO> listaSolicitacaoServicoProblema = new ArrayList<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO>();

		if (listAux != null) {
			for (RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO relatorioAnaliseServico : listAux) {
				listaSolicitacaoServicoProblema = getSolicitacaoServicoDao().listaSolicitacaoServicoProblemaPorServico(relatorioAnaliseServico);
				relatorioAnaliseServico.setListaSolicitacaoServicoProblema(listaSolicitacaoServicoProblema);
				listFinal.add(relatorioAnaliseServico);
			}
			return listFinal;
		}

		return listAux;

	}

	public boolean permissaoGrupoExecutorServico(int idGrupoExecutor, int idTipoFluxoSolicitacaoServico) throws Exception {
		boolean resultado = false;

		PermissoesFluxoService permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);

		resultado = permissoesFluxoService.permissaoGrupoExecutorLiberacaoServico(idGrupoExecutor, idTipoFluxoSolicitacaoServico);

		return resultado;
	}

	public Collection<SolicitacaoServicoDTO> listaSolicitacoesPorIdEmpregado(Integer pgAtual, Integer qtdPaginacao, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception {
		try {

			return this.getDao().listaSolicitacoesPorIdEmpregado(pgAtual, qtdPaginacao, gerenciamentoBean, listContratoUsuarioLogado);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection<SolicitacaoServicoDTO> listaSolicitacoesPorIdEmpregado(Integer pgAtual, Integer qtdPaginacao, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado, String[] filtro) throws Exception {
		try {

			return this.getDao().listaSolicitacoesPorIdEmpregado(pgAtual, qtdPaginacao, gerenciamentoBean, listContratoUsuarioLogado, filtro);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


	public Collection<SolicitacaoServicoDTO> resumoSolicitacoesServico(Collection<TarefaFluxoDTO> listTarefas, Integer idTipoDemandaServico, Integer idPrioridade) throws Exception {
		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoDto = new ArrayList();
		HashMap hashMap = new HashMap();
		Integer qtdEmAndamento = 0;
		Integer qtdCancelada = 0;
		Integer qtdSuspensa = 0;
		Integer qtdPrazoVencido = 0;

		listSolicitacaoServicoDto = listByTarefas(listTarefas, new TransactionControlerImpl(br.com.citframework.util.Constantes.getValue("DATABASE_ALIAS")));

		if (listTarefas != null) {
			for (SolicitacaoServicoDTO solicitacaoServicoDTO : listSolicitacaoServicoDto) {
				if (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("EmAndamento")) {
					qtdEmAndamento++;
				}
				if (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Cancelada")) {
					qtdEmAndamento++;
				}
				if (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Suspensa")) {
					qtdSuspensa++;
				}

				solicitacaoServicoDTO.setDataHoraLimiteStr(solicitacaoServicoDTO.getDataHoraLimiteStr());
				solicitacaoServicoDTO.setDataHoraInicioSLA(solicitacaoServicoDTO.getDataHoraInicioSLA());

				solicitacaoServicoDTO.setNomeServico(solicitacaoServicoDTO.getServico());
				if (solicitacaoServicoDTO.getNomeUnidadeSolicitante() != null && !solicitacaoServicoDTO.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
					solicitacaoServicoDTO.setSolicitanteUnidade(solicitacaoServicoDTO.getSolicitante() + " (" + solicitacaoServicoDTO.getNomeUnidadeSolicitante() + ")");

				if (solicitacaoServicoDTO.getNomeUnidadeResponsavel() != null && !solicitacaoServicoDTO.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
					solicitacaoServicoDTO.setResponsavel(solicitacaoServicoDTO.getResponsavel() + " (" + solicitacaoServicoDTO.getNomeUnidadeResponsavel() + ")");
				this.verificaSituacaoSLA(solicitacaoServicoDTO);
				if (solicitacaoServicoDTO.getAtrasoSLA() > 0 && !solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Cancelada")) {
					qtdPrazoVencido++;
				}
			}
		}

		hashMap.put("Em Andamento", qtdEmAndamento);
		hashMap.put("Cancelada", qtdCancelada);
		hashMap.put("Suspensa", qtdSuspensa);

		hashMap.put("Prazo Vencido", qtdPrazoVencido);

		return listSolicitacaoServicoDto;
	}

	/*
	 * Mário Júnior - 29/10/2013 - 17:00 Modificado para atender o resumo de solicitações
	 */
	public Collection<TipoDemandaServicoDTO> resumoTipoDemandaServico(List<TarefaFluxoDTO> listTarefas) throws Exception {
		Collection<TipoDemandaServicoDTO> resumoTipoDemandaServico = new ArrayList<TipoDemandaServicoDTO>();
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		Integer qtdeItens = 0;
		Collection<TipoDemandaServicoDTO> colTipoDemandaServicos = tipoDemandaServicoService.listSolicitacoes();

		ArrayList<TarefaFluxoDTO> listaTarefasAtivo = new ArrayList<TarefaFluxoDTO>();
		if (listTarefas != null) {
			for (TipoDemandaServicoDTO tipoDemandaServicoDTO : colTipoDemandaServicos) {
				TipoDemandaServicoDTO dto = new TipoDemandaServicoDTO();
				for (TarefaFluxoDTO tarefasStr : listTarefas) {
				SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) tarefasStr.getSolicitacaoDto();
					if (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("EmAndamento") || solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Resolvida")|| solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Reaberta")
						|| solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Suspensa")|| solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Cancelada")) {
						if (solicitacaoServicoDTO.getIdTipoDemandaServico().intValue() == tipoDemandaServicoDTO.getIdTipoDemandaServico().intValue()) {
						qtdeItens++;
						}
					}
				}
				dto.setNomeTipoDemandaServico(tipoDemandaServicoDTO.getNomeTipoDemandaServico());
				dto.setQuantidade(qtdeItens);
				resumoTipoDemandaServico.add(dto);
				qtdeItens = 0 ;
			}
		}
		return resumoTipoDemandaServico;
	}

	/*
	 * Mário Júnior - 29/10/2013 - 17:00 Modificado para atender o resumo de solicitações
	 */
	public Collection<PrioridadeDTO> resumoPrioridade(List<TarefaFluxoDTO> listTarefas) throws Exception {
		Collection<PrioridadeDTO> colPrioridade = new ArrayList<PrioridadeDTO>();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		Integer qtdeItens = 0;

		Collection<PrioridadeDTO> colPrioridadeService = prioridadeService.list();

		ArrayList<TarefaFluxoDTO> listaTarefasAtivo = new ArrayList<TarefaFluxoDTO>();
		if (listTarefas != null) {
			for (PrioridadeDTO prioridadeDTO : colPrioridadeService) {
				PrioridadeDTO dto = new PrioridadeDTO();
				for (TarefaFluxoDTO tarefasStr : listTarefas) {
				SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) tarefasStr.getSolicitacaoDto();
				if (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Reaberta") || solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("EmAndamento") || solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Resolvida")
						|| (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Suspensa")||solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Cancelada"))) {
						if (prioridadeDTO.getIdPrioridade().intValue() == solicitacaoServicoDTO.getIdPrioridade().intValue()) {
							qtdeItens++;
						}
					}
				}
				dto.setNomePrioridade(prioridadeDTO.getNomePrioridade());
				dto.setQuantidade(qtdeItens);
				colPrioridade.add(dto);
				qtdeItens = 0;
			}
		}
		return colPrioridade;
	}

	public HashMap resumoPorPrazoLimite(Collection<TarefaFluxoDTO> listTarefas) throws Exception {
		Integer qtdPrazoVencido = 0;
		Integer qtdPrazoAVencer = 0;
		Integer qtdPrazoNormal = 0;
		HashMap hashMap = new HashMap();
		Collection<SolicitacaoServicoDTO> colSolicitacaoServico = new ArrayList<SolicitacaoServicoDTO>();
		// SolicitacaoServicoDTO solicitacaoServicoDTO = new SolicitacaoServicoDTO();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		if (listTarefas != null) {
			for (TarefaFluxoDTO tarefasStr : listTarefas) {
				SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) tarefasStr.getSolicitacaoDto();

				solicitacaoServicoDTO.setDataHoraLimiteStr(solicitacaoServicoDTO.getDataHoraLimiteStr());
				solicitacaoServicoDTO.setDataHoraInicioSLA(solicitacaoServicoDTO.getDataHoraInicioSLA());

				solicitacaoServicoDTO.setNomeServico(solicitacaoServicoDTO.getServico());
				if (solicitacaoServicoDTO.getNomeUnidadeSolicitante() != null && !solicitacaoServicoDTO.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
					solicitacaoServicoDTO.setSolicitanteUnidade(solicitacaoServicoDTO.getSolicitante() + " (" + solicitacaoServicoDTO.getNomeUnidadeSolicitante() + ")");

				if (solicitacaoServicoDTO.getNomeUnidadeResponsavel() != null && !solicitacaoServicoDTO.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
					solicitacaoServicoDTO.setResponsavel(solicitacaoServicoDTO.getResponsavel() + " (" + solicitacaoServicoDTO.getNomeUnidadeResponsavel() + ")");

				if (solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Reaberta") || solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("EmAndamento") || solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Resolvida")
						|| solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Suspensa")||solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Cancelada")) {
					if (solicitacaoServicoDTO.getAtrasoSLA() > 0 && !solicitacaoServicoDTO.getSituacao().equalsIgnoreCase("Cancelada")) {
						if (solicitacaoServicoDTO.getDataHoraLimite() != null) {
							Timestamp dataHoraLimite = solicitacaoServicoDTO.getDataHoraLimite();
							Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
							if (solicitacaoServicoDTO.encerrada())
								dataHoraComparacao = solicitacaoServicoDTO.getDataHoraFim();
							if (dataHoraComparacao != null) {
								if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
									qtdPrazoVencido++;
								}
							}
						}
					} else {
						if (solicitacaoServicoDTO.getFalta1Hora() == true) {
							qtdPrazoAVencer++;
						} else {
							qtdPrazoNormal++;
						}
					}
				}
			}
		}

		hashMap.put("Prazo Normal", qtdPrazoNormal);
		hashMap.put("Prazo a Vencer", qtdPrazoAVencer);
		hashMap.put("Prazo Vencido", qtdPrazoVencido);

		/*
		 * solicitacaoServicoDTO.setPrazo("Prazo Vencido"); solicitacaoServicoDTO.setPrazo("Prazo a Vencer"); solicitacaoServicoDTO.setPrazo("Prazo Normal");
		 * solicitacaoServicoDTO.setQuantidade(qtdPrazoVencido); solicitacaoServicoDTO.setQuantidade(qtdPrazoAVencer); solicitacaoServicoDTO.setQuantidade(qtdPrazoNormal);
		 */

		return hashMap;
	}

	@Override
	public Collection<RelatorioQuantitativoRetornoDTO> listaServicosRetorno(SolicitacaoServicoDTO solicitacaoServicoDTO, String grupoRetorno) throws Exception {
		return getSolicitacaoServicoDao().listaServicosRetorno(solicitacaoServicoDTO, grupoRetorno);
	}

	@Override
	public Collection<RelatorioQuantitativoRetornoDTO> listaServicosRetornoNomeResponsavel(RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception {
		return getSolicitacaoServicoDao().listaServicosRetornoNomeResponsavel(relatorioQuantitativoRetornoDTO);
	}

	@Override
	public SolicitacaoServicoDTO listaIdItemTrabalho(Integer idInstancia) throws Exception {
		return getSolicitacaoServicoDao().listaIdItemTrabalho(idInstancia);
	}

	@Override
	public RelatorioQuantitativoRetornoDTO servicoRetorno(RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception {
		return getSolicitacaoServicoDao().servicoRetorno(relatorioQuantitativoRetornoDTO);
	}

	@Override
	public boolean validaQuantidadeRetorno(RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception {
		return getSolicitacaoServicoDao().validaQuantidadeRetorno(relatorioQuantitativoRetornoDTO);
	}

	@Override
	public RelatorioQuantitativoRetornoDTO retornarIdEncerramento(String encerramento, RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception {
		return getSolicitacaoServicoDao().retornarIdEncerramento(encerramento, relatorioQuantitativoRetornoDTO);
	}

	public boolean confirmaEncerramento(RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO, Integer idElemento) throws Exception {
		return getSolicitacaoServicoDao().confirmaEncerramento(relatorioQuantitativoRetornoDTO, idElemento);
	}

	public Collection<SolicitacaoServicoDTO> findByCodigoExterno(String codigoExterno) throws Exception {
		return getSolicitacaoServicoDao().findByCodigoExterno(codigoExterno);
	}

	public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefas, Integer qtdAtual, Integer qtdAPaginacao,TransactionControler tc) throws Exception {
		if (tc != null)
			this.getDao().setTransactionControler(tc);
		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoDto = new ArrayList();

		listSolicitacaoServicoDto = this.getDao().listByTarefas(listTarefas, qtdAtual, qtdAPaginacao);

		if (listSolicitacaoServicoDto != null && !listSolicitacaoServicoDto.isEmpty()) {

			for (SolicitacaoServicoDTO solicitacaoDto : listSolicitacaoServicoDto) {

				if (solicitacaoDto != null) {
					solicitacaoDto.setDataHoraLimiteStr(solicitacaoDto.getDataHoraLimiteStr());
					solicitacaoDto.setDataHoraInicioSLA(solicitacaoDto.getDataHoraInicioSLA());

					solicitacaoDto.setNomeServico(solicitacaoDto.getServico());
					if (solicitacaoDto.getNomeUnidadeSolicitante() != null && !solicitacaoDto.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
						solicitacaoDto.setSolicitanteUnidade(solicitacaoDto.getSolicitante() + " (" + solicitacaoDto.getNomeUnidadeSolicitante() + ")");

					if (solicitacaoDto.getNomeUnidadeResponsavel() != null && !solicitacaoDto.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
						solicitacaoDto.setResponsavel(solicitacaoDto.getResponsavel() + " (" + solicitacaoDto.getNomeUnidadeResponsavel() + ")");

					solicitacaoDto = this.verificaSituacaoSLA(solicitacaoDto, tc);
				}
			}
		}
		return listSolicitacaoServicoDto;
	}

	public boolean existeSolicitacaoServico(SolicitacaoServicoDTO solicitacaoservico) throws Exception {
		return this.getDao().existeSolicitacaoServico(solicitacaoservico);
	}

	/**
	 * Retorna a Lista de TarefaDTO com SolicitacaoServidoDTO de acordo com o Login, Lista de Contratos do Usuário Logado e os Filtros Selecionados na Tela de Gerenciamento de Serviços.
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
	public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefas, Integer qtdAtual, Integer qtdAPaginacao, GerenciamentoServicosDTO gerenciamentoBean,
			Collection<ContratoDTO> listContratoUsuarioLogado, TransactionControler tc) throws Exception {

		if (tc != null)
			this.getDao().setTransactionControler(tc);

		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoDto = this.getDao().listByTarefas(listTarefas, qtdAtual, qtdAPaginacao, gerenciamentoBean, listContratoUsuarioLogado);

		if (listSolicitacaoServicoDto != null && !listSolicitacaoServicoDto.isEmpty()) {

			for (SolicitacaoServicoDTO solicitacaoDto : listSolicitacaoServicoDto) {

				if (solicitacaoDto != null) {
					solicitacaoDto.setDataHoraLimiteStr(solicitacaoDto.getDataHoraLimiteStr());
					solicitacaoDto.setDataHoraInicioSLA(solicitacaoDto.getDataHoraInicioSLA());

					solicitacaoDto.setNomeServico(solicitacaoDto.getServico());
					if (solicitacaoDto.getNomeUnidadeSolicitante() != null && !solicitacaoDto.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
						solicitacaoDto.setSolicitanteUnidade(solicitacaoDto.getSolicitante() + " (" + solicitacaoDto.getNomeUnidadeSolicitante() + ")");

					if (solicitacaoDto.getNomeUnidadeResponsavel() != null && !solicitacaoDto.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
						solicitacaoDto.setResponsavel(solicitacaoDto.getResponsavel() + " (" + solicitacaoDto.getNomeUnidadeResponsavel() + ")");

					solicitacaoDto = this.verificaSituacaoSLA(solicitacaoDto, tc);
				}
			}
		}

		return listSolicitacaoServicoDto;
	}

	/**
	 * Utilizado para a RENDERIZAÇÃO do GRÁFICO, pois no Gráfico não é necessário a utilização de Paginação. Esta consulta considera o Login do Usuário Logado, os Contratos em que está inserido e os
	 * Filtros Selecionados na tela de Gerenciamento de Serviços.
	 *
	 * @param listTarefas
	 * @param gerenciamentoBean
	 * @return
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 05.11.2013
	 */
	public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefas, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado, TransactionControler tc)
			throws Exception {

		if (tc != null)
			this.getDao().setTransactionControler(tc);

		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoDto = new ArrayList();

		listSolicitacaoServicoDto = this.getDao().listByTarefas(listTarefas, gerenciamentoBean, listContratoUsuarioLogado);

		if (listSolicitacaoServicoDto != null && !listSolicitacaoServicoDto.isEmpty()) {

			for (SolicitacaoServicoDTO solicitacaoDto : listSolicitacaoServicoDto) {

				if (solicitacaoDto != null) {
					solicitacaoDto.setDataHoraLimiteStr(solicitacaoDto.getDataHoraLimiteStr());
					solicitacaoDto.setDataHoraInicioSLA(solicitacaoDto.getDataHoraInicioSLA());

					solicitacaoDto.setNomeServico(solicitacaoDto.getServico());
					if (solicitacaoDto.getNomeUnidadeSolicitante() != null && !solicitacaoDto.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
						solicitacaoDto.setSolicitanteUnidade(solicitacaoDto.getSolicitante() + " (" + solicitacaoDto.getNomeUnidadeSolicitante() + ")");

					if (solicitacaoDto.getNomeUnidadeResponsavel() != null && !solicitacaoDto.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
						solicitacaoDto.setResponsavel(solicitacaoDto.getResponsavel() + " (" + solicitacaoDto.getNomeUnidadeResponsavel() + ")");

					solicitacaoDto = this.verificaSituacaoSLA(solicitacaoDto, tc);
				}
			}
		}
		return listSolicitacaoServicoDto;
	}

	public SolicitacaoServicoDTO restoreInfoEmails(Integer idSolicitacaoServico, TransactionControler tc) throws Exception{
		if (tc != null) {
			this.getDao().setTransactionControler(tc);
		}
		SolicitacaoServicoDTO solicitacaoDto = null;
		try {
			solicitacaoDto = (SolicitacaoServicoDTO) this.getDao().restoreInfoEmails(idSolicitacaoServico);
		} catch (Exception e) {
			throw new Exception(i18nMessage("solicitacaoservico.erro.recuperardadosolicitacao") + " " + idSolicitacaoServico);
		}

		return solicitacaoDto;
	}

	public Collection<SolicitacaoServicoDTO> listarSolicitacoesAbertasEmAndamentoPorGrupo(int idGrupoAtual, String situacaoSla) throws Exception {
		 return this.getDao().listarSolicitacoesAbertasEmAndamentoPorGrupo(idGrupoAtual, situacaoSla);
	}

	public Collection<SolicitacaoServicoDTO> listarSolicitacoesMultadasSuspensasPorGrupo(int idGrupoAtual, String situacaoSla) throws Exception {
		 return this.getDao().listarSolicitacoesMultadasSuspensasPorGrupo(idGrupoAtual, situacaoSla);
	}

	public Collection<SolicitacaoServicoDTO> listaServicosPorResponsavelNoPeriodo(Date dataIncio, Date dataFim, int idFuncionario , boolean mostrarIncidentes, boolean mostrarRequisicoes,String situacao) throws Exception {
		 return this.getDao().listaServicosPorResponsavelNoPeriodo(dataIncio, dataFim, idFuncionario, mostrarIncidentes, mostrarRequisicoes, situacao);
	}

/*
	@Override
	public Collection<SolicitacaoServicoDTO> findSolicitacoesNaoResolvidasNoPrazoKPI(
			RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDTO)
			throws Exception {
		SolicitacaoServicoDao dao = new SolicitacaoServicoDao();
		 return this.getDao().findSolicitacoesNaoResolvidasNoPrazoKPI(relatorioIncidentesNaoResolvidosDTO);
	}
*/

	public Collection<SolicitacaoServicoDTO> listaServicosPorSolicitanteNoPeriodoEnviadosAoteste(Date dataIncio, Date dataFim, int idFuncionario , boolean mostrarIncidentes, boolean mostrarRequisicoes) throws Exception {
		 return this.getDao().listaServicosPorSolicitanteNoPeriodoEnviadosAoteste(dataIncio, dataFim, idFuncionario , mostrarIncidentes, mostrarRequisicoes);
	}

	public Collection<SolicitacaoServicoDTO> listaServicosPorAbertosParaDocumentacao(Date dataIncio, Date dataFim, boolean mostrarIncidentes, boolean mostrarRequisicoes) throws Exception {
		 return this.getDao().listaServicosPorAbertosParaDocumentacao(dataIncio, dataFim, mostrarIncidentes, mostrarRequisicoes);
	}

	public Collection<SolicitacaoServicoDTO> listaServicosPorResponsavelNoPeriodoDocumentacao(Date dataIncio, Date dataFim, int idFuncionario , boolean mostrarIncidentes, boolean mostrarRequisicoes) throws Exception {
		 return this.getDao().listaServicosPorResponsavelNoPeriodoDocumentacao(dataIncio, dataFim, idFuncionario , mostrarIncidentes, mostrarRequisicoes);
	}

	public Collection<SolicitacaoServicoDTO> listaServicosPorResponsavelNoPeriodoDocumentacaoPorServico(Date dataIncio, Date dataFim, int idFuncionario , boolean mostrarIncidentes, boolean mostrarRequisicoes, String listaIdsServicosHomologacaoDocumentacao) throws Exception {
		 return this.getDao().listaServicosPorResponsavelNoPeriodoDocumentacaoPorServico(dataIncio, dataFim, idFuncionario , mostrarIncidentes, mostrarRequisicoes, listaIdsServicosHomologacaoDocumentacao);
	}

	public Collection<SolicitacaoServicoDTO> listaServicosPorAbertosPelotesteParaValidacao(Date dataIncio, Date dataFim, boolean mostrarIncidentes, boolean mostrarRequisicoes) throws Exception {
		 return this.getDao().listaServicosPorAbertosPelotesteParaValidacao(dataIncio, dataFim, mostrarIncidentes, mostrarRequisicoes);
	}

	public void verificaSituacaoSLA(List<SolicitacaoServicoDTO> solicitacaoDto) throws Exception {
		for (SolicitacaoServicoDTO solicitacaoServicoDTO : solicitacaoDto) {
			verificaSituacaoSLA(solicitacaoServicoDTO);
		}

	}

	public SolicitacaoServicoDTO buscarNumeroItemTrabalhoPorNumeroSolicitacao(int idSolicitacao) throws Exception {
		 return  this.getDao().buscarNumeroItemTrabalhoPorNumeroSolicitacao(idSolicitacao);
	}


	public Collection<RelatorioEficaciaTesteDTO> listaSolicitacaoPorServicosAbertosNoPerido(Date dataIncio, Date dataFim,List<ServicoDTO> listaServicos) throws Exception {
		 return this.getDao().listaSolicitacaoPorServicosAbertosNoPerido(dataIncio, dataFim, listaServicos);
	}

	public Collection<RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO> listaQtdSolicitacoesCanceladasFinalizadasporServicoNoPeriodo(Date dataIncio, Date dataFim,List<ServicoDTO> listaServicos) throws Exception {
		 return this.getDao().listaQtdSolicitacoesCanceladasFinalizadasporServicoNoPeriodo(dataIncio, dataFim, listaServicos);
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findSolicitacoesNaoResolvidasNoPrazoKPI(RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDTO)throws Exception {
		 return this.getDao().findSolicitacoesNaoResolvidasNoPrazoKPI(relatorioIncidentesNaoResolvidosDTO);
	}

	@Override
	public Collection<SolicitacaoServicoDTO> findSolicitacoesNaoResolvidasEntrePrazoKPI(RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDTO)throws Exception {
		 return this.getDao().findSolicitacoesNaoResolvidasEntrePrazoKPI(relatorioIncidentesNaoResolvidosDTO);
	}

	@Override
	public Collection<RelatorioKpiProdutividadeDTO> listaQuantitativaEmpregadoSolicitacoesEmcaminhaExito(RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDto) throws Exception {

		Collection<RelatorioKpiProdutividadeDTO> listaQuantitativaEmpregadoSolicitacoesEmcaminhaExito = new ArrayList();

		listaQuantitativaEmpregadoSolicitacoesEmcaminhaExito = this.getDao().listaQuantitativaEmpregadoSolicitacoesEmcaminhaExito(relatorioKpiProdutividadeDto);

		if(listaQuantitativaEmpregadoSolicitacoesEmcaminhaExito!=null){

			for(RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDTO : listaQuantitativaEmpregadoSolicitacoesEmcaminhaExito){
				if(relatorioKpiProdutividadeDTO.getQtdeencaminhadas() > 0 ){
					Double porcentagemExecutadaExito ;
					porcentagemExecutadaExito = 100 *  ( relatorioKpiProdutividadeDTO.getQtdeexito().doubleValue() / relatorioKpiProdutividadeDTO.getQtdeencaminhadas().doubleValue());
					relatorioKpiProdutividadeDTO.setPorcentagemExecutadaExito(porcentagemExecutadaExito);

				}else{
					relatorioKpiProdutividadeDTO.setQtdeExecutada(0);

				}
			}

			return listaQuantitativaEmpregadoSolicitacoesEmcaminhaExito;
		}

		return null;
	}

	public Collection<SolicitacaoServicoDTO> listSolicitacoesFilhasFiltradas(GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado, TransactionControler tc) throws Exception {
		SolicitacaoServicoDao solicitacaoServicoDao = (SolicitacaoServicoDao) this.getDao();
		if (tc != null)
			solicitacaoServicoDao.setTransactionControler(tc);
		return solicitacaoServicoDao.listSolicitacoesFilhasFiltradas(gerenciamentoBean, listContratoUsuarioLogado);
	}

	public boolean verificaPermGestorSolicitanteRH(Integer idSolicitante) throws PersistenceException {
		return this.getDao().verificaPermGestorSolicitanteRH(idSolicitante);
    }

	@Override
	public Collection<RelatorioCausaSolucaoDTO> listaCausaSolicitacao(RelatorioCausaSolucaoDTO relatorioCausaSolicitacao) throws Exception {
		return getSolicitacaoServicoDao().listaCausaSolicitacao(relatorioCausaSolicitacao);
	}

	@Override
	public Collection<RelatorioCausaSolucaoDTO> listaSolucaoSolicitacao(RelatorioCausaSolucaoDTO relatorioCausaSolicitacao) throws Exception {
		return getSolicitacaoServicoDao().listaSolucaoSolicitacao(relatorioCausaSolicitacao);
	}

	@Override
	public Collection<RelatorioCausaSolucaoDTO> listaCausaSolucaoAnalitico(RelatorioCausaSolucaoDTO relatorioCausaSolicitacao) throws Exception {
		return getSolicitacaoServicoDao().listaCausaSolucaoAnalitico(relatorioCausaSolicitacao);
	}

	@Override
	public Collection<SolicitacaoServicoDTO> listSolicitacoesFilhasFiltradas(
			GerenciamentoServicosDTO gerenciamentoBean,
			Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception {
		// TODO Auto-generated method stub
		return listSolicitacoesFilhasFiltradas(gerenciamentoBean,listContratoUsuarioLogado, null);
	}

	@Override
	public Collection<SolicitacaoServicoDTO> listSolicitacoesFilhas() throws Exception {
		return listSolicitacoesFilhas(null);
	}

	@Override
	public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefas, TipoSolicitacaoServico[] tiposSolicitacao) throws Exception {
		return listByTarefas(listTarefas, tiposSolicitacao, null);
	}

	public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefas, Integer qtdAtual, Integer qtdAPaginacao, GerenciamentoServicosDTO gerenciamentoBean,
			Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception {
		return listByTarefas(listTarefas, qtdAtual, qtdAPaginacao, gerenciamentoBean, listContratoUsuarioLogado, null);
	}

	public void determinaPrazoLimite(SolicitacaoServicoDTO solicitacaoDto, Integer idCalendario, TransactionControler tc) throws Exception {
		if (solicitacaoDto.getDataHoraInicioSLA() == null)
			return;

		Timestamp tsAtual = UtilDatas.getDataHoraAtual();

		if (idCalendario == null || idCalendario.intValue() == 0) {
			ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
			if (tc != null)
				servicoContratoDao.setTransactionControler(tc);
			ServicoContratoDTO servicoContratoDto = servicoContratoDao.findByIdContratoAndIdServico(solicitacaoDto.getIdContrato(), solicitacaoDto.getIdServico());
			if (servicoContratoDto == null)
				throw new LogicException(i18nMessage("solicitacaoservico.validacao.servicolocalizado"));
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
			calculoDto = new CalendarioServiceEjb().calculaDataHoraFinal(calculoDto, true, tc);
		} else {
			if (solicitacaoDto.getDataHoraReativacaoSLA() != null && solicitacaoDto.getTempoDecorridoHH() == 0 && solicitacaoDto.getTempoDecorridoMM() == 0) {
				calculoDto = new CalculoJornadaDTO(idCalendario, solicitacaoDto.getDataHoraReativacaoSLA(), solicitacaoDto.getPrazoHH(), solicitacaoDto.getPrazoMM());
			} else {
				calculoDto = new CalculoJornadaDTO(idCalendario, solicitacaoDto.getDataHoraInicioSLA(), solicitacaoDto.getPrazoHH(), solicitacaoDto.getPrazoMM());
			}
			calculoDto = new CalendarioServiceEjb().calculaDataHoraFinal(calculoDto, true, tc);
		}

		solicitacaoDto.setDataHoraLimite(calculoDto.getDataHoraFinal());
	}

	@Override
	public Integer numeroSolicitacoesForaPeriodo(RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDTO) throws PersistenceException, ServiceException {
		return getSolicitacaoServicoDao().numeroSolicitacoesForaPeriodo(relatorioIncidentesNaoResolvidosDTO);
	}

	/**
	 * Novos métodos para paginação
	 * @author thyen.chang
	 *
	 * @param pesquisaSolicitacaoServicoDto
	 * @return
	 * @throws Exception
	 */
	@Override
	public Long listaRelatorioGetQuantidadeRegistros(PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto) throws Exception {
		return getSolicitacaoServicoDao().listaRelatorioGetQuantidadeRegistros(pesquisaSolicitacaoServicoDto);
	}

	@Override
	public List<SolicitacaoServicoDTO> listRelatorioGetListaPaginada(PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDTO, Integer paginaAtual, Integer quantidadePorPagina)
			throws Exception {
		return getSolicitacaoServicoDao().listRelatorioGetListaPaginada(pesquisaSolicitacaoServicoDTO, paginaAtual, quantidadePorPagina);
	}

}