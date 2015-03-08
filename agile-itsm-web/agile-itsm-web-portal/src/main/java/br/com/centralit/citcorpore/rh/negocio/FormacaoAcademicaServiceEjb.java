package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.FormacaoAcademicaDao;
import br.com.citframework.service.CrudServiceImpl;

public class FormacaoAcademicaServiceEjb extends CrudServiceImpl implements FormacaoAcademicaService {

    private FormacaoAcademicaDao dao;

    @Override
    protected FormacaoAcademicaDao getDao() {
        if (dao == null) {
            dao = new FormacaoAcademicaDao();
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
