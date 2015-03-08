package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO;
import br.com.centralit.citcorpore.integracao.CriterioAvaliacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class CriterioAvaliacaoServiceEjb extends CrudServiceImpl implements CriterioAvaliacaoService {

    private CriterioAvaliacaoDao dao;

    @Override
    protected CriterioAvaliacaoDao getDao() {
        if (dao == null) {
            dao = new CriterioAvaliacaoDao();
        }
        return dao;
    }

    @Override
    public Collection findByAplicavelCotacao() throws Exception {
        try {
            return this.getDao().findByAplicavelCotacao();
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByAplicavelAvaliacaoSolicitante() throws Exception {
        try {
            return this.getDao().findByAplicavelAvaliacaoSolicitante();
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByAplicavelAvaliacaoComprador() throws Exception {
        try {
            return this.getDao().findByAplicavelAvaliacaoComprador();
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean verificarSeCriterioExiste(final CriterioAvaliacaoDTO criterioAvaliacaoDto) throws Exception {
        try {
            return this.getDao().verificarSeCriterioExiste(criterioAvaliacaoDto);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
