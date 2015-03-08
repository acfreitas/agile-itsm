package br.com.centralit.citcorpore.negocio;

import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServiceImpl;

class PerfisAcessoServiceEjb extends CrudServiceImpl implements PerfisAcessoService {

    @Override
    protected CrudDAO getDao() {
        return null;
    }

}
