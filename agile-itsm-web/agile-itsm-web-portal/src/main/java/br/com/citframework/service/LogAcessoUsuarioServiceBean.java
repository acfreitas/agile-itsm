package br.com.citframework.service;

import br.com.citframework.integracao.LogAcessoUsuarioDao;

/**
 * @author karem.ricarte
 *
 */
public class LogAcessoUsuarioServiceBean extends CrudServiceImpl implements LogAcessoUsuarioService {

    private LogAcessoUsuarioDao dao;

    @Override
    protected LogAcessoUsuarioDao getDao() {
        if (dao == null) {
            dao = new LogAcessoUsuarioDao(usuario);
        }
        return dao;
    }

}
