package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.centralit.citcorpore.integracao.ProblemaMudancaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class ProblemaMudancaServiceEjb extends CrudServiceImpl implements ProblemaMudancaService {

    private ProblemaMudancaDAO dao;

    @Override
    protected ProblemaMudancaDAO getDao() {
        if (dao == null) {
            dao = new ProblemaMudancaDAO();
        }
        return dao;
    }

    @Override
    public Collection findByIdProblemaMudanca(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProblemaMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdProblemaMudanca(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdProblemaMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdProblema(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProblema(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdProblema(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdProblema(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdRequisicaoMudanca(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdRequisicaoMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdRequisicaoMudanca(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdRequisicaoMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProblemaMudancaDTO restoreByIdProblema(final Integer idProblema) throws Exception {
        try {
            return this.getDao().restoreByIdProblema(idProblema);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
