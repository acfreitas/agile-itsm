package br.com.centralit.citcorpore.bean;

import java.util.ArrayList;

import br.com.citframework.dto.IDto;

/**
 * @author breno.guimaraes
 * 
 */
public class ClienteEmailCentralServicoDTO implements IDto {

    private static final long serialVersionUID = 4004251311921076618L;

    private String host;
    private String username;
    private String password;
    private String provider;
    private Integer port;
    
    private Integer idContrato;
    private String emailOrigem;
    private String emailMessageId;
    
    private ArrayList<ClienteEmailCentralServicoMessagesDTO> emailMessages;
    private String resultMessage;
    private boolean resultSucess;

    public String getHost() {
    	return host;
    }

    public void setHost(String host) {
    	this.host = host;
    }

    public String getUsername() {
    	return username;
    }

    public void setUsername(String username) {
    	this.username = username;
    }

    public String getPassword() {
    	return password;
    }

    public void setPassword(String password) {
    	this.password = password;
    }

    public String getProvider() {
    	return provider;
    }

    public void setProvider(String provider) {
    	this.provider = provider;
    }

    public Integer getPort() {
    	return port;
    }

    public void setPort(Integer port) {
    	this.port = port;
    }

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getEmailMessageId() {
		return emailMessageId;
	}

	public void setEmailMessageId(String emailMessageId) {
		this.emailMessageId = emailMessageId;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public boolean isResultSucess() {
		return resultSucess;
	}

	public void setResultSucess(boolean resultSucess) {
		this.resultSucess = resultSucess;
	}

	public ArrayList<ClienteEmailCentralServicoMessagesDTO> getEmailMessages() {
		return emailMessages;
	}

	public void setEmailMessages(
			ArrayList<ClienteEmailCentralServicoMessagesDTO> emailMessages) {
		this.emailMessages = emailMessages;
	}

	public String getEmailOrigem() {
		return emailOrigem;
	}

	public void setEmailOrigem(String emailOrigem) {
		this.emailOrigem = emailOrigem;
	}

}
