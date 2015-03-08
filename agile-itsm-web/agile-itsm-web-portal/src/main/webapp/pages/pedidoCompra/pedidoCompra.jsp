<%@page import="br.com.centralit.citcorpore.util.Enumerados.TipoDate"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoPedidoCompra"%>

<!doctype html public "">
<html>
<head>
<%
            //identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
            String iframe = "";
            iframe = request.getParameter("iframe");

            Collection<CriterioAvaliacaoDTO> colCriterios = (Collection)request.getAttribute("colCriterios");

            String idCotacao = (String)request.getAttribute("idCotacao");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
<!--<![endif]-->
<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
    <script type="text/javascript" src="${ctx}/cit/objects/ItemPedidoCompraDTO.js"></script>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

<script>
    addEvent(window, "load", load, false);
	var popupProduto;
    function load() {
		popupProduto = new PopupManager(1000, 680, "${ctx}/pages/");
		popupProduto.titulo = i18n_message("produto.cadastro");
        document.form.afterRestore = function() {
            document.form.idCotacao.value = '<%=idCotacao%>';
            $('.tabs').tabs('select', 0);
            if (!StringUtils.isBlank(document.form.idPedido.value)) {
                //document.getElementById('idFornecedor').disabled = true;
                verificaSituacao(document.form.situacao.value);
            }else{
                //document.getElementById('idFornecedor').disabled = false;
                document.form.dataPedido.value = "<%=UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, new java.util.Date(), WebUtil.getLanguage(request))%>";
            }
        }
        document.form.afterLoad = function() {
            $('.tabs').tabs('select', 0);
            document.form.dataPedido.value = "<%=UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, new java.util.Date(), WebUtil.getLanguage(request))%>";
        }
    }

    function desabilitarTela() {
        var f = document.form;
        for(i=0;i<f.length;i++){
            var el =  f.elements[i];
            if (el.type != 'hidden' && el.type != 'button') {
                if (el.disabled != null && el.disabled != undefined) {
                    el.disabled = 'disabled';
                }
            }
        }
    }

    function habilitarTela() {
        var f = document.form;
        for(i=0;i<f.length;i++){
            var el =  f.elements[i];
            if (el.type != 'hidden' && el.type != 'button') {
                if (el.disabled != null && el.disabled != undefined) {
                    el.disabled = false;
                }
            }
        }
    }

    function limpar(){
        var id = '';
        if (rowPedido != null)
            rowPedido.style.background = '';
        rowPedido = null;

        document.form.idCotacao.value = '<%=idCotacao%>';
        if (document.form.idCotacao.value == 'null' || document.form.idCotacao.value == null) {
        	document.form.idCotacao.value = '';
        }

		document.form.restore({idPedido: id, idCotacao: document.form.idCotacao.value});
    }

    var rowPedido = null;
    function exibirPedido(row, obj) {
        if (rowPedido != null)
            rowPedido.style.background = '';
        rowPedido = row;
        row.style.background = '#FFCC99';
        var obj = HTMLUtils.getObjectByTableIndex("tblPedidos", row.rowIndex);
        var id = obj.idPedido;
        if (id == '')
            return;
        document.form.idCotacao.value = '<%=idCotacao%>';
        document.form.restore({idPedido: id, idCotacao: document.form.idCotacao.value});
    }

    function gerarImgAlteracao(row, obj) {
        row.cells[0].innerHTML = '<img style="cursor: pointer;" src="${ctx}/imagens/btnAlterarRegistro.gif" />';
    }

    function gravar() {
        if (document.form.situacao.value == '<%=SituacaoPedidoCompra.Entregue.name()%>') {
            if (!confirm(i18n_message("pedidoCompra.mensagemConfirmacao")))
                return;
        }

        document.form.idCotacao.value = '<%=idCotacao%>';
        var itens = HTMLUtils.getObjectsByTableId('tblItensPedido');
        document.form.itens_serialize.value = ObjectUtils.serializeObjects(itens);
        document.form.save();
    }

    function excluir() {
         if (confirm(i18n_message("citcorpore.comum.deleta"))) {
             document.form.fireEvent("delete");
         }
    }

    function LOOKUP_PRODUTO_select(id, desc){
        document.getElementById('item#idProduto').value = id;
        document.getElementById('codigoProduto').value = desc.split("-")[0];
        document.getElementById('nomeProduto').value = desc.split("-")[1];
        document.getElementById('item#descricaoItem').value = desc.split("-")[1];
        $("#POPUP_PRODUTO").dialog("close");
    }

    $(function() {
        $("#POPUP_ITEM_PEDIDO").dialog({
            autoOpen : false,
            top: 0,
            width : 1024,
            height : 350,
            modal : true
        });
        $("#POPUP_PRODUTO").dialog({
            autoOpen : false,
            top: 0,
            width : 890,
            height : 500,
            modal : true
        });
    });

    $(function() {
        $("#nomeProduto").click(function() {
            if (document.formItemPedido.selecionaItem.value == 'S') {
                $("#POPUP_PRODUTO").dialog("open");
                document.formPesquisaProduto.pesqLockupLOOKUP_PRODUTO_nomeProduto.focus();
            }
        });
    });

    $(function() {
        $("#codigoProduto").click(function() {
            if (document.formItemPedido.selecionaItem.value == 'S') {
                $("#POPUP_PRODUTO").dialog("open");
                document.formPesquisaProduto.pesqLockupLOOKUP_PRODUTO_codigoProduto.focus();
            }
        });
    });


    function adicionarItem() {
        if (StringUtils.isBlank(document.form.idFornecedor.value) || parseInt(document.form.idFornecedor.value) == 0){
            alert(i18n_message("fornecedor")+" "+i18n_message("citcorpore.comum.naoInformado"));
            document.form.idFornecedor.focus();
            return false;
        }
        document.formItemPedido.clear();
        document.getElementById('item#index').value = '0';
        document.getElementById('btnExcluirItem').style.display = "none";
        document.formItemPedido.selecionaItem.value = 'N';
        document.form.idColetaPreco.value = '';
        document.form.fireEvent("preparaTelaItemPedido");
    }

    function carregarItens() {
        if (StringUtils.isBlank(document.form.idFornecedor.value) || parseInt(document.form.idFornecedor.value) == 0){
            alert(i18n_message("fornecedor")+" "+i18n_message("citcorpore.comum.naoInformado"));
            document.form.idFornecedor.focus();
            return false;
        }
        document.form.fireEvent("carregaItens");
    }

    function excluirItem(linha) {
        if (linha > 0 && confirm(i18n_message("citcorpore.comum.confirmaExclusao"))) {
            HTMLUtils.deleteRow('tblItensPedido', linha);
            $("#POPUP_ITEM_PEDIDO").dialog("close");
        }
    }

    function recuperaValoresColetaPreco() {
        document.form.idColetaPreco.value = document.getElementById('item#idColetaPreco').value;
        document.form.idItemPedido.value = document.getElementById('item#idItemPedido').value;
        document.form.quantidade.value = document.getElementById('item#quantidade').value;
        document.form.fireEvent("recuperaValoresColetaPreco");
    }

    function exibeValoresColetaPreco(serialize_item) {
        var item = ObjectUtils.deserializeObject(serialize_item);
        document.getElementById('item#quantidade').value = NumberUtil.format(NumberUtil.toDouble(item.quantidade), 2, ",", ".");
        document.getElementById('item#valorTotal').value = NumberUtil.format(NumberUtil.toDouble(item.valorTotal), 2, ",", ".");
        document.getElementById('item#valorDesconto').value = NumberUtil.format(NumberUtil.toDouble(item.valorDesconto), 2, ",", ".");
        document.getElementById('item#valorFrete').value = NumberUtil.format(NumberUtil.toDouble(item.valorFrete), 2, ",", ".");

        if (StringUtils.isBlank(item.idProduto) || parseInt(item.idProduto) == 0){
            document.getElementById('item#descricaoItem').value = '';
            document.getElementById('item#idProduto').value = '';
            document.getElementById('codigoProduto').value = '';
            document.getElementById('nomeProduto').value = '';

            document.formItemPedido.selecionaItem.value = 'S';
        }else{
            document.getElementById('item#descricaoItem').value = item.descricaoItem;
            document.getElementById('item#idProduto').value = item.idProduto;
            document.getElementById('codigoProduto').value = item.codigoProduto;
            document.getElementById('nomeProduto').value = item.descricaoItem;

            document.formItemPedido.selecionaItem.value = 'N';
        }
    }

    validarItemPedido = function() {
        var qtde = document.getElementById('item#quantidade')
        if (StringUtils.isBlank(qtde.value) || parseInt(qtde.value) == 0){
            alert(i18n_message("itemPedido.quantidade")+" "+i18n_message("citcorpore.comum.naoInformado"));
            qtde.focus();
            return false;
        }
        if (StringUtils.isBlank(document.getElementById('item#idProduto').value)){
            alert(i18n_message("itemPedidoCompra.produto")+" "+i18n_message("citcorpore.comum.naoInformado"));
            document.getElementById('item#idProduto').focus();
            return false;
        }
        return true;
    }

    atualizarItem = function(){
        if (!validarItemPedido())
            return;

        var indice = parseInt(document.getElementById('item#index').value);
        if (indice == 0){
            var obj = new CIT_ItemPedidoCompraDTO();
            HTMLUtils.addRow('tblItensPedido', document.formItemPedido, 'item', obj,
                    ["","descricaoItem","quantidade","valorTotal"], null, '', [gerarImgAlterar], editarItem, null, false);
        }else{
            var obj = HTMLUtils.getObjectByTableIndex('tblItensPedido', indice);
            HTMLUtils.updateRow('tblItensPedido', document.formItemPedido, 'item', obj,
                    ["","descricaoItem","quantidade","valorTotal"], null,'', [gerarImgAlterar], editarItem, null, indice);
        }
        atualizaValorFrete();
        $("#POPUP_ITEM_PEDIDO").dialog("close");
    };

    function atualizaValorFrete() {
        var vlrFrete = parseFloat(0);
        var itens = HTMLUtils.getObjectsByTableId('tblItensPedido');
        if (itens != null) {
            for ( var i = 0; i < itens.length; i++) {
                var item = itens[i];
                if (item.valorFrete != null)
                    vlrFrete = parseFloat(vlrFrete) + parseFloat(item.valorFrete);
			}
        }
        eval('document.form.valorFrete.value = "' + NumberUtil.format(NumberUtil.toDouble(""+vlrFrete), 2, ",", ".")  + '"');
    }

    function editarItem(row, obj) {
        document.formItemPedido.clear();
        HTMLUtils.setValues(document.formItemPedido,'item',obj);
        document.getElementById('item#index').value = row.rowIndex;
        document.getElementById('btnExcluirItem').style.display = "block";
        document.getElementById('nomeProduto').value = obj.descricaoItem;
        document.form.idColetaPreco.value = obj.idColetaPreco;
        document.form.fireEvent("preparaTelaItemPedido");
    }

    function gerarImgAlterar(row, obj){
        row.cells[0].innerHTML = '<img src="${ctx}/imagens/btnAlterarRegistro.gif" style="cursor: pointer;"/>';
    };

    function verificaSituacao(situacao) {
        if (situacao.trim() == '<%=SituacaoPedidoCompra.Entregue.name()%>') {
            //document.getElementById('divDataEntrega').style.display = "block";
        }else{
            document.form.dataEntrega.value = '';
            //document.getElementById('divDataEntrega').style.display = "none";
        }
    }

