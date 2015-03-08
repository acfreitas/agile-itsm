package br.com.citframework.push;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Entidade que representa o conteúdo de uma messagem a ser enviada na request
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 15/11/2010
 */
public class MessageRequest<E> {

    private E data;

    public E getData() {
        return this.data;
    }

    public void setData(final E data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
