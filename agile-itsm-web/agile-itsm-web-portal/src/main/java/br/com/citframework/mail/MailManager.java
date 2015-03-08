/*
 * Created on 29/09/2005
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.citframework.mail;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import br.com.citframework.util.Constantes;

/**
 * @author rogerio
 * 
 * 
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MailManager {

	private boolean NEED_AUTH = false;

	private boolean USE_MAIL = false;

	public MailManager() {
		NEED_AUTH = new Boolean(Constantes.getValue("NEED_AUTH"))
				.booleanValue();
		NEED_AUTH = new Boolean(Constantes.getValue("NEED_AUTH"))
				.booleanValue();
		USE_MAIL = new Boolean(Constantes.getValue("USE_MAIL")).booleanValue();
	}

	public void send(String to, String cc, String bcc, String from,
			String subject, String text) throws Exception {
		MailMessage msg = new MailMessage(to, cc, bcc, from, subject, text);
		this.send(msg);
		return;
	}

	public void send(String to, String cc, String bcc, String from,
			String subject, String text, boolean confirmarLeituraMail)
			throws Exception {
		MailMessage msg = new MailMessage(to, cc, bcc, from, subject, text);
		msg.setConfirmarLeituraMail(confirmarLeituraMail);
		this.send(msg);
		return;
	}

	public void send(MailMessage msg) throws Exception {
		if (USE_MAIL) {

			try {

				Properties mailProps = new Properties();
				mailProps
						.put("mail.smtp.host", Constantes.getValue("SRV_SMTP"));
				mailProps.put("mail.transport.protocol", "smtp");
				Autenticador auth;
				Session mailSession;
				if (!NEED_AUTH) {
					// Não ha necessidade de autenticacao
					mailProps.put("mail.smtp.auth", "false");
					mailSession = Session.getInstance(mailProps);
				} else {
					// caso haja necessidade de autenticacao
					auth = new Autenticador(Constantes.getValue("USER_MAIL"),
							Constantes.getValue("PWD_MAIL"));

					mailProps.put("mail.smtp.auth", "true");
					mailProps.put("mail.smtp.submitter", auth.username);
					mailProps.put("mail.user", auth.username);
					mailProps.put("mail.pwd", auth.password);
					mailProps.put("mail.password", auth.password);
					mailProps.put("mail.from", msg.getFrom());
					mailProps.put("mail.to", msg.getTo());

					mailSession = Session.getInstance(mailProps, auth);
				}

				mailSession.setDebug(false);
				Message email = new MimeMessage(mailSession);
				email.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(msg.getTo()));
				if (msg.getCc() != null && msg.getCc().trim().length() > 0) {
					email.setRecipients(Message.RecipientType.CC,
							InternetAddress.parse(msg.getCc()));
				}

				if (msg.getCco() != null && msg.getCco().trim().length() > 0) {
					email.setRecipients(Message.RecipientType.BCC,
							InternetAddress.parse(msg.getCco()));
				}
				email.setFrom(new InternetAddress(msg.getFrom()));
				email.setSubject(MimeUtility.encodeText(msg.getSubject(), "ISO-8859-1", "B"));
				try {
					email.setSentDate(new Date());
				} catch (Exception e) {
					System.out
							.println("ERRO AO SETAR A DATA EM Message email = new MimeMessage(mailSession)");
					e.printStackTrace();
				}
				// email.setContent(msg.getText(), "text/html; charset=" +
				// System.getProperty("file.encoding") + ";");
				email.setContent(msg.getText(),
						"text/html; charset=ISO-8859-1;");
				if (msg.isConfirmarLeituraMail()) { // Adiciona header para
													// pedir confirmacao de
													// leitura
					email.addHeader("Disposition-Notification-To",
							msg.getFrom());
				}
				// Transport transport = mailSession.getTransport();
				Transport.send(email);
				/*
				 * transport.connect(); transport.sendMessage(email,
				 * email.getRecipients(Message.RecipientType.TO));
				 * transport.close();
				 */
			} catch (Exception e) {
				System.out.println("PROBLEMAS AO ENVIAR EMAIL! "
						+ Constantes.getValue("SRV_SMTP") + " -> "
						+ Constantes.getValue("NEED_AUTH") + " ["
						+ Constantes.getValue("USER_MAIL") + "] - ["
						+ Constantes.getValue("PWD_MAIL") + "]");
				System.out.println("[E]ERROR: " + e);
				e.printStackTrace(System.out);
				throw e;
			}

			System.out.println(" [#] Email enviado");
			return;

		}
	}

	protected Message prepareHeader(String from, String to, String cc,
			String bcc, String subject) throws IOException, AddressException,
			MessagingException {
		int i = 0;
		Properties props = new Properties();
		props.put("mail.smtp.host", Constantes.getValue("SRV_SMTP"));
		props.put("mail.transport.protocol", "smtp");
		Autenticador auth;
		Session session;
		if (!NEED_AUTH) {
			// Não ha necessidade de autenticacao
			props.put("mail.smtp.auth", "false");
			session = Session.getDefaultInstance(props, null);
		} else {
			// caso haja necessidade de autenticacao
			auth = new Autenticador(Constantes.getValue("USER_MAIL"),
					Constantes.getValue("PWD_MAIL"));

			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.submitter", auth.username);
			props.put("mail.user", auth.username);
			props.put("mail.pwd", auth.password);
			props.put("mail.password", auth.password);
			//props.put("mail.from", from);
			//props.put("mail.to", to);

			session = Session.getInstance(props, auth);
		}		
		Message msg = new MimeMessage(session);

		InternetAddress addr;
		if (!to.trim().equalsIgnoreCase("")) {
			String[] auxTo = to.split(";");

			if (auxTo.length <= 0) {
				addr = new InternetAddress(to);
				msg.addRecipients(Message.RecipientType.TO,
						new InternetAddress[] { addr });
			} else {
				for (i = 0; i < auxTo.length; i++) {
					addr = new InternetAddress(auxTo[i]);
					msg.addRecipients(Message.RecipientType.TO,
							new InternetAddress[] { addr });
				}
			}
		}
		if (cc != null && !cc.trim().equalsIgnoreCase("")) {
			String[] auxCC = cc.split(";");

			if (auxCC.length <= 0) {
				addr = new InternetAddress(cc);
				msg.addRecipients(Message.RecipientType.CC,
						new InternetAddress[] { addr });
			} else {
				for (i = 0; i < auxCC.length; i++) {
					addr = new InternetAddress(auxCC[i]);
					msg.addRecipients(Message.RecipientType.CC,
							new InternetAddress[] { addr });
				}
			}
		}
		if (bcc != null && !bcc.trim().equalsIgnoreCase("")) {
			String[] auxBCC = cc.split(";");

			if (auxBCC.length <= 0) {
				addr = new InternetAddress(bcc);
				msg.addRecipients(Message.RecipientType.BCC,
						new InternetAddress[] { addr });
			} else {
				for (i = 0; i < auxBCC.length; i++) {
					addr = new InternetAddress(auxBCC[i]);
					msg.addRecipients(Message.RecipientType.BCC,
							new InternetAddress[] { addr });
				}
			}
		}

		InternetAddress from_addr = new InternetAddress(from);
		msg.setFrom(from_addr);

		msg.setSubject(subject);

		return msg;
	}

	public void sendWithAttachments(String subject, String to, String cc,
			String bcc, String from, String message, Collection attach)
			throws IOException, AddressException, MessagingException {
		if (cc == null)
			cc = "";
		if (bcc == null)
			bcc = "";

		Message msg = prepareHeader(from, to, cc, bcc, subject);

		MimeMultipart mp = new MimeMultipart();

		MimeBodyPart text = new MimeBodyPart();
		text.setDisposition(Part.INLINE);
		text.setContent(message, "text/html");
		mp.addBodyPart(text);

		Iterator itAux = attach.iterator();
		while (itAux.hasNext()) {
			MimeBodyPart file_part = new MimeBodyPart();
			File file = (File) itAux.next();
			FileDataSource fds = new FileDataSource(file);
			DataHandler dh = new DataHandler(fds);
			file_part.setFileName(file.getName());
			file_part.setDisposition(Part.ATTACHMENT);
			file_part.setDescription("Arquivo Anexado: " + file.getName());
			file_part.setDataHandler(dh);
			mp.addBodyPart(file_part);
		}

		msg.setContent(mp);
		Transport.send(msg);
	}
}
