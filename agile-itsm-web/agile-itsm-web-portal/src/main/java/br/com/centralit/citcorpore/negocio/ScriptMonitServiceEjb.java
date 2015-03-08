package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ScriptMonitDTO;
import br.com.centralit.citcorpore.integracao.ScriptMonitDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author euler.ramos
 *
 */
public class ScriptMonitServiceEjb extends CrudServiceImpl implements ScriptMonitService {

    private ScriptMonitDAO dao;

    @Override
    protected ScriptMonitDAO getDao() {
        if (dao == null) {
            dao = new ScriptMonitDAO();
        }
        return dao;
    }

    @Override
    public Collection<ScriptMonitDTO> restoreByIdMonitoramentoAtivos(final Integer idMonitoramentoAtivos) throws Exception {
        return this.getDao().restoreByIdMonitoramentoAtivos(idMonitoramentoAtivos);
    }

}
