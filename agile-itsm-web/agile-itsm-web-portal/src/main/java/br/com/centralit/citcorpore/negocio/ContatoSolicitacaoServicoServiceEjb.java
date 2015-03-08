package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ContatoSolicitacaoServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ContatoSolicitacaoServicoServiceEjb extends CrudServiceImpl implements ContatoSolicitacaoServicoService {

    private ContatoSolicitacaoServicoDao dao;

    @Override
    protected ContatoSolicitacaoServicoDao getDao() {
        if (dao == null) {
            dao = new ContatoSolicitacaoServicoDao();
        }
        return dao;
    }

    @Override
    public synchronized IDto create(final IDto model) throws ServiceException, LogicException {
        return super.create(model);
    }

}
