<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<!doctype html public "">
<html>
	<head>	
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="css/justificativaListaNegra.css"/>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>
				<div class="flat_area grid_16">
					<h2>
						<fmt:message key="rh.justificativaListaNegra"/>
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li><a href="#tabs-1"><fmt:message key="rh.cadastrarJustificativa"/></a></li>
						<li><a href="#tabs-2" class="round_top"><fmt:message key="rh.pesquisarJustificativa"/></a></li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/justificativaListaNegra/justificativaListaNegra'>
									<input type='hidden' id='idJustificativa' name='idJustificativa' />
									
									<div class="innerLR">
										<div class="row-fluid">
											<div class="span6">
												<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.justificativa" /></label>
											</div>
										</div>
										<div class="row-fluid">
											<div class="span6">
												<input type='text' name="justificativa" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</div>
										<div class="row-fluid">
											<div class="span6">
												<label class="strong campoObrigatorio" style="margin-top: 5px;"><fmt:message key="citcorpore.comum.situacao" /></label>
											</div>
										</div>
										<div class="row-fluid">
											<div class="span6">
												<label><input type="radio" name="situacao" value="A" checked="checked" class="Valid[Required] Description[citcorpore.comum.situacao]"/><fmt:message key="citcorpore.comum.ativo" /></label>
												<label><input type="radio" name="situacao" value="I" class="Valid[Required] Description[citcorpore.comum.situacao]"/><fmt:message key="citcorpore.comum.inativo" /></label>
											</div>
										</div>
										<div class="row-fluid">
											<div class="innerTB">
												<button type='button' name='btnGravar' class="light" onclick='document.form.save();'>
													<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
													<span><fmt:message key="citcorpore.comum.gravar" /></span>
												</button>
												<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
													<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
													<span><fmt:message key="citcorpore.comum.limpar" /></span>
												</button>
												<button type='button' name='btnExcluir' class="light" onclick='excluir();'>
													<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
													<span><fmt:message key="citcorpore.comum.excluir" /></span>
												</button>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section">
								<fmt:message key="citcorpore.comum.pesquisa" />
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_JUSTIFICATIVALISTANEGRA' 
									id='LOOKUP_JUSTIFICATIVALISTANEGRA' top='0' left='0' len='550' heigth='400' 
									javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</div>
				</div>
			</div>
			<!-- Fim da Pagina de Conteudo -->
		</div>
		<script type="text/javascript" src="js/justificativaListaNegra.js"></script>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>
