package br.com.centralit.citcorpore.tld;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoGrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoMenuService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author breno.guimaraes
 *
 */
@Deprecated
@SuppressWarnings({"unchecked"})
public class Menu extends BodyTagSupport {

    private static final long serialVersionUID = 5585162748054781636L;

    private static final Logger LOGGER = Logger.getLogger(Menu.class);

    private String rapido;

    private Integer qtdSub = 0;
    protected Boolean btnGrava;
    protected Boolean btnPesquisa;
    protected Boolean btnDeleta;

    private final String CAMINHO_PAGINAS = "/citsmart/pages";
    private final String CAMINHO_IMAGENS = "/citsmart/template_new/images/icons/small/grey/";
    private final String CAMINHO_IMAGENS_LARGE = "/citsmart/template_new/images/icons/large/grey/";

    private static final String INTERROGACAO = "?";

    @Override
    public int doStartTag() throws JspException {
        final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        final UsuarioDTO usrSession = WebUtil.getUsuario(request);
        final StringBuilder menu = new StringBuilder();
        String menuSessao = "";
        String menuRapido = "";
        String permissaoBotao = "";

        try {
            if (usrSession != null && usrSession.getStatus().equals("I")) {
                pageContext.getOut().println("<p style='color:#990000'>Usuário não cadastrado. Contate o administrador.</p>");
                return SKIP_BODY;
            }
            if (usrSession != null && usrSession.getIdPerfilAcessoUsuario() == null) {
                pageContext.getOut().println("<p style='color:#990000'>Usuário não cadastrado. Contate o administrador.</p>");
                return SKIP_BODY;
            }
            if (this.getRapido() == null) {
                this.setRapido("N");
            }
            if (this.getRapido().equalsIgnoreCase("S")) {
                menuRapido = (String) request.getSession(true).getAttribute("menuRapido");

                if (menuRapido != null && !StringUtils.isBlank(menuRapido)) {
                    menu.append(menuRapido);
                } else {
                    menuRapido = this.getMenuRapido(usrSession);
                    request.getSession(true).setAttribute("menuRapido", menuRapido);
                    menu.append(menuRapido);
                }

                permissaoBotao = this.validarPermissaoDeBotao(request, usrSession);

                menu.append(permissaoBotao);
            } else {
                menuSessao = (String) request.getSession(true).getAttribute("menu");

                if (menuSessao != null && !StringUtils.isBlank(menuSessao)) {
                    menu.append(menuSessao);
                } else {
                    menuSessao = this.getMenu(usrSession);
                    request.getSession(true).setAttribute("menu", menuSessao);
                    menu.append(menuSessao);
                }
            }

            pageContext.getOut().println(menu);
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    private String getMenu(final UsuarioDTO usrSession) throws Exception {
        final MenuDao menuDao = new MenuDao();
        final StringBuilder html = new StringBuilder();
        final Collection<MenuDTO> menusPai = menuDao.listarMenusPorPerfil(usrSession, null, false);
        if (menusPai != null) {
            html.append("<script>function chamaItemMenu(url){window.location = url;}</script>");
            html.append("<div id='tst' style='background: #D5DBDF; width:100%;'>");
            for (final MenuDTO menPai : menusPai) {
                if (menPai.getNome().trim().equalsIgnoreCase("$menu.nome.recursosHumanos")) {
                    final String mostrarGerenciaRecursosHumanos = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.MOSTRAR_GERENCIA_RECURSOS_HUMANOS, "N");
                    if (!mostrarGerenciaRecursosHumanos.trim().equalsIgnoreCase("S") || menPai.getMostrar() == null || !Boolean.parseBoolean(menPai.getMostrar())) {
                        continue;
                    }
                } else if (menPai.getNome().trim().equalsIgnoreCase("$menu.nome.compras")) {
                    final String mostrarCompras = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.MOSTRAR_COMPRAS, "N");
                    if (!mostrarCompras.trim().equalsIgnoreCase("S") || menPai.getMostrar() == null || !Boolean.parseBoolean(menPai.getMostrar())) {
                        continue;
                    }
                }
                html.append("<a href=\"javascript:void(0)\" id='itemMM");
                html.append(menPai.getIdMenu());
                html.append("' style='background:url(");
                html.append(CAMINHO_IMAGENS);
                html.append(menPai.getImagem());
                html.append(") no-repeat;' ");
                html.append("class=\"easyui-menubutton m-btn l-btn l-btn-plain\" data-options=\"menu:'#mm");
                html.append(menPai.getIdMenu());
                html.append("'");
                html.append("");
                html.append("\">");
                html.append("<span class=\"l-btn-left\"><span class=\"l-btn-text\">");
                html.append(this.internacionalizar(menPai.getNome()));
                html.append("</span><span class=\"m-btn-downarrow\">&nbsp;</span></span></a>\n");
            }
            html.append("</div>");
            this.gerarMenus(html, menusPai, usrSession, 0);
        }
        return html.toString();
    }

