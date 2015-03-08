<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.FornecedorDTO" %>
<%@ page import="br.com.centralit.citcorpore.bean.EnderecoDTO" %>

<!doctype html public "">
<html>
	<head>
		<%@include file="/include/header.jsp"%>
		<%@ include file="/include/security/security.jsp" %>
		<title>
			<fmt:message key="citcorpore.comum.title" />
		</title>
		<%@ include file="/include/menu/menuConfig.jsp" %>
		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");

			String language = (String) request.getSession().getAttribute("locale");
			pageContext.setAttribute("language", language);
		%>

<%
	// Se for chamado por iframe deixa apenas a parte de cadastro da p?ina
	if (iframe != null) {
%>
		<link rel="stylesheet" type="text/css" href="./css/fornecedor.css">
<%
	}
%>
	</head>

	<body>
		<div id="wrapper">
	<%
		if (iframe == null) {
	%>
			<%@ include file="/include/menu_vertical.jsp" %>
	<%
		}
	%>
			<!-- CONTEUDO -->
			<div id="main_container" class="main_container container_16 clearfix">
		<%
			if (iframe == null) {
		%>
				<%@ include file="/include/menu_horizontal.jsp" %>
		<%
			}
		%>
				<div class="flat_area grid_16">
					<h2>
						<fmt:message key="fornecedor" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1">
								<fmt:message key="fornecedor.cadastro" />
							</a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top">
								<fmt:message key="fornecedor.pesquisa" />
							</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form id="form" name="form" action="${ctx}/pages/fornecedor/fornecedor">
									<input type="hidden" id="idFornecedor" name="idFornecedor" />
									<input type="hidden" id="idEndereco" name="idEndereco" />
									<input type="hidden" id="idPais" name="idPais" />
									<input type="hidden" id="idUf" name="idUf" />
									<input type="hidden" id="idCidade" name="idCidade" />
									<input type="hidden" id="tipoPessoa" name="tipoPessoa" />
									<div class="columns clearfix">
										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="fornecedor.nomeRazaoSocial" />
												</label>
												<div>
													<input type="text" id="razaoSocial" name="razaoSocial" maxlength="100" class="Valid[Required] Description[fornecedor.nomeRazaoSocial]" />
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="fornecedor.nomeFantasia" />
												</label>
												<div>
													<input type="text" id="nomeFantasia" name="nomeFantasia" maxlength="70" class="Valid[Required] Description[fornecedor.nomeFantasia]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="fornecedor.tipoPessoa" />
												</label>
												<div style="height: 32px;">
													<select id="comboTiposPessoa" name="comboTiposPessoa">
                                                        <option value="">
                                                            <fmt:message key="citcorpore.comum.selecione" />
                                                        </option>
														<option value="J">
															<fmt:message key="fornecedor.juridica" />
														</option>
                                                        <option value="F">
                                                            <fmt:message key="fornecedor.fisica" />
                                                        </option>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<fmt:message key="fornecedor.cpfcnpj" />
												</label>
												<div>
													<input type="text" id="cnpj" name="cnpj" value="<fmt:message key="fornecedor.selecione_tipo_pessoa" />" maxlength="14" class="Description[fornecedor.cpfcnpj]" onblur="verificaCpfCnpj(this);"/>


												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<fmt:message key="avaliacaoFonecedor.inscricaoEstadual" />
												</label>
												<div>
													<input type="text" id="inscricaoEstadual" name="inscricaoEstadual" maxlength="25" class="Description[avaliacaoFonecedor.inscricaoEstadual]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<fmt:message key="avaliacaoFornecedor.inscricaoMunicipal" />
												</label>
												<div>
													<input type="text" id="inscricaoMunicipal" name="inscricaoMunicipal" maxlength="25" class="Description[avaliacaoFonecedor.inscricaoMunicipal]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<fmt:message key="fornecedor.telefone" />
												</label>
												<div>
													<input type="text" id="telefone" name="telefone" maxlength="20" class="Description[fornecedor.telefone]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<fmt:message key="fornecedor.fax" />
												</label>
												<div>
													<input type="text" id="fax" name="fax" maxlength="20" class="Description[fornecedor.fax]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<fmt:message key="fornecedor.email" />
												</label>
												<div>
													<input type="text" id="email" name="email" maxlength="255" class="Description[fornecedor.email]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<fmt:message key="fornecedor.nomeContato" />
												</label>
												<div>
													<input type="text" id="nomeContato" name="nomeContato" maxlength="100" class="Description[fornecedor.email]" />
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label>
													<fmt:message key="unidade.logradouro" />
												</label>
												<div>
													<input type="text" id="logradouro" name="logradouro" maxlength="200" class="Description[unidade.logradouro]" />
												</div>
											</fieldset>
										</div>
										<div class="col_10">
											<fieldset>
												<label>
													<fmt:message key="localidade.numero" />
												</label>
												<div>
													<input type="text" id="numero" name="numero" maxlength="20" class="Description[localidade.numero]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<fmt:message key="localidade.complemento" />
												</label>
												<div>
													<input type="text" id="complemento" name="complemento" maxlength="200" class="Description[localidade.complemento]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<fmt:message key="localidade.bairro" />
												</label>
												<div>
													<input type="text" id="bairro" name="bairro" maxlength="200" class="Description[localidade.bairro]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<fmt:message key="avaliacaoFonecedor.cep" />
												</label>
												<div>
													<input type="text" id="cep" name="cep" maxlength="8" class="Description[avaliacaoFonecedor.cep]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="unidade.pais" />
												</label>
												<div style="height: 32px;">
													<select id="comboPaises" name="comboPaises" class="Valid[Required] Description[unidade.pais]"></select>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="avaliacaoFonecedor.uf" />
												</label>
												<div style="height: 32px;">
													<select id="comboUfs" name="comboUfs" class="Valid[Required] Description[avaliacaoFonecedor.uf]"></select>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="avaliacaoFonecedor.cidade" />
												</label>
												<div style="height: 32px;">
													<select id="comboCidades" name="comboCidades" class="Valid[Required] Description[avaliacaoFonecedor.cidade]"></select>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="columns clearfix">
										<div class="col_50" >
											<fieldset>
												<label>
													<fmt:message key="fornecedor.observacao" />
												</label>
												<div>
													<textarea id="observacao" name="observacao" maxlength="2147483647" cols="50" rows="4" class="Description[fornecedor.observacao]"></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label>
													<fmt:message key="fornecedor.responsabilidades" />
												</label>
												<div>
													<textarea id="responsabilidades" name="responsabilidades" maxlength="250" cols="50" rows="4" class="Description['Responsabilidades']"></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="fornecedor.tipoDeRegistro" />
												</label>
												<div style="height: 32px;">
													<select id="idTipoRegistro" name="idTipoRegistro">
	                                                   <option value="">
	                                                       <fmt:message key="citcorpore.comum.selecione" />
	                                                   </option>
														<option value="1">
															<fmt:message key="fornecedor.atasDeReuniao" />
														</option>
	                                                    <option value="2">
	                                                        <fmt:message key="fornecedor.registroDeSolicitacao" />
	                                                    </option>
	                                                    <option value="3">
	                                                        <fmt:message key="fornecedor.formularios" />
	                                                    </option>
	                                                    <option value="4">
	                                                        <fmt:message key="fornecedor.naoConformidades_reclamacoesDeClientes" />
	                                                    </option>
	                                                    <option value="5">
	                                                        <fmt:message key="fornecedor.falhasDeSistema" />
	                                                    </option>
	                                                    <option value="6">
	                                                       <fmt:message key="fornecedor.solicitacaoNoCitsmart" />
	                                                    </option>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset>
												<label style="margin-bottom: 3px;">
												 <fmt:message key="fornecedor.frequencia" />
												</label>
												<div style="height: 32px;">
													<select id="idFrequencia" name="idFrequencia">
	                                                   <option value="">
	                                                       <fmt:message key="citcorpore.comum.selecione" />
	                                                   </option>
														<option value="1">
															 <fmt:message key="fornecedor.periodica" />
														</option>
	                                                    <option value="2">
	                                                         <fmt:message key="fornecedor.diario" />
	                                                    </option>
	                                                    <option value="3">
	                                                         <fmt:message key="fornecedor.semanal" />
	                                                    </option>
	                                                    <option value="4">
	                                                         <fmt:message key="fornecedor.quinzenal" />
	                                                    </option>
	                                                    <option value="5">
	                                                         <fmt:message key="fornecedor.mensal" />
	                                                    </option>
	                                                    <option value="6">
	                                                        <fmt:message key="fornecedor.trimestral" />
	                                                    </option>
	                                                    <option value="7">
	                                                        <fmt:message key="fornecedor.semestral" />
	                                                    </option>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="fornecedor.formaDeContato" />
												</label>
												<div style="height: 32px;">
													<select id="idFormaContato" name="idFormaContato">
                                                         <option value="">
	                                                       <fmt:message key="citcorpore.comum.selecione" />
	                                                   </option>
														<option value="1">
															<fmt:message key="fornecedor.email" />
														</option>
	                                                    <option value="2">
	                                                        <fmt:message key="fornecedor.solicitacaoViaCitsmartOuEmail" />
	                                                    </option>
	                                                    <option value="3">
	                                                       <fmt:message key="fornecedor.telefone" />
	                                                    </option>
	                                                    <option value="4">
	                                                        <fmt:message key="fornecedor.internet" />
	                                                    </option>
	                                                    <option value="5">
	                                                        <fmt:message key="fornecedor.videoconferencia" />
	                                                    </option>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_50" >
											<fieldset>
												<label>
													<fmt:message key="fornecedor.atividadesResponsabilidades" />:
												</label>
												<div>
													<textarea id="ativ_responsabilidades" name="ativ_responsabilidades" maxlength="2000" cols="50" rows="4" class="Description['Atividades e Responsabilidades do t?mino normal ou antecipado']"></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label>
													<fmt:message key="fornecedor.gerenciamentoDesacordoDisputasContratuais"/>:
												</label>
												<div>
													<textarea id="gerenciamentodesacordo" name="gerenciamentodesacordo" maxlength="2000" cols="50" rows="4" class="Description['Gerenciamento de Desacordo ou Disputas Contatuais']"></textarea>
												</div>
											</fieldset>
										</div>
									</div>
									<br />
									<br />
									<button type="button" name="btnGravar" class="light" onclick="document.form.save();">
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png" />
										<span>
											<fmt:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type="button" name="btnLimpar" class="light" onclick='document.form.clear();document.form.fireEvent("load");'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png" />
										<span>
											<fmt:message key="citcorpore.comum.limpar" />
										</span>
									</button>
									<button type="button" name="btnExcluir" class="light" onclick="excluir();">
										<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png" />
										<span>
											<fmt:message key="citcorpore.comum.excluir" />
										</span>
									</button>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section">
								<fmt:message key="citcorpore.comum.pesquisa" />
								<form name="formPesquisa">
									<cit:findField formName="formPesquisa"
										lockupName="LOOKUP_FORNECEDOR"
										id="LOOKUP_FORNECEDOR"
										top="0"
										left="0"
										len="550"
										heigth="400"
										javascriptCode="true"
										htmlCode="true" />
								</form>
							</div>
						</div>
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</div>
				</div>
			</div>
			<!-- FIM DA P?INA DE CONTE?O -->
		</div>
		<%@include file="/include/footer.jsp"%>
		<script>
			var language = "${language}";
		</script>
		<script type="text/javascript" src="./js/fornecedor.js"></script>
	</body>
</html>