</script>
<style>

    .linhaSubtituloGrid
    {
        color           :#000000;
        background-color: #d3d3d3;
        BORDER-RIGHT: thin outset;
        BORDER-TOP: thin outset;
        BORDER-LEFT: thin outset;
        BORDER-BOTTOM: thin outset;
        FONT-WEIGHT: bold;
        padding: 5px 0px 5px 5px;

    }
    .linhaGrid{
        border: 1px solid black;
        background-color:  #F2F2F2;
        vertical-align: middle;
    }
        .table {
            border-left:1px solid #ddd;
            width: 100%;
        }

        .table th {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
        }

        .table td {
            border:1px solid #ddd;
            padding:4px 10px;
            border-top:none;
            border-left:none;
        }

</style>
<style>
    div#main_container {
        margin: 10px 10px 10px 10px;
        width: 100%;
    }
</style>
</head>
<body>
<div class="box grid_16 tabs">
    <form name='form'
        action='${ctx}/pages/pedidoCompra/pedidoCompra'>
        <div class="columns clearfix">
            <input type='hidden' name='idPedido' id='idPedido'/>
            <input type='hidden' name='idColetaPreco' id='idColetaPreco'/>
            <input type='hidden' name='idItemPedido' id='idItemPedido'/>
            <input type='hidden' name='idEmpresa' id='idEmpresa'/>
            <input type='hidden' name='quantidade' id='quantidade'/>
            <input type='hidden' name='situacao' id='situacao'/>
            <input type='hidden' name='itens_serialize' id='itens_serialize'/>
            <input type='hidden' name='idCotacao' id='idCotacao' value='<%=idCotacao%>'/>

            <div class="section">
              <div class="col_40">
                     <h2 class="section">
                         <fmt:message key="cotacao.pedidosIncluidos" />
                     </h2>
                     <div class="col_100" style='height:190px;overflow:auto' >
                         <table id="tblPedidos" class="table">
                             <tr>
                                 <th width="1%"></th>
                                 <th width="15%"><fmt:message key="citcorpore.comum.numero" /></th>
                                 <th><fmt:message key="fornecedor" /></th>
                                 <th width="30%"><fmt:message key="citcorpore.comum.situacao" /></th>
                             </tr>
                         </table>
                     </div>
              </div>
              <div class="col_60">
                     <h2 class="section">
                         <fmt:message key="pedidoCompra" />
                     </h2>
                     <div class="col_100">
                                <div class="col_50">
                                     <fieldset >
                                         <label class="campoObrigatorio" style="cursor: pointer;"><fmt:message key="fornecedor" /></label>
                                         <div>
                                             <select name='idFornecedor' id='idFornecedor' class="Valid[Required] Description[fornecedor]"></select>
                                         </div>
                                     </fieldset>
                                 </div>
                                <div class="col_25">
                                    <fieldset >
                                        <label><fmt:message key="pedidoCompra.numero" />
                                        </label>
                                        <div>
                                             <input type='text' name="numeroPedido" id="numeroPedido" maxlength="25" />
                                        </div>
                                    </fieldset>
                                </div>
                                <div class="col_25">
                                    <fieldset >
                                        <label class="campoObrigatorio"><fmt:message key="pedidoCompra.dataPedido" />
                                        </label>
                                            <div>
                                                <input type='text' name="dataPedido" id="dataPedido" maxlength="10" size="10"
                                                    class="Valid[Required] Description[pedidoCompra.dataPedido] Format[Data] datepicker" />
                                            </div>
                                    </fieldset>
                                </div>
                     </div>
                     <div class="col_100">
                                <div class="col_33">
                                    <fieldset >
                                        <label class="campoObrigatorio"><fmt:message key="pedidoCompra.dataPrevistaEntrega" />
                                        </label>
                                            <div>
                                                <input type='text' name="dataPrevistaEntrega" id="dataPrevistaEntrega" maxlength="10" size="10"
                                                    class="Valid[Required] Description[pedidoCompra.dataPrevistaEntrega] Format[Data] datepicker" />
                                            </div>
                                    </fieldset>
                                </div>
                                <div class="col_33">
                                    <fieldset >
                                        <label><fmt:message key="pedidoCompra.identificadorEntrega" />
                                        </label>
                                        <div>
                                             <input type='text' name="identificacaoEntrega" id="identificacaoEntrega" maxlength="25" />
                                        </div>
                                    </fieldset>
                                </div>
                               <div class="col_33">
                                    <fieldset >
                                        <label ><fmt:message key="pedidoCompra.numeroNF" />
                                        </label>
                                        <div>
                                           <input id="numeroNF" type='text' name="numeroNF"  maxlength="25" />
                                        </div>
                                    </fieldset>
                                </div>
                     </div>
                     <div class="col_100">
                                <div class="col_80">
                                     <fieldset >
                                         <label class="campoObrigatorio" style="cursor: pointer;"><fmt:message key="requisicaoProduto.enderecoEntrega" /></label>
                                         <div>
                                             <select name='idEnderecoEntrega' class="Valid[Required] Description[requisicaoProduto.enderecoEntrega]"></select>
                                         </div>
                                     </fieldset>
                                </div>
                                <div class="col_20">
                                    <fieldset >
                                        <label ><fmt:message key="pedidoCompra.valorFrete" />
                                        </label>
                                        <div>
                                           <input id="valorFrete" type='text' name="valorFrete" class="Format[Moeda]" />
                                        </div>
                                    </fieldset>
                                </div>
                     </div>
                     <div class="col_100">
                         <fieldset>
                             <label><fmt:message key="citcorpore.comum.observacoes" />
                             </label>
                             <div>
                                 <textarea name="observacoes" id="observacoes" cols='200' rows='4' ></textarea>
                             </div>
                         </fieldset>
                     </div>
                     <div class="col_100">
                         <div class="col_60">
                             <h2 class="section">
                                 <fmt:message key="pedidoCompra.itens" />
                             </h2>
                         </div>
                         <div id="divCarregarItens" class="col_20" style='margin-top:10px'>
                             <label  style="cursor: pointer;" onclick='carregarItens();'>
                                 <img  src="${ctx}/imagens/list.png" /><span><b><fmt:message key="pedidoCompra.carregarItens" /></b></span>
                             </label>
                         </div>
                         <div id="divAdicionarItem" class="col_20" style='margin-top:10px'>
                             <label  style="cursor: pointer;" onclick='adicionarItem();'>
                                 <img  src="${ctx}/imagens/add.png" /><span><b><fmt:message key="requisicaoProduto.adicionarItem" /></b></span>
                             </label>
                         </div>
                     </div>
                     <div class="col_100" class="col_100" style='height:150px;overflow:auto;'>
                         <table id="tblItensPedido" class="table">
                             <tr>
                                 <th width="1%">&nbsp;</th>
                                 <th ><fmt:message key="itemRequisicaoProduto.descricao" /></th>
                                 <th width="10%"><fmt:message key="itemRequisicaoProduto.quantidade" /></th>
                                 <th width="10%"><fmt:message key="itemPedidoCompra.valorTotal" /></th>
                             </tr>
                         </table>
                     </div>
                     <div class="col_100">
                          <fieldset>
                           <label ><h2><fmt:message key="citcorpore.comum.anexos"/></h2></label>
                               <cit:uploadControl  style="height:10%;width:100%; border-bottom:1px solid #DDDDDD ; border-top:1px solid #DDDDDD "  title="<fmt:message key='citcorpore.comum.anexos' />" id="uploadAnexos" form="document.form" action="/pages/upload/upload.load" disabled="false"/>
                          </fieldset>
                     </div>
                     <div class="col_100">
                           <button type='button' name='btnGravar' id='btnGravar' class="light" style='margin-top: 10px !important'
                               onclick='gravar();'>
                               <img
                                   src="${ctx}/template_new/images/icons/small/grey/create_write.png">
                               <span><fmt:message key="citcorpore.comum.confirmar" />
                           </span>
                           </button>
                           <button type='button' name='btnLimpar' class="light" style='margin-top: 10px !important'
                               onclick='limpar();'>
                               <img
                                   src="${ctx}/template_new/images/icons/small/grey/clear.png">
                               <span><fmt:message key="citcorpore.comum.limpar" />
                               </span>
                           </button>
                           <button type='button' name='btnExcluir' id='btnExcluir' class="light" style='margin-top: 15px !important'
                               onclick='excluir();'>
                               <img
                                   src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
                               <span><fmt:message key="citcorpore.comum.excluir" />
                               </span>
                           </button>
                     </div>
              </div>
              </div>
            </div>
        </div>
    </form>
