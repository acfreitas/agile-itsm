<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao"%>
<!doctype html public "">
<html>
<head>
<%
			//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
			String iframe = "";
			iframe = request.getParameter("iframe");

            String idCotacao = (String)request.getAttribute("idCotacao");
            SituacaoCotacao situacao = (SituacaoCotacao)request.getAttribute("situacao");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>

<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="../../cit/objects/FornecedorDTO.js"></script>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

<script>
	var objTab = null;
	var popupFornecedor;
    var popupQualificacao;

	addEvent(window, "load", load, false);
	function load() {
		popupFornecedor = new PopupManager(1000, 680, "${ctx}/pages/");
		popupFornecedor.titulo = "<fmt:message key='fornecedor.cadastro' />";
		popupQualificacao = new PopupManager(1000, 680, "${ctx}/pages/");
		popupQualificacao.titulo = "<fmt:message key='menu.nome.qualificacaoFornecedor' />";
		document.form.afterRestore = function() {
		}
	}

    function LOOKUP_FORNECEDOR_select(id, desc){
        var fornecedoresSelecionados = [];
        var fornecedorDto = new CIT_FornecedorDTO();
        fornecedorDto.idFornecedor = id;
        fornecedoresSelecionados.push(fornecedorDto);
        document.form.colFornecedores_Serialize.value = ObjectUtils.serializeObjects(fornecedoresSelecionados);
        document.form.save();
        //$("#POPUP_FORNECEDOR").dialog("close");
    }

    $(function() {
        $("#POPUP_SUGESTAO_FORNECEDORES").dialog({
            autoOpen : false,
            width : 1000,
            height : 400,
            modal : true
        });
        $("#POPUP_FORNECEDOR").dialog({
            autoOpen : false,
            width : 600,
            height : 400,
            modal : true
        })
    });

    function abrirSugestaoFornecedores() {
        document.getElementById('btnSelecionarFornecedores').style.display = 'none';
        document.form.fireEvent('sugereFornecedores');
    }

    function gerarImgExclusaoFornec(row, obj) {
        <%if (situacao != null && situacao.equals(SituacaoCotacao.EmAndamento)){%>
            row.cells[0].innerHTML = '<img src="${ctx}/imagens/delete.png" onclick="excluirFornecedor(' + row.rowIndex + ')"/>';
        <%}else{%>
            row.cells[0].innerHTML = '&nbsp;';
        <%}%>
    }

    function excluirFornecedor(indice) {
        if (indice > 0 && confirm('Confirma a exclusão')) {
            var obj = HTMLUtils.getObjectByTableIndex('tblFornecedoresCotacao', indice);
            document.form.idFornecedor.value = obj.idFornecedor;
            document.form.fireEvent('delete');
        }
    }

    function marcarDesmarcar(chk, indice, tbl) {
        var obj = HTMLUtils.getObjectByTableIndex(tbl, indice);
        if (chk.checked)
            obj.selecionado = 'S';
        else
            obj.selecionado = 'N';
    }

    function gerarSelecaoFornecedor(row, obj){
        obj.selecionado = 'N';
        row.cells[0].innerHTML = "<input type='checkbox' name='chkSel_"+obj.idFornecedor+"' id='chkSel_"+obj.idFornecedor+"' onclick='marcarDesmarcar(this,"+row.rowIndex+",\"tblFornecedores\")' />";
    }

    function marcarDesmarcarTodosFornecedores(chk) {
        var fornecedores = HTMLUtils.getObjectsByTableId('tblFornecedores');
        if (fornecedores == null)
            return;
        for(i=0;i<fornecedores.length;i++){
            var obj = fornecedores[i];
            if (chk.checked)
                obj.selecionado = 'S';
            else
                obj.selecionado = 'N';
            document.getElementById('chkSel_'+obj.idFornecedor).checked = chk.checked;
        }
    }

    function incluirFornecedores() {
        var fornecedores = HTMLUtils.getObjectsByTableId('tblFornecedores');
        if (fornecedores == null)
            return;
        var fornecedoresSelecionados = [];
        for(i=0;i<fornecedores.length;i++){
            var obj = fornecedores[i];
            if (obj.selecionado == 'S')
                fornecedoresSelecionados.push(obj);
        }
        if (fornecedoresSelecionados.length == 0) {
            alert(i18n_message("citcorpore.comum.nenhumaSelecao"));
            return;
        }
        document.form.colFornecedores_Serialize.value = ObjectUtils.serializeObjects(fornecedoresSelecionados);
        document.form.save();
        $("#POPUP_SUGESTAO_FORNECEDORES").dialog("close");
    }

    $(function() {
        $("#addFornecedor").click(function() {
            $("#POPUP_FORNECEDOR").dialog("open");
        });
    });

</script>
<style>

    .linhaSubtituloGrid
    {
        color			:#000000;
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
        margin: 10px 10px 10px 10px;
        width: 100%;
    }
