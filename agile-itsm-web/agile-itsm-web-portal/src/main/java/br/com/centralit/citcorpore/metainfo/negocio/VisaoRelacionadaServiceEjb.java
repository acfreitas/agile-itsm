package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.VisaoRelacionadaDao;
import br.com.citframework.service.CrudServiceImpl;

public class VisaoRelacionadaServiceEjb extends CrudServiceImpl implements VisaoRelacionadaService {

    private VisaoRelacionadaDao dao;

    @Override
    protected VisaoRelacionadaDao getDao() {
        if (dao == null) {
            dao = new VisaoRelacionadaDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdVisaoPaiAtivos(final Integer idVisaoPai) throws Exception {
        return this.getDao().findByIdVisaoPaiAtivos(idVisaoPai);
    }

}
