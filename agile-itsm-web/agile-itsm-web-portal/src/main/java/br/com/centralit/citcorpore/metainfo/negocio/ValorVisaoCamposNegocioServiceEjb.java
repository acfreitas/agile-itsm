package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.ValorVisaoCamposNegocioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ValorVisaoCamposNegocioServiceEjb extends CrudServiceImpl implements ValorVisaoCamposNegocioService {

    private ValorVisaoCamposNegocioDao dao;

    @Override
    protected ValorVisaoCamposNegocioDao getDao() {
        if (dao == null) {
            dao = new ValorVisaoCamposNegocioDao();
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

    @Override
    public Collection findByIdGrupoVisaoAndIdCampoObjetoNegocio(final Integer idGrupoVisao, final Integer idCamposObjetoNegocio) throws Exception {
        try {
            return this.getDao().findByIdGrupoVisaoAndIdCampoObjetoNegocio(idGrupoVisao, idCamposObjetoNegocio);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
