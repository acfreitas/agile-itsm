<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.centralit.citcorpore.bean.NivelAutoridadeDTO"%>
<%@page import="br.com.centralit.bpm.dto.FluxoDTO"%>
<%@page import="java.util.Collection"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");

		    Collection<NivelAutoridadeDTO> colAutoridades = (Collection)request.getAttribute("colAutoridades");
		    Collection<FluxoDTO> colTiposFluxo = (Collection)request.getAttribute("colTiposFluxo");
		%>
		<%@include file="/include/header.jsp"%>

		<%@ include file="/include/security/security.jsp" %>

		<title>
			<fmt:message key="citcorpore.comum.title" />
		</title>

		<%@ include file="/include/menu/menuConfig.jsp" %>

		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
		<script type="text/javascript" src="../../cit/objects/ProcessoNivelAutoridadeDTO.js"></script>

		<link rel="stylesheet" type="text/css" href="./css/processoNegocio.css">

		<script type="text/javascript" src="./js/processoNegocio.js"></script>

	<%
		// Se for chamado por iframe deixa apenas a parte de cadastro da página
		if (iframe != null) {
	%>
		<link rel="stylesheet" type="text/css" href="./css/processoNegocioIFrame.css">
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
			<!-- Conteudo -->
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
						<fmt:message key="processoNegocio" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1">
								<fmt:message key="processoNegocio.cadastro" />
							</a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top">
								<fmt:message key="processoNegocio.pesquisa" />
							</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action="${ctx}/pages/processoNegocio/processoNegocio">
									<div class="columns clearfix">
										<input type="hidden" id="idProcessoNegocio" name="idProcessoNegocio" />
                                        <input type='hidden' name='colAutoridades_Serialize' />

										<div class="col_100">
										<div class="col_40">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="citcorpore.comum.nome" />
												</label>
												<div>
													<input type="text" id="nomeProcessoNegocio" name="nomeProcessoNegocio" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
												</div>
											</fieldset>
										</div>
										<div class="col_30">
											<fieldset>
												<label>
													<fmt:message key="processoNegocio.grupoExecutor"/>
												</label>
												<div>
													<select name="idGrupoExecutor" id ="idGrupoExecutor"  class="Description[processoNegocio.grupoExecutor]">
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_30">
											<fieldset>
												<label>
													<fmt:message key="processoNegocio.grupoAdministrador"/>
												</label>
												<div>
													<select name="idGrupoAdministrador" id ="idGrupoAdministrador"  class="Description[processoNegocio.grupoAdministrador]">
													</select>
												</div>
											</fieldset>
										</div>
										</div>

										<div class="col_100">
										<div class="col_20">
											<fieldset>
												<label>
													<fmt:message key="processoNegocio.percDispensaNovaAprovacao" />
												</label>
												<div>
													<input id="percDispensaNovaAprovacao" type="text"  maxlength="15" name='percDispensaNovaAprovacao' class="Format[Moeda]"/>
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="processoNegocio.permiteAprovacaoInferior" />
												</label>
												<div>
													<input type="radio" id="permiteAprovacaoNivelInferior" name="permiteAprovacaoNivelInferior" value="S" checked="checked" /><fmt:message key="citcorpore.comum.sim" />
													<input type="radio" id="permiteAprovacaoNivelInferior" name="permiteAprovacaoNivelInferior" value="N" /><fmt:message key="citcorpore.comum.nao" />
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="processoNegocio.alcadaPrimeiroNivel" />
												</label>
												<div>
													<input type="radio" id="alcadaPrimeiroNivel" name="alcadaPrimeiroNivel" value="S" class="Valid[Required] Description[processoNegocio.alcadaPrimeiroNivel]"/><fmt:message key="citcorpore.comum.sim" />
													<input type="radio" id="alcadaPrimeiroNivel" name="alcadaPrimeiroNivel" value="N" class="Valid[Required] Description[processoNegocio.alcadaPrimeiroNivel]" checked="checked" /><fmt:message key="citcorpore.comum.nao" />
												</div>
											</fieldset>
										</div>
										</div>
	                                    <div class="col_60">
	                                    <div class="col_100">
	                                        <div class="col_100">
		                                        <div class="col_70">
			                                        <h2 class="section">
			                                             <fmt:message key="processoNegocio.autoridades" />
			                                        </h2>
		                                        </div>
	                                            <div class="col_30">
	                                                 <button type='button' class='light img_icon has_text'  onclick='incluirAutoridade();'>
	                                                 <fmt:message key="processoNegocio.novaAutoridade" />
	                                                 </button>
	                                            </div>
	                                        </div>
                                         </div>
                                         <div class="col_100">
                                             <fieldset>
                                               <div style='overflow:auto;'>
                                               <cit:grid id="GRID_AUTORIDADES" columnHeaders="processoNegocio.cabecalhoGridAutoridades" styleCells="linhaGrid">
                                                   <cit:column idGrid="GRID_AUTORIDADES" number="001">
                                                       <select name='idNivelAutoridade#SEQ#' id='idNivelAutoridade#SEQ#' style='border:none; width: 200px' onchange='verificarAutoridade("#SEQ#");'>
                                                           <option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
                                                           <%
                                                           if (colAutoridades != null){
                                                               for (NivelAutoridadeDTO obj : colAutoridades) {
                                                                   out.println("<option value='" + obj.getIdNivelAutoridade() + "'>" +
                                                                   obj.getNomeNivelAutoridade() + "</option>");
                                                               }
                                                           }
                                                           %>
                                                       </select>
                                                   </cit:column>
                                                   <cit:column idGrid="GRID_AUTORIDADES" number="002">
                                                       <select name='permiteAprovacaoPropria#SEQ#' id='permiteAprovacaoPropria#SEQ#' style='border:none;'>
                                                           <option value='S'><fmt:message key="citcorpore.comum.sim" /></option>
                                                           <option value='N'><fmt:message key="citcorpore.comum.nao" /></option>
                                                       </select>
                                                   </cit:column>
                                                   <cit:column idGrid="GRID_AUTORIDADES" number="003">
														<input id="antecedenciaMinimaAprovacao#SEQ#" type="text" maxlength="15" name='antecedenciaMinimaAprovacao#SEQ#' class="Format[Numero]"/>
                                                   </cit:column>
                                               </cit:grid>
                                               </div>
                                             </fieldset>
                                         </div>
                                         </div>
										 <div class="col_40">
										 <div class="col_100">
	                                        <h2 class="section">
	                                             <fmt:message key="processoNegocio.fluxos" />
	                                        </h2>
	                                        <div style='overflow:auto;'>
	                                             <%
                                                    if (colTiposFluxo != null){
                                                        for (FluxoDTO obj : colTiposFluxo) {
                                                     	   %>
													<div class="col_100">
	                                                         	   <input type="checkbox" name="idTipoFluxo" value="<%= obj.getIdTipoFluxo()%>"><%=obj.getDescricao()%>
													</div>
														   <%
														}
	                                                        }
	                                                        %>
											</div>
	                                     </div>
                                         </div>
									</div>
									<br />
									<br />
									<button type="button" name="btnGravar" class="light" onclick="gravar();">
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
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section">
								<fmt:message key="citcorpore.comum.pesquisa" />
								<form name="formPesquisa">
									<cit:findField formName="formPesquisa"
										lockupName="LOOKUP_PROCESSONEGOCIO"
										id="LOOKUP_PROCESSONEGOCIO"
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
			<!-- Fim da Pagina de Conteudo -->
		</div>
        <%@include file="/include/footer.jsp"%>
	</body>
</html>
