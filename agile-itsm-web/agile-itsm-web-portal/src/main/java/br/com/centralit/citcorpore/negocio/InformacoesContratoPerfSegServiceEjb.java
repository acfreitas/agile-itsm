package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.InformacoesContratoPerfSegDao;
import br.com.citframework.service.CrudServiceImpl;

public class InformacoesContratoPerfSegServiceEjb extends CrudServiceImpl implements InformacoesContratoPerfSegService {

    private InformacoesContratoPerfSegDao dao;

    @Override
    protected InformacoesContratoPerfSegDao getDao() {
        if (dao == null) {
            dao = new InformacoesContratoPerfSegDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdProntuarioEletronicoConfig(final Integer idProntuarioEletronicoConfig) throws Exception {
        return this.getDao().findByIdInformacoesContratoConfig(idProntuarioEletronicoConfig);
    }

}
