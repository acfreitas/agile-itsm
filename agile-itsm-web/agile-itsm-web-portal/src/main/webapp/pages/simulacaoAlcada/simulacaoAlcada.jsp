<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="java.util.Collection"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");
		%>
		<%@include file="/include/header.jsp"%>

		<%@ include file="/include/security/security.jsp" %>

		<title>
			<fmt:message key="citcorpore.comum.title" />
		</title>

		<%@ include file="/include/menu/menuConfig.jsp" %>
		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

		<link rel="stylesheet" type="text/css" href="./css/simulacaoAlcada.css" />

		<script type="text/javascript" src="./js/simulacaoAlcada.js"></script>

	<%
		// Se for chamado por iframe deixa apenas a parte de cadastro da página
		if (iframe != null) {
	%>
				<link rel="stylesheet" type="text/css" href="./css/simulacaoAlcadaIFrame.css" />

	<%	}	%>
	</head>
	<body>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>

		<div id="wrapper">
		<%
			if (iframe == null) {
		%>
			<%@ include file="/include/menu_vertical.jsp" %>
		<%
			}
		%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix">
			<%
				if (iframe == null) {
			%>
				<%@ include file="/include/menu_horizontal.jsp" %>
			<%
				}
			%>
				<div class="flat_area grid_16">
					<h2>
						Simulação de Alçada
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1">
								Simulação
							</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action="${ctx}/pages/simulacaoAlcada/simulacaoAlcada">
									<input type='hidden' name='idSolicitante' id='idSolicitante'/>

									<div class="columns clearfix">
		                                <div class="col_100">
									        <div class="col_20">
												<fieldset>
													<label>Número da solicitação</label>
													<div class="inline">
														<input id="idSolicitacaoServico" type="text" maxlength="15" name='idSolicitacaoServico' class="Format[Numero]"/>
													</div>
												</fieldset>
								            </div>
		                                    <div class="col_40">
		                                         <fieldset>
		                                             <label class="campoObrigatorio"><fmt:message key="centroResultado" /></label>
		                                             <div>
		                                                 <select name='idCentroResultado' class="Description[centroResultado.custo]"></select>
		                                             </div>
		                                         </fieldset>
		                                    </div>
									        <div class="col_40">
												<fieldset>
													<label class="campoObrigatorio">Solicitante</label>
													<div class="inline">
														<input onclick="adicionarEmpregado();" type="text" name="nomeEmpregado" id="nomeEmpregado" readonly='readonly' class="Valid[Required] Description[Responsável]" />
													</div>
												</fieldset>
								            </div>
		                                </div>

		                                <div class="col_100">
		                                    <div class="col_20">
		                                         <fieldset>
		                                             <label class="campoObrigatorio">Fluxo</label>
		                                             <div>
		                                                 <select name='idTipoFluxo' id='idTipoFluxo' class="Description[Fluxo]"></select>
		                                             </div>
		                                         </fieldset>
		                                    </div>
		                                    <div class="col_20">
		                                         <fieldset>
		                                             <label class="campoObrigatorio">Finalidade</label>
		                                             <div>
		                                                 <select name='finalidade' id='finalidade' class="Description[Finalidade]"></select>
		                                             </div>
		                                         </fieldset>
		                                    </div>
						                    <div class="col_15">
						                         <fieldset>
						                             <label class="campoObrigatorio">Data da solicitação</label>
						                             <div>
						                                <input type='text' name="dataHoraSolicitacao" id="dataHoraSolicitacao" maxlength="10" size="10"
						                                       class="Valid[Required] Description[delegacaoCentroResultado.termino] Format[Data] datepicker" />
						                             </div>
						                         </fieldset>
						                    </div>
									        <div class="col_10">
												<fieldset>
													<label>Valor</label>
													<div class="inline">
														<input id="valor" type="text" maxlength="15" name='valor' class="Format[Moeda]"/>													</div>
												</fieldset>
								            </div>
									        <div class="col_10">
												<fieldset>
													<label>Valor mensal</label>
													<div class="inline">
														<input id="valorMensal" type="text" maxlength="15" name='valorMensal' class="Format[Moeda]"/>													</div>
												</fieldset>
								            </div>
									        <div class="col_15">
												<fieldset>
													<label>Valor outras alçadas</label>
													<div class="inline">
														<input id="valorOutrasAlcadas" type="text" maxlength="15" name='valorOutrasAlcadas' class="Format[Moeda]"/>													</div>
												</fieldset>
								            </div>
		                                    <div class="col_10">
                                               	 <br><button type='button' class='light img_icon has_text'  onclick='pesquisar();'>
                                                 <fmt:message key="citcorpore.comum.pesquisar" />
                                                 </button>
		                                    </div>
		                                </div>
		                                <div id="divResponsaveis" class="col_100" style="overflow:auto;">
		                                </div>
									</div>
								</form>
							</div>
						</div>
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</div>
				</div>
			</div>
			<!-- Fim da Pagina de Conteudo -->
		</div>

		<div id="POPUP_EMPREGADO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaEmpregado' style="width: 540px">
								<input type="hidden" id="isNotificacao" name="isNotificacao">
								<cit:findField formName='formPesquisaEmpregado'  lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

        <%@include file="/include/footer.jsp"%>
	</body>
</html>


