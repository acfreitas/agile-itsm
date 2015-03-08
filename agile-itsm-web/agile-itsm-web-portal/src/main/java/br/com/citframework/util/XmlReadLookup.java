package br.com.citframework.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import br.com.citframework.dto.ItemValorDescricaoDTO;

@SuppressWarnings({"rawtypes", "unchecked"})
public class XmlReadLookup {

    private static final Logger LOGGER = Logger.getLogger(XmlReadLookup.class);

    private static XmlReadLookup xmlReaderLookup = null;
    private Document doc = null;
    private HttpServletRequest request;
    private HashMap mapElementos = null;
    private static Properties props = null;
    private static String fileName = "";

    public static XmlReadLookup getInstance() {
        if (xmlReaderLookup == null) {
            xmlReaderLookup = new XmlReadLookup(XmlReadLookup.class.getResourceAsStream(Constantes.getValue("LOOKUP_FILE_CFG")));
        }
        return xmlReaderLookup;
    }

    public static XmlReadLookup getInstance(final Locale locale) {
        xmlReaderLookup = new XmlReadLookup(locale);
        return xmlReaderLookup;
    }

    public XmlReadLookup(final InputStream ioos) {
        mapElementos = new HashMap();
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(ioos);
            this.carregaLookups();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    public XmlReadLookup(final Locale locale) {
        mapElementos = new HashMap();
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(XmlReadLookup.class.getResourceAsStream(Constantes.getValue("LOOKUP_FILE_CFG")));
            this.carregaLookups(locale);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    public XmlReadLookup(final String file) {
        mapElementos = new HashMap();
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(file));
            this.carregaLookups();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    public LookupInfo getLookup(final String nome) {
        if (mapElementos != null) {
            if (mapElementos.containsKey(nome)) {
                final LookupInfo lookupInfoAux = (LookupInfo) mapElementos.get(nome);
                if (lookupInfoAux != null) {
                    return lookupInfoAux;
                }
            }
        } else {
            mapElementos = new HashMap();
        }
        mapElementos = new HashMap();
        final LookupInfo lookupInfo = new LookupInfo();
        Node noRoot = null;
        try {
            noRoot = doc.getChildNodes().item(0);
        } catch (final Exception e) {
            xmlReaderLookup = new XmlReadLookup(XmlReadLookup.class.getResourceAsStream(Constantes.getValue("LOOKUP_FILE_CFG")));
            try {
                noRoot = doc.getChildNodes().item(0);
            } catch (final Exception e2) {
                LOGGER.warn(e2.getMessage(), e2);
            }
        }
        String nomeAux = "";
        String tabelaAux = "";
        String daoAux = "";
        String whereAux = "";
        String scriptRefAux = "";
        String scriptAux = "";
        String separaCamposAux = "";
        boolean tudoEmBranco = true;
        try {
            if (noRoot != null) {
                for (int j = 0; j < noRoot.getChildNodes().getLength(); j++) {
                    final Node noLookup = noRoot.getChildNodes().item(j);
                    if (noLookup == null || noLookup.getNodeName() == null) {
                        continue;
                    }
                    if (noLookup.getNodeName().equals("#text")) {
                        continue;
                    }
                    tudoEmBranco = false;

                    final NamedNodeMap map = noLookup.getAttributes();
                    nomeAux = map.getNamedItem("name").getNodeValue();
                    tabelaAux = map.getNamedItem("tabela").getNodeValue();
                    daoAux = map.getNamedItem("daoProcessor").getNodeValue();
                    whereAux = map.getNamedItem("where").getNodeValue();
                    if (map.getNamedItem("separaCampos") == null) {
                        separaCamposAux = "N";
                    } else {
                        separaCamposAux = map.getNamedItem("separaCampos").getNodeValue();
                    }
                    scriptRefAux = "";
                    if (map.getNamedItem("scriptRef") != null) {
                        scriptRefAux = map.getNamedItem("scriptRef").getNodeValue();
                    }
                    scriptAux = "";
                    if (map.getNamedItem("script") != null) {
                        scriptAux = map.getNamedItem("script").getNodeValue();
                    }
                    if (nomeAux == null) {
                        nomeAux = "";
                    }
                    if (tabelaAux == null) {
                        tabelaAux = "";
                    }
                    if (daoAux == null) {
                        daoAux = "";
                    }
                    if (whereAux == null) {
                        whereAux = "";
                    }
                    if (nome.equalsIgnoreCase(nomeAux)) {
                        lookupInfo.setNome(nomeAux);
                        lookupInfo.setTabela(tabelaAux);
                        lookupInfo.setDaoProcessor(daoAux);
                        lookupInfo.setWhere(whereAux);
                        lookupInfo.setScriptRef(scriptRefAux);
                        lookupInfo.setScript(scriptAux);
                        lookupInfo.setSeparaCampos(separaCamposAux);
                        for (int i = 0; i < noLookup.getChildNodes().getLength(); i++) {
                            final Node noLookupItem = noLookup.getChildNodes().item(i);
                            if (noLookupItem.getNodeName().equals("#text")) {
                                continue;
                            }
                            if (noLookupItem.getNodeName().equalsIgnoreCase("camposPesquisa")) {
                                lookupInfo.setColCamposPesquisa(this.getCampos(noLookupItem));
                            } else if (noLookupItem.getNodeName().equalsIgnoreCase("camposRetorno")) {
                                lookupInfo.setColCamposRetorno(this.getCampos(noLookupItem));
                            } else if (noLookupItem.getNodeName().equalsIgnoreCase("camposOrdenacao")) {
                                lookupInfo.setColCamposOrdenacao(this.getCampos(noLookupItem));
                            } else if (noLookupItem.getNodeName().equalsIgnoreCase("camposChave")) {
                                lookupInfo.setColCamposChave(this.getCampos(noLookupItem));
                            }
                        }
                        mapElementos.put(nome, lookupInfo);
                    }
                }
            }
        } catch (final Exception e) {
            tudoEmBranco = true;
            LOGGER.warn("CITSMart - Problemas no processamento do xml do lookup: " + e.getMessage(), e);
        }
        if (tudoEmBranco) {
            xmlReaderLookup = new XmlReadLookup(XmlReadLookup.class.getResourceAsStream(Constantes.getValue("LOOKUP_FILE_CFG")));
        }
        return lookupInfo;
    }

    public synchronized void carregaLookups() {
        this.getProperties(null);
        Node noRoot = null;
        try {
            noRoot = doc.getChildNodes().item(0);
        } catch (final Exception e) {
            xmlReaderLookup = new XmlReadLookup(XmlReadLookup.class.getResourceAsStream(Constantes.getValue("LOOKUP_FILE_CFG")));
            try {
                noRoot = doc.getChildNodes().item(0);
            } catch (final Exception e2) {
                LOGGER.warn(e2.getMessage(), e2);
            }
        }
        String nomeAux = "";
        String tabelaAux = "";
        String daoAux = "";
        String whereAux = "";
        String scriptRefAux = "";
        String scriptAux = "";
        String separaCamposAux = "";
        try {
            if (noRoot != null) {
                for (int j = 0; j < noRoot.getChildNodes().getLength(); j++) {
                    try {
                        final LookupInfo lookupInfo = new LookupInfo();
                        final Node noLookup = noRoot.getChildNodes().item(j);
                        if (noLookup == null || noLookup.getNodeName() == null) {
                            continue;
                        }
                        if (noLookup.getNodeName().equals("#text")) {
                            continue;
                        }

                        final NamedNodeMap map = noLookup.getAttributes();
                        nomeAux = map.getNamedItem("name").getNodeValue();
                        tabelaAux = map.getNamedItem("tabela").getNodeValue();
                        daoAux = map.getNamedItem("daoProcessor").getNodeValue();
                        whereAux = map.getNamedItem("where").getNodeValue();
                        if (map.getNamedItem("separaCampos") == null) {
                            separaCamposAux = "N";
                        } else {
                            separaCamposAux = map.getNamedItem("separaCampos").getNodeValue();
                        }
                        scriptRefAux = "";
                        if (map.getNamedItem("scriptRef") != null) {
                            scriptRefAux = map.getNamedItem("scriptRef").getNodeValue();
                        }
                        scriptAux = "";
                        if (map.getNamedItem("script") != null) {
                            scriptAux = map.getNamedItem("script").getNodeValue();
                        }
                        if (nomeAux == null) {
                            nomeAux = "";
                        }
                        if (tabelaAux == null) {
                            tabelaAux = "";
                        }
                        if (daoAux == null) {
                            daoAux = "";
                        }
                        if (whereAux == null) {
                            whereAux = "";
                        }

                        lookupInfo.setNome(nomeAux);
                        lookupInfo.setTabela(tabelaAux);
                        lookupInfo.setDaoProcessor(daoAux);
                        lookupInfo.setWhere(whereAux);
                        lookupInfo.setScriptRef(scriptRefAux);
                        lookupInfo.setScript(scriptAux);
                        lookupInfo.setSeparaCampos(separaCamposAux);
                        for (int i = 0; i < noLookup.getChildNodes().getLength(); i++) {
                            final Node noLookupItem = noLookup.getChildNodes().item(i);
                            if (noLookupItem.getNodeName().equals("#text")) {
                                continue;
                            }
                            if (noLookupItem.getNodeName().equalsIgnoreCase("camposPesquisa")) {
                                lookupInfo.setColCamposPesquisa(this.getCampos(noLookupItem));
                            } else if (noLookupItem.getNodeName().equalsIgnoreCase("camposRetorno")) {
                                lookupInfo.setColCamposRetorno(this.getCampos(noLookupItem));
                            } else if (noLookupItem.getNodeName().equalsIgnoreCase("camposOrdenacao")) {
                                lookupInfo.setColCamposOrdenacao(this.getCampos(noLookupItem));
                            } else if (noLookupItem.getNodeName().equalsIgnoreCase("camposChave")) {
                                lookupInfo.setColCamposChave(this.getCampos(noLookupItem));
                            }
                        }
                        LOGGER.debug("CITSMart - Carregando lookup '" + nomeAux + "' em memoria!");
                        mapElementos.put(nomeAux, lookupInfo);
                    } catch (final Exception e) {
                        LOGGER.warn(e.getMessage(), e);
                    }
                }
            }
        } catch (final Exception e) {
            LOGGER.warn("CITSMart - PROBLEMAS NO PROCESSAMENTO DO XML DO LOOKUP: " + e.getMessage(), e);
        }
    }

    public synchronized void carregaLookups(final Locale locale) {
        this.getProperties(locale);
        Node noRoot = null;
        try {
            noRoot = doc.getChildNodes().item(0);
        } catch (final Exception e) {
            xmlReaderLookup = new XmlReadLookup(XmlReadLookup.class.getResourceAsStream(Constantes.getValue("LOOKUP_FILE_CFG")));
            try {
                noRoot = doc.getChildNodes().item(0);
            } catch (final Exception e2) {
                LOGGER.warn(e2.getMessage(), e2);
            }
        }
        String nomeAux = "";
        String tabelaAux = "";
        String daoAux = "";
        String whereAux = "";
        String scriptRefAux = "";
        String scriptAux = "";
        String separaCamposAux = "";
        try {
            if (noRoot != null) {
                for (int j = 0; j < noRoot.getChildNodes().getLength(); j++) {
                    try {
                        final LookupInfo lookupInfo = new LookupInfo();
                        final Node noLookup = noRoot.getChildNodes().item(j);
                        if (noLookup == null || noLookup.getNodeName() == null) {
                            continue;
                        }
                        if (noLookup.getNodeName().equals("#text")) {
                            continue;
                        }

                        final NamedNodeMap map = noLookup.getAttributes();
                        nomeAux = map.getNamedItem("name").getNodeValue();
                        tabelaAux = map.getNamedItem("tabela").getNodeValue();
                        daoAux = map.getNamedItem("daoProcessor").getNodeValue();
                        whereAux = map.getNamedItem("where").getNodeValue();
                        if (map.getNamedItem("separaCampos") == null) {
                            separaCamposAux = "N";
                        } else {
                            separaCamposAux = map.getNamedItem("separaCampos").getNodeValue();
                        }
                        scriptRefAux = "";
                        if (map.getNamedItem("scriptRef") != null) {
                            scriptRefAux = map.getNamedItem("scriptRef").getNodeValue();
                        }
                        scriptAux = "";
                        if (map.getNamedItem("script") != null) {
                            scriptAux = map.getNamedItem("script").getNodeValue();
                        }
                        if (nomeAux == null) {
                            nomeAux = "";
                        }
                        if (tabelaAux == null) {
                            tabelaAux = "";
                        }
                        if (daoAux == null) {
                            daoAux = "";
                        }
                        if (whereAux == null) {
                            whereAux = "";
                        }

                        lookupInfo.setNome(nomeAux);
                        lookupInfo.setTabela(tabelaAux);
                        lookupInfo.setDaoProcessor(daoAux);
                        lookupInfo.setWhere(whereAux);
                        lookupInfo.setScriptRef(scriptRefAux);
                        lookupInfo.setScript(scriptAux);
                        lookupInfo.setSeparaCampos(separaCamposAux);
                        for (int i = 0; i < noLookup.getChildNodes().getLength(); i++) {
                            final Node noLookupItem = noLookup.getChildNodes().item(i);
                            if (noLookupItem.getNodeName().equals("#text")) {
                                continue;
                            }
                            if (noLookupItem.getNodeName().equalsIgnoreCase("camposPesquisa")) {
                                lookupInfo.setColCamposPesquisa(this.getCampos(noLookupItem));
                            } else if (noLookupItem.getNodeName().equalsIgnoreCase("camposRetorno")) {
                                lookupInfo.setColCamposRetorno(this.getCampos(noLookupItem));
                            } else if (noLookupItem.getNodeName().equalsIgnoreCase("camposOrdenacao")) {
                                lookupInfo.setColCamposOrdenacao(this.getCampos(noLookupItem));
                            } else if (noLookupItem.getNodeName().equalsIgnoreCase("camposChave")) {
                                lookupInfo.setColCamposChave(this.getCampos(noLookupItem));
                            }
                        }
                        mapElementos.put(nomeAux, lookupInfo);
                    } catch (final Exception e) {
                        LOGGER.warn(e.getMessage(), e);
                    }
                }
            }
        } catch (final Exception e) {
            LOGGER.warn("CITSMart - Problemas no processamento do xml do lookup:" + e.getMessage(), e);
        }
    }

    private Collection getCampos(final Node noLookupItem) {
        final Collection colRetorno = new ArrayList();
        for (int i = 0; i < noLookupItem.getChildNodes().getLength(); i++) {
            final Node noCampo = noLookupItem.getChildNodes().item(i);
            if (noCampo.getNodeName().equals("#text")) {
                continue;
            }
            final Campo campo = this.getCampo(noCampo);
            colRetorno.add(campo);
        }
        return colRetorno;
    }

    private Campo getCampo(final Node noCampo) {
        final NamedNodeMap map = noCampo.getAttributes();
        try {
            final Campo campo = new Campo();
            campo.setNomeFisico(map.getNamedItem("nome").getNodeValue());

            /* Faz a leitura da chave */
            if (props.containsKey(map.getNamedItem("descricao").getNodeValue())) {
                campo.setDescricao(props.getProperty(map.getNamedItem("descricao").getNodeValue()));
            } else {
                campo.setDescricao(map.getNamedItem("descricao").getNodeValue());
            }

            campo.setType(map.getNamedItem("tipo").getNodeValue());
            final Node somenteBuscaNamedItem = map.getNamedItem("somenteBusca");
            if (somenteBuscaNamedItem != null) {
                final String somenteBusca = somenteBuscaNamedItem.getNodeValue();
                campo.setSomenteBusca("true".equalsIgnoreCase(somenteBusca));
            }
            final Node values = map.getNamedItem("values");
            Collection colValores = null;
            if (values != null) {
                final String valuesStr = values.getNodeValue();
                if (valuesStr != null) {
                    final String[] str = valuesStr.split(";");
                    if (str != null) {
                        for (final String element : str) {
                            String aux = element;
                            if (aux != null) {
                                aux = aux.replaceAll("\\{", "");
                                aux = aux.replaceAll("\\}", "");
                                final String[] str2 = aux.split("=");
                                if (str2 != null && str2.length > 1) {
                                    final ItemValorDescricaoDTO item = new ItemValorDescricaoDTO();
                                    item.setValor(str2[0]);
                                    item.setDescricao(str2[1]);
                                    if (colValores == null) {
                                        colValores = new ArrayList();
                                    }
                                    colValores.add(item);
                                }
                            }
                        }
                    }
                }
            }
            campo.setColValores(colValores);

            String auxStr = map.getNamedItem("tamanho").getNodeValue();
            if (auxStr == null) {
                auxStr = "";
            }
            final int tam = Integer.parseInt("0" + auxStr);
            campo.setTamanho(tam);
            //
            auxStr = map.getNamedItem("obrigatorio").getNodeValue();
            if (auxStr == null) {
                auxStr = "N";
            }
            if (auxStr.equalsIgnoreCase("")) {
                auxStr = "N";
            }
            if (auxStr.substring(0, 1).equalsIgnoreCase("S")) {
                campo.setObrigatorio(true);
            } else {
                campo.setObrigatorio(false);
            }
            auxStr = "";
            if (map.getNamedItem("scriptLostFocus") != null) {
                auxStr = map.getNamedItem("scriptLostFocus").getNodeValue();
            }
            campo.setScriptLostFocus(auxStr);
            auxStr = null;
            if (map.getNamedItem("mesmalinha") != null) {
                auxStr = map.getNamedItem("mesmalinha").getNodeValue();
            }
            campo.setMesmalinha(auxStr);
            return campo;
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @return the request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @param request
     *            the request to set
     */
    public void setRequest(final HttpServletRequest request) {
        this.request = request;
    }

    private Properties getProperties(final Locale locale) {
        try {
            if (locale != null && !locale.toString().trim().equals("") && !locale.toString().trim().equalsIgnoreCase(UtilI18N.PORTUGUESE_SIGLA)) {
                fileName = "Mensagens_" + locale.toString().trim() + ".properties";
            } else {
                fileName = "Mensagens.properties";
            }

            props = new Properties();
            final ClassLoader load = Mensagens.class.getClassLoader();
            InputStream is = load.getResourceAsStream(fileName);
            if (is == null) {
                is = ClassLoader.getSystemResourceAsStream(fileName);
            }
            if (is == null) {
                is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
            }

            try {
                if (is != null) {
                    props.load(is);
                }
            } catch (final IOException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        } catch (final SecurityException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return props;
    }

}
