package br.com.centralit.citcorpore.rh.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.negocio.CandidatoService;
import br.com.centralit.citcorpore.util.CitCorporeConstantes;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class RecuperaSenhaCandidato extends AjaxFormAction{
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void validacaoEmail(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CandidatoDTO cadidatoDto = (CandidatoDTO)document.getBean();
		CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);
		
		CandidatoDTO cadidatoAux = candidatoService.findByEmail(cadidatoDto.getEmail());
		
		String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
		if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
			algoritmo = "SHA-1";
		}
		
		String usuarioEmail = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
		
		if (usuarioEmail == null){
    		usuarioEmail = "citsmart@centralit.com.br";
    	}
		
		if(cadidatoAux != null && cadidatoAux.getEmail()!= null && cadidatoAux.getEmail()!= ""){
			String hashID = CriptoUtils.generateHash(UtilDatas.getDataHoraAtual() + "", algoritmo);
			cadidatoAux.setHashID(hashID);
			candidatoService.update(cadidatoAux);
			StringBuilder msgEmail = new StringBuilder();
			
			msgEmail.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#F4F3F4'>");
			msgEmail.append("	<tbody>");
			msgEmail.append("		<tr>");
			msgEmail.append("		<td style='padding:15px'>");
			msgEmail.append("			<center>");
			msgEmail.append("				<table width='550' cellspacing='0' cellpadding='0' align='center' bgcolor='#ffffff'>");
			msgEmail.append("				<tbody>");
			msgEmail.append("					<tr>");
			msgEmail.append("					<td align='left'>");
			msgEmail.append("						<div style='border:solid 1px #d9d9d9'>");
			msgEmail.append("							<table style='line-height:1.6;font-size:12px;font-family:Helvetica,Arial,sans-serif;border:solid 1px #ffffff;color:#444' width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#ffffff'>");
			msgEmail.append("								<tbody>");
			msgEmail.append("									<tr>");
			msgEmail.append("									<td style='color:#ffffff' colspan='2' valign='bottom' height='30'>.</td>");
			msgEmail.append("									</tr>");
			msgEmail.append("									<tr>");
			msgEmail.append("									<td style='line-height:32px;padding-left:30px' valign='baseline'>");
			msgEmail.append("										<span style='font-size:32px'><a style='text-decoration:none' href='http://www.centralit.com.br/' target='_blank'>");
			msgEmail.append("										<img alt='' src='http://www.centralit.com.br/images/logo_central.png' border='0'></a><br>");
			msgEmail.append("										</span>");
			msgEmail.append("									</td>");
			msgEmail.append("									<td style='padding-right:30px' align='right' valign='baseline'><span style='font-size:14px;color:#777777'>&nbsp;</span></td>");
			msgEmail.append("									</tr>");
			msgEmail.append("								</tbody>");
			msgEmail.append("							</table>");
			msgEmail.append("							<table style='margin-top:15px;margin-right:30px;margin-left:30px;color:#444;line-height:1.6;font-size:12px;font-family:Arial,sans-serif' width='490' border='0' cellspacing='0' cellpadding='0' bgcolor='#ffffff'>");
			msgEmail.append("								<tbody>");
			msgEmail.append("									<tr>");
			msgEmail.append("									<td style='border-top:solid 1px #d9d9d9' colspan='2'>");
			msgEmail.append("										<div style='padding:15px 0'>");
			msgEmail.append("											Olá <b>"+cadidatoAux.getNome());
			msgEmail.append("											</b><br>Você solicitou a recuperação de senha do seu cadastro, por favor clique no link abaixo para redefinir sua senha.<br><br>");
			msgEmail.append("											<a href='http://"+ request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/pages/trabalheConosco/trabalheConosco.load?id="+cadidatoAux.getHashID()+"'> "+"Recuperar Senha</a>");
			msgEmail.append("											<br><br>Se não foi você quem realizou esta solicitação, favor desconsiderar este e-mail.<br><br>");
			msgEmail.append("										</div>");
			msgEmail.append("									</td>");
			msgEmail.append("									</tr>");
			msgEmail.append("								</tbody>");
			msgEmail.append("							</table>");
			msgEmail.append("							<table style='line-height:1.5;font-size:12px;font-family:Arial,sans-serif;margin-right:30px;margin-left:30px' width='490' border='0' cellspacing='0' cellpadding='0' bgcolor='#ffffff'>");
			msgEmail.append("								<tbody>");
			msgEmail.append("									<tr style='font-size:11px;color:#999999'>");
			msgEmail.append("									<td style='border-top:solid 1px #d9d9d9' colspan='2'>");
			msgEmail.append("								<div style='padding-top:15px;padding-bottom:1px'>");
			msgEmail.append("									<br>Atenciosamente,<br><b>Central IT - Governança Corporativa</b>");
			msgEmail.append("								</div>");
			msgEmail.append("									</td>");
			msgEmail.append("									</tr>");
			msgEmail.append("									<tr><td style='color:#ffffff' colspan='2' height='15'>.</td></tr>");
			msgEmail.append("								</tbody>");
			msgEmail.append("							</table>");
			msgEmail.append("						</div>");
			msgEmail.append("					</td>");
			msgEmail.append("					</tr>");
			msgEmail.append("				</tbody>");
			msgEmail.append("				</table>");
			msgEmail.append("			</center>");
			msgEmail.append("		</td>");
			msgEmail.append("		</tr>");
			msgEmail.append("	</tbody>");
			msgEmail.append("</table>");
			
			CandidatoTrabalheConosco.sendSimpleMail("Recuperação de conta.",cadidatoAux.getEmail(),usuarioEmail,msgEmail.toString());
			
			document.alert(UtilI18N.internacionaliza(request,"candidato.emailenviadosucesso"));
			
			document.executeScript("window.location.href = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/loginCandidato/loginCandidato.load'");
		}else{
			document.alert(UtilI18N.internacionaliza(request, "candidato.nenhumaContaEncontrada"));
		}
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}
	
	public String getRemetenteEmail() throws Exception {
		String remetente = ParametroUtil.getValor(ParametroSistema.RemetenteNotificacoesSolicitacao);
		if (remetente == null)
			throw new LogicException("Remetente para notificações de solicitação de serviço não foi parametrizado");
		return remetente;
	}
	
	
