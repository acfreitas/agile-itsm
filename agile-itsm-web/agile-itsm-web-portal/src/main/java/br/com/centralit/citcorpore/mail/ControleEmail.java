/*
 * Created on 29/09/2005
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.centralit.citcorpore.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;

/**
 * @author rogerio
 * 
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ControleEmail implements Runnable {

	private String username;
	private String password;
	private String auth;
	private String servidorSMTP;
	private String porta;
	private String starttls;
	private MensagemEmail mensagem;

	public ControleEmail(MensagemEmail mensagem) throws Exception {
		this.username = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EmailUsuario, "");
		this.password = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EmailSenha, "");
		if (ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EmailAutenticacao, "N").equalsIgnoreCase("S")) {
			this.auth = "true";
		} else {
			this.auth = "false";
		}
		this.servidorSMTP = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EmailSMTP, "");
		this.porta = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SMTP_LEITURA_Porta, "587");
		if (ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EmailStartTLS, "N").equalsIgnoreCase("S")) {
			this.starttls = "true";
		} else {
			this.starttls = "false";
		}
		this.mensagem = mensagem;
	}

	public void send(String to, String cc, String bcc, String from, String subject, String text) throws Exception {
		mensagem = new MensagemEmail(to, cc, bcc, from, subject, text);
		this.send();
		return;
	}

	public void send(String to, String cc, String bcc, String from, String subject, String text, boolean confirmarLeituraMail) throws Exception {
		mensagem = new MensagemEmail(to, cc, bcc, from, subject, text);
		mensagem.setConfirmarLeituraMail(confirmarLeituraMail);
		this.send();
		return;
	}

	public void send() throws Exception {
		try {
	    	Properties mailProps=new Properties();
	    	mailProps.put("mail.smtp.auth", this.auth);
	    	mailProps.put("mail.smtp.host", this.servidorSMTP);
	    	mailProps.put("mail.smtp.port", this.porta);
	    	mailProps.put("mail.smtp.starttls.enable", this.starttls);
            
	    	/**
	    	 * Motivo: Alteração para resolução de incidente. Se não exige autenticação pelo parametro então o mesmo não será atribuido
	    	 * Autor: flavio.santana
	    	 * Data/Hora: 02/12/2013
	    	 */
	    	
	    	Session mailSession = null;
	    	if (ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EmailAutenticacao, "N").equalsIgnoreCase("S")) {
	    		mailSession = Session.getInstance(mailProps, new javax.mail.Authenticator() {
	    			protected PasswordAuthentication getPasswordAuthentication() {
	    				return new PasswordAuthentication(username, password);
	    			}
	    		});
	    	} else {
	    		mailSession = Session.getInstance(mailProps);
	    	}
	    	
			mailSession.setDebug(false);
			Message email = new MimeMessage(mailSession);
			
			email.setFrom(new InternetAddress(mensagem.getFrom()));
			email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mensagem.getTo()));
			if (mensagem.getCc() != null && mensagem.getCc().trim().length() > 0) {
				email.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mensagem.getCc()));
			}
			if (mensagem.getCco() != null && mensagem.getCco().trim().length() > 0) {
				email.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mensagem.getCco()));
			}
			
			email.setSubject(MimeUtility.encodeText(mensagem.getSubject(), "ISO-8859-1", "B"));
			
			try {
				email.setSentDate(new Date());
			} catch (Exception e) {
				System.out.println("ERRO AO SETAR A DATA EM Message email = new MimeMessage(mailSession)");
				e.printStackTrace();
			}
			
            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
         	mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
         	mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
         	mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
         	mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
         	mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
         	CommandMap.setDefaultCommandMap(mc);

			email.setContent((StringEscapeUtils.unescapeHtml(mensagem.getText())), "text/html; charset=ISO-8859-1;");
			
			// Adicionar header para pedir confirmacao de leitura
			if (mensagem.isConfirmarLeituraMail()) { 
				email.addHeader("Disposition-Notification-To", mensagem.getFrom());
			}
			Transport.send(email);
		} catch (Exception e) {
			System.out.println("PROBLEMAS AO ENVIAR EMAIL! ");
			System.out.println("[E]ERROR: " + e);
			//e.printStackTrace(System.out);
			//throw e;
		}
		return;
	}

/*	Codigo antigo
 * public void send() throws Exception {
		if (USE_MAIL) {
			try {
				Properties mailProps = new Properties();
				mailProps.put("mail.smtp.host", ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EmailSMTP, null));
				mailProps.put("mail.transport.protocol", "smtp");
				Autenticador auth;
				Session mailSession;
				if (!NEED_AUTH) {
					// Não ha necessidade de autenticacao
					mailProps.put("mail.smtp.auth", "false");
					mailSession = Session.getInstance(mailProps);
				} else {
					ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EmailAutenticacao, "N").equalsIgnoreCase("S");
					// caso haja necessidade de autenticacao
					auth = new Autenticador(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EmailUsuario, null), ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EmailSenha, null));
					mailProps.put("mail.smtp.auth", "true");
					mailProps.put("mail.smtp.submitter", auth.username);
					mailProps.put("mail.user", auth.username);
					mailProps.put("mail.pwd", auth.password);
					mailProps.put("mail.password", auth.password);
					mailProps.put("mail.from", mensagem.getFrom());
					mailProps.put("mail.to", mensagem.getTo());
					String parametroGmail = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SMTP_GMAIL, "N");
					if (parametroGmail != null && !StringUtils.isEmpty(parametroGmail) && StringUtils.contains(parametroGmail, "S")) {
						mailProps.put("mail.smtp.starttls.enable", "true");
						mailProps.put("mail.smtp.socketFactory.port", (ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SMTP_LEITURA_Porta, "465")));
						mailProps.put("mail.smtp.socketFactory.fallback", "false");
						mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
					}
					mailSession = Session.getInstance(mailProps, auth);
				}
				mailSession.setDebug(false);
				Message email = new MimeMessage(mailSession);
				email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mensagem.getTo()));
				if (mensagem.getCc() != null && mensagem.getCc().trim().length() > 0) {
					email.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mensagem.getCc()));
				}
				if (mensagem.getCco() != null && mensagem.getCco().trim().length() > 0) {
					email.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mensagem.getCco()));
				}
				email.setFrom(new InternetAddress(mensagem.getFrom()));
				email.setSubject(MimeUtility.encodeText(mensagem.getSubject(), "ISO-8859-1", "B"));
				try {
					email.setSentDate(new Date());
				} catch (Exception e) {
					System.out.println("ERRO AO SETAR A DATA EM Message email = new MimeMessage(mailSession)");
					e.printStackTrace();
				}
				// email.setContent(msg.getText(), "text/html; charset=" +
				// System.getProperty("file.encoding") + ";");
				email.setContent(Util.encodeHTML(mensagem.getText()), "text/html; charset=ISO-8859-1;");
				if (mensagem.isConfirmarLeituraMail()) { // Adiciona header para
					// pedir confirmacao de
					// leitura
					email.addHeader("Disposition-Notification-To", mensagem.getFrom());
				}
				// Transport transport = mailSession.getTransport();
				Transport.send(email);
				 //transport.connect(); transport.sendMessage(email, email.getRecipients(Message.RecipientType.TO)); transport.close();
			} catch (Exception e) {
				System.out.println("PROBLEMAS AO ENVIAR EMAIL! ");
				System.out.println("[E]ERROR: " + e);
				e.printStackTrace(System.out);
				throw e;
			}
			// System.out.println(" [#] Email enviado");
			return;
		}
	}*/	
	
	@Override
	public void run() {
		try {
			send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}