package br.com.centralit.citgerencial.util;

import javax.servlet.http.HttpServletRequest;

import br.com.citframework.dto.Usuario;
import br.com.citframework.util.Constantes;

public class WebUtilGerencial {

    public static void setUsuario(final Usuario usuario, final HttpServletRequest req) {
        String USUARIO_SESSAO = Constantes.getValue("USUARIO_SESSAO");
        if (USUARIO_SESSAO == null) {
            USUARIO_SESSAO = "USUARIO_SESSAO";
        }
        req.getSession().setAttribute(USUARIO_SESSAO, usuario);

    }

    public static Usuario getUsuario(final HttpServletRequest req) {
        String USUARIO_SESSAO = Constantes.getValue("USUARIO_SESSAO");
        if (USUARIO_SESSAO == null) {
            USUARIO_SESSAO = "USUARIO_SESSAO";
        }
        Usuario user = (Usuario) req.getSession().getAttribute(USUARIO_SESSAO);
        if (user == null) {
            final Usuario usr = new Usuario();
            usr.setIdUsuario("1");
            usr.setNomeUsuario("EMAURI GOMES GASPAR JUNIOR");
            usr.setMatricula("1");
            usr.setIdProfissional(new Integer(1));
            usr.setIdEmpresa(new Integer(1));

            final String[] grupos = new String[] {"grupoteste"};
            usr.setGrupos(grupos);

            setUsuario(usr, req);
            user = usr;
        }
        return user;
    }

}
