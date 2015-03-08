package br.com.centralit.citcorpore.rh.ajaxForms;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.negocio.CandidatoService;
import br.com.centralit.citcorpore.util.CitCorporeConstantes;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author flavio.santana
 *
 */
@SuppressWarnings({"rawtypes" })
public class CandidatoTrabalheConosco extends Candidato {
		
	@Override
	public Class getBeanClass() {
		return CandidatoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CandidatoDTO candidatoDTO = (CandidatoDTO)request.getSession().getAttribute("CANDIDATO");
		if(candidatoDTO != null) {
			request.setAttribute("idCandidato",candidatoDTO.getIdCandidato());
			request.setAttribute("nome", candidatoDTO.getNome());
			request.setAttribute("email",candidatoDTO.getEmail());
			request.setAttribute("cpf",candidatoDTO.getCpf());
//			request.setAttribute("dataInicio",candidatoDTO.getDataInicio());
			request.setAttribute("dataFim",candidatoDTO.getDataFim());
			request.setAttribute("situacao",candidatoDTO.getSituacao());
			
			request.setAttribute("nomeCandidatoAbrev", abreviarNomeCandidato(candidatoDTO.getNome()));
			request.setAttribute("nomeCandidato", candidatoDTO.getNome());
			request.setAttribute("emailCandidato",candidatoDTO.getEmail());
			request.setAttribute("metodoAutenticacao",candidatoDTO.getMetodoAutenticacao());
			
		} 
		
	}
	
