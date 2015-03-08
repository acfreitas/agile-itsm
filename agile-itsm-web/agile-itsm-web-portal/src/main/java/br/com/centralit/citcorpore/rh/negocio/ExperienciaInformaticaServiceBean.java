package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.ExperienciaInformaticaDao;
import br.com.citframework.service.CrudServiceImpl;

public class ExperienciaInformaticaServiceBean extends CrudServiceImpl implements ExperienciaInformaticaService {

    private ExperienciaInformaticaDao dao;

    @Override
    protected ExperienciaInformaticaDao getDao() {
        if (dao == null) {
            dao = new ExperienciaInformaticaDao();
        }
        return dao;
    }

    @Override
    public Collection findByNotIdFuncao(final Integer idFuncao) throws Exception {
        return this.getDao().findByNotIdFuncao(idFuncao);
    }

}
