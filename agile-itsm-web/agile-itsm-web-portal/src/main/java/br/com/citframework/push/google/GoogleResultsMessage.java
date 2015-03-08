package br.com.citframework.push.google;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Padrão do objeto "result" na response que o <a href="http://developer.android.com/google/gcm/gs.html">Google Cloud Message</a> retorna
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @date 21/11/2014
 *
 */
public class GoogleResultsMessage {

    private String registration_id;
    private String message_id;
    private String error;

    public String getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(final String registration_id) {
        this.registration_id = registration_id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(final String message_id) {
        this.message_id = message_id;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
