package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.TipoSoftwareDAO;
import br.com.citframework.service.CrudServiceImpl;

public class TipoSoftwareServiceEjb extends CrudServiceImpl implements TipoSoftwareService {

    private TipoSoftwareDAO dao;

    @Override
    protected TipoSoftwareDAO getDao() {
        if (dao == null) {
            dao = new TipoSoftwareDAO();
        }
        return dao;
    }

}
