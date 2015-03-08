package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.centralit.citcorpore.integracao.LocalidadeDAO;
import br.com.citframework.service.CrudServiceImpl;

public class LocalidadeServiceEjb extends CrudServiceImpl implements LocalidadeService {

    private LocalidadeDAO dao;

    @Override
    protected LocalidadeDAO getDao() {
        if (dao == null) {
            dao = new LocalidadeDAO();
        }
        return dao;
    }

    @Override
    public boolean verificarLocalidadeAtiva(final LocalidadeDTO obj) throws Exception {
        return this.getDao().verificarLocalidadeAtiva(obj);
    }

    @Override
    public Collection<LocalidadeDTO> listLocalidade() throws Exception {
        return this.getDao().listLocalidade();
    }

}
