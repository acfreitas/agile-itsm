package br.com.centralit.citcorpore.negocio;

import java.sql.Date;

import br.com.centralit.citcorpore.integracao.FeriadoDao;
import br.com.citframework.service.CrudServiceImpl;

public class FeriadoServiceBean extends CrudServiceImpl implements FeriadoService {

    private FeriadoDao dao;

    @Override
    protected FeriadoDao getDao() {
        if (dao == null) {
            dao = new FeriadoDao();
        }
        return dao;
    }

    @Override
    public boolean isFeriado(final Date data, final Integer idCidade, final Integer idUf) throws Exception {
        return this.getDao().isFeriado(data, idCidade, idUf);
    }

}
