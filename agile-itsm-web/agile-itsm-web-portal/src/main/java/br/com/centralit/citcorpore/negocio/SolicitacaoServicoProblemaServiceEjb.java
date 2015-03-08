package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoProblemaDTO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoProblemaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class SolicitacaoServicoProblemaServiceEjb extends CrudServiceImpl implements SolicitacaoServicoProblemaService {

    private SolicitacaoServicoProblemaDao dao;

    @Override
    protected SolicitacaoServicoProblemaDao getDao() {
        if (dao == null) {
            dao = new SolicitacaoServicoProblemaDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdProblema(final Integer parm) throws Exception {
        return this.getDao().findByIdProblema(parm);
    }

    @Override
    public SolicitacaoServicoProblemaDTO restoreByIdProblema(final Integer idProblema) throws Exception {
        try {
            return this.getDao().restoreByIdProblema(idProblema);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
