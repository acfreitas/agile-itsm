package br.com.centralit.citcorpore.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.util.Constantes;

public class ControleSessaoListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {

	}

	public void sessionDestroyed(HttpSessionEvent event) {
		if (event.getSession() != null) {
			UsuarioDTO user = (UsuarioDTO) event.getSession().getAttribute(	Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
			if (user != null) {
				try {

				} catch (Exception e) {
					System.out.println("PROBLEMAS AO REMOVER USUARIO DO CONTROLADOR DE USUARIOS!");
				}
			}
		}
	}


}

