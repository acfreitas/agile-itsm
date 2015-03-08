package br.com.citframework.push.google;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import br.com.citframework.push.AbstractPushMessageServiceImpl;
import br.com.citframework.push.ConfigPushService.Key;
import br.com.citframework.push.PushServiceException;
import br.com.citframework.util.Assert;

import com.google.gson.Gson;

/**
 * Implemetação para push messages utilização do <a href="http://developer.android.com/google/gcm/gs.html">Google Cloude Message</a>
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 15/11/2014
 */
public class GoogleCloudMessageServiceImpl extends AbstractPushMessageServiceImpl<GoogleCloudMessageRequest<?>, GoogleCloudMessageResponse> {

    private static final Logger LOGGER = Logger.getLogger(GoogleCloudMessageServiceImpl.class);

    private static final Gson GSON = new Gson();
    private static final HttpClient CLIENT = HttpClientBuilder.create().build();
    private static final String GCM_CONSTANT_URL = "https://android.googleapis.com/gcm/send";

    @Override
    public GoogleCloudMessageResponse sendMessage(final GoogleCloudMessageRequest<?> request) throws PushServiceException {
        Assert.notNull(this.getConfig(), "Set config before use Push Service.");

        LOGGER.debug("Envio de message push para GCM - início");
        final HttpPost post = new HttpPost(GCM_CONSTANT_URL);
        this.makeHTTPRequestHeader(post);

        HttpResponse response;
        final String content = GSON.toJson(request);
        try {
            LOGGER.debug(String.format("Conteúdo enviado para GCM: %s", content));
            final HttpEntity entity = new StringEntity(content);
            post.setEntity(entity);
            response = CLIENT.execute(post);

            final GoogleCloudMessageResponse gcmResponse = this.makeMessageResponse(response);

            LOGGER.debug("Envio de message push para GCM - fim");

            return gcmResponse;
        } catch (final IOException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PushServiceException(e.getMessage(), e);
        }
    }

    private GoogleCloudMessageResponse makeMessageResponse(final HttpResponse httpResponse) throws IOException {
        GoogleCloudMessageResponse response = new GoogleCloudMessageResponse();
        try (StringWriter writer = new StringWriter()) {
            final HttpEntity content = httpResponse.getEntity();
            IOUtils.copy(content.getContent(), writer, null);
            final String theString = writer.toString();
            LOGGER.debug(String.format("Conteúdo recebido da GCM: %s", theString));
            response = GSON.fromJson(theString, GoogleCloudMessageResponse.class);
            response.setHttpCode(httpResponse.getStatusLine().getStatusCode());
        }
        return response;
    }

    private void makeHTTPRequestHeader(final HttpRequest request) {
        request.addHeader(HttpHeaders.AUTHORIZATION, String.format("key=%s", this.getConfig().getByKey(Key.GOOLE_API_KEY)));
        request.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
    }

}
