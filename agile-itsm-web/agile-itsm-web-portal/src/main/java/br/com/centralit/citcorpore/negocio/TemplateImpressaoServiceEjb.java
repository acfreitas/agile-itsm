package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.TemplateImpressaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class TemplateImpressaoServiceEjb extends CrudServiceImpl implements TemplateImpressaoService {

    private TemplateImpressaoDao dao;

    @Override
    protected TemplateImpressaoDao getDao() {
        if (dao == null) {
            dao = new TemplateImpressaoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdTipoTemplateImp(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdTipoTemplateImp(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdTipoTemplateImp(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdTipoTemplateImp(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
