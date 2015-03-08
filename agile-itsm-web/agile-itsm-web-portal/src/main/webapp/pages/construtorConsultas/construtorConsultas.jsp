<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/include/header.jsp"%>
		<title><fmt:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/security/security.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<script type="text/javascript" src="../../js/parametroCorpore.js"></script>
		<script charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
		<script type="text/javascript" src="../../cit/objects/BIConsultaColunasDTO.js"></script>

		<script type="text/javascript" src="./js/construtorConsultas.js"></script>

	</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>
	<body>
		<div id="wrapper" style="overflow: hidden;">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="construtorconsultas.construtorconsultas"/></h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="construtorconsultas.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><fmt:message key="construtorconsultas.pesquisa"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/construtorConsultas/construtorConsultas'>
									<input type='hidden' name='colCriterios_Serialize' />
									<div class="columns clearfix">
										<div class="columns clearfix">
											<div class="col_25">
												<fieldset>
													<label class="campoObrigatorio">Id:</label>
														<div>
														  <input type='text' name="idConsulta" maxlength="11" size="11" readonly="readonly"/>
														</div>
												</fieldset>
											</div>
											<div class="col_75">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="construtorconsultas.nomeconsulta"/></label>
														<div>
														  <input type='text' name="nomeConsulta" maxlength="255" size="70" class="Valid[Required] Description[construtorconsultas.nomeconsulta]" />
														</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="construtorconsultas.identificacao"/></label>
														<div>
															<input type='text' name="identificacao" maxlength="70" size="70" class="Valid[Required] Description[construtorconsultas.identificacao]" />
														</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="construtorconsultas.tipoconsulta"/></label>
														<div>
															<select name='tipoConsulta' class="Valid[Required] Description[construtorconsultas.tipoconsulta]">
																<option value=""><fmt:message key="citcorpore.comum.selecione" /></option>
																<option value="C"><fmt:message key="construtorconsultas.informacoescruzadas" /></option>
																<!-- option value="L"><fmt:message key="construtorconsultas.listagem" /></option>  -->
																<option value="T"><fmt:message key="construtorconsultas.template" /></option>
																<option value="D"><fmt:message key="construtorconsultas.datatable" /></option>
																<option value="R"><fmt:message key="construtorconsultas.classe" /></option>
																<option value="S"><fmt:message key="construtorconsultas.script" /></option>
																<option value="1"><fmt:message key="construtorconsultas.graficoPizza" /></option>
																<option value="2"><fmt:message key="construtorconsultas.graficoBarra" /></option>
																<option value="#"><fmt:message key="construtorconsultas.urlExterna" /></option>
																<option value="J"><fmt:message key="construtorconsultas.jsp" /></option>
																<option value="X"><fmt:message key="construtorconsultas.xmlGerencial" /></option>
															</select>
														</div>
												</fieldset>
											</div>
											<div class="col_100">
												<div class="col_50">
													<fieldset>
														<label><fmt:message key="construtorconsultas.categoria"/></label>
															<div>
																<select name='idCategoria' class="Valid[Required] Description[construtorconsultas.categoria]">
																	<option value=""><fmt:message key="citcorpore.comum.selecione" /></option>
																</select>
															</div>
													</fieldset>
												</div>
												<div class="col_50">
													<fieldset>
														<label><fmt:message key="construtorconsultas.naoAtualizBase"/></label>
															<div>
																<input type='checkbox' name='naoAtualizBase' id='naoAtualizBase' value='S'/>
															</div>
													</fieldset>
												</div>
											</div>
											<div class="col_100">
												<fieldset>
													<label><fmt:message key="construtorconsultas.textoSQL"/></label>
														<div>
															<textarea rows="10" cols="70" name="textoSQL"></textarea>
														</div>
												</fieldset>
											</div>
											<div class="col_100">
					                             <label  style="cursor: pointer;" onclick='incluirColuna();'>
					                                 <img  src="${ctx}/imagens/add.png" /><span><b><fmt:message key="construtorconsultas.novaColuna" /></b></span>
					                             </label>
					                        </div>
											<div class="col_100">
												<cit:grid id="GRID_COLUNAS" columnHeaders="construtorconsultas.cabecalhoGridColunas" styleCells="linhaGrid">
					                               <cit:column idGrid="GRID_COLUNAS" number="001">
					                                   <input type='text' name='nomeColuna#SEQ#' id='nomeColuna#SEQ#' size='70' maxlength='90' style='border:none;'/>
					                               </cit:column>
					                               <cit:column idGrid="GRID_COLUNAS" number="002">
					                                   <input type='text' name='ordem#SEQ#' id='ordem#SEQ#' size='3' maxlength='2' style='border:none; text-align: right;' class='Format[Numero]'/>
					                               </cit:column>
					                           </cit:grid>
											</div>
											<div class="col_100">
												<fieldset>
													<label><fmt:message key="construtorconsultas.parametros"/></label>
														<div>
															<textarea rows="10" cols="70" name="parametros"></textarea>
														</div>
												</fieldset>
											</div>
											<div class="col_100">
												<fieldset>
													<label><fmt:message key="construtorconsultas.scriptExec"/></label>
														<div>
															<textarea rows="10" cols="70" name="scriptExec"></textarea>
														</div>
												</fieldset>
											</div>
											<div class="col_100">
												<fieldset>
													<label><fmt:message key="construtorconsultas.template"/></label>
														<div>
															<textarea rows="10" cols="70" name="template"></textarea>
														</div>
												</fieldset>
											</div>
										</div>
										<br>
									</div>
									<br><br>
									<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='GRID_COLUNAS.deleteAllRows();document.form.clear();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar"/></span>
									</button>
									<button type='button' name='btnExportar' class="light" onclick='exportar();'>
										<span><fmt:message key="dataManager.export"/></span>
									</button>
									<button type='button' name='btnImportar' class="light" onclick='POPUP_IMPORTAR.show();'>
										<span><fmt:message key="dataManager.import"/></span>
									</button>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_BICONSULTAS' id='LOOKUP_BICONSULTAS' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

<cit:janelaPopup modal="true" style="display:none;top:300px;width:600px;left:100px;height:160px;position:absolute;" title='<fmt:message key="dataManager.import" />' id="POPUP_IMPORTAR">
   <label style="font-size:15px;font-weight:bold;"><fmt:message key="citcorpore.comum.arquivo"/></label>
    <form name='formImportar' method='post' ENCTYPE="multipart/form-data" action='${ctx}/pages/construtorConsultas/construtorConsultas.load'>
		<input type='hidden' name='acao' id='acaoImportar' value='<fmt:message key="dataManager.import" />'/>
        <table width="100%">
            <tr>
                <td>

                  <fmt:message key="citcorpore.comum.arquivo" />:*
                </td>
                <td>
                   <input type='file' name='fileImportar' size="50" value='<fmt:message key="questionario.selecionarArquivo" />'/>
                </td>
                <td>
                    <input type='button' value='OK' onclick='importarReport()' />
                </td>
            </tr>
        </table>
    </form>
</cit:janelaPopup>

		<%@include file="/include/footer.jsp"%>
	</body>
</html>

