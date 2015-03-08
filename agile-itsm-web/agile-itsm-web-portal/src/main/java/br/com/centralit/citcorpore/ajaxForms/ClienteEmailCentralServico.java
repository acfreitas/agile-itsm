package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ClienteEmailCentralServicoDTO;
import br.com.centralit.citcorpore.bean.ClienteEmailCentralServicoMessagesDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ClienteEmailCentralServicoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.Enumerados.TipoOrigemLeituraEmail;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author breno.guimaraes Controla as transações para manipulação de Emails.
 *
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class ClienteEmailCentralServico extends AjaxFormAction {
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		loadMails(document, request, response);
	}

	public void loadMails(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ClienteEmailCentralServicoDTO clienteEmailCentralServicoDTO = (ClienteEmailCentralServicoDTO) document.getBean();
		ClienteEmailCentralServicoService clienteEmailService = (ClienteEmailCentralServicoService) ServiceLocator.getInstance().getService(ClienteEmailCentralServicoService.class, null);

		StringBuilder html = new StringBuilder();
		StringBuilder htmlBody = new StringBuilder();
		ArrayList<ClienteEmailCentralServicoMessagesDTO> emailMessages;

		ClienteEmailCentralServicoDTO emailMessagesDto = clienteEmailService.getMessagesByLimitAndNoRequest(document, request, response);

		if (emailMessagesDto.isResultSucess()) {
			emailMessages = emailMessagesDto.getEmailMessages();

			if (emailMessages == null || emailMessages.isEmpty()) {
				document.alert(UtilI18N.internacionaliza(request, "MSG04"));
				document.executeScript("toggleClass('widgetEmails', 'hide');");
			} else {
				html.append("<table id='tblEmails' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>");
				html.append("<tr>");
				html.append("<th>" + UtilI18N.internacionaliza(request, "eventoItemConfiguracao.data") + "</th>");
				html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.de") + "</th>");
				html.append("<th>" + UtilI18N.internacionaliza(request, "requisitosla.assunto") + "</th>");
				html.append("<th>" + UtilI18N.internacionaliza(request, "questionario.acoes") + "</th>");
				html.append("</tr>");

				for (ClienteEmailCentralServicoMessagesDTO message : emailMessages) {
					html.append("<tr " + (!message.isSeen() ? "style='font-weight: bold;'" : "") + ">");
					html.append("<td style='width: 20%;'>" + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, message.getMessageReceivedDate(), WebUtil.getLanguage(request)) + "</td>");
					html.append("<td style='width: 25%;overflow: hidden;'>" + message.getMessageEmail() + "</td>");
					html.append("<td style='width: 40%;overflow: hidden;'>" + message.getMessageSubject() + "</td>");
					html.append("<td><button class='btn btn-mini btn-primary light' id='verificarEmails" + message.getMessageNumber() + "' name='verificarEmails" + message.getMessageNumber() + "' onclick='copiaEmail(" + message.getMessageNumber() + ");return false;'><i></i>" + UtilI18N.internacionaliza(request, "solicitacaoServico.copiarMsg") + "</button></td>");
					html.append("</tr>");

					htmlBody.append("<input type='hidden' value='" + message.getMessageId() + "' name='emailMessageId" + message.getMessageNumber() + "' id='emailMessageId" + message.getMessageNumber() + "'/>");
				}

				html.append("</table>");
				html.append(htmlBody.toString());

				document.getElementById("divEmails").setInnerHTML(html.toString());
				document.executeScript("toggleClass('widgetEmails', 'show');");
			}
		} else {
			document.alert(emailMessagesDto.getResultMessage());
			document.executeScript("toggleClass('widgetEmails', 'hide');");
		}

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public void readMail(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ClienteEmailCentralServicoService clienteEmailService = (ClienteEmailCentralServicoService) ServiceLocator.getInstance().getService(ClienteEmailCentralServicoService.class, null);
		ClienteEmailCentralServicoDTO clienteEmailCentralServicoDTO = (ClienteEmailCentralServicoDTO) document.getBean();
		ArrayList<ClienteEmailCentralServicoMessagesDTO> emailMessages;

		if (clienteEmailCentralServicoDTO != null && clienteEmailCentralServicoDTO.getEmailMessageId() != null && clienteEmailCentralServicoDTO.getIdContrato() != null) {
			ClienteEmailCentralServicoDTO cecsDto = clienteEmailService.readMessage(document, request, response, clienteEmailCentralServicoDTO.getEmailMessageId());

			if (cecsDto.isResultSucess()) {
				emailMessages = cecsDto.getEmailMessages();

				if (emailMessages == null || emailMessages.isEmpty()) {
					document.alert(UtilI18N.internacionaliza(request, "MSG04"));
					document.executeScript("toggleClass('widgetEmails', 'hide');");
				} else {
					for (ClienteEmailCentralServicoMessagesDTO message : emailMessages) {
			            document.getElementById("messageId").setValue(clienteEmailCentralServicoDTO.getEmailMessageId());
			            document.executeScript("setDescricao('" + message.getMessageContent() + "');");

						//Se for empregado irá setar o restante das informações
						EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
						EmpregadoDTO empregadoDTO = new EmpregadoDTO();
						empregadoDTO = empregadoService.listEmpregadoContrato(clienteEmailCentralServicoDTO.getIdContrato(), message.getMessageEmail());
						if (empregadoDTO != null) {
							document.getElementById("idSolicitante").setValue(empregadoDTO.getIdEmpregado().toString());
							document.getElementById("solicitante").setValue(empregadoDTO.getNome());

							document.getElementById("idUnidade").setValue(Util.tratarAspasSimples(empregadoDTO.getIdUnidade().toString()));

							if (clienteEmailCentralServicoDTO != null && clienteEmailCentralServicoDTO.getEmailOrigem().equalsIgnoreCase(TipoOrigemLeituraEmail.SOLICITACAO_SERVICO.toString())) {
								document.getElementById("nomecontato").setValue(empregadoDTO.getNome());
								document.getElementById("emailcontato").setValue(message.getMessageEmail());
								document.getElementById("telefonecontato").setValue(empregadoDTO.getTelefone());
								document.getElementById("idOrigem").setValue("3");

								document.executeScript("renderizaInformacoesSolicitante();");
							} else if (clienteEmailCentralServicoDTO != null && clienteEmailCentralServicoDTO.getEmailOrigem().equalsIgnoreCase(TipoOrigemLeituraEmail.PROBLEMA.toString())) {
								document.getElementById("nomeContato").setValue(empregadoDTO.getNome());
								document.getElementById("emailContato").setValue(message.getMessageEmail());
								document.getElementById("telefoneContato").setValue(empregadoDTO.getTelefone());
								document.getElementById("idOrigemAtendimento").setValue("3");
							}
						}
					}

					document.executeScript("fechaModalLeituraEmail();");
				}
			} else {
				document.alert(cecsDto.getResultMessage());
				document.executeScript("toggleClass('widgetEmails', 'hide');");
			}

			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		}
	}

	public Class getBeanClass() {
		return ClienteEmailCentralServicoDTO.class;
	}
}
