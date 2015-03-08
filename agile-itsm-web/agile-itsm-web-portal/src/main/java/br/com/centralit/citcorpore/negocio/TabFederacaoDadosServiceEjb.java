package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.TabFederacaoDadosDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class TabFederacaoDadosServiceEjb extends CrudServiceImpl implements TabFederacaoDadosService {

    private TabFederacaoDadosDao dao;

    @Override
    protected TabFederacaoDadosDao getDao() {
        if (dao == null) {
            dao = new TabFederacaoDadosDao();
        }
        return dao;
    }

    @Override
    public Collection findByNomeTabela(final String parm) throws Exception {
        try {
            return this.getDao().findByNomeTabela(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByNomeTabela(final String parm) throws Exception {
        try {
            this.getDao().deleteByNomeTabela(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByChaveFinal(final String parm) throws Exception {
        try {
            return this.getDao().findByChaveFinal(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByChaveFinal(final String parm) throws Exception {
        try {
            this.getDao().deleteByChaveFinal(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByChaveOriginal(final String parm) throws Exception {
        try {
            return this.getDao().findByChaveOriginal(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByChaveOriginal(final String parm) throws Exception {
        try {
            this.getDao().deleteByChaveOriginal(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByOrigem(final String parm) throws Exception {
        try {
            return this.getDao().findByOrigem(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByOrigem(final String parm) throws Exception {
        try {
            this.getDao().deleteByOrigem(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
