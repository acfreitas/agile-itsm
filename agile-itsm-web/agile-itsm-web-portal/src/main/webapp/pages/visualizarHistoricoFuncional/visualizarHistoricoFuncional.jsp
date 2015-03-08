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
		<link type="text/css" rel="stylesheet" href="css/visualizarHistoricoFuncional.css"/>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div>
			<div id="wrapper">
			<!--Inicio conteudo -->
					<div class="widget">
						<div class="row-fluid">
							<div class="span12">
								<div class="widget-body collapse in">
									<form name="form" action='${ctx}/pages/visualizarHistoricoFuncional/visualizarHistoricoFuncional'>
										<input type='hidden' name='idCandidato' id='idCandidato' />
										<input type='hidden' name='idResponsavel' id='idResponsavel' />
										<input type='hidden' name='idHistoricoFuncional' id='idHistoricoFuncional' />
										
										<div class="innerTB">
											<label class="historico strong "><fmt:message key="citcorpore.comum.historico"/> -</label>
											<label class="historico strong " name="nome" id="nome"></label>
										</div>
										<div id="divItens">
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>						
			<!--Fim conteudo -->
			<script src="${ctx}/pages/visualizarHistoricoFuncional/js/visualizarHistoricoFuncional.js"></script>
			</div>
		</div>
	</body>
</html>