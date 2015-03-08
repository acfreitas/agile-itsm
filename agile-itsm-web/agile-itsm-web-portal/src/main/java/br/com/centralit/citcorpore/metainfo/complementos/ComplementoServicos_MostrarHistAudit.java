package br.com.centralit.citcorpore.metainfo.complementos;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citcorpore.bean.CategoriaServicoDTO;
import br.com.centralit.citcorpore.bean.HistoricoServicoDTO;
import br.com.centralit.citcorpore.bean.SituacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.integracao.CategoriaServicoDao;
import br.com.centralit.citcorpore.integracao.HistoricoServicoDao;
import br.com.centralit.citcorpore.integracao.MoedaDao;
import br.com.centralit.citcorpore.integracao.SituacaoServicoDao;
import br.com.centralit.citcorpore.integracao.TipoDemandaServicoDao;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class ComplementoServicos_MostrarHistAudit {
	public void execute(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null;
		try{
			out = response.getWriter();
			
			String IDSERVICO_STR = request.getParameter("IDSERVICO");
			int IDSERVICO = 0;
			if (IDSERVICO_STR != null){
				try{
					IDSERVICO = Integer.parseInt(IDSERVICO_STR);					
				}catch(Exception e){
				}
			}
			
			String strTable = "<table width='100%'>";
			strTable += "<tr>";
			strTable += "<td style='border:1px solid black'>";
				strTable += "&nbsp;";
			strTable += "</td>";			
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "servico.nome")) + "</b>";
			strTable += "</td>";
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "servico.categoria")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "servico.situacao")) + "</b>";
			strTable += "</td>";
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "servico.tipodemanda")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "servico.datainicio")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "servico.abreviacao")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "servico.solicitadoportal")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "servico.modificadopor")) + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "servico.modificadoem")) + "</b>";
			strTable += "</td>";			
			strTable += "</tr>";	
			if (IDSERVICO > 0){
				HistoricoServicoDao historicoServicoDao = new HistoricoServicoDao();
				Collection col = historicoServicoDao.findByIdServico(IDSERVICO);
				CategoriaServicoDao categoriaServicoDao = new CategoriaServicoDao();
				SituacaoServicoDao situacaoServicoDao = new SituacaoServicoDao();
				TipoDemandaServicoDao tipoDemandaDao = new TipoDemandaServicoDao();
				MoedaDao moedaDao = new MoedaDao();
				if (col != null){
					for (Iterator it = col.iterator(); it.hasNext();){
						HistoricoServicoDTO historicoServicoDTO = (HistoricoServicoDTO)it.next();
						String nomeCategoria = "";
						String nomeSituacao = "";
						String nomeTipoDemanda = "";
						CategoriaServicoDTO categoriaServicoDTO = new CategoriaServicoDTO();
						categoriaServicoDTO.setIdCategoriaServico(historicoServicoDTO.getIdCategoriaServico());
						categoriaServicoDTO = (CategoriaServicoDTO) categoriaServicoDao.restore(categoriaServicoDTO);
						if (categoriaServicoDTO != null){
							nomeCategoria = categoriaServicoDTO.getNomeCategoriaServico();
						}
						SituacaoServicoDTO situacaoServicoDTO = new SituacaoServicoDTO();
						situacaoServicoDTO.setIdSituacaoServico(historicoServicoDTO.getIdSituacaoServico());
						situacaoServicoDTO = (SituacaoServicoDTO) situacaoServicoDao.restore(situacaoServicoDTO);
						if (situacaoServicoDTO != null){
							nomeSituacao = situacaoServicoDTO.getNomeSituacaoServico();
						}
						TipoDemandaServicoDTO tipoDemandaDTO = new TipoDemandaServicoDTO();
						tipoDemandaDTO.setIdTipoDemandaServico(historicoServicoDTO.getIdTipoDemandaServico());
						tipoDemandaDTO = (TipoDemandaServicoDTO) tipoDemandaDao.restore(tipoDemandaDTO);
						if (tipoDemandaDTO != null){
							nomeTipoDemanda = tipoDemandaDTO.getNomeTipoDemandaServico();
						}						
						
						strTable += "<tr>";
						strTable += "<td style='border:1px solid black'>";
							strTable += "<img id='img_trHISTCONT_" + historicoServicoDTO.getIdHistoricoServico() + "' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/mais.jpg' border='0' onclick=\"abreFechaMaisMenos(this, 'trHISTCONT_" + historicoServicoDTO.getIdHistoricoServico() + "')\"/>";
						strTable += "</td>";			
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilHTML.encodeHTML(historicoServicoDTO.getNomeServico()) + "";
						strTable += "</td>";
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilHTML.encodeHTML(nomeCategoria) + "";
						strTable += "</td>";	
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilHTML.encodeHTML(nomeSituacao) + "";
						strTable += "</td>";	
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilHTML.encodeHTML(nomeTipoDemanda) + "";
						strTable += "</td>";						
						strTable += "<td style='border:1px solid black'>";
							if (historicoServicoDTO.getDataInicio() != null){
								strTable += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, historicoServicoDTO.getDataInicio(), WebUtil.getLanguage(request)) + "";
							}else{
								strTable += "&nbsp;";
							}
						strTable += "</td>";
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilHTML.encodeHTML(historicoServicoDTO.getSiglaAbrev()) + "";
						strTable += "</td>";
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilHTML.encodeHTML(UtilStrings.nullToVazio(historicoServicoDTO.getDispPortal())) + "";
						strTable += "</td>";						
					
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilHTML.encodeHTML(historicoServicoDTO.getModificadoPor()) + "";
						strTable += "</td>";	
						strTable += "<td style='border:1px solid black'>";
							strTable += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, historicoServicoDTO.getModificadoEm(), WebUtil.getLanguage(request)) + "";
						strTable += "</td>";	
						strTable += "</tr>";	
						
						strTable += "<tr>";
						strTable += "<td colspan='10' style='border:1px solid black'>";
							strTable += "<div id='trHISTCONT_" + historicoServicoDTO.getIdHistoricoServico() + "' style='display:none'>";
								strTable += "<table width='100%'>";
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "servico.detalhe")) + "</b>";
								strTable += "</td>";
								strTable += "</tr>";
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "" + UtilHTML.encodeHTML(historicoServicoDTO.getDetalheServico()) + "";
								strTable += "</td>";								
								strTable += "</tr>";	
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "servico.objetivo")) + "</b>";
								strTable += "</td>";
								strTable += "</tr>";
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "" + UtilHTML.encodeHTML(historicoServicoDTO.getObjetivo()) + "";
								strTable += "</td>";								
								strTable += "</tr>";
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "contrato.dadoslog")) + "</b>";
								strTable += "</td>";
								strTable += "</tr>";	
								strTable += "<tr>";
								strTable += "<td colspan='2'>";
									if (historicoServicoDTO.getConteudodados() != null){
										strTable += historicoServicoDTO.getConteudodados().replaceAll("\n", "<br>");
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
