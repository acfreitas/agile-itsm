package br.com.centralit.citcorpore.comm.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.centralit.citcorpore.bean.InventarioXMLDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.negocio.InventarioXMLService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.NetMapService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.cripto.CriptoSignedUtil;
import br.com.citframework.util.cripto.SignedInfo;

public class Servidor implements Runnable, ClientServer {

    static List<NetMapDTO> listnetMap = null;
    static ParametroCorporeDTO paramentroDTO = null;
    private String diretorioXmlAgente = "";
    private NetMapDTO netMapDTO;
    static List<NetMapDTO> listNetMapErro = new ArrayList<NetMapDTO>();

    public Servidor() {}

    public Servidor(final NetMapDTO netMapDTO) {
        this.netMapDTO = netMapDTO;
        try {
            diretorioXmlAgente = ParametroUtil.getValorParametroCitSmartHashMap(
                    Enumerados.ParametroSistema.DiretorioXmlAgente, " ");
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public List<NetMapDTO> carregarListaIP(final List<NetMapDTO> listNetMapDTO) throws InterruptedException {
        listnetMap = listNetMapDTO;
        listNetMapErro = new ArrayList<NetMapDTO>();
        for (final NetMapDTO netMapDTO : listNetMapDTO) {
            final Thread server = new Thread(new Servidor(netMapDTO));
            server.start();
            server.join();
        }
        return listNetMapErro;
    }

    public List<NetMapDTO> carregarIP(final NetMapDTO listNetMapDTO) throws InterruptedException {
        listnetMap = new ArrayList<NetMapDTO>();
        listNetMapErro = new ArrayList<NetMapDTO>();
        final Thread server = new Thread(new Servidor(netMapDTO));
        server.start();
        server.join();
        return listNetMapErro;
    }

    public static void executarPesquisaIPGerarInvenario() throws ServiceException, Exception {
        final NetMapService netMapService = serviceNetMapService();
        Integer dias = 0;
        final String diasInventario = ParametroUtil.getValorParametroCitSmartHashMap(
                Enumerados.ParametroSistema.DiasInventario, " ");

        try {
            dias = Integer.parseInt(diasInventario);
        } catch (final NumberFormatException ex) {
            System.err.println(ex);
        }

        final Date dataInventario = UtilDatas.getSqlDate(UtilDatas.incrementaDiasEmData(Util.getDataAtual(),
                -new Integer(dias)));
        // Pesquisa ips para geracao de iventario, passa data como
        // parametro, para identificar apartir de que data será iventariado.
        listnetMap = netMapService.listIpByDataInventario(dataInventario);
        for (final NetMapDTO netMapDTO : listnetMap) {
            final Thread server = new Thread(new Servidor(netMapDTO));
            server.start();
            server.join();
        }
    }

    private static NetMapService serviceNetMapService() throws ServiceException, Exception {
        final NetMapService netMapService = (NetMapService) ServiceLocator.getInstance().getService(
                NetMapService.class, null);
        return netMapService;
    }

    @Override
    public void run() {
        try {
            // Declaro o socket cliente
            Socket s = null;

            // Declaro a Stream de saida de dados
            ObjectOutputStream outObjects = null;
            try {
                // Cria o socket com o recurso desejado
                System.out.println("Conectando a " + netMapDTO.getIp() + "...	");
                s = new Socket(netMapDTO.getIp(), SERVER_PORT);
                s.setSoTimeout(90000);
                // Cria a Stream de saida de dados
                outObjects = new ObjectOutputStream(s.getOutputStream());

                final List<String> parametrosEvento = new ArrayList<String>();
                parametrosEvento.add("INVENTORY");

                // Imprime uma linha para a stream de saída de dados
                SignedInfo signedInfo = CriptoSignedUtil.generateStringToSend(CITCorporeUtil.CAMINHO_REAL_APP
                        + "/keysSec/citsmart.jks", CITCorporeUtil.CAMINHO_REAL_APP + "/keysSec/citsmartcripto.jks",
                        parametrosEvento.toString());

                outObjects.writeObject(signedInfo);
                outObjects.flush();
                boolean running = true;
                while (running) {
                    String dadoRecebido = "";
                    try {
                        System.out.println("Recebendo dados do Socket..");
                        final ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                        signedInfo = (SignedInfo) ois.readObject();
                    } catch (final IOException e) {
                        System.out.println("Problema ao receber dados pelo socket: " + e);
                        e.printStackTrace();
                        listNetMapErro.add(netMapDTO);
                        running = false;
                        break;
                    }
                    String dadoRecebidoAux = "";
                    try {
                        dadoRecebidoAux = CriptoSignedUtil.translateStringReceive(CITCorporeUtil.CAMINHO_REAL_APP
                                + "/keysSec/citsmart.jks", CITCorporeUtil.CAMINHO_REAL_APP
                                + "/keysSec/citsmartcripto.jks", signedInfo.getStrCripto(), signedInfo.getStrSigned());
                    } catch (final Exception e) {
                        System.out.println("Problema ao descriptografar o texto de inventario: " + e);
                        e.printStackTrace();
                    }
                    if (dadoRecebidoAux.equals("") || dadoRecebidoAux.equals("false")) {
                        dadoRecebidoAux = null;
                    }
                    dadoRecebido = dadoRecebidoAux != null ? new String(dadoRecebidoAux.getBytes()) : null;
                    if (dadoRecebido != null) {
                        try {
                            getGravarItemConfiguracao(dadoRecebido);
                            getGravarInventarioXml(dadoRecebido);
                        } catch (final Exception e) {
                            System.out.println("Problema ao gravar o inventario: " + e);
                            e.printStackTrace();
                            listNetMapErro.add(netMapDTO);
                            final BufferedWriter br = new BufferedWriter(new FileWriter(new File(diretorioXmlAgente
                                    + getNomeArquivo() + ".xml")));
                            br.write(dadoRecebido);
                            br.close();
                            running = false;
                        }
                        running = false;
                    } else {
                        running = false;
                        listNetMapErro.add(netMapDTO);
                        System.out.println("Mensagem não pode ser lida..");
                    }
                }
            } catch (final ConnectException e) {
                System.out.println("Conexão recusada..");
                listNetMapErro.add(netMapDTO);
            } finally {
                try {
                    // Encerra o ServerSocket
                    if (s != null && !s.isClosed()) {
                        s.close();
                    }
                } catch (final IOException e) {
                    System.out.println("Problema ao encerrar o socket: " + e);
                    e.printStackTrace();
                }
            }
        } catch (final Exception e) {
            System.out.println("Problema ao gerar inventario manualmente: " + e);
            e.printStackTrace();
        }
    }

    private void getGravarInventarioXml(final String dadoRecebido) {
        try {
            InventarioXMLDTO inventarioXMLDTO = new InventarioXMLDTO();
            final InventarioXMLService inventarioXmlService = (InventarioXMLService) ServiceLocator.getInstance()
                    .getService(InventarioXMLService.class, null);
            inventarioXMLDTO.setIdNetMap(netMapDTO.getIdNetMap());
            inventarioXMLDTO.setConteudo(dadoRecebido);
            inventarioXMLDTO.setDatainicial(UtilDatas.getDataAtual());
            inventarioXMLDTO.setNome(getNomeArquivo());
            // Grava Xml Completo.
            inventarioXMLDTO = inventarioXmlService.create(inventarioXMLDTO);
        } catch (final Exception e) {
            System.out.println("Problema ao gravar o inventario: " + e);
            e.printStackTrace();
        }
    }

    public void getGravarItemConfiguracao(final String dadoRecebido) throws ServiceException, LogicException, Exception {
        final XmlReadDtdAgente xmlReadDtdAgente = new XmlReadDtdAgente();
        final List<ItemConfiguracaoDTO> list = xmlReadDtdAgente.XmlReadDtdAgent(dadoRecebido);
        final ItemConfiguracaoService serviceItem = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(
                ItemConfiguracaoService.class, null);
        for (final ItemConfiguracaoDTO itemConfiguracaoDTO : list) {
            serviceItem.createItemConfiguracao(itemConfiguracaoDTO, null);
        }
    }

    private String getNomeArquivo() {
        String nomeArquivo = netMapDTO.getIp() + "__" + netMapDTO.getMac() + "__" + UtilDatas.getDataAtual() + "__"
                + UtilDatas.getHoraAtual();
        nomeArquivo = nomeArquivo.replace(".", "-");
        nomeArquivo = nomeArquivo.replace(":", "-");
        nomeArquivo = nomeArquivo.replace("-", "-");
        return nomeArquivo;
    }

}
