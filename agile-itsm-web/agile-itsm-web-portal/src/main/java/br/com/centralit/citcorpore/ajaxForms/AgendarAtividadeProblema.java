package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.ExecucaoAtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.GrupoAtvPeriodicaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AtividadePeriodicaService;
import br.com.centralit.citcorpore.negocio.ExecucaoAtividadePeriodicaService;
import br.com.centralit.citcorpore.negocio.GrupoAtvPeriodicaService;
import br.com.centralit.citcorpore.negocio.OcorrenciaProblemaService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.ProgramacaoAtividadeService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
@SuppressWarnings({"rawtypes","unchecked"})
public class AgendarAtividadeProblema  extends AjaxFormAction{


	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		AtividadePeriodicaDTO atividadePeriodicaDTO = (AtividadePeriodicaDTO)document.getBean();
		HTMLForm form = document.getForm("form");
		form.clear();
		HTMLSelect idGrupoAtvPeriodica = (HTMLSelect) document.getSelectById("idGrupoAtvPeriodica");
		GrupoAtvPeriodicaService grupoAtvPeriodicaService = (GrupoAtvPeriodicaService) ServiceLocator.getInstance().getService(GrupoAtvPeriodicaService.class, null);
		Collection colGrupos = grupoAtvPeriodicaService.list();
		idGrupoAtvPeriodica.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		idGrupoAtvPeriodica.addOptions(colGrupos, "idGrupoAtvPeriodica", "nomeGrupoAtvPeriodica", null);

		form.setValues(atividadePeriodicaDTO);

