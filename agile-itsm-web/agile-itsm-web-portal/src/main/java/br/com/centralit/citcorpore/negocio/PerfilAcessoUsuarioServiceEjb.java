package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.PerfilAcessoUsuarioDAO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class PerfilAcessoUsuarioServiceEjb extends CrudServiceImpl implements PerfilAcessoUsuarioService {

    private PerfilAcessoUsuarioDAO dao;

    @Override
    protected PerfilAcessoUsuarioDAO getDao() {
        if (dao == null) {
            dao = new PerfilAcessoUsuarioDAO();
        }
        return dao;
    }

    @Override
    public void reativaPerfisUsuario(final Integer idUsuario) {
        try {
            this.getDao().reativaPerfisUsuario(idUsuario);
        } catch (final PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        final PerfilAcessoUsuarioDTO dto = (PerfilAcessoUsuarioDTO) arg0;
        if (dto.getDataInicio() == null) {
            dto.setDataInicio(UtilDatas.getDataAtual());
        }
    }

    @Override
    public PerfilAcessoUsuarioDTO listByIdUsuario(final PerfilAcessoUsuarioDTO obj) throws Exception {
        try {
            return this.getDao().listByIdUsuario(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PerfilAcessoUsuarioDTO obterPerfilAcessoUsuario(final UsuarioDTO usuario) throws Exception {
        return this.getDao().obterPerfilAcessoUsuario(usuario);
    }

    @Override
    public Collection obterPerfisAcessoUsuario(final UsuarioDTO usuario) throws Exception {
        try {
            return this.getDao().listPerfilByIdUsuario(usuario);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
