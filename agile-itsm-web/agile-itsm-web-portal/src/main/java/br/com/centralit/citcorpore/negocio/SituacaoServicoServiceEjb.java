package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.SituacaoServicoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;

public class SituacaoServicoServiceEjb extends CrudServiceImpl implements SituacaoServicoService {

    private SituacaoServicoDao dao;

    @Override
    protected SituacaoServicoDao getDao() {
        if (dao == null) {
            dao = new SituacaoServicoDao();
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
    public boolean jaExisteSituacaoComMesmoNome(final String nome) {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("nomeSituacaoServico", "=", nome));
        condicoes.add(new Condition("dataFim", "is", null));
        Collection retorno = null;
        try {
            retorno = this.getDao().findByCondition(condicoes, null);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return retorno == null ? false : true;
    }

    @Override
    public boolean existeServicoAssociado(final Integer idSituacaoServico) {
        try {
            return this.getDao().existeServicoAssociado(idSituacaoServico);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
