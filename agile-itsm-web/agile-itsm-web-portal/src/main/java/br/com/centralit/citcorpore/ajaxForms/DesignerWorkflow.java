package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.DesignerWorkflowDTO;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

public class DesignerWorkflow extends AjaxFormAction{

	@Override
	public Class getBeanClass() {
		return DesignerWorkflowDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("workflow", null);
	}
	
	public void addItem(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Collection col = (Collection) request.getSession().getAttribute("workflow");
		if (col == null){
			col = new ArrayList();
		}
		DesignerWorkflowDTO designerWorkflowDTO = (DesignerWorkflowDTO)document.getBean();
		col.add(designerWorkflowDTO);
		
		request.getSession().setAttribute("workflow", col);
		
		draw(document, request, response);
		document.executeScript("$( '#POPUP_OBJ' ).dialog( 'hide' );");
	}	

	public void draw(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Collection col = (Collection) request.getSession().getAttribute("workflow");
		if (col == null){
			col = new ArrayList();
		}
		String strDrawWorkflow = "";
		int i = 0;
		boolean bMostrarSetaFim = false;
		for(Iterator it = col.iterator(); it.hasNext();){
			i++;
			DesignerWorkflowDTO designerWorkflowDTO = (DesignerWorkflowDTO)it.next();
			designerWorkflowDTO.setNumero(new Integer(i));
			String nome = designerWorkflowDTO.getNome();
			nome = UtilStrings.nullToVazio(nome);
			
			bMostrarSetaFim = true;
			
			if (designerWorkflowDTO.getType().equalsIgnoreCase("1")){
				strDrawWorkflow += "<li class='ui-state-default'><span class='ui-icon ui-icon-arrowthick-2-n-s'></span><img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/engrenagem.png' border='0'/> " + nome + "</li>";
			}else{
				strDrawWorkflow += "<li class='ui-state-default'><span class='ui-icon ui-icon-arrowthick-2-n-s'></span><img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/alternativas.png' border='0'/>";
				strDrawWorkflow += generateDecisions(designerWorkflowDTO, document);
				strDrawWorkflow += "</li>";
			}
			/*
			if (designerWorkflowDTO.getType().equalsIgnoreCase("1")){
				strDrawWorkflow += "<div style='text-align: center; border: 1px solid black'>";
				strDrawWorkflow += "<table style='text-align: center;' width='100%'>";
				strDrawWorkflow += "	<tr>";
				strDrawWorkflow += "		<td style='text-align: center;'>";
				strDrawWorkflow += "		<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/engrenagem.png' border='0'/><br>";
				strDrawWorkflow += "		" + nome;
				strDrawWorkflow += "		</td>";
				strDrawWorkflow += "		<td style='text-align: right;'>";
				strDrawWorkflow += "			<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/plus.png' border='0' style='cursor: pointer' onclick='mostraAddObj();'/>";
				strDrawWorkflow += "		</td>";
				strDrawWorkflow += "		<td style='text-align: right;'>";
				strDrawWorkflow += "			<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/button_cancel.png' border='0' style='cursor: pointer' onclick='mostraAddObj();'/>";
				strDrawWorkflow += "		</td>";			
				strDrawWorkflow += "	</tr>";
				strDrawWorkflow += "</table>";
				strDrawWorkflow += "</div>";
			}else{
				strDrawWorkflow += "<div style='text-align: center; border: 1px solid black'>";
				strDrawWorkflow += "<table style='text-align: center;' width='100%'>";
				strDrawWorkflow += "	<tr>";
				strDrawWorkflow += "		<td style='text-align: center;'>";
				strDrawWorkflow += "		<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/alternativas.png' border='0'/><br>";
				strDrawWorkflow += "		" + nome;
				strDrawWorkflow += "			<div id='conteudoDecisao_" + designerWorkflowDTO.getNumero() + "' style='text-align: center; width:100%'>";
				strDrawWorkflow += generateDecisions(designerWorkflowDTO);
				strDrawWorkflow += "			</div>";
				strDrawWorkflow += "		</td>";
				strDrawWorkflow += "		<td style='text-align: right;'>";
				strDrawWorkflow += "			<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/plus.png' border='0' style='cursor: pointer' onclick='mostraAddObj();'/>";
				strDrawWorkflow += "		</td>";
				strDrawWorkflow += "		<td style='text-align: right;'>";
				strDrawWorkflow += "			<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/button_cancel.png' border='0' style='cursor: pointer' onclick='mostraAddObj();'/>";
				strDrawWorkflow += "		</td>";			
				strDrawWorkflow += "	</tr>";
				strDrawWorkflow += "</table>";
				strDrawWorkflow += "</div>";				
			}
			*/
			
			//strDrawWorkflow += generateArrow();
		}
		
		if (bMostrarSetaFim){
			document.getElementById("setaFim").setVisible(true);
		}else{
			document.getElementById("setaFim").setVisible(false);
		}
		
		document.getElementById("sortable").setInnerHTML(strDrawWorkflow);
	}
	
