package br.com.centralit.citcorpore.batch;

import java.util.Collection;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmailDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.MonitoramentoAtivosDTO;
import br.com.centralit.citcorpore.bean.NotificacaoGrupoMonitDTO;
import br.com.centralit.citcorpore.bean.NotificacaoUsuarioMonitDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmailDao;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoGrupoMonitDAO;
import br.com.centralit.citcorpore.integracao.NotificacaoUsuarioMonitDAO;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.PortalService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

/**
 * @author valdoilo.damasceno
 * @since 13.06.2014
 */
@SuppressWarnings("unused")
public class ThreadMonitoraAtivosConfiguracao extends Thread {

    private TipoItemConfiguracaoDTO tipoItemConfiguracaoDto;

    private CaracteristicaDTO caracteristicaDto;

    private ValorDTO valorAnteriorDto;

    private ValorDTO novoValorDto;

    private MonitoramentoAtivosDTO monitoramentoAtivosDto;

    public ThreadMonitoraAtivosConfiguracao(MonitoramentoAtivosDTO monitoramentoAtivosDto, TipoItemConfiguracaoDTO tipoItemConfiguracaoDto, CaracteristicaDTO caracteristicaDto, ValorDTO valorAntigo,
            ValorDTO novoValorDto) {

        this.monitoramentoAtivosDto = monitoramentoAtivosDto;

        this.tipoItemConfiguracaoDto = tipoItemConfiguracaoDto;

        this.caracteristicaDto = caracteristicaDto;

        valorAnteriorDto = valorAntigo;

        this.novoValorDto = novoValorDto;
    }

    @Override
    public void run() {

        if (monitoramentoAtivosDto != null) {

            if (monitoramentoAtivosDto.getEnviarEmail() != null && monitoramentoAtivosDto.getEnviarEmail().equalsIgnoreCase("y")) {
                try {
                    this.tratarNotificacaoEmail(monitoramentoAtivosDto, tipoItemConfiguracaoDto, caracteristicaDto, valorAnteriorDto, novoValorDto);
                } catch (LogicException e) {
                    e.printStackTrace();
                }
            }

            if (monitoramentoAtivosDto.getCriarProblema() != null && monitoramentoAtivosDto.getCriarProblema().equalsIgnoreCase("y")) {
                try {
                    this.criarProblema(monitoramentoAtivosDto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (monitoramentoAtivosDto.getCriarIncidente() != null && monitoramentoAtivosDto.getCriarIncidente().equalsIgnoreCase("y")) {
                this.criarIncidente(monitoramentoAtivosDto);

            }
        }
    }

    /**
     * @param monitoramentoAtivosDto2
     * @author rodrigo.pecci
     */
    private void criarIncidente(MonitoramentoAtivosDTO monitoramentoAtivosDto2) {
        try {
            SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
            UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
            EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
            PortalService portalService = (PortalService) ServiceLocator.getInstance().getService(PortalService.class, null);
            ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);

            SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();

            // Preenche o contrato
            Integer idContrato = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTRATO_PADRAO, "1"));
            solicitacaoServicoDto.setIdContrato(idContrato);

            // Preenche a origem
            Integer idOrigem = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "2"));
            solicitacaoServicoDto.setIdOrigem(idOrigem);

