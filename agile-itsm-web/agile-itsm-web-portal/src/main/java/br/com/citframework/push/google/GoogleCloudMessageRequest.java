package br.com.citframework.push.google;

import java.util.List;

import br.com.citframework.push.MessageRequest;

/**
 * Padrão de mensagem de request que o <a href="http://developer.android.com/google/gcm/gs.html">Google Cloud Message</a> espera
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 15/11/2014
 */
public class GoogleCloudMessageRequest<E> extends MessageRequest<E> {

    private String collapse_key;
    private Integer time_to_live;
    private Boolean delay_while_idle;

    private List<String> registration_ids;

    public String getCollapseKey() {
        return this.collapse_key;
    }

    public void setCollapseKey(final String collapse_key) {
        this.collapse_key = collapse_key;
    }

    public Integer getTimeToLive() {
        return this.time_to_live;
    }

    public void setTimeToLive(final Integer time_to_live) {
        this.time_to_live = time_to_live;
    }

    public Boolean getDelayWhileIdle() {
        return this.delay_while_idle;
    }

    public void setDelayWhileIdle(final Boolean delay_while_idle) {
        this.delay_while_idle = delay_while_idle;
    }

    public List<String> getRegistrationIds() {
        return this.registration_ids;
    }

    public void setRegistratioIds(final List<String> registration_ids) {
        this.registration_ids = registration_ids;
    }

}
