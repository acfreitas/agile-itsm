package br.com.citframework.service;

import java.util.Collection;

import br.com.citframework.dto.LogDados;

/**
 * @author karem.ricarte
 *
 */
public interface LogDadosService extends CrudService {

    Collection<LogDados> listAllLogs() throws Exception;

    Collection<LogDados> listLogs(final LogDados log) throws Exception;

    Collection<LogDados> listNomeTabela() throws Exception;

}
