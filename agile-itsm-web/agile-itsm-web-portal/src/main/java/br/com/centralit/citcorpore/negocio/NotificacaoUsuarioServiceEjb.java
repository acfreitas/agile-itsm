package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO;
import br.com.centralit.citcorpore.integracao.NotificacaoUsuarioDao;
import br.com.citframework.service.CrudServiceImpl;

public class NotificacaoUsuarioServiceEjb extends CrudServiceImpl implements NotificacaoUsuarioService {

    private NotificacaoUsuarioDao dao;

    @Override
    protected NotificacaoUsuarioDao getDao() {
        if (dao == null) {
            dao = new NotificacaoUsuarioDao();
        }
        return dao;
    }

    @Override
    public Collection<NotificacaoUsuarioDTO> listaIdUsuario(final Integer idNotificacao) throws Exception {
        return this.getDao().listaIdUsuario(idNotificacao);
    }

}
