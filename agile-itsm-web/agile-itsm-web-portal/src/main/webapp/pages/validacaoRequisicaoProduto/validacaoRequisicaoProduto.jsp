<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
    <%
        String id = request.getParameter("id");
    %>
	<%@include file="/include/header.jsp" %>
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
         div#main_container {
            margin: 0px 0px 0px 0px;
        }

        .container_16
        {
            width: 100%;
            margin: 0px 0px 0px 0px;

            letter-spacing: -4px;
        }
    </style>

    <script>
	    addEvent(window, "load", load, false);
		var popupProduto;
	    function load() {
			popupProduto = new PopupManager(580, 680, "${ctx}/pages/");
			popupProduto.titulo = i18n_message("produto.cadastro");
            document.form.afterLoad = function () {
            	parent.escondeJanelaAguarde();
            }
        }
        function LOOKUP_PRODUTO_select(id, desc){
        	document.getElementById('item#idProduto').value = id;
            document.getElementById('codigoProduto').value = desc.split("-")[0];
        	document.getElementById('nomeProduto').value = desc.split("-")[1];
            $("#POPUP_PRODUTO").dialog("close");
        }
        $(function() {
            $("#POPUP_ITEM_REQUISICAO").dialog({
                autoOpen : false,
                width : 580,
                height : 650,
                top: 0,
                modal : true
            });
            $("#POPUP_PRODUTO").dialog({
                autoOpen : false,
                width : 580,
                height : 600,
                modal : true
            });
            $("#POPUP_JUSTIFICATIVA").dialog({
                autoOpen : false,
                width : 580,
                height : 300,
                modal : true
            });
            $("#POPUP_APROVADORES").dialog({
                autoOpen : false,
                width : 600,
                height : 250,
                modal : true
            });
            $("#nomeProduto").click(function() {
                if (document.getElementById("item#tipoIdentificacao").value == 'D') {
                    $("#POPUP_PRODUTO").dialog("open");
                    document.formPesquisaProduto.pesqLockupLOOKUP_PRODUTO_nomeProduto.focus();
                }
            });
            $("#codigoProduto").click(function() {
                if (document.getElementById("item#tipoIdentificacao").value == 'D') {
                    $("#POPUP_PRODUTO").dialog("open");
                    document.formPesquisaProduto.pesqLockupLOOKUP_PRODUTO_codigoProduto.focus();
                }
            });
            redimensionarTamhanho("#POPUP_ITEM_REQUISICAO", "XG")
        });

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

			$(identificador).dialog("option","width", w);
			//$(identificador).dialog("option","height", h)
		}

        atualizarItem = function(){
            if (!document.formItemRequisicao.validate())
                return;

            var indice = parseInt(document.getElementById('item#index').value);
            if (indice == 0)
                return;

            var idUnidade = document.getElementById('item#idUnidadeMedida');
            document.getElementById('item#siglaUnidadeMedida').value = idUnidade[idUnidade.selectedIndex].text;
            var obj = HTMLUtils.getObjectByTableIndex('tblItensRequisicao', indice);
            HTMLUtils.setValuesObjectByGroupName(document.formItemRequisicao, 'item', obj);
            var tipoAtendimento = document.formItemRequisicao['item#tipoAtendimento'];
            var aprovado = document.formItemRequisicao['item#validado'];
            if (aprovado[0].checked) {
                obj.validado = 'S';
                obj.descrSituacao = i18n_message("itemRequisicaoProduto.validado");
                tipoAtendimento[0].checked = true;
            }else if (aprovado[1].checked){
                obj.validado = 'N';
                obj.descrSituacao = i18n_message("itemRequisicaoProduto.naoValidado");
                if (obj.tipoIdentificacao == 'D') {
	                obj.idProduto = '';
	                obj.nomeProduto = '';
	                obj.codigoProduto = '';
                }
            }
            if (tipoAtendimento[0].checked) {
                obj.tipoAtendimento = 'C';
                obj.descrTipoAtendimento = i18n_message("itemRequisicaoProduto.compra");
            }else if (tipoAtendimento[1].checked) {
                obj.tipoAtendimento = 'E';
                obj.descrTipoAtendimento = i18n_message("itemRequisicaoProduto.estoque");
            }
            if (obj.idCategoriaProduto == '' && document.form.idCategoriaProduto.value != '')
            	obj.idCategoriaProduto = document.form.idCategoriaProduto.value;
            HTMLUtils.updateRow('tblItensRequisicao', document.formItemRequisicao, 'item', obj,
                    ["","","","quantidade","descrSituacao"], null,'', [gerarImg], null, null, indice, true);
            $("#POPUP_ITEM_REQUISICAO").dialog("close");
        };

        function gerarImg (row, obj){
        	var title = 'title="header=[] body=['+
        	'<div class=\'tip_conteudo\'>'+
        	'	<div class=\'seta\'></div>'+
        	'	<div class=\'tip_conteudo_legenda\'>'+
        	'		<div class=\'tip_titulo\'>'+obj.descricaoItem+'</div>';
        	title += descricaoFmtHtml(obj);
        	title += '</div>'+
        	'</div>'+
        	']"';

            obj.selecionado = 'N';
            row.cells[0].innerHTML = "&nbsp;<input type='checkbox' name='chkSel_"+obj.idItemRequisicaoProduto+"' id='chkSel_"+obj.idItemRequisicaoProduto+"' onclick='marcarDesmarcar(this,"+row.rowIndex+",\"tblItensRequisicao\")' />";
            row.cells[0].align = "center";
            row.cells[1].innerHTML = '<img  src="${ctx}/imagens/edit.png" style="cursor: pointer;" onclick="editarItem('+row.rowIndex+')" title="'+i18n_message("dinamicview.editar")+'" >';
            row.cells[2].innerHTML = '<a '+title+'>'+obj.descricaoItem+'</a>';
        };

        function posicionarCategoria(id) {
        	HTMLUtils.setValue('item#idCategoriaProduto', id);
        	document.form.idCategoriaProduto.value = id;
        }

        function editarItem(indice) {
            var obj = HTMLUtils.getObjectByTableIndex("tblItensRequisicao", indice);

            if (document.form.editar.value == 'N')
                return;

            document.formItemRequisicao.clear();
            HTMLUtils.setValues(document.formItemRequisicao,'item',obj);
            posicionarCategoria(obj.idCategoriaProduto);
            document.getElementById('item#index').value = indice;
            var aprovado = document.formItemRequisicao['item#validado'];
            if (obj.validado == 'S')
                aprovado[0].checked = true;
            else if (obj.validado == 'N')
                aprovado[1].checked = true;

            var tipoAtendimento = document.formItemRequisicao['item#tipoAtendimento'];
            if (obj.tipoAtendimento == 'C')
                tipoAtendimento[0].checked = true;
            else if (obj.tipoAtendimento == 'E')
                tipoAtendimento[1].checked = true;

            configuraJustificativa(obj.validado);
            document.getElementById('divSelecaoProduto').style.display = 'none';
            document.form.idCategoriaProduto.value = obj.idCategoriaProduto;
            document.form.tipoIdentificacaoItem.value = obj.tipoIdentificacao;
            document.form.idProduto.value = obj.idProduto;
            document.getElementById('item#idCategoriaProduto').disabled = true;
            document.form.fireEvent('preparaTelaItemRequisicao');
        }

        function prepararTelaDigitacaoProduto() {
            document.getElementById('divSelecaoProduto').style.display = 'block';
            document.getElementById('divProduto').style.display = 'none';
            document.getElementById('divDigitacao').style.display = 'block';
            document.form.tipoIdentificacaoItem.value = 'D';
            document.getElementById('item#idCategoriaProduto').disabled = false;
        }

        function validar() {
            document.getElementById('item#idCategoriaProduto').disabled = false;
            return document.form.validate();
        }

        function getObjetoSerializado() {
            var obj = new CIT_RequisicaoProdutoDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            obj.itensRequisicao_serialize = ObjectUtils.serializeObjects(itensRequisicao);
            return ObjectUtils.serializeObject(obj);
        }

        function configuraJustificativa(aprovado) {
            document.getElementById('divJustificativa').style.display = 'none';
            document.getElementById('divTipoAtendimento').style.display = 'none';
            if (aprovado == 'N')
            	document.getElementById('divJustificativa').style.display = 'block';
            //else if (aprovado == 'S')
            //    document.getElementById('divTipoAtendimento').style.display = 'block';
        }

        function marcarDesmarcar(chk, indice, tbl) {
            var obj = HTMLUtils.getObjectByTableIndex(tbl, indice);
            if (chk.checked)
                obj.selecionado = 'S';
            else
                obj.selecionado = 'N';
        }

        function marcarDesmarcarTodosItens(chk) {
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            if (itensRequisicao == null)
                return;
            for(i=0;i<itensRequisicao.length;i++){
                var obj = itensRequisicao[i];
                if (chk.checked)
                    obj.selecionado = 'S';
                else
                    obj.selecionado = 'N';
                document.getElementById('chkSel_'+obj.idItemRequisicaoProduto).checked = chk.checked;
            }
        }

		itensSelecionados = function() {
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            if (itensRequisicao == null)
                return false;

            var sel = 0;
            for(i=0;i<itensRequisicao.length;i++){
                var obj = itensRequisicao[i];
                if (obj.selecionado == 'S')
                    sel++;
            }
            return sel > 0;
		}

        validarItens = function() {
            if (!itensSelecionados()) {
                alert("Selecione pelo menos um item");
                return;
            }

            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            if (itensRequisicao == null)
                return;

            var processado = 0;
            for(var i=0;i<itensRequisicao.length;i++){
                var obj = itensRequisicao[i];
                if (obj.selecionado != 'S')
                    continue;

                obj.validado = 'S';
                obj.tipoAtendimento = 'C';
                obj.descrSituacao = i18n_message("itemRequisicaoProduto.validado");

                HTMLUtils.updateRow('tblItensRequisicao', null, 'item', obj,
                		["","","","quantidade","descrSituacao"], null,'', [gerarImg], null, null, i+1, true);
            }
            document.getElementById('chkSelTodosItens').checked = false;
        }

        inviabilizarItens = function() {
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            for(i=0;i<itensRequisicao.length;i++){
                var obj = itensRequisicao[i];
                if (obj.selecionado != 'S')
                    continue;

                obj.validado = 'N';
                obj.tipoAtendimento = 'C';
                obj.descrSituacao = i18n_message("itemRequisicaoProduto.naoValidado");
                if (obj.tipoIdentificacao == 'D') {
	                obj.idProduto = '';
	                obj.nomeProduto = '';
	                obj.codigoProduto = '';
                }
                obj.idJustificativaValidacao = document.getElementById("idJustificativaPopup").value;
                obj.complemJustificativaValidacao = document.getElementById("complemJustificativaPopup").value;

                HTMLUtils.updateRow('tblItensRequisicao', null, 'item', obj,
                		["","","","quantidade","descrSituacao"], null,'', [gerarImg], null, null, i+1, true);
            }
            document.getElementById('chkSelTodosItens').checked = false;
            $("#POPUP_JUSTIFICATIVA").dialog("close");
        }

        exibirJustificativa = function() {
            if (!itensSelecionados()) {
                alert("Selecione pelo menos um item");
                return;
            }

            document.getElementById("complemJustificativaPopup").value = '';
        	$("#POPUP_JUSTIFICATIVA").dialog("open");
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

<body>
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='${ctx}/pages/validacaoRequisicaoProduto/validacaoRequisicaoProduto'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
                                <input type='hidden' name='editar' id='editar' />
                                <input type='hidden' name='acao' id='acao'/>
                                <input type='hidden' name='idCategoriaProduto' id='idCategoriaProduto'/>
                                <input type='hidden' name='idProduto' id='idProdutoForm'/>
                                <input type='hidden' name='tipoIdentificacaoItem' id='tipoIdentificacaoItem'/>

                                <div class="col_100">
                                    <div class="col_50">
                                        <fieldset >
                                            <label class="campoObrigatorio"><fmt:message key="requisicaoProduto.finalidade" /></label>
                                            <div>
                                                <select name='finalidade' id='finalidade' class="Valid[Required] Description[requisicaoProduto.finalidade]"></select>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col_50">
                                         <fieldset >
                                             <label class="campoObrigatorio"><fmt:message key="centroResultado.custo" /></label>
                                             <div>
                                                 <select name='idCentroCusto' class="Valid[Required] Description[centroResultado.custo]"></select>
                                             </div>
                                         </fieldset>
                                    </div>
								</div>
                                <div class="col_100">
                                    <div class="col_40">
                                         <fieldset >
                                             <label class="campoObrigatorio"><fmt:message key="requisicaoProduto.projeto" /></label>
                                             <div>
                                                 <select name='idProjeto' class="Valid[Required] Description[requisicaoProduto.projeto]"></select>
                                             </div>
                                         </fieldset>
                                    </div>
                                    <div class="col_60">
                                         <fieldset >
                                             <label class="campoObrigatorio"><fmt:message key="requisicaoProduto.enderecoEntrega" /></label>
                                             <div>
                                                 <select name='idEnderecoEntrega' class="Valid[Required] Description[requisicaoProduto.enderecoEntrega]"></select>
                                             </div>
                                         </fieldset>
                                    </div>
								</div>
                                <div class="col_100">
                                    <div class="col_50" >
                                             <div style="margin-top: 30px!important">
                                                <label style='cursor:pointer'><input type='checkbox' value='S' name='rejeitada'/><b><fmt:message key="requisicaoProduto.rejeitarValidacao"/></b></label>
                                             </div>
                                    </div>
                                    <div class="col_30">
				                        <button class="btn btn-minier" type="button" onclick='$("#POPUP_APROVADORES").dialog("open")'>
				                            <b>&nbsp;<fmt:message key="citcorpore.comum.visualizar" />&nbsp;<fmt:message key="requisicaoProduto.aprovadores" />&nbsp;</b>
				                        </button>
                                    </div>
                                </div>

                                <div class="col_100">
                                    <div class="col_50">
                                        <h2 class="section">
                                            <fmt:message key="requisicaoProduto.itens" />
                                        </h2>
                                    </div>
                                </div>
                                <div class="col_100">
                                	<div class="col_60">&nbsp;</div>
									<div class="col_40">
										<button type="button" id="btnValidar" class="btn btn-minier" onclick='validarItens();'><b>&nbsp;Validar&nbsp;</b></button>
										<button type="button" id="btnInviabilizar" class="btn btn-minier" onclick='exibirJustificativa();'><b>&nbsp;Inviabilizar&nbsp;</b></button>
                                   	</div>
                                </div>
                                <div class="col_100" style="overflow:auto; height:250px">
                                    <table id="tblItensRequisicao" class="table">
                                    	<thead>
	                                        <tr>
	                                            <th width="1px"><input type='checkbox' name='chkSelTodosItens' onclick='marcarDesmarcarTodosItens(this)' /></th>
	                                            <th width="1px">&nbsp;</th>
	                                            <th ><fmt:message key="itemRequisicaoProduto.descricao" /></th>
	                                            <th width="7%"><fmt:message key="itemRequisicaoProduto.quantidade" /></th>
	                                            <th width="30%"><fmt:message key="citcorpore.comum.situacao" /></th>
	                                        </tr>
                                    	</thead>
                                    </table>
                                </div>
                        </form>
                    </div>
            </div>
        </div>

<div id="POPUP_ITEM_REQUISICAO" title="<fmt:message key="requisicaoProduto.itens" />"  style="overflow: auto;">
    <form name='formItemRequisicao'>
        <input type='hidden' name='item#index'/>
        <input type='hidden' name='item#siglaUnidadeMedida'/>
        <input type='hidden' name='item#idProduto'/>
        <input type='hidden' name='item#tipoIdentificacao'/>

		<div class="columns clearfix">
	        <div class="col_100">
				<fieldset>
                	<label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.categoria" /></label>
                    <div>
                    	<select id='item#idCategoriaProduto' name='item#idCategoriaProduto' class="Valid[Required] Description[itemRequisicaoProduto.categoria]" ></select>
 					</div>
				</fieldset>
	        </div>
	        <div class="col_100">
	            <div class="col_80">
	                 <fieldset>
	                      <label><fmt:message key="itemRequisicaoProduto.produto" /></label>
	                     <div>
							<input type='text' name='item#descricaoItem' id='descricaoItem'/>
	                      </div>
	                  </fieldset>
				</div>

	            <div class="col_20">
	                <fieldset>
	                    <label><fmt:message key="itemRequisicaoProduto.quantidade" /></label>
	                    <div>
	                        <input id="item#quantidade" type="text"  maxlength="15" name='item#quantidade' class="Format[Moeda]"/>
	                    </div>
	                </fieldset>
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
                          <label><fmt:message key="itemRequisicaoProduto.especificacoes" /></label>
                          <div>
                               <textarea id="item#especificacoes" name="item#especificacoes" cols='60'></textarea>
                          </div>
                      </fieldset>
                   </div>
                   <div class="col_100">
                       <div class="col_40">
                           <fieldset>
                               <label><fmt:message key="itemRequisicaoProduto.marcaPreferencial" /></label>
                               <div>
                                   <input id="item#marcaPreferencial"  type='text'  name="item#marcaPreferencial" maxlength="100"/>
                               </div>
                           </fieldset>
                       </div>
                       <div class="col_20">
                           <fieldset>
                               <label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.unidadeMedida" /></label>
                               <div>
                                   <select id='item#idUnidadeMedida'  name='item#idUnidadeMedida'></select>
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
        </div>

       <div style="display: block;" id="validacao">
           <div id='divSelecaoProduto' class="col_100">
	             <div class="col_75" >
	             </div>
	             <div id="divAdicionarItem" class="col_25" style="width: 98%; float: center;" align="right" >
	                <label  style="cursor: pointer;" onclick="popupProduto.abrePopup('produto', '')">
	                    <img  id="addProduto" src="${ctx}/imagens/add.png" /><span><b><fmt:message key="produto.cadastrarProduto" /></b></span>
	                </label>
	             </div>
	           <div class="col_100">
	                <div class="col_25" >
	                    <fieldset>
	                        <label  >
	                            <fmt:message key="itemRequisicaoProduto.codigoProduto" />
	                        </label>
	                        <div>
	                            <input id="codigoProduto" type='text' name="item#codigoProduto"/>
	                        </div>
	                   </fieldset>
	                </div>
	                <div class="col_75" >
	                    <fieldset>
	                        <label  >
	                            <fmt:message key="itemRequisicaoProduto.descrProduto" />
	                        </label>
	                        <div>
	                            <input id="nomeProduto" type='text' name="item#nomeProduto"/>
	                        </div>
	                   </fieldset>
	                </div>
	           </div>
           </div>
           <div class="col_100">
               <fieldset>
	               <label  class="campoObrigatorio">
	                   <fmt:message key="itemRequisicaoProduto.validacao" />
	               </label>
	               <div>
	                   <input type='radio' name="item#validado" value="S" onclick='configuraJustificativa("S");' checked="checked"><fmt:message key="itemRequisicaoProduto.validado"/>
	                   <input type='radio' name="item#validado" value="N" onclick='configuraJustificativa("N");' ><fmt:message key="itemRequisicaoProduto.naoValidado"/>
                   </div>
               </fieldset>
		   </div>
           <div id="divJustificativa" class="col_100" >
               <div class="col_50">
                    <fieldset>
                        <label><fmt:message key="itemRequisicaoProduto.justificativa" /></label>
                        <div>
                            <select id='item#idJustificativaValidacao'  name='item#idJustificativaValidacao'></select>
                        </div>
                    </fieldset>
               </div>
               <div class="col_50">
                   <fieldset>
                       <label><fmt:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
                       <div>
                            <textarea id="item#complemJustificativaValidacao" name="item#complemJustificativaValidacao" cols='60' rows='2'></textarea>
                       </div>
                   </fieldset>
               </div>
           </div>
           <div id="divTipoAtendimento" style="display:none" class="col_100">
                <fieldset>
                     <label  >
                         <fmt:message key="itemRequisicaoProduto.tipoAtendimento" />
                     </label>
                     <div>
                        <label style='cursor:pointer'><input type='radio' name="item#tipoAtendimento" value="C" ><fmt:message key="itemRequisicaoProduto.compra"/></label>
                        <label style='cursor:pointer'><input type='radio' name="item#tipoAtendimento" value="E" ><fmt:message key="itemRequisicaoProduto.estoque"/></label>
                     </div>
                </fieldset>
            </div>
            <div class="col_100" >
	           <div class="col_50">
                    <fieldset>
                         <label>
                             &nbsp;
                         </label>
                    </fieldset>
               </div>
               <div class="col_50">
                    <fieldset>
                        <div style="padding: 10px 0px 0px 10px">
			                <button type="button" onclick='$("#POPUP_ITEM_REQUISICAO").dialog("close")'>
			                    <fmt:message key="citcorpore.comum.fechar" />
			                </button>
			                <button type="button" onclick='atualizarItem()'>
			                    <fmt:message key="citcorpore.comum.confirmar" />
			                </button>
                        </div>
                    </fieldset>
	           </div>
            </div>
       </div>
    </form>
    <div id="popupCadastroRapido">
        <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="99%">
        </iframe>
    </div>
</div>

    <div id="POPUP_PRODUTO" title="<fmt:message key="citcorpore.comum.pesquisaproduto" />">
        <div class="box grid_16 tabs">
            <div class="toggle_container">
                <div id="tabs-2" class="block">
                    <div class="section">
                        <form name='formPesquisaProduto' style="width: 540px">
                            <cit:findField formName='formPesquisaProduto'
                                lockupName='LOOKUP_PRODUTO' id='LOOKUP_PRODUTO' top='0'
                                left='0' len='550' heigth='400' javascriptCode='true'
                                htmlCode='true' />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>


<div id="POPUP_APROVADORES" title="<fmt:message key="requisicaoProduto.aprovadores" />"  style="overflow: auto;">
	<div class="columns clearfix">
         <div class="col_100" >
              <div id='divAprovadores' style="overflow:auto;">
              </div>
         </div>
         <div class="col_100" >
              <div id='divLoginAprovadores' style="overflow:auto;margin: 10px 10px 0px;">
              </div>
         </div>
	</div>
</div>

<div id="POPUP_JUSTIFICATIVA" title="<fmt:message key="itemRequisicaoProduto.justificativa" />"  style="overflow: auto;">
	<div class="columns clearfix">
             <div class="col_100">
                  <fieldset>
                      <label>&nbsp;</label>
                      <div>
                          <select id='idJustificativaPopup'  name='idJustificativaPopup'></select>
                      </div>
                  </fieldset>
             </div>
             <div class="col_100">
                 <fieldset>
                     <label><fmt:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
                     <div>
                          <textarea id="complemJustificativaPopup" name="complemJustificativaPopup" cols='60' rows='2'></textarea>
                     </div>
                 </fieldset>
             </div>
            <div class="col_100" >
	           <div class="col_60">
                    <fieldset>
                         <label>
                             &nbsp;
                         </label>
                    </fieldset>
               </div>
               <div class="col_40">
                    <fieldset>
                        <div style="padding: 10px 0px 0px 10px">
			                <button type="button" onclick='$("#POPUP_JUSTIFICATIVA").dialog("close")'>
			                    <fmt:message key="citcorpore.comum.fechar" />
			                </button>
			                <button type="button" onclick='inviabilizarItens()'>
			                    <fmt:message key="citcorpore.comum.confirmar" />
			                </button>
                        </div>
                    </fieldset>
	           </div>
            </div>
	</div>
</div>

</body>

</html>
