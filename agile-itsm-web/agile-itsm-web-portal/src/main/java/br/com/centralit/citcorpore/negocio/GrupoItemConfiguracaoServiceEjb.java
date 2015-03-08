package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.GrupoItemConfiguracaoDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class GrupoItemConfiguracaoServiceEjb extends CrudServiceImpl implements GrupoItemConfiguracaoService {

    private GrupoItemConfiguracaoDAO dao;

    @Override
    protected GrupoItemConfiguracaoDAO getDao() {
        if (dao == null) {
            dao = new GrupoItemConfiguracaoDAO();
        }
        return dao;
    }

    @Override
    public boolean VerificaSeCadastrado(final GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO) throws PersistenceException {
        return this.getDao().VerificaSeCadastrado(grupoItemConfiguracaoDTO);
    }

    @Override
    public Collection<GrupoItemConfiguracaoDTO> listByEvento(final Integer idEvento) throws ServiceException {
        try {
            return this.getDao().listByEvento(idEvento);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateNotNull(final IDto dto) {
        try {
            this.validaUpdate(dto);
            this.getDao().updateNotNull(dto);
        } catch (final ServiceException e) {
            e.printStackTrace();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean verificaICRelacionados(final GrupoItemConfiguracaoDTO grupoItemConfiguracao) throws Exception {
        try {
            return this.getDao().verificaICRelacionados(grupoItemConfiguracao);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracaoPai(final Integer idGrupoItemConfiguracaoPai) throws Exception {
        try {
            return this.getDao().listByIdGrupoItemConfiguracaoPai(idGrupoItemConfiguracaoPai);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracaoDesenvolvimento(final Integer idGrupoItemConfiguracaoPai) throws Exception {
        try {
            return this.getDao().listByIdGrupoItemConfiguracaoDesenvolvimento(idGrupoItemConfiguracaoPai);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracao(final Integer idGrupoItemConfiguracaoPai) throws Exception {
        try {
            return this.getDao().listByIdGrupoItemConfiguracao(idGrupoItemConfiguracaoPai);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listHierarquiaGruposByIdGrupo(final Integer idGrupo, GrupoItemConfiguracaoDAO dao) throws Exception {
        try {
            if (dao == null) {
                dao = this.getDao();
            }
            final Collection col = dao.listByIdGrupoItemConfiguracaoPai(idGrupo);
            if (col != null && col.size() > 0) {
                Iterator it = col.iterator();
                final Collection colTratada = new ArrayList();
                for (; it.hasNext();) {
                    final GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) it.next();
                    colTratada.add(grupoItemConfiguracaoDTO);
                }
                it = colTratada.iterator();
                for (; it.hasNext();) {
                    final GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) it.next();
                    final Collection col2 = this.listHierarquiaGruposByIdGrupo(grupoItemConfiguracaoDTO.getIdGrupoItemConfiguracao(), dao);
                    if (col2 != null && col2.size() > 0) {
                        col.addAll(col2);
                    }
                }
            }
            return col;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listHierarquiaGrupoPaiNull() throws Exception {
        try {
            final Collection col = this.getDao().listByIdGrupoItemConfiguracaoPaiNull();
            if (col != null && col.size() > 0) {
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) it.next();
                    final Collection col2 = this.listHierarquiaGruposByIdGrupo(grupoItemConfiguracaoDTO.getIdGrupoItemConfiguracao(), this.getDao());
                    if (col2 != null && col2.size() > 0) {
                        col.addAll(col2);
                    }
                }
            }
            return col;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void autenticaGrupoPadrao(final int id, final int idPai, final String nome) throws Exception {
        final GrupoItemConfiguracaoDTO grupoIC = new GrupoItemConfiguracaoDTO();
        grupoIC.setIdGrupoItemConfiguracao(id);
        grupoIC.setNomeGrupoItemConfiguracao(nome);
        grupoIC.setIdGrupoItemConfiguracaoPai(idPai);
        grupoIC.setDataInicio(UtilDatas.getDataAtual());

        if (this.getDao().verificaSeExiste(grupoIC)) {
            final GrupoItemConfiguracaoDTO grupoTemp = (GrupoItemConfiguracaoDTO) this.restore(grupoIC);
            if (grupoTemp != null) {
                if (grupoTemp.getDataFim() == null) {
                    this.getDao().updateNotNull(grupoIC);
                }
            }
        } else {
            try {
                // Grupo 'wolverine'
                this.createWithID(grupoIC);
            } catch (final Exception e) {

            }
        }
    }

    public IDto createWithID(final IDto obj) throws Exception {
        return this.getDao().createWithID(obj);
    }

}
