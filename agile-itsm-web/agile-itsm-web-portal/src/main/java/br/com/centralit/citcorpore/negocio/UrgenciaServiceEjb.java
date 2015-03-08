package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.UrgenciaDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author valdoilo.damasceno
 *
 */
public class UrgenciaServiceEjb extends CrudServiceImpl implements UrgenciaService {

    private UrgenciaDAO dao;

    @Override
    protected UrgenciaDAO getDao() {
        if (dao == null) {
            dao = new UrgenciaDAO();
        }
        return dao;
    }

}
