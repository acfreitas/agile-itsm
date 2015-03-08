package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ProcessoNivelAutoridadeDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ProcessoNivelAutoridadeServiceEjb extends CrudServiceImpl implements ProcessoNivelAutoridadeService {

    private ProcessoNivelAutoridadeDao dao;

    @Override
    protected ProcessoNivelAutoridadeDao getDao() {
        if (dao == null) {
            dao = new ProcessoNivelAutoridadeDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdProcessoNegocio(final Integer parm) throws ServiceException, LogicException {
        try {
            return this.getDao().findByIdProcessoNegocio(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdProcessoNegocio(final Integer parm) throws ServiceException, LogicException {
        try {
            this.getDao().deleteByIdProcessoNegocio(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
