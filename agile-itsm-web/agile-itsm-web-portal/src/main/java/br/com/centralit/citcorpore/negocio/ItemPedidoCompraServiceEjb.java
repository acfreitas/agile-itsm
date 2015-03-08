package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemPedidoCompraDTO;
import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.bean.UnidadeMedidaDTO;
import br.com.centralit.citcorpore.integracao.ItemPedidoCompraDao;
import br.com.centralit.citcorpore.integracao.ProdutoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ItemPedidoCompraServiceEjb extends CrudServiceImpl implements ItemPedidoCompraService {

    private ItemPedidoCompraDao dao;

    @Override
    protected ItemPedidoCompraDao getDao() {
        if (dao == null) {
            dao = new ItemPedidoCompraDao();
        }
        return dao;
    }

    public void recuperaRelacionamentos(final Collection<ItemPedidoCompraDTO> colItens) throws Exception {
        if (colItens != null) {
            final ProdutoDao produtoDao = new ProdutoDao();
            for (final ItemPedidoCompraDTO itemDto : colItens) {
                new UnidadeMedidaDTO();
                if (itemDto.getIdProduto() != null) {
                    ProdutoDTO produtoDto = new ProdutoDTO();
                    produtoDto.setIdProduto(itemDto.getIdProduto());
                    produtoDto = (ProdutoDTO) produtoDao.restore(produtoDto);
                    if (produtoDto != null) {
                        itemDto.setCodigoProduto(produtoDto.getCodigoProduto());
                        itemDto.setDescricaoItem(produtoDto.getNomeProduto());
                    }
                }
            }
        }
    }

    @Override
    public Collection findByIdPedido(final Integer parm) throws Exception {
        try {
            final Collection<ItemPedidoCompraDTO> colItens = this.getDao().findByIdPedido(parm);
            this.recuperaRelacionamentos(colItens);
            return colItens;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdPedidoOrderByIdSolicitacao(final Integer idPedido) throws Exception {
        try {
            final Collection<ItemPedidoCompraDTO> colItens = this.getDao().findByIdPedidoOrderByIdSolicitacao(idPedido);
            this.recuperaRelacionamentos(colItens);
            return colItens;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdPedido(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdPedido(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public double obtemQtdePedidoColetaPreco(final Integer idColetaPreco) throws Exception {
        double qtde = 0;
        try {
            final Collection<ItemPedidoCompraDTO> colItens = this.getDao().findByIdColetaPreco(idColetaPreco);
            if (colItens != null) {
                for (final ItemPedidoCompraDTO itemPedidoDto : colItens) {
                    qtde += itemPedidoDto.getQuantidade().doubleValue();
                }
            }
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        return qtde;
    }

}
