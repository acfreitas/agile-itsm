package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoSituacaoFaturaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoSituacaoFaturaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class PerfilAcessoSituacaoFaturaServiceEjb extends CrudServiceImpl implements PerfilAcessoSituacaoFaturaService {

    private PerfilAcessoSituacaoFaturaDao dao;

    @Override
    protected PerfilAcessoSituacaoFaturaDao getDao() {
        if (dao == null) {
            dao = new PerfilAcessoSituacaoFaturaDao();
        }
        return dao;
    }

    @Override
    public Collection getSituacoesFaturaPermitidasByUsuario(final UsuarioDTO usuario) throws Exception {
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
            final PerfilAcessoSituacaoFaturaDTO perfilAcessoSituacaoFaturaDTO = (PerfilAcessoSituacaoFaturaDTO) it.next();
            if (perfilAcessoSituacaoFaturaDTO.getDataFim() == null || perfilAcessoSituacaoFaturaDTO.getDataFim().after(UtilDatas.getDataAtual())) {
                colFinal.add(Integer.parseInt(perfilAcessoSituacaoFaturaDTO.getSituacaoFatura()));
            }
        }
        return colFinal;
    }

    @Override
    public Collection getSituacoesFaturaPermitidasByGrupo(final PerfilAcessoGrupoDTO perfilAcessoGrupoDTO) throws Exception {
        final Integer idPerfilAcesso = perfilAcessoGrupoDTO.getIdPerfilAcessoGrupo();
        if (idPerfilAcesso == null) {
            return null;
        }
        final Collection colSituacoesPerfil = this.getDao().findByIdPerfil(idPerfilAcesso);
        if (colSituacoesPerfil == null) {
            return null;
        }
        final Collection<Integer> colFinal = new ArrayList<>();
        for (final Iterator it = colSituacoesPerfil.iterator(); it.hasNext();) {
            final PerfilAcessoSituacaoFaturaDTO perfilAcessoSituacaoFaturaDTO = (PerfilAcessoSituacaoFaturaDTO) it.next();
            if (perfilAcessoSituacaoFaturaDTO.getDataFim() == null || perfilAcessoSituacaoFaturaDTO.getDataFim().after(UtilDatas.getDataAtual())) {
                colFinal.add(Integer.parseInt(perfilAcessoSituacaoFaturaDTO.getSituacaoFatura()));
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
    public Collection findBySituacaoFatura(final String parm) throws Exception {
        try {
            return this.getDao().findBySituacaoFatura(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBySituacaoFatura(final String parm) throws Exception {
        try {
            this.getDao().deleteBySituacaoFatura(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
