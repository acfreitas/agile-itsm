package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ValorAjusteGlosaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author rodrigo.oliveira
 *
 */
public class ValorAjusteGlosaServiceEjb extends CrudServiceImpl implements ValorAjusteGlosaService {

    private ValorAjusteGlosaDAO dao;

    @Override
    protected ValorAjusteGlosaDAO getDao() {
        if (dao == null) {
            dao = new ValorAjusteGlosaDAO();
        }
        return dao;
    }

    @Override
    public Collection findByIdServicoContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdServicoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
