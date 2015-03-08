package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.InstalacaoDao;
import br.com.citframework.service.CrudServiceImpl;

public class InstalacaoServiceEjb extends CrudServiceImpl implements InstalacaoService {

    private InstalacaoDao dao;

    @Override
    protected InstalacaoDao getDao() {
        if (dao == null) {
            dao = new InstalacaoDao();
        }
        return dao;
    }

    @Override
    public boolean isSucessoInstalacao() throws Exception {
        return this.getDao().isSucessoInstalacao();
    }

}
