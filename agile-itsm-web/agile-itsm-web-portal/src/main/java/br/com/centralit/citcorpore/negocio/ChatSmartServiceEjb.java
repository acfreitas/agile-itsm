package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ChatSmartDao;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author CentralIT
 *
 */
/**
 * @author Centralit
 *
 */
public class ChatSmartServiceEjb extends CrudServiceImpl implements ChatSmartService {

    private ChatSmartDao dao;

    @Override
    protected ChatSmartDao getDao() {
        if (dao == null) {
            dao = new ChatSmartDao();
        }
        return dao;
    }

}
