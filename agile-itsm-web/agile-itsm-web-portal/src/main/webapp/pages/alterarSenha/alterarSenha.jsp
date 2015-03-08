<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
	</head>
	<body>
		<div class="container-fluid fixed ">

			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar main hidden-print">

				<%@include file="/novoLayout/common/include/cabecalho.jsp" %>

				<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>

			</div>

			<div id="wrapper">
			<div class="widget-body">

				<!-- Inicio conteudo -->
				<div id="content">
					<form style="margin: 0;" class="form-horizontal" name='form' action='${ctx}/pages/usuario/usuario'>
						<div style="padding: 0;" class="tab-content">
								<input type='hidden' name='idUsuario' id='idUsuario'/>
								<input type='hidden' name='idEmpresa' id='idEmpresa'  />
								<input type='hidden' name='idEmpregado' id='idEmpregado' />
								<input type='hidden' name='dataInicio' id='dataInicio' />
								<input type='hidden' name='dataFim' id='dataFim' />
								<input type='hidden' name='status' id='status'  />

								<!-- Tab content -->
								<div id="account-details" class="tab-pane active">

									<!-- Row -->
									<div class="row-fluid">

										<!-- Column -->
										<div class="span6">

											<!-- inicio senha -->
											<div class="control-group">
												<label class="control-label"> <span><fmt:message key="alterarSenha.novaSenha"/></span></label>
												<div class="controls">
													<input value="" id="senha" type="password" name="senha" maxlength="20" class="Valid[Required] Description[alterarSenha.senhaNovamente]" class="span10">
													<span data-original-title="" data-placement="top" data-toggle="tooltip" class="btn-action single glyphicons circle_question_mark" style="margin: 0;"><i></i></span>
												</div>
											</div>
											<!-- // fim senha -->

											<!-- Inicio repetir senha -->
											<div class="control-group">
												<label class="control-label"><fmt:message key="alterarSenha.senhaNovamente"/></label>
												<div class="controls">
													<input id="senhaNovamente" type="password" name="senha_novamente" maxlength="20" class="Valid[Required] Description[alterarSenha.senhaNovamente]" class="span10">
													<span data-original-title="Last name is mandatory" data-placement="top" data-toggle="tooltip" class="btn-action single glyphicons circle_question_mark" style="margin: 0;"><i></i></span>
												</div>
											</div>
											<!-- // fim  repetir senha -->

									<!-- Form actions -->
									<div style="margin: 0;" class="form-actions">
										<button class="btn btn-icon btn-primary glyphicons circle_ok" type="button" onclick='validar();'><i></i><fmt:message key="citcorpore.comum.gravar" /></button>
										<button class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick='document.form.clear();'><i></i><fmt:message key="citcorpore.ui.botao.rotulo.Limpar" /></button>
									</div>
									<!-- // Form actions END -->

								</div>
								<!-- // Tab content END -->

								</div>
								<!-- // Tab content END -->
							</div>
						</div>
					</form>
				</div>
			</div>
				<!--  Fim conteudo-->

				<%@include file="/novoLayout/common/include/rodape.jsp" %>
				<script type="text/javascript" src="${ctx}/pages/alterarSenha/js/alterarSenha2.js" ></script>
			</div>
		</div>
	</body>
</html>
