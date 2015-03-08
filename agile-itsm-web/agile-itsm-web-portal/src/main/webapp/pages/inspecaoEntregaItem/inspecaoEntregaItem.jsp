<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO"%>
<%@page import="java.util.Collection"%>

<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
    <%
        String id = request.getParameter("id");
        Collection<CriterioAvaliacaoDTO> colCriterios = (Collection)request.getAttribute("colCriterios");
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
    <script type="text/javascript" src="../../cit/objects/InspecaoEntregaItemDTO.js"></script>
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

     .linhaSubtituloGrid{
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
                top: 0,
                width : 580,
                height : 630,
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
            if (!tratarCriterios()){
                return;
            }

            if (!document.formItemRequisicao.validate())
                return;

            var indice = parseInt(document.getElementById('item#index').value);
            if (indice == 0)
                return;

            var obj = HTMLUtils.getObjectByTableIndex('tblItensRequisicao', indice);
            HTMLUtils.setValuesObjectByGroupName(document.formItemRequisicao, 'item', obj);
            var aprovado = document.formItemRequisicao['item#aprovado'];
            obj.aprovado = '';
            if (aprovado[0].checked) {
                obj.aprovado = 'S';
                obj.descrSituacao = i18n_message("pedidoCompra.entregaAprovada");
            }else if (aprovado[1].checked) {
                obj.aprovado = 'N';
                obj.descrSituacao = i18n_message("pedidoCompra.entregaNaoAprovada");
            }
            configuraJustificativa(obj.aprovado);

            HTMLUtils.updateRow('tblItensRequisicao', document.formItemRequisicao, 'item', obj,
                    ["","","quantidadeEntregue","descrSituacao"], null,'', [gerarImg], null, null, indice, true);

            document.form.save();
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

            row.cells[0].innerHTML = '<img  src="${ctx}/imagens/edit.png" style="cursor: pointer;" onclick="editarItem('+row.rowIndex+')" title="'+i18n_message("dinamicview.editar")+'" >';
            row.cells[1].innerHTML = '<a '+title+'>'+obj.descricaoItem+'</a>';
        };

        function editarItem(indice) {
            var obj = HTMLUtils.getObjectByTableIndex("tblItensRequisicao", indice);

            document.formItemRequisicao.clear();
            HTMLUtils.setValues(document.formItemRequisicao,'item',obj);
            var aprovado = document.formItemRequisicao['item#aprovado'];
            if (obj.aprovado == 'S')
                aprovado[0].checked = true;
            else if (obj.aprovado == 'N')
                aprovado[1].checked = true;
            document.getElementById('item#index').value = indice;
            configuraJustificativa(obj.aprovado)
            document.getElementById('item#dataEntrega').value = obj.dataEntrega;
            document.form.idEntrega.value = obj.idEntrega;
            document.form.fireEvent('exibeAvaliacao');
        }

        function validar() {
            return document.form.validate();
        }

        function getObjetoSerializado() {
            var obj = new CIT_RequisicaoProdutoDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            obj.itensEntrega_serialize = ObjectUtils.serializeObjects(itensRequisicao);
            return ObjectUtils.serializeObject(obj);
        }

        var seqCriterio = '';
        incluirCriterio = function() {
            GRID_CRITERIOS.addRow();
            seqCriterio = NumberUtil.zerosAEsquerda(GRID_CRITERIOS.getMaxIndex(),5);
            eval('document.formItemRequisicao.observacoes' + seqCriterio + '.value = ""');
            eval('document.formItemRequisicao.idCriterio' + seqCriterio + '.focus()');
        }

        exibeCriterio = function(serializeCriterio) {
            if (seqCriterio != '') {
                if (!StringUtils.isBlank(serializeCriterio)) {
                    var criterioDto = new CIT_InspecaoEntregaItemDTO();
                    criterioDto = ObjectUtils.deserializeObject(serializeCriterio);
                    try{
                        eval('HTMLUtils.setValue("idCriterio' + seqCriterio + '",' + criterioDto.idCriterio + ')');
                    }catch(e){
                    }
                    if (criterioDto.tipoAvaliacao == 'S') {
                    	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "', i18n_message('criterioAvaliacao.sim'), i18n_message('criterioAvaliacao.sim'))");
                    	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "', i18n_message('criterioAvaliacao.nao'),i18n_message('criterioAvaliacao.nao'))");
                    }
                    if (criterioDto.tipoAvaliacao == 'A') {
                    	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "', i18n_message('criterioAvaliacao.aceito'),i18n_message('criterioAvaliacao.aceito'))");
                    	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "', i18n_message('criterioAvaliacao.naoAceito'),i18n_message('criterioAvaliacao.naoAceito'))");
                    }
                    if (criterioDto.tipoAvaliacao == 'C') {
                    	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "', i18n_message('criterioAvaliacao.conforme'),i18n_message('criterioAvaliacao.conforme'))");
                    	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "', i18n_message('criterioAvaliacao.naoConforme'),i18n_message('criterioAvaliacao.naoConforme'))");
                    }
                    if (criterioDto.tipoAvaliacao == 'P') {
                        for ( var i = 1; i < 11; i++) {
                        	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "',"+i+","+i+");");
						}
                    }
                    eval('document.formItemRequisicao.avaliacao' + seqCriterio + '.value = "' + criterioDto.avaliacao + '"');
                    eval('document.formItemRequisicao.observacoes' + seqCriterio + '.value = "' + criterioDto.observacoes + '"');
                }
            }
        }

        getCriterio = function(seq) {
            var criterioDto = new CIT_InspecaoEntregaItemDTO();

            seqCriterio = NumberUtil.zerosAEsquerda(seq,5);
            criterioDto.sequencia = seq;
            criterioDto.idCriterio = parseInt(eval('document.formItemRequisicao.idCriterio' + seqCriterio + '.value'));
            criterioDto.avaliacao = eval('document.formItemRequisicao.avaliacao' + seqCriterio + '.value');
            criterioDto.observacoes = eval('document.formItemRequisicao.observacoes' + seqCriterio + '.value');
            return criterioDto;
        }

        verificarCriterio = function(seq) {
            var idCriterio = eval('document.formItemRequisicao.idCriterio' + seq + '.value');
            var count = GRID_CRITERIOS.getMaxIndex();
            for (var i = 1; i <= count; i++){
                if (parseInt(seq) != i) {
                     var trObj = document.getElementById('GRID_CRITERIOS_TD_' + NumberUtil.zerosAEsquerda(i,5));
                     if (!trObj){
                        continue;
                     }
                     var idAux = eval('document.formItemRequisicao.idCriterio' + NumberUtil.zerosAEsquerda(i,5) + '.value');
                     if (idAux == idCriterio) {
                          alert('Critério já selecionado!');
                          eval('document.formItemRequisicao.idCriterio' + seq + '.focus()');
                          return false;
                     }
                }
            }
            return true;
        }

        function tratarCriterios(){
            try{
                var count = GRID_CRITERIOS.getMaxIndex();
                var contadorAux = 0;
                var objs = new Array();
                for (var i = 1; i <= count; i++){
                    var trObj = document.getElementById('GRID_CRITERIOS_TD_' + NumberUtil.zerosAEsquerda(i,5));
                    if (!trObj){
                        continue;
                    }

                    var criterioDto = getCriterio(i);
                    if (parseInt(criterioDto.idCriterio) > 0) {
                        if  (!verificarCriterio(NumberUtil.zerosAEsquerda(i,5))) {
                            return false;
                        }
                        if (StringUtils.isBlank(criterioDto.avaliacao)){
                            alert('Informe a avaliação!');
                            eval('document.formItemRequisicao.avaliacao' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
                            return false;
                        }
                        objs[contadorAux] = criterioDto;
                        contadorAux = contadorAux + 1;
                    }else{
                        alert('Selecione a avaliacao!');
                        eval('document.formItemRequisicao.idCriterio' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
                        return false;
                    }
                }
                document.form.colCriterios_Serialize.value = ObjectUtils.serializeObjects(objs);
                return true;
            }catch(e){
            }
        }

        function gravarCriterios() {
            if (!tratarCriterios()){
                return;
            }

            document.form.save();
        }

        function configuraJustificativa(aprovado) {
            document.getElementById('divJustificativa').style.display = 'none';
            if (aprovado == 'N')
                document.getElementById('divJustificativa').style.display = 'block';
        }
    </script>
</head>

<body>
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='${ctx}/pages/inspecaoEntregaItem/inspecaoEntregaItem'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
                                <input type='hidden' name='editar' id='editar' />
                                <input type='hidden' name='acao' id='acao'/>
                                <input type='hidden' name='colCriterios_Serialize' id='colCriterios_Serialize'/>
                                <input type='hidden' name='idEntrega' id='idEntrega'/>

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
                                             <label ><fmt:message key="centroResultado.custo" /></label>
                                             <div>
                                                 <select name='idCentroCusto' disabled="disabled"></select>
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
                                    <div class="col_50">
                                        <h2 class="section">
                                            <fmt:message key="requisicaoProduto.itens" />
                                        </h2>
                                    </div>
                                </div>
                                <div class="col_100" style="overflow:auto; height:300px">
                                    <table id="tblItensRequisicao" class="table">
                                        <tr>
                                            <th width="1%">&nbsp;</th>
                                            <th ><fmt:message key="itemRequisicaoProduto.descricao" /></th>
                                            <th width="10%"><fmt:message key="itemRequisicaoProduto.quantidade" /></th>
                                            <th width="25%"><fmt:message key="citcorpore.comum.situacao" /></th>
                                        </tr>
                                    </table>
                                </div>
                        </form>
                    </div>
            </div>
        </div>

<div id="POPUP_ITEM_REQUISICAO" title="<fmt:message key="pedidoCompra.avaliacao" />"  style="overflow: auto;">
    <form name='formItemRequisicao'>
        <input type='hidden' name='item#index'/>
        <div class="section">
            <div class="col_100">
                 <fieldset>
	                 <label><fmt:message key="coletaPreco.item" />
	                 </label>
                    <div>
                      <input id="item#descricaoItem"  type='text'  name="item#descricaoItem" readonly="readonly" />
                    </div>
                 </fieldset>
            </div>
            <div class="col_100">
                 <div class="col_20">
                     <fieldset>
                        <label><fmt:message key="fornecedor.cpfcnpj" />
                        </label>
                        <div>
                          <input id="item#cpfCnpjFornecedor"  type='text'  name="item#cpfCnpjFornecedor" readonly="readonly" />
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
                 <div class="col_20">
                    <fieldset>
                        <label style="cursor: pointer;"><fmt:message key="itemRequisicaoProduto.quantidade" /></label>
                        <div>
                            <input id="item#quantidadeEntregue" type="text"  name='item#quantidadeEntregue' class="Format[Moeda]" readonly="readonly" />
                        </div>
                    </fieldset>
                 </div>
                 <div class="col_40">
                     <fieldset>
                        <label><fmt:message key="pedidoCompra.numero" />
                        </label>
                        <div>
                          <input id="item#numeroPedido"  type='text'  name="item#numeroPedido" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
                 <div class="col_40">
                     <fieldset>
                        <label><fmt:message key="pedidoCompra.dataEntrega" />
                        </label>
                        <div>
                          <input id="item#dataEntrega"  type='text'  name="item#dataEntrega" class="Format[Data]" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
            </div>
	       <div style="display: block;" id="validacao">
	           <div class="col_100">
	               <fieldset>
		               <label  class="campoObrigatorio">
		                   <fmt:message key="itemRequisicaoProduto.aprovacao" />
		               </label>
		               <div>
	                       <input type='radio' name="item#aprovado" value="S" onclick='configuraJustificativa("S");' checked="checked"><fmt:message key="pedidoCompra.entregaAprovada"/>
	                       <input type='radio' name="item#aprovado" value="N" onclick='configuraJustificativa("N");' ><fmt:message key="pedidoCompra.entregaNaoAprovada"/>
	                   </div>
	               </fieldset>
			   </div>
               <div id="divJustificativa" class="col_100" style='display:none' >
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
            </div>
            <div class="col_100">
                   <h2 class="section">
                         <fmt:message key="pedidoCompra.avaliacao" />
                    </h2>
            </div>
            <div class="col_100" style='height:170px;overflow:auto;'>
	             <cit:grid id="GRID_CRITERIOS" columnHeaders="Critério;Avaliacao;Observações" styleCells="linhaGrid" deleteIcon="false">
	                 <cit:column idGrid="GRID_CRITERIOS" number="001">
	                     <select name='idCriterio#SEQ#' id='idCriterio#SEQ#' style='border:none;' disabled='disabled; min-width:20% !important'>
	                         <option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
	                         <%
	                         if (colCriterios != null){
	                             for (CriterioAvaliacaoDTO criterioDto : colCriterios) {
	                                 out.println("<option value='" + criterioDto.getIdCriterio() + "'>" +
	                                 criterioDto.getDescricao() + "</option>");
	                             }
	                         }
	                         %>
	                     </select>
	                 </cit:column>
	                 <cit:column idGrid="GRID_CRITERIOS" number="002">
	                     <select name='avaliacao#SEQ#' id='avaliacao#SEQ#' style='border:none'>
	                         <option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
	                     </select>
	                 </cit:column>
	                 <cit:column idGrid="GRID_CRITERIOS" number="003">
	                 	 <textarea name='observacoes#SEQ#' id='observacoes#SEQ#' cols='30' rows='2' ></textarea>
	                 </cit:column>
	             </cit:grid>
            </div>
            <div class="col_100" >
                    <fieldset>
                        <div>
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
    </form>
</div>

</body>

</html>
