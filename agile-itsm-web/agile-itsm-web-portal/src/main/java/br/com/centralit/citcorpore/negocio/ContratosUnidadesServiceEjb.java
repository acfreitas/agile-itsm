package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ContratosUnidadesDTO;
import br.com.centralit.citcorpore.integracao.ContratosUnidadesDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ContratosUnidadesServiceEjb extends CrudServiceImpl implements ContratosUnidadesService {

    private ContratosUnidadesDAO dao;

    @Override
    protected ContratosUnidadesDAO getDao() {
        if (dao == null) {
            dao = new ContratosUnidadesDAO();
        }
        return dao;
    }

    @Override
    public Collection<ContratosUnidadesDTO> findByIdUnidade(final Integer idUnidade) throws Exception {
        try {
            return this.getDao().findByIdUnidade(idUnidade);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
