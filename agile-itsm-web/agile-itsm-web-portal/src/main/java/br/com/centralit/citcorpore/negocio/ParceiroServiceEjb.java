package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ParceiroDTO;
import br.com.centralit.citcorpore.integracao.ParceiroDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author david.silva
 *
 */
public class ParceiroServiceEjb extends CrudServiceImpl implements ParceiroService {

    private ParceiroDAO dao;

    @Override
    protected ParceiroDAO getDao() {
        if (dao == null) {
            dao = new ParceiroDAO();
        }
        return dao;
    }

    @Override
    public Collection<ParceiroDTO> consultarFornecedorPorRazaoSocialAutoComplete(final String razaoSocial) throws Exception {
        return this.getDao().consultarFornecedorPorRazaoSocialAutoComplete(razaoSocial);
    }

}
