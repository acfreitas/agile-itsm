package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ConhecimentoProblemaDTO;
import br.com.centralit.citcorpore.integracao.ConhecimentoProblemaDao;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class ConhecimentoProblemaServiceEjb extends CrudServiceImpl implements ConhecimentoProblemaService {

    private ConhecimentoProblemaDao dao;

    @Override
    protected ConhecimentoProblemaDao getDao() {
        if (dao == null) {
            dao = new ConhecimentoProblemaDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdProblema(final Integer parm) throws Exception {
        return this.getDao().findByIdProblema(parm);
    }

    @Override
	public ConhecimentoProblemaDTO getErroConhecidoByProblema(Integer parm) throws Exception {
		return this.getDao().getErroConhecidoByProblema(parm);
    }

}
