package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.TimeSheetDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class TimeSheetServiceEjb extends CrudServiceImpl implements TimeSheetService {

    private TimeSheetDao dao;

    @Override
    protected TimeSheetDao getDao() {
        if (dao == null) {
            dao = new TimeSheetDao();
        }
        return dao;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public Collection findByPessoaAndPeriodo(final Integer idEmpregado, final Date dataInicio, final Date dataFim) throws LogicException, ServiceException {
        try {
            return this.getDao().findByPessoaAndPeriodo(idEmpregado, dataInicio, dataFim);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByDemanda(final Integer idDemanda) throws LogicException, ServiceException {
        try {
            return this.getDao().findByDemanda(idDemanda);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

}
