package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.PrioridadeAcordoNivelServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class PrioridadeAcordoNivelServicoServiceEjb extends CrudServiceImpl implements PrioridadeAcordoNivelServicoService {

    private PrioridadeAcordoNivelServicoDao dao;

    @Override
    protected PrioridadeAcordoNivelServicoDao getDao() {
        if (dao == null) {
            dao = new PrioridadeAcordoNivelServicoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdUnidade(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdUnidade(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdUnidade(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdUnidade(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdAcordoNivelServico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdAcordoNivelServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdAcordoNivelServico(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdAcordoNivelServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
