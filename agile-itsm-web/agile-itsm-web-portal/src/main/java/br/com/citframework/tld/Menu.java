package br.com.citframework.tld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.citframework.dto.Usuario;
import br.com.citframework.menu.IRenderMenu;
import br.com.citframework.menu.MenuConfig;
import br.com.citframework.menu.MenuItem;
import br.com.citframework.security.Access;
import br.com.citframework.security.AccessConfig;
import br.com.citframework.util.Constantes;

public class Menu extends BodyTagSupport {

    private static final Logger LOGGER = Logger.getLogger(Menu.class);

    private static final String FORWARD_SLASH = "/";
    private static final long serialVersionUID = -3103179786470352866L;

    @Override
    public int doStartTag() throws JspException {
        MenuConfig menuConfig = null;
        try {
            menuConfig = MenuConfig.getInstance(pageContext.getServletContext());
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new JspException(e);
        }
        if (menuConfig == null) {
            throw new JspException("MenuConfig Esta Nulo !!!!!!!!!! Verificar!!!!!!!!!");
        }

        final Collection<MenuItem> colMenus = menuConfig.getMenuItens();
        final Usuario usuario = (Usuario) pageContext.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO"));
        LOGGER.debug("Usuario visualizando o menu\r\n" + usuario);
        if (colMenus != null) {
            LOGGER.debug("Quantidade de itens na raiz do menu antes da filtragem: " + colMenus.size());
        }

        if (colMenus != null) {
            LOGGER.debug("Quantidade de itens na raiz do menu depois da filtragem: " + colMenus.size());
        }

        if (colMenus != null) {
            try {
                String str = StringUtils.EMPTY;
                try {
                    final String classeMenu = Constantes.getValue("CLASSE_MENU");
                    if (classeMenu == null) {
                        LOGGER.debug("CLASSE_MENU: NAO DEFINIDO NO ARQUIVO DE CONSTANTES.PROPERTIES!");
                        LOGGER.debug("CLASSE_MENU: NAO DEFINIDO NO ARQUIVO DE CONSTANTES.PROPERTIES!");
                    } else {
                        final Class<?> classMenu = Class.forName(classeMenu);
                        if (classMenu == null) {
                            LOGGER.debug("CLASSE_MENU: CLASSE INEXISTENTE!");
                            LOGGER.debug("CLASSE_MENU: CLASSE INEXISTENTE!");
                        } else {
                            final Object objMenuObj = classMenu.newInstance();
                            final IRenderMenu objRenderMenu = (IRenderMenu) objMenuObj;
                            str = objRenderMenu.render(colMenus, ((HttpServletRequest) pageContext.getRequest()).getContextPath(), (HttpServletRequest) pageContext.getRequest(),
                                    (HttpServletResponse) pageContext.getResponse());
                        }
                    }
                } catch (final Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new JspException(e);
                }
                pageContext.getOut().print(str);
            } catch (final IOException e) {
                LOGGER.error(e.getMessage(), e);
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }

    public List<MenuItem> filtrarMenuUsuario(final Collection<MenuItem> menuItemCollection, final Usuario usuario) {
        if (menuItemCollection == null || menuItemCollection.size() == 0 || usuario == null) {
            return new ArrayList<>();
        }

        final List<MenuItem> itemList = new ArrayList<>();

        for (final MenuItem item : menuItemCollection) {
            String path = item.getPath();
            // if 'path' is blank, item is top level for some itens
            if (StringUtils.isBlank(path)) {
                Collection<MenuItem> menuItens = item.getMenuItens();
                if (menuItens != null && menuItens.size() != 0) {
                    menuItens = this.filtrarMenuUsuario(menuItens, usuario);
                    if (menuItens != null && menuItens.size() != 0) {
                        item.setMenuItens(menuItens);
                        itemList.add(item);
                    }
                }
            } else {
                path = FORWARD_SLASH + path;
                final Access access = AccessConfig.getAccess(path);
                if (access == null) {
                    LOGGER.debug("NO mapping for path: " + path);
                } else {
                    final String acessosUsuario = usuario.getAcessos();
                    if (access.hasAccess(acessosUsuario)) {
                        LOGGER.debug("User '" + usuario.getIdUsuario() + "' has access to: " + path);
                        itemList.add(item);
                    } else {
                        LOGGER.debug("User '" + usuario.getIdUsuario() + "' has NO access to: " + path);
                    }
                }
            }
        }
        return itemList;
    }

}
