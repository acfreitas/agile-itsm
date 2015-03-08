<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>

<!doctype html public "">
<html>
	<head>
		<%
			String iframe = "";
		    iframe = request.getParameter("iframe");
		%>
		<%@include file="/include/header.jsp"%>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>

		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<%
		    //se for chamado por iframe deixa apenas a parte de cadastro da página
		    if (iframe != null) {
		%>
		<link rel="stylesheet" type="text/css" href="./css/caracteristica.css" />
		<%
		    }
		%>

		<script type="text/javascript" src="./js/caracteristica.js"></script>
	</head>
	<body>
		<div id="wrapper">
			<%
			    if (iframe == null) {
			%>
			<%@include file="/include/menu_vertical.jsp"%>
			<%
			    }
			%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%
				    if (iframe == null) {
				%>
				<%@include file="/include/menu_horizontal.jsp"%>
				<%
				    }
				%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="citcorpore.comum.caracteristica"/></h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="caracteristica.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><fmt:message key="caracteristica.pesquisa"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/caracteristica/caracteristica'>
									<div class="columns clearfix">
										<input type='hidden' id="idCaracteristica" name='idCaracteristica'/>
										<input type='hidden' name='idEmpresa'/>
										<input type='hidden' name='dataInicio'/>
										<input type='hidden' name='dataFim'/>
										<input type='hidden' name='tipo'/>

										<div class="columns clearfix">
											<div class="col_40">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.caracteristica"/></label>
														<div>
														  	<input type='text' name="nome" maxlength="255" size="255" class="Valid[Required] Description[citcorpore.comum.caracteristica]" />
														</div>
												</fieldset>
											</div>
											<div class="col_20">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.tag"/></label>
														<div>
														  	<input type='text' name="tag" maxlength="255" size="255" class="Valid[Required] Description[Tag]" />
														</div>
												</fieldset>
											</div>
											<div class="col_40">
												<fieldset>
													<label><fmt:message key="citcorpore.comum.descricao"/></label>
														<div>
														  	<input type='text' name="descricao" maxlength="4000" size="4000"/>
														</div>
												</fieldset>
											</div>
										</div>
										<br>
									</div>
									<br><br>
									<button type='button' name='btnGravar' class="light"  onclick='save();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar"/></span>
									</button>
									<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
										<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
										<span><fmt:message key="citcorpore.comum.excluir"/></span>
									</button>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_CARACTERISTICA' id='LOOKUP_CARACTERISTICA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>


