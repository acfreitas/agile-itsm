<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<!doctype html public "">
<html>
<head>
	<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
	<%@include file="/novoLayout/common/include/titulo.jsp" %>

	<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>

	<script src="js/importarDados.js"></script>
	<script type="text/javascript" src="../../cit/objects/ImportarDadosDTO.js"></script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>

<body>
	<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">

		<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
		<div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">

			<% if(iframe == null) { %>
				<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
				<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
			<% } %>

		</div>

		<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">
			<!-- Inicio conteudo -->
			<div id="content">
				<div class="separator top"></div>
				<div class="row-fluid">
					<div class="innerLR">
						<div class="widget">
							<div class="widget-head">
								<h4 class="heading"><fmt:message key="importarDados.telaDeImportarDados"/></h4>
							</div>

							<div class="widget-body collapse in">
								<div class="tabsbar">
									<ul>
										<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="importarDados.cadastroImportarDados"/></a></li>
										<li><a href="#tab2-3" data-toggle="tab"><fmt:message key="importarDados.pesquisaImportarDados"/></a></li>
									</ul>
								</div>

								<div class="tab-content">
									<div class="tab-pane active" id="tab1-3">
										<form name='form' action='${ctx}/pages/importarDados/importarDados'>
											<input type='hidden' name='idImportarDados' id='idImportarDados' />
											<input type='hidden' name=jsonMatriz id='jsonMatriz' />
											<input type='hidden' name='seqGrid' />
											<input type='hidden' name='valorDaConexaoAntiga' />

											<div class="row-fluid">
												<div class="span3">
													<label class="strong campoObrigatorio "><fmt:message key='importarDados.importarDadosPor'/></label>
													<div>
														<select name="importarPor" id ="importarPor" class="Valid[Required] Description[importarDados.importarDadosPor] span12" onchange="mostrarDiv()"></select>
													</div>
												</div>
											</div>
											<div class="row-fluid">
												<div class="span3">
													<label class="strong campoObrigatorio" style="margin-top: 5px;"><fmt:message key="citcorpore.comum.tipo"/></label>
													<div>
														<select name="tipo" id ="tipo" class="Valid[Required] Description[citcorpore.comum.tipo] span12"></select>
													</div>
												</div>
												<div class="span3">
													<label class="strong campoObrigatorio" style="margin-top: 5px;"><fmt:message key="importmanager.conexao"/></label>
													<div>
														<select name="idExternalConnection" id ="idExternalConnection" class="Valid[Required] Description[importmanager.conexao] span12"></select>
													</div>
												</div>
											</div>

											<div class="row-fluid">
												<div class="span6">
													<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.nome" /></label>
													<div>
														<input type='text' name="nome" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
													</div>
												</div>
											</div>

											<div id="divTabela" class="row-fluid">
												<div class="span3">
													<label class="strong campoObrigatorio" style="margin-top: 5px;"><fmt:message key="lookup.tabelaOrigem"/></label>
													<div>
														<select name="tabelaOrigem" id ="tabelaOrigem" class="Description[lookup.tabelaOrigem] span12"></select>
													</div>
												</div>
												<div class="span3">
													<label class="strong campoObrigatorio" style="margin-top: 5px;"><fmt:message key="lookup.tabelaDestino"/></label>
													<div>
														<select name="tabelaDestino" id ="tabelaDestino" class="Description[lookup.tabelaDestino] span12"></select>
													</div>
												</div>

												<div class="col_100">
													<h2 class="section">
														<fmt:message key="importmanager.dadosimportacao" />
													</h2>

													<div id='divNovoCriterio' class="col_100">
														<div class="col_50">
															<button type='button' class='light img_icon has_text' onclick='adicionarLinhaGrid();'>
																<fmt:message key="citcorpore.comum.adicionar" />
															</button>
														</div>
													</div>
												</div>

												<div class="col_100">
													<cit:grid id="gridImport" columnHeaders="importarDados.opcoesDaGrid" styleCells="linhaGrid">
														<cit:column idGrid="gridImport" number="001">
															<select name='camposTabelaOrigem#SEQ#' id='camposTabelaOrigem#SEQ#' style='border:none; width:300px;'></select>
														</cit:column>
														<cit:column idGrid="gridImport" number="002">
															<select name='camposTabelaDestino#SEQ#' id='camposTabelaDestino#SEQ#' style='border:none; width:300px;'></select>
														</cit:column>
														<cit:column idGrid="gridImport" number="003">
															<textarea name="script#SEQ#" cols='65' rows='2'></textarea>
														</cit:column>
													</cit:grid>
												</div>
											</div>

											<div id="divImportarPorScriptOuJar" class="row-fluid">
												<div class="row-fluid" id="divScriptConversao">
													<div class="span7">
														<label><fmt:message key="desenhoFluxo.codigoScript" /></label>
														<div class="span10">
															<textarea style="resize: none;" name="script" cols="10" rows="5" maxlength="5000"></textarea>
														</div>
													</div>
												</div>

												<div class="row-fluid">
													<div class="widget span6" id="divArquivo" style="display: none;">
														<fieldset>
															<label><h2><fmt:message key="citcorpore.comum.anexos"/></h2></label>
															<cit:uploadControl style="height:10%;width:100%; border-bottom:1px solid #DDDDDD ; border-top:1px solid #DDDDDD " title="<fmt:message key='citcorpore.comum.anexos' />" id="uploadAnexos" form="document.form" action="/pages/upload/upload.load" disabled="false"/>
														</fieldset>
													</div>
												</div>

												<div class="row-fluid">
													<div class="span12">
														<table>
															<tr>
																<td style="text-align: center;">
																	<input type="checkbox" name="agendarRotina" id="agendarRotina" checked="checked" onclick="agendamentoParaRotina()" />
																</td>

																<td style="text-align: center;">
																	<label class="strong"><fmt:message key='importarDados.agendarRotina'/></label>
																</td>
															</tr>
														</table>
													</div>
												</div>

												<div id="divRotina">
													<div class="row-fluid">
														<div class="span2">
															<label class="strong campoObrigatorio"><fmt:message key="importarDados.executarRotinaPor"/></label>
															<div class="controls">
																<select name="executarPor" class="span12" id ="executarPor" class="Valid[Required] Description[importarDados.executarRotinaPor]" onchange="alterarExecutarPor();"></select>
															</div>
														</div>

														<div id="divHora" class="span1">
															<label for="horaExecucao" class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.horario"/></label>
															<input type="text" placeholder="__:__" name="horaExecucao" id ="horaExecucao" size="10" onblur="validaHoras(this)" />
														</div>
														<div id="divPeriodo" class="span2">
															<label for="periodoHora" class="strong campoObrigatorio"><fmt:message key="importarDados.periodoPorHora"/></label>
															<div class="span4">
																<input type="text" name="periodoHora" placeholder="<fmt:message key='importarDados.horas'/>" id="periodoHora" maxlength="3" size="10" onKeypress="return numeros();" onkeydown="Verificar();"/>
															</div>
														</div>
													</div>
												</div>
											</div>

											<div class="row-fluid">
												<div class="span12">
													<button type='button' name='btnGravar' class="light" onclick='salvar();'>
														<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
														<span><fmt:message key="citcorpore.comum.gravar" /></span>
													</button>
													<button type='button' name='btnLimpar' class="light" onclick='document.form.fireEvent("load");'>
														<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
														<span><fmt:message key="citcorpore.comum.limpar" /></span>
													</button>
													<button type='button' name='btnExcluir' class="light" onclick='excluir();'>
														<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
														<span><fmt:message key="citcorpore.comum.excluir" /></span>
													</button>
													<button type='button' name='btnExecutarRotina' class="light" onclick='executarRotina();'>
														<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
														<span><fmt:message key="importarDados.executarRotina" /></span>
													</button>
												</div>
											</div>
										</form>
									</div>

									<div class="tab-pane" id="tab2-3">
										<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
											<form name='formPesquisa'>
												<cit:findField formName='formPesquisa' lockupName='LOOKUP_IMPORTARDADOS' id='LOOKUP_IMPORTARDADOS' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- Fim conteudo-->
		</div>
	</div>
	<%@include file="/novoLayout/common/include/rodape.jsp" %>
</body>
</html>