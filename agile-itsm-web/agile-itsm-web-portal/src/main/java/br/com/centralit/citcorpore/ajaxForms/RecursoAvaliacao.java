package br.com.centralit.citcorpore.ajaxForms;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.CentreonLogDTO;
import br.com.centralit.citcorpore.bean.ExecucaoAtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.GrupoRecursosDTO;
import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.bean.NagiosConexaoDTO;
import br.com.centralit.citcorpore.bean.NagiosNDOStateHistoryDTO;
import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.bean.RecursoAvaliacaoDTO;
import br.com.centralit.citcorpore.bean.RecursoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CalendarioService;
import br.com.centralit.citcorpore.negocio.CentreonLogService;
import br.com.centralit.citcorpore.negocio.ExecucaoAtividadePeriodicaService;
import br.com.centralit.citcorpore.negocio.GrupoRecursosService;
import br.com.centralit.citcorpore.negocio.JornadaTrabalhoService;
import br.com.centralit.citcorpore.negocio.NagiosConexaoService;
import br.com.centralit.citcorpore.negocio.NagiosNDOStateHistoryService;
import br.com.centralit.citcorpore.negocio.ProgramacaoAtividadeService;
import br.com.centralit.citcorpore.negocio.RecursoService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaItemConfiguracaoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
@SuppressWarnings({"rawtypes","unchecked"})
public class RecursoAvaliacao extends AjaxFormAction {
	
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GrupoRecursosService grupoRecursosService = (GrupoRecursosService) ServiceLocator.getInstance().getService(GrupoRecursosService.class, null);
		Collection colGrupos = grupoRecursosService.list();
		HTMLSelect idGrupoRecurso = document.getSelectById("idGrupoRecurso");
		idGrupoRecurso.removeAllOptions();
		idGrupoRecurso.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos") );
		if (colGrupos != null) {
			for (Iterator it = colGrupos.iterator(); it.hasNext();) {
				GrupoRecursosDTO grupoRecursosDTO = (GrupoRecursosDTO) it.next();
				if (grupoRecursosDTO.getDeleted() == null
						|| grupoRecursosDTO.getDeleted().equalsIgnoreCase("n")) {
					idGrupoRecurso.addOption("" + grupoRecursosDTO.getIdGrupoRecurso(),StringEscapeUtils.escapeJavaScript(grupoRecursosDTO.getNomeGrupoRecurso()));
				}
			}
		}		
	}
	public void geraInformacoes(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}	
		RecursoAvaliacaoDTO recursoAvaliacaoDTO = (RecursoAvaliacaoDTO)document.getBean();
		if (recursoAvaliacaoDTO.getDataInicio() == null){
			document.alert("Informe a data inicial!");
			return;
		}
		if (recursoAvaliacaoDTO.getDataFim() == null){
			document.alert("Informe a data final!");
			return;
		}
		RecursoService recursoService = (RecursoService) ServiceLocator.getInstance().getService(RecursoService.class, null);
		Collection colRecursos = null;
		if (recursoAvaliacaoDTO.getIdGrupoRecurso() != null){
			colRecursos = recursoService.findByIdGrupoRecurso(recursoAvaliacaoDTO.getIdGrupoRecurso());
		}else{
			colRecursos = recursoService.list();
		}
		
		String table = "<table border='1'>";
		if (colRecursos != null && colRecursos.size() > 0){
			table += "<tr>";
			table += "<td colspan='4'>";
				table += "<b>" + UtilI18N.internacionaliza(request, "recurso.avaliacao") + "</b>";
			table += "</td>";
			table += "</tr>";
			for (Iterator it = colRecursos.iterator(); it.hasNext();){
				RecursoDTO recursoDTO = (RecursoDTO)it.next();
				if (UtilStrings.nullToVazio(recursoDTO.getTipoAtualizacao()).equalsIgnoreCase(RecursoDTO.NAGIOS_CENTREON)){
					table += geraAvaliacaoNagiosCentreon(document, request, response, recursoDTO, recursoAvaliacaoDTO.getDataInicio(), recursoAvaliacaoDTO.getDataFim(), usuarioDto, null);
				}else if (UtilStrings.nullToVazio(recursoDTO.getTipoAtualizacao()).equalsIgnoreCase(RecursoDTO.NAGIOS_NATIVE)){
					table += geraAvaliacaoNagiosNDOUtils(document, request, response, recursoDTO, recursoAvaliacaoDTO.getDataInicio(), recursoAvaliacaoDTO.getDataFim(), usuarioDto, null);
				}
			}
		}else{
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.resultado"));
		}
		table += "</table>";
		document.getElementById("divInfo").setInnerHTML(table);		
	}
	public String geraAvaliacaoNagiosNDOUtils(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response, RecursoDTO recursoDTO, Date dataInicio, Date dataFim, UsuarioDTO usuarioDto, AcordoNivelServicoDTO acordoNivelServicoDTO) throws ServiceException, Exception{
		NagiosConexaoService nagiosConexaoService = (NagiosConexaoService) ServiceLocator.getInstance().getService(NagiosConexaoService.class, null);
		CalendarioService calendarioService = (CalendarioService) ServiceLocator.getInstance().getService(CalendarioService.class, null);
		NagiosNDOStateHistoryService nagiosNDOStateHistoryService = (NagiosNDOStateHistoryService) ServiceLocator.getInstance().getService(NagiosNDOStateHistoryService.class, null);
		RequisicaoMudancaItemConfiguracaoService requisicaoMudancaItemConfiguracaoService = (RequisicaoMudancaItemConfiguracaoService)ServiceLocator.getInstance().getService(RequisicaoMudancaItemConfiguracaoService.class, null);
		ExecucaoAtividadePeriodicaService execucaoAtividadePeriodicaService = (ExecucaoAtividadePeriodicaService)ServiceLocator.getInstance().getService(ExecucaoAtividadePeriodicaService.class, null);
		ProgramacaoAtividadeService programacaoAtividadeService = (ProgramacaoAtividadeService)ServiceLocator.getInstance().getService(ProgramacaoAtividadeService.class, null);
		NagiosConexaoDTO nagiosConexaoDTO = new NagiosConexaoDTO();
		if (recursoDTO.getIdNagiosConexao() == null){
			document.alert("Não há configuração de conexão ao Nagios para o recursos: " + recursoDTO.getNomeRecurso());
		}
		nagiosConexaoDTO.setIdNagiosConexao(recursoDTO.getIdNagiosConexao());
		if (recursoDTO.getIdNagiosConexao() != null){
			nagiosConexaoDTO = (NagiosConexaoDTO) nagiosConexaoService.restore(nagiosConexaoDTO);
		}
		if (nagiosConexaoDTO == null){
			document.alert("Não há configuração correta de conexão ao Nagios para o recursos: " + recursoDTO.getNomeRecurso());
		}
		
		Collection colMudancasIC = null;
		Collection colBlackoutsTotais = new ArrayList();
		if (recursoDTO.getIdItemConfiguracao() != null){
			colMudancasIC = requisicaoMudancaItemConfiguracaoService.findByIdItemConfiguracao(recursoDTO.getIdItemConfiguracao());
			if (colMudancasIC != null){
				for (Iterator it = colMudancasIC.iterator(); it.hasNext();){
					RequisicaoMudancaItemConfiguracaoDTO requisicaoMudancaItemConfiguracaoDTO = (RequisicaoMudancaItemConfiguracaoDTO)it.next();
					Collection colBlackoutMud = execucaoAtividadePeriodicaService.findBlackoutByIdMudancaAndPeriodo(requisicaoMudancaItemConfiguracaoDTO.getIdRequisicaoMudanca(), dataInicio, dataFim);
					if (colBlackoutMud != null){
						colBlackoutsTotais.addAll(colBlackoutMud);
					}
				}
			}
		}	
		double qtdeHorasTotais = 0;
		if (recursoDTO.getIdCalendario() != null){
			CalendarioDTO calendarioDTO = new CalendarioDTO();
			calendarioDTO.setIdCalendario(recursoDTO.getIdCalendario());
			calendarioDTO = (CalendarioDTO) calendarioService.restore(calendarioDTO);
			Date d = dataInicio;
			while(d.compareTo(dataFim) <= 0){
				System.out.println("Verificando---> " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, d, WebUtil.getLanguage(request)));
				String diaSemana = UtilDatas.getDiaSemana(new java.util.Date(d.getTime()));
				if (diaSemana.equalsIgnoreCase("dom")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaDom());
				}
				if (diaSemana.equalsIgnoreCase("seg")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaSeg());
				}
				if (diaSemana.equalsIgnoreCase("ter")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaTer());
				}	
				if (diaSemana.equalsIgnoreCase("qua")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaQua());
				}	
				if (diaSemana.equalsIgnoreCase("qui")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaQui());
				}
				if (diaSemana.equalsIgnoreCase("sex")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaSex());
				}
				if (diaSemana.equalsIgnoreCase("sab")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaSab());
				}					
				d = new Date(UtilDatas.alteraData(d, 1, Calendar.DAY_OF_MONTH).getTime());
			}
		}
		System.out.println(recursoDTO.getNomeRecurso() + " --> Quantidade Total de Horas ---> " + qtdeHorasTotais);
		String table = "";
		table += "<tr>";
		table += "<td colspan='4'>";
			table += "&nbsp;";
		table += "</td>";
		table += "</tr>";				
		table += "<tr>";
		table += "<td colspan='4' style='background-color: lightgrey; font-size:14px;'>";
			table += "Recurso: <b>" + recursoDTO.getNomeRecurso() + "</b>";
		table += "</td>";
		table += "</tr>";	
		
		Timestamp tsFinal = null;
		Timestamp tsInicio = null;
		if (recursoDTO.getHoraFimFunc() != null && !recursoDTO.getHoraFimFunc().trim().equalsIgnoreCase("")){
			tsFinal = UtilDatas.strToTimestamp("00/00/0000 " + recursoDTO.getHoraFimFunc() + ":00");
		}
		if (recursoDTO.getHoraInicioFunc() != null && !recursoDTO.getHoraInicioFunc().trim().equalsIgnoreCase("")){
			tsInicio = UtilDatas.strToTimestamp("00/00/0000 " + recursoDTO.getHoraInicioFunc() + ":00");
		}
		
		long timeAtividadeMill = 0;
		if (tsFinal != null && tsInicio != null){
			timeAtividadeMill = tsFinal.getTime() - tsInicio.getTime();
			String tempoFmt = converte(timeAtividadeMill);
			table += "<tr>";
			table += "<td colspan='4'>";
				table += "<b>Tempo de Maior Atividade: " + recursoDTO.getHoraInicioFunc() + " a " + recursoDTO.getHoraFimFunc() + " (" + tempoFmt + "/dia)</b>";
			table += "</td>";
			table += "</tr>";					
		}
		table += "<tr>";
		table += "<td colspan='4'>";
			table += "<b>Volume de Horas do Período: " + UtilFormatacao.formatDouble(qtdeHorasTotais,2) + "</b>";
		table += "</td>";
		table += "</tr>";				
		
		Collection colLogs = null;
		if (nagiosConexaoDTO != null && nagiosConexaoDTO.getNomeJNDI() != null && !nagiosConexaoDTO.getNomeJNDI().trim().equalsIgnoreCase("")){
			nagiosNDOStateHistoryService.setJndiName(nagiosConexaoDTO.getNomeJNDI());
			if (recursoDTO.getServiceName() == null || recursoDTO.getServiceName().trim().equalsIgnoreCase("")){
				colLogs = nagiosNDOStateHistoryService.findByHostServiceStatusAndServiceNull(nagiosConexaoDTO.getNomeJNDI(), recursoDTO.getHostName(), null, dataInicio, dataFim);
			}else{
				colLogs = nagiosNDOStateHistoryService.findByHostServiceStatus(nagiosConexaoDTO.getNomeJNDI(), recursoDTO.getHostName(), recursoDTO.getServiceName(), null, dataInicio, dataFim);
			}
		}	
		table += "<tr>";
		table += "<td colspan='4'>";
			table += "<b>Quedas</b>";
		table += "</td>";
		table += "</tr>";		
		table += "<tr>";
		table += "<td style='border:1px solid black'>";
			table += "<b>Alerta</b>";
		table += "</td>";				
		table += "<td style='border:1px solid black'>";
			table += "<b>Tempo de parada</b>";
		table += "</td>";
		table += "<td style='border:1px solid black'>";
			table += "<b>Inicio</b>";
		table += "</td>";
		table += "<td style='border:1px solid black'>";
			table += "<b>Fim</b>";
		table += "</td>";								
		table += "</tr>";				
		java.sql.Timestamp horaQueda = null;
		boolean quedas = false;
		long tempoTotalQueda = 0;
		if (colLogs != null && colLogs.size() > 0){
			for (Iterator itLog = colLogs.iterator(); itLog.hasNext();){
				NagiosNDOStateHistoryDTO log = (NagiosNDOStateHistoryDTO) itLog.next();
				if ("DOWN".equalsIgnoreCase(log.getStatus().trim())){
					if (horaQueda == null){
						horaQueda = new java.sql.Timestamp(log.getState_time().getTime());
						quedas = true;
					}
				}
				if ("UP".equalsIgnoreCase(log.getStatus().trim())){
					java.sql.Timestamp horaRetorno = new java.sql.Timestamp(log.getState_time().getTime());
					if (horaQueda != null){
						long tempoDeQueda = UtilDatas.calculaDiferencaTempoEmMilisegundos(horaRetorno, horaQueda);
						String tempoPronto = converte(tempoDeQueda);
				        
						table += "<tr>";
						table += "<td style='border:1px solid black'>";
							table += "&nbsp;";
						table += "</td>";								
						table += "<td style='border:1px solid black'>";
							table += "" + tempoPronto + "";
						table += "</td>";
						table += "<td style='border:1px solid black'>";
							table += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, horaQueda, WebUtil.getLanguage(request)) + " " + UtilDatas.formatHoraFormatadaHHMMSSStr(horaQueda) + "";
						table += "</td>";
						table += "<td style='border:1px solid black'>";
							table += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, horaRetorno, WebUtil.getLanguage(request)) + " " +  UtilDatas.formatHoraFormatadaHHMMSSStr(horaRetorno) + "";
						table += "</td>";								
						table += "</tr>";
						
						horaQueda = null;
						tempoTotalQueda = tempoTotalQueda + tempoDeQueda;
					}
				}						
			}
		}
		if (horaQueda != null){
			java.sql.Timestamp horaRetorno = UtilDatas.getDataHoraAtual();
			if (horaQueda != null){
				long tempoTotalDeQueda = UtilDatas.calculaDiferencaTempoEmMilisegundos(horaRetorno, horaQueda);
				String tempoPronto = converte(tempoTotalDeQueda);
		        
				table += "<tr>";
				table += "<td style='border:1px solid black'>";
					table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + 
							br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/b.gif' border='0'/>";
				table += "</td>";						
				table += "<td style='border:1px solid black'>";
					table += "" + tempoPronto + "";
				table += "</td>";
				table += "<td style='border:1px solid black'>";
					table += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, horaQueda, WebUtil.getLanguage(request)) + " " + UtilDatas.formatHoraFormatadaHHMMSSStr(horaQueda) + "";
				table += "</td>";
				table += "<td style='border:1px solid black'>";
					table += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, horaRetorno, WebUtil.getLanguage(request)) + " " +  UtilDatas.formatHoraFormatadaHHMMSSStr(horaRetorno) + "";
				table += "</td>";								
				table += "</tr>";
				
				horaQueda = null;
			}					
		}
		long totalHorasBlackoutMudancas = 0;
		long totalHorasBlackoutMudancasMilli = 0;
		if (colBlackoutsTotais.size() > 0){
			table += "<tr>";
			table += "<td colspan='4' style='border:1px solid black'>";
			table += "<b>Mudanças planejadas (blackouts)</b>";
			table += "</td>";	
			table += "</tr>";			
			table += "<tr>";
			table += "<td colspan='4' style='border:1px solid black'>";			
			for (Iterator it = colBlackoutsTotais.iterator(); it.hasNext();){
				ExecucaoAtividadePeriodicaDTO execucaoAtividadePeriodicaDTO = (ExecucaoAtividadePeriodicaDTO)it.next();
				ProgramacaoAtividadeDTO programacaoAtividadeDTO = new ProgramacaoAtividadeDTO();
				programacaoAtividadeDTO.setIdProgramacaoAtividade(execucaoAtividadePeriodicaDTO.getIdProgramacaoAtividade());
				programacaoAtividadeDTO = (ProgramacaoAtividadeDTO) programacaoAtividadeService.restore(programacaoAtividadeDTO);
				if (programacaoAtividadeDTO != null){
					table += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, execucaoAtividadePeriodicaDTO.getDataExecucao(), WebUtil.getLanguage(request)) + " - " + execucaoAtividadePeriodicaDTO.getHoraExecucao() + " - Tempo: " + programacaoAtividadeDTO.getDuracaoEstimada() + " min.<br>";
					if (programacaoAtividadeDTO.getDuracaoEstimada() != null){
						totalHorasBlackoutMudancas = totalHorasBlackoutMudancas + programacaoAtividadeDTO.getDuracaoEstimada().intValue();
					}
				}
			}
			table += "<br><br>";	
			table += "</td>";	
			table += "</tr>";			
		}
		double tempoDisponibilidade = 0;
		boolean comparaComANS = false;
		if (!quedas){
			table += "<tr>";
			table += "<td colspan='4' style='border:1px solid black'>";
			table += "<b>Não há informação de queda.</b>";
			table += "</td>";	
			table += "</tr>";
		}else{
			table += "<tr>";
			table += "<td style='border:1px solid black'>";
			table += "<b>Tempo Total de Parada:</b>";
			table += "</td>";		
			String tempoPronto = converte(tempoTotalQueda);
			table += "<td colspan='3' style='border:1px solid black'>";
			table += tempoPronto;
			table += "</td>";	
			
			table += "</tr>";
			table += "<tr>";
			table += "<td style='border:1px solid black'>";
			table += "<b>Tempo Total de Blackout (por mudanças):</b>";
			table += "</td>";		
			table += "<td colspan='3' style='border:1px solid black'>";
			totalHorasBlackoutMudancasMilli = totalHorasBlackoutMudancas * 60 * 1000;
			String tempoProntoAux = converte(totalHorasBlackoutMudancasMilli);
			table += tempoProntoAux;
			//table += UtilFormatacao.formatDouble(totalHorasBlackoutMudancas,2) + " min";
			table += "</td>";	
			table += "</tr>";		
			
			table += "<tr>";
			table += "<td style='border:1px solid black'>";
			table += "<b>Tempo Total de Parada Ajustado:</b>";
			table += "</td>";		
			String tempoProntoAjustado = converte(tempoTotalQueda - totalHorasBlackoutMudancasMilli);
			table += "<td colspan='3' style='border:1px solid black'>";
			table += tempoProntoAjustado;
			table += "</td>";				
			
			table += "<tr>";
			table += "<td style='border:1px solid black'>";
			table += "<b>Tempo de Disponibilidade:</b>";
			table += "</td>";		
			
			String timeStr = converte(tempoTotalQueda - totalHorasBlackoutMudancasMilli);
			double h = 0;
			try{
				String[] hh = timeStr.split(":");
				//h = Integer.parseInt(timeStr.substring(0, 2));
				h = Integer.parseInt(hh[0]);
			}catch(Exception e){
				System.out.println("Problema de conversao de hora (hh): " + timeStr);
			}
			double m = 0;
			try{
				String[] hh = timeStr.split(":");
				//m = Integer.parseInt(timeStr.substring(3, 5));
				m = Integer.parseInt(hh[1]);
			}catch(Exception e){
				System.out.println("Problema de conversao de hora (mm): " + timeStr);
			}
			
			double convert = h + (m / 60);
			
			tempoDisponibilidade = (100 - (convert / qtdeHorasTotais) * 100);
			tempoPronto = UtilFormatacao.formatDouble(tempoDisponibilidade,2) + "%";
			
			table += "<td colspan='3' style='border:1px solid black'>";
			table += tempoPronto;
			table += "</td>";	
			table += "</tr>";		
			
			if (acordoNivelServicoDTO != null){
				if (acordoNivelServicoDTO.getDisponibilidade() != null){
					table += "<tr>";
					table += "<td style='border:1px solid black'>";
					table += "<b>Acordo de Nível de Serviço:</b>";
					table += "</td>";	
					
					table += "<td colspan='3' style='border:1px solid black'>";
					table += UtilFormatacao.formatDouble(acordoNivelServicoDTO.getDisponibilidade(),2) + "%";;
					table += "</td>";	
					table += "</tr>";	
					
					comparaComANS = true;
				}
			}
		}
		/*
		if (colLogs != null && colLogs.size() > 0){
			for (Iterator itLog = colLogs.iterator(); itLog.hasNext();){
				CentreonLogDTO log = (CentreonLogDTO) itLog.next();
				java.util.Date data = transformCTime(log.getCtime());
				table += "<tr>";
				table += "<td style='border:1px solid black'>";
					table += "" + log.getCtime() + "";
				table += "</td>";
				table += "<td style='border:1px solid black'>";
					table += "" + UtilDatas.dateToSTR(data) + "";
				table += "</td>";						
				table += "<td style='border:1px solid black'>";
					table += "" + UtilDatas.formatHoraFormatadaHHMMSSStr(data) + "";
				table += "</td>";
				table += "<td style='border:1px solid black'>";
					table += "" + log.getHost_name() + "";
				table += "</td>";
				table += "<td style='border:1px solid black'>";
					table += "" + log.getService_description() + "";
				table += "</td>";
				table += "<td style='border:1px solid black'>";
					table += "" + log.getOutput() + "";
				table += "</td>";	
				table += "<td style='border:1px solid black'>";
					table += "" + log.getStatus() + "";
				table += "</td>";							
				table += "</tr>";						
			}
		}
		*/
		if (tempoDisponibilidade > 0){
			double tempoIndisp = (100 - tempoDisponibilidade);
			DefaultPieDataset datasetPie = new DefaultPieDataset();
			datasetPie.setValue(UtilI18N.internacionaliza(request, "recurso.disponivel") + " (" + UtilFormatacao.formatDouble(tempoDisponibilidade,2) + "%)", new Double(tempoDisponibilidade));
			datasetPie.setValue(UtilI18N.internacionaliza(request, "recurso.indisponivel") + " (" + UtilFormatacao.formatDouble(tempoIndisp,2) + "%)", new Double(tempoIndisp));
			
			JFreeChart chartX = ChartFactory.createPieChart(
					UtilI18N.internacionaliza(request, "recurso.disponibilidade"),  // chart title
		            datasetPie,             // data
		            true,               // include legend
		            false,
		            false
		        );

		        PiePlot plotPie = (PiePlot) chartX.getPlot();
		        plotPie.setLabelFont(new Font("SansSerif", Font.PLAIN, 9));
		        plotPie.setNoDataMessage(UtilI18N.internacionaliza(request,"sla.avaliacao.naohadados"));
		        plotPie.setCircular(false);
		        plotPie.setLabelGap(0.02);	
		        
		        plotPie.setSectionPaint(UtilI18N.internacionaliza(request, "recurso.disponivel") + " (" + UtilFormatacao.formatDouble(tempoDisponibilidade,2) + "%)", Color.green);
		        plotPie.setSectionPaint(UtilI18N.internacionaliza(request, "recurso.indisponivel") + " (" + UtilFormatacao.formatDouble(tempoIndisp,2) + "%)", Color.red);
		    
		    File fileDir = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario());
		    if (!fileDir.exists()){
		    	fileDir.mkdirs();
		    }
	        String nomeImgAval2 = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalDispRecs_" + recursoDTO.getIdRecurso() + ".png";
	        String nomeImgAvalRel2 = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalDispRecs_" + recursoDTO.getIdRecurso() + ".png";
			File arquivo2 = new File(nomeImgAval2);
			if (arquivo2.exists()) {
				arquivo2.delete();
			}			        
		    ChartUtilities.saveChartAsPNG(arquivo2, chartX, 400, 300);	
		    
		    String qtdeStr = "4";
		    if (comparaComANS){
		    	qtdeStr = "2";
		    }
		    
		    table += "<tr>";
			table += "<td colspan='" + qtdeStr + "' style='border:1px solid black'>";
			table += "<img src='" + nomeImgAvalRel2 + "' border='0'/>";
			table += "</td>";	
			if (comparaComANS){
				table += "<td colspan='" + qtdeStr + "' style='border:1px solid black; vertical-align:middle'>";
					table += "<table width='100%'>";
						table += "<tr>";
							table += "<td style='font-size:14px; border:1px solid black'>";
								table += "% Disponibilidade:";
							table += "</td>";		
							if (tempoDisponibilidade >= acordoNivelServicoDTO.getDisponibilidade()){
								table += "<td style='font-size:14px; border:1px solid black; color:blue'>";
							}else{
								table += "<td style='font-size:14px; border:1px solid black; color:red'>";
							}
								table += "<b>" + UtilFormatacao.formatDouble(tempoDisponibilidade,2) + "%</b>";
							table += "</td>";
							table += "<td style='font-size:14px; border:1px solid black'>";
							if (tempoDisponibilidade >= acordoNivelServicoDTO.getDisponibilidade()){
								table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/tickg.png' border='0'/>";
							}else{
								table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/exclamation02.gif' border='0'/>";
							}
							table += "</td>";							
						table += "</tr>";
						table += "<tr>";
							table += "<td style='font-size:14px; border:1px solid black'>";
								table += "Acordo:";
							table += "</td>";						
							table += "<td style='font-size:14px; border:1px solid black'>";
								table += UtilFormatacao.formatDouble(acordoNivelServicoDTO.getDisponibilidade(),2) + "%";
							table += "</td>";
						table += "</tr>";						
					table += "</table>";
				table += "</td>";				
			}
			table += "</tr>";	
		}
		return table;
	}
	public String geraAvaliacaoNagiosCentreon(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response, RecursoDTO recursoDTO, Date dataInicio, Date dataFim, UsuarioDTO usuarioDto, AcordoNivelServicoDTO acordoNivelServicoDTO) throws ServiceException, Exception{
		NagiosConexaoService nagiosConexaoService = (NagiosConexaoService) ServiceLocator.getInstance().getService(NagiosConexaoService.class, null);
		CalendarioService calendarioService = (CalendarioService) ServiceLocator.getInstance().getService(CalendarioService.class, null);
		CentreonLogService centreonLogService = (CentreonLogService) ServiceLocator.getInstance().getService(CentreonLogService.class, null);
		RequisicaoMudancaItemConfiguracaoService requisicaoMudancaItemConfiguracaoService = (RequisicaoMudancaItemConfiguracaoService)ServiceLocator.getInstance().getService(RequisicaoMudancaItemConfiguracaoService.class, null);
		ExecucaoAtividadePeriodicaService execucaoAtividadePeriodicaService = (ExecucaoAtividadePeriodicaService)ServiceLocator.getInstance().getService(ExecucaoAtividadePeriodicaService.class, null);
		ProgramacaoAtividadeService programacaoAtividadeService = (ProgramacaoAtividadeService)ServiceLocator.getInstance().getService(ProgramacaoAtividadeService.class, null);
		NagiosConexaoDTO nagiosConexaoDTO = new NagiosConexaoDTO();
		if (recursoDTO.getIdNagiosConexao() == null){
			document.alert("Não há configuração de conexão ao Nagios para o recursos: " + recursoDTO.getNomeRecurso());
		}
		nagiosConexaoDTO.setIdNagiosConexao(recursoDTO.getIdNagiosConexao());
		if (recursoDTO.getIdNagiosConexao() != null){
			nagiosConexaoDTO = (NagiosConexaoDTO) nagiosConexaoService.restore(nagiosConexaoDTO);
		}
		if (nagiosConexaoDTO == null){
			document.alert("Não há configuração correta de conexão ao Nagios para o recursos: " + recursoDTO.getNomeRecurso());
		}
		
		Collection colMudancasIC = null;
		Collection colBlackoutsTotais = new ArrayList();
		if (recursoDTO.getIdItemConfiguracao() != null){
			colMudancasIC = requisicaoMudancaItemConfiguracaoService.findByIdItemConfiguracao(recursoDTO.getIdItemConfiguracao());
			if (colMudancasIC != null){
				for (Iterator it = colMudancasIC.iterator(); it.hasNext();){
					RequisicaoMudancaItemConfiguracaoDTO requisicaoMudancaItemConfiguracaoDTO = (RequisicaoMudancaItemConfiguracaoDTO)it.next();
					Collection colBlackoutMud = execucaoAtividadePeriodicaService.findBlackoutByIdMudancaAndPeriodo(requisicaoMudancaItemConfiguracaoDTO.getIdRequisicaoMudanca(), dataInicio, dataFim);
					if (colBlackoutMud != null){
						colBlackoutsTotais.addAll(colBlackoutMud);
					}
				}
			}
		}
		
		double qtdeHorasTotais = 0;
		if (recursoDTO.getIdCalendario() != null){
			CalendarioDTO calendarioDTO = new CalendarioDTO();
			calendarioDTO.setIdCalendario(recursoDTO.getIdCalendario());
			calendarioDTO = (CalendarioDTO) calendarioService.restore(calendarioDTO);
			Date d = dataInicio;
			while(d.compareTo(dataFim) <= 0){
				System.out.println("Verificando---> " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, d, WebUtil.getLanguage(request)));
				String diaSemana = UtilDatas.getDiaSemana(new java.util.Date(d.getTime()));
				if (diaSemana.equalsIgnoreCase("dom")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaDom());
				}
				if (diaSemana.equalsIgnoreCase("seg")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaSeg());
				}
				if (diaSemana.equalsIgnoreCase("ter")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaTer());
				}	
				if (diaSemana.equalsIgnoreCase("qua")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaQua());
				}	
				if (diaSemana.equalsIgnoreCase("qui")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaQui());
				}
				if (diaSemana.equalsIgnoreCase("sex")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaSex());
				}
				if (diaSemana.equalsIgnoreCase("sab")){
					qtdeHorasTotais = qtdeHorasTotais + getQuantidadeHoras(calendarioDTO.getIdJornadaSab());
				}					
				d = new Date(UtilDatas.alteraData(d, 1, Calendar.DAY_OF_MONTH).getTime());
			}
		}
		System.out.println(recursoDTO.getNomeRecurso() + " --> Quantidade Total de Horas ---> " + qtdeHorasTotais);
		String table = "";
		table += "<tr>";
		table += "<td colspan='4'>";
			table += "&nbsp;";
		table += "</td>";
		table += "</tr>";				
		table += "<tr>";
		table += "<td colspan='4' style='background-color: lightgrey; font-size:14px;'>";
			table += "Recurso: <b>" + recursoDTO.getNomeRecurso() + "</b>";
		table += "</td>";
		table += "</tr>";	
		
		Timestamp tsFinal = null;
		Timestamp tsInicio = null;
		if (recursoDTO.getHoraFimFunc() != null && !recursoDTO.getHoraFimFunc().trim().equalsIgnoreCase("")){
			tsFinal = UtilDatas.strToTimestamp("00/00/0000 " + recursoDTO.getHoraFimFunc() + ":00");
		}
		if (recursoDTO.getHoraInicioFunc() != null && !recursoDTO.getHoraInicioFunc().trim().equalsIgnoreCase("")){
			tsInicio = UtilDatas.strToTimestamp("00/00/0000 " + recursoDTO.getHoraInicioFunc() + ":00");
		}
		
		long timeAtividadeMill = 0;
		if (tsFinal != null && tsInicio != null){
			timeAtividadeMill = tsFinal.getTime() - tsInicio.getTime();
			String tempoFmt = converte(timeAtividadeMill);
			table += "<tr>";
			table += "<td colspan='4'>";
				table += "<b>Tempo de Maior Atividade: " + recursoDTO.getHoraInicioFunc() + " a " + recursoDTO.getHoraFimFunc() + " (" + tempoFmt + "/dia)</b>";
			table += "</td>";
			table += "</tr>";					
		}
		table += "<tr>";
		table += "<td colspan='4'>";
			table += "<b>Volume de Horas do Período: " + UtilFormatacao.formatDouble(qtdeHorasTotais,2) + "</b>";
		table += "</td>";
		table += "</tr>";				
		
		Collection colLogs = null;
		if (nagiosConexaoDTO != null && nagiosConexaoDTO.getNomeJNDI() != null && !nagiosConexaoDTO.getNomeJNDI().trim().equalsIgnoreCase("")){
			centreonLogService.setJndiName(nagiosConexaoDTO.getNomeJNDI());
			if (recursoDTO.getServiceName() == null || recursoDTO.getServiceName().trim().equalsIgnoreCase("")){
				colLogs = centreonLogService.findByHostServiceStatusAndServiceNull(nagiosConexaoDTO.getNomeJNDI(), recursoDTO.getHostName(), null, dataInicio, dataFim);
			}else{
				colLogs = centreonLogService.findByHostServiceStatus(nagiosConexaoDTO.getNomeJNDI(), recursoDTO.getHostName(), recursoDTO.getServiceName(), null, dataInicio, dataFim);
			}
		}
		table += "<tr>";
		table += "<td colspan='4'>";
			table += "<b>Quedas</b>";
		table += "</td>";
		table += "</tr>";		
		table += "<tr>";
		table += "<td style='border:1px solid black'>";
			table += "<b>Alerta</b>";
		table += "</td>";				
		table += "<td style='border:1px solid black'>";
			table += "<b>Tempo de parada</b>";
		table += "</td>";
		table += "<td style='border:1px solid black'>";
			table += "<b>Inicio</b>";
		table += "</td>";
		table += "<td style='border:1px solid black'>";
			table += "<b>Fim</b>";
		table += "</td>";								
		table += "</tr>";				
		java.sql.Timestamp horaQueda = null;
		boolean quedas = false;
		long tempoTotalQueda = 0;
		if (colLogs != null && colLogs.size() > 0){
			for (Iterator itLog = colLogs.iterator(); itLog.hasNext();){
				CentreonLogDTO log = (CentreonLogDTO) itLog.next();
				if ("DOWN".equalsIgnoreCase(log.getStatus().trim())){
					if (horaQueda == null){
						horaQueda = new java.sql.Timestamp(transformCTime(log.getCtime()).getTime());
						quedas = true;
					}
				}
				if ("UP".equalsIgnoreCase(log.getStatus().trim())){
					java.sql.Timestamp horaRetorno = new java.sql.Timestamp(transformCTime(log.getCtime()).getTime());
					if (horaQueda != null){
						long tempoDeQueda = UtilDatas.calculaDiferencaTempoEmMilisegundos(horaRetorno, horaQueda);
						String tempoPronto = converte(tempoDeQueda);
				        
						table += "<tr>";
						table += "<td style='border:1px solid black'>";
							table += "&nbsp;";
						table += "</td>";								
						table += "<td style='border:1px solid black'>";
							table += "" + tempoPronto + "";
						table += "</td>";
						table += "<td style='border:1px solid black'>";
							table += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, horaQueda, WebUtil.getLanguage(request)) + " " + UtilDatas.formatHoraFormatadaHHMMSSStr(horaQueda) + "";
						table += "</td>";
						table += "<td style='border:1px solid black'>";
							table += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, horaRetorno, WebUtil.getLanguage(request)) + " " +  UtilDatas.formatHoraFormatadaHHMMSSStr(horaRetorno) + "";
						table += "</td>";								
						table += "</tr>";
						
						horaQueda = null;
						tempoTotalQueda = tempoTotalQueda + tempoDeQueda;
					}
				}						
			}
		}
		if (horaQueda != null){
			java.sql.Timestamp horaRetorno = UtilDatas.getDataHoraAtual();
			if (horaQueda != null){
				long tempoTotalDeQueda = UtilDatas.calculaDiferencaTempoEmMilisegundos(horaRetorno, horaQueda);
				String tempoPronto = converte(tempoTotalDeQueda);
		        
				table += "<tr>";
				table += "<td style='border:1px solid black'>";
					table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + 
							br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/b.gif' border='0'/>";
				table += "</td>";						
				table += "<td style='border:1px solid black'>";
					table += "" + tempoPronto + "";
				table += "</td>";
				table += "<td style='border:1px solid black'>";
					table += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, horaQueda, WebUtil.getLanguage(request)) + " " + UtilDatas.formatHoraFormatadaHHMMSSStr(horaQueda) + "";
				table += "</td>";
				table += "<td style='border:1px solid black'>";
					table += "" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, horaRetorno, WebUtil.getLanguage(request)) + " " +  UtilDatas.formatHoraFormatadaHHMMSSStr(horaRetorno) + "";
				table += "</td>";								
				table += "</tr>";
				
				horaQueda = null;
			}					
		}
		long totalHorasBlackoutMudancas = 0;
		long totalHorasBlackoutMudancasMilli = 0;
		if (colBlackoutsTotais.size() > 0){
			table += "<tr>";
			table += "<td colspan='4' style='border:1px solid black'>";
			table += "<b>Mudanças planejadas (blackouts)</b>";
			table += "</td>";	
			table += "</tr>";			
			table += "<tr>";
			table += "<td colspan='4' style='border:1px solid black'>";			
			for (Iterator it = colBlackoutsTotais.iterator(); it.hasNext();){
				ExecucaoAtividadePeriodicaDTO execucaoAtividadePeriodicaDTO = (ExecucaoAtividadePeriodicaDTO)it.next();
				ProgramacaoAtividadeDTO programacaoAtividadeDTO = new ProgramacaoAtividadeDTO();
				programacaoAtividadeDTO.setIdProgramacaoAtividade(execucaoAtividadePeriodicaDTO.getIdProgramacaoAtividade());
				programacaoAtividadeDTO = (ProgramacaoAtividadeDTO) programacaoAtividadeService.restore(programacaoAtividadeDTO);
				if (programacaoAtividadeDTO != null){
					table += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, execucaoAtividadePeriodicaDTO.getDataExecucao(), WebUtil.getLanguage(request)) + " - " + execucaoAtividadePeriodicaDTO.getHoraExecucao() + " - Tempo: " + programacaoAtividadeDTO.getDuracaoEstimada() + " min.<br>";
					if (programacaoAtividadeDTO.getDuracaoEstimada() != null){
						totalHorasBlackoutMudancas = totalHorasBlackoutMudancas + programacaoAtividadeDTO.getDuracaoEstimada().intValue();
					}
				}
			}
			table += "<br><br>";	
			table += "</td>";	
			table += "</tr>";			
		}
		double tempoDisponibilidade = 0;
		boolean comparaComANS = false;
		if (!quedas){
			table += "<tr>";
			table += "<td colspan='4' style='border:1px solid black'>";
			table += "<b>Não há informação de queda.</b>";
			table += "</td>";	
			table += "</tr>";
		}else{
			table += "<tr>";
			table += "<td style='border:1px solid black'>";
			table += "<b>Tempo Total de Parada:</b>";
			table += "</td>";		
			String tempoPronto = converte(tempoTotalQueda);
			table += "<td colspan='3' style='border:1px solid black'>";
			table += tempoPronto;
			table += "</td>";	
			
			table += "</tr>";
			table += "<tr>";
			table += "<td style='border:1px solid black'>";
			table += "<b>Tempo Total de Blackout (por mudanças):</b>";
			table += "</td>";		
			table += "<td colspan='3' style='border:1px solid black'>";
			totalHorasBlackoutMudancasMilli = totalHorasBlackoutMudancas * 60 * 1000;
			String tempoProntoAux = converte(totalHorasBlackoutMudancasMilli);
			table += tempoProntoAux;
			//table += UtilFormatacao.formatDouble(totalHorasBlackoutMudancas,2) + " min";
			table += "</td>";	
			table += "</tr>";		
			
			table += "<tr>";
			table += "<td style='border:1px solid black'>";
			table += "<b>Tempo Total de Parada Ajustado:</b>";
			table += "</td>";		
			String tempoProntoAjustado = converte(tempoTotalQueda - totalHorasBlackoutMudancasMilli);
			table += "<td colspan='3' style='border:1px solid black'>";
			table += tempoProntoAjustado;
			table += "</td>";				
			
			table += "<tr>";
			table += "<td style='border:1px solid black'>";
			table += "<b>Tempo de Disponibilidade:</b>";
			table += "</td>";		
			
			String timeStr = converte(tempoTotalQueda - totalHorasBlackoutMudancasMilli);
			double h = 0;
			try{
				String[] hh = timeStr.split(":");
				//h = Integer.parseInt(timeStr.substring(0, 2));
				h = Integer.parseInt(hh[0]);
			}catch(Exception e){
				System.out.println("Problema de conversao de hora (hh): " + timeStr);
			}
			double m = 0;
			try{
				String[] hh = timeStr.split(":");
				//m = Integer.parseInt(timeStr.substring(3, 5));
				m = Integer.parseInt(hh[1]);
			}catch(Exception e){
				System.out.println("Problema de conversao de hora (mm): " + timeStr);
			}
			
			double convert = h + (m / 60);
			
			tempoDisponibilidade = (100 - (convert / qtdeHorasTotais) * 100);
			tempoPronto = UtilFormatacao.formatDouble(tempoDisponibilidade,2) + "%";
			
			table += "<td colspan='3' style='border:1px solid black'>";
			table += tempoPronto;
			table += "</td>";	
			table += "</tr>";		
			
			if (acordoNivelServicoDTO != null){
				if (acordoNivelServicoDTO.getDisponibilidade() != null){
					table += "<tr>";
					table += "<td style='border:1px solid black'>";
					table += "<b>Acordo de Nível de Serviço:</b>";
					table += "</td>";	
					
					table += "<td colspan='3' style='border:1px solid black'>";
					table += UtilFormatacao.formatDouble(acordoNivelServicoDTO.getDisponibilidade(),2) + "%";;
					table += "</td>";	
					table += "</tr>";	
					
					comparaComANS = true;
				}
			}
		}
		/*
		if (colLogs != null && colLogs.size() > 0){
			for (Iterator itLog = colLogs.iterator(); itLog.hasNext();){
				CentreonLogDTO log = (CentreonLogDTO) itLog.next();
				java.util.Date data = transformCTime(log.getCtime());
				table += "<tr>";
				table += "<td style='border:1px solid black'>";
					table += "" + log.getCtime() + "";
				table += "</td>";
				table += "<td style='border:1px solid black'>";
					table += "" + UtilDatas.dateToSTR(data) + "";
				table += "</td>";						
				table += "<td style='border:1px solid black'>";
					table += "" + UtilDatas.formatHoraFormatadaHHMMSSStr(data) + "";
				table += "</td>";
				table += "<td style='border:1px solid black'>";
					table += "" + log.getHost_name() + "";
				table += "</td>";
				table += "<td style='border:1px solid black'>";
					table += "" + log.getService_description() + "";
				table += "</td>";
				table += "<td style='border:1px solid black'>";
					table += "" + log.getOutput() + "";
				table += "</td>";	
				table += "<td style='border:1px solid black'>";
					table += "" + log.getStatus() + "";
				table += "</td>";							
				table += "</tr>";						
			}
		}
		*/
		if (tempoDisponibilidade > 0){
			double tempoIndisp = (100 - tempoDisponibilidade);
			DefaultPieDataset datasetPie = new DefaultPieDataset();
			datasetPie.setValue(UtilI18N.internacionaliza(request, "recurso.disponivel") + " (" + UtilFormatacao.formatDouble(tempoDisponibilidade,2) + "%)", new Double(tempoDisponibilidade));
			datasetPie.setValue(UtilI18N.internacionaliza(request, "recurso.indisponivel") + " (" + UtilFormatacao.formatDouble(tempoIndisp,2) + "%)", new Double(tempoIndisp));
			
			JFreeChart chartX = ChartFactory.createPieChart(
					UtilI18N.internacionaliza(request, "recurso.disponibilidade"),  // chart title
		            datasetPie,             // data
		            true,               // include legend
		            false,
		            false
		        );

		        PiePlot plotPie = (PiePlot) chartX.getPlot();
		        plotPie.setLabelFont(new Font("SansSerif", Font.PLAIN, 9));
		        plotPie.setNoDataMessage(UtilI18N.internacionaliza(request,"sla.avaliacao.naohadados"));
		        plotPie.setCircular(false);
		        plotPie.setLabelGap(0.02);	
		        
		        plotPie.setSectionPaint(UtilI18N.internacionaliza(request, "recurso.disponivel") + " (" + UtilFormatacao.formatDouble(tempoDisponibilidade,2) + "%)", Color.green);
		        plotPie.setSectionPaint(UtilI18N.internacionaliza(request, "recurso.indisponivel") + " (" + UtilFormatacao.formatDouble(tempoIndisp,2) + "%)", Color.red);
		    
		    File fileDir = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario());
		    if (!fileDir.exists()){
		    	fileDir.mkdirs();
		    }
	        String nomeImgAval2 = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalDispRecs_" + recursoDTO.getIdRecurso() + ".png";
	        String nomeImgAvalRel2 = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles/" + usuarioDto.getIdUsuario() + "/avalDispRecs_" + recursoDTO.getIdRecurso() + ".png";
			File arquivo2 = new File(nomeImgAval2);
			if (arquivo2.exists()) {
				arquivo2.delete();
			}			        
		    ChartUtilities.saveChartAsPNG(arquivo2, chartX, 400, 300);	
		    
		    String qtdeStr = "4";
		    if (comparaComANS){
		    	qtdeStr = "2";
		    }
		    
		    table += "<tr>";
			table += "<td colspan='" + qtdeStr + "' style='border:1px solid black'>";
			table += "<img src='" + nomeImgAvalRel2 + "' border='0'/>";
			table += "</td>";	
			if (comparaComANS){
				table += "<td colspan='" + qtdeStr + "' style='border:1px solid black; vertical-align:middle'>";
					table += "<table width='100%'>";
						table += "<tr>";
							table += "<td style='font-size:14px; border:1px solid black'>";
								table += "% Disponibilidade:";
							table += "</td>";		
							if (tempoDisponibilidade >= acordoNivelServicoDTO.getDisponibilidade()){
								table += "<td style='font-size:14px; border:1px solid black; color:blue'>";
							}else{
								table += "<td style='font-size:14px; border:1px solid black; color:red'>";
							}
								table += "<b>" + UtilFormatacao.formatDouble(tempoDisponibilidade,2) + "%</b>";
							table += "</td>";
							table += "<td style='font-size:14px; border:1px solid black'>";
							if (tempoDisponibilidade >= acordoNivelServicoDTO.getDisponibilidade()){
								table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/tickg.png' border='0'/>";
							}else{
								table += "<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/exclamation02.gif' border='0'/>";
							}
							table += "</td>";							
						table += "</tr>";
						table += "<tr>";
							table += "<td style='font-size:14px; border:1px solid black'>";
								table += "Acordo:";
							table += "</td>";						
							table += "<td style='font-size:14px; border:1px solid black'>";
								table += UtilFormatacao.formatDouble(acordoNivelServicoDTO.getDisponibilidade(),2) + "%";
							table += "</td>";
						table += "</tr>";						
					table += "</table>";
				table += "</td>";				
			}
			table += "</tr>";	
		}
		return table;
	}
	public String converte(long ms) {
		int s, m, h;
		String tempoPronto = "";

		s = (int)ms / 1000;
		ms -= s * 1000;
		m = s / 60;
		s -= m * 60;
		h = m / 60;
		m -= h * 60;

		tempoPronto += (h < 10) ? "0" + h : h;
		tempoPronto += (m < 10) ? ":0" + m : ":" +  m;
		tempoPronto += (s < 10) ? ":0" + s : ":" + s;
		tempoPronto += (ms < 10) ? ".00" + ms : (ms < 100) ? ".0" + ms : "." +  ms;
		
		return tempoPronto;
	}	
	public java.util.Date transformCTime(Long ctime) throws ParseException{
		if (ctime == null){
			return null;
		}
		long l = (ctime.longValue() * 1000);
		java.util.Date data = new java.util.Date(l);
		return data;
	}
	public double getQuantidadeHoras(Integer idJornada) throws ServiceException, Exception{
		if (idJornada == null){
			return 0;
		}
		JornadaTrabalhoService jornadaTrabalhoService = (JornadaTrabalhoService) ServiceLocator.getInstance().getService(JornadaTrabalhoService.class, null);
		JornadaTrabalhoDTO jornadaTrabalhoDto = new JornadaTrabalhoDTO();
		jornadaTrabalhoDto.setIdJornada(idJornada);
		jornadaTrabalhoDto = (JornadaTrabalhoDTO) jornadaTrabalhoService.restore(jornadaTrabalhoDto);
		
		if (jornadaTrabalhoDto != null){
			long qtde1 = 0;
			if (jornadaTrabalhoDto.getInicio1() != null && !jornadaTrabalhoDto.getInicio1().trim().equalsIgnoreCase("") &&
					jornadaTrabalhoDto.getTermino1() != null && !jornadaTrabalhoDto.getTermino1().trim().equalsIgnoreCase("")){
				qtde1 = calcHoras(jornadaTrabalhoDto.getInicio1(), jornadaTrabalhoDto.getTermino1());
			}
			long qtde2 = 0;
			if (jornadaTrabalhoDto.getInicio2() != null && !jornadaTrabalhoDto.getInicio2().trim().equalsIgnoreCase("") &&
					jornadaTrabalhoDto.getTermino2() != null && !jornadaTrabalhoDto.getTermino2().trim().equalsIgnoreCase("")){
				qtde2 = calcHoras(jornadaTrabalhoDto.getInicio2(), jornadaTrabalhoDto.getTermino2());
			}
			long qtde3 = 0;
			if (jornadaTrabalhoDto.getInicio3() != null && !jornadaTrabalhoDto.getInicio3().trim().equalsIgnoreCase("") &&
					jornadaTrabalhoDto.getTermino3() != null && !jornadaTrabalhoDto.getTermino3().trim().equalsIgnoreCase("")){
				qtde3 = calcHoras(jornadaTrabalhoDto.getInicio3(), jornadaTrabalhoDto.getTermino3());
			}
			long qtde4 = 0;
			if (jornadaTrabalhoDto.getInicio4() != null && !jornadaTrabalhoDto.getInicio4().trim().equalsIgnoreCase("") &&
					jornadaTrabalhoDto.getTermino4() != null && !jornadaTrabalhoDto.getTermino4().trim().equalsIgnoreCase("")){
				qtde4 = calcHoras(jornadaTrabalhoDto.getInicio4(), jornadaTrabalhoDto.getTermino4());
			}
			long qtde5 = 0;
			if (jornadaTrabalhoDto.getInicio5() != null && !jornadaTrabalhoDto.getInicio5().trim().equalsIgnoreCase("") &&
					jornadaTrabalhoDto.getTermino5() != null && !jornadaTrabalhoDto.getTermino5().trim().equalsIgnoreCase("")){
				qtde5 = calcHoras(jornadaTrabalhoDto.getInicio5(), jornadaTrabalhoDto.getTermino5());
			}
			long qtde = qtde1 + qtde2 + qtde3 + qtde4 + qtde5;
			String timeStr = converte(qtde);
			double h = Integer.parseInt(timeStr.substring(0, 2));
			double m = Integer.parseInt(timeStr.substring(3, 5));
			
			double convert = h + (m / 60);
			return convert;
		}
		return 0;
	}
	
	public long calcHoras(String data1, String data2) throws ServiceException, Exception{
		Timestamp tsFinal = null;
		Timestamp tsInicio = null;
		if (data2 != null && !data2.trim().equalsIgnoreCase("")){
			tsFinal = UtilDatas.strToTimestamp("00/00/0000 " + data2 + ":00");
		}
		if (data1 != null && !data1.trim().equalsIgnoreCase("")){
			tsInicio = UtilDatas.strToTimestamp("00/00/0000 " + data1 + ":00");
		}
		
		long timeAtividadeMill = 0;
		if (tsFinal != null && tsInicio != null){
			timeAtividadeMill = tsFinal.getTime() - tsInicio.getTime();
			return timeAtividadeMill;
		}
		return 0;
	}
	
	@Override
	public Class getBeanClass() {
		return RecursoAvaliacaoDTO.class;
	}
	public static void main(String[] args) throws NumberFormatException, ParseException {
		RecursoAvaliacao r = new RecursoAvaliacao();
		System.out.println("System.currentTimeMillis() (ANTES): ::::::>>>> " + System.currentTimeMillis());
		System.out.println("Hora Atual: ::::::>>>> " + UtilDatas.formatHoraFormatadaHHMMSSStr(new java.util.Date(System.currentTimeMillis())));
		String str = "" + (System.currentTimeMillis() / 1000);
		Long aux = new Long(str);
		System.out.println("Tempo: ::::::>>>> " + str + " -> " + aux);
		
		Long l = aux * 1000;
		System.out.println("System.currentTimeMillis() (DEPOIS): ::::::>>>> " + l);
		System.out.println("Tempo: 2222 ::::::>>>> " + str + " -> " + UtilDatas.formatHoraFormatadaHHMMSSStr(new java.util.Date(l.longValue())));
		
		System.out.println("Tempo: XXXXXXXXXXX>>>> " + str + " -> " + UtilDatas.dateToSTR(r.transformCTime(aux)) + "   " + UtilDatas.formatHoraFormatadaHHMMSSStr(r.transformCTime(aux)));
	}
}
