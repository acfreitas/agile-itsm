<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UploadDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO"%>
<%@page import="java.util.Collection"%>

<!doctype html public "">
<html>
<head>
<%
     Collection<CriterioAvaliacaoDTO> colCriterios = (Collection)request.getAttribute("colCriterios");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" />
</title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
<script type="text/javascript" src="../../cit/objects/CriterioCotacaoCategoriaDTO.js"></script>

<link rel="stylesheet" type="text/css" href="./css/categoriaProduto.css" />

<script type="text/javascript" src="./js/categoriaProduto.js"></script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="categoriaProduto.categoria_produto" />
				</h2>
			</div>
			<div id="tabs" class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message	key="categoriaProduto.cadastro_categoria" /></a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="categoriaProduto.pesquisa_categoria" />	</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form id='form' name='form' action='${ctx}/pages/categoriaProduto/categoriaProduto'>
								<div class="columns clearfix">
									<input type='hidden' name="idCategoria" id="idCategoria" />
									<input type='hidden' name='idCategoriaPai' />
                                    <input type='hidden' name='colCriterios_Serialize' />

									<div class="col_33">
										<fieldset style="height: 60px;">
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome" /></label>
											<div>
												<input type='text' name="nomeCategoria" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset  style="height: 60px;">
											<label><fmt:message key="categoriaProduto.categoria_superior" /></label>
											<div>
												<div>
													<input onclick="consultarCategoriaServicoSuperior()" readonly="readonly" style="width: 90% !important;" type='text' name="nomeCategoriaPai" maxlength="70" size="70"  />
													<img onclick="consultarCategoriaServicoSuperior()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
												</div>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
									<fieldset style="height: 60px;">
										<label class="campoObrigatorio">
											<fmt:message key="categoriaProduto.categoria_situacao"/>
										</label>
										<div class="inline">
											<label>
												<input type='radio' id="situacao" name="situacao" value="A" class="Valid[Required] Description[categoriaProduto.categoria_situacao]" checked="checked" />
												<fmt:message key="categoriaProduto.categoria_ativo"/>
											</label>
											<label>
												<input type='radio' id="situacao" name="situacao" value="I" class="Valid[Required] Description[categoriaProduto.categoria_situacao]" />
												<fmt:message key="categoriaProduto.categoria_inativo"/>
											</label>
										</div>
									</fieldset>
									<br><br>
								 </div>

					            <div class="col_100">
					                <div class="col_50">
					                    <h2 class="section">
					                         <fmt:message key="categoriaProduto.criteriosObrigatorios" />
					                       </h2>
					                    <div class="col_50">
					                        <fieldset>
					                            <label class="campoObrigatorio"><fmt:message key="coletaPreco.criterioPreco" />&nbsp;<fmt:message key="cotacao.valoresCriterio" />
					                            </label>
					                            <div>
			                                   		<input id="pesoCotacaoPreco" type='text' name="pesoCotacaoPreco" maxlength="2" class="Valid[Required] Description[coletaPreco.criterioPreco]" />
					                            </div>
					                        </fieldset>
					                    </div>
					                       <div class="col_50">
					                           <fieldset>
					                               <label class="campoObrigatorio"><fmt:message key="coletaPreco.criterioPrazoEntrega" />&nbsp;<fmt:message key="cotacao.valoresCriterio" />
					                               </label>
					                               <div>
					                                  <input id="pesoCotacaoPrazoEntrega" type='text' name="pesoCotacaoPrazoEntrega" maxlength="2" class="Valid[Required] Description[coletaPreco.criterioPrazoEntrega]" />
					                               </div>
					                           </fieldset>
					                       </div>
					                       <div class="col_50">
					                           <fieldset>
					                               <label class="campoObrigatorio"><fmt:message key="coletaPreco.criterioPrazoPagto" />&nbsp;<fmt:message key="cotacao.valoresCriterio" />
					                               </label>
					                               <div>
					                                  <input id="pesoCotacaoPrazoPagto" type='text' name="pesoCotacaoPrazoPagto" maxlength="2" class="Valid[Required] Description[coletaPreco.criterioPrazoPagto]" />
					                               </div>
					                           </fieldset>
					                       </div>
					                       <div class="col_50">
					                           <fieldset>
					                               <label class="campoObrigatorio"><fmt:message key="coletaPreco.criterioPrazoGarantia" />&nbsp;<fmt:message key="cotacao.valoresCriterio" />
					                               </label>
					                               <div>
					                                  <input id="pesoCotacaoPrazoGarantia" type='text' name="pesoCotacaoPrazoGarantia" maxlength="2" class="Valid[Required] Description[coletaPreco.criterioPrazoGarantia]" />
					                               </div>
					                           </fieldset>
					                       </div>
					                       <div class="col_50">
					                           <fieldset>
					                               <label class="campoObrigatorio"><fmt:message key="coletaPreco.criterioTaxaJuros" />&nbsp;<fmt:message key="cotacao.valoresCriterio" />
					                               </label>
					                               <div>
					                                  <input id="pesoCotacaoTaxaJuros" type='text' name="pesoCotacaoTaxaJuros" maxlength="2" class="Valid[Required] Description[coletaPreco.criterioTaxaJuros]" />
					                               </div>
					                           </fieldset>
					                       </div>
					                </div>

					                <div class="col_50">
					                    <h2 class="section">
					                         <fmt:message key="categoriaProduto.criteriosVariaveis" />
					                    </h2>
					                <!-- Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 17:00 - ID Citsmart: 120948 -
									* Motivo/Comentário: Opção de adicionar muito longe da grid, Inserido proximo a citgrid e alterado para button-->
					                    <div id='divNovoCriterio' class="col_100">
					                      <!--   <div class="col_66">
					                             <label>&nbsp;</label>
					                        </div> -->
					                        <div class="col_33">
					                             <button type='button' onclick='incluirCriterio();' class='light img_icon has_text'>
					                               <fmt:message key="cotacao.novoCriterio" />
					                             </button>
					                        </div>
					                    </div>
					                     <div class="col_100">
					                         <fieldset style='height:200px'>
					                           <div style='width:330px;height:190px;overflow:auto;'>
					                           <cit:grid id="GRID_CRITERIOS" columnHeaders="categoriaProduto.cabecalhoGridCriterios" styleCells="linhaGrid">
					                               <cit:column idGrid="GRID_CRITERIOS" number="001">
					                                   <select name='idCriterio#SEQ#' id='idCriterio#SEQ#' style='border:none; width: 200px' onchange='verificarCriterio("#SEQ#");'>
					                                       <option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
					                                       <%
					                                       if (colCriterios != null){
					                                           for (CriterioAvaliacaoDTO criterioDto : colCriterios) {
					                                               out.println("<option value='" + criterioDto.getIdCriterio() + "'>" +
					                                               criterioDto.getDescricao() + "</option>");
					                                           }
					                                       }
					                                       %>
					                                   </select>
					                               </cit:column>
					                               <cit:column idGrid="GRID_CRITERIOS" number="002">
					                                   <input type='text' name='pesoCotacao#SEQ#' id='pesoCotacao#SEQ#' size='3' maxlength='2' style='border:none; text-align: right;' class='Format[Numero]'/>
					                               </cit:column>
					                           </cit:grid>
					                           </div>
					                         </fieldset>
					                     </div>
					                </div>
                                 </div>
									<!-- Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 17:00 - ID Citsmart: 120948 -
									* Motivo/Comentário: Layout sem usabilidade, dificultando o entendimento-->
									<div class="col_99">
										<fieldset>
											<br /> <a id="btAnexos" onclick="return anexos();" class='' ><fmt:message key="citcorpore.comum.adicionarAlterarImagem" /></a>
											<div>
												<input type="hidden" name="imagem" />
												<label>&nbsp;</label>

												<div id='divImgFoto' style='border: 0px; width: 60%; height: 100px;'>
												</div>

												<%-- <input type="button" class="light img_icon has_text"
													id="btAnexos" onclick="return anexos();"
													value="<fmt:message key="citcorpore.comum.adicionarAlterarImagem" />..." />
												<br />
												<br /> --%>
											</div>
										</fieldset>
									</div>
								</div>
								<label>&nbsp;</label>
								<button type='button' name='btnGravar' class="light"
									onclick='gravar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='document.form.clear();document.form.fireEvent("load");'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' class="light"
									onclick='removerCategoria();'>
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
							<h3>
								<fmt:message key="categoriaProduto.categoria_produto" />
							</h3>
							<div id="divCategoria" style="height: 290px;overflow:auto;">
							</div>
						</div>

					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<div id="POPUP_CATEGORIAPRODUTOPAI" title="<fmt:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px;">
						<form name='formCategoriaProdutoSuperior'>
							<cit:findField formName='formCategoriaProdutoSuperior'
							lockupName='LOOKUP_CATEGORIAPRODUTOPAI'
							id='LOOKUP_CATEGORIAPRODUTOPAI' top='0' left='0' len='550' heigth='400'
							javascriptCode='true'
							htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_MENUFOTOS" style='display:none'>
		<form name="formUpload" method="post" enctype="multipart/form-data">
			<cit:uploadControl style="height:100px;width:98%;border:0px solid black"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/upload/upload.load" disabled="false"/>
			<button id="btnEscolherFotos" name="btnEscolherFotos" type="button">
				<fmt:message key="citcorpore.comum.gravar" />
			</button>
		</form>
	</div>

	<%@include file="/include/footer.jsp"%>
</body>
</html>


