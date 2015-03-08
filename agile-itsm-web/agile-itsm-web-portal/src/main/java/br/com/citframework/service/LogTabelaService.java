package br.com.citframework.service;

import br.com.citframework.dto.LogTabela;
import br.com.citframework.excecao.ServiceException;

/**
 * @author karem.ricarte
 *
 */
public interface LogTabelaService extends CrudService {

    LogTabela getLogByTabela(final String nomeTabela) throws ServiceException;

}
