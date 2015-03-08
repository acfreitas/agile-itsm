<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="java.util.Collection"%>

<!doctype html public "">
<html>
<head>
<%
			//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
			String iframe = "";
			iframe = request.getParameter("iframe");

            String idCotacao = (String)request.getAttribute("idCotacao");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/boxover.js"></script>
<!--<![endif]-->
<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script>
	$(function() {
	    $("#POPUP_RESULTADO").dialog({
	        autoOpen : false,
	        width : 800,
	        height : 450,
	        modal : true
	    });
	});

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			document.formResultado.idCotacao.value = '<%=idCotacao%>';
		}
	    document.form.afterLoad = function() {
	    	exibirResultadoItem('');
        }
	}

    function calcularResultado() {
    	if (!confirm(i18n_message("cotacao.mensagemCalculo")))
        	return;
    	JANELA_AGUARDE_MENU.show();
    	document.formResultado.idCotacao.value = '<%=idCotacao%>';
        document.formResultado.fireEvent("calculaResultado");
    }

    function publicarResultado() {
        if (!confirm(i18n_message("cotacao.mensagemPublicacao")))
            return;
        JANELA_AGUARDE_MENU.show();
        document.formResultado.idCotacao.value = '<%=idCotacao%>';
        document.formResultado.fireEvent("publicaResultado");
    }

    var rowItem = null;
    function exibirResultado(row, obj) {
        if (rowItem != null)
            rowItem.style.background = '';
        rowItem = row;
        row.style.background = '#FFCC99';
        var id = obj.idItemCotacao;
        if (id == '')
            return;
        document.formResultado.idCotacao.value = '<%=idCotacao%>';
        document.formResultado.idItemCotacao.value = id;
        document.formResultado.fireEvent('exibeResultado');
    }

    function exibirResultadoItem(idItem) {
        var i = 1;
        var tbl = document.getElementById("tblItensCotacao");
        if (idItem != '') {
        	var itens = HTMLUtils.getObjectsByTableId('tblItensCotacao');
        	if (!itens)
            	return;
        	for (l = 0; l < itens.length; l++) {
        	    if (itens[l].idItemCotacao == parseInt(idItem))
            	    break;
        	    i++;
			}
        }
        if (i == 0)
            return;
        var row = tbl.rows[i];
        var obj = HTMLUtils.getObjectByTableIndex("tblItensCotacao", row.rowIndex);
        exibirResultado(row, obj);
    }

    function gerarImgResultados(row, obj) {
        var title = '';
        if (obj.mensagensFmtHTML != '')
            title = 'title="header=[Problemas detectados] body=[' + obj.mensagensFmtHTML + ']"';
        row.cells[0].innerHTML = '<img style="cursor: pointer" src="'+obj.imagem+'"' + title + ';/>';
    }

    function exibirDefinicaoResultado(idColetaPreco) {
        document.formResultado.idColetaPreco.value = idColetaPreco;
        document.formResultado.fireEvent('exibeDefinicaoResultado');
    }

    function configurarTelaResultado() {
    	document.formResultado.idCotacao.value = '<%=idCotacao%>';
        document.formResultado.fireEvent('configuraTelaResultado');
    }

    function definirResultado() {
    	document.formResultado.idCotacao.value = '<%=idCotacao%>';
        document.formResultado.fireEvent('defineResultado');
    }
    function reabrirColeta() {
        if (!confirm(i18n_message("cotacao.mensagemReaberturaColeta")))
            return;
        JANELA_AGUARDE_MENU.show();
        document.formResultado.idCotacao.value = '<%=idCotacao%>';
        document.formResultado.fireEvent("reabreColetaPrecos");
    }

</script>
<style>
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
<div class="box grid_16 tabs" style="width:100%">
	<form name='form'
		action='${ctx}/pages/resultadoCotacao/resultadoCotacao'>
		<div class="columns clearfix">
            <div class="section">
                <div class="col_100">
	                <div class="col_40">
	                    <label>&nbsp;</label>
	                </div>
	                <div class="col_60" style='padding: 10px 0px 0px 0px'>
	                     <button type='button' name='btnCalcularResultado' class="light"
	                         onclick='calcularResultado();'>
	                         <img
	                             src="${ctx}/template_new/images/icons/small/grey/cog_4.png">
	                         <span><fmt:message key="cotacao.calcularResultado" />
	                         </span>
	                     </button>
	                     <button type='button' name='btnPublicarResultado' class="light"
	                         onclick='publicarResultado();'>
	                         <img
	                             src="${ctx}/template_new/images/icons/small/grey/price_tags.png">
	                         <span><fmt:message key="cotacao.publicarResultado" />
	                         </span>
	                     </button>
                         <button type='button' name='btnReabrirColeta' class="light"
                             onclick='reabrirColeta();'>
                             <img
                                 src="${ctx}/template_new/images/icons/small/grey/unlocked.png">
                             <span><fmt:message key="cotacao.reabrirColeta" />
                             </span>
                         </button>
	                </div>
                </div>
                <div class="col_100">
		              <div class="col_40">
	                     <h2 class="section">
	                         <fmt:message key="cotacao.itens" />
	                     </h2>
	                     <div class="col_100" style='height:190px;overflow:auto' >
				               <table id="tblItensCotacao" class="table">
				                   <tr>
				                       <th width="1%"></th>
				                       <th colspan="2"><fmt:message key="itemRequisicaoProduto.produto" /></th>
				                       <th width="10%"><fmt:message key="itemRequisicaoProduto.marca" /></th>
				                       <th width="5%"><fmt:message key="itemRequisicaoProduto.quantidade" /></th>
				                   </tr>
				               </table>
	                     </div>
		              </div>
                      <div class="col_60">
                         <h2 class="section">
                             <fmt:message key="cotacao.resultados" />
                         </h2>
                         <div id="divResultado" class="col_100" style='padding:5px;height:190px;overflow:auto' >
                         </div>
                      </div>
                </div>
            </div>
        </div>
    </form>
