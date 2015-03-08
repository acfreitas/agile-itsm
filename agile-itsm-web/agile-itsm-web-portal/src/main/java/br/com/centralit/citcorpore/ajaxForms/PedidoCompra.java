package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.ItemPedidoCompraDTO;
import br.com.centralit.citcorpore.bean.PedidoCompraDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ColetaPrecoService;
import br.com.centralit.citcorpore.negocio.CotacaoService;
import br.com.centralit.citcorpore.negocio.EnderecoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.ItemPedidoCompraService;
import br.com.centralit.citcorpore.negocio.PedidoCompraService;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoPedidoCompra;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class PedidoCompra extends AjaxFormAction {
    
    @SuppressWarnings("rawtypes")
    public Class getBeanClass() {
        return PedidoCompraDTO.class;
    }

    private void configuraBotoes(DocumentHTML document, HttpServletRequest request, PedidoCompraDTO pedidoCompraBean) throws Exception {
        if (pedidoCompraBean.getIdCotacao() == null)
            return;
        
        document.executeScript("document.getElementById('btnGravar').style.display = 'none'");
        document.executeScript("document.getElementById('btnExcluir').style.display = 'none'");
        
        if (pedidoCompraBean.getSituacao() != null && pedidoCompraBean.getSituacao().equals(SituacaoPedidoCompra.Entregue.name())) {
            document.executeScript("desabilitarTela()");
            return;
        }
        
        document.executeScript("habilitarTela()");
        CotacaoDTO cotacaoDto = new CotacaoDTO();
        cotacaoDto.setIdCotacao(pedidoCompraBean.getIdCotacao());
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        cotacaoDto = (CotacaoDTO) cotacaoService.restore(cotacaoDto);
        if (cotacaoDto == null)
            return;
 
        SituacaoCotacao situacao = SituacaoCotacao.valueOf(cotacaoDto.getSituacao());
        if (situacao.equals(SituacaoCotacao.Publicada) || situacao.equals(SituacaoCotacao.Pedido) || situacao.equals(SituacaoCotacao.Entrega)) {
            Collection colItens = cotacaoService.findItensPendentesAprovacao(cotacaoDto);
            if (colItens == null || colItens.size() == 0) {
                document.executeScript("document.getElementById('btnGravar').style.display = 'block'");
                if (pedidoCompraBean.getIdPedido() != null)
                    document.executeScript("document.getElementById('btnExcluir').style.display = 'block'");
            }
        }
    }
    
    public void carregaItens(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
        if (pedidoCompraDto.getIdFornecedor() == null)
            return;
        
        ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, null);
        ItemPedidoCompraService itemPedidoService = (ItemPedidoCompraService) ServiceLocator.getInstance().getService(ItemPedidoCompraService.class, null);
        Collection<ColetaPrecoDTO> colColetas = coletaPrecoService.findHabilitadasByIdCotacao(pedidoCompraDto.getIdCotacao());
        if (colColetas != null) {
        	Collection<ItemPedidoCompraDTO> itensPedido = new ArrayList();
            for (ColetaPrecoDTO coletaPrecoDto : colColetas) {
                if (coletaPrecoDto.getIdFornecedor().intValue() != pedidoCompraDto.getIdFornecedor().intValue())
                    continue;
                if (coletaPrecoDto.getIdProduto() == null)
                	continue;
                double qtdePedido = itemPedidoService.obtemQtdePedidoColetaPreco(coletaPrecoDto.getIdColetaPreco());
                if (qtdePedido < coletaPrecoDto.getQuantidadeDisponivel()) {
                    double quantidade = coletaPrecoDto.getQuantidadeDisponivel().doubleValue() - qtdePedido;
                    if (pedidoCompraDto.getQuantidade() != null && pedidoCompraDto.getQuantidade().doubleValue() < quantidade)
                        quantidade = pedidoCompraDto.getQuantidade().doubleValue();
                    
                    double valorTotal = coletaPrecoDto.getPreco() / coletaPrecoDto.getQuantidadeCotada() * quantidade;
                    double valorDesconto = coletaPrecoDto.getValorDesconto() / coletaPrecoDto.getQuantidadeCotada() * quantidade;
                    double valorAcrescimo = coletaPrecoDto.getValorAcrescimo() / coletaPrecoDto.getQuantidadeCotada() * quantidade;
                    
                    ItemPedidoCompraDTO itemPedidoDto = new ItemPedidoCompraDTO();
                    itemPedidoDto.setIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
                    itemPedidoDto.setQuantidade(quantidade);
                    itemPedidoDto.setValorTotal(valorTotal);
                    itemPedidoDto.setValorDesconto(valorDesconto);
                    itemPedidoDto.setValorAcrescimo(valorAcrescimo);
                    itemPedidoDto.setIdProduto(coletaPrecoDto.getIdProduto());
                    itemPedidoDto.setDescricaoItem(coletaPrecoDto.getDescricaoItem());
                    itemPedidoDto.setCodigoProduto(coletaPrecoDto.getCodigoProduto());
                    itemPedidoDto.setValorFrete(coletaPrecoDto.getValorFrete()); 
                    itensPedido.add(itemPedidoDto);
                }
            }
            if (itensPedido.size() > 0) {
            	HTMLTable tblItensPedido = document.getTableById("tblItensPedido");
                tblItensPedido.addRowsByCollection(itensPedido, 
							                        new String[] {"","descricaoItem","quantidade","valorTotal"}, 
							                        null, 
							                        "", 
							                        new String[] {"gerarImgAlterar"}, 
							                        "editarItem", 
							                        null);             	
            }else
                document.alert(UtilI18N.internacionaliza(request, "MSG04"));
        }
    }
    
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession(true).setAttribute("colUploadsGED", null);
        try{
            UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }
            
            PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
            
            HTMLSelect idFornecedor = (HTMLSelect) document.getSelectById("idFornecedor");
            idFornecedor.removeAllOptions();
            idFornecedor.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
            
            ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, null);
            Collection<ColetaPrecoDTO> colColetas = coletaPrecoService.findHabilitadasByIdCotacao(pedidoCompraDto.getIdCotacao());
            if (colColetas != null) {
                HashMap<String, ColetaPrecoDTO> mapFornecedor = new HashMap();
                for (ColetaPrecoDTO coletaPrecoDto : colColetas) {
                    if (mapFornecedor.get(""+coletaPrecoDto.getIdFornecedor()) != null)
                        continue;
                    idFornecedor.addOption(""+coletaPrecoDto.getIdFornecedor(), coletaPrecoDto.getNomeFornecedor());
                    mapFornecedor.put(""+coletaPrecoDto.getIdFornecedor(), coletaPrecoDto);
                }
            }
            
            /*HTMLSelect situacao = (HTMLSelect) document.getSelectById("situacao");
            situacao.removeAllOptions();
            situacao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
            for (SituacaoPedidoCompra situacaoObj : SituacaoPedidoCompra.values()) {  
                if (!situacaoObj.equals(SituacaoPedidoCompra.Entregue))
                    situacao.addOption(situacaoObj.name(), situacaoObj.getDescricao());
            }  */
            
            EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, WebUtil.getUsuarioSistema(request));
            HTMLSelect idEnderecoEntrega = (HTMLSelect) document.getSelectById("idEnderecoEntrega");
            idEnderecoEntrega.removeAllOptions();
            idEnderecoEntrega.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
            Collection colEnderecos = enderecoService.recuperaEnderecosEntregaProduto();
            if(colEnderecos != null && !colEnderecos.isEmpty())
                idEnderecoEntrega.addOptions(colEnderecos, "idEndereco", "enderecoStr", null);
            
            request.setAttribute("idCotacao", ""+pedidoCompraDto.getIdCotacao()); 
            exibePedidosCompra(document,request,pedidoCompraDto);
            restore(document,request,response);
            
        }finally{
            document.executeScript("parent.escondeJanelaAguarde()");
        }
    }


    private void exibePedidosCompra(DocumentHTML document, HttpServletRequest request, PedidoCompraDTO pedidoCompraDto) throws Exception {
        HTMLTable tblPedidos = document.getTableById("tblPedidos");
        tblPedidos.deleteAllRows();

        PedidoCompraService pedidoCompraService = (PedidoCompraService) ServiceLocator.getInstance().getService(PedidoCompraService.class, null);
        Collection<PedidoCompraDTO> colPedidos = pedidoCompraService.findByIdCotacao(pedidoCompraDto.getIdCotacao());
        if (colPedidos != null && !colPedidos.isEmpty()) {
            FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
            for (PedidoCompraDTO pedidoDto : colPedidos) {
                FornecedorDTO fornecedorDto = new FornecedorDTO();
                fornecedorDto.setIdFornecedor(pedidoDto.getIdFornecedor());
                fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
                if (fornecedorDto != null)
                    pedidoDto.setNomeFornecedor(fornecedorDto.getNomeFantasia());
            }
            tblPedidos.addRowsByCollection(colPedidos, 
                                                new String[] {"","numeroPedido","nomeFornecedor","descrSituacao"}, 
                                                null, 
                                                null,
                                                new String[] {"gerarImgAlteracao"}, 
                                                "exibirPedido", 
                                                null);
        }    
    }    
    
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
        request.getSession(true).setAttribute("colUploadsGED", null);
        document.executeScript("uploadAnexos.refresh()");
        
        HTMLForm form = document.getForm("form");
        form.clear();
        HTMLTable tblItensPedido = document.getTableById("tblItensPedido");
        tblItensPedido.deleteAllRows();
        if (pedidoCompraDto.getIdPedido() != null) {
            Integer idCotacao = pedidoCompraDto.getIdCotacao();
            PedidoCompraService pedidoCompraService = (PedidoCompraService) ServiceLocator.getInstance().getService(PedidoCompraService.class, null);
            pedidoCompraDto = (PedidoCompraDTO) pedidoCompraService.restore(pedidoCompraDto);
            pedidoCompraDto.setIdCotacao(idCotacao);
            
            ItemPedidoCompraService itemPedidoCompraService = (ItemPedidoCompraService) ServiceLocator.getInstance().getService(ItemPedidoCompraService.class, WebUtil.getUsuarioSistema(request));
            Collection<ItemPedidoCompraDTO> itensPedido = itemPedidoCompraService.findByIdPedido(pedidoCompraDto.getIdPedido());
            if (itensPedido != null) {
                tblItensPedido.addRowsByCollection(itensPedido, 
                                                    new String[] {"","descricaoItem","quantidade","valorTotal"}, 
                                                    null, 
                                                    "", 
                                                    new String[] {"gerarImgAlterar"}, 
                                                    "editarItem", 
                                                    null);  
            }
            
            restaurarAnexos(request, pedidoCompraDto.getIdPedido());
        }
        configuraBotoes(document, request, pedidoCompraDto);
        form.setValues(pedidoCompraDto);
    }
    
    public void recuperaValoresColetaPreco(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
        if (pedidoCompraDto.getIdColetaPreco() == null)
            return;
        
        ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, null);
        ColetaPrecoDTO coletaPrecoDto = new ColetaPrecoDTO();
        coletaPrecoDto.setIdColetaPreco(pedidoCompraDto.getIdColetaPreco());
        coletaPrecoDto = (ColetaPrecoDTO) coletaPrecoService.restore(coletaPrecoDto);
        if (coletaPrecoDto == null)
            return;
        
        ItemPedidoCompraService itemPedidoService = (ItemPedidoCompraService) ServiceLocator.getInstance().getService(ItemPedidoCompraService.class, null);
        double qtdePedido = itemPedidoService.obtemQtdePedidoColetaPreco(coletaPrecoDto.getIdColetaPreco());
        if (qtdePedido >= coletaPrecoDto.getQuantidadeDisponivel())
            return;
        
        double quantidade = coletaPrecoDto.getQuantidadeDisponivel().doubleValue() - qtdePedido;
        if (pedidoCompraDto.getQuantidade() != null && pedidoCompraDto.getQuantidade().doubleValue() < quantidade)
            quantidade = pedidoCompraDto.getQuantidade().doubleValue();
        
        double valorTotal = coletaPrecoDto.getPreco() / coletaPrecoDto.getQuantidadeCotada() * quantidade;
        double valorDesconto = coletaPrecoDto.getValorDesconto() / coletaPrecoDto.getQuantidadeCotada() * quantidade;
        double valorAcrescimo = coletaPrecoDto.getValorAcrescimo() / coletaPrecoDto.getQuantidadeCotada() * quantidade;
        
        ItemPedidoCompraDTO itemPedidoDto = new ItemPedidoCompraDTO();
        itemPedidoDto.setIdColetaPreco(pedidoCompraDto.getIdColetaPreco());
        itemPedidoDto.setQuantidade(quantidade);
        itemPedidoDto.setValorTotal(valorTotal);
        itemPedidoDto.setValorDesconto(valorDesconto);
        itemPedidoDto.setValorAcrescimo(valorAcrescimo);
        itemPedidoDto.setIdProduto(coletaPrecoDto.getIdProduto());
        itemPedidoDto.setDescricaoItem(coletaPrecoDto.getDescricaoItem());
        itemPedidoDto.setCodigoProduto(coletaPrecoDto.getCodigoProduto());
        itemPedidoDto.setValorFrete(coletaPrecoDto.getValorFrete());  
        document.executeScript("exibeValoresColetaPreco(\""+br.com.citframework.util.WebUtil.serializeObject(itemPedidoDto, WebUtil.getLanguage(request))+"\")");
    }
    
    public void preparaTelaItemPedido(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
        if (pedidoCompraDto.getIdFornecedor() == null)
            return;
        
        int idColeta = 0;
        if (pedidoCompraDto.getIdColetaPreco() != null)
            idColeta = pedidoCompraDto.getIdColetaPreco();
        
        HTMLSelect idColetaPreco = (HTMLSelect) document.getSelectById("item#idColetaPreco");
        idColetaPreco.removeAllOptions();
        idColetaPreco.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        
        ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, null);
        ItemPedidoCompraService itemPedidoService = (ItemPedidoCompraService) ServiceLocator.getInstance().getService(ItemPedidoCompraService.class, null);
        Collection<ColetaPrecoDTO> colColetas = coletaPrecoService.findHabilitadasByIdCotacao(pedidoCompraDto.getIdCotacao());
        if (colColetas != null) {
            for (ColetaPrecoDTO coletaPrecoDto : colColetas) {
                if (coletaPrecoDto.getIdFornecedor().intValue() != pedidoCompraDto.getIdFornecedor().intValue())
                    continue;
                double qtdePedido = itemPedidoService.obtemQtdePedidoColetaPreco(coletaPrecoDto.getIdColetaPreco());
                if (qtdePedido < coletaPrecoDto.getQuantidadeDisponivel() || idColeta == coletaPrecoDto.getIdColetaPreco())
                    idColetaPreco.addOption(""+coletaPrecoDto.getIdColetaPreco(), "Coleta de preço "+coletaPrecoDto.getIdColetaPreco()+" - "+coletaPrecoDto.getDescricaoItem());
            }
            if (idColeta > 0)
                idColetaPreco.setValue(""+idColeta);
        }
        
        document.executeScript("$(\"#POPUP_ITEM_PEDIDO\").dialog(\"open\");");
    }
    
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
        
        Collection<ItemPedidoCompraDTO> colItens = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ItemPedidoCompraDTO.class, "itens_serialize", request);
        pedidoCompraDto.setColItens(colItens);

        Collection<UploadDTO> anexos = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
        pedidoCompraDto.setAnexos(anexos);
        
        pedidoCompraDto.setUsuarioDto(usuario);
        pedidoCompraDto.setSituacao(SituacaoPedidoCompra.Efetivado.name());
        PedidoCompraService pedidoCompraService = (PedidoCompraService) ServiceLocator.getInstance().getService(PedidoCompraService.class, null);
        if (pedidoCompraDto.getIdPedido() != null) {
            pedidoCompraService.update(pedidoCompraDto);
        }else{
            pedidoCompraDto.setIdEmpresa(usuario.getIdEmpresa());
            pedidoCompraService.create(pedidoCompraDto);
        }
        exibePedidosCompra(document,request,pedidoCompraDto);
        document.alert(UtilI18N.internacionaliza(request, "pedidoCompra.mensagemAtualizacao"));
        document.executeScript("limpar();");
        document.executeScript("parent.atualiza();");
    }
    
    public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
        pedidoCompraDto.setUsuarioDto(usuario);
        
        if (pedidoCompraDto.getIdPedido() != null) {
            pedidoCompraDto.setUsuarioDto(usuario);
            PedidoCompraService pedidoCompraService = (PedidoCompraService) ServiceLocator.getInstance().getService(PedidoCompraService.class, null);
            pedidoCompraService.delete(pedidoCompraDto);
            exibePedidosCompra(document,request,pedidoCompraDto);
            document.alert(UtilI18N.internacionaliza(request, "pedidoCompra.mensagemExclusao"));
            document.executeScript("limpar();");
        }
    }    
    protected void restaurarAnexos(HttpServletRequest request, Integer idPedido) throws ServiceException, Exception {
        ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_PEDIDOCOMPRA, idPedido);
        Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

        request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);
    }

}
