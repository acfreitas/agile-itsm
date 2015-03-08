package br.com.centralit.citcorpore.ajaxForms;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.EventMonitorDTO;
import br.com.centralit.citcorpore.bean.EventTotalsDTO;
import br.com.centralit.citcorpore.bean.GrupoRecursosDTO;
import br.com.centralit.citcorpore.bean.RecursoDTO;
import br.com.centralit.citcorpore.negocio.GrupoRecursosService;
import br.com.centralit.citcorpore.negocio.RecursoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.nagios.Host;
import br.com.centralit.nagios.MonitoraNagios;
import br.com.centralit.nagios.Service;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

public class EventMonitor extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		geraInformacoesMonitoramento(document, request, response);
	}

	public void geraInformacoesMonitoramento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoRecursosService grupoRecursosService = (GrupoRecursosService) ServiceLocator.getInstance().getService(GrupoRecursosService.class, null);
		RecursoService recursoService = (RecursoService) ServiceLocator.getInstance().getService(RecursoService.class, null);
		Collection colGrupos = grupoRecursosService.list();
		String strTable = "<table>";
		strTable += "<tr>";
		boolean primVez = true;
		int qtdeTotalHostsDown = 0;
		int qtdeTotalServicesCritical = 0;
		List lstServicesCritical = new ArrayList();
		if (colGrupos != null) {
			for (Iterator it = colGrupos.iterator(); it.hasNext();) {
				GrupoRecursosDTO grupoRecursosDTO = (GrupoRecursosDTO) it.next();
				if (grupoRecursosDTO.getDeleted() == null || grupoRecursosDTO.getDeleted().equalsIgnoreCase("n")) {
					if (primVez) {
						strTable += "<td style='text-align:center'>";
						strTable += "<div style='border:1px solid black; height:115px; #complemenCoresProbl#'>";
						strTable += "<u>Current problems</u><br>";
						strTable += "<div id='divProblemasCorrentes'>";
						strTable += "#divProblemasCorrentes#";
						strTable += "</div>";
						strTable += "</div>";
						strTable += "</td>";
					}
					primVez = false;
					Collection colRecursos = recursoService.findByIdGrupoRecurso(grupoRecursosDTO.getIdGrupoRecurso());
					int hostDown = 0;
					int hostUp = 0;
					int hostPending = 0;
					int qtdeServCritical = 0;
					Date ultimoDownGrp = null;
					EventTotalsDTO eventTotalsDTO = new EventTotalsDTO();
					if (colRecursos != null) {
						for (Iterator itRec = colRecursos.iterator(); itRec.hasNext();) {
							RecursoDTO recursoDTO = (RecursoDTO) itRec.next();
							if (recursoDTO.getHostName() != null && !recursoDTO.getHostName().trim().equalsIgnoreCase("")) {
								Date ultimoDown = this.getUltimoDown(recursoDTO.getHostName());
								if (ultimoDownGrp == null) {
									ultimoDownGrp = ultimoDown;
								} else {
									if (ultimoDown != null) {
										if (ultimoDown.after(ultimoDownGrp)) {
											ultimoDownGrp = ultimoDown;
										}
									}
								}
								String strSit = getSituacaoServidor(recursoDTO.getHostName());
								if (strSit.equalsIgnoreCase("UP")) {
									hostUp++;
								} else if (strSit.equalsIgnoreCase("DOWN")) {
									hostDown++;
									qtdeTotalHostsDown++;
								} else if (strSit.equalsIgnoreCase("PENDING")) {
									hostPending++;
								}
								eventTotalsDTO = getQtdeServicosCriticalByServidor(recursoDTO.getHostName(), lstServicesCritical);
								qtdeTotalServicesCritical = qtdeTotalServicesCritical + eventTotalsDTO.getQtdeCritical();
							}
						}
					}
					strTable += "<td style='text-align:center'>";
					String cor = "";
					String corBorda = "1px solid black";
					if (hostDown == 0) {
						cor = "background-color:lightgreen";
					} else {
						cor = "background-color:orange";
						corBorda = "3px dotted red";
					}
					strTable += "<div style='border:" + corBorda + "; height:115px;" + cor + ";cursor:pointer' onclick='mostraHostsGrupo(\"" + grupoRecursosDTO.getIdGrupoRecurso() + "\", \""
							+ grupoRecursosDTO.getNomeGrupoRecurso() + "\")'>";
					strTable += "<u>" + grupoRecursosDTO.getNomeGrupoRecurso() + "</u><br>";
					if (hostDown > 0) {
						strTable += "<table width='100%'>";
						strTable += "<tr>";
						strTable += "<td style='text-align: center;'>";
						strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/b.gif" + "' border='0'/>";
						strTable += "</td>";
						strTable += "<td colspan='2' style='text-align: center;background-color:red'>";
						strTable += hostDown;
						strTable += "</td>";
						strTable += "<td colspan='2' style='text-align: center;background-color:green'>";
						strTable += hostUp;
						strTable += "</td>";
						strTable += "</tr>";
						strTable += "<tr>";
						strTable += "<td style='text-align: center;'>";
						if (eventTotalsDTO.getQtdeCritical() > 0) {
							strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'/><img src='"
									+ Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/b.gif" + "' border='0'/>" + eventTotalsDTO.getQtdeCritical();
						} else {
							strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'/>"
									+ eventTotalsDTO.getQtdeCritical();
						}
						strTable += "</td>";
						strTable += "<td style='text-align: center;'>";
						strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolaverde.png' border='0'/>"
								+ eventTotalsDTO.getQtdeOk();
						strTable += "</td>";
						strTable += "<td style='text-align: center;'>";
						strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/amarelo20x20.gif' border='0'/>"
								+ eventTotalsDTO.getQtdeWarning();
						strTable += "</td>";
						strTable += "<td style='text-align: center;'>";
						strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/ball_gray__.gif' border='0'/>"
								+ eventTotalsDTO.getQtdeUnknown();
						strTable += "</td>";
						strTable += "</tr>";
						strTable += "</table>";
					} else {
						strTable += "<table width='100%'>";
						strTable += "<tr>";
						strTable += "<td colspan='4' style='text-align: center;background-color:green'>";
						strTable += hostUp;
						strTable += "</td>";
						strTable += "</tr>";
						strTable += "<tr>";
						strTable += "<td style='text-align: center;'>";
						if (eventTotalsDTO.getQtdeCritical() > 0) {
							strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'/><img src='"
									+ Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/b.gif" + "' border='0'/>" + eventTotalsDTO.getQtdeCritical();
						} else {
							strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'/>"
									+ eventTotalsDTO.getQtdeCritical();
						}
						strTable += "</td>";
						strTable += "<td style='text-align: center;'>";
						strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/bolaverde.png' border='0'/>"
								+ eventTotalsDTO.getQtdeOk();
						strTable += "</td>";
						strTable += "<td style='text-align: center;'>";
						strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/amarelo20x20.gif' border='0'/>"
								+ eventTotalsDTO.getQtdeWarning();
						strTable += "</td>";
						strTable += "<td style='text-align: center;'>";
						strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/ball_gray__.gif' border='0'/>"
								+ eventTotalsDTO.getQtdeUnknown();
						strTable += "</td>";
						strTable += "</tr>";
						strTable += "</table>";
					}
					strTable += "Último down: <br><b>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, ultimoDownGrp, WebUtil.getLanguage(request)) + "</b></div>";
					strTable += "</div>";
					strTable += "</td>";
				}
			}
		}
		strTable += "</tr>";
		strTable += "</table>";

		String strAux1 = "";

		if (qtdeTotalHostsDown > 0 || qtdeTotalServicesCritical > 0) {
			strAux1 = "background-color:orange";
		}

		String strAux = "Services Critical: " + qtdeTotalServicesCritical + "<br>";
		strAux = "Hosts Down: " + qtdeTotalHostsDown + "<br>" + strAux;
		strTable = strTable.replaceAll("#divProblemasCorrentes#", strAux);

		strTable = strTable.replaceAll("#complemenCoresProbl#", strAux1);

		document.getElementById("divGrupos").setInnerHTML(strTable);

		String strListaServiceCritical = "";
		if (lstServicesCritical != null && lstServicesCritical.size() > 0) {
			for (Iterator it = lstServicesCritical.iterator(); it.hasNext();) {
				Service service = (Service) it.next();
				String plugin_output = service.getParameter("plugin_output");
				if (plugin_output == null) {
					plugin_output = "";
				}
				strListaServiceCritical = strListaServiceCritical + "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/b.gif' border='0'>"
						+ service.getServiceName() + " - " + plugin_output + "<br>";
			}
		} else {
			strListaServiceCritical = "No critical services.";
		}

		document.getElementById("divServicesCritical").setInnerHTML(strListaServiceCritical);
	}

	public String getSituacaoServidor(String hostName) {
		if (MonitoraNagios.javaNagios == null) {
			return "";
		}
		MonitoraNagios.performanceDataSemaphore.acquireUninterruptibly();
		String current_state = null;
		try {
			Host host = MonitoraNagios.javaNagios.findHostByHostName(hostName.trim());
			if (host != null) {
				
				String nagiosTipoAcesso = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NAGIOS_TIPO_ACESSO, "1");
				
				if (nagiosTipoAcesso != null && StringUtils.isNotBlank(nagiosTipoAcesso) && nagiosTipoAcesso.trim().equalsIgnoreCase("1")){
					current_state = host.getParameter("state");
				}else{
					current_state = host.getParameter("current_state");
				}
				
				if (current_state.equalsIgnoreCase("1")) {
					current_state = "DOWN";
				} else if (current_state.equalsIgnoreCase("0")) {
					current_state = "UP";
				} else {
					current_state = "PENDING";
				}
			}
		} finally {
			MonitoraNagios.performanceDataSemaphore.release();
		}
		if (current_state == null) {
			return "PENDING";
		}
		return current_state;
	}

	public EventTotalsDTO getQtdeServicosCriticalByServidor(String hostName, List lstServicesCritical) {
		EventTotalsDTO eventTotalsDTO = new EventTotalsDTO();
		if (MonitoraNagios.javaNagios == null) {
			return eventTotalsDTO;
		}
		MonitoraNagios.performanceDataSemaphore.acquireUninterruptibly();
		String current_state = null;
		int qtdeServicosCriticos = 0;
		if (lstServicesCritical == null) {
			lstServicesCritical = new ArrayList();
		}
		try {
			Host host = MonitoraNagios.javaNagios.findHostByHostName(hostName.trim());
			if (host != null) {
				List lstServices = host.getServices();
				if (lstServices != null) {
					for (Iterator it = lstServices.iterator(); it.hasNext();) {
						Service service = (Service) it.next();
						if (service != null) {
							String nagiosTipoAcesso = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NAGIOS_TIPO_ACESSO, "1");
							
							if (nagiosTipoAcesso != null && StringUtils.isNotBlank(nagiosTipoAcesso) && nagiosTipoAcesso.trim().equalsIgnoreCase("1")){
								current_state = host.getParameter("state");
							}else{
								current_state = host.getParameter("current_state");
							}
							
							if (current_state.equalsIgnoreCase("1")) {
								current_state = "WARNING";
								eventTotalsDTO.setQtdeWarning(eventTotalsDTO.getQtdeWarning() + 1);
							} else if (current_state.equalsIgnoreCase("0")) {
								current_state = "OK";
								eventTotalsDTO.setQtdeOk(eventTotalsDTO.getQtdeOk() + 1);
							} else if (current_state.equalsIgnoreCase("2")) {
								current_state = "CRITICAL";
								eventTotalsDTO.setQtdeCritical(eventTotalsDTO.getQtdeCritical() + 1);
								lstServicesCritical.add(service);
							} else {
								current_state = "UNKNOWN";
								eventTotalsDTO.setQtdeUnknown(eventTotalsDTO.getQtdeUnknown() + 1);
							}
						}
					}
				}
			}
		} finally {
			MonitoraNagios.performanceDataSemaphore.release();
		}
		return eventTotalsDTO;
	}

	public Date getUltimoDown(String hostName) throws ParseException {
		if (MonitoraNagios.javaNagios == null) {
			return null;
		}
		String last_time_downStr = null;
		MonitoraNagios.performanceDataSemaphore.acquireUninterruptibly();
		try {
			Host host = MonitoraNagios.javaNagios.findHostByHostName(hostName.trim());
			if (host != null) {
				last_time_downStr = host.getParameter("last_time_down");
			}
		} finally {
			MonitoraNagios.performanceDataSemaphore.release();
		}
		if (last_time_downStr == null || last_time_downStr.trim().equalsIgnoreCase("")) {
			return null;
		}
		long last_time_down = Long.parseLong(last_time_downStr);
		Date d = transformCTime(last_time_down);
		return d;
	}

	public java.util.Date transformCTime(Long ctime) throws ParseException {
		if (ctime == null) {
			return null;
		}
		long l = (ctime.longValue() * 1000);
		java.util.Date data = new java.util.Date(l);
		return data;
	}

	@Override
	public Class getBeanClass() {
		return EventMonitorDTO.class;
	}

	public void mostraHostsGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EventMonitorDTO eventMonitorDTO = (EventMonitorDTO) document.getBean();
		RecursoService recursoService = (RecursoService) ServiceLocator.getInstance().getService(RecursoService.class, null);
		if (eventMonitorDTO.getIdGrupoRecurso() == null) {
			document.getElementById("divDetalhamento").setInnerHTML("");
			return;
		}
		Collection colRecursos = recursoService.findByIdGrupoRecurso(eventMonitorDTO.getIdGrupoRecurso());
		Date ultimoDownGrp = null;
		List lstServicesCritical = new ArrayList();
		EventTotalsDTO eventTotalsDTO = new EventTotalsDTO();

		String strTable = "<b>" + eventMonitorDTO.getNomeGrupoRecurso() + "</b><br>";

		strTable += "<table cellpadding='0' cellspacing='0'>";
		strTable += "<tr>";

		strTable += "<td style='border:1px solid black' rowspan='2'>";
		strTable += "Server Name";
		strTable += "</td>";
		strTable += "<td style='border:1px solid black' rowspan='2'>";
		strTable += "Last down";
		strTable += "</td>";
		strTable += "<td style='border:1px solid black' rowspan='2'>";
		strTable += "Host Status";
		strTable += "</td>";
		strTable += "<td style='border:1px solid black; text-align:center' colspan='3'>";
		strTable += "Service Status";
		strTable += "</td>";

		strTable += "</tr>";

		strTable += "<tr>";

		strTable += "<td style='border:1px solid black'>";
		strTable += "Qtd. Ok";
		strTable += "</td>";
		strTable += "<td style='border:1px solid black'>";
		strTable += "Qtd. Warning";
		strTable += "</td>";
		strTable += "<td style='border:1px solid black'>";
		strTable += "Qtd. Critical";
		strTable += "</td>";

		strTable += "</tr>";
		if (colRecursos != null) {
			for (Iterator it = colRecursos.iterator(); it.hasNext();) {
				RecursoDTO recursoDTO = (RecursoDTO) it.next();
				if (recursoDTO.getHostName() != null && !recursoDTO.getHostName().trim().equalsIgnoreCase("")) {
					strTable += "<tr>";

					strTable += "<td style='border:1px solid black'>";
					strTable += recursoDTO.getHostName();
					strTable += "</td>";
					Date ultimoDown = getUltimoDown(recursoDTO.getHostName());
					strTable += "<td style='border:1px solid black'>";
					strTable += UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, ultimoDown, WebUtil.getLanguage(request));
					strTable += "</td>";
					if (ultimoDownGrp == null) {
						ultimoDownGrp = ultimoDown;
					} else {
						if (ultimoDown != null) {
							if (ultimoDown.after(ultimoDownGrp)) {
								ultimoDownGrp = ultimoDown;
							}
						}
					}
					String strSit = getSituacaoServidor(recursoDTO.getHostName());
					String strBack = "background-color:white";
					if (strSit.equalsIgnoreCase("DOWN")) {
						strBack = "background-color:red";
					} else if (strSit.equalsIgnoreCase("UP")) {
						strBack = "background-color:green";
					} else {
						strBack = "background-color:lightgrey";
					}
					strTable += "<td style='border:1px solid black; text-align:center;" + strBack + "'>";
					strTable += strSit;
					strTable += "</td>";
					eventTotalsDTO = getQtdeServicosCriticalByServidor(recursoDTO.getHostName(), lstServicesCritical);
					String strBackOK = "";
					String strBackWarnning = "";
					String strBackCritical = "";
					if (eventTotalsDTO.getQtdeOk() > 0) {
						strBackOK = "background-color:green";
					}
					if (eventTotalsDTO.getQtdeWarning() > 0) {
						strBackWarnning = "background-color:yellow";
					}
					if (eventTotalsDTO.getQtdeCritical() > 0) {
						strBackCritical = "background-color:red";
					}
					strTable += "<td style='border:1px solid black; text-align:center;" + strBackOK + "'>";
					strTable += eventTotalsDTO.getQtdeOk();
					strTable += "</td>";
					strTable += "<td style='border:1px solid black; text-align:center;" + strBackWarnning + "'>";
					strTable += eventTotalsDTO.getQtdeWarning();
					strTable += "</td>";
					strTable += "<td style='border:1px solid black; text-align:center;" + strBackCritical + "'>";
					strTable += eventTotalsDTO.getQtdeCritical();
					strTable += "</td>";

					strTable += "</tr>";
				}
			}
		}
		strTable += "</table>";

		document.getElementById("divDetalhamento").setInnerHTML(strTable);
	}
}
