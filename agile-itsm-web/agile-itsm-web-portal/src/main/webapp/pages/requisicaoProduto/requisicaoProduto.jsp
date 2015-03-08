<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ProdutoDTO"%>
<%@page import="java.util.Collection"%>
<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
    <%
        String id = request.getParameter("id");

        Collection<ProdutoDTO> colProdutos = (Collection<ProdutoDTO>)request.getAttribute("colProdutos");
    %>
	<%@include file="/include/header.jsp"%>

    <title><fmt:message key="citcorpore.comum.title"/></title>
    <%@include file="/include/menu/menuConfig.jsp" %>

    <%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
    <script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
    <script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
    <script type="text/javascript" src="${ctx}/js/boxover.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/RequisicaoProdutoDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/ItemRequisicaoProdutoDTO.js"></script>
    <link href="${ctx}/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />


    <style type="text/css">
    .tip_conteudo{
		    height: auto;
		    position: relative;
			float:left;
			padding-left:0.5em;
		    width: 500px;
			left: -23px;
		}
		.tip_conteudo_legenda{
			background-color: #F3F6F6;
		    border: 1px solid #D1D1D1;
		    border-radius: 5px 5px 5px 5px;
		    box-shadow: 0 0 8px -5px #000000;
		    display: block;
			font-family:Arial, Helvetica, sans-serif;
			font-size:12px;
			margin-left:1.4em;
			padding:0px 10px 0px 10px;
		}
		.tip_conteudo .seta {
		    background: url('<%=request.getContextPath()%>/imagens/seta.png') no-repeat scroll transparent;
		    display: block;
		    height: 37px;
		    position: absolute;
		    top: 0px;
		    width: 23px;
		    z-index: 45;
		}
		.tip_conteudo .opcoes {
		    background-color: #F3F6F6;
		    border: 1px solid #D1D1D1;
		    border-radius: 5px 5px 5px 5px;
		    box-shadow: 0 0 8px -5px #000000;
		    display: block;
		    width: 361px;
		}
		.tip_titulo{
			border-bottom: 1px solid #D8D8D8;
			font-size:14px;
			font-weight:bold;
			color:#FF0000;
			padding: 5px 0px 5px 0px;
		}
        .destacado {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
            font-size: 14px;
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

        .table1 {
        }

        .table1 th {
            border:1px solid #ddd;
            padding:4px 10px;
            background:#eee;
        }

        .table1 td {
        }
td{
padding-bottom: 0 !important;
}
#btnlimparTelaItem{
	margin-top: 3px!important;
}
    </style>

    <script>
        function desabilitarTela() {
            var f = document.form;
            for(i=0;i<f.length;i++){
                var el =  f.elements[i];
                if (el.type != 'hidden') {
                    if (el.disabled != null && el.disabled != undefined) {
                        el.disabled = 'disabled';
                    }
                }
            }
            document.getElementById('divAdicionarItem').style.display = 'none';
        }
        addEvent(window, "load", load, false);
        function load(){
            document.form.afterLoad = function () {
                if (document.form.editar.value != '' && document.form.editar.value != 'S')
                    desabilitarTela();
                document.form.fireEvent('montaHierarquiaCategoria');
                parent.escondeJanelaAguarde();
            }
        }

        $(function() {
            $("#POPUP_ITEM_REQUISICAO").dialog({
                autoOpen : false,
                width : 580,
                height : 520,
                modal : true
            });
            redimensionarTamhanho("#POPUP_ITEM_REQUISICAO", "XG")
        });

        function adicionarItem() {
            document.getElementById('descricaoItem').disabled = false;
            document.formItemRequisicao.clear();
            carregarProdutos('');
            document.getElementById('itemIndex').value = '0';
            document.getElementById('divValidacao').style.display = 'none';
            $("#POPUP_ITEM_REQUISICAO").dialog("open");
        }

        validarItemRequisicao = function() {
            var qtde = document.getElementById('item#quantidade');
            if (StringUtils.isBlank(qtde.value) || parseInt(qtde.value) == 0){
                alert(i18n_message("itemRequisicaoProduto.quantidade")+" "+i18n_message("citcorpore.comum.naoInformado"));
                qtde.focus();
                return false;
            }
            if (document.form.tipoIdentificacaoItem.value == 'S') {
                if (StringUtils.isBlank(document.getElementById('item#idProduto').value)){
                    alert(i18n_message("itemRequisicaoProduto.produto")+" "+i18n_message("citcorpore.comum.naoInformado"));
                    document.getElementById('descricaoItem').focus();
                    return false;
                }
                if (StringUtils.isBlank(document.getElementById('item#idUnidadeMedida').value)){
                    alert(i18n_message("unidadeMedida.naoCadastrada"));
                    document.getElementById('descricaoItem').focus();
                    return false;
                }
            }else{
                /*if (StringUtils.isBlank(document.getElementById('item#idCategoriaProduto').value)){
                    alert(i18n_message("itemRequisicaoProduto.categoria")+" "+i18n_message("citcorpore.comum.naoInformado"));
                    document.getElementById('item#idCategoriaProduto').focus();
                    return false;
                }  */
                if (StringUtils.isBlank(document.getElementById('descricaoItem').value)){
                    alert(i18n_message("itemRequisicaoProduto.descricao")+" "+i18n_message("citcorpore.comum.naoInformado"));
                    document.getElementById('descricaoItem').focus();
                    return false;
                }
                if (StringUtils.isBlank(document.getElementById('item#especificacoes').value)){
                    alert(i18n_message("itemRequisicaoProduto.especificacoes")+" "+i18n_message("citcorpore.comum.naoInformado"));
                    document.getElementById('item#especificacoes').focus();
                    return false;
                }
                if (StringUtils.isBlank(document.getElementById('item#idUnidadeMedida').value)){
                    alert(i18n_message("itemRequisicaoProduto.unidadeMedida")+" "+i18n_message("citcorpore.comum.naoInformado"));
                    document.getElementById('item#idUnidadeMedida').focus();
                    return false;
                }
                /*if (StringUtils.isBlank(document.getElementById('item#precoAproximado').value) || parseFloat(document.getElementById('item#precoAproximado').value) == 0){
                    alert(i18n_message("itemRequisicaoProduto.precoAproximado")+" "+i18n_message("citcorpore.comum.naoInformado"));
                    document.getElementById('item#precoAproximado').focus();
                    return false;
                }*/
            }
            return true;
        }

        atualizarItem = function(){
            if (document.form.tipoIdentificacaoItem.value == 'S') {
            	posicionarCategoria(document.form.idCategoriaProduto.value);
            	document.getElementById('descricaoItem').value = document.form.identificacao.value;
                document.getElementById('item#marcaPreferencial').value = document.form.nomeMarca.value;
                document.getElementById('item#idUnidadeMedida').value = document.form.idUnidadeMedida.value;
                document.getElementById('item#especificacoes').value = document.form.detalhes.value;
                document.getElementById('item#precoAproximado').value = document.form.precoMercado.value;
            }
            if (!validarItemRequisicao())
                return;

            var indice = parseInt(document.getElementById('itemIndex').value);
            var idUnidade = document.getElementById('item#idUnidadeMedida');
            document.getElementById('item#siglaUnidadeMedida').value = idUnidade[idUnidade.selectedIndex].text;
            if (indice == 0){
                document.getElementById('item#descrSituacao').value = i18n_message("itemRequisicaoProduto.solicitado");
                var obj = new CIT_ItemRequisicaoProdutoDTO();
                HTMLUtils.addRow('tblItensRequisicao', document.formItemRequisicao, 'item', obj,
                        ["","","quantidade","descrSituacao"], null, '', [gerarImgAlterar], null, null, false);
            }else{
                var obj = HTMLUtils.getObjectByTableIndex('tblItensRequisicao', indice);
                HTMLUtils.updateRow('tblItensRequisicao', document.formItemRequisicao, 'item', obj,
                        ["","","quantidade","descrSituacao"], null,'', [gerarImgAlterar], null, null, indice);
            }

            document.form.idUnidadeMedida.value = "";
            document.form.nomeMarca.value = "";

            $("#POPUP_ITEM_REQUISICAO").dialog("close");
        };

        function editarItem(indice) {
            var obj = HTMLUtils.getObjectByTableIndex("tblItensRequisicao", indice);
            if (document.form.editar.value == 'N' || obj == null)
                return;

            posicionarCategoria(obj.idCategoriaProduto);
            document.formItemRequisicao.clear();
            HTMLUtils.setValues(document.formItemRequisicao,'item',obj);
            document.getElementById('itemIndex').value = indice;
            document.getElementById('btnExcluirItem').style.display = "block";
            document.form.idProduto.value = obj.idProduto;
            document.form.tipoIdentificacaoItem.value = obj.tipoIdentificacao;
            if (document.form.tipoIdentificacaoItem.value != 'D' && document.form.tipoIdentificacaoItem.value != 'S') {
	            if (obj.idProduto == '')
	                document.form.tipoIdentificacaoItem.value = 'D';
	            else
	                document.form.tipoIdentificacaoItem.value = 'S';
        	}
            configuraJustificativa(obj);
            document.form.fireEvent('preparaTelaItemRequisicao');
        }

        function validar() {
            return document.form.validate();
        }

        function getObjetoSerializado() {
            var obj = new CIT_RequisicaoProdutoDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            obj.itensRequisicao_serialize = ObjectUtils.serializeObjects(itensRequisicao);
            return ObjectUtils.serializeObject(obj);
        }

        function gerarImgAlterar(row, obj){
        	var title = 'title="header=[] body=['+
        	'<div class=\'tip_conteudo\'>'+
        	'	<div class=\'seta\'></div>'+
        	'	<div class=\'tip_conteudo_legenda\'>'+
        	'		<div class=\'tip_titulo\'>'+obj.descricaoItem+'</div>';
        	title += descricaoFmtHtml(obj);
        	title += '</div>'+
        	'</div>'+
        	']"';

            row.cells[0].innerHTML = '<img  src="${ctx}/imagens/edit.png" style="cursor: pointer;" onclick="editarItem('+row.rowIndex+')" title="'+i18n_message("dinamicview.editar")+'" >';
            row.cells[1].innerHTML = '<a '+title+'>'+obj.descricaoItem+'</a>';
        };

        function excluirItem(linha) {
            if (linha > 0 && confirm(i18n_message("citcorpore.comum.confirmaExclusao"))) {
                HTMLUtils.deleteRow('tblItensRequisicao', linha);
                $("#POPUP_ITEM_REQUISICAO").dialog("close");
            }
        }

        function posicionarCategoria(id) {
        	HTMLUtils.setValue('item#idCategoriaProduto', id);
        	document.form.idCategoriaProduto.value = id;
        }

        function limparTelaItem() {
            carregarProdutos(document.getElementById('item#idCategoriaProduto').options[document.getElementById('item#idCategoriaProduto').selectedIndex].value);
        }

        function carregarProdutos(idCategoria) {
        	if (document.form.tipoIdentificacaoItem.value == 'D')
        		return;
            document.formItemRequisicao.clear();
            document.getElementById('divValidacao').style.display = 'none';
            posicionarCategoria(idCategoria);
            document.form.idProduto.value = '';
            document.getElementById('descricaoItem').disabled = false;
            document.getElementById('divBotoesItem').style.display = 'block';
            document.getElementById('divDigitacao').style.display = 'block';
            document.getElementById('divProduto').style.display = 'none';
            document.getElementById('divImagemProduto').innerHTML = '';
            document.getElementById('divDetalhesProduto').innerHTML = '';
            document.getElementById('divAcessorios').innerHTML = '';
            document.getElementById('btnExcluirItem').style.display = "none";
            document.getElementById('descricaoItem').focus();
            geraAutoCompleteProduto(idCategoria);
        }

        function verificaDigitacaoProduto(idCategoria) {
            if (document.getElementById('item#idProduto').value == '' && document.getElementById('descricaoItem').value != '') {
                prepararTelaDigitacaoProduto();
                document.getElementById('item#quantidade').focus();
            }
        }

        function exibirProduto(id) {
            document.form.idProduto.value = id;
            document.getElementById("item#idProduto").value = id;
            if (id != '') {
                document.form.tipoIdentificacaoItem.value = 'S';
                document.form.fireEvent('exibeProduto');
            }
        }

        function prepararTelaDigitacaoProduto() {
            document.form.idProduto.value = '';
            document.getElementById('divProduto').style.display = 'none';
            document.getElementById('divDigitacao').style.display = 'block';
            document.getElementById('descricaoItem').disabled = false;
            document.getElementById('item#idProduto').value = '';
            document.form.tipoIdentificacaoItem.value = 'D';
        }

        function geraAutoCompleteProduto(idCategoria){
            <%if (colProdutos == null) {%>
               return;
            <%}%>
            var availableTags = new Array();
            var iAux = 0;
            <%for (ProdutoDTO produtoDto: colProdutos) {%>
                 if (idCategoria == '' || (idCategoria != '' && parseInt(idCategoria) == <%=produtoDto.getIdCategoria().intValue()%>)){
                     availableTags[iAux] = {value: '<%=produtoDto.getIdProduto().intValue()%>', label: '<%=produtoDto.getIdentificacao()%>'};
                     iAux++;
                 }
            <%}%>
            $( "#descricaoItem" ).autocomplete({
                source: availableTags,
                select: function(event, ui) {
                    document.getElementById('descricaoItem').value = ui.item.label;
                    exibirProduto(ui.item.value);
                    document.getElementById('item#quantidade').focus();
                    return false;
                },
            });
        }

        function configuraJustificativa(obj) {
        	document.getElementById('divValidacao').style.display = 'none';
			if (obj.idParecerValidacao != '') {
                document.getElementById('divValidacao').style.display = 'block';
                document.getElementById('divJustificativaValidacao').style.display = 'none';
                var aprovado = document.formItemRequisicao['item#validado'];
                if (obj.validado == 'S') {
                    aprovado[0].checked = true;
                }else if (obj.validado == 'N') {
                    aprovado[1].checked = true;
                    document.getElementById('divJustificativaValidacao').style.display = 'block';
                }
            }
        }

        /**
		* Motivo: Redimensiona a popup em tamanho compativel com o tamanho da tela
		* Autor: flavio.santana
		* Data/Hora: 18/11/2013 15:35
		*/
		function redimensionarTamhanho(identificador, tipo_variacao){
			var h;
			var w;
			switch(tipo_variacao)
			{
			case "PEQUENO":
				w = parseInt($(window).width() * 0.25);
				h = parseInt($(window).height() * 0.35);
			  break;
			case "MEDIO":
				w = parseInt($(window).width() * 0.5);
				h = parseInt($(window).height() * 0.6);
			  break;
			case "GRANDE":
				w = parseInt($(window).width() * 0.75);
				h = parseInt($(window).height() * 0.85);
			  break;
			case "XG":
				w = parseInt($(window).width() * 0.95);
				h = parseInt($(window).height() * 0.95);
			  break;
			default:
				w = parseInt($(window).width() * 0.5);
				h = parseInt($(window).height() * 0.6);
			}

			$(identificador).dialog("option","top", 0);
			$(identificador).dialog("option","width", w);
			//$(identificador).dialog("option","height", h)
		}

		descricaoFmtHtml = function(obj) {
			var descricaoFmtHtml = "";
	    	if (!StringUtils.isBlank(obj.especificacoes))
	        	descricaoFmtHtml += "<p><b>Especificações:</b> "+obj.especificacoes+"</p>";
	    	descricaoFmtHtml += "<p><b>Unidade de medida:</b> "+obj.siglaUnidadeMedida+"</p>";
	    	if (!StringUtils.isBlank(obj.marcaPreferencial))
	        	descricaoFmtHtml += "<p><b>Marca preferencial:</b> "+obj.marcaPreferencial+"</p>";
	    	if (!StringUtils.isBlank(obj.precoAproximado))
	        	descricaoFmtHtml += "<p><b>Preço aproximado:</b> "+obj.precoAproximado+"</p>";
	    	if (!StringUtils.isBlank(obj.idProduto))
	        	descricaoFmtHtml += "<p><b>Produto:</b> "+obj.codigoProduto+" - "+obj.nomeProduto+"</p>";
	       	descricaoFmtHtml += "<p><b>Situação:</b> "+obj.descrSituacao+"</p>";

	       	if (!StringUtils.isBlank(obj.idJustificativaValidacao)) {
	       		if (!StringUtils.isBlank(obj.descrJustificativaValidacao)) {
	       	       	descricaoFmtHtml += "<p><b>Justificativa:</b> "+obj.descrJustificativaValidacao;
	       	       	if (!StringUtils.isBlank(obj.complemJustificativaValidacao))
	           	       	descricaoFmtHtml += " - "+obj.complemJustificativaValidacao;
	       	       	descricaoFmtHtml += "</p>";
	       		}
	       	}else if (!StringUtils.isBlank(obj.idJustificativaAutorizacao)) {
	   	       	descricaoFmtHtml += "<p><b>Justificativa:</b> "+obj.descrJustificativaAutorizacao;
	   	       	if (!StringUtils.isBlank(obj.complemJustificativaAutorizacao))
	       	       	descricaoFmtHtml += "<br>"+obj.complemJustificativaAutorizacao;
	   	       	descricaoFmtHtml += "</p>";
	       	}
	    	return descricaoFmtHtml;
		}
    </script>