/*    public static void sendSimpleMail(String subject, String to, String from, String mensagem) throws AddressException, MessagingException, NamingException, IOException {
        
    	try{
	        Properties mailProps = new Properties(); 
	       
	         mailProps.put("mail.smtp.host", "smtp.gmail.com"); 
	         mailProps.put("mail.smtp.auth", "true");
	         mailProps.put("mail.user", usuario);
	         mailProps.put("mail.from",from);
	         mailProps.put("mail.to",to);
	         mailProps.put("mail.pwd",senha);
	          //Autenticador aut = new Autenticador(usuario,senha);
	          
	         mailProps.put("mail.smtp.socketFactory.port", "465");
	         mailProps.put("mail.smtp.socketFactory.class",
	                                   "javax.net.ssl.SSLSocketFactory");
	         mailProps.put("mail.smtp.auth", "true");
	         mailProps.put("mail.smtp.port", "465");
	         
	         
	//         Properties props = new Properties();
//	        mailProps.put("mail.smtp.auth", "true");
//	        mailProps.put("mail.smtp.starttls.enable", "true");
//	        mailProps.put("mail.smtp.host", "smtp.gmail.com");
//	        mailProps.put("mail.smtp.port", "465");
//	          
	        
	//        String[] destinatarios = to.split(MAIL_ADDRESS_SEPARATOR);
	        Context ic = new InitialContext();
	        //Session mailSession = (Session) ic.lookup("java:comp/env/mail/TheMailSession");
	        Session mailSession = Session.getInstance(props, authenticator)
	        Session mailSession = Session.getInstance(mailProps,  new javax.mail.Authenticator() { 
	               protected PasswordAuthentication getPasswordAuthentication() { 
	                     return new PasswordAuthentication(usuario, senha);
	                             }
	                        });
	        
	        //Properties mailProps = new Properties();
	        //mailProps.put("mail.smtp.host", MAIL_SERVER);
	        //Session mailSession = Session.getDefaultInstance(mailProps, null);
	        
	//        LOGGER.info("-----> vai entrar no laco...");
	//        for (int i = 0; i < destinatarios.length; i++) {
	               InternetAddress destinatario = new InternetAddress(to);
	               InternetAddress remetente = new InternetAddress(from);
	//               LOGGER.info(remetente);
	//               LOGGER.info(" -------- INICIO simple mail --------");
	//               LOGGER.info("E-mail do Destinatário: " + destinatario);
	//               LOGGER.info("Remetente:    " + remetente);
	//               LOGGER.info(" -------- FIM simple mail --------");
	               Message message = new MimeMessage(mailSession);
	               
	               //Message message = prepareHeader(from, to, "", "", subject);
	               
	               message.setFrom(remetente);
	               message.setRecipient(Message.RecipientType.TO, destinatario);
	               message.setSubject(subject);
	               message.setContent(mensagem.toString(), "text/html");
	
	               Transport.send(message);
	//               LOGGER.info("-----> fez o send...");
    		} catch (Exception e) {
    			System.out.println("PROBLEMAS AO ENVIAR EMAIL! ");
    			System.out.println("[E]ERROR: " + e);
    			e.printStackTrace(System.out);
    			throw e;
    		}	
        
  }*/

	
	
	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return CandidatoDTO.class;
	}

}

