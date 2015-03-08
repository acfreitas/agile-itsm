package br.com.centralit.citgerencial.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import br.com.centralit.citgerencial.bean.ParametroDTO;
import br.com.centralit.citgerencial.integracao.ParametroDao;
import br.com.citframework.dto.Usuario;
import br.com.citframework.util.Constantes;

public final class ParametrosConfig {

    private static final Logger LOGGER = Logger.getLogger(ParametrosConfig.class);

    private static ParametrosConfig SINGLETON;
    private static InputStream INPUT_STREAM_SETTED_IN_LOAD = null;

    private Document doc = null;
    private Collection<ItemConfiguracaoParametro> lstParametros;
    private Collection<GrupoConfiguracaoParametro> lstGruposParametros;

    public static ParametrosConfig getInstance() throws Exception {
        if (SINGLETON == null) {
            String fileNameConfig = "cfgParametros.xml";
            if (Constantes.getValue("PARAMETROS_CONFIG") != null && !Constantes.getValue("PARAMETROS_CONFIG").trim().equalsIgnoreCase("")) {
                fileNameConfig = Constantes.getValue("PARAMETROS_CONFIG");
            }
            InputStream parametrosConfigFile = ParametrosConfig.class.getClassLoader().getResourceAsStream(fileNameConfig);
            if (parametrosConfigFile == null) {
                parametrosConfigFile = ClassLoader.getSystemResourceAsStream(fileNameConfig);
                if (parametrosConfigFile == null) {
                    parametrosConfigFile = ClassLoader.getSystemClassLoader().getResourceAsStream(fileNameConfig);
                    if (parametrosConfigFile == null) {
                        try {
                            parametrosConfigFile = new FileInputStream(Constantes.getValue("CAMINHO_PARAMETROS_CONFIG") + fileNameConfig);
                        } catch (final Exception e) {
                            e.printStackTrace();
                            LOGGER.error(e);
                            // Se der errado, tenta por ultimo pegar informacoes do carregamento do sistema, se houver.
                            parametrosConfigFile = INPUT_STREAM_SETTED_IN_LOAD;
                        }
                    }
                }
            }
            LOGGER.info("PARAMETROS_CONFIG: " + fileNameConfig);
            SINGLETON = new ParametrosConfig(parametrosConfigFile, fileNameConfig);
        }
        return SINGLETON;
    }