</head>

<body style="overflow: hidden;">
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='${ctx}/pages/requisicaoProduto/requisicaoProduto'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
                                <input type='hidden' name='editar' id='editar' />
                                <input type='hidden' name='acao' id='acao'/>
                                <input type='hidden' name='idCategoriaProduto' id='idCategoriaProduto'/>
                                <input type='hidden' name='idProduto' id='idProdutoForm'/>
                                <input type='hidden' name='tipoIdentificacaoItem' id='tipoIdentificacaoItem'/>
                                <input type='hidden' name='identificacao' id='identificacaoItemForm'/>
                                <input type='hidden' name='nomeMarca' id='nomeMarca'/>
                                <input type='hidden' name='precoMercado' id='precoMercado'/>
                                <input type='hidden' name='idUnidadeMedida' id='idUnidadeMedida'/>
                                <input type='hidden' name='siglaUnidadeMedida' id='siglaUnidadeMedida'/>
                                <input type='hidden' name='detalhes' id='detalhes'/>
                                <input type='hidden' name='itemIndex'/>

                                <div class="col_100">
                                    <div class="col_50">
                                        <fieldset>
                                            <label class="campoObrigatorio"><fmt:message key="requisicaoProduto.finalidade" /></label>
                                            <div>
                                                <select name='finalidade' id='finalidade' class="Valid[Required] Description[requisicaoProduto.finalidade]"></select>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col_50">
                                         <fieldset>
                                             <label class="campoObrigatorio"><fmt:message key="centroResultado.custo" /></label>
                                             <div>
                                                 <select name='idCentroCusto' class="Valid[Required] Description[centroResultado.custo]"></select>
                                             </div>
                                         </fieldset>
                                    </div>
                                </div>
                                <div class="col_100">
                                    <div class="col_50">
                                         <fieldset>
                                             <label class="campoObrigatorio"><fmt:message key="requisicaoProduto.projeto" /></label>
                                             <div>
                                                 <select name='idProjeto' class="Valid[Required] Description[requisicaoProduto.projeto]"></select>
                                             </div>
                                         </fieldset>
                                    </div>
                                    <div class="col_50">
                                         <fieldset>
                                             <label class="campoObrigatorio"><fmt:message key="requisicaoProduto.enderecoEntrega" /></label>
                                             <div>
                                                 <select name='idEnderecoEntrega' class="Valid[Required] Description[requisicaoProduto.enderecoEntrega]"></select>
                                             </div>
                                         </fieldset>
                                    </div>
                                </div>

                                <div class="col_100">
                                    <div class="col_50">
                                        <h2 class="section">
                                            <fmt:message key="requisicaoProduto.itens" />
                                        </h2>
                                    </div>
                                    <div id="divAdicionarItem" class="col_50" style="width: 100%; float: center;" align="right" >
                                        <label  style="cursor: pointer;" onclick='adicionarItem();'>
                                            <img  src="${ctx}/imagens/add.png" /><span><b><fmt:message key="requisicaoProduto.adicionarItem" /></b></span>
                                        </label>
                                    </div>
                                </div>
                                <div class="col_100" style="overflow:auto; height:300px">
                                    <table id="tblItensRequisicao" class="table">
                                        <tr>
                                            <th width="1px">&nbsp;</th>
                                            <th ><fmt:message key="itemRequisicaoProduto.descricao" /></th>
                                            <th width="7%"><fmt:message key="itemRequisicaoProduto.quantidade" /></th>
                                            <th width="30%"><fmt:message key="citcorpore.comum.situacao" /></th>
                                        </tr>
                                    </table>
                                </div>
                        </form>
                    </div>
            </div>
        </div>

