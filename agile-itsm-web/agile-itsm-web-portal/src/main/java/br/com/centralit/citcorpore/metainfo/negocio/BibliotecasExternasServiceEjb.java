package br.com.centralit.citcorpore.metainfo.negocio;

import br.com.centralit.citcorpore.metainfo.integracao.BibliotecasExternasDao;
import br.com.citframework.service.CrudServiceImpl;

public class BibliotecasExternasServiceEjb extends CrudServiceImpl implements BibliotecasExternasService {

    private BibliotecasExternasDao dao;

    @Override
    protected BibliotecasExternasDao getDao() {
        if (dao == null) {
            dao = new BibliotecasExternasDao();
        }
        return dao;
    }

}
