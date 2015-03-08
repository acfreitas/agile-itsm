package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ItemGrupoAssinaturaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author euler.ramos
 *
 */
public class ItemGrupoAssinaturaServiceEjb extends CrudServiceImpl implements ItemGrupoAssinaturaService {

    private ItemGrupoAssinaturaDAO dao;

    @Override
    protected ItemGrupoAssinaturaDAO getDao() {
        if (dao == null) {
            dao = new ItemGrupoAssinaturaDAO();
        }
        return dao;
    }

    @Override
    public Collection findByIdGrupoAssinatura(final Integer idGrupoAssinatura) throws ServiceException {
        return this.getDao().findByIdGrupoAssinatura(idGrupoAssinatura);
    }

    @Override
    public Collection findByIdAssinatura(final Integer idAssinatura) throws ServiceException {
        return this.getDao().findByIdAssinatura(idAssinatura);
    }

}