	public String generateArrow(){
		String strArrow = "";
		strArrow += "<div style='text-align: center;'>";
		strArrow += "<table style='text-align: center;' width='100%'>";
		strArrow += "	<tr>";
		strArrow += "		<td style='text-align: center;'>";
		strArrow += "		<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/arrow_down_2.png' border='0'/><br>";
		strArrow += "		</td>";
		strArrow += "		<td style='text-align: right;'>";
		strArrow += "			<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/vazio.png' border='0'/>";
		strArrow += "		</td>";
		strArrow += "		<td style='text-align: right;'>";
		strArrow += "			<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/vazio.png' border='0'/>";
		strArrow += "		</td>";		
		strArrow += "	</tr>";
		strArrow += "</table>";								
		strArrow += "</div>";
		
		return strArrow;
	}
	
	public String generateDecisions(DesignerWorkflowDTO designerWorkflowDTO, DocumentHTML document){
		String strDecisions = "";
		
		if (designerWorkflowDTO.getNumeroDecisoes() == null){
			designerWorkflowDTO.setNumeroDecisoes(1);
		}
		
		strDecisions += "<table width='100%'>";
		strDecisions += "<tr>";
		for (int i = 0; i < designerWorkflowDTO.getNumeroDecisoes(); i++){
			strDecisions += "<td>";
			strDecisions += "<div id='conteudoDecisao_" + designerWorkflowDTO.getNumero() + "_" + i + "' style='text-align: center; border: 1px solid black'>";
			strDecisions += "	Case " + i + "<br>";
			strDecisions += "			<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/plus.png' border='0' style='cursor: pointer' onclick='mostraAddObj();'/>";
			strDecisions += "<li class='ui-state-default'></span><img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/engrenagem.png' border='0'/> TESTE 1</li>";
			strDecisions += "<li class='ui-state-default'></span><img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/engrenagem.png' border='0'/> TESTE 1</li>";
			strDecisions += "</div>";
			strDecisions += "</td>";
			document.executeScript("geraSortable('conteudoDecisao_" + designerWorkflowDTO.getNumero() + "_" + i + "')");
		}
		strDecisions += "<td>";
		strDecisions += "<div id='conteudoDecisao_" + designerWorkflowDTO.getNumero() + "_0' style='text-align: center; border: 1px solid black'>";
		strDecisions += "	Padrão<br>";
		strDecisions += "			<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/plus.png' border='0' style='cursor: pointer' onclick='mostraAddObj();'/>";
		strDecisions += "</div>";
		strDecisions += "</td>";
		
		strDecisions += "</tr>";
		strDecisions += "</table>";
		
		document.executeScript("geraSortable('conteudoDecisao_" + designerWorkflowDTO.getNumero() + "_0')");
		
		return strDecisions;
	}
	
	/*
	public String generateDecisions(DesignerWorkflowDTO designerWorkflowDTO){
		String strDecisions = "";
		
		if (designerWorkflowDTO.getNumeroDecisoes() == null){
			designerWorkflowDTO.setNumeroDecisoes(1);
		}
		
		strDecisions += "<table width='100%'>";
		strDecisions += "<tr>";
		for (int i = 0; i < designerWorkflowDTO.getNumeroDecisoes(); i++){
			strDecisions += "<td>";
			strDecisions += "<div id='conteudoDecisao_" + designerWorkflowDTO.getNumero() + "_" + i + "' style='text-align: center; border: 1px solid black'>";
			strDecisions += "	Case " + i + "<br>";
			strDecisions += "			<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/plus.png' border='0' style='cursor: pointer' onclick='mostraAddObj();'/>";
			strDecisions += "</div>";
			strDecisions += "</td>";
		}
		strDecisions += "<td>";
		strDecisions += "<div id='conteudoDecisao_" + designerWorkflowDTO.getNumero() + "_0' style='text-align: center; border: 1px solid black'>";
		strDecisions += "	Padrão<br>";
		strDecisions += "			<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/plus.png' border='0' style='cursor: pointer' onclick='mostraAddObj();'/>";
		strDecisions += "</div>";
		strDecisions += "</td>";
		
		strDecisions += "</tr>";
		strDecisions += "</table>";
		
		return strDecisions;
	}
	*/
}
