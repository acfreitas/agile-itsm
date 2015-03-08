package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.CertificacaoCurriculoDao;
import br.com.citframework.service.CrudServiceImpl;

public class CertificacaoCurriculoServiceEjb extends CrudServiceImpl implements CertificacaoCurriculoService {

    private CertificacaoCurriculoDao dao;

    @Override
    protected CertificacaoCurriculoDao getDao() {
        if (dao == null) {
            dao = new CertificacaoCurriculoDao();
        }
        return dao;
    }

    @Override
    public Collection findByNome(final String nome) throws Exception {
        return this.getDao().findByNome(nome);
    }

}
