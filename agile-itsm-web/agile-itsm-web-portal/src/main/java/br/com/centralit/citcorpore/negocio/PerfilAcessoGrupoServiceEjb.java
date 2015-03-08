package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.integracao.PerfilAcessoGrupoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class PerfilAcessoGrupoServiceEjb extends CrudServiceImpl implements PerfilAcessoGrupoService {

    private PerfilAcessoGrupoDao dao;

    @Override
    protected PerfilAcessoGrupoDao getDao() {
        if (dao == null) {
            dao = new PerfilAcessoGrupoDao();
        }
        return dao;
    }

    @Override
    public PerfilAcessoGrupoDTO listByIdGrupo(final PerfilAcessoGrupoDTO obj) throws Exception {
        try {
            return this.getDao().listByIdGrupo(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean existeGrupoVinculadoPerfil(final Integer idPerfilAcesso) throws Exception {
        try {
            return this.getDao().existeGrupoVinculadoPerfil(idPerfilAcesso);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
