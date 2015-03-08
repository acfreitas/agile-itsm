package br.com.centralit.citcorpore.negocio;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ClienteEmailCentralServicoDTO;
import br.com.citframework.service.CrudService;

public interface ClienteEmailCentralServicoService extends CrudService {
    public ClienteEmailCentralServicoDTO getMessages(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ClienteEmailCentralServicoDTO getMessagesByLimitAndNoRequest(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception;
    public ClienteEmailCentralServicoDTO readMessage(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, String messageId) throws Exception;
    public String getContent(Part p) throws MessagingException, IOException;
}