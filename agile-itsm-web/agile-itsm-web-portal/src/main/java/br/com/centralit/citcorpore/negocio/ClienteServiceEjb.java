package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.integracao.ClienteDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ClienteServiceEjb extends CrudServiceImpl implements ClienteService {

    private ClienteDao dao;

    @Override
    protected ClienteDao getDao() {
        if (dao == null) {
            dao = new ClienteDao();
        }
        return dao;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public boolean existeDuplicacao(final HashMap mapFields) throws LogicException, ServiceException {
        final String nomeRazaoSocial = (String) mapFields.get("NOMERAZAOSOCIAL");
        boolean retorno = false;
        try {
            retorno = this.getDao().existeDuplicacao(nomeRazaoSocial);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    /**
     * Verifica se existe cliente vinculado à algum contrato.
     *
     * @param mapFields
     * @return
     * @throws LogicException
     * @throws ServiceException
     * @author mario.haysaki
     *
     */
    public boolean clienteVinculadoContrato(final HashMap mapFields) throws LogicException, ServiceException {
        boolean retorno = false;
        if (mapFields.get("IDCLIENTE") != null && !((String) mapFields.get("IDCLIENTE")).isEmpty()) {
            final Integer idCliente = Integer.parseInt((String) mapFields.get("IDCLIENTE"));
            try {
                retorno = this.getDao().clienteVinculadoContrato(idCliente);
            } catch (final Exception e) {
                throw new ServiceException(e);
            }
        }
        return retorno;
    }

}