</div>

<div id="POPUP_RESULTADO" title="<fmt:message key="coletaPreco.definirResultado" />"  style="overflow: hidden;">
    <form name='formResultado'
        action='${ctx}/pages/resultadoCotacao/resultadoCotacao'>
        <input type='hidden' name='idCotacao' id='idCotacao' value='<%=idCotacao%>'/>
        <input type='hidden' name='idItemCotacao' id='idItemCotacao'/>
        <input type='hidden' name='idColetaPreco' id='idColetaPreco'/>
        <input type='hidden' name='resultadoCalculo' id='resultadoCalculo'/>

        <div class="col_100">
             <h2 class="section">
                <fmt:message key="coletaPreco.calculo" />
             </h2>
        </div>
        <div class="col_100">
             <div class="col_33">
                 <fieldset>
                     <label style="cursor: pointer;"><fmt:message key="coletaPreco.pontuacao" /></label>
                     <div>
                          <input id="pontuacao"  type='text'  name="pontuacao" readonly="readonly" class="Format[Moeda]"/>
                     </div>
                 </fieldset>
              </div>
              <div class="col_33">
                 <fieldset>
                     <label style="cursor: pointer;"><fmt:message key="coletaPreco.resultado" /></label>
                     <div>
                          <input id="resultadoCalculoStr"  type='text'  name="resultadoCalculoStr" readonly="readonly" />
                     </div>
                 </fieldset>
              </div>
              <div class="col_33">
                 <fieldset>
                     <label style="cursor: pointer;"><fmt:message key="coletaPreco.quantidade" /></label>
                     <div>
                          <input id="quantidadeCalculo"  type='text'  name="quantidadeCalculo" readonly="readonly" class="Format[Moeda]"/>
                     </div>
                 </fieldset>
              </div>
        </div>
        <div class="col_100">
             <h2 class="section">
                <fmt:message key="coletaPreco.definicao" />
             </h2>
        </div>
        <div class="col_100">
             <div class="col_75" >
                 <fieldset >
                     <label style='cursor:pointer'><input type='radio' name="resultadoFinal" value="M" onclick='configurarTelaResultado();' ><fmt:message key="coletaPreco.melhorCotacao"/></label>
                     <label style='cursor:pointer'><input type='radio' name="resultadoFinal" value="D" onclick='configurarTelaResultado();' ><fmt:message key="coletaPreco.desclassificada"/></label>
                 </fieldset>
             </div>
             <div class="col_25">
                <fieldset >
                    <label style="cursor: pointer;"><fmt:message key="coletaPreco.quantidade" /></label>
                    <div>
                         <input id="quantidadeCompra"  type='text'  name="quantidadeCompra" class="Format[Moeda]" onchange='configurarTelaResultado()'/>
                    </div>
                </fieldset>
             </div>
        </div>
        <div id="divJustificativa" class="col_100" style='display:none'>
            <div class="col_33">
                 <fieldset >
                     <label><fmt:message key="coletaPreco.justificativa" /></label>
                     <div>
                         <select id='idJustifResultado'  name='idJustifResultado'></select>
                     </div>
                 </fieldset>
            </div>
            <div class="col_66">
                <fieldset >
                    <label><fmt:message key="coletaPreco.complementoJustificativa" /></label>
                    <div>
                         <textarea id="complemJustifResultado" name="complemJustifResultado" cols='60' rows='2'></textarea>
                    </div>
                </fieldset>
            </div>
        </div>
        <div class="col_100" >
           <div class="col_75">
                <fieldset >
                     <label>
                         &nbsp;
                     </label>
                </fieldset>
           </div>
           <div class="col_25">
                <fieldset >
                    <div style="padding: 10px 0px 0px 10px">
                        <button type="button" onclick='$("#POPUP_RESULTADO").dialog("close")'>
                            <fmt:message key="citcorpore.comum.fechar" />
                        </button>
                        <button type="button" onclick='definirResultado()'>
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