package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.integracao.PrioridadeDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author leandro.viana
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class PrioridadeServiceEjb extends CrudServiceImpl implements PrioridadeService {

    private PrioridadeDao dao;

    @Override
    protected PrioridadeDao getDao() {
        if (dao == null) {
            dao = new PrioridadeDao();
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
    public Collection<PrioridadeDTO> prioridadesAtivasPorNome(final String nome) {
        final List condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("nomePrioridade", "=", nome));
        condicoes.add(new Condition("situacao", "!=", "I"));
        try {
            return this.getDao().findByCondition(condicoes, null);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PrioridadeDTO>();
    }

    @Override
    public Collection<PrioridadeDTO> prioridadesAtivas() {
        try {
            final Collection<PrioridadeDTO> result = this.getDao().list();
            return result == null ? new ArrayList<PrioridadeDTO>() : result;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PrioridadeDTO>();
    }

}
