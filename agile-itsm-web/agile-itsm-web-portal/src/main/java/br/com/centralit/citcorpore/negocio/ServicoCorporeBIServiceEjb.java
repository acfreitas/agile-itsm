package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ServicoCorporeBIDTO;
import br.com.centralit.citcorpore.integracao.ServicoCorporeBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class ServicoCorporeBIServiceEjb extends CrudServiceImpl implements ServicoCorporeBIService {

    private ServicoCorporeBIDao dao;

    @Override
    protected ServicoCorporeBIDao getDao() {
        if (dao == null) {
            dao = new ServicoCorporeBIDao();
        }
        return dao;
    }

    @Override
    public Collection<ServicoCorporeBIDTO> findByNome(final String consulta) {
        return this.getDao().findByNome(consulta);
    }

    @Override
    public ServicoCorporeBIDTO findById(final Integer idServicoDe) {
        return this.getDao().findById(idServicoDe);
    }

}
