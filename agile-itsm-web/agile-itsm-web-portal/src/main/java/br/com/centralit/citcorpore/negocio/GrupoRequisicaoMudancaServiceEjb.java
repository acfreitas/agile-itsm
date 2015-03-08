package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.GrupoRequisicaoMudancaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class GrupoRequisicaoMudancaServiceEjb extends CrudServiceImpl implements GrupoRequisicaoMudancaService {

    private GrupoRequisicaoMudancaDao dao;

    @Override
    protected GrupoRequisicaoMudancaDao getDao() {
        if (dao == null) {
            dao = new GrupoRequisicaoMudancaDao();
        }
        return dao;
    }

    @Override
    public void deleteByIdRequisicaoMudanca(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdRequisicaoMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Collection listByIdHistoricoMudanca(final Integer idHistoricoMudanca) throws Exception {
        try {
            return this.getDao().listByIdHistoricoMudanca(idHistoricoMudanca);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdRequisicaoMudanca(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdRequisicaoMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdGrupo(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdGrupo(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdGrupo(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdGrupo(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdGrupoRequisicaoMudanca(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdGrupoRequisicaoMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Collection findByIdGrupoRequisicaoMudanca(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdGrupoRequisicaoMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdMudancaEDataFim(final Integer idRequisicaoMudanca) throws Exception {
        try {
            return this.getDao().findByIdMudancaEDataFim(idRequisicaoMudanca);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
