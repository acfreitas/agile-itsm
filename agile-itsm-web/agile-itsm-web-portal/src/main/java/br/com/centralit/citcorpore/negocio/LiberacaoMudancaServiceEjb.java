package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LiberacaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.LiberacaoMudancaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class LiberacaoMudancaServiceEjb extends CrudServiceImpl implements LiberacaoMudancaService {

    private LiberacaoMudancaDao dao;

    @Override
    protected LiberacaoMudancaDao getDao() {
        if (dao == null) {
            dao = new LiberacaoMudancaDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdLiberacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdLiberacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdLiberacao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdLiberacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Lista por idrequisicaoliberacao, no caso ele listará apenas os que o status não for nulo.
     * 
     * @author geber.costa
     * @param idrequisicaoliberacao
     * @return
     * @throws ServiceException
     * @throws Exception
     *
     */
    @Override
    public Collection<LiberacaoMudancaDTO> listAll() throws ServiceException, Exception {
        try {
            return this.getDao().listAll();
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdRequisicaoMudanca(final Integer idLiberacao, final Integer idRequisicaoMudanca) throws Exception {
        try {
            return this.getDao().findByIdRequisicaoMudanca(idLiberacao, idRequisicaoMudanca);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
