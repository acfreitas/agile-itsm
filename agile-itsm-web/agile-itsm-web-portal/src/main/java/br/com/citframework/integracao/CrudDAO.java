package br.com.citframework.integracao;

import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;

public interface CrudDAO<E extends IDto> extends IDaoTransact {

    E create(final E obj) throws PersistenceException;

    void update(final E obj) throws PersistenceException;

    void delete(final E obj) throws PersistenceException;

    E restore(final IDto obj) throws PersistenceException;

    Collection<E> find(final E obj) throws PersistenceException;

    Collection<E> list() throws PersistenceException;

}
