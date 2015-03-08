package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.BotaoAcaoVisaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class BotaoAcaoVisaoServiceEjb extends CrudServiceImpl implements BotaoAcaoVisaoService {

    private BotaoAcaoVisaoDao dao;

    @Override
    protected BotaoAcaoVisaoDao getDao() {
        if (dao == null) {
            dao = new BotaoAcaoVisaoDao();
        }
        return dao;
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
