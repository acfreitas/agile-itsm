package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoDao;
import br.com.citframework.service.CrudServiceImpl;

public class GrupoVisaoServiceEjb extends CrudServiceImpl implements GrupoVisaoService {

    private GrupoVisaoDao dao;

    @Override
    protected GrupoVisaoDao getDao() {
        if (dao == null) {
            dao = new GrupoVisaoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdVisao(final Integer idVisao) throws Exception {
        return this.getDao().findByIdVisao(idVisao);
    }

    @Override
    public Collection findByIdVisaoAtivos(final Integer idVisao) throws Exception {
        return this.getDao().findByIdVisaoAtivos(idVisao);
    }

}
