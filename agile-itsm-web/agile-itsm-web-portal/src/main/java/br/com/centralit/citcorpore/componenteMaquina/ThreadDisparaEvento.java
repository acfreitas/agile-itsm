package br.com.centralit.citcorpore.componenteMaquina;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import br.com.centralit.citcorpore.bean.BaseItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.EventoItemConfigDTO;
import br.com.centralit.citcorpore.bean.HistoricoTentativaDTO;
import br.com.centralit.citcorpore.negocio.BaseItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.EventoItemConfigService;
import br.com.centralit.citcorpore.negocio.HistoricoTentativaService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.cripto.CriptoSignedUtil;
import br.com.citframework.util.cripto.SignedInfo;

public class ThreadDisparaEvento implements Runnable {

    private final Integer idItemConfiguracao;
    private final Integer idBaseItemConfiguracao;
    private final Integer idEvento;
    private final String tipoExecucao;
    private final String linhaComando;
    private final String linhaComandoLinux;

    public ThreadDisparaEvento(Integer idItemConfiguracao, Integer idBaseItemConfiguracao, Integer idEvento,
            String tipoExecucao, String linhaComando, String linhaComandoLinux) {
        this.idItemConfiguracao = idItemConfiguracao;
        this.idBaseItemConfiguracao = idBaseItemConfiguracao;
        this.idEvento = idEvento;
        this.tipoExecucao = tipoExecucao;
        this.linhaComando = linhaComando;
        this.linhaComandoLinux = linhaComandoLinux;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread (Evento) iniciada!");
            String caminho = "";
            String comando = "";
            String comandoCopia = "";
            String comandoDelete = "";
            // Serviços
            final EventoItemConfigService eventoItemCfgService = (EventoItemConfigService) ServiceLocator.getInstance()
                    .getService(EventoItemConfigService.class, null);
            final BaseItemConfiguracaoService baseItemCfgService = (BaseItemConfiguracaoService) ServiceLocator
                    .getInstance().getService(BaseItemConfiguracaoService.class, null);
            // Recuperando informações de rede (IP, MAC e máscara)
            System.out.println("Buscando IP do computador...");
            final List<CaracteristicaDTO> colNetworks = (List<CaracteristicaDTO>) eventoItemCfgService
                    .pegarNetworksItemConfiguracao(idItemConfiguracao);

            // TESTE COM IP ESTATICO
            // String ip = "10.2.1.138";
            // String mac = "08:00:27:9e:09:a0";
            // String ipMask = "255.255.255.0";

            String ip = "";
            String mac = "";
            String ipMask = "";
            final CaracteristicaDTO caracteristicaDTO = new CaracteristicaDTO();
            caracteristicaDTO.setTag("IPADDRESS");
            int index = colNetworks.indexOf(caracteristicaDTO);
            if (index > -1) {
                ip = colNetworks.get(index).getValorString();
            }
            caracteristicaDTO.setTag("MACADDR");
            index = colNetworks.indexOf(caracteristicaDTO);
            if (index > -1) {
                mac = colNetworks.get(index).getValorString();
            }
            caracteristicaDTO.setTag("IPMASK");
            index = colNetworks.indexOf(caracteristicaDTO);
            if (index > -1) {
                ipMask = colNetworks.get(index).getValorString();
            }

            /*
             * for (CaracteristicaDTO caracDto : colNetworks) {
             * if (ip.equals("") && caracDto.getTag().equalsIgnoreCase("IPADDRESS")) {
             * ip = caracDto.getValorString();
             * }
             * if (mac.equals("") && caracDto.getTag().equalsIgnoreCase("MACADDR")) {
             * mac = caracDto.getValorString();
             * }
             * if (ipMask.equals("") && caracDto.getTag().equalsIgnoreCase("IPMASK")) {
             * ipMask = caracDto.getValorString();
             * }
             * if (!ip.equals("") && !mac.equals("") && !ipMask.equals("")) {
             * break;
             * }
             * }
             */

            if (!ip.equals("")) {
                System.out.println("IP encontrado: " + ip);
                final boolean existeArquivo = true;
                // Item Configuração
                EventoItemConfigDTO eventoItemCfgDto = new EventoItemConfigDTO();
                eventoItemCfgDto.setIdEvento(idEvento);
                eventoItemCfgDto = (EventoItemConfigDTO) eventoItemCfgService.restore(eventoItemCfgDto);
                // Base Item Configuração
                BaseItemConfiguracaoDTO baseItemCfgDto = new BaseItemConfiguracaoDTO();
                baseItemCfgDto.setId(idBaseItemConfiguracao);
                baseItemCfgDto = (BaseItemConfiguracaoDTO) baseItemCfgService.restore(baseItemCfgDto);
                String dominio = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.DOMINIO_REDE, ".");
                // Não está associando valor DEFAULT, . significa domínio local
                if (dominio == null || dominio.isEmpty()) {
                    dominio = ".";
                }
                comando = eventoItemCfgDto.getUsuario() + " " + eventoItemCfgDto.getSenha() + " " + dominio;
                comandoCopia = comando;
                comandoDelete = comando;
                // Tipos de Evento
                // Instalação
                if (tipoExecucao.equalsIgnoreCase("I")) {
                    // Recupera o caminho da base de itens de configuração

                    caminho = ParametroUtil.getValorParametroCitSmartHashMap(
                            Enumerados.ParametroSistema.CaminhoBaseItemCfg, " ");

                    // Verifica se existe o arquivo no diretório
                    if (existeArquivo) {
                        if (!linhaComando.equals("")) {
                            // Recupera o sistema operacional utilizado
                            final String sO = eventoItemCfgService.pegarSistemaOperacionalItemConfiguracao(
                                    idItemConfiguracao).toUpperCase();
                            // Configura as linhas de comando a serem enviadas ao interpretador de comandos de acordo com o sistema
                            // operacional
                            if (sO.indexOf("WINDOWS") >= 0) {
                                // O argumento /C faz com que o iterpretador de comandos feche após a execução dos comandos
                                comando += " cmd.exe /C C:\\" + baseItemCfgDto.getExecutavel() + " " + linhaComando;
                                // Comando de cópia do instalador (argumento /y efetua a cópia sem pedir confirmação)
                                comandoCopia += " cmd.exe /C COPY \"\\\\" + caminho.replace("/", "\\").trim() + "\\"
                                        + baseItemCfgDto.getExecutavel() + "\" C:\\ /y";
                                // Comando de remoção do instalador
                                comandoDelete += " cmd.exe /C del C:\\" + baseItemCfgDto.getExecutavel();
                            } else { // Linux
                                String[] caminhoSplit = caminho.split("/");
                                if (caminhoSplit.length == 0) {
                                    caminhoSplit = caminho.split("\\");
                                }
                                // Componentes do caminho
                                // índice 0 - ip da máquina.
                                // índice 1 - pasta compartilhada.
                                // índices restantes - pastas a serem acessadas para achar a pasta de instalação
                                if (caminhoSplit.length < 2) {
                                    System.out.println("Erro com o parâmetro: CAMINHO_INSTALADORES");
                                    return;
                                }
                                String pastaSoftware = "";
                                for (int i = 2; i < caminhoSplit.length; i++) {
                                    pastaSoftware += caminhoSplit[i] + "/";
                                }
                                // Seta linha de comando digitada pelo usuário
                                comando = linhaComandoLinux.replace(" ", "##") + "##/tmp/"
                                        + baseItemCfgDto.getExecutavel() + "##" + linhaComando.replace(" ", "##");
                                // Seta comando de copia do executavel
                                comandoCopia = "-COMANDOCOPIA-mount -t cifs //" + caminhoSplit[0].trim() + "/"
                                        + caminhoSplit[1] + " /mnt -o user=" + eventoItemCfgDto.getUsuario()
                                        + ",password=" + eventoItemCfgDto.getSenha() + "##" + "cp-SEPARADOR-/mnt/"
                                        + pastaSoftware + baseItemCfgDto.getExecutavel() + "-SEPARADOR-/tmp/";
                                // Arquivos em /tmp são deletados ao desligar, basta desmontar unidade
                                comandoDelete = "umount##/mnt";
                            }
                        }
                    } else {
                        System.out.println("###############################");
                        System.out.println("Arquivo " + caminho + "\\" + baseItemCfgDto.getExecutavel()
                                + " não encontrado! ");
                        System.out.println("IP: " + ip + " Data: " + Util.getDataAtual());
                        System.out.println("###############################");
                        incluirHistoricoTentativa("Arquivo " + caminho + "\\" + baseItemCfgDto.getExecutavel()
                                + " não encontrado!");
                    }
                } else if (tipoExecucao.equalsIgnoreCase("D")) { // Desinstalação
                    final String sO = eventoItemCfgService.pegarSistemaOperacionalItemConfiguracao(idItemConfiguracao)
                            .toUpperCase();
                    if (sO.indexOf("WINDOWS") >= 0) { // Windows
                        comando += " cmd.exe /C \"" + baseItemCfgDto.getExecutavel() + "\" " + linhaComando;
                    } else {
                        // Seta linha de comando digitada pelo usuário
                        comando = linhaComandoLinux.replace(" ", "##") + "##" + baseItemCfgDto.getExecutavel() + "##"
                                + linhaComando.replace(" ", "##");
                    }
                }
                // Verifica a existência do executável
                if (existeArquivo) {
                    // Objeto que verifica o status, liga ou desliga o PC
                    final WakeOnLan wakeOnLan = new WakeOnLan(ip, ipMask, mac, 3, 50);
                    // Verifica se é para ligar o PC caso esteja desligado
                    if (eventoItemCfgDto.getLigarCasoDesl().equalsIgnoreCase("S")) {
                        if (wakeOnLan.pingar(ip)) {
                            System.out.println("O computador " + ip + " estava ligado!");
                            dispararEvento(ip, comando, comandoCopia, comandoDelete);
                        } else {
                            System.out.println("O computador " + ip + " não estava ligado!");
                            // Executa uma thread WakeOnLan para ligar o PC
                            wakeOnLan.start();
                            // Espera por 500 milisegundos até que o PC ligue
                            while (wakeOnLan.getControl().equalsIgnoreCase(WakeOnLan.NONE)) {
                                Thread.sleep(500);
                            }
                            // Verifica se o PC está ligado
                            if (wakeOnLan.getStatus()) {
                                System.out.println("O computador " + ip + " ligou!");
                                System.out.println("Aguardando subir o serviço do agente...");
                                Thread.sleep(120000);
                                dispararEvento(ip, comando, comandoCopia, comandoDelete);
                                desligarMaquina(ip);
                            } else {
                                System.out.println("###############################");
                                System.out.println("O computador " + ip + " não ligou!");
                                System.out.println("###############################");
                                incluirHistoricoTentativa("O computador " + ip + " não ligou!");
                            }
                        }
                    } else {
                        if (wakeOnLan.pingar(ip)) {
                            System.out.println("O computador " + ip + " estava ligado!");
                            dispararEvento(ip, comando, comandoCopia, comandoDelete);
                        } else {
                            System.out.println("###############################");
                            System.out.println("O computador " + ip + " estava desligado!");
                            System.out.println("###############################");
                            incluirHistoricoTentativa("O computador " + ip + " estava desligado!");
                        }
                    }
                    Thread.sleep(5000);
                }
            } else {
                System.out.println("###############################");
                System.out.println("IP do computador não foi encontrado!");
                System.out.println("###############################");
                incluirHistoricoTentativa("IP do computador não foi encontrado!");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Thread (Evento) finalizada!");
        }
    }

    private void dispararEvento(String ip, String comando, String comandoCopia, String comandoDelete) {
        String resposta = "";
        try {
            // Efetua cópia do arquivo de instalação
            if (tipoExecucao.equalsIgnoreCase("I")) {
                System.out.println("Iniciando cópia do arquivo de instalação...");
                resposta = executaComando(ip, comandoCopia, 60);
                if (resposta.equalsIgnoreCase("true")) {
                    System.out.println("Finalizando cópia do arquivo de instalação...");
                    Thread.sleep(5000);
                } else {
                    System.out.println("###############################");
                    System.out.println("Cópia do arquivo de instalação falhou!");
                    System.out.println("IP: " + ip + " Data: " + Util.getDataAtual());
                    System.out.println("###############################");
                    incluirHistoricoTentativa("Cópia do arquivo de instalação falhou!");
                    return;
                }
            }
            // Efetua execução específica do evento
            if (tipoExecucao.equalsIgnoreCase("I")) {
                System.out.println("Iniciando instalação do software...");
            } else {
                System.out.println("Iniciando desinstalação do software...");
            }
            resposta = executaComando(ip, comando, 300);
            if (resposta.equalsIgnoreCase("true")) {
                if (tipoExecucao.equalsIgnoreCase("I")) {
                    System.out.println("Finalizando instalação do software...");
                } else {
                    System.out.println("Finalizando desinstalação do software...");
                }
                Thread.sleep(20000);
            } else {
                System.out.println("###############################");
                System.out.println("Execução falhou!");
                System.out.println("IP: " + ip + " Data: " + Util.getDataAtual());
                System.out.println("###############################");
                incluirHistoricoTentativa("Execução falhou!");
            }
            // Efetua exclusão do arquivo de instalação
            if (tipoExecucao.equalsIgnoreCase("I")) {
                System.out.println("Iniciando exclusão do arquivo de instalação...");
                resposta = executaComando(ip, comandoDelete, 60);
                if (resposta.equalsIgnoreCase("true")) {
                    System.out.println("Finalizando exclusão do arquivo de instalação...");
                    Thread.sleep(5000);
                } else {
                    System.out.println("###############################");
                    System.out.println("Exclusão do arquivo de instalação falhou!");
                    System.out.println("IP: " + ip + " Data: " + Util.getDataAtual());
                    System.out.println("###############################");
                    incluirHistoricoTentativa("Exclusão do arquivo de instalação falhou!");
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private String executaComando(String ip, String comando, int tempo) {
        Socket s = null;
        ObjectOutputStream outObjects = null;
        String dadoRecebido = "";
        try {
            // Socket que escuta por requisições na porta 7000
            s = new Socket(ip, 7000);
            // Recuperando saída do socket
            outObjects = new ObjectOutputStream(s.getOutputStream());
            final List<String> parametrosEvento = new ArrayList<String>();
            parametrosEvento.add("EVENTO");
            parametrosEvento.add(comando);
            SignedInfo signedInfo = CriptoSignedUtil.generateStringToSend(CITCorporeUtil.CAMINHO_REAL_APP
                    + "/keysSec/citsmart.jks", CITCorporeUtil.CAMINHO_REAL_APP + "/keysSec/citsmartcripto.jks",
                    parametrosEvento.toString());
            outObjects.writeObject(signedInfo);
            outObjects.flush();
            ObjectInputStream ois = null;
            signedInfo = null;
            String dadoRecebidoAux = "";
            int cont = 1;
            boolean running = true;
            while (running) {
                try {
                    ois = new ObjectInputStream(s.getInputStream());
                    signedInfo = (SignedInfo) ois.readObject();
                } catch (final IOException e) {
                    System.out.println("###############################");
                    System.out.println("Algum problema ocorreu para receber dados do socket.");
                    System.out.println("Comunicação encerrada!");
                    System.out.println("IP: " + ip + " Data: " + Util.getDataAtual());
                    System.out.println("###############################");
                    e.printStackTrace();
                    break;
                }
                dadoRecebidoAux = CriptoSignedUtil.translateStringReceive(CITCorporeUtil.CAMINHO_REAL_APP
                        + "/keysSec/citsmart.jks", CITCorporeUtil.CAMINHO_REAL_APP + "/keysSec/citsmartcripto.jks",
                        signedInfo.getStrCripto(), signedInfo.getStrSigned());
                if (dadoRecebidoAux == null) {
                    dadoRecebidoAux = "";
                }
                dadoRecebido = new String(dadoRecebidoAux.getBytes());
                if (dadoRecebido != null) {
                    running = false;
                }
                if (cont == tempo) {
                    running = false;
                }
                Thread.sleep(1000);
                cont++;
            }
        } catch (final Exception e) {
            System.out.println("###############################");
            System.out.println("Algum problema ocorreu para receber dados do socket.");
            System.out.println("Comunicação encerrada!");
            System.out.println("IP: " + ip + " Data: " + Util.getDataAtual());
            System.out.println("###############################");
            e.printStackTrace();
        } finally {
            try {
                // Encerra o socket
                if (s != null) {
                    s.close();
                }
            } catch (final IOException e) {
                // Deixa passar!
            }
        }
        return dadoRecebido;
    }

    private void desligarMaquina(String ip) {
        // Socket servidor
        Socket s = null;
        // Stream de saida de dados
        ObjectOutputStream outObjects = null;
        try {
            System.out.println("Iniciando desligamento do computador...");
            // Cria o socket com o recurso desejado
            s = new Socket(ip, 7000);
            // Cria a Stream de saida de dados
            outObjects = new ObjectOutputStream(s.getOutputStream());
            // Imprime uma linha para a stream de saída de dados
            final SignedInfo signedInfo = CriptoSignedUtil.generateStringToSend(CITCorporeUtil.CAMINHO_REAL_APP
                    + "/keysSec/citsmart.jks", CITCorporeUtil.CAMINHO_REAL_APP + "/keysSec/citsmartcripto.jks",
                    "DESLIGAR");
            outObjects.writeObject(signedInfo);
            outObjects.flush();
        } catch (final Exception e) {
            System.out.println("###############################");
            System.out.println("Ocorreu um erro durante o desligamento do computador!");
            System.out.println("Comunicação encerrada!");
            System.out.println("IP: " + ip + " Data: " + Util.getDataAtual());
            System.out.println("###############################");
            e.printStackTrace();
        } finally {
            try {
                // Encerra o socket
                if (s != null) {
                    s.close();
                }
                System.out.println("O computador está sendo desligado!");
            } catch (final IOException e) {
                // Deixa passar!
            }
        }
    }

    private void incluirHistoricoTentativa(String descricao) throws ServiceException, Exception {
        // Cria um objeto para trafegar os dados relativos ao histórico de tentativas
        final HistoricoTentativaDTO historicoTentativaDto = new HistoricoTentativaDTO();
        historicoTentativaDto.setIdEvento(idEvento);
        historicoTentativaDto.setIdItemConfiguracao(idItemConfiguracao);
        historicoTentativaDto.setIdBaseItemConfiguracao(idBaseItemConfiguracao);
        historicoTentativaDto.setDescricao(descricao);
        historicoTentativaDto.setData(UtilDatas.getDataAtual());
        historicoTentativaDto.setHora(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()).replaceAll(":", ""));
        final HistoricoTentativaService histTentService = (HistoricoTentativaService) ServiceLocator.getInstance()
                .getService(HistoricoTentativaService.class, null);
        histTentService.create(historicoTentativaDto);
    }
}
