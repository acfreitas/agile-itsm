package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoCamposNegocioDao;
import br.com.citframework.service.CrudServiceImpl;

public class GrupoVisaoCamposNegocioServiceEjb extends CrudServiceImpl implements GrupoVisaoCamposNegocioService {

    private GrupoVisaoCamposNegocioDao dao;

    @Override
    protected GrupoVisaoCamposNegocioDao getDao() {
        if (dao == null) {
            dao = new GrupoVisaoCamposNegocioDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdGrupoVisao(final Integer idGrupoVisao) throws Exception {
        return this.getDao().findByIdGrupoVisao(idGrupoVisao);
    }

    @Override
    public Collection findByIdGrupoVisaoAtivos(final Integer idGrupoVisao) throws Exception {
        return this.getDao().findByIdGrupoVisaoAtivos(idGrupoVisao);
    }

}
