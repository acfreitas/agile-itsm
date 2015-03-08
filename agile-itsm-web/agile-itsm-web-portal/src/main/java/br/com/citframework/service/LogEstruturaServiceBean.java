package br.com.citframework.service;

import br.com.citframework.integracao.LogEstruturaDao;

/**
 * @author karem.ricarte
 *
 */
public class LogEstruturaServiceBean extends CrudServiceImpl implements LogEstruturaService {

    private LogEstruturaDao dao;

    @Override
    protected LogEstruturaDao getDao() {
        if (dao == null) {
            dao = new LogEstruturaDao(usuario);
        }
        return dao;
    }

}
