package br.com.centralit.citcorpore.integracao;

import java.net.URI;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.EmailMessageSchema;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.FindItemsResults;
import microsoft.exchange.webservices.data.Item;
import microsoft.exchange.webservices.data.ItemView;
import microsoft.exchange.webservices.data.SearchFilter;
import microsoft.exchange.webservices.data.WebCredentials;
import microsoft.exchange.webservices.data.WellKnownFolderName;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.LogicException;

import com.sun.mail.imap.IMAPFolder;

/**
 * @author breno.guimaraes
 */
public class ClienteEmailCentralServicoDao {

	//Microsoft Exchange
	public ArrayList<Item> getMails() throws MessagingException, LogicException {
		FindItemsResults<Item> results = null;
		EmailMessage email = null;
		String protocolo = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Provider, "");
		
		try {
			String SMTP_LEITURA_Caixa = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Caixa,"");
			String SMTP_LEITURA_Senha = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Senha,"");
			String SMTP_LEITURA_Servidor = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Servidor,"");
			ExchangeService service = new
			ExchangeService(ExchangeVersion.Exchange2007_SP1);
			
			// Provide Crendentials
			ExchangeCredentials credentials = new
			WebCredentials(SMTP_LEITURA_Caixa,SMTP_LEITURA_Senha);
			service.setCredentials(credentials);
			
			// Set Exchange WebSevice URL
			service.setUrl(new URI("https://"+SMTP_LEITURA_Servidor +"/ews/exchange.asmx"));
			
			//Seta a quantidade padrão de emails listados
			String LIMITE =ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_LIMITE_,"");
			Integer limiteEmails = (LIMITE.trim().equalsIgnoreCase("") ? 30 : Integer.valueOf(LIMITE.trim()));
			
			SearchFilter itemFilter = new SearchFilter.IsEqualTo(EmailMessageSchema.IsRead, false);
			//service.FindItems(WellKnownFolderName.Inbox, itemFilter, view);
			 
			// Get five items from mail box
			ItemView view = new ItemView(limiteEmails);
			results =  service.findItems(WellKnownFolderName.Inbox, itemFilter, view);
			 
			// emails in inbox
			return results.getItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(results != null){
			return results.getItems();
		} else {
			return null;
		}
	}
	
	//JavaMail
	public Message[] getEmail2s()throws MessagingException, LogicException{
		
		try {
			Properties pop = new Properties();
			String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			pop.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
			pop.setProperty("mail.pop3.socketFactory.fallback", "true");
			pop.setProperty("mail.pop3.port", ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Porta,""));
			pop.setProperty("mail.pop3.socketFactory.port", ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Porta,""));
			pop.setProperty("mail.store.protocol", "pop");
			pop.setProperty("mail.smtp.host", "smtp.gmail.com");
			Session session = Session.getDefaultInstance(System.getProperties(), null);
			
			String SMTP_LEITURA_Servidor = "imap.gmail.com";
			
			String SMTP_LEITURA_Caixa = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Caixa,"");
			String SMTP_LEITURA_Senha = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SMTP_LEITURA_Senha,"");

			Store store = session.getStore("imaps");
			store.connect(SMTP_LEITURA_Servidor, SMTP_LEITURA_Caixa,SMTP_LEITURA_Senha);
			System.out.println(store);

			Folder inbox = (IMAPFolder) store.getFolder("Inbox");
			inbox.open(Folder.READ_WRITE);
			Message mensagens[];
			mensagens = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));

			return mensagens;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
