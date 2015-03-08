package br.com.citframework.menu;

import java.util.Collection;
import java.util.Iterator;

import br.com.citframework.util.Constantes;

public class RenderMenu {
	
	public static String render(Collection colMenus, String contextName) throws Exception{
		ItemController itemController = new ItemController(); 
		String strCab = "var menu = new Array();\n" + 
			"var defOver = '" + Constantes.getValue("MENU_defOver") + "', defBack = '" + Constantes.getValue("MENU_defBack") + "', defOver2 = '" + Constantes.getValue("MENU_defOver2") + "', defBack2 = '" + Constantes.getValue("MENU_defBack2") + "';\n" +
			"var defLength = 20;\nvar posicao = 00;\n" +
			"menu[0] = new Array();\n" +
			"menu[0][0] = new Menu(true, '', 0, 90, " + Constantes.getValue("MENU_tamMenu") + ", defOver, defBack, 'menuBorda', 'menu');\n";
			
		String strBuffer = "";
		String strBufferSubMenus = "";
		StringBuilder sMenuSubNiveis = new StringBuilder("");
		
		Iterator it = colMenus.iterator();
		MenuItem mItem;
		
		itemController.setControle(colMenus.size());
		int iPos = 0;
		int iItem = 0;
		
		strBuffer += strCab;
		while(it.hasNext()){
			mItem = (MenuItem)it.next();
			iItem++;
			strBuffer += renderRoot(mItem, iItem, iPos);
			iPos+=20;
			strBufferSubMenus += renderItens(mItem, iItem, itemController, sMenuSubNiveis, false, contextName);
		}
		return strBuffer + strBufferSubMenus + sMenuSubNiveis.toString();
	}
	private static String renderRoot(MenuItem m, int iRoot, int iPos){
		String str = "";
		str += "menu[0][" + iRoot + "] = new Item('" + m.getDescription() + " <img src=\"../../imagens/seta_link1.gif\" border=0>\', '#', '', defLength, 0, " + iRoot + ", 1, '<img src=\"../../imagens/seta_branca.gif\" border=0>&nbsp;&nbsp;', 0,"+iPos+");";
		str += "\n";
		
		return str;
	}
	private static String renderItens(MenuItem m, int iItem, ItemController itemController, StringBuilder sMenuSubNiveis, boolean subMenu, String contextName) throws Exception{
		if (m == null) return "";
		String str = "menu[" + iItem + "] = new Array();\n";
		str += "menu[" + iItem + "][0] = new Menu(true, '', 160, 0, 160, defOver2, defBack2, 'menuBorda1', 'menu');";
		str += "\n";
		MenuItem mTemp = null;
		String strImgSubItem = "";
		int iInterno = 1;
		int pos = 1;
		int iRootAux = itemController.getControle();
		String strSubMenus = "";
		boolean hasSubItens = false;
		if (m.getMenuItens()!=null){
			Iterator it = m.getMenuItens().iterator();
			while(it.hasNext()){
				mTemp = (MenuItem)it.next();
				
				hasSubItens = false;
				if (mTemp.getMenuItens()!=null){
					if (mTemp.getMenuItens().size()>0){
						hasSubItens = true;
					}
				}
				
				strImgSubItem = "";
				if (hasSubItens){
					strImgSubItem = "<img src=\"../../imagens/seta_link.gif\" border=0>";
					//A atribuicao abaixo eh sempre necessaria, pois o parametro referencia. 
					iRootAux = itemController.getControle();
					iRootAux++;
					itemController.setControle(iRootAux);
				}else{
					iRootAux = 0;
				}
				String plusHeight = (mTemp.getDescription().length() > 21) ? "" : "";
				str += "menu[" + iItem + "][" + iInterno + "] = new Item('" + mTemp.getDescription() + " " + strImgSubItem + "', '" + renderPath(mTemp.getPath(), contextName) + "', '', defLength" + plusHeight + ", 0, " + iRootAux + ", 1, '', 1," + pos + ");";
				str += "\n";
				iInterno++;
				pos += 20;
				if (mTemp.getDescription().trim().length() >= 29){ //Incrementa o tamanho do menu devido ao excesso de caracteres
					pos += 9;
				}
				if (mTemp.getDescription().trim().length() > 49){ //Incrementa o tamanho do menu devido ao excesso de caracteres
					pos += 9;
				}				
				if (hasSubItens){
					strSubMenus += renderItens(mTemp, iRootAux, itemController, sMenuSubNiveis, true, contextName);
				}
			}
		}
		if (!subMenu){
			sMenuSubNiveis.append(strSubMenus);
			strSubMenus = "";
		}		
		str += strSubMenus;
		return str;
	}	
	public static String renderPath(String path, String contextName) throws Exception{
		if (path==null){
			return "#";
		}
		if ("".equalsIgnoreCase(path.trim())){
			return "#";
		}
		String ctxNew = contextName.replaceAll("\\#", "\\&");
		String pathNew = path.replaceAll("\\#", "\\&");
		String ret = Constantes.getValue("SERVER_ADDRESS") + ctxNew + "/" + pathNew;
		return ret;
	}
}