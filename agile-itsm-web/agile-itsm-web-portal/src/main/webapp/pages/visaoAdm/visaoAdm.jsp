<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="br.com.centralit.citcorpore.metainfo.bean.HtmlCodePartDTO" %>
<%@ page import="br.com.centralit.citcorpore.metainfo.bean.HtmlCodeVisaoDTO" %>
<%@ page import="br.com.centralit.citcorpore.metainfo.bean.BotaoAcaoVisaoDTO" %>
<%@ page import="br.com.centralit.citcorpore.metainfo.bean.ExternalClassDTO" %>
<%@ page import="br.com.centralit.citcorpore.util.CITCorporeUtil" %>
<%@ page import="br.com.centralit.citcorpore.metainfo.bean.ScriptEventDTO" %>
<%@ page import="br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO" %>
<%@ page import="br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO" %>
<%@ page import="br.com.citframework.util.Constantes" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="br.com.centralit.citcorpore.metainfo.bean.VisaoDTO" %>
<%@ page import="br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO" %>
<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@ page import="br.com.centralit.citcorpore.bean.UploadDTO" %>
<%@ page import="br.com.citframework.dto.Usuario" %>
<%@ page import="br.com.citframework.util.UtilI18N" %>

<%
	String templateTab = (String)request.getAttribute("templateTab");
	if (templateTab == null){
		templateTab = "";
	}
	pageContext.setAttribute("templateTab", templateTab);

	Collection relacaoVisoes = (Collection)request.getAttribute("relacaoVisoes");
	if (relacaoVisoes == null){
		relacaoVisoes = new ArrayList();
	}
	Collection objetosNegocio = (Collection)request.getAttribute("objetosNegocio");
	if (objetosNegocio == null){
		objetosNegocio = new ArrayList();
	}

	String ACAO_GRAVAR = BotaoAcaoVisaoDTO.ACAO_GRAVAR;
	String ACAO_LIMPAR = BotaoAcaoVisaoDTO.ACAO_LIMPAR;
	String ACAO_EXCLUIR = BotaoAcaoVisaoDTO.ACAO_EXCLUIR;
	String ACAO_SCRIPT = BotaoAcaoVisaoDTO.ACAO_SCRIPT;
	pageContext.setAttribute("ACAO_GRAVAR", ACAO_GRAVAR);
	pageContext.setAttribute("ACAO_LIMPAR", ACAO_LIMPAR);
	pageContext.setAttribute("ACAO_EXCLUIR", ACAO_EXCLUIR);
	pageContext.setAttribute("ACAO_SCRIPT", ACAO_SCRIPT);

	String EXTERNALCLASS = VisaoDTO.EXTERNALCLASS;
	String MATRIZ = VisaoDTO.MATRIZ;
	pageContext.setAttribute("EXTERNALCLASS", EXTERNALCLASS);
	pageContext.setAttribute("MATRIZ", MATRIZ);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/include/security/security.jsp" %>

	<%@include file="/include/header.jsp" %>
	<%@include file="/include/titleComum/titleComum.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/reset.css" />

	<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
	<script type="text/javascript" src="../../cit/objects/VisaoDTO.js"></script>

	<link rel="stylesheet" type="text/css" href="css/visaoAdm.css" />

	<script type="text/javascript">
		var btn_action = {
			gravar: "${ACAO_GRAVAR}",
			limpar: "${ACAO_LIMPAR}",
			excluir: "${ACAO_EXCLUIR}",
			script: "${ACAO_SCRIPT}"
		};

		var visao_dto = {
			external_class: "${EXTERNALCLASS}",
			matriz: "${MATRIZ}"
		};

		var template_tab = "${templateTab}";
	</script>
</head>

<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2><fmt:message key='citcorpore.comum.visao'/></h2>
			</div>

			<div id="tabs" class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li style="float: left !important;">
						<a href="#tabs-1"><fmt:message key='visaoAdm.cadastroVisao'/></a>
					</li>
					<li>
						<a href="#tabs-2" class="round_top"><fmt:message key='visaoAdm.pesquisaVisao'/></a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<form name='form' method="POST" action='${ctx}/pages/visaoAdm/visaoAdm'>
							<input type='hidden' name='idVisao' class='Description[Identificação da Visão]'/>
							<input type='hidden' name='idVisaoRel' class='Description[Identificação da Visão]'/>
							<input type='hidden' name='ordemCampos' class='Description[Ordem dos campos]'/>
							<input type='hidden' name='numTabs' class='Description[Numero de Tabs]'/>
							<input type='hidden' name='nomeCombo' />
							<input type='hidden' name='seq' />
							<input type='hidden' name='idObjetoNegocio' id='idObjetoNegocioAux'/>
							<div class="section">
								<div id='init' style='text-align: center;'>
									<table>
										<tr>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='gravar();'>
													<img src='${ctx}/imagens/Save-icon.png' border='0'/><span><fmt:message key='projeto.salvar'/></span>
												</button>
											</td>
											<td>
												&nbsp;
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='showScripts();'>
													<img src='${ctx}/imagens/forms/script_lightning.png' border='0'/><span>Scripts</span>
												</button>
											</td>
											<td>
												&nbsp;
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='showBotoes();'>
													<img src='${ctx}/imagens/forms/button_lightning.png' border='0'/><span><fmt:message key='dinamicview.botoesacao'/></span>
												</button>
											</td>
											<td>
												&nbsp;
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='showHTMLCode();'>
													<img src='${ctx}/imagens/forms/stock_form_image_html.png' border='0'/><span><fmt:message key='visaoAdm.HTMLCodeAdicional'/></span>
												</button>
											</td>
											<td>
												&nbsp;
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='limparTela();'>
													<img src='${ctx}/template_new/images/icons/small/grey/clear.png' border='0'/><span><fmt:message key='citcorpore.ui.botao.rotulo.Limpar'/></span>
												</button>
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='excluirVisao();'>
													<img src='${ctx}/template_new/images/icons/small/grey/trashcan.png' border='0'/><span><fmt:message key='itemRequisicaoProduto.excluir'/></span>
												</button>
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='mostrarExportarVisoesXML();'>
													<img src='${ctx}/imagens/forms/exportar.png' border='0'/><span><fmt:message key='visaoAdm.exportarVisoes'/> XML</span>
												</button>
											</td>
											<td>
												&nbsp;
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='mostrarImportarVisoesXML();'>
													<img src='${ctx}/imagens/forms/importar.png' border='0'/><span><fmt:message key='visaoAdm.importarVisoes'/> XML</span>
												</button>
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='mostrarImportarTodasVisoesXML();'>
													<img src='${ctx}/imagens/xml.png' border='0'/><span><fmt:message key='visaoAdm.importarTodasVisoes'/> XML</span>
												</button>
											</td>
										</tr>
									</table>
									<table>
										<tr>
											<td>
												<label class="campoObrigatorio"><fmt:message key='calendario.descricao'/>: </label>
											</td>
											<td>
												<input type='text' name='descricao' size="80" maxlength="120" class='Valid[Required] Description[calendario.descricao]'/>
											</td>
										</tr>
										<tr>
											<td>
												<label class="campoObrigatorio"><fmt:message key='citSmart.comum.identificador'/>: </label>
											</td>
											<td style='text-align: left;'>
												<input type='text' name='identificador' size="50" maxlength="50" class='Valid[Required] Description[citSmart.comum.identificador]' onblur='verificaBrancos(this)'/>
											</td>
										</tr>
										<tr>
											<td>
												<label class="campoObrigatorio"><fmt:message key='visaoAdm.tipoVisao'/>: </label>
											</td>
											<td>
												<select name='tipoVisao' class='Valid[Required] Description[visaoAdm.tipoVisao]' onchange='verificaTipoVisao(this)'>
													<option value=''><fmt:message key='citcorpore.comum.selecione'/></option>
													<option value='<%=VisaoDTO.EXTERNALCLASS%>'><fmt:message key='visaoAdm.classeExterna'/></option>
													<option value='<%=VisaoDTO.EDIT%>'><fmt:message key='visaoAdm.edicao'/></option>
													<option value='<%=VisaoDTO.TABLEEDIT%>'><fmt:message key='visaoAdm.listagemTabelaPermitindoEdicao'/></option>
													<option value='<%=VisaoDTO.TABLESEARCH%>'><fmt:message key='visaoAdm.tabelaBusca'/></option>
													<option value='<%=VisaoDTO.MATRIZ%>'><fmt:message key='visaoAdm.matriz'/></option>
												</select>
											</td>
										</tr>
										<tr>
											<td>
												<label class="campoObrigatorio"><fmt:message key='lookup.situacaoVisao'/>: </label>
											</td>
											<td>
												<input type='radio' name='situacaoVisao' value='A' class='Valid[Required] Description[lookup.situacaoVisao]'/><fmt:message key='planoMelhoria.situacao.ativo'/>
												<input type='radio' name='situacaoVisao' value='I' class='Valid[Required] Description[lookup.situacaoVisao]'/><fmt:message key='requisitosla.inativo'/>
											</td>
										</tr>
									</table>
									<table width="100%">
										<tr>
											<td>
												<button type='button' class="light" onclick='mostraAddObj();'>
													<img src='${ctx}/imagens/Button-Add-icon.png' border='0'/><span><fmt:message key='citcorpore.comum.adicionarCampo'/></span>
												</button>
											</td>
											<td>
												&nbsp;
											</td>
											<td align="right" style="text-align: right;">
												<span style='color: red; font-size: 8'><fmt:message key='visaoAdm.atencaoEditarCampoExistenteBastaDuploClique'/></span>
											</td>
										</tr>
									</table>
								</div>
								<div id='matriz' style='text-align: center;display:none'>
									<table>
										<tr>
											<td>
												<fmt:message key='visaoAdm.objetoNegocioBaseMatriz'/>
											</td>
											<td>
												<select name='idObjetoNegocioMatriz' onchange='selecionaObjetoNegocioMatriz(this)'>
													<option value=''><fmt:message key='citcorpore.comum.selecione'/></option>
													<%
													for (Iterator it = objetosNegocio.iterator(); it.hasNext();){
														ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO)it.next();
														out.println("<option value='" + objetoNegocioDTO.getIdObjetoNegocio() + "'>" + objetoNegocioDTO.getNomeObjetoNegocio() + "</option>");
													}
													%>
											</select>
										</td>
									</tr>
									<tr>
										<td>
											<fmt:message key='visaoAdm.campoChaveObjetoNegocioBaseMatriz'/>:
										</td>
										<td>
											<select name='idCamposObjetoNegocio1'>
												<option value=''><fmt:message key='citcorpore.comum.selecione'/></option>
											</select>
										</td>
										<td>
											<fmt:message key='calendario.descricao'/>:
										</td>
										<td>
											<input type='text' name='descricaoCampo1' size='70' maxlength="120"/>
										</td>
									</tr>
									<tr>
										<td>
											<fmt:message key='visaoAdm.campoApresentacaoUmObjetoNegocioBaseMatriz'/>:
										</td>
										<td>
											<select name='idCamposObjetoNegocio2'>
												<option value=''><fmt:message key='citcorpore.comum.selecione'/></option>
											</select>
										</td>
										<td>
											<fmt:message key='calendario.descricao'/>:
										</td>
										<td>
											<input type='text' name='descricaoCampo2' size='70' maxlength="120"/>
										</td>
									</tr>
									<tr>
										<td>
											<fmt:message key='visaoAdm.campoApresentacaoDoisObjetoNegocioBaseMatriz'/>:
										</td>
										<td>
											<select name='idCamposObjetoNegocio3'>
												<option value=''><fmt:message key='citcorpore.comum.selecione'/></option>
											</select>
										</td>
										<td>
											<fmt:message key='calendario.descricao'/>:
										</td>
										<td>
											<input type='text' name='descricaoCampo3' size='70' maxlength="120"/>
										</td>
									</tr>
								</table>
							</div>
							<div id='sortable' style='text-align: center;'>

							</div>
							<div id='classeExterna' style='text-align: center;display:none'>
								<select name='classeName' id='classeName'>
									<option value=''><fmt:message key='citcorpore.comum.selecione'/></option>
									<%
										if (CITCorporeUtil.LIST_EXTERNAL_CLASSES != null) {
											for (Iterator it = CITCorporeUtil.LIST_EXTERNAL_CLASSES.iterator(); it.hasNext();){
												ExternalClassDTO externalClassDTO = (ExternalClassDTO)it.next();
												out.print("<option value='" + externalClassDTO.getNameClass() + "'>" + externalClassDTO.getNameClass() + " (" + externalClassDTO.getNameJarOriginal() + ")" + "</option>");
											}
										}
									%>
								</select>
							</div>
							<div id='ctrlTabs' style='text-align: center;'>
								<button name="btnSalvar" type='button' onclick='addTab()'>
									<fmt:message key='visaoAdm.adicionarVisaoVinculada'/>
								</button>
							</div>
							<br><br>
							<div id='tabsAssociadas' style='text-align: center;'>
								<ul>
									<li><a href="#tabsAssociadas-1"><fmt:message key='visaoAdm.visaoRelacionada'/></a> <span class="ui-icon ui-icon-close">Remove Tab</span></li>
								</ul>
								<div id="tabsAssociadas-1">
								</div>
							</div>
						</div>
						</form>
					</div>
					<div id="tabs-2" class="block">
						<form name='formPesquisa' method="POST">
						<div class="section">
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_VISAO' id='LOOKUP_VISAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div id='divOcultaVisaoRelacionada' style='display:none'>
			<table>
				<tr>
					<td>
						<fmt:message key='visaoAdm.visaoRelacionada'/>:
					</td>
					<td>
						<select name='divVisaoRelacionada_#SEQ#' onchange='mudaCampoVisaoRel(this, #SEQ#)'>
							<option value=''><fmt:message key='citcorpore.comum.selecione'/></option>
							<%
							for (Iterator it = relacaoVisoes.iterator(); it.hasNext();){
								VisaoDTO visaoDTO = (VisaoDTO)it.next();
								out.println("<option value='" + visaoDTO.getIdVisao() + "'>" + visaoDTO.getDescricao() + "</option>");
							}
							%>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key='notificacao.titulo'/>:
					</td>
					<td style='text-align: left;'>
						<input type='text' name='titulo_#SEQ#' size="80" maxlength="500" class='Description[notificacao.titulo]'/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key='visaoAdm.tipoVinculacao'/>:
					</td>
					<td>
						<select name='tipoVinculacao_#SEQ#'>
							<option value=''><fmt:message key='citcorpore.comum.selecione'/></option>
							<option value='1'><fmt:message key='visaoAdm.abaAoLado'/></option>
							<option value='2'><fmt:message key='visaoAdm.abaFilhaEmBaixo'/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key='visaoAdm.acaoCasoVisaoPesquisa'/>:
					</td>
					<td>
						<select name='acaoEmSelecaoPesquisa_#SEQ#'>
							<option value=''><fmt:message key='citcorpore.comum.nenhuma'/></option>
							<option value='<%=VisaoRelacionadaDTO.ACAO_RECUPERAR_PRINCIPAL%>'><fmt:message key='visaoAdm.recuperarRegistroPrincipal'/></option>
							<option value='<%=VisaoRelacionadaDTO.ACAO_RECUPERAR_REGISTROS_VINCULADOS%>'><fmt:message key='visaoAdm.recuperarRegistroVinculado'/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key='visaoAdm.situacaoVinculacao'/>:
					</td>
					<td style='text-align: left;'>
						<input type='radio' name='situacaoVisaoVinculada_#SEQ#' value='A' class='Description[lookup.situacaoVisao]'/> <fmt:message key='categoriaProduto.categoria_ativo'/>
						<input type='radio' name='situacaoVisaoVinculada_#SEQ#' value='I' class='Description[lookup.situacaoVisao]'/> <fmt:message key='categoriaProduto.categoria_inativo'/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key='visaoAdm.vinculoEntreVisoes'/>:
					</td>
					<td>
						<select name='tipoVinculo_#SEQ#' id='tipoVinculo_#SEQ#' onchange="mudaTipoVinculo(this, '#SEQ#', true)">
							<option value=''><fmt:message key='citcorpore.comum.nenhum'/></option>
							<option value='1'><fmt:message key='visaoAdm.relacionamentoUmParaN'/></option>
							<option value='2'><fmt:message key='visaoAdm.relacionamentoNParaN'/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<div id='divLabelVinculoVisao1_#SEQ#' style='display:none'>
							<fmt:message key='visaoAdm.objetoNegocioRelacao'/>:
						</div>
					</td>
					<td>
						<div id='divLabelVinculoVisao2_#SEQ#' style='display:none'>
							<select name='idObjetoNegocioNN_#SEQ#' onchange="mudaCampoObjNN(this, '#SEQ#')">
								<option value=''><fmt:message key='citcorpore.comum.nenhum'/></option>
								<%
								for (Iterator it = objetosNegocio.iterator(); it.hasNext();){
									ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO)it.next();
									out.println("<option value='" + objetoNegocioDTO.getIdObjetoNegocio() + "'>" + objetoNegocioDTO.getNomeObjetoNegocio() + "</option>");
								}
								%>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div id='divVinculoVisaoNN1_#SEQ#' style='display:none'>
						<br><br>
						<fieldset>
							<legend><b><fmt:message key='visaoAdm.relacaoObjetoFilhoVisaoPai'/></b></legend>
						<table>
							<tr>
								<td>
									<fmt:message key='visaoAdm.campoNegocioVisaoPai'/>
								</td>
								<td>
									<fmt:message key='visaoAdm.campoNegocioFilho'/>
								</td>
							</tr>
							<tr>
								<td>
									<select name='idCamposObjetoNegocioPai_#SEQ#' id='idCamposObjetoNegocioPai_#SEQ#' style='width: 350px'>
										<option value=''><fmt:message key='citcorpore.comum.nenhum'/></option>
									</select>
								</td>
								<td>
									<select name='idCamposObjetoNegocioObjNN1_#SEQ#' id='idCamposObjetoNegocioObjNN1_#SEQ#' style='width: 350px'>
									</select>
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<input type="button" name='btnAddItemVinc' value='<fmt:message key="portal.carrinho.adicionar"/>' onclick='addVincObjNNPai(#SEQ#)'/>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<select name='vinculosVisaoPaiNN_#SEQ#' id='vinculosVisaoPaiNN_#SEQ#' multiple="multiple" size="5" style='width: 700px; height: 100px'></select>
								</td>
								<td>
									<input type="button" name='btnRemItemVinc' value='<fmt:message key="dinamicview.remover"/>' onclick='remVincObjNNPai(#SEQ#)'/>
								</td>
							</tr>
						</table>
						</fieldset>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div id='divVinculoVisaoNN2_#SEQ#' style='display:none'>
						<br><br>
						<fieldset>
							<legend><b><fmt:message key='visaoAdm.relacaoObjetoComVisaoFilha'/></b></legend>
						<table>
							<tr>
								<td>
									<fmt:message key='visaoAdm.campoNegocioVisaoFilha'/>
								</td>
								<td>
									<fmt:message key='visaoAdm.campoNegocio'/>
								</td>
							</tr>
							<tr>
								<td>
									<select name='idCamposObjetoNegocioFilho_#SEQ#' id='idCamposObjetoNegocioFilho_#SEQ#' style='width: 350px'>
										<option value=''><fmt:message key='citcorpore.comum.nenhum'/></option>
									</select>
								</td>
								<td>
									<select name='idCamposObjetoNegocioObjNN2_#SEQ#' id='idCamposObjetoNegocioObjNN2_#SEQ#' style='width: 350px'>
									</select>
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<input type="button" name='btnAddItemVinc' value='<fmt:message key="portal.carrinho.adicionar"/>' onclick='addVincObjNNFilho(#SEQ#)'/>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<select name='vinculosVisaoFilhoNN_#SEQ#' id='vinculosVisaoFilhoNN_#SEQ#' multiple="multiple" size="5" style='width: 700px; height: 100px'></select>
								</td>
								<td>
									<input type="button" name='btnRemItemVinc' value='Remover' onclick='remVincObjNNFilho(#SEQ#)'/>
								</td>
							</tr>
						</table>
						</fieldset>
						</div>
					</td>
				</tr>
			</table>
		</div>

		<div id="POPUP_OBJ" style='width: 600px; height: 600px' >
			<form name='formItem' method="POST" action='${ctx}/pages/visaoAdm/visaoAdm'>
			<input type='hidden' name='numeroEdicao' class='Description[Número]'/>
			<table>
				<tr>
					<td>
						<fmt:message key='visaoAdm.objetoNegocio'/>:
					</td>
					<td>
						<select name='idObjetoNegocio' onchange='selecionaObjNegocio(this)' class='Valid[Required] Description[Objeto de Negócio]'>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key='visaoAdm.tipoCampo'/>:
					</td>
					<td>
						<select name='tipoNegocio' onchange='selecionaCampoObjNegocio(this)' class='Valid[Required] Description[Tipo de Campo]'>
							<option value=''><fmt:message key='citcorpore.comum.selecione'/></option>
							<option value='SELECT'><fmt:message key='visaoAdm.caixaSelecao'/></option>
							<option value='HIDDEN'><fmt:message key='visaoAdm.controleEscondido'/></option>
							<option value='DATE'><fmt:message key='avaliacaoFonecedor.dataAvaliacao'/></option>
							<option value='HTML'>HTML Code</option>
							<option value='DECIMAL'><fmt:message key='visaoAdm.moedaDecimal'/></option>
							<option value='NUMBER'><fmt:message key='citcorpore.comum.numero'/></option>
							<option value='RADIO'><fmt:message key='visaoAdm.radio'/></option>
							<option value='RELATION'><fmt:message key='visaoAdm.relacionamentoOutroObjetoNegocio'/></option>
							<option value='CLASS'><fmt:message key='visaoAdm.retornoExecucaoClasseMetodo'/></option>
							<option value='TEXT'><fmt:message key='modeloemail.texto'/></option>
							<option value='TEXTAREA'><fmt:message key='visaoAdm.textoLongo'/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key='visaoAdm.campoObjetoNegocio'/>:
					</td>
					<td>
						<select name='idCamposObjetoNegocio' onchange='selecionaCampoObjNegocio(this)' class='Valid[Required] Description[visaoAdm.campoObjetoNegocio]'>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key='solicitacaoServico.descricao'/>:
					</td>
					<td>
						<input type='text' name='descricaoNegocio' size="80" maxlength="500" class='Valid[Required] Description[solicitacaoServico.descricao]'/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key='questionario.tamanho'/>:
					</td>
					<td>
						<input type='text' name='tamanho' size="4" maxlength="4"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key='citcorpore.comum.obrigatorio'/>:
					</td>
					<td>
						<input type='radio' name='obrigatorio' value='S' class='Valid[Required] Description[Obrigatório]'/> <fmt:message key='citcorpore.comum.sim'/>
						<input type='radio' name='obrigatorio' value='N' class='Valid[Required] Description[Obrigatório]'/> <fmt:message key='citcorpore.comum.nao'/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div id='divCampoRelacao' style='display: none; border:1px solid black;'>
							<table>
								<tr>
									<td>
										<fmt:message key='visaoAdm.descricaoParaRelacionamentoto'/>:
									</td>
									<td>
										<input type='text' name='descricaoRelacionamento' size="70" maxlength="70"/>
									</td>
								</tr>
								<tr>
									<td>
										<fmt:message key='visaoAdm.relacionamentoObjetoNegocio'/>:
									</td>
									<td>
										<select name='idObjetoNegocioLigacao' onchange='selecionaObjNegocioLigacao(this)' class='Description[visaoAdm.relacionamentoObjetoNegocio]'>
										</select>
									</td>
								</tr>
								<tr>
									<td>
										<fmt:message key='visaoAdm.campoObjetoNegocioApresentacaoRelacionamento'/>:
									</td>
									<td>
										<select name='idCamposObjetoNegocioLigacao' class='Description[visaoAdm.campoObjetoNegocioApresentacaoRelacionamento]'>
										</select>
									</td>
								</tr>
								<tr>
									<td>
										<fmt:message key='visaoAdm.campoObjetoNegocioLigacaoRelacionamento'/>:
									</td>
									<td>
										<select name='idCamposObjetoNegocioLigacaoVinc' class='Description[Campo do Objeto de Negócio de Ligação]'>
										</select>
									</td>
								</tr>
								<tr>
									<td>
										<fmt:message key='visaoAdm.campoObjetoNegocioOrdemRelacionamento'/>:
									</td>
									<td>
										<select name='idCamposObjetoNegocioLigacaoOrder' class='Description[visaoAdm.campoObjetoNegocioOrdemRelacionamento]'>
										</select>
									</td>
								</tr>
								<tr>
									<td>
										<fmt:message key='visaoAdm.tipoCampoAprensentar'/>:
									</td>
									<td>
										<select name='tipoLigacao' class='Description[visaoAdm.tipoCampoAprensentar]'>
											<option value='N'><fmt:message key='citcorpore.comum.padrao'/></option>
											<option value='C'>COMBO</option>
											<option value='S'>LOOKUP</option>
										</select>
									</td>
								</tr>
								<tr>
									<td>
										<fmt:message key='visaoAdm.filtroAdicional'/>:
									</td>
									<td>
										<textarea rows="3" cols="80" name="filtro" id="filtro" style='border:1px solid black;'></textarea>
									</td>
								</tr>
							</table>
						</div>
						<div id='divCampoNumeroDecimais' style='display: none; border:1px solid black;'>
							<table>
								<tr>
									<td>
										<fmt:message key='questionario.decimais'/>:
									</td>
									<td>
										<input type='text' name='decimais' size="4" maxlength="4"/>
									</td>
								</tr>
							</table>
						</div>
						<div id='divCampoRadioSelect' style='display: none; border:1px solid black;'>
							<table>
								<tr>
									<td>
										<fmt:message key='visaoAdm.valorOpcao'/>:
									</td>
									<td>
										<input type='text' name='valorOpcao' size="70" maxlength="100"/>
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										<fmt:message key='visaoAdm.descricaoOpcao'/>:
									</td>
									<td>
										<input type='text' name='descricaoOpcao' size="70" maxlength="100"/>
									</td>
									<td>
										<input type='button' name='btnAddOpt' value='<fmt:message key="visaoAdm.adicionarOpcao"/>' onclick='addOpcao()'/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<select size='10' style='width: 400px; height: 100px' name='valoresOpcoes' multiple="multiple"></select>
									</td>
									<td>
										<input type='button' name='btnRemOpt' value='<fmt:message key="visaoAdm.removerOpcao"/>' onclick='removeOpcao()'/>
									</td>
								</tr>
							</table>
						</div>
						<div id='divCampoHTMLCode' style='display: none; border:1px solid black;'>
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td style="vertical-align: middle;">
										HTML Code:
									</td>
									<td>
										<textarea name="htmlCode" id="htmlCode" rows="5" cols="90" class='Description[HTML Code]' style='border:1px solid black;'></textarea>
									</td>
								</tr>
							</table>
						</div>
						<div id='divCampoClass' style='display: none; border:1px solid black;'>
							<fmt:message key="visaoAdm.atencaoMetodoDeveRetornarColecao"/>
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td style="vertical-align: middle;">
										<fmt:message key="visaoAdm.acrescenteInformacoesAbaixoCampoFormula"/>:<br>
										<b><fmt:message key='visaoAdm.classe'/></b>, <b><fmt:message key='visaoAdm.metodoExecutar'/></b>, <b><fmt:message key="visaoAdm.identificadorCombo"/></b>, <b><fmt:message key="visaoAdm.atributoDescricaoCombo"/></b> (<fmt:message key="visaoAdm.devemSeperadosVirgulo"/>):
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">
						<fmt:message key='requisitosla.formula'/>:
					</td>
					<td>
						<textarea name="formula" rows="5" cols="90" class='Description[Fórmula]' style='border:1px solid black;'></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key='projeto.situacao'/>:
					</td>
					<td>
						<input type='radio' name='situacao' value='A' class='Valid[Required] Description[Situação]'/> <fmt:message key='citcorpore.comum.ativo'/>
						<input type='radio' name='situacao' value='I' class='Valid[Required] Description[Situação]'/> <fmt:message key='citcorpore.comum.inativo'/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table>
							<tr>
								<td>
									<button name="btnSalvar" type='button' onclick='adicionaAtualizaItem()'>OK</button>
								</td>
								<td>
									<button name="btnExcluir" type='button' onclick='excluir()'><fmt:message key='visaoAdm.excluirCampo'/></button>
								</td>
								<td>
									<button name="btnFechar" type='button' onclick='fechar()'><fmt:message key='citSmart.comum.fechar'/></button>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</form>
		</div>

		<div id="POPUP_SCRIPT" style='width: 750px; height: 600px' >
			<form name='formScript' method="POST" action='${ctx}/pages/visaoAdm/visaoAdm'>
			<table>
				<tr>
					<td>
						<select name='scryptType' onchange='recuperaScript(this)' class='Valid[Required] Description[Tipo de Script]' size="20" style='height: 500px'>
							<optgroup label='<fmt:message key="citcorpore.controleContrato.cliente"/>' style='background-color: yellow; color: red'></optgroup>
							<%
							for (ScriptEventDTO scriptEvt : ScriptsVisaoDTO.colScriptEvents){
								if (!scriptEvt.getName().equalsIgnoreCase(ScriptsVisaoDTO.SCRIPT_ONSQLSEARCH.getName())
										&& !scriptEvt.getName().equalsIgnoreCase(ScriptsVisaoDTO.SCRIPT_ONSQLWHERESEARCH.getName())
										&& !scriptEvt.getName().equalsIgnoreCase(ScriptsVisaoDTO.SCRIPT_AFTERCREATE.getName())
										&& !scriptEvt.getName().equalsIgnoreCase(ScriptsVisaoDTO.SCRIPT_AFTERUPDATE.getName())){
									out.println("<option value='" + ScriptsVisaoDTO.SCRIPT_EXECUTE_CLIENT + "#" + scriptEvt.getName() + "'>"+UtilI18N.internacionaliza(request, scriptEvt.getDescription())+"</option>");
								}
							}
							%>
							<optgroup label='<fmt:message key="mapaDesenhoServico.servidor"/>' style='background-color: yellow; color: red'></optgroup>
							<%
							for (ScriptEventDTO scriptEvt : ScriptsVisaoDTO.colScriptEvents){
								out.println("<option value='" + ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + scriptEvt.getName() + "'>"+UtilI18N.internacionaliza(request, scriptEvt.getDescription())+"</option>");
							}
							%>
						</select>
					</td>
					<td style='vertical-align: top;'>
						<textarea rows="27" cols="80" name="script" id="script" style='border: 1px solid black; width: 100%; font-family: "Courier New"'></textarea>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						<table>
							<tr>
								<td>
									<button name="btnSalvarScript" type='button' onclick='adicionaAtualizaScript()'><fmt:message key='visaoAdm.atualizaScript'/></button>
								</td>
								<td>
									<button name="btnFecharScript" type='button' onclick='fecharScript()'><fmt:message key='citSmart.comum.fechar'/></button>
								</td>
								<td>
									&nbsp;&nbsp;
								</td>
								<td>
									<div id='divMensagemScript' style='background-color: yellow; color: red'>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</form>
		</div>

		<div id="POPUP_HTMLCODE" style='width: 750px; height: 600px' >
			<form name='formHtmlCode' method="POST" action='${ctx}/pages/visaoAdm/visaoAdm'>
			<table>
				<tr>
					<td>
						<select name='htmlCodeType' onchange='recuperaHtmlCode(this)' class='Valid[Required] Description[Tipo de HTML Code]' size="20" style='height: 500px'>
							<%
							for (HtmlCodePartDTO htmlCodePart : HtmlCodeVisaoDTO.colHtmlCodeParts){
								out.println("<option value='" + htmlCodePart.getName() + "'>" +UtilI18N.internacionaliza(request,htmlCodePart.getDescription()) + "</option>");
							}
							%>
						</select>
					</td>
					<td style='vertical-align: top;'>
						<textarea rows="27" cols="84" name="htmlCode" id="htmlCodePopupHtmlCode" style='border: 1px solid black; width: 100%; font-family: "Courier New"'></textarea>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						<table>
							<tr>
								<td>
									<button name="btnSalvarHTMLCode" type='button' onclick='adicionaAtualizaHTMLCode()'><fmt:message key='visaoAdm.atualizaHTMLCode'/></button>
								</td>
								<td>
									<button name="btnFecharHTMLCode" type='button' onclick='fecharHTMLCode()'><fmt:message key='citSmart.comum.fechar'/></button>
								</td>
								<td>
									&nbsp;&nbsp;
								</td>
								<td>
									<div id='divMensagemHTMLCode' style='background-color: yellow; color: red'>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</form>
		</div>

		<div id="POPUP_BOTOES" style='width: 800px; height: 600px' >
			<form name='formBotoes' method="POST" action='${ctx}/pages/visaoAdm/visaoAdm'>
			<input type='hidden' name='ordemBotoes' value=''/>
			<table>
				<tr>
					<td>
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<select name='botaoCadastrado' onchange='recuperaBotao(this)' size="2" style='height: 250px; width: 120px'>
									</select>
								</td>
							</tr>
							<tr>
								<td style='text-align: center'>
									<table>
										<tr>
											<td style='border:1px solid black; cursor: pointer' onclick='ordenaCima();'>
												<img src='${ctx}/imagens/forms/ordena.gif' border='0'/>
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td style='border:1px solid black; cursor: pointer' onclick='ordenaBaixo();'>
												<img src='${ctx}/imagens/forms/ordena2.gif' border='0'/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
					<td style="vertical-align: top; border: 1px solid black" valign="top">
						<table>
							<tr>
								<td>
									<fmt:message key='solicitacaoServico.acao'/>:
								</td>
								<td>
									<select name='acao' onchange='selecionaAcaoBotao(this)' class='Valid[Required] Description[solicitacaoServico.acao]'>
										<option value=''><fmt:message key='citcorpore.comum.selecione'/></option>
										<option value='<%=BotaoAcaoVisaoDTO.ACAO_GRAVAR%>'><fmt:message key='portal.gravarDados'/></option>
										<option value='<%=BotaoAcaoVisaoDTO.ACAO_LIMPAR%>'><fmt:message key='portal.limparDados'/></option>
										<option value='<%=BotaoAcaoVisaoDTO.ACAO_EXCLUIR%>'><fmt:message key='itemRequisicaoProduto.excluir'/></option>
										<option value='<%=BotaoAcaoVisaoDTO.ACAO_SCRIPT%>'><fmt:message key='visaoAdm.executarScript'/></option>
									</select>
								</td>
							</tr>
							<tr>
								<td>
									<fmt:message key='modeloemail.texto'/>:
								</td>
								<td>
									<input type='text' name='texto' size="80" maxlength="100" class='Valid[Required] Description[modeloemail.texto]'/>
								</td>
							</tr>
							<tr>
								<td style='vertical-align: middle;' valign="middle">
									Script:
								</td>
								<td>
									<textarea rows="10" cols="70" name="script" id="scriptBotao" style='border: 1px solid black; font-family: "Courier New"'></textarea>
								</td>
							</tr>
							<tr>
								<td style='vertical-align: middle;' valign="middle">
									<fmt:message key='visaoAdm.mensagemMovimentar'/><br><fmt:message key='visaoAdm.mouseSobreBotão'/>:
								</td>
								<td>
									<input type='text' name='hint' size="80" maxlength="100"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						<table>
							<tr>
								<td>
									<button name="btnSalvarScript" type='button' onclick='adicionaAtualizaBotao()'>OK</button>
								</td>
								<td>
									&nbsp;&nbsp;
								</td>
								<td>
									<button name="btnExcluirScript" type='button' onclick='excluirBotao()'><fmt:message key='itemRequisicaoProduto.excluir'/></button>
								</td>
								<td>
									&nbsp;&nbsp;
								</td>
								<td>
									<button name="btnSalvarScript" type='button' onclick='limparBotao()'><fmt:message key='citcorpore.ui.botao.rotulo.Limpar'/></button>
								</td>
								<td>
									<button name="btnFecharScript" type='button' onclick='fecharBotao()'><fmt:message key='citcorpore.ui.botao.rotulo.Fechar'/></button>
								</td>
								<td>
									&nbsp;&nbsp;
								</td>
								<td>
									<div id='divMensagemBotao' style='background-color: yellow; color: red'>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</form>
		</div>

		<!-- Pop de Exportação -->
		<div id="POPUP_EXPORTARVISOES" style='display:none'>
			<form name="formExport" method='post' action='${ctx}/pages/visaoAdm/visaoAdm.load'>
				<button name="btnExportarOK" type='button' onclick='exportarVisoesXML()'><fmt:message key='citcorpore.comum.exportarArquivo'/></button>
				<button id='btnFechaExportacoes' name='btnFechaExportacoes' type="button"><fmt:message key='citcorpore.ui.botao.rotulo.Fechar'/></button>
				<input type='hidden' id='visoesSerializadas' name='visoesSerializadas'/>
				<div>
					<div id="listaVisoesTb"></div>
				</div>
			</form>
		</div>
		<!-- Pop de Importação -->
		<div id="POPUP_IMPORTARVISOES" style='display:none'>
			<form name="formUpload" method="post" enctype="multipart/form-data">
				<cit:uploadControl style="height:100px;width:100%;border:1px solid black"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/upload/upload.load" disabled="false"/>
				<button name="btnImportarOK" type='button' onclick='importarVisoesXML()'><fmt:message key='citcorpore.comum.importarArquivo'/></button>
				<button id='btnFechaImportacoes' name='btnFechaImportacoes' type="button"><fmt:message key='citcorpore.ui.botao.rotulo.Fechar'/></button>
				<input type='hidden' id='visoesSerializadas' name='visoesSerializadas'/>
			</form>
		</div>
		<!-- Pop de Importação de Todas as Visoes Novas -->
		<div id="POPUP_IMPORTARTODASVISOES" style='display:none'>
			<fmt:message key='visaoAdm.mensagemPopUp'/><fmt:message key='visaoAdm.mensagemPopUpCont'/>
			<div class="barra">
			<button id="concluir" name="btnImportarTodosOK" type='button' onclick='importarTodasVisoesXML()'><fmt:message key='citcorpore.comum.importarTudo'/></button>
				<button id='btnFechaTodasImportacoes' name='btnFechaTodasImportacoes' type="button"><fmt:message key='citcorpore.ui.botao.rotulo.Fechar'/></button>
				<div id="Throbber" class="throbber"></div>
			</div>
			<br/>
			<div id="listaTodasVisoesTb"></div>
			<div id="listaMetadadosTb"></div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>

	<script type="text/javascript" src="js/visaoAdm.js"></script>

	<%@include file="/include/footer.jsp"%>
</body>
</html>
