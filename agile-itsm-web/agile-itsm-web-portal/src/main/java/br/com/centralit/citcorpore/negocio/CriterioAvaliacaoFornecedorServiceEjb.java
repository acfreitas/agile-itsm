package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CriterioAvaliacaoFornecedorDTO;
import br.com.centralit.citcorpore.integracao.CriterioAvaliacaoFornecedorDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class CriterioAvaliacaoFornecedorServiceEjb extends CrudServiceImpl implements CriterioAvaliacaoFornecedorService {

    private CriterioAvaliacaoFornecedorDao dao;

    @Override
    protected CriterioAvaliacaoFornecedorDao getDao() {
        if (dao == null) {
            dao = new CriterioAvaliacaoFornecedorDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdCriterio(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCriterio(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCriterio(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCriterio(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<CriterioAvaliacaoFornecedorDTO> listByIdAvaliacaoFornecedor(final Integer idAvaliacaoFornecedor) throws Exception {
        final Collection<CriterioAvaliacaoFornecedorDTO> listCriterioAvaliacaoFornecedor = this.getDao().listByIdAvaliacaoFornecedor(idAvaliacaoFornecedor);
        try {
            if (listCriterioAvaliacaoFornecedor != null) {
                for (final CriterioAvaliacaoFornecedorDTO criterioAvaliacaoFornecedor : listCriterioAvaliacaoFornecedor) {
                    if (criterioAvaliacaoFornecedor.getValorInteger() == 1) {
                        criterioAvaliacaoFornecedor.setValor("Sim");
                    } else {
                        if (criterioAvaliacaoFornecedor.getValorInteger() == 0) {
                            criterioAvaliacaoFornecedor.setValor("Não");
                        } else {
                            criterioAvaliacaoFornecedor.setValor("N/A");
                        }
                    }
                }
            }
            return listCriterioAvaliacaoFornecedor;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdAvaliacaoFornecedor(final Integer idAvaliacaoFornecedor) throws Exception {
        try {
            this.getDao().deleteByIdAvaliacaoFornecedor(idAvaliacaoFornecedor);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }

    }
}
