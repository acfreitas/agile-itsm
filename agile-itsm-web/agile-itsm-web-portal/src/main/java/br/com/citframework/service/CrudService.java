package br.com.citframework.service;

import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;

public interface CrudService extends IService {

    /**
     * Grava o IModel passado como parametro em meio persistente.
     *
     * @param entity
     * @return
     * @throws LogicException
     * @throws ServiceException
     *
     */
    <E extends IDto> E create(final E entity) throws LogicException, ServiceException;

    /**
     * Atualiza o Object passado como parametro em meio persistente.
     *
     * @param entity
     * @return
     * @throws LogicException
     * @throws ServiceException
     *
     */
    <E extends IDto> void update(final E entity) throws LogicException, ServiceException;

    /**
     * Exclui o Object passado como parametro do meio persistente.
     *
     * @param entity
     * @return
     * @throws LogicException
     * @throws ServiceException
     *
     */
    <E extends IDto> void delete(final E entity) throws LogicException, ServiceException;

    /**
     * Recebe um Object com seus atributos chave preenchidos, recupera todos os atributos do meio persistente e retorna o Object Preenchido.
     *
     * @param entity
     * @return
     * @throws LogicException
     * @throws ServiceException
     *
     */
    <E extends IDto> E restore(final IDto entity) throws LogicException, ServiceException;

    <E extends IDto> Collection<E> find(final E entity) throws LogicException, ServiceException;

    <E extends IDto> Collection<E> list() throws LogicException, ServiceException;

}
