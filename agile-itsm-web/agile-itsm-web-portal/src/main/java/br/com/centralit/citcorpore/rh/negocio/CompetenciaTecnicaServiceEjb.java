package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.CompetenciaTecnicaDao;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class CompetenciaTecnicaServiceEjb extends CrudServiceImpl implements CompetenciaTecnicaService {

    private CompetenciaTecnicaDao dao;

    @Override
    protected CompetenciaTecnicaDao getDao() {
        if (dao == null) {
            dao = new CompetenciaTecnicaDao();
        }
        return dao;
    }

    @Override
    public Collection findByNome(final String nome) throws Exception {
        return this.getDao().findByNome(nome);
    }

}
