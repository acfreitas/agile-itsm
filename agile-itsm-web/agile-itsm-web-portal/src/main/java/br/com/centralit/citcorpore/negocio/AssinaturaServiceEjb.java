package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.AssinaturaDTO;
import br.com.centralit.citcorpore.integracao.AssinaturaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author euler.ramos
 *
 */
public class AssinaturaServiceEjb extends CrudServiceImpl implements AssinaturaService {

    private AssinaturaDAO dao;

    @Override
    protected AssinaturaDAO getDao() {
        if (dao == null) {
            dao = new AssinaturaDAO();
        }
        return dao;
    }

    @Override
    public boolean violaIndiceUnico(final AssinaturaDTO assinaturaDTO) throws ServiceException {
        return this.getDao().violaIndiceUnico(assinaturaDTO);
    }

}
