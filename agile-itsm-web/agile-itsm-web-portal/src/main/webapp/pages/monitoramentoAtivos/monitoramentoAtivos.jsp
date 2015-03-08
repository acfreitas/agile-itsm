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
	<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
	<%@include file="/novoLayout/common/include/titulo.jsp"%>
	<link type="text/css" rel="stylesheet" href="css/monitoramentoAtivos.css"/>
	<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css" />

</head>
	
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	
	<body>
		<div class="<%=(iframe == null) ? "container-fluid fixed" : ""%>">
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar <%=(iframe == null) ? "main" : "nomain"%> hidden-print">
		
				<%
				if (iframe == null) {
				%>
					<%@include file="/novoLayout/common/include/cabecalho.jsp"%>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp"%>
				<%
				}
				%>
		
			</div>
	
			<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper"%>">
				<div id="content">
					<div class="separator top"></div>
					<div class="row-fluid">
						<div class="innerLR">
							<div class="widget">
								<div class="widget-head">
									<h4 class="heading">
										<fmt:message key="monitoramentoAtivos.titulo"/>
									</h4>
								</div>
								
								<div class="widget-body collapse in">
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-2" data-toggle="tab"><fmt:message key="monitoramentoAtivos.titulo"/></a></li>
											<li class=""><a href="#tab2-2" data-toggle="tab"><fmt:message key="monitoramentoAtivos.pesquisa"/></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-2">
											<form name='form' action='${ctx}/pages/monitoramentoAtivos/monitoramentoAtivos.load'>
												<input type="hidden" name="idMonitoramentoAtivos" id="idMonitoramentoAtivos" value="" />
												<input type="hidden" name="idTipoItemConfiguracao" id="idTipoItemConfiguracao" value="" />

												<div class='row-fluid'>
													<div class="span6">
														<label class="strong campoObrigatorio">
															<fmt:message key="monitoramentoAtivos.nome" />
														</label>
														<input class="span12" type="text" name="titulo" id="titulo" required="required" maxlength="255">
													</div>	
												</div>
												
												<div class="separator top"></div>

												<fieldset>
													<legend><fmt:message key="monitoramentoAtivos.filtrosMonitoramento" /></legend>
													
													<div class='row-fluid'>
														<div class="span6">
															<label class="strong campoObrigatorio">
																<fmt:message key="itemConfiguracaoTree.tipoItemConfiguracao" />&nbsp;<span class="limpar" title="<fmt:message key="citcorpore.comum.limpar" />" data-input="tipoItemConfiguracao" data-id="idTipoItemConfiguracao"><img src="${ctx}/imagens/borracha.png" /></span>
															</label>
															<input class="span12" type="text" onfocus='abrePopupTipoItemConfiguracao();' name="tipoItemConfiguracao" id="tipoItemConfiguracao" required="required" placeholder="<fmt:message key="tipoItemConfiguracao.pesquisa"/>">
														</div>	
													</div>
													
													<div class='row-fluid'>
														<div class='span6'>
															<label class="strong campoObrigatorio"><fmt:message key="monitoramentoAtivos.regraMonitoramento" /></label>
															<div class="uniformjs">
																<label class="radio">
																	<input type="radio" class="radio" value="c" name="tipoRegra" id="tipoRegraCaracteristicas" onclick="alterarRegra(this);" />
																	<fmt:message key="pesquisaItemConfiguracao.caracteristicas" />
																</label>
																<label class="radio">
																	<input type="radio" class="radio" value="s" name="tipoRegra" id="tipoRegraScriptRhino" onclick="alterarRegra(this);"/>
																	<fmt:message key="solicitacaoServico.script" />
																</label><br/>
															</div>
														</div>
													</div>
													
													<div class="divCaracteristicas hide">
														<div class="separator top"></div>
														<div class='row-fluid'>
															<div class='span8'>
																<table id="tblCaracteristicas" name="tblCaracteristicas" class="table table-bordered">
																	<tr>
																		<th width="2%"></th>
																		<th><fmt:message key="citcorpore.comum.caracteristica" /></th>
																		<th><fmt:message key="citcorpore.comum.descricao" /></th>
																	</tr>
																</table>
															</div>
														</div>
													</div>
													
													<div class="divScriptRhino hide">
														<div class="separator top"></div>
														<div class='row-fluid'>
															<div class="span6">
																<label class="strong campoObrigatorio">
																	<fmt:message key="solicitacaoServico.script" />
																</label>
																<textarea class="span12 h170" name="script" id="script"></textarea>
															</div>	
														</div>
													</div>
												</fieldset>

												<div class="separator top"></div>
												<div class="separator top"></div>

												<fieldset>
													<legend class="campoObrigatorio"><fmt:message key="monitoramentoAtivos.regrasNotificacaoAcao" /></legend>
													
													<div class='row-fluid'>
														<div class='span6'>
															<div class="uniformjs">																
																<input type="checkbox" class="checkbox" name="enviarEmail" id="enviarEmail" value="y" onclick="mostraEnviarEmail(this);" />
																<fmt:message key="regraEscalonamento.enviarEmail" />&nbsp;&nbsp;
																<input type="checkbox" class="checkbox" name="criarProblema" id="criarProblema" value="y" />
																<fmt:message key="tipoDemandaServico.criarProblema" />&nbsp;&nbsp;
																<input type="checkbox" class="checkbox" name="criarIncidente" id="criarIncidente" value="y" />
																<fmt:message key="monitoramentoAtivos.criarIncidente" />&nbsp;&nbsp;
															</div>
														</div>
													</div>
													
													<div class="divEnviarEmail hide">
														<div class="separator top"></div>
														
														<div class='row-fluid'>
															<div class='span4'>
																<label class="strong"><a href="javascript: return false;" onclick="adicionarUsuario();"><fmt:message key="monitoramentoAtivos.adicionarUsuario" /></a></label>
																<table id="tblNotificacaoUsuarios" name="tblNotificacaoUsuarios" class="table table-bordered">
																	<tr>
																		<th width="2%"><fmt:message key="dinamicview.remover" /></th>
																		<th><fmt:message key="citcorpore.comum.usuario" /></th>
																	</tr>
																</table>
															</div>
														</div>
														
														<div class="separator top"></div>
														
														<div class='row-fluid'>
															<div class='span4'>
																<label class="strong"><a href="javascript: return false;" onclick="adicionarGrupo();"><fmt:message key="monitoramentoAtivos.adicionarGrupo" /></a></label>
																<table id="tblNotificacaoGrupos" name="tblNotificacaoGrupos" class="table table-bordered">
																	<tr>
																		<th width="2%"><fmt:message key="dinamicview.remover" /></th>
																		<th><fmt:message key="grupo.grupo" /></th>
																	</tr>
																</table>
															</div>
														</div>
													</div>
													
													<div class="separator top"></div>
													
													<div class='row-fluid'>
														<div class="span6">
															<label class="strong campoObrigatorio">
																<fmt:message key="monitoramentoAtivos.descricaoNotificacao" />:
															</label>
															<textarea class="span12 h170" name="descricao" id="descricao"></textarea>
														</div>	
													</div>
												</fieldset>

												<div class="separator top"></div>
												<div class="separator top"></div>
												
												<div class='row-fluid'>
													<div class="span4">
														<button class="btn btn-default btn-primary" type="button" onclick="gravar();" id="btnGravar" name="btnGravar">
															<span><fmt:message key="citcorpore.comum.gravar"/></span>
														</button>
														<button class="btn btn-default btn-primary" type="button" onclick="deletar()" id="btnExcluir" name="btnExcluir">
															<span><fmt:message key="citcorpore.comum.excluir"/></span>
														</button>
														<button class="btn btn-default btn-primary" type="button" onclick="limpar()" id="btnLimpar" name="btnLimpar">
															<span><fmt:message key="citcorpore.comum.limpar"/></span>
														</button>
													</div>
												</div>
											</form>
										</div>
										<div class="tab-pane" id="tab2-2">
											<form name='formPesquisa'>
												<cit:findField formName='formPesquisa' lockupName='LOOKUP_MONITORAMENTOATIVOS' id='LOOKUP_MONITORAMENTOATIVOS' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--  Fim conteudo-->
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		
			<div id="POPUP_TIPOITEMCONFIGURACAO" class="hide" title="<fmt:message key="itemConfiguracaoTree.tipoItemConfiguracao" />">
				<div class="box grid_16 tabs">
					<div class="toggle_container">
						<div id="tabs-2" class="block">
							<div class="section">
								<form name='formServico' style="width: 540px">
									<cit:findField formName='formServico' lockupName='LOOKUP_TIPOITEMCONFIGURACAO' id='LOOKUP_TIPOITEMCONFIGURACAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="POPUP_USUARIO" class="hide" title="<fmt:message key="citcorpore.comum.pesquisar" />">
				<div class="box grid_16 tabs" style='width: 560px !important;'>
					<div class="toggle_container">
						<div id="tabs-2" class="block">
							<div class="section">
								<form name='formPesquisaUsuario' style="width: 540px">
									<input type="hidden" id="isNotificacao" name="isNotificacao">
									<cit:findField formName='formPesquisaUsuario'  lockupName='LOOKUP_USUARIO' id='LOOKUP_USUARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div> 
			
			<div id="POPUP_GRUPO" class="hide" title="<fmt:message key="citcorpore.comum.pesquisar" />">
				<div class="box grid_16 tabs" style='width: 560px !important;'>
					<div class="toggle_container">
						<div id="tabs-2" class="block">
							<div class="section">
								<form name='formPesquisaGrupo' style="width: 540px">
								<input type="hidden" id="isNotificacaoGrupo" name="isNotificacaoGrupo">
									<cit:findField formName='formPesquisaGrupo' lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
		</div>

		<script  charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
		<script type="text/javascript" src="js/monitoramentoAtivos.js"></script>
	</body>
	
</html>