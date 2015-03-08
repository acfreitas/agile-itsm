package br.com.centralit.citcorpore.negocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.SearchTerm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ClienteEmailCentralServicoDTO;
import br.com.centralit.citcorpore.bean.ClienteEmailCentralServicoMessagesDTO;
import br.com.centralit.citcorpore.bean.EmailSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class ClienteEmailCentralServicoServiceEjb extends CrudServiceImpl implements ClienteEmailCentralServicoService {

	String CONEXAO_EMAIL_SERVIDOR   = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Servidor, ""); //Servidor (ex: imap.gmail.com)
	String CONEXAO_EMAIL_PROVIDER   = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Provider, ""); //Provedor (ex: imaps, pop3s, imap, pop3, etc)
	String CONEXAO_EMAIL_PORTA      = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Porta, ""); // Porta
	String CONEXAO_EMAIL_CAIXA      = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Caixa, ""); //Usuario
	String CONEXAO_EMAIL_SENHA      = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Senha, ""); //Senha
	String CONEXAO_EMAIL_PASTA      = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Pasta, ""); //Pasta de entrada
	String CONEXAO_EMAIL_LIMITE     = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_LIMITE_, "10"); //Limite de e-mails

	public ClienteEmailCentralServicoDTO getMessages(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ClienteEmailCentralServicoDTO clienteEmailCentralServicoDto = new ClienteEmailCentralServicoDTO();
		clienteEmailCentralServicoDto.setResultSucess(true);
		
		try {
			if (CONEXAO_EMAIL_SERVIDOR.equals("") || CONEXAO_EMAIL_PROVIDER.equals("") || CONEXAO_EMAIL_CAIXA.equals("") || CONEXAO_EMAIL_SENHA.equals("") || CONEXAO_EMAIL_PASTA.equals("")) {
				clienteEmailCentralServicoDto.setResultSucess(false);
				clienteEmailCentralServicoDto.setResultMessage(UtilI18N.internacionaliza(request, "clienteEmailCentralServico.problemasRealizarleituraEmailsParametros"));
			} else {
		        Properties props = new Properties();
		        props.setProperty("mail.store.protocol", CONEXAO_EMAIL_PROVIDER);
		        
		        props.setProperty("mail.imaps.auth.plain.disable","true");
		        props.setProperty("mail.imaps.ssl.trust", "*");
		        //props.setProperty("mail.debug", "true");
		        
		        if (!CONEXAO_EMAIL_PORTA.equals("")) props.setProperty("mail." + CONEXAO_EMAIL_PROVIDER + ".port", CONEXAO_EMAIL_PORTA);

	            Session session = Session.getInstance(props, null);
	            Store store = session.getStore();
	            store.connect(CONEXAO_EMAIL_SERVIDOR, CONEXAO_EMAIL_CAIXA, CONEXAO_EMAIL_SENHA);
	            
	            Folder inbox = store.getFolder(CONEXAO_EMAIL_PASTA);
	            inbox.open(Folder.READ_WRITE);

	            Message[] messages = inbox.getMessages();
	            
	            if (messages != null && messages.length > 0) {
	            	ArrayList<ClienteEmailCentralServicoMessagesDTO> emailMessages = new ArrayList<ClienteEmailCentralServicoMessagesDTO>();
	            	for (Message message : messages) {
	            		ClienteEmailCentralServicoMessagesDTO clienteEmailMessagesDto = new ClienteEmailCentralServicoMessagesDTO();
	            		
	    	            MimeMessage m = (MimeMessage) inbox.getMessage(message.getMessageNumber());
	    	            clienteEmailMessagesDto.setMessageId(m.getMessageID());
	    	            clienteEmailMessagesDto.setMessageNumber(message.getMessageNumber());

			            Address[] in = message.getFrom();
			            clienteEmailMessagesDto.setMessageEmail((in == null ? null : ((InternetAddress) in[0]).getAddress()));
			            
			            clienteEmailMessagesDto.setMessageSubject(message.getSubject());
			            clienteEmailMessagesDto.setMessageReceivedDate(message.getReceivedDate());
			            //clienteEmailMessagesDto.setSeen(message.isSet(Flags.Flag.SEEN));
			            clienteEmailMessagesDto.setSeen(true);
			            
			            emailMessages.add(clienteEmailMessagesDto);
	            	}
	            	
	            	clienteEmailCentralServicoDto.setEmailMessages(emailMessages);
	            }
			} 
		} catch (Exception e) {
			e.printStackTrace();
			clienteEmailCentralServicoDto.setResultSucess(false);
			clienteEmailCentralServicoDto.setResultMessage(UtilI18N.internacionaliza(request, "clienteEmailCentralServico.problemasRealizarleituraEmails"));
		}
		
		return clienteEmailCentralServicoDto;
	}
	
	public ClienteEmailCentralServicoDTO getMessagesByLimitAndNoRequest(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EmailSolicitacaoServicoService emailSolicitacaoServicoService = (EmailSolicitacaoServicoService) ServiceLocator.getInstance().getService(EmailSolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		ClienteEmailCentralServicoDTO clienteEmailCentralServicoDto = new ClienteEmailCentralServicoDTO();
		clienteEmailCentralServicoDto.setResultSucess(true);
		
		try {
			if (CONEXAO_EMAIL_SERVIDOR.equals("") || CONEXAO_EMAIL_PROVIDER.equals("") || CONEXAO_EMAIL_CAIXA.equals("") || CONEXAO_EMAIL_SENHA.equals("") || CONEXAO_EMAIL_PASTA.equals("")) {
				clienteEmailCentralServicoDto.setResultSucess(false);
				clienteEmailCentralServicoDto.setResultMessage(UtilI18N.internacionaliza(request, "clienteEmailCentralServico.problemasRealizarleituraEmailsParametros"));
			} else {
		        Properties props = new Properties();
		        props.setProperty("mail.store.protocol", CONEXAO_EMAIL_PROVIDER);
		        
		        props.setProperty("mail.imaps.auth.plain.disable","true");
		        props.setProperty("mail.imaps.ssl.trust", "*");
		        //props.setProperty("mail.debug", "true");
		        
		        if (!CONEXAO_EMAIL_PORTA.equals("")) props.setProperty("mail." + CONEXAO_EMAIL_PROVIDER + ".port", CONEXAO_EMAIL_PORTA);

	            Session session = Session.getInstance(props, null);
	            Store store = session.getStore();
	            store.connect(CONEXAO_EMAIL_SERVIDOR, CONEXAO_EMAIL_CAIXA, CONEXAO_EMAIL_SENHA);
	            
	            Folder inbox = store.getFolder(CONEXAO_EMAIL_PASTA);
	            inbox.open(Folder.READ_WRITE);
	            
	            Message[] messages = inbox.getMessages();
	            
	            if (messages != null && messages.length > 0) {
	            	ArrayUtils.reverse(messages);
	            	
	            	Integer limiteEmails = 10;
	            	
	            	try {
		        		limiteEmails = Integer.parseInt(CONEXAO_EMAIL_LIMITE);
		        	} catch (NumberFormatException e) {
		        		e.printStackTrace();
		        		limiteEmails = 10;
		        	}
	            	
	            	ArrayList<ClienteEmailCentralServicoMessagesDTO> emailMessages = new ArrayList<ClienteEmailCentralServicoMessagesDTO>();

	            	for (Message message : messages) {
	            		if (emailMessages.size() < limiteEmails) {
		            		MimeMessage m = (MimeMessage) inbox.getMessage(message.getMessageNumber());
		            		EmailSolicitacaoServicoDTO colEmailDto = emailSolicitacaoServicoService.getEmailByIdMessage(m.getMessageID());
		            		
		            		if (colEmailDto == null) {
			            		ClienteEmailCentralServicoMessagesDTO clienteEmailMessagesDto = new ClienteEmailCentralServicoMessagesDTO();

			    	            clienteEmailMessagesDto.setMessageId(m.getMessageID());
			    	            clienteEmailMessagesDto.setMessageNumber(message.getMessageNumber());

					            Address[] in = message.getFrom();
					            clienteEmailMessagesDto.setMessageEmail((in == null ? null : ((InternetAddress) in[0]).getAddress()));
					            
					            clienteEmailMessagesDto.setMessageSubject(message.getSubject());
					            clienteEmailMessagesDto.setMessageReceivedDate(message.getReceivedDate());
					            
					            //clienteEmailMessagesDto.setSeen(message.isSet(Flags.Flag.SEEN)); //Atrapalha a performance
					            clienteEmailMessagesDto.setSeen(true);

					            emailMessages.add(clienteEmailMessagesDto);
		            		}
	            		} else {
	            			break;
	            		}
	            	}
	            	
	            	clienteEmailCentralServicoDto.setEmailMessages(emailMessages);
	            }
			} 
		} catch (Exception e) {
			e.printStackTrace();
			clienteEmailCentralServicoDto.setResultSucess(false);
			clienteEmailCentralServicoDto.setResultMessage(UtilI18N.internacionaliza(request, "clienteEmailCentralServico.problemasRealizarleituraEmails"));
		}
		
		return clienteEmailCentralServicoDto;
	}
	
	public ClienteEmailCentralServicoDTO readMessage(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, String messageId) throws Exception {
		ClienteEmailCentralServicoDTO clienteEmailCentralServicoDto = new ClienteEmailCentralServicoDTO();
		clienteEmailCentralServicoDto.setResultSucess(true);
		
		try {
			if (CONEXAO_EMAIL_SERVIDOR.equals("") || CONEXAO_EMAIL_PROVIDER.equals("") || CONEXAO_EMAIL_CAIXA.equals("") || CONEXAO_EMAIL_SENHA.equals("") || CONEXAO_EMAIL_PASTA.equals("")) {
				clienteEmailCentralServicoDto.setResultSucess(false);
				clienteEmailCentralServicoDto.setResultMessage(UtilI18N.internacionaliza(request, "clienteEmailCentralServico.problemasRealizarleituraEmailsParametros"));
			} else {
		        Properties props = new Properties();
		        props.setProperty("mail.store.protocol", CONEXAO_EMAIL_PROVIDER);
		        
		        props.setProperty("mail.imaps.auth.plain.disable","true");
		        props.setProperty("mail.imaps.ssl.trust", "*");
		        //props.setProperty("mail.debug", "true");
		        
		        if (!CONEXAO_EMAIL_PORTA.equals("")) props.setProperty("mail." + CONEXAO_EMAIL_PROVIDER + ".port", CONEXAO_EMAIL_PORTA);

	            Session session = Session.getInstance(props, null);
	            Store store = session.getStore();
	            store.connect(CONEXAO_EMAIL_SERVIDOR, CONEXAO_EMAIL_CAIXA, CONEXAO_EMAIL_SENHA);
	            
	            Folder inbox = store.getFolder(CONEXAO_EMAIL_PASTA);
	            inbox.open(Folder.READ_WRITE);

	            SearchTerm searchTerm = new MessageIDTerm(messageId);
	            Message[] messages = inbox.search(searchTerm);
	            
	            if (messages != null && messages.length > 0) {
	            	ArrayList<ClienteEmailCentralServicoMessagesDTO> emailMessages = new ArrayList<ClienteEmailCentralServicoMessagesDTO>();
	            	for (Message message : messages) {
	            		ClienteEmailCentralServicoMessagesDTO clienteEmailMessagesDto = new ClienteEmailCentralServicoMessagesDTO();
	            		
	    	            MimeMessage m = (MimeMessage) inbox.getMessage(message.getMessageNumber());
	    	            clienteEmailMessagesDto.setMessageId(m.getMessageID());
	    	            clienteEmailMessagesDto.setMessageNumber(message.getMessageNumber());

			            Address[] in = message.getFrom();
			            clienteEmailMessagesDto.setMessageEmail((in == null ? null : ((InternetAddress) in[0]).getAddress()));
			            
			            clienteEmailMessagesDto.setMessageSubject(message.getSubject());
			            clienteEmailMessagesDto.setMessageReceivedDate(message.getReceivedDate());
			            clienteEmailMessagesDto.setSeen(message.isSet(Flags.Flag.SEEN));
			            
						Object objRef = message.getContent();
						String content = "";
						
						if (!(objRef instanceof Multipart)) {
							content = (String) message.getContent();
						} else {
				            Multipart mp = (Multipart) message.getContent();
				            BodyPart bp = mp.getBodyPart(0);
				            
				            content = getContent(bp);
						}
						
						if (content != null) {
				            //content = content.replaceAll("(\r\n|\r|\n)", "<br />");
				            content = StringEscapeUtils.escapeJavaScript(content);
				            
				            clienteEmailMessagesDto.setMessageContent(content);
						}
			            
			            emailMessages.add(clienteEmailMessagesDto);
	            	}
	            	
	            	clienteEmailCentralServicoDto.setEmailMessages(emailMessages);
	            }
			} 
		} catch (Exception e) {
			e.printStackTrace();
			clienteEmailCentralServicoDto.setResultSucess(false);
			clienteEmailCentralServicoDto.setResultMessage(UtilI18N.internacionaliza(request, "clienteEmailCentralServico.problemasRealizarleituraEmails"));
		}
		
		return clienteEmailCentralServicoDto;
	}

    /**
     * Return the primary text content of the message.
     */
    public String getContent(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getContent(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getContent(bp);
                    if (s != null)
                        return s;
                } else {
                    return getContent(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getContent(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }
    
	@Override
	protected CrudDAO getDao() {
		return null;
	}

}
