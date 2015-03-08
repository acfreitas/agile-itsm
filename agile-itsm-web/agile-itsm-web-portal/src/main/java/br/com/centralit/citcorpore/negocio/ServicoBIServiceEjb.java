package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ServicoBIDTO;
import br.com.centralit.citcorpore.integracao.ServicoBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class ServicoBIServiceEjb extends CrudServiceImpl implements ServicoBIService {

    private ServicoBIDao dao;

    @Override
    protected ServicoBIDao getDao() {
        if (dao == null) {
            dao = new ServicoBIDao();
        }
        return dao;
    }

    @Override
    public Collection<ServicoBIDTO> findByNomeEconexaoBI(final String consulta, final Integer idConexaoBI) {
        return this.getDao().findByNomeEconexaoBI(consulta, idConexaoBI);
    }

    @Override
    public ServicoBIDTO findByIdEconexaoBI(final Integer idServicoDe, final Integer idConexaoBI) {
        return this.getDao().findByIdEconexaoBI(idServicoDe, idConexaoBI);
    }

}
