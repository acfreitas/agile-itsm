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

<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
    <script type="text/javascript" src="../../cit/objects/InspecaoPedidoCompraDTO.js"></script>

<script>
	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			document.form.idCotacao.value = '<%=idCotacao%>';
	        $('.tabs').tabs('select', 0);
            if (!StringUtils.isBlank(document.form.idPedido.value)) {
                //document.getElementById('idFornecedor').disabled = true;
                verificaSituacao(document.form.situacao.value);
            }else{
                //document.getElementById('idFornecedor').disabled = false;
                document.form.dataPedido.value = "<%=UtilDatas.dateToSTR(UtilDatas.getDataAtual())%>";
            }
		}
	    document.form.afterLoad = function() {
            $('.tabs').tabs('select', 0);
            document.form.dataPedido.value = "<%=UtilDatas.dateToSTR(UtilDatas.getDataAtual())%>";
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
        if (!tratarCriterios()){
            return;
        }

        if (!confirm(i18n_message("pedidoCompra.mensagemConfirmacao")))
             return;

    	document.form.idCotacao.value = '<%=idCotacao%>';
        document.form.save();
    }

    var seqCriterio = '';
    incluirCriterio = function() {
        GRID_CRITERIOS.addRow();
        seqCriterio = NumberUtil.zerosAEsquerda(GRID_CRITERIOS.getMaxIndex(),5);
        eval('document.form.observacoes' + seqCriterio + '.value = ""');
        eval('document.form.idCriterio' + seqCriterio + '.focus()');
    }

    exibeCriterio = function(serializeCriterio) {
        if (seqCriterio != '') {
            if (!StringUtils.isBlank(serializeCriterio)) {
                var criterioDto = new CIT_InspecaoPedidoCompraDTO();
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
                try{
                    eval('HTMLUtils.setValue("avaliacao' + seqCriterio + '",' + criterioDto.avaliacao + ')');
                }catch(e){
                }
                eval('document.form.observacoes' + seqCriterio + '.value = "' + criterioDto.observacoes + '"');
            }
        }
    }

    getCriterio = function(seq) {
        var criterioDto = new CIT_InspecaoPedidoCompraDTO();

        seqCriterio = NumberUtil.zerosAEsquerda(seq,5);
        criterioDto.sequencia = seq;
        criterioDto.idCriterio = parseInt(eval('document.form.idCriterio' + seqCriterio + '.value'));
        criterioDto.avaliacao = eval('document.form.avaliacao' + seqCriterio + '.value');
        criterioDto.observacoes = eval('document.form.observacoes' + seqCriterio + '.value');
        return criterioDto;
    }

    verificarCriterio = function(seq) {
        var idCriterio = eval('document.form.idCriterio' + seq + '.value');
        var count = GRID_CRITERIOS.getMaxIndex();
        for (var i = 1; i <= count; i++){
            if (parseInt(seq) != i) {
                 var trObj = document.getElementById('GRID_CRITERIOS_TD_' + NumberUtil.zerosAEsquerda(i,5));
                 if (!trObj){
                    continue;
                 }
                 var idAux = eval('document.form.idCriterio' + NumberUtil.zerosAEsquerda(i,5) + '.value');
                 if (idAux == idCriterio) {
                      alert('Critério já selecionado!');
                      eval('document.form.idCriterio' + seq + '.focus()');
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
                        eval('document.form.avaliacao' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
                        return false;
                    }
                    objs[contadorAux] = criterioDto;
                    contadorAux = contadorAux + 1;
                }else{
                    alert('Selecione a avaliacao!');
                    eval('document.form.idCriterio' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
                    return false;
                }
            }
            document.form.colCriterios_Serialize.value = ObjectUtils.serializeObjects(objs);
            return true;
        }catch(e){
        }
    }

    function limpar(){
        var id = '';
        if (rowPedido != null)
            rowPedido.style.background = '';
        rowPedido = null;
        document.form.idCotacao.value = '<%=idCotacao%>';
        document.form.restore({idPedido: id, idCotacao: document.form.idCotacao.value});
    }

    emitirEspelho = function() {
        JANELA_AGUARDE_MENU.show();
        document.form.fireEvent("emiteEspelhoCompra");
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
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>
<div class="box grid_16 tabs">
	<form name='form'
		action='${ctx}/pages/entregaPedido/entregaPedido'>
		<div class="columns clearfix">
            <input type='hidden' name='idPedido' id='idPedido'/>
            <input type='hidden' name='idCotacao' id='idCotacao' value='<%=idCotacao%>'/>
            <input type='hidden' name='colCriterios_Serialize' id='colCriterios_Serialize'/>

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
                         <fmt:message key="pedidoCompra.entrega" />
                     </h2>
                     <div class="col_100">
			                 <fieldset >
			                     <label style="cursor: pointer;"><fmt:message key="fornecedor" /></label>
			                     <div>
			                         <input type='text' name="nomeFornecedor" id="nomeFornecedor" readonly="readonly" />
			                     </div>
			                 </fieldset>
                     </div>
                     <div class="col_100">
                                <div class="col_33">
                                    <fieldset >
                                        <label><fmt:message key="pedidoCompra.numero" />
                                        </label>
                                        <div>
                                             <input type='text' name="numeroPedido" id="numeroPedido" readonly="readonly" />
                                        </div>
                                    </fieldset>
                                </div>
                                <div class="col_33">
                                    <fieldset >
                                        <label class="campoObrigatorio" ><fmt:message key="pedidoCompra.dataEntrega" />
                                        </label>
                                            <div>
                                                <input type='text' name="dataEntrega" id="dataEntrega" maxlength="10" size="10"
                                                    class="Valid[Required] Description[pedidoCompra.dataEntrega] Format[Data] datepicker" readonly="readonly" />
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
		            	<div id='divNovoCriterio' class="col_60">
		                   <h2 class="section">
		                         <fmt:message key="pedidoCompra.avaliacao" />
		                    </h2>
		                </div>
	                    <div id='divNovoCriterio' class="col_40">
                             <br>
                             <label  style="cursor: pointer;" onclick='incluirCriterio();'>
                                 <img  src="${ctx}/imagens/add.png" /><span><b><fmt:message key="cotacao.novoCriterio" /></b></span>
                             </label>
	                    </div>
		            </div>
		            <div class="col_100">
		                  <div style='width:700px;overflow:auto;'>
		                   <cit:grid id="GRID_CRITERIOS" columnHeaders="Critério;Avaliacao;Observações" styleCells="linhaGrid">
		                       <cit:column idGrid="GRID_CRITERIOS" number="001">
		                           <select name='idCriterio#SEQ#' id='idCriterio#SEQ#' style='border:none; width: 200px' onchange='verificarCriterio("#SEQ#");'>
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
		                           <select name='avaliacao#SEQ#' id='avaliacao#SEQ#' style='border:none; width: 150px'>
		                               <option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
		                           </select>
		                       </cit:column>
		                       <cit:column idGrid="GRID_CRITERIOS" number="003">
		                       		<textarea name='observacoes#SEQ#' id='observacoes#SEQ#' cols='50' rows='3' ></textarea>
		                       </cit:column>
		                   </cit:grid>
		                  </div>
		            </div>
		             <div class="col_100">
		                   <button type='button' name='btnGravar' id='btnGravar' class="light" style='margin-top: 10px !important'
		                       onclick='gravar();'>
		                       <img
		                           src="${ctx}/template_new/images/icons/small/grey/create_write.png">
		                       <span><fmt:message key="pedidoCompra.confirmarEntrega" /></span>
		                   </button>
		                   <button type='button' name='btnEspelho' id='btnEspelho' class="light" style='margin-top: 10px !important'
		                       onclick='emitirEspelho();'>
		                       <img
		                           src="${ctx}/template_new/images/icons/small/grey/printer.png">
		                       <span>Espelho da Autorização de Compra</span>
		                   </button>
		             </div>
              </div>
              </div>
            </div>
        </div>
    </form>
</div>
</body>

</html>