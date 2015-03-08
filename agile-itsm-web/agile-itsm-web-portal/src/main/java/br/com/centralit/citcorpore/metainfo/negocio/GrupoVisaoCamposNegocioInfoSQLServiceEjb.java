package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoCamposNegocioInfoSQLDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class GrupoVisaoCamposNegocioInfoSQLServiceEjb extends CrudServiceImpl implements GrupoVisaoCamposNegocioInfoSQLService {

    private GrupoVisaoCamposNegocioInfoSQLDao dao;

    @Override
    protected GrupoVisaoCamposNegocioInfoSQLDao getDao() {
        if (dao == null) {
            dao = new GrupoVisaoCamposNegocioInfoSQLDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdGrupoVisao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdGrupoVisao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdGrupoVisao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdGrupoVisao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdCamposObjetoNegocio(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCamposObjetoNegocio(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCamposObjetoNegocio(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCamposObjetoNegocio(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
