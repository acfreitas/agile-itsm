package br.com.centralit.citcorpore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.batch.MonitoraAtivosDiscovery;
import br.com.centralit.citcorpore.batch.ThreadValidaFaixaIP;
import br.com.centralit.citcorpore.comm.server.IPAddress;
import br.com.centralit.citcorpore.metainfo.bean.ExternalClassDTO;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

public class CITCorporeUtil {

    private static final Logger LOGGER = Logger.getLogger(CITCorporeUtil.class);

    public static boolean DEBUG = false;

    public static List<ExternalClassDTO> LIST_EXTERNAL_CLASSES = null;
    public static Map<String, String> HSM_EXTERNAL_CLASSES = null;

    public static String CAMINHO_REAL_APP = "";
    public static String CAMINHO_REAL_CONFIG_FILE = "";
    public static String IDENTIFICACAO_CLIENTE = "GERAL";

    public static boolean START_MODE_DISCOVERY = true;
    public static boolean START_MODE_INVENTORY = true;
    public static boolean START_MODE_RULES = true;
    public static boolean START_MODE_ITSM = true;

    public static boolean START_INVENTARIO_ANTIGO = true;
    public static boolean START_MONITORA_NAGIOS = true;
    public static boolean START_MONITORA_DISCOVERY = true;
    public static boolean START_MONITORA_INCIDENTES = true;
    public static boolean START_VERIFICA_EVENTOS = true;

    /**
     * Define se o monitoramento de ativos será executado nessa instância. Esse valor será alterado conforme definição no arquivo citsmart.cfg.
     */
    public static boolean START_MONITORING_ASSETS = false;

    public static String IP_RANGE_DISCOVERY = "";
    public static String JDBC_ALIAS_INVENTORY = "";
    public static String JDBC_ALIAS_REPORTS = "";
    public static String JDBC_ALIAS_BPM = "";

    public static int QUANTIDADE_BACKUPLOGDADOS = 1000;

    public static String SGBD_PRINCIPAL = "";
    public static String CAMINHO_SCRIPTS = "/citsmart/scripts_deploy/";

    public static final String CAMINHO_SERVIDOR = Constantes.getValue("SERVER_ADDRESS");

    static {
        LOGGER.info("CITSMart - CITCorporeUtil.CAMINHO_SERVIDOR - " + CITCorporeUtil.CAMINHO_SERVIDOR);
    }

    /*
     * Inserido por Emauri - 23/11/2013
     */
    public static void fazLeituraArquivoConfiguracao() {
        if (CITCorporeUtil.CAMINHO_REAL_CONFIG_FILE == null) {
            CITCorporeUtil.CAMINHO_REAL_CONFIG_FILE = "";
        }
        final File fConf = new File(CITCorporeUtil.CAMINHO_REAL_CONFIG_FILE);
        final String pathConfigStartMode = CITCorporeUtil.CAMINHO_REAL_CONFIG_FILE;
        if (fConf.exists()) {
            LOGGER.info("CITSMart - Caminho da Config: " + pathConfigStartMode + " - Arquivo existe!!! Avaliando configuracoes!!!");
            final Properties propsCfg = new Properties();
            try {
                propsCfg.load(new FileInputStream(fConf));
                if (propsCfg != null) {
                    // --- Verificando START_MODE_INVENTORY
                    String strItemProp = propsCfg.getProperty("START_MODE_INVENTORY");
                    if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp
                                    .trim().equalsIgnoreCase("S"))) {
                        CITCorporeUtil.START_MODE_INVENTORY = true;
                    } else if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp
                                    .trim().equalsIgnoreCase("N"))) {
                        CITCorporeUtil.START_MODE_INVENTORY = false;
                    }
                    LOGGER.info("CITSMart - START_MODE_INVENTORY: " + strItemProp + " - (" + CITCorporeUtil.START_MODE_INVENTORY + ")");

