<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema"%>
<%@page import="br.com.citframework.util.UtilStrings"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="css/blackList.css"/>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div>
			<div id="wrapper">
			<!--Inicio conteudo -->
					<div class="widget">
						<div class="widget-head">
							<h4 class="heading"><fmt:message key="rh.addRmvListaNegra"/></h4>
						</div>
						<div class="row-fluid">
							<div class="widget-body collapse in">
								<form name='form' action='${ctx}/pages/blackList/blackList'>
									<input type="hidden" id='idListaNegra' name='idListaNegra'>	
									<input type="hidden" id='idBlackList' name='idBlackList'>
									<input type="hidden" id='idCandidato' name='idCandidato'>
									<input type="hidden" id='idUsuario' name='idUsuario'>
									<input type="hidden" id='acao' name='acao'>
										
									<div id="divBlackList">									
										<div class="innerLR">
											<div id='informacoesCandidato'></div>
											<div class="row-fluid">
												<div class="span12">
													<label for="idJustificativa" class='strong campoObrigatorio'><fmt:message key="citcorpore.comum.justificativa"/>:</label>
													<select id="idJustificativa" name="idJustificativa" class="Valid[Required] Description[citcorpore.comum.justificativa]" ></select>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span12">
													<label for="descricao" class='strong campoObrigatorio'><fmt:message key="citcorpore.comum.descricao"/>:</label>
													<textarea id="descricao" name="descricao" rows="5" cols="80" class="Valid[Required] Description[citcorpore.comum.descricao] tamanho_label" maxlength="500"></textarea>
												</div>
											</div>
											<div class="innerTB">
												<div id='divInserirListaNegra'>
												<!-- S = Está na lista negra  -->
													<button type="button" id='btnInserirListaNegra' class='btn btn-warning' data-loading-text='Inserido.' onclick="gravar('S')"><fmt:message key="rh.inserirListaNegra" /></button>
													<button class="btn" type="button" onclick="parent.fecharModalAddBlackList();"><fmt:message key="citcorpore.comum.cancelar"/></button>
												</div>
												<div id='divRetirarListaNegra' >
												<!-- N = Não está na lista negra  -->
													<button type="button" id='btnRetirarListaNegra' class='btn btn-primary' data-loading-text='Retirado.' onclick="gravar('N')"><fmt:message key="rh.removerListaNegra" /></button>
													<button class="btn" type="button" onclick="parent.fecharModalAddBlackList();"><fmt:message key="citcorpore.comum.cancelar"/></button>
												</div>
											</div>
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
				<script src="${ctx}/pages/blackList/js/blackList.js"></script>
			</div>
		</div>
	</body>
</html>