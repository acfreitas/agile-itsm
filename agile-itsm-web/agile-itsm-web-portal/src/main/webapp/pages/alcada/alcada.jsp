<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.citframework.util.UtilHTML"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.AlcadaDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.LimiteAlcadaDTO"%>

<!doctype html public "">
<html>
	<head>
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");
		%>
		<%@include file="/include/header.jsp"%>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>

		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<link type="text/css" rel="stylesheet" href="css/alcadaAll.css"/>

		<script type="text/javascript" src="js/alcada.js"></script>

	<%
		// Se for chamado por iframe deixa apenas a parte de cadastro da página
		if (iframe != null) {
	%>
		<link type="text/css" rel="stylesheet" href="css/alcadaIframe.css"/>
	<%
		}
	%>
	</head>
	<body>
		<div id="wrapper" style="overflow: hidden;">
		<%
			if (iframe == null) {
		%>
			<%@ include file="/include/menu_vertical.jsp" %>
		<%
			}
		%>
			<div id="main_container" class="main_container container_16 clearfix">
			<%
				if (iframe == null) {
			%>
				<%@ include file="/include/menu_horizontal.jsp" %>
			<%
				}
			%>
				<div class="flat_area grid_16">
					<h2><fmt:message key="alcada"/></h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="alcada.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><fmt:message key="alcada.pesquisa"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/alcada/alcada'>
									<div class="columns clearfix">
										<input type='hidden' name='idAlcada'/>
										<input type='hidden' name='listLimites'/>
										<div class="columns clearfix">
											<div class="col_40">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="alcada.nome"/></label>
														<div>
														  <input type='text' name="nomeAlcada" maxlength="70" size="70" class="Valid[Required] Description[alcada.nome]"/>
														</div>
												</fieldset>
											</div>
											<div class="col_30">
												<fieldset style="height: 55px !important;">
													<label class="campoObrigatorio"><fmt:message key="alcada.tipo"/></label>
														<div>
														  <select name='tipoAlcada' id='tipoAlcada' class="Valid[Required] Description[alcada.tipo]"></select>
														</div>
												</fieldset>
											</div>
											<div class="col_30">
												<fieldset style="height: 55px !important;">
													<label class="campoObrigatorio campoEsquerda"><fmt:message key="citcorpore.comum.situacao"/></label>
													<input class="Valid[Required] Description[citcorpore.comum.situacao]" type="radio" name="situacao" id="situacao" value="A" checked="checked"/><fmt:message key="citcorpore.comum.ativo"/>
													<input class="Valid[Required] Description[citcorpore.comum.situacao]" type="radio" name="situacao" id="situacao" value="I"/><fmt:message key="citcorpore.comum.inativo"/>
												</fieldset>
											</div>
										</div>
										<br>
									</div>

									<h2 class="section">
										<fmt:message key="alcada.limite" />
									</h2>
									<div class="columns clearfix">
										<div class="col_100">
											<fieldset>
												<label id="addGrupoItemConfig" style="cursor: pointer;" title="<fmt:message key="alcada.limite.clique_adicionar" />">
													<fmt:message key="alcada.limite.adicionar_limite" />
													<img src="${ctx}/imagens/add.png">
												</label>
												<div  id="divLimites">
												<table class="table" id="tabelaLimites" style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th ><fmt:message key="grupo.grupo" /></th>
														<th style="width: 15%;"><fmt:message  key="alcada.limite.tipoLimite" /></th>
														<th style="width: 15%;"><fmt:message  key="alcada.limite.abrangencia" /></th>
                                                        <th style="width: 5%;"><fmt:message  key="citcorpore.comum.situacao" /></th>
                                                        <th style="width: 10%;"><fmt:message  key="alcada.limite.limiteItemUsoInterno" /></th>
                                                        <th style="width: 10%;"><fmt:message  key="alcada.limite.limiteMensalUsoInterno" /></th>
                                                        <th style="width: 10%;"><fmt:message  key="alcada.limite.limiteItemAtendCliente" /></th>
                                                        <th style="width: 10%;"><fmt:message  key="alcada.limite.limiteMensalAtendCliente" /></th>
													</tr>
												</table>
											</div>
											</fieldset>
										</div>
									</div>

									<br><br>
									<button type='button' name='btnGravar' class="light" onclick='serializaLimite();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" /></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();document.formLimite.clear();deleteAllRows();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar" /></span>
									</button>
									<button type='button' name='btnExcluir' class="light" onclick='excluir();'>
										<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
										<span><fmt:message key="citcorpore.comum.excluir" /></span>
									</button>




								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_ALCADA' id='LOOKUP_ALCADA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- TELA DE CADASTRO DE LIMITES -->
		<div id="POPUP_ADICIONARLIMITE" title="<fmt:message key="alcada.limite.adicionar_limite" />">
			<div class="box grid_16 ">
				<div class="toggle_container">
					<div class="section">
					<form name='formLimite' action='${ctx}/pages/alcada/alcada'>
						<div class="col_100">
								<div class="col_40">
									<fieldset style="height: 55px">
										<label class="campoObrigatorio">
											<fmt:message key="grupo.grupo"/>
										</label>
										<div>
											<input onclick="pesquisaGrupos()" readonly="readonly" style="width: 90% !important;" type='text' name="grupoLimite" id="grupoLimite" maxlength="70" size="70"  />
											<img onclick="pesquisaGrupos()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
											<input type="hidden" name="idGrupoLimite" id="idGrupoLimite" />
										</div>
									</fieldset>
								</div>
								<div class="col_20">
									<fieldset style="height: 55px">
										<label><fmt:message key="alcada.limite.tipoLimite"/></label>
											<div>
											  <select name='tipoLimite' id='tipoLimite' onchange="desabilitaValores(this);" class="Valid[Required] Description[tipoLimite]">
											  	<option value="F"><fmt:message key="alcada.limite.faixaValores"/></option>
											  	<option value="Q"><fmt:message key="alcada.limite.qualquerValor"/></option>
											  </select>
											</div>
									</fieldset>
                				</div>
								<div class="col_25">
									<fieldset style="height: 55px">
										<label class="campoEsquerda"><fmt:message key="alcada.limite.abrangencia"/></label>
											<div>
											  <select name='abrangenciaCentroCustoLimite' id='abrangenciaCentroCustoLimite' class="Valid[Required] Description[alcada.limite.abrangencia]">
											  	<option value="T"><fmt:message key="alcada.limite.todos"/></option>
											  	<option value="R"><fmt:message key="alcada.limite.somenteResponsal"/></option>
											  </select>
											</div>
									</fieldset>
								</div>
				                <div class="col_15">
				                    <fieldset style="height: 55px !important;">
				                        <label class="campoObrigatorio"><fmt:message key="citcorpore.comum.situacao"/></label>
				                        <input class="Valid[Required] Description[citcorpore.comum.situacao]" type="radio" name="situacaoLimite" id="situacaoLimite" value="A" checked="checked"/><fmt:message key="citcorpore.comum.ativo"/>
				                        <input class="Valid[Required] Description[citcorpore.comum.situacao]" type="radio" name="situacaoLimite" id="situacaoLimite" value="I"/><fmt:message key="citcorpore.comum.inativo"/>
				                    </fieldset>
				                </div>
                    </div>
					<div class="col_100" id="divValorItem">
                        <div class="col_50">
							<fieldset>
								<label><fmt:message key="alcada.limite.limiteItemUsoInterno"/></label>
									<div>
									  <input type='text' name='limiteItemUsoInterno' id="limiteItemUsoInterno" size="20" maxlength="8" style="width: 250px !important;" class="Description[alcada.limite.limiteItemUsoInterno] Format[Moeda]"/>
									</div>
							</fieldset>
                        </div>
                        <div class="col_50">
		                    <fieldset>
		                        <label><fmt:message key="alcada.limite.limiteItemAtendCliente"/></label>
		                            <div>
		                              <input type='text' name='limiteItemAtendCliente' id="limiteItemAtendCliente" size="20" maxlength="8" style="width: 250px !important;" class="Description[alcada.limite.limiteItemAtendCliente] Format[Moeda]"/>
		                            </div>
		                    </fieldset>
                        </div>
					</div>
					<div class="col_100" id="divValorMensal">
                        <div class="col_50">
							<fieldset>
								<label><fmt:message key="alcada.limite.limiteMensalUsoInterno"/></label>
									<div>
									  <input type='text' name='limiteMensalUsoInterno' id="limiteMensalUsoInterno" size="20" maxlength="8" style="width: 250px !important;" class="Description[alcada.limite.limiteMensalUsoInterno] Format[Moeda]"/>
									</div>
							</fieldset>
                        </div>
                        <div class="col_50">
		                    <fieldset>
		                        <label><fmt:message key="alcada.limite.limiteMensalAtendCliente"/></label>
		                            <div>
		                              <input type='text' name='limiteMensalAtendCliente' id="limiteMensalAtendCliente" size="20" maxlength="8" style="width: 250px !important;" class="Description[alcada.limite.limiteMensalAtendCliente] Format[Moeda]"/>
		                            </div>
		                    </fieldset>
                        </div>
					</div>
		</div>
		<br /><br />
		<button type='button' name='btnGravar' class="light" onclick='adicionarLimite();'>
				<img src="${ctx}/template_new/images/icons/small/util/adcionar.png">
				<span><fmt:message key="citcorpore.comum.adicionar" /></span>
			</button>
		</form>
						</div>
				</div>
			</div>
		</div>
		<!-- FIM TELA DE CADASTRO DE LIMITES -->

		<div id="POPUP_GRUPO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
			<div class="box grid_16 tabs" style="width: 570px;">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaFabricante' style="width: 540px">
								<cit:findField formName='formPesquisaFabricante'
									lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>