package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ProblemaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.ProblemaItemConfiguracaoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class ProblemaItemConfiguracaoServiceEjb extends CrudServiceImpl implements ProblemaItemConfiguracaoService {

    private ProblemaItemConfiguracaoDAO dao;

    @Override
    protected ProblemaItemConfiguracaoDAO getDao() {
        if (dao == null) {
            dao = new ProblemaItemConfiguracaoDAO();
        }
        return dao;
    }

    @Override
    public Collection findByIdProblemaItemConfiguracao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProblemaItemConfiguracao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdProblemaItemConfiguracao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdProblemaItemConfiguracao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdProblema(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProblema(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdProblema(final Integer parm) throws Exception {
        try {
            dao.deleteByIdProblema(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdItemConfiguracao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdItemConfiguracao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdItemConfiguracao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdItemConfiguracao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProblemaItemConfiguracaoDTO restoreByIdProblema(final Integer idProblema) throws Exception {
        try {
            return this.getDao().restoreByIdProblema(idProblema);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
