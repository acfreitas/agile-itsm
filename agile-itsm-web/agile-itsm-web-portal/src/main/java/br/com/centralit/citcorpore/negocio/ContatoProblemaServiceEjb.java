package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ContatoProblemaDTO;
import br.com.centralit.citcorpore.integracao.ContatoProblemaDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author geber.costa
 */
public class ContatoProblemaServiceEjb extends CrudServiceImpl implements ContatoProblemaService {

    private ContatoProblemaDAO dao;

    @Override
    protected ContatoProblemaDAO getDao() {
        if (dao == null) {
            dao = new ContatoProblemaDAO();
        }
        return dao;
    }

    @Override
    public synchronized IDto create(final IDto model) throws ServiceException, LogicException {
        return super.create(model);
    }

    @Override
    public ContatoProblemaDTO restoreContatosById(final Integer idContatoProblema) throws Exception {
        return this.getDao().restoreById(idContatoProblema);
    }

    @Override
    public ContatoProblemaDTO restoreContatosById(final ContatoProblemaDTO obj) throws Exception {
        return this.getDao().restoreById(obj);
    }

}
