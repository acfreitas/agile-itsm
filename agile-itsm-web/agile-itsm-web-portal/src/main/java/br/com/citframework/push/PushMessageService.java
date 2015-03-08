package br.com.citframework.push;

/**
 * Interface para abstra��o do comportamento de envio de mensagem por push
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 15/11/2014
 */
public interface PushMessageService<E extends MessageRequest<?>, R extends MessageResponse> {

    /**
     * Seta as configura��es a serem utilizadas no servi�o de push
     *
     * @param config
     *            {@link ConfigPushService}
     */
    void configPushService(final ConfigPushService config);

    /**
     * Envia um conte�do por push a um servidor
     *
     * @param content
     *            conte�do a ser enviado
     * @return {@link R} response do servidor abstra�da em uma mensagem
     * @throws PushServiceException
     */
    R sendMessage(final E messageRequest) throws PushServiceException;

}