<div id="POPUP_ITEM_REQUISICAO" title="<fmt:message key="requisicaoProduto.itens" />"  style="overflow: auto;">
    <form name='formItemRequisicao'>
        <input type='hidden' name='item#idItemRequisicaoProduto'/>
        <input type='hidden' name='item#siglaUnidadeMedida'/>
        <input type='hidden' name='item#descrSituacao'/>
        <input type='hidden' name='item#idProduto'/>

		<div class="columns clearfix">
	        <div class="col_100">
				<fieldset>
                	<label><fmt:message key="itemRequisicaoProduto.categoria" /></label>
                    <div>
                    	<select id='item#idCategoriaProduto' name='item#idCategoriaProduto'
                    			onchange="carregarProdutos(document.getElementById('item#idCategoriaProduto').options[document.getElementById('item#idCategoriaProduto').selectedIndex].value)"></select>
 					</div>
				</fieldset>
	        </div>
	        <div class="col_100">
	            <div class="col_70">
	                 <fieldset>
	                      <label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.produto" /></label>
	                     <div>
							<input type='text' name='item#descricaoItem' id='descricaoItem' maxlength='120' onblur='verificaDigitacaoProduto("")'/>
	                      </div>
	                  </fieldset>
				</div>

	            <div class="col_15">
	                <fieldset>
	                    <label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.quantidade" /></label>
	                    <div>
	                        <input id="item#quantidade" type="text"  maxlength="15" name='item#quantidade' class="Format[Moeda]"/>
	                    </div>
	                </fieldset>
	            </div>
	            <div class="col_15">
	                     <label>&nbsp;</label>
							<button type="button" class="light" name='btnlimparTelaItem' onclick='this.form.reset();limparTelaItem();'>
		                     	<fmt:message key="citcorpore.ui.botao.rotulo.Limpar" />
			                </button>
				</div>
			</div>
            <div id='divProduto' style='display:none' class="col_100">
                <div class="col_25">
                    <fieldset>
                        <div id='divImagemProduto' style='padding:10px 10px;'>
                        </div>
                    </fieldset>
                </div>
                <div class="col_50">
                    <fieldset>
                        <div id='divDetalhesProduto' style='padding:10px 10px;'>
                        </div>
                    </fieldset>
                </div>
                 <div class="col_25">
                        <fieldset>
                            <div id='divAcessorios' style='padding:10px 10px;'>
                            </div>
                        </fieldset>
                 </div>
            </div>
            <div id='divDigitacao' class="col_100">
                  <div class="col_100">
                      <fieldset>
                          <label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.especificacoes" /></label>
                          <div>
                               <textarea id="item#especificacoes" name="item#especificacoes" cols='60'  ></textarea>
                          </div>
                      </fieldset>
                   </div>
                   <div class="col_100">
                       <div class="col_40">
                           <fieldset>
                               <label><fmt:message key="itemRequisicaoProduto.marcaPreferencial" /></label>
                               <div>
                                   <input id="item#marcaPreferencial"  type='text'  name="item#marcaPreferencial" maxlength="100" />
                               </div>
                           </fieldset>
                       </div>
                       <div class="col_20">
                           <fieldset>
                               <label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.unidadeMedida" /></label>
                               <div>
                                   <select id='item#idUnidadeMedida'  name='item#idUnidadeMedida' ></select>
                               </div>
                           </fieldset>
                       </div>
                       <div class="col_40">
                           <fieldset>
                               <label><fmt:message key="itemRequisicaoProduto.precoAproximado" /></label>
                               <div>
                                   <input id="item#precoAproximado" type="text"  maxlength="15" name='item#precoAproximado' class="Format[Moeda]"/>
                               </div>
                           </fieldset>
                       </div>
                   </div>
            </div>
	        <div id="divValidacao" class="col_100">
	                 <h2 class="section">
	                    <fmt:message key="itemRequisicaoProduto.validacao" />
	                 </h2>
	                <div class="col_100" >
	                    <fieldset >
	                        <input type='radio' name="item#validado" value="S" disabled="disabled"><fmt:message key="itemRequisicaoProduto.validado"/>
	                        <input type='radio' name="item#validado" value="N" disabled="disabled" ><fmt:message key="itemRequisicaoProduto.naoValidado"/>
	                    </fieldset>
	                </div>
	                <div id="divJustificativaValidacao" class="col_100" >
	                    <div class="col_50">
	                         <fieldset>
	                             <label><fmt:message key="itemRequisicaoProduto.justificativa" /></label>
	                             <div>
	                                 <select id='item#idJustificativaValidacao'  name='item#idJustificativaValidacao' disabled="disabled"></select>
	                             </div>
	                         </fieldset>
	                    </div>
	                    <div class="col_50">
	                        <fieldset>
	                            <label><fmt:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
	                            <div>
	                                 <textarea id="item#complemJustificativaValidacao" name="item#complemJustificativaValidacao" cols='60' rows='3' readonly="readonly"></textarea>
	                            </div>
	                        </fieldset>
	                    </div>
	                </div>
	        </div>
        </div>

         <div id='divBotoesItem'>
             <div class="col_40">
                <label>&nbsp;</label>
             </div>
             <div class="col_60">
                 <button type="button" class="light" onclick='this.form.reset();limparTelaItem();'>
                     <fmt:message key="citcorpore.comum.limpar" />
                 </button>
                 <button type="button" class="light" onclick='atualizarItem()'>
                     <fmt:message key="citcorpore.comum.confirmar" />
                 </button>
                 <button type="button" class="light" style='display:none' id='btnExcluirItem' name='btnExcluirItem' onclick='excluirItem(document.getElementById("itemIndex").value)'>
                     <fmt:message key="itemRequisicaoProduto.excluir" />
                 </button>
                 <button type="button" class="light" onclick='$("#POPUP_ITEM_REQUISICAO").dialog("close");'>
                     <fmt:message key="citcorpore.comum.fechar" />
                 </button>
             </div>
         </div>
    </form>
</div>

<div id="POPUP_INFO_PRODUTOS" title="Produtos" style="overflow: hidden;">
        <div id='divInfoProdutos' style="overflow: auto; font-size: 14px; width: 100%; height: 100%">
            <fmt:message key="itemRequisicaoProduto.filtroselecionado" />
        </div>
</div>

</body>

</html>
