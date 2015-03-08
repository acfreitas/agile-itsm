package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoUsuarioMonitDTO;
import br.com.centralit.citcorpore.integracao.NotificacaoUsuarioMonitDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author euler.ramos
 *
 */
public class NotificacaoUsuarioMonitServiceEjb extends CrudServiceImpl implements NotificacaoUsuarioMonitService {

    private NotificacaoUsuarioMonitDAO dao;

    @Override
    protected NotificacaoUsuarioMonitDAO getDao() {
        if (dao == null) {
            dao = new NotificacaoUsuarioMonitDAO();
        }
        return dao;
    }

    @Override
    public Collection<NotificacaoUsuarioMonitDTO> restoreByIdMonitoramentoAtivos(final Integer idMonitoramentoAtivos) throws Exception {
        return this.getDao().restoreByIdMonitoramentoAtivos(idMonitoramentoAtivos);
    }

}
