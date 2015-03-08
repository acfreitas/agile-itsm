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
        function load(){
            document.form.afterLoad = function () {
            	parent.escondeJanelaAguarde();
            }
        }

        $(function() {
            $("#POPUP_ITEM_REQUISICAO").dialog({
                autoOpen : false,
                top : 0,
                width : 580,
                height : 640,
                modal : true
            });
            $("#POPUP_JUSTIFICATIVA").dialog({
                autoOpen : false,
                width : 580,
                height : 300,
                modal : true
            });
            $("#POPUP_RESULTADO").dialog({
                autoOpen : false,
                width : 580,
                height : 300,
                modal : true
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

            var obj = HTMLUtils.getObjectByTableIndex('tblItensRequisicao', indice);
            HTMLUtils.setValuesObjectByGroupName(document.formItemRequisicao, 'item', obj);
            var aprovado = document.formItemRequisicao['item#aprovado'];
            if (aprovado[0].checked) {
                obj.aprovado = 'S';
                obj.descrSituacao = i18n_message("itemRequisicaoProduto.aprovado");
            }else{
                obj.aprovado = 'N';
                obj.descrSituacao = i18n_message("itemRequisicaoProduto.naoAprovado");
            }

            HTMLUtils.updateRow('tblItensRequisicao', document.formItemRequisicao, 'item', obj,
                    ["","","","descricaoItem","quantidade","valorTotal","","descrSituacao"], null,'', [gerarImg], null, null, indice, true);
            totalizarItens();
            $("#POPUP_ITEM_REQUISICAO").dialog("close");
        };

        function gerarImg (row, obj){
        	var title = 'title="header=[] body=['+
        	'<div class=\'tip_conteudo\'>'+
        	'	<div class=\'seta\'></div>'+
        	'	<div class=\'tip_conteudo_legenda\'>'+
        	'		<div class=\'tip_titulo\'>'+obj.descricaoItem+'</div>';
        	title += obj.descricaoFmtHtml;
        	title += '</div>'+
        	'</div>'+
        	']"';

        	var vlrUnitario = parseFloat("0"+obj.preco.replace(",",".")) - parseFloat("0"+obj.valorDesconto.replace(",",".")) + parseFloat("0"+obj.valorAcrescimo.replace(",","."));
            obj.selecionado = 'N';

            row.cells[0].innerHTML = "&nbsp;<input type='checkbox' name='chkSel_"+obj.idItemRequisicaoProduto+"' id='chkSel_"+obj.idItemRequisicaoProduto+"' onclick='marcarDesmarcar(this,"+row.rowIndex+",\"tblItensRequisicao\")' />";
            row.cells[0].align = "center";
            row.cells[1].innerHTML = '<img  src="${ctx}/imagens/edit.png" style="cursor: pointer;" onclick="editarItem('+row.rowIndex+')" title="'+i18n_message("dinamicview.editar")+'" >';
            row.cells[2].innerHTML = '<img  src="${ctx}/imagens/documents.png" style="cursor: pointer;" onclick="exibirResultados('+row.rowIndex+')" title="Exibir cotações" >';
            row.cells[3].innerHTML = '<a '+title+'>'+obj.descricaoItem+'</a>';
            row.cells[6].innerHTML = NumberUtil.format(vlrUnitario, 2, ",", ".");
            row.cells[6].align = "right";
        };

        function exibirResultados(indice) {
            var obj = HTMLUtils.getObjectByTableIndex("tblItensRequisicao", indice);
            document.form.idItemCotacao.value = obj.idItemCotacao;
            document.form.fireEvent('exibeResultado');
            $('#POPUP_RESULTADO').dialog('open');
        }

        function editarItem(indice) {
            var obj = HTMLUtils.getObjectByTableIndex("tblItensRequisicao", indice);
            if (document.form.editar.value == 'N')
                return;

            document.formItemRequisicao.clear();
            HTMLUtils.setValues(document.formItemRequisicao,'item',obj);
            var aprovado = document.formItemRequisicao['item#aprovado'];
            if (obj.aprovado == 'S')
            	aprovado[0].checked = true;
            else if (obj.aprovado == 'N')
                aprovado[1].checked = true;
            document.getElementById('item#index').value = indice;
            configuraJustificativa(obj.aprovado);
            $('#POPUP_ITEM_REQUISICAO').dialog('open');
        }

        function validar() {
            return document.form.validate();
        }

        function getObjetoSerializado() {
            var obj = new CIT_RequisicaoProdutoDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            obj.itensCotacao_serialize = ObjectUtils.serializeObjects(itensRequisicao);
            return ObjectUtils.serializeObject(obj);
        }

        function configuraJustificativa(aprovado) {
        	document.getElementById('divJustificativa').style.display = 'none';
            document.getElementById('divAprovacao').style.display = 'none';
            if (aprovado == 'N')
            	document.getElementById('divJustificativa').style.display = 'block';
            else if (aprovado == 'S')
                document.getElementById('divAprovacao').style.display = 'block';
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
            for(i=0;i<itensRequisicao.length;i++){
                var obj = itensRequisicao[i];
                if (obj.selecionado != 'S')
                    continue;

                obj.aprovado = 'S';
                obj.descrSituacao = i18n_message("itemRequisicaoProduto.aprovado");

                HTMLUtils.updateRow('tblItensRequisicao', null, 'item', obj,
                		["","","","descricaoItem","quantidade","valorTotal","","descrSituacao"], null,'', [gerarImg], null, null, i+1, true);
            }
            totalizarItens();
            document.getElementById('chkSelTodosItens').checked = false;
        }

        inviabilizarItens = function() {
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            for(i=0;i<itensRequisicao.length;i++){
                var obj = itensRequisicao[i];
                if (obj.selecionado != 'S')
                    continue;

                obj.aprovado = 'N';
                obj.descrSituacao = i18n_message("itemRequisicaoProduto.naoAprovado");

                obj.idJustificativa = document.getElementById("idJustificativaPopup").value;
                obj.complemJustificativa = document.getElementById("complemJustificativaPopup").value;

                HTMLUtils.updateRow('tblItensRequisicao', null, 'item', obj,
                		["","","","descricaoItem","quantidade","valorTotal","","descrSituacao"], null,'', [gerarImg], null, null, i+1, true);
            }
            totalizarItens();
            $("#POPUP_JUSTIFICATIVA").dialog("close");
            document.getElementById('chkSelTodosItens').checked = false;
        }

        exibirJustificativa = function() {
            if (!itensSelecionados()) {
                alert("Selecione pelo menos um item");
                return;
            }

            document.getElementById("complemJustificativaPopup").value = '';
        	$("#POPUP_JUSTIFICATIVA").dialog("open");
        }

        totalizarItens = function() {
        	var vlr = "";
            var total = 0.0;
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            for(i=0;i<itensRequisicao.length;i++){
                var obj = itensRequisicao[i];
                if (obj.aprovado != 'S')
                    continue;

                total += parseFloat(obj.valorTotal);
            }
            vlr = NumberUtil.format(total, 2, ",", ".");
            document.getElementById("spanValorTotal").innerHTML = "<b>"+vlr+"</b>";
        }

        </script>
</head>

<body>
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='${ctx}/pages/aprovacaoCotacao/aprovacaoCotacao'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
                                <input type='hidden' name='editar' id='editar' />
                                <input type='hidden' name='acao' id='acao'/>
                                <input type='hidden' name='itensCotacao_serialize' id='itensCotacao_serialize'/>
                                <input type='hidden' name='idItemCotacao' id='idItemCotacao'/>

                               <div class="col_100">
                                    <div class="col_50">
                                        <fieldset >
                                            <label><fmt:message key="requisicaoProduto.finalidade" /></label>
                                            <div>
                                                <select name='finalidade' id='finalidade' disabled="disabled"></select>
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
                                             <label ><fmt:message key="requisicaoProduto.projeto" /></label>
                                             <div>
                                                 <select name='idProjeto' disabled="disabled"></select>
                                             </div>
                                         </fieldset>
                                    </div>
                                    <div class="col_60">
                                         <fieldset >
                                             <label ><fmt:message key="requisicaoProduto.enderecoEntrega" /></label>
                                             <div>
                                                 <select name='idEnderecoEntrega' disabled="disabled"></select>
                                             </div>
                                         </fieldset>
                                    </div>
								</div>

                                <div class="col_100">
                                    <div class="col_60">
                                        <h2 class="section">
                                            <fmt:message key="requisicaoProduto.itens" />
                                        </h2>
                                    </div>
                                    <div class="col_40">
										<button type="button" id="btnValidar" class="btn btn-minier" onclick='validarItens();'><b>&nbsp;Aprovar&nbsp;</b></button>
										<button type="button" id="btnInviabilizar" class="btn btn-minier" onclick='exibirJustificativa();'><b>&nbsp;Não aprovar&nbsp;</b></button>
                                    </div>
                                </div>
                                <div class="col_100" style="overflow:auto; height:180px">
                                    <table id="tblItensRequisicao" class="table">
                                    	<thead>
	                                        <tr>
	                                            <th width="1px"><input type='checkbox' name='chkSelTodosItens' onclick='marcarDesmarcarTodosItens(this)' /></th>
	                                            <th width="1px">&nbsp;</th>
	                                            <th width="1px">&nbsp;</th>
	                                            <th ><fmt:message key="itemRequisicaoProduto.descricao" /></th>
	                                            <th width="7%" style="text-align:rig"><fmt:message key="itemRequisicaoProduto.quantidade" /></th>
	                                            <th width="10%"><fmt:message key="citcorpore.comum.valor" /></th>
	                                            <th width="10%">Valor unitário</th>
	                                            <th width="35%"><fmt:message key="citcorpore.comum.situacao" /></th>
	                                        </tr>
                                    	</thead>
                                    </table>
                                </div>
                                <div class="col_100">
                                	<div class="col_60">&nbsp;</div>
                                    <div class="col_40">
	                                    <span><b>Valor total aprovado:&nbsp;</b></span>
				 						<span id="spanValorTotal"></span>
	                                 </div>
	                            </div>
                        </form>
                    </div>
            </div>
        </div>


<div id="POPUP_ITEM_REQUISICAO" title="<fmt:message key="requisicaoProduto.itens" />"  style="overflow: auto;">
    <form name='formItemRequisicao'>
        <input type='hidden' name='item#index'/>

        <div class="col_100">
            <div class="col_100">
                 <div class="col_80">
                     <fieldset>
	                    <label><fmt:message key="coletaPreco.item" />
	                    </label>
                        <div>
                          <input id="item#descricaoItem"  type='text'  name="item#descricaoItem" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
                 <div class="col_20">
                    <fieldset>
                        <label style="cursor: pointer;"><fmt:message key="itemRequisicaoProduto.quantidade" /></label>
                        <div>
                            <input id="item#quantidade" type="text"  name='item#quantidade' class="Format[Moeda]" readonly="readonly" />
                        </div>
                    </fieldset>
                 </div>
            </div>
            <div class="col_100">
                 <div class="col_20">
                     <fieldset>
                        <label><fmt:message key="fornecedor.cpfcnpj" />
                        </label>
                        <div>
                          <input id="item#cpfCnpjFornecedor"  type='text'  name="cpfCnpjFornecedor" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
                 <div class="col_80">
                     <fieldset>
                        <label><fmt:message key="fornecedor" />
                        </label>
                        <div>
                          <input id="item#nomeFornecedor"  type='text'  name="item#nomeFornecedor" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
            </div>
            <div class="col_100">
                <fieldset>
                    <label><fmt:message key="coletaPreco.especificacoes" />
                    </label>
                    <div>
                        <textarea name="item#especificacoes" id="item#especificacoes" cols='200' rows='3' ></textarea>
                    </div>
                </fieldset>
            </div>
            <div class="col_100">
                <div class="col_33">
                    <fieldset>
                        <label ><fmt:message key="itemRequisicaoProduto.preco" />
                        </label>
                        <div>
                           <input id="item#preco" type='text' name="item#preco" class="Format[Moeda]" readonly="readonly"/>
                        </div>
                    </fieldset>
                </div>
                <div class="col_33">
                    <fieldset>
                        <label ><fmt:message key="coletaPreco.preco" />
                        </label>
                        <div>
                           <input id="item#valorTotal" type='text' name="item#valorTotal" class="Format[Moeda]"  readonly="readonly"/>
                        </div>
                    </fieldset>
                </div>
                <div class="col_33">
                    <fieldset>
                        <label><fmt:message key="coletaPreco.prazoEntrega" />
                        </label>
                        <div>
                           <input id="item#prazoEntrega" type='text' name="item#prazoEntrega" class="Format[Numero]"  readonly="readonly"/>
                        </div>
                    </fieldset>
                </div>
            </div>
        </div>
       <div style="display: block;" id="validacao">
           <div class="col_100">
               <fieldset>
	               <label  class="campoObrigatorio">
	                   <fmt:message key="itemRequisicaoProduto.aprovacao" />
	               </label>
	               <div>
                       <input type='radio' name="item#aprovado" value="S" onclick='configuraJustificativa("S");' checked="checked"><fmt:message key="itemRequisicaoProduto.aprovado"/>
                       <input type='radio' name="item#aprovado" value="N" onclick='configuraJustificativa("N");' ><fmt:message key="itemRequisicaoProduto.naoAprovado"/>
                   </div>
               </fieldset>
		   </div>

              <div id="divJustificativa" class="col_100"  style='display:none'>
                  <div class="col_50">
                       <fieldset>
                           <label><fmt:message key="itemRequisicaoProduto.justificativa" /></label>
                           <div>
                               <select id='item#idJustificativa'  name='item#idJustificativa'></select>
                           </div>
                       </fieldset>
                  </div>
                  <div class="col_50">
                      <fieldset>
                          <label><fmt:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
                          <div>
                               <textarea id="item#complementoJustificativa" name="item#complementoJustificativa" cols='60' rows='2'></textarea>
                          </div>
                      </fieldset>
                  </div>
              </div>
            <div id="divAprovacao" class="col_30" style='display:none'>
                 <fieldset>
                     <label><fmt:message key="itemRequisicaoProduto.percVariacaoPreco" /></label>
                     <div>
                          <input id="item#percVariacaoPreco" type="text"  maxlength="15" name='item#percVariacaoPreco' class="Valid[Required] Description[itemRequisicaoProduto.percVariacaoPreco] Format[Moeda]"/>
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
</div>

<div id="POPUP_RESULTADO" title="<fmt:message key="cotacao.resultados" />"  style="overflow: auto;">
	<div class="columns clearfix">
       	<div class="col_90" id="divResultado" style='padding:5px;height:250px;overflow:auto' ></div>
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
