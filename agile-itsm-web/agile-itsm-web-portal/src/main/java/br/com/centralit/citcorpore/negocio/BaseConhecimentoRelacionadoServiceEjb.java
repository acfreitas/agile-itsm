/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.BaseConhecimentoRelacionadoDTO;
import br.com.centralit.citcorpore.integracao.BaseConhecimentoRelacionadoDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author Vadoilo Damasceno
 *
 */
public class BaseConhecimentoRelacionadoServiceEjb extends CrudServiceImpl implements BaseConhecimentoRelacionadoService {

    private BaseConhecimentoRelacionadoDAO dao;

    @Override
    protected BaseConhecimentoRelacionadoDAO getDao() {
        if (dao == null) {
            dao = new BaseConhecimentoRelacionadoDAO();
        }
        return dao;
    }

    @Override
    public void deleteByIdConhecimento(final Integer idBaseConhecimento, final TransactionControler transactionControler) throws Exception {
        this.getDao().setTransactionControler(transactionControler);
        this.getDao().deleteByIdConhecimento(idBaseConhecimento);
    }

    @Override
    public void create(final BaseConhecimentoRelacionadoDTO baseConhecimentoRelacionado, final TransactionControler transactionControler) throws Exception {
        this.getDao().setTransactionControler(transactionControler);
        this.getDao().create(baseConhecimentoRelacionado);
    }

    @Override
    public Collection<BaseConhecimentoRelacionadoDTO> listByIdBaseConhecimento(final Integer idBaseConhecimento) throws Exception {
        return this.getDao().listByIdBaseConhecimento(idBaseConhecimento);
    }

    @Override
    public Collection<BaseConhecimentoRelacionadoDTO> listByIdBaseConhecimentoRelacionado(final Integer idBaseConhecimento) throws Exception {
        return this.getDao().listByIdBaseConhecimentoRelacionado(idBaseConhecimento);
    }

}
