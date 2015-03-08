package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.GlosaOSDTO;
import br.com.centralit.citcorpore.integracao.GlosaOSDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class GlosaOSServiceEjb extends CrudServiceImpl implements GlosaOSService {

    private GlosaOSDao dao;

    @Override
    protected GlosaOSDao getDao() {
        if (dao == null) {
            dao = new GlosaOSDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdOs(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdOs(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdOs(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdOs(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Double retornarCustoGlosaOSByIdOs(final Integer idOs) throws Exception {
        try {
            return this.getDao().retornarCustoGlosaOSByIdOs(idOs);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Collection<GlosaOSDTO> listaDeGlosas(final Integer idOs) throws Exception {
        try {
            return this.getDao().listaDeGlosas(idOs);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
