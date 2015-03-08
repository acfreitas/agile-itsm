package br.com.centralit.citgerencial.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import br.com.centralit.citgerencial.bean.GerencialPainelDTO;
import br.com.centralit.citgerencial.util.CITGerencialUtil;

public class GerencialGrupoPainelConfig {

    private static final Logger LOGGER = Logger.getLogger(GerencialGrupoPainelConfig.class);

    private Document doc = null;

    public static Collection<GerencialPainelDTO> getItensGrupo(final String fileNameConfig) throws Exception {
        LOGGER.info("GerencialGrupoPainelConfig File: " + fileNameConfig);
        final GerencialGrupoPainelConfig gerencialGrupoPainelConfig = new GerencialGrupoPainelConfig();

        InputStream iisGrupo = GerencialGrupoPainelConfig.class.getClassLoader().getResourceAsStream(fileNameConfig);
        if (iisGrupo == null) {
            iisGrupo = ClassLoader.getSystemResourceAsStream(fileNameConfig);
        }
        if (iisGrupo == null) {
            final String strPath = CITGerencialUtil.caminho_real_app + "WEB-INF/classes/" + fileNameConfig;
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(strPath);
            } catch (final Exception e) {}
            iisGrupo = fis;
        }
        if (iisGrupo == null) {
            iisGrupo = ClassLoader.getSystemResourceAsStream(fileNameConfig);
        }
        if (iisGrupo == null) {
            iisGrupo = ClassLoader.getSystemClassLoader().getResourceAsStream(fileNameConfig);
        }
        if (iisGrupo == null) {
            // Tenta pelo ClassLoader do Log4j. DESESPERO!
            iisGrupo = LOGGER.getClass().getClassLoader().getResourceAsStream(fileNameConfig);
        }

        return gerencialGrupoPainelConfig.getItensGrupoPainel(iisGrupo, fileNameConfig);
    }

    public Collection<GerencialPainelDTO> getItensGrupoPainel(final InputStream iisGrupo, final String fileNameConfig) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            if (iisGrupo == null) {
                throw new Exception("ARQUIVO (ITEM_INFORMACAO_CONFIG): " + fileNameConfig + " NAO ENCONTRADO!!!!!!!!!");
            }
            doc = builder.parse(iisGrupo);
            return this.load();
        } catch (final Exception e) {
            e.printStackTrace();
            doc = null;
            return null;
        }
    }

    public Collection<GerencialPainelDTO> load() {
        if (doc == null) {
            return null;
        }
        GerencialPainelDTO gerencialPainelDto = null;

        final Collection<GerencialPainelDTO> colItens = new ArrayList<>();

        final Node noRoot = doc.getChildNodes().item(0);
        if (noRoot != null) {
            for (int i = 0; i < noRoot.getChildNodes().getLength(); i++) {
                final Node noSubItem = noRoot.getChildNodes().item(i);
                if (noSubItem.getNodeName().equals("#text")) {
                    continue;
                }
                if (noSubItem.getNodeName().equals("#comment")) {
                    continue;
                }

                final NamedNodeMap map = noSubItem.getAttributes();

                gerencialPainelDto = new GerencialPainelDTO();
                gerencialPainelDto.setDescription(map.getNamedItem("description").getNodeValue());
                gerencialPainelDto.setFileName(map.getNamedItem("file").getNodeValue());

                colItens.add(gerencialPainelDto);
            }
        }

        return colItens;
    }

}
