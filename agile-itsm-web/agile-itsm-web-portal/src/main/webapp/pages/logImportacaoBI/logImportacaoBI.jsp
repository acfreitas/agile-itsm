<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA"%>
<%@ page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@ page import="br.com.centralit.citcorpore.free.Free"%>
<%@page import="br.com.centralit.bpm.util.Enumerados"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");

	String idConexaoBI = "";
	idConexaoBI = request.getParameter("idConexaoBI");
%>
<!doctype html public "">
<html>
	<head>
		<meta name="viewport" content="width=device-width" />
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<title><fmt:message key="citcorpore.comum.title" /></title>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>

		<style type="text/css">
			#itenPaginacao ul li {
				padding:  0px!important;
			}
			#itenPaginacao ul li font {
				padding:  6px!important;
				background-color: white!important;
				border: 2px  solid #ddd!important;
			}
			#itenPaginacao ul li font:HOVER {
				border: 2px  solid #74AF3B!important;
			}
			#itenPaginacao ul li font a{
				color: #74AF3B!important;
			}
		</style>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">

			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">

				<% if(iframe == null) { %>
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
				<% } %>

			</div>

			<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">
				<div id="content">
					<div class="separator top"></div>
					<div class="row-fluid">
						<div class="innerLR">
							<form class="form-horizontal" id='form' name='form' action='${ctx}/pages/logImportacaoBI/logImportacaoBI.load'>
								<input type="hidden" id="idConexaoBI" name="idConexaoBI" value="<%=idConexaoBI%>" />
								<input type="hidden" id="idLogImportacao" name="idLogImportacao" value="" />
								<input type='hidden' name='paginaSelecionada' id='paginaSelecionada'/>
								<div style="text-align: center;">
									<table id="tblLog" class='dynamicTable table table-striped table-bordered table-condensed dataTable' style='table-layout: fixed;'></table>
								</div>
								<br/>
								<div id="paginas" class="col_50" align="center"></div>
							</form>
							<form class="form-horizontal" id='formGetLogImportacaoBI' name='formGetLogImportacaoBI' action='${ctx}/pages/getLogImportacaoBI/getLogImportacaoBI.load'>
								<input type="hidden" id="idLogImportacao" name="idLogImportacao" value="" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			//Mudança na paginação
			function paginarItens (paginaSelecionada) {
				//Página selecionada
				document.form.paginaSelecionada.value = paginaSelecionada;

				//Preenche a tabela com a próxima página
				document.form.fireEvent("listInfoLogConexaoBI");
			}

			function baixarLog (idLogImportacao) {
				JANELA_AGUARDE_MENU.show();

				//Página selecionada
				document.formGetLogImportacaoBI.idLogImportacao.value = idLogImportacao;
				document.formGetLogImportacaoBI.submit();

				//Preenche a tabela com a próxima página
				//document.form.fireEvent("baixarLog");
				JANELA_AGUARDE_MENU.hide();
			}
		</script>
	</body>
</html>