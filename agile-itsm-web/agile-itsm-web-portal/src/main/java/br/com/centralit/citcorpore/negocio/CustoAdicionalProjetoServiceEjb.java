package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.CustoAdicionalProjetoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class CustoAdicionalProjetoServiceEjb extends CrudServiceImpl implements CustoAdicionalProjetoService {

    private CustoAdicionalProjetoDao dao;

    @Override
    protected CustoAdicionalProjetoDao getDao() {
        if (dao == null) {
            dao = new CustoAdicionalProjetoDao();
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
