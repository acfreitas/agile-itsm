package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.RegiaoDTO;
import br.com.centralit.citcorpore.integracao.RegiaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RegiaoServiceEjb extends CrudServiceImpl implements RegiaoService {

    private RegiaoDao dao;

    @Override
    protected RegiaoDao getDao() {
        if (dao == null) {
            dao = new RegiaoDao();
        }
        return dao;
    }

    @Override
    public RegiaoDTO listByIdRegiao(final RegiaoDTO obj) throws Exception {
        try {
            return this.getDao().listByIdRegiao(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
