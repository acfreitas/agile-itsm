package br.com.citframework.push.google;

import java.util.List;

import br.com.citframework.push.MessageResponse;

/**
 * Padrão de mensagem de response que o <a href="http://developer.android.com/google/gcm/gs.html">Google Cloud Message</a> retorna
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 15/11/2014
 */
public class GoogleCloudMessageResponse extends MessageResponse {

    private int httpCode;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(final int httpCode) {
        this.httpCode = httpCode;
    }

    private Long multicast_id;
    private Integer success;
    private Integer failure;
    private Long canonical_ids;
    private List<GoogleResultsMessage> results;

    public Long getMulticastId() {
        return multicast_id;
    }

    public void setMulticastId(final Long multicast_id) {
        this.multicast_id = multicast_id;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(final Integer success) {
        this.success = success;
    }

    public Integer getFailure() {
        return failure;
    }

    public void setFailure(final Integer failure) {
        this.failure = failure;
    }

    public Long getCanonicalIds() {
        return canonical_ids;
    }

    public void setCanonicalIds(final Long canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    public List<GoogleResultsMessage> getResults() {
        return results;
    }

    public void setResults(final List<GoogleResultsMessage> results) {
        this.results = results;
    }

}