            // Preenche o grupo atual
            Integer idGrupoAtual = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ID_GRUPO_PADRAO_NIVEL1, "53"));
            solicitacaoServicoDto.setIdGrupoAtual(idGrupoAtual);

            // Preenche a situação e registro de execução
            solicitacaoServicoDto.setSituacao("EmAndamento");
            solicitacaoServicoDto.setRegistroexecucao("");

            // Preenche as informações do solicitante e contato
            UsuarioDTO usuarioDto = usuarioService.restoreByIdEmpregado(1);
            EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(usuarioDto.getIdEmpregado());

            solicitacaoServicoDto.setIdSolicitante(usuarioDto.getIdEmpregado());
            solicitacaoServicoDto.setUsuarioDto(usuarioDto);
            solicitacaoServicoDto.setRegistradoPor(usuarioDto.getNomeUsuario());
            solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());
            solicitacaoServicoDto.setNomecontato(empregadoDto.getNome());
            solicitacaoServicoDto.setEmailcontato(empregadoDto.getEmail());
            solicitacaoServicoDto.setTelefonecontato(empregadoDto.getTelefone());

            // Preenche o id do serviço
            Integer idServico = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SERVICO_PADRAO_SOLICITACAO, "1721"));
            solicitacaoServicoDto.setIdServico(idServico);

            // Preenche o tipo de demanda
            ServicoDTO servicoDto = servicoService.findById(idServico);
            solicitacaoServicoDto.setIdTipoDemandaServico(servicoDto.getIdTipoDemandaServico());

            // Preenche o impacto e urgência
            portalService.relacionaImpactoUrgencia(solicitacaoServicoDto);

            // Preenche a descrição
            solicitacaoServicoDto.setDescricao(UtilStrings.getParameter(monitoramentoAtivosDto2.getDescricao()));

            // Realiza o create
            solicitacaoServicoService.create(solicitacaoServicoDto);
        } catch (ServiceException e) {
            System.out.println("FALHA AO CRIAR INCIDENTE NO MONITORAMENTO DE ATIVOS DE CONFIGURAÇÃO. VERIFICAR PARÂMETROS RELACIONADOS.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("FALHA AO CRIAR INCIDENTE NO MONITORAMENTO DE ATIVOS DE CONFIGURAÇÃO. VERIFICAR PARÂMETROS RELACIONADOS.");
            e.printStackTrace();
        }
    }

    /**
     * @param monitoramentoAtivosDto
     * @author valdoilo.damasceno
     * @throws Exception
     * @since 17.06.2014
     */
    private void criarProblema(MonitoramentoAtivosDTO monitoramentoAtivosDto) throws Exception {

        Integer idContrato = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.CONTRATO_PADRAO, "1"));
        Integer idGrupoNivel1 = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_NIVEL1, "53"));
        Integer idServico = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVICO_PADRAO_SOLICITACAO, "1721"));
        Integer idOrigem = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "2"));

        EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

        UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

        // admin
        UsuarioDTO usuarioDto = usuarioService.restoreByIdEmpregado(1);

        EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(usuarioDto.getIdEmpregado());

        ProblemaDTO problemaDto = new ProblemaDTO();
        ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
        problemaDto.setIdContrato(idContrato);
        problemaDto.setIdSolicitante(empregadoDto.getIdEmpregado());
        problemaDto.setIdOrigemAtendimento(idOrigem);
        problemaDto.setNomeContato(empregadoDto.getNome());
        problemaDto.setEmailContato(empregadoDto.getEmail());
        problemaDto.setTelefoneContato(empregadoDto.getTelefone());
        problemaDto.setRamal(empregadoDto.getRamal());
        problemaDto.setIdUnidade(empregadoDto.getIdUnidade());
        problemaDto.setTitulo("Problema Criado por Rotina automática");
        problemaDto.setDescricao(StringEscapeUtils.escapeHtml(monitoramentoAtivosDto.getDescricao()));
        problemaDto.setSeveridade("Alta");

        problemaDto.setStatus("Registrada");
        problemaDto.setImpacto("M");
        problemaDto.setUrgencia("M");

        problemaDto.setEnviaEmailCriacao("S");
        problemaDto.setEnviaEmailFinalizacao("S");
        problemaDto.setEnviaEmailPrazoSolucionarExpirou("S");
        problemaDto.setIdGrupo(idGrupoNivel1);
        problemaDto.setIdCriador(empregadoDto.getIdEmpregado());
        problemaDto.setIdResponsavel(empregadoDto.getIdEmpregado());
        problemaDto.setIdPrioridade(3);
        problemaDto.setPrioridade(3);
        problemaDto.setIdServico(idServico);
        problemaDto.setUsuarioDto(usuarioDto);
        problemaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
        // categoria padrão
        problemaDto.setIdCategoriaProblema(1);

        problemaService.create(problemaDto);

    }

    /**
     * Trata o envio de Notificações de acordo com os Usuários e Grupos informados no Monitoramento de Ativos.
     *
     * @throws LogicException
     * @param monitoramentoAtivosDto2
     * @author valdoilo.damasceno
     * @param novoValorDto2
     * @param valorAnteriorDto2
     * @param caracteristicaDto2
     * @param tipoItemConfiguracaoDto2
     * @since 16.06.2014
     */
    private void tratarNotificacaoEmail(MonitoramentoAtivosDTO monitoramentoAtivosDto2, TipoItemConfiguracaoDTO tipoItemConfiguracaoDto2, CaracteristicaDTO caracteristicaDto2,
            ValorDTO valorAnteriorDto2, ValorDTO novoValorDto2) throws LogicException {

        if (monitoramentoAtivosDto2.getEnviarEmail() != null && monitoramentoAtivosDto2.getEnviarEmail().equalsIgnoreCase("y")) {
            NotificacaoUsuarioMonitDAO notificacaoUsuarioMonitDao = new NotificacaoUsuarioMonitDAO();
            NotificacaoGrupoMonitDAO notificacaoGrupoMonitDao = new NotificacaoGrupoMonitDAO();
            EmpregadoDao empregadoDao = new EmpregadoDao();
            GrupoEmailDao grupoEmailDao = new GrupoEmailDao();
            UsuarioDao usuarioDao = new UsuarioDao();

            String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
            if (remetente == null) {
                throw new LogicException("Remetente para notificações de solicitação de serviço não foi parametrizado");
            }

            try {
                Integer idModeloEmail = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.MONITORAMENTO_ATIVOS_ID_MODELO_EMAIL_NOTIFICACAO, ""));

                ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
				ItemConfiguracaoDTO itemConfiguracaoDTO = itemConfiguracaoDao.findByIdItemConfiguracaoWithIdentificacaoPai(valorAnteriorDto2.getIdItemConfiguracao());
                
                Collection<NotificacaoUsuarioMonitDTO> listNotificacaoUsuarioMonitDto = notificacaoUsuarioMonitDao.restoreByIdMonitoramentoAtivos(monitoramentoAtivosDto2.getIdMonitoramentoAtivos());

                if (listNotificacaoUsuarioMonitDto != null && !listNotificacaoUsuarioMonitDto.isEmpty()) {
                    for (NotificacaoUsuarioMonitDTO notificacaoUsuario : listNotificacaoUsuarioMonitDto) {

                        EmpregadoDTO empregadoDto = empregadoDao.restoreByIdUsuario(notificacaoUsuario.getIdUsuario());
                        if (empregadoDto != null && empregadoDto.getEmail() != null && StringUtils.isNotBlank(empregadoDto.getEmail())) {
                            this.enviarEmail(remetente, idModeloEmail, empregadoDto.getEmail(), monitoramentoAtivosDto2, tipoItemConfiguracaoDto2, caracteristicaDto2, valorAnteriorDto2, novoValorDto2, itemConfiguracaoDTO);
                        }
                    }
                }

                Collection<NotificacaoGrupoMonitDTO> listNotificacaoGrupoMonitDTO = notificacaoGrupoMonitDao.restoreByIdMonitoramentoAtivos(monitoramentoAtivosDto2.getIdMonitoramentoAtivos());

                if (listNotificacaoGrupoMonitDTO != null && !listNotificacaoGrupoMonitDTO.isEmpty()) {
                    for (NotificacaoGrupoMonitDTO notificacaoGrupo : listNotificacaoGrupoMonitDTO) {

                        Collection<EmpregadoDTO> listEmpregadoDto = empregadoDao.restoreByIdGrupo(notificacaoGrupo.getIdGrupo());
                        if (listEmpregadoDto != null && !listEmpregadoDto.isEmpty()) {
                            for (EmpregadoDTO empregadoDto : listEmpregadoDto) {
                                if (empregadoDto.getEmail() != null && StringUtils.isNotBlank(empregadoDto.getEmail())) {
                                    this.enviarEmail(remetente, idModeloEmail, empregadoDto.getEmail(), monitoramentoAtivosDto2, tipoItemConfiguracaoDto2, caracteristicaDto2, valorAnteriorDto2,
                                            novoValorDto2, itemConfiguracaoDTO);
                                }
                            }
                        }

                        Collection<GrupoEmailDTO> listGrupoEmailDto = grupoEmailDao.findByIdGrupo(notificacaoGrupo.getIdGrupo());

                        if (listGrupoEmailDto != null && !listGrupoEmailDto.isEmpty()) {
                            for (GrupoEmailDTO grupoEmailDto : listGrupoEmailDto) {
                                this.enviarEmail(remetente, idModeloEmail, grupoEmailDto.getEmail(), monitoramentoAtivosDto2, tipoItemConfiguracaoDto2, caracteristicaDto2, valorAnteriorDto2,
                                        novoValorDto2, itemConfiguracaoDTO);
                            }
                        }
                    }
                }

            } catch (NumberFormatException ne) {
                System.out.println("FALHA AO ENVIAR E-MAIL NO MONITORAMENTO DE ATIVOS DE CONFIGURAÇÃO. VERIFICAR PARÂMETROS RELACIONADOS.");
                ne.printStackTrace();
            } catch (Exception e) {
                System.out.println("FALHA AO ENVIAR E-MAIL NO MONITORAMENTO DE ATIVOS DE CONFIGURAÇÃO. VERIFICAR PARÂMETROS RELACIONADOS.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Envia Mensagem de E-mail utilizando o Remente, Modelo de E-mail e Destinatário informados.
     *
     * @param remetente
     *            - Remetente do e-mail.
     * @param idModeloEmail
     *            - Modelo de e-mail que será utilizado.
     * @param empregadoDto
     *            - Empregado para quem será enviado e-mail.
     * @param monitoramentoAtivosDto2
     * @throws Exception
     * @author valdoilo.damasceno
     * @param novoValorDto2
     * @param valorAnteriorDto2
     * @param caracteristicaDto2
     * @param tipoItemConfiguracaoDto2
     * @since 16.06.2014
     */
    private void enviarEmail(String remetente, Integer idModeloEmail, String emailEmpregado, MonitoramentoAtivosDTO monitoramentoAtivosDto2, TipoItemConfiguracaoDTO tipoItemConfiguracaoDto2,
            CaracteristicaDTO caracteristicaDto2, ValorDTO valorAnteriorDto2, ValorDTO novoValorDto2, ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {
    	
        MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] { monitoramentoAtivosDto2, tipoItemConfiguracaoDto2, caracteristicaDto2, valorAnteriorDto2, novoValorDto2, itemConfiguracaoDTO });

        try {
            mensagem.envia(emailEmpregado, remetente, remetente);
        } catch (Exception e) {
            System.out.println("FALHA AO ENVIAR E-MAIL NO MONITORAMENTO DE ATIVOS DE CONFIGURAÇÃO. VERIFICAR PARÂMETROS RELACIONADOS.");
            e.printStackTrace();
        }
    }

}
