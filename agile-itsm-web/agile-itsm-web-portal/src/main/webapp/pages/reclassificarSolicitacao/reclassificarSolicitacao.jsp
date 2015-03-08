<html>
<head>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@ page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.util.CitCorporeConstantes" %>
<%@ page import="br.com.centralit.citcorpore.negocio.ParametroCorporeService" %>
<%@ page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO" %>
<%@ page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema" %>
<%@ page import="br.com.centralit.citcorpore.free.Free"%>
<%@page import="br.com.centralit.bpm.util.Enumerados"%>

<%
	String id = request.getParameter("id");
	String strRegistrosExecucao = (String) request
			.getAttribute("strRegistrosExecucao");
	if (strRegistrosExecucao == null) {
		strRegistrosExecucao = "";
	}

	String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil
			.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO,"");

	String tarefaAssociada = (String) request
			.getAttribute("tarefaAssociada");
	if (tarefaAssociada == null) {
		tarefaAssociada = "N";
	}
	String iframe = "";
	iframe = request.getParameter("iframe");
%>

<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>


<script src="../../novoLayout/common/include/js/solicitacaoServicoMultiContratos.js"></script>
<script src="../../novoLayout/common/include/js/canvas.js"></script>
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/solicitacaoServicoMultiContratos.css"/>
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
<script type="text/javascript" src="../../novoLayout/common/include/js/jquery.slimscroll.min.js"></script>

<script type="text/javascript">


</script>

<title>CITSMart</title>


</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body onload="">
<div class="wrapper" >

		<form name='form' id='form' action='${ctx}/pages/reclassificarServico/reclassificarServico'>
			<div class="row-fluid">
				<div class="span7">
				<!-- <label  class="strong">Nome do Serviço</label> -->
					<!-- <div class="input-append" >
					  	<input class="span6" id='servicoBusca'  name='servicoBusca' type="text" onblur="camposObrigatoriosSolicitacao();"  placeholder="Digite o nome do Solicitante">
					  	<button class="btn btn-default" type="button"><i class="icon-search"></i></button>
					</div> -->
					<div class="input-prepend input-append" id='divNomeDoServico'>
					  	<label  class="strong">Nome do Serviço</label>
					  	<input class="span12"  type="text" name="servicoBusca" id="servicoBusca" onblur="document.form.fireEvent('verificaImpactoUrgencia');carregaScript(this);document.form.fireEvent('verificaGrupoExecutor');carregarInformacoesComplementares();calcularSLA();"
							class=" Valid[Required] Description[<fmt:message key='servico.servico' />]" placeholder="Digite o nome do Solicitante" >
					  	<span class="add-on"><i class="icon-search"></i></span>
					</div>
					</div>
					<div class="span5">
					<label  class="strong">SLA</label>
					<div class='input-append'>
						 <span class='label large' id="tdResultadoSLAPrevisto"></span>
						<span  id="divMini_loading" ><img src="../../novoLayout/common/include/imagens/mini_loading.gif"></span>

					</div>
					</div>
			</div>
		</form>
</div>

			<%@include file="/novoLayout/common/include/libRodape.jsp" %>
</body>
</html>