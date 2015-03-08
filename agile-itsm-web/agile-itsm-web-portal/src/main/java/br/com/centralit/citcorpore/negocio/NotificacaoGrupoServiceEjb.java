package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoGrupoDTO;
import br.com.centralit.citcorpore.integracao.NotificacaoGrupoDao;
import br.com.citframework.service.CrudServiceImpl;

public class NotificacaoGrupoServiceEjb extends CrudServiceImpl implements NotificacaoGrupoService {

    private NotificacaoGrupoDao dao;

    @Override
    protected NotificacaoGrupoDao getDao() {
        if (dao == null) {
            dao = new NotificacaoGrupoDao();
        }
        return dao;
    }

    @Override
    public Collection<NotificacaoGrupoDTO> listaIdGrupo(final Integer idNotificacao) throws Exception {
        return this.getDao().listaIdGrupo(idNotificacao);
    }

}
