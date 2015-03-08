package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.AtitudeIndividualDao;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class AtitudeIndividualServiceEjb extends CrudServiceImpl implements AtitudeIndividualService {

    private AtitudeIndividualDao dao;

    @Override
    protected AtitudeIndividualDao getDao() {
        if (dao == null) {
            dao = new AtitudeIndividualDao();
        }
        return dao;
    }

    @Override
    public Collection findByNome(final String nome) throws Exception {
        return this.getDao().findByNome(nome);
    }

    @Override
    public Collection findByIdSolicitacaoServico(final Integer idSolicitacaoServico) throws Exception {
        return this.getDao().findByIdSolicitacaoServico(idSolicitacaoServico);
    }

}
