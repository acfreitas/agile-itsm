package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.JustificativaAcaoCurriculoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author ygor.magalhaes
 *
 */
public class JustificativaAcaoCurriculoServiceEjb extends CrudServiceImpl implements JustificativaAcaoCurriculoService {

    private JustificativaAcaoCurriculoDao dao;

    @Override
    protected JustificativaAcaoCurriculoDao getDao() {
        if (dao == null) {
            dao = new JustificativaAcaoCurriculoDao();
        }
        return dao;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

}
