package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.integracao.ProdutoDao;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class ProdutoServiceEjb extends CrudServiceImpl implements ProdutoService {

    private ProdutoDao dao;

    @Override
    protected ProdutoDao getDao() {
        if (dao == null) {
            dao = new ProdutoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdTipoProduto(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdTipoProduto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdTipoProduto(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdTipoProduto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void recuperaImagem(final ProdutoDTO especificacaoProdutoDto) throws Exception {
        especificacaoProdutoDto.setImagem(null);
        final List<ControleGEDDTO> colGed = (List<ControleGEDDTO>) new ControleGEDDao().listByIdTabelaAndID(ControleGEDDTO.TABELA_PRODUTO, especificacaoProdutoDto.getIdProduto());
        if (colGed != null && !colGed.isEmpty()) {
            especificacaoProdutoDto.setImagem(new ControleGEDServiceBean().getRelativePathFromGed(colGed.get(0)));
        }
    }

    @Override
    public Collection findByIdCategoria(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCategoria(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdCategoriaAndAceitaRequisicao(final Integer idCategoria, final String aceitaRequisicao) throws Exception {
        try {
            return this.getDao().findByIdCategoriaAndAceitaRequisicao(idCategoria, aceitaRequisicao);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection validaNovoProduto(final ProdutoDTO produtoDto) throws Exception {
        try {
            return this.getDao().validaNovoProduto(produtoDto);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        final ProdutoDTO produtoDto = (ProdutoDTO) super.restore(model);
        if (produtoDto != null) {
            produtoDto.montaIdentificacao(); // só pra setar a identificação
        }
        return produtoDto;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final ProdutoDao produtoDao = new ProdutoDao();
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaCreate(model);

            produtoDao.setTransactionControler(tc);

            tc.start();

            ProdutoDTO produtoDto = (ProdutoDTO) model;
            produtoDto = (ProdutoDTO) produtoDao.create(produtoDto);

            if (produtoDto.getCodigoProduto() == null || produtoDto.getCodigoProduto().trim().length() == 0) {
                produtoDto.setCodigoProduto("" + produtoDto.getIdProduto());
                produtoDao.update(produtoDto);
            }
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

}
