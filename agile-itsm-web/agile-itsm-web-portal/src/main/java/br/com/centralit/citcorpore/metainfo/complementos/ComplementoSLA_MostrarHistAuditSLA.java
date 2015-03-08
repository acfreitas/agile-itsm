package br.com.centralit.citcorpore.metainfo.complementos;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoHistoricoDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoHistoricoDao;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;

public class ComplementoSLA_MostrarHistAuditSLA {
	public void execute(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null;
		try{
			out = response.getWriter();
			
			String IDACORDONIVELSERVICO_STR = request.getParameter("IDACORDONIVELSERVICO");
			int IDACORDONIVELSERVICO = 0;
			if (IDACORDONIVELSERVICO_STR != null){
				try{
					IDACORDONIVELSERVICO = Integer.parseInt(IDACORDONIVELSERVICO_STR);					
				}catch(Exception e){
				}
			}
			
			String strTable = "<table width='100%'>";
			strTable += "<tr>";
			strTable += "<td style='border:1px solid black'>";
				strTable += "&nbsp;";
			strTable += "</td>";			
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.titulo")) + "</b>";
			strTable += "</td>";
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.datainicio")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.datafim")) + "</b>";
			strTable += "</td>";
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.avaliarem")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.situacao")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.modificadoPor")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.modificadoEm")) + "</b>";
			strTable += "</td>";			
			strTable += "</tr>";	
			if (IDACORDONIVELSERVICO > 0){
				AcordoNivelServicoHistoricoDao acordoNivelServicoHistoricoDao = new AcordoNivelServicoHistoricoDao();
				Collection col = acordoNivelServicoHistoricoDao.findByIdAcordoNivelServico(IDACORDONIVELSERVICO);
				if (col != null){
					for (Iterator it = col.iterator(); it.hasNext();){
						AcordoNivelServicoHistoricoDTO acordoNivelServicoHistoricoDTO = (AcordoNivelServicoHistoricoDTO)it.next();
						strTable += "<tr>";
						strTable += "<td style='border:1px solid black'>";
							strTable += "<img id='img_trHISTSLA_" + acordoNivelServicoHistoricoDTO.getIdAcordoNivelServico_Hist() + "' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/mais.jpg' border='0' onclick=\"abreFechaMaisMenos(this, 'trHISTSLA_" + acordoNivelServicoHistoricoDTO.getIdAcordoNivelServico_Hist() + "')\"/>";
						strTable += "</td>";			
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilHTML.encodeHTML(acordoNivelServicoHistoricoDTO.getTituloSLA()) + "";
						strTable += "</td>";
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoNivelServicoHistoricoDTO.getDataInicio(), WebUtil.getLanguage(request)) + "";
						strTable += "</td>";	
						strTable += "<td style='border:1px solid black'>";
							if (acordoNivelServicoHistoricoDTO.getDataFim() != null){
								strTable += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoNivelServicoHistoricoDTO.getDataFim(), WebUtil.getLanguage(request)) + "";
							}else{
								strTable += "&nbsp;";
							}
						strTable += "</td>";
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoNivelServicoHistoricoDTO.getAvaliarEm(), WebUtil.getLanguage(request)) + "";
						strTable += "</td>";	
						strTable += "<td style='border:1px solid black'>";
							String situacao = acordoNivelServicoHistoricoDTO.getSituacao();
							if (acordoNivelServicoHistoricoDTO.getSituacao().equalsIgnoreCase("A")){
								situacao = UtilI18N.internacionaliza(request, "sla.ativo");
							}
							if (acordoNivelServicoHistoricoDTO.getSituacao().equalsIgnoreCase("I")){
								situacao = UtilI18N.internacionaliza(request, "sla.inativo");
							}
							strTable += "" + UtilHTML.encodeHTML(situacao) + "";
						strTable += "</td>";	
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilHTML.encodeHTML(acordoNivelServicoHistoricoDTO.getModificadoPor()) + "";
						strTable += "</td>";	
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, acordoNivelServicoHistoricoDTO.getModificadoEm(), WebUtil.getLanguage(request)) + "";
						strTable += "</td>";			
						strTable += "</tr>";	
						
						strTable += "<tr>";
						strTable += "<td colspan='8' style='border:1px solid black'>";
							strTable += "<div id='trHISTSLA_" + acordoNivelServicoHistoricoDTO.getIdAcordoNivelServico_Hist() + "' style='display:none'>";
								strTable += "<table width='100%'>";
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.descricao")) + "</b>";
								strTable += "</td>";
								strTable += "</tr>";
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "" + UtilHTML.encodeHTML(acordoNivelServicoHistoricoDTO.getDescricaoSLA()) + "";
								strTable += "</td>";								
								strTable += "</tr>";	
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.escopo")) + "</b>";
								strTable += "</td>";
								strTable += "</tr>";
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "" + UtilHTML.encodeHTML(acordoNivelServicoHistoricoDTO.getEscopoSLA()) + "";
								strTable += "</td>";								
								strTable += "</tr>";
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.tipoeprioridade")) + "</b>";
								strTable += "</td>";
								strTable += "</tr>";
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									String tipo = acordoNivelServicoHistoricoDTO.getTipo();
									if (acordoNivelServicoHistoricoDTO.getTipo().equalsIgnoreCase("D")){
										tipo = UtilI18N.internacionaliza(request, "sla.tipo.disponibilidade");
									}
									if (acordoNivelServicoHistoricoDTO.getTipo().equalsIgnoreCase("T")){
										tipo = UtilI18N.internacionaliza(request, "sla.tipo.tempo");
									}	
									if (acordoNivelServicoHistoricoDTO.getTipo().equalsIgnoreCase("V")){
										tipo = UtilI18N.internacionaliza(request, "sla.tipo.outros");
									}	
									String prioridade = "";
									if (acordoNivelServicoHistoricoDTO.getIdPrioridadePadrao() != null){
										prioridade = ", " + acordoNivelServicoHistoricoDTO.getIdPrioridadePadrao();
									}
									strTable += "" + UtilHTML.encodeHTML(tipo) + " " + prioridade;
								strTable += "</td>";								
								strTable += "</tr>";			
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.dadoslog")) + "</b>";
								strTable += "</td>";
								strTable += "</tr>";		
								strTable += "<tr>";		
								strTable += "<td colspan='2'>";
									if (acordoNivelServicoHistoricoDTO.getConteudodados() != null){
										strTable += acordoNivelServicoHistoricoDTO.getConteudodados().replaceAll("\n", "<br>");
									}else{
										strTable += "&nbsp;";
									}
								strTable += "</td>";								
								strTable += "</tr>";								
								strTable += "</table>";
							strTable += "</div>";
						strTable += "</td>";								
						strTable += "</tr>";
					}
				}
			}
			strTable += "</table>";
			
			out.write(strTable);			
		}catch(Exception e){
		}
		response.setContentType("text/html; charset=UTF-8");
	}
}
