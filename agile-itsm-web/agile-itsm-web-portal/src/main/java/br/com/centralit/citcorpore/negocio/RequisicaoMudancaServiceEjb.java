/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.htmlparser.jericho.Source;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.citcorpore.ajaxForms.RequisicaoMudanca;
import br.com.centralit.citcorpore.bean.AprovacaoMudancaDTO;
import br.com.centralit.citcorpore.bean.AprovacaoPropostaDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CalculoJornadaDTO;
import br.com.centralit.citcorpore.bean.ContatoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.HistoricoGEDDTO;
import br.com.centralit.citcorpore.bean.HistoricoMudancaDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.JustificativaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.LiberacaoMudancaDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoGrupoDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoProblemaDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoResponsavelDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoRiscosDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoMudancaHistoricoServicoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaMudancaDTO;
import br.com.centralit.citcorpore.bean.PesquisaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaResponsavelDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaRiscoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaServicoDTO;
import br.com.centralit.citcorpore.bean.ReuniaoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoMudancaDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AprovacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.AprovacaoPropostaDao;
import br.com.centralit.citcorpore.integracao.ContatoRequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.GrupoRequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.HistoricoGEDDao;
import br.com.centralit.citcorpore.integracao.HistoricoMudancaDao;
import br.com.centralit.citcorpore.integracao.LiberacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoLiberacaoRiscosDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoMudancaGrupoDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoMudancaItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoMudancaProblemaDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoMudancaResponsavelDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoMudancaServicoDao;
import br.com.centralit.citcorpore.integracao.OcorrenciaMudancaDao;
import br.com.centralit.citcorpore.integracao.PermissoesFluxoDao;
import br.com.centralit.citcorpore.integracao.ProblemaMudancaDAO;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaResponsavelDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaRiscoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaServicoDao;
import br.com.centralit.citcorpore.integracao.ReuniaoRequisicaoMudancaDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoMudancaDao;
import br.com.centralit.citcorpore.integracao.TemplateSolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.TipoMudancaDAO;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoMudanca;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;

@SuppressWarnings({"rawtypes", "unchecked"})
public class RequisicaoMudancaServiceEjb extends CrudServiceImpl implements RequisicaoMudancaService {

    private RequisicaoMudancaDao dao;

    private ItemConfiguracaoService itemConfiguracaoService;

    @Override
    public RequisicaoMudancaDao getDao() {
        if (dao == null) {
            dao = new RequisicaoMudancaDao();
        }
        return dao;
    }

    @Override
    public void updateNotNull(final IDto obj) throws Exception {
        this.getDao().updateNotNull(obj);
    }

    /*
     * (non-Javadoc)
     * @see br.com.citframework.service.CrudServicePojoImpl#create(br.com.citframework.dto.IDto)
     */
    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final ExecucaoMudancaServiceEjb execucaoMudancaService = new ExecucaoMudancaServiceEjb();
        final SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();
        final TipoMudancaDAO tipoMudancaDAO = new TipoMudancaDAO();
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();

        ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDto = new ContatoRequisicaoMudancaDTO();

        final AprovacaoMudancaDao aprovacaoMudancaDao = new AprovacaoMudancaDao();
        final AprovacaoPropostaDao aprovacaoPropostaDao = new AprovacaoPropostaDao();
        final RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaItemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao();
        final ProblemaMudancaDAO problemaMudancaDao = new ProblemaMudancaDAO();
        final RequisicaoMudancaRiscoDao requisicaoMudancaRiscoDao = new RequisicaoMudancaRiscoDao();
        final LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();
        final RequisicaoMudancaServicoDao requisicaoMudancaServicoDao = new RequisicaoMudancaServicoDao();
        final RequisicaoMudancaResponsavelDao requisicaoMudancaResponsavelDao = new RequisicaoMudancaResponsavelDao();
        final GrupoRequisicaoMudancaDao grupoRequisicaoMudancaDao = new GrupoRequisicaoMudancaDao();

