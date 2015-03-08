package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ChatSmartDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.WebUtil;

public class ChatSmart extends AjaxFormAction {
	UsuarioDTO usuario = null;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.usuario = WebUtil.getUsuario(request);
	}

	public synchronized void fechaTelaChat(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Entrei aq-----------------------------------------------------");
	    ChatSmartDTO bean =(ChatSmartDTO) document.getBean();
		String nome = "";
		String listaUsuarios = (String)request.getSession().getAttribute("listaChat"); 
		 if(listaUsuarios!=null && !listaUsuarios.trim().equalsIgnoreCase("")){ 
			String[] array = listaUsuarios.split("#");
			for(String nomeUsuario : array){
				if(!bean.getNomeUsuarioConversando().equals(nomeUsuario)){
					nome += nomeUsuario+"#";
				}
			}
			request.getSession().setAttribute("listaChat",nome);
			System.out.println(request.getSession().getAttribute("listaChat"));
		 } 
	}

	@Override
	public Class getBeanClass() {
		return ChatSmartDTO.class;
	}

}
