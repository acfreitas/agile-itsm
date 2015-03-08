package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.VinculoVisaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class VinculoVisaoServiceEjb extends CrudServiceImpl implements VinculoVisaoService {

    private VinculoVisaoDao dao;

    @Override
    protected VinculoVisaoDao getDao() {
        if (dao == null) {
            dao = new VinculoVisaoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdVisaoRelacionada(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdVisaoRelacionada(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdGrupoVisaoPai(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdGrupoVisaoPai(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdGrupoVisaoPai(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdGrupoVisaoPai(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdCamposObjetoNegocioPai(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCamposObjetoNegocioPai(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCamposObjetoNegocioPai(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCamposObjetoNegocioPai(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdGrupoVisaoFilho(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdGrupoVisaoFilho(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdGrupoVisaoFilho(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdGrupoVisaoFilho(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdCamposObjetoNegocioFilho(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCamposObjetoNegocioFilho(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCamposObjetoNegocioFilho(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCamposObjetoNegocioFilho(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdCamposObjetoNegocioPaiNN(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCamposObjetoNegocioPaiNN(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCamposObjetoNegocioPaiNN(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCamposObjetoNegocioPaiNN(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdCamposObjetoNegocioFilhoNN(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCamposObjetoNegocioFilhoNN(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCamposObjetoNegocioFilhoNN(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCamposObjetoNegocioFilhoNN(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
