package br.com.centralit.citcorpore.ajaxForms;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PesquisaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class Pesquisa extends AjaxFormAction {

	private PesquisaDTO pesquisaBean;
	private final String CAMINHO_PAGINAS = Constantes.getValue("CONTEXTO_APLICACAO") + "/pages";
	private final String CAMINHO_IMAGENS = "/citsmart/template_new/images/icons/small/grey/";
	private UsuarioDTO usuario;
	private Integer qtdSub = 0;
	
	@Override
	public Class getBeanClass() {
		return PesquisaDTO.class;
	}
	

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
		    document.alert("O usuário não está logado! Favor logar no sistema!");
		    return;
		}
		
		PesquisaDTO pesquisaBean = (PesquisaDTO) document.getBean();
		StringBuilder header = new StringBuilder();
		StringBuilder html = new StringBuilder();
		header.append("<h2>Resultado da busca por: '<font color='red'; >" + pesquisaBean.getPesquisa() + "</font>'</h2>");
		ArrayList<MenuDTO> menus = (ArrayList<MenuDTO>) request.getSession(true).getAttribute("sessionMenu");
		if(menus!= null && !menus.isEmpty())
		{
			gerarMenus(html, menus, usuario, pesquisaBean.getPesquisa());
		}
		if(html.toString().trim().equals("<ul></ul>"))
		{
			html. append("<font color='red'; >Sua busca não encontrou nenhum resultado</font>");
		}
		HTMLElement divmenu = document.getElementById("result");
		divmenu.setInnerHTML(header.toString() + html.toString());
	}
	
	private void gerarMenus(StringBuilder sb, Collection<MenuDTO> listaDeMenus, UsuarioDTO usuario, String pesquisa) throws ServiceException, Exception {
		MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
		String link;
		try {
			sb.append("<ul>");
			for (MenuDTO menu : listaDeMenus) {
				
				Collection<MenuDTO> novaListaSubMenus;
				novaListaSubMenus = menuService.listarMenusPorPerfil(usuario, menu.getIdMenu(), false);
				
				if (novaListaSubMenus != null && !novaListaSubMenus.isEmpty()) {
					
					for (MenuDTO submenu : novaListaSubMenus) 
					{
						if(removerAcentos(submenu.getNome().toLowerCase()).contains(removerAcentos(pesquisa.toLowerCase())))
						{
							link = submenu.getLink() == null || submenu.getLink().trim().equals("") ? "#" : CAMINHO_PAGINAS + submenu.getLink();
							sb.append("<li id='" + submenu.getIdMenu() + "'>");
							sb.append("<a href='" + link + "'>");
							sb.append(submenu.getNome());
							sb.append("</a> ");
							sb.append("</li>");
							//this.gerarMenus(sb, novaListaSubMenus, usuario, pesquisa);
						}
					}							
				}
			}
			sb.append("</ul>");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PesquisaDTO getPesquisaBean() {
		return pesquisaBean;
	}

	public void setPesquisaBean(PesquisaDTO pesquisaBean) {
		this.pesquisaBean = pesquisaBean;
	}

		
	public String removerAcentos(String text)
	{
		text = Normalizer.normalize(text, Normalizer.Form.NFD);
       return text.replaceAll("[^\\p{ASCII}]", "");
	}

	
}
