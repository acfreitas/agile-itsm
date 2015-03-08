package br.com.centralit.citcorpore.ajaxForms;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citgerencial.generateservices.incidentes.ControleGenerateSLAPorFornecedor;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;

/**
 * Classe responsável por montar o gráfico e desempenho por serviço de um fornecedor
 * 
 * @author rodrigo.oliveira
 *
 */
public class AvaliacaoPorFornecedor extends AjaxFormAction {
	
	private ControleGenerateSLAPorFornecedor controleGenerateSLAPorFornecedor = new ControleGenerateSLAPorFornecedor();
	
	private FornecedorService fornecedorService;
	private ServicoService servicoService;
	private ServicoContratoService serviceContratoService;
	private TipoDemandaServicoService tipoDemandaServicoService;
	private AcordoNivelServicoService acordoNivelServicoService;
	private AcordoServicoContratoService acordoServicoContratoService;
	
	private Integer idFornecedor = null;
	private Collection<ServicoContratoDTO> colServicosContrato;
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		preencheComboFornecedor(document, request);
		
	}
	
	private void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}
	
	public void montaGraficoGeraDesempenho(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		@SuppressWarnings("unchecked")
		HashMap<String, String> hashValues = (HashMap<String, String>) document.getValuesForm();
		
		String fornecedor = hashValues.get("COMBOFORNECEDOR");
		
		if(fornecedor != null && !fornecedor.equals("")){
			setIdFornecedor(Integer.parseInt(hashValues.get("COMBOFORNECEDOR")));
		}
		
		if(getIdFornecedor() == null || fornecedor == null){
			document.alert(UtilI18N.internacionaliza(request, "avaliacao.fornecedor.pesquisa.erro"));
			document.executeScript("fechar_aguarde();");
			return;
		}
		
		setColServicosContrato(getServiceContratoService(request).listarServicosPorFornecedor(getIdFornecedor()));
		
		//Monta gráfico de desempenho do fornecedor selecionado
		geraGraficoPorFornecedor(document, request, response);
		
		//Gera desempenho por serviço do fornecedor selecionado
		montaTelaDesempenho(document, request, response);
		
	}
	
	private void geraGraficoPorFornecedor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String table = "<table border='1'>";
		if (getIdFornecedor() != null && getIdFornecedor() != 0){
			table += "<tr>";
//			table += "<td>";
//				table += "<b>" + UtilI18N.internacionaliza(request, "avaliacao.fornecedor") + "</b>";
//			table += "</td>";
			table += "</tr>";
				table += "<tr>";
					table += "<td style='border:1px solid black; vertical-align:middle;'>";
						table += "<br>" + geraTabelaMeses(document, request, response);
					table += "</td>";
				table += "</tr>";
		}
		
		table += "</table>";
		document.getElementById("tableGrafico").setInnerHTML(table);
		
	}
	
	private String geraTabelaMeses(DocumentHTML documento, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException, LogicException{
		
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		
		String strTable = "<table width='100%' border='1'>";
		String strHeader = "";
		String strDados = "";
		strHeader += "<tr>";
		strDados += "<tr>";
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();		
		Date dataAtual = UtilDatas.getDataAtual();
		Date seisMesAtras = UtilDatas.acrescentaSubtraiMesesData(dataAtual, -6);
		Date dataPesq = new Date();
		
		for (int i = 0; i < 6; i++){
			
			if(i > 0){
				dataPesq = UtilDatas.acrescentaSubtraiMesesData(dataPesq, +1);
			}else{
				dataPesq = seisMesAtras;
			}
			
			strHeader = strHeader + "<td colspan='2' style='border:1px solid black; text-align:center'>";
			strHeader = strHeader + (UtilDatas.getMesAno(dataPesq));
			strHeader = strHeader + "</td>";
			
			Collection<ServicoContratoDTO> listServicosContrato = getColServicosContrato();
			
			List lst = new ArrayList();
			double qtdeDentroPrazo = 0;
			double qtdeForaPrazo = 0;
			
			if ( listServicosContrato != null ) {
				for (ServicoContratoDTO servicoContratoDTO : listServicosContrato) {
					lst = controleGenerateSLAPorFornecedor.execute(servicoContratoDTO.getIdServicoContrato(), idFornecedor, UtilDatas.getYear(dataPesq), UtilDatas.getMonth(dataPesq));
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
			
			dataset.setValue(new Double(qtdeDentroPrazoPerc), UtilI18N.internacionaliza(request, "sla.avaliacao.noprazo"), "" + (UtilDatas.getMesAno(dataPesq)));
			dataset.setValue(new Double(qtdeForaPrazoPerc), UtilI18N.internacionaliza(request, "sla.avaliacao.foraprazo"), "" + (UtilDatas.getMesAno(dataPesq)));
		}
		strHeader += "</tr>";
		strDados += "</tr>";
		
		// create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
        	UtilI18N.internacionaliza(request, "avaliacao.fornecedor"),       // chart title
        	UtilI18N.internacionaliza(request, "sla.avaliacao.indicadores"),     // domain axis label
        	UtilI18N.internacionaliza(request, "sla.avaliacao.resultado"),      // range axis label
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
        
        String nomeImgAval = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalSLAXX_" + getIdFornecedor() + ".png";
        String nomeImgAvalRel = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalSLAXX_" + getIdFornecedor() + ".png";
		
        File arquivo2 = new File(nomeImgAval);
        
        if(arquivo2.exists()){
        	arquivo2.delete();
        }else{
        	String nomeDir = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario() + "/";
        	File dirTemp = new File(nomeDir);
        	dirTemp.mkdirs();
        	arquivo2.createNewFile();
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
	
	
	private void preencheComboFornecedor(DocumentHTML document, HttpServletRequest request) throws Exception {
		HTMLSelect combo = (HTMLSelect) document.getSelectById("comboFornecedor");
		inicializaCombo(combo, request);
		
		List<FornecedorDTO> listaFornecedor = (List) getFornecedorService(request).list();
		
		for(FornecedorDTO f : listaFornecedor){
			combo.addOption(f.getIdFornecedor().toString(), f.getNomeFantasia().replaceAll("'", ""));
		}
		
	}

	private void montaTelaDesempenho(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
				
		List colFinal = new ArrayList();
		
		if (getColServicosContrato() != null){
			for(Iterator it = getColServicosContrato().iterator(); it.hasNext();){
				ServicoContratoDTO servicoContratoAux = (ServicoContratoDTO)it.next();
				if (servicoContratoAux.getDeleted() != null && !servicoContratoAux.getDeleted().equalsIgnoreCase("N")){
				    continue;
				}
				if (servicoContratoAux.getIdServico() != null){
					ServicoDTO servicoDto = new ServicoDTO();
					servicoDto.setIdServico(servicoContratoAux.getIdServico());
					servicoDto = (ServicoDTO) getServicoService(request).restore(servicoDto);
					if (servicoDto != null){
						if (servicoDto.getDeleted() != null && !servicoDto.getDeleted().equalsIgnoreCase("N")){
						    continue;
						}		
						servicoContratoAux.setTemSLA(false);
						servicoContratoAux.setNomeServico(servicoDto.getNomeServico());
						servicoContratoAux.setServicoDto(servicoDto);
						servicoContratoAux.setSituacaoServico(servicoDto.getIdSituacaoServico());
						if (servicoDto.getIdTipoDemandaServico() != null){
						    TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
						    tipoDemandaServicoDto.setIdTipoDemandaServico(servicoDto.getIdTipoDemandaServico());
						    tipoDemandaServicoDto = (TipoDemandaServicoDTO) getTipoDemandaServicoService(request).restore(tipoDemandaServicoDto);
						    if (tipoDemandaServicoDto != null){
						    	servicoContratoAux.setNomeTipoDemandaServico(tipoDemandaServicoDto.getNomeTipoDemandaServico());
						    }
						}
						Collection col = getAcordoNivelServicoService(request).findByIdServicoContrato(servicoContratoAux.getIdServicoContrato());
						Collection colVincs = getAcordoServicoContratoService(request).findByIdServicoContrato(servicoContratoAux.getIdServicoContrato());		
						if ((col != null && col.size() > 0) || (colVincs != null && colVincs.size() > 0)){
							servicoContratoAux.setTemSLA(true);	
						}
						
						List lst = controleGenerateSLAPorFornecedor.execute(servicoContratoAux.getIdServicoContrato(), getIdFornecedor(), null, null);
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
						double qtdeDentroPrazoPerc = qtdeDentroPrazo / (qtdeDentroPrazo + qtdeForaPrazo);
						double qtdeForaPrazoPerc = qtdeForaPrazo / (qtdeDentroPrazo + qtdeForaPrazo);
						servicoContratoAux.setDentroPrazo((qtdeDentroPrazoPerc * 100));
						servicoContratoAux.setForaPrazo((qtdeForaPrazoPerc * 100));
						
						servicoContratoAux.setQtdeDentroPrazo((int)qtdeDentroPrazo);
						servicoContratoAux.setQtdeForaPrazo((int)qtdeForaPrazo);
						colFinal.add(servicoContratoAux);
					}
				}
			}
		}
		Collections.sort(colFinal, new ObjectSimpleComparator("getNomeServico", ObjectSimpleComparator.ASC));
		//request.setAttribute("listaServicos", colFinal);
		
		String table = "";
		
		table += "<table cellpadding='0' cellspacing='0' style='width: 100% !important;'>";
		table += "<tr style='text-align: center;' class=''>";
		table += "<td class='linhaSubtituloGrid'>";
		table += "<b style='line-height: 20px;font-size:13px; padding:0.5em;font-family: arial;'>" + UtilI18N.internacionaliza(request, "servico.servico") + "</b>";
		table += "</td>";
		table += "<td class='linhaSubtituloGrid'>";
		table += "<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.situacao") + "</b>";
		table += "</td>";
		table += "<td class='linhaSubtituloGrid'>";
		table += "<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'>" + UtilI18N.internacionaliza(request, "avaliacao.fornecedor.servico.demanda") + "</b>";
		table += "</td>";
		table += "<td class='linhaSubtituloGrid'>";
		table += "<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'>" + UtilI18N.internacionaliza(request, "avaliacao.fornecedor.sla") + "</b>";
		table += "</td>";
		table += "<td class='linhaSubtituloGrid'>";
		table += "<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'>" + UtilI18N.internacionaliza(request, "avaliacao.fornecedor.dataInicio") + "</b>";
		table += "</td>";
		table += "<td class='linhaSubtituloGrid'>";
		table += "<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'>" + UtilI18N.internacionaliza(request, "avaliacao.fornecedor.dataFim") + "</b>";
		table += "</td>";
		table += "</tr>";
		
		String corLinha = "";
		if (colFinal != null && colFinal.size() > 0){
			for(Iterator it = colFinal.iterator(); it.hasNext();){
				if (!corLinha.trim().equalsIgnoreCase("#f5f5f5")){
					corLinha = "#f5f5f5";
				}else{
					corLinha = "white";
				}
				ServicoContratoDTO servicoContratoAux = (ServicoContratoDTO) it.next();
				//table += "<table>";
					table += "<tr style='border: none;background:" + corLinha + ";'>";
//						table += "<td style='padding:0.2em; text-align: center;' align='center'>";
//						table += "</td>";
						table += "<td>";
							table += servicoContratoAux.getNomeServico();
						table += "</td>";
						table += "<td>";
					if (servicoContratoAux.getSituacaoServico().intValue() == 1){
						if (servicoContratoAux.getDataFim() != null && servicoContratoAux.getDataFim().before(UtilDatas.getDataAtual())){
							table += "<img src='" + br.com.citframework.util.Constantes
									.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
									.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'  title='"+UtilI18N.internacionaliza(request, "citcorpore.comum.servicoInativo")+"'/>";					
							table += UtilI18N.internacionaliza(request, "avalicaoContrato.Inativo(Datafim)");						
						}else{
							table += "<img src='" + br.com.citframework.util.Constantes
									.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
									.getValue("CONTEXTO_APLICACAO") + "/imagens/bolaverde.png' border='0'  title='"+UtilI18N.internacionaliza(request, "citcorpore.comum.servicoAtivo")+"'/>";					
							table += UtilI18N.internacionaliza(request, "categoriaProduto.categoria_ativo");
						}
					}else if (servicoContratoAux.getSituacaoServico().intValue() == 2){
						table += "<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'  title='"+UtilI18N.internacionaliza(request, "citcorpore.comum.servicoInativo")+"'/>";					
						table += UtilI18N.internacionaliza(request, "categoriaProduto.categoria_inativo");
					}else {
						String strSituacao = "";
						if (servicoContratoAux.getSituacaoServico().intValue() == -999){
							strSituacao = "Em análise";
						}
						if (servicoContratoAux.getSituacaoServico().intValue() == 3){
							strSituacao = "Em criação";
						}
						if (servicoContratoAux.getSituacaoServico().intValue() == 4){
							strSituacao = "Em desenho";
						}					
						table += "<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/ball_gray__.gif' border='0'  title='" + strSituacao + "'/>";					
						table += strSituacao;
					}
					table += "</td>";				
					table += "<td>";
						table += servicoContratoAux.getNomeTipoDemandaServico();
					table += "</td>";		
					table += "<td>";
					if (servicoContratoAux.getTemSLA()){
						table += "<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/tick.png' border='0'  title='SLA OK'/>";					
					}else{
						table += "&nbsp;";
					}
					table += "</td>";
					table += "<td>";
						table += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, servicoContratoAux.getDataInicio(), WebUtil.getLanguage(request));
					table += "</td>";
					table += "<td>";
					if (servicoContratoAux.getDataFim() != null){
						table += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, servicoContratoAux.getDataFim(), WebUtil.getLanguage(request));
					}else{
						table += "--";
					}
					table += "</td>";				
				table += "</tr>";
				if ((servicoContratoAux.getQtdeDentroPrazo() != null && servicoContratoAux.getQtdeDentroPrazo().intValue() > 0) 
						|| (servicoContratoAux.getQtdeForaPrazo() != null && servicoContratoAux.getQtdeForaPrazo().intValue() > 0)){
					table += "<tr style='border: none;background:" + corLinha + ";'>";
	//					table += "<td>";
	//						table += "&nbsp;";
	//					table += "</td>";
						table += "<td colspan='20'>";
						table += "<table width='100%'>";
						table += "<tr>";
							table += "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>";
							table += "<td>";
							table += "<div style='border: none;background:" + corLinha + ";' id='divContratoServico_" + servicoContratoAux.getIdServicoContrato() + "'>";
							table += "<table>";
								table += "<tr>";
									table += "<td>&nbsp;</td>";
								table += "</tr>";
								table += "<tr>";
									table += "<td>" + UtilI18N.internacionaliza(request, "avaliacao.fornecedor.dentroPrazo") + "</td>";
									table += "<td> <b><u>" + servicoContratoAux.getQtdeDentroPrazo() + " (" + UtilFormatacao.formatDouble(servicoContratoAux.getDentroPrazo(),2) + "%)</u></b></td>";
									table += "<td>&nbsp;</td>";
									table += "<td>&nbsp;" + UtilI18N.internacionaliza(request, "avaliacao.fornecedor.foraPrazo") + "</td>";
									table += "<td> <b><u>" + servicoContratoAux.getQtdeForaPrazo() + " (" + UtilFormatacao.formatDouble(servicoContratoAux.getForaPrazo(),2) + "%)</u></b></td>";
								table += "</tr>";
							table += "</table>";
							table += "<table width='100%' border='1'>";
								table += "<tr>";
									for (int i = 1; i <= 100; i++){
										String cor = "";
										if (i <= servicoContratoAux.getDentroPrazo().intValue()){
											cor = "green";
										}else{
											cor = "red";
										}
										table += "<td style='border:1px solid black;background-color:" + cor + ";width:1%' title='" + i + "%'>&nbsp;</td>";
									}
								table += "</tr>";
							table += "</table>";
							table += "</div>";
							table += "</td>";
						table += "</tr>";
						table += "</table>";
						table += "</td>";
					table += "</tr>";
				}else{
					table += "<tr style='border: none;background:" + corLinha + ";'>";
//						table += "<td>";
//							table += "&nbsp;";
//						table += "</td>";
						table += "<td colspan='20'>";
							table += "<table width='100%'>";
								table += "<tr>";
									table += "<td>&nbsp;</td>";
								table += "</tr>";						
								table += "<tr>";
									table += "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>";
									table += "<td>";
										table += UtilI18N.internacionaliza(request, "avaliacao.fornecedor.sla.erro");
									table += "</td>";				
								table += "</tr>";
							table += "</table>";
						table += "</td>";
					table += "</tr>";
				}
				//table += "</table>";
			}
		}else{
			table += "<tr>";
			table += "<td>";
			table += "<b>";
				table += UtilI18N.internacionaliza(request, "avaliacao.fornecedor.servico.erro");
			table += "</b>";	
			table += "</td>";
			table += "</tr>";
			table += "</table>";
		}
		
		document.getElementById("tableResult").setInnerHTML(table);
		document.executeScript("fechar_aguarde();");
		
	}
	
	@Override
	public Class getBeanClass() {
		return FornecedorDTO.class;
	}
	
	/**
	 * @return the fornecedorService
	 * @throws Exception 
	 * @throws ServiceException 
	 */
	public FornecedorService getFornecedorService(HttpServletRequest request) throws ServiceException, Exception {
		if(fornecedorService == null){
			fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, WebUtil.getUsuarioSistema(request));
		}
		return fornecedorService;
	}
	
	/**
	 * @return the serviceContratoService
	 * @throws Exception 
	 * @throws ServiceException 
	 */
	public ServicoContratoService getServiceContratoService(HttpServletRequest request) throws ServiceException, Exception {
		if(serviceContratoService == null){
			serviceContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, WebUtil.getUsuarioSistema(request));
		}		
		return serviceContratoService;
	}

	/**
	 * @return the servicoService
	 * @throws Exception 
	 * @throws ServiceException 
	 */
	public ServicoService getServicoService(HttpServletRequest request) throws ServiceException, Exception {
		if(servicoService == null){
			servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, WebUtil.getUsuarioSistema(request));
		}
		return servicoService;
	}

	/**
	 * @return the tipoDemandaServicoService
	 * @throws Exception 
	 * @throws ServiceException 
	 */
	public TipoDemandaServicoService getTipoDemandaServicoService(HttpServletRequest request) throws ServiceException, Exception {
		if(tipoDemandaServicoService == null){
			tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, WebUtil.getUsuarioSistema(request));
		}
		return tipoDemandaServicoService;
	}

	/**
	 * @return the acordoNivelServicoService
	 * @throws Exception 
	 * @throws ServiceException 
	 */
	public AcordoNivelServicoService getAcordoNivelServicoService(HttpServletRequest request) throws ServiceException, Exception {
		if(acordoNivelServicoService == null){
			acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, WebUtil.getUsuarioSistema(request));
		}
		return acordoNivelServicoService;
	}

	/**
	 * @return the acordoServicoContratoService
	 * @throws Exception 
	 * @throws ServiceException 
	 */
	public AcordoServicoContratoService getAcordoServicoContratoService(HttpServletRequest request) throws ServiceException, Exception {
		if(acordoServicoContratoService == null){
			acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, WebUtil.getUsuarioSistema(request));
		}
		return acordoServicoContratoService;
	}

	/**
	 * @return the idFornecedor
	 */
	public Integer getIdFornecedor() {
		return idFornecedor;
	}

	/**
	 * @param idFornecedor the idFornecedor to set
	 */
	public void setIdFornecedor(Integer idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	/**
	 * @return the colServicosContrato
	 */
	public Collection getColServicosContrato() {
		return colServicosContrato;
	}

	/**
	 * @param colServicosContrato the colServicosContrato to set
	 */
	public void setColServicosContrato(Collection colServicosContrato) {
		this.colServicosContrato = colServicosContrato;
	}

}
