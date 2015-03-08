<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor"%>

<!DOCTYPE html>
<compress:html
	enabled="true"
	jsCompressor="closure"
	compressCss="true"
	compressJavaScript="true"
	removeComments="true"
	removeMultiSpaces="true">
<html>
	<head>
	<%
	    	String iframe = "";
			iframe = request.getParameter("iframe");
			String subSolicitacao = "";
			subSolicitacao = request.getParameter("subSolicitacao");
		%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>

		 <link rel="stylesheet" type="text/css" href="./css/origemAtendimento.css">

	</head>
	<body>
		<div class="container-fluid fixed ">

			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
		<%if (iframe == null) {%>
			<div class="navbar main hidden-print">
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
			</div>
			<%}%>

			<div id="wrapper">
					<%if (iframe == null) {%>
				<!-- Inicio conteudo -->
				<div id="content">
				<%} else{%>
				<div id="toggle_container">
				<%} %>
				<h3><fmt:message key="origemAtendimento.origem" /></h3>
				<div class="box-generic">

						<!-- Tabs Heading -->
						<div class="tabsbar">
							<ul>
								<li class="active"><a href="#tab1" data-toggle="tab"><fmt:message key="origemAtendimento.cadastroOrigem" /></a></li>
								<li  class=""><a href="#tab2" data-toggle="tab"><fmt:message key="origemAtendimento.pesquisaOrigem" /></a></li>
							</ul>
						</div>
						<!-- // Tabs Heading END -->
						<form name="form" id="form" action="${ctx}/pages/origemAtendimento/origemAtendimento">
						<div class="tab-content">

							<!-- Tab content -->
							<div class="tab-pane active" id="tab1">

									<input type='hidden' name='idOrigem' id='idOrigem' />
									<input type='hidden' name='dataInicio' id="dataInicio" />
									<input type='hidden' name='dataFim' id="dataFim" />
									<input type='hidden' name='subSolicitacao' id='subSolicitacao' value="<%=subSolicitacao%>"/>
									<div class="row-fluid">
										<div class="span5">
											<label  class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.nome"/></label>
											  	<input placeholder="" class="span10" id="descricao" required  type="text" name="descricao" maxlength="100">
										</div>
									</div>
									<div style="margin: 0;" class="form-actions">
										<button class="btn  btn-primary " type="button" onclick='savarDados();'><i></i><fmt:message key="citcorpore.comum.gravar" /></button>
										<button class="btn  btn-primary " type="button" onclick='document.form.clear();;'><i></i><fmt:message key="citcorpore.comum.limpar" /></button>
										<button class="btn  btn-primary " type="button" onclick='excluir();;'><i></i><fmt:message key="citcorpore.comum.excluir" /></button>
									</div>


							</div>
							<!-- // Tab content END -->

							<!-- Tab content -->
							<div class="tab-pane" id="tab2">
								<cit:findField id="LOOKUP_ORIGEMATENDIMENTO"
										formName="form"
										lockupName="LOOKUP_ORIGEMATENDIMENTO"
										top="0"
										left="0"
										len="550"
										heigth="400"
										javascriptCode="true"
										htmlCode="true" />
							</div>
							<!-- // Tab content END -->


						</div>
							</form>
				</div>
				</div>
				<!--  Fim conteudo-->

				<%@include file="/novoLayout/common/include/rodape.jsp" %>

			</div>
		</div>

		<script type="text/javascript" src="js/origemAtendimento.js"></script>
	</body>
</html>
</compress:html>
