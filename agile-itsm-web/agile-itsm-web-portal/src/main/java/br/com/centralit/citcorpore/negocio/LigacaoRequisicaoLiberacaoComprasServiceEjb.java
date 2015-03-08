package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoComprasDTO;
import br.com.centralit.citcorpore.integracao.LigacaoRequisicaoLiberacaoMidiaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class LigacaoRequisicaoLiberacaoComprasServiceEjb extends CrudServiceImpl implements LigacaoRequisicaoLiberacaoComprasService {

    private LigacaoRequisicaoLiberacaoMidiaDao dao;

    @Override
    protected LigacaoRequisicaoLiberacaoMidiaDao getDao() {
        if (dao == null) {
            dao = new LigacaoRequisicaoLiberacaoMidiaDao();
        }
        return dao;
    }

    @Override
    public Collection<LigacaoRequisicaoLiberacaoHistoricoComprasDTO> findByIdLiberacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdLiberacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
