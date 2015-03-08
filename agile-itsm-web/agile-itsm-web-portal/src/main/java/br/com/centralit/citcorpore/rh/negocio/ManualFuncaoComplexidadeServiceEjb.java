package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.ManualFuncaoComplexidadeDao;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class ManualFuncaoComplexidadeServiceEjb extends CrudServiceImpl implements ManualFuncaoComplexidadeService {

    private ManualFuncaoComplexidadeDao dao;

    @Override
    protected ManualFuncaoComplexidadeDao getDao() {
        if (dao == null) {
            dao = new ManualFuncaoComplexidadeDao();
        }
        return dao;
    }

    @Override
    public Collection listAtivos() throws Exception {
        return this.getDao().listAtivos();
    }

}
