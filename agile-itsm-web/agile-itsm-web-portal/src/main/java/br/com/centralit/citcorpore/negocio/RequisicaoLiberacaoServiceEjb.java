package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.htmlparser.jericho.Source;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.citcorpore.ajaxForms.RequisicaoLiberacao;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CalculoJornadaDTO;
import br.com.centralit.citcorpore.bean.ContatoRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.ControleQuestionariosDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ExecucaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.HistoricoGEDDTO;
import br.com.centralit.citcorpore.bean.HistoricoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoMudancaDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.LiberacaoMudancaDTO;
import br.com.centralit.citcorpore.bean.LiberacaoProblemaDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoComprasDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoMidiaDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoResponsavelDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaLiberacaoDTO;
import br.com.centralit.citcorpore.bean.PesquisaRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoMidiaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoRequisicaoComprasDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoResponsavelDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ContatoRequisicaoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.ControleQuestionariosDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.ExecucaoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.HistoricoGEDDao;
import br.com.centralit.citcorpore.integracao.HistoricoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.LiberacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.LiberacaoProblemaDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoLiberacaoComprasDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoLiberacaoMidiaDao;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoLiberacaoResponsavelDao;
import br.com.centralit.citcorpore.integracao.OcorrenciaLiberacaoDao;
import br.com.centralit.citcorpore.integracao.PermissoesFluxoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoMidiaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoRequisicaoComprasDAO;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoResponsavelDao;
import br.com.centralit.citcorpore.integracao.RequisicaoQuestionarioDao;
import br.com.centralit.citcorpore.integracao.TemplateSolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.TipoLiberacaoDAO;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoLiberacao;
import br.com.centralit.citcorpore.util.Enumerados.TipoRequisicao;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioDao;
import br.com.centralit.citquestionario.negocio.RespostaItemQuestionarioServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;

@SuppressWarnings({"rawtypes", "unchecked"})
public class RequisicaoLiberacaoServiceEjb extends CrudServiceImpl implements RequisicaoLiberacaoService {

    private RequisicaoLiberacaoDao dao;
    private HistoricoLiberacaoDao historicoLiberacaoDao;

    @Override
    protected RequisicaoLiberacaoDao getDao() {
        if (dao == null) {
            dao = new RequisicaoLiberacaoDao();
        }
        return dao;
    }

    private HistoricoLiberacaoDao getHistoricoLiberacaoDao() {
        if (historicoLiberacaoDao == null) {
            historicoLiberacaoDao = new HistoricoLiberacaoDao();
        }
        return historicoLiberacaoDao;
    }