</div>

<div id="POPUP_ITEM_PEDIDO" title="<fmt:message key="pedidoCompra.itens" />"  style="overflow: auto;">
    <form name='formItemPedido'>
        <input type='hidden' name='item#index'/>
        <input type='hidden' name='item#siglaUnidadeMedida'/>
        <input type='hidden' name='item#idProduto'/>
        <input type='hidden' name='item#idItemPedido'/>
        <input type='hidden' name='item#descricaoItem' id='item#descricaoItem'/>
        <input type='hidden' name='selecionaItem'/>

        <div class="col_100">
            <div class="col_75">
                <fieldset >
                    <label class="campoObrigatorio" style="cursor: pointer;"><fmt:message key="coletaPreco.item" /></label>
                    <div>
                        <select name='item#idColetaPreco' id='item#idColetaPreco' class="Valid[Required] Description[coletaPreco.item]" onchange="recuperaValoresColetaPreco()"></select>
                    </div>
                </fieldset>
            </div>
            <div class="col_25">
                <fieldset >
                    <label class="campoObrigatorio"><fmt:message key="itemPedidoCompra.quantidade" />
                    </label>
                    <div>
                       <input id="item#quantidade" type='text' name="item#quantidade" onchange="recuperaValoresColetaPreco()"/>
                    </div>
                </fieldset>
            </div>
        </div>
        <div class="col_100">
            <div class="col_33">
                <fieldset >
                    <label ><fmt:message key="itemPedidoCompra.valorTotal" />
                    </label>
                    <div>
                       <input id="item#valorTotal" type='text' name="item#valorTotal" class="Format[Moeda] Description[itemPedidoCompra.valorTotal]"  readonly="readonly"/>
                    </div>
                </fieldset>
            </div>
            <div class="col_33">
                <fieldset >
                    <label ><fmt:message key="itemPedidoCompra.valorDesconto" />
                    </label>
                    <div>
                       <input id="item#valorDesconto" type='text' name="item#valorDesconto" class="Format[Moeda] Description[itemPedidoCompra.valorDesconto]"/>
                    </div>
                </fieldset>
            </div>
            <div class="col_33">
                <fieldset >
                    <label ><fmt:message key="pedidoCompra.valorFrete" />
                    </label>
                    <div>
                       <input id="item#valorFrete" type='text' name="item#valorFrete" class="Format[Moeda] escription[pedidoCompra.valorFrete]"  readonly="readonly"/>
                    </div>
                </fieldset>
            </div>
        </div>
       <!-- <div class="col_100">
            <div class="col_33">
                <fieldset >
                    <label ><fmt:message key="itemPedidoCompra.baseCalculoIcms" />
                    </label>
                    <div>
                       <input id="item#baseCalculoIcms" type='text' name="item#baseCalculoIcms" class="Format[Moeda] Valid[Required] Description[itemPedidoCompra.baseCalculoIcms]" />
                    </div>
                </fieldset>
            </div>
            <div class="col_33">
                <fieldset >
                    <label ><fmt:message key="itemPedidoCompra.aliquotaICMS" />
                    </label>
                    <div>
                       <input id="item#aliquotaIcms" type='text' name="item#aliquotaIcms" class="Format[Moeda] Valid[Required] Description[itemPedidoCompra.aliquotaICMS]" />
                    </div>
                </fieldset>
            </div>
            <div class="col_33">
                <fieldset >
                    <label ><fmt:message key="itemPedidoCompra.aliquotaIPI" />
                    </label>
                    <div>
                       <input id="item#aliquotaIpi" type='text' name="item#aliquotaIpi" class="Format[Moeda] Valid[Required] Description[itemPedidoCompra.aliquotaIPI]" />
                    </div>
                </fieldset>
            </div>
        </div> -->
        <div id='divSelecaoProduto' class="col_100">
             <div class="col_75" >
             </div>
             <div id="divAdicionarItem" class="col_25" style="width: 100%; float: center;" align="right" >
                <label  style="cursor: pointer;" onclick="popupProduto.abrePopup('produto', '')">
                    <img  id="addProduto" src="${ctx}/imagens/add.png" /><span><b><fmt:message key="produto.cadastrarProduto" /></b></span>
                </label>
             </div>
             <div class="col_25" >
                 <fieldset>
                     <label style="cursor: pointer; " >
                         <fmt:message key="itemRequisicaoProduto.codigoProduto" />
                     </label>
                     <div>
                         <input id="codigoProduto" type='text' name="item#codigoProduto" readonly="readonly"/>
                     </div>
                </fieldset>
             </div>
             <div class="col_75" >
                 <fieldset>
                     <label style="cursor: pointer; " >
                         <fmt:message key="itemRequisicaoProduto.descrProduto" />
                     </label>
                     <div>
                         <input id="nomeProduto" type='text' name="item#nomeProduto" readonly="readonly"/>
                     </div>
                </fieldset>
             </div>
        </div>
        <div id='divBotoesItem'>
            <div class="col_80">
               <label style="cursor: pointer;">&nbsp;</label>
            </div>
            <div class="col_20">
                <button type="button" onclick='atualizarItem()'>
                    <fmt:message key="citcorpore.comum.confirmar" />
                </button>
                <button type="button" style='display:none' id='btnExcluirItem' name='btnExcluirItem' onclick='excluirItem(document.getElementById("item#index").value)'>
                    <fmt:message key="itemRequisicaoProduto.excluir" />
                </button>
                <button type="button" onclick='$("#POPUP_ITEM_PEDIDO").dialog("close");'>
                    <fmt:message key="citcorpore.comum.fechar" />
                </button>
            </div>
        </div>
    </form>
    <div id="popupCadastroRapido">
        <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="99%" height="99%">
        </iframe>
    </div>
</div>

    <div id="POPUP_PRODUTO" title="<fmt:message key="citcorpore.comum.pesquisaproduto" />">
        <div class="box grid_16 tabs">
            <div class="toggle_container">
                <div id="tabs-2" class="block">
                    <div class="section">
                        <form name='formPesquisaProduto' style="width: 800px">
                            <cit:findField formName='formPesquisaProduto'
                                lockupName='LOOKUP_PRODUTO' id='LOOKUP_PRODUTO' top='0'
                                left='0' len='200' heigth='200' javascriptCode='true'
                                htmlCode='true' />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>