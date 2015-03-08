package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoSituacaoOSDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoSituacaoOSDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class PerfilAcessoSituacaoOSServiceEjb extends CrudServiceImpl implements PerfilAcessoSituacaoOSService {

    private PerfilAcessoSituacaoOSDao dao;

    @Override
    protected PerfilAcessoSituacaoOSDao getDao() {
        if (dao == null) {
            dao = new PerfilAcessoSituacaoOSDao();
        }
        return dao;
    }

    @Override
    public Collection getSituacoesOSPermitidasByUsuario(final UsuarioDTO usuario) throws Exception {
        final MenuDao menuDao = new MenuDao();
        final Integer idPerfilAcesso = menuDao.getPerfilAcesso(usuario);
        if (idPerfilAcesso == null) {
            return null;
        }
        final Collection colSituacoesPerfil = this.getDao().findByIdPerfil(idPerfilAcesso);
        if (colSituacoesPerfil == null) {
            return null;
        }
        final Collection<Integer> colFinal = new ArrayList<>();
        for (final Iterator it = colSituacoesPerfil.iterator(); it.hasNext();) {
            final PerfilAcessoSituacaoOSDTO perfilAcessoSituacaoOSDTO = (PerfilAcessoSituacaoOSDTO) it.next();
            if (perfilAcessoSituacaoOSDTO.getDataFim() == null || perfilAcessoSituacaoOSDTO.getDataFim().after(UtilDatas.getDataAtual())) {
                colFinal.add(perfilAcessoSituacaoOSDTO.getSituacaoOs());
            }
        }
        return colFinal;
    }

    @Override
    public Collection getSituacoesOSPermitidasByGrupo(final PerfilAcessoGrupoDTO perfilAcessoGrupoDTO) throws Exception {

        final Integer idPerfilAcesso = perfilAcessoGrupoDTO.getIdPerfilAcessoGrupo();
        if (idPerfilAcesso == null) {
            return null;
        }
        final Collection colSituacoesPerfil = this.getDao().findByIdPerfil(idPerfilAcesso);
        if (colSituacoesPerfil == null) {
            return null;
        }
        final Collection colFinal = new ArrayList();
        for (final Iterator it = colSituacoesPerfil.iterator(); it.hasNext();) {
            final PerfilAcessoSituacaoOSDTO perfilAcessoSituacaoOSDTO = (PerfilAcessoSituacaoOSDTO) it.next();
            if (perfilAcessoSituacaoOSDTO.getDataFim() == null || perfilAcessoSituacaoOSDTO.getDataFim().after(UtilDatas.getDataAtual())) {
                colFinal.add(perfilAcessoSituacaoOSDTO.getSituacaoOs());
            }
        }
        return colFinal;
    }

    @Override
    public Collection findByIdPerfil(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdPerfil(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdPerfil(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdPerfil(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findBySituacaoOs(final Integer parm) throws Exception {
        try {
            return this.getDao().findBySituacaoOs(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBySituacaoOs(final Integer parm) throws Exception {
        try {
            this.getDao().deleteBySituacaoOs(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