    /**
     * @param requisicaoLiberacaoDTO
     * @param user
     * @return
     * @throws Exception
     * @author murilo.pacheco metodo para gravar o historico de auterações em liberações
     */
    public HistoricoLiberacaoDTO createHistoricoLiberacao(final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception {
        final HistoricoLiberacaoDTO historico = new HistoricoLiberacaoDTO();
        RequisicaoLiberacaoDTO requisicaoLiberacaoDTOAux = requisicaoLiberacaoDTO;
        final Integer idExecutormodificacao = requisicaoLiberacaoDTO.getUsuarioDto().getIdEmpregado();
        final RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, null);
        requisicaoLiberacaoDTOAux = (RequisicaoLiberacaoDTO) requisicaoLiberacaoService.restore(requisicaoLiberacaoDTO);
        requisicaoLiberacaoDTOAux.setAlterarSituacao(requisicaoLiberacaoDTO.getAlterarSituacao());
        requisicaoLiberacaoDTOAux.setAcaoFluxo(requisicaoLiberacaoDTO.getAcaoFluxo());
        Reflexao.copyPropertyValues(requisicaoLiberacaoDTOAux, historico);
        historico.setIdExecutorModificacao(idExecutormodificacao);

        // esse bloco seta as informações de contato.
        ContatoRequisicaoLiberacaoDTO contatoRequisicaoLiberacaoDTO = new ContatoRequisicaoLiberacaoDTO();
        final ContatoRequisicaoLiberacaoService contatoRequisicaoLiberacaoService = (ContatoRequisicaoLiberacaoService) ServiceLocator.getInstance().getService(
                ContatoRequisicaoLiberacaoService.class, null);
        contatoRequisicaoLiberacaoDTO = contatoRequisicaoLiberacaoService.restoreContatosById(requisicaoLiberacaoDTO.getIdContatoRequisicaoLiberacao());
        if (contatoRequisicaoLiberacaoDTO != null) {
            historico.setNomeContato2(contatoRequisicaoLiberacaoDTO.getNomeContato());
            historico.setEmailContato(contatoRequisicaoLiberacaoDTO.getEmailContato());
            historico.setIdContatoRequisicaoLiberacao(contatoRequisicaoLiberacaoDTO.getIdContatoRequisicaoLiberacao());
            historico.setIdUnidade(contatoRequisicaoLiberacaoDTO.getIdUnidade());
            historico.setIdLocalidade(contatoRequisicaoLiberacaoDTO.getIdLocalidade());
            historico.setRamal(contatoRequisicaoLiberacaoDTO.getRamal());
            historico.setTelefoneContato(contatoRequisicaoLiberacaoDTO.getTelefoneContato());
            historico.setObservacao(contatoRequisicaoLiberacaoDTO.getObservacao());
        }

        HistoricoLiberacaoDTO ultVersao = new HistoricoLiberacaoDTO();
        ultVersao = this.getHistoricoLiberacaoDao().maxIdHistorico(requisicaoLiberacaoDTO);
        if (ultVersao.getIdHistoricoLiberacao() != null) {
            ultVersao = (HistoricoLiberacaoDTO) this.getHistoricoLiberacaoDao().restore(ultVersao);
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

    @Override
    public Collection findByIdSolicitante(final Integer parm) throws Exception {
        final RequisicaoLiberacaoDao dao = this.getDao();
        try {
            return dao.findByIdSolicitante(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final RequisicaoLiberacaoDao liberacaoDao = this.getDao();
        final LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();
        final LiberacaoProblemaDao liberacaoProblemaDao = new LiberacaoProblemaDao();
        final RequisicaoLiberacaoItemConfiguracaoDao requisicaoLiberacaoItemConfiguracaoDao = new RequisicaoLiberacaoItemConfiguracaoDao();
        final RequisicaoLiberacaoMidiaDao requisicaoLiberacaoMidiaDao = new RequisicaoLiberacaoMidiaDao();
        final RequisicaoLiberacaoResponsavelDao requisicaoLiberacaoResponsavelDao = new RequisicaoLiberacaoResponsavelDao();
        final RequisicaoLiberacaoRequisicaoComprasDAO requisicaoLiberacaoPedidoComprasDao = new RequisicaoLiberacaoRequisicaoComprasDAO();
        final TipoLiberacaoDAO tipoLiberacaoDAO = new TipoLiberacaoDAO();
        TipoLiberacaoDTO tipoLiberacaoDTO = new TipoLiberacaoDTO();
        final UsuarioDao usuarioDao = new UsuarioDao();
        final HistoricoLiberacaoDTO historicoLiberacaoDTO = new HistoricoLiberacaoDTO();

        ContatoRequisicaoLiberacaoDTO contatoRequisicaoLiberacaoDto = new ContatoRequisicaoLiberacaoDTO();
        UsuarioDTO usuarioDto = new UsuarioDTO();
        final RequisicaoLiberacaoDTO requisicaoLiberacaoDto = (RequisicaoLiberacaoDTO) model;

        final TransactionControler tc = new TransactionControlerImpl(liberacaoDao.getAliasDB());
        final ExecucaoLiberacaoServiceEjb execucaoLiberacaoServiceEjb = new ExecucaoLiberacaoServiceEjb();

        try {
            this.validaCreate(model);

            liberacaoDao.setTransactionControler(tc);
            liberacaoMudancaDao.setTransactionControler(tc);
            liberacaoProblemaDao.setTransactionControler(tc);
            tipoLiberacaoDAO.setTransactionControler(tc);
            requisicaoLiberacaoMidiaDao.setTransactionControler(tc);
            requisicaoLiberacaoResponsavelDao.setTransactionControler(tc);
            requisicaoLiberacaoPedidoComprasDao.setTransactionControler(tc);
            usuarioDao.setTransactionControler(tc);
            tc.start();

            RequisicaoLiberacaoDTO liberacaoDto = (RequisicaoLiberacaoDTO) model;

            if (liberacaoDto != null && liberacaoDto.getDataInicial() != null && liberacaoDto.getDataFinal() != null) {
                final Timestamp dataInicialAgendada = UtilDatas.strToTimestamp(liberacaoDto.getDataInicial().toString());
                liberacaoDto.setDataHoraInicioAgendada(dataInicialAgendada);
                final Timestamp dataFinalAgendada = UtilDatas.strToTimestamp(liberacaoDto.getDataFinal().toString());
                liberacaoDto.setDataHoraTerminoAgendada(dataFinalAgendada);
            }

            if (liberacaoDto != null && liberacaoDto.getDataInicial() != null && liberacaoDto.getHoraAgendamentoInicial() != null && liberacaoDto != null
                    && liberacaoDto.getDataFinal() != null && liberacaoDto.getHoraAgendamentoFinal() != null) {
                final boolean resultado = this.seHoraFinalMenorQHoraInicial(liberacaoDto);
                if (resultado == true) {
                    throw new LogicException(this.i18nMessage("requisicaoMudanca.horaFinalMenorQueInicial"));
                }
            }

            if (liberacaoDto != null && liberacaoDto.getDataInicial() != null && liberacaoDto.getHoraAgendamentoInicial() != null) {

                final boolean resultado = this.seHoraInicialMenorQAtual(liberacaoDto);
                if (resultado == true) {
                    throw new LogicException(this.i18nMessage("requisicaoMudanca.horaInicialMenorQueAtual"));
                }
            }

            if (liberacaoDto != null && liberacaoDto.getDataFinal() != null && liberacaoDto.getHoraAgendamentoFinal() != null) {
                final boolean resultado = this.seHoraFinalMenorQAtual(liberacaoDto);
                if (resultado == true) {
                    throw new LogicException(this.i18nMessage("requisicaoMudanca.horaFinalMenorQueAtual"));
                }
            }

            if (liberacaoDto != null && liberacaoDto.getDataInicial() != null && liberacaoDto.getHoraAgendamentoInicial() != null) {
                final Timestamp dataHoraInicial = this.MontardataHoraAgendamentoInicial(liberacaoDto);
                liberacaoDto.setDataHoraInicioAgendada(dataHoraInicial);
            }

            if (liberacaoDto != null && liberacaoDto.getDataFinal() != null && liberacaoDto.getHoraAgendamentoFinal() != null) {
                final Timestamp dataHoraFinal = this.MontardataHoraAgendamentoFinal(liberacaoDto);
                liberacaoDto.setDataHoraTerminoAgendada(dataHoraFinal);
                liberacaoDto.setDataHoraTermino(dataHoraFinal);
            }

            if (liberacaoDto != null && liberacaoDto.getIdTipoLiberacao() != null) {
                tipoLiberacaoDTO.setIdTipoLiberacao(liberacaoDto.getIdTipoLiberacao());
                tipoLiberacaoDTO = (TipoLiberacaoDTO) tipoLiberacaoDAO.restore(tipoLiberacaoDTO);
            }

            final boolean resultado = this.validacaoGrupoExecutor(liberacaoDto);
            if (resultado == false) {
                throw new LogicException(this.i18nMessage("requisicaoLiberacao.grupoSemPermissao"));
            }

            if (liberacaoDto.getDataInicial() != null && liberacaoDto.getDataFinal() != null) {

                this.determinaPrazo(liberacaoDto, tipoLiberacaoDTO.getIdCalendario());
                this.calculaTempoAtraso(liberacaoDto);

            } else {
                liberacaoDto.setPrazoHH(00);
                liberacaoDto.setPrazoMM(00);
            }

            if (liberacaoDto.getIdTipoLiberacao() != null) {
                tipoLiberacaoDTO.setIdTipoLiberacao(liberacaoDto.getIdTipoLiberacao());
                tipoLiberacaoDTO = (TipoLiberacaoDTO) tipoLiberacaoDAO.restore(tipoLiberacaoDTO);
            }

            if (liberacaoDto.getIdGrupoNivel1() == null || liberacaoDto.getIdGrupoNivel1().intValue() <= 0) {
                liberacaoDto.setIdGrupoNivel1(tipoLiberacaoDTO.getIdGrupoExecutor());
            }

            if (liberacaoDto.getIdGrupoAtual() == null) {
                liberacaoDto.setIdGrupoAtual(tipoLiberacaoDTO.getIdGrupoExecutor());
            }

            liberacaoDto.setIdCalendario(tipoLiberacaoDTO.getIdCalendario());
            liberacaoDto.setTempoDecorridoHH(new Integer(0));
            liberacaoDto.setTempoDecorridoMM(new Integer(0));
            liberacaoDto.setDataHoraSuspensao(null);
            liberacaoDto.setDataHoraReativacao(null);
            liberacaoDto.setSeqReabertura(new Integer(0));
            liberacaoDto.setDataHoraSolicitacao(new Timestamp(new java.util.Date().getTime()));
            liberacaoDto.setIdProprietario(liberacaoDto.getUsuarioDto().getIdUsuario());
            liberacaoDto.setIdResponsavel(liberacaoDto.getUsuarioDto().getIdUsuario());
            usuarioDto = usuarioDao.restoreByIdEmpregado(liberacaoDto.getIdSolicitante());

            if (usuarioDto != null) {
                liberacaoDto.setUsuarioSolicitante(usuarioDto.getLogin());
            }

            if (liberacaoDto.getDataFinal() != null && liberacaoDto.getDataInicial() != null) {
                // liberacaoDto.setDataHoraTerminoAgendada(UtilDatas.strToTimestamp(UtilDatas.dateToSTR(liberacaoDto.getDataFinal())));
                // liberacaoDto.setDataHoraInicioAgendada(
                // UtilDatas.strToTimestamp(UtilDatas.dateToSTR(liberacaoDto.getDataInicial())));
                this.determinaPrazo(liberacaoDto, tipoLiberacaoDTO.getIdCalendario());
            } else {
                liberacaoDto.setPrazoHH(00);
                liberacaoDto.setPrazoMM(00);
            }

            liberacaoDto.setDataHoraInicio(new Timestamp(new java.util.Date().getTime()));
            liberacaoDto.setDataHoraCaptura(liberacaoDto.getDataHoraInicio());

            contatoRequisicaoLiberacaoDto = this.criarContatoRequisicaoLiberacao(requisicaoLiberacaoDto, tc);

            liberacaoDto.setIdContatoRequisicaoLiberacao(contatoRequisicaoLiberacaoDto.getIdContatoRequisicaoLiberacao());
            liberacaoDto = (RequisicaoLiberacaoDTO) liberacaoDao.create(liberacaoDto);

            // Grava Liberação DTO

            if (liberacaoDto.getColArquivosUpload() != null) {
                this.gravaInformacoesGED(liberacaoDto, tc, historicoLiberacaoDTO);
            }
            if (liberacaoDto.getColArquivosUploadDocsLegais() != null) {
                this.gravaGEDDocLegais(liberacaoDto, tc, historicoLiberacaoDTO);
            }
            if (liberacaoDto.getColDocsGerais() != null) {
                this.gravaGEDDocGerais(liberacaoDto, tc, historicoLiberacaoDTO);
            }

            this.criarOcorrenciaLiberacao(liberacaoDto, tc);

            // esse bloco grava a ocorrencia a partir do botão adicionar registro de execução
            final Source source = new Source(liberacaoDto.getRegistroexecucao());
            liberacaoDto.setRegistroexecucao(source.getTextExtractor().toString());

            if (liberacaoDto.getRegistroexecucao() != null && !liberacaoDto.getRegistroexecucao().trim().equalsIgnoreCase("")) {
                final OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
                ocorrenciaLiberacaoDao.setTransactionControler(tc);
                final OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDTO = new OcorrenciaLiberacaoDTO();
                ocorrenciaLiberacaoDTO.setIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
                ocorrenciaLiberacaoDTO.setDataregistro(UtilDatas.getDataAtual());
                ocorrenciaLiberacaoDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
                ocorrenciaLiberacaoDTO.setTempoGasto(0);
                ocorrenciaLiberacaoDTO.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
                ocorrenciaLiberacaoDTO.setDataInicio(UtilDatas.getDataAtual());
                ocorrenciaLiberacaoDTO.setDataFim(UtilDatas.getDataAtual());
                ocorrenciaLiberacaoDTO.setInformacoesContato("não se aplica");
                ocorrenciaLiberacaoDTO.setRegistradopor(liberacaoDto.getUsuarioDto().getLogin());
                try {
                    ocorrenciaLiberacaoDTO.setDadosLiberacao(new Gson().toJson(liberacaoDto));
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                ocorrenciaLiberacaoDTO.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
                ocorrenciaLiberacaoDTO.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Criacao.getSigla());
                ocorrenciaLiberacaoDTO.setOcorrencia(liberacaoDto.getRegistroexecucao());
                ocorrenciaLiberacaoDao.create(ocorrenciaLiberacaoDTO);
            }

            final Collection<LiberacaoMudancaDTO> colMudancas = liberacaoDto.getColMudancas();
            if (colMudancas != null) {
                for (final LiberacaoMudancaDTO liberacaoMudancaDto : colMudancas) {
                    liberacaoMudancaDto.setIdLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
                    liberacaoMudancaDao.create(liberacaoMudancaDto);
                }
            }

            final Collection<LiberacaoProblemaDTO> colProblema = liberacaoDto.getColProblemas();
            if (colProblema != null) {
                for (final LiberacaoProblemaDTO liberacaoProblemaDTO : colProblema) {
                    liberacaoProblemaDTO.setIdLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
                    liberacaoProblemaDao.create(liberacaoProblemaDTO);
                }
            }
            final Collection<RequisicaoLiberacaoItemConfiguracaoDTO> colitens = liberacaoDto.getListRequisicaoLiberacaoItemConfiguracaoDTO();
            if (colitens != null) {
                for (final RequisicaoLiberacaoItemConfiguracaoDTO requisicaoLiberacaoItemConfiguracaoDTO : colitens) {
                    requisicaoLiberacaoItemConfiguracaoDTO.setIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
                    requisicaoLiberacaoItemConfiguracaoDao.create(requisicaoLiberacaoItemConfiguracaoDTO);
                }
            }

            final Collection<RequisicaoLiberacaoMidiaDTO> colRequisicaoLiberacao = liberacaoDto.getColMidia();
            if (colRequisicaoLiberacao != null) {
                for (final RequisicaoLiberacaoMidiaDTO requisicaoLiberacaoMidiaDTO : colRequisicaoLiberacao) {
                    requisicaoLiberacaoMidiaDTO.setIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
                    requisicaoLiberacaoMidiaDao.create(requisicaoLiberacaoMidiaDTO);
                }
            }

            if (requisicaoLiberacaoDto.getDataHoraInicioAgendada() != null && requisicaoLiberacaoDto.getDataHoraTerminoAgendada() != null
                    && (requisicaoLiberacaoDto.getIdGrupoAtvPeriodica() == null || requisicaoLiberacaoDto.getIdGrupoAtvPeriodica() == 0)) {
                throw new LogicException(this.i18nMessage("gerenciaservico.agendaratividade.informacoesGrupoAtividade"));
            }

            // create Responsavel
            final Collection<RequisicaoLiberacaoResponsavelDTO> colRequisicaoLiberacaoResp = liberacaoDto.getColResponsaveis();
            if (colRequisicaoLiberacaoResp != null) {
                for (final RequisicaoLiberacaoResponsavelDTO liberacaoResponsavelDTO : colRequisicaoLiberacaoResp) {
                    liberacaoResponsavelDTO.setIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
                    requisicaoLiberacaoResponsavelDao.create(liberacaoResponsavelDTO);

                }
            }

            // create PedidoCompras
            final Collection<RequisicaoLiberacaoRequisicaoComprasDTO> colRequisicaoLiberacaoCompras = liberacaoDto.getColRequisicaoCompras();
            if (colRequisicaoLiberacaoCompras != null) {
                for (final RequisicaoLiberacaoRequisicaoComprasDTO liberacaoPedidoComprasDTO : colRequisicaoLiberacaoCompras) {
                    liberacaoPedidoComprasDTO.setIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
                    requisicaoLiberacaoPedidoComprasDao.create(liberacaoPedidoComprasDTO);
                }
            }

            // Grava Fluxo
            execucaoLiberacaoServiceEjb.registraLiberacao(liberacaoDto, tc, usuario);

            tc.commit();

            if (requisicaoLiberacaoDto.getDataHoraInicioAgendada() != null) {
                RequisicaoLiberacao.salvaGrupoAtvPeriodicaEAgenda(requisicaoLiberacaoDto);
            }

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.closeQuietly();
        }
        return model;
    }

    @Override
    public List<RequisicaoLiberacaoItemConfiguracaoDTO> listItensRelacionadosRequisicaoLiberacao(final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws ServiceException,
            Exception {
        ItemConfiguracaoDTO ic = null;

        final RequisicaoLiberacaoItemConfiguracaoService requisicaoLiberacaoService = (RequisicaoLiberacaoItemConfiguracaoService) ServiceLocator.getInstance().getService(
                RequisicaoLiberacaoItemConfiguracaoService.class, null);
        final ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);

        final ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> listaReqLiberacaoIC = requisicaoLiberacaoService.listByIdRequisicaoLiberacao(requisicaoLiberacaoDTO
                .getIdRequisicaoLiberacao());

        // atribui nome para os itens retornados
        if (listaReqLiberacaoIC != null) {
            for (final RequisicaoLiberacaoItemConfiguracaoDTO r : listaReqLiberacaoIC) {
                ic = itemConfiguracaoService.restoreByIdItemConfiguracao(r.getIdItemConfiguracao());
                if (ic != null) {
                    r.setNomeItemConfiguracao(ic.getIdentificacao());
                }
            }
        }

        return listaReqLiberacaoIC;
    }

    @Override
    public void updateSimples(final IDto model) {
        try {
            super.update(model);
        } catch (final LogicException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        ContatoRequisicaoLiberacaoDTO contatoRequisicaoLiberacaoDto = new ContatoRequisicaoLiberacaoDTO();
        final RequisicaoLiberacaoDao liberacaoDao = this.getDao();
        final LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();
        final LiberacaoProblemaDao liberacaoProblemaDao = new LiberacaoProblemaDao();
        final RequisicaoLiberacaoResponsavelDao liberacaoResponsavelDao = new RequisicaoLiberacaoResponsavelDao();
        final RequisicaoLiberacaoMidiaDao requisicaoLiberacaoMidiaDao = new RequisicaoLiberacaoMidiaDao();
        final RequisicaoLiberacaoRequisicaoComprasDAO requisicaoLiberacaoPedidoComprasDao = new RequisicaoLiberacaoRequisicaoComprasDAO();
        final TipoLiberacaoDAO tipoLiberacaoDAO = new TipoLiberacaoDAO();
        final UsuarioDao usuarioDao = new UsuarioDao();
        LigacaoRequisicaoLiberacaoHistoricoMidiaDTO ligacaoMidiaDto = new LigacaoRequisicaoLiberacaoHistoricoMidiaDTO();
        LigacaoRequisicaoLiberacaoHistoricoResponsavelDTO ligacaoResponsavelDTO = new LigacaoRequisicaoLiberacaoHistoricoResponsavelDTO();
        final LigacaoRequisicaoLiberacaoMidiaDao ligacaoMidiaDao = new LigacaoRequisicaoLiberacaoMidiaDao();
        final LigacaoRequisicaoLiberacaoResponsavelDao ligacaoResponsavelDao = new LigacaoRequisicaoLiberacaoResponsavelDao();
        LigacaoRequisicaoLiberacaoHistoricoComprasDTO ligacaoComprasDTO = new LigacaoRequisicaoLiberacaoHistoricoComprasDTO();
        final LigacaoRequisicaoLiberacaoComprasDao ligacaoComprasDao = new LigacaoRequisicaoLiberacaoComprasDao();

        final TransactionControler tc = new TransactionControlerImpl(liberacaoDao.getAliasDB());
        Connection connection = null;

        try {
            connection = tc.getConnection();
            connection.setAutoCommit(true);
        } catch (final Exception e1) {
            e1.printStackTrace();
        }

        TipoLiberacaoDTO tipoLiberacaoDto = new TipoLiberacaoDTO();
        UsuarioDTO usuarioDto = new UsuarioDTO();

        try {
            this.validaUpdate(model);

            liberacaoDao.setTransactionControler(tc);
            liberacaoMudancaDao.setTransactionControler(tc);
            liberacaoProblemaDao.setTransactionControler(tc);
            liberacaoResponsavelDao.setTransactionControler(tc);
            tipoLiberacaoDAO.setTransactionControler(tc);
            requisicaoLiberacaoMidiaDao.setTransactionControler(tc);
            requisicaoLiberacaoPedidoComprasDao.setTransactionControler(tc);
            usuarioDao.setTransactionControler(tc);
            ligacaoMidiaDao.setTransactionControler(tc);
            ligacaoResponsavelDao.setTransactionControler(tc);

            tc.start();

            final RequisicaoLiberacaoDTO liberacaoDto = (RequisicaoLiberacaoDTO) model;

            if (liberacaoDto != null && liberacaoDto.getDataInicial() != null && liberacaoDto.getDataFinal() != null) {
                final Timestamp dataInicialAgendada = UtilDatas.strToTimestamp(liberacaoDto.getDataInicial().toString());
                liberacaoDto.setDataHoraInicioAgendada(dataInicialAgendada);
                final Timestamp dataFinalAgendada = UtilDatas.strToTimestamp(liberacaoDto.getDataFinal().toString());
                liberacaoDto.setDataHoraTerminoAgendada(dataFinalAgendada);
            }

            if (liberacaoDto != null && liberacaoDto.getDataInicial() != null && liberacaoDto.getHoraAgendamentoInicial() != null && liberacaoDto != null
                    && liberacaoDto.getDataFinal() != null && liberacaoDto.getHoraAgendamentoFinal() != null) {
                final boolean resultado = this.seHoraFinalMenorQHoraInicial(liberacaoDto);
                if (resultado == true) {
                    throw new LogicException(this.i18nMessage("requisicaoMudanca.horaFinalMenorQueInicial"));
                }
            }

            if (liberacaoDto != null && liberacaoDto.getDataInicial() != null && liberacaoDto.getHoraAgendamentoInicial() != null) {
                final Timestamp dataHoraInicial = this.MontardataHoraAgendamentoInicial(liberacaoDto);
                liberacaoDto.setDataHoraInicioAgendada(dataHoraInicial);
            }

            if (liberacaoDto != null && liberacaoDto.getDataFinal() != null && liberacaoDto.getHoraAgendamentoFinal() != null) {
                final Timestamp dataHoraFinal = this.MontardataHoraAgendamentoFinal(liberacaoDto);
                liberacaoDto.setDataHoraTerminoAgendada(dataHoraFinal);
                liberacaoDto.setDataHoraTermino(dataHoraFinal);
            }

            if (liberacaoDto != null && liberacaoDto.getIdTipoLiberacao() != null) {
                tipoLiberacaoDto.setIdTipoLiberacao(liberacaoDto.getIdTipoLiberacao());
                tipoLiberacaoDto = (TipoLiberacaoDTO) tipoLiberacaoDAO.restore(tipoLiberacaoDto);
            }

            if (liberacaoDto != null && liberacaoDto.getDataInicial() != null && liberacaoDto.getDataFinal() != null) {

                this.determinaPrazo(liberacaoDto, tipoLiberacaoDto.getIdCalendario());
                this.calculaTempoAtraso(liberacaoDto);

            } else {
                if (liberacaoDto != null) {
                    liberacaoDto.setPrazoHH(00);
                    liberacaoDto.setPrazoMM(00);
                }
            }

            final RequisicaoLiberacaoDTO auxliberacaoDto = (RequisicaoLiberacaoDTO) liberacaoDao.restore(liberacaoDto);

            if (auxliberacaoDto != null && auxliberacaoDto.getDataHoraInicio() != null) {
                liberacaoDto.setDataHoraInicio(auxliberacaoDto.getDataHoraInicio());
            }

            if (liberacaoDto != null && liberacaoDto.getIdTipoLiberacao() != null) {
                tipoLiberacaoDto.setIdTipoLiberacao(liberacaoDto.getIdTipoLiberacao());
                tipoLiberacaoDto = (TipoLiberacaoDTO) tipoLiberacaoDAO.restore(tipoLiberacaoDto);
            }

            if (auxliberacaoDto != null && auxliberacaoDto.getIdGrupoNivel1() == null) {
                auxliberacaoDto.setIdGrupoNivel1(auxliberacaoDto.getIdGrupoNivel1());
            }

            if (liberacaoDto != null && liberacaoDto.getIdGrupoNivel1() == null || liberacaoDto.getIdGrupoNivel1().intValue() <= 0) {
                liberacaoDto.setIdGrupoNivel1(tipoLiberacaoDto.getIdGrupoExecutor());
            }

            if (liberacaoDto != null && liberacaoDto.getDataHoraTerminoAgendada() != null && liberacaoDto.getDataHoraInicioAgendada() != null) {
                this.determinaPrazo(liberacaoDto);
            } else {
                if (liberacaoDto != null) {
                    liberacaoDto.setPrazoHH(00);
                    liberacaoDto.setPrazoMM(00);
                }
            }
            if (!liberacaoDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoLiberacao.Cancelada.name())) {
                if (liberacaoDto.getAlterarSituacao() != null && liberacaoDto.getAlterarSituacao().equalsIgnoreCase("N")) {
                    liberacaoDto.setStatus(this.getStatusAtual(liberacaoDto.getIdRequisicaoLiberacao()));
                }
            }

            contatoRequisicaoLiberacaoDto = this.criarContatoRequisicaoLiberacao(liberacaoDto, tc);

            if (contatoRequisicaoLiberacaoDto != null) {
                liberacaoDto.setIdContatoRequisicaoLiberacao(contatoRequisicaoLiberacaoDto.getIdContatoRequisicaoLiberacao());
            }

            if (liberacaoDto.getIdGrupoAtual() == null) {
                liberacaoDto.setIdGrupoAtual(tipoLiberacaoDto.getIdGrupoExecutor());

            }
            if (liberacaoDto.getAcaoFluxo() != null && liberacaoDto.getAcaoFluxo().equalsIgnoreCase("E")) {
                // RequisicaoLiberacaoRequisicaoComprasService requisicaoComprasService =
                // (RequisicaoLiberacaoRequisicaoComprasService)
                // ServiceLocator.getInstance().getService(RequisicaoLiberacaoRequisicaoComprasService.class, null);
                // Collection<RequisicaoLiberacaoRequisicaoComprasDTO> colReqCompras =
                // requisicaoComprasService.findByIdLiberacaoAndDataFim(liberacaoDto.getIdRequisicaoLiberacao());

                /*
                 * if (liberacaoDto.getColRequisicaoCompras() != null && colReqCompras != null && colReqCompras.size()>
                 * 0){ for (RequisicaoLiberacaoRequisicaoComprasDTO reqCompras : colReqCompras){ if
                 * (!reqCompras.getSituacaoServicos().equals("Fechada") &&
                 * !reqCompras.getSituacaoServicos().equals("Resolvida")){ throw new
                 * LogicException(i18n_Message("requisicaoLiberacao.requisicaoComprasVinculadas")); } } }
                 */
                if (liberacaoDto.getColRequisicaoCompras() != null) {
                    for (final RequisicaoLiberacaoRequisicaoComprasDTO reqCompras : liberacaoDto.getColRequisicaoCompras()) {
                        if (!reqCompras.getSituacaoServicos().equals("Fechada") && !reqCompras.getSituacaoServicos().equals("Concluida")) {
                            throw new LogicException(this.i18nMessage("requisicaoLiberacao.requisicaoComprasVinculacao"));
                        }
                    }
                }

                if (liberacaoDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoLiberacao.Resolvida.name())) {
                    if (liberacaoDto.getFechamento() == null || liberacaoDto.getFechamento().equalsIgnoreCase("")) {
                        throw new LogicException(this.i18nMessage("citcorpore.comum.informeFechamento"));
                    }

                    if (!this.verificaConfirmacaoQuestionario(liberacaoDto)) {
                        throw new LogicException(this.i18nMessage("requisicaoLiberacao.verificaQuestionario"));
                    }

                }
            }

            if (liberacaoDto.getAcaoFluxo() != null && liberacaoDto.getAcaoFluxo().equalsIgnoreCase("E")) {
                if (liberacaoDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoLiberacao.NaoResolvida.name())) {
                    if (liberacaoDto.getFechamento() == null || liberacaoDto.getFechamento().equalsIgnoreCase("")) {
                        throw new LogicException(this.i18nMessage("citcorpore.comum.informeFechamento"));
                    }
                }
            }

            if (liberacaoDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoLiberacao.Cancelada.name())) {
                if (liberacaoDto.getFechamento() == null || liberacaoDto.getFechamento().equalsIgnoreCase("")) {
                    throw new LogicException(this.i18nMessage("citcorpore.comum.informeFechamento"));
                }
            }

            usuarioDto = usuarioDao.restoreByIdEmpregado(liberacaoDto.getIdSolicitante());
            if (usuarioDto != null) {
                liberacaoDto.setUsuarioSolicitante(usuarioDto.getLogin());
            }

            if (usuarioDto != null && liberacaoDto.getUsuarioDto() != null && liberacaoDto.getUsuarioDto().getIdUsuario() != null) {
                liberacaoDto.setIdProprietario(liberacaoDto.getUsuarioDto().getIdUsuario());
            }

            final ExecucaoLiberacaoServiceEjb execucaoLiberacaoService = new ExecucaoLiberacaoServiceEjb();
            if (liberacaoDto.getIdTarefa() == null) {
                liberacaoDao.updateNotNull(liberacaoDto);
            } else {
                if (liberacaoDto.getFase() != null && !liberacaoDto.getFase().equalsIgnoreCase("")) {
                    liberacaoDao.updateFase(liberacaoDto.getIdRequisicaoLiberacao(), liberacaoDto.getFase());
                    liberacaoDao.updateNotNull(liberacaoDto);
                } else {
                    if (tipoLiberacaoDto != null) {
                        liberacaoDao.updateNotNull(model);
                    } else {
                        throw new LogicException(this.i18nMessage("requisicaoLiberacao.categoriaLiberacaoNaoLocalizada"));
                    }
                }
                if (liberacaoDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoLiberacao.Cancelada.name())) {
                    execucaoLiberacaoService.encerra(liberacaoDto.getUsuarioDto(), liberacaoDto, tc);
                } else {

                    // ############################################# INICIA ROTINA DO FLUXO
                    // #######################################
                    execucaoLiberacaoService.executa(liberacaoDto, liberacaoDto.getIdTarefa(), liberacaoDto.getAcaoFluxo(), tc);

                    // ############################################# ALTERA GRUPO EXECUTOR
                    // #######################################
                    if (auxliberacaoDto != null && liberacaoDto.getIdGrupoAtual() != null
                            && UtilStrings.nullToVazio(liberacaoDto.getAcaoFluxo()).equals(br.com.centralit.bpm.util.Enumerados.ACAO_EXECUTAR)
                            && (!auxliberacaoDto.escalada() || auxliberacaoDto.getIdGrupoAtual().intValue() != liberacaoDto.getIdGrupoAtual().intValue())) {
                        execucaoLiberacaoService.direcionaAtendimento(liberacaoDto, tc);
                    }
                }
            }

            // esse bloco grava a ocorrencia a partir do botão adicionar registro de execução
            if (liberacaoDto.getRegistroexecucao() != null) {
                final Source source = new Source(liberacaoDto.getRegistroexecucao());
                liberacaoDto.setRegistroexecucao(source.getTextExtractor().toString());

                if (liberacaoDto.getRegistroexecucao() != null && !liberacaoDto.getRegistroexecucao().trim().equalsIgnoreCase("")) {
                    final OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
                    ocorrenciaLiberacaoDao.setTransactionControler(tc);
                    final OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDTO = new OcorrenciaLiberacaoDTO();
                    ocorrenciaLiberacaoDTO.setIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
                    ocorrenciaLiberacaoDTO.setDataregistro(UtilDatas.getDataAtual());
                    ocorrenciaLiberacaoDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
                    ocorrenciaLiberacaoDTO.setTempoGasto(0);
                    ocorrenciaLiberacaoDTO.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
                    ocorrenciaLiberacaoDTO.setDataInicio(UtilDatas.getDataAtual());
                    ocorrenciaLiberacaoDTO.setDataFim(UtilDatas.getDataAtual());
                    ocorrenciaLiberacaoDTO.setInformacoesContato("não se aplica");
                    ocorrenciaLiberacaoDTO.setRegistradopor(liberacaoDto.getUsuarioDto().getLogin());
                    try {
                        ocorrenciaLiberacaoDTO.setDadosLiberacao(new Gson().toJson(liberacaoDto));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    ocorrenciaLiberacaoDTO.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
                    ocorrenciaLiberacaoDTO.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Criacao.getSigla());
                    ocorrenciaLiberacaoDTO.setOcorrencia(liberacaoDto.getRegistroexecucao());
                    ocorrenciaLiberacaoDao.create(ocorrenciaLiberacaoDTO);
                }
            }

            /* Gravando o historico */
            HistoricoLiberacaoDTO historicoLiberacaoDTO = new HistoricoLiberacaoDTO();
            final HistoricoLiberacaoDao historicoLiberacaoDao = new HistoricoLiberacaoDao();
            historicoLiberacaoDao.setTransactionControler(tc);
            if (liberacaoDto.getIdRequisicaoLiberacao() != null) {
                historicoLiberacaoDTO = (HistoricoLiberacaoDTO) historicoLiberacaoDao.create(this.createHistoricoLiberacao(liberacaoDto));
                final ControleGEDDao controleGEDDao = new ControleGEDDao();
                controleGEDDao.setTransactionControler(tc);

                historicoLiberacaoDTO.setListRequisicaoLiberacaoItemConfiguracaoDTO(this.listarICsLiberacaoRequisicao(historicoLiberacaoDTO));
                if (historicoLiberacaoDTO.getListRequisicaoLiberacaoItemConfiguracaoDTO() != null) {
                    this.gravarICsRequisicaoLiberacao(historicoLiberacaoDTO, tc);
                }
                historicoLiberacaoDTO.setColMudancas(this.listarColMudancas(historicoLiberacaoDTO));
                if (historicoLiberacaoDTO.getColMudancas() != null) {
                    this.gravarMudancasHistorico(historicoLiberacaoDTO, tc);
                }
                historicoLiberacaoDTO.setColProblemas(this.listarColProblemas(historicoLiberacaoDTO));
                if (historicoLiberacaoDTO.getColProblemas() != null) {
                    this.gravarProblemasHistorico(historicoLiberacaoDTO, tc);
                }
                historicoLiberacaoDTO.setColMidia(this.listarColMidias(historicoLiberacaoDTO));
                if (historicoLiberacaoDTO.getColMidia() != null) {
                    for (final RequisicaoLiberacaoMidiaDTO requisicaoLiberacaoMidiaDTO : historicoLiberacaoDTO.getColMidia()) {
                        ligacaoMidiaDto.setIdRequisicaoLiberacao(historicoLiberacaoDTO.getIdRequisicaoLiberacao());
                        ligacaoMidiaDto.setIdRequisicaoLiberacaoMidia(requisicaoLiberacaoMidiaDTO.getIdRequisicaoLiberacaoMidia());
                        ligacaoMidiaDto.setIdHistoricoLiberacao(historicoLiberacaoDTO.getIdHistoricoLiberacao());
                        ligacaoMidiaDao.create(ligacaoMidiaDto);
                        ligacaoMidiaDto = new LigacaoRequisicaoLiberacaoHistoricoMidiaDTO();
                    }
                }
                historicoLiberacaoDTO.setColResponsaveis(this.listarColResponsaveis(historicoLiberacaoDTO));
                if (historicoLiberacaoDTO.getColResponsaveis() != null) {
                    for (final RequisicaoLiberacaoResponsavelDTO requisicaoLiberacaoRespDTO : historicoLiberacaoDTO.getColResponsaveis()) {
                        ligacaoResponsavelDTO.setIdRequisicaoLiberacao(historicoLiberacaoDTO.getIdRequisicaoLiberacao());
                        ligacaoResponsavelDTO.setIdRequisicaoLiberacaoResp(requisicaoLiberacaoRespDTO.getIdRequisicaoLiberacaoResp());
                        ligacaoResponsavelDTO.setIdHistoricoLiberacao(historicoLiberacaoDTO.getIdHistoricoLiberacao());
                        ligacaoResponsavelDao.create(ligacaoResponsavelDTO);
                        ligacaoResponsavelDTO = new LigacaoRequisicaoLiberacaoHistoricoResponsavelDTO();
                    }
                }
                historicoLiberacaoDTO.setColRequisicaoCompras(this.listarColCompras(historicoLiberacaoDTO));
                if (historicoLiberacaoDTO.getColRequisicaoCompras() != null) {
                    for (final RequisicaoLiberacaoRequisicaoComprasDTO requisicaoLiberacaoComprasDTO : historicoLiberacaoDTO.getColRequisicaoCompras()) {
                        ligacaoComprasDTO.setIdRequisicaoLiberacao(historicoLiberacaoDTO.getIdRequisicaoLiberacao());
                        ligacaoComprasDTO.setIdRequisicaoLiberacaoCompras(requisicaoLiberacaoComprasDTO.getIdRequisicaoLiberacaoCompras());
                        ligacaoComprasDTO.setIdHistoricoLiberacao(historicoLiberacaoDTO.getIdHistoricoLiberacao());
                        ligacaoComprasDao.create(ligacaoComprasDTO);
                        ligacaoComprasDTO = new LigacaoRequisicaoLiberacaoHistoricoComprasDTO();
                    }
                }
            }

            // esse bloco grava os anexos da liberação
            final ControleGEDDao gedDao = new ControleGEDDao();
            gedDao.setTransactionControler(tc);

            /** Comentado pois estava deletando o arquivo e pegando referencia incorreta **/
            // /gedDao.deleteByIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao(),
            // ControleGEDDTO.TABELA_REQUISICAOLIBERACAO);
            final HistoricoGEDDao historicoGEDDao = new HistoricoGEDDao();
            Collection<HistoricoGEDDTO> colHistoricoGed = new ArrayList<HistoricoGEDDTO>();
            colHistoricoGed = historicoGEDDao.listByIdTabelaAndIdLiberacao(ControleGEDDTO.TABELA_REQUISICAOLIBERACAO, liberacaoDto.getIdRequisicaoLiberacao());
            if (colHistoricoGed != null && colHistoricoGed.size() > 0) {
                for (final HistoricoGEDDTO historicoGEDDTO : colHistoricoGed) {
                    historicoGEDDTO.setDataFim(UtilDatas.getDataAtual());
                    historicoGEDDTO.setIdHistoricoMudanca(historicoLiberacaoDTO.getIdHistoricoLiberacao());
                    historicoGEDDao.update(historicoGEDDTO);
                }
            }
            if (liberacaoDto.getColArquivosUpload() != null /* && liberacaoDto.getColArquivosUpload().size() > 0 */) {
                this.gravaInformacoesGED(liberacaoDto, tc, historicoLiberacaoDTO);
            }

            // lista todos os anexos de decumentos legais para setar a data fim para o historico
            Collection<HistoricoGEDDTO> colHistoricoGedDocsLegais = new ArrayList<HistoricoGEDDTO>();
            colHistoricoGedDocsLegais = historicoGEDDao.listByIdTabelaAndIdLiberacao(ControleGEDDTO.TABELA_DOCSLEGAIS_REQUISICAOLIBERACAO, liberacaoDto.getIdRequisicaoLiberacao());
            if (colHistoricoGedDocsLegais != null && colHistoricoGedDocsLegais.size() > 0) {
                for (final HistoricoGEDDTO historicoGEDDTODocsLegais : colHistoricoGedDocsLegais) {
                    historicoGEDDTODocsLegais.setDataFim(UtilDatas.getDataAtual());
                    historicoGEDDTODocsLegais.setIdHistoricoMudanca(historicoLiberacaoDTO.getIdHistoricoLiberacao());
                    historicoGEDDao.update(historicoGEDDTODocsLegais);
                }
            }
            if (liberacaoDto.getColArquivosUploadDocsLegais() != null /*
                                                                       * &&
                                                                       * liberacaoDto.getColArquivosUploadDocsLegais()
                                                                       * .size() > 0
                                                                       */) {
                this.gravaGEDDocLegais(liberacaoDto, tc, historicoLiberacaoDTO);
            }

            // lista todos os anexos de decumentos legais para setar a data fim para o historico
            Collection<HistoricoGEDDTO> colHistoricoGedDocsGerais = new ArrayList<HistoricoGEDDTO>();
            colHistoricoGedDocsGerais = historicoGEDDao.listByIdTabelaAndIdLiberacao(ControleGEDDTO.TABELA_DOCSGERAIS_REQUISICAOLIBERACAO, liberacaoDto.getIdRequisicaoLiberacao());
            if (colHistoricoGedDocsGerais != null && colHistoricoGedDocsGerais.size() > 0) {
                for (final HistoricoGEDDTO historicoGEDDTODocsGerais : colHistoricoGedDocsGerais) {
                    historicoGEDDTODocsGerais.setDataFim(UtilDatas.getDataAtual());
                    historicoGEDDTODocsGerais.setIdHistoricoMudanca(historicoLiberacaoDTO.getIdHistoricoLiberacao());
                    historicoGEDDao.update(historicoGEDDTODocsGerais);
                }
            }
            if (liberacaoDto.getColDocsGerais() != null /* && liberacaoDto.getColDocsGerais().size() > 0 */) {
                this.gravaGEDDocGerais(liberacaoDto, tc, historicoLiberacaoDTO);
            }

            // lista todos os anexos de decumentos legais para setar a data fim para o historico
            Collection<HistoricoGEDDTO> colHistoricoGedPlanoReversao = new ArrayList<HistoricoGEDDTO>();
            colHistoricoGedPlanoReversao = historicoGEDDao.listByIdTabelaAndIdLiberacao(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA, liberacaoDto.getIdMudanca());
            if (colHistoricoGedPlanoReversao != null && colHistoricoGedPlanoReversao.size() > 0) {
                for (final HistoricoGEDDTO historicoGEDDTOPlanoReversao : colHistoricoGedPlanoReversao) {
                    historicoGEDDTOPlanoReversao.setDataFim(UtilDatas.getDataAtual());
                    historicoGEDDTOPlanoReversao.setIdHistoricoMudanca(historicoLiberacaoDTO.getIdHistoricoLiberacao());
                    historicoGEDDao.update(historicoGEDDTOPlanoReversao);
                }
            }
            if (liberacaoDto.getColDocsGerais() != null /* && liberacaoDto.getColDocsGerais().size() > 0 */) {
                final RequisicaoMudancaDTO requisicaomudacaDTO = new RequisicaoMudancaDTO();
                final HistoricoMudancaDTO historicoMudancaDTO = new HistoricoMudancaDTO();

                this.gravaPlanoDeReversaoGED(requisicaomudacaDTO, tc, historicoMudancaDTO);
            }

            // gravar ICs vinculados a liberacao
            this.gravarICsRequisicaoLiberacao(liberacaoDto, tc);

            liberacaoMudancaDao.deleteByIdLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
            final Collection<LiberacaoMudancaDTO> colMudancas = liberacaoDto.getColMudancas();
            if (colMudancas != null) {
                for (final LiberacaoMudancaDTO liberacaoMudancaDto : colMudancas) {
                    liberacaoMudancaDto.setIdLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
                    liberacaoMudancaDto.setIdHistoricoLiberacao(null);
                    liberacaoMudancaDao.create(liberacaoMudancaDto);
                }
            }

            liberacaoProblemaDao.deleteByIdLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
            final Collection<LiberacaoProblemaDTO> colProblema = liberacaoDto.getColProblemas();
            if (colProblema != null) {
                for (final LiberacaoProblemaDTO liberacaoProblemaDTO : colProblema) {
                    liberacaoProblemaDTO.setIdLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
                    liberacaoProblemaDao.create(liberacaoProblemaDTO);
                }
            }

            // requisicaoLiberacaoMidiaDao.deleteByIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
            if (liberacaoDto.getColMidia() != null && liberacaoDto.getColMidia().size() > 0) {
                this.deleteAdicionaTabelaMidias(liberacaoDto, tc);
            } else {
                final RequisicaoLiberacaoMidiaDao midiaDao = new RequisicaoLiberacaoMidiaDao();
                final Collection<RequisicaoLiberacaoMidiaDTO> ListRequisicaoLiberacaoMidiaDTO = midiaDao.findByIdLiberacaoEDataFim(liberacaoDto.getIdRequisicaoLiberacao());
                if (ListRequisicaoLiberacaoMidiaDTO != null && ListRequisicaoLiberacaoMidiaDTO.size() > 0) {
                    for (final RequisicaoLiberacaoMidiaDTO requisicaoLiberacaoMidiaDTO : ListRequisicaoLiberacaoMidiaDTO) {
                        requisicaoLiberacaoMidiaDTO.setDataFim(UtilDatas.getDataAtual());
                        midiaDao.update(requisicaoLiberacaoMidiaDTO);
                    }
                }
            }

            // update Responsavel
            // liberacaoResponsavelDao.deleteByIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
            if (liberacaoDto.getColResponsaveis() != null && liberacaoDto.getColResponsaveis().size() > 0) {
                this.deleteAdicionaTabelaResponsavel(liberacaoDto, tc);
            } else {
                final RequisicaoLiberacaoResponsavelDao requisicaoLiberacaoResponsavelDao = new RequisicaoLiberacaoResponsavelDao();
                final Collection<RequisicaoLiberacaoResponsavelDTO> responsavel = requisicaoLiberacaoResponsavelDao.findByIdLiberacaoEDataFim(liberacaoDto
                        .getIdRequisicaoLiberacao());
                if (responsavel != null && responsavel.size() > 0) {
                    for (final RequisicaoLiberacaoResponsavelDTO requisicaoLiberacaoResponsavelDTO : responsavel) {
                        requisicaoLiberacaoResponsavelDTO.setDataFim(UtilDatas.getDataAtual());
                        requisicaoLiberacaoResponsavelDao.update(requisicaoLiberacaoResponsavelDTO);
                    }
                }
            }
            /*
             * Collection<RequisicaoLiberacaoResponsavelDTO> colLiberacaoResponsavelResp =
             * liberacaoDto.getColResponsaveis(); if (colLiberacaoResponsavelResp != null) { for
             * (RequisicaoLiberacaoResponsavelDTO liberacaoResponsavelDTO : colLiberacaoResponsavelResp) {
             * liberacaoResponsavelDTO.setIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
             * liberacaoResponsavelDao.create(liberacaoResponsavelDTO); } }
             */

            // update Requisicao Compras
            /*
             * requisicaoLiberacaoPedidoComprasDao.deleteByIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao())
             * ; Collection<RequisicaoLiberacaoRequisicaoComprasDTO> colReqCompras =
             * liberacaoDto.getColRequisicaoCompras(); if (colReqCompras != null) { for
             * (RequisicaoLiberacaoRequisicaoComprasDTO liberacaoPedidoComprasDTO : colReqCompras) {
             * liberacaoPedidoComprasDTO.setIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
             * requisicaoLiberacaoPedidoComprasDao.create(liberacaoPedidoComprasDTO); } }
             */
            if (liberacaoDto.getColRequisicaoCompras() != null && liberacaoDto.getColRequisicaoCompras().size() > 0) {
                this.deleteAdicionaTabelaCompras(liberacaoDto, tc);
            } else {
                final RequisicaoLiberacaoRequisicaoComprasDAO requisicaoComprasDAO = new RequisicaoLiberacaoRequisicaoComprasDAO();
                final Collection<RequisicaoLiberacaoRequisicaoComprasDTO> requisicaoCompras = requisicaoComprasDAO.findByIdLiberacaoAndDataFim(liberacaoDto
                        .getIdRequisicaoLiberacao());
                if (requisicaoCompras != null && requisicaoCompras.size() > 0) {
                    for (final RequisicaoLiberacaoRequisicaoComprasDTO requisicaoLiberacaoRequisicaoComprasDTO : requisicaoCompras) {
                        requisicaoLiberacaoRequisicaoComprasDTO.setDataFim(UtilDatas.getDataAtual());
                        requisicaoComprasDAO.update(requisicaoLiberacaoRequisicaoComprasDTO);
                    }
                }

            }

            tc.commit();

            if (liberacaoDto.getDataHoraInicioAgendada() != null) {
                RequisicaoLiberacao.salvaGrupoAtvPeriodicaEAgenda(liberacaoDto);
            }

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.closeQuietly();
        }
    }

