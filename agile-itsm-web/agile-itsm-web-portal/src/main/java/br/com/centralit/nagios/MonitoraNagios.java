package br.com.centralit.nagios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitoramentoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RecursoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoEvtMonDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.EmpregadoServiceEjb;
import br.com.centralit.citcorpore.negocio.EventoMonitoramentoServiceEjb;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoServiceEjb;
import br.com.centralit.citcorpore.negocio.RecursoServiceEjb;
import br.com.centralit.citcorpore.negocio.ServicoContratoServiceEjb;
import br.com.centralit.citcorpore.negocio.ServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoEvtMonServiceEjb;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.UsuarioServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.nagios.livestatus.tables.Hosts;
import br.com.centralit.nagios.livestatus.tables.Services;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class MonitoraNagios extends Thread {

    public static Semaphore performanceDataSemaphore = new Semaphore(1);
    public static PerformanceData performanceData;
    public static JavNag javaNagios;
    long lastPerformanceDump = 0;
    public static Map<String, String> mapaStatusHosts = new HashMap<>();
    public static Map<String, Integer> mapaStatusHosts_RegIncident = new HashMap<>();

    private final EmpregadoServiceEjb empregadoService = new EmpregadoServiceEjb();
    private final EventoMonitoramentoServiceEjb eventoMonitoramentoService = new EventoMonitoramentoServiceEjb();
    private final ItemConfiguracaoServiceEjb itemConfiguracaoServiceEjb = new ItemConfiguracaoServiceEjb();
    private final RecursoServiceEjb recursoService = new RecursoServiceEjb();
    private final ServicoContratoServiceEjb servicoContratoService = new ServicoContratoServiceEjb();
    private final ServicoServiceEjb servicoService = new ServicoServiceEjb();
    private final UsuarioServiceEjb usuarioService = new UsuarioServiceEjb();
    private final SolicitacaoServicoServiceEjb solicitacaoServicoService = new SolicitacaoServicoServiceEjb();
    private final SolicitacaoServicoEvtMonServiceEjb solicitacaoServicoEvtMonServiceEjb = new SolicitacaoServicoEvtMonServiceEjb();

    @Override
    public void run() {
        final String habilitaMonitoramentoNagios = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.HABILITA_MONITORAMENTO_NAGIOS, "N");

        if (habilitaMonitoramentoNagios != null && habilitaMonitoramentoNagios.trim().equalsIgnoreCase("S")) {
            performanceData = new PerformanceData();
            while (true) {
                performanceDataSemaphore.acquireUninterruptibly();
                try {
                    System.out.println("CITSMART - Carregando informacoes do Nagios...");
                    javaNagios = new JavNag();

                    final String nagiosTipoAcesso = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NAGIOS_TIPO_ACESSO, "1");

                    if (nagiosTipoAcesso != null && StringUtils.isNotBlank(nagiosTipoAcesso)) {

                        if (nagiosTipoAcesso.trim().equalsIgnoreCase("1")) {
                            final String conexoes = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NAGIOS_CONEXOES_LIVESTATUS, "");

                            if (conexoes != null && StringUtils.isNotBlank(conexoes)) {
                                final String[] nagiosConexoes = conexoes.split(",");

                                for (final String conexao : nagiosConexoes) {
                                    final Hosts servidorNagios = new Hosts(conexao.trim());

                                    try {
                                        for (final String nomeHost : servidorNagios.hosts()) {
                                            final Host host = new Host("Nagios", nomeHost);

                                            final Map<String, String> atributosHost = servidorNagios.asArrayString("hosts", "name = " + nomeHost);
                                            for (final String key : atributosHost.keySet()) {
                                                host.addParameter(key, atributosHost.get(key));
                                            }

                                            for (final String serviceName : servidorNagios.services(nomeHost)) {
                                                final Services serviceHostLiveStatus = new Services(conexao.trim());
                                                final Map<String, String> atributosService = serviceHostLiveStatus.asArrayString("services", "display_name = " + serviceName + ","
                                                        + "host_name = " + nomeHost);

                                                for (final String keyService : atributosService.keySet()) {
                                                    host.addServiceEntry(serviceName, keyService, atributosService.get(keyService));
                                                }
                                            }
                                            javaNagios.hosts.add(host);
                                        }
                                    } catch (final Exception e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        } else {
                            if (nagiosTipoAcesso.trim().equalsIgnoreCase("2")) {
                                String pathNagiosMon = "";
                                try {
                                    pathNagiosMon = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PATH_NAGIOS_STATUS,
                                            "C:\\jboss\\jboss\\server\\default\\deploy\\status.dat.txt");
                                } catch (final Exception e1) {
                                    pathNagiosMon = "C:\\jboss\\jboss\\server\\default\\deploy\\status.dat.txt";
                                }
                                try {
                                    javaNagios.loadNagiosData(pathNagiosMon, 3, "Nagios");
                                } catch (final Exception e) {}
                            }
                        }
                    }

                    try {
                        this.collectPerformanceData(javaNagios);
                    } catch (final Exception e) {}
                } finally {
                    performanceDataSemaphore.release();
                }
                super.run();
                try {
                    Thread.sleep(60000);
                } catch (final InterruptedException e) {}
            }
        }
    }

    public void generateHashLastState(final JavNag javNag) throws Exception {
        for (final Host currentHost : javNag.getListOfHosts()) {
            final List<ParameterEntry> hostEntries = currentHost.getParameters();
            String current_state = currentHost.getParameter("current_state");
            if (current_state.equalsIgnoreCase("1")) {
                current_state = "DOWN";
            } else if (current_state.equalsIgnoreCase("0")) {
                current_state = "UP";
            } else {
                current_state = "PENDING";
            }
            final String last_check_str = currentHost.getParameter("last_check");

            int last_check = 0;
            try {
                last_check = Integer.parseInt(last_check_str);
            } catch (final Exception e) {}

            if (current_state.equalsIgnoreCase("DOWN") || current_state.equalsIgnoreCase("CRITICAL") || current_state.equalsIgnoreCase("WARNING")) {
                final Integer lastRegIncidente = mapaStatusHosts_RegIncident.get(currentHost.getHostName());
                if (lastRegIncidente == null || lastRegIncidente.intValue() != last_check) {
                    final Collection col = recursoService.findByHostName(currentHost.getHostName());
                    if (col != null) {
                        for (final Iterator it = col.iterator(); it.hasNext();) {
                            RecursoDTO recursoDTO = (RecursoDTO) it.next();
                            if (recursoDTO.getServiceName() == null || recursoDTO.getServiceName().trim().equalsIgnoreCase("")) {
                                try {
                                    this.generateIncidente(recursoDTO, currentHost.getHostName(), current_state, last_check, hostEntries);
                                } catch (final Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            recursoDTO = null;
                        }
                    }
                }
            }
            current_state = current_state + "#" + last_check_str;
            mapaStatusHosts.put(currentHost.getHostName(), current_state);
        }
    }

    public void generateIncidente(final RecursoDTO recursoDTO, final String hostName, final String current_state, final int last_check, final List<ParameterEntry> hostEntries)
            throws ServiceException, LogicException {
        if (recursoDTO == null) {
            return;
        }
        if (recursoDTO.getStatusAberturaInc() == null || recursoDTO.getStatusAberturaInc().trim().equalsIgnoreCase("")) {
            return;
        }
        if (recursoDTO.getStatusAberturaInc().indexOf(current_state) > -1) { // Se o status em questao estiver dentro da
            // lista informada no cadastro. o padrao eh DOWN,CRITICAL,...
            Collection colDados = null;
            try {
                colDados = solicitacaoServicoEvtMonServiceEjb.findByIdRecursoAndSolicitacaoAberta(recursoDTO.getIdRecurso());
            } catch (final Exception e1) {
                e1.printStackTrace();
            }
            if (colDados == null || colDados.size() == 0) {
                ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
                servicoContratoDTO.setIdServicoContrato(recursoDTO.getIdServicoContrato());
                try {
                    servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);
                } catch (final Exception e) {
                    e.printStackTrace();
                    servicoContratoDTO = null;
                }

                String nameEventSystem = "";
                if (recursoDTO.getIdEventoMonitoramento() != null && recursoDTO.getIdEventoMonitoramento().intValue() > 0) {
                    EventoMonitoramentoDTO eventoMonitoramentoDTO = new EventoMonitoramentoDTO();
                    eventoMonitoramentoDTO.setIdEventoMonitoramento(recursoDTO.getIdEventoMonitoramento());
                    eventoMonitoramentoDTO = (EventoMonitoramentoDTO) eventoMonitoramentoService.restore(eventoMonitoramentoDTO);
                    if (eventoMonitoramentoDTO != null) {
                        nameEventSystem = eventoMonitoramentoDTO.getNomeEvento();
                    }
                }
                String descr = recursoDTO.getDescricaoAbertInc();

                descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{dadosmonitoramento\\}", this.getInfoParameters(hostEntries));
                descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{hostname\\}", hostName);
                descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{eventname\\}", nameEventSystem);
                descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{currentstate\\}", current_state);
                descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{dataatual\\}", UtilDatas.dateToSTR(UtilDatas.getDataAtual()));
                descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{datahoraatual\\}",
                        UtilDatas.dateToSTR(UtilDatas.getDataHoraAtual()) + " - " + UtilDatas.formatHoraFormatadaHHMMSSStr(UtilDatas.getDataHoraAtual()));

                SolicitacaoServicoDTO solicitacaoServicoDTO = new SolicitacaoServicoDTO();
                solicitacaoServicoDTO.setIdSolicitante(recursoDTO.getIdSolicitante());
                solicitacaoServicoDTO.setDescricao(descr);
                solicitacaoServicoDTO.setIdOrigem(recursoDTO.getIdOrigem());
                if (servicoContratoDTO != null) {
                    solicitacaoServicoDTO.setIdContrato(servicoContratoDTO.getIdContrato());
                }
                solicitacaoServicoDTO.setIdServicoContrato(recursoDTO.getIdServicoContrato());
                solicitacaoServicoDTO.setIdGrupoAtual(recursoDTO.getIdGrupo());
                solicitacaoServicoDTO.setSituacao(Enumerados.SituacaoSolicitacaoServico.EmAndamento.name());
                solicitacaoServicoDTO.setEmailcontato(recursoDTO.getEmailAberturaInc());
                solicitacaoServicoDTO.setUrgencia(recursoDTO.getUrgencia());
                solicitacaoServicoDTO.setImpacto(recursoDTO.getImpacto());

                EmpregadoDTO empregadoDto = new EmpregadoDTO();
                empregadoDto.setIdEmpregado(recursoDTO.getIdSolicitante());
                try {
                    empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
                } catch (final Exception e) {
                    empregadoDto = null;
                }
                UsuarioDTO usuario = new UsuarioDTO();
                if (empregadoDto != null) {
                    solicitacaoServicoDTO.setNomecontato(empregadoDto.getNome());
                    solicitacaoServicoDTO.setTelefonecontato(empregadoDto.getTelefone());
                    solicitacaoServicoDTO.setRamal(empregadoDto.getRamal());
                    solicitacaoServicoDTO.setIdUnidade(empregadoDto.getIdUnidade());

                    usuario.setIdEmpregado(empregadoDto.getIdEmpregado());
                    try {
                        usuario = usuarioService.restoreByIdEmpregado(empregadoDto.getIdEmpregado());
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
                solicitacaoServicoDTO.setObservacao("Automatically generate by monitoring system.\n" + nameEventSystem + "\nState: " + current_state);

                ServicoDTO servicoDTO = new ServicoDTO();
                if (servicoContratoDTO != null) {
                    servicoDTO.setIdServico(servicoContratoDTO.getIdServico());
                }
                servicoDTO = (ServicoDTO) servicoService.restore(servicoDTO);

                if (servicoContratoDTO != null) {
                    solicitacaoServicoDTO.setIdServico(servicoContratoDTO.getIdServico());
                    solicitacaoServicoDTO.setIdTipoDemandaServico(servicoDTO.getIdTipoDemandaServico());
                }

                solicitacaoServicoDTO.setUsuarioDto(usuario);
                solicitacaoServicoDTO.setRegistradoPor(usuario.getNomeUsuario());

                solicitacaoServicoDTO.setEnviaEmailCriacao("S");
                solicitacaoServicoDTO.setEnviaEmailAcoes("S");
                solicitacaoServicoDTO.setEnviaEmailFinalizacao("S");

                if (recursoDTO.getIdItemConfiguracao() != null) {
                    final List<ItemConfiguracaoDTO> colItensIC = new ArrayList<ItemConfiguracaoDTO>();
                    ItemConfiguracaoDTO itemConfiguracaoDTO = null;
                    try {
                        itemConfiguracaoDTO = itemConfiguracaoServiceEjb.restoreByIdItemConfiguracao(recursoDTO.getIdItemConfiguracao());
                    } catch (final Exception e) {
                        e.printStackTrace();
                        itemConfiguracaoDTO = null;
                    }
                    if (itemConfiguracaoDTO != null) {
                        colItensIC.add(itemConfiguracaoDTO);
                        solicitacaoServicoDTO.setColItensICSerialize(colItensIC);
                    }
                }

                final SolicitacaoServicoEvtMonDTO solicitacaoServicoEvtMonDTO = new SolicitacaoServicoEvtMonDTO();
                solicitacaoServicoEvtMonDTO.setIdEventoMonitoramento(recursoDTO.getIdEventoMonitoramento());
                solicitacaoServicoEvtMonDTO.setIdRecurso(recursoDTO.getIdRecurso());
                solicitacaoServicoEvtMonDTO.setNomeHost(recursoDTO.getHostName());
                solicitacaoServicoEvtMonDTO.setNomeService(recursoDTO.getServiceName());
                solicitacaoServicoEvtMonDTO.setInfoAdd(this.getInfoParameters(hostEntries));
                final List<SolicitacaoServicoEvtMonDTO> colSolicitacaoServicoEvtMon = new ArrayList<SolicitacaoServicoEvtMonDTO>();
                colSolicitacaoServicoEvtMon.add(solicitacaoServicoEvtMonDTO);

                solicitacaoServicoDTO.setColSolicitacaoServicoEvtMon(colSolicitacaoServicoEvtMon);

                solicitacaoServicoDTO.setRegistroexecucao(solicitacaoServicoDTO.getObservacao());

                solicitacaoServicoDTO = (SolicitacaoServicoDTO) solicitacaoServicoService.create(solicitacaoServicoDTO);
                mapaStatusHosts_RegIncident.put(hostName, last_check);
                solicitacaoServicoDTO = null;
            }
        }
        if (recursoDTO.getStatusAlerta().indexOf(current_state) > -1) { // Se o status em questao estiver dentro da
            // lista informada no cadastro. o padrao eh
            // DOWN,CRITICAL,...
            final EventoMonitoramentoServiceEjb eventoMonitoramentoService = new EventoMonitoramentoServiceEjb();
            String nameEventSystem = "";
            if (recursoDTO.getIdEventoMonitoramento() != null && recursoDTO.getIdEventoMonitoramento().intValue() > 0) {
                EventoMonitoramentoDTO eventoMonitoramentoDTO = new EventoMonitoramentoDTO();
                eventoMonitoramentoDTO.setIdEventoMonitoramento(recursoDTO.getIdEventoMonitoramento());
                eventoMonitoramentoDTO = (EventoMonitoramentoDTO) eventoMonitoramentoService.restore(eventoMonitoramentoDTO);
                if (eventoMonitoramentoDTO != null) {
                    nameEventSystem = eventoMonitoramentoDTO.getNomeEvento();
                }
            }

            String descr = recursoDTO.getDescricaoAbertInc();
            descr = UtilStrings.nullToVazio(descr).replaceAll("\n", "<br>");
            descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{dadosmonitoramento\\}", this.getInfoParameters(hostEntries));
            descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{hostname\\}", hostName);
            descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{eventname\\}", nameEventSystem);
            descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{currentstate\\}", current_state);
            descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{dataatual\\}", UtilDatas.dateToSTR(UtilDatas.getDataAtual()));
            descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{datahoraatual\\}",
                    UtilDatas.dateToSTR(UtilDatas.getDataHoraAtual()) + " - " + UtilDatas.formatHoraFormatadaHHMMSSStr(UtilDatas.getDataHoraAtual()));

            String remetente = null;
            try {
                remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            if (remetente == null) {
                remetente = "citsmart@citsmart.com.br";
            }
            final String mailsAlerta = UtilStrings.nullToVazio(recursoDTO.getEmailsAlerta()).trim();
            final String[] mails = mailsAlerta.split(";");
            if (mails != null) {
                for (final String mail : mails) {
                    final String mailTo = UtilStrings.nullToVazio(mail).trim();
                    final MensagemEmail mensagemEmail = new MensagemEmail(mailTo, null, null, remetente, "CITSMART - Monitoring Alert: " + hostName + " - " + current_state
                            + " -->> Event: " + nameEventSystem, descr);
                    try {
                        mensagemEmail.envia(mailTo, null, remetente);
                        System.out.println("CITSMART - Monitoring Alert: " + current_state + " Event: " + nameEventSystem + " -->> Send mail: " + mailTo);
                    } catch (final Exception e) {
                        e.printStackTrace();
                        System.out.println("CITSMART - Monitoring Alert: " + current_state + " Event: " + nameEventSystem + " -->> PROBLEM Send mail: " + mailTo);
                    }
                }
            }
        }
    }

    public String getInfoParameters(final List<ParameterEntry> hostEntries) {
        if (hostEntries == null) {
            return "";
        }
        String ret = "";
        for (final Object element : hostEntries) {
            final ParameterEntry parameterEntry = (ParameterEntry) element;
            ret = ret + parameterEntry.getParameterName() + " : " + parameterEntry.getParameterValue() + "<br>\n";
        }
        return ret;
    }

    public void collectPerformanceData(final JavNag javNag) throws Exception {
        for (final Host currentHost : javNag.getListOfHosts()) {
            final String hostPerformanceData = currentHost.getParameter("performance_data");
            final String lastHostCheck = currentHost.getParameter("last_check");
            if (hostPerformanceData != null && hostPerformanceData.trim().equals("") == false) {
                performanceData.add(currentHost.getHostName(), hostPerformanceData, lastHostCheck);
            }

            for (final Service currentService : currentHost.getServices()) {
                final String servicePerformanceData = currentService.getParameter("performance_data");
                final String lastServiceCheck = currentService.getParameter("last_check");
                if (servicePerformanceData != null && servicePerformanceData.trim().equals("") == false) {
                    performanceData.add(currentHost.getHostName() + " | " + currentService.getServiceName(), servicePerformanceData, lastServiceCheck);
                }
            }
        }
        this.generateHashLastState(javNag);

        if (System.currentTimeMillis() - lastPerformanceDump > 1800000) {
            this.dumpPerformanceData(javNag);

            lastPerformanceDump = System.currentTimeMillis();
        }
    }

    public void dumpPerformanceData(final JavNag javNag) throws Exception {
        this.processaDump(javNag);
    }

    public void processaDump(final JavNag javNag) {
        for (final Host currentHost : javNag.getListOfHosts()) {
            String current_state = currentHost.getParameter("current_state");
            current_state = UtilStrings.nullToVazio(current_state);
            if (current_state.equalsIgnoreCase("1")) {
                current_state = "DOWN";
            } else if (current_state.equalsIgnoreCase("0")) {
                current_state = "UP";
            } else {
                current_state = "PENDING";
            }
        }
    }

    public static void main(final String[] args) {
        final MonitoraNagios monitoraNagios = new MonitoraNagios();
        monitoraNagios.start();
    }

}
