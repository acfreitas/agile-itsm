package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ControleContratoDTO;
import br.com.centralit.citcorpore.integracao.ControleContratoOcorrenciaDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author Pedro
 *
 */
@SuppressWarnings("rawtypes")
public class ControleContratoOcorrenciaServiceEjb extends CrudServiceImpl implements ControleContratoOcorrenciaService {

    private ControleContratoOcorrenciaDao dao;

    @Override
    protected ControleContratoOcorrenciaDao getDao() {
        if (dao == null) {
            dao = new ControleContratoOcorrenciaDao();
        }
        return dao;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public void deleteByIdControleContrato(final ControleContratoDTO controleContrato) throws Exception {
        this.getDao().deleteByIdControleContrato(controleContrato);
    }

}
