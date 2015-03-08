package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.FornecedorCotacaoDTO;
import br.com.centralit.citcorpore.integracao.ColetaPrecoDao;
import br.com.centralit.citcorpore.integracao.CotacaoDao;
import br.com.centralit.citcorpore.integracao.FornecedorCotacaoDao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class FornecedorCotacaoServiceEjb extends CrudServiceImpl implements FornecedorCotacaoService {

    private FornecedorCotacaoDao dao;

    @Override
    protected FornecedorCotacaoDao getDao() {
        if (dao == null) {
            dao = new FornecedorCotacaoDao();
        }
        return dao;
    }

    @Override
    protected void validaDelete(final Object arg0) throws Exception {
        final FornecedorCotacaoDTO fornecedorCotacaoDto = (FornecedorCotacaoDTO) this.restore((FornecedorCotacaoDTO) arg0);
        CotacaoDTO cotacaoDto = new CotacaoDTO();
        cotacaoDto.setIdCotacao(fornecedorCotacaoDto.getIdCotacao());
        cotacaoDto = (CotacaoDTO) new CotacaoDao().restore(cotacaoDto);
        if (!cotacaoDto.getSituacao().equals(SituacaoCotacao.EmAndamento.name())) {
            throw new LogicException("A situação da cotação não permite a exclusão do fornecedor.");
        }

        final Collection colColetas = new ColetaPrecoDao().findByIdCotacaoAndIdFornecedor(fornecedorCotacaoDto.getIdCotacao(), fornecedorCotacaoDto.getIdFornecedor());
        if (colColetas != null && !colColetas.isEmpty()) {
            throw new LogicException("Exclusão não permitida. Existe pelo menos uma coleta de preços associada ao fornecedor.");
        }
    }

    @Override
    public Collection findByIdCotacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCotacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCotacao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCotacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdFornecedor(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdFornecedor(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(final IDto model) throws ServiceException, LogicException {
        FornecedorCotacaoDTO fornecedorCotacaoDto = (FornecedorCotacaoDTO) model;
        fornecedorCotacaoDto = (FornecedorCotacaoDTO) this.restore(fornecedorCotacaoDto);
        final FornecedorCotacaoDao fornecedorCotacaoDao = new FornecedorCotacaoDao();
        final TransactionControler tc = new TransactionControlerImpl(fornecedorCotacaoDao.getAliasDB());

        try {
            this.validaDelete(model);

            fornecedorCotacaoDao.setTransactionControler(tc);

            tc.start();

            fornecedorCotacaoDao.delete(fornecedorCotacaoDto);

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

}
