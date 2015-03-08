package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.VisaoPersonalizadaDao;
import br.com.citframework.service.CrudServiceImpl;

public class VisaoPersonalizadaServiceEjb extends CrudServiceImpl implements VisaoPersonalizadaService {

    private VisaoPersonalizadaDao dao;

    @Override
    protected VisaoPersonalizadaDao getDao() {
        if (dao == null) {
            dao = new VisaoPersonalizadaDao();
        }
        return dao;
    }

    @Override
    public void deleteAll() throws Exception {
        this.getDao().deleteAll();
    }

}
