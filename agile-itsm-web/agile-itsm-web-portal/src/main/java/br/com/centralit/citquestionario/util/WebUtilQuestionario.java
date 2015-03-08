package br.com.centralit.citquestionario.util;

import javax.servlet.http.HttpServletRequest;

import br.com.citframework.dto.Usuario;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.WebUtil;

public class WebUtilQuestionario {
	

	public static void setUsuario(Usuario usuario, HttpServletRequest req) {
		req.getSession().setAttribute(Constantes.getValue("USUARIO_SESSAO"), usuario);
 
	}

	public static Usuario getUsuario(HttpServletRequest req) {
		return WebUtil.getUsuario(req);
		/*
		Usuario user = (Usuario) req.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO"));
		if (user == null){
				Usuario usr = new Usuario();
				usr.setIdUsuario("1");
				usr.setNomeUsuario("EMAURI GOMES GASPAR JUNIOR");
				usr.setMatricula("1");
				usr.setIdProfissional(new Integer(1));
				usr.setIdEmpresa(new Integer(123));
				
				String[] grupos = new String[] {"grupoteste"};
				usr.setGrupos(grupos);
				
				setUsuario(usr, req);
				user = usr;	
		}
		return user;
		*/
	}
	
}