    private void gravarICsRequisicaoLiberacao(final RequisicaoLiberacaoDTO liberacaoDto, final TransactionControler tc) throws ServiceException, Exception {
        ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> icsBanco = null;
        RequisicaoLiberacaoItemConfiguracaoDTO requisicaoLiberacaoItemConfiguracaoDTO2 = new RequisicaoLiberacaoItemConfiguracaoDTO();
        final RequisicaoLiberacaoItemConfiguracaoDao requisicaoLiberacaoItemConfiguracaoDao = new RequisicaoLiberacaoItemConfiguracaoDao();
        if (tc != null) {
            requisicaoLiberacaoItemConfiguracaoDao.setTransactionControler(tc);
        }

        if (liberacaoDto.getListRequisicaoLiberacaoItemConfiguracaoDTO() != null) {
            // se não existir no banco, cria, caso contrário, atualiza
            for (final RequisicaoLiberacaoItemConfiguracaoDTO requisicaoLiberacaoItemConfiguracaoDTO : liberacaoDto.getListRequisicaoLiberacaoItemConfiguracaoDTO()) {

                requisicaoLiberacaoItemConfiguracaoDTO.setIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());

                requisicaoLiberacaoItemConfiguracaoDTO2 = requisicaoLiberacaoItemConfiguracaoDao.restoreByChaveComposta(requisicaoLiberacaoItemConfiguracaoDTO);

                if (requisicaoLiberacaoItemConfiguracaoDTO2 == null) {
                    requisicaoLiberacaoItemConfiguracaoDao.create(requisicaoLiberacaoItemConfiguracaoDTO);
                } else {
                    requisicaoLiberacaoItemConfiguracaoDao.update(requisicaoLiberacaoItemConfiguracaoDTO2);
                }
            }
        }
        // confere se existe algo no banco que não está na lista salva, e deleta
        icsBanco = requisicaoLiberacaoItemConfiguracaoDao.listByIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
        if (icsBanco != null) {
            for (final RequisicaoLiberacaoItemConfiguracaoDTO i : icsBanco) {
                if (!this.requisicaoLiberacaoICExisteNaLista(i, liberacaoDto.getListRequisicaoLiberacaoItemConfiguracaoDTO())) {
                    requisicaoLiberacaoItemConfiguracaoDao.delete(i);
                }
            }
        }
    }

    private void gravarICsRequisicaoLiberacao(final HistoricoLiberacaoDTO historicoLiberacaoDTO, final TransactionControler tc) throws ServiceException, Exception {
        final RequisicaoLiberacaoItemConfiguracaoDao requisicaoLiberacaoItemConfiguracaoDao = new RequisicaoLiberacaoItemConfiguracaoDao();
        if (tc != null) {
            requisicaoLiberacaoItemConfiguracaoDao.setTransactionControler(tc);
        }

        if (historicoLiberacaoDTO.getListRequisicaoLiberacaoItemConfiguracaoDTO() != null) {
            // se não existir no banco, cria, caso contrário, atualiza
            for (final RequisicaoLiberacaoItemConfiguracaoDTO requisicaoLiberacaoItemConfiguracaoDTO : historicoLiberacaoDTO.getListRequisicaoLiberacaoItemConfiguracaoDTO()) {

                requisicaoLiberacaoItemConfiguracaoDTO.setIdHistoricoLiberacao(historicoLiberacaoDTO.getIdHistoricoLiberacao());
                requisicaoLiberacaoItemConfiguracaoDTO.setIdRequisicaoLiberacao(null);
                requisicaoLiberacaoItemConfiguracaoDao.create(requisicaoLiberacaoItemConfiguracaoDTO);
            }
        }

    }

    private void gravarMudancasHistorico(final HistoricoLiberacaoDTO historicoLiberacaoDTO, final TransactionControler tc) throws ServiceException, Exception {
        final LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();

        if (tc != null) {
            liberacaoMudancaDao.setTransactionControler(tc);
        }

        if (historicoLiberacaoDTO.getColMudancas() != null) {
            // grava no banco os historicos de mudanças.
            for (final LiberacaoMudancaDTO liberacaoMudancaDTO : historicoLiberacaoDTO.getColMudancas()) {

                liberacaoMudancaDTO.setIdHistoricoLiberacao(historicoLiberacaoDTO.getIdHistoricoLiberacao());
                liberacaoMudancaDTO.setIdLiberacao(historicoLiberacaoDTO.getIdRequisicaoLiberacao());
                liberacaoMudancaDao.create(liberacaoMudancaDTO);
            }
        }

    }

    private void gravarProblemasHistorico(final HistoricoLiberacaoDTO historicoLiberacaoDTO, final TransactionControler tc) throws ServiceException, Exception {
        final LiberacaoProblemaDao liberacaoProblemaDao = new LiberacaoProblemaDao();
        if (tc != null) {
            liberacaoProblemaDao.setTransactionControler(tc);
        }

        if (historicoLiberacaoDTO.getColMudancas() != null) {
            // se não existir no banco, cria, caso contrário, atualiza
            for (final LiberacaoProblemaDTO liberacaoProblemaDTO : historicoLiberacaoDTO.getColProblemas()) {

                liberacaoProblemaDTO.setIdHistoricoLiberacao(historicoLiberacaoDTO.getIdHistoricoLiberacao());
                liberacaoProblemaDTO.setIdLiberacao(null);
                liberacaoProblemaDao.create(liberacaoProblemaDTO);
            }
        }

    }

    public List<RequisicaoLiberacaoItemConfiguracaoDTO> listarICsLiberacaoRequisicao(final HistoricoLiberacaoDTO historicoLiberacaoDTO) throws ServiceException, Exception {
        final RequisicaoLiberacaoItemConfiguracaoDao requisicaoLiberacaoDao = new RequisicaoLiberacaoItemConfiguracaoDao();
        final List<RequisicaoLiberacaoItemConfiguracaoDTO> listICsRequisicaoLiberacao = requisicaoLiberacaoDao.listByIdRequisicaoLiberacao(historicoLiberacaoDTO
                .getIdRequisicaoLiberacao());

        return listICsRequisicaoLiberacao;
    }

    public Collection<LiberacaoMudancaDTO> listarColMudancas(final HistoricoLiberacaoDTO historicoLiberacaoDTO) throws ServiceException, Exception {
        final LiberacaoMudancaDao requisicaoLiberacaoDao = new LiberacaoMudancaDao();
        final Collection<LiberacaoMudancaDTO> listmudancas = requisicaoLiberacaoDao.findByIdLiberacao2(historicoLiberacaoDTO.getIdRequisicaoLiberacao());

        return listmudancas;
    }

    public Collection<LiberacaoProblemaDTO> listarColProblemas(final HistoricoLiberacaoDTO historicoLiberacaoDTO) throws ServiceException, Exception {
        final LiberacaoProblemaDao liberacaoProblemaDao = new LiberacaoProblemaDao();
        final Collection<LiberacaoProblemaDTO> listProblemas = liberacaoProblemaDao.listByIdRequisicaoLiberacao(historicoLiberacaoDTO.getIdRequisicaoLiberacao());

        return listProblemas;
    }

    public Collection<RequisicaoLiberacaoMidiaDTO> listarColMidias(final HistoricoLiberacaoDTO historicoLiberacaoDTO) throws ServiceException, Exception {
        final RequisicaoLiberacaoMidiaDao midiaDao = new RequisicaoLiberacaoMidiaDao();
        final Collection<RequisicaoLiberacaoMidiaDTO> listMidias = midiaDao.findByIdLiberacao(historicoLiberacaoDTO.getIdRequisicaoLiberacao());

        return listMidias;
    }

    public Collection<RequisicaoLiberacaoResponsavelDTO> listarColResponsaveis(final HistoricoLiberacaoDTO historicoLiberacaoDTO) throws ServiceException, Exception {
        final RequisicaoLiberacaoResponsavelDao respDao = new RequisicaoLiberacaoResponsavelDao();
        final Collection<RequisicaoLiberacaoResponsavelDTO> listResp = respDao.findByIdLiberacaoEDataFim(historicoLiberacaoDTO.getIdRequisicaoLiberacao());

        return listResp;
    }

    public Collection<RequisicaoLiberacaoRequisicaoComprasDTO> listarColCompras(final HistoricoLiberacaoDTO historicoLiberacaoDTO) throws ServiceException, Exception {
        final RequisicaoLiberacaoRequisicaoComprasDAO comprasDao = new RequisicaoLiberacaoRequisicaoComprasDAO();
        final Collection<RequisicaoLiberacaoRequisicaoComprasDTO> listCompras = comprasDao.findByIdLiberacaoAndDataFim(historicoLiberacaoDTO.getIdRequisicaoLiberacao());

        return listCompras;
    }

    /**
     * Verifica se o item existe na lista.
     *
     * @param item
     * @param lista
     * @return
     */
    private boolean requisicaoLiberacaoICExisteNaLista(final RequisicaoLiberacaoItemConfiguracaoDTO item, final List<RequisicaoLiberacaoItemConfiguracaoDTO> lista) {
        if (lista == null) {
            return false;
        }
        for (final RequisicaoLiberacaoItemConfiguracaoDTO l : lista) {
            if (l.equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void delete(final IDto model) throws ServiceException, LogicException {
        final RequisicaoLiberacaoDao liberacaoDao = this.getDao();
        final LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();
        final TransactionControler tc = new TransactionControlerImpl(liberacaoDao.getAliasDB());

        try {
            this.validaDelete(model);

            liberacaoDao.setTransactionControler(tc);
            liberacaoMudancaDao.setTransactionControler(tc);

            tc.start();

            final RequisicaoLiberacaoDTO liberacaoDto = (RequisicaoLiberacaoDTO) model;
            liberacaoMudancaDao.deleteByIdLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
            liberacaoDao.delete(liberacaoDto);

            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.closeQuietly();
        }
    }

    @Override
    public List<RequisicaoLiberacaoDTO> listLiberacoes() throws Exception {
        return this.getDao().listLiberacoes();
    }

    @Override
    public void reativa(final UsuarioDTO usuarioDto, final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception {
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        this.reativa(usuarioDto, requisicaoLiberacaoDTO, tc);
        tc.commit();
        tc.close();
    }

    public void reativa(final UsuarioDTO usuarioDto, final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO, final TransactionControler tc) throws Exception {
        final RequisicaoLiberacaoDTO requisicaoLiberacaoAuxDto = this.restoreAll(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao(), tc);
        new ExecucaoLiberacaoServiceEjb().reativa(usuarioDto, requisicaoLiberacaoAuxDto, tc);
    }

    private void determinaPrazo(final RequisicaoLiberacaoDTO requisicaoDto, final Integer idCalendarioParm) throws Exception {
        if (requisicaoDto.getDataHoraTerminoAgendada() == null) {
            throw new LogicException(this.i18nMessage("citcorpore.comum.Data/horaTerminoNaoDefinida"));
        }

        CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendarioParm, requisicaoDto.getDataHoraInicioAgendada());
        calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, requisicaoDto.getDataHoraTerminoAgendada(), null);
        requisicaoDto.setPrazoHH(calculoDto.getTempoDecorridoHH());
        requisicaoDto.setPrazoMM(calculoDto.getTempoDecorridoMM());
    }

    @Override
    public RequisicaoLiberacaoDTO restoreAll(final Integer idRequisicaoLiberacao) throws Exception {
        return this.restoreAll(idRequisicaoLiberacao, null);
    }

    public RequisicaoLiberacaoDTO restoreAll(final Integer idRequisicaoLiberacao, final TransactionControler tc) throws Exception {
        final RequisicaoLiberacaoDao requisicaoDao = this.getDao();
        if (tc != null) {
            requisicaoDao.setTransactionControler(tc);
        }

        RequisicaoLiberacaoDTO requisicaoDto = new RequisicaoLiberacaoDTO();
        requisicaoDto.setIdRequisicaoLiberacao(idRequisicaoLiberacao);
        requisicaoDto = (RequisicaoLiberacaoDTO) requisicaoDao.restore(requisicaoDto);
        if (requisicaoDto != null) {
            if (requisicaoDto.getDescricao() != null) {
                final Source source = new Source(requisicaoDto.getDescricao());
                requisicaoDto.setDescricao(source.getTextExtractor().toString());
            }

            if (requisicaoDto != null) {

                if (requisicaoDto != null && requisicaoDto.getDataHoraInicioAgendada() != null) {
                    final Timestamp dataHoraTerminoAgendada = requisicaoDto.getDataHoraInicioAgendada();
                    final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    final String horaAgendamentoInicialSTR = format.format(dataHoraTerminoAgendada);
                    final String horaInicial = horaAgendamentoInicialSTR.substring(11, 16);
                    requisicaoDto.setHoraAgendamentoInicial(horaInicial.trim());
                }
                if (requisicaoDto != null && requisicaoDto.getDataHoraTerminoAgendada() != null) {
                    final Timestamp dataHoraTerminoAgendada = requisicaoDto.getDataHoraTerminoAgendada();
                    final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    final String horaAgendamentoFinallSTR = format.format(dataHoraTerminoAgendada);
                    final String horaFinal = horaAgendamentoFinallSTR.substring(11, 16);
                    requisicaoDto.setHoraAgendamentoFinal(horaFinal.trim());
                }

                requisicaoDto.setDataHoraTerminoStr(requisicaoDto.getDataHoraTerminoStr());

                final EmpregadoDTO empregadoDto = new EmpregadoDao().restoreByIdEmpregado(requisicaoDto.getIdSolicitante());
                if (empregadoDto != null) {
                    requisicaoDto.setNomeSolicitante(empregadoDto.getNome());
                    requisicaoDto.setEmailSolicitante(empregadoDto.getEmail());
                }

                if (requisicaoDto.getIdProprietario() != null) {
                    UsuarioDTO usuarioDto = new UsuarioDTO();
                    final UsuarioDao usuarioDao = new UsuarioDao();
                    usuarioDto.setIdUsuario(requisicaoDto.getIdProprietario());
                    usuarioDto = (UsuarioDTO) usuarioDao.restore(usuarioDto);
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

                if (requisicaoDto.getIdGrupoAprovador() != null) {
                    final GrupoDao grupoDao = new GrupoDao();
                    GrupoDTO grupoDto = new GrupoDTO();
                    grupoDto.setIdGrupo(requisicaoDto.getIdGrupoAprovador());
                    grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
                    if (grupoDto != null) {
                        requisicaoDto.setNomeGrupoAprovador(grupoDto.getSigla());
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

                if (requisicaoDto.getIdTipoLiberacao() != null) {
                    TipoLiberacaoDTO tipoLiberacao = new TipoLiberacaoDTO();
                    final TipoLiberacaoDAO tipoMudancaDao = new TipoLiberacaoDAO();

                    tipoLiberacao.setIdTipoLiberacao(requisicaoDto.getIdTipoLiberacao());
                    tipoLiberacao = (TipoLiberacaoDTO) tipoMudancaDao.restore(tipoLiberacao);
                    if (tipoLiberacao != null) {
                        requisicaoDto.setTipo(tipoLiberacao.getNomeTipoLiberacao());
                    }
                }

            }
            return this.verificaAtraso(requisicaoDto);
        }
        return null;
    }

    public RequisicaoLiberacaoDTO verificaAtraso(final RequisicaoLiberacaoDTO requisicaoDto) throws Exception {
        if (requisicaoDto == null) {
            return null;
        }

        long atrasoSLA = 0;

        if (requisicaoDto.getDataHoraTerminoAgendada() != null) {
            final Timestamp dataHoraLimite = requisicaoDto.getDataHoraTerminoAgendada();
            Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
            if (requisicaoDto.encerrada()) {
                if (requisicaoDto.getDataHoraConclusao() != null) {
                    dataHoraComparacao = requisicaoDto.getDataHoraConclusao();
                }
            }

            if (dataHoraLimite != null) {
                if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
                    atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraComparacao, dataHoraLimite) / 1000;
                }
            }
        }

        requisicaoDto.setAtraso(atrasoSLA);
        return requisicaoDto;
    }

    private void determinaPrazo(final RequisicaoLiberacaoDTO requisicaoDto) throws Exception {
        if (requisicaoDto.getDataHoraTerminoAgendada() == null) {
            throw new LogicException(this.i18nMessage("citcorpore.comum.Data/horaTerminoNaoDefinida"));
        }

        TipoLiberacaoDTO tipoLiberacaoDto = new TipoLiberacaoDTO();
        final TipoLiberacaoDAO tipoLiberacaoDAO = new TipoLiberacaoDAO();
        tipoLiberacaoDto.setIdTipoLiberacao(requisicaoDto.getIdTipoLiberacao());
        tipoLiberacaoDto = (TipoLiberacaoDTO) tipoLiberacaoDAO.restore(tipoLiberacaoDto);

        CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(tipoLiberacaoDto.getIdCalendario(), requisicaoDto.getDataHoraInicioAgendada());
        calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, requisicaoDto.getDataHoraTerminoAgendada(), null);
        requisicaoDto.setPrazoHH(calculoDto.getTempoDecorridoHH());
        requisicaoDto.setPrazoMM(calculoDto.getTempoDecorridoMM());
    }

    private String getStatusAtual(final Integer id) throws ServiceException, Exception {
        RequisicaoLiberacaoDTO reqLiberacao = new RequisicaoLiberacaoDTO();
        reqLiberacao.setIdRequisicaoLiberacao(id);
        reqLiberacao = (RequisicaoLiberacaoDTO) this.getDao().restore(reqLiberacao);
        final String res = reqLiberacao.getStatus();
        return res;

    }

    public void gravaInformacoesGED(final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO, final TransactionControler tc, final HistoricoLiberacaoDTO historicoLiberacaoDTO)
            throws Exception {
        final Collection<UploadDTO> colArquivosUpload = requisicaoLiberacaoDTO.getColArquivosUpload();
        new HistoricoGEDDTO();
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
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaoLiberacaoDTO.getIdEmpresa());
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaoLiberacaoDTO.getIdEmpresa() + "/" + pasta);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }
        /**
         * Grava informações do upload principal.
         * **/

        if (colArquivosUpload != null) {
            for (final UploadDTO upDto : colArquivosUpload) {
                final UploadDTO uploadDTO = upDto;
                ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

                Integer idControleGed = uploadDTO.getIdControleGED();

                /*
                 * historicoGEDDTO.setIdRequisicaoMudanca(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
                 * historicoGEDDTO.setIdTabela(ControleGEDDTO.TABELA_REQUISICAOLIBERACAO);
                 * if(historicoLiberacaoDTO.getIdHistoricoLiberacao() != null){
                 * historicoGEDDTO.setIdHistoricoMudanca(null); }else{ historicoGEDDTO.setIdHistoricoMudanca(-1); }
                 * historicoGEDDao.create(historicoGEDDTO);
                 */

                controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_REQUISICAOLIBERACAO);
                controleGEDDTO.setId(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
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
                // esse bloco grava a tabela de historicos de anexos:

                boolean existe = false;
                if (idControleGed != null) {
                    final Collection<ControleGEDDTO> colAux = controleGEDDao.listByIdTabelaAndID(ControleGEDDTO.TABELA_REQUISICAOLIBERACAO,
                            requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
                    if (colAux != null && colAux.size() > 0) {
                        for (final ControleGEDDTO historicoGedDTOAux : colAux) {
                            if (idControleGed.intValue() == historicoGedDTOAux.getIdControleGED().intValue()) {
                                idControleGed = historicoGedDTOAux.getIdControleGED();
                                existe = true;
                                break;
                            }
                        }
                    }
                }

                if (!existe) {
                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);
                    controleGEDDTO.setId(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
                    idControleGed = controleGEDDTO.getIdControleGED();
                }
                /*
                 * historicoGEDDTO.setIdControleGed(idControleGed); historicoGEDDao.update(historicoGEDDTO);
                 */

                // uploadDTO.setIdControleGED(controleGEDDTO.getIdControleGED());
                if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se
                    // utiliza
                    // GED
                    // interno e nao eh BD
                    if (controleGEDDTO != null) {
                        try {
                            final File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaoLiberacaoDTO.getIdEmpresa() + "/" + pasta + "/"
                                    + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDTO.getNameFile()));
                            CriptoUtils.encryptFile(uploadDTO.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + requisicaoLiberacaoDTO.getIdEmpresa() + "/" + pasta + "/"
                                    + controleGEDDTO.getIdControleGED() + ".ged", System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
                            arquivo.delete();
                        } catch (final Exception e) {

                        }

                    }
                } /*
                   * else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { // Se // utiliza // GED // externo //
                   * FALTA IMPLEMENTAR!!! }
                   */
            }
        }

        final Collection<ControleGEDDTO> colAnexo = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_REQUISICAOLIBERACAO,
                requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
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

    public void gravaGEDDocLegais(final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO, final TransactionControler tc, final HistoricoLiberacaoDTO historicoLiberacaoDTO)
            throws Exception {

        final Collection<UploadDTO> colArquivosUpload = requisicaoLiberacaoDTO.getColArquivosUploadDocsLegais();
        new HistoricoGEDDTO();
        final HistoricoGEDDao historicoGEDDao = new HistoricoGEDDao();

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
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaoLiberacaoDTO.getIdEmpresa());
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaoLiberacaoDTO.getIdEmpresa() + "/" + pasta);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }

        /*
         * for (Iterator it = colArquivosUpload.iterator(); it.hasNext();) { UploadDTO uploadDTO = (UploadDTO)
         * it.next(); ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
         * controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_DOCSLEGAIS_REQUISICAOLIBERACAO);
         * controleGEDDTO.setId(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
         * controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
         * controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());
         * controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));
         * controleGEDDTO.setPasta(pasta); controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile()); if
         * (!uploadDTO.getTemporario().equalsIgnoreCase("S")) { // Se nao // continue; } if
         * (PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("S") &&
         * "S".equalsIgnoreCase(prontuarioGedInternoBancoDados.trim())) { // Se // utiliza // GED // interno e eh BD
         * controleGEDDTO.setPathArquivo(uploadDTO.getPath()); // Isso vai // fazer a // gravacao // no BD. // dento do
         * // create // abaixo. } else { controleGEDDTO.setPathArquivo(null); }
         */

        if (colArquivosUpload != null) {
            for (final UploadDTO upDto : colArquivosUpload) {
                final UploadDTO uploadDTO = upDto;
                ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

                Integer idControleGed = uploadDTO.getIdControleGED();

                /*
                 * historicoGEDDTO.setIdRequisicaoMudanca(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
                 * historicoGEDDTO.setIdTabela(ControleGEDDTO.TABELA_DOCSLEGAIS_REQUISICAOLIBERACAO);
                 * if(historicoLiberacaoDTO.getIdHistoricoLiberacao() != null){
                 * historicoGEDDTO.setIdHistoricoMudanca(null); }else{ historicoGEDDTO.setIdHistoricoMudanca(-1); }
                 * historicoGEDDao.create(historicoGEDDTO);
                 */

                controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_DOCSLEGAIS_REQUISICAOLIBERACAO);
                controleGEDDTO.setId(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
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
                // esse bloco grava a tabela de historicos de anexos:

                boolean existe = false;
                if (idControleGed != null) {
                    final Collection<ControleGEDDTO> colAux = controleGEDDao.listByIdTabelaAndID(ControleGEDDTO.TABELA_DOCSLEGAIS_REQUISICAOLIBERACAO,
                            requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
                    if (colAux != null && colAux.size() > 0) {
                        for (final ControleGEDDTO historicoGedDTOAux : colAux) {
                            if (idControleGed.intValue() == historicoGedDTOAux.getIdControleGED().intValue()) {
                                idControleGed = historicoGedDTOAux.getIdControleGED();
                                existe = true;
                                break;
                            }
                        }
                    }
                }

                if (!existe) {
                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);
                    controleGEDDTO.setId(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
                    idControleGed = controleGEDDTO.getIdControleGED();
                }
                /*
                 * historicoGEDDTO.setIdControleGed(idControleGed); historicoGEDDao.update(historicoGEDDTO);
                 */

                if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se
                    // utiliza
                    // GED
                    // interno e nao eh BD
                    if (controleGEDDTO != null) {
                        try {
                            new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaoLiberacaoDTO.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
                                    + Util.getFileExtension(uploadDTO.getNameFile()));
                            CriptoUtils.encryptFile(uploadDTO.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + requisicaoLiberacaoDTO.getIdEmpresa() + "/" + pasta + "/"
                                    + controleGEDDTO.getIdControleGED() + ".ged", System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
                            // arquivo.delete();
                        } catch (final Exception e) {

                        }

                    }
                } /*
                   * else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { // Se // utiliza // GED // externo //
                   * FALTA IMPLEMENTAR!!! }
                   */
            }
        }

        final Collection<ControleGEDDTO> colAnexo = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_DOCSLEGAIS_REQUISICAOLIBERACAO,
                requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
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

    public void gravaGEDDocGerais(final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO, final TransactionControler tc, final HistoricoLiberacaoDTO historicoLiberacaoDTO)
            throws Exception {

        final Collection<UploadDTO> colArquivosUpload = requisicaoLiberacaoDTO.getColDocsGerais();
        new HistoricoGEDDTO();
        final HistoricoGEDDao historicoGEDDao = new HistoricoGEDDao();

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
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaoLiberacaoDTO.getIdEmpresa());
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaoLiberacaoDTO.getIdEmpresa() + "/" + pasta);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }

        /*
         * for (Iterator it = colArquivosUpload.iterator(); it.hasNext();) { UploadDTO uploadDTO = (UploadDTO)
         * it.next(); ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
         * controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_DOCSGERAIS_REQUISICAOLIBERACAO);
         * controleGEDDTO.setId(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
         * controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
         * controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());
         * controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));
         * controleGEDDTO.setPasta(pasta); controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile()); if
         * (!uploadDTO.getTemporario().equalsIgnoreCase("S")) { // Se nao // continue; } if
         * (PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("S") &&
         * "S".equalsIgnoreCase(prontuarioGedInternoBancoDados.trim())) { // Se // utiliza // GED // interno e eh BD
         * controleGEDDTO.setPathArquivo(uploadDTO.getPath()); // Isso vai // fazer a // gravacao // no BD. // dento do
         * // create // abaixo. } else { controleGEDDTO.setPathArquivo(null); }
         */

        if (colArquivosUpload != null) {
            for (final UploadDTO upDto : colArquivosUpload) {
                final UploadDTO uploadDTO = upDto;
                ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

                Integer idControleGed = uploadDTO.getIdControleGED();

                /*
                 * historicoGEDDTO.setIdRequisicaoMudanca(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
                 * historicoGEDDTO.setIdTabela(ControleGEDDTO.TABELA_DOCSGERAIS_REQUISICAOLIBERACAO);
                 * if(historicoLiberacaoDTO.getIdHistoricoLiberacao() != null){
                 * historicoGEDDTO.setIdHistoricoMudanca(null); }else{ historicoGEDDTO.setIdHistoricoMudanca(-1); }
                 * historicoGEDDao.create(historicoGEDDTO);
                 */

                controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_DOCSGERAIS_REQUISICAOLIBERACAO);
                controleGEDDTO.setId(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
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
                // esse bloco grava a tabela de historicos de anexos:

                boolean existe = false;
                if (idControleGed != null) {
                    final Collection<ControleGEDDTO> colAux = controleGEDDao.listByIdTabelaAndIdLiberacao(ControleGEDDTO.TABELA_DOCSGERAIS_REQUISICAOLIBERACAO,
                            requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
                    if (colAux != null && colAux.size() > 0) {
                        for (final ControleGEDDTO historicoGedDTOAux : colAux) {
                            if (idControleGed.intValue() == historicoGedDTOAux.getIdControleGED().intValue()) {
                                idControleGed = historicoGedDTOAux.getIdControleGED();
                                existe = true;
                                break;
                            }
                        }
                    }
                }

                if (!existe) {
                    controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);
                    controleGEDDTO.setId(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
                    idControleGed = controleGEDDTO.getIdControleGED();
                }
                /*
                 * historicoGEDDTO.setIdControleGed(idControleGed); historicoGEDDao.update(historicoGEDDTO);
                 */

                if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se
                    // utiliza
                    // GED
                    // interno e nao eh BD
                    if (controleGEDDTO != null) {
                        try {
                            new File(PRONTUARIO_GED_DIRETORIO + "/" + requisicaoLiberacaoDTO.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
                                    + Util.getFileExtension(uploadDTO.getNameFile()));
                            CriptoUtils.encryptFile(uploadDTO.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + requisicaoLiberacaoDTO.getIdEmpresa() + "/" + pasta + "/"
                                    + controleGEDDTO.getIdControleGED() + ".ged", System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
                            // arquivo.delete();
                        } catch (final Exception e) {

                        }

                    }
                } /*
                   * else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { // Se // utiliza // GED // externo //
                   * FALTA IMPLEMENTAR!!! }
                   */
            }

            final Collection<ControleGEDDTO> colAnexo = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_DOCSGERAIS_REQUISICAOLIBERACAO,
                    requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
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
    }

    public void gravaInformacoesGED(final HistoricoLiberacaoDTO hitoricoDto, final Collection col, final TransactionControler tc, final Integer idtabela) throws Exception {
        final Collection<UploadDTO> colArquivosUpload = col;

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
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + hitoricoDto.getIdEmpresa());
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + hitoricoDto.getIdEmpresa() + "/" + pasta);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }

        for (final UploadDTO upDto : colArquivosUpload) {
            final UploadDTO uploadDTO = upDto;
            ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
            controleGEDDTO.setIdTabela(idtabela);
            controleGEDDTO.setId(hitoricoDto.getIdHistoricoLiberacao());
            controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
            controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());
            controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));
            controleGEDDTO.setPasta(pasta);
            controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile());

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
                        final File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + hitoricoDto.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
                                + Util.getFileExtension(uploadDTO.getNameFile()));
                        CriptoUtils.encryptFile(uploadDTO.getPath(),
                                PRONTUARIO_GED_DIRETORIO + "/" + hitoricoDto.getIdEmpresa() + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", System
                                        .getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
                        arquivo.delete();
                    } catch (final Exception e) {

                    }

                }
            } /*
               * else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { // Se // utiliza // GED // externo // FALTA
               * IMPLEMENTAR!!! }
               */
        }
        final Collection<ControleGEDDTO> colAnexo = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_REQUISICAOLIBERACAO,
                hitoricoDto.getIdRequisicaoLiberacao());
        if (colAnexo != null) {
            for (final ControleGEDDTO dtoGed : colAnexo) {
                boolean b = false;
                for (final UploadDTO uploadDTO : colArquivosUpload) {
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
    public void suspende(final UsuarioDTO usuarioDto, final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception {
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        this.suspende(usuarioDto, requisicaoLiberacaoDTO, tc);
        tc.commit();
        tc.close();
    }

    public void suspende(final UsuarioDTO usuarioDto, final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO, final TransactionControler tc) throws Exception {
        final RequisicaoLiberacaoDTO requisicaoLibercaoAuxiliarDto = this.restoreAll(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao(), tc);
        requisicaoLibercaoAuxiliarDto.setIdJustificativa(requisicaoLiberacaoDTO.getIdJustificativa());
        requisicaoLibercaoAuxiliarDto.setComplementoJustificativa(requisicaoLiberacaoDTO.getComplementoJustificativa());
        new ExecucaoLiberacaoServiceEjb().suspende(usuarioDto, requisicaoLiberacaoDTO, tc);
    }

    public ContatoRequisicaoLiberacaoDTO criarContatoRequisicaoLiberacao(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto, final TransactionControler tc)
            throws ServiceException, LogicException {

        final ContatoRequisicaoLiberacaoDao contatoRequisicaoLiberacaoDao = new ContatoRequisicaoLiberacaoDao();
        ContatoRequisicaoLiberacaoDTO contatoRequisicaoLiberacaoDto = new ContatoRequisicaoLiberacaoDTO();

        // RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();

        try {
            contatoRequisicaoLiberacaoDao.setTransactionControler(tc);
            // requisicaoMudancaDao.setTransactionControler(tc);

            if (requisicaoLiberacaoDto.getIdContatoRequisicaoLiberacao() != null) {
                contatoRequisicaoLiberacaoDto.setIdContatoRequisicaoLiberacao(requisicaoLiberacaoDto.getIdContatoRequisicaoLiberacao());
                contatoRequisicaoLiberacaoDto.setNomeContato(requisicaoLiberacaoDto.getNomeContato2());
                contatoRequisicaoLiberacaoDto.setTelefoneContato(requisicaoLiberacaoDto.getTelefoneContato());
                contatoRequisicaoLiberacaoDto.setRamal(requisicaoLiberacaoDto.getRamal());
                if (requisicaoLiberacaoDto.getEmailContato() != null) {
                    contatoRequisicaoLiberacaoDto.setEmailContato(requisicaoLiberacaoDto.getEmailContato().trim());
                }
                contatoRequisicaoLiberacaoDto.setObservacao(requisicaoLiberacaoDto.getObservacao());
                contatoRequisicaoLiberacaoDto.setIdLocalidade(requisicaoLiberacaoDto.getIdLocalidade());
                contatoRequisicaoLiberacaoDto.setIdUnidade(requisicaoLiberacaoDto.getIdUnidade());
                contatoRequisicaoLiberacaoDao.update(contatoRequisicaoLiberacaoDto);

            } else {
                contatoRequisicaoLiberacaoDto.setNomeContato(requisicaoLiberacaoDto.getNomeContato2());
                contatoRequisicaoLiberacaoDto.setTelefoneContato(requisicaoLiberacaoDto.getTelefoneContato());
                contatoRequisicaoLiberacaoDto.setRamal(requisicaoLiberacaoDto.getRamal());
                if (requisicaoLiberacaoDto.getEmailContato() != null) {
                    contatoRequisicaoLiberacaoDto.setEmailContato(requisicaoLiberacaoDto.getEmailContato().trim());
                }
                contatoRequisicaoLiberacaoDto.setObservacao(requisicaoLiberacaoDto.getObservacao());
                contatoRequisicaoLiberacaoDto.setIdLocalidade(requisicaoLiberacaoDto.getIdLocalidade());
                contatoRequisicaoLiberacaoDto.setIdUnidade(requisicaoLiberacaoDto.getIdUnidade());
                contatoRequisicaoLiberacaoDto = (ContatoRequisicaoLiberacaoDTO) contatoRequisicaoLiberacaoDao.create(contatoRequisicaoLiberacaoDto);
            }

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        }
        return contatoRequisicaoLiberacaoDto;
    }

    /*
     * (non-Javadoc)
     * @see
     * br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoService#updateLiberacaoAprovada(br.com.citframework.dto
     * .IDto) Parametro Requisicao Liberacao;
     */
    @Override
    public void updateLiberacaoAprovada(final IDto obj) throws Exception {
        final RequisicaoLiberacaoDTO liberacaoDto = (RequisicaoLiberacaoDTO) obj;
        final RequisicaoLiberacaoDao requiDao = this.getDao();
        final OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        final OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDTO = new OcorrenciaLiberacaoDTO();
        requiDao.setTransactionControler(tc);
        ocorrenciaLiberacaoDao.setTransactionControler(tc);

        try {
            tc.start();

            requiDao.updateNotNull(liberacaoDto);

            // Criando ocorrecia para liberação de uma release
            ocorrenciaLiberacaoDTO.setIdRequisicaoLiberacao(liberacaoDto.getIdRequisicaoLiberacao());
            ocorrenciaLiberacaoDTO.setDataregistro(UtilDatas.getDataAtual());
            ocorrenciaLiberacaoDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
            ocorrenciaLiberacaoDTO.setTempoGasto(0);
            ocorrenciaLiberacaoDTO.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
            ocorrenciaLiberacaoDTO.setDataInicio(UtilDatas.getDataAtual());
            ocorrenciaLiberacaoDTO.setDataFim(UtilDatas.getDataAtual());
            ocorrenciaLiberacaoDTO.setInformacoesContato("não se aplica");
            ocorrenciaLiberacaoDTO.setRegistradopor(liberacaoDto.getUsuarioDto().getLogin());
            ocorrenciaLiberacaoDTO.setDadosLiberacao(new Gson().toJson(liberacaoDto));
            ocorrenciaLiberacaoDTO.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
            ocorrenciaLiberacaoDTO.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Aprovar.getSigla());
            ocorrenciaLiberacaoDTO.setOcorrencia(liberacaoDto.getRegistroexecucao());
            ocorrenciaLiberacaoDao.create(ocorrenciaLiberacaoDTO);

            tc.commit();

        } catch (final Exception e) {
            tc.rollback();
        } finally {
            tc.closeQuietly();
        }
    }

    @Override
    public Collection<RequisicaoLiberacaoDTO> listaRequisicaoLiberacaoPorCriterios(final PesquisaRequisicaoLiberacaoDTO pesquisaRequisicaoLiberacaoDto) throws Exception {
        Collection<RequisicaoLiberacaoDTO> listaRequisicaoLiberacaoPorCriterios = null;
        try {
            listaRequisicaoLiberacaoPorCriterios = this.getDao().listaRequisicaoLiberacaoPorCriterios(pesquisaRequisicaoLiberacaoDto);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return listaRequisicaoLiberacaoPorCriterios;
    }

    @Override
    public FluxoDTO recuperaFluxo(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception {
        if (requisicaoLiberacaoDto == null || requisicaoLiberacaoDto.getIdRequisicaoLiberacao() == null) {
            throw new Exception(this.i18nMessage("requisicaoliberacao.validacao.requisicaoliberacao"));
        }
        FluxoDTO fluxoDto = null;
        final Collection<ExecucaoLiberacaoDTO> colExecucao = new ExecucaoLiberacaoDao().listByIdRequisicaoLiberacao(requisicaoLiberacaoDto.getIdRequisicaoLiberacao());
        if (colExecucao != null && !colExecucao.isEmpty()) {
            fluxoDto = new FluxoDTO();
            final ExecucaoLiberacaoDTO execucaoDto = (ExecucaoLiberacaoDTO) ((List) colExecucao).get(0);
            fluxoDto.setIdFluxo(execucaoDto.getIdFluxo());
            fluxoDto = (FluxoDTO) new FluxoDao().restore(fluxoDto);
        }

        return fluxoDto;
    }

    @Override
    public void reabre(final UsuarioDTO usuarioDto, final RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception {
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        this.reabre(usuarioDto, requisicaoLiberacaoDto, tc);
        tc.close();
    }

    public void reabre(final UsuarioDTO usuarioDto, final RequisicaoLiberacaoDTO requisicaoLiberacaoDto, final TransactionControler tc) throws Exception {
        final RequisicaoLiberacaoDTO liberacaoAuxDto = this.restoreAll(requisicaoLiberacaoDto.getIdRequisicaoLiberacao(), tc);
        new ExecucaoLiberacaoServiceEjb().reabre(usuarioDto, liberacaoAuxDto, tc);
    }

    @Override
    public String getUrlInformacoesComplementares(final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception {
        String url = "";

        final TemplateSolicitacaoServicoDTO templateDto = new TemplateSolicitacaoServicoServiceEjb().recuperaTemplateRequisicaoLiberacao(requisicaoLiberacaoDTO);
        if (templateDto != null) {
            url += templateDto.getUrlRecuperacao();
            url += "?";

            url = url.replaceAll("\n", "");
            url = url.replaceAll("\r", "");

            String editar = "S";
            if (requisicaoLiberacaoDTO.getIdRequisicaoLiberacao() != null && requisicaoLiberacaoDTO.getIdRequisicaoLiberacao().intValue() > 0) {

                url += "idRequisicao=" + requisicaoLiberacaoDTO.getIdRequisicaoLiberacao() + "&";
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
    public void atualizaInformacoesQuestionario(final RequisicaoQuestionarioDTO requisicaoQuestionarioDTO) throws Exception {
        final ControleQuestionariosDao controleQuestionariosDao = new ControleQuestionariosDao();
        final RequisicaoQuestionarioDao requisicaoQuestionarioDao = new RequisicaoQuestionarioDao();
        final RespostaItemQuestionarioDao respostaItemDao = new RespostaItemQuestionarioDao();
        final RespostaItemQuestionarioServiceBean respostaItemQuestionarioServiceBean = new RespostaItemQuestionarioServiceBean();

        final TransactionControler tc = new TransactionControlerImpl(requisicaoQuestionarioDao.getAliasDB());

        controleQuestionariosDao.setTransactionControler(tc);
        requisicaoQuestionarioDao.setTransactionControler(tc);
        respostaItemDao.setTransactionControler(tc);

        try {
            if (requisicaoQuestionarioDTO.getIdRequisicaoQuestionario() != null && requisicaoQuestionarioDTO.getIdRequisicaoQuestionario().intValue() > 0) {
                requisicaoQuestionarioDTO.setDataHoraGrav(UtilDatas.getDataHoraAtual());
                requisicaoQuestionarioDao.updateNotNull(requisicaoQuestionarioDTO);
                respostaItemDao.deleteByIdIdentificadorResposta(requisicaoQuestionarioDTO.getIdRequisicaoQuestionario());
                respostaItemQuestionarioServiceBean.processCollection(tc, requisicaoQuestionarioDTO.getColValores(), requisicaoQuestionarioDTO.getColAnexos(),
                        requisicaoQuestionarioDTO.getIdRequisicaoQuestionario(), null);
            } else {
                ControleQuestionariosDTO controleQuestionariosDto = new ControleQuestionariosDTO();
                controleQuestionariosDto = (ControleQuestionariosDTO) controleQuestionariosDao.create(controleQuestionariosDto);

                if (requisicaoQuestionarioDTO.getDataQuestionario() == null) {
                    requisicaoQuestionarioDTO.setDataQuestionario(UtilDatas.getDataAtual());
                }
                requisicaoQuestionarioDTO.setSituacao("E");
                requisicaoQuestionarioDTO.setIdRequisicaoQuestionario(controleQuestionariosDto.getIdControleQuestionario());
                requisicaoQuestionarioDTO.setDataHoraGrav(UtilDatas.getDataHoraAtual());
                requisicaoQuestionarioDTO.setIdTipoRequisicao(Enumerados.TipoRequisicao.LIBERCAO.getId());

                final RequisicaoQuestionarioDTO reqQuestionariosDTO = (RequisicaoQuestionarioDTO) requisicaoQuestionarioDao.create(requisicaoQuestionarioDTO);

                final Integer idIdentificadorResposta = reqQuestionariosDTO.getIdRequisicaoQuestionario();
                respostaItemQuestionarioServiceBean.processCollection(tc, reqQuestionariosDTO.getColValores(), reqQuestionariosDTO.getColAnexos(), idIdentificadorResposta, null);
            }
        } finally {
            tc.close();
        }
    }

    public ComplemInfSolicitacaoServicoService getInformacoesComplementaresService(final String nomeClasse) throws Exception {
        final ComplemInfSolicitacaoServicoService informacoesComplementaresService = (ComplemInfSolicitacaoServicoService) Class.forName(nomeClasse).newInstance();
        return informacoesComplementaresService;
    }

    @Override
    public List<RequisicaoLiberacaoDTO> findByConhecimento(final BaseConhecimentoDTO baseConhecimentoDto) throws Exception {
        try {
            return this.getDao().findByConhecimento(baseConhecimentoDto);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RequisicaoLiberacaoDTO> listLiberacaoByItemConfiugracao(final Integer idItemConfiguracao) throws Exception {
        return this.getDao().listLiberacaoByIdItemConfiguracao(idItemConfiguracao);
    }

    private boolean verificaConfirmacaoQuestionario(final RequisicaoLiberacaoDTO liberacao) throws ServiceException, Exception {
        final RequisicaoQuestionarioService questService = (RequisicaoQuestionarioService) ServiceLocator.getInstance().getService(RequisicaoQuestionarioService.class, null);

        final Integer tipo = TipoRequisicao.LIBERCAO.getId();

        final Collection lista = questService.listNaoConfirmados(liberacao.getIdRequisicaoLiberacao(), tipo);
        if (lista != null && lista.size() > 0) {
            return false;

        } else {
            return true;
        }
    }

    @Override
    public String getUrlInformacoesQuestionario(final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception {
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
            if (requisicaoLiberacaoDTO.getIdRequisicaoLiberacao() != null && requisicaoLiberacaoDTO.getIdRequisicaoLiberacao().intValue() > 0) {

                url += "idRequisicao=" + requisicaoLiberacaoDTO.getIdRequisicaoLiberacao() + "&";
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

    public void deleteAdicionaTabelaMidias(final RequisicaoLiberacaoDTO liberacaoDTO, final TransactionControler tc) throws Exception {
        Collection<RequisicaoLiberacaoMidiaDTO> colMidiasBanco = new ArrayList<RequisicaoLiberacaoMidiaDTO>();
        final RequisicaoLiberacaoMidiaDao midiaDAO = new RequisicaoLiberacaoMidiaDao();
        colMidiasBanco = midiaDAO.findByIdLiberacaoEDataFim(liberacaoDTO.getIdRequisicaoLiberacao());
        midiaDAO.setTransactionControler(tc);
        boolean grava = false;
        boolean exclui = false;
        int idMidia1 = 0;
        int idMidia2 = 0;
        if (colMidiasBanco == null || colMidiasBanco.size() == 0) {
            for (final RequisicaoLiberacaoMidiaDTO requisicaoLiberacaoMidiaDTO : liberacaoDTO.getColMidia()) {
                requisicaoLiberacaoMidiaDTO.setIdRequisicaoLiberacao(liberacaoDTO.getIdRequisicaoLiberacao());
                midiaDAO.create(requisicaoLiberacaoMidiaDTO);
            }
        } else {
            if (liberacaoDTO.getColMidia() != null && liberacaoDTO.getColMidia().size() > 0) {
                // compara o que vem da tela com o que está no banco se o que estiver na tela for diferente do banco
                // então ele grava poruqe o item não existe no banco.
                for (final RequisicaoLiberacaoMidiaDTO requisicaoLiberacaoMidiaDTO : liberacaoDTO.getColMidia()) {
                    for (final RequisicaoLiberacaoMidiaDTO requisicaoLiberacaoMidiaDTO2 : colMidiasBanco) {
                        idMidia1 = requisicaoLiberacaoMidiaDTO.getIdMidiaSoftware();
                        idMidia2 = requisicaoLiberacaoMidiaDTO2.getIdMidiaSoftware();
                        if (idMidia1 == idMidia2) {
                            grava = false;
                            break;
                        } else {
                            grava = true;
                        }
                    }
                    if (grava) {
                        requisicaoLiberacaoMidiaDTO.setIdRequisicaoLiberacao(liberacaoDTO.getIdRequisicaoLiberacao());
                        midiaDAO.create(requisicaoLiberacaoMidiaDTO);
                    }
                }
                // Compara o que vem do banco com o que está na tela se o que estiver no banco for diferente do que tem
                // na tela
                // então ele seta a data fim para desabilitar no banco.
                if (colMidiasBanco != null && colMidiasBanco.size() > 0) {
                    for (final RequisicaoLiberacaoMidiaDTO requisicaoLiberacaoMidiaDTO : colMidiasBanco) {
                        for (final RequisicaoLiberacaoMidiaDTO requisicaoLiberacaoMidiaDTO2 : liberacaoDTO.getColMidia()) {
                            idMidia1 = requisicaoLiberacaoMidiaDTO.getIdMidiaSoftware();
                            idMidia2 = requisicaoLiberacaoMidiaDTO2.getIdMidiaSoftware();
                            if (idMidia1 == idMidia2) {
                                exclui = false;
                                break;
                            } else {
                                exclui = true;
                                requisicaoLiberacaoMidiaDTO.setDataFim(UtilDatas.getDataAtual());
                            }
                        }
                        if (exclui) {
                            midiaDAO.update(requisicaoLiberacaoMidiaDTO);
                        }
                    }
                }
            }
        }

    }

    public void deleteAdicionaTabelaResponsavel(final RequisicaoLiberacaoDTO liberacaoDTO, final TransactionControler tc) throws Exception {
        Collection<RequisicaoLiberacaoResponsavelDTO> colResponsavelBanco = new ArrayList<RequisicaoLiberacaoResponsavelDTO>();
        final RequisicaoLiberacaoResponsavelDao responsavelDao = new RequisicaoLiberacaoResponsavelDao();
        colResponsavelBanco = responsavelDao.findByIdLiberacaoEDataFim(liberacaoDTO.getIdRequisicaoLiberacao());
        responsavelDao.setTransactionControler(tc);
        boolean grava = false;
        boolean exclui = false;
        int idResp1 = 0;
        int idResp2 = 0;
        if (colResponsavelBanco == null || colResponsavelBanco.size() == 0) {
            for (final RequisicaoLiberacaoResponsavelDTO requisicaoLiberacaoRespDTO : liberacaoDTO.getColResponsaveis()) {
                requisicaoLiberacaoRespDTO.setIdRequisicaoLiberacao(liberacaoDTO.getIdRequisicaoLiberacao());
                responsavelDao.create(requisicaoLiberacaoRespDTO);
            }
        } else {
            if (liberacaoDTO.getColResponsaveis() != null && liberacaoDTO.getColResponsaveis().size() > 0) {
                // compara o que vem da tela com o que está no banco se o que estiver na tela for diferente do banco
                // então ele grava poruqe o item não existe no banco.
                for (final RequisicaoLiberacaoResponsavelDTO requisicaoLiberacaoRespDTO : liberacaoDTO.getColResponsaveis()) {
                    for (final RequisicaoLiberacaoResponsavelDTO requisicaoLiberacaoRespDTO2 : colResponsavelBanco) {
                        idResp1 = requisicaoLiberacaoRespDTO.getIdResponsavel();
                        idResp2 = requisicaoLiberacaoRespDTO2.getIdResponsavel();
                        if (idResp1 == idResp2) {
                            grava = false;
                            break;
                        } else {
                            grava = true;
                        }
                    }
                    if (grava) {
                        requisicaoLiberacaoRespDTO.setIdRequisicaoLiberacao(liberacaoDTO.getIdRequisicaoLiberacao());
                        responsavelDao.create(requisicaoLiberacaoRespDTO);
                    }
                }
                // Compara o que vem do banco com o que está na tela se o que estiver no banco for diferente do que tem
                // na tela
                // então ele seta a data fim para desabilitar no banco.
                if (colResponsavelBanco != null && colResponsavelBanco.size() > 0) {
                    for (final RequisicaoLiberacaoResponsavelDTO requisicaoLiberacaoRespDTO : colResponsavelBanco) {
                        for (final RequisicaoLiberacaoResponsavelDTO requisicaoLiberacaoMidiaDTO2 : liberacaoDTO.getColResponsaveis()) {
                            idResp1 = requisicaoLiberacaoRespDTO.getIdResponsavel();
                            idResp2 = requisicaoLiberacaoMidiaDTO2.getIdResponsavel();
                            if (idResp1 == idResp2) {
                                exclui = false;
                                break;
                            } else {
                                exclui = true;
                                requisicaoLiberacaoRespDTO.setDataFim(UtilDatas.getDataAtual());
                            }
                        }
                        if (exclui) {
                            responsavelDao.update(requisicaoLiberacaoRespDTO);
                        }
                    }
                }
            }
        }

    }

    public void deleteAdicionaTabelaCompras(final RequisicaoLiberacaoDTO liberacaoDTO, final TransactionControler tc) throws Exception {
        Collection<RequisicaoLiberacaoRequisicaoComprasDTO> colComprasBanco = new ArrayList<RequisicaoLiberacaoRequisicaoComprasDTO>();
        final RequisicaoLiberacaoRequisicaoComprasDAO comprasDao = new RequisicaoLiberacaoRequisicaoComprasDAO();
        colComprasBanco = comprasDao.findByIdLiberacaoAndDataFim(liberacaoDTO.getIdRequisicaoLiberacao());
        comprasDao.setTransactionControler(tc);
        boolean grava = false;
        boolean exclui = false;
        int idCompras1 = 0;
        int idCompras2 = 0;
        if (colComprasBanco == null || colComprasBanco.size() == 0) {
            for (final RequisicaoLiberacaoRequisicaoComprasDTO requisicaoLiberacaoComprasDTO : liberacaoDTO.getColRequisicaoCompras()) {
                requisicaoLiberacaoComprasDTO.setIdRequisicaoLiberacao(liberacaoDTO.getIdRequisicaoLiberacao());
                comprasDao.create(requisicaoLiberacaoComprasDTO);
            }
        } else {
            if (liberacaoDTO.getColRequisicaoCompras() != null && liberacaoDTO.getColRequisicaoCompras().size() > 0) {
                // compara o que vem da tela com o que está no banco se o que estiver na tela for diferente do banco
                // então ele grava poruqe o item não existe no banco.
                for (final RequisicaoLiberacaoRequisicaoComprasDTO requisicaoLiberacaoComprasDTO : liberacaoDTO.getColRequisicaoCompras()) {
                    for (final RequisicaoLiberacaoRequisicaoComprasDTO requisicaoLiberacaoComprasDTO2 : colComprasBanco) {
                        idCompras1 = requisicaoLiberacaoComprasDTO.getIdSolicitacaoServico();
                        idCompras2 = requisicaoLiberacaoComprasDTO2.getIdSolicitacaoServico();
                        if (idCompras1 == idCompras2) {
                            grava = false;
                            break;
                        } else {
                            grava = true;
                        }
                    }
                    if (grava) {
                        requisicaoLiberacaoComprasDTO.setIdRequisicaoLiberacao(liberacaoDTO.getIdRequisicaoLiberacao());
                        comprasDao.create(requisicaoLiberacaoComprasDTO);
                    }
                }
                // Compara o que vem do banco com o que está na tela se o que estiver no banco for diferente do que tem
                // na tela
                // então ele seta a data fim para desabilitar no banco.
                if (colComprasBanco != null && colComprasBanco.size() > 0) {
                    for (final RequisicaoLiberacaoRequisicaoComprasDTO requisicaoLiberacaoComprasDTO : colComprasBanco) {
                        for (final RequisicaoLiberacaoRequisicaoComprasDTO requisicaoLiberacaoComprasDTO2 : liberacaoDTO.getColRequisicaoCompras()) {
                            idCompras1 = requisicaoLiberacaoComprasDTO.getIdSolicitacaoServico();
                            idCompras2 = requisicaoLiberacaoComprasDTO2.getIdSolicitacaoServico();
                            if (idCompras1 == idCompras2) {
                                exclui = false;
                                break;
                            } else {
                                exclui = true;
                                requisicaoLiberacaoComprasDTO.setDataFim(UtilDatas.getDataAtual());
                            }
                        }
                        if (exclui) {
                            comprasDao.update(requisicaoLiberacaoComprasDTO);
                        }
                    }
                }
            }
        }

    }

    @Override
    public void gravaInformacoesGED(final RequisicaoLiberacaoDTO requisicaoLiberacaoDTO, final TransactionControler tc) throws Exception {

    }

    @Override
    public Boolean verificaPermissaoGrupoCancelar(final Integer idTipoLiberacao, final Integer idGrupo) throws ServiceException, Exception {
        boolean isOk = false;
        final TipoLiberacaoService tipoLiberacaoService = (TipoLiberacaoService) ServiceLocator.getInstance().getService(TipoLiberacaoService.class, null);
        TipoLiberacaoDTO tipoLiberacaoDTO = new TipoLiberacaoDTO();
        final PermissoesFluxoDao permissoesDao = new PermissoesFluxoDao();

        tipoLiberacaoDTO.setIdTipoLiberacao(idTipoLiberacao);
        tipoLiberacaoDTO = (TipoLiberacaoDTO) tipoLiberacaoService.restore(tipoLiberacaoDTO);
        if (tipoLiberacaoDTO != null) {
            final PermissoesFluxoDTO permissoesDto = permissoesDao.permissaoGrupoCancelar(idGrupo, tipoLiberacaoDTO.getIdTipoFluxo());
            if (permissoesDto != null && permissoesDto.getCancelar() != null && permissoesDto.getCancelar().equalsIgnoreCase("S")) {
                isOk = true;
            }
        }

        return isOk;
    }

    @Override
    public Timestamp MontardataHoraAgendamentoInicial(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto) {
        final Timestamp dataHoraInicio = requisicaoLiberacaoDto.getDataHoraInicioAgendada();
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String horaAgendamentoInicialSTR = format.format(dataHoraInicio);
        horaAgendamentoInicialSTR = horaAgendamentoInicialSTR.substring(0, 11);
        final String horaAgendamentoInicial = requisicaoLiberacaoDto.getHoraAgendamentoInicial();
        final String dia = horaAgendamentoInicialSTR.substring(0, 2);
        final String mes = horaAgendamentoInicialSTR.substring(3, 5);
        final String ano = horaAgendamentoInicialSTR.substring(6, 10);
        final String dataHoraMontada = ano + "-" + mes + "-" + dia + " " + horaAgendamentoInicial + ":00.0";
        final Timestamp dataHoraInicial = Timestamp.valueOf(dataHoraMontada);

        return dataHoraInicial;
    }

    @Override
    public Timestamp MontardataHoraAgendamentoFinal(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto) {
        final Timestamp dataHoraFim = requisicaoLiberacaoDto.getDataHoraTerminoAgendada();
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        final String horaAgendamentoFinalSTR = format.format(dataHoraFim);
        final String horaAgendamentoFinal = requisicaoLiberacaoDto.getHoraAgendamentoFinal();
        final String dia = horaAgendamentoFinalSTR.substring(0, 2);
        final String mes = horaAgendamentoFinalSTR.substring(3, 5);
        final String ano = horaAgendamentoFinalSTR.substring(6, 10);
        final String dataHoraMontada = ano + "-" + mes + "-" + dia + " " + horaAgendamentoFinal + ":00.0";
        final Timestamp dataHoraFinal = Timestamp.valueOf(dataHoraMontada);

        return dataHoraFinal;
    }

    @Override
    public void calculaTempoAtraso(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception {
        requisicaoLiberacaoDto.setPrazoHH(0);
        requisicaoLiberacaoDto.setPrazoMM(0);
        if (requisicaoLiberacaoDto.getDataHoraInicioAgendada() != null && requisicaoLiberacaoDto.getDataHoraTerminoAgendada() != null) {
            final Timestamp dataHoraInicioComparacao = requisicaoLiberacaoDto.getDataHoraInicioAgendada();
            final Timestamp dataHoraFinalComparacao = requisicaoLiberacaoDto.getDataHoraTerminoAgendada();
            if (dataHoraFinalComparacao.compareTo(dataHoraInicioComparacao) > 0) {
                final long atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraFinalComparacao, dataHoraInicioComparacao) / 1000;

                final String hora = Util.getHoraStr(new Double(atrasoSLA) / 3600);
                final int tam = hora.length();
                requisicaoLiberacaoDto.setPrazoHH(new Integer(hora.substring(0, tam - 2)));
                requisicaoLiberacaoDto.setPrazoMM(new Integer(hora.substring(tam - 2, tam)));
            }
        }
    }

    @Override
    public Boolean seHoraInicialMenorQAtual(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto) {
        boolean resultado = false;
        final Time horaAtual = UtilDatas.getHoraAtual();
        final Date dataAtual = UtilDatas.getDataAtual();
        String horaAtualStr = horaAtual.toString();
        final String dataAtualStr = dataAtual.toString();
        horaAtualStr = horaAtualStr.substring(0, 5);
        final Timestamp dataHoraInicio = requisicaoLiberacaoDto.getDataHoraInicioAgendada();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String dataAgendamentoFinalSTR = format.format(dataHoraInicio);
        if (dataAtualStr.equals(dataAgendamentoFinalSTR)) {
            String horaAgendamentoInicial = requisicaoLiberacaoDto.getHoraAgendamentoInicial();
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

    @Override
    public Boolean seHoraFinalMenorQAtual(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto) {
        boolean resultado = false;
        final Time horaAtual = UtilDatas.getHoraAtual();
        final Date dataAtual = UtilDatas.getDataAtual();
        String horaAtualStr = horaAtual.toString();
        final String dataAtualStr = dataAtual.toString();
        horaAtualStr = horaAtualStr.substring(0, 5);
        final Timestamp dataHoraFim = requisicaoLiberacaoDto.getDataHoraTerminoAgendada();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String dataAgendamentoFinalSTR = format.format(dataHoraFim);
        if (dataAtualStr.equals(dataAgendamentoFinalSTR)) {
            String horaAgendamentoFim = requisicaoLiberacaoDto.getHoraAgendamentoFinal();
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

    @Override
    public Boolean seHoraFinalMenorQHoraInicial(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto) {
        boolean resultado = false;
        final Time horaAtual = UtilDatas.getHoraAtual();
        final Date dataAtual = UtilDatas.getDataAtual();
        String horaAtualStr = horaAtual.toString();
        final String dataAtualStr = dataAtual.toString();
        horaAtualStr = horaAtualStr.substring(0, 5);
        final Timestamp dataHoraInicio = requisicaoLiberacaoDto.getDataHoraInicioAgendada();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String dataAgendamentoInicialSTR = format.format(dataHoraInicio);
        final Timestamp dataHoraFim = requisicaoLiberacaoDto.getDataHoraTerminoAgendada();
        final String dataAgendamentoFinalSTR = format.format(dataHoraFim);
        String horaAgendamentoFim = requisicaoLiberacaoDto.getHoraAgendamentoFinal();
        String horaAgendamentoInicial = requisicaoLiberacaoDto.getHoraAgendamentoInicial();
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

    public boolean validacaoGrupoExecutor(final RequisicaoLiberacaoDTO requisicaoMudancaDto) throws Exception {
        boolean resultado = false;

        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdGrupoAtual() != null && requisicaoMudancaDto.getIdTipoLiberacao() != null) {
            final Integer idGrupoExecutor = requisicaoMudancaDto.getIdGrupoAtual();
            final Integer idTipoMudanca = requisicaoMudancaDto.getIdTipoLiberacao();

            final PermissoesFluxoService permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);

            resultado = permissoesFluxoService.permissaoGrupoExecutorLiberacao(idTipoMudanca, idGrupoExecutor);
        }
        return resultado;
    }

    public void gravaPlanoDeReversaoGED(final RequisicaoMudancaDTO requisicaomudacaDTO, final TransactionControler tc, final HistoricoMudancaDTO historicoMudancaDTO)
            throws Exception {
        final Collection<UploadDTO> colArquivosUpload = requisicaomudacaDTO.getColUploadPlanoDeReversaoGED();
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
        /**
         * Grava informações do upload principal.
         * **/

        if (colArquivosUpload != null) {
            for (final UploadDTO upDto : colArquivosUpload) {
                final UploadDTO uploadDTO = upDto;
                ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

                Integer idControleGed = uploadDTO.getIdControleGED();

                historicoGEDDTO.setIdRequisicaoMudanca(requisicaomudacaDTO.getIdRequisicaoMudanca());
                historicoGEDDTO.setIdTabela(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA);
                if (historicoMudancaDTO.getIdHistoricoMudanca() != null) {
                    historicoGEDDTO.setIdHistoricoMudanca(null);
                } else {
                    historicoGEDDTO.setIdHistoricoMudanca(-1);
                }
                historicoGEDDao.create(historicoGEDDTO);

                controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA);
                controleGEDDTO.setId(requisicaomudacaDTO.getIdRequisicaoMudanca());
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

                if (PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados.trim())) { // Se
                    // utiliza
                    // GED
                    // interno e eh BD
                    controleGEDDTO.setPathArquivo(uploadDTO.getPath()); // Isso
                    // vai
                    // fazer a
                    // gravacao
                    // no BD.
                    // dento do
                    // create
                    // abaixo.
                } else {
                    controleGEDDTO.setPathArquivo(null);
                }
                // esse bloco grava a tabela de historicos de anexos:

                boolean existe = false;
                if (idControleGed != null) {
                    final Collection<HistoricoGEDDTO> colAux = historicoGEDDao.listByIdTabelaAndIdLiberacaoEDataFim(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA,
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
                    controleGEDDTO.setId(requisicaomudacaDTO.getIdRequisicaoMudanca());
                    idControleGed = controleGEDDTO.getIdControleGED();
                }
                /*
                 * historicoGEDDTO.setIdControleGed(idControleGed);
                 * historicoGEDDTO.setDataFim(UtilDatas.getDataAtual()); historicoGEDDao.update(historicoGEDDTO);
                 */

                // uploadDTO.setIdControleGED(controleGEDDTO.getIdControleGED());
                if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se
                    // utiliza
                    // GED
                    // interno e nao eh BD
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
                } /*
                   * else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { // Se // utiliza // GED // externo //
                   * FALTA IMPLEMENTAR!!! }
                   */
            }
        }

        /*
         * Collection<ControleGEDDTO> colAnexo = controleGEDDao.listByIdTabelaAndIdBaseConhecimento
         * (ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA, requisicaomudacaDTO.getIdRequisicaoMudanca()); if (colAnexo !=
         * null ) { for (ControleGEDDTO dtoGed : colAnexo) { boolean b = false; for (Iterator it =
         * colArquivosUpload.iterator(); it.hasNext();) { UploadDTO uploadDTO = (UploadDTO) it.next();
         * if(uploadDTO.getIdControleGED() == null) { b = true; break; } if(uploadDTO.getIdControleGED().intValue() ==
         * dtoGed.getIdControleGED().intValue()) { b = true; } if (b) { break; } } if (!b) {
         * controleGEDDao.delete(dtoGed); } } }
         */

    }

    public void criarOcorrenciaLiberacao(final RequisicaoLiberacaoDTO requisicaoLiberacaoDto, final TransactionControler tc) throws Exception {
        final OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
        ocorrenciaLiberacaoDao.setTransactionControler(tc);
        final OcorrenciaLiberacaoDTO ocorrenciaLiberacaoDto = new OcorrenciaLiberacaoDTO();
        ocorrenciaLiberacaoDto.setIdRequisicaoLiberacao(requisicaoLiberacaoDto.getIdRequisicaoLiberacao());
        ocorrenciaLiberacaoDto.setDataregistro(UtilDatas.getDataAtual());
        ocorrenciaLiberacaoDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
        ocorrenciaLiberacaoDto.setTempoGasto(0);
        ocorrenciaLiberacaoDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao.getDescricao());
        ocorrenciaLiberacaoDto.setDataInicio(UtilDatas.getDataAtual());
        ocorrenciaLiberacaoDto.setDataFim(UtilDatas.getDataAtual());
        ocorrenciaLiberacaoDto.setInformacoesContato("não se aplica");
        ocorrenciaLiberacaoDto.setRegistradopor(requisicaoLiberacaoDto.getUsuarioDto().getLogin());
        ocorrenciaLiberacaoDto.setDadosLiberacao(new Gson().toJson(requisicaoLiberacaoDto));
        ocorrenciaLiberacaoDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
        ocorrenciaLiberacaoDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Criacao.getSigla());
        ocorrenciaLiberacaoDao.create(ocorrenciaLiberacaoDto);

    }

}
