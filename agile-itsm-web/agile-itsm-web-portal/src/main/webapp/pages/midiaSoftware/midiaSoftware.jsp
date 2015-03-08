<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.MidiaSoftwareDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%
		String iframe = "";
	    iframe = request.getParameter("iframe");
	%>
	<%@include file="/include/header.jsp"%>
    <%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="../../cit/objects/MidiaSoftwareChaveDTO.js"></script>
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<script type="text/javascript" src="./js/midiaSoftware.js"></script>
<link rel="stylesheet" type="text/css" href="./css/midiaSoftware.css" />
<%
    //se for chamado por iframe deixa apenas a parte de cadastro da página
    if (iframe != null) {
%>
	<link rel="stylesheet" type="text/css" href="./css/midiaSoftwareIFrame.css">
<%
    }
%>
</head>
<body>
<div id="wrapper">
	<%
	    if (iframe == null) {
	%>
		<%@include file="/include/menu_vertical.jsp"%>
	<%
	    }
	%>
	<div id="main_container" class="main_container container_16 clearfix">
	<%
	    if (iframe == null) {
	%>
		<%@include file="/include/menu_horizontal.jsp"%>
	<%
	    }
	%>

	<div class="flat_area grid_16">
			<h2><fmt:message key="midiaSoftware.midiaDefinitiva" /></h2>
	</div>
  	<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><fmt:message key="midiaSoftware.cadastro" /></a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top"><fmt:message key="midiaSoftware.pesquisa" /></a>
				</li>
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
	 <div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="">
							<form name='form'
							action='${ctx}/pages/midiaSoftware/midiaSoftware'>
								<div class="columns clearfix">
									<input type='hidden' name='idMidiaSoftware' id='idMidiaSoftware' />
									<input type='hidden' name='dataInicio' id='dataInicio' />
									<input type="hidden" id="rowIndex" name="rowIndex"/>
									<input type="hidden" id="midiaSoftwareChaveSerializada" name="midiaSoftwareChaveSerializada"/>

									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome" />
											</label>
											<div >
												<input id="nome" name="nome" type='text' maxlength="200"
													class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="midiaSoftware.endFisico" />
											</label>
											<div>
												<input id="endFisico" type="text" name="endFisico" maxlength="500" class="Valid[Required] Description[midiaSoftware.endFisico]" />
											</div>
										</fieldset>
									</div>

									<div class="col_33">
										<fieldset>
											<label><fmt:message key="midiaSoftware.endLogico" />
											</label>
											<div>
												<input id="endLogico" type="text" name="endLogico" maxlength="200" class="Description[midiaSoftware.endLogico]" />
											</div>
										</fieldset>
									</div>

									<div class="col_33">
										<fieldset   style="height: 52px">
											<label class="campoObrigatorio"><fmt:message key="midiaSoftware.tipoMidia" /> </label>
											<div>
											<select id="idMidia" name='idMidia' class="Valid[Required] Description[midiaSoftware.tipoMidia]"></select>
											</div>
										</fieldset>
										</div>

										<div class = "col_33">
										<fieldset>
											<label><fmt:message key="midiaSoftware.tipoSoftware" /></label>
											<div>
												<select id="idTipoSoftware" name='idTipoSoftware' class="Description[midiaSoftware.tipoSoftware]"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_16">
										<fieldset   style="height: 52px">
											<label><fmt:message key="midiaSoftware.licencas" />
											</label>
											<div style = "width: 50px">
												<input id="licencas" type="text" readonly="readonly" name="licencas"  maxlength="6" class="Format[Numero] Description[midiaSoftware.licencas]" />
											</div>
										</fieldset>
									</div>

									<div class="col_16">
										<fieldset   style="height: 52px">
											<label> <fmt:message key="midiaSoftware.versao" />
											</label>
											<div style = "width: 50px">
												<input id="versao" type="text" name="versao"
													maxlength="20"
													class="Description[midiaSoftware.versao] Format[Numero]" />
											</div>
										</fieldset>
									</div>
									</div>
									<div class="col_66">
										<fieldset>
											<div>
												<!--
													Adicionado label
													@autor flavio.santana
													28/10/2013 10:28
												 -->
												<div class="col_60 chave">
													<label><fmt:message key="midiaSoftware.chave" /></label>
													<div><input type='text' id="chave" name="chave" maxlength="255" /></div>
												</div>
												<div class="col_30 qtdPer">
													<label><fmt:message key="midiaSoftware.qtdPermissoes" /></label>
													<div><input type='text' id="qtdPermissoes" name="qtdPermissoes" maxlength="5" class="Format[Numero]" value="1" /></div>
												</div>
												<button id="buttonAddLimpar" type="button" class="light" onclick="limpaDados()" style="float: right !important;">
													<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
													<span>
														<fmt:message key="citcorpore.comum.limpar" />
													</span>
												</button>
												<button id="buttonAddChave" type="button" class="light" onclick="addItemInfo()" style="float: right !important;">
													<img src="${ctx}/template_new/images/icons/small/grey/list.png">
													<span>
														<fmt:message key="citcorpore.comum.adicionar" />
													</span>
												</button>
											</div>
										</fieldset>
									</div>
									<div class="col_66">
										<table class="tableLess" id="tblMidiaSoftwareChave" >
											<thead>
												<tr>
													<th></th>
													<th><fmt:message key="midiaSoftware.chave" /></th>
													<th><fmt:message key="midiaSoftware.qtdPermissoes" /></th>
												</tr>
											</thead>
										</table>
									</div>
									<div class="col_100">
								<button type='button' name='btnGravar' class="light" onclick='salvar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type="button" name='btnLimpar' class="light" onclick='limpar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnUpDate' class="light"
									onclick='excluir();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message	key="citcorpore.comum.excluir" />
									</span>
								</button>
							   </div>
								<div id="popupCadastroRapido">
			                           <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
		                        </div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_MIDIASOFTWARE' id='LOOKUP_MIDIASOFTWARE' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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


