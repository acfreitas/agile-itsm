<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>

<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="./js/jornadaTrabalho.js"></script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2><fmt:message key="jornadaTrabalho.jornadaTrabalho"/></h2>
			</div>
			<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="jornadaTrabalho.cadastroJornadaTrabalho"/>
					</a></li>
					<li><a href="#tabs-2" class="round_top" ><fmt:message key="jornadaTrabalho.pesquisaJornadaTrabalho"/>
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/jornadaTrabalho/jornadaTrabalho'>
								<div class="columns clearfix">
									<input type='hidden' name='idJornada' />
									<div class="col_50">
										<fieldset style="margin: 0 0 0 0">
											<label class="campoObrigatorio"><fmt:message key="jornadaTrabalho.descricao" /></label>
											<div>
												<input type='text' name="descricao" id="descricao" maxlength="70" class="Valid[Required] Description[jornadaTrabalho.descricao]" />
											</div>
										</fieldset>
									</div>
									</div>
							<div class="columns clearfix">
								<div class="col_50">
									<br>
									<table>
										<tbody>
										<tr>
											<td></td>
											<th><fmt:message key="jornadaTrabalho.horaInicio"/></th>
											<td></td>
											<th><fmt:message key="jornadaTrabalho.horaFim"/></th>
										</tr>
										<tr>
											<td WIDTH=100 align="center"><b>1</b></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="inicio1" name="inicio1"/></td>
											<td WIDTH=100 align="center"><label><b><fmt:message key="jornadaTrabalho.as" /></b></label></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="termino1" name="termino1"/></td>
										</tr>
										<tr>
											<td WIDTH=100 align="center"><b>2</b></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="inicio2" name="inicio2"/></td>
											<td WIDTH=100 align="center"><label><b><fmt:message key="jornadaTrabalho.as" /></b></label></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="termino2" name="termino2"/></td>

										</tr>
										<tr>
											<td WIDTH=100 align="center"><b>3</b></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="inicio3" name="inicio3"/></td>
											<td WIDTH=100 align="center"><label><b><fmt:message key="jornadaTrabalho.as" /></b></label></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="termino3" name="termino3"/></td>

										</tr>
										<tr>
											<td WIDTH=100 align="center"><b>4</b></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="inicio4" name="inicio4"/></td>
											<td WIDTH=100 align="center"><label><b><fmt:message key="jornadaTrabalho.as" /></b></label></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="termino4" name="termino4"/></td>
											<td></td>
										</tr>
										<tr>
											<td WIDTH=100 align="center"><b>5</b></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="inicio5" name="inicio5"/></td>
											<td WIDTH=100 align="center"><label><b><fmt:message key="jornadaTrabalho.as" /></b></label></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="termino5" name="termino5"/></td>
										</tr>
										</tbody>
									</table>
									<br>
									<br>
									<div>
										<fieldset style="margin-left: 2%; margin-right: 71%;">
											<label><fmt:message key="jornadaTrabalho.cargaHoraria"/></label>
											<input size="10" class="Format[Hora]" type="text" id="cargaHoraria" name="cargaHoraria" readonly="readonly" onclick="document.getElementById('descricao').focus();alert(i18n_message('citcorpore.comum.cargaHorariaCalculadaSistema'));"  />
										</fieldset>
								    </div>
								</div>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light" onclick='gravar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' class="light"
									onclick='excluir();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message key="citcorpore.comum.excluir" />
									</span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_JORNADATRABALHO' id='LOOKUP_JORNADATRABALHO' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>

