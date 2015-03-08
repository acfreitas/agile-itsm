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
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="css/processamentoBatch.css"/>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
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
									<h4 class="heading"><fmt:message key="processamentoBatch.processamentoBatch"/></h4>
								</div>
								<div class="widget-body collapse in">
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="processamentoBatch.cadastroBatch"/></a></li>
											<li><a href="#tab2-3" data-toggle="tab" ><fmt:message key="processamentoBatch.pesquisaBatch"/></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name='form' action='${ctx}/pages/processamentoBatch/processamentoBatch'>
												<input type='hidden' name='idProcessamentoBatch'/>

												<div class='row-fluid'>
													<div class='span12'>
														<div class='row-fluid'>
															<div class='span6'>
																<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.descricao" /></label>
																<input id="descricao" name="descricao" type='text' maxlength="254" class="span12 Valid[Required] Description[]" />
															</div>
															<div class='span1'>
																<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.situacao" /></label>
																<select name='situacao' id='situacao' class="span12 Valid[Required] Description[citcorpore.comum.situacao]"></select>
															</div>
															<div class='span1'>
																<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.tipo" /></label>
																<select name='tipo' id='tipo' class="span12 Valid[Required] Description[citcorpore.comum.tipo]"></select>
															</div>
														</div>
													</div>
												</div>

												<div class='row-fluid'>
													<div id="divAgendamento" class='span12'>
														<h5><fmt:message key="citcorpore.ui.janela.popup.titulo.Agendamento"/></h5>
														<div class='span1'>
															<label class="campoObrigatorio"><fmt:message key="processamentoBatch.segundos"/></label>
															<select name='segundos' id='segundos' class="span12 Valid[Required] Description[processamentoBatch.segundos]"></select>
														</div>
														<div class='span1'>
															<label class="campoObrigatorio"><fmt:message key="processamentoBatch.minutos"/></label>
															<select name='minutos' id='minutos' class="span12 Valid[Required] Description[processamentoBatch.minutos]"></select>
														</div>
														<div class='span1'>
															<label class="campoObrigatorio"><fmt:message key="processamentoBatch.horas"/></label>
															<select name='horas' id='horas' class="span12 Valid[Required] Description[processamentoBatch.horas]"></select>
														</div>
														<div class='span2'>
															<label><fmt:message key="processamentoBatch.diaDoMes"/></label>
															<select name='diaDoMes' id='diaDoMes' class="span12 Valid[Required] Description[processamentoBatch.diaDoMes]"></select>
														</div>
														<div class='span1'>
															<label class="campoObrigatorio"><fmt:message key="processamentoBatch.mes"/></label>
															<select name='mes' id='mes' class="span12 Valid[Required] Description[processamentoBatch.mes]"></select>
														</div>
														<div class='span2'>
															<label><fmt:message key="processamentoBatch.diaDaSemana"/></label>
															<select name='diaDaSemana' id='diaDaSemana' class="span12 Valid[Required] Description[processamentoBatch.diaDaSemana]"></select>
														</div>
														<div class='span1'>
															<label><fmt:message key="processamentoBatch.ano"/></label>
															<select name='ano' id='ano' class="span12 Description[processamentoBatch.ano]"></select>
														</div>

													</div>
												</div>

												<div class='row-fluid'>
													<div class='span12'>
														<label ><fmt:message key="citcorpore.comum.conteudo"/></label>
														<textarea cols=1000 rows=7 name="conteudo" class="span12 Description[citcorpore.comum.conteudo]"></textarea>
													</div>
												</div>

												<div class='row-fluid'>
													<div class='span12'>
														<button type='button' name='btnGravar' class="lFloat btn btn-icon btn-primary" onclick='document.form.save();'>
															<i></i><fmt:message key="citcorpore.comum.gravar" />
														</button>
														<button type="button" name='btnLimpar' class="lFloat btn btn-icon btn-primary" onclick='document.form.clear();'>
															<i></i><fmt:message key="citcorpore.comum.limpar" />
														</button>
														<button type='button' name='btnExecucoes' class="lFloat btn btn-icon btn-primary" onclick='mostraExecucoes();'>
															<i></i><fmt:message	key="processamentoBatch.mostrarExecucoes" />
														</button>
													</div>
											   </div>
											</form>
										</div>
										<div class="tab-pane" id="tab2-3">
											<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
												<form name='formPesquisa'>
													<cit:findField formName='formPesquisa' lockupName='LOOKUP_PROCESSAMENTO_BATCH' id='LOOKUP_PROCESSAMENTO_BATCH' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>

		<!-- MODAL EXECUCOES-->
		<div class="modal hide fade" id="modal_Execucoes" tabindex="-1" data-backdrop="static" data-keyboard="false">
			<!-- Modal heading -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3><fmt:message key="processamento.ultimasExecucoes" /></h3>
			</div>
			<!-- // Modal heading END -->
			<!-- Modal body -->
			<div class="modal-body">
				<div class='slimScrollDiv'>
					<div class='slim-scroll' id='contentExecucao'>
					<div class='row-fluid'>
						<div class='span12'>
							<table id="tblExecucao" name="tblExecucao" class="table  table-bordered">
								<tr>
									<th height="10px" width="15%"><fmt:message	key="citcorpore.comum.datahora" /></th>
									<th height="10px" width="85%"><fmt:message	key="citcorpore.comum.conteudo" /></th>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<!-- // Modal body END -->
			<!-- Modal footer -->
		</div>

		<script type="text/javascript" src="js/processamentoBatch.js"></script>
	</body>
</html>