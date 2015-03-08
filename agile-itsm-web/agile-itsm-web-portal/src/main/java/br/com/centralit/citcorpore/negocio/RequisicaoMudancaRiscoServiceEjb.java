package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaRiscoDTO;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaRiscoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;

public class RequisicaoMudancaRiscoServiceEjb extends CrudServiceImpl implements RequisicaoMudancaRiscoService {

    private RequisicaoMudancaRiscoDao dao;

    @Override
    protected RequisicaoMudancaRiscoDao getDao() {
        if (dao == null) {
            dao = new RequisicaoMudancaRiscoDao();
        }
        return dao;
    }

    @Override
    public RequisicaoMudancaRiscoDTO restoreByChaveComposta(final RequisicaoMudancaRiscoDTO dto) throws ServiceException, Exception {
        return null;
    }

    @Override
    public ArrayList<RequisicaoMudancaRiscoDTO> listByIdRequisicaoMudanca(final Integer idRequisicaoMudanca) throws ServiceException, Exception {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));
        return (ArrayList<RequisicaoMudancaRiscoDTO>) this.getDao().findByCondition(condicoes, null);
    }

    @Override
    public Collection findByIdRequisicaoMudanca(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdRequisicaoMudancaEDataFim(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
