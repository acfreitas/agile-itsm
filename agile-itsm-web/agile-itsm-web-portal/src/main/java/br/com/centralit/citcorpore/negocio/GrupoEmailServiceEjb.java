package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Collections;

import br.com.centralit.citcorpore.bean.GrupoEmailDTO;
import br.com.centralit.citcorpore.integracao.GrupoEmailDao;
import br.com.citframework.service.CrudServiceImpl;

public class GrupoEmailServiceEjb extends CrudServiceImpl implements GrupoEmailService {

    private GrupoEmailDao dao;

    @Override
    protected GrupoEmailDao getDao() {
        if (dao == null) {
            dao = new GrupoEmailDao();
        }
        return dao;
    }

    @Override
    public Collection<GrupoEmailDTO> findByIdGrupo(final Integer idGrupo) throws Exception {
        return this.getDao().findByIdGrupo(idGrupo);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<GrupoEmailDTO> obterEmailsGrupo(final Integer idGrupoAtual) throws Exception {
        if (idGrupoAtual != null) {
            return this.getDao().obterEmailsGrupo(idGrupoAtual);
        }
        return Collections.EMPTY_LIST;
    }

}
