<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@include file="/include/header.jsp"%>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>

		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

    	<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
		<script type="text/javascript" src="./js/pagamentoProjeto.js"></script>

	</head>
	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="pagamentoProjeto.pagamentoProjeto"/></h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="citcorpore.ui.aba.titulo.Cadastro"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="formConsulta" action='${ctx}/pages/pagamentoProjeto/pagamentoProjeto'>
									<div class="columns clearfix">
										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label class="" ><fmt:message key="pagamentoProjeto.projeto"/></label>
														<div>
														  	<select name='idProjetoAux' id='idProjetoAux' onchange='selecionaProjeto(this)'></select>
														</div>
												</fieldset>
											</div>
										</div>

										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label class="" ><fmt:message key="pagamentoProjeto.pagamentosEfetuados"/></label>
														<div id='divPagamentosEfetuados'>
														</div>
												</fieldset>
											</div>
										</div>

										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label class="" ><fmt:message key="pagamentoProjeto.tarefasPendentesPagamento"/></label>
														<div id='divTarefasParaPagamentoVis'>
														</div>
												</fieldset>
											</div>
										</div>

									</div>

									<button type='button' name='btnGravar' class="light"  onclick='gerarPagamento();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="pagamentoProjeto.gerarPagamento"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar"/></span>
									</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_EDITAR" title="Editar Pagamento" style="display: none">
					<form name="form" action='${ctx}/pages/pagamentoProjeto/pagamentoProjeto'>
						<input type='hidden' name='idProjeto' id='idProjeto'/>
						<div class="col_100">
							<div class="col_25">
								<fieldset>
									<label style="margin-top: 5px;"><fmt:message key="citcorpore.comum.data"/></label>
										<div>
										  	<input type='text' name='dataPagamento' id='dataPagamento' size='10' maxlength="10" class='Description[citcorpore.comum.data] Format[Date] Valid[Required,Date] datepicker'/>
										</div>
								</fieldset>
							</div>
							<div class="col_25">
								<fieldset>
									<label style="margin-top: 5px;"><fmt:message key="citcorpore.comum.situacao"/></label>
										<div>
											<select name='situacao' id='situacao'>
												<option value='S'><fmt:message key="pagamentoProjeto.solicitado"/></option>
												<option value='E'><fmt:message key="pagamentoProjeto.efetuado"/></option>
											</select>
										</div>
								</fieldset>
							</div>
							<div class="col_50">
								<fieldset>
									<label style="margin-top: 5px;"><fmt:message key="pagamentoProjeto.valorGlosa"/></label>
										<div>
										  	<input type='text' name='valorGlosa' id='valorGlosa' size='6' maxlength="6" class='Format[Moeda]'/>
										</div>
								</fieldset>
							</div>
						</div>
						<div class="col_100">
							<fieldset>
								<label style="margin-top: 5px;"><fmt:message key="pagamentoProjeto.detalhamento"/></label>
									<div>
									  	<textarea rows="3" cols="70" name="detPagamento" id="detPagamento"></textarea>
									</div>
							</fieldset>
						</div>
						<div class="col_100">
							<fieldset>
								<label class="" >
								<fmt:message key="pagamentoProjeto.tarefasQueSeraoPagas"/></label>
									<div>
										<fieldset>
											<label style="margin-top: 5px;"><fmt:message key="pagamentoProjeto.cronogramaFinanceiro"/></label>
												<div>
													<select name='idMarcoPagamentoPrj' id='idMarcoPagamentoPrj' onchange='selecionaMarcoFin()'>
													</select>
												</div>
										</fieldset>
									</div>
									<div id='divTarefasParaPagamento'>
									</div>
							</fieldset>
						</div>
						<br><br>
						<fieldset>
						<label>
								<button type='button' name='btnGravar' class="light"  onclick='save();'>
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar"/></span>
								</button>
							</label>
						</fieldset>
					</form>
				</div>
				<div id="POPUP_MUDA_SIT_PAGAMENTO" title="Mudar Situação Pagamento" style="display: none">
					<form name="formAtuSit" action='${ctx}/pages/pagamentoProjeto/pagamentoProjeto'>
						<input type='hidden' name='idPagamentoProjeto' id='idPagamentoProjeto'/>
						<div class="col_100">
							<div class="col_25">
								<fieldset>
									<label style="margin-top: 5px;"><fmt:message key="citcorpore.comum.data"/></label>
										<div>
										  	<input type='text' name='dataPagamentoAtu' id='dataPagamentoAtu' size='10' maxlength="10" class='Description[Data Pagamento] Format[Date] Valid[Required,Date] datepicker'/>
										</div>
								</fieldset>
							</div>
						</div>
						<br><br>
						<fieldset>
						<label>
						<button type='button' name='btnGravar' class="light"  onclick='saveAtu();'>
							<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
							<span><fmt:message key="citcorpore.comum.gravar"/></span>
						</button>
							</label>
						</fieldset>
					</form>
				</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>
