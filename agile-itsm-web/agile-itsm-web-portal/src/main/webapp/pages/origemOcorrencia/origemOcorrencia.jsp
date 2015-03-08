<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ page import="br.com.citframework.util.UtilI18N" %>

<html>
	<head>
	<%
	    	String iframe = "";
			iframe = request.getParameter("iframe");
		%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>

		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<script type="text/javascript">
		var objTab = null;
		addEvent(window, "load", load, false);

		function load() {
			document.formOrigemOcorrencia.afterRestore = function() {
				$('.tabsbar li:eq(0) a').tab('show')
			}
		}

		function excluir() {
			if (document.getElementById("idOrigemOcorrencia").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
					document.formOrigemOcorrencia.fireEvent("delete");
				}
			}
		}
		function LOOKUP_ORIGEM_OCORRENCIA_select(id, desc) {
			document.formOrigemOcorrencia.restore({
				idOrigemOcorrencia: id
			});
		}

		</script>
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

				<!-- Inicio conteudo -->
				<div id="content">
				<%if (iframe == null) {%>
				<h3><fmt:message key="citcorpore.comum.origemOcorrencia" /></h3>
				<%}%>
				<div class="box-generic">

						<!-- Tabs Heading -->
						<div class="tabsbar">
							<ul>
								<li class="active"><a href="#tab1" data-toggle="tab"><fmt:message key="citcorpore.comum.cadastroOrigemOcorrencia" /></a></li>
								<li  class=""><a href="#tab2" data-toggle="tab"><fmt:message key="citcorpore.comum.pesquisaOrigemOcorrencia" /></a></li>
							</ul>
						</div>
						<!-- // Tabs Heading END -->
						<form name="formOrigemOcorrencia" action="${ctx}/pages/origemOcorrencia/origemOcorrencia">
						<div class="tab-content">

							<!-- Tab content -->
							<div class="tab-pane active" id="tab1">

									<input type="hidden" id="idOrigemOcorrencia" name="idOrigemOcorrencia" />
									<input type="hidden" id="dataInicio" name="dataInicio" />
									<input type="hidden" id="dataFim" name="dataFim" />
								<%-- 	<div class="control-group">
										<label class="strong"> <span><fmt:message key="citcorpore.comum.nome"/></span></label>
										<div class="span12">
											<input value="" id="nome" type="text" name="nome" maxlength="20" class="Valid[Required] Description[<%= UtilI18N.internacionaliza(request, "citcorpore.comum.nome") %>] span12" class="span10">
										</div>
									</div> --%>
									<div class="row-fluid">
										<div class="span5">
											<label  class="strong"><fmt:message key="citcorpore.comum.nome"/></label>
											  	<input placeholder="" class="span10 Valid[Required] Description[<%= UtilI18N.internacionaliza(request, "citcorpore.comum.nome") %>]" id="nome" required  type="text" name="nome">
										</div>
									</div>
									<div style="margin: 0;" class="form-actions">
										<button class="btn btn-icon btn-primary glyphicons circle_ok" type="button" onclick='document.formOrigemOcorrencia.save();'><i></i><fmt:message key="citcorpore.comum.gravar" /></button>
										<button class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick='document.formOrigemOcorrencia.clear();'><i></i><fmt:message key="citcorpore.comum.limpar" /></button>
										<button class="btn btn-icon btn-default glyphicons remove_2" type="button" onclick='excluir();'><i></i><fmt:message key="citcorpore.comum.excluir" /></button>
									</div>


							</div>
							<!-- // Tab content END -->

							<!-- Tab content -->
							<div class="tab-pane" id="tab2">
								<cit:findField id="LOOKUP_ORIGEM_OCORRENCIA"
										formName="formOrigemOcorrencia"
										lockupName="LOOKUP_ORIGEM_OCORRENCIA"
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
	</body>
</html>