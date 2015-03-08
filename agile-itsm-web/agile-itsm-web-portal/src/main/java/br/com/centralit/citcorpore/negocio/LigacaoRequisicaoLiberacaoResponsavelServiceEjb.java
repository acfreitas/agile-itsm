package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoLiberacaoResponsavelDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class LigacaoRequisicaoLiberacaoResponsavelServiceEjb extends CrudServiceImpl implements LigacaoRequisicaoLiberacaoMidiaService {

    private LigacaoRequisicaoLiberacaoResponsavelDao dao;

    @Override
    protected LigacaoRequisicaoLiberacaoResponsavelDao getDao() {
        if (dao == null) {
            dao = new LigacaoRequisicaoLiberacaoResponsavelDao();
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

}
