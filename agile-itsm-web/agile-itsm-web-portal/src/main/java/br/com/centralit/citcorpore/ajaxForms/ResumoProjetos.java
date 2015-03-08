package br.com.centralit.citcorpore.ajaxForms;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.LinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.bean.PagamentoProjetoDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.ResumoProjetosDTO;
import br.com.centralit.citcorpore.bean.TarefaLinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.LinhaBaseProjetoService;
import br.com.centralit.citcorpore.negocio.PagamentoProjetoService;
import br.com.centralit.citcorpore.negocio.PerfilContratoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.RecursoTarefaLinBaseProjService;
import br.com.centralit.citcorpore.negocio.TarefaLinhaBaseProjetoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class ResumoProjetos extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	}
	/* Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 15:15 - ID Citsmart: 120948 -
	* Motivo/Comentário: Tabela com dificil visualização/ alterado layout: retirado algumas bordas */
	public void pesquisa(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		ResumoProjetosDTO resumoProjetosDTO = (ResumoProjetosDTO)document.getBean();
		if (resumoProjetosDTO.getCondicaoProjeto() == null){
			resumoProjetosDTO.setCondicaoProjeto("");
		}
		ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, null);
		LinhaBaseProjetoService linhaBaseProjetoService = (LinhaBaseProjetoService) ServiceLocator.getInstance().getService(LinhaBaseProjetoService.class, null);
		TarefaLinhaBaseProjetoService tarefaLinhaBaseProjetoService = (TarefaLinhaBaseProjetoService) ServiceLocator.getInstance().getService(TarefaLinhaBaseProjetoService.class, null);
		RecursoTarefaLinBaseProjService recursoTarefaLinBaseProjService = (RecursoTarefaLinBaseProjService)ServiceLocator.getInstance().getService(RecursoTarefaLinBaseProjService.class, null);
		PerfilContratoService perfilContratoService = (PerfilContratoService) ServiceLocator.getInstance().getService(PerfilContratoService.class, null);
		PagamentoProjetoService pagamentoProjetoService = (PagamentoProjetoService) ServiceLocator.getInstance().getService(PagamentoProjetoService.class, null);
		Collection colProjetos = projetoService.list();
		String strTable = "<table width='100%' class='table table-bordered table-striped'>";
		strTable += "<tr>";

		strTable += "<td style=' font-family: arial; font-size: 12px;'>";
			strTable += "<b>"+UtilI18N.internacionaliza(request, "projeto.nomeProjeto")+"</b>";
		strTable += "</td>";
		strTable += "<td style=' font-family: arial; font-size: 12px;'>";
			strTable += "<b>"+UtilI18N.internacionaliza(request, "projeto.siglaProjeto")+"</b>";
		strTable += "</td>";
		strTable += "<td style=' font-family: arial; font-size: 12px;'>";
			strTable += "<b>"+UtilI18N.internacionaliza(request, "projeto.situacao")+"</b>";
		strTable += "</td>";
		strTable += "<td style=' font-family: arial; font-size: 12px;text-align:right'>";
			strTable += "<b>% "+UtilI18N.internacionaliza(request, "projeto.exec")+"</b>";
		strTable += "</td>";
		strTable += "<td style=' font-family: arial; font-size: 12px;text-align:right'>";
			strTable += "<b>&nbsp;</b>";
		strTable += "</td>";
		strTable += "<td style=' font-family: arial; font-size: 12px;text-align:right'>";
			strTable += "<b>"+UtilI18N.internacionaliza(request, "projeto.tarefasAtrasadas")+"</b>";
		strTable += "</td>";
		strTable += "<td style=' font-family: arial; font-size: 12px;text-align:right'>";
			strTable += "<b>"+UtilI18N.internacionaliza(request, "projeto.valorProjeto")+"</b>";
		strTable += "</td>";
		strTable += "<td style=' font-family: arial; font-size: 12px;text-align:right'>";
			strTable += "<b>"+UtilI18N.internacionaliza(request, "projeto.valorPagamento")+"</b>";
		strTable += "</td>";
		strTable += "<td style=' font-family: arial; font-size: 12px;text-align:right'>";
			strTable += "<b>"+UtilI18N.internacionaliza(request, "projeto.valorGlosas")+"</b>";
		strTable += "</td>";

		strTable += "</tr>";
		if (colProjetos != null){
			for (Iterator it = colProjetos.iterator(); it.hasNext();){
				Double percExec = new Double(0);
				Double valorProjeto = new Double(0);
				Double valorPagamentos = new Double(0);
				Double valorGlosas = new Double(0);
				ProjetoDTO projetoDTO = (ProjetoDTO)it.next();
				if (resumoProjetosDTO.getCondicaoProjeto().equalsIgnoreCase("A")){
					if (!projetoDTO.getSituacao().equalsIgnoreCase("A")){
						continue;
					}
				}else if (resumoProjetosDTO.getCondicaoProjeto().equalsIgnoreCase("I")){
					if (!projetoDTO.getSituacao().equalsIgnoreCase("I")){
						continue;
					}
				}

				Collection colLinhasBase = linhaBaseProjetoService.findByIdProjeto(projetoDTO.getIdProjeto());
				LinhaBaseProjetoDTO linhaBaseProjetoDTO = null;
				if (colLinhasBase != null){
					for (Iterator it2 = colLinhasBase.iterator(); it2.hasNext();){
						linhaBaseProjetoDTO = (LinhaBaseProjetoDTO) it2.next();
						break;
					}
				}
				int atrasadas = 0;
				if (linhaBaseProjetoDTO != null){
					Collection colTarefasLnBase = tarefaLinhaBaseProjetoService.findByIdLinhaBaseProjeto(linhaBaseProjetoDTO.getIdLinhaBaseProjeto());
					if (colTarefasLnBase != null){
						int i = 0;
						for (Iterator it3 = colTarefasLnBase.iterator(); it3.hasNext();){
							TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = (TarefaLinhaBaseProjetoDTO)it3.next();
							if (tarefaLinhaBaseProjetoDTO.getProgresso() != null && tarefaLinhaBaseProjetoDTO.getProgresso().doubleValue() < 100){
								if (tarefaLinhaBaseProjetoDTO.getDataFim().before(UtilDatas.getDataAtual())){
									atrasadas++;
								}
							}
							Collection colPerfisVinc = perfilContratoService.getTotaisByIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
							tarefaLinhaBaseProjetoDTO.setColPerfis(colPerfisVinc);
							Collection colRecsVinc = recursoTarefaLinBaseProjService.findByIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
							tarefaLinhaBaseProjetoDTO.setColRecursos(colRecsVinc);
							if (i == 0){
								percExec = tarefaLinhaBaseProjetoDTO.getProgresso();
							}
							if (tarefaLinhaBaseProjetoDTO.getCustoPerfil() != null && !tarefaLinhaBaseProjetoDTO.isTarefaFase()){
								valorProjeto = new Double(valorProjeto.doubleValue() + tarefaLinhaBaseProjetoDTO.getCustoPerfil().doubleValue());
							}
							i++;
						}
					}
				}
				Collection col = pagamentoProjetoService.getTotaisByIdProjeto(projetoDTO.getIdProjeto());
				if (col != null){
					for (Iterator itAux = col.iterator(); itAux.hasNext();){
						PagamentoProjetoDTO pagProjetoDto = (PagamentoProjetoDTO) itAux.next();
						if (pagProjetoDto.getValorPagamento() != null){
							valorPagamentos = new Double(valorPagamentos.doubleValue() + pagProjetoDto.getValorPagamento().doubleValue());
						}
						if (pagProjetoDto.getValorGlosa() != null){
							valorGlosas = new Double(valorGlosas.doubleValue() + pagProjetoDto.getValorGlosa().doubleValue());
						}
					}
				}
				if (percExec == null){
					percExec = new Double(0);
				}

				ArrayList colMeters = new ArrayList<>();

				MeterInterval meterInterval = new MeterInterval("",
						new Range(0, percExec),
						Color.GREEN, null, Color.GREEN);
				colMeters.add(meterInterval);
				meterInterval = new MeterInterval("",
						new Range(percExec, 100),
						Color.RED, null, Color.RED);
				colMeters.add(meterInterval);

				Range intervaloTotal = new Range(0,100);

				DefaultValueDataset dataset = new DefaultValueDataset();
				dataset.setValue(percExec);
				gerarRelatorioVelocimetro(dataset, intervaloTotal, colMeters, "", CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuario.getIdUsuario() + "/", "IMG_GRF_RESPRJ_" + usuario.getIdUsuario() + "_"  + projetoDTO.getIdProjeto() + ".png", 190, false);

				strTable += "<tr>";

				strTable += "<td style=' font-family: arial; font-size: 12px;'>";
					strTable += projetoDTO.getNomeProjeto();
				strTable += "</td>";
				strTable += "<td style=' font-family: arial; font-size: 12px;'>";
					strTable += UtilStrings.nullToVazio(projetoDTO.getSigla()) + "&nbsp;";
				strTable += "</td>";
				strTable += "<td style=' font-family: arial; font-size: 12px;'>";
				if ( UtilStrings.nullToVazio(projetoDTO.getSituacao()).equalsIgnoreCase("A")){
					strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.ativo");
				}else{
					strTable += UtilI18N.internacionaliza(request, "citcorpore.comum.inativo");
				}
				strTable += "</td>";
				strTable += "<td style=' font-family: arial; font-size: 12px;text-align:right'>";
					strTable += UtilFormatacao.formatDouble(percExec, 2);
				strTable += "</td>";
				strTable += "<td style=' font-family: arial; font-size: 12px;text-align:right'>";
					strTable += "<img src='" + request.getContextPath() + "/tempFiles/" + usuario.getIdUsuario() + "/IMG_GRF_RESPRJ_" + usuario.getIdUsuario() + "_"  + projetoDTO.getIdProjeto() + ".png' border='0'/>";
				strTable += "</td>";
				strTable += "<td style=' font-family: arial; font-size: 12px;text-align:right'>";
					if (atrasadas > 0){
						strTable += "<table width='100%'>";
							strTable += "<tr>";
								strTable += "<td><img src='" + request.getContextPath() + "/imagens/b.gif' border='0'/></td>";
								strTable += "<td style='color:red'>" + atrasadas + "</td>";
							strTable += "</tr>";
						strTable += "</table>";
					}else{
						strTable += atrasadas;
					}
				strTable += "</td>";
				strTable += "<td style=' font-family: arial; font-size: 12px;text-align:right'>";
					strTable += UtilFormatacao.formatDouble(valorProjeto, 2);
				strTable += "</td>";
				strTable += "<td style=' font-family: arial; font-size: 12px;text-align:right'>";
					strTable += UtilFormatacao.formatDouble(valorPagamentos, 2);
				strTable += "</td>";
				strTable += "<td style=' font-family: arial; font-size: 12px;text-align:right'>";
					strTable += UtilFormatacao.formatDouble(valorGlosas, 2);
				strTable += "</td>";

				strTable += "</tr>";
			}
		}
		strTable += "</table>";

		document.getElementById("divInfoProjetos").setInnerHTML(strTable);
	}

	@Override
	public Class<ResumoProjetosDTO> getBeanClass() {
		return ResumoProjetosDTO.class;
	}
	public static void gerarRelatorioVelocimetro(DefaultValueDataset dataset,
			Range intervaloTotal,
			ArrayList intervalos,
			String titulo,
			String caminho,
			String nomeArquivo,
			int angulo,
			boolean legendaGrafico) {
        int width = 120;
        int height = 80;

        MeterPlot plot1 = new MeterPlot();
        // units
        plot1.setUnits("");
        // range
        plot1.setRange(intervaloTotal);
        for (int i = 0;i < intervalos.size();i++) {
            plot1.addInterval((MeterInterval)intervalos.get(i));
        }
        plot1.setDialShape(DialShape.CHORD);
        // dial background paint
        plot1.setDialBackgroundPaint(Color.white);
        // needle paint
        plot1.setNeedlePaint(Color.black);
        // value font
        plot1.setValueFont(new Font("Serif", Font.PLAIN, 12));
        // value paint
        plot1.setValuePaint(Color.black);
        // tick label type
//        plot1.setTickLabelType(MeterPlot.NO_LABELS);
        // tick label font
        plot1.setTickLabelFont(new Font("Serif", Font.PLAIN, 12));
        // tick label format
        plot1.setTickLabelFormat(new DecimalFormat("0"));
        // meter angle
        plot1.setMeterAngle(angulo);

        //DefaultValueDataset ds;
//        ds = new DefaultValueDataset();
//        ds.setValue(dado);
        plot1.setDataset(dataset);
        //JFreeChart chart =  new JFreeChart(titulo,plot1);
        JFreeChart chart = new JFreeChart(titulo, JFreeChart.DEFAULT_TITLE_FONT,
        		plot1, false);
        File arquivo = new File(caminho);
        if (!arquivo.exists())
            arquivo.mkdirs();

        arquivo = new File(caminho + nomeArquivo);
        if (arquivo.exists()) {
        	arquivo.delete();
        }

        try {
            ChartUtilities.saveChartAsPNG(arquivo, chart, width, height);
            System.out.println("Gráfico Pizza gerado com sucesso em: \n\t" + caminho + nomeArquivo);

        } catch (Exception e) {
            System.err.println("Problemas durante a criação do Gráfico XY: " + e.getMessage());
        }
    }

}
