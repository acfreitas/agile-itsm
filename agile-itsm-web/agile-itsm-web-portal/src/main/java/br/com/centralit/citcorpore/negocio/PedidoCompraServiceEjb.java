package br.com.centralit.citcorpore.negocio;
import java.util.Collection;
import java.util.HashMap;

import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.CotacaoItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO;
import br.com.centralit.citcorpore.bean.EntregaItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.InspecaoEntregaItemDTO;
import br.com.centralit.citcorpore.bean.InspecaoPedidoCompraDTO;
import br.com.centralit.citcorpore.bean.ItemPedidoCompraDTO;
import br.com.centralit.citcorpore.bean.PedidoCompraDTO;
import br.com.centralit.citcorpore.integracao.ColetaPrecoDao;
import br.com.centralit.citcorpore.integracao.CotacaoItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.CriterioAvaliacaoDao;
import br.com.centralit.citcorpore.integracao.EntregaItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.InspecaoEntregaItemDao;
import br.com.centralit.citcorpore.integracao.InspecaoPedidoCompraDao;
import br.com.centralit.citcorpore.integracao.ItemPedidoCompraDao;
import br.com.centralit.citcorpore.integracao.PedidoCompraDao;
import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoEntregaItemRequisicao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoPedidoCompra;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class PedidoCompraServiceEjb extends CrudServiceImpl implements PedidoCompraService {

	private PedidoCompraDao dao;

 	protected PedidoCompraDao getDao() {
		if (dao == null) {
			dao = new PedidoCompraDao();
		}
		return dao;
	}

	protected void validaCreate(Object arg0) throws Exception {
	    validaPedido((PedidoCompraDTO) arg0);
	}

	private void validaPedido(PedidoCompraDTO pedidoCompraDto) throws Exception {
        if (pedidoCompraDto.getDataPedido() == null)
            throw new LogicException("Data do pedido não informada");

        if (pedidoCompraDto.getColItens() == null || pedidoCompraDto.getColItens().size() == 0)
	        throw new LogicException("Não foi adicionado nenhum item ao pedido");

	    if (pedidoCompraDto.getDataPrevistaEntrega() != null && pedidoCompraDto.getDataPedido().compareTo(pedidoCompraDto.getDataPrevistaEntrega()) > 0)
            throw new LogicException("Data prevista da entrega deve ser maior ou igual à data do pedido");

        if (pedidoCompraDto.getDataEntrega() != null && pedidoCompraDto.getDataPedido().compareTo(pedidoCompraDto.getDataEntrega()) > 0)
            throw new LogicException("Data da entrega deve ser maior ou igual à data do pedido");

        if (pedidoCompraDto.getDataEntrega() != null && pedidoCompraDto.getDataEntrega().compareTo(UtilDatas.getDataAtual()) > 0)
            throw new LogicException("Data da entrega não pode ser maior que a data atual");

        ColetaPrecoDao coletaPrecoDao = new ColetaPrecoDao();

	    HashMap<String, ColetaPrecoDTO> mapColetas = new HashMap();
	    for (ItemPedidoCompraDTO item : pedidoCompraDto.getColItens()) {
	        ColetaPrecoDTO coletaPrecoDto = mapColetas.get(""+item.getIdColetaPreco());
	        if (coletaPrecoDto == null) {
	            coletaPrecoDto = new ColetaPrecoDTO();
	            coletaPrecoDto.setIdColetaPreco(item.getIdColetaPreco());
	            coletaPrecoDto = (ColetaPrecoDTO) coletaPrecoDao.restore(coletaPrecoDto);
	            mapColetas.put(""+item.getIdColetaPreco(), coletaPrecoDto);
	        }
	        double qtdePedido = 0;
	        if (coletaPrecoDto.getQuantidadePedido() != null)
	            qtdePedido = coletaPrecoDto.getQuantidadePedido();
	        coletaPrecoDto.setQuantidadePedido(qtdePedido + item.getQuantidade().doubleValue());
        }
        for (ColetaPrecoDTO coletaPrecoDto: mapColetas.values()) {
            if (coletaPrecoDto.getQuantidadePedido().doubleValue() > coletaPrecoDto.getQuantidadeAprovada().doubleValue())
                throw new LogicException("Quantidade pedida é maior que a quantidade aprovada na coleta de preço número "+coletaPrecoDto.getIdColetaPreco());
        }

        if (pedidoCompraDto.getSituacao().equals(SituacaoPedidoCompra.Entregue.name())) {
            if (pedidoCompraDto.getDataEntrega() == null)
                throw new LogicException("Data de entrega não informada");
        }else{
            pedidoCompraDto.setDataEntrega(null);
        }

    }

    protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}

	protected void validaUpdate(Object arg0) throws Exception {
	    validaPedido((PedidoCompraDTO) arg0);
	}

	public Collection findByIdCotacao(Integer parm) throws Exception{
		PedidoCompraDao dao = new PedidoCompraDao();
		try{
			return dao.findByIdCotacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
    public Collection findEntreguesByIdCotacao(Integer parm) throws Exception{
        PedidoCompraDao dao = new PedidoCompraDao();
        try{
            return dao.findEntreguesByIdCotacao(parm);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
	public void deleteByIdCotacao(Integer parm) throws Exception{
		PedidoCompraDao dao = new PedidoCompraDao();
		try{
			dao.deleteByIdCotacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

    private void atualizaAnexos(PedidoCompraDTO pedidoCompraDto, TransactionControler tc) throws Exception {
        new ControleGEDServiceBean().atualizaAnexos(pedidoCompraDto.getAnexos(), ControleGEDDTO.TABELA_PEDIDOCOMPRA, pedidoCompraDto.getIdPedido(), tc);
    }

    private void excluiItens(PedidoCompraDTO pedidoCompraDto, TransactionControler tc) throws Exception {
        ItemPedidoCompraDao itemPedidoCompraDao = new ItemPedidoCompraDao();
        itemPedidoCompraDao.setTransactionControler(tc);
        Collection<ItemPedidoCompraDTO> colItens = itemPedidoCompraDao.findByIdPedido(pedidoCompraDto.getIdPedido());
        if (colItens != null) {
            CotacaoItemRequisicaoDao cotacaoItemRequisicaoDao = new CotacaoItemRequisicaoDao();
            cotacaoItemRequisicaoDao.setTransactionControler(tc);

            ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
            for (ItemPedidoCompraDTO itemDto : colItens) {
                String complemento = "Exclusão do pedido ";
                if (pedidoCompraDto.getNumeroPedido() != null)
                    complemento += " No. " + pedidoCompraDto.getNumeroPedido();
                complemento += ", Cotação No. "+pedidoCompraDto.getIdCotacao();
                Collection<CotacaoItemRequisicaoDTO> colItensRequisicao = cotacaoItemRequisicaoDao.findByIdColetaPreco(itemDto.getIdColetaPreco());
                if (colItensRequisicao != null) {
                    for (CotacaoItemRequisicaoDTO cotacaoItemRequisicaoDto : colItensRequisicao) {
                        itemRequisicaoService.alteraSituacao(pedidoCompraDto.getUsuarioDto(), cotacaoItemRequisicaoDto.getIdItemRequisicaoProduto(), AcaoItemRequisicaoProduto.ExclusaoPedido, SituacaoItemRequisicaoProduto.AguardandoPedido, complemento, tc);
                    }
                }
                itemPedidoCompraDao.delete(itemDto);
            }
        }
    }

    private void criaItens(PedidoCompraDTO pedidoCompraDto, TransactionControler tc) throws Exception {
        Collection<ItemPedidoCompraDTO> colItens = pedidoCompraDto.getColItens();
        if (colItens != null) {
            CotacaoItemRequisicaoDao cotacaoItemRequisicaoDao = new CotacaoItemRequisicaoDao();
            cotacaoItemRequisicaoDao.setTransactionControler(tc);

            ItemPedidoCompraDao itemPedidoCompraDao = new ItemPedidoCompraDao();
            itemPedidoCompraDao.setTransactionControler(tc);

            String complemento = "Cotação No. "+pedidoCompraDto.getIdCotacao();
            if (pedidoCompraDto.getNumeroPedido() != null)
                complemento += ", Pedido No. " + pedidoCompraDto.getNumeroPedido();
            ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
            for (ItemPedidoCompraDTO itemDto : colItens) {
                itemDto.setIdPedido(pedidoCompraDto.getIdPedido());
                itemPedidoCompraDao.create(itemDto);

                Collection<CotacaoItemRequisicaoDTO> colItensRequisicao = cotacaoItemRequisicaoDao.findByIdColetaPreco(itemDto.getIdColetaPreco());
                if (colItensRequisicao != null) {
                    for (CotacaoItemRequisicaoDTO cotacaoItemRequisicaoDto : colItensRequisicao) {
                        itemRequisicaoService.alteraSituacao(pedidoCompraDto.getUsuarioDto(), cotacaoItemRequisicaoDto.getIdItemRequisicaoProduto(), AcaoItemRequisicaoProduto.Pedido, SituacaoItemRequisicaoProduto.AguardandoEntrega, complemento, tc);
                    }
                }
            }
        }
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        PedidoCompraDao pedidoCompraDao = new PedidoCompraDao();
        TransactionControler tc = new TransactionControlerImpl(pedidoCompraDao.getAliasDB());

        try{
            validaCreate(model);

            pedidoCompraDao.setTransactionControler(tc);

            tc.start();

            PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) model;
            pedidoCompraDto = (PedidoCompraDTO) pedidoCompraDao.create(pedidoCompraDto);

            criaItens(pedidoCompraDto,tc);

            atualizaAnexos(pedidoCompraDto, tc);
            if (pedidoCompraDto.getSituacao().equals(SituacaoPedidoCompra.Entregue.name())) {
                criaEntregaItemRequisicao(pedidoCompraDto, tc);
                new CotacaoServiceEjb().alteraSituacao(pedidoCompraDto.getUsuarioDto(), pedidoCompraDto.getIdCotacao(), SituacaoCotacao.Entrega, tc);
            }else{
                new CotacaoServiceEjb().alteraSituacao(pedidoCompraDto.getUsuarioDto(), pedidoCompraDto.getIdCotacao(), SituacaoCotacao.Pedido, tc);
            }

            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    @Override
    public void update(IDto model) throws ServiceException, LogicException {
        PedidoCompraDao pedidoCompraDao = new PedidoCompraDao();
        TransactionControler tc = new TransactionControlerImpl(pedidoCompraDao.getAliasDB());

        try{
            validaUpdate(model);

            pedidoCompraDao.setTransactionControler(tc);

            tc.start();

            PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) model;
            pedidoCompraDao.update(pedidoCompraDto);

            excluiItens(pedidoCompraDto, tc);
            criaItens(pedidoCompraDto,tc);

            atualizaAnexos(pedidoCompraDto, tc);
            if (pedidoCompraDto.getSituacao().equals(SituacaoPedidoCompra.Entregue.name())) {
                criaEntregaItemRequisicao(pedidoCompraDto, tc);
                new CotacaoServiceEjb().alteraSituacao(pedidoCompraDto.getUsuarioDto(), pedidoCompraDto.getIdCotacao(), SituacaoCotacao.Entrega, tc);
            }

            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
    }

    private void criaEntregaItemRequisicao(PedidoCompraDTO pedidoCompraDto, TransactionControler tc) throws Exception {
        EntregaItemRequisicaoDao entregaItemDao = new EntregaItemRequisicaoDao();
        entregaItemDao.setTransactionControler(tc);
        entregaItemDao.deleteByIdPedido(pedidoCompraDto.getIdPedido());

        Collection<ItemPedidoCompraDTO> colItens = pedidoCompraDto.getColItens();
        if (colItens != null) {
            Collection<CriterioAvaliacaoDTO> colCriterios = new CriterioAvaliacaoDao().findByAplicavelAvaliacaoSolicitante();

            CotacaoItemRequisicaoDao cotacaoItemDao = new CotacaoItemRequisicaoDao();
            cotacaoItemDao.setTransactionControler(tc);
            for (ItemPedidoCompraDTO itemDto : colItens) {
                InspecaoEntregaItemDao inspecaoEntregaDao = new InspecaoEntregaItemDao();
                inspecaoEntregaDao.setTransactionControler(tc);
                Collection<CotacaoItemRequisicaoDTO> colCotacaoItem = cotacaoItemDao.findByIdColetaPrecoOrderQtde(itemDto.getIdColetaPreco());
                if (colCotacaoItem != null) {
                    ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
                    double qtdePedido = itemDto.getQuantidade();
                    for (CotacaoItemRequisicaoDTO cotacaoItemDto : colCotacaoItem) {
                        double qtde = cotacaoItemDto.getQuantidade().doubleValue() - cotacaoItemDto.getQuantidadeEntregue().doubleValue();
                        if (qtde > qtdePedido)
                            qtde = qtdePedido;
                        qtdePedido = qtdePedido - qtde;
                        cotacaoItemDto.setQuantidadeEntregue(cotacaoItemDto.getQuantidadeEntregue().doubleValue() + qtde);
                        cotacaoItemDao.update(cotacaoItemDto);

                        EntregaItemRequisicaoDTO entregaDto = new EntregaItemRequisicaoDTO();
                        entregaDto.setIdColetaPreco(cotacaoItemDto.getIdColetaPreco());
                        entregaDto.setIdItemRequisicaoProduto(cotacaoItemDto.getIdItemRequisicaoProduto());
                        entregaDto.setIdPedido(pedidoCompraDto.getIdPedido());
                        entregaDto.setIdSolicitacaoServico(cotacaoItemDto.getIdSolicitacaoServico());
                        entregaDto.setQuantidadeEntregue(qtde);
                        entregaDto.setSituacao(SituacaoEntregaItemRequisicao.Aguarda.name());
                        entregaDto = (EntregaItemRequisicaoDTO) entregaItemDao.create(entregaDto);

                        String complemento = "Cotação No. "+pedidoCompraDto.getIdCotacao();
                        if (pedidoCompraDto.getNumeroPedido() != null)
                            complemento += ", Pedido No. " + pedidoCompraDto.getNumeroPedido();

                        itemRequisicaoService.alteraSituacao(pedidoCompraDto.getUsuarioDto(), cotacaoItemDto.getIdItemRequisicaoProduto(), AcaoItemRequisicaoProduto.Entrega, SituacaoItemRequisicaoProduto.AguardandoInspecao, complemento, tc);

                        if (colCriterios != null) {
                            for (CriterioAvaliacaoDTO criterioDto : colCriterios) {
                                InspecaoEntregaItemDTO inspecaoDto = new InspecaoEntregaItemDTO();
                                inspecaoDto.setIdEntrega(entregaDto.getIdEntrega());
                                inspecaoDto.setIdCriterio(criterioDto.getIdCriterio());
                                inspecaoEntregaDao.create(inspecaoDto);
                            }
                        }

                        if (qtdePedido <= 0)
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void delete(IDto model) throws ServiceException, LogicException {
        PedidoCompraDao pedidoCompraDao = new PedidoCompraDao();
        TransactionControler tc = new TransactionControlerImpl(pedidoCompraDao.getAliasDB());

        try{
            pedidoCompraDao.setTransactionControler(tc);

            tc.start();

            PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) model;

            excluiItens(pedidoCompraDto, tc);
            pedidoCompraDao.delete(pedidoCompraDto);

            Collection colPedidos = pedidoCompraDao.findByIdCotacao(pedidoCompraDto.getIdCotacao());
            if (colPedidos == null || colPedidos.isEmpty()) {
                new CotacaoServiceEjb().alteraSituacao(pedidoCompraDto.getUsuarioDto(), pedidoCompraDto.getIdCotacao(), SituacaoCotacao.Publicada, tc);
            }

            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void atualizaEntrega(PedidoCompraDTO pedidoCompraDto) throws Exception {
        PedidoCompraDao pedidoCompraDao = new PedidoCompraDao();
        PedidoCompraDTO pedidoAuxDto = (PedidoCompraDTO) pedidoCompraDao.restore(pedidoCompraDto);
        if (pedidoAuxDto.getSituacao().equals(SituacaoPedidoCompra.Entregue.name()))
            throw new Exception("Pedido já foi entregue");

        Collection<InspecaoPedidoCompraDTO> colInspecao = pedidoCompraDto.getColInspecao();
        if (colInspecao == null)
            throw new Exception("Avaliação do pedido nao informada");

        InspecaoPedidoCompraDao inspecaoPedidoDao = new InspecaoPedidoCompraDao();
        ItemPedidoCompraDao itemPedidoDao = new ItemPedidoCompraDao();

        TransactionControler tc = null;
        try{

            if (pedidoCompraDto.getDataEntrega() == null)
                throw new LogicException("Data de entrega não informada");

            if (pedidoAuxDto.getDataPedido().compareTo(pedidoCompraDto.getDataEntrega()) > 0)
                throw new LogicException("Data da entrega deve ser maior ou igual à data do pedido");

            if (pedidoCompraDto.getDataEntrega().compareTo(UtilDatas.getDataAtual()) > 0)
                throw new LogicException("Data da entrega não pode ser maior que a data atual");

            tc = new TransactionControlerImpl(pedidoCompraDao.getAliasDB());

            pedidoCompraDao.setTransactionControler(tc);
            inspecaoPedidoDao.setTransactionControler(tc);
            itemPedidoDao.setTransactionControler(tc);

            tc.start();

            pedidoAuxDto.setSituacao(SituacaoPedidoCompra.Entregue.name());
            pedidoAuxDto.setDataEntrega(pedidoCompraDto.getDataEntrega());
            pedidoAuxDto.setIdentificacaoEntrega(pedidoCompraDto.getIdentificacaoEntrega());
            pedidoAuxDto.setObservacoes(pedidoCompraDto.getObservacoes());
            pedidoCompraDao.update(pedidoAuxDto);

            inspecaoPedidoDao.deleteByIdPedido(pedidoCompraDto.getIdPedido());
            for (InspecaoPedidoCompraDTO inspecaoDto : colInspecao) {
                if (inspecaoDto.getAvaliacao() == null || inspecaoDto.getAvaliacao().trim().length() == 0)
                    throw new Exception("Avaliação não informada");
                inspecaoDto.setIdResponsavel(pedidoCompraDto.getUsuarioDto().getIdEmpregado());
                inspecaoDto.setDataHoraInspecao(UtilDatas.getDataHoraAtual());
                inspecaoDto.setIdPedido(pedidoCompraDto.getIdPedido());
                inspecaoPedidoDao.create(inspecaoDto);
            }

            pedidoAuxDto.setUsuarioDto(pedidoCompraDto.getUsuarioDto());
            pedidoAuxDto.setColItens(itemPedidoDao.findByIdPedido(pedidoAuxDto.getIdPedido()));
            criaEntregaItemRequisicao(pedidoAuxDto, tc);
            new CotacaoServiceEjb().alteraSituacao(pedidoCompraDto.getUsuarioDto(), pedidoCompraDto.getIdCotacao(), SituacaoCotacao.Entrega, tc);

            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
    }

}
