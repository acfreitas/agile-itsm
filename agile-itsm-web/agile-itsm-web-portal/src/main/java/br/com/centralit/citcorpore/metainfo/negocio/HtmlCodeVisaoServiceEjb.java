package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.HtmlCodeVisaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class HtmlCodeVisaoServiceEjb extends CrudServiceImpl implements HtmlCodeVisaoService {

    private HtmlCodeVisaoDao htmlCodeVisaoDao;

    @Override
    protected HtmlCodeVisaoDao getDao() {
        if (htmlCodeVisaoDao == null) {
            htmlCodeVisaoDao = new HtmlCodeVisaoDao();
        }
        return htmlCodeVisaoDao;
    }

    @Override
    public Collection findByIdVisao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdVisao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdVisao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdVisao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
