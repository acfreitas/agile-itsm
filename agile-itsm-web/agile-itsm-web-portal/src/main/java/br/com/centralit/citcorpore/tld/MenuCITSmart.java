package br.com.centralit.citcorpore.tld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.MenuServiceEjb;
import br.com.centralit.citcorpore.negocio.PerfilAcessoGrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoMenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoUsuarioServiceEjb;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.negocio.UsuarioServiceEjb;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

/**
 * @author breno.guimaraes
 *
 */
@SuppressWarnings({"unused", "unchecked"})
public class MenuCITSmart extends BodyTagSupport {

    private static final long serialVersionUID = 1L;
    private String orientation;
    private MenuService menuService;
    private static final String INTERROGACAO = "?";
    private final String CAMINHO_PAGINAS = Constantes.getValue("CONTEXTO_APLICACAO") + "/pages";
    private final String CAMINHO_IMAGENS = "/citsmart/template_new/images/icons/small/grey/";
    private final String CAMINHO_IMAGENS_LARGE = "/citsmart/template_new/images/icons/large/grey/";
    private UsuarioDTO usuario;
    private Integer qtdSub = 0;

    protected Boolean btnGrava;
    protected Boolean btnPesquisa;
    protected Boolean btnDeleta;

    private UsuarioDTO getAtualizacoesUsuario(String login) {
        UsuarioDTO retorno = null;
        UsuarioServiceEjb usuarioService = new UsuarioServiceEjb();
        PerfilAcessoUsuarioServiceEjb perfilAcessoUsuario = new PerfilAcessoUsuarioServiceEjb();

        PerfilAcessoUsuarioDTO perfilAcessoDTO = new PerfilAcessoUsuarioDTO();
        perfilAcessoDTO.setIdUsuario(usuario.getIdUsuario());

        try {
            retorno = usuarioService.restoreByLogin(login);
            perfilAcessoDTO = perfilAcessoUsuario.listByIdUsuario(perfilAcessoDTO);
            retorno.setIdPerfilAcessoUsuario(perfilAcessoDTO.getIdPerfilAcesso());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public int doStartTag() throws JspException {
        usuario = WebUtil.getUsuario((HttpServletRequest) pageContext.getRequest());
        if (usuario == null) {
            return SKIP_BODY;
        }
        if (usuario != null) {
            usuario = getAtualizacoesUsuario(usuario.getLogin());
        }

        if (usuario != null && usuario.getStatus().equals("I")) {
            try {
                pageContext.getOut().println("<p style='color:#990000'>Usuário não cadastrado. Contate o administrador.</p>");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return SKIP_BODY;
        }

        if (usuario != null && usuario.getIdPerfilAcessoUsuario() == null) {
            try {
                pageContext.getOut().println("<p style='color:#990000'>Sem perfil de acesso. Contate o administrador.</p>");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return SKIP_BODY;
        }
        this.menuService = new MenuServiceEjb();

        String html = "";
        if (getOrientation().equals("VERTICAL")) {
            try {
                html = renderizacaoVertical();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                html = renderizacaoHorizontal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            pageContext.getOut().println(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    /**
     * Monta o HTML retornado pela taglib.
     *
     * @return
     * @throws Exception
     */
    public String renderizacaoVertical() throws Exception {
        sessionMenu((HttpServletRequest) pageContext.getRequest());
        StringBuilder html = new StringBuilder();
        html.append("<script>function chamaItemMenu(url){window.location = url;}</script>");
        ArrayList<MenuDTO> menus = (ArrayList<MenuDTO>) menuService.listarMenusPorPerfil(usuario, null, false);
        html.append("<div id='tst' style='background: #D5DBDF; width:100%;'>");
        for (MenuDTO submenu : menus) {
            String iconMenu = "";
            html.append("<a href=\"javascript:void(0)\" id='itemMM" + submenu.getIdMenu() + "' style='background:url(" + CAMINHO_IMAGENS + submenu.getImagem()
                    + ") no-repeat;' class=\"easyui-menubutton\" data-options=\"menu:'#mm" + submenu.getIdMenu() + "'" + iconMenu + "\">" + submenu.getNome() + "</a>\n");
        }
        html.append("</div>");
        gerarMenus(html, menus, usuario, 0);
        return html.toString();
    }

    public void sessionMenu(HttpServletRequest request) throws Exception {
        ArrayList<MenuDTO> menus = (ArrayList<MenuDTO>) menuService.listarMenusPorPerfil(usuario, null, false);
        request.getSession(true).setAttribute("sessionMenu", menus);
    }

    /**
     * Incrementa a String do HTML para menu VERTICAL através da referência.
     *
     * @param sb
     *            String com o HTML que esta sendo montado.
     * @param listaDeMenus
     *            Lista de Menus que deve ser convertida em HTML.
     * @param usuario
     *            Usuário com as permissões de acesso aos menus.
     */
    private void gerarMenus(StringBuilder sb, Collection<MenuDTO> listaDeMenus, UsuarioDTO usuario, int indice) {
        String link;
        try {
            for (MenuDTO submenu : listaDeMenus) {
                Collection<MenuDTO> novaListaSubMenus;
                novaListaSubMenus = this.menuService.listarMenusPorPerfil(usuario, submenu.getIdMenu(), false);
                if (novaListaSubMenus != null && !novaListaSubMenus.isEmpty()) {
                    this.qtdSub++;
                    String compl = "";
                    if (indice > 0) {
                        compl = "SUB";
                    }
                    String iconMenu = "";
                    if (submenu.getImagem() != null && !"".equalsIgnoreCase(submenu.getImagem().trim())) {
                        iconMenu = "iconCls:'icon-menu" + submenu.getIdMenu() + "'";
                    }
                    sb.append("<div id=\"mm" + compl + submenu.getIdMenu() + "\" style=\"width:250px;\" data-options=\"" + iconMenu + "\">\n");
                    if (indice > 0) {
                        sb.append("<span>" + submenu.getNome() + "</span>");
                        sb.append("<div style=\"width:250px;\">\n");
                    }
                    this.gerarMenus(sb, novaListaSubMenus, usuario, indice + 1);
                    if (indice > 0) {
                        sb.append("</div>\n");
                    }
                    sb.append("</div>\n");
                } else {
                    link = submenu.getLink() == null || submenu.getLink().trim().equals("") ? "#" : CAMINHO_PAGINAS + submenu.getLink();
                    String iconMenu = "";
                    if (submenu.getImagem() != null && !"".equalsIgnoreCase(submenu.getImagem().trim())) {
                        iconMenu = "iconCls:'icon-menu" + submenu.getIdMenu() + "'";
                    }
                    if (indice > 0) {
                        sb.append("		<div id=\"mmSUB" + submenu.getIdMenu() + "\" data-options=\"" + iconMenu + "\" onclick=\"chamaItemMenu('" + link + "')\">" + submenu.getNome()
                                + "</div>\n");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String renderizacaoHorizontal() throws Exception {
        sessionMenu((HttpServletRequest) pageContext.getRequest());

        StringBuilder html = new StringBuilder();
        ArrayList<MenuDTO> menu = (ArrayList<MenuDTO>) menuService.listarMenusPorPerfil(usuario, null, true);
        html.append("<ul class=\"menu_horizontal\">");
        for (MenuDTO m : menu) {
            // CAMINHO_IMAGENS_LARGE
            html.append("<a href=\"" + CAMINHO_PAGINAS + m.getLink() + "\"> ");
            html.append("<li class=\"li_menu tooltip_bottom\" title=\"" + m.getDescricao() + "\">");
            html.append("<img src=\"" + CAMINHO_IMAGENS_LARGE + m.getImagem() + "\">");
            html.append("<div class=\"name\">" + m.getNome() + "</div>");
            html.append("</li>");
            html.append("</a>");
        }

        html.append("<a href=\"/cithelp/index.html\" target=\"blank\">");
        html.append("<li class=\"li_menu tooltip_bottom\" title=\" Help CITSmart\">");
        html.append("<img src=\"" + CAMINHO_IMAGENS_LARGE + "help.png\">");
        html.append("<div class=\"name\">Help</div>");
        html.append("</li>");
        html.append("</a>");

        html.append("</ul>");
        return html.toString();
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String validarPermissaoDeBotao(HttpServletRequest request) throws Exception {
        if ((usuario != null && usuario.getLogin() != null)
                && (usuario != null && usuario.getLogin().equalsIgnoreCase("admin") || usuario.getLogin().equalsIgnoreCase("consultor"))) { // Permissao total
            return "";
        }
        StringBuilder html = new StringBuilder();
        MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
        PerfilAcessoMenuService perfilAcessoMenuService = (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
        UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
        PerfilAcessoGrupoService perfilAcessoGrupoService = (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);

        PerfilAcessoMenuDTO perfilAcessoMenudto = new PerfilAcessoMenuDTO();
        PerfilAcessoGrupoDTO perfilAcessoGrupo = new PerfilAcessoGrupoDTO();
        String pathInfo = getRequestedPath(request);
        String[] auxDinamic = {};
        auxDinamic = pathInfo.split(".jsp");
        String strForm = getObjectName(pathInfo);
        String url = "/" + strForm + "/" + strForm + ".load";
        try {
            if (!auxDinamic[1].equals("null")) {
                url += "?" + auxDinamic[1];
            }
        } catch (Exception x) {

        }
        Integer idMenu = menuService.buscarIdMenu(url);
        html.append("<script> addEvent(window, \"load\", carregaPermissao, false); ");
        html.append("function carregaPermissao(){");

        if (idMenu != null) {
            if (usuario.getIdPerfilAcessoUsuario() != null) {
                perfilAcessoMenudto.setIdPerfilAcesso(usuario.getIdPerfilAcessoUsuario());
                perfilAcessoMenudto.setIdMenu(idMenu);
                Collection<PerfilAcessoMenuDTO> listaAcessoMenus = perfilAcessoMenuService.restoreMenusAcesso(perfilAcessoMenudto);

                usuario = (UsuarioDTO) usuarioService.restore(usuario);
                Integer idEmpregado = usuario.getIdEmpregado();
                Collection<GrupoEmpregadoDTO> listaDeGrupoEmpregado = grupoEmpregadoService.findByIdEmpregado(idEmpregado);
                if (listaDeGrupoEmpregado != null) {
                    for (GrupoEmpregadoDTO grupoEmpregado : listaDeGrupoEmpregado) {
                        perfilAcessoGrupo.setIdGrupo(grupoEmpregado.getIdGrupo());
                        perfilAcessoGrupo = perfilAcessoGrupoService.listByIdGrupo(perfilAcessoGrupo);
                        perfilAcessoMenudto.setIdPerfilAcesso(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
                        perfilAcessoMenudto.setIdMenu(idMenu);
                        Collection<PerfilAcessoMenuDTO> listaAcessoMenusGrupo = perfilAcessoMenuService.restoreMenusAcesso(perfilAcessoMenudto);
                        if (listaAcessoMenusGrupo != null) {
                            for (PerfilAcessoMenuDTO perfilAcessoMenu : listaAcessoMenusGrupo) {
                                PerfilAcessoMenuDTO perfil = new PerfilAcessoMenuDTO();
                                perfil.setGrava(perfilAcessoMenu.getGrava());
                                perfil.setPesquisa(perfilAcessoMenu.getPesquisa());
                                perfil.setDeleta(perfilAcessoMenu.getDeleta());
                                listaAcessoMenus.add(perfil);
                            }
                        }

                    }

                }
                if (listaAcessoMenus != null) {
                    setBtnGrava(true);
                    html.append("$('#btnGravar').attr('class','light img_icon has_text disabledButtons'); ");
                    setBtnPesquisa(true);
                    html.append("$('#btnPesquisar').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only disabledButtons'); ");
                    html.append("$('#btnTodos').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only disabledButtons'); ");
                    setBtnDeleta(true);
                    html.append("$('#btnUpDate').attr('class','light img_icon has_text disabledButtons'); ");
                    html.append("$('#btnExcluir').attr('class','light img_icon has_text disabledButtons'); ");
                    for (PerfilAcessoMenuDTO perfilAcesso : listaAcessoMenus) {
                        if (perfilAcesso.getGrava().equalsIgnoreCase("S")) {
                            html.append("$('#btnGravar').attr('class','light img_icon has_text'); ");
                            setBtnGrava(false);
                        }
                        if (perfilAcesso.getPesquisa().equalsIgnoreCase("S")) {
                            html.append("$('#btnPesquisar').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
                            html.append("$('#btnTodos').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
                            setBtnPesquisa(false);
                        }
                        if (perfilAcesso.getDeleta().equalsIgnoreCase("S")) {
                            html.append("$('#btnUpDate').attr('class','light img_icon has_text'); ");
                            html.append("$('#btnExcluir').attr('class','light img_icon has_text'); ");
                            setBtnDeleta(false);
                        }
                    }
                    html.append("$('#btnGravar').attr('disabled', " + getBtnGrava() + "); ");
                    html.append("$('#btnPesquisar').attr('disabled', " + getBtnPesquisa() + "); ");
                    html.append("$('#btnTodos').attr('disabled', " + getBtnPesquisa() + "); ");
                    html.append("$('#btnUpDate').attr('disabled', " + getBtnDeleta() + "); ");
                    html.append("$('#btnExcluir').attr('disabled', " + getBtnDeleta() + "); ");

                }
            }
        }

        html.append("}");
        html.append("</script>");
        return html.toString();
    }

    private String getRequestedPath(HttpServletRequest request) {
        String path = request.getRequestURI() + request.getQueryString();
        /*
         * path = path.substring(request.getContextPath().length());
         * int index = path.indexOf(INTERROGACAO);
         * if (index != -1)
         * path = path.substring(0, index);
         */
        return path;
    }

    public String getObjectName(String path) {
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

    /**
     * @return the btnGrava
     */
    public Boolean getBtnGrava() {
        return btnGrava;
    }

    /**
     * @param btnGrava
     *            the btnGrava to set
     */
    public void setBtnGrava(Boolean btnGrava) {
        this.btnGrava = btnGrava;

    }

    /**
     * @return the btnPesquisa
     */
    public Boolean getBtnPesquisa() {
        return btnPesquisa;
    }

    /**
     * @param btnPesquisa
     *            the btnPesquisa to set
     */
    public void setBtnPesquisa(Boolean btnPesquisa) {
        this.btnPesquisa = btnPesquisa;
    }

    /**
     * @return the btnDeleta
     */
    public Boolean getBtnDeleta() {
        return btnDeleta;
    }

    /**
     * @param btnDeleta
     *            the btnDeleta to set
     */
    public void setBtnDeleta(Boolean btnDeleta) {
        this.btnDeleta = btnDeleta;
    }

}
