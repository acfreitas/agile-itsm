package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.PrioridadeServicoUnidadeDTO;
import br.com.centralit.citcorpore.integracao.PrioridadeServicoUnidadeDao;
import br.com.citframework.service.CrudServiceImpl;

public class PrioridadeServicoUnidadeServiceEjb extends CrudServiceImpl implements PrioridadeServicoUnidadeService {

    private PrioridadeServicoUnidadeDao dao;

    @Override
    protected PrioridadeServicoUnidadeDao getDao() {
        if (dao == null) {
            dao = new PrioridadeServicoUnidadeDao();
        }
        return dao;
    }

    @Override
    public PrioridadeServicoUnidadeDTO restore(final Integer idServicoContrato, final Integer idUnidade) throws Exception {
        return this.getDao().restore(idServicoContrato, idUnidade);
    }

}
