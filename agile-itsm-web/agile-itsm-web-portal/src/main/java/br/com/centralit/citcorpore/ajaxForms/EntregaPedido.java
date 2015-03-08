package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO;
import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.InspecaoPedidoCompraDTO;
import br.com.centralit.citcorpore.bean.ItemPedidoCompraDTO;
import br.com.centralit.citcorpore.bean.PedidoCompraDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CotacaoService;
import br.com.centralit.citcorpore.negocio.CriterioAvaliacaoService;
import br.com.centralit.citcorpore.negocio.EnderecoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.InspecaoPedidoCompraService;
import br.com.centralit.citcorpore.negocio.ItemPedidoCompraService;
import br.com.centralit.citcorpore.negocio.PedidoCompraService;
import br.com.centralit.citcorpore.negocio.RequisicaoProdutoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoPedidoCompra;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Html2Pdf;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

public class EntregaPedido extends AjaxFormAction {
    
    @SuppressWarnings("rawtypes")
    public Class getBeanClass() {
        return PedidoCompraDTO.class;
    }

    private void configuraBotoes(DocumentHTML document, HttpServletRequest request, PedidoCompraDTO pedidoCompraBean) throws Exception {
        if (pedidoCompraBean.getIdCotacao() == null)
            return;
        
        document.executeScript("document.getElementById('btnGravar').style.display = 'none'");
        document.executeScript("document.getElementById('btnEspelho').style.display = 'none'");
        
        if (pedidoCompraBean.getSituacao() != null && pedidoCompraBean.getSituacao().equals(SituacaoPedidoCompra.Entregue.name())) {
            document.executeScript("desabilitarTela()");
            document.executeScript("document.getElementById('btnEspelho').style.display = 'block'");
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
        if (situacao.equals(SituacaoCotacao.Pedido) || situacao.equals(SituacaoCotacao.Entrega)) {
            Collection colItens = cotacaoService.findItensPendentesAprovacao(cotacaoDto);
            if (colItens == null || colItens.size() == 0) {
                document.executeScript("document.getElementById('btnGravar').style.display = 'block'");
            }
        }
    }
    
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }
            
            CriterioAvaliacaoService criterioAvaliacaoService = (CriterioAvaliacaoService) ServiceLocator.getInstance().getService(CriterioAvaliacaoService.class, WebUtil.getUsuarioSistema(request));
            Collection<CriterioAvaliacaoDTO> colCriterios = criterioAvaliacaoService.findByAplicavelAvaliacaoComprador();
            request.setAttribute("colCriterios", colCriterios);
            
            PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
            
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
        
