package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleRendimentoUsuarioDTO;
import br.com.centralit.citcorpore.integracao.ControleRendimentoUsuarioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ControleRendimentoUsuarioServiceEjb extends CrudServiceImpl implements ControleRendimentoUsuarioService {

    private ControleRendimentoUsuarioDao dao;

    @Override
    protected ControleRendimentoUsuarioDao getDao() {
        if (dao == null) {
            dao = new ControleRendimentoUsuarioDao();
        }
        return dao;
    }

    @Override
    public Collection<ControleRendimentoUsuarioDTO> findByIdControleRendimentoUsuario(final Integer idGrupo, final String mes, final String ano) throws ServiceException {
        try {
            return this.getDao().findByIdControleRendimentoUsuario(idGrupo, mes, ano);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<ControleRendimentoUsuarioDTO> findByIdControleRendimentoMelhoresUsuario(final Integer idGrupo, final String mesInicio, final String mesFim,
            final String anoInicio, final String anoFim, final Boolean deUmAnoParaOOutro) throws ServiceException {
        try {
            return this.getDao().findByIdControleRendimentoMelhoresUsuario(idGrupo, mesInicio, mesFim, anoInicio, anoFim, deUmAnoParaOOutro);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<ControleRendimentoUsuarioDTO> findIdsControleRendimentoUsuarioPorPeriodo(final Integer idGrupo, final String mesInicio, final String mesFim,
            final String anoInicio, final String anoFim, final Boolean deUmAnoParaOOutro) throws ServiceException {
        try {
            return this.getDao().findIdsControleRendimentoUsuarioPorPeriodo(idGrupo, mesInicio, mesFim, anoInicio, anoFim, deUmAnoParaOOutro);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