    private ParametrosConfig(final InputStream ioos, final String fileNameConfig) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            if (ioos == null) {
                throw new Exception("ARQUIVO (PARAMETROS_CONFIG): " + fileNameConfig + " NAO ENCONTRADO!!!!!!!!!");
            }
            doc = builder.parse(ioos);
            this.load();
        } catch (final Exception e) {
            e.printStackTrace();
            doc = null;
        }
    }

    public void load() {
        if (doc == null) {
            return;
        }
        String name = "", description = "";
        lstParametros = new ArrayList<>();
        lstGruposParametros = new ArrayList<>();
        final Node noRoot = doc.getChildNodes().item(0);
        for (int j = 0; j < noRoot.getChildNodes().getLength(); j++) {
            final Node noItem = noRoot.getChildNodes().item(j);
            if (noItem.getNodeName().equals("#text")) {
                continue;
            }

            if (noItem.getNodeName().equalsIgnoreCase("grupo")) {
                final NamedNodeMap map = noItem.getAttributes();
                name = map.getNamedItem("name").getNodeValue();
                description = map.getNamedItem("description").getNodeValue();

                final GrupoConfiguracaoParametro grupoConfiguracaoParametro = new GrupoConfiguracaoParametro();
                grupoConfiguracaoParametro.setName(name);
                grupoConfiguracaoParametro.setDescription(description);

                lstGruposParametros.add(grupoConfiguracaoParametro);

                for (int i = 0; i < noItem.getChildNodes().getLength(); i++) {
                    final Node noItemParm = noItem.getChildNodes().item(i);
                    if (noItemParm.getNodeName().equals("#text")) {
                        continue;
                    }
                    if (noItemParm.getNodeName().equalsIgnoreCase("parametro")) {
                        final NamedNodeMap mapItem = noItemParm.getAttributes();

                        final ItemConfiguracaoParametro itemConfiguracaoParametro = new ItemConfiguracaoParametro();
                        itemConfiguracaoParametro.setGrupoName(name);
                        itemConfiguracaoParametro.setGrupoDescription(description);

                        final String modulo = mapItem.getNamedItem("modulo").getNodeValue();
                        final String nameParm = mapItem.getNamedItem("name").getNodeValue();
                        final String type = mapItem.getNamedItem("type").getNodeValue();
                        final String size = mapItem.getNamedItem("size").getNodeValue();
                        final String valorDefault = mapItem.getNamedItem("default").getNodeValue();
                        final String descriptionParm = mapItem.getNamedItem("description").getNodeValue();

                        itemConfiguracaoParametro.setModulo(modulo);
                        itemConfiguracaoParametro.setName(nameParm);
                        itemConfiguracaoParametro.setType(type);
                        itemConfiguracaoParametro.setSize(size);
                        itemConfiguracaoParametro.setValorDefault(valorDefault);
                        itemConfiguracaoParametro.setDescription(descriptionParm);

                        lstParametros.add(itemConfiguracaoParametro);

                        for (int z = 0; z < noItemParm.getChildNodes().getLength(); z++) {
                            final Node noItemCombo = noItemParm.getChildNodes().item(z);
                            if (noItemCombo.getNodeName().equals("#text")) {
                                continue;
                            }
                            if (noItemCombo.getNodeName().equalsIgnoreCase("item")) {
                                final NamedNodeMap mapItemCombo = noItemCombo.getAttributes();

                                final String value = mapItemCombo.getNamedItem("value").getNodeValue();
                                final String descriptionItem = mapItemCombo.getNamedItem("description").getNodeValue();

                                final ItemComboParametro item = new ItemComboParametro();
                                item.setValue(value);
                                item.setDescription(descriptionItem);

                                itemConfiguracaoParametro.getColItens().add(item);
                            }
                        }
                    }
                }
            }
        }
    }

    public Collection<ItemConfiguracaoParametro> getLstParametros() {
        return lstParametros;
    }

    public void setLstParametros(final Collection<ItemConfiguracaoParametro> lstParametros) {
        this.lstParametros = lstParametros;
    }

    public Collection<GrupoConfiguracaoParametro> getLstGruposParametros() {
        return lstGruposParametros;
    }

    public void setLstGruposParametros(final Collection<GrupoConfiguracaoParametro> lstGruposParametros) {
        this.lstGruposParametros = lstGruposParametros;
    }

    public String getValueStr(final HttpServletRequest request, final String moduloParm, final String nomeParametroParm) throws Exception {
        final Usuario usuario = (Usuario) request.getSession(true).getAttribute("USUARIO_SESSAO");
        Integer idEmpresaParm = null;
        try {
            idEmpresaParm = usuario.getIdEmpresa();
        } catch (final Exception e) {
            idEmpresaParm = new Integer(1);
        }

        final ParametroDao parametroDao = new ParametroDao();
        final ParametroDTO parametroDTO = parametroDao.getValue(moduloParm, nomeParametroParm, idEmpresaParm);
        if (parametroDTO == null) {
            return null;
        }
        return parametroDTO.getValor();
    }

    public String getValueStr(final Integer idEmpresa, final String moduloParm, final String nomeParametroParm) throws Exception {
        final ParametroDao parametroDao = new ParametroDao();
        final ParametroDTO parametroDTO = parametroDao.getValue(moduloParm, nomeParametroParm, idEmpresa);
        if (parametroDTO == null) {
            return null;
        }
        return parametroDTO.getValor();
    }

    public String getDetalhamentoStr(final HttpServletRequest request, final String moduloParm, final String nomeParametroParm) throws Exception {
        final Usuario usuario = (Usuario) request.getSession(true).getAttribute("USUARIO_SESSAO");
        Integer idEmpresaParm = null;
        try {
            idEmpresaParm = usuario.getIdEmpresa();
        } catch (final Exception e) {
            idEmpresaParm = new Integer(1);
        }
        final ParametroDao parametroDao = new ParametroDao();
        final ParametroDTO parametroDTO = parametroDao.getValue(moduloParm, nomeParametroParm, idEmpresaParm);
        if (parametroDTO == null) {
            return null;
        }

        return parametroDTO.getDetalhamento();
    }

}
