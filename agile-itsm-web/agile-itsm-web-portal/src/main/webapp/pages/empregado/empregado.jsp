<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor"%>

<!DOCTYPE html>
<compress:html
	enabled="true"
	jsCompressor="closure"
	compressCss="true"
	compressJavaScript="true"
	removeComments="true"
	removeMultiSpaces="true">
<html>
<head>
<%
	String iframe = "";
	String idContrato = "";
	iframe = request.getParameter("iframe");
	if (iframe == null){
	    iframe = "false";
	}
%>
	<%@include file="/include/header.jsp"%>

	<%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script  charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
	<script  charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
	<script type="text/javascript">
		var ctx = "${ctx}";
	</script>
	<script  charset="ISO-8859-1" type="text/javascript" src="./js/empregado.js"></script>


<%//se for chamado por iframe deixa apenas a parte de cadastro da pagina
			if (iframe != null && iframe.equalsIgnoreCase("true")) {%>
<link href="./css/atualiza-antigo.css" rel="stylesheet" />
<%}%>
</head>
<body>
	<div id="">
		<%if (iframe == null || iframe.equalsIgnoreCase("false")) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
<!-- Conteudo -->
	<div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null || iframe.equalsIgnoreCase("false")) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
		<%}%>



		<div class="flat_area grid_16">
				<h2><fmt:message key="colaborador.colaborador"/></h2>
		</div>
		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><fmt:message key="colaborador.cadastroColaborador"/></a>
				</li>
				<%if (!iframe.equalsIgnoreCase("true")){%>
				<li>
					<a href="#tabs-2" class="round_top"><fmt:message key="colaborador.pesquisacolaborador"/></a>
				</li>
				<%}%>
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/empregado/empregado'>
							<div class="columns clearfix">
								<input type='hidden' id="idEmpregado" name='idEmpregado'/>
								<input type='hidden' name='dataFim'/>
								<input type='hidden' name='iframe' value="<%=iframe%>"/>
								<input type='hidden' name='idContrato'/>
								<div class="col_50">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome"/></label>
											<div>
												<!-- Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Criação de função para não digitar numeros, para retirar bug de não poder usar setas do teclado . -->
												<input type='text' onkeypress="return naoDigitarNumeros(event);" name="nome" maxlength="256" class="Valid[Required] Description[citcorpore.comum.nome]"  />

											</div>
									</fieldset>
								</div>
								<div  class="col_25">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="colaborador.tipoParceiro"/></label>
											<div style="height: 35px;">
												<select name='tipo' id="tipo"  class="Valid[Required] Description[colaborador.tipoColaborador]" onchange='verificaValor(this)'>
												</select>
											</div>
									</fieldset>
								</div>
								<div  class="col_25">
								<fieldset>
									<label class="campoObrigatorio"><fmt:message key="colaborador.situacao"/></label>
										<div style="height: 35px;">
											<select name='idSituacaoFuncional' class="Valid[Required] Description[colaborador.situacao]"></select>
										</div>
								</fieldset>
								</div>
							<div class="col_100">
								<div class="col_50">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.email"/></label>
											<div style="height: 39px;">
												<input maxlength="200" type='text' name="email"  class="Valid[Required] Description[citcorpore.comum.email]" />
											</div>
									</fieldset>
								</div>

									<div class="col_25">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.telefone"/></label>
											<div style="height: 39px;">
												<input maxlength="45" type='text' id="telefone" name="telefone"  class="Valid[Required] Description[citcorpore.comum.telefone]" />
											</div>
									</fieldset>
									</div>
									<div class="col_25">
									<fieldset>
										<label><fmt:message key="citcorpore.comum.ramal"/></label>
											<div style="height: 39px;">
												<input maxlength="5" type='text' id="ramal" name="ramal" class="Format[Numero]" />
											</div>
									</fieldset>
									</div>
								</div>
								  <div class='col_50'>
								  <fieldset >
									<label id="labelUnidade" class="tooltip_bottom campoObrigatorio" title="<fmt:message key="unidade.unidade"/>" >
										<fmt:message key="unidade.unidade"/>
										<%if (iframe == null || iframe.equalsIgnoreCase("false")) {%>
										<img src="${ctx}/imagens/add.png"
											onclick="popup2.abrePopup('unidade', 'preencherComboUnidade')">
											<%}%>
									 </label>
										<div>
											<select id='idUnidade' name='idUnidade' class="Description[unidade.unidade]"></select>
										</div>
								    </fieldset>
								  </div>
								  <div class='col_50'>
									  <fieldset >
											<label id="labelCargos" class="tooltip_bottom campoObrigatorio" title="<fmt:message key="cargos.cargos"/>" >
												<fmt:message key="cargos.cargos"/>
												<%if (iframe == null || iframe.equalsIgnoreCase("false")) {%>
												<img src="${ctx}/imagens/add.png"
													onclick="popup3.abrePopup('cargos', 'preencherComboCargos')">
													<%}%>
										 	</label>
											<div>
												<select id='idCargo' name='idCargo' class="Description[cargos.cargos]"></select>
											</div>
									    </fieldset>
								  </div>
								  </div>
								  <div id="gruposContrato" class="col_100">
										<div class='col_100'>
											<fieldset >
								  				<label title="Grupo" ><fmt:message key="grupo.grupo"/></label>
								  				<div>
													<select id='idGrupo' name='idGrupo'></select>
								  				</div>
											</fieldset>
										</div>
								  </div>

							<div id="divOutrasInformacoes" style="display: block;">


						       <h2 id="tituloInformacaoPagamento" class="section"><fmt:message key="colaborador.informacaoPagamento"/></h2>
						       <div id="informacaoPagamento" class="columns clearfix">
						        <div class="col_33">
						        <fieldset>
									<label ><fmt:message key="colaborador.valorSalario"/>(<fmt:message key="citcorpore.comum.simboloMonetario"/>)</label>
										<div>
											<input id="valorSalario" type="text"  maxlength="15" name='valorSalario' class="Description[colaborador.valorSalario] Format[Moeda]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset>
									<label ><fmt:message key="colaborador.valorProdutividadeMedia"/>(<fmt:message key="citcorpore.comum.simboloMonetario"/>)</label>
										<div>
											<input type='text'  maxlength="15" name='valorProdutividadeMedia' class=" Format[Moeda] Description[colaborador.valorProdutividadeMedia]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
						        <fieldset>
									<label ><fmt:message key="colaborador.valorPlanoSaude"/>(<fmt:message key="citcorpore.comum.simboloMonetario"/>)</label>
										<div>
											<input type='text'  maxlength="15" name='valorPlanoSaudeMedia' class=" Description[colaborador.valorPlanoSaude] Format[Moeda]" />
										</div>
								</fieldset>
								</div>
								</div>
								<div class="columns clearfix">
								<div class="col_33">
								<fieldset>
									<label ><fmt:message key="colaborador.valorValeTransporte"/>(<fmt:message key="citcorpore.comum.simboloMonetario"/>)</label>
										<div>
											<input type='text'  maxlength="15" name='valorVTraMedia' class=" Description[colaborador.valorValeTransporte] Format[Moeda]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset>
									<label ><fmt:message key="colaborador.valorValeRefeicaoAlim"/>(<fmt:message key="citcorpore.comum.simboloMonetario"/>)</label>
										<div>
											<input type='text'  maxlength="15" name='valorVRefMedia' class=" Description[colaborador.valorValeRefeicaoAlim] Format[Moeda]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset>
									<label><fmt:message key="colaborador.custoHora"/>(<fmt:message key="citcorpore.comum.simboloMonetario"/>)</label>
										<div>
											<input type='text'  name="custoPorHora" maxlength="10" readonly="readonly" size="10" class="Format[Moeda]" />
										</div>
								</fieldset>
								</div>
								</div>
								<div class="columns clearfix">
								<div class="col_33">
								<fieldset>
									<label><fmt:message key="colaborador.custoTotalMensal"/></label>
										<div>
											<input type='text'  name="custoTotalMes" maxlength="15" readonly="readonly" size="15" class="Format[Moeda]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset>
									<label><fmt:message key="colaborador.agencia"/></label>
										<div>
											<input type='text' name="agencia" maxlength="10" size="10"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset>
									<label><fmt:message key="colaborador.conta"/></label>
										<div>
											<input type='text' name="contaSalario" maxlength="20" size="20" />
										</div>
								</fieldset>
								</div>
								</div>
								<h2 class="section"><fmt:message key="colaborador.dadosPessoais"/></h2>
								 <div class="columns clearfix">
								  <div class="col_33">
								  <fieldset>
										<label ><fmt:message key="colaborador.cpf"/></label>
											<div>
												<input type='text' id="cpf" name="cpf" maxlength="14" size="15" class="Format[Numero] Description[colaborador.cpf]" />

											</div>
									</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
										<label ><fmt:message key="colaborador.dataNascimento"/></label>
											<div>
												<input  type='text'  id="dataNasc"  name="dataNascimento" maxlength="10" size="10"  class="Valid[Data] Description[colaborador.dataNascimento] Format[Data] datepicker" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
									<fieldset>
										<label ><fmt:message key="colaborador.sexo"/></label>
											<div class="inline clearfix">
												<label><input type='radio' id="sexoMasculino" name="sexo" value="M" class="  Description[colaborador.sexo]" />
													<fmt:message key="colaborador.masculino"/>
												</label>
												<label><input type='radio' id="sexoFeminino" name="sexo" value="F" class="  Description[colaborador.sexo]" />
													<fmt:message key="colaborador.feminino"/>
												</label>
											</div>
									</fieldset>
								 </div>
								</div>
								<div class="columns clearfix">
								  	<div class="col_33">
									<fieldset>
										<label ><fmt:message key="colaborador.rg"/></label>
											<div>
												<input type='text' name="rg" maxlength="15" size="15" />
											</div>
									</fieldset>
									</div>
									<div class="col_33">
									<fieldset>
										<label><fmt:message key="colaborador.dataEmissaoRg"/></label>
											<div>
												<input type='text' id="dataEmissaoRg" name="dataEmissaoRG" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataEmissaoRg] Format[Data] datepicker"/>
											</div>
									</fieldset>
								 </div>
								 <div class="col_16">
									<fieldset>
										<label><fmt:message key="colaborador.orgaoExpedidor"/></label>
											<div>
												<input type='text' name="orgExpedidor" maxlength="15" size="10" />
											</div>
									</fieldset>
									</div>
									<div class="col_16">
									<fieldset>
										<label><fmt:message key="colaborador.ufExpedidor"/></label>
											<div>
												<select name='idUFOrgExpedidor'></select>
											</div>
									</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
								  	<div class="col_33">
									<fieldset>
										<label><fmt:message key="colaborador.numeroCtps"/></label>
											<div>
												<input type='text' name="ctpsNumero" maxlength="15" size="15" class="Description[colaborador.numeroCtps]"/>
											</div>
									</fieldset>
									</div>
									<div class="col_33">
									<fieldset>
										<label><fmt:message key="colaborador.dataEmissaoCtps"/></label>
											<div>
												<input type='text' id="dataEm" name="ctpsDataEmissao" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataEmissaoCtps] Format[Data] datepicker"/>
											</div>
									</fieldset>
								 </div>
								 <div class="col_16">
									<fieldset>
										<label><fmt:message key="colaborador.serieCtps"/></label>
											<div>
												<input type='text' id="ctpsSerie" name="ctpsSerie" maxlength="10" size="10"  class="Description[colaborador.serieCtps]"/>
											</div>
									</fieldset>
									</div>
									<div class="col_16">
									<fieldset>
										<label><fmt:message key="colaborador.ufCtps"/></label>
											<div>
												<select name='ctpsIdUf' class="Description[colaborador.ufCtps]"></select>
											</div>
									</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
								  	<div class="col_33">
									<fieldset>
										<label><fmt:message key="colaborador.nit"/></label>
											<div>
							       				<input type='text' name="nit" maxlength="20" size="20" class="Description[colaborador.nit]"/>
											</div>
									</fieldset>
									</div>
									<div class="col_33">
									<fieldset>
										<label><fmt:message key="colaborador.dataAdmissao"/></label>
											<div>
							       				<input type='text' id="dataAdmissao" name="dataAdmissao" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataAdmissao] Format[Data] datepicker"/>
											</div>
									</fieldset>
								 </div>
								 <div class="col_16">
									<fieldset>
										<label><fmt:message key="colaborador.dataDesligamento"/></label>
											<div>
												<input type='text' id="dataDemissao" name="dataDemissao" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataDesligamento] Format[Data] datepicker"/>
											</div>
									</fieldset>
									</div>

								</div>
								<div class="columns clearfix">
								  <div class="col_66">
										<fieldset>
											<label><fmt:message key="colaborador.conjuge"/></label>
												<div>
								       				<input type='text' name="conjuge" maxlength="50" size="50"/>
												</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><fmt:message key="colaborador.estadoCivil"/></label>
												<div>
								       				<select name='estadoCivil'></select>
												</div>
										</fieldset>
									</div>
								</div>
							    <div class="columns clearfix">
								  <div class="col_50">
										<fieldset>
											<label><fmt:message key="colaborador.pai"/></label>
												<div>
								       				<input type='text' name="pai" maxlength="50" size="50"/>
												</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="colaborador.mae"/></label>
												<div>
								       				<input type='text' name="mae" maxlength="50" size="50"/>
												</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
								  <div class="col_50">
										<fieldset>
											<label><fmt:message key="colaborador.observacao"/></label>
												<div>
								       				<textarea name="observacoes" cols='70' maxlength ="2147483647" rows='5'></textarea>
												</div>
										</fieldset>
									</div>
								</div>
<!-- 								<div class="columns clearfix" id='divListaContratos' style='display: none'>           -->
<!-- 								  <div class="col_100"> -->
<!-- 										<fieldset id='fldListaContratos'> -->
<!-- 										</fieldset> -->
<!-- 									</div> 	 -->
<!-- 								</div>								 -->
							</div>
							<div id="popupCadastroRapido">
							     <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="99%"></iframe>
						    </div>
							<br><br>
							<fieldset>
							<div>
								<button type='button' name='btnGravar' class="light"  onclick="gravar();">
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar"/></span>
								</button>
								<%if (!iframe.equalsIgnoreCase("true")){%>
								<button type="button" name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar"/></span>
								</button>
								<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
										<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
										<span><fmt:message key="citcorpore.comum.excluir" /></span>
								</button>
								<%}%>
							</div>
							</fieldset>
						</form>
						</div>
					</div>
				</div>
					<%if (!iframe.equalsIgnoreCase("true")){%>
					<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<%}%>
				</div>
			</div>
		</div>
	</div>
</body>
<%//se for chamado por iframe deixa apenas a parte de cadastro da pagina
	if (iframe != null && iframe.equalsIgnoreCase("true")) {%>
		<script>
			document.getElementById("divOutrasInformacoes").style.display = "none";
		</script>
<%}%>
<%@include file="/include/footer.jsp"%>
</html>
</compress:html>
