package br.com.centralit.citcorpore.ajaxForms;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ReuniaoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoMudanca;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.ReuniaoRequisicaoMudancaService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class AgendarReuniaoRequisicaoMudanca extends AjaxFormAction{


	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		ReuniaoRequisicaoMudancaDTO atividadePeriodicaDTO = (ReuniaoRequisicaoMudancaDTO)document.getBean();
		HTMLForm form = document.getForm("form");
		//form.clear();

		if (atividadePeriodicaDTO.getIdRequisicaoMudanca()== null){
			document.alert(UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.naoidentificarsolicitacao"));
			document.executeScript("fechar();");
			return;
		}

		ReuniaoRequisicaoMudancaService atividadePeriodicaService = (ReuniaoRequisicaoMudancaService) ServiceLocator.getInstance().getService(ReuniaoRequisicaoMudancaService.class, null);
//		ProgramacaoAtividadeService programacaoAtividadeService = (ProgramacaoAtividadeService) ServiceLocator.getInstance().getService(ProgramacaoAtividadeService.class, null);
//		ExecucaoAtividadePeriodicaService execucaoAtividadePeriodicaService = (ExecucaoAtividadePeriodicaService) ServiceLocator.getInstance().getService(ExecucaoAtividadePeriodicaService.class, null);
		Collection colAgendamentos = atividadePeriodicaService.findByIdRequisicaoMudanca(atividadePeriodicaDTO.getIdRequisicaoMudanca());

		confereStatusReuniao(colAgendamentos, request);

		StringBuilder strBufferAgend = new StringBuilder();
		String buffer = "";
		strBufferAgend.append("<table width='100%'>");
		strBufferAgend.append("<tr>");
		strBufferAgend.append("<td style='border:1px solid black'>");
		strBufferAgend.append("<b>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.criacao")+"</b>");
		strBufferAgend.append("</td>");
		strBufferAgend.append("<td style='border:1px solid black'>");
		strBufferAgend.append("<b>"+ UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.datahoraAgendamento")+"</b>");
		strBufferAgend.append("</td>");
		strBufferAgend.append("<td style='border:1px solid black'>");
		strBufferAgend.append("<b>"+ UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.localReuniao")+"</b>");
		strBufferAgend.append("</td>");
		strBufferAgend.append("<td style='border:1px solid black'>");
		strBufferAgend.append("<b>"+ UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.descricao")+"</b>");
		strBufferAgend.append("</td>");
		strBufferAgend.append("<td style='border:1px solid black'>");
		strBufferAgend.append("<b>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.cancelar")+"</b>");
		strBufferAgend.append("</td>");
		strBufferAgend.append("</tr>");
		if (colAgendamentos != null){
			for (Iterator it = colAgendamentos.iterator(); it.hasNext();){
				ReuniaoRequisicaoMudancaDTO atividadePeriodicaAux = (ReuniaoRequisicaoMudancaDTO)it.next();
				strBufferAgend.append("<tr>");

				strBufferAgend.append("<td style='border:1px solid black'>");
				strBufferAgend.append(UtilI18N.internacionaliza(request, "citcorpore.comum.data")+": " + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, atividadePeriodicaAux.getDataCriacao(), WebUtil.getLanguage(request)));
				strBufferAgend.append("<br>" + UtilI18N.internacionaliza(request, "login.usuario")+ ": " + atividadePeriodicaAux.getCriadoPor());
				strBufferAgend.append("<br><br>" + UtilI18N.internacionaliza(request, "gerenciaservico.codreuniao") + ": " + atividadePeriodicaAux.getIdReuniaoRequisicaoMudanca());
				strBufferAgend.append("</td>");
				strBufferAgend.append("<td style='border:1px solid black'>");

				if(atividadePeriodicaAux.getStatus() != null && atividadePeriodicaAux.getStatus().equals("Ocorrendo")){
					strBufferAgend.append("<b><u>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, atividadePeriodicaAux.getDataInicio(), WebUtil.getLanguage(request)) + " " + atividadePeriodicaAux.getHoraInicio() + "</u></b>" +
							"<font color=\"red\"> "+ atividadePeriodicaAux.getStatus() + "!	</font>");
				} else {
					strBufferAgend.append("<b><u>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, atividadePeriodicaAux.getDataInicio(), WebUtil.getLanguage(request)) + " " + atividadePeriodicaAux.getHoraInicio() + "</u></b>" +
							"<font color=\"blue\"> "+ atividadePeriodicaAux.getStatus() + "	</font>");
				}

				strBufferAgend.append("<br><br>" + UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.duracaoestimada")  + atividadePeriodicaAux.getDuracaoEstimada() + "min");
				strBufferAgend.append("</td>");

				strBufferAgend.append("<td style='border:1px solid black'>");
				strBufferAgend.append(atividadePeriodicaAux.getLocalReuniao());
				strBufferAgend.append("</td>");
				strBufferAgend.append("<td style='border:1px solid black'>");
				strBufferAgend.append(atividadePeriodicaAux.getDescricao());
				strBufferAgend.append("</td>");

				strBufferAgend.append("<td style='border:1px solid black'>");
				strBufferAgend.append("#CONTROLE#");
				strBufferAgend.append("</td>");

				strBufferAgend.append("</td>");
				strBufferAgend.append("</tr>");

				buffer = strBufferAgend.toString();
				buffer = buffer.replaceAll("\\#CONTROLE\\#", "<label type='button'  class='light'  onclick='excluiReuniao(" + atividadePeriodicaAux.getIdReuniaoRequisicaoMudanca()+ ")'>" +
				"<img src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/delete.png'>" +
				"</label>");
				strBufferAgend.setLength(0);
				strBufferAgend.append(buffer);
			}
		}
		strBufferAgend.append("</table>");
		document.getElementById("divAgendamentos").setInnerHTML(strBufferAgend.toString());

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		ReuniaoRequisicaoMudancaDTO reuniaoRequisicaoMudancaDto = (ReuniaoRequisicaoMudancaDTO)document.getBean();

		if (reuniaoRequisicaoMudancaDto.getDuracaoEstimada() == null || reuniaoRequisicaoMudancaDto.getDuracaoEstimada().intValue() == 0){
			document.alert(UtilI18N.internacionaliza(request, "gerenciaservico.agendaratividade.valida.duracao"));
			return;
		}
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, WebUtil.getUsuarioSistema(request));
		RequisicaoMudancaDTO requisicaoMudancaDto = requisicaoMudancaService.restoreAll(reuniaoRequisicaoMudancaDto.getIdRequisicaoMudanca());

		reuniaoRequisicaoMudancaDto.setDataCriacao(UtilDatas.getDataAtual());
		reuniaoRequisicaoMudancaDto.setCriadoPor(usuario.getNomeUsuario());

		ReuniaoRequisicaoMudancaService atividadePeriodicaService = (ReuniaoRequisicaoMudancaService) ServiceLocator.getInstance().getService(ReuniaoRequisicaoMudancaService.class, null);

		reuniaoRequisicaoMudancaDto.setStatus("Aguardando");
		atividadePeriodicaService.create(reuniaoRequisicaoMudancaDto);

		document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		//enviar e-mail para todos as pessoas do grupo responsável.
		ExecucaoMudanca execucaoMudanca = new ExecucaoMudanca();
		String ID_MODELO_EMAIL_AVISAR_REUNIAO_MARCADA = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_REUNIAO_MARCADA, "39");
		execucaoMudanca.enviaEmailReuniaoGrupo(Integer.parseInt(ID_MODELO_EMAIL_AVISAR_REUNIAO_MARCADA), requisicaoMudancaDto.getIdGrupoAtual(), reuniaoRequisicaoMudancaDto.getIdRequisicaoMudanca(), reuniaoRequisicaoMudancaDto.getIdReuniaoRequisicaoMudanca());
		/*Thiago Fernandes - 29/10/2013 - 17:33 - Sol. 121468 - Assim que for adicionado um agendamento a popup não deve ser fechada, ela deve ser apenas recarregada.*/
		load(document, request, response);
		HTMLForm form = document.getForm("form");
		document.executeScript("limparCampos();");
	}

	@Override
	public Class getBeanClass() {
		return ReuniaoRequisicaoMudancaDTO.class;
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReuniaoRequisicaoMudancaDTO reuniao = (ReuniaoRequisicaoMudancaDTO) document.getBean();
		ReuniaoRequisicaoMudancaService reuniaoService  = (ReuniaoRequisicaoMudancaService) ServiceLocator.getInstance().getService(ReuniaoRequisicaoMudancaService.class, WebUtil.getUsuarioSistema(request) );

		// Verificando a existência do DTO e do serviço.
		if (reuniao != null && reuniaoService != null) {

			//if (reuniao.getIdReuniaoRequisicaoMudanca() != null && reuniao.getIdReuniaoRequisicaoMudanca().intValue() > 0) {
				reuniao.setStatus("Cancelada");
				reuniaoService.update(reuniao);
			//}
			/*Thiago Fernandes - 28/10/2013 - 11:30 - Sol. 121468 - Assim que for ecluido um agendamento a popup não deve ser fechada, ela deve ser apenas recarregada.*/
			//document.executeScript("fechar();");
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			load(document, request, response);
		}
	}

	public void validaHorarioESalva(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ReuniaoRequisicaoMudancaDTO reuniao = (ReuniaoRequisicaoMudancaDTO) document.getBean();
		Date dataAtualComHorario = new Date();
		Date dataAtual = UtilDatas.getDataAtual();
		Date dataInicio = reuniao.getDataInicio();
		if(dataAtual.equals(dataInicio)){

			if(verificaSeHorarioReuniaoEhMenorQueHorarioAtual(dataAtualComHorario, reuniao.getHoraInicio())){
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.horaAtrasada"));
				return;
			}

		}
		save(document, request, response);
	}

	public Boolean verificaSeHorarioReuniaoEhMenorQueHorarioAtual(Date dataAtual, String horaMinutoReuniao){

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataAtual);

		int horaAtual = calendar.get(Calendar.HOUR_OF_DAY);
		int minutoAtual = calendar.get(Calendar.MINUTE);

		String[] horaEMinutosReuniao = horaMinutoReuniao.split(":");
		int horaReuniao = Integer.parseInt(horaEMinutosReuniao[0]);
		int minutosReuniao = Integer.parseInt(horaEMinutosReuniao[1]);

		int tempoTotalAtual = horaAtual*60 + minutoAtual;
		int tempoTotalReuniao = horaReuniao*60 + minutosReuniao;

		if(tempoTotalReuniao < tempoTotalAtual){
			return true;
		}

		return false;
	}

	public Boolean verificaSeReuniaoJaAcabou(Date dataAtual, ReuniaoRequisicaoMudancaDTO reuniao){

		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataAtual);

		int horaAtual = calendar.get(Calendar.HOUR_OF_DAY);
		int minutoAtual = calendar.get(Calendar.MINUTE);

		String[] horaEMinutosReuniao = reuniao.getHoraInicio().split(":");
		int horaReuniao = Integer.parseInt(horaEMinutosReuniao[0]);
		int minutosReuniao = Integer.parseInt(horaEMinutosReuniao[1]) + reuniao.getDuracaoEstimada();

		int tempoTotalAtual = horaAtual*60 + minutoAtual;
		int tempoTotalReuniao = horaReuniao*60 + minutosReuniao;

		if(tempoTotalReuniao < tempoTotalAtual){
			return true;
		}

		return false;
	}

	public void confereStatusReuniao(Collection colAgendamentos, HttpServletRequest request) throws ServiceException, Exception{
		if(colAgendamentos != null){
			ReuniaoRequisicaoMudancaService reuniaoService  = (ReuniaoRequisicaoMudancaService) ServiceLocator.getInstance().getService(ReuniaoRequisicaoMudancaService.class, WebUtil.getUsuarioSistema(request) );
			for (Iterator iterator = colAgendamentos.iterator(); iterator.hasNext();) {
				ReuniaoRequisicaoMudancaDTO reuniao = (ReuniaoRequisicaoMudancaDTO) iterator.next();

				Date dataAtual = new Date();
				if(dataAtual.equals(reuniao.getDataInicio()) || dataAtual.after(reuniao.getDataInicio())){
					if(verificaSeHorarioReuniaoEhMenorQueHorarioAtual(dataAtual, reuniao.getHoraInicio())){

						if(verificaSeReuniaoJaAcabou(dataAtual, reuniao)){
							reuniao.setStatus("Finalizada");
						} else{
							reuniao.setStatus("Ocorrendo");
						}
						reuniaoService.update(reuniao);
					}
				}
			}
		}
	}
}
