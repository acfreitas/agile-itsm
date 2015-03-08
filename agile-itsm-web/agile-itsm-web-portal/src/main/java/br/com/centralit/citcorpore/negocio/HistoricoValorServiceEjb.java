package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoValorDTO;
import br.com.centralit.citcorpore.integracao.HistoricoValorDAO;
import br.com.citframework.service.CrudServiceImpl;

public class HistoricoValorServiceEjb extends CrudServiceImpl implements HistoricoValorService {

    private HistoricoValorDAO dao;

    @Override
    protected HistoricoValorDAO getDao() {
        if (dao == null) {
            dao = new HistoricoValorDAO();
        }
        return dao;
    }

    @Override
    public List<HistoricoValorDTO> listHistoricoValorByIdHistoricoIc(final Integer idHistoricoIc) throws Exception {
        return this.getDao().listHistoricoValorByIdHistoricoIc(idHistoricoIc);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Collection findByIdHitoricoIC(final Integer idHistoricoIc) throws Exception {
        return this.getDao().findByIdHitoricoIC(idHistoricoIc);
    }

}
