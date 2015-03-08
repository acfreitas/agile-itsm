package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.LimiteAlcadaDao;
import br.com.citframework.service.CrudServiceImpl;

public class LimiteAlcadaServiceEjb extends CrudServiceImpl implements LimiteAlcadaService {

    private LimiteAlcadaDao dao;

    @Override
    protected LimiteAlcadaDao getDao() {
        if (dao == null) {
            dao = new LimiteAlcadaDao();
        }
        return dao;
    }

    @Override
    public void removerPorIdAlcada(final Integer idAlcada) throws Exception {
        this.getDao().removerPorIdAlcada(idAlcada);
    }

    @Override
    public Collection findByIdAlcada(final Integer idAlcada) throws Exception {
        return this.getDao().findByIdAlcada(idAlcada);
    }

}
