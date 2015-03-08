package br.com.centralit.citcorpore.batch;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Semaphore;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.EscalonamentoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaMudancaDTO;
import br.com.centralit.citcorpore.bean.RegraEscalonamentoDTO;
import br.com.centralit.citcorpore.bean.RelEscalonamentoMudancaDto;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.EscalonamentoDAO;
import br.com.centralit.citcorpore.integracao.OcorrenciaMudancaDao;
import br.com.centralit.citcorpore.integracao.RegraEscalonamentoDAO;
import br.com.centralit.citcorpore.integracao.RelEscalonamentoMudancaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.OcorrenciaMudancaServiceEjb;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaServiceEjb;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

public class MonitoraMudanca extends Thread {

    private final Semaphore performanceDataSemaphore = new Semaphore(1);

    private final RequisicaoMudancaServiceEjb mudancaServiceEjb = new RequisicaoMudancaServiceEjb();

    private final EscalonamentoDAO escalonamentoDao = new EscalonamentoDAO();
    private final OcorrenciaMudancaDao ocorrenciaDao = new OcorrenciaMudancaDao();
    private final RegraEscalonamentoDAO regraEscalonamentoDao = new RegraEscalonamentoDAO();
    private final RelEscalonamentoMudancaDao relEscalonamentoMudancaDao = new RelEscalonamentoMudancaDao();
    private final RequisicaoMudancaDao mudancaDao = new RequisicaoMudancaDao();

    private AcordoNivelServicoService acordoNivelServicoService;

    private AcordoNivelServicoService getAcordoNivelServicoService() throws Exception {
        if (acordoNivelServicoService == null) {
            acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
        }
        return acordoNivelServicoService;
    }

    private RequisicaoMudancaServiceEjb requisicaoMudancaService;

    private RequisicaoMudancaServiceEjb getRequisicaoMudancaService() {
        if (requisicaoMudancaService == null) {
            requisicaoMudancaService = new RequisicaoMudancaServiceEjb();
        }
        return requisicaoMudancaService;
    }

    @Override
    public void run() {
        while (true) {
            performanceDataSemaphore.acquireUninterruptibly();

            final Timestamp dataHoraAtual = UtilDatas.getDataHoraAtual();
            String ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA_STR = "";
            Integer ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA = 0;
            String ligaFuncionamentoRegrasEscalonamento = "N";
            Integer ID_MODELO_EMAIL_PRAZO_VENCENDO = null;
            final String ID_MODELO_EMAIL_PRAZO_VENCENDO_STR = "";

            Collection<RequisicaoMudancaDTO> col = null;
            try {
                ligaFuncionamentoRegrasEscalonamento = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.HABILITA_ESCALONAMENTO_MUDANÇA, "N");
                ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA_STR = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA, "0");
                if (!ID_MODELO_EMAIL_PRAZO_VENCENDO_STR.trim().equals("")) {
                    ID_MODELO_EMAIL_PRAZO_VENCENDO = new Integer(ID_MODELO_EMAIL_PRAZO_VENCENDO_STR);
                }
                ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA = new Integer(ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA_STR.trim());
            } catch (final Exception e) {
                e.printStackTrace();
            }

