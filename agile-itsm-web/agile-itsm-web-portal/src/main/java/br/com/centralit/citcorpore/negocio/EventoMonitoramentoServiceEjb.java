/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.EventoMonitoramentoDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author Vadoilo Damasceno
 *
 */
public class EventoMonitoramentoServiceEjb extends CrudServiceImpl implements EventoMonitoramentoService {

    private EventoMonitoramentoDAO dao;

    @Override
    protected EventoMonitoramentoDAO getDao() {
        if (dao == null) {
            dao = new EventoMonitoramentoDAO();
        }
        return dao;
    }

}
