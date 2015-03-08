package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.SolicitacaoServicoEvtMonDao;
import br.com.citframework.service.CrudServiceImpl;

public class SolicitacaoServicoEvtMonServiceEjb extends CrudServiceImpl implements SolicitacaoServicoEvtMonService {

    private SolicitacaoServicoEvtMonDao dao;

    @Override
    protected SolicitacaoServicoEvtMonDao getDao() {
        if (dao == null) {
            dao = new SolicitacaoServicoEvtMonDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdRecursoAndSolicitacaoAberta(final Integer idRecurso) throws Exception {
        return this.getDao().findByIdRecursoAndSolicitacaoAberta(idRecurso);
    }

    @Override
    public Collection findByIdSolicitacao(final Integer idSolicitacaoServico) throws Exception {
        return this.getDao().findByIdSolicitacao(idSolicitacaoServico);
    }

}