    private void gerarMenus(final StringBuilder sb, final Collection<MenuDTO> listaDeMenus, final UsuarioDTO usuario, final int indice) {
        String link;
        try {
            final MenuDao menuDao = new MenuDao();
            for (final MenuDTO submenu : listaDeMenus) {
                final Collection<MenuDTO> menusFilho = menuDao.listarMenusPorPerfil(usuario, submenu.getIdMenu(), false);
                if (menusFilho != null && !menusFilho.isEmpty()) {
                    qtdSub++;
                    String compl = "";
                    if (indice > 0) {
                        compl = "SUB";
                    }
                    String iconMenu = "";
                    if (submenu.getImagem() != null && !"".equalsIgnoreCase(submenu.getImagem().trim())) {
                        iconMenu = "iconCls:'icon-menu" + submenu.getIdMenu() + "'";
                    }
                    final String s = compl.equals("SUB") ? "" : "display:none;";
                    if (submenu.getNome().equals("$menu.esconder")) {
                        sb.append("<div style=\"display:none;\" id=\"mm");
                        sb.append(compl);
                        sb.append(submenu.getIdMenu());
                        sb.append("\" style=\"width:250px;");
                        sb.append(s);
                        sb.append("\" data-options=\"");
                        sb.append(iconMenu);
                        sb.append("\">\n");
                    } else {
                        sb.append("<div id=\"mm");
                        sb.append(compl);
                        sb.append(submenu.getIdMenu());
                        sb.append("\" style=\"width:250px;");
                        sb.append(s);
                        sb.append("\" data-options=\"");
                        sb.append(iconMenu);
                        sb.append("\">\n");
                    }
                    if (indice > 0) {
                        sb.append("<span>");
                        sb.append(this.internacionalizar(submenu.getNome()));
                        sb.append("</span>");
                        sb.append("<div style=\"width:250px;\">\n");
                    }
                    this.gerarMenus(sb, menusFilho, usuario, indice + 1);
                    if (indice > 0) {
                        sb.append("</div>\n");
                    }
                    sb.append("</div>\n");
                } else {
                    link = StringUtils.isBlank(submenu.getLink()) ? "#" : CAMINHO_PAGINAS + submenu.getLink();
                    String iconMenu = "";
                    if (StringUtils.isNotBlank(submenu.getImagem())) {
                        iconMenu = "iconCls:'icon-menu" + submenu.getIdMenu() + "'";
                    }
                    if (indice > 0) {
                        if (submenu.getNome().equals("$menu.esconder")) {
                            sb.append("<div style=\"display:none;\" id=\"mmSUB");
                            sb.append(submenu.getIdMenu());
                            sb.append("\" data-options=\"");
                            sb.append(iconMenu);
                            sb.append("\" onclick=\"chamaItemMenu('");
                            sb.append(link);
                            sb.append("')\">");
                            sb.append(this.internacionalizar(submenu.getNome()));
                            sb.append("</div>\n");
                        } else {
                            sb.append("<div id=\"mmSUB");
                            sb.append(submenu.getIdMenu());
                            sb.append("\" data-options=\"");
                            sb.append(iconMenu);
                            sb.append("\" onclick=\"chamaItemMenu('");
                            sb.append(link);
                            sb.append("')\">");
                            sb.append(this.internacionalizar(submenu.getNome()));
                            sb.append("</div>\n");
                        }
                    }
                }
            }
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    private boolean permiteAdicionarMenu() {
        boolean resultado = true;
        final String asteriskAtivo = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKATIVAR, "N");
        if (!asteriskAtivo.equals("S")) {
            resultado = false;
        }
        return resultado;
    }

    private String getMenuRapido(final UsuarioDTO usrSession) throws Exception {
        final MenuDao menuDao = new MenuDao();
        final StringBuilder html = new StringBuilder();
        final Collection<MenuDTO> menusRapido = menuDao.listarMenusPorPerfil(usrSession, null, true);
        if (this.permiteAdicionarMenu()) {
            html.append("<li class=\"li_menu tooltip_bottom\" title=\"Ramal onde se encontra o usuário\">");
            html.append("<img onclick=\"abreRamalTelefone();\" src=\"");
            html.append(CAMINHO_IMAGENS_LARGE);
            html.append("phone.png\">");
            html.append("</li>");
        }
        if (menusRapido != null) {
            for (final MenuDTO menRapido : menusRapido) {
                html.append("<a href=\"");
                html.append(CAMINHO_PAGINAS);
                html.append(menRapido.getLink());
                html.append("\"> ");
                html.append("<li class=\"li_menu tooltip_bottom\" title=\"");
                html.append(menRapido.getDescricao());
                html.append("\">");
                html.append("<img src=\"");
                html.append(CAMINHO_IMAGENS_LARGE);
                html.append(menRapido.getImagem());
                html.append("\">");
                html.append("</li>");
                html.append("</a>");
            }
            html.append("<a href=\"/cithelp/index.html\" target=\"blank\">");
            html.append("<li class=\"li_menu tooltip_bottom\" title=\" Help CITSmart\">");
            html.append("<img src=\"");
            html.append(CAMINHO_IMAGENS_LARGE);
            html.append("help.png\">");
            html.append("</li>");
            html.append("</a>");
            html.append("</ul>");
        }
        return html.toString();
    }

    public String validarPermissaoDeBotao(final HttpServletRequest request, UsuarioDTO usuario) throws Exception {
        final String validarPermissoesDeBotoes = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.VALIDAR_BOTOES, "N");
        final StringBuilder html = new StringBuilder();
        // Permissao total
        if (usuario != null && usuario.getLogin() != null && (usuario.getLogin().equalsIgnoreCase("admin") || usuario.getLogin().equalsIgnoreCase("consultor"))) {
            return "";
        }
        if (validarPermissoesDeBotoes.trim().equalsIgnoreCase("S")) {
            final MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
            final PerfilAcessoMenuService perfilAcessoMenuService = (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
            final UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
            final GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
            final PerfilAcessoGrupoService perfilAcessoGrupoService = (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);
            final PerfilAcessoMenuDTO perfilAcessoMenudto = new PerfilAcessoMenuDTO();
            PerfilAcessoGrupoDTO perfilAcessoGrupo = new PerfilAcessoGrupoDTO();
            final String pathInfo = this.getRequestedPath(request);
            String[] auxDinamic = {};
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
            final Integer idMenu = menuService.buscarIdMenu(url);
            html.append("<script> addEvent(window, \"load\", carregaPermissao, false); ");
            html.append("function carregaPermissao(){");
            if (idMenu != null) {
                if (usuario.getIdPerfilAcessoUsuario() != null) {
                    perfilAcessoMenudto.setIdPerfilAcesso(usuario.getIdPerfilAcessoUsuario());
                    perfilAcessoMenudto.setIdMenu(idMenu);
                    final Collection<PerfilAcessoMenuDTO> listaAcessoMenus = perfilAcessoMenuService.restoreMenusAcesso(perfilAcessoMenudto);
                    usuario = (UsuarioDTO) usuarioService.restore(usuario);
                    final Integer idEmpregado = usuario.getIdEmpregado();
                    final Collection<GrupoEmpregadoDTO> listaDeGrupoEmpregado = grupoEmpregadoService.findByIdEmpregado(idEmpregado);
                    if (listaDeGrupoEmpregado != null) {
                        for (final GrupoEmpregadoDTO grupoEmpregado : listaDeGrupoEmpregado) {
                            perfilAcessoGrupo.setIdGrupo(grupoEmpregado.getIdGrupo());
                            perfilAcessoGrupo = perfilAcessoGrupoService.listByIdGrupo(perfilAcessoGrupo);

                            if (perfilAcessoGrupo != null) {
                                perfilAcessoMenudto.setIdPerfilAcesso(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
                                perfilAcessoMenudto.setIdMenu(idMenu);
                                final Collection<PerfilAcessoMenuDTO> listaAcessoMenusGrupo = perfilAcessoMenuService.restoreMenusAcesso(perfilAcessoMenudto);
                                if (listaAcessoMenusGrupo != null) {
                                    for (final PerfilAcessoMenuDTO perfilAcessoMenu : listaAcessoMenusGrupo) {
                                        final PerfilAcessoMenuDTO perfil = new PerfilAcessoMenuDTO();
                                        perfil.setGrava(perfilAcessoMenu.getGrava());
                                        perfil.setPesquisa(perfilAcessoMenu.getPesquisa());
                                        perfil.setDeleta(perfilAcessoMenu.getDeleta());
                                        listaAcessoMenus.add(perfil);
                                    }
                                }
                            }

                        }
                    }
                    if (listaAcessoMenus != null) {
                        this.setBtnGrava(true);
                        html.append("$('#btnGravar').attr('class','light img_icon has_text disabledButtons'); ");
                        this.setBtnPesquisa(true);
                        html.append("$('#btnPesquisar').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only disabledButtons'); ");
                        html.append("$('#btnTodos').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only disabledButtons'); ");
                        this.setBtnDeleta(true);
                        html.append("$('#btnUpDate').attr('class','light img_icon has_text disabledButtons'); ");
                        html.append("$('#btnExcluir').attr('class','light img_icon has_text disabledButtons'); ");
                        for (final PerfilAcessoMenuDTO perfilAcesso : listaAcessoMenus) {
                            if (perfilAcesso.getGrava().equalsIgnoreCase("S")) {
                                html.append("$('#btnGravar').attr('class','light img_icon has_text'); ");
                                this.setBtnGrava(false);

                                html.append("$('#btnPesquisar').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
                                html.append("$('#btnTodos').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
                                this.setBtnPesquisa(false);
                            }
                            if (perfilAcesso.getPesquisa().equalsIgnoreCase("S")) {
                                html.append("$('#btnPesquisar').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
                                html.append("$('#btnTodos').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
                                this.setBtnPesquisa(false);
                            }
                            if (perfilAcesso.getDeleta().equalsIgnoreCase("S")) {
                                html.append("$('#btnUpDate').attr('class','light img_icon has_text'); ");
                                html.append("$('#btnExcluir').attr('class','light img_icon has_text'); ");
                                this.setBtnDeleta(false);

                                html.append("$('#btnPesquisar').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
                                html.append("$('#btnTodos').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
                                this.setBtnPesquisa(false);
                            }
                        }
                        html.append("$('#btnGravar').attr('disabled', " + this.getBtnGrava() + "); ");
                        html.append("$('#btnPesquisar').attr('disabled', " + this.getBtnPesquisa() + "); ");
                        html.append("$('#btnTodos').attr('disabled', " + this.getBtnPesquisa() + "); ");
                        html.append("$('#btnUpDate').attr('disabled', " + this.getBtnDeleta() + "); ");
                        html.append("$('#btnExcluir').attr('disabled', " + this.getBtnDeleta() + "); ");
                    }
                }
            }
            html.append("}");
            html.append("</script>");
        }
        return html.toString();
    }

    private String getRequestedPath(final HttpServletRequest request) {
        String path = request.getRequestURI() + request.getQueryString();
        path = path.substring(request.getContextPath().length());
        final int index = path.indexOf(INTERROGACAO);
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

    public String getRapido() {
        return rapido;
    }

    public void setRapido(final String rapido) {
        this.rapido = rapido;
    }

    public Boolean getBtnGrava() {
        return btnGrava;
    }

    public void setBtnGrava(final Boolean btnGrava) {
        this.btnGrava = btnGrava;
    }

    public Boolean getBtnPesquisa() {
        return btnPesquisa;
    }

    public void setBtnPesquisa(final Boolean btnPesquisa) {
        this.btnPesquisa = btnPesquisa;
    }

    public Boolean getBtnDeleta() {
        return btnDeleta;
    }

    public void setBtnDeleta(final Boolean btnDeleta) {
        this.btnDeleta = btnDeleta;
    }

    private String internacionalizar(final String valor) throws Exception {
        String sessaoLocale = UtilI18N.PORTUGUESE_SIGLA;
        if (pageContext.getSession().getAttribute("locale") != null) {
            sessaoLocale = pageContext.getSession().getAttribute("locale").toString();
        }
        return UtilI18N.internacionaliza(sessaoLocale, valor);
    }

}
