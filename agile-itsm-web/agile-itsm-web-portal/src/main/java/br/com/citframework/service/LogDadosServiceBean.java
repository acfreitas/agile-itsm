package br.com.citframework.service;

import java.util.Collection;

import br.com.citframework.dto.LogDados;
import br.com.citframework.integracao.LogDadosDao;

/**
 * @author karem.ricarte
 *
 */
public class LogDadosServiceBean extends CrudServiceImpl implements LogDadosService {

    private LogDadosDao dao;

    @Override
    protected LogDadosDao getDao() {
        if (dao == null) {
            dao = new LogDadosDao(usuario);
        }
        return dao;
    }

    @Override
    public Collection<LogDados> listAllLogs() throws Exception {
        return this.getDao().listAllLogs();
    }

    @Override
    public Collection<LogDados> listLogs(final LogDados log) throws Exception {
        return this.getDao().listLogs(log);
    }

    @Override
    public Collection<LogDados> listNomeTabela() throws Exception {
        return this.getDao().listNomeTabela();
    }

}
