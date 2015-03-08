package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.AtividadeFluxoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class AtividadeFluxoServiceEjb extends CrudServiceImpl implements AtividadeFluxoService {

    private AtividadeFluxoDao dao;

    @Override
    protected AtividadeFluxoDao getDao() {
        if (dao == null) {
            dao = new AtividadeFluxoDao();
        }
        return dao;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

}
