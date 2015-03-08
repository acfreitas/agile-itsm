package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ExcecaoEmpregadoDTO;
import br.com.centralit.citcorpore.integracao.ExcecaoEmpregadoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ExcecaoEmpregadoServiceEjb extends CrudServiceImpl implements ExcecaoEmpregadoService {

    private ExcecaoEmpregadoDao dao;

    @Override
    protected ExcecaoEmpregadoDao getDao() {
        if (dao == null) {
            dao = new ExcecaoEmpregadoDao();
        }
        return dao;
    }

    @Override
    public Collection<ExcecaoEmpregadoDTO> listByIdEventoIdGrupo(final Integer idEvento, final Integer idGrupo) throws ServiceException {
        try {
            return this.getDao().listByIdEventoIdGrupo(idEvento, idGrupo);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<ExcecaoEmpregadoDTO> listByIdEventoIdUnidade(final Integer idEvento, final Integer idUnidade) throws ServiceException {
        try {
            return this.getDao().listByIdEventoIdUnidade(idEvento, idUnidade);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
