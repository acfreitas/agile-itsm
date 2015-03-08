package br.com.citframework.push;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Padrão de mensagem de response
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 15/11/2014
 */
public class MessageResponse {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
