package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.RelatorioGruposUsuarioDTO;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class GrupoEmpregadoServiceEjb extends CrudServiceImpl implements GrupoEmpregadoService {

    private GrupoEmpregadoDao dao;

    @Override
    protected GrupoEmpregadoDao getDao() {
        if (dao == null) {
            dao = new GrupoEmpregadoDao();
        }
        return dao;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public Collection<GrupoEmpregadoDTO> findByIdGrupo(final Integer idGrupo) throws Exception {
        return this.getDao().findByIdGrupo(idGrupo);
    }

    @Override
    public Collection<GrupoEmpregadoDTO> findUsariosGrupo() throws Exception {
        return this.getDao().findUsariosGrupo();
    }

    @Override
    public void gerarGridEmpregados(final DocumentHTML document, final Collection<GrupoEmpregadoDTO> grupoEmpregados) throws Exception {

    }

    @Override
    public Collection findByIdEmpregado(final Integer idEmpregado) throws Exception {
        return this.getDao().findByIdEmpregado(idEmpregado);
    }

    @Override
    public void deleteByIdGrupoAndEmpregado(final Integer idGrupo, final Integer idEmpregado) throws Exception {
        this.getDao().deleteByIdGrupoAndEmpregado(idGrupo, idEmpregado);

    }

    @Override
    public Collection<GrupoEmpregadoDTO> findGrupoEmpregadoHelpDeskByIdContrato(final Integer idContrato) {
        try {
            return this.getDao().findGrupoEmpregadoHelpDeskByIdContrato(idContrato);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<GrupoEmpregadoDTO> findGrupoAndEmpregadoByIdGrupo(final Integer idGrupo) throws Exception {
        return this.getDao().findGrupoAndEmpregadoByIdGrupo(idGrupo);
    }

    @Override
    public Collection<RelatorioGruposUsuarioDTO> listaRelatorioGruposUsuario(final Integer idColaborador) throws Exception {
        try {
            return this.getDao().listaRelatorioGruposUsuario(idColaborador);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection findByIdEmpregadoNome(final Integer idEmpregado) throws Exception {
        try {
            return this.getDao().findByIdEmpregadoNome(idEmpregado);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer calculaTotalPaginas(final Integer itensPorPagina, final Integer idGrupo) throws Exception {
        try {
            return this.getDao().calculaTotalPaginas(itensPorPagina, idGrupo);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<GrupoEmpregadoDTO> paginacaoGrupoEmpregado(final Integer idGrupo, final Integer pgAtual, final Integer qtdPaginacao) throws Exception {
        try {
            return this.getDao().paginacaoGrupoEmpregado(idGrupo, pgAtual, qtdPaginacao);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean grupoempregado(final Integer idEmpregado, final Integer idGrupo) throws Exception {
        return this.getDao().grupoempregado(idEmpregado, idGrupo);
    }

    @Override
    public Collection<GrupoEmpregadoDTO> findEmpregado(final Integer idGrupo, final Integer idEmpregado) throws Exception {
        return this.getDao().findEmpregado(idGrupo, idEmpregado);
    }

    @Override
    public void deleteTodosEmpregados(final Integer idGrupo) throws Exception {
        this.getDao().deleteByIdGrupo(idGrupo);
    }

    @Override
    public Collection<GrupoEmpregadoDTO> verificacaoResponsavelPorSolicitacao(final Integer idGrupo, final Integer idEmpregado) {
        try {
            return this.getDao().verificacaoResponsavelPorSolicitacao(idGrupo, idEmpregado);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
