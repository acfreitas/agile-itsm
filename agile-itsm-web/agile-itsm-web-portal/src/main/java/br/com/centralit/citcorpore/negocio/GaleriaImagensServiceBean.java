package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.GaleriaImagensDao;
import br.com.citframework.service.CrudServiceImpl;

public class GaleriaImagensServiceBean extends CrudServiceImpl implements GaleriaImagensService {

    private GaleriaImagensDao dao;

    @Override
    protected GaleriaImagensDao getDao() {
        if (dao == null) {
            dao = new GaleriaImagensDao();
        }
        return dao;
    }

    @Override
    public Collection findByCategoria(final Integer idCategoria) throws Exception {
        return this.getDao().findByCategoria(idCategoria);
    }

    @Override
    public Collection listOrderByCategoria() throws Exception {
        return this.getDao().listOrderByCategoria();
    }

}