            try {
                try {
                    /* Lista todas as solicitaçãoes relacionadas a regra de escalonamento definido e demais. */
                    col = mudancaDao.listSolicitacoesByRegra();
                } catch (final Exception e) {
                    e.printStackTrace();
                }

                if (col != null) {
                    for (final RequisicaoMudancaDTO requisicaoMudancaDto : col) {

                        OcorrenciaMudancaDTO ocorrSolDto = null;
                        try {
                            ocorrSolDto = ocorrenciaDao.findUltimoByIdSolicitacaoServico(requisicaoMudancaDto.getIdRequisicaoMudanca());
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }

                        if (ligaFuncionamentoRegrasEscalonamento != null && ligaFuncionamentoRegrasEscalonamento.equalsIgnoreCase("S")) {
                            Collection<RegraEscalonamentoDTO> colecaoRegrasEscalonamento = null;
                            Collection<EscalonamentoDTO> colecaoEscalonamento = null;
                            try {
                                // regra: 1 para solicitação/incidente, 3 para mudança, 2 para problema
                                colecaoRegrasEscalonamento = regraEscalonamentoDao.findRegraByRequisicaoMudanca(requisicaoMudancaDto, 3);
                                if (colecaoRegrasEscalonamento != null) {
                                    for (final RegraEscalonamentoDTO regraEscalonamentoDTO : colecaoRegrasEscalonamento) {

                                        this.trataSituacaoVencimentoSolicitacao(requisicaoMudancaDto, regraEscalonamentoDTO, mudancaServiceEjb, dataHoraAtual, ocorrSolDto,
                                                ID_MODELO_EMAIL_PRAZO_VENCENDO);

                                        colecaoEscalonamento = escalonamentoDao.findByRegraEscalonamento(regraEscalonamentoDTO);
                                        if (colecaoEscalonamento != null) {
                                            for (final EscalonamentoDTO escalonamentoDTO : colecaoEscalonamento) {
                                                // System.out.println("Estabelecendo regra... Solicitação: " + solicitacaoServicoDTO.getIdRequisicaoMudanca());
                                                if (escalonamentoDTO.getPrazoExecucao() == null || escalonamentoDTO.getPrazoExecucao().intValue() == 0) {
                                                    continue;
                                                }

                                                // Verifica se já existe referencia
                                                final boolean temEscalonamento = relEscalonamentoMudancaDao.temRelacionamentoSolicitacaoEscalonamento(
                                                        requisicaoMudancaDto.getIdRequisicaoMudanca(), escalonamentoDTO.getIdEscalonamento());
                                                if (temEscalonamento) {
                                                    // System.out.println("Escalonamento " + escalonamentoDTO.getIdEscalonamento() + " já executado.");
                                                    continue;
                                                }

                                                if (regraEscalonamentoDTO.getTipoDataEscalonamento() != null && regraEscalonamentoDTO.getTipoDataEscalonamento().intValue() == 1) {
                                                    /**
                                                     * Verifica se o tempo que se passou é maior que o prazo de execução
                                                     */
                                                    if (requisicaoMudancaDto.getDataHoraInicio() != null
                                                            && dataHoraAtual.getTime() - requisicaoMudancaDto.getDataHoraInicio().getTime() > escalonamentoDTO.getPrazoExecucao() * 60 * 1000) {
                                                        if (escalonamentoDTO.getIdGrupoExecutor() != null) {
                                                            /**
                                                             * Atualizando a tabela de relacionamento
                                                             */
                                                            final RelEscalonamentoMudancaDto dto = new RelEscalonamentoMudancaDto();
                                                            dto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                                                            dto.setIdEscalonamento(escalonamentoDTO.getIdEscalonamento());
                                                            relEscalonamentoMudancaDao.create(dto);

                                                            /**
                                                             * Realizando o escalonamento da solicitação com base nas regras estabelecidas
                                                             * Se prioridade for nula estão se escalonamento com a mesma prioridade antiga
                                                             */
                                                            mudancaServiceEjb.updateTimeAction(
                                                                    escalonamentoDTO.getIdGrupoExecutor(),
                                                                    escalonamentoDTO.getIdPrioridade() != null ? escalonamentoDTO.getIdPrioridade() : requisicaoMudancaDto
                                                                            .getPrioridade(), requisicaoMudancaDto.getIdRequisicaoMudanca());
                                                            /**
                                                             * Enviando email de escalação automática
                                                             */
                                                            this.enviaEmail(ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA, requisicaoMudancaDto.getIdRequisicaoMudanca());

                                                            final List<AcordoNivelServicoDTO> listaContratos = this.getAcordoNivelServicoService()
                                                                    .findIdEmailByIdSolicitacaoServico(requisicaoMudancaDto.getIdRequisicaoMudanca());
                                                            if (listaContratos != null && !listaContratos.isEmpty()) {
                                                                this.enviaEmail(listaContratos.get(0).getIdEmail(), requisicaoMudancaDto.getIdRequisicaoMudanca());
                                                            }
                                                        }
                                                    }
                                                } else if (regraEscalonamentoDTO.getTipoDataEscalonamento() != null
                                                        && regraEscalonamentoDTO.getTipoDataEscalonamento().intValue() == 2) {

                                                    Date dateCons = null;
                                                    final OcorrenciaMudancaDTO ocorrEscalacao = ocorrenciaDao.findUltimoByIdSolicitacaoServicoAndOcorrencia(requisicaoMudancaDto
                                                            .getIdRequisicaoMudanca());

                                                    if (ocorrSolDto != null) {
                                                        if (ocorrEscalacao != null) {
                                                            ocorrSolDto = ocorrEscalacao;
                                                        }

                                                        dateCons = ocorrSolDto.getDataregistro();
                                                        final String hora = ocorrSolDto.getHoraregistro();
                                                        try {
                                                            final Timestamp timeAux = UtilDatas.strToTimestamp(UtilDatas.dateToSTR(dateCons) + " " + hora + ":00");
                                                            if (dataHoraAtual.getTime() - timeAux.getTime() > escalonamentoDTO.getPrazoExecucao() * 60 * 1000) {
                                                                if (escalonamentoDTO.getIdGrupoExecutor() != null) {

                                                                    /**
                                                                     * Atualizando a tabela de relacionamento
                                                                     */
                                                                    final RelEscalonamentoMudancaDto dto = new RelEscalonamentoMudancaDto();
                                                                    dto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                                                                    dto.setIdEscalonamento(escalonamentoDTO.getIdEscalonamento());
                                                                    relEscalonamentoMudancaDao.create(dto);

                                                                    /**
                                                                     * Realizando o escalonamento da solicitação com base nas regras estabelecidas
                                                                     * Se prioridade for nula estão se escalonamento com a mesma prioridade antiga
                                                                     */
                                                                    mudancaServiceEjb.updateTimeAction(
                                                                            escalonamentoDTO.getIdGrupoExecutor(),
                                                                            escalonamentoDTO.getIdPrioridade() != null ? escalonamentoDTO.getIdPrioridade() : requisicaoMudancaDto
                                                                                    .getPrioridade(), requisicaoMudancaDto.getIdRequisicaoMudanca());
                                                                    /**
                                                                     * Enviando email de escalação automática
                                                                     */
                                                                    this.enviaEmail(ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA, requisicaoMudancaDto.getIdRequisicaoMudanca());

                                                                    final List<AcordoNivelServicoDTO> listaContratos = this.getAcordoNivelServicoService()
                                                                            .findIdEmailByIdSolicitacaoServico(requisicaoMudancaDto.getIdRequisicaoMudanca());
                                                                    if (listaContratos != null && !listaContratos.isEmpty()) {
                                                                        this.enviaEmail(listaContratos.get(0).getIdEmail(), requisicaoMudancaDto.getIdRequisicaoMudanca());
                                                                    }
                                                                }
                                                            }
                                                            System.out.println("Finalizando regra de escalonamento...");
                                                        } catch (final Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } finally {
                performanceDataSemaphore.release();
            }

            try {
                Thread.sleep(60000);
            } catch (final InterruptedException e) {}
        }
    }

    private void trataSituacaoVencimentoSolicitacao(final RequisicaoMudancaDTO requisicaoMudancaDto, final RegraEscalonamentoDTO regraEscalonamentoDTO,
            final RequisicaoMudancaServiceEjb requisicaoMudancaServiceEjb, final Timestamp dataHoraAtual, final OcorrenciaMudancaDTO ocorrSolDto, final Integer idModeloEmail)
            throws Exception {
        // trata regra de classificação se está vencendo
        if (requisicaoMudancaDto.getDataHoraTermino() != null && regraEscalonamentoDTO.getTempoExecucao() != null
                && (requisicaoMudancaDto.getPrazoHH() != 0 || requisicaoMudancaDto.getPrazoMM() != 0)
                && dataHoraAtual.getTime() - requisicaoMudancaDto.getDataHoraTermino().getTime() >= regraEscalonamentoDTO.getTempoExecucao() * 60 * 1000) {

            final double intervaloTempo = regraEscalonamentoDTO.getIntervaloNotificacao().intValue() * 60 * 1000;

            Date dateCons = null;
            if (ocorrSolDto != null) {
                dateCons = ocorrSolDto.getDataregistro();
                final String hora = ocorrSolDto.getHoraregistro();
                final Timestamp timeAux = UtilDatas.strToTimestamp(UtilDatas.dateToSTR(dateCons) + " " + hora + ":00");
                if (dataHoraAtual.getTime() - timeAux.getTime() > intervaloTempo) {
                    if (regraEscalonamentoDTO.getEnviarEmail() != null && regraEscalonamentoDTO.getEnviarEmail().equals("S")) {
                        final UsuarioDTO usuarioDTO = new UsuarioDTO();
                        usuarioDTO.setLogin("Automático");
                        OcorrenciaMudancaServiceEjb.create(requisicaoMudancaDto, null, "Vencendo", OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Atualizacao, null,
                                CategoriaOcorrencia.Atualizacao.getDescricao(), usuarioDTO, 0, null, null);
                        if (idModeloEmail != null) {
                            this.enviaEmail(idModeloEmail, requisicaoMudancaDto.getIdRequisicaoMudanca());
                        }
                    }
                }
            }
        }

        if (requisicaoMudancaDto.getDataHoraTermino() != null && regraEscalonamentoDTO.getTempoExecucao() != null
                && (requisicaoMudancaDto.getPrazoHH() != 0 || requisicaoMudancaDto.getPrazoMM() != 0)
                && dataHoraAtual.getTime() - requisicaoMudancaDto.getDataHoraTermino().getTime() >= regraEscalonamentoDTO.getTempoExecucao() * 60 * 1000) {
            // trata caso o sla esteja vencendo
            if (requisicaoMudancaDto.getVencendo() == null || requisicaoMudancaDto.getVencendo() != null && !requisicaoMudancaDto.getVencendo().equalsIgnoreCase("S")) {
                RequisicaoMudancaDTO solicitacao = new RequisicaoMudancaDTO();
                solicitacao.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                solicitacao = (RequisicaoMudancaDTO) requisicaoMudancaServiceEjb.restore(solicitacao);
                solicitacao.setVencendo("S");
                requisicaoMudancaServiceEjb.updateNotNull(solicitacao);
                solicitacao = null;
            }
        } else {
            if (requisicaoMudancaDto.getVencendo() == null) {
                RequisicaoMudancaDTO solicitacao = new RequisicaoMudancaDTO();
                solicitacao.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                solicitacao = (RequisicaoMudancaDTO) requisicaoMudancaServiceEjb.restore(solicitacao);
                solicitacao.setVencendo("N");
                requisicaoMudancaServiceEjb.updateNotNull(solicitacao);
                solicitacao = null;
            }
            // trata caso o sla tenha sido alterado para mais
            if (requisicaoMudancaDto.getVencendo() != null && !requisicaoMudancaDto.getVencendo().equalsIgnoreCase("N")) {
                RequisicaoMudancaDTO solicitacao = new RequisicaoMudancaDTO();
                solicitacao.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
                solicitacao = (RequisicaoMudancaDTO) requisicaoMudancaServiceEjb.restore(solicitacao);
                solicitacao.setVencendo("N");
                requisicaoMudancaServiceEjb.updateNotNull(solicitacao);
                solicitacao = null;
            }
        }

    }

    public void enviaEmail(final Integer idModeloEmail, final Integer idRequisicaoMudanca) throws Exception {
        if (idModeloEmail == null || idModeloEmail.intValue() == 0) {
            return;
        }

        final String enviaEmail = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EnviaEmailFluxo, "N");
        if (!enviaEmail.equalsIgnoreCase("S")) {
            return;
        }

        final String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
        if (remetente == null) {
            throw new LogicException("Remetente para notificações de solicitação de serviço não foi parametrizado");
        }

        final String urlSistema = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.URL_Sistema, "");

        final RequisicaoMudancaDTO requisicaoAuxDto = this.getRequisicaoMudancaService().restoreAll(idRequisicaoMudanca, null);
        requisicaoAuxDto.setNomeTarefa("Automatic escalation");

        final String idHashValidacao = CriptoUtils.generateHash("CODED" + requisicaoAuxDto.getIdRequisicaoMudanca(), "MD5");
        requisicaoAuxDto.setLinkPesquisaSatisfacao("<a href=\"" + urlSistema + "/pages/pesquisaSatisfacao/pesquisaSatisfacao.load?idSolicitacaoServico="
                + requisicaoAuxDto.getIdRequisicaoMudanca() + "&hash=" + idHashValidacao + "\">Clique aqui para fazer a avaliação do Atendimento</a>");

        final MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] {requisicaoAuxDto});
        try {
            mensagem.envia(requisicaoAuxDto.getEmailContato(), remetente, remetente);
        } catch (final Exception e) {}
    }

}
