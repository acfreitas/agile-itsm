package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.CertificacaoDao;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class CertificacaoServiceEjb extends CrudServiceImpl implements CertificacaoService {

    private CertificacaoDao dao;

    @Override
    protected CertificacaoDao getDao() {
        if (dao == null) {
            dao = new CertificacaoDao();
        }
        return dao;
    }

    @Override
    public Collection findByNome(final String nome) throws Exception {
        return this.getDao().findByNome(nome);
    }

    @Override
    public Collection findByNotIdFuncao(final Integer idFuncao) throws Exception {
        new CertificacaoDao();
        return this.getDao().findByNotIdFuncao(idFuncao);
    }

}
