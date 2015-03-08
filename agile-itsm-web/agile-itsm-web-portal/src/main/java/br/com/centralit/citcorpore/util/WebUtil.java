package br.com.centralit.citcorpore.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.InformacoesContratoItem;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoMenuService;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class WebUtil {

    public static void setUsuario(final UsuarioDTO usuario, final HttpServletRequest request) {
        request.getSession().setAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE", usuario);
    }

    public static void setLocale(final String locale, final HttpServletRequest request) {
        request.getSession().setAttribute("locale", locale);
    }

    public static UsuarioDTO getUsuario(final HttpServletRequest request) {
        final UsuarioDTO user = (UsuarioDTO) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
        return user;
    }

    public static br.com.citframework.dto.Usuario getUsuarioSistema(final HttpServletRequest request) throws Exception {
        final br.com.citframework.dto.Usuario usr = new Usuario();
        final UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
        if (usuario != null) {
            if (request.getSession().getAttribute("locale") != null && !request.getSession().getAttribute("locale").equals("")) {
                usuario.setLocale((String) request.getSession().getAttribute("locale"));
            } else {
            	/**
            	 * Se não tive uma língua definida, seta como português
            	 * 
            	 * @author thyen.chang
            	 * @since 04/02/2015
            	 */
                usuario.setLocale(UtilI18N.PORTUGUESE_SIGLA);
            }

            Reflexao.copyPropertyValues(usuario, usr);
        } else {
            return null;
        }

        return usr;
    }

    public static boolean isUserInGroup(final HttpServletRequest req, final String grupo) {
        final UsuarioDTO usuario = WebUtil.getUsuario(req);
        if (usuario == null) {
            return false;
        }

        final String[] grupos = usuario.getGrupos();
        final String grpAux = UtilStrings.nullToVazio(grupo);
        for (final String grupo2 : grupos) {
            if (grupo2 != null) {
                if (grupo2.trim().equalsIgnoreCase(grpAux.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public static void renderizaFilhos(final InformacoesContratoItem itemProntuario, final JspWriter out) throws IOException {
        if (itemProntuario.getColSubItens() == null) {
            return;
        }

        out.print("<div id='divMenu_" + itemProntuario.getNome() + "' style='display:none'>");

        final Iterator it = itemProntuario.getColSubItens().iterator();
        InformacoesContratoItem itemProntuarioTemp;
        for (; it.hasNext();) {
            itemProntuarioTemp = (InformacoesContratoItem) it.next();

            final boolean subItens = itemProntuarioTemp.getColSubItens() != null && itemProntuarioTemp.getColSubItens().size() > 0;

            out.print("<table width='100%'>");
            out.print("<tr id='trITEMMENU_" + itemProntuarioTemp.getNome() + "'>");
            out.print("<td width='10%'>&nbsp;</td>");
            out.print("<td id='tdITEMMENU_" + itemProntuarioTemp.getNome() + "' style='cursor:pointer' class='bordaNaoSelecionaProntuario' onclick=\"setaAbaSelecionada('"
                    + itemProntuarioTemp.getNome() + "', " + subItens + ", '" + itemProntuarioTemp.getPath() + "', 'tdITEMMENU_" + itemProntuarioTemp.getNome() + "')\">");
            out.print(itemProntuarioTemp.getDescricao());

            WebUtil.renderizaFilhos(itemProntuarioTemp, out);
            out.print("</td>");
            out.print("</tr>");

            out.println("<script>arrayItensMenu[iItemMenu] = 'tdITEMMENU_" + itemProntuarioTemp.getNome() + "';</script>");
            out.println("<script>iItemMenu++;</script>");

            out.print("<tr><td style='height:5px'></td></tr>");
            out.print("</table>");
        }

        out.print("</div>");
    }

    @SuppressWarnings("rawtypes")
    public static void renderizaFilhosSomenteQuestionarios(final InformacoesContratoItem itemProntuario, final JspWriter out) throws IOException {
        if (itemProntuario.getColSubItens() == null) {
            return;
        }

        out.print("<div id='divMenu2_" + itemProntuario.getNome() + "' style='display:none'>");

        final Iterator it = itemProntuario.getColSubItens().iterator();
        InformacoesContratoItem itemProntuarioTemp;
        for (; it.hasNext();) {
            itemProntuarioTemp = (InformacoesContratoItem) it.next();
            if (!UtilStrings.nullToVazio(itemProntuarioTemp.getFuncItem()).equalsIgnoreCase("1")) {
                continue;
            }

            final boolean subItens = itemProntuarioTemp.getColSubItens() != null && itemProntuarioTemp.getColSubItens().size() > 0;

            out.print("<table width='100%'>");
            out.print("<tr id='trITEMMENU2_" + itemProntuarioTemp.getNome() + "'>");
            out.print("<td width='10%'>&nbsp;</td>");
            out.print("<td id='tdITEMMENU2_" + itemProntuarioTemp.getNome() + "' style='cursor:pointer' class='bordaNaoSelecionaProntuario' onclick=\"setaAbaSel2('"
                    + itemProntuarioTemp.getNome() + "', " + subItens + ", '" + itemProntuarioTemp.getPath() + "', 'tdITEMMENU2_" + itemProntuarioTemp.getNome() + "', false, '"
                    + itemProntuarioTemp.getIdQuestionario() + "')\">");
            out.print(itemProntuarioTemp.getDescricao());

            WebUtil.renderizaFilhosSomenteQuestionarios(itemProntuarioTemp, out);
            out.print("</td>");
            out.print("</tr>");

            out.println("<script>arrayItensMenu2[iItemMenu] = 'tdITEMMENU2_" + itemProntuarioTemp.getNome() + "';</script>");
            out.println("<script>iItemMenu2++;</script>");

            out.print("<tr><td style='height:5px'></td></tr>");
            out.print("</table>");
        }
        out.print("</div>");
    }

    /**
     * Retorna o Id da Empresa.
     *
     * @param request
     * @return
     */
    public static Integer getIdEmpresa(final HttpServletRequest req) {
        final UsuarioDTO usuario = WebUtil.getUsuario(req);
        return usuario.getIdEmpresa();
    }

    public static EmpregadoDTO getColaborador(final HttpServletRequest req) throws Exception {
        final UsuarioDTO user = (UsuarioDTO) req.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
        final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
        return empregadoService.restoreByIdEmpregado(user.getIdEmpregado());
    }

    /**
     * Valida se usuario esta ativo na sessao
     *
     * @param request
     * @return true:usuario esta na sessão || false:usuario não esta na sessão
     */
    public static Boolean usuarioEstaNaSessao(final HttpServletRequest request) {
        if (WebUtil.getUsuario(request) == null) {
            return false;
        }
        return true;
    }

    /**
     * Valida se o usuario esta na sessão, podendo direcionar o formulario para tela de login
     *
     * @param request
     * @param document
     * @return true: usuario está na sessao ||
     *         false: usuario não esta na sessao e a tela é redirecionada para a tela de login
     */
    public static Boolean validarSeUsuarioEstaNaSessao(final HttpServletRequest request, final DocumentHTML document) {
        if (!usuarioEstaNaSessao(request)) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return false;
        }
        return true;
    }

    /**
     * Retorna Linguagem do session do request.
     *
     * @param request
     * @return String - language
     * @author valdoilo.damasceno
     * @since 04.02.2014
     */
    public static String getLanguage(final HttpServletRequest request) {
        String language = UtilI18N.PORTUGUESE_SIGLA;

        if (request != null && request.getSession() != null && request.getSession().getAttribute("locale") != null) {
            language = (String) request.getSession().getAttribute("locale");
        }
        return language.trim();
    }

    /**
     * Retorna um número inteiro aleatório. Método pode ser utilizado para a geração do nome de relatório.
     *
     * @return int - Número aleatório.
     * @author valdoilo.damasceno
     */
    public static int getRandomNumber() {
        final Random gerador = new Random();
        return gerador.nextInt();
    }

    /**
     * Obtém lista de PerfilAcessoMenu do menu que o usuário está tentando acessar
     *
     * @author thyen.chang
     * @since 28/01/2015 - OPERAÇÃO USAIN BOLT
     * @param request
     * @param usuario
     * @param idMenu
     * @return
     * @throws ServiceException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static List<PerfilAcessoMenuDTO> getPerfilAcessoUsuarioByMenu(final HttpServletRequest request, final UsuarioDTO usuario, final Integer idMenu) throws ServiceException,
            Exception {
        Map<Integer, List<PerfilAcessoMenuDTO>> mapaPerfilAcessoUsuario = (Map<Integer, List<PerfilAcessoMenuDTO>>) request.getSession().getAttribute(
                Constantes.getValue("USUARIO_SESSAO") + "_PERFILACESSOMENU_CITCORPORE");
        if (mapaPerfilAcessoUsuario == null) {
            final PerfilAcessoMenuService perfilAcessoMenuService = (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
            mapaPerfilAcessoUsuario = perfilAcessoMenuService.getPerfilAcessoBotoesMenu(usuario);
            request.getSession().setAttribute(Constantes.getValue("USUARIO_SESSAO") + "_PERFILACESSOMENU_CITCORPORE", mapaPerfilAcessoUsuario);
        }
        return mapaPerfilAcessoUsuario.get(idMenu);
    }

}
