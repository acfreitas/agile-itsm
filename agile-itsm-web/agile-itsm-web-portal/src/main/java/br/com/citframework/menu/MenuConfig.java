package br.com.citframework.menu;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import br.com.citframework.util.Constantes;

public class MenuConfig {

    private static final Logger LOGGER = Logger.getLogger(MenuConfig.class);
    private static MenuConfig singleton;
    private Document doc = null;
    private Collection<MenuItem> menuItens;

    public static MenuConfig getInstance(final ServletContext servletContext) throws Exception {
        if (singleton == null) {
            LOGGER.info("ServletContext: " + servletContext);
            InputStream menuFile = servletContext.getResourceAsStream(Constantes.getValue("MENU_FILE_CFG"));
            if (menuFile == null) {
                menuFile = servletContext.getResourceAsStream("/WEB-INF/menu-config.xml");
            }
            LOGGER.info("MENU_FILE_CFG: " + menuFile);
            singleton = new MenuConfig(menuFile);
        }
        return singleton;
    }

    public MenuConfig(final InputStream ioos) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            if (ioos == null) {
                throw new Exception("ARQUIVO: " + Constantes.getValue("MENU_FILE_CFG") + " NAO ENCONTRADO!!!!!!!!!");
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
        String description = "", path = "";
        menuItens = new ArrayList<>();
        MenuItem m;
        final Node noRoot = doc.getChildNodes().item(0);
        for (int j = 0; j < noRoot.getChildNodes().getLength(); j++) {
            final Node noMenu = noRoot.getChildNodes().item(j);
            if (noMenu.getNodeName().equals("#text")) {
                continue;
            }

            final NamedNodeMap map = noMenu.getAttributes();
            description = map.getNamedItem("description").getNodeValue();
            path = map.getNamedItem("path").getNodeValue();

            m = new MenuItem();
            m.setDescription(description);
            m.setPath(path);
            m.setRootLevel(true);
            m = this.getSubTree(m, noMenu);
            menuItens.add(m);
        }
    }

    public MenuItem getSubTree(MenuItem m, final Node noMenu) {
        if (noMenu == null) {
            return m;
        }
        String description = "", path = "";
        MenuItem menuTemp;
        for (int i = 0; i < noMenu.getChildNodes().getLength(); i++) {
            final Node noMenuItem = noMenu.getChildNodes().item(i);
            if (noMenuItem.getNodeName().equals("#text")) {
                continue;
            }
            if (noMenuItem.getNodeName().equalsIgnoreCase("menu-itens")) {
                m = this.getSubTree(m, noMenuItem);
            } else if (noMenuItem.getNodeName().equalsIgnoreCase("item-menu")) {
                final NamedNodeMap map = noMenuItem.getAttributes();
                description = map.getNamedItem("description").getNodeValue();
                path = map.getNamedItem("path").getNodeValue();

                menuTemp = new MenuItem();
                menuTemp.setDescription(description);
                menuTemp.setPath(path);
                if (m.getMenuItens() == null) {
                    m.setMenuItens(new ArrayList<MenuItem>());
                }
                m.getMenuItens().add(menuTemp);
                menuTemp = this.getSubTree(menuTemp, noMenuItem);
            }
        }
        return m;
    }

    public Collection<MenuItem> getMenuItens() {
        return menuItens;
    }

    public void setMenuItens(final Collection<MenuItem> menuItens) {
        this.menuItens = menuItens;
    }

}
