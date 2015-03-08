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
<link type="text/css" rel="stylesheet"
	href="../../novoLayout/common/include/css/template.css" />
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title=""
	style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body>
	<div class="<%=(iframe == null) ? "container-fluid fixed" : ""%>">

		<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
		<div
			class="navbar <%=(iframe == null) ? "main" : "nomain"%> hidden-print">

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

			<!-- Inicio conteudo -->
			<div id="content">
				<div class="separator top"></div>
				<div class="row-fluid">
					<div class="innerLR">
						<div class="widget">
							<div class="widget-head">
								<h4 class="heading">
									<fmt:message key="menu.nome.formulaOs" />
								</h4>
							</div>
							<div class="tabsbar">
								<ul>
									<li class="active"><a href="#tab1-4" data-toggle="tab"><fmt:message key="formula.cadastro" /></a></li>
									<li class=""><a href="#tab2-4" data-toggle="tab"><fmt:message key="formula.pesquisa" /></a></li>
								</ul>
							</div>

							<div class="widget-body collapse in">
								<div class="tab-content">
									<div class="tab-pane active" id="tab1-4">
										<form name='form'action='${ctx}/pages/formulaOs/formulaOs'>
											<input type="hidden"  id="idFormulaOs" name= 'idFormulaOs'/>
											<input type="hidden"  id="formulaSimulada" name= 'formulaSimulada'/>

											<div class='row-fluid'>
												<div class="separator top"></div>
												<div class='span12'>
													<div class='row-fluid'>
														<div class='span6'>
															<span class="strong campoObrigatorio"><fmt:message key="gerenciamentoservico.descricao" />:</span>
															<input maxlength="254"   type="text" class="span12 Format[text]" id=descricao name="descricao"  required="required">
														</div>
													</div>
												</div>

												<div class="separator top"></div>
											 	<div class='span12'>
													<div class='row-fluid'>
														<div class='span8'>
															<span class="strong"><fmt:message key="questionario.opcoes" />:</span>
															<div id="legenda">

																<button type='button' name='btnValor' class="btn btn-default btn-primary" value="vValor" onclick="addOperador(this)">Valor</button>

																<button type='button' name='btnSomar' class="btn btn-default btn-primary" value="+" onclick="addOperador(this)">+</button>

																<button type='button' name='btnSubtrair' class="btn btn-default btn-primary" value="-" onclick="addOperador(this)">-</button>

																<button type='button' name='btnMultiplicacao' class="btn btn-default btn-primary" value="*" onclick="addOperador(this)">*</button>

																<button type='button' name='btnDivisao' class="btn btn-default btn-primary" value="/" onclick="addOperador(this)">/</button>

																<button type='button' name='btnOp1' class="btn btn-default btn-primary" value="(" onclick="addOperador(this)">(</button>

																<button type='button' name='btnOp2' class="btn btn-default btn-primary" value=")" onclick="addOperador(this)">)</button>

																<button type='button' name='btnDiasUteis' class="btn btn-default btn-primary" value="vDiasUteis" onclick="addOperador(this)"><fmt:message key="citcorpore.comum.diasUteis" /></button>

																<button type='button' name='btnDiasCorridos' class="btn btn-default btn-primary" value="vDiasCorridos" onclick="addOperador(this)"><fmt:message key="citcorpore.comum.diasCorridos" /></button>

																<button type='button' name='btnNumeroUsuarios' class="btn btn-default btn-primary" value="vNumeroUsuarios" onclick="addOperador(this)"><fmt:message key="formula.NumeroDeUsuarios" /></button>

																<button type='button' name='btnComplexidade' class="btn btn-default btn-primary" value="vComplexidade" onclick="addOperador(this)"><fmt:message key="matrizvisao.complexidade" /></button>

															</div>
														</div>
													</div>
												</div>
												<div class='span12'>
													<div class='row-fluid'>
														<div class='span6'><!--verificarOperador()-->
															<span class="strong campoObrigatorio"><fmt:message key="formula.formula" />:</span>
															<input maxlength="254"  type="text" class="span12 Format[text]" id=formula name="formula"  required="required" style="height: 62px;">
															<button class="btn btn-default btn-primary" type="button" onclick="MostrarFormula();" id="add" name='add'><fmt:message key="formula.VisualizarFormula" /></button>
														</div>
													</div>
												</div>
												<div class='span12'>
													<div class='row-fluid'>
														<div class='span6'>
															<div class="separator top"></div>
															<label class="strong campoObrigatorio"><fmt:message key="colaborador.situacao"/></label>
															<div id="DivSituacao">
																<select name='situacao' class="Valid[Required] Description[colaborador.situacao]"></select>
															</div>
														</div>
													</div>
												</div>
												<div class='span12'>
													<div class='row-fluid'>
														<div class='span6'>
															<div class="separator top"></div>
															<span class="strong"><fmt:message key="formula.formula" />:</span>
															<div id="formulaMontada">
															</div>
															<div id="resultadoFormula">
															</div>
														</div>
													</div>
												</div>

												<div class='span12'>
													<div class='row-fluid'>
														<div class='span6'>
														<div class="separator top"></div>
															<button type='button' name='btnGravar' class="btn btn-default btn-primary" onclick='validar();'>
																<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
																<span><fmt:message key="citcorpore.comum.gravar" /></span>
															</button>
															<button type="button" name='btnLimpar' class="btn btn-default btn-primary" onclick='limpar();'>
																<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
																<span><fmt:message key="citcorpore.comum.limpar" /></span>
															</button>
															<button type='button' name='btnUpDate' class="btn btn-default btn-primary" onclick='excluir();'>
																<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
																<span><fmt:message key="citcorpore.comum.excluir" /></span>
															</button>
														</div>
													</div>
												</div>

											</div>
										</form>
									</div>


									<div class="tab-pane" id="tab2-4">
										<div class='row-fluid'>
											<form name='formPesquisa'>
												<cit:findField formName='formPesquisa' lockupName='LOOKUP_FORMULAOS' id='LOOKUP_FORMULAOS' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
											</form>
										</div>
									</div>


								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
			<!--  Fim conteudo-->
			<%@include file="/novoLayout/common/include/rodape.jsp"%>
			<script type="text/javascript" src="./js/formulaOs.js"></script>
		</div>
	</div>
</body>
</html>
