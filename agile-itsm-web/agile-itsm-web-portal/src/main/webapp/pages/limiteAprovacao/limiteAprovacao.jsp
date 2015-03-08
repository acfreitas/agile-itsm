<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.centralit.citcorpore.bean.NivelAutoridadeDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ProcessoNegocioDTO"%>
<%@page import="java.util.Collection"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");

		    Collection<NivelAutoridadeDTO> colAutoridades = (Collection)request.getAttribute("colAutoridades");
		    Collection<ProcessoNegocioDTO> colProcessos = (Collection)request.getAttribute("colProcessos");
		%>
		<%@include file="/include/header.jsp"%>
		<%@ include file="/include/security/security.jsp" %>

		<title>
			<fmt:message key="citcorpore.comum.title" />
		</title>

		<%@ include file="/include/menu/menuConfig.jsp" %>
		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
		<script type="text/javascript" src="../../cit/objects/ValorLimiteAprovacaoDTO.js"></script>

		<link rel="stylesheet" type="text/css" href="./css/limiteAprovacao.css">

		<script type="text/javascript" src="./js/limiteAprovacao.js"></script>

	<%
		// Se for chamado por iframe deixa apenas a parte de cadastro da página
		if (iframe != null) {
	%>
		<link rel="stylesheet" type="text/css" href="./css/limiteAprovacaoIFrame.css">
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
						<fmt:message key="limiteAprovacao" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1">
								<fmt:message key="limiteAprovacao.cadastro" />
							</a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top">
								<fmt:message key="limiteAprovacao.pesquisa" />
							</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action="${ctx}/pages/limiteAprovacao/limiteAprovacao">
									<div class="columns clearfix">
										<input type="hidden" id="idLimiteAprovacao" name="idLimiteAprovacao" />
                                        <input type='hidden' name='colValores_Serialize' />

										<div class="col_100">
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="limiteAprovacao.identificacao" />
												</label>
												<div>
													<input type="text" id="identificacao" name="identificacao" maxlength="100" class="Valid[Required] Description[limiteAprovacao.identificacao]" />
												</div>
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="limiteAprovacao.limiteValor" />
												</label>
												<div>
													<input type="radio" id="tipoLimitePorValor" name="tipoLimitePorValor" value="V" checked="checked" /><fmt:message key="limiteAprovacao.limiteValor.faixaValores" />
													<input type="radio" id="tipoLimitePorValor" name="tipoLimitePorValor" value="Q" /><fmt:message key="limiteAprovacao.limiteValor.qualquerValor" />
													<input type="radio" id="tipoLimitePorValor" name="tipoLimitePorValor" value="N" /><fmt:message key="limiteAprovacao.limiteValor.naoSeAplica" />
												</div>
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="limiteAprovacao.abrangenciaCentroResultado" />
												</label>
												<div>
													<input type="radio" id="abrangenciaCentroResultado" name="abrangenciaCentroResultado" value="R" checked="checked" /><fmt:message key="limiteAprovacao.abrangenciaCentroResultado.somenteResponsavel" />
													<input type="radio" id="abrangenciaCentroResultado" name="abrangenciaCentroResultado" value="T" /><fmt:message key="limiteAprovacao.abrangenciaCentroResultado.todos" />
												</div>
											</fieldset>
										</div>
										</div>

	                                    <div class="col_100">
											<div class="col_30">
		                                         <div class="col_100">
			                                        <h2 class="section">
			                                             <fmt:message key="limiteAprovacao.processosNegocio" />
			                                        </h2>
                                                      <%
                                                      if (colProcessos != null){
                                                          for (ProcessoNegocioDTO obj : colProcessos) {
                                                       	   %>
															<div class="col_100">
			                                                   <input type="checkbox" name="idProcessoNegocio" value="<%= obj.getIdProcessoNegocio()%>"/><%=obj.getNomeProcessoNegocio()%>
															</div>
														   <%
														}
                                                      }
                                                      %>
			                                     </div>
		                                         <div class="col_100">
			                                        <h2 class="section">
			                                             <fmt:message key="limiteAprovacao.niveisAutoridade" />
			                                        </h2>
                                                           <%
                                                           if (colAutoridades != null){
                                                               for (NivelAutoridadeDTO obj : colAutoridades) {
                                                            	   %>
															<div class="col_100">
                                                            	   <input type="checkbox" name="idNivelAutoridade" value="<%= obj.getIdNivelAutoridade()%>"><%=obj.getNomeNivelAutoridade()%>
															</div>
																   <%
																}
                                                           }
                                                           %>
			                                     </div>
											</div>
											<div class="col_70">
	                                         <div class="col_100">
		                                        <div  class="col_50">
			                                        <h2 class="section">
			                                             <fmt:message key="limiteAprovacao.limitesValor" />
			                                        </h2>
		                                        </div>
	                                            <div class="col_20">
	                                                 <button type='button' class='light img_icon has_text'  onclick='incluirValor();'>
	                                                 <fmt:message key="limiteAprovacao.novoValor" />
	                                                 </button>
	                                            </div>
		                                     </div>
		                                     <div class="col_100">
	                                         <div class="col_70">
	                                             <fieldset>
	                                               <div style='overflow:auto;'>
	                                               <cit:grid id="GRID_VALORES" columnHeaders="limiteAprovacao.cabecalhoGridValores" styleCells="linhaGrid">
	                                                   <cit:column idGrid="GRID_VALORES" number="001">
	                                                       <select name='tipoUtilizacao#SEQ#' id='tipoUtilizacao#SEQ#' style='border:none;'>
	                                                           <option value='I'><fmt:message key="limiteAprovacao.usoInterno" /></option>
	                                                           <option value='C'><fmt:message key="limiteAprovacao.atendCliente" /></option>
	                                                       </select>
	                                                   </cit:column>
	                                                   <cit:column idGrid="GRID_VALORES" number="002">
	                                                       <select name='tipoLimite#SEQ#' id='tipoLimite#SEQ#' style='border:none;'>
	                                                           <option value='I'><fmt:message key="limiteAprovacao.individual" /></option>
	                                                           <option value='M'><fmt:message key="limiteAprovacao.mensal" /></option>
	                                                           <option value='A'><fmt:message key="limiteAprovacao.anual" /></option>
	                                                       </select>
	                                                   </cit:column>
	                                                   <cit:column idGrid="GRID_VALORES" number="003">
															<input id="valorLimite#SEQ#" type="text" maxlength="15" name='valorLimite#SEQ#' class="Format[Moeda]"/>
	                                                   </cit:column>
	                                               </cit:grid>
	                                               </div>
	                                             </fieldset>
	                                         </div>
	                                         </div>
	                                    </div>
									</div>
									<br />
									<br />
									<div class="col_100">
									<fieldset>
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
									</fieldset>
									</div>
									</div>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section">
								<fmt:message key="citcorpore.comum.pesquisa" />
								<form name="formPesquisa">
									<cit:findField formName="formPesquisa"
										lockupName="LOOKUP_LIMITEAPROVACAO"
										id="LOOKUP_LIMITEAPROVACAO"
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
