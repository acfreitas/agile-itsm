<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>

<!doctype html public "">
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

	 var popup;
	 var popup2;
	 var popup3;
	    addEvent(window, "load", load, false);
	    function load(){
			popup = new PopupManager(800, 600, "${ctx}/pages/");
			popup2 = new PopupManager(900, 450, "${ctx}/pages/");
			popup3 = new PopupManager(900, 450, "${ctx}/pages/");
			converteCpfParaPadraoIngles();
			document.form.afterLoad = function () {
				dataAtual();
			}
			document.form.afterRestore = function () {
				dataAtual();
				$('.tabs').tabs('select', 0);
			}
	    }
	    /*Bruno.Aquino : Foi retirado o class de mascaras do campo cpf e inserido ao carregar a página a mascara do jquery para as linguas: Português e espanhol, para inglês não haverá mascara */
		function converteCpfParaPadraoIngles(){
			if('<%=request.getSession().getAttribute("locale")%>'=="en"){
				$("#cpf").unmask();
			}else{
				$("#cpf").mask("999.999.999-99");
			}
	    }
		function verificaValor(obj){
			if (obj.options[obj.selectedIndex].value == 'E'){
				$('valorSalario').innerHTML = 'Valor Sal&atilde;rio CLT:';
			}else if (obj.options[obj.selectedIndex].value == 'S'){
				$('valorSalario').innerHTML = 'Valor Est&atilde;gio:';
			}else if (obj.options[obj.selectedIndex].value == 'P'){
				$('valorSalario').innerHTML = 'Valor Contratado Mensal:';
			}else if (obj.options[obj.selectedIndex].value == 'X'){
				$('valorSalario').innerHTML = 'Valor do Prolabore:';
			}else{
				$('valorSalario').innerHTML = 'Valor Mensal:';
			}
		}

		$(function() {
            $("#POPUP_ENTREVISTAS_NAO_ADMITIDAS").dialog({
            	 autoOpen : false,
                 width : 600,
                 height : 400,
                 modal : true
            });
        });

		$(function() {
			   $('.datepicker').datepicker();
			   $('#telefone').mask('(999) 9999-9999');
		  });

		function ocultarDivGruposContrato(){
			$('#gruposContrato').hide();
		}

		function exibirDivGruposContrato(){
			$('#gruposContrato').show();
		}

	function excluir() {
		if (document.getElementById("idEmpregado").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

	function gravar(){
		if(!validaDatas()){
			return;
		}
		document.form.save();
	}

	function abrePopup() {
		$("#POPUP_ENTREVISTAS_NAO_ADMITIDAS").dialog("open");
	}

	function LOOKUP_ENTREVISTAS_NAO_ADMITIDAS_select(id,desc){
		document.form.restore({idEntrevista:id});
		$("#POPUP_ENTREVISTAS_NAO_ADMITIDAS").dialog("close");
	}

	function validaDatas(){
		if(!nullOrEmpty(gebi("dataNasc"))){
			if(!nullOrEmpty(gebi("dataEmissaoRg"))){
				if (!DateTimeUtil.comparaDatas(document.form.dataNasc, document.form.dataEmissaoRg, i18n_message("citcorpore.comum.validacao.datargmenordatanasc"))){
					return false;
				}
			}
			if(!nullOrEmpty(gebi("dataEm"))){
				if (!DateTimeUtil.comparaDatas(document.form.dataNasc, document.form.dataEm, i18n_message("citcorpore.comum.validacao.emissaoctpsmenornasci"))){
					return false;
				}
			}
			if(!nullOrEmpty(gebi("dataAdmissao"))){
				if (!DateTimeUtil.comparaDatas(document.form.dataNasc, document.form.dataAdmissao, i18n_message("citcorpore.comum.validacao.admissaomenornascimento"))){
					return false;
				}
			}
		}
		return true;
	}


	function gebi(id){
		return document.getElementById(id);
	}

	function nullOrEmpty(comp){
		return comp.value == null || comp.value == "" ? true : false;
	}

	function dataAtual() {
    	hoje = new Date();
    	dia = hoje.getDate();
    	mes = hoje.getMonth();
    	ano = hoje.getFullYear();
    	if (dia < 10)
    	 dia = "0" + dia;
    	if (mes < 10)
	     mes = "0" + (mes+1);
    	if (ano < 2000)
    	 ano = "19" + ano;

    	document.getElementById('dataAdmissao').value = (dia+"/"+ mes +"/"+ano);
    }
</script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da pagina
			if (iframe != null && iframe.equalsIgnoreCase("true")) {%>
<style>
	div#main_container {
		margin: 10px 10px 10px 10px;
	}
</style>
<%}%>
</head>
<body>
	<div id="wrapper">
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
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/admissaoEmpregado/admissaoEmpregado'>
							<div class="columns clearfix">
								<input type='hidden' id="idEntrevista" name='idEntrevista'/>
								<input type='hidden' name='iframe' value="<%=iframe%>"/>
								<input type='hidden' name='idCargo'/>
								<div class="col_100">
								<div class="col_25">
									<fieldset style="height: 70px;">
										<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome"/></label>
											<div>
												<input type='text' style="cursor: pointer;" onkeydown="FormatUtils.noNumbers(this)" onchange="FormatUtils.noNumbers(this)" name="nome" maxlength="80" class="Valid[Required] Description[citcorpore.comum.nome]" onclick="abrePopup()" readonly="readonly" />

											</div>
									</fieldset>
								</div>
								<div  class="col_25">
									<fieldset style="height: 70px;">
										<label class="campoNaoObrigatorio"><fmt:message key="colaborador.tipoColaborador"/></label>
											<div style="height: 35px;">
												<select id='tipo' name='tipo' class="Valid[Required] Description[colaborador.tipoColaborador]" ></select>
											</div>
									</fieldset>
								</div>
								  <div class='col_25'>
								 <fieldset style="height: 70px;">
									<label class="campoObrigatorio" >
										<fmt:message key="unidade.unidade"/>
									 </label>
										<div>
											<select id='idUnidade' name='idUnidade' class="Valid[Required] Description[unidade.unidade]"></select>
										</div>
								    </fieldset>
								  </div>
								  <div class='col_25'>
									  <fieldset style="height: 70px;">
											<label class="campoNaoObrigatorio" >
												<fmt:message key="Cargo"/>
										 	</label>
											<div>
												<input type='text' onkeydown="FormatUtils.noNumbers(this)" onchange="FormatUtils.noNumbers(this)" name="cargo" maxlength="80" class="Valid[Required] Description[cargos.cargos]" readonly="readonly" />
											</div>
									    </fieldset>
								  </div>
								  </div>
								  </div>


						       <h2 id="tituloInformacaoPagamento" class="section"><fmt:message key="colaborador.informacaoPagamento"/></h2>
						       <div id="informacaoPagamento" class="columns clearfix">
						        <div class="col_33">
						        <fieldset style="height: 70px;">
									<label ><fmt:message key="colaborador.valorSalario"/>(R$)</label>
										<div>
											<input id="valorSalario" type="text"  maxlength="15" name='valorSalario' class="Description[colaborador.valorSalario] Format[Moeda]" readonly="readonly"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset style="height: 70px;">
									<label ><fmt:message key="colaborador.valorProdutividadeMedia"/>(R$)</label>
										<div>
											<input type='text'  maxlength="15" name='valorProdutividadeMedia' class=" Format[Moeda] Description[colaborador.valorProdutividadeMedia]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
						        <fieldset style="height: 70px;">
									<label ><fmt:message key="colaborador.valorPlanoSaude"/>(R$)</label>
										<div>
											<input type='text'  maxlength="15" name='valorPlanoSaudeMedia' class=" Description[colaborador.valorPlanoSaude] Format[Moeda]" />
										</div>
								</fieldset>
								</div>
								</div>
								<div class="columns clearfix">
								<div class="col_33">
								<fieldset style="height: 70px;">
									<label ><fmt:message key="colaborador.valorValeTransporte"/>(R$)</label>
										<div>
											<input type='text'  maxlength="15" name='valorVTraMedia' class=" Description[colaborador.valorValeTransporte] Format[Moeda]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset style="height: 70px;">
									<label ><fmt:message key="colaborador.valorValeRefeicaoAlim"/>(R$)</label>
										<div>
											<input type='text'  maxlength="15" name='valorVRefMedia' class=" Description[colaborador.valorValeRefeicaoAlim] Format[Moeda]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset style="height: 70px;">
									<label><fmt:message key="colaborador.custoHora"/>(R$)</label>
										<div>
											<input type='text'  name="custoPorHora" maxlength="10" readonly="readonly" size="10" class="Format[Moeda]" />
										</div>
								</fieldset>
								</div>
								</div>
								<div class="columns clearfix">
								<div class="col_33">
								<fieldset style="height: 70px;">
									<label><fmt:message key="colaborador.custoTotalMensal"/></label>
										<div>
											<input type='text'  name="custoTotalMes" maxlength="15" readonly="readonly" size="15" class="Format[Moeda]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset style="height: 70px;">
									<label><fmt:message key="colaborador.agencia"/></label>
										<div>
											<input type='text' name="agencia" maxlength="10" size="10"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset style="height: 70px;">
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
								  <fieldset style="height: 70px;">
										<label ><fmt:message key="colaborador.cpf"/></label>
											<div>
												<input type='text' id="cpf" name="cpf" maxlength="14" size="15" class=" Description[colaborador.cpf]" />

											</div>
									</fieldset>
									</div>
									<div class="col_33">
										<fieldset style="height: 70px;">
										<label ><fmt:message key="colaborador.dataNascimento"/></label>
											<div>
												<input  type='text'  id="dataNasc"  name="dataNascimento" maxlength="10" size="10"  class="Valid[Data] Description[colaborador.dataNascimento] Format[Data]" readonly="readonly"/>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
									<fieldset style="height: 70px;">
										<label ><fmt:message key="colaborador.sexo"/></label>
											<div class="inline clearfix">
												<label><input type='radio' id="sexoMasculino" name="sexo" value="M" class="  Description[colaborador.sexo]"   />
													<fmt:message key="colaborador.masculino"/>
												</label>
												<label><input type='radio' id="sexoFeminino" name="sexo" value="F" class="  Description[colaborador.sexo]"  />
													<fmt:message key="colaborador.feminino"/>
												</label>
											</div>
									</fieldset>
								 </div>
								</div>
								<div class="columns clearfix">
								  	<div class="col_33">
									<fieldset style="height: 70px;">
										<label ><fmt:message key="colaborador.rg"/></label>
											<div>
												<input type='text' name="rg" maxlength="15" size="15" />
											</div>
									</fieldset>
									</div>
									<div class="col_33">
									<fieldset style="height: 70px;">
										<label><fmt:message key="colaborador.dataEmissaoRg"/></label>
											<div>
												<input type='text' id="dataEmissaoRg" name="dataEmissaoRG" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataEmissaoRg] Format[Data] datepicker"/>
											</div>
									</fieldset>
								 </div>
								 <div class="col_16">
									<fieldset style="height: 70px;">
										<label><fmt:message key="colaborador.orgaoExpedidor"/></label>
											<div>
												<input type='text' name="orgExpedidor" maxlength="10" size="10" />
											</div>
									</fieldset>
									</div>
									<div class="col_16">
									<fieldset style="height: 70px;">
										<label><fmt:message key="colaborador.ufExpedidor"/></label>
											<div>
												<select name='idUFOrgExpedidor'></select>
											</div>
									</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
								  	<div class="col_33">
									<fieldset style="height: 70px;">
										<label><fmt:message key="colaborador.numeroCtps"/></label>
											<div>
												<input type='text' name="ctpsNumero" maxlength="15" size="15" class="Description[colaborador.numeroCtps]"/>
											</div>
									</fieldset>
									</div>
									<div class="col_33">
									<fieldset style="height: 70px;">
										<label><fmt:message key="colaborador.dataEmissaoCtps"/></label>
											<div>
												<input type='text' id="dataEm" name="ctpsDataEmissao" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataEmissaoCtps] Format[Data] datepicker"/>
											</div>
									</fieldset>
								 </div>
								 <div class="col_16">
									<fieldset style="height: 70px;">
										<label><fmt:message key="colaborador.serieCtps"/></label>
											<div>
												<input type='text' id="ctpsSerie" name="ctpsSerie" maxlength="10" size="10"  class="Description[colaborador.serieCtps]"/>
											</div>
									</fieldset>
									</div>
									<div class="col_16">
									<fieldset style="height: 70px;">
										<label><fmt:message key="colaborador.ufCtps"/></label>
											<div>
												<select name='ctpsIdUf' class="Description[colaborador.ufCtps]"></select>
											</div>
									</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
								  	<div class="col_33">
									<fieldset style="height: 70px;">
										<label><fmt:message key="colaborador.nit"/></label>
											<div>
							       				<input type='text' name="nit" maxlength="20" size="20" class="Description[colaborador.nit]"/>
											</div>
									</fieldset>
									</div>
									<div class="col_33">
									<fieldset style="height: 70px;">
										<label><fmt:message key="colaborador.dataAdmissao"/></label>
											<div>
							       				<input type='text' id="dataAdmissao" name="dataAdmissao" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataAdmissao] Format[Data]" readonly="readonly"/>
											</div>
									</fieldset>
								 </div>
								 <div class="col_33">
										<fieldset style="height: 70px;">
											<label><fmt:message key="colaborador.estadoCivil"/></label>
												<div>
								       				<select name='estadoCivil'  ></select>
												</div>
										</fieldset>
									</div>

								</div>
								<div class="col_100">
								  <div class="col_33">
										<fieldset style="height: 70px;">
											<label><fmt:message key="colaborador.conjuge"/></label>
												<div>
								       				<input type='text' name="conjuge" maxlength="50" size="50"/>
												</div>
										</fieldset>
									</div>
								  <div class="col_33">
										<fieldset style="height: 70px;">
											<label><fmt:message key="colaborador.pai"/></label>
												<div>
								       				<input type='text' name="pai" maxlength="50" size="50"/>
												</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset style="height: 70px;">
											<label><fmt:message key="colaborador.mae"/></label>
												<div>
								       				<input type='text' name="mae" maxlength="50" size="50"/>
												</div>
										</fieldset>
									</div>
									<div class="columns clearfix">
								  <div class="col_100">
										<fieldset>
											<label><fmt:message key="colaborador.observacao"/></label>
												<div>
								       				<textarea name="observacoes" cols='70' maxlength ="2000" rows='5'></textarea>
												</div>
										</fieldset>
									</div>
								</div>
							</div>
							<div id="popupCadastroRapido">
							     <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
						    </div>
							<br><br>
							<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
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
						</form>
					</div>
				</div>
					</div>
			</div>
		</div>
	</div>
	<div id="POPUP_ENTREVISTAS_NAO_ADMITIDAS" title="<fmt:message key="citcorpore.comum.pesquisa" />">
        <div class="box grid_16 tabs">
            <div class="toggle_container">
                <div id="tabs-2" class="block">
                    <div class="section">
                        <form name='formEntrevista' style="width: 540px">
                            <cit:findField formName='formEntrevista'
                                lockupName='LOOKUP_ENTREVISTAS_NAO_ADMITIDAS' id='LOOKUP_ENTREVISTAS_NAO_ADMITIDAS' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
                        </form>
                    </div>
                </div>
            </div>
        </div>
   </div>
</body>
<%//se for chamado por iframe deixa apenas a parte de cadastro da pagina
	if (iframe != null && iframe.equalsIgnoreCase("true")) {%>
		<script type="text/javascript">
			document.getElementById("divOutrasInformacoes").style.display = "none";
		</script>
<%}%>
<%@include file="/include/footer.jsp"%>
</html>
