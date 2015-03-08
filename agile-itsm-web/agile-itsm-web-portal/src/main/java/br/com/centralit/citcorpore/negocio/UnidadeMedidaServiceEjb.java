package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.UnidadeMedidaDTO;
import br.com.centralit.citcorpore.integracao.UnidadeMedidaDao;
import br.com.citframework.service.CrudServiceImpl;

public class UnidadeMedidaServiceEjb extends CrudServiceImpl implements UnidadeMedidaService {

    private UnidadeMedidaDao dao;

    @Override
    protected UnidadeMedidaDao getDao() {
        if (dao == null) {
            dao = new UnidadeMedidaDao();
        }
        return dao;
    }

    @Override
    public boolean consultarUnidadesMedidas(final UnidadeMedidaDTO unidadeMedidaDTO) throws Exception {
        return this.getDao().consultarUnidadesMedidas(unidadeMedidaDTO);
    }

}