        HTMLForm form = document.getForm("form");
        form.clear();
        if (pedidoCompraDto.getIdPedido() != null) {
            Integer idCotacao = pedidoCompraDto.getIdCotacao();
            PedidoCompraService pedidoCompraService = (PedidoCompraService) ServiceLocator.getInstance().getService(PedidoCompraService.class, null);
            pedidoCompraDto = (PedidoCompraDTO) pedidoCompraService.restore(pedidoCompraDto);
            pedidoCompraDto.setIdCotacao(idCotacao);

            FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
            FornecedorDTO fornecedorDto = new FornecedorDTO();
            fornecedorDto.setIdFornecedor(pedidoCompraDto.getIdFornecedor());
            fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
            pedidoCompraDto.setNomeFornecedor(fornecedorDto.getNomeFantasia());
            
            InspecaoPedidoCompraService inspecaoPedidoService = (InspecaoPedidoCompraService) ServiceLocator.getInstance().getService(InspecaoPedidoCompraService.class, null);
            CriterioAvaliacaoService criterioAvaliacaoService = (CriterioAvaliacaoService) ServiceLocator.getInstance().getService(CriterioAvaliacaoService.class, null);
            Collection<InspecaoPedidoCompraDTO> colInspecao = inspecaoPedidoService.findByIdPedido(pedidoCompraDto.getIdPedido());
            document.executeScript("GRID_CRITERIOS.deleteAllRows();");
            if (colInspecao == null) {
                Collection<CriterioAvaliacaoDTO> colCriterios = criterioAvaliacaoService.findByAplicavelAvaliacaoComprador();
                if (colCriterios != null) {
                    colInspecao = new ArrayList();
                    for (CriterioAvaliacaoDTO criterioDto : colCriterios) {
                        InspecaoPedidoCompraDTO inspecaoDto = new InspecaoPedidoCompraDTO();
                        inspecaoDto.setIdCriterio(criterioDto.getIdCriterio());
                        inspecaoDto.setTipoAvaliacao(criterioDto.getTipoAvaliacao());
                        colInspecao.add(inspecaoDto);
                    }
                }
            }else{
                for (InspecaoPedidoCompraDTO inspecaoDto : colInspecao) {
                    CriterioAvaliacaoDTO criterioDto = new CriterioAvaliacaoDTO();
                    criterioDto.setIdCriterio(inspecaoDto.getIdCriterio());
                    criterioDto = (CriterioAvaliacaoDTO) criterioAvaliacaoService.restore(criterioDto);
                    if (criterioDto == null)
                        continue;
                    inspecaoDto.setTipoAvaliacao(criterioDto.getTipoAvaliacao());
                }    
            }
            if (colInspecao != null) {
                int i = 0;
                for (InspecaoPedidoCompraDTO inspecaoDto : colInspecao) {
                    i++;
                    document.executeScript("GRID_CRITERIOS.addRow()");
                    inspecaoDto.setSequencia(i);
                    document.executeScript("seqCriterio = NumberUtil.zerosAEsquerda("+i+",5)");
                    document.executeScript("exibeCriterio('" + br.com.citframework.util.WebUtil.serializeObject(inspecaoDto, WebUtil.getLanguage(request)) + "')");
                }    
            }
        }
        configuraBotoes(document, request, pedidoCompraDto);
        form.setValues(pedidoCompraDto);
    }
    
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
        if (pedidoCompraDto.getIdPedido() == null) 
            return;
        
        pedidoCompraDto.setUsuarioDto(usuario);
        Collection<InspecaoPedidoCompraDTO> colInspecao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(InspecaoPedidoCompraDTO.class, "colCriterios_Serialize", request);
        pedidoCompraDto.setColInspecao(colInspecao);
        pedidoCompraDto.setUsuarioDto(usuario);
        
        PedidoCompraService pedidoCompraService = (PedidoCompraService) ServiceLocator.getInstance().getService(PedidoCompraService.class, null);
        pedidoCompraService.atualizaEntrega(pedidoCompraDto);
        exibePedidosCompra(document,request,pedidoCompraDto);
        document.alert(UtilI18N.internacionaliza(request, "pedidoCompra.mensagemAtualizacao"));
        document.executeScript("limpar();");
        document.executeScript("parent.atualiza();");
    }

    public void emiteEspelhoCompra(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try{
	        UsuarioDTO usuario = WebUtil.getUsuario(request);
	        if (usuario == null) {
	            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
	            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
	            return;
	        }
	        
	        PedidoCompraDTO pedidoCompraDto = (PedidoCompraDTO) document.getBean();
	        if (pedidoCompraDto.getIdPedido() == null) 
	            return;
	        
            PedidoCompraService pedidoCompraService = (PedidoCompraService) ServiceLocator.getInstance().getService(PedidoCompraService.class, null);
            pedidoCompraDto = (PedidoCompraDTO) pedidoCompraService.restore(pedidoCompraDto);
            
            FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
            FornecedorDTO fornecedorDto = new FornecedorDTO();
            fornecedorDto.setIdFornecedor(pedidoCompraDto.getIdFornecedor());
            fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);

            ItemPedidoCompraService itemPedidoCompraService = (ItemPedidoCompraService) ServiceLocator.getInstance().getService(ItemPedidoCompraService.class, WebUtil.getUsuarioSistema(request));
            Collection<ItemPedidoCompraDTO> itensPedido = itemPedidoCompraService.findByIdPedidoOrderByIdSolicitacao(pedidoCompraDto.getIdPedido());
            if (itensPedido == null)
            	return;
            
            EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, WebUtil.getUsuarioSistema(request));
            EnderecoDTO enderecoDto = new EnderecoDTO();
            enderecoDto.setIdEndereco(pedidoCompraDto.getIdEnderecoEntrega());
            enderecoDto = enderecoService.recuperaEnderecoCompleto(enderecoDto);
            
            RequisicaoProdutoService requisicaoProdutoService = (RequisicaoProdutoService) ServiceLocator.getInstance().getService(RequisicaoProdutoService.class, WebUtil.getUsuarioSistema(request));

            String style = 
            		"<style>"
            		+"@page {size: 4.18in 6.88in; margin: 30px 20px 15px 35px; size:landscape;}"
            		+"		  .table {"
					+"            border:1px solid #ddd;"
					+"            font-size:12px;"
					+"        }"
					+"        .table th {"
					+"            border:1px solid #ddd;"
					+"            padding:4px 10px;"
					+"            background:#eee;"
					+"        }"
					+"        .table td {"
					+"            border:1px solid #ddd;"
					+"            padding:4px 10px;"
					+"            border-top:none;"
					+"        }"
					+"</style>";
            		
	    	String str = "<div style='text-align:center;font-size:18px'><b>ESPELHO DA AUTORIZAÇÃO DE COMPRA</b></div><br>";
	    	str += "<table class='table' width='100%'>";
			str += "<tr>";
			str += "<th class='table th' width='15%'>Número pedido:</th>";
			str += "<td class='table th' width='20%'>"+pedidoCompraDto.getNumeroPedido()+"</td>";
			str += "<th class='table th' width='15%'>Número cotação:</th>";
			str += "<td class='table th' width='10%'>"+pedidoCompraDto.getIdCotacao()+"</td>";
			str += "<th class='table th' width='15%'>Data entrega:</th>";
			str += "<td class='table th' width='25%'>"+UtilDatas.dateToSTR(pedidoCompraDto.getDataEntrega())+"</td>";
			str += "</tr>";
			str += "<tr>";
			str += "<th class='table th'>Número nota fiscal:</th>";
			str += "<td>"+UtilStrings.nullToVazio(pedidoCompraDto.getNumeroNF())+"</td>";
			str += "<th class='table th'>Valor frete:</th>";
			if (pedidoCompraDto.getValorFrete() != null)
				str += "<td colspan='3'>"+UtilFormatacao.formatDouble(pedidoCompraDto.getValorFrete(), 2)+"</td>";
			else
				str += "<td colspan='3'>&nbsp;</td>";
			str += "</tr>";
			str += "<tr>";
			str += "<th class='table th'>Fornecedor:</th><td colspan='5'>";
			if (fornecedorDto.getTipoPessoa().equalsIgnoreCase("J"))
				str += UtilFormatacao.formataCnpj(fornecedorDto.getCnpj());
			else
				str += UtilFormatacao.formataCpf(fornecedorDto.getCnpj());
			str += " - "+fornecedorDto.getNomeFantasia()+"</td>";
			str += "</tr>";
			str += "<tr>";
			str += "<th class='table th'>Endereço entrega:</th><td colspan='5'>";
			str += enderecoDto.getEnderecoStr()+"</td>";
			str += "</tr>";
			str += "</table>";

			Double qtdeTotal = 0.0;
			Double valorTotal = 0.0;
			RequisicaoProdutoDTO requisicaoProdutoDto = null;
			Integer idSolicitacaoAnterior = 0;
	    	for (ItemPedidoCompraDTO itemPedidoDto : itensPedido) {
	    		if (idSolicitacaoAnterior.intValue() != itemPedidoDto.getIdSolicitacaoServico().intValue()) {
	    			requisicaoProdutoDto = new RequisicaoProdutoDTO();
	    			requisicaoProdutoDto.setIdSolicitacaoServico(itemPedidoDto.getIdSolicitacaoServico());
	    			requisicaoProdutoDto = (RequisicaoProdutoDTO) requisicaoProdutoService.restore(requisicaoProdutoDto);
	    	    	str += "<br><table class='table' width='100%'>";
					str += "<tr>";
					str += "<th class='table th'>Requisição</th>";
					str += "<th class='table th'>Solicitante</th>";
					str += "<th class='table th'>Centro de Resultado</th>";
					str += "<th class='table th'>Projeto</th>";
					str += "</tr>";
					str += "<tr>";
					str += "<td>"+requisicaoProdutoDto.getIdSolicitacaoServico()+"</td>";
					str += "<td>"+requisicaoProdutoDto.getSolicitante()+"</td>";
					str += "<td>"+requisicaoProdutoDto.getCentroCusto()+"</td>";
					str += "<td>"+requisicaoProdutoDto.getProjeto()+"</td>";
					str += "</tr>";
					str += "</table>";
	    	    	str += "<table class='table' width='100%'>";
					str += "<tr>";
					str += "<th class='table th'>Descrição do item</th>";
					str += "<th class='table th' width='20%'>Comprador</th>";
					str += "<th class='table th' width='20%'>Autorizador</th>";
					str += "<th class='table th' width='20%'>Aprovador</th>";
					str += "<th class='table th' width='3%'>Qtde</th>";
					str += "<th class='table th' width='9%'>Vlr total (R$)</th>";
					str += "</tr>";
					idSolicitacaoAnterior = itemPedidoDto.getIdSolicitacaoServico();
	    		}
				str += "<tr>";
				str += "<td>"+itemPedidoDto.getDescricaoItem()+"</td>";
				str += "<td>"+itemPedidoDto.getAutoridadeValidacao()+"</td>";
				str += "<td>"+itemPedidoDto.getAutoridadeAprovacao()+"</td>";
				str += "<td>"+itemPedidoDto.getAutoridadeCotacao()+"</td>";
				str += "<td style='text-align:right'>"+UtilFormatacao.formatDouble(itemPedidoDto.getQuantidade(),2)+"</td>";
				str += "<td style='text-align:right'>"+UtilFormatacao.formatDouble(itemPedidoDto.getValorLiquido(),2)+"</td>";
				str += "</tr>";
				qtdeTotal += itemPedidoDto.getQuantidade();
				valorTotal += itemPedidoDto.getValorLiquido();
			}
			str += "<tr>";
			str += "<th colspan='4' style='text-align:right'>&nbsp;</th>";
			str += "<th style='text-align:right'>"+UtilFormatacao.formatDouble(qtdeTotal,2)+"</th>";
			str += "<th style='text-align:right'>"+UtilFormatacao.formatDouble(valorTotal,2)+"</th>";
			str += "</tr>";
			str += "</table>";

			String caminho = CITCorporeUtil.CAMINHO_REAL_APP + "tempFiles";
			String caminhoRelativo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
			try {
				File arquivo = new File(caminho);
				if (!arquivo.exists()) {
					arquivo.mkdirs();
				}
	            java.util.Date dt = new java.util.Date();
	            String nomeArquivo = "/EspelhoAutorizacaoCompra_" + dt.getTime() + ".pdf";

				caminho += nomeArquivo;
				caminhoRelativo += nomeArquivo;

				arquivo = new File(caminho);
				if (arquivo.exists()) {
					arquivo.delete();
				}

	            OutputStream os = new FileOutputStream(arquivo);
	            str = UtilHTML.encodeHTMLComEspacos(str);
	            Html2Pdf.convert(str, os, style);   
	            
	            os.close();

	        	document.executeScript("window.open('" + caminhoRelativo + "')");
			} catch (Exception e) {
				e.printStackTrace();
				// handle exception
			}
    	}finally{
    		document.executeScript("JANELA_AGUARDE_MENU.hide();");
    	}
    }
}
