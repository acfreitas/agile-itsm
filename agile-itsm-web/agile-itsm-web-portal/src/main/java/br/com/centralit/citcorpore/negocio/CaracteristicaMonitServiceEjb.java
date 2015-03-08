package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CaracteristicaMonitDTO;
import br.com.centralit.citcorpore.integracao.CaracteristicaMonitDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author euler.ramos
 *
 */
public class CaracteristicaMonitServiceEjb extends CrudServiceImpl implements CaracteristicaMonitService {

    private CaracteristicaMonitDAO dao;

    @Override
    protected CaracteristicaMonitDAO getDao() {
        if (dao == null) {
            dao = new CaracteristicaMonitDAO();
        }
        return dao;
    }

    @Override
    public Collection<CaracteristicaMonitDTO> restoreByIdMonitoramentoAtivos(final Integer idMonitoramentoAtivos) throws Exception {
        return this.getDao().restoreByIdMonitoramentoAtivos(idMonitoramentoAtivos);
    }

}