        TipoMudancaDTO tipoMudancaDTO = new TipoMudancaDTO();
        RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) model;

        final TransactionControler tc = new TransactionControlerImpl(requisicaoMudancaDao.getAliasDB());

        try {
            tc.start();

            this.validaCreate(model);

            tipoMudancaDAO.setTransactionControler(tc);
            requisicaoMudancaDao.setTransactionControler(tc);
            solicitacaoServicoMudancaDao.setTransactionControler(tc);
            aprovacaoMudancaDao.setTransactionControler(tc);
            aprovacaoPropostaDao.setTransactionControler(tc);
            requisicaoMudancaItemConfiguracaoDao.setTransactionControler(tc);
            problemaMudancaDao.setTransactionControler(tc);
            requisicaoMudancaRiscoDao.setTransactionControler(tc);
            liberacaoMudancaDao.setTransactionControler(tc);
            requisicaoMudancaServicoDao.setTransactionControler(tc);
            requisicaoMudancaResponsavelDao.setTransactionControler(tc);
            grupoRequisicaoMudancaDao.setTransactionControler(tc);

            if (usuario == null) {
                usuario = new Usuario();
            }

            if (usuario != null && requisicaoMudancaDto != null) {
                usuario.setLocale(requisicaoMudancaDto.getUsuarioDto().getLocale());
            }

            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null
                    && requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null) {
                final boolean resultado = this.seHoraFinalMenorQHoraInicial(requisicaoMudancaDto);
                if (resultado == true) {
                    throw new LogicException(this.i18nMessage("requisicaoMudanca.horaFinalMenorQueInicial"));
                }
            }

            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null) {
                final boolean resultado = this.seHoraInicialMenorQAtual(requisicaoMudancaDto);
                if (resultado == true) {
                    throw new LogicException(this.i18nMessage("requisicaoMudanca.horaInicialMenorQueAtual"));
                }
            }

            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoFinal() != null) {
                final boolean resultado = this.seHoraFinalMenorQAtual(requisicaoMudancaDto);
                if (resultado == true) {
                    throw new LogicException(this.i18nMessage("requisicaoMudanca.horaFinalMenorQueAtual"));
                }
            }

            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null) {
                final Timestamp dataHoraInicial = this.MontardataHoraAgendamentoInicial(requisicaoMudancaDto);
                requisicaoMudancaDto.setDataHoraInicioAgendada(dataHoraInicial);
            }

            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoFinal() != null) {
                final Timestamp dataHoraFinal = this.MontardataHoraAgendamentoFinal(requisicaoMudancaDto);
                requisicaoMudancaDto.setDataHoraTerminoAgendada(dataHoraFinal);
                requisicaoMudancaDto.setDataHoraTermino(dataHoraFinal);
            }

            final boolean resultado = this.validacaoGrupoExecutor(requisicaoMudancaDto);
            if (resultado == false) {
                throw new LogicException(this.i18nMessage("requisicaoMudanca.grupoSemPermissao"));
            }

            if (requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null) {

                this.calculaTempoAtraso(requisicaoMudancaDto);

            } else {
                requisicaoMudancaDto.setPrazoHH(00);
                requisicaoMudancaDto.setPrazoMM(00);
            }

            if (requisicaoMudancaDto.getUsuarioDto() == null) {
                throw new LogicException(this.i18nMessage("citcorpore.comum.usuarioNaoidentificado"));
            }

            if (requisicaoMudancaDto.getIdTipoMudanca() != null) {

                tipoMudancaDTO.setIdTipoMudanca(requisicaoMudancaDto.getIdTipoMudanca());
                tipoMudancaDTO = (TipoMudancaDTO) tipoMudancaDAO.restore(tipoMudancaDTO);
            }

            if (tipoMudancaDTO.getIdGrupoExecutor() == null) {

                throw new LogicException(this.i18nMessage("citcorpore.comum.grupoExecutorNaoDefinido"));
            }

            if (tipoMudancaDTO.getIdCalendario() == null) {

                throw new LogicException(this.i18nMessage("citcorpore.comum.calendarioNaoDefinido"));
            }

            if (requisicaoMudancaDto.getEhPropostaAux().equalsIgnoreCase("S")) {
                requisicaoMudancaDto.setStatus(SituacaoRequisicaoMudanca.Proposta.name());
            } else {
                requisicaoMudancaDto.setStatus(SituacaoRequisicaoMudanca.Registrada.name());
            }

            if (requisicaoMudancaDto.getIdGrupoAtual() == null) {
                requisicaoMudancaDto.setIdGrupoAtual(tipoMudancaDTO.getIdGrupoExecutor());
            }
            requisicaoMudancaDto.setIdGrupoNivel1(requisicaoMudancaDto.getIdGrupoAtual());
            requisicaoMudancaDto.setIdCalendario(tipoMudancaDTO.getIdCalendario());
            requisicaoMudancaDto.setTempoDecorridoHH(new Integer(0));
            requisicaoMudancaDto.setTempoDecorridoMM(new Integer(0));
            requisicaoMudancaDto.setDataHoraSuspensao(null);
            requisicaoMudancaDto.setDataHoraReativacao(null);
            requisicaoMudancaDto.setSeqReabertura(new Integer(0));
            requisicaoMudancaDto.setDataHoraSolicitacao(new Timestamp(new java.util.Date().getTime()));
            requisicaoMudancaDto.setIdProprietario(requisicaoMudancaDto.getUsuarioDto().getIdEmpregado());

            requisicaoMudancaDto.setDataHoraInicio(new Timestamp(new java.util.Date().getTime()));

            requisicaoMudancaDto.setDataHoraCaptura(requisicaoMudancaDto.getDataHoraInicio());

            contatoRequisicaoMudancaDto = this.criarContatoRequisicaoMudanca(requisicaoMudancaDto, tc);

            requisicaoMudancaDto.setIdContatoRequisicaoMudanca(contatoRequisicaoMudancaDto.getIdContatoRequisicaoMudanca());

            if (contatoRequisicaoMudancaDto.getIdLocalidade() != null) {
                requisicaoMudancaDto.setIdLocalidade(contatoRequisicaoMudancaDto.getIdLocalidade());
            }

            final String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
            if (remetente == null) {
                throw new LogicException(this.i18nMessage("citcorpore.comum.notficacaoEmailParametrizado"));
            }

            requisicaoMudancaDto = (RequisicaoMudancaDTO) requisicaoMudancaDao.create(requisicaoMudancaDto);

            this.criarOcorrenciaMudanca(requisicaoMudancaDto, tc);

            Source source = null;
            if (requisicaoMudancaDto != null) {
                source = new Source(requisicaoMudancaDto.getRegistroexecucao());
                requisicaoMudancaDto.setRegistroexecucao(source.getTextExtractor().toString());
            }

            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getRegistroexecucao() != null && !requisicaoMudancaDto.getRegistroexecucao().trim().equalsIgnoreCase("")) {
                final OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
                ocorrenciaMudancaDao.setTransactionControler(tc);
                final OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();
                ocorrenciaMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                ocorrenciaMudancaDto.setDataregistro(UtilDatas.getDataAtual());
                ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
                ocorrenciaMudancaDto.setTempoGasto(0);
                ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
                ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
                ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
                ocorrenciaMudancaDto.setInformacoesContato("não se aplica");
                ocorrenciaMudancaDto.setRegistradopor(requisicaoMudancaDto.getUsuarioDto().getLogin());
                try {
                    ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(requisicaoMudancaDto));
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
                ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Criacao.getSigla());
                ocorrenciaMudancaDto.setOcorrencia(requisicaoMudancaDto.getRegistroexecucao());
                ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);
            }

            if (requisicaoMudancaDto != null) {
                if (requisicaoMudancaDto.getListIdSolicitacaoServico() != null && requisicaoMudancaDto.getListIdSolicitacaoServico().size() > 0) {
                    for (final SolicitacaoServicoDTO solicitacaoServicoDTO : requisicaoMudancaDto.getListIdSolicitacaoServico()) {
                        final SolicitacaoServicoMudancaDTO solicitacaoServicoMudancaDTO = new SolicitacaoServicoMudancaDTO();
                        solicitacaoServicoMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        solicitacaoServicoMudancaDTO.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());
                        solicitacaoServicoMudancaDao.create(solicitacaoServicoMudancaDTO);
                    }
                }

                if (requisicaoMudancaDto.getListAprovacaoMudancaDTO() != null) {
                    for (final AprovacaoMudancaDTO aprovacaoMudancaDto : requisicaoMudancaDto.getListAprovacaoMudancaDTO()) {
                        aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        aprovacaoMudancaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
                        aprovacaoMudancaDao.create(aprovacaoMudancaDto);
                    }
                }

                if (requisicaoMudancaDto.getListAprovacaoPropostaDTO() != null) {
                    for (final AprovacaoPropostaDTO aprovacaoPropostaDto : requisicaoMudancaDto.getListAprovacaoPropostaDTO()) {
                        aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        aprovacaoPropostaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
                        aprovacaoPropostaDao.create(aprovacaoPropostaDto);
                    }
                }

                if (requisicaoMudancaDto.getListRequisicaoMudancaItemConfiguracaoDTO() != null) {
                    for (final RequisicaoMudancaItemConfiguracaoDTO requisicaoMudancaItemConfiguracaoDto : requisicaoMudancaDto.getListRequisicaoMudancaItemConfiguracaoDTO()) {
                        requisicaoMudancaItemConfiguracaoDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        requisicaoMudancaItemConfiguracaoDao.create(requisicaoMudancaItemConfiguracaoDto);
                    }
                }

                if (requisicaoMudancaDto.getListProblemaMudancaDTO() != null) {
                    for (final ProblemaMudancaDTO problemaMudancaDto : requisicaoMudancaDto.getListProblemaMudancaDTO()) {
                        problemaMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        problemaMudancaDao.create(problemaMudancaDto);
                    }
                }

                if (requisicaoMudancaDto.getListRequisicaoMudancaRiscoDTO() != null) {
                    for (final RequisicaoMudancaRiscoDTO RequisicaoMudancaRiscoDto : requisicaoMudancaDto.getListRequisicaoMudancaRiscoDTO()) {
                        RequisicaoMudancaRiscoDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        requisicaoMudancaRiscoDao.create(RequisicaoMudancaRiscoDto);
                    }
                }

                if (requisicaoMudancaDto.getListGrupoRequisicaoMudancaDTO() != null) {
                    for (final GrupoRequisicaoMudancaDTO grupoRequisicaoMudancaDto : requisicaoMudancaDto.getListGrupoRequisicaoMudancaDTO()) {
                        grupoRequisicaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        grupoRequisicaoMudancaDao.create(grupoRequisicaoMudancaDto);
                    }
                }

                // geber.costa
                if (requisicaoMudancaDto.getListLiberacaoMudancaDTO() != null) {
                    for (final LiberacaoMudancaDTO liberacaoMudancaDto : requisicaoMudancaDto.getListLiberacaoMudancaDTO()) {
                        liberacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        liberacaoMudancaDto.setIdLiberacao(liberacaoMudancaDto.getIdLiberacao());
                        // O Trim foi utilizado para tratamento, os campos não virem com espaço
                        if (liberacaoMudancaDto.getSituacaoLiberacao() != null) {
                            liberacaoMudancaDto.setSituacaoLiberacao(liberacaoMudancaDto.getSituacaoLiberacao().trim());
                        }
                        if (liberacaoMudancaDto.getStatus() != null) {
                            liberacaoMudancaDto.setStatus(liberacaoMudancaDto.getStatus().trim());
                        }
                        liberacaoMudancaDao.create(liberacaoMudancaDto);
                    }
                }

                if (requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null
                        && (requisicaoMudancaDto.getIdGrupoAtvPeriodica() == null || requisicaoMudancaDto.getIdGrupoAtvPeriodica() == 0)) {
                    throw new LogicException(this.i18nMessage("gerenciaservico.agendaratividade.informacoesGrupoAtividade"));
                }

                if (requisicaoMudancaDto.getListRequisicaoMudancaServicoDTO() != null) {
                    for (final RequisicaoMudancaServicoDTO requisicaoMudancaServicoDto : requisicaoMudancaDto.getListRequisicaoMudancaServicoDTO()) {
                        requisicaoMudancaServicoDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        requisicaoMudancaServicoDao.create(requisicaoMudancaServicoDto);
                    }
                }
            }

            // create Responsavel
            Collection<RequisicaoMudancaResponsavelDTO> colRequisicaoMudancaResp = null;
            if (requisicaoMudancaDto != null) {
                colRequisicaoMudancaResp = requisicaoMudancaDto.getColResponsaveis();
            }
            if (colRequisicaoMudancaResp != null) {
                for (final RequisicaoMudancaResponsavelDTO mudancaResponsavelDTO : colRequisicaoMudancaResp) {
                    mudancaResponsavelDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                    requisicaoMudancaResponsavelDao.create(mudancaResponsavelDTO);
                }
            }

            // gravar anexos de mudança
            final HistoricoMudancaDTO historicoMudancaDTO = new HistoricoMudancaDTO();
            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getColArquivosUpload() != null /* && liberacaoDto.getColArquivosUpload().size() > 0 */) {
                this.gravaInformacoesGED(requisicaoMudancaDto, tc, historicoMudancaDTO);
            }

            // gravar anexos dos planos de reversao de mudança
            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getColUploadPlanoDeReversaoGED() != null /* && liberacaoDto.getColArquivosUpload().size() > 0 */) {
                this.gravaPlanoDeReversaoGED(requisicaoMudancaDto, tc, historicoMudancaDTO);
            }

            if (requisicaoMudancaDto != null) {
                execucaoMudancaService.registraMudanca(requisicaoMudancaDto, tc, requisicaoMudancaDto.getUsuarioDto());
            }

            tc.commit();

            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null) {
                RequisicaoMudanca.salvaGrupoAtvPeriodicaEAgenda(requisicaoMudancaDto);
            }
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        } finally {
            try {
                tc.close();
            } catch (final PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return model;
    }

    public void criarOcorrenciaMudanca(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception {
        final OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
        ocorrenciaMudancaDao.setTransactionControler(tc);
        final OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();
        ocorrenciaMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
        ocorrenciaMudancaDto.setDataregistro(UtilDatas.getDataAtual());
        ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
        ocorrenciaMudancaDto.setTempoGasto(0);
        ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
        ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
        ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
        ocorrenciaMudancaDto.setInformacoesContato("não se aplica");
        ocorrenciaMudancaDto.setRegistradopor(requisicaoMudancaDto.getUsuarioDto().getLogin());
        try {
            ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(requisicaoMudancaDto));
        } catch (final Exception e) {
            System.out.println("Falha na gravação de dadosMudanca - Objeto Gson");
            e.printStackTrace();
        }
        ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
        ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Criacao.getSigla());
        ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);
    }

    /**
     * PODE SER UTILIZADO PARA SETAR OS CONTATOS DA REQUISICAO MUDANCA
     *
     */
    public ContatoRequisicaoMudancaDTO criarContatoRequisicaoMudanca(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws ServiceException,
            LogicException {

        final ContatoRequisicaoMudancaDao contatoRequisicaoMudancaDao = new ContatoRequisicaoMudancaDao();
        ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDto = new ContatoRequisicaoMudancaDTO();

        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();

        try {
            contatoRequisicaoMudancaDao.setTransactionControler(tc);
            requisicaoMudancaDao.setTransactionControler(tc);

            if (requisicaoMudancaDto.getIdContatoRequisicaoMudanca() != null) {

                contatoRequisicaoMudancaDto.setIdContatoRequisicaoMudanca(requisicaoMudancaDto.getIdContatoRequisicaoMudanca());
                contatoRequisicaoMudancaDto.setNomecontato(requisicaoMudancaDto.getNomeContato());
                contatoRequisicaoMudancaDto.setTelefonecontato(requisicaoMudancaDto.getTelefoneContato());
                contatoRequisicaoMudancaDto.setRamal(requisicaoMudancaDto.getRamal());
                if (requisicaoMudancaDto.getEmailSolicitante() != null) {
                    contatoRequisicaoMudancaDto.setEmailcontato(requisicaoMudancaDto.getEmailSolicitante().trim());
                }
                contatoRequisicaoMudancaDto.setObservacao(requisicaoMudancaDto.getObservacao());
                contatoRequisicaoMudancaDto.setIdLocalidade(requisicaoMudancaDto.getIdLocalidade());
                contatoRequisicaoMudancaDao.update(contatoRequisicaoMudancaDto);

            } else {
                contatoRequisicaoMudancaDto.setNomecontato(requisicaoMudancaDto.getNomeContato());
                contatoRequisicaoMudancaDto.setTelefonecontato(requisicaoMudancaDto.getTelefoneContato());
                contatoRequisicaoMudancaDto.setRamal(requisicaoMudancaDto.getRamal());
                if (requisicaoMudancaDto.getEmailSolicitante() != null) {
                    contatoRequisicaoMudancaDto.setEmailcontato(requisicaoMudancaDto.getEmailSolicitante().trim());
                }
                contatoRequisicaoMudancaDto.setObservacao(requisicaoMudancaDto.getObservacao());
                contatoRequisicaoMudancaDto.setIdLocalidade(requisicaoMudancaDto.getIdLocalidade());
                contatoRequisicaoMudancaDto = (ContatoRequisicaoMudancaDTO) contatoRequisicaoMudancaDao.create(contatoRequisicaoMudancaDto);
            }

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        }
        return contatoRequisicaoMudancaDto;
    }

    @SuppressWarnings("unused")
    private void determinaPrazo(final RequisicaoMudancaDTO requisicaoDto, final Integer idCalendarioParm) throws Exception {
        if (requisicaoDto.getDataHoraTerminoAgendada() == null) {
            throw new LogicException(this.i18nMessage("citcorpore.comum.Data/horaTerminoNaoDefinida"));
        }

        CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendarioParm, requisicaoDto.getDataHoraInicioAgendada());
        calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, requisicaoDto.getDataHoraTerminoAgendada(), null);
        requisicaoDto.setPrazoHH(calculoDto.getTempoDecorridoHH());
        requisicaoDto.setPrazoMM(calculoDto.getTempoDecorridoMM());
    }

    @SuppressWarnings("unused")
    private void determinaPrazo(final RequisicaoMudancaDTO requisicaoDto) throws Exception {
        if (requisicaoDto.getDataHoraTerminoAgendada() == null) {
            throw new LogicException(this.i18nMessage("citcorpore.comum.Data/horaTerminoNaoDefinida"));
        }

        TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
        final TipoMudancaDAO tipoMudancaDAO = new TipoMudancaDAO();
        tipoMudancaDto.setIdTipoMudanca(requisicaoDto.getIdTipoMudanca());
        tipoMudancaDto = (TipoMudancaDTO) tipoMudancaDAO.restore(tipoMudancaDto);

        CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(tipoMudancaDto.getIdCalendario(), requisicaoDto.getDataHoraInicioAgendada());
        calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, requisicaoDto.getDataHoraTerminoAgendada(), null);
        requisicaoDto.setPrazoHH(calculoDto.getTempoDecorridoHH());
        requisicaoDto.setPrazoMM(calculoDto.getTempoDecorridoMM());
    }

    /**
     * Lista os ics relacionados a requisição de mudança
     * e atribui o nome do item de configuração para correta
     * restauração de suas informações na table
     *
     * @param requisicaoMudancaItemConfiguracaoDTO
     * @throws ServiceException
     * @throws Exception
     */
    public ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listItensRelacionadosRequisicaoMudanca(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws ServiceException,
            Exception {
        ItemConfiguracaoDTO ic = null;
        final RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaItemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao();

        final ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listaReqMudancaIC = (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>) requisicaoMudancaItemConfiguracaoDao
                .findByIdMudancaEDataFim(requisicaoMudancaDTO.getIdRequisicaoMudanca());
        // getRequisicaoMudancaItemConfiguracaoService().listByIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());

        // atribui nome para os itens retornados
        if (listaReqMudancaIC != null) {
            for (final RequisicaoMudancaItemConfiguracaoDTO r : listaReqMudancaIC) {
                ic = this.getItemConfiguracaoService().restoreByIdItemConfiguracao(r.getIdItemConfiguracao());
                if (ic != null) {
                    r.setNomeItemConfiguracao(ic.getIdentificacao());
                }
            }
        }

        return listaReqMudancaIC;
    }

    private ItemConfiguracaoService getItemConfiguracaoService() throws ServiceException, Exception {
        if (itemConfiguracaoService == null) {
            itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
        }
        return itemConfiguracaoService;
    }

    /*
     * (non-Javadoc)
     * @see br.com.citframework.service.CrudServicePojoImpl#update(br.com.citframework.dto.IDto)
     */

    @Override
    public void update(final IDto model, final HttpServletRequest request) throws ServiceException, LogicException {
        final RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) model;
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();
        LigacaoRequisicaoMudancaHistoricoResponsavelDTO ligacaoResponsavelDTO = new LigacaoRequisicaoMudancaHistoricoResponsavelDTO();
        final LigacaoRequisicaoMudancaResponsavelDao ligacaoResponsavelDao = new LigacaoRequisicaoMudancaResponsavelDao();
        LigacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO ligacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO = new LigacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO();
        final LigacaoRequisicaoMudancaItemConfiguracaoDao ligacaoRequisicaoMudancaItemConfiguracaoDao = new LigacaoRequisicaoMudancaItemConfiguracaoDao();
        LigacaoRequisicaoMudancaHistoricoServicoDTO ligacaoRequisicaoMudancaHistoricoServicoDTO = new LigacaoRequisicaoMudancaHistoricoServicoDTO();
        final LigacaoRequisicaoMudancaServicoDao ligacaoRequisicaoMudancaServicoDao = new LigacaoRequisicaoMudancaServicoDao();
        LigacaoRequisicaoMudancaHistoricoProblemaDTO ligacaoRequisicaoMudancaHistoricoProblemaDTO = new LigacaoRequisicaoMudancaHistoricoProblemaDTO();
        LigacaoRequisicaoMudancaHistoricoGrupoDTO ligacaoRequisicaoMudancaHistoricoGrupoDTO = new LigacaoRequisicaoMudancaHistoricoGrupoDTO();
        final LigacaoRequisicaoMudancaProblemaDao ligacaoRequisicaoMudancaProblemaDao = new LigacaoRequisicaoMudancaProblemaDao();
        final LigacaoRequisicaoMudancaGrupoDao ligacaoRequisicaoMudancaGrupoDao = new LigacaoRequisicaoMudancaGrupoDao();
        final LigacaoRequisicaoMudancaHistoricoRiscosDTO ligaHistoricoRiscosDTO = new LigacaoRequisicaoMudancaHistoricoRiscosDTO();
        final LigacaoRequisicaoLiberacaoRiscosDao ligacaoriscoDao = new LigacaoRequisicaoLiberacaoRiscosDao();

        if (usuario == null) {
            usuario = new Usuario();
        }

        if (usuario != null && requisicaoMudancaDto != null) {
            usuario.setLocale(requisicaoMudancaDto.getUsuarioDto().getLocale());
        }

        try {
            requisicaoMudancaDto.setDataHoraInicio(this.getRequisicaoAtual(requisicaoMudancaDto.getIdRequisicaoMudanca()).getDataHoraInicio());
        } catch (final Exception e1) {
            e1.printStackTrace();
        }

        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null) {
            final Timestamp dataHoraInicial = this.MontardataHoraAgendamentoInicial(requisicaoMudancaDto);
            requisicaoMudancaDto.setDataHoraInicioAgendada(dataHoraInicial);
        }

        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoFinal() != null) {
            final Timestamp dataHoraFinal = this.MontardataHoraAgendamentoFinal(requisicaoMudancaDto);
            requisicaoMudancaDto.setDataHoraTerminoAgendada(dataHoraFinal);
            requisicaoMudancaDto.setDataHoraTermino(dataHoraFinal);
        }

        ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDto = new ContatoRequisicaoMudancaDTO();
        TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
        final SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();

        final AprovacaoMudancaDao aprovacaoMudancaDao = new AprovacaoMudancaDao();
        final AprovacaoPropostaDao aprovacaoPropostaDao = new AprovacaoPropostaDao();

        final TipoMudancaDAO tipoMudancaDAO = new TipoMudancaDAO();
        final ProblemaMudancaDAO problemaMudancaDao = new ProblemaMudancaDAO();
        final GrupoRequisicaoMudancaDao gruporequisicaomudancaDao = new GrupoRequisicaoMudancaDao();

        final TransactionControler tc = new TransactionControlerImpl(requisicaoMudancaDao.getAliasDB());

        final RequisicaoMudancaServicoDao requisicaoMudancaServicoDao = new RequisicaoMudancaServicoDao();
        final RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaItemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao();
        final RequisicaoMudancaRiscoDao requisicaoMudancaRiscoDao = new RequisicaoMudancaRiscoDao();
        final LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();
        final RequisicaoMudancaResponsavelDao mudancaResponsavelDao = new RequisicaoMudancaResponsavelDao();
        try {
            tc.start();

            solicitacaoServicoMudancaDao.setTransactionControler(tc);
            requisicaoMudancaServicoDao.setTransactionControler(tc);
            requisicaoMudancaItemConfiguracaoDao.setTransactionControler(tc);
            tipoMudancaDAO.setTransactionControler(tc);
            requisicaoMudancaDao.setTransactionControler(tc);
            aprovacaoMudancaDao.setTransactionControler(tc);
            aprovacaoPropostaDao.setTransactionControler(tc);
            problemaMudancaDao.setTransactionControler(tc);
            gruporequisicaomudancaDao.setTransactionControler(tc);
            requisicaoMudancaRiscoDao.setTransactionControler(tc);
            liberacaoMudancaDao.setTransactionControler(tc);
            mudancaResponsavelDao.setTransactionControler(tc);
            ligacaoRequisicaoMudancaProblemaDao.setTransactionControler(tc);
            ligacaoRequisicaoMudancaGrupoDao.setTransactionControler(tc);
            ligacaoriscoDao.setTransactionControler(tc);

            if (requisicaoMudancaDto != null) {
                if (requisicaoMudancaDto.getIdTipoMudanca() != null) {
                    tipoMudancaDto.setIdTipoMudanca(requisicaoMudancaDto.getIdTipoMudanca());
                    tipoMudancaDto = (TipoMudancaDTO) tipoMudancaDAO.restore(tipoMudancaDto);
                }

                if (requisicaoMudancaDto.getListAprovacaoPropostaDTO() != null && requisicaoMudancaDto.getFase().equalsIgnoreCase("Proposta")) {
                    for (final AprovacaoPropostaDTO aprovacaoPropostaDto : requisicaoMudancaDto.getListAprovacaoPropostaDTO()) {
                        aprovacaoPropostaDao.deleteLinha(requisicaoMudancaDto.getIdRequisicaoMudanca(), aprovacaoPropostaDto.getIdEmpregado());
                        aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        aprovacaoPropostaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
                        aprovacaoPropostaDao.create(aprovacaoPropostaDto);
                    }
                }

                if (requisicaoMudancaDto.getAcaoFluxo().equalsIgnoreCase("E") && requisicaoMudancaDto.getFase().equalsIgnoreCase("Proposta")) {
                    if (!requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Cancelada.name())) {
                        if (!this.validacaoAvancaFluxoProposta(requisicaoMudancaDto, tc)) {
                            throw new LogicException(this.i18nMessage("requisicaoMudanca.essaPropostaNaoFoiAprovada"));
                        }
                    }
                }

                if (requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null) {

                    this.calculaTempoAtraso(requisicaoMudancaDto);

                } else {
                    requisicaoMudancaDto.setPrazoHH(00);
                    requisicaoMudancaDto.setPrazoMM(00);
                }

                if (requisicaoMudancaDto.getStatus() != null && !requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Cancelada.name())
                        && !requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Rejeitada.name())) {
                    if (requisicaoMudancaDto.getAlterarSituacao().equalsIgnoreCase("N")) {
                        requisicaoMudancaDto.setStatus(this.getStatusAtual(requisicaoMudancaDto.getIdRequisicaoMudanca()));
                    }
                }

            }

            // Gravando o historico
            HistoricoMudancaDTO historicoMudancaDTO = new HistoricoMudancaDTO();
            final HistoricoMudancaDao historicoMudancaDao = new HistoricoMudancaDao();
            historicoMudancaDao.setTransactionControler(tc);
            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
                historicoMudancaDTO = (HistoricoMudancaDTO) historicoMudancaDao.create(this.createHistoricoMudanca(requisicaoMudancaDto));

                final ControleGEDDao controleGEDDao = new ControleGEDDao();
                controleGEDDao.setTransactionControler(tc);

                historicoMudancaDTO.setColResponsaveis(this.listarColResponsaveis(historicoMudancaDTO));
                if (historicoMudancaDTO.getColResponsaveis() != null) {
                    for (final RequisicaoMudancaResponsavelDTO requisicaoMudancaRespDTO : historicoMudancaDTO.getColResponsaveis()) {
                        ligacaoResponsavelDTO.setIdRequisicaoMudanca(requisicaoMudancaRespDTO.getIdRequisicaoMudanca());
                        ligacaoResponsavelDTO.setIdRequisicaoMudancaResp(requisicaoMudancaRespDTO.getIdRequisicaoMudancaResp());
                        ligacaoResponsavelDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
                        ligacaoResponsavelDao.create(ligacaoResponsavelDTO);
                        ligacaoResponsavelDTO = new LigacaoRequisicaoMudancaHistoricoResponsavelDTO();
                    }
                }

                historicoMudancaDTO.setListRequisicaoMudancaItemConfiguracaoDTO(this.listarColItemConfiguracao(historicoMudancaDTO));
                if (historicoMudancaDTO.getListRequisicaoMudancaItemConfiguracaoDTO() != null) {
                    for (final RequisicaoMudancaItemConfiguracaoDTO requisicaoMudancaItemConfiguracaoDTO : historicoMudancaDTO.getListRequisicaoMudancaItemConfiguracaoDTO()) {
                        ligacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO.setIdRequisicaoMudanca(requisicaoMudancaItemConfiguracaoDTO.getIdRequisicaoMudanca());
                        ligacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO.setIdrequisicaomudancaitemconfiguracao(requisicaoMudancaItemConfiguracaoDTO
                                .getIdRequisicaoMudancaItemConfiguracao());
                        ligacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
                        ligacaoRequisicaoMudancaItemConfiguracaoDao.create(ligacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO);
                        ligacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO = new LigacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO();
                    }
                }

                historicoMudancaDTO.setListRequisicaoMudancaServicoDTO(this.listarServico(historicoMudancaDTO));
                if (historicoMudancaDTO.getListRequisicaoMudancaServicoDTO() != null) {
                    for (final RequisicaoMudancaServicoDTO requisicaoMudancaServicoDTO : historicoMudancaDTO.getListRequisicaoMudancaServicoDTO()) {
                        ligacaoRequisicaoMudancaHistoricoServicoDTO.setIdRequisicaoMudanca(requisicaoMudancaServicoDTO.getIdRequisicaoMudanca());
                        ligacaoRequisicaoMudancaHistoricoServicoDTO.setIdrequisicaomudancaservico(requisicaoMudancaServicoDTO.getIdRequisicaoMudancaServico());
                        ligacaoRequisicaoMudancaHistoricoServicoDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
                        ligacaoRequisicaoMudancaServicoDao.create(ligacaoRequisicaoMudancaHistoricoServicoDTO);
                        ligacaoRequisicaoMudancaHistoricoServicoDTO = new LigacaoRequisicaoMudancaHistoricoServicoDTO();
                    }
                }

                historicoMudancaDTO.setListProblemaMudancaDTO(this.listarProblema(historicoMudancaDTO));
                if (historicoMudancaDTO.getListProblemaMudancaDTO() != null) {
                    for (final ProblemaMudancaDTO problemaMudancaDTO : historicoMudancaDTO.getListProblemaMudancaDTO()) {
                        ligacaoRequisicaoMudancaHistoricoProblemaDTO.setIdRequisicaoMudanca(problemaMudancaDTO.getIdRequisicaoMudanca());
                        ligacaoRequisicaoMudancaHistoricoProblemaDTO.setIdProblemaMudanca(problemaMudancaDTO.getIdProblemaMudanca());
                        ligacaoRequisicaoMudancaHistoricoProblemaDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
                        ligacaoRequisicaoMudancaProblemaDao.create(ligacaoRequisicaoMudancaHistoricoProblemaDTO);
                        ligacaoRequisicaoMudancaHistoricoProblemaDTO = new LigacaoRequisicaoMudancaHistoricoProblemaDTO();
                    }
                }

                historicoMudancaDTO.setListGrupoRequisicaoMudancaDTO(this.listarGrupo(historicoMudancaDTO));
                if (historicoMudancaDTO.getListGrupoRequisicaoMudancaDTO() != null) {
                    for (final GrupoRequisicaoMudancaDTO gruporequisicaomudancaDTO : historicoMudancaDTO.getListGrupoRequisicaoMudancaDTO()) {
                        ligacaoRequisicaoMudancaHistoricoGrupoDTO.setIdRequisicaoMudanca(gruporequisicaomudancaDTO.getIdRequisicaoMudanca());
                        ligacaoRequisicaoMudancaHistoricoGrupoDTO.setIdGrupoRequisicaoMudanca(gruporequisicaomudancaDTO.getIdGrupoRequisicaoMudanca());
                        ligacaoRequisicaoMudancaHistoricoGrupoDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
                        ligacaoRequisicaoMudancaGrupoDao.create(ligacaoRequisicaoMudancaHistoricoGrupoDTO);
                        ligacaoRequisicaoMudancaHistoricoGrupoDTO = new LigacaoRequisicaoMudancaHistoricoGrupoDTO();
                    }
                }

                historicoMudancaDTO.setListRequisicaoMudancaRiscoDTO(this.listarRiscos(historicoMudancaDTO));
                if (historicoMudancaDTO.getListRequisicaoMudancaRiscoDTO() != null) {
                    for (final RequisicaoMudancaRiscoDTO riscoMudancaDTO : historicoMudancaDTO.getListRequisicaoMudancaRiscoDTO()) {
                        ligaHistoricoRiscosDTO.setIdRequisicaoMudanca(historicoMudancaDTO.getIdRequisicaoMudanca());
                        ligaHistoricoRiscosDTO.setIdRequisicaoMudancaRisco(riscoMudancaDTO.getIdRequisicaoMudancaRisco());
                        ligaHistoricoRiscosDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
                        ligacaoriscoDao.create(ligaHistoricoRiscosDTO);
                        ligacaoRequisicaoMudancaHistoricoProblemaDTO = new LigacaoRequisicaoMudancaHistoricoProblemaDTO();
                    }
                }

                historicoMudancaDTO.setListLiberacaoMudancaDTO(this.listarLiberacoes(historicoMudancaDTO));
                if (historicoMudancaDTO.getListLiberacaoMudancaDTO() != null && historicoMudancaDTO.getListLiberacaoMudancaDTO().size() > 0) {
                    this.gravarLiberacaoHistorico(historicoMudancaDTO, tc);
                }

                // gravando historico mudancaSolicitacaoServico
                List<RequisicaoMudancaDTO> listSolicitacaoServicosMudanca = new ArrayList<RequisicaoMudancaDTO>();
                listSolicitacaoServicosMudanca = this.listarSolicitacaoServico(historicoMudancaDTO);
                if (listSolicitacaoServicosMudanca != null) {
                    this.gravarSolicitacaoServicoHistoricos(historicoMudancaDTO, listSolicitacaoServicosMudanca, tc);
                }

                // gravando o historico de aprovação de mudança.
                historicoMudancaDTO.setListAprovacaoMudancaDTO(this.listarAprovacoes(historicoMudancaDTO));
                if (historicoMudancaDTO.getListAprovacaoMudancaDTO() != null) {
                    for (final AprovacaoMudancaDTO aprovacaoMudancaDTO : historicoMudancaDTO.getListAprovacaoMudancaDTO()) {
                        aprovacaoMudancaDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
                        aprovacaoMudancaDao.create(aprovacaoMudancaDTO);
                    }
                }

            }

            contatoRequisicaoMudancaDto = this.criarContatoRequisicaoMudanca(requisicaoMudancaDto, tc);
            if (contatoRequisicaoMudancaDto != null) {
                requisicaoMudancaDto.setIdContatoRequisicaoMudanca(contatoRequisicaoMudancaDto.getIdContatoRequisicaoMudanca());
            }

            if (requisicaoMudancaDto != null) {
                if (requisicaoMudancaDto.getIdGrupoAtual() == null) {
                    requisicaoMudancaDto.setIdGrupoAtual(tipoMudancaDto.getIdGrupoExecutor());

                }
                if (requisicaoMudancaDto.getAcaoFluxo().equalsIgnoreCase("E")) {
                    if (requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Executada.name())) {
                        if (requisicaoMudancaDto.getFechamento() == null || requisicaoMudancaDto.getFechamento().equalsIgnoreCase("")) {
                            throw new LogicException(this.i18nMessage("citcorpore.comum.informeFechamento"));
                        }
                    }
                }

                if (requisicaoMudancaDto.getStatus() != null && requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Cancelada.name())) {
                    if (requisicaoMudancaDto.getFechamento() == null || requisicaoMudancaDto.getFechamento().equalsIgnoreCase("")) {
                        throw new LogicException(this.i18nMessage("citcorpore.comum.informeFechamento"));
                    }
                }

                solicitacaoServicoMudancaDao.deleteByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            }

            if (requisicaoMudancaDto != null) {
                if (requisicaoMudancaDto.getListIdSolicitacaoServico() != null && requisicaoMudancaDto.getListIdSolicitacaoServico().size() > 0) {
                    for (final SolicitacaoServicoDTO solicitacaoServicoDTO : requisicaoMudancaDto.getListIdSolicitacaoServico()) {
                        final SolicitacaoServicoMudancaDTO solicitacaoServicoMudancaDTO = new SolicitacaoServicoMudancaDTO();

                        solicitacaoServicoMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        solicitacaoServicoMudancaDTO.setIdSolicitacaoServico(solicitacaoServicoDTO.getIdSolicitacaoServico());
                        solicitacaoServicoMudancaDao.create(solicitacaoServicoMudancaDTO);
                    }
                }

            }
            // adiciona e deleta logicamente os itens da grid de problemas
            if (requisicaoMudancaDto != null) {
                if (requisicaoMudancaDto.getListProblemaMudancaDTO() != null && requisicaoMudancaDto.getListProblemaMudancaDTO().size() > 0) {
                    this.deleteAdicionaTabelaProblema(requisicaoMudancaDto, tc);
                } else {
                    final ProblemaMudancaDAO problemadaoDao = new ProblemaMudancaDAO();
                    final Collection<ProblemaMudancaDTO> ListProblemaMudancaaDTO = problemadaoDao.findByIdMudancaEDataFim(requisicaoMudancaDto.getIdRequisicaoMudanca());
                    if (ListProblemaMudancaaDTO != null && ListProblemaMudancaaDTO.size() > 0) {
                        for (final ProblemaMudancaDTO problemaMudancaDTO : ListProblemaMudancaaDTO) {
                            problemaMudancaDTO.setDataFim(UtilDatas.getDataAtual());
                            problemaMudancaDao.update(problemaMudancaDTO);
                        }
                    }
                }

                if (requisicaoMudancaDto.getListGrupoRequisicaoMudancaDTO() != null && requisicaoMudancaDto.getListGrupoRequisicaoMudancaDTO().size() > 0) {
                    this.deleteAdicionaTabelaGrupo(requisicaoMudancaDto, tc);
                } else {
                    final GrupoRequisicaoMudancaDao gruporequisicaomudancaDao1 = new GrupoRequisicaoMudancaDao();
                    final Collection<GrupoRequisicaoMudancaDTO> ListGrupoRequisicaoMudanca = gruporequisicaomudancaDao1.findByIdMudancaEDataFim(requisicaoMudancaDto
                            .getIdRequisicaoMudanca());
                    if (ListGrupoRequisicaoMudanca != null && ListGrupoRequisicaoMudanca.size() > 0) {
                        for (final GrupoRequisicaoMudancaDTO gruporequisicaomudancaDTO : ListGrupoRequisicaoMudanca) {
                            gruporequisicaomudancaDTO.setDataFim(UtilDatas.getDataAtual());
                            gruporequisicaomudancaDao1.update(gruporequisicaomudancaDTO);
                        }
                    }
                }

                if (requisicaoMudancaDto.getListRequisicaoMudancaRiscoDTO() != null && requisicaoMudancaDto.getListRequisicaoMudancaRiscoDTO().size() > 0) {
                    this.deleteAdicionaTabelaRiscos(requisicaoMudancaDto, tc);
                } else {
                    final RequisicaoMudancaRiscoDao riscosDao = new RequisicaoMudancaRiscoDao();
                    final Collection<RequisicaoMudancaRiscoDTO> ListRiscosMudancaaDTO = riscosDao.findByIdRequisicaoMudancaEDataFim(requisicaoMudancaDto.getIdRequisicaoMudanca());
                    if (ListRiscosMudancaaDTO != null && ListRiscosMudancaaDTO.size() > 0) {
                        for (final RequisicaoMudancaRiscoDTO riscosMudancaDTO : ListRiscosMudancaaDTO) {
                            riscosMudancaDTO.setDataFim(UtilDatas.getDataAtual());
                            riscosMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                            riscosDao.update(riscosMudancaDTO);
                        }
                    }
                }

                // geber.costa
                liberacaoMudancaDao.deleteByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                if (requisicaoMudancaDto.getListLiberacaoMudancaDTO() != null) {
                    for (final LiberacaoMudancaDTO dto : requisicaoMudancaDto.getListLiberacaoMudancaDTO()) {
                        dto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        liberacaoMudancaDao.create(dto);
                    }
                }
            }
            List<RequisicaoMudancaServicoDTO> servicosBanco = null;

            RequisicaoMudancaServicoDTO aux = null;
            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getListRequisicaoMudancaServicoDTO() != null) {
                // se não existir no banco, cria, caso contrário, atualiza
                for (final RequisicaoMudancaServicoDTO requisicaoMudancaServicoDTO : requisicaoMudancaDto.getListRequisicaoMudancaServicoDTO()) {
                    requisicaoMudancaServicoDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());

                    aux = requisicaoMudancaServicoDao.restoreByChaveComposta(requisicaoMudancaServicoDTO);

                    if (aux == null) {
                        requisicaoMudancaServicoDao.create(requisicaoMudancaServicoDTO);
                    } else {
                        requisicaoMudancaServicoDao.update(aux);
                    }
                }

            }
            // confere se existe algo no banco que não está na lista salva, e deleta
            if (requisicaoMudancaDto != null) {
                servicosBanco = requisicaoMudancaServicoDao.listByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            }
            if (servicosBanco != null) {
                for (final RequisicaoMudancaServicoDTO i : servicosBanco) {
                    if (!this.requisicaoMudancaServicoExisteNaLista(i, requisicaoMudancaDto.getListRequisicaoMudancaServicoDTO())) {
                        i.setDataFim(UtilDatas.getDataAtual());
                        requisicaoMudancaServicoDao.update(i);
                    }
                }
            }

            List<RequisicaoMudancaItemConfiguracaoDTO> icsBanco = null;
            RequisicaoMudancaItemConfiguracaoDTO requisicaoMudancaItemConfiguracaoDTO2 = new RequisicaoMudancaItemConfiguracaoDTO();

            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getListRequisicaoMudancaItemConfiguracaoDTO() != null) {
                // se não existir no banco, cria, caso contrário, atualiza
                for (final RequisicaoMudancaItemConfiguracaoDTO requisicaoMudancaItemConfiguracaoDTO : requisicaoMudancaDto.getListRequisicaoMudancaItemConfiguracaoDTO()) {

                    requisicaoMudancaItemConfiguracaoDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());

                    requisicaoMudancaItemConfiguracaoDTO2 = requisicaoMudancaItemConfiguracaoDao.restoreByChaveComposta(requisicaoMudancaItemConfiguracaoDTO);

                    if (requisicaoMudancaItemConfiguracaoDTO2 == null) {
                        requisicaoMudancaItemConfiguracaoDao.create(requisicaoMudancaItemConfiguracaoDTO);
                    } else {
                        requisicaoMudancaItemConfiguracaoDao.update(requisicaoMudancaItemConfiguracaoDTO2);
                    }
                }
            }
            // confere se existe algo no banco que não está na lista salva, e deleta
            if (requisicaoMudancaDto != null) {
                icsBanco = requisicaoMudancaItemConfiguracaoDao.listByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            }
            if (icsBanco != null) {
                for (final RequisicaoMudancaItemConfiguracaoDTO i : icsBanco) {
                    if (!this.requisicaoMudancaICExisteNaLista(i, requisicaoMudancaDto.getListRequisicaoMudancaItemConfiguracaoDTO())) {
                        i.setDataFim(UtilDatas.getDataAtual());
                        requisicaoMudancaItemConfiguracaoDao.update(i);
                    }
                }
            }

            // update Responsavel
            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getColResponsaveis() != null && requisicaoMudancaDto.getColResponsaveis().size() > 0) {
                this.deleteAdicionaTabelaResponsavel(requisicaoMudancaDto, tc);
            } else {
                final RequisicaoMudancaResponsavelDao requisicaoMudancaResponsavelDao = new RequisicaoMudancaResponsavelDao();
                final Collection<RequisicaoMudancaResponsavelDTO> responsavel = requisicaoMudancaResponsavelDao.findByIdMudancaEDataFim(requisicaoMudancaDto
                        .getIdRequisicaoMudanca());
                if (responsavel != null && responsavel.size() > 0) {
                    for (final RequisicaoMudancaResponsavelDTO requisicaoLiberacaoResponsavelDTO : responsavel) {
                        requisicaoLiberacaoResponsavelDTO.setDataFim(UtilDatas.getDataAtual());
                        requisicaoMudancaResponsavelDao.update(requisicaoLiberacaoResponsavelDTO);
                    }
                }
            }

            // gravando historico de anexos
            final HistoricoGEDDao historicoGEDDao = new HistoricoGEDDao();
            Collection<HistoricoGEDDTO> colHistoricoGed = new ArrayList<HistoricoGEDDTO>();
            colHistoricoGed = historicoGEDDao.listByIdTabelaAndIdLiberacao(ControleGEDDTO.TABELA_REQUISICAOMUDANCA, requisicaoMudancaDto.getIdRequisicaoMudanca());
            if (colHistoricoGed != null && colHistoricoGed.size() > 0) {
                for (final HistoricoGEDDTO historicoGEDDTO : colHistoricoGed) {
                    historicoGEDDTO.setDataFim(UtilDatas.getDataAtual());
                    historicoGEDDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
                    historicoGEDDao.update(historicoGEDDTO);
                }
            }
            if (requisicaoMudancaDto.getColArquivosUpload() != null /* && liberacaoDto.getColArquivosUpload().size() > 0 */) {
                this.gravaInformacoesGED(requisicaoMudancaDto, tc, historicoMudancaDTO);
            }
            // gravando historico de anexos
            Collection<HistoricoGEDDTO> colHistoricoPlanoReversaoGed = new ArrayList<HistoricoGEDDTO>();
            colHistoricoPlanoReversaoGed = historicoGEDDao
                    .listByIdTabelaAndIdLiberacao(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA, requisicaoMudancaDto.getIdRequisicaoMudanca());
            if (colHistoricoPlanoReversaoGed != null && colHistoricoPlanoReversaoGed.size() > 0) {
                for (final HistoricoGEDDTO historicoGEDDTO : colHistoricoPlanoReversaoGed) {
                    historicoGEDDTO.setDataFim(UtilDatas.getDataAtual());
                    historicoGEDDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
                    historicoGEDDao.update(historicoGEDDTO);
                }
            }
            if (requisicaoMudancaDto.getColUploadPlanoDeReversaoGED() != null /* && liberacaoDto.getColArquivosUpload().size() > 0 */) {
                this.gravaPlanoDeReversaoGED(requisicaoMudancaDto, tc, historicoMudancaDTO);
            }

            if (requisicaoMudancaDto.getStatus().equalsIgnoreCase(Enumerados.SituacaoRequisicaoMudanca.Resolvida.getDescricao())) {
                this.fechaRelacionamentoMudanca(tc, requisicaoMudancaDto);
            }

            if (requisicaoMudancaDto.getRegistroexecucao() != null) {
                final Source source = new Source(requisicaoMudancaDto.getRegistroexecucao());
                requisicaoMudancaDto.setRegistroexecucao(source.getTextExtractor().toString());
            }

            final ExecucaoMudancaServiceEjb execucaoMudancaService = new ExecucaoMudancaServiceEjb();
            if (requisicaoMudancaDto.getIdTarefa() == null) {
                requisicaoMudancaDao.update(requisicaoMudancaDto);
            } else {

                if (requisicaoMudancaDto.getFase() != null && !requisicaoMudancaDto.getFase().equalsIgnoreCase("")) {
                    requisicaoMudancaDao.updateFase(requisicaoMudancaDto.getIdRequisicaoMudanca(), requisicaoMudancaDto.getFase());
                    requisicaoMudancaDao.update(requisicaoMudancaDto);
                } else {
                    if (tipoMudancaDto != null) {
                        requisicaoMudancaDao.update(model);
                    } else {
                        throw new LogicException(this.i18nMessage("requisicaoMudanca.categoriaMudancaNaoLocalizada"));
                    }
                }
                if (requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Cancelada.name())
                        || requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Rejeitada.name())) {
                    execucaoMudancaService.encerra(requisicaoMudancaDto.getUsuarioDto(), requisicaoMudancaDto, tc);
                } else {
                    execucaoMudancaService.executa(requisicaoMudancaDto, requisicaoMudancaDto.getIdTarefa(), requisicaoMudancaDto.getAcaoFluxo(), tc);
                }

            }

            if (requisicaoMudancaDto.getRegistroexecucao() != null && !requisicaoMudancaDto.getRegistroexecucao().trim().equalsIgnoreCase("")) {
                final OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
                ocorrenciaMudancaDao.setTransactionControler(tc);
                final OcorrenciaMudancaDTO ocorrenciaMudancaDto = new OcorrenciaMudancaDTO();
                ocorrenciaMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                ocorrenciaMudancaDto.setDataregistro(UtilDatas.getDataAtual());
                ocorrenciaMudancaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
                ocorrenciaMudancaDto.setTempoGasto(0);
                ocorrenciaMudancaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
                ocorrenciaMudancaDto.setDataInicio(UtilDatas.getDataAtual());
                ocorrenciaMudancaDto.setDataFim(UtilDatas.getDataAtual());
                ocorrenciaMudancaDto.setInformacoesContato("não se aplica");
                ocorrenciaMudancaDto.setRegistradopor(requisicaoMudancaDto.getUsuarioDto().getLogin());
                ocorrenciaMudancaDto.setDadosMudanca(new Gson().toJson(requisicaoMudancaDto));
                ocorrenciaMudancaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
                ocorrenciaMudancaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Criacao.getSigla());
                ocorrenciaMudancaDto.setOcorrencia(requisicaoMudancaDto.getRegistroexecucao());
                ocorrenciaMudancaDao.create(ocorrenciaMudancaDto);
            }

            if (requisicaoMudancaDto.getAcaoFluxo().equalsIgnoreCase("E")) {
                if (requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Executada.name())
                        || requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Concluida.name())) {
                    this.fechaRelacionamentoMudanca(tc, requisicaoMudancaDto);
                }
            }

            tc.commit();

            if (requisicaoMudancaDto.getDataHoraInicioAgendada() != null) {
                RequisicaoMudanca.salvaGrupoAtvPeriodicaEAgenda(requisicaoMudancaDto);
            }
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        } finally {
            try {
                tc.close();
            } catch (final PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    @Override
    public RequisicaoMudancaDTO restoreAll(final Integer idRequisicaoMudanca) throws Exception {
        return this.restoreAll(idRequisicaoMudanca, null);
    }

    public RequisicaoMudancaDTO restoreAll(final Integer idRequisicaoMudanca, final TransactionControler tc) throws Exception {
        final RequisicaoMudancaDao requisicaoDao = this.getDao();
        if (tc != null) {
            requisicaoDao.setTransactionControler(tc);
        }
        RequisicaoMudancaDTO requisicaoDto = new RequisicaoMudancaDTO();
        requisicaoDto.setIdRequisicaoMudanca(idRequisicaoMudanca);
        requisicaoDto = (RequisicaoMudancaDTO) requisicaoDao.restore(requisicaoDto);
        if (requisicaoDto != null && requisicaoDto.getDataHoraInicioAgendada() != null) {
            final Timestamp dataHoraTerminoAgendada = requisicaoDto.getDataHoraInicioAgendada();
            final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            final String horaAgendamentoInicialSTR = format.format(dataHoraTerminoAgendada);
            final String horaInicial = horaAgendamentoInicialSTR.substring(11, 16);
            requisicaoDto.setHoraAgendamentoInicial(horaInicial.trim());
        }
        if (requisicaoDto != null && requisicaoDto.getDataHoraTermino() != null && requisicaoDto.getDataHoraTerminoStr() != null) {
            final Timestamp dataHoraTerminoAgendada = requisicaoDto.getDataHoraTerminoAgendada();
            final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            if (dataHoraTerminoAgendada != null) {
                final String horaAgendamentoFinallSTR = format.format(dataHoraTerminoAgendada);
                final String horaFinal = horaAgendamentoFinallSTR.substring(11, 16);
                requisicaoDto.setHoraAgendamentoFinal(horaFinal.trim());
            }

        }
        if (requisicaoDto != null && requisicaoDto.getDescricao() != null) {
            final Source source = new Source(requisicaoDto.getDescricao());
            requisicaoDto.setDescricao(source.getTextExtractor().toString());
        }

        if (requisicaoDto != null) {
            requisicaoDto.setDataHoraTerminoStr(requisicaoDto.getDataHoraTerminoStr());

            final EmpregadoDTO empregadoDto = new EmpregadoDao().restoreByIdEmpregado(requisicaoDto.getIdSolicitante());
            if (empregadoDto != null) {
                requisicaoDto.setNomeSolicitante(empregadoDto.getNome());
                requisicaoDto.setEmailSolicitante(empregadoDto.getEmail());
            }

            if (requisicaoDto.getIdProprietario() != null) {
                UsuarioDTO usuarioDto = new UsuarioDTO();
                final UsuarioDao usuarioDao = new UsuarioDao();
                // usuarioDto.setIdUsuario(requisicaoDto.getIdProprietario());
                // usuarioDto = (UsuarioDTO) usuarioDao.restore(usuarioDto);
                /**
                 * Motivo: Restaura o usuário a partir do idProprietario gravado no banco de dados
                 * Autor: flavio.santana
                 * Data/Hora: 28/11/2013 17:50
                 */
                usuarioDto = usuarioDao.restoreByIdEmpregado(requisicaoDto.getIdProprietario());
                if (usuarioDto != null) {
                    requisicaoDto.setResponsavelAtual(usuarioDto.getLogin());
                }
            }

            if (requisicaoDto.getIdGrupoAtual() != null) {
                final GrupoDao grupoDao = new GrupoDao();
                GrupoDTO grupoDto = new GrupoDTO();
                grupoDto.setIdGrupo(requisicaoDto.getIdGrupoAtual());
                grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
                if (grupoDto != null) {
                    requisicaoDto.setNomeGrupoAtual(grupoDto.getSigla());
                }
            }

            if (requisicaoDto.getIdGrupoNivel1() != null) {
                final GrupoDao grupoDao = new GrupoDao();
                GrupoDTO grupoDto = new GrupoDTO();
                grupoDto.setIdGrupo(requisicaoDto.getIdGrupoNivel1());
                grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
                if (grupoDto != null) {
                    requisicaoDto.setNomeGrupoNivel1(grupoDto.getSigla());
                }
            }

            if (requisicaoDto.getIdContatoRequisicaoMudanca() != null) {
                ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDto = new ContatoRequisicaoMudancaDTO();
                final ContatoRequisicaoMudancaDao contatoRequisicaoMudancaDao = new ContatoRequisicaoMudancaDao();

                if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER")) {
                    if (tc != null) {
                        contatoRequisicaoMudancaDao.setTransactionControler(tc);
                    }
                }

                contatoRequisicaoMudancaDto.setIdContatoRequisicaoMudanca(requisicaoDto.getIdContatoRequisicaoMudanca());
                contatoRequisicaoMudancaDto = (ContatoRequisicaoMudancaDTO) contatoRequisicaoMudancaDao.restore(contatoRequisicaoMudancaDto);
                if (contatoRequisicaoMudancaDto != null) {
                    requisicaoDto.setNomeContato(contatoRequisicaoMudancaDto.getNomecontato());
                }
            }
            if (requisicaoDto.getIdTipoMudanca() != null) {
                TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
                final TipoMudancaDAO tipoMudancaDao = new TipoMudancaDAO();

                tipoMudancaDto.setIdTipoMudanca(requisicaoDto.getIdTipoMudanca());
                tipoMudancaDto = (TipoMudancaDTO) tipoMudancaDao.restore(tipoMudancaDto);
                if (tipoMudancaDto != null) {
                    requisicaoDto.setTipo(tipoMudancaDto.getNomeTipoMudanca());
                }
            }

        }
        return this.verificaAtraso(requisicaoDto);
    }

    public RequisicaoMudancaDTO restoreAllReuniao(final Integer idRequisicaoMudanca, final Integer idReuniaoRequisicaoMudanca, final TransactionControler tc) throws Exception {
        final RequisicaoMudancaDao requisicaoDao = this.getDao();
        if (tc != null) {
            requisicaoDao.setTransactionControler(tc);
        }
        RequisicaoMudancaDTO requisicaoDto = new RequisicaoMudancaDTO();
        requisicaoDto.setIdRequisicaoMudanca(idRequisicaoMudanca);
        requisicaoDto = (RequisicaoMudancaDTO) requisicaoDao.restore(requisicaoDto);
        if (requisicaoDto != null && requisicaoDto.getDescricao() != null) {
            final Source source = new Source(requisicaoDto.getDescricao());
            requisicaoDto.setDescricao(source.getTextExtractor().toString());
        }

        if (requisicaoDto != null) {
            requisicaoDto.setDataHoraTerminoStr(requisicaoDto.getDataHoraTerminoStr());

            final EmpregadoDTO empregadoDto = new EmpregadoDao().restoreByIdEmpregado(requisicaoDto.getIdSolicitante());
            if (empregadoDto != null) {
                requisicaoDto.setNomeSolicitante(empregadoDto.getNome());
                requisicaoDto.setEmailSolicitante(empregadoDto.getEmail());
            }

            if (requisicaoDto.getIdGrupoAtual() != null) {
                final GrupoDao grupoDao = new GrupoDao();
                GrupoDTO grupoDto = new GrupoDTO();
                grupoDto.setIdGrupo(requisicaoDto.getIdGrupoAtual());
                grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
                if (grupoDto != null) {
                    requisicaoDto.setNomeGrupoAtual(grupoDto.getSigla());
                }
            }

            if (requisicaoDto.getIdGrupoNivel1() != null) {
                final GrupoDao grupoDao = new GrupoDao();
                GrupoDTO grupoDto = new GrupoDTO();
                grupoDto.setIdGrupo(requisicaoDto.getIdGrupoNivel1());
                grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
                if (grupoDto != null) {
                    requisicaoDto.setNomeGrupoNivel1(grupoDto.getSigla());
                }
            }

            if (requisicaoDto.getIdContatoRequisicaoMudanca() != null) {
                ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDto = new ContatoRequisicaoMudancaDTO();
                final ContatoRequisicaoMudancaDao contatoRequisicaoMudancaDao = new ContatoRequisicaoMudancaDao();
                // contatoRequisicaoMudancaDao.setTransactionControler(tc);
                contatoRequisicaoMudancaDto.setIdContatoRequisicaoMudanca(requisicaoDto.getIdContatoRequisicaoMudanca());
                contatoRequisicaoMudancaDto = (ContatoRequisicaoMudancaDTO) contatoRequisicaoMudancaDao.restore(contatoRequisicaoMudancaDto);
                if (contatoRequisicaoMudancaDto != null) {
                    requisicaoDto.setNomeContato(contatoRequisicaoMudancaDto.getNomecontato());
                }
            }
            if (requisicaoDto.getIdTipoMudanca() != null) {
                TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
                final TipoMudancaDAO tipoMudancaDao = new TipoMudancaDAO();

                tipoMudancaDto.setIdTipoMudanca(requisicaoDto.getIdTipoMudanca());
                tipoMudancaDto = (TipoMudancaDTO) tipoMudancaDao.restore(tipoMudancaDto);
                if (tipoMudancaDto != null) {
                    requisicaoDto.setTipo(tipoMudancaDto.getNomeTipoMudanca());
                }
            }

            final ReuniaoRequisicaoMudancaDAO reuniaoDao = new ReuniaoRequisicaoMudancaDAO();
            ReuniaoRequisicaoMudancaDTO reuniaoDto = new ReuniaoRequisicaoMudancaDTO();
            reuniaoDto.setIdReuniaoRequisicaoMudanca(idReuniaoRequisicaoMudanca);
            reuniaoDto = (ReuniaoRequisicaoMudancaDTO) reuniaoDao.restore(reuniaoDto);
            requisicaoDto.setLocalReuniao(reuniaoDto.getLocalReuniao());
            requisicaoDto.setDataInicio(reuniaoDto.getDataInicio());
            requisicaoDto.setHoraInicio(reuniaoDto.getHoraInicio());
            requisicaoDto.setDuracaoEstimada(reuniaoDto.getDuracaoEstimada());
            requisicaoDto.setDescricao(reuniaoDto.getDescricao());

        }
        return this.verificaAtraso(requisicaoDto);
    }

    public RequisicaoMudancaDTO verificaAtraso(final RequisicaoMudancaDTO requisicaoDto) throws Exception {
        if (requisicaoDto == null) {
            return null;
        }

        long atrasoSLA = 0;

        if (requisicaoDto.getDataHoraTermino() != null) {
            final Timestamp dataHoraLimite = requisicaoDto.getDataHoraTermino();
            Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
            if (requisicaoDto.encerrada()) {
                if (requisicaoDto.getDataHoraConclusao() != null) {
                    dataHoraComparacao = requisicaoDto.getDataHoraConclusao();
                }
            }
            if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
                atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraComparacao, dataHoraLimite) / 1000;
                requisicaoDto.setPrazoHH(0);
                requisicaoDto.setPrazoMM(0);
            }
        }

        requisicaoDto.setAtraso(atrasoSLA);
        return requisicaoDto;
    }

    @Override
    public Collection findBySolictacaoServico(final RequisicaoMudancaDTO bean) throws ServiceException, LogicException {

        try {
            return this.getDao().listProblemaByIdSolicitacao(bean);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<RequisicaoMudancaDTO> obterMudancaCriticos(final Integer idItemConfiguracao) {
        try {
            final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();
            return requisicaoMudancaDao.listMudancaByIdItemConfiguracao(idItemConfiguracao);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RequisicaoMudancaDTO> listMudancaByIdItemConfiguracao(final Integer idItemConfiguracao) throws Exception {
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();

        return requisicaoMudancaDao.listMudancaByIdItemConfiguracao(idItemConfiguracao);
    }

    @Override
    public Collection<RequisicaoMudancaDTO> listaMudancaPorBaseConhecimento(final RequisicaoMudancaDTO mudanca) throws Exception {
        Collection<RequisicaoMudancaDTO> listaMudancaPorBaseConhecimento = null;
        final RequisicaoMudancaDao mudancaDao = this.getDao();
        try {
            listaMudancaPorBaseConhecimento = mudancaDao.listaMudancasPorBaseConhecimento(mudanca);
            if (listaMudancaPorBaseConhecimento != null) {
                for (final RequisicaoMudancaDTO mudancaDTO : listaMudancaPorBaseConhecimento) {

                    final Source source = new Source(mudancaDTO.getDescricao());
                    mudancaDTO.setDescricao(source.getTextExtractor().toString());
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return listaMudancaPorBaseConhecimento;

    }

    @Override
    public Collection<RequisicaoMudancaDTO> quantidadeMudancaPorBaseConhecimento(final RequisicaoMudancaDTO mudanca) throws Exception {
        final RequisicaoMudancaDao mudancaDao = this.getDao();
        return mudancaDao.quantidadeMudancaPorBaseConhecimento(mudanca);
    }

    @Override
    public Collection findByConhecimento(final BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, LogicException {
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();

        try {
            return requisicaoMudancaDao.findByConhecimento(baseConhecimentoDto);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ServicoContratoDTO findByIdContratoAndIdServico(final Integer idContrato, final Integer idServico) throws Exception {
        return null;
    }

    public Collection<RequisicaoMudancaDTO> listRequisicaoMudancaByCriterios(final RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
        return null;
    }

    @Override
    public Collection<PesquisaRequisicaoMudancaDTO> listRequisicaoMudancaByCriterios(final PesquisaRequisicaoMudancaDTO pesquisaRequisicaoMudancaDto) throws Exception {

        final Collection<PesquisaRequisicaoMudancaDTO> listRequisicaoMudancaByCriterios = this.getDao().listRequisicaoMudancaByCriterios(pesquisaRequisicaoMudancaDto);

        if (listRequisicaoMudancaByCriterios != null) {
            for (final PesquisaRequisicaoMudancaDTO requisicaoMudanca : listRequisicaoMudancaByCriterios) {

                final Source source = new Source(requisicaoMudanca.getDescricao());

                requisicaoMudanca.setDescricao(source.getTextExtractor().toString());
            }
        }

        return listRequisicaoMudancaByCriterios;
    }

    @Override
    public boolean verificarSeRequisicaoMudancaPossuiTipoMudanca(final Integer idTipoMudanca) throws Exception {
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();
        return requisicaoMudancaDao.verificarSeRequisicaoMudancaPossuiTipoMudanca(idTipoMudanca);
    }

    /**
     * Verifica se o item existe na lista.
     *
     * @param item
     * @param lista
     * @return
     */
    private boolean requisicaoMudancaServicoExisteNaLista(final RequisicaoMudancaServicoDTO item, final List<RequisicaoMudancaServicoDTO> lista) {
        if (lista == null) {
            return false;
        }
        for (final RequisicaoMudancaServicoDTO l : lista) {
            if (l.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se o item existe na lista.
     *
     * @param item
     * @param lista
     * @return
     */
    private boolean requisicaoMudancaICExisteNaLista(final RequisicaoMudancaItemConfiguracaoDTO item, final List<RequisicaoMudancaItemConfiguracaoDTO> lista) {
        if (lista == null) {
            return false;
        }
        for (final RequisicaoMudancaItemConfiguracaoDTO l : lista) {
            if (l.equals(item)) {
                return true;
            }
        }
        return false;
    }

    private String getStatusAtual(final Integer id) throws ServiceException, Exception {
        RequisicaoMudancaDTO reqMudanca = new RequisicaoMudancaDTO();
        reqMudanca.setIdRequisicaoMudanca(id);
        reqMudanca = (RequisicaoMudancaDTO) this.getDao().restore(reqMudanca);
        final String res = reqMudanca.getStatus();
        return res;

    }

    private RequisicaoMudancaDTO getRequisicaoAtual(final Integer id) throws ServiceException, Exception {
        RequisicaoMudancaDTO reqMudanca = new RequisicaoMudancaDTO();
        reqMudanca.setIdRequisicaoMudanca(id);
        reqMudanca = (RequisicaoMudancaDTO) this.getDao().restore(reqMudanca);
        return reqMudanca;

    }

    @Override
    public void suspende(final UsuarioDTO usuarioDto, final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        this.suspende(usuarioDto, requisicaoMudancaDTO, tc);
        tc.commit();
        tc.close();
    }

    public void suspende(final UsuarioDTO usuarioDto, final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception {
        final RequisicaoMudancaDTO requisicaoMudancaAuxiliarDto = this.restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca(), tc);
        requisicaoMudancaAuxiliarDto.setIdJustificativa(requisicaoMudancaDto.getIdJustificativa());
        requisicaoMudancaAuxiliarDto.setComplementoJustificativa(requisicaoMudancaDto.getComplementoJustificativa());
        new ExecucaoMudancaServiceEjb().suspende(usuarioDto, requisicaoMudancaDto, tc);
    }

    @Override
    public void reativa(final UsuarioDTO usuarioDto, final RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        this.reativa(usuarioDto, requisicaoMudancaDto, tc);
        tc.commit();
        tc.close();
    }

    public void reativa(final UsuarioDTO usuarioDto, final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception {
        final RequisicaoMudancaDTO requisicaoMudancaAuxDto = this.restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca(), tc);
        new ExecucaoMudancaServiceEjb().reativa(usuarioDto, requisicaoMudancaAuxDto, tc);
    }

    @Override
    public List<RequisicaoMudancaDTO> listMudancaByIdSolicitacao(final RequisicaoMudancaDTO bean) throws Exception {
        return this.getDao().listMudancaByIdSolicitacao(bean);
    }

    @Override
    public boolean validacaoAvancaFluxo(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception {
        final AprovacaoMudancaDTO aprovacaoMudancaDto = new AprovacaoMudancaDTO();
        final AprovacaoMudancaDao dao = new AprovacaoMudancaDao();
        dao.setTransactionControler(tc);
        if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
            aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            aprovacaoMudancaDto.setVoto("A");
            aprovacaoMudancaDto.setQuantidadeVotoAprovada(dao.quantidadeAprovacaoMudancaPorVotoAprovada(aprovacaoMudancaDto, requisicaoMudancaDto.getIdGrupoComite()));
            aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            aprovacaoMudancaDto.setVoto("R");
            aprovacaoMudancaDto.setQuantidadeVotoRejeitada(dao.quantidadeAprovacaoMudancaPorVotoRejeitada(aprovacaoMudancaDto, requisicaoMudancaDto.getIdGrupoComite()));
            aprovacaoMudancaDto.setQuantidadeAprovacaoMudanca(dao.quantidadeDeEmpregdosPorGrupo(requisicaoMudancaDto.getIdGrupoComite()));

        }
        if (aprovacaoMudancaDto.getQuantidadeVotoAprovada() > 0) {

            if (aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca().intValue() == aprovacaoMudancaDto.getQuantidadeVotoAprovada()) {
                return true;
            } else {
                if (aprovacaoMudancaDto.getQuantidadeVotoAprovada() >= aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca() / 2 + 1) {
                    return true;
                }
            }
        } else if (aprovacaoMudancaDto.getQuantidadeVotoRejeitada() > 0
                && aprovacaoMudancaDto.getQuantidadeVotoRejeitada() > aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca() / 2) {
            requisicaoMudancaDto.setStatus("Rejeitada");
            return true;
        }

        return false;

    }

    public boolean validacaoAvancaFluxoProposta(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception {
        final AprovacaoPropostaDTO aprovacaoPropostaDto = new AprovacaoPropostaDTO();
        final AprovacaoPropostaDao dao = new AprovacaoPropostaDao();
        dao.setTransactionControler(tc);
        if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
            aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            aprovacaoPropostaDto.setVoto("A");
            aprovacaoPropostaDto.setQuantidadeVotoAprovada(dao.quantidadeAprovacaoPropostaPorVotoAprovada(aprovacaoPropostaDto, requisicaoMudancaDto.getIdGrupoComite()));
            aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            aprovacaoPropostaDto.setVoto("R");
            aprovacaoPropostaDto.setQuantidadeVotoRejeitada(dao.quantidadeAprovacaoPropostaPorVotoAprovada(aprovacaoPropostaDto, requisicaoMudancaDto.getIdGrupoComite()));
            aprovacaoPropostaDto.setQuantidadeAprovacaoProposta(dao.quantidadeDeEmpregdosPorGrupo(requisicaoMudancaDto.getIdGrupoComite()));

        }
        if (aprovacaoPropostaDto.getQuantidadeVotoAprovada() > 0) {

            if (aprovacaoPropostaDto.getQuantidadeAprovacaoProposta().intValue() == aprovacaoPropostaDto.getQuantidadeVotoAprovada()) {
                requisicaoMudancaDto.setVotacaoPropostaAprovadaAux("S");
                requisicaoMudancaDto.setFase("Registrada");
                return true;
            } else if (aprovacaoPropostaDto.getQuantidadeVotoAprovada() >= aprovacaoPropostaDto.getQuantidadeAprovacaoProposta() / 2 + 1) {
                requisicaoMudancaDto.setVotacaoPropostaAprovadaAux("S");
                requisicaoMudancaDto.setFase("Registrada");
                return true;
            }

        } else if (aprovacaoPropostaDto.getQuantidadeVotoRejeitada() > 0
                && aprovacaoPropostaDto.getQuantidadeVotoRejeitada() >= aprovacaoPropostaDto.getQuantidadeAprovacaoProposta() / 2) {
            requisicaoMudancaDto.setVotacaoPropostaAprovadaAux("N");
            requisicaoMudancaDto.setStatus("Rejeitada");
            return true;
        }

        return false;
    }

    @Override
    public String verificaAprovacaoProposta(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception {
        final String aprovado = "aprovado";
        final String reprovado = "reprovado";
        final String aquardando = "aquardando";
        final AprovacaoPropostaDTO aprovacaoPropostaDto = new AprovacaoPropostaDTO();
        final AprovacaoPropostaDao dao = new AprovacaoPropostaDao();
        dao.setTransactionControler(tc);
        if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
            aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            aprovacaoPropostaDto.setVoto("A");
            aprovacaoPropostaDto.setQuantidadeVotoAprovada(dao.quantidadeAprovacaoPropostaPorVotoAprovada(aprovacaoPropostaDto, requisicaoMudancaDto.getIdGrupoComite()));
            aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            aprovacaoPropostaDto.setVoto("R");
            aprovacaoPropostaDto.setQuantidadeVotoRejeitada(dao.quantidadeAprovacaoPropostaPorVotoAprovada(aprovacaoPropostaDto, requisicaoMudancaDto.getIdGrupoComite()));
            aprovacaoPropostaDto.setQuantidadeAprovacaoProposta(dao.quantidadeDeEmpregdosPorGrupo(requisicaoMudancaDto.getIdGrupoComite()));

        }

        if (aprovacaoPropostaDto.getQuantidadeVotoAprovada() > 0) {
            if (aprovacaoPropostaDto.getQuantidadeAprovacaoProposta().intValue() == aprovacaoPropostaDto.getQuantidadeVotoAprovada()) {
                return aprovado;
            }

            if (aprovacaoPropostaDto.getQuantidadeVotoAprovada() >= aprovacaoPropostaDto.getQuantidadeAprovacaoProposta() / 2 + 1) {
                return aprovado;
            }

            if (aprovacaoPropostaDto.getQuantidadeVotoRejeitada() > aprovacaoPropostaDto.getQuantidadeAprovacaoProposta() / 2) {
                return reprovado;
            }
        }
        return aquardando;
    }

    @Override
    public String verificaAprovacaoMudanca(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) throws Exception {
        final String aprovado = "aprovado";
        final String reprovado = "reprovado";
        final String aquardando = "aquardando";
        final AprovacaoMudancaDTO aprovacaoMudancaDto = new AprovacaoMudancaDTO();
        final AprovacaoMudancaDao dao = new AprovacaoMudancaDao();
        dao.setTransactionControler(tc);
        if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
            aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            aprovacaoMudancaDto.setVoto("A");
            aprovacaoMudancaDto.setQuantidadeVotoAprovada(dao.quantidadeAprovacaoMudancaPorVotoAprovada(aprovacaoMudancaDto, requisicaoMudancaDto.getIdGrupoComite()));
            aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            aprovacaoMudancaDto.setVoto("R");
            aprovacaoMudancaDto.setQuantidadeVotoRejeitada(dao.quantidadeAprovacaoMudancaPorVotoRejeitada(aprovacaoMudancaDto, requisicaoMudancaDto.getIdGrupoComite()));
            aprovacaoMudancaDto.setQuantidadeAprovacaoMudanca(dao.quantidadeDeEmpregdosPorGrupo(requisicaoMudancaDto.getIdGrupoComite()));
        }

        if (aprovacaoMudancaDto.getQuantidadeVotoAprovada() > 0) {
            if (aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca().intValue() == aprovacaoMudancaDto.getQuantidadeVotoAprovada()) {
                return aprovado;
            }

            if (aprovacaoMudancaDto.getQuantidadeVotoAprovada() >= aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca() / 2 + 1) {
                return aprovado;
            }

            if (aprovacaoMudancaDto.getQuantidadeVotoRejeitada() > aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca() / 2) {
                return reprovado;

            }
        }
        return aquardando;
    }

    public void gravaInformacoesGED(final RequisicaoMudancaDTO requisicaomudacaDTO, final TransactionControler tc, final HistoricoMudancaDTO historicoMudancaDTO) throws Exception {
        final Collection<UploadDTO> colArquivosUpload = requisicaomudacaDTO.getColArquivosUpload();
        final HistoricoGEDDTO historicoGEDDTO = new HistoricoGEDDTO();
        final HistoricoGEDDao historicoGEDDao = new HistoricoGEDDao();

        // Setando a transaction no GED
        final ControleGEDDao controleGEDDao = new ControleGEDDao();
        if (tc != null) {
            controleGEDDao.setTransactionControler(tc);
            historicoGEDDao.setTransactionControler(tc);
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
        if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados)) {
            prontuarioGedInternoBancoDados = "N";
        }
        String pasta = "";
        if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
            pasta = controleGEDDao.getProximaPastaArmazenar();
            File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa());
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa() + "/" + pasta);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }

        // Grava informações do upload principal.
        if (colArquivosUpload != null) {
            for (final UploadDTO upDto : colArquivosUpload) {
                final UploadDTO uploadDTO = upDto;
                ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

                Integer idControleGed = uploadDTO.getIdControleGED();

                historicoGEDDTO.setIdRequisicaoMudanca(requisicaomudacaDTO.getIdRequisicaoMudanca());
                historicoGEDDTO.setIdTabela(ControleGEDDTO.TABELA_REQUISICAOMUDANCA);
                if (historicoMudancaDTO.getIdHistoricoMudanca() != null) {
                    historicoGEDDTO.setIdHistoricoMudanca(null);
                } else {
                    historicoGEDDTO.setIdHistoricoMudanca(-1);
                }
                historicoGEDDao.create(historicoGEDDTO);

                controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_REQUISICAOMUDANCA);
                controleGEDDTO.setId(requisicaomudacaDTO.getIdRequisicaoMudanca());
                controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());
                controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));
                controleGEDDTO.setPasta(pasta);
                controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile());
                upDto.setTemporario("S");
                uploadDTO.setTemporario("S");
                if (upDto.getTemporario() != null) {
                    if (!uploadDTO.getTemporario().equalsIgnoreCase("S")) { // Se nao //
                        continue;
                    }
                } else {
                    continue;
                }

                // Se utiliza GEDinterno e eh BD
                if (PRONTUARIO_GED_INTERNO != null && PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados.trim())) {
                    controleGEDDTO.setPathArquivo(uploadDTO.getPath()); // Isso vai fazer a gravacao no BD. dento do create abaixo.
                } else {
                    controleGEDDTO.setPathArquivo(null);
                }
                // esse bloco grava a tabela de historicos de anexos:

                boolean existe = false;
                if (idControleGed != null) {
                    final Collection<HistoricoGEDDTO> colAux = historicoGEDDao.listByIdTabelaAndIdLiberacaoEDataFim(ControleGEDDTO.TABELA_REQUISICAOMUDANCA,
                            requisicaomudacaDTO.getIdRequisicaoMudanca());
                    if (colAux != null && colAux.size() > 0) {
                        for (final HistoricoGEDDTO historicoGedDTOAux : colAux) {
                            if (idControleGed.intValue() == historicoGedDTOAux.getIdControleGed().intValue()) {
                                idControleGed = historicoGedDTOAux.getIdControleGed();
                                existe = true;
                                break;
                            }
                        }
                    }
                }

                if (!existe) {
                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);
                    controleGEDDTO.setId(historicoGEDDTO.getIdLigacaoHistoricoGed());
                    idControleGed = controleGEDDTO.getIdControleGED();
                }
                historicoGEDDTO.setIdControleGed(idControleGed);
                historicoGEDDao.update(historicoGEDDTO);

                // Se utiliza GED interno e nao eh BD
                if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
                    if (controleGEDDTO != null) {
                        try {
                            final File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa() + "/" + pasta + "/"
                                    + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDTO.getNameFile()));
                            CriptoUtils.encryptFile(uploadDTO.getPath(),
                                    PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", System
                                            .getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
                            arquivo.delete();
                        } catch (final Exception e) {

                        }

                    }
                }
            }
        }
    }

    @Override
    public void gravaPlanoDeReversaoGED(final RequisicaoMudancaDTO requisicaomudacaDTO, final TransactionControler tc, final HistoricoMudancaDTO historicoMudancaDTO)
            throws Exception {
        final Collection<UploadDTO> colArquivosUpload = requisicaomudacaDTO.getColUploadPlanoDeReversaoGED();

        // Setando a transaction no GED
        final ControleGEDDao controleGEDDao = new ControleGEDDao();
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
        if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados)) {
            prontuarioGedInternoBancoDados = "N";
        }
        String pasta = "";
        if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
            pasta = controleGEDDao.getProximaPastaArmazenar();
            File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa());
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa() + "/" + pasta);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }

        // Grava informações do upload principal.
        if (colArquivosUpload != null) {
            for (final UploadDTO upDto : colArquivosUpload) {
                final UploadDTO uploadDTO = upDto;
                ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

                Integer idControleGed = uploadDTO.getIdControleGED();

                controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA);
                controleGEDDTO.setId(historicoMudancaDTO.getIdRequisicaoMudanca());
                controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());
                controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));
                controleGEDDTO.setPasta(pasta);
                controleGEDDTO.setVersao(uploadDTO.getVersao());
                controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile());
                upDto.setTemporario("S");
                uploadDTO.setTemporario("S");
                if (upDto.getTemporario() != null) {
                    if (!uploadDTO.getTemporario().equalsIgnoreCase("S")) { // Se
                                                                            // nao
                                                                            // //
                        continue;
                    }
                } else {
                    continue;
                }

                // Se utiliza GED interno e eh BD
                if (PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados.trim())) {
                    controleGEDDTO.setPathArquivo(uploadDTO.getPath()); // Isso vai fazer a gravacao no BD. dento do create abaixo.
                } else {
                    controleGEDDTO.setPathArquivo(null);
                }
                // esse bloco grava a tabela de historicos de anexos:

                boolean existe = false;
                if (idControleGed != null) {
                    final Collection<ControleGEDDTO> colAux = controleGEDDao.listByIdTabelaAndID(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA,
                            requisicaomudacaDTO.getIdRequisicaoMudanca());
                    if (colAux != null && colAux.size() > 0) {
                        for (final ControleGEDDTO controleGedDTOAux : colAux) {
                            if (idControleGed.intValue() == controleGedDTOAux.getIdControleGED().intValue()) {
                                idControleGed = controleGedDTOAux.getIdControleGED();
                                existe = true;
                                break;
                            }
                        }
                    }
                }

                if (!existe) {
                    controleGEDDTO.setId(requisicaomudacaDTO.getIdRequisicaoMudanca());
                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);
                    idControleGed = controleGEDDTO.getIdControleGED();
                }

                // Se utiliza GED interno e nao eh BD
                if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
                    if (controleGEDDTO != null) {
                        try {
                            final File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa() + "/" + pasta + "/"
                                    + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDTO.getNameFile()));
                            CriptoUtils.encryptFile(uploadDTO.getPath(),
                                    PRONTUARIO_GED_DIRETORIO + "/" + requisicaomudacaDTO.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", System
                                            .getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
                            arquivo.delete();
                        } catch (final Exception e) {

                        }

                    }
                }
            }
        }

        final Collection<ControleGEDDTO> colAnexo = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA,
                requisicaomudacaDTO.getIdRequisicaoMudanca());
        if (colAnexo != null) {
            for (final ControleGEDDTO dtoGed : colAnexo) {
                boolean b = false;
                for (final Object element : colArquivosUpload) {
                    final UploadDTO uploadDTO = (UploadDTO) element;
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
    public Collection listaQuantidadeMudancaPorImpacto(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();
        return requisicaoMudancaDao.listaQuantidadeMudancaPorImpacto(requisicaoMudancaDTO);
    }

    @Override
    public Collection listaQuantidadeMudancaPorPeriodo(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();
        return requisicaoMudancaDao.listaQuantidadeMudancaPorPeriodo(requisicaoMudancaDTO);
    }

    public String negritoHtml(final String string) {
        return "<style isBold=\"true\" pdfFontName=\"Helvetica-Bold\">" + string + "</style>";
    }

    @Override
    public Collection listaIdETituloMudancasPorPeriodo(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();
        return requisicaoMudancaDao.listaIdETituloMudancasPorPeriodo(requisicaoMudancaDTO);
    }

    @Override
    public Collection listaQuantidadeMudancaPorProprietario(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();
        return requisicaoMudancaDao.listaQuantidadeMudancaPorProprietario(requisicaoMudancaDTO);
    }

    @Override
    public Collection listaQuantidadeMudancaPorSolicitante(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();
        return requisicaoMudancaDao.listaQuantidadeMudancaPorSolicitante(requisicaoMudancaDTO);
    }

    @Override
    public Collection listaQuantidadeMudancaPorStatus(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();
        return requisicaoMudancaDao.listaQuantidadeMudancaPorStatus(requisicaoMudancaDTO);
    }

    @Override
    public Collection listaQuantidadeMudancaPorUrgencia(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();
        return requisicaoMudancaDao.listaQuantidadeMudancaPorUrgencia(requisicaoMudancaDTO);
    }

    @Override
    public Collection listaQuantidadeSemAprovacaoPorPeriodo(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();
        return requisicaoMudancaDao.listaQuantidadeSemAprovacaoPorPeriodo(requisicaoMudancaDTO);
    }

    @Override
    public Collection listaQuantidadeERelacionamentos(final HttpServletRequest request, final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final RequisicaoMudancaDao requisicaoMudancaDao = this.getDao();

        final List<RequisicaoMudancaDTO> listaIdETituloMudancasPorPeriodo = new ArrayList<RequisicaoMudancaDTO>(
                requisicaoMudancaDao.listaIdETituloMudancasPorPeriodo(requisicaoMudancaDTO));
        final Collection<RequisicaoMudancaDTO> listaMudancaIncidente = requisicaoMudancaDao.listaMudancaIncidente(requisicaoMudancaDTO);
        final Collection<RequisicaoMudancaDTO> listaMudancaServico = requisicaoMudancaDao.listaMudancaServico(requisicaoMudancaDTO);
        final Collection<RequisicaoMudancaDTO> listaMudancaProblema = requisicaoMudancaDao.listaMudancaProblema(requisicaoMudancaDTO);
        final Collection<RequisicaoMudancaDTO> listaMudancaGrupo = requisicaoMudancaDao.listaMudancaGrupo(requisicaoMudancaDTO);
        final Collection<RequisicaoMudancaDTO> listaMudancaConhecimento = requisicaoMudancaDao.listaMudancaConhecimento(requisicaoMudancaDTO);
        final Collection<RequisicaoMudancaDTO> listaMudancaItemConfiguracao = requisicaoMudancaDao.listaMudancaItemConfiguracao(requisicaoMudancaDTO);

        for (int i = 0, j; i < listaIdETituloMudancasPorPeriodo.size(); i++) {
            final int tamanhoInicio = listaIdETituloMudancasPorPeriodo.size();
            j = i;
            for (final RequisicaoMudancaDTO incidente : listaMudancaIncidente) {
                final RequisicaoMudancaDTO aux = listaIdETituloMudancasPorPeriodo.get(j);
                if (aux.getIdRequisicaoMudanca() == incidente.getIdRequisicaoMudanca()) {
                    if (aux.getIncidente() == null || aux.getIncidente().isEmpty()) {
                        listaIdETituloMudancasPorPeriodo.get(j).setIncidente(incidente.getIncidente());
                    } else if (j + 1 == listaIdETituloMudancasPorPeriodo.size()
                            || listaIdETituloMudancasPorPeriodo.get(j + 1).getIdRequisicaoMudanca() != aux.getIdRequisicaoMudanca()) {
                        listaIdETituloMudancasPorPeriodo.add(++j, incidente);
                    } else {
                        listaIdETituloMudancasPorPeriodo.get(++j).setIncidente(incidente.getIncidente());
                    }
                }
            }
            j = i;
            for (final RequisicaoMudancaDTO servico : listaMudancaServico) {
                final RequisicaoMudancaDTO aux = listaIdETituloMudancasPorPeriodo.get(j);
                if (aux.getIdRequisicaoMudanca() == servico.getIdRequisicaoMudanca()) {
                    if (aux.getServico() == null || aux.getServico().isEmpty()) {
                        listaIdETituloMudancasPorPeriodo.get(j).setServico(servico.getServico());
                    } else if (j + 1 == listaIdETituloMudancasPorPeriodo.size()
                            || listaIdETituloMudancasPorPeriodo.get(j + 1).getIdRequisicaoMudanca() != aux.getIdRequisicaoMudanca()) {
                        listaIdETituloMudancasPorPeriodo.add(++j, servico);
                    } else {
                        listaIdETituloMudancasPorPeriodo.get(++j).setServico(servico.getIncidente());
                    }
                }
            }
            j = i;
            for (final RequisicaoMudancaDTO problema : listaMudancaProblema) {
                final RequisicaoMudancaDTO aux = listaIdETituloMudancasPorPeriodo.get(j);
                if (aux.getIdRequisicaoMudanca() == problema.getIdRequisicaoMudanca()) {
                    if (aux.getProblema() == null || aux.getProblema().isEmpty()) {
                        listaIdETituloMudancasPorPeriodo.get(j).setProblema(problema.getProblema());
                    } else if (j + 1 == listaIdETituloMudancasPorPeriodo.size()
                            || listaIdETituloMudancasPorPeriodo.get(j + 1).getIdRequisicaoMudanca() != aux.getIdRequisicaoMudanca()) {
                        listaIdETituloMudancasPorPeriodo.add(++j, problema);
                    } else {
                        listaIdETituloMudancasPorPeriodo.get(++j).setProblema(problema.getIncidente());
                    }
                }
            }
            j = i;
            for (final RequisicaoMudancaDTO grupo : listaMudancaGrupo) {
                final RequisicaoMudancaDTO aux = listaIdETituloMudancasPorPeriodo.get(j);
                if (aux.getIdRequisicaoMudanca() == grupo.getIdRequisicaoMudanca()) {
                    if (aux.getGrupoMudanca() == null || aux.getGrupoMudanca().isEmpty()) {
                        listaIdETituloMudancasPorPeriodo.get(j).setGrupoMudanca(grupo.getGrupoMudanca());
                    } else if (j + 1 == listaIdETituloMudancasPorPeriodo.size()
                            || listaIdETituloMudancasPorPeriodo.get(j + 1).getIdRequisicaoMudanca() != aux.getIdRequisicaoMudanca()) {
                        listaIdETituloMudancasPorPeriodo.add(++j, grupo);
                    } else {
                        listaIdETituloMudancasPorPeriodo.get(++j).setGrupoMudanca(grupo.getIncidente());
                    }
                }
            }
            j = i;
            for (final RequisicaoMudancaDTO conhecimento : listaMudancaConhecimento) {
                final RequisicaoMudancaDTO aux = listaIdETituloMudancasPorPeriodo.get(j);
                if (aux.getIdRequisicaoMudanca() == conhecimento.getIdRequisicaoMudanca()) {
                    if (aux.getConhecimento() == null || aux.getConhecimento().isEmpty()) {
                        listaIdETituloMudancasPorPeriodo.get(j).setConhecimento(conhecimento.getConhecimento());
                    } else if (j + 1 == listaIdETituloMudancasPorPeriodo.size()
                            || listaIdETituloMudancasPorPeriodo.get(j + 1).getIdRequisicaoMudanca() != aux.getIdRequisicaoMudanca()) {
                        listaIdETituloMudancasPorPeriodo.add(++j, conhecimento);
                    } else {
                        listaIdETituloMudancasPorPeriodo.get(++j).setConhecimento(conhecimento.getIncidente());
                    }
                }
            }
            j = i;
            for (final RequisicaoMudancaDTO itemConfiguracao : listaMudancaItemConfiguracao) {
                final RequisicaoMudancaDTO aux = listaIdETituloMudancasPorPeriodo.get(j);
                if (aux.getIdRequisicaoMudanca() == itemConfiguracao.getIdRequisicaoMudanca()) {
                    if (aux.getItemConfiguracao() == null || aux.getItemConfiguracao().isEmpty()) {
                        listaIdETituloMudancasPorPeriodo.get(j).setItemConfiguracao(itemConfiguracao.getItemConfiguracao());
                    } else if (j + 1 == listaIdETituloMudancasPorPeriodo.size()
                            || listaIdETituloMudancasPorPeriodo.get(j + 1).getIdRequisicaoMudanca() != aux.getIdRequisicaoMudanca()) {
                        listaIdETituloMudancasPorPeriodo.add(++j, itemConfiguracao);
                    } else {
                        listaIdETituloMudancasPorPeriodo.get(++j).setItemConfiguracao(itemConfiguracao.getIncidente());
                    }
                }
            }

            i += listaIdETituloMudancasPorPeriodo.size() - tamanhoInicio;
        }

        if (!listaIdETituloMudancasPorPeriodo.isEmpty()) {
            Integer idRequisicaoMudancaAnterior = listaIdETituloMudancasPorPeriodo.get(0).getIdRequisicaoMudanca();

            Integer totalIncidente = 0;
            Integer totalServico = 0;
            Integer totalProblema = 0;
            Integer totalConhecimento = 0;
            Integer totalItemConfiguracao = 0;

            for (int i = 0; i < listaIdETituloMudancasPorPeriodo.size(); i++) {
                if (listaIdETituloMudancasPorPeriodo.get(i).getIdRequisicaoMudanca().equals(idRequisicaoMudancaAnterior)) {
                    if (listaIdETituloMudancasPorPeriodo.get(i).getIncidente() != null && !listaIdETituloMudancasPorPeriodo.get(i).getIncidente().isEmpty()) {
                        totalIncidente++;
                    }
                    if (listaIdETituloMudancasPorPeriodo.get(i).getServico() != null && !listaIdETituloMudancasPorPeriodo.get(i).getServico().isEmpty()) {
                        totalServico++;
                    }
                    if (listaIdETituloMudancasPorPeriodo.get(i).getProblema() != null && !listaIdETituloMudancasPorPeriodo.get(i).getProblema().isEmpty()) {
                        totalProblema++;
                    }
                    if (listaIdETituloMudancasPorPeriodo.get(i).getConhecimento() != null && !listaIdETituloMudancasPorPeriodo.get(i).getConhecimento().isEmpty()) {
                        totalConhecimento++;
                    }
                    if (listaIdETituloMudancasPorPeriodo.get(i).getItemConfiguracao() != null && !listaIdETituloMudancasPorPeriodo.get(i).getItemConfiguracao().isEmpty()) {
                        totalItemConfiguracao++;
                    }
                } else {
                    final RequisicaoMudancaDTO mudancaDTO = new RequisicaoMudancaDTO();
                    mudancaDTO.setTitulo(this.negritoHtml(UtilI18N.internacionaliza(request, "citcorpore.comum.totalMudanca")));
                    mudancaDTO.setIncidente(this.negritoHtml(totalIncidente.toString()));
                    mudancaDTO.setServico(this.negritoHtml(totalServico.toString()));
                    mudancaDTO.setProblema(this.negritoHtml(totalProblema.toString()));
                    mudancaDTO.setConhecimento(this.negritoHtml(totalConhecimento.toString()));
                    mudancaDTO.setItemConfiguracao(this.negritoHtml(totalItemConfiguracao.toString()));

                    listaIdETituloMudancasPorPeriodo.add(i++, mudancaDTO);

                    totalIncidente = 0;
                    totalServico = 0;
                    totalProblema = 0;
                    totalConhecimento = 0;
                    totalItemConfiguracao = 0;

                    if (listaIdETituloMudancasPorPeriodo.get(i).getIncidente() != null && !listaIdETituloMudancasPorPeriodo.get(i).getIncidente().isEmpty()) {
                        totalIncidente++;
                    }
                    if (listaIdETituloMudancasPorPeriodo.get(i).getServico() != null && !listaIdETituloMudancasPorPeriodo.get(i).getServico().isEmpty()) {
                        totalServico++;
                    }
                    if (listaIdETituloMudancasPorPeriodo.get(i).getProblema() != null && !listaIdETituloMudancasPorPeriodo.get(i).getProblema().isEmpty()) {
                        totalProblema++;
                    }
                    if (listaIdETituloMudancasPorPeriodo.get(i).getConhecimento() != null && !listaIdETituloMudancasPorPeriodo.get(i).getConhecimento().isEmpty()) {
                        totalConhecimento++;
                    }
                    if (listaIdETituloMudancasPorPeriodo.get(i).getItemConfiguracao() != null && !listaIdETituloMudancasPorPeriodo.get(i).getItemConfiguracao().isEmpty()) {
                        totalItemConfiguracao++;
                    }

                    idRequisicaoMudancaAnterior = listaIdETituloMudancasPorPeriodo.get(i).getIdRequisicaoMudanca();
                }
            }
            final RequisicaoMudancaDTO mudancaDTO = new RequisicaoMudancaDTO();
            mudancaDTO.setTitulo(this.negritoHtml(UtilI18N.internacionaliza(request, "citcorpore.comum.totalMudanca")));
            mudancaDTO.setIncidente(this.negritoHtml(totalIncidente.toString()));
            mudancaDTO.setServico(this.negritoHtml(totalServico.toString()));
            mudancaDTO.setProblema(this.negritoHtml(totalProblema.toString()));
            mudancaDTO.setConhecimento(this.negritoHtml(totalConhecimento.toString()));
            mudancaDTO.setItemConfiguracao(this.negritoHtml(totalItemConfiguracao.toString()));

            listaIdETituloMudancasPorPeriodo.add(mudancaDTO);
        }

        return listaIdETituloMudancasPorPeriodo;
    }

    public Timestamp MontardataHoraAgendamentoInicial(final RequisicaoMudancaDTO requisicaoMudancaDto) {
        final Timestamp dataHoraInicio = requisicaoMudancaDto.getDataHoraInicioAgendada();
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String horaAgendamentoInicialSTR = format.format(dataHoraInicio);
        horaAgendamentoInicialSTR = horaAgendamentoInicialSTR.substring(0, 11);
        final String horaAgendamentoInicial = requisicaoMudancaDto.getHoraAgendamentoInicial();
        final String dia = horaAgendamentoInicialSTR.substring(0, 2);
        final String mes = horaAgendamentoInicialSTR.substring(3, 5);
        final String ano = horaAgendamentoInicialSTR.substring(6, 10);
        final String dataHoraMontada = ano + "-" + mes + "-" + dia + " " + horaAgendamentoInicial + ":00.0";
        final Timestamp dataHoraInicial = Timestamp.valueOf(dataHoraMontada);

        return dataHoraInicial;
    }

    public Timestamp MontardataHoraAgendamentoFinal(final RequisicaoMudancaDTO requisicaoMudancaDto) {
        final Timestamp dataHoraFim = requisicaoMudancaDto.getDataHoraTerminoAgendada();
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        final String horaAgendamentoFinalSTR = format.format(dataHoraFim);
        final String horaAgendamentoFinal = requisicaoMudancaDto.getHoraAgendamentoFinal();
        final String dia = horaAgendamentoFinalSTR.substring(0, 2);
        final String mes = horaAgendamentoFinalSTR.substring(3, 5);
        final String ano = horaAgendamentoFinalSTR.substring(6, 10);
        final String dataHoraMontada = ano + "-" + mes + "-" + dia + " " + horaAgendamentoFinal + ":00.0";
        final Timestamp dataHoraFinal = Timestamp.valueOf(dataHoraMontada);

        return dataHoraFinal;
    }

    public void calculaTempoAtraso(final RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
        requisicaoMudancaDto.setPrazoHH(0);
        requisicaoMudancaDto.setPrazoMM(0);
        if (requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null) {
            final Timestamp dataHoraInicioComparacao = requisicaoMudancaDto.getDataHoraInicioAgendada();
            final Timestamp dataHoraFinalComparacao = requisicaoMudancaDto.getDataHoraTerminoAgendada();
            if (dataHoraFinalComparacao.compareTo(dataHoraInicioComparacao) > 0) {
                final long atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraFinalComparacao, dataHoraInicioComparacao) / 1000;

                final String hora = Util.getHoraStr(new Double(atrasoSLA) / 3600);
                final int tam = hora.length();
                requisicaoMudancaDto.setPrazoHH(new Integer(hora.substring(0, tam - 2)));
                requisicaoMudancaDto.setPrazoMM(new Integer(hora.substring(tam - 2, tam)));
            }
        }
    }

    public boolean seHoraInicialMenorQAtual(final RequisicaoMudancaDTO requisicaoMudancaDto) {
        boolean resultado = false;
        final Time horaAtual = UtilDatas.getHoraAtual();
        final Date dataAtual = UtilDatas.getDataAtual();
        String horaAtualStr = horaAtual.toString();
        final String dataAtualStr = dataAtual.toString();
        horaAtualStr = horaAtualStr.substring(0, 5);
        final Timestamp dataHoraInicio = requisicaoMudancaDto.getDataHoraInicioAgendada();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String dataAgendamentoFinalSTR = format.format(dataHoraInicio);
        if (dataAtualStr.equals(dataAgendamentoFinalSTR)) {
            String horaAgendamentoInicial = requisicaoMudancaDto.getHoraAgendamentoInicial();
            horaAgendamentoInicial = horaAgendamentoInicial.replaceAll(":", "");
            horaAtualStr = horaAtualStr.replaceAll(":", "");
            final int horaAtualInt = Integer.parseInt(horaAtualStr);
            final int horaAgendamentoInicialInt = Integer.parseInt(horaAgendamentoInicial);
            if (horaAgendamentoInicialInt < horaAtualInt) {
                resultado = true;
            }
        }
        return resultado;
    }

    public boolean seHoraFinalMenorQAtual(final RequisicaoMudancaDTO requisicaoMudancaDto) {
        boolean resultado = false;
        final Time horaAtual = UtilDatas.getHoraAtual();
        final Date dataAtual = UtilDatas.getDataAtual();
        String horaAtualStr = horaAtual.toString();
        final String dataAtualStr = dataAtual.toString();
        horaAtualStr = horaAtualStr.substring(0, 5);
        final Timestamp dataHoraFim = requisicaoMudancaDto.getDataHoraTerminoAgendada();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String dataAgendamentoFinalSTR = format.format(dataHoraFim);
        if (dataAtualStr.equals(dataAgendamentoFinalSTR)) {
            String horaAgendamentoFim = requisicaoMudancaDto.getHoraAgendamentoFinal();
            horaAgendamentoFim = horaAgendamentoFim.replaceAll(":", "");
            horaAtualStr = horaAtualStr.replaceAll(":", "");
            final int horaAtualInt = Integer.parseInt(horaAtualStr);
            final int horaAgendamentoFimInt = Integer.parseInt(horaAgendamentoFim);
            if (horaAgendamentoFimInt < horaAtualInt) {
                resultado = true;
            }
        }
        return resultado;
    }

    public boolean seHoraFinalMenorQHoraInicial(final RequisicaoMudancaDTO requisicaoMudancaDto) {
        boolean resultado = false;
        final Time horaAtual = UtilDatas.getHoraAtual();
        final Date dataAtual = UtilDatas.getDataAtual();
        String horaAtualStr = horaAtual.toString();
        final String dataAtualStr = dataAtual.toString();
        horaAtualStr = horaAtualStr.substring(0, 5);
        final Timestamp dataHoraInicio = requisicaoMudancaDto.getDataHoraInicioAgendada();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String dataAgendamentoInicialSTR = format.format(dataHoraInicio);
        final Timestamp dataHoraFim = requisicaoMudancaDto.getDataHoraTerminoAgendada();
        final String dataAgendamentoFinalSTR = format.format(dataHoraFim);
        String horaAgendamentoFim = requisicaoMudancaDto.getHoraAgendamentoFinal();
        String horaAgendamentoInicial = requisicaoMudancaDto.getHoraAgendamentoInicial();
        horaAgendamentoInicial = horaAgendamentoInicial.replaceAll(":", "");
        horaAgendamentoFim = horaAgendamentoFim.replaceAll(":", "");
        final int horaInicioInt = Integer.parseInt(horaAgendamentoInicial);
        final int horaFimInt = Integer.parseInt(horaAgendamentoFim);
        if (dataAtualStr.equals(dataAgendamentoFinalSTR) && dataAtualStr.equals(dataAgendamentoInicialSTR)) {
            if (horaInicioInt > horaFimInt) {
                resultado = true;
            }
        }
        if (dataAgendamentoInicialSTR.equals(dataAgendamentoFinalSTR)) {
            if (horaInicioInt > horaFimInt) {
                resultado = true;
            }
        }
        return resultado;
    }

    public boolean validacaoGrupoExecutor(final RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
        boolean resultado = false;

        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdGrupoAtual() != null && requisicaoMudancaDto.getIdTipoMudanca() != null) {
            final Integer idGrupoExecutor = requisicaoMudancaDto.getIdGrupoAtual();
            final Integer idTipoMudanca = requisicaoMudancaDto.getIdTipoMudanca();

            final PermissoesFluxoService permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);

            resultado = permissoesFluxoService.permissaoGrupoExecutor(idTipoMudanca, idGrupoExecutor);
        }
        return resultado;
    }

    @Override
    public String getUrlInformacoesComplementares(final RequisicaoMudancaDTO requisicaoLiberacaoDTO) throws Exception {
        String url = "";
        final TemplateSolicitacaoServicoDao templateDao = new TemplateSolicitacaoServicoDao();
        TemplateSolicitacaoServicoDTO templateDto = null;
        if (templateDto == null) {
            final String idTemplate = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.TEMPLATE_QUESTIONARIO, "13");
            if (idTemplate != null && !idTemplate.equals("")) {
                templateDto = new TemplateSolicitacaoServicoDTO();
                templateDto.setIdTemplate(new Integer(idTemplate));
                templateDto = (TemplateSolicitacaoServicoDTO) templateDao.restore(templateDto);
            }
        }
        if (templateDto != null) {
            url += templateDto.getUrlRecuperacao();
            url += "?";

            url = url.replaceAll("\n", "");
            url = url.replaceAll("\r", "");

            String editar = "S";
            if (requisicaoLiberacaoDTO.getIdRequisicaoMudanca() != null && requisicaoLiberacaoDTO.getIdRequisicaoMudanca().intValue() > 0) {
                url += "idRequisicao=" + requisicaoLiberacaoDTO.getIdRequisicaoMudanca() + "&";
                url += "IdTipoRequisicao=" + Enumerados.TipoRequisicao.LIBERCAO.getId() + "&";
                if (requisicaoLiberacaoDTO.getIdTipoAba() != null) {
                    url += "idTipoAba=" + requisicaoLiberacaoDTO.getIdTipoAba() + "&";
                }
                if (requisicaoLiberacaoDTO.getIdTarefa() == null) {
                    editar = "N";
                } else {
                    url += "idTarefa=" + requisicaoLiberacaoDTO.getIdTarefa() + "&";
                }
            }
            url += "&editar=" + editar;
        }
        return url;
    }

    @Override
    public boolean verificaPermissaoGrupoCancelar(final Integer idTipoMudança, final Integer idGrupo) throws ServiceException, Exception {
        boolean isOk = false;
        final TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, null);
        TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
        final PermissoesFluxoDao permissoesDao = new PermissoesFluxoDao();

        tipoMudancaDto.setIdTipoMudanca(idTipoMudança);
        tipoMudancaDto = (TipoMudancaDTO) tipoMudancaService.restore(tipoMudancaDto);
        if (tipoMudancaDto != null) {
            final PermissoesFluxoDTO permissoesDto = permissoesDao.permissaoGrupoCancelar(idGrupo, tipoMudancaDto.getIdTipoFluxo());
            if (permissoesDto != null && permissoesDto.getCancelar() != null && permissoesDto.getCancelar().equalsIgnoreCase("S")) {
                isOk = true;
            }
        }

        return isOk;
    }

    /* ################################################# HISTORICO MUDANCA ################################################# */

    public HistoricoMudancaDTO createHistoricoMudanca(final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final HistoricoMudancaDTO historico = new HistoricoMudancaDTO();
        RequisicaoMudancaDTO requisicaoMudancaDTOAux = requisicaoMudancaDTO;
        final Integer idExecutormodificacao = requisicaoMudancaDTO.getUsuarioDto().getIdEmpregado();
        final RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
        requisicaoMudancaDTOAux = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoMudancaDTO);
        requisicaoMudancaDTOAux.setAlterarSituacao(requisicaoMudancaDTO.getAlterarSituacao());
        requisicaoMudancaDTOAux.setAcaoFluxo(requisicaoMudancaDTO.getAcaoFluxo());
        Reflexao.copyPropertyValues(requisicaoMudancaDTOAux, historico);
        historico.setIdExecutorModificacao(idExecutormodificacao);
        historico.setRegistroexecucao(requisicaoMudancaDTO.getRegistroexecucao());

        // esse bloco seta as informações de contato.
        ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDTO = new ContatoRequisicaoMudancaDTO();
        final ContatoRequisicaoMudancaService contatoRequisicaoMudancaService = (ContatoRequisicaoMudancaService) ServiceLocator.getInstance().getService(
                ContatoRequisicaoMudancaService.class, null);
        requisicaoMudancaDTO.setIdContatoRequisicaoMudanca(historico.getIdContatoRequisicaoMudanca());
        contatoRequisicaoMudancaDTO = contatoRequisicaoMudancaService.restoreContatosById(requisicaoMudancaDTO.getIdContatoRequisicaoMudanca());
        if (contatoRequisicaoMudancaDTO != null) {
            historico.setNomeContato(contatoRequisicaoMudancaDTO.getNomecontato());
            historico.setEmailSolicitante(contatoRequisicaoMudancaDTO.getEmailcontato());
            historico.setIdContatoRequisicaoMudanca(contatoRequisicaoMudancaDTO.getIdContatoRequisicaoMudanca());
            historico.setIdLocalidade(contatoRequisicaoMudancaDTO.getIdLocalidade());
            historico.setRamal(contatoRequisicaoMudancaDTO.getRamal());
            historico.setTelefoneContato(contatoRequisicaoMudancaDTO.getTelefonecontato());
            historico.setObservacao(contatoRequisicaoMudancaDTO.getObservacao());
        }

        HistoricoMudancaDTO ultVersao = new HistoricoMudancaDTO();
        ultVersao = this.getHistoricoMudancaDao().maxIdHistorico(requisicaoMudancaDTO);
        if (ultVersao.getIdHistoricoMudanca() != null) {
            ultVersao = (HistoricoMudancaDTO) this.getHistoricoMudancaDao().restore(ultVersao);
            historico.setHistoricoVersao(ultVersao.getHistoricoVersao() == null ? 1d : +new BigDecimal(ultVersao.getHistoricoVersao() + 0.1f).setScale(1, BigDecimal.ROUND_DOWN)
                    .floatValue());
        } else {
            historico.setHistoricoVersao(1d);
        }

        historico.setDataHoraModificacao(UtilDatas.getDataHoraAtual());
        if (historico.getIdExecutorModificacao() == null) {
            historico.setIdExecutorModificacao(1);
        } else {
            historico.setIdExecutorModificacao(idExecutormodificacao);
        }

        return historico;
    }

    public HistoricoMudancaDao getHistoricoMudancaDao() throws ServiceException {
        return (HistoricoMudancaDao) this.getHistoricoMudDao();
    }

    protected CrudDAO getHistoricoMudDao() throws ServiceException {
        return new HistoricoMudancaDao();
    }

    public Collection<RequisicaoMudancaResponsavelDTO> listarColResponsaveis(final HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception {
        final RequisicaoMudancaResponsavelDao requisicaoMudancaResponsavelDao = new RequisicaoMudancaResponsavelDao();
        final Collection<RequisicaoMudancaResponsavelDTO> requisicaoMudancaResponsavelDTOs = requisicaoMudancaResponsavelDao.findByIdMudancaEDataFim(historicoMudancaDTO
                .getIdRequisicaoMudanca());

        return requisicaoMudancaResponsavelDTOs;
    }

    public List<RequisicaoMudancaItemConfiguracaoDTO> listarColItemConfiguracao(final HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception {
        final RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaItemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao();
        final List<RequisicaoMudancaItemConfiguracaoDTO> requisicaoMudancaItemConfiguracaoDTOs = (List<RequisicaoMudancaItemConfiguracaoDTO>) requisicaoMudancaItemConfiguracaoDao
                .findByIdMudancaEDataFim(historicoMudancaDTO.getIdRequisicaoMudanca());
        return requisicaoMudancaItemConfiguracaoDTOs;
    }

    public List<RequisicaoMudancaServicoDTO> listarServico(final HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception {
        final RequisicaoMudancaServicoDao requisicaoMudancaServicoDao = new RequisicaoMudancaServicoDao();
        final List<RequisicaoMudancaServicoDTO> requisicaoMudancaServicoDTOs = (List<RequisicaoMudancaServicoDTO>) requisicaoMudancaServicoDao
                .findByIdMudancaEDataFim(historicoMudancaDTO.getIdRequisicaoMudanca());
        return requisicaoMudancaServicoDTOs;
    }

    public List<ProblemaMudancaDTO> listarProblema(final HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception {

        final ProblemaMudancaDAO problemaMudancaDAO = new ProblemaMudancaDAO();
        final List<ProblemaMudancaDTO> problemaMudancaDTOs = (List<ProblemaMudancaDTO>) problemaMudancaDAO.findByIdMudancaEDataFim(historicoMudancaDTO.getIdRequisicaoMudanca());
        return problemaMudancaDTOs;
    }

    public List<GrupoRequisicaoMudancaDTO> listarGrupo(final HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception {

        final GrupoRequisicaoMudancaDao grupoRequisicaoMudancaDAO = new GrupoRequisicaoMudancaDao();
        final List<GrupoRequisicaoMudancaDTO> grupoRequisicaoMudancaDTOs = (List<GrupoRequisicaoMudancaDTO>) grupoRequisicaoMudancaDAO.findByIdMudancaEDataFim(historicoMudancaDTO
                .getIdRequisicaoMudanca());
        return grupoRequisicaoMudancaDTOs;
    }

    public List<RequisicaoMudancaRiscoDTO> listarRiscos(final HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception {

        final RequisicaoMudancaRiscoDao riscoDao = new RequisicaoMudancaRiscoDao();
        final List<RequisicaoMudancaRiscoDTO> problemaMudancaDTOs = (List<RequisicaoMudancaRiscoDTO>) riscoDao.findByIdRequisicaoMudancaEDataFim(historicoMudancaDTO
                .getIdRequisicaoMudanca());
        return problemaMudancaDTOs;
    }

    public List<AprovacaoMudancaDTO> listarAprovacoes(final HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception {

        final AprovacaoMudancaDao aprovacaoDao = new AprovacaoMudancaDao();
        final List<AprovacaoMudancaDTO> aprovacaoMudancaDTOs = (List<AprovacaoMudancaDTO>) aprovacaoDao.listaAprovacaoMudancaPorIdRequisicaoMudancaEHistorico(
                historicoMudancaDTO.getIdRequisicaoMudanca(), null, null);
        return aprovacaoMudancaDTOs;
    }

    public List<LiberacaoMudancaDTO> listarLiberacoes(final HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception {

        final LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();
        final List<LiberacaoMudancaDTO> liberacaoMudancaDTOs = (List<LiberacaoMudancaDTO>) liberacaoMudancaDao.findByIdRequisicaoMudanca(historicoMudancaDTO.getIdLiberacao(),
                historicoMudancaDTO.getIdRequisicaoMudanca());
        return liberacaoMudancaDTOs;
    }

    public List<RequisicaoMudancaDTO> listarSolicitacaoServico(final HistoricoMudancaDTO historicoMudancaDTO) throws ServiceException, Exception {

        final RequisicaoMudancaDao mudancaDao = this.getDao();
        final List<RequisicaoMudancaDTO> solicitacaoMudancaDTOs = (List<RequisicaoMudancaDTO>) mudancaDao.findByIdRequisicaoMudancaEDataFim(historicoMudancaDTO
                .getIdRequisicaoMudanca());
        return solicitacaoMudancaDTOs;
    }

    public void deleteAdicionaTabelaResponsavel(final RequisicaoMudancaDTO requisicaoMudancaDTO, final TransactionControler tc) throws Exception {
        Collection<RequisicaoMudancaResponsavelDTO> colResponsavelBanco = new ArrayList<RequisicaoMudancaResponsavelDTO>();
        final RequisicaoMudancaResponsavelDao responsavelDao = new RequisicaoMudancaResponsavelDao();
        colResponsavelBanco = responsavelDao.findByIdMudancaEDataFim(requisicaoMudancaDTO.getIdRequisicaoMudanca());
        responsavelDao.setTransactionControler(tc);
        boolean grava = false;
        boolean exclui = false;
        int idResp1 = 0;
        int idResp2 = 0;
        if (colResponsavelBanco == null || colResponsavelBanco.size() == 0) {
            for (final RequisicaoMudancaResponsavelDTO requisicaoMudancaResponsavelDTO : requisicaoMudancaDTO.getColResponsaveis()) {
                requisicaoMudancaResponsavelDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
                responsavelDao.create(requisicaoMudancaResponsavelDTO);
            }
        } else {
            if (requisicaoMudancaDTO.getColResponsaveis() != null && requisicaoMudancaDTO.getColResponsaveis().size() > 0) {
                // compara o que vem da tela com o que está no banco se o que estiver na tela for diferente do banco
                // então ele grava poruqe o item não existe no banco.
                for (final RequisicaoMudancaResponsavelDTO requisicaoMudancaResponsavelDTO : requisicaoMudancaDTO.getColResponsaveis()) {
                    for (final RequisicaoMudancaResponsavelDTO requisicaoMudancaResponsavelDTO2 : colResponsavelBanco) {
                        idResp1 = requisicaoMudancaResponsavelDTO.getIdResponsavel();
                        idResp2 = requisicaoMudancaResponsavelDTO2.getIdResponsavel();
                        if (idResp1 == idResp2) {
                            grava = false;
                            break;
                        } else {
                            grava = true;
                        }
                    }
                    if (grava) {
                        requisicaoMudancaResponsavelDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
                        responsavelDao.create(requisicaoMudancaResponsavelDTO);
                    }
                }
                // Compara o que vem do banco com o que está na tela se o que estiver no banco for diferente do que tem na tela
                // então ele seta a data fim para desabilitar no banco.
                if (colResponsavelBanco != null && colResponsavelBanco.size() > 0) {
                    for (final RequisicaoMudancaResponsavelDTO requisicaoMudancaResponsavelDTO : colResponsavelBanco) {
                        for (final RequisicaoMudancaResponsavelDTO requisicaoMudancaResponsavelDTO2 : requisicaoMudancaDTO.getColResponsaveis()) {
                            idResp1 = requisicaoMudancaResponsavelDTO.getIdResponsavel();
                            idResp2 = requisicaoMudancaResponsavelDTO2.getIdResponsavel();
                            if (idResp1 == idResp2) {
                                exclui = false;
                                break;
                            } else {
                                exclui = true;
                                requisicaoMudancaResponsavelDTO.setDataFim(UtilDatas.getDataAtual());
                            }
                        }
                        if (exclui) {
                            responsavelDao.update(requisicaoMudancaResponsavelDTO);
                        }
                    }
                }
            }
        }

    }

    public void deleteAdicionaTabelaProblema(final RequisicaoMudancaDTO requisicaoMudancaDTO, final TransactionControler tc) throws Exception {
        Collection<ProblemaMudancaDTO> colProblemaBanco = new ArrayList<ProblemaMudancaDTO>();
        final ProblemaMudancaDAO problemaDao = new ProblemaMudancaDAO();
        colProblemaBanco = problemaDao.findByIdMudancaEDataFim(requisicaoMudancaDTO.getIdRequisicaoMudanca());
        problemaDao.setTransactionControler(tc);
        boolean grava = false;
        boolean exclui = false;
        int idProblema1 = 0;
        int idProblema2 = 0;
        if (colProblemaBanco == null || colProblemaBanco.size() == 0) {
            for (final ProblemaMudancaDTO problemamudancaDto : requisicaoMudancaDTO.getListProblemaMudancaDTO()) {
                problemamudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
                problemaDao.create(problemamudancaDto);
            }
        } else {
            if (requisicaoMudancaDTO.getListProblemaMudancaDTO() != null && requisicaoMudancaDTO.getListProblemaMudancaDTO().size() > 0) {
                // compara o que vem da tela com o que está no banco se o que estiver na tela for diferente do banco
                // então ele grava poruqe o item não existe no banco.
                for (final ProblemaMudancaDTO problemaMudancaDTO : requisicaoMudancaDTO.getListProblemaMudancaDTO()) {
                    for (final ProblemaMudancaDTO problemaMudancaDTO2 : colProblemaBanco) {
                        idProblema1 = problemaMudancaDTO.getIdProblema();
                        idProblema2 = problemaMudancaDTO2.getIdProblema();
                        if (idProblema1 == idProblema2) {
                            grava = false;
                            break;
                        } else {
                            grava = true;
                        }
                    }
                    if (grava) {
                        problemaMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
                        problemaDao.create(problemaMudancaDTO);
                    }
                }
                // Compara o que vem do banco com o que está na tela se o que estiver no banco for diferente do que tem na tela
                // então ele seta a data fim para desabilitar no banco.
                if (colProblemaBanco != null && colProblemaBanco.size() > 0) {
                    for (final ProblemaMudancaDTO problemaMudancaDTO : colProblemaBanco) {
                        for (final ProblemaMudancaDTO requisicaoMudancaResponsavelDTO2 : requisicaoMudancaDTO.getListProblemaMudancaDTO()) {
                            idProblema1 = problemaMudancaDTO.getIdProblema();
                            idProblema2 = requisicaoMudancaResponsavelDTO2.getIdProblema();
                            if (idProblema1 == idProblema2) {
                                exclui = false;
                                break;
                            } else {
                                exclui = true;
                                problemaMudancaDTO.setDataFim(UtilDatas.getDataAtual());
                            }
                        }
                        if (exclui) {
                            problemaDao.update(problemaMudancaDTO);
                        }
                    }
                }
            }
        }

    }

    public void deleteAdicionaTabelaRiscos(final RequisicaoMudancaDTO requisicaoMudancaDTO, final TransactionControler tc) throws Exception {
        Collection<RequisicaoMudancaRiscoDTO> colRiscosBanco = new ArrayList<RequisicaoMudancaRiscoDTO>();
        final RequisicaoMudancaRiscoDao riscosDao = new RequisicaoMudancaRiscoDao();
        colRiscosBanco = riscosDao.findByIdRequisicaoMudancaEDataFim(requisicaoMudancaDTO.getIdRequisicaoMudanca());
        riscosDao.setTransactionControler(tc);
        boolean grava = false;
        boolean exclui = false;
        int idRisco1 = 0;
        int idRisco2 = 0;
        if (colRiscosBanco == null || colRiscosBanco.size() == 0) {
            for (final RequisicaoMudancaRiscoDTO riscoMudancaDto : requisicaoMudancaDTO.getListRequisicaoMudancaRiscoDTO()) {
                riscoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
                riscosDao.create(riscoMudancaDto);
            }
        } else {
            if (requisicaoMudancaDTO.getListRequisicaoMudancaRiscoDTO() != null && requisicaoMudancaDTO.getListRequisicaoMudancaRiscoDTO().size() > 0) {
                // compara o que vem da tela com o que está no banco se o que estiver na tela for diferente do banco
                // então ele grava poruqe o item não existe no banco.
                for (final RequisicaoMudancaRiscoDTO riscoMudancaDTO : requisicaoMudancaDTO.getListRequisicaoMudancaRiscoDTO()) {
                    for (final RequisicaoMudancaRiscoDTO riscoMudancaDTO2 : colRiscosBanco) {
                        idRisco1 = riscoMudancaDTO.getIdRisco();
                        idRisco2 = riscoMudancaDTO2.getIdRisco();
                        if (idRisco1 == idRisco2) {
                            grava = false;
                            break;
                        } else {
                            grava = true;
                        }
                    }
                    if (grava) {
                        riscoMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
                        riscosDao.create(riscoMudancaDTO);
                    }
                }
                // Compara o que vem do banco com o que está na tela se o que estiver no banco for diferente do que tem na tela
                // então ele seta a data fim para desabilitar no banco.
                if (colRiscosBanco != null && colRiscosBanco.size() > 0) {
                    for (final RequisicaoMudancaRiscoDTO riscoMudancaDTO : colRiscosBanco) {
                        for (final RequisicaoMudancaRiscoDTO riscoMudancaDTO2 : requisicaoMudancaDTO.getListRequisicaoMudancaRiscoDTO()) {
                            idRisco1 = riscoMudancaDTO.getIdRisco();
                            idRisco2 = riscoMudancaDTO2.getIdRisco();
                            if (idRisco1 == idRisco2) {
                                exclui = false;
                                break;
                            } else {
                                exclui = true;
                                riscoMudancaDTO.setDataFim(UtilDatas.getDataAtual());
                                riscoMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
                            }
                        }
                        if (exclui) {
                            riscosDao.update(riscoMudancaDTO);
                        }
                    }
                }
            }
        }

    }

    public void deleteAdicionaTabelaGrupo(final RequisicaoMudancaDTO requisicaoMudancaDTO, final TransactionControler tc) throws Exception {
        Collection<GrupoRequisicaoMudancaDTO> colGrupoBanco = new ArrayList<GrupoRequisicaoMudancaDTO>();
        final GrupoRequisicaoMudancaDao grupoDao = new GrupoRequisicaoMudancaDao();
        colGrupoBanco = grupoDao.findByIdMudancaEDataFim(requisicaoMudancaDTO.getIdRequisicaoMudanca());
        grupoDao.setTransactionControler(tc);
        boolean grava = false;
        boolean exclui = false;
        int idGrupo1 = 0;
        int idGrupo2 = 0;
        if (colGrupoBanco == null || colGrupoBanco.size() == 0) {
            for (final GrupoRequisicaoMudancaDTO gruporequisicaomudancaDto : requisicaoMudancaDTO.getListGrupoRequisicaoMudancaDTO()) {
                gruporequisicaomudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
                grupoDao.create(gruporequisicaomudancaDto);
            }
        } else {
            if (requisicaoMudancaDTO.getListGrupoRequisicaoMudancaDTO() != null && requisicaoMudancaDTO.getListGrupoRequisicaoMudancaDTO().size() > 0) {
                for (final GrupoRequisicaoMudancaDTO gruporequisicaomudancaDto : requisicaoMudancaDTO.getListGrupoRequisicaoMudancaDTO()) {
                    for (final GrupoRequisicaoMudancaDTO gruporequisicaomudancaDto2 : colGrupoBanco) {
                        idGrupo1 = gruporequisicaomudancaDto.getIdGrupo();
                        idGrupo2 = gruporequisicaomudancaDto2.getIdGrupo();
                        if (idGrupo1 == idGrupo2) {
                            grava = false;
                            break;
                        } else {
                            grava = true;
                        }
                    }
                    if (grava) {
                        gruporequisicaomudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
                        grupoDao.create(gruporequisicaomudancaDto);
                    }
                }

                if (colGrupoBanco != null && colGrupoBanco.size() > 0) {
                    for (final GrupoRequisicaoMudancaDTO gruporequisicaomudancaDto : colGrupoBanco) {
                        for (final GrupoRequisicaoMudancaDTO gruporequisicaomudancaDto2 : requisicaoMudancaDTO.getListGrupoRequisicaoMudancaDTO()) {
                            idGrupo1 = gruporequisicaomudancaDto.getIdGrupo();
                            idGrupo2 = gruporequisicaomudancaDto2.getIdGrupo();
                            if (idGrupo1 == idGrupo2) {
                                exclui = false;
                                break;
                            } else {
                                exclui = true;
                                gruporequisicaomudancaDto.setDataFim(UtilDatas.getDataAtual());
                            }
                        }
                        if (exclui) {
                            grupoDao.update(gruporequisicaomudancaDto);
                        }
                    }
                }
            }
        }

    }

    private void gravarLiberacaoHistorico(final HistoricoMudancaDTO historicoMudancaDTO, final TransactionControler tc) throws ServiceException, Exception {
        final LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();

        if (tc != null) {
            liberacaoMudancaDao.setTransactionControler(tc);
        }

        if (historicoMudancaDTO.getListLiberacaoMudancaDTO() != null) {
            // grava no banco os historicos de liberacao.
            for (final LiberacaoMudancaDTO liberacaoMudancaDTO : historicoMudancaDTO.getListLiberacaoMudancaDTO()) {

                liberacaoMudancaDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
                liberacaoMudancaDTO.setIdRequisicaoMudanca(historicoMudancaDTO.getIdRequisicaoMudanca());
                liberacaoMudancaDao.create(liberacaoMudancaDTO);
            }
        }

    }

    // metodo que grava o historico da grid de incidentes/requisicoes
    private void gravarSolicitacaoServicoHistoricos(final HistoricoMudancaDTO historicoMudancaDTO, final List<RequisicaoMudancaDTO> listSolicitacaoServicosMudanca,
            final TransactionControler tc) throws ServiceException, Exception {
        final SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();

        if (tc != null) {
            solicitacaoServicoMudancaDao.setTransactionControler(tc);
        }

        if (listSolicitacaoServicosMudanca != null) {
            // grava no banco os historicos de liberacao.
            for (final RequisicaoMudancaDTO solicitacaoMudancaDTO : listSolicitacaoServicosMudanca) {
                final SolicitacaoServicoMudancaDTO solicitacaoServicoMudancaDTO = new SolicitacaoServicoMudancaDTO();

                solicitacaoServicoMudancaDTO.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
                solicitacaoServicoMudancaDTO.setIdRequisicaoMudanca(historicoMudancaDTO.getIdRequisicaoMudanca());
                solicitacaoServicoMudancaDTO.setIdSolicitacaoServico(solicitacaoMudancaDTO.getIdSolicitacaoServico());
                solicitacaoServicoMudancaDao.create(solicitacaoServicoMudancaDTO);
            }
        }
    }

    public void fechaRelacionamentoMudanca(final TransactionControler tc, final RequisicaoMudancaDTO requisicaoMudancaDto) {
        if (tc != null) {
            this.fecharSolicitacaoServicoVinculadaMudanca(tc, requisicaoMudancaDto);
            this.fecharProblemaVinculadoMudanca(requisicaoMudancaDto, tc);
            this.fecharItemConfiguracaoVinculadoMudanca(requisicaoMudancaDto, tc);
        }
    }

    public void fecharSolicitacaoServicoVinculadaMudanca(final TransactionControler tc, final RequisicaoMudancaDTO requisicaoMudancaDto) {
        final SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();
        try {
            solicitacaoServicoMudancaDao.setTransactionControler(tc);
            final List<SolicitacaoServicoDTO> listSolicitacaoServicoDTO = solicitacaoServicoMudancaDao.listSolicitacaoByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            if (listSolicitacaoServicoDTO != null && listSolicitacaoServicoDTO.size() > 0) {
                for (final SolicitacaoServicoDTO solicitacaoServicoDTO : listSolicitacaoServicoDTO) {
                    new SolicitacaoServicoServiceEjb().fechaSolicitacaoServicoVinculadaByProblemaOrMudanca(solicitacaoServicoDTO, tc);
                }
            }
        } catch (final ServiceException e) {
            e.printStackTrace();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void fecharProblemaVinculadoMudanca(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) {
        final ProblemaMudancaDAO problemaMudancaDAO = new ProblemaMudancaDAO();
        try {
            problemaMudancaDAO.setTransactionControler(tc);
            final List<ProblemaDTO> listProblemaDto = problemaMudancaDAO.listProblemaByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            if (listProblemaDto != null && listProblemaDto.size() > 0) {
                for (final ProblemaDTO problemaDTO : listProblemaDto) {
                    new ProblemaServiceEjb().fechaProblemaERelacionamento(problemaDTO, tc);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void fecharItemConfiguracaoVinculadoMudanca(final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc) {
        final RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaIcDao = new RequisicaoMudancaItemConfiguracaoDao();
        try {
            requisicaoMudancaIcDao.setTransactionControler(tc);
            final List<ItemConfiguracaoDTO> listItemCofiguracao = requisicaoMudancaIcDao.listItemConfiguracaoByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            if (listItemCofiguracao != null && listItemCofiguracao.size() > 0) {
                for (final ItemConfiguracaoDTO itemConfiguracaoDTO : listItemCofiguracao) {
                    new ItemConfiguracaoServiceEjb().finalizarItemConfiguracao(itemConfiguracaoDTO, tc);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean verificarItensRelacionados(final RequisicaoMudancaDTO requisicaoMudancaDto) throws ServiceException, Exception {
        final RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaIcDao = new RequisicaoMudancaItemConfiguracaoDao();
        final ProblemaMudancaDAO problemaMudancaDAO = new ProblemaMudancaDAO();
        final SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();

        final List<ProblemaDTO> listProblemaDto = problemaMudancaDAO.listProblemaByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
        final List<SolicitacaoServicoDTO> listSolicitacaoServicoDTO = solicitacaoServicoMudancaDao.listSolicitacaoByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
        final List<ItemConfiguracaoDTO> listItemCofiguracao = requisicaoMudancaIcDao.listItemConfiguracaoByIdMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());

        if (listProblemaDto != null && listProblemaDto.size() > 0) {
            return true;
        } else if (listSolicitacaoServicoDTO != null && listSolicitacaoServicoDTO.size() > 0) {
            return true;
        } else if (listItemCofiguracao != null && listItemCofiguracao.size() > 0) {
            return true;
        }

        return false;
    }

    public void updateTimeAction(final Integer idGrupoRedirect, final Integer idPrioridadeRedirect, final Integer idRequisicaoMudanca) throws ServiceException, LogicException {
        final ExecucaoMudancaServiceEjb execucaoMudancaService = new ExecucaoMudancaServiceEjb();
        final RequisicaoMudancaDao requiscaoMudancaDao = this.getDao();
        final OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();

        final TransactionControler tc = new TransactionControlerImpl(requiscaoMudancaDao.getAliasDB());

        try {
            tc.start();

            // Faz validacao, caso exista.

            requiscaoMudancaDao.setTransactionControler(tc);
            ocorrenciaMudancaDao.setTransactionControler(tc);

            List<RequisicaoMudancaDTO> listaRequisicaoMudanca = new ArrayList<RequisicaoMudancaDTO>();

            RequisicaoMudancaDTO mudancaAuxDto = new RequisicaoMudancaDTO();
            mudancaAuxDto.setIdRequisicaoMudanca(idRequisicaoMudanca);

            listaRequisicaoMudanca = (List<RequisicaoMudancaDTO>) requiscaoMudancaDao.find(mudancaAuxDto);
            if (listaRequisicaoMudanca != null) {
                mudancaAuxDto = listaRequisicaoMudanca.get(0);
            }

            final RequisicaoMudancaDTO requisicaoMudancaDto = new RequisicaoMudancaDTO();

            requisicaoMudancaDto.setIdGrupoAtual(idGrupoRedirect);
            requisicaoMudancaDto.setPrioridade(idPrioridadeRedirect);
            requisicaoMudancaDto.setIdRequisicaoMudanca(idRequisicaoMudanca);

            requiscaoMudancaDao.updateNotNull(requisicaoMudancaDto);
            execucaoMudancaService.direcionaAtendimentoAutomatico(requisicaoMudancaDto, tc);

            final String strOcorr = "\nEscalação automática.";

            final JustificativaRequisicaoMudancaDTO justificativaDto = new JustificativaRequisicaoMudancaDTO();
            justificativaDto.setIdJustificativaMudanca(requisicaoMudancaDto.getIdJustificativa());
            justificativaDto.setDescricaoJustificativa(requisicaoMudancaDto.getComplementoJustificativa());

            final UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setLogin("Automático");

            OcorrenciaMudancaServiceEjb.create(requisicaoMudancaDto, null, strOcorr, OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Atualizacao, null,
                    CategoriaOcorrencia.Atualizacao.getDescricao(), usuarioDTO, 0, justificativaDto, tc);

            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        } finally {
            try {
                tc.close();
            } catch (final PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    @Override
    public void gravaInformacoesGED(final Collection colArquivosUpload, final int idEmpresa, final RequisicaoMudancaDTO requisicaoMudancaDto, final TransactionControler tc)
            throws Exception {

    }

    // Thiago Fernandes - 01/11/2013 14:06 - Sol. 121468 - Criação de metodo para validar se foi ninformado o anxo plano de reverção, caso não tenha deve ser aberta a aba de anxo
    // plano de reverção.
    @Override
    public boolean planoDeReversaoInformado(final RequisicaoMudancaDTO requisicaoMudancaDto, final HttpServletRequest request) throws Exception {
        boolean planoReversaoInformado = true;
        TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
        final AprovacaoMudancaDao aprovacaoMudancaDao = new AprovacaoMudancaDao();
        final TipoMudancaDAO tipoMudancaDAO = new TipoMudancaDAO();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        try {
            aprovacaoMudancaDao.setTransactionControler(tc);
            tipoMudancaDAO.setTransactionControler(tc);
            tc.start();
            if (requisicaoMudancaDto.getIdTipoMudanca() != null) {
                tipoMudancaDto.setIdTipoMudanca(requisicaoMudancaDto.getIdTipoMudanca());
                tipoMudancaDto = (TipoMudancaDTO) tipoMudancaDAO.restore(tipoMudancaDto);
            }
            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null
                    && requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null) {
                final boolean resultado = this.seHoraFinalMenorQHoraInicial(requisicaoMudancaDto);
                if (resultado == true) {
                    throw new LogicException(UtilI18N.internacionaliza(request, "requisicaoMudanca.horaFinalMenorQueInicial"));
                }
            }

            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoInicial() != null) {
                final boolean resultado = this.seHoraInicialMenorQAtual(requisicaoMudancaDto);
                if (resultado == true) {
                    throw new LogicException(UtilI18N.internacionaliza(request, "requisicaoMudanca.horaInicialMenorQueAtual"));
                }
            }

            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null && requisicaoMudancaDto.getHoraAgendamentoFinal() != null) {
                final boolean resultado = this.seHoraFinalMenorQAtual(requisicaoMudancaDto);
                if (resultado == true) {
                    throw new LogicException(UtilI18N.internacionaliza(request, "requisicaoMudanca.horaFinalMenorQueAtual"));
                }
            }

            // gravando a aprovação de mudança
            if (requisicaoMudancaDto.getListAprovacaoMudancaDTO() != null && !requisicaoMudancaDto.getFase().equalsIgnoreCase("Proposta")) {
                for (final AprovacaoMudancaDTO aprovacaoMudancaDto : requisicaoMudancaDto.getListAprovacaoMudancaDTO()) {
                    aprovacaoMudancaDao.deleteLinha(requisicaoMudancaDto.getIdRequisicaoMudanca(), aprovacaoMudancaDto.getIdEmpregado());
                    aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                    aprovacaoMudancaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
                    aprovacaoMudancaDao.create(aprovacaoMudancaDto);
                }
            }

            if (requisicaoMudancaDto.getAcaoFluxo().equalsIgnoreCase("E") && !requisicaoMudancaDto.getFase().equalsIgnoreCase("Proposta")) {
                if (!requisicaoMudancaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoMudanca.Cancelada.name())) {
                    if (tipoMudancaDto.getExigeAprovacao() != null) {
                        if (!this.validacaoAvancaFluxo(requisicaoMudancaDto, tc)) {
                            throw new LogicException(UtilI18N.internacionaliza(request, "requisicaoMudanca.essaSolicitacaoMudancaNaoFoiAprovada"));
                        }
                    }
                    if (!requisicaoMudancaDto.getStatus().equalsIgnoreCase("Rejeitada")) {
                        final Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadPlanoDeReversaoGED");
                        if (arquivosUpados == null || arquivosUpados.size() == 0) {
                            planoReversaoInformado = false;
                        }
                    }
                }

            }
            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        } finally {
            try {
                tc.close();
            } catch (final PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return planoReversaoInformado;
    }

}
