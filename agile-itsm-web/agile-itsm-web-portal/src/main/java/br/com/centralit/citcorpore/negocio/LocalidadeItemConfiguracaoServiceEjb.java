package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.LocalidadeItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.LocalidadeItemConfiguracaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class LocalidadeItemConfiguracaoServiceEjb extends CrudServiceImpl implements LocalidadeItemConfiguracaoService {

    private LocalidadeItemConfiguracaoDao dao;

    @Override
    protected LocalidadeItemConfiguracaoDao getDao() {
        if (dao == null) {
            dao = new LocalidadeItemConfiguracaoDao();
        }
        return new LocalidadeItemConfiguracaoDao();
    }

    @Override
    public LocalidadeItemConfiguracaoDTO listByIdRegiao(final LocalidadeItemConfiguracaoDTO obj) throws Exception {
        try {
            return this.getDao().listByIdRegiao(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public LocalidadeItemConfiguracaoDTO listByIdUf(final LocalidadeItemConfiguracaoDTO obj) throws Exception {
        try {
            return this.getDao().listByIdUf(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
