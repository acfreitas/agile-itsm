package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.MovimentacaoPessoalDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class MovimentacaoPessoalServiceEjb extends CrudServiceImpl implements MovimentacaoPessoalService {

    private MovimentacaoPessoalDao dao;

    @Override
    protected MovimentacaoPessoalDao getDao() {
        if (dao == null) {
            dao = new MovimentacaoPessoalDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdEmpregado(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdEmpregado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdEmpregado(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdEmpregado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
