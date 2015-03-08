package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.ConhecimentoDao;
import br.com.citframework.service.CrudServiceImpl;

public class ConhecimentoServiceEjb extends CrudServiceImpl implements ConhecimentoService {

    private ConhecimentoDao dao;

    @Override
    protected ConhecimentoDao getDao() {
        if (dao == null) {
            dao = new ConhecimentoDao();
        }
        return dao;
    }

    @Override
    public Collection findByNome(final String nome) throws Exception {
        return this.getDao().findByNome(nome);
    }

    @Override
    public Collection findByNotIdFuncao(final Integer idFuncao) throws Exception {
        return this.getDao().findByNotIdFuncao(idFuncao);
    }

}
