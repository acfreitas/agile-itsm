package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.InspecaoEntregaItemDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class InspecaoEntregaItemServiceEjb extends CrudServiceImpl implements InspecaoEntregaItemService {

    private InspecaoEntregaItemDao dao;

    @Override
    protected InspecaoEntregaItemDao getDao() {
        if (dao == null) {
            dao = new InspecaoEntregaItemDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdEntrega(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdEntrega(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdEntrega(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdEntrega(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