	/**
	 * Salva ou atualiza candidato
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			usuario = new UsuarioDTO();
			usuario.setIdUsuario(1);
		}
		
		String usuarioEmail = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
		
		if (usuarioEmail == null){
    		usuarioEmail = "citsmart@centralit.com.br";
    	}
		
		CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);
		CandidatoDTO candidatoDTO = (CandidatoDTO) document.getBean();
		candidatoDTO.setIdResponsavel(usuario.getIdUsuario());
		
		if (request.getSession().getAttribute("CANDIDATO") == null) {
			candidatoDTO.setTipo("F");
			//candidatoDTO.setCpf(candidatoDTO.getCpf().replaceAll("[^0-9]*",""));
			if (candidatoService.consultarCandidatosAtivos(candidatoDTO)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				document.executeScript("fecha_aguarde()");
				return;
			}
			
			CandidatoDTO candidatoDtoAux = null;
			candidatoDtoAux = candidatoService.findByEmail(candidatoDTO.getEmail());
			
			if(candidatoDtoAux != null){
				document.alert(UtilI18N.internacionaliza(request, "candidato.emailEmUso"));
				document.executeScript("fecha_aguarde()");
				return;
			}
			
			if(candidatoDTO.getSenha() == null || candidatoDTO.getSenha().equalsIgnoreCase("")){
				document.alert(UtilI18N.internacionaliza(request, "candidato.senhaObrigatorio"));
				return;
			}
			String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
			if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
				algoritmo = "SHA-1";
			}	
			candidatoDTO.setSenha(CriptoUtils.generateHash(candidatoDTO.getSenha(), algoritmo));
			candidatoDTO.setDataInicio(UtilDatas.getDataAtual());
			candidatoDTO.setSituacao("C");
			candidatoDTO.setAutenticado("N");
			String hashID = CriptoUtils.generateHash(candidatoDTO.getCpf()+"", algoritmo);
			candidatoDTO.setHashID(hashID);
			candidatoDTO = (CandidatoDTO)candidatoService.create(candidatoDTO);
			document.executeScript("fecha_aguarde()");
			
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
			msgEmail.append("											Olá <b>"+candidatoDTO.getNome());
			msgEmail.append("											</b><br>Para Ativar o seu Cadastro, clique no link abaixo.<br><br>");
			msgEmail.append("											<a href='http://"+ request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/pages/trabalheConosco/trabalheConosco.load?id="+hashID+"&autentica=S'>Ativar Cadastro</a><br>");
			msgEmail.append("											<br>Depois de ativar, você poderá complementar seu currículo.<br><br>");
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
			
			System.out.println(msgEmail.toString());
			sendSimpleMail("Confirmação de conta.",candidatoDTO.getEmail(),usuarioEmail,msgEmail.toString());	
			candidatoDTO.setCpf(candidatoDTO.getCpf().replaceAll("[^0-9]*",""));
			
			document.alert(UtilI18N.internacionaliza(request, "candidato.paraValidacaoContaEmail"));
			document.executeScript("window.location = '" +CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+"/pages/loginCandidato/loginCandidato.load'; ");	
			
		} else {
			
			//candidatoDTO.setCpf(candidatoDTO.getCpf().replaceAll("[^0-9]*",""));
			if(candidatoDTO.getSenha() == null || candidatoDTO.getSenha().equals("")  ){
				CandidatoDTO auxCandidatoDTO = (CandidatoDTO) candidatoService.restore(candidatoDTO);
				candidatoDTO.setSenha(auxCandidatoDTO.getSenha());
			}else{
				String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
				if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
					algoritmo = "SHA-1";
				}	
				candidatoDTO.setSenha(CriptoUtils.generateHash(candidatoDTO.getSenha(), algoritmo));
			}
			candidatoService.updateNotNull(candidatoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			document.executeScript("window.location = '" +CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+"/pages/trabalheConosco/trabalheConosco.load'; ");	
		}
		document.executeScript("fecha_aguarde()");
			
				

	}
	
	private String abreviarNomeCandidato(String nameUser) {
		StringBuilder finalNameUser = new StringBuilder();
		if(nameUser != null){
			String[] array = new String[15];
			int index;
			
			if(nameUser.contains(" ")){
				int cont = 0;
			
				nameUser = nameUser.trim();
				array = nameUser.split(" ");
				index = array.length;
				
				for(String nome : array){
					if(cont == 0){
						finalNameUser.append(nome.toUpperCase() + " ");
						cont++;
					}else{
						if(cont == index-1){
							finalNameUser.append(" " + nome.toUpperCase());
						}else{
							if(nome.length() > 3){
								finalNameUser.append(nome.substring(0, 1).toUpperCase() + ". ");
							}
							cont++;
						}
					}
				}					
			}else{
				finalNameUser.append(nameUser.toUpperCase());
			}
		}
		return finalNameUser.toString();
	}
	
    public static void sendSimpleMail(String subject, String to, String from, String mensagem) throws AddressException, MessagingException, NamingException, IOException {
    	MensagemEmail em = new MensagemEmail(to, null, null,  from, subject, mensagem);
		try {
			em.envia(to, null, from);
		} catch (Exception e) {
			e.printStackTrace();
		}
/*
        Properties mailProps = new Properties(); 
       
         mailProps.put("mail.smtp.host", "smtp.gmail.com"); 
         mailProps.put("mail.smtp.auth", "true");
         mailProps.put("mail.user", usuarioEmail);
         mailProps.put("mail.from",from);
         mailProps.put("mail.to",to);
         mailProps.put("mail.pwd",senha);
          //Autenticador aut = new Autenticador(usuario,senha);
          
         mailProps.put("mail.smtp.socketFactory.port", "465");
         mailProps.put("mail.smtp.socketFactory.class",
                                   "javax.net.ssl.SSLSocketFactory");
         mailProps.put("mail.smtp.auth", "true");
         mailProps.put("mail.smtp.port", "465");
          

        Context ic = new InitialContext();
        //Session mailSession = (Session) ic.lookup("java:comp/env/mail/TheMailSession");
        Session mailSession = Session.getInstance(props, authenticator)
        Session mailSession = Session.getInstance(mailProps,  new javax.mail.Authenticator() { 
               protected PasswordAuthentication getPasswordAuthentication() { 
                     return new PasswordAuthentication(usuarioEmail, senha);
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
        
  }*/
    }
}
