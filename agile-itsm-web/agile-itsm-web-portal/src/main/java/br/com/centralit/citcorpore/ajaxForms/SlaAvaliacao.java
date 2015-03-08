package br.com.centralit.citcorpore.ajaxForms;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.RecursoDTO;
import br.com.centralit.citcorpore.bean.RequisitoSLADTO;
import br.com.centralit.citcorpore.bean.SlaAvaliacaoDTO;
import br.com.centralit.citcorpore.bean.TempoAcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.TempoAcordoNivelServicoDao;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.RecursoService;
import br.com.centralit.citcorpore.negocio.RequisitoSLAService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citgerencial.generateservices.incidentes.ControleGenerateSLAPorAcordoNivelServico;
import br.com.centralit.citgerencial.generateservices.incidentes.ControleGenerateSLAPorAcordoNivelServicoByMesAno;
import br.com.centralit.citgerencial.generateservices.incidentes.ControleGenerateSLAPorAcordoNivelServicoEmAndamento;
import br.com.centralit.citgerencial.generateservices.incidentes.ControleGenerateSLAPorRequisitoSLA;
import br.com.centralit.citgerencial.generateservices.incidentes.ControleGenerateSLAPorRequisitoSLAEmAndamento;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class SlaAvaliacao extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
	}
	public void avalia(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
		AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
		RequisitoSLAService requisitoSLAService = (RequisitoSLAService) ServiceLocator.getInstance().getService(RequisitoSLAService.class, null);
		Collection colAcordos = acordoNivelServicoService.findAcordosSemVinculacaoDireta();
		Collection colReqs = requisitoSLAService.list();
		
		ControleGenerateSLAPorRequisitoSLA controleGenerateSLAPorRequisitoSLA = new ControleGenerateSLAPorRequisitoSLA();
		ControleGenerateSLAPorRequisitoSLAEmAndamento controleGenerateSLAPorRequisitoSLAEmAndamento = new ControleGenerateSLAPorRequisitoSLAEmAndamento();
		String table = "<table border='1'>";
		if (colAcordos != null && colAcordos.size() > 0){
			table += "<tr>";
			table += "<td>";
				table += "<b>" + UtilI18N.internacionaliza(request, "sla.avaliacao.acordo") + "</b>";
			table += "</td>";
			table += "</tr>";
			table += "<tr>";
			table += "<td>";
				table += "&nbsp;";
			table += "</td>";
			table += "</tr>";			
			for (Iterator it = colAcordos.iterator(); it.hasNext();){
				AcordoNivelServicoDTO acordoNivelServicoDTO = (AcordoNivelServicoDTO)it.next();
				table += "<tr>";
					table += "<td colspan='5' style='background-color:gray'>";
						table += UtilI18N.internacionaliza(request, "citcorpore.comum.acordo")+": <b>" + acordoNivelServicoDTO.getTituloSLA() + "</b>";
					table += "</td>";
				table += "</tr>";
				if (acordoNivelServicoDTO.getTipo().equalsIgnoreCase("T")){
                    table += generateSLATime(document, request, acordoNivelServicoDTO, acordoNivelServicoDTO.getIdAcordoNivelServico(), usuarioDto);
					table += generateAvailSLATime(document, request, acordoNivelServicoDTO, acordoNivelServicoDTO.getIdAcordoNivelServico(), usuarioDto);
				}else if (acordoNivelServicoDTO.getTipo().equalsIgnoreCase("D")){
					table += generateAvailSLAAvailability(document, request, response, acordoNivelServicoDTO, acordoNivelServicoDTO.getIdAcordoNivelServico(), usuarioDto);
				}
			}
		}
		if (colReqs != null && colReqs.size() > 0){
			table += "<tr>";
			table += "<td>";
				table += "&nbsp;";
			table += "</td>";
			table += "</tr>";
			table += "<tr>";
			table += "<td>";
				table += "<b>" + UtilI18N.internacionaliza(request, "sla.avaliacao.requisito") + "</b>";
			table += "</td>";
			table += "</tr>";	
			for (Iterator it = colReqs.iterator(); it.hasNext();){
				RequisitoSLADTO requisitoSLADTO = (RequisitoSLADTO)it.next();
				List lst = controleGenerateSLAPorRequisitoSLA.execute(requisitoSLADTO.getIdRequisitoSLA());
				double qtdeDentroPrazo = 0;
				double qtdeForaPrazo = 0;
				if (lst != null && lst.size() > 0){
					for (Iterator itSLA = lst.iterator(); itSLA.hasNext();){
						Object[] objs = (Object[]) itSLA.next();
						if (((String)objs[0]).indexOf("Fora") > -1 || ((String)objs[0]).indexOf("Out") > -1){
							qtdeForaPrazo = (Double)objs[2];
						}else{
							qtdeDentroPrazo = (Double)objs[2];
						}
					}
				}
				double qtdeDentroPrazoPerc = (qtdeDentroPrazo / (qtdeDentroPrazo + qtdeForaPrazo)) * 100;
				double qtdeForaPrazoPerc = (qtdeForaPrazo / (qtdeDentroPrazo + qtdeForaPrazo)) * 100;
				
		        final DefaultValueDataset dataset = new DefaultValueDataset(new Double(qtdeDentroPrazoPerc));

		        // create the chart...
		        final ThermometerPlot plot = new ThermometerPlot(dataset);
		        final JFreeChart chart = new JFreeChart(UtilI18N.internacionaliza(request, "sla.avaliacao.avaliacaogeral"),  // chart title
		                                          JFreeChart.DEFAULT_TITLE_FONT,
		                                          plot,                 // plot
		                                          false);               // include legend
		        
		        plot.setSubrangeInfo(ThermometerPlot.NORMAL, 90.000001, 100);
		        plot.setSubrangeInfo(ThermometerPlot.WARNING, 80.000001, 90);
		        plot.setSubrangeInfo(ThermometerPlot.CRITICAL, 0, 80);
		        
		        plot.setThermometerStroke(new BasicStroke(2.0f));
		        plot.setThermometerPaint(Color.lightGray);	
		        
		        String nomeImgAval = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalREQSLA_" + requisitoSLADTO.getIdRequisitoSLA() + ".png";
		        String nomeImgAvalRel = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalREQSLA_" + requisitoSLADTO.getIdRequisitoSLA() + ".png";
		        File arquivo = new File(nomeImgAval);
		        
		        if(arquivo.exists()){
		        	arquivo.delete();
		        }else{
		        	String nomeDir = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario() + "/";
		        	File dirTemp = new File(nomeDir);
		        	dirTemp.mkdirs();
		        	arquivo.createNewFile();
		        }
		        
		        ChartUtilities.saveChartAsPNG(arquivo, chart, 500, 200);	
		        
		        List lst2 = controleGenerateSLAPorRequisitoSLAEmAndamento.execute(requisitoSLADTO.getIdRequisitoSLA());
				qtdeDentroPrazo = 0;
				qtdeForaPrazo = 0;
				if (lst2 != null && lst2.size() > 0){
					for (Iterator itSLA = lst2.iterator(); itSLA.hasNext();){
						Object[] objs = (Object[]) itSLA.next();
						if (((String)objs[0]).indexOf("Fora") > -1 || ((String)objs[0]).indexOf("Out") > -1){
							qtdeForaPrazo = (Double)objs[2];
						}else{
							qtdeDentroPrazo = (Double)objs[2];
						}
					}
				}
				qtdeDentroPrazoPerc = (qtdeDentroPrazo / (qtdeDentroPrazo + qtdeForaPrazo)) * 100;
				qtdeForaPrazoPerc = (qtdeForaPrazo / (qtdeDentroPrazo + qtdeForaPrazo)) * 100;

				DefaultPieDataset datasetPie = new DefaultPieDataset();
				datasetPie.setValue(UtilI18N.internacionaliza(request, "sla.avaliacao.noprazo") + " (" + UtilFormatacao.formatDouble(qtdeDentroPrazo,0) + ")", new Double(qtdeDentroPrazoPerc));
				datasetPie.setValue(UtilI18N.internacionaliza(request, "sla.avaliacao.foraprazo") + " (" + UtilFormatacao.formatDouble(qtdeForaPrazo,0) + ")", new Double(qtdeForaPrazoPerc));
				
				JFreeChart chartX = ChartFactory.createPieChart(
						UtilI18N.internacionaliza(request, "sla.avaliacao.emandamento"),  // chart title
			            datasetPie,             // data
			            true,               // include legend
			            false,
			            false
			        );

			        PiePlot plotPie = (PiePlot) chartX.getPlot();
			        plotPie.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
			        plotPie.setNoDataMessage(UtilI18N.internacionaliza(request,"sla.avaliacao.naohadados"));
			        plotPie.setCircular(false);
			        plotPie.setLabelGap(0.02);	
			        
		        String nomeImgAval2 = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalREQSLA2_" + requisitoSLADTO.getIdRequisitoSLA() + ".png";
		        String nomeImgAvalRel2 = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalREQSLA2_" + requisitoSLADTO.getIdRequisitoSLA() + ".png";
				File arquivo2 = new File(nomeImgAval2);
				if (arquivo2.exists()) {
					arquivo2.delete();
				}			        
			    ChartUtilities.saveChartAsPNG(arquivo2, chartX, 200, 200);		        
				
				table += "<tr>";
					table += "<td style='border:1px solid black; vertical-align:middle;'>";
						table += UtilHTML.encodeHTML(UtilStrings.retiraApostrofe(requisitoSLADTO.getAssunto()));
					table += "</td>";
					table += "<td style='border:1px solid black; vertical-align:middle;'>";
					if (requisitoSLADTO.getSituacao() != null && requisitoSLADTO.getSituacao().equalsIgnoreCase("A")){
						table += "<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/bolaverde.png' border='0'  title='" + UtilI18N.internacionaliza(request, "requisitosla.ativo") + "'/>";					
						table += UtilI18N.internacionaliza(request, "requisitosla.ativo");
					}else if (requisitoSLADTO.getSituacao() != null && requisitoSLADTO.getSituacao().equalsIgnoreCase("P")){
						table += "<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'  title='" + UtilI18N.internacionaliza(request, "requisitosla.planejamento") + "'/>";					
						table += UtilI18N.internacionaliza(request, "requisitosla.planejamento");
					}else if (requisitoSLADTO.getSituacao() != null && requisitoSLADTO.getSituacao().equalsIgnoreCase("R")){
						table += "<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'  title='" + UtilI18N.internacionaliza(request, "requisitosla.emrevisao") + "'/>";					
						table += UtilI18N.internacionaliza(request, "requisitosla.emrevisao");						
					}else{
						table += "<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'  title='" + UtilI18N.internacionaliza(request, "requisitosla.inativo") + "'/>";					
						table += UtilI18N.internacionaliza(request, "requisitosla.inativo");
					}
					table += "</td>";	
					table += "<td style='border:1px solid black'>";
					table += "<img src='" + nomeImgAvalRel + "' border='0'/>";
					table += "</td>";	
					table += "<td style='border:1px solid black'>";
					table += "<img src='" + nomeImgAvalRel2 + "' border='0'/>";
					table += "</td>";					
				table += "</tr>";
			}
		}
		table += "</table>";
		document.getElementById("divInfo").setInnerHTML(table);
	}
	
	private String generateAvailSLAAvailability(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, AcordoNivelServicoDTO acordoNivelServicoDTO, Integer idAcordoNivelServico, UsuarioDTO usuarioDto) throws Exception {
		SlaAvaliacaoDTO slaAvaliacaoDto = (SlaAvaliacaoDTO)document.getBean();
		AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
		AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
		RecursoService recursoService = (RecursoService) ServiceLocator.getInstance().getService(RecursoService.class, null);
		Collection colAcordosServicosContratos = acordoServicoContratoService.findByIdAcordoNivelServico(acordoNivelServicoDTO.getIdAcordoNivelServico());
		RecursoAvaliacao recursoAvaliacao = new RecursoAvaliacao();
		String ret = "";
		if (colAcordosServicosContratos != null){
			ret += "<tr>";
			ret += "<td colspan='5'>";
			ret += "<table width='100%'>";
			for (Iterator it = colAcordosServicosContratos.iterator(); it.hasNext();){
				AcordoServicoContratoDTO acordoServicoContratoDTO = (AcordoServicoContratoDTO)it.next();
				RecursoDTO recursoDto = new RecursoDTO();
				recursoDto.setIdRecurso(acordoServicoContratoDTO.getIdRecurso());
				if ((recursoDto.getIdRecurso()!=null)&&(recursoDto.getIdRecurso()>0)){
					recursoDto = (RecursoDTO) recursoService.restore(recursoDto);
				} else {
					recursoDto = null;
				}
				if (recursoDto != null){
					ret += recursoAvaliacao.geraAvaliacaoNagiosCentreon(document, request, response, recursoDto, slaAvaliacaoDto.getDataInicio(), slaAvaliacaoDto.getDataFim(), usuarioDto, acordoNivelServicoDTO);
				}
			}
			ret += "</table>";
			ret += "</td>";
			ret += "</tr>";
		}
		return ret;
	}
	
   private String generateSLATime(DocumentHTML document, HttpServletRequest request, AcordoNivelServicoDTO acordoNivelServicoDTO, Integer idAcordoNivelServico, UsuarioDTO usuarioDto) throws Exception{
       TempoAcordoNivelServicoDao tempoAcordoNivelServicoDao = new TempoAcordoNivelServicoDao();
       String table = "<tr><td><table border='1' width='90%'>";
       table += "<tr>";
       table += "<td colspan='6'>";
           table += "<b>" + UtilI18N.internacionaliza(request, "sla.alvostempo") + "</b>";
       table += "</td>";
       table += "</tr>";
       table += "<tr>";
       table += "<td>";
           table += "&nbsp;";
       table += "</td>";
       table += "</tr>";
       table += "<tr>";
       table += "<td>";
       table += "    &nbsp;";
       table += "</td>";
       table += "<td style='text-align: center; background-color: gray; border:1px solid black;'>";
       table += "    <b>--- 1 ---</b>";
       table += "</td>";
       table += "<td style='text-align: center; background-color: gray; border:1px solid black;'>";
       table += "    <b>--- 2 ---</b>";
       table += "</td>";
       table += "<td style='text-align: center; background-color: gray; border:1px solid black;'>";
       table += "    <b>--- 3 ---</b>";
       table += "</td>";
       table += " <td style='text-align: center; background-color: gray; border:1px solid black;'>";
       table += "    <b>--- 4 ---</b>";
       table += "</td>";
       table += "<td style='text-align: center; background-color: gray; border:1px solid black;'>";
       table += "    <b>--- 5 ---</b>";
       table += "</td> ";                                              
       table += "</tr>";  
       table += "<tr>"; 
       table += "<td>"; 
       table += "<b>" + UtilI18N.internacionaliza(request, "sla.captura") + "</b>";
       table += "</td>"; 
       for(int i = 1; i <= 5; i++){
           Collection colAux1 = tempoAcordoNivelServicoDao.findByIdAcordoAndFaseAndIdPrioridade(idAcordoNivelServico, 1, i);
           if (colAux1 != null && colAux1.size() > 0){
               TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = (TempoAcordoNivelServicoDTO) ((List)colAux1).get(0);
               table += "<td style='text-align:right;border:1px solid black;'>"+tempoAcordoNivelServicoDTO.getTempoHH()+":"+tempoAcordoNivelServicoDTO.getTempoMM()+"</td>";
           }
       }
       table += "<tr>"; 
       table += "<td>"; 
       table += "<b>" + UtilI18N.internacionaliza(request, "sla.resolucao") + "</b>";
       table += "</td>"; 
       for(int i = 1; i <= 5; i++){
           Collection colAux1 = tempoAcordoNivelServicoDao.findByIdAcordoAndFaseAndIdPrioridade(idAcordoNivelServico, 2, i);
           if (colAux1 != null && colAux1.size() > 0){
               TempoAcordoNivelServicoDTO tempoAcordoNivelServicoDTO = (TempoAcordoNivelServicoDTO) ((List)colAux1).get(0);
               table += "<td style='text-align:right;border:1px solid black;'>"+tempoAcordoNivelServicoDTO.getTempoHH()+":"+tempoAcordoNivelServicoDTO.getTempoMM()+"</td>";
           }           
       }
       table += "</tr>";  
       table += "</table></td></tr>";
       return table;
   }
	   
	private String generateAvailSLATime(DocumentHTML document, HttpServletRequest request, AcordoNivelServicoDTO acordoNivelServicoDTO, Integer idAcordoNivelServico, UsuarioDTO usuarioDto) throws IOException, ParseException{
		SlaAvaliacaoDTO slaAvaliacaoDto = (SlaAvaliacaoDTO)document.getBean();
		ControleGenerateSLAPorAcordoNivelServico controleGenerateSLAPorAcordoNivelServico = new ControleGenerateSLAPorAcordoNivelServico();
		ControleGenerateSLAPorAcordoNivelServicoEmAndamento controleGenerateSLAPorAcordoNivelServicoEmAndamento = new ControleGenerateSLAPorAcordoNivelServicoEmAndamento();
		List lst = controleGenerateSLAPorAcordoNivelServico.execute(idAcordoNivelServico, slaAvaliacaoDto.getDataInicio(), slaAvaliacaoDto.getDataFim());
		double qtdeDentroPrazo = 0;
		double qtdeForaPrazo = 0;
		if (lst != null && lst.size() > 0){
			for (Iterator itSLA = lst.iterator(); itSLA.hasNext();){
				Object[] objs = (Object[]) itSLA.next();
				if (((String)objs[0]).indexOf("Fora") > -1 || ((String)objs[0]).indexOf("Out") > -1){
					qtdeForaPrazo = (Double)objs[2];
				}else{
					qtdeDentroPrazo = (Double)objs[2];
				}
			}
		}
		double qtdeDentroPrazoPerc = (qtdeDentroPrazo / (qtdeDentroPrazo + qtdeForaPrazo)) * 100;
		double qtdeForaPrazoPerc = (qtdeForaPrazo / (qtdeDentroPrazo + qtdeForaPrazo)) * 100;
		
        final DefaultValueDataset dataset = new DefaultValueDataset(new Double(qtdeDentroPrazoPerc));

        // create the chart...
        final ThermometerPlot plot = new ThermometerPlot(dataset);
        final JFreeChart chart = new JFreeChart(UtilI18N.internacionaliza(request, "sla.avaliacao.avaliacaogeral"),  // chart title
                                          JFreeChart.DEFAULT_TITLE_FONT,
                                          plot,                 // plot
                                          false);               // include legend
        
        plot.setSubrangeInfo(ThermometerPlot.NORMAL, 90.000001, 100);
        plot.setSubrangeInfo(ThermometerPlot.WARNING, 80.000001, 90);
        plot.setSubrangeInfo(ThermometerPlot.CRITICAL, 0, 80);
        
        plot.setThermometerStroke(new BasicStroke(2.0f));
        plot.setThermometerPaint(Color.lightGray);	
        
        String nomeImgAval = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalSLA_" + acordoNivelServicoDTO.getIdAcordoNivelServico() + ".png";
        String nomeImgAvalRel = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalSLA_" + acordoNivelServicoDTO.getIdAcordoNivelServico() + ".png";
        File arquivo = new File(nomeImgAval);
        
        if(arquivo.exists()){
        	arquivo.delete();
        }else{
        	String nomeDir = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario() + "/";
        	File dirTemp = new File(nomeDir);
        	dirTemp.mkdirs();
        	arquivo.createNewFile();
        }
        
        ChartUtilities.saveChartAsPNG(arquivo, chart, 500, 200);	
        
        List lst2 = controleGenerateSLAPorAcordoNivelServicoEmAndamento.execute(idAcordoNivelServico);
		qtdeDentroPrazo = 0;
		qtdeForaPrazo = 0;
		if (lst2 != null && lst2.size() > 0){
			for (Iterator itSLA = lst2.iterator(); itSLA.hasNext();){
				Object[] objs = (Object[]) itSLA.next();
				if (((String)objs[0]).indexOf("Fora") > -1 || ((String)objs[0]).indexOf("Out") > -1){
					qtdeForaPrazo = (Double)objs[2];
				}else{
					qtdeDentroPrazo = (Double)objs[2];
				}
			}
		}
		qtdeDentroPrazoPerc = (qtdeDentroPrazo / (qtdeDentroPrazo + qtdeForaPrazo)) * 100;
		qtdeForaPrazoPerc = (qtdeForaPrazo / (qtdeDentroPrazo + qtdeForaPrazo)) * 100;

		DefaultPieDataset datasetPie = new DefaultPieDataset();
		datasetPie.setValue(UtilI18N.internacionaliza(request, "sla.avaliacao.noprazo") + " (" + UtilFormatacao.formatDouble(qtdeDentroPrazo,0) + ")", new Double(qtdeDentroPrazoPerc));
		datasetPie.setValue(UtilI18N.internacionaliza(request, "sla.avaliacao.foraprazo") + " (" + UtilFormatacao.formatDouble(qtdeForaPrazo,0) + ")", new Double(qtdeForaPrazoPerc));
		
		JFreeChart chartX = ChartFactory.createPieChart(
				UtilI18N.internacionaliza(request, "sla.avaliacao.emandamento"),  // chart title
	            datasetPie,             // data
	            true,               // include legend
	            true,
	            false
	        );

	        PiePlot plotPie = (PiePlot) chartX.getPlot();
	        plotPie.setLabelFont(new Font("SansSerif", Font.PLAIN, 6));
	        plotPie.setNoDataMessage(UtilI18N.internacionaliza(request,"sla.avaliacao.naohadados"));
	        plotPie.setCircular(true);
	        plotPie.setLabelGap(0);	
	        
        String nomeImgAval2 = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalSLA2_" + idAcordoNivelServico + ".png";
        String nomeImgAvalRel2 = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalSLA2_" + idAcordoNivelServico + ".png";
		File arquivo2 = new File(nomeImgAval2);
		if (arquivo2.exists()) {
			arquivo2.delete();
		}			        
	    ChartUtilities.saveChartAsPNG(arquivo2, chartX, 200, 200);
        
	    String table = "";
		table += "<tr>";
			table += "<td style='border:1px solid black; vertical-align:middle;'>";
				//table += UtilHTML.encodeHTML(UtilStrings.retiraApostrofe(acordoNivelServicoDTO.getTituloSLA()));
				table += "<br>" + geraTabelaMeses(acordoNivelServicoDTO.getTituloSLA(), idAcordoNivelServico, request, usuarioDto);
			table += "</td>";
			table += "<td style='border:1px solid black; vertical-align:middle;'>";
			if (acordoNivelServicoDTO.getTipo() != null && acordoNivelServicoDTO.getTipo().equalsIgnoreCase("T")){
				table += "<img src='" + br.com.citframework.util.Constantes
						.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
						.getValue("CONTEXTO_APLICACAO") + "/imagens/relogio.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.avaliacao.tempo") + "'/>";					
				table += UtilI18N.internacionaliza(request, "sla.avaliacao.tempo");
			}else if (acordoNivelServicoDTO.getTipo() != null && acordoNivelServicoDTO.getTipo().equalsIgnoreCase("D")){
				table += "<img src='" + br.com.citframework.util.Constantes
						.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
						.getValue("CONTEXTO_APLICACAO") + "/imagens/disponibilidade.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.avaliacao.disponibilidade") + "'/>";					
				table += UtilI18N.internacionaliza(request, "sla.avaliacao.disponibilidade");
			}else if (acordoNivelServicoDTO.getTipo() != null && acordoNivelServicoDTO.getTipo().equalsIgnoreCase("V")){
				table += "<img src='" + br.com.citframework.util.Constantes
						.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
						.getValue("CONTEXTO_APLICACAO") + "/imagens/outrasfontes.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.avaliacao.outrasfontes") + "'/>";					
				table += UtilI18N.internacionaliza(request, "sla.avaliacao.outrasfontes") ;					
			}
			table += "</td>";				
			table += "<td style='border:1px solid black; vertical-align:middle;'>";
			if (acordoNivelServicoDTO.getDataFim() != null && acordoNivelServicoDTO.getDataFim().before(UtilDatas.getDataAtual())){
				table += "<img src='" + br.com.citframework.util.Constantes
						.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
						.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.avaliacao.inativo") + "'/>";					
				table += UtilI18N.internacionaliza(request, "sla.avaliacao.inativo");
			}else{
				table += "<img src='" + br.com.citframework.util.Constantes
						.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
						.getValue("CONTEXTO_APLICACAO") + "/imagens/bolaverde.png' border='0'  title='" + UtilI18N.internacionaliza(request, "sla.avaliacao.ativo") + "'/>";					
				table += UtilI18N.internacionaliza(request, "sla.avaliacao.ativo");
			}
			table += "</td>";		
			table += "<td style='border:1px solid black'>";
			table += "<img src='" + nomeImgAvalRel + "' border='0'/>";
			table += "</td>";
			table += "<td style='border:1px solid black'>";
			table += "<img src='" + nomeImgAvalRel2 + "' border='0'/>";
			table += "</td>";					
		table += "</tr>";
		
		return table;
	}
	
	private String geraTabelaMeses(String tituloSLA, Integer idAcordoNivelServico, HttpServletRequest request, UsuarioDTO usuarioDto) throws ParseException, IOException{
		ControleGenerateSLAPorAcordoNivelServicoByMesAno controleGenerateSLAPorAcordoNivelServicoByMesAno = new ControleGenerateSLAPorAcordoNivelServicoByMesAno();
		int m = UtilDatas.getMonth(UtilDatas.getDataAtual());
		int y = UtilDatas.getYear(UtilDatas.getDataAtual());
		int mPesq = (m + 1); //Faz este incremento de 1, pois logo que entrar no laço, faz um -1
		String strTable = "<table width='100%' border='1'>";
		String strHeader = "";
		String strDados = "";
		strHeader += "<tr>";
		strDados += "<tr>";
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();		
		for (int i = 0; i < 6; i++){
			mPesq = (mPesq - 1);
			if (mPesq <= 0){
				mPesq = 12;
				y = y - 1;
			}
			strHeader = strHeader + "<td colspan='2' style='border:1px solid black; text-align:center'>";
			strHeader = strHeader + (mPesq + "/" + y);
			strHeader = strHeader + "</td>";
			
			List lst = controleGenerateSLAPorAcordoNivelServicoByMesAno.execute(idAcordoNivelServico, y, mPesq);
			double qtdeDentroPrazo = 0;
			double qtdeForaPrazo = 0;
			if (lst != null && lst.size() > 0){
				for (Iterator itSLA = lst.iterator(); itSLA.hasNext();){
					Object[] objs = (Object[]) itSLA.next();
					if (((String)objs[0]).indexOf("Fora") > -1 || ((String)objs[0]).indexOf("Out") > -1){
						qtdeForaPrazo = (Double)objs[2];
					}else{
						qtdeDentroPrazo = (Double)objs[2];
					}
				}
			}
			double qtdeDentroPrazoPerc = 0;
			if ((qtdeDentroPrazo + qtdeForaPrazo) > 0){
				qtdeDentroPrazoPerc = (qtdeDentroPrazo / (qtdeDentroPrazo + qtdeForaPrazo)) * 100;
			}
			double qtdeForaPrazoPerc = 0;
			if ((qtdeDentroPrazo + qtdeForaPrazo) > 0){
				qtdeForaPrazoPerc = (qtdeForaPrazo / (qtdeDentroPrazo + qtdeForaPrazo)) * 100;	
			}
			strDados = strDados + "<td style='border:1px solid black'>";		
			strDados = strDados + UtilFormatacao.formatDouble(qtdeDentroPrazoPerc,2) + "%";
			strDados = strDados + "</td>";
			strDados = strDados + "<td style='border:1px solid black'>";		
			strDados = strDados + UtilFormatacao.formatDouble(qtdeForaPrazoPerc,2) + "%";
			strDados = strDados + "</td>";
			
			dataset.setValue(new Double(qtdeDentroPrazoPerc), UtilI18N.internacionaliza(request, "sla.avaliacao.noprazo"), "" + (mPesq + "/" + y));
			dataset.setValue(new Double(qtdeForaPrazoPerc), UtilI18N.internacionaliza(request, "sla.avaliacao.foraprazo"), "" + (mPesq + "/" + y));
		}
		strHeader += "</tr>";
		strDados += "</tr>";
		
		// create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
        	tituloSLA,         // chart title
        	UtilI18N.internacionaliza(request, "sla.avaliacao.indicadores"),               // domain axis label
        	UtilI18N.internacionaliza(request, "sla.avaliacao.resultado"),                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );		

     // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
     // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, new Color(0, 0, 64)
        );
        GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, new Color(0, 64, 0)
        );        
        
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );        
        
        String nomeImgAval = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalSLAXX_" + idAcordoNivelServico + ".png";
        String nomeImgAvalRel = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalSLAXX_" + idAcordoNivelServico + ".png";
		File arquivo2 = new File(nomeImgAval);
		if (arquivo2.exists()) {
			arquivo2.delete();
		}			        
	    ChartUtilities.saveChartAsPNG(arquivo2, chart, 500, 200);		
		strTable += "<tr>";
		strTable += "<td colspan='12'>";
		strTable += "<img src='" + nomeImgAvalRel + "' border='0'/>";
		strTable += "</td>";
		strTable += "</tr>";
		
		strTable += strHeader;
		strTable += strDados;		
		
		strTable += "</table>";
		return strTable;
	}

	@Override
	public Class getBeanClass() {
		return SlaAvaliacaoDTO.class;
	}

}