		if (atividadePeriodicaDTO.getIdProblema()== null){
			document.alert(UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.naoidentificarsolicitacao"));
		    document.executeScript("fechar();");
		    return;
		}

		AtividadePeriodicaService atividadePeriodicaService = (AtividadePeriodicaService) ServiceLocator.getInstance().getService(AtividadePeriodicaService.class, null);
		ProgramacaoAtividadeService programacaoAtividadeService = (ProgramacaoAtividadeService) ServiceLocator.getInstance().getService(ProgramacaoAtividadeService.class, null);
		ExecucaoAtividadePeriodicaService execucaoAtividadePeriodicaService = (ExecucaoAtividadePeriodicaService) ServiceLocator.getInstance().getService(ExecucaoAtividadePeriodicaService.class, null);
		Collection colAgendamentos = atividadePeriodicaService.findByIdProblema(atividadePeriodicaDTO.getIdProblema());
		String strBufferAgend = "<table width='100%'>";
		strBufferAgend += "<tr>";
		strBufferAgend += "<td style='border:1px solid black'>";
		strBufferAgend += "<b>&nbsp;</b>";
		strBufferAgend += "</td>";
		strBufferAgend += "<td style='border:1px solid black'>";
		strBufferAgend += "<b>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.criacao")+"</b>";
		strBufferAgend += "</td>";
		strBufferAgend += "<td style='border:1px solid black'>";
		strBufferAgend += "<b>"+ UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.datahoraAgendamento")+"</b>";
		strBufferAgend += "</td>";
		strBufferAgend += "<td style='border:1px solid black'>";
		strBufferAgend += "<b>"+ UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.grupo")+"</b>";
		strBufferAgend += "</td>";
		strBufferAgend += "<td style='border:1px solid black'>";
		strBufferAgend += "<b>"+ UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.orientacao")+"</b>";
		strBufferAgend += "</td>";
		strBufferAgend += "<td style='border:1px solid black'>";
		strBufferAgend += "<b>"+ UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.execucao")+"</b>";
		strBufferAgend += "</td>";
		strBufferAgend += "<td style='border:1px solid black'>";
		strBufferAgend += "<b>&nbsp;</b>";
		strBufferAgend += "</td>";
		strBufferAgend += "</tr>";
		if (colAgendamentos != null){
		    for (Iterator it = colAgendamentos.iterator(); it.hasNext();){
			AtividadePeriodicaDTO atividadePeriodicaAux = (AtividadePeriodicaDTO)it.next();
			Collection colProgs = programacaoAtividadeService.findByIdAtividadePeriodicaOrderDataHora(atividadePeriodicaAux.getIdAtividadePeriodica());
			if (colProgs != null){
			    for (Iterator itProg = colProgs.iterator(); itProg.hasNext();){
				ProgramacaoAtividadeDTO programacaoAtividadeDTO = (ProgramacaoAtividadeDTO)itProg.next();
				strBufferAgend += "<tr>";

				strBufferAgend += "<td style='border:1px solid black'>";
				strBufferAgend += "#CONTROLE#";
				strBufferAgend += "</td>";
				strBufferAgend += "<td style='border:1px solid black'>";
					strBufferAgend += UtilI18N.internacionaliza(request, "citcorpore.comum.data")+": " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, atividadePeriodicaAux.getDataCriacao(), WebUtil.getLanguage(request));
					strBufferAgend += "<br>" + UtilI18N.internacionaliza(request, "login.usuario")+ ": " + atividadePeriodicaAux.getCriadoPor();
					strBufferAgend += "<br><br>" + UtilI18N.internacionaliza(request, "gerenciaservico.codagendamento") + ": " + atividadePeriodicaAux.getIdAtividadePeriodica();
				strBufferAgend += "</td>";
				strBufferAgend += "<td style='border:1px solid black'>";
					strBufferAgend += "<b><u>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, programacaoAtividadeDTO.getDataInicio(), WebUtil.getLanguage(request)) + " " + programacaoAtividadeDTO.getHoraInicio() + "</u></b>";
					strBufferAgend += "<br><br>" + UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.duracaoestimada") + ": " + programacaoAtividadeDTO.getDuracaoEstimada() + "min";
				strBufferAgend += "</td>";
				GrupoAtvPeriodicaDTO grupoAtvPeriodicaDTO = new GrupoAtvPeriodicaDTO();
				grupoAtvPeriodicaDTO.setIdGrupoAtvPeriodica(atividadePeriodicaAux.getIdGrupoAtvPeriodica());
				grupoAtvPeriodicaDTO = (GrupoAtvPeriodicaDTO) grupoAtvPeriodicaService.restore(grupoAtvPeriodicaDTO);
				String grupoAtv = "--";
				if (grupoAtvPeriodicaDTO != null){
				    grupoAtv = grupoAtvPeriodicaDTO.getNomeGrupoAtvPeriodica();
				}
				strBufferAgend += "<td style='border:1px solid black'>";
					strBufferAgend += grupoAtv;
				strBufferAgend += "</td>";
				strBufferAgend += "<td style='border:1px solid black'>";
				String strOr = atividadePeriodicaAux.getOrientacaoTecnica();
				if (strOr != null){
				    strOr = strOr.replaceAll("\n", "<br>");
				}else{
				    strOr = "&nbsp;";
				}
					strBufferAgend += strOr;
				strBufferAgend += "</td>";
				strBufferAgend += "<td style='border:1px solid black'>";
				String strExecucao = "";
				String finalizado = "";
					Collection colExecucoes = execucaoAtividadePeriodicaService.findByIdAtividadePeriodica(atividadePeriodicaAux.getIdAtividadePeriodica());
					if (colExecucoes != null){
					    for (Iterator itExc = colExecucoes.iterator(); itExc.hasNext();){
						ExecucaoAtividadePeriodicaDTO execucaoAtividadePeriodicaDTO = (ExecucaoAtividadePeriodicaDTO) itExc.next();
						strExecucao += UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.dataexecucao")+ " " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, execucaoAtividadePeriodicaDTO.getDataExecucao(), WebUtil.getLanguage(request)) + " " + execucaoAtividadePeriodicaDTO.getHoraExecucao();
						strExecucao += "<br>"+ UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.dataregistro")+ " " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, execucaoAtividadePeriodicaDTO.getDataRegistro(), WebUtil.getLanguage(request)) + " " + execucaoAtividadePeriodicaDTO.getHoraRegistro();
						strExecucao += "<br>"+ UtilI18N.internacionaliza(request, "colaborador.situacao") +":<b><u>" + execucaoAtividadePeriodicaDTO.getSituacaoDescr() + "</u></b>";
						strExecucao += "<br>"+UtilI18N.internacionaliza(request, "gerenciaservico.detalhamentoexecucao") +":<br> " + UtilStrings.nullToVazio(execucaoAtividadePeriodicaDTO.getDetalhamento());
						if (!execucaoAtividadePeriodicaDTO.getSituacao().equalsIgnoreCase("F")){
						    finalizado = "N";
						}
						if (finalizado.equalsIgnoreCase("") && execucaoAtividadePeriodicaDTO.getSituacao().equalsIgnoreCase("F")){
						    finalizado = "S";
						}
					  }
					}
				if (strExecucao.equalsIgnoreCase("")){
				    strBufferAgend += UtilI18N.internacionaliza(request, "gerenciaservico.semregistro");
				}else{
				    strBufferAgend += strExecucao;
				}
				strBufferAgend += "</td>";
				if(finalizado.equalsIgnoreCase("S")){
					strBufferAgend += "<td style='border:1px solid black'>";
					strBufferAgend += "<b>&nbsp;</b>";
					strBufferAgend += "</td>";
				} else{
					strBufferAgend += "<td style='border:1px solid black' onclick='excluirAgendamento("+atividadePeriodicaAux.getIdAtividadePeriodica()+","+atividadePeriodicaDTO.getIdProblema()+")'>";
					strBufferAgend += "<img style='cursor:pointer;'src='" + Constantes.getValue("SERVER_ADDRESS") +
			    				Constantes.getValue("CONTEXTO_APLICACAO") +"/imagens/excluirPeq.gif' border='0'/>";
					strBufferAgend += "</td>";
				}
				strBufferAgend += "</tr>";

					if (finalizado.equalsIgnoreCase("S")){
					    strBufferAgend = strBufferAgend.replaceAll("\\#CONTROLE\\#", "<img src='" + Constantes.getValue("SERVER_ADDRESS") +
		    				Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/tick.png' border='0'/>");
					}else{
					    strBufferAgend = strBufferAgend.replaceAll("\\#CONTROLE\\#", "<img src='" + Constantes.getValue("SERVER_ADDRESS") +
			    				Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/relogio.png' border='0'/>");
					}
			    }
			}
		    }
		}
		strBufferAgend += "</table>";
		document.getElementById("divAgendamentos").setInnerHTML(strBufferAgend);

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		AtividadePeriodicaDTO atividadePeriodicaDTO = (AtividadePeriodicaDTO)document.getBean();

		if (atividadePeriodicaDTO.getDuracaoEstimada() == null || atividadePeriodicaDTO.getDuracaoEstimada().intValue() == 0){
		    document.alert(UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.valida.duracao"));
		    return;
		}

		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, WebUtil.getUsuarioSistema(request));
		GrupoAtvPeriodicaService grupoAtvPeriodicaService = (GrupoAtvPeriodicaService) ServiceLocator.getInstance().getService(GrupoAtvPeriodicaService.class, null);
		ProblemaDTO problemaDto = problemaService.restoreAll(atividadePeriodicaDTO.getIdProblema());

		String orient = "";
		String ocorr = "";

		if (!ocorr.equalsIgnoreCase("")) ocorr += "\n";
			ocorr += UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.dataagendamento") +" " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, atividadePeriodicaDTO.getDataInicio(), WebUtil.getLanguage(request));

		if (!ocorr.equalsIgnoreCase("")) ocorr += "\n";
			ocorr += UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.horaagendamento") + " " + atividadePeriodicaDTO.getHoraInicio();

		if (!ocorr.equalsIgnoreCase("")) ocorr += "\n";
			ocorr += UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.duracaoestimada") +" " + atividadePeriodicaDTO.getDuracaoEstimada();

		GrupoAtvPeriodicaDTO grupoAtvPeriodicaDTO = new GrupoAtvPeriodicaDTO();
		grupoAtvPeriodicaDTO.setIdGrupoAtvPeriodica(atividadePeriodicaDTO.getIdGrupoAtvPeriodica());
		grupoAtvPeriodicaDTO = (GrupoAtvPeriodicaDTO) grupoAtvPeriodicaService.restore(grupoAtvPeriodicaDTO);

		if (grupoAtvPeriodicaDTO != null){
			ocorr += "\n"+UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.grupo") + ": " + grupoAtvPeriodicaDTO.getNomeGrupoAtvPeriodica();
		}

		if (atividadePeriodicaDTO.getOrientacaoTecnica() != null){
			orient = atividadePeriodicaDTO.getOrientacaoTecnica();
			ocorr += "\n"+ UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.orientacaotecnica") +": \n" + atividadePeriodicaDTO.getOrientacaoTecnica();
		}

		orient += "\n\n"+ UtilI18N.internacionaliza(request, "problema.problema") +": \n" + problemaDto.getTitulo();

		atividadePeriodicaDTO.setTituloAtividade(UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.solicitacaoincidente") +" " + atividadePeriodicaDTO.getIdProblema());
		atividadePeriodicaDTO.setDescricao(problemaDto.getDescricao());
		atividadePeriodicaDTO.setDataCriacao(UtilDatas.getDataAtual());
		atividadePeriodicaDTO.setCriadoPor(usuario.getNomeUsuario());
		atividadePeriodicaDTO.setIdContrato(problemaDto.getIdContrato());
		atividadePeriodicaDTO.setOrientacaoTecnica(orient);

		Collection colItens = new ArrayList();
		ProgramacaoAtividadeDTO programacaoAtividadeDTO = new ProgramacaoAtividadeDTO();
		programacaoAtividadeDTO.setTipoAgendamento("U");
		programacaoAtividadeDTO.setDataInicio(atividadePeriodicaDTO.getDataInicio());
		programacaoAtividadeDTO.setHoraInicio(atividadePeriodicaDTO.getHoraInicio());
		programacaoAtividadeDTO.setHoraFim("00:00");
		programacaoAtividadeDTO.setDuracaoEstimada(atividadePeriodicaDTO.getDuracaoEstimada());
		programacaoAtividadeDTO.setRepeticao("N");
		colItens.add(programacaoAtividadeDTO);

		AtividadePeriodicaService atividadePeriodicaService = (AtividadePeriodicaService) ServiceLocator.getInstance().getService(AtividadePeriodicaService.class, null);
		atividadePeriodicaDTO.setColItens(colItens);
		atividadePeriodicaService.create(atividadePeriodicaDTO);

		document.alert(UtilI18N.internacionaliza(request, "MSG05"));


		OcorrenciaProblemaService ocorrenciaProblemaService = (OcorrenciaProblemaService) ServiceLocator.getInstance().getService(OcorrenciaProblemaService.class, null);
		OcorrenciaProblemaDTO ocorrenciaProblemaDTO = new OcorrenciaProblemaDTO();
		ocorrenciaProblemaDTO.setIdProblema(atividadePeriodicaDTO.getIdProblema());
		ocorrenciaProblemaDTO.setDataregistro(UtilDatas.getDataAtual());
		ocorrenciaProblemaDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
		ocorrenciaProblemaDTO.setTempoGasto(0);
		ocorrenciaProblemaDTO.setDescricao(Enumerados.CategoriaOcorrencia.Agendamento.getDescricao());
		ocorrenciaProblemaDTO.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaProblemaDTO.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaProblemaDTO.setInformacoesContato(UtilI18N.internacionaliza(request, "MSG013"));
		ocorrenciaProblemaDTO.setRegistradopor(usuario.getNomeUsuario());
		ocorrenciaProblemaDTO.setOcorrencia(ocorr);
		ocorrenciaProblemaDTO.setOrigem(Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaProblemaDTO.setCategoria(Enumerados.CategoriaOcorrencia.Agendamento.getSigla());
		ocorrenciaProblemaDTO.setIdItemTrabalho(problemaDto.getIdTarefa());
		ocorrenciaProblemaService.create(ocorrenciaProblemaDTO);

		this.load(document, request, response);
		document.executeScript("refresh();");
    }

	public void excluirAgendamento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		AtividadePeriodicaDTO atividadePeriodicaDTO = (AtividadePeriodicaDTO)document.getBean();
		AtividadePeriodicaService atividadePeriodicaService = (AtividadePeriodicaService) ServiceLocator.getInstance().getService(AtividadePeriodicaService.class, null);
		atividadePeriodicaDTO = (AtividadePeriodicaDTO) atividadePeriodicaService.restore(atividadePeriodicaDTO);

		atividadePeriodicaDTO.setDataFim(UtilDatas.getDataAtual());
		atividadePeriodicaService.update(atividadePeriodicaDTO);

		this.load(document, request, response);
		document.executeScript("refresh();");
	}

	@Override
	public Class getBeanClass() {
		return AtividadePeriodicaDTO.class;
	}

}
