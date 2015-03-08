package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CategoriaProdutoDTO;
import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.centralit.citcorpore.bean.ItemCotacaoDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CategoriaProdutoService;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.CotacaoService;
import br.com.centralit.citcorpore.negocio.EnderecoService;
import br.com.centralit.citcorpore.negocio.ItemCotacaoService;
import br.com.centralit.citcorpore.negocio.ItemRequisicaoProdutoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.RequisicaoProdutoService;
import br.com.centralit.citcorpore.negocio.UnidadeMedidaService;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
@SuppressWarnings({"rawtypes","unchecked"})
public class ItemCotacao extends AjaxFormAction {
    
   
    public Class getBeanClass() {
        return ItemCotacaoDTO.class;
    }

    private void configuraBotoes(DocumentHTML document, HttpServletRequest request, ItemCotacaoDTO itemCotacaoDto) throws Exception {
        CotacaoDTO cotacaoDto = new CotacaoDTO();
        cotacaoDto.setIdCotacao(itemCotacaoDto.getIdCotacao());
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        cotacaoDto = (CotacaoDTO) cotacaoService.restore(cotacaoDto);
        if (cotacaoDto == null)
            return;
        
        document.executeScript("document.getElementById('btnPesquisarRequisicao').style.display = 'none'");

        SituacaoCotacao situacao = SituacaoCotacao.valueOf(cotacaoDto.getSituacao());
        request.setAttribute("situacao", situacao);
        if (situacao.equals(SituacaoCotacao.EmAndamento)) {
            document.executeScript("document.getElementById('btnPesquisarRequisicao').style.display = 'block'");
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
            
            ItemCotacaoDTO itemCotacaoDto = (ItemCotacaoDTO) document.getBean();
            
            UnidadeMedidaService unidadeMedidaService = (UnidadeMedidaService) ServiceLocator.getInstance().getService(UnidadeMedidaService.class, WebUtil.getUsuarioSistema(request));
            HTMLSelect idUnidadeMedida = (HTMLSelect) document.getSelectById("idUnidadeMedida");
            idUnidadeMedida.removeAllOptions();
            idUnidadeMedida.addOption("", "---");
            Collection colUnidades = unidadeMedidaService.list();
            if(colUnidades != null && !colUnidades.isEmpty())
                idUnidadeMedida.addOptions(colUnidades, "idUnidadeMedida", "siglaUnidadeMedida", null);
            
            CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
            HTMLSelect idCentroCusto = (HTMLSelect) document.getSelectById("idCentroCusto");
            idCentroCusto.removeAllOptions();
            idCentroCusto.addOption("", "-- Selecione --");
            Collection colCCusto = centroResultadoService.listPermiteRequisicaoProduto();
            if(colCCusto != null && !colCCusto.isEmpty())
                idCentroCusto.addOptions(colCCusto, "idCentroResultado", "nomeHierarquizado", null);
                    
            HTMLSelect idProjeto = (HTMLSelect) document.getSelectById("idProjeto");
            idProjeto.removeAllOptions();
            idProjeto.addOption("", "-- Selecione --");
            ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
            Collection colProjetos = projetoService.list();
            if(colProjetos != null && !colProjetos.isEmpty()) 
                idProjeto.addOptions(colProjetos, "idProjeto", "nomeProjeto", null);
            
            EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, WebUtil.getUsuarioSistema(request));
            HTMLSelect idEnderecoEntrega = (HTMLSelect) document.getSelectById("idEnderecoEntrega");
            idEnderecoEntrega.removeAllOptions();
            idEnderecoEntrega.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
            Collection colEnderecos = enderecoService.recuperaEnderecosEntregaProduto();
            if(colEnderecos != null && !colEnderecos.isEmpty())
                idEnderecoEntrega.addOptions(colEnderecos, "idEndereco", "enderecoStr", null);

            CategoriaProdutoService categoriaProdutoService = (CategoriaProdutoService) ServiceLocator.getInstance().getService(CategoriaProdutoService.class, WebUtil.getUsuarioSistema(request));
            Collection<CategoriaProdutoDTO> colCategorias = categoriaProdutoService.listAtivas();
            HTMLSelect idCategoria = (HTMLSelect) document.getSelectById("idCategoriaProduto");
            idCategoria.removeAllOptions();
            idCategoria.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
            if(colCategorias != null && !colCategorias.isEmpty())
            	idCategoria.addOptions(colCategorias, "idCategoria", "nomeHierarquizado", null);

            exibeItensCotacao(document, request, itemCotacaoDto);
            configuraBotoes(document, request, itemCotacaoDto);
            request.setAttribute("idCotacao", ""+itemCotacaoDto.getIdCotacao());
        }finally{
            document.executeScript("parent.escondeJanelaAguarde()");
        }
    }

	private void exibeItensCotacao(DocumentHTML document, HttpServletRequest request, ItemCotacaoDTO itemCotacaoDto) throws Exception {
        String str = "";
        ItemCotacaoService itemCotacaoService = (ItemCotacaoService) ServiceLocator.getInstance().getService(ItemCotacaoService.class, null);
        Collection<ItemCotacaoDTO> colItensCotacao = itemCotacaoService.findByIdCotacao(itemCotacaoDto.getIdCotacao());
        if (colItensCotacao != null && !colItensCotacao.isEmpty()) {
            ItemRequisicaoProdutoService itemRequisicaoService = (ItemRequisicaoProdutoService) ServiceLocator.getInstance().getService(ItemRequisicaoProdutoService.class, null);
            RequisicaoProdutoService requisicaoService = (RequisicaoProdutoService) ServiceLocator.getInstance().getService(RequisicaoProdutoService.class, null);

            str = 
             "<table class=\"table\" width=\"100%\">";
             str += "    <tr>";
             str += "        <th style=\"font-size:14px;background-color:#BFC6C3\" colspan=\"4\">"+UtilI18N.internacionaliza(request, "itemCotacao") + "</th>";
             str += "        <th style=\"font-size:14px;background-color:#BFC6C3\" colspan=\"4\">"+UtilI18N.internacionaliza(request, "requisicaoProduto.requisicao") + "</th>";
             str += "    </tr>";
             str += "    <tr>";
             str += "        <th width=\"1%\">&nbsp;</th>";
             str += "        <th colspan=\"2\">"+UtilI18N.internacionaliza(request, "itemRequisicaoProduto.produto") + "</th>";
             str += "        <th width=\"5%\">"+UtilI18N.internacionaliza(request, "itemRequisicaoProduto.quantidade") + "</th>";
             str += "        <th width=\"5%\">"+UtilI18N.internacionaliza(request, "coletaPreco.numero") + "</th>";
             str += "        <th width=\"15%\">"+UtilI18N.internacionaliza(request, "requisicaoProduto.finalidade") + "</th>";
             str += "        <th width=\"5%\">"+UtilI18N.internacionaliza(request, "itemRequisicaoProduto.quantidade") + "</th>";
             str += "        <th width=\"15%\">"+UtilI18N.internacionaliza(request, "solicitacaoServico.datahoralimite") + "</th>";
             str += "    </tr>";
    
             for (ItemCotacaoDTO itemDto : colItensCotacao) {
                 int rowSpan = 1;
                 Collection<ItemRequisicaoProdutoDTO> colItens = itemRequisicaoService.findByIdItemCotacao(itemDto.getIdItemCotacao());
                 if (colItens != null && colItens.size() > 0)
                     rowSpan = colItens.size();
                 str += "<tr onMouseOver='TrowOn(this,\"#FFCC99\");' onMouseOut='TrowOff(this)'>";
                 str += "    <td rowspan=\""+rowSpan+"\" ><img style=\"cursor: pointer;\" src=\""+br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")+"/imagens/delete.png\" onclick=\"excluirItemCotacao('" + itemDto.getIdItemCotacao() + "')\" /></td>";
                 str += "    <td rowspan=\""+rowSpan+"\" style=\"text-align:left;\" >"+UtilStrings.nullToVazio(itemDto.getCodigoProduto())+"</td>";
                 str += "    <td rowspan=\""+rowSpan+"\" style=\"text-align:left;\" >"+itemDto.getDescricaoItem()+"</td>";
                 str += "    <td rowspan=\""+rowSpan+"\" style=\"text-align:left;\" >"+itemDto.getQuantidade()+"</td>";
                 
                 if (colItens != null && colItens.size() > 0) {
                     int i = 0;
                     for (ItemRequisicaoProdutoDTO itemRequisicaoDto : colItens) {
                         RequisicaoProdutoDTO requisicaoDto = new RequisicaoProdutoDTO();
                         requisicaoDto.setIdSolicitacaoServico(itemRequisicaoDto.getIdSolicitacaoServico());
                         requisicaoDto = (RequisicaoProdutoDTO) requisicaoService.restore(requisicaoDto);
                         
                         requisicaoDto.setDataHoraLimiteStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, requisicaoDto.getDataHoraLimite(), WebUtil.getLanguage(request)));
                         
                         if (i > 0)
                             str += "<tr>";
                         String cor = "";
                         String finalidade = UtilI18N.internacionaliza(request, "requisicaoProduto.finalidade.usoInterno");
                         if (requisicaoDto.getFinalidade().equals("C")) {
                             cor = "red";
                             finalidade = UtilI18N.internacionaliza(request, "requisicaoProduto.finalidade.atendimentoCliente");
                         }
                         str += "    <td style=\"text-align:right;color:"+cor+"\" >"+requisicaoDto.getIdSolicitacaoServico()+"</td>";
                         str += "    <td style=\"color:"+cor+"\">"+finalidade+"</td>";
                         str += "    <td style=\"text-align:right;color:"+cor+"\" >"+itemRequisicaoDto.getQtdeAprovada()+"</td>";
                         str += "    <td style=\"color:"+cor+"\">"+(requisicaoDto.getDataHoraLimiteStr() == null ? "" : requisicaoDto.getDataHoraLimiteStr())+"</td>"; 
                         str += "</tr>";
                         i++;
                     }
                 }else{
                     str += "    <td>&nbsp;</td>";
                     str += "    <td>&nbsp;</td>";
                     str += "    <td>&nbsp;</td>";
                     str += "    <td>&nbsp;</td>";
                     str += "</tr>";
                 }
             }
             str += "</table>";
             
        }    
        document.getElementById("divItensCotacao").setInnerHTML(str);
    }

    
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    }
    
    public void incluiItensRequisicao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        ItemCotacaoDTO itemCotacaoDto = (ItemCotacaoDTO) document.getBean();
        Collection<ItemRequisicaoProdutoDTO> colItensRequisicao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ItemRequisicaoProdutoDTO.class, "colItensRequisicao_Serialize", request);
        if (colItensRequisicao == null || colItensRequisicao.isEmpty()) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.nenhumaSelecao"));
            return;            
        }
        
        incluiItensRequisicao(document,request,itemCotacaoDto,itemCotacaoDto,colItensRequisicao);
    }
    
    public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        ItemCotacaoDTO itemCotacaoDto = (ItemCotacaoDTO) document.getBean();
        if (itemCotacaoDto.getIdItemCotacao() == null)
            return;
        
        ItemCotacaoService itemCotacaoService = (ItemCotacaoService) ServiceLocator.getInstance().getService(ItemCotacaoService.class, null);
        itemCotacaoDto.setUsuarioDto(usuario);
        itemCotacaoService.delete(itemCotacaoDto);
        exibeItensCotacao(document, request, itemCotacaoDto);
    }
    

    public void pesquisaItensParaCotacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        HTMLTable tblItensRequisicao = document.getTableById("tblItensRequisicao");
        tblItensRequisicao.deleteAllRows();

        ItemCotacaoDTO itemCotacaoDto = (ItemCotacaoDTO) document.getBean();
        
        ItemRequisicaoProdutoService itemRequisicaoProdutoService = (ItemRequisicaoProdutoService) ServiceLocator.getInstance().getService(ItemRequisicaoProdutoService.class, WebUtil.getUsuarioSistema(request));
        Collection<ItemRequisicaoProdutoDTO> itensRequisicao = itemRequisicaoProdutoService.recuperaItensParaCotacao(itemCotacaoDto.getDataInicio(), 
                                                                                                                     itemCotacaoDto.getDataFim(),
                                                                                                                     itemCotacaoDto.getIdCentroCusto(),
                                                                                                                     itemCotacaoDto.getIdProjeto(),
                                                                                                                     itemCotacaoDto.getIdEnderecoEntrega(),                                                                                                                  
                                                                                                                     itemCotacaoDto.getIdSolicitacaoServico());
        if (itensRequisicao != null && !itensRequisicao.isEmpty()) {
            EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, WebUtil.getUsuarioSistema(request));
        	for (ItemRequisicaoProdutoDTO itemDto : itensRequisicao) {
        		EnderecoDTO enderecoDto = enderecoService.recuperaEnderecoComUnidade(itemDto.getIdEnderecoEntrega());
        		if (enderecoDto != null)
        			itemDto.setEnderecoStr(enderecoDto.getEnderecoStr());
			}
            tblItensRequisicao.addRowsByCollection(itensRequisicao, 
                                                new String[] {"","idSolicitacaoServico","dataHoraSolicitacao","nomeCentroCusto","nomeProjeto","enderecoStr","codigoProduto","descricaoItem","qtdeAprovada"}, 
                                                null, 
                                                "", 
                                                new String[] {"gerarSelecaoItemRequisicao"}, 
                                                null, 
                                                null);  
            document.executeScript("document.getElementById('divSelecionarItens').style.display = 'block';");       
        }else{
        	document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.resultado"));
        }
    }
    
    public void verificaInclusaoItensRequisicao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        Collection<ItemRequisicaoProdutoDTO> colItensRequisicao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ItemRequisicaoProdutoDTO.class, "colItensRequisicao_Serialize", request);
        if (colItensRequisicao == null || colItensRequisicao.isEmpty()) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.nenhumaSelecao"));
            return;            
        }
        
        ItemCotacaoDTO itemCotacaoDto = (ItemCotacaoDTO) document.getBean();
        if (itemCotacaoDto.getTipoCriacaoItem() == null) {
            document.alert(UtilI18N.internacionaliza(request, "itemCotacao.tipoCriacao")+""+UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado"));
            return;
        }
        
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        ItemCotacaoDTO itemCotacaoRef = cotacaoService.verificaInclusaoItensRequisicao(itemCotacaoDto.getTipoCriacaoItem(), colItensRequisicao);
        
        if (itemCotacaoDto.getTipoCriacaoItem().equals("2") && itemCotacaoRef != null && itemCotacaoRef.getTipoIdentificacao().equalsIgnoreCase("D")) {
            HTMLForm form = document.getForm("formItemCotacao");
            form.setValues(itemCotacaoRef);
            document.executeScript("exibirItemCotacao();");
            return;
        }else{
            incluiItensRequisicao(document,request,itemCotacaoDto,itemCotacaoRef,colItensRequisicao);
        }
    }
    
    private void incluiItensRequisicao(DocumentHTML document, HttpServletRequest request, ItemCotacaoDTO itemCotacaoDto, ItemCotacaoDTO itemCotacaoRef, Collection<ItemRequisicaoProdutoDTO> colItensRequisicao) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        cotacaoService.incluiItensRequisicao(usuario, itemCotacaoDto.getIdCotacao(), itemCotacaoRef, itemCotacaoDto.getTipoCriacaoItem(), colItensRequisicao);
        exibeItensCotacao(document, request, itemCotacaoDto);
        document.executeScript("$(\"#POPUP_ITENS_REQUISICAO\").dialog(\"close\");");
    }

}