                    // --- Verificando START_MODE_DISCOVERY
                    strItemProp = propsCfg.getProperty("START_MODE_DISCOVERY");
                    if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp
                                    .trim().equalsIgnoreCase("S"))) {
                        CITCorporeUtil.START_MODE_DISCOVERY = true;
                    } else if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp
                                    .trim().equalsIgnoreCase("N"))) {
                        CITCorporeUtil.START_MODE_DISCOVERY = false;
                    }
                    LOGGER.info("CITSMart - START_MODE_DISCOVERY: " + strItemProp + " - (" + CITCorporeUtil.START_MODE_DISCOVERY + ")");

                    // --- Verificando START_MODE_RULES
                    strItemProp = propsCfg.getProperty("START_MODE_RULES");
                    if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp
                                    .trim().equalsIgnoreCase("S"))) {
                        CITCorporeUtil.START_MODE_RULES = true;
                    } else if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp
                                    .trim().equalsIgnoreCase("N"))) {
                        CITCorporeUtil.START_MODE_RULES = false;
                    }
                    LOGGER.info("CITSMart - START_MODE_RULES: " + strItemProp + " - (" + CITCorporeUtil.START_MODE_RULES + ")");

                    // --- Verificando START_MODE_ITSM
                    strItemProp = propsCfg.getProperty("START_MODE_ITSM");
                    if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp
                                    .trim().equalsIgnoreCase("S"))) {
                        CITCorporeUtil.START_MODE_ITSM = true;
                    } else if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp
                                    .trim().equalsIgnoreCase("N"))) {
                        CITCorporeUtil.START_MODE_ITSM = false;
                    }
                    LOGGER.info("CITSMart - START_MODE_ITSM: " + strItemProp + " - (" + CITCorporeUtil.START_MODE_ITSM + ")");

                    // --- Verificando NATIVE_PING
                    strItemProp = propsCfg.getProperty("NATIVE_PING");
                    if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp
                                    .trim().equalsIgnoreCase("S"))) {
                        IPAddress.NATIVE_PING = true;
                    } else if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp
                                    .trim().equalsIgnoreCase("N"))) {
                        IPAddress.NATIVE_PING = false;
                    }
                    LOGGER.info("CITSMart - NATIVE_PING: " + strItemProp + " - (" + IPAddress.NATIVE_PING + ")");

                    // --- Verificando PING_TIMEOUT
                    strItemProp = propsCfg.getProperty("PING_TIMEOUT");
                    if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")) {
                        try {
                            int pingTimeOut = IPAddress.PING_TIMEOUT;
                            pingTimeOut = Integer.parseInt(strItemProp);
                            IPAddress.PING_TIMEOUT = pingTimeOut;
                        } catch (final Exception e) {}
                    }
                    LOGGER.info("CITSMart - PING_TIMEOUT: " + strItemProp + " - (" + IPAddress.PING_TIMEOUT + ")");

                    // --- Verificando NUM_THREADS_DISCOVERY
                    strItemProp = propsCfg.getProperty("NUM_THREADS_DISCOVERY");
                    if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")) {
                        try {
                            int numThreads = ThreadValidaFaixaIP.NUMERO_THREADS;
                            numThreads = Integer.parseInt(strItemProp);
                            ThreadValidaFaixaIP.NUMERO_THREADS = numThreads;
                        } catch (final Exception e) {}
                    }
                    LOGGER.info("CITSMart - NUM_THREADS_DISCOVERY: " + strItemProp + " - (" + ThreadValidaFaixaIP.NUMERO_THREADS + ")");

                    // --- Verificando NUM_THREADS_INVENTORY
                    strItemProp = propsCfg.getProperty("NUM_THREADS_INVENTORY");
                    if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")) {
                        try {
                            int numThreads = MonitoraAtivosDiscovery.NUMERO_THREADS;
                            numThreads = Integer.parseInt(strItemProp);
                            MonitoraAtivosDiscovery.NUMERO_THREADS = numThreads;
                        } catch (final Exception e) {}
                    }
                    LOGGER.info("CITSMart - NUM_THREADS_INVENTORY: " + strItemProp + " - (" + MonitoraAtivosDiscovery.NUMERO_THREADS + ")");

                    // --- Verificando JDBC_ALIAS_INVENTORY
                    strItemProp = propsCfg.getProperty("JDBC_ALIAS_INVENTORY");
                    if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")) {
                        try {
                            CITCorporeUtil.JDBC_ALIAS_INVENTORY = strItemProp.trim();
                        } catch (final Exception e) {}
                    }
                    LOGGER.info("CITSMart - JDBC_ALIAS_INVENTORY: " + strItemProp + " - (" + CITCorporeUtil.JDBC_ALIAS_INVENTORY + ")");

                    // --- Verificando JDBC_ALIAS_REPORTS
                    strItemProp = propsCfg.getProperty("JDBC_ALIAS_REPORTS");
                    if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")) {
                        try {
                            CITCorporeUtil.JDBC_ALIAS_REPORTS = strItemProp.trim();
                        } catch (final Exception e) {}
                    }
                    LOGGER.info("CITSMart - JDBC_ALIAS_REPORTS: " + strItemProp + " - (" + CITCorporeUtil.JDBC_ALIAS_REPORTS + ")");

                    // --- Verificando IP_RANGE_DISCOVERY
                    strItemProp = propsCfg.getProperty("IP_RANGE_DISCOVERY");
                    if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")) {
                        try {
                            CITCorporeUtil.IP_RANGE_DISCOVERY = strItemProp.trim();
                        } catch (final Exception e) {}
                    }
                    LOGGER.info("CITSMart - IP_RANGE_DISCOVERY: " + strItemProp + " - (" + CITCorporeUtil.IP_RANGE_DISCOVERY + ")");

                    // --- Verificando JDBC_ALIAS_BPM
                    strItemProp = propsCfg.getProperty("JDBC_ALIAS_BPM");
                    if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("")) {
                        try {
                            CITCorporeUtil.JDBC_ALIAS_BPM = strItemProp.trim();
                        } catch (final Exception e) {}
                    }
                    LOGGER.info("CITSMart - JDBC_ALIAS_BPM: " + strItemProp + " - (" + CITCorporeUtil.JDBC_ALIAS_BPM + ")");

                    // --- Verificando START_INVENTARIO_ANTIGO
                    strItemProp = propsCfg.getProperty("START_INVENTARIO_ANTIGO");
                    if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp
                                    .trim().equalsIgnoreCase("S"))) {
                        CITCorporeUtil.START_INVENTARIO_ANTIGO = true;
                    } else if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp
                                    .trim().equalsIgnoreCase("N"))) {
                        CITCorporeUtil.START_INVENTARIO_ANTIGO = false;
                    }
                    LOGGER.info("CITSMart - START_INVENTARIO_ANTIGO: " + strItemProp + " - (" + CITCorporeUtil.START_INVENTARIO_ANTIGO + ")");

                    // --- Verificando START_MONITORA_NAGIOS
                    strItemProp = propsCfg.getProperty("START_MONITORA_NAGIOS");
                    if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp
                                    .trim().equalsIgnoreCase("S"))) {
                        CITCorporeUtil.START_MONITORA_NAGIOS = true;
                    } else if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp
                                    .trim().equalsIgnoreCase("N"))) {
                        CITCorporeUtil.START_MONITORA_NAGIOS = false;
                    }
                    LOGGER.info("CITSMart - START_MONITORA_NAGIOS: " + strItemProp + " - (" + CITCorporeUtil.START_MONITORA_NAGIOS + ")");

                    // --- Verificando START_MONITORA_DISCOVERY
                    strItemProp = propsCfg.getProperty("START_MONITORA_DISCOVERY");
                    if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp
                                    .trim().equalsIgnoreCase("S"))) {
                        CITCorporeUtil.START_MONITORA_DISCOVERY = true;
                    } else if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp
                                    .trim().equalsIgnoreCase("N"))) {
                        CITCorporeUtil.START_MONITORA_DISCOVERY = false;
                    }
                    LOGGER.info("CITSMart - START_MONITORA_DISCOVERY: " + strItemProp + " - (" + CITCorporeUtil.START_MONITORA_DISCOVERY + ")");

                    // --- Verificando START_MONITORA_INCIDENTES
                    strItemProp = propsCfg.getProperty("START_MONITORA_INCIDENTES");
                    if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp
                                    .trim().equalsIgnoreCase("S"))) {
                        CITCorporeUtil.START_MONITORA_INCIDENTES = true;
                    } else if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp
                                    .trim().equalsIgnoreCase("N"))) {
                        CITCorporeUtil.START_MONITORA_INCIDENTES = false;
                    }
                    LOGGER.info("CITSMart - START_MONITORA_INCIDENTES: " + strItemProp + " - (" + CITCorporeUtil.START_MONITORA_INCIDENTES + ")");

                    // --- Verificando START_VERIFICA_EVENTOS
                    strItemProp = propsCfg.getProperty("START_VERIFICA_EVENTOS");
                    if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp
                                    .trim().equalsIgnoreCase("S"))) {
                        CITCorporeUtil.START_VERIFICA_EVENTOS = true;
                    } else if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp
                                    .trim().equalsIgnoreCase("N"))) {
                        CITCorporeUtil.START_VERIFICA_EVENTOS = false;
                    }
                    LOGGER.info("CITSMart - START_VERIFICA_EVENTOS: " + strItemProp + " - (" + CITCorporeUtil.START_VERIFICA_EVENTOS + ")");

                    // --- VERIFICANDO START_MONITORING_ASSETS
                    strItemProp = propsCfg.getProperty("START_MONITORING_ASSETS");
                    if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp
                                    .trim().equalsIgnoreCase("S"))) {
                        CITCorporeUtil.START_MONITORING_ASSETS = true;
                    } else if (strItemProp != null
                            && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp
                                    .trim().equalsIgnoreCase("N"))) {
                        CITCorporeUtil.START_MONITORING_ASSETS = false;
                    }
                    LOGGER.info("CITSMart - START_MONITORING_ASSETS: " + strItemProp + " - (" + CITCorporeUtil.START_MONITORING_ASSETS + ")");

                    // --- Quantidade de itens de dados por arquivo para backup logdados
                    strItemProp = propsCfg.getProperty("QUANTIDADE_BACKUPLOGDADOS");
                    if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")) {
                        try {
                            final int numeroItensBackupLog = Integer.parseInt(strItemProp);
                            CITCorporeUtil.QUANTIDADE_BACKUPLOGDADOS = numeroItensBackupLog;
                        } catch (final Exception e) {
                            LOGGER.warn(e.getMessage(), e);
                        }
                    }
                    LOGGER.info("CITSMart - QUANTIDADE_BACKUPLOGDADOS: " + strItemProp + " - (" + CITCorporeUtil.QUANTIDADE_BACKUPLOGDADOS + ")");
                }
            } catch (final IOException e) {
                LOGGER.warn("CITSMart - Problemas ao ler as propriedades do arquivo de configuracoes: " + e.getMessage(), e);
            }
        } else {
            LOGGER.info("CITSMart - CAMINHO DA CONFIG: " + pathConfigStartMode + " - ARQUIVO NAO EXISTE!!!!!!!!!!!!!!!!!!!! ASSUMINDO CONFIGURACOES PADRAO...");
            LOGGER.info("CITSMart - START_MODE_INVENTORY: '' - (" + CITCorporeUtil.START_MODE_INVENTORY + ")");
            LOGGER.info("CITSMart - START_MODE_DISCOVERY: ' - (" + CITCorporeUtil.START_MODE_DISCOVERY + ")");
            LOGGER.info("CITSMart - START_MODE_RULES: '' - (" + CITCorporeUtil.START_MODE_RULES + ")");
            LOGGER.info("CITSMart - START_MODE_ITSM: '' - (" + CITCorporeUtil.START_MODE_ITSM + ")");
            LOGGER.info("CITSMart - NATIVE_PING: '' - (" + IPAddress.NATIVE_PING + ")");
            LOGGER.info("CITSMart - PING_TIMEOUT: '' - (" + IPAddress.PING_TIMEOUT + ")");
            LOGGER.info("CITSMart - NUM_THREADS_DISCOVERY: '' - (" + ThreadValidaFaixaIP.NUMERO_THREADS + ")");
            LOGGER.info("CITSMart - NUM_THREADS_INVENTORY: '' - (" + MonitoraAtivosDiscovery.NUMERO_THREADS + ")");
            LOGGER.info("CITSMart - JDBC_ALIAS_INVENTORY: '' - (" + CITCorporeUtil.JDBC_ALIAS_INVENTORY + ")");
            LOGGER.info("CITSMart - JDBC_ALIAS_REPORTS: '' - (" + CITCorporeUtil.JDBC_ALIAS_REPORTS + ")");
            LOGGER.info("CITSMart - IP_RANGE_DISCOVERY: '' - (" + CITCorporeUtil.IP_RANGE_DISCOVERY + ")");
        }
    }

    public static String getNameFile(final String fullPathFile) {
        final int tam = fullPathFile.length() - 1;
        String nomeFile = "";
        for (int i = tam; i >= 0; i--) {
            if (fullPathFile.charAt(i) == '\\' || fullPathFile.charAt(i) == '/') {
                break;
            } else {
                nomeFile = fullPathFile.charAt(i) + nomeFile;
            }
        }
        nomeFile = UtilStrings.removeCaracteresEspeciais(nomeFile);
        nomeFile = UtilStrings.retiraEspacoPorUnderline(nomeFile);
        return nomeFile;
    }

    public static String getExtensao(final String nome) {
        if (nome == null) {
            return "";
        }
        String resultado = "";
        int i = nome.length();
        while (i >= 1) {
            if (nome.charAt(i - 1) == '.') {
                resultado = nome.substring(i, nome.length());
                i = -1;
            } else {
                i = i - 1;
            }
        }
        return resultado;
    }

    /**
     * Limpa formulário.
     *
     * @param document
     * @return HTMLForm
     * @author valdoilo.damasceno
     */
    public static HTMLForm limparFormulario(final DocumentHTML document) {
        final HTMLForm form = document.getForm("form");
        form.clear();
        return form;
    }

}
