package br.com.citframework.menu;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.citframework.dto.Usuario;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.WebUtil;
/**
 * Este menu pode ficar na Horizontal como na Vertical.
 * 	Basta alterar o CSS.
 * 
 * 	Consulte a documentacao do fsmenu
 * 
 * @author emauri
 *
 */
@SuppressWarnings("rawtypes")
public class RenderMenuGenerico implements IRenderMenu {
	
	
	public String render(Collection colMenus, String contextName, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String strBuffer = "";
		
		Iterator it = colMenus.iterator();
		MenuItem mItem;
				
		strBuffer += "<ul class='menulist' id='listMenuRoot'>\n";
		while(it.hasNext()){
			mItem = (MenuItem)it.next();
			
			strBuffer += renderItem(mItem, contextName, request, false);
		}
		strBuffer += "</ul>\n";
		
		return strBuffer;
	}
	private String renderItem(MenuItem m, String contextName, HttpServletRequest request, boolean subMenu) throws Exception{
		String str = "";
		
		String SEGURANCA_HABILITADA = Constantes.getValue("SEGURANCA_HABILITADA");
		if (SEGURANCA_HABILITADA == null){
			SEGURANCA_HABILITADA = "N";
		}
		
		if (SEGURANCA_HABILITADA.equalsIgnoreCase("S")){ //Se nao estiver setado como "S" - Sim, entao esta livre.
			Usuario user = WebUtil.getUsuario(request);
			if (!user.getNomeUsuario().equalsIgnoreCase("admin") || !user.getNomeUsuario().equalsIgnoreCase("consultor")){ //O admin tem permissao a tudo!
				Collection col = (Collection)request.getSession().getAttribute("acessosUsuario");
				if (col == null){
					return "";
				}
				String path = m.getPath();
				if (path != null && !path.trim().equalsIgnoreCase("") && !path.endsWith("/sair.load")){ //Ignora Agrupamentose Sair
					boolean bAutorizado = col.contains(path);
					if (!bAutorizado){
						if (path.startsWith("/")){
							path = path.substring(1);
							bAutorizado = col.contains(path); //Faz mais uma tentativa, mas agora sem a barra que havia.
							if (!bAutorizado){
								return "";
							}
						}else{
							return "";
						}
					}	
				}				
			}
		}
		
		boolean teveAcessoASubItem = false;
		String strSubMenu = "";
		if (subMenu){
			strSubMenu = "class='listSubMenu'";
		}
		str = "<li><a " + strSubMenu + " href='" + renderPath(m.getPath(), contextName) + "'>" + renderDescription(m.getDescription()) + "</a>\n";
		
		if (m.getMenuItens() != null && m.getMenuItens().size() > 0){
			str += "<ul>\n";
			
			Iterator it = m.getMenuItens().iterator();
			while(it.hasNext()){
				MenuItem mItemAux = (MenuItem)it.next();
				
				String strAux = renderItem(mItemAux, contextName, request, true);
				if (strAux != null && !strAux.trim().equalsIgnoreCase("")){
					teveAcessoASubItem = true;
				}
				str += strAux;
			}
			
			str += "</ul>\n";
		}else{
			//Se nao tem subitens, entao seta true pra passar pelo IF abaixo.
			teveAcessoASubItem = true;
		}
		
		str += "</li>\n";
		
		if (!teveAcessoASubItem){ //Se todos os subniveis estiverem bloqueados entao nao mostre o agrupador.
			return "";
		}
		return str;
	}
	public String renderDescription(String description) throws Exception{
		if (description == null) return "";
		boolean bImg = false;
		String img = "";
		String retorno = "";
		for(int i = 0; i < description.length(); i++){
			if (description.charAt(i) == '{'){
				bImg = true;
				img = "";
			}else if (description.charAt(i) == '}'){
				bImg = false;
				img = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + img;
				retorno += "<img src='" + img + "' border='0'/>";
				img = "";
			}else {
				if (bImg){
					img += description.charAt(i);
				}else{
					retorno += description.charAt(i);
				}
			}
		}
		return retorno;
	}
	public String renderPath(String path, String contextName) throws Exception{
		if (path==null){
			return "#";
		}
		if ("".equalsIgnoreCase(path.trim())){
			return "#";
		}
		String ctxNew = contextName.replaceAll("\\#", "\\&");
		String pathNew = path.replaceAll("\\#", "\\&");
		if (ctxNew == null) ctxNew = "";
		if (ctxNew.trim().equalsIgnoreCase("/")){
			ctxNew = "";
		}
		String ret = Constantes.getValue("SERVER_ADDRESS") + ctxNew + "/" + pathNew;
		return ret;
	}
}