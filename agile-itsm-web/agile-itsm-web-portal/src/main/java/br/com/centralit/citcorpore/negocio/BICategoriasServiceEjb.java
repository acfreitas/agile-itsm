package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.BICategoriasDao;
import br.com.citframework.service.CrudServiceImpl;

public class BICategoriasServiceEjb extends CrudServiceImpl implements BICategoriasService {

    private BICategoriasDao dao;

    @Override
    protected BICategoriasDao getDao() {
        if (dao == null) {
            dao = new BICategoriasDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdCategoriaPai(final Integer parm) throws Exception {
        return this.getDao().findByIdCategoriaPai(parm);
    }

    @Override
    public Collection findSemPai() throws Exception {
        return this.getDao().findSemPai();
    }

}
