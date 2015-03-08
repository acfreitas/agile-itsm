<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.centralit.citcorpore.bean.GrupoDTO"%>
<%@page import="java.util.Collection"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");

		    Collection<GrupoDTO> colGrupos = (Collection)request.getAttribute("colGrupos");
		%>

		<%@include file="/include/header.jsp"%>

		<%@ include file="/include/security/security.jsp" %>

		<title>
			<fmt:message key="citcorpore.comum.title" />
		</title>

		<%@ include file="/include/menu/menuConfig.jsp" %>
		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
		<script type="text/javascript" src="../../cit/objects/GrupoNivelAutoridadeDTO.js"></script>

		<link type="text/css" rel="stylesheet" href="css/nivelAutoridadeAll.css"/>

		<script type="text/javascript" src="js/nivelAutoridade.js"></script>

	<%
		// Se for chamado por iframe deixa apenas a parte de cadastro da página
		if (iframe != null) {
	%>

		<link type="text/css" rel="stylesheet" href="css/nivelAutoridadeIframe.css"/>
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
						<fmt:message key="nivelAutoridade" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1">
								<fmt:message key="nivelAutoridade.cadastro" />
							</a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top">
								<fmt:message key="nivelAutoridade.pesquisa" />
							</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action="${ctx}/pages/nivelAutoridade/nivelAutoridade">
									<div class="columns clearfix">
										<input type="hidden" id="idNivelAutoridade" name="idNivelAutoridade" />
                                        <input type='hidden' name='colGrupos_Serialize' />

										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="citcorpore.comum.nome" />
												</label>
												<div>
													<input type="text" id="nomeNivelAutoridade" name="nomeNivelAutoridade" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
												</div>
											</fieldset>
										</div>
										<div class="col_20">
											<fieldset>
												<label class = "campoObrigatorio">
													<fmt:message key="lookup.hierarquia" />
												</label>
												<div>
													<input type="text" id="hierarquia" name="hierarquia" maxlength="5" class="Valid[Required] Description[lookup.hierarquia] Valid[numero]" />
												</div>
											</fieldset>
										</div>
										<div class="col_30">
											<fieldset>
												<label class="campoObrigatorio" style="height: 30px;">
													<fmt:message key="citcorpore.comum.situacao" />
												</label>
												<div>
													<input type="radio" id="situacaoAtivo" name="situacao" value="A" checked="checked" /><fmt:message key="citcorpore.comum.ativo" />
													<input type="radio" id="situacaoInativo" name="situacao" value="I" /><fmt:message key="citcorpore.comum.inativo" />
												</div>
											</fieldset>
										</div>
	                                    <div class="col_100">
	                                        <div id='divNovoCriterio' class="col_50">
		                                        <h2 class="section">
		                                             <fmt:message key="nivelAutoridade.grupos" />
		                                        </h2>
	                                        </div>
	                                            <div class="col_20">
	                                                 <button type='button' class='light img_icon has_text'  onclick='incluirGrupo();'>
	                                                 <fmt:message key="nivelAutoridade.novoGrupo" />
	                                                 </button>
	                                            </div>
                                         </div>
                                         <div class="col_60">
                                             <fieldset style='height:200px'>
                                               <div style='height:250px;overflow:auto;'>
                                               <cit:grid id="GRID_GRUPOS" columnHeaders="nivelAutoridade.cabecalhoGridGrupos" styleCells="linhaGrid">
                                                   <cit:column idGrid="GRID_GRUPOS" number="001">
                                                       <select name='idGrupo#SEQ#' id='idGrupo#SEQ#' style='border:none; width: 300px' onchange='verificarGrupo("#SEQ#");'>
                                                           <option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
                                                           <%
                                                           if (colGrupos != null){
                                                               for (GrupoDTO grupoDto : colGrupos) {
                                                                   out.println("<option value='" + grupoDto.getIdGrupo() + "'>" +
                                                                   grupoDto.getNome() + "</option>");
                                                               }
                                                           }
                                                           %>
                                                       </select>
                                                   </cit:column>
                                               </cit:grid>
                                               </div>
                                             </fieldset>
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
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section">
								<fmt:message key="citcorpore.comum.pesquisa" />
								<form name="formPesquisa">
									<cit:findField formName="formPesquisa"
										lockupName="LOOKUP_NIVELAUTORIDADE"
										id="LOOKUP_NIVELAUTORIDADE"
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
