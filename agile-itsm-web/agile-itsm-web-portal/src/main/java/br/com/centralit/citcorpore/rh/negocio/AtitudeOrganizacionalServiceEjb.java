package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.rh.integracao.AtitudeOrganizacionalDao;
import br.com.citframework.service.CrudServiceImpl;

public class AtitudeOrganizacionalServiceEjb extends CrudServiceImpl implements AtitudeOrganizacionalService {

    private AtitudeOrganizacionalDao dao;

    @Override
    protected AtitudeOrganizacionalDao getDao() {
        if (dao == null) {
            dao = new AtitudeOrganizacionalDao();
        }
        return dao;
    }

}
