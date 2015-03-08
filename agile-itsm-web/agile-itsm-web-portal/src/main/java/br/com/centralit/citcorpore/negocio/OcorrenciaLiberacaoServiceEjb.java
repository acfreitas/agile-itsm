package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.OcorrenciaLiberacaoDao;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author breno.guimaraes
 *
 */
public class OcorrenciaLiberacaoServiceEjb extends CrudServiceImpl implements OcorrenciaLiberacaoService {

    private OcorrenciaLiberacaoDao dao;

    @Override
    protected OcorrenciaLiberacaoDao getDao() {
        if (dao == null) {
            dao = new OcorrenciaLiberacaoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdRequisicaoLiberacao(final Integer idRequisicaoLiberacao) throws Exception {
        return this.getDao().findByIdRequisicaoLiberacao(idRequisicaoLiberacao);
    }

}
