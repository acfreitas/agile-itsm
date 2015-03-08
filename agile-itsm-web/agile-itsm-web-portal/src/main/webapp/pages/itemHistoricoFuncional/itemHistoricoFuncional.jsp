<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.citframework.util.UtilStrings"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>

		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="css/itemHistoricoFuncional.css"/>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div>
			<div id="wrapper">
			<!--Inicio conteudo -->
					<div class="widget">
						<div class="widget-head">
							<h4 class="heading"><fmt:message key="rh.itemHistorico"/></h4>
						</div>
						<div class="row-fluid">
							<div class="widget-body collapse in">
								<form name="form" action='${ctx}/pages/itemHistoricoFuncional/itemHistoricoFuncional'>
									<input type='hidden' name='idItemHistoricoFuncional' id='idItemHistoricoFuncional' />
									<input type='hidden' name='idHistoricoFuncional' id='idHistoricoFuncional' />
									
										<div class="innerLR">
											<div class="row-fluid">
												<div class="span12">
												<label for="titulo" class='strong campoObrigatorio'><fmt:message key="baseConhecimento.titulo"/>:</label>
												<input id="titulo" type="text" name="titulo" maxlength="100" class="Valid[Required] Description[baseConhecimento.titulo] tamanho_label"/>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span12">
												<label for="descricao" class='strong campoObrigatorio'><fmt:message key="citcorpore.comum.descricao"/>:</label>
												<textarea id="descricao" name="descricao" rows="10" cols="80" class="Valid[Required] Description[citcorpore.comum.descricao] tamanho_label" maxlength="500"></textarea>
												</div>
											</div>
										</div>
										<div class="innerTB">
											<div class="innerLR">
												<button class="btn btn-icon btn-primary" type="button" onclick="document.form.save();"><fmt:message key="questionario.salvar"/></button>
												<button class="btn" type="button" onclick="cancelar();"><fmt:message key="citcorpore.comum.cancelar"/></button>
											</div>
										</div>
								</form>
							</div>
						</div>
					</div>						
			<!--Fim conteudo -->
			<div style="display: none;">
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
			<script src="${ctx}/pages/itemHistoricoFuncional/js/itemHistoricoFuncional.js"></script>
			</div>
		</div>
	</body>
</html>