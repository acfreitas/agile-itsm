package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoGrupoMonitDTO;
import br.com.centralit.citcorpore.integracao.NotificacaoGrupoMonitDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author euler.ramos
 *
 */
public class NotificacaoGrupoMonitServiceEjb extends CrudServiceImpl implements NotificacaoGrupoMonitService {

    private NotificacaoGrupoMonitDAO dao;

    @Override
    protected NotificacaoGrupoMonitDAO getDao() {
        if (dao == null) {
            dao = new NotificacaoGrupoMonitDAO();
        }
        return dao;
    }

    @Override
    public Collection<NotificacaoGrupoMonitDTO> restoreByIdMonitoramentoAtivos(final Integer idMonitoramentoAtivos) throws Exception {
        return this.getDao().restoreByIdMonitoramentoAtivos(idMonitoramentoAtivos);
    }

}
