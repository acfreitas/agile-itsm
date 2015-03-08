package br.com.centralit.citsmart.rest.operation;

import javax.xml.bind.JAXBException;

import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtMessage;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;

/**
 * Interface para execu��o de uma opera��o REST cadastrada no sistema {@link RestOperationDTO}
 *
 * @param <T>
 *            objeto JAXB da request
 * @param <U>
 *            objeto JAXB da response
 */
public interface IRestOperation<T extends CtMessage, U extends CtMessageResp> {

    /**
     * Executa uma opera��o REST previamente cadastrada no sistema
     *
     * @param session
     *            sess�o do usu�rio que realizou a chamada
     * @param operation
     *            opera��o REST
     * @param message
     *            {@code T extends CtMessage}, request a ser processada
     * @return {@code U extends CtMessageResp}, response a ser serializada
     * @throws JAXBException
     */
    U execute(final RestSessionDTO session, final RestOperationDTO operation, final T message) throws JAXBException;

}
