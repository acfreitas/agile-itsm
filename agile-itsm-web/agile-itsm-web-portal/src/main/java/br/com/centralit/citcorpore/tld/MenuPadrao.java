package br.com.centralit.citcorpore.tld;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

/**
 * Layout do novo menu
 *
 * @author flavio.santana
 * @since 30/07/2013
 */
public class MenuPadrao extends BodyTagSupport {

    private static final long serialVersionUID = 8322540014033475637L;

    private static final Logger LOGGER = Logger.getLogger(MenuPadrao.class);

    private boolean btnGravar;
    private boolean btnExcluir;
    private boolean btnPesquisar;

    private final String CAMINHO_PAGINAS = Constantes.getValue("CONTEXTO_APLICACAO") + "/pages";

    /**
     * Mapa com todos os menus que o usuário pode acessar
     * Map<idMenuPai, List<MenusFilhos> >
     *
     * @author thyen.chang
     * @since 26/01/2015 - OPERAÇÃO USAIN BOLT
     */
    private Map<Integer, List<MenuDTO>> mapaMenu;

    @Override
    public int doStartTag() throws JspException {
        final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        final UsuarioDTO usrSession = WebUtil.getUsuario(request);
        final StringBuilder sbMenu = new StringBuilder();
        String menuSessao = "";
        String permissaoBotao = "";

        try {
            final HttpSession session = request.getSession(true);
            menuSessao = (String) session.getAttribute("menuPadrao");
            if (menuSessao != null && !StringUtils.isBlank(menuSessao)) {
                sbMenu.append(menuSessao);
            } else {
                menuSessao = this.gerarMenuPrincipal(usrSession);
                session.setAttribute("menuPadrao", menuSessao);

                sbMenu.append(menuSessao);
            }

            permissaoBotao = this.validarPermissaoDeBotao(request, usrSession);
            session.setAttribute("permissaoBotao", permissaoBotao);

            pageContext.getOut().println(sbMenu);
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    /***
     * Gera o HTML do menu principal
     *
     * @since 26/01/2015 - OPERAÇÃO USAIN BOLT
     *        Adicionado método para pegar mapa com todos os menus que o usuário tem permissão de acessar
     * @author thyen.chang
     * @param usuario
     * @return
     * @throws Exception
     */
    private String gerarMenuPrincipal(final UsuarioDTO usuario) throws Exception {
        final StringBuilder html = new StringBuilder();
        html.append("<ul class='g-unit g-section g-tpl-nest'  id='nav' >");
        final Collection<MenuDTO> listaMenusPai = this.getMenuService().listarMenusPorPerfil(usuario, null, false);
        mapaMenu = this.getMenuService().listaMenuPorUsuario(usuario);
        if (listaMenusPai != null) {
            for (final MenuDTO menu : listaMenusPai) {
                html.append("<li class='g-unit'>");
                html.append("   <a href='javascript:;'>");
                html.append("<span>" + UtilI18N.internacionaliza((HttpServletRequest) pageContext.getRequest(), menu.getNome()) + "</span>");
                html.append("<i></i>");
                html.append("</a>");
                this.gerarSubMenu(html, menu.getIdMenu());
            }
        }
        html.append("</ul>");
        return html.toString();
    }

    /**
     * Gera o HTML dos subMenus
     *
     * @since 16/01/2014 - OPERAÇÃO USAIN BOLT
     *        Alterado método de busca de filhos para pegar do Mapa
     * @author thyen.chang
     * @param html
     * @param idMenu
     * @param usuario
     */
    private void gerarSubMenu(final StringBuilder html, final Integer idMenu) {
        try {
            final List<MenuDTO> listaSubMenus = this.getFilhos(idMenu);
            if (listaSubMenus != null && !listaSubMenus.isEmpty()) {
                html.append("<i></i>");
                html.append("<ul>");
                for (final MenuDTO submenu : listaSubMenus) {
                    html.append("<li>");
                    html.append("   <a href='" + (submenu.getLink() == null || submenu.getLink().equals("") ? "javascript:;" : CAMINHO_PAGINAS + submenu.getLink()));
                    html.append("'>");
                    html.append("");
                    html.append(UtilI18N.internacionaliza((HttpServletRequest) pageContext.getRequest(), submenu.getNome()));
                    html.append("</a>");
                    this.gerarSubMenu(html, submenu.getIdMenu());
                    html.append("</li>");
                }
                html.append("</ul>");
            }
            html.append("</li>");
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    /**
     * Retorna Lista com menus filhos do Mapa
     *
     * @author thyen.chang
     * @since 26/01/2015 - OPERAÇÃO USAIN BOLT
     * @param idMenu
     * @return
     */
    private List<MenuDTO> getFilhos(final Integer idMenu) {
        return mapaMenu.get(idMenu);
    }

    /**
     * Obtém as permissões do menu através de um mapa estático gravado na seção do usuário
     *
     * @author thyen.chang
     * @since 28/01/2015 - OPERAÇÃO USAIN BOLT
     * @param request
     * @param usuario
     * @return
     * @throws Exception
     */
    public String validarPermissaoDeBotao(final HttpServletRequest request, final UsuarioDTO usuario) throws Exception {
        final String validarPermissoesDeBotoes = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.VALIDAR_BOTOES, "N");
        final StringBuilder html = new StringBuilder();
        // Permissao total
        if (usuario != null && usuario.getLogin() != null && (usuario.getLogin().equalsIgnoreCase("admin") || usuario.getLogin().equalsIgnoreCase("consultor"))) {
            return "";
        }
        if (validarPermissoesDeBotoes.trim().equalsIgnoreCase("S")) {
            String[] auxDinamic = {};
            final String pathInfo = this.getRequestedPath(request);
            auxDinamic = pathInfo.split(".jsp");
            final String strForm = this.getObjectName(pathInfo);
            String url = "/" + strForm + "/" + strForm + ".load";
            try {
                if (!auxDinamic[1].equals("null")) {
                    url += "?" + auxDinamic[1];
                }
            } catch (final Exception e) {
                LOGGER.warn(e.getMessage(), e);
            }
            final MenuDao menuDao = new MenuDao();
            final Integer idMenu = menuDao.buscarIdMenu(url);

            if(idMenu != null){
	            
	            final Collection<PerfilAcessoMenuDTO> listaAcessoMenus = WebUtil.getPerfilAcessoUsuarioByMenu(request, usuario, idMenu);
	
	            if (listaAcessoMenus != null) {
	                html.append("<script type=\"text/javascript\"> $(window).load(function() { ");
	                btnGravar = false;
	                btnExcluir = false;
	                btnPesquisar = false;
	                for (final PerfilAcessoMenuDTO perfilAcesso : listaAcessoMenus) {
	                    if (perfilAcesso.getGrava().equalsIgnoreCase("S")) {
	                        btnGravar = true;
	                    }
	                    if (perfilAcesso.getPesquisa().equalsIgnoreCase("S")) {
	                        btnPesquisar = true;
	                    }
	                    if (perfilAcesso.getDeleta().equalsIgnoreCase("S")) {
	                        btnExcluir = true;
	                    }
	                }
	
	                if (!btnGravar) {
	                    html.append("$('#btnGravar').addClass('disabled'); ");
	                    html.append("$('#btnGravar').addClass('disabledButtons'); ");
	                    html.append("$('#btnGravar').attr('disabled', 'disabled'); ");
	                }
	
	                if (!btnExcluir) {
	                    html.append("$('#btnExcluir').addClass('disabled'); ");
	                    html.append("$('#btnExcluir').addClass('disabledButtons'); ");
	                    html.append("$('#btnExcluir').attr('disabled', 'disabled'); ");
	
	                    html.append("$('#btnUpDate').addClass('disabled'); ");
	                    html.append("$('#btnUpDate').addClass('disabledButtons'); ");
	                    html.append("$('#btnUpDate').attr('disabled', 'disabled'); ");
	                }
	
	                if (!btnPesquisar) {
	                    html.append("$('#btnPesquisar').addClass('disabled'); ");
	                    html.append("$('#btnPesquisar').addClass('disabledButtons'); ");
	                    html.append("$('#btnPesquisar').attr('disabled', 'disabled'); ");
	
	                    html.append("$('#campoPesquisa').attr('disabled', 'disabled'); ");
	
	                    html.append("$('#btnTodos').addClass('disabled'); ");
	                    html.append("$('#btnTodos').addClass('disabledButtons'); ");
	                    html.append("$('#btnTodos').attr('disabled', 'disabled'); ");
	
	                    request.getSession(true).setAttribute("habilitaPesquisa", "S");
	                }
	                html.append("});");
	                html.append("</script>");
	            }
            } else {
            	return "";
            }
        }
        return html.toString();
    }

    private String getRequestedPath(final HttpServletRequest request) {
        String path = request.getRequestURI() + request.getQueryString();
        path = path.substring(request.getContextPath().length());
        final int index = path.indexOf("?");
        if (index != -1) {
            path = path.substring(0, index);
        }
        return path;
    }

    public String getObjectName(final String path) {
        String strResult = "";
        boolean b = false;
        for (int i = path.length() - 1; i >= 0; i--) {
            if (b) {
                if (path.charAt(i) == '/') {
                    return strResult;
                } else {
                    strResult = path.charAt(i) + strResult;
                }
            } else {
                if (path.charAt(i) == '.') {
                    b = true;
                }
            }
        }
        return strResult;
    }

    private MenuService menuService;

    private MenuService getMenuService() throws ServiceException {
        if (menuService == null) {
            menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
        }
        return menuService;
    }

}
