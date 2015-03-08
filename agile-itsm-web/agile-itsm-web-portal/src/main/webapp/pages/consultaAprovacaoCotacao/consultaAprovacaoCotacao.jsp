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
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
	}

    var offColor = '';
    function TrowOff(src){
        src.style.background = offColor;
    }

    function TrowOn(src,OnColor){
        offColor = src.style.background;
        src.style.background = OnColor;
    }


    function reabrirColeta() {
        if (!confirm(i18n_message("cotacao.mensagemReaberturaColeta")))
            return;
        JANELA_AGUARDE_MENU.show();
        document.form.idCotacao.value = '<%=idCotacao%>';
        document.form.fireEvent("reabreColetaPrecos");
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
        action='${ctx}/pages/consultaAprovacaoCotacao/consultaAprovacaoCotacao'>
        <div class="columns clearfix">
            <input type='hidden' name='idCotacao' id='idCotacao' value='<%=idCotacao%>'/>
	        <div class="section">
	            <div class="col_100">
	                <div class="col_80">
	                    <label>&nbsp;</label>
	                </div>
	                <div id="divReabrirColeta" class="col_20" style='display:none;padding: 10px 0px 0px 0px'>
	                     <button type='button' name='btnReabrirColeta' class="light"
	                         onclick='reabrirColeta();'>
	                         <img
	                             src="${ctx}/template_new/images/icons/small/grey/unlocked.png">
	                         <span><fmt:message key="cotacao.reabrirColeta" />
	                         </span>
	                     </button>
	                </div>
	            </div>
	            <h2 class="section">
	                <fmt:message key="cotacao.coletasPrecosHabilitadas" />
	            </h2>
	            <div id="divAprovacoes" class="col_100" style='height:350px;overflow:auto;'>
	           </div>
	        </div>
        </div>
    </form>
</div>
</body>

</html>