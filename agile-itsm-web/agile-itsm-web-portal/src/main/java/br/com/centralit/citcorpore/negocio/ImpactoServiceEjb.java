/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ImpactoDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author valdoilo.damasceno
 * @since 04.06.2014
 */
public class ImpactoServiceEjb extends CrudServiceImpl implements ImpactoService {

    private ImpactoDAO dao;

    @Override
    protected ImpactoDAO getDao() {
        if (dao == null) {
            dao = new ImpactoDAO();
        }
        return dao;
    }

}
