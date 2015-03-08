package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ParametroDTO;
import br.com.centralit.citcorpore.integracao.ParametroDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ParametroServiceEjb extends CrudServiceImpl implements ParametroService {

    private ParametroDao dao;

    @Override
    protected ParametroDao getDao() {
        if (dao == null) {
            dao = new ParametroDao();
        }
        return dao;
    }

    @Override
    public ParametroDTO getValue(final String moduloParm, final String nomeParametroParm, final Integer idEmpresaParm) throws ServiceException, LogicException {
        try {
            return this.getDao().getValue(moduloParm, nomeParametroParm, idEmpresaParm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setValue(final String moduloParm, final String nomeParametroParm, final Integer idEmpresaParm, final String valorParm, final String detalhamentoParm)
            throws ServiceException, LogicException {
        try {
            this.getDao().setValue(moduloParm, nomeParametroParm, idEmpresaParm, valorParm, detalhamentoParm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