</style>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>
<div class="box grid_16 tabs" style='width:100%;margin: 0px 0px 0px 0px;'>
    <form name='form'
        action='${ctx}/pages/fornecedorCotacao/fornecedorCotacao'>
        <div class="columns clearfix">
            <input type='hidden' name='idCotacao' id='idCotacao' value='<%=idCotacao%>'/>
            <input type='hidden' name='idFornecedor' id='idFornecedor'/>
            <input type='hidden' name='colFornecedores_Serialize' />

            <div class="section">
                <div class="col_100">
                    <div class="col_40">
                        <label>&nbsp;</label>
                    </div>
                    <div class="col_60">
                        <div style='padding: 5px 5px 5px 5px'>
                           <button type='button' name='btnCadFornecedor' class="light" onclick="popupFornecedor.abrePopup('fornecedor', '()')">
                               <img
                                   src="${ctx}/template_new/images/icons/small/grey/create_write.png">
                               <span><fmt:message key="cotacao.novoFornecedor" />
                               </span>
                           </button>
                           <button type='button' name='btnQualifFornecedor' class="light" onclick="popupQualificacao.abrePopup('avaliacaoFornecedor', '()')">
                               <img
                                   src="${ctx}/template_new/images/icons/small/grey/facebook_like.png">
                               <span><fmt:message key="cotacao.qualificacaoFornecedor" />
                               </span>
                           </button>
                           <button type='button' name='btnAddFornecedor' class="light" id="addFornecedor">
                               <img
                                   src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
                               <span><fmt:message key="cotacao.adicionarFornecedor" />
                               </span>
                           </button>
                           <button type='button' name='btnSugerirFornecedores' id='btnSugerirFornecedores' class="light"
                               onclick='abrirSugestaoFornecedores();'>
                               <img
                                   src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
                               <span><fmt:message key="cotacao.sugerirFornecedores" />
                               </span>
                           </button>
                        </div>
                    </div>
                </div>
                <h2 class="section">
                    <fmt:message key="cotacao.fornecedores" />
                </h2>
               <div class="col_100" style='height:200px'>
                   <table id="tblFornecedoresCotacao" class="table">
                       <tr>
                           <th width="1%"></th>
                           <th width="15%"><fmt:message key="fornecedor.cpfcnpj" /></th>
                           <th ><fmt:message key="citcorpore.comum.nome" /></th>
                           <th width="15%"><fmt:message key="citcorpore.comum.telefone" /></th>
                           <th width="15"><fmt:message key="citcorpore.comum.email" /></th>
                           <th width="15%"><fmt:message key="fornecedor.contato" /></th>
                           <th width="5%"><fmt:message key="fornecedor.qualificado" /></th>
                       </tr>
                   </table>
               </div>
            </div>
      </div>

      <div id="POPUP_SUGESTAO_FORNECEDORES" title="<fmt:message key="fornecedor.pesquisa" />"  style="overflow: hidden;">
            <div class="columns clearfix">
                <h2 class="section">
                    <fmt:message key="cotacao.fornecedoresSugeridos" />
                </h2>

                 <div class="col_100" style='height:200px'>
                     <table id="tblFornecedores" class="table">
                         <tr>
                             <th width="1%"><input type='checkbox' name='chkSelTodosFornecedores' onclick='marcarDesmarcarTodosFornecedores(this)' /></th>
                             <th width="15%"><fmt:message key="fornecedor.cpfcnpj" /></th>
                             <th ><fmt:message key="citcorpore.comum.nome" /></th>
                             <th width="15%"><fmt:message key="citcorpore.comum.telefone" /></th>
                             <th width="15"><fmt:message key="citcorpore.comum.email" /></th>
                             <th width="15%"><fmt:message key="fornecedor.contato" /></th>
                         </tr>
                     </table>
                 </div>
                 <div class="col_100">
                     <button type='button' name='btnSelecionarFornecedores' id='btnSelecionarFornecedores' class="light"
                         onclick='incluirFornecedores();'>
                         <img
                             src="${ctx}/template_new/images/icons/small/grey/create_write.png">
                         <span><fmt:message key="citcorpore.comum.confirmar" />
                         </span>
                     </button>
                 </div>
           </div>
      </div>
    </form>
    <div id="popupCadastroRapido">
        <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="95%">
        </iframe>
    </div>
</div>

</body>

<div id="POPUP_FORNECEDOR" title="<fmt:message key="citcorpore.comum.pesquisa" />">
        <div class="box grid_16 tabs">
            <div class="toggle_container">
                <div id="tabs-2" class="block">
                    <div class="section">
                        <form name='formPesquisaFornecedor' style="width: 540px">
                            <cit:findField formName='formPesquisaFornecedor'
                                lockupName='LOOKUP_FORNECEDOR' id='LOOKUP_FORNECEDOR' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
                        </form>
                    </div>
                </div>
            </div>
        </div>
</div>

</html>