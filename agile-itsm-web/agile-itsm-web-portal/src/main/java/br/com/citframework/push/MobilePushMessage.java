package br.com.citframework.push;

import java.io.Serializable;

/**
 * Objeto para envio de mensagem push de atribução de solicitação ao mobile
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 15/11/2014
 */
public class MobilePushMessage implements Serializable {

    private static final long serialVersionUID = -5545944235637401742L;

    private String connection;
    private String userName;

    public String getConnection() {
        return connection;
    }

    public void setConnection(final String connection) {
        this.connection = connection;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

}
