<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
	<%@include file="/include/security/security.jsp"%>
	<title>CIT Corpore</title>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

	<style type="text/css">
	.table {
		border-left: 1px solid #ddd;
	}

	.table th {
		border: 1px solid #ddd;
		padding: 4px 10px;
		border-left: none;
		background: #eee;
	}

	.table td {
		border: 1px solid #ddd;
		padding: 4px 10px;
		border-top: none;
		border-left: none;

	}
	#tblip{
		width: 90% !important;
		margin-left: -20px;
	}
	#tblip .marcaTodos{
		display: block;
		float: left;
		width: 150px;
	}
	#tblip .pesquisarNetMap{
		display: block;
		float: right;
		width: 150px;
	}
	#tblip .quantIP{
		display: block;
		float: left;
		width: 350px;
	}
	#totalIP{
		width: 59% !important;
		margin-left: -20px;
	}
	</style>
	<script>
	function inventarioManual() {

		JANELA_AGUARDE_MENU.show();
		$('#loading_overlay').show();
		document.form.fireEvent("inventarioManual");
	}

	function netMapManual(){
		JANELA_AGUARDE_MENU.show();
		$('#loading_overlay').show();
		document.form.fireEvent("netMapManual");

	}

	function marcarTodosCheckbox() {
		var itens = document.form.ips;
		var marcar = document.form.marcatodos;
		for ( var i = 0; i < itens.length; i++) {
			if (marcar.checked) {
				if (!itens[i].checked) {
					itens[i].checked = true;
				}
			} else {
				itens[i].checked = false;
			}
		}
	}

	$(function() {
		$("#POPUP_RESULTADO_INVENTARIO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		$("#POPUP_ABERTURA_INC").dialog({
			autoOpen : false,
			width : 1000,
			height : 700,
			modal : true
		});
	});
	function abreFecha(nameDiv){
		if (document.getElementById(nameDiv).style.display == 'none'){
			document.getElementById(nameDiv).style.display = 'block';
			document.getElementById(nameDiv + '_CTRL').src = '${ctx}/imagens/collapse.gif';
		}else{
			document.getElementById(nameDiv).style.display = 'none';
			document.getElementById(nameDiv + '_CTRL').src = '${ctx}/imagens/expand.gif';
		}
	}
	function listaInfoRelacionadaContrato(){
		document.form.fireEvent('listaServicos');
	}
	function abreItensRegrasIC(idIc){
		document.form.idItemConfiguracao.value = idIc;
		$('#POPUP_ABERTURA_INC').dialog('open');
	}
	function fecharPopupAbInc(){
		$('#POPUP_ABERTURA_INC').dialog('close');
	}
	function gravarAbInc(){

	}
</script>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
	<body>
		<div id="wrapper"><%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
		<%@include file="/include/menu_horizontal.jsp"%>

		<div class="flat_area grid_16">
		<h2><fmt:message key="inventario.invetario" /></h2>
		</div>

		<div class="box grid_16 tabs">
		<ul class="tab_header clearfix">
			<li><a href="#tabs-1"><fmt:message key="inventario.cadastro" />
			</a></li>

		</ul>
		<a href="#" class="toggle">&nbsp;</a>
		<div class="toggle_container">
			<div id="tabs-1" class="block">
				<div class="section" style="width: ">
					<form name='form' action='${ctx}/pages/inventarioAdvanced/inventarioAdvanced'>
						<div class="columns clearfix">
						<h2 class="section"><label><fmt:message key="inventario.cbcIps" /> </label></h2>
						<input type='hidden' name='idEmpresa' value="<%=WebUtil.getIdEmpresa(request)%>" />

						<div class="columns clearfix">
							<div>
								<fieldset >
									<div id="ipMac" ></div>
								</fieldset>

						   </div>
						</div>
						</div>
						<div style="width: 500px" >&nbsp;</div>
						<button type='button' name='btnGravar' class="light" onclick='inventarioManual();'>
							<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
							<span><fmt:message key="inventario.gerarInventario" /> </span>
						</button>
						<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
							<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
							<span><fmt:message key="citcorpore.comum.limpar" /> </span>
						</button>
						<div id="POPUP_RESULTADO_INVENTARIO" title="Resultado Inventário">
							<div id="resultado">

							</div>

						</div>

						<div id='POPUP_ABERTURA_INC' title="Configurações">
							<input type='hidden' name='idItemConfiguracao'/>
							<table>
									<tr>
										<td colspan="2">
											<fieldset>
												<legend><b>Abertura automática de incidente</b></legend>
												<table>
													<tr>
														<td colspan="3">
															<div id='divMsg'>
															</div>
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">Email para abertura de incidente:</td>
														<td colspan="2">
															<input type='text' name='emailAberturaInc' size="100"	maxlength="255" />
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">Solicitante:</td>
														<td colspan="2">
															<input type='hidden' name='idSolicitante' />
															<input type='text' name='nomeSolicitante' size="100" maxlength="255" readonly="readonly" onclick='LOOKUP_SOLICITANTE.show()' onfocus='LOOKUP_SOLICITANTE.show()'/>
															<cit:lookupField formName='form'
																lockupName='LOOKUP_SOLICITANTE' id='LOOKUP_SOLICITANTE' top='0'
																left='0' len='550' heigth='400' javascriptCode='true'
																htmlCode='true' hide='true'/>
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">Descrição/Texto:</td>
														<td colspan="2">
															<textarea name='descricaoAbertInc' rows="5" cols="100"></textarea>
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">Impacto:</td>
														<td colspan="2">
															<select name='impacto' id='impacto'>
															</select>
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">Urgência:</td>
														<td colspan="2">
															<select name='urgencia' id='urgencia'>
															</select>
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">Origem:</td>
														<td colspan="2">
															<select name='idOrigem' id='idOrigem'>
															</select>
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">Contrato:</td>
														<td colspan="2">
															<select name='idContrato' id='idContrato' onchange='listaInfoRelacionadaContrato()'>
															</select>
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">Serviço:</td>
														<td colspan="2">
															<select name='idServico' id='idServico'>
															</select>
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">Direcionar para o grupo:</td>
														<td colspan="2">
															<select name='idGrupo' id='idGrupo'>
															</select>
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">Evento a ser gerado:</td>
														<td colspan="2">
															<select name='idEventoMonitoramento' id='idEventoMonitoramento'>
															</select>
														</td>
													</tr>
												</table>
											</fieldset>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<fieldset>
												<legend><b>Alertas</b></legend>
												<table>
													<tr>
														<td class="campoEsquerda">E-mail(s) de alerta:</td>
														<td>
															<textarea name='emailsAlerta' rows="5" cols="100"></textarea>
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">Descrição/Texto:</td>
														<td>
															<textarea name='descricaoAlerta' rows="5" cols="100"></textarea>
														</td>
													</tr>
												</table>
											</fieldset>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<table>
												<tr>
													<td>
														<button onclick='gravarAbInc()'>Gravar</button>
													</td>
													<td>
														<button onclick='fecharPopupAbInc()'>Fechar</button>
													</td>
												</tr>
											</table>
										</td>
									</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		<!-- ## FIM - AREA DA APLICACAO ## --></div>
		</div>
		</div>

		<!-- Fim da Pagina de Conteudo --></div>
		<%@include file="/include/footer.jsp"%>
	</body>

</html>