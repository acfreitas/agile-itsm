package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoCamposNegocioLigacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class GrupoVisaoCamposNegocioLigacaoServiceEjb extends CrudServiceImpl implements GrupoVisaoCamposNegocioLigacaoService {

    private GrupoVisaoCamposNegocioLigacaoDao dao;

    @Override
    protected GrupoVisaoCamposNegocioLigacaoDao getDao() {
        if (dao == null) {
            dao = new GrupoVisaoCamposNegocioLigacaoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdGrupoVisaoAndIdCamposObjetoNegocio(final Integer idGrpVisao, final Integer idCamposObjetoNegocio) throws Exception {
        try {
            return this.getDao().findByIdGrupoVisaoAndIdCamposObjetoNegocio(idGrpVisao, idCamposObjetoNegocio);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
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
    public Collection findByIdCamposObjetoNegocioLigacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCamposObjetoNegocioLigacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCamposObjetoNegocioLigacao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCamposObjetoNegocioLigacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
