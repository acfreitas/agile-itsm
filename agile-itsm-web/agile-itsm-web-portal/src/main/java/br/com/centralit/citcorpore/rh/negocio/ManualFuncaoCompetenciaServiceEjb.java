package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.ManualFuncaoCompetenciaDao;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class ManualFuncaoCompetenciaServiceEjb extends CrudServiceImpl implements ManualFuncaoCompetenciaService {

    private ManualFuncaoCompetenciaDao dao;

    @Override
    protected ManualFuncaoCompetenciaDao getDao() {
        if (dao == null) {
            dao = new ManualFuncaoCompetenciaDao();
        }
        return dao;
    }

    @Override
    public Collection listAtivos() throws Exception {
        return this.getDao().listAtivos();
    }

}
