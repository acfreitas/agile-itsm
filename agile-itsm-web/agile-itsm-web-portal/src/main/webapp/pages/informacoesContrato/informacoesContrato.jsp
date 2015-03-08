<%@page import="br.com.centralit.citcorpore.ajaxForms.InformacoesContrato"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.util.CITCorporeUtil"%>
<%@page import="br.com.citframework.service.ServiceLocator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.InformacoesContratoItem"%>
<%@page import="br.com.centralit.citcorpore.bean.InformacoesContratoDTO"%>

<!doctype html public "">
<html>
<head>
	<%@include file="/include/header.jsp"%>
	<%
		UsuarioDTO user = WebUtil.getUsuario(request);

		String retorno = "${ctx}/pages/index/index.load";
		String modulo = (String)request.getParameter("modulo");
		if (modulo == null || modulo.equalsIgnoreCase("")){
			retorno = "${ctx}/pages/index/index.load";
			modulo = "";
		}
		if (modulo.equalsIgnoreCase("enfermagem")){
			retorno = "${ctx}/pages/indexcitcorpore/index.jsp";
		}

		String parametroPortal = request.getParameter("portal");
		pageContext.setAttribute("parametroPortal", parametroPortal);

		String dataAtual = UtilDatas.dateToSTR(UtilDatas.getDataAtual());
		pageContext.setAttribute("dataAtual", dataAtual);

		String caminho = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO");
		pageContext.setAttribute("", caminho);

		pageContext.setAttribute("retorno", retorno);

	%>

	<%@include file="/include/security/security.jsp"%>
	<title>CITSmart</title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	<%@include file="/include/titleComum/titleComum.jsp" %>
	<%@include file="/include/menu/menuConfig.jsp" %>

	<link rel="stylesheet" type="text/css" href="./css/informacoesContrato.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/css/pesquisa.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/fullcalendar/fullcalendar.css">
	<link href="${ctx}/novoLayout/common/theme/css/style-light.css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="./css/informacoesContrato2.css" />


</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>

<body >
	<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>

	<!-- Conteudo -->
	<div id="main_container" class="main_container container_16 clearfix" >
	<%@include file="/include/menu_horizontal.jsp"%>
	<div class="flat_area grid_16">
				<h2><fmt:message key="contrato.administracao_contrato"/></h2>

	<!-- ## AREA DA APLICACAO ## -->
	<form name='formProntuario' id='formProntuario' action='${ctx}/pages/informacoesContrato/informacoesContrato' accept-charset="ISO-8859-1" onsubmit="document.charset = 'ISO-8859-1'">
		<div id="estruturaContrato" >
			<div id="topo" style="width: 100% !important;">
				<!-- 	<input type='hidden' name='nomeContrato'/> -->
					<input type='hidden' name='funcaoUpload'/>
					<input type='hidden' name='idServicoContrato'/>
					<input type='hidden' id="servicosContratosSerializadas" name='servicosContratosSerializadas'/>
					<input type='hidden' name='conteudoDivImprimir'/>
					<input type='hidden' name='funcaoListarOS'/>
					<input type='hidden' name='funcaoListarFatura'/>
					<input type='hidden' name='dataOS1'/>
					<input type='hidden' name='dataOS2'/>
					<input type='hidden' name='dataFatura1'/>
					<input type='hidden' name='dataFatura2'/>
					<input type='hidden' name='idOS'/>
					<input type='hidden' name='idFatura'/>
					<input type='hidden' name='idFaturaApuracaoANS'/>
					<input type='hidden' name='idAcordoNivelServicoContrato'/>
					<input type='hidden' name='dataInicioAtividade'/>
					<input type='hidden' name='dataFimAtividade'/>
					<input type='hidden' name='idAcordoServicoContrato'/>
					<input type='hidden' name='servicoDinamicView' id='servicoDinamicView' />
						<label class="showAlertas"><!-- <img src='${ctx}/produtos/citsaude/imagens/barraFerrProntuario/36.gif' border='0' style='cursor:pointer' onclick='showAlertas();' title="<fmt:message key="contrato.alertas" />" alt="<fmt:message key="contrato.alertas" />"/> --></label>
						<div class='row-fluid'>
							<div class='span12'>
								<div class="span3">
									<label><strong><fmt:message key="contrato.contrato" /></strong></label>
									<%-- <cit:lookupField lockupName="LOOKUP_CONTRATOS" id="LOOKUP_CONTRATOS" heigth="600" left="40" len="1000" htmlCode="true" top="0" javascriptCode="true" formName="formProntuario" value="idContrato" text="numeroContrato"/> --%>
									<input type='text' name='nomeContrato' id='nomeContrato' size="10" maxlength="10" value="" href="#modal_lookupContrato" data-toggle="modal" data-target="#modal_lookupContrato"/>
								</div>
								<div class="span3">
									<label ><strong><fmt:message key="contrato.identificacao_contrato" /></strong></label>
									<input type='text' class='campoReadOnly' name='idContrato' id='idContrato' size="10" maxlength="10" value="" readonly="readonly"/>
								</div>
							</div>
							<div class='row-fluid'>
								<div class="span6">
									<label><strong><fmt:message key="contrato.pesquisar_servicos_contrato"/></strong></label>
									<input type='text' value="" id="campoPesquisa" name="campoPesquisa" onkeydown="if ( event.keyCode == 13 ) pesquisarServicosContrato();" />

								</div>

								<div class="span6">
									<label>&nbsp;</label>
									<button type='button' name='btnPesquisar' class="light" onclick='pesquisarServicosContrato();' >
										<span><fmt:message key="citcorpore.comum.pesquisar" /></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpaCamposEGrid();' >
										<span><fmt:message key="citcorpore.ui.botao.rotulo.Limpar" /></span>
									</button>
									<button type='button' name='btnNovo' class="light" onclick="showCadNovoContrato();"  >
										<span><fmt:message key="contrato.criar_novo_contrato" /></span>
									</button>
								</div>
							</div>
						</div>
						<div id="clear-fix"></div>
					<div class="tabsbar tabsbar-2">
						<ul class="row-fluid row-merge">
							<li onclick='showServicosContrato();' id="idMostrarServicosContrato" class="span3 glyphicons cogwheel"><a  data-toggle="tab"><i></i> <span><fmt:message key="contrato.servicos_contrato" /></span></a></li>
							<li onclick='showOS();' id="idMostrarOS" class="span3 glyphicons more_items"><a  data-toggle="tab"><i></i> <span><fmt:message key="contrato.ordens_servico" /></span></a></li>
							<li onclick='showFaturas();' id="idMostrarFaturas" class="span3 glyphicons coins"><a  data-toggle="tab"><i></i> <span><fmt:message key="contrato.faturas" /></span></a></li>
							<li onclick='showNotificacoes();' id="idMostrarNotificacoes"class="span3 glyphicons circle_exclamation_mark"><a  data-toggle="tab"><i></i> <span><fmt:message key="contrato.notificacoes" /></span></a></li>
						</ul>
					</div>
						<div class="separator bottom"></div>

				</div>
				<!-- <div>
					<input type='button' id='buttonAbrirPopupNotificacoesServicos' value='Criar notificações' title='Criar notificações de serviços de contrato' onclick='abrirPopupNotificacoesServicos();'>
				</div> -->
				<div id="corpo">
				<div id="direita" class="divProntuario ui-widget ui-state-default ui-corner-all" style="width:100% !important; display:none; overflow: auto;"></div>
				<div id="esquerda" style="display: none;" class="ui-widget ui-state-default ui-corner-all">
					<label class="imgSeta">
						<img id='imgSetaProntuario' src='${ctx}/imagens/arrow_right.png' border='0' style='cursor:pointer' onclick='atualizaTamanhoDIV()'/>
					</label>
					<table cellpadding="0" cellspacing="0" id='tblMenuProntuario' class='table table-bordered table-striped' style='width: 155px'>
					<% Collection itensProntuario = (Collection)request.getAttribute("itensProntuario");
						if (itensProntuario != null){
							Iterator it = itensProntuario.iterator();
							InformacoesContratoItem itemProntuario;
							for(;it.hasNext();){
								itemProntuario = (InformacoesContratoItem)it.next();
								boolean subItens = (itemProntuario.getColSubItens() != null && itemProntuario.getColSubItens().size() > 0);
								out.print("<tr id='trITEMMENU_" + itemProntuario.getNome() + "'>");
								boolean bEntrei = false;
								if (itemProntuario.getColSubItens() != null && itemProntuario.getColSubItens().size() > 0){
									out.print("<td width='5%' style='cursor:pointer' onclick=\"setaAbaSelecionada('" + itemProntuario.getNome() + "', " + subItens + ", '" + itemProntuario.getPath() + "', 'tdITEMMENU_" + itemProntuario.getNome() + "', " + itemProntuario.isGrupo() + ")\"><img id='img_" + itemProntuario.getNome() + "' src='" + "${ctx}/produtos/citsaude/imagens/im_mais1.jpg'/></td>");
									bEntrei = true;
								}else{
									out.print("<td width='5%' style='cursor:pointer' onclick=\"setaAbaSelecionada('" + itemProntuario.getNome() + "', " + subItens + ", '" + itemProntuario.getPath() + "', 'tdITEMMENU_" + itemProntuario.getNome() + "', " + itemProntuario.isGrupo() + ")\">&nbsp;</td>");
								}
								String compl = "";
								if (!bEntrei){
									compl = " colspan='2' ";
								}
								out.print("<td " + compl + " id='tdITEMMENU_" + itemProntuario.getNome() + "' style='cursor:pointer' class='bordaNaoSelecionaProntuario' onclick=\"setaAbaSelecionada('" + itemProntuario.getNome() + "', " + subItens + ", '" + itemProntuario.getPath() + "', 'tdITEMMENU_" + itemProntuario.getNome() + "', " + itemProntuario.isGrupo() + ")\">");
								out.print(itemProntuario.getDescricao());
								WebUtil.renderizaFilhos(itemProntuario, out);
								out.print("</td>");
								out.print("</tr>");
								out.print("<tr><td style='height:2px'></td></tr>");
								out.println("<script>arrayItensMenu[iItemMenu] = 'tdITEMMENU_" + itemProntuario.getNome() + "';</script>");
								out.println("<script>arrayNomesItensMenu[iItemMenu] = '" + itemProntuario.getNome() + "';</script>");
								out.println("<script>iItemMenu++;</script>");
							}
						}


					%>
					</table>
				</div>
			</div>
		</div>

	</form>

	<form style="border: 0;" name='formListaAnexos' action='${ctx}/recuperaFromGed/recuperaFromGed'>
		<input type='hidden' name='idControleGED'/>
		<input type='hidden' name='nomeArquivo'/>
	</form>

	<cit:janelaPopup style="display:none;top:1350px;width:935px;left:0px;height:575px;position:absolute;" modal="true" title="Estatística" id="POPUP_ESTATISTICA_IMPRIMIR_QUEST">
		<form name='formImprimirFormulario' method="POST" action="${ctx}/pages/contratoQuestionarios/contratoQuestionarios">
			<input type='hidden' name='idContratoQuestionario'/>
			<input type='hidden' name='idQuestionario' />
		 	<input type='hidden' name='parmCount'/>
		 	<input type='hidden' name='parm1'/>
		 	<input type='hidden' name='parm2'/>
		 	<input type='hidden' name='parm3'/>
		</form>
	</cit:janelaPopup>

	<script>
		document.formProntuario.afterLoad = function(){
			window.setTimeout('funcaoStart()', 500);
		};
	</script>

	<cit:janelaPopup maximize="true" style="display:none;top:1350px;width:805px;left:0px;height:455px;position:absolute;" modal="true" title="Formulário" id="POPUP_QUESTIONARIO3">
		<form name='formPessoaQuestionario3' method="post" action='${ctx}/pages/contratoQuestionarios/contratoQuestionarios'>
			<input type='hidden' name='idContrato' id='idContrato3'/>
			<input type='hidden' name='idQuestionario' id='idQuestionario3'/>
			<input type='hidden' name='aba' id='aba3'/>
			<input type='hidden' name='idItem' id='idItem3'/>
			<input type='hidden' name='idContratoQuestionario' id='idContratoQuestionario3'/>

			<div id='divQuestionario3' style='width: 100%; height: 100%'>
				<table id='tblQuestionario3' style='width: 100%; height: 100%'>
					<tr>
						<td>
							<table>
								<tr>
									<td>
										<div id='divOpcoesSituacaoQuest3'>
											Situação: <select name='situacao' id='situacao3'><option value='E'>Em Edição</option><option value='F'>Finalizado</option></select>
										</div>
									</td>
									<td>
										<input type='button' name='btnGravarQuestionario3' id='btnGravarQuestionario3' value='Gravar' onclick='gravarFormulario3()'/>
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										<input type='button' value='Visualizar Histórico desta Seção' onclick='showHistoricoAba(document.formPessoaQuestionario3.aba.value)'/>
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										<input type='button' name='btnFecharQuestionario' value='Fechar' onclick='POPUP_QUESTIONARIO3.hide()'/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td id='tdFrameQuestionario3' style='width: 100%; height: 100%'>
							<iframe name="frameQuestionario3" id="frameQuestionario3" src='about:blank' frameborder="0" height="390" width="800"></iframe>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</cit:janelaPopup>
	<cit:janelaPopup style="display:none;top:1350px;width:805px;left:0px;height:500px;position:absolute;" modal="true" title="Gravação de Registro Eletrônico de Saúde" id="POPUP_FINALIZACAO_ITEM">
		<iframe name='frameUploadCertificado' id='frameUploadCertificado' src='about:blank' height="0" width="0" style='display:none'/></iframe>
			<div style='border:1px solid black; background-color: white; width:805px; height:40px; overflow: auto; text-align: center'>
				<font color='red' size="12"><b>Confirma a gravação do formulário  <br>Após gravação não serão permitidas alterações!</b></font>
			</div>
		<div id='divCertificados' style='border:1px solid black; background-color: white; width:805px; height:100px; overflow: auto; display:none'></div>
		<form name='formSelecaoProdutos' method="POST" action='${ctx}/pessoasQuestionarios/pessoasQuestionarios'>
			<input type='hidden' name='divAtualizarCertificado' value='divCertificados'/>
			<input type='hidden' name='nomeFrame'/>
			<div id='divSelecaoProdutos' style='border:1px solid black; background-color: white; width:805px; height:380px; overflow: auto;'>
			</div>
			<input type='hidden' name='idContrato' id='idContratoSelecaoProdutos'/>
			<input type='hidden' name='aba' id='abaSelecaoProdutos'/>
			<div id='divOpcoes' style='border:1px solid black; background-color: white; width:805px; height:50px; overflow: auto;'>
				<table>
					<tr>
						<td>
							<input type='button' name='btnGravarItem' value='Gravar' onclick='gravarItemRES()'/>
						</td>
						<td>
							<input type='button' name='btnfecharItem' value='Fechar' onclick='fecharTelaFinalizacao()'/>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</cit:janelaPopup>

	<cit:janelaPopup style="display:none;top:1350px;width:805px;left:50px;height:350px;position:absolute;" modal="true" title="Upload Anexo" id="POPUP_ANEXO">
		<iframe name='frameUploadCertificadoAnexos' id='frameUploadCertificado' src='about:blank' height="0" width="0" style='display:none'/></iframe>
			<div id='divMsgCertDigApplet' style='border:1px solid black; background-color: white; width:805px; height:40px; overflow: auto; text-align: center'>
				<font color='red' size="12"><b>Atenção ! <br>Para que o anexo tenha validade legal é importante utilizar seu certificado digital (ver Resolução CFM 1821/2007).</b></font>
			</div>
		<iframe name='frameUploadCertificadoAnexosApplet' id='frameUploadCertificadoApplet' style='display:none' src='about:blank' height="250" width="800"/>
		</iframe>
		<form name='formListaCertificadosAnexos' method="POST" action='${ctx}/pessoasQuestionarios/pessoasQuestionarios'>
			<input type='hidden' name='divAtualizarCertificado' value='divCertificadosAnexos'/>
		</form>
		<iframe name='frameUploadAnexo' id='frameUploadAnexo' src='about:blank' height="0" width="0" style="display: none" frameborder="0"/></iframe>
		<div id='divEnviarArquivo' style='display:block; padding: 5px;'>
		<form name='formAnexo' method="post" ENCTYPE="multipart/form-data" action='${ctx}/pages/imagemHistorico/imagemHistorico.load'>
			<input type='hidden' name='idContrato'/>
			<input type='hidden' name='aba' id='abaImagens'/>
			<table>
				<tr>
					<td class="campoEsquerda">
						Arquivo Anexo:
					</td>
					<td>
						<input type='file' name='arquivo' size="60"/>
					</td>
				</tr>
				<tr>
					<td class="campoEsquerda">
						Observações:
					</td>
					<td>
						<textarea rows="8" cols="60" name="observacao" style="border: 1px solid black;"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<table>
							<tr>
								<td>
									<button type='button' name='btnUpDate' class="light" onclick='submitFormAnexo();'>
											<img src="${ctx}/template_new/images/icons/small/grey/paperclip.png">
											<span>Enviar</span>
									</button>
								</td>
								<td>
									<button type='button' name='btnUpDate' class="light" onclick='POPUP_ANEXO.hide();'>
											<img src="${ctx}/template_new/images/icons/small/grey/paperclip.png">
											<span>Fechar</span>
									</button>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		</div>
	</cit:janelaPopup>

	<!-- AREA DE SCRIPTS DAS ABAS DO PRONTUARIO -->
	<script type="text/javascript" src="${ctx}/js/contratosAnexos.js"></script>

	<!-- FIM - AREA DE SCRIPTS DAS ABAS DO PRONTUARIO -->

	<!-- ## FIM - AREA DA APLICACAO ## --></div>
	</div>
	</div>
	<!-- Fim da Pagina de Conteudo -->

	<%@include file="/include/footer.jsp"%>

	<cit:janelaPopup maximize="true" style="display:none;top:1350px;width:915px;left:0px;height:635px;position:absolute;" modal="true" title="Formulário" id="POPUP_QUESTIONARIO">
		<form name='formPessoaQuestionario' method="post" action='${ctx}/pages/contratoQuestionarios/contratoQuestionarios'>
			<input type='hidden' name='idContrato'/>
			<input type='hidden' name='idQuestionario'/>
			<input type='hidden' name='aba'/>
			<input type='hidden' name='idItem'/>
			<input type='hidden' name='dataAtual'/>
			<input type='hidden' name='idContratoQuestionario'/>

			<div id='divQuestionario' style='width: 100%; height: 100%'>
				<table id='tblQuestionario' style='width: 100%; height: 100%;'>
					<tr>
						<td>
							<table width="100%" style='width: 100%;border: 1px solid black;' bgcolor="#d3d3d3" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td colspan="10">
													<div id='divInfoPaciente' style='width: 100%; '>
													</div>
												</td>
											</tr>
										</table>
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td>
													<div id='divOpcoesSituacaoQuest' >
														<table cellpadding="0" cellspacing="0">
															<tr>
																<td class="campoEsquerdaSemTamanho">
																	Situação:
																</td>
																<td>
																	 <select name='situacao'><option value='E'>Em Edição</option><option value='F'>Finalizado</option></select>
																</td>
																<td>
																	&nbsp;
																</td>
																<td>
																	<input type='text' name='dataQuestionario' id='dataQuestionario' size="10" maxlength="10" class='Format[Date] Valid[Date] datepicker' />
																</td>
															</tr>
														</table>
													</div>
												</td>
												<td>
													&nbsp;
												</td>
												<td>
												<!-- 	<input type='button' name='btnGravarQuestionario' id='btnGravarQuestionario' value='Gravar' onclick='gravarFormulario()'/>  -->
														<button type='button' id="btnGravarQuestionario" name='btnGravarQuestionario' style="margin-top: 5px; margin-left: 3px" class="light img_icon has_text" onclick="gravarFormulario()">
																<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
															<span style="font-size: 12px !important;">Gravar</span>
														</button>
												</td>
												<td>
													&nbsp;
												</td>
												<td>
													<div id='imgGravarEditar' >
														<table>
															<tr>
																<td>
																</td>
															</tr>
														</table>
													</div>
												</td>
												<td>
												</td>
												<td>
													&nbsp;
												</td>
												<td>
													&nbsp;
												</td>
												<td>
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
												<button type='button' id="btnFecharQuestionario" name='btnFecharQuestionario' style="margin-top: 5px; margin-left: 3px" class="light img_icon has_text" onclick="fecharQuestionario()">
													<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
												 <span style="font-size: 12px !important;">Fechar</span>
												</button>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td id='tdFrameQuestionario' style='width: 100%; height: 100%'>
							<iframe name="frameQuestionario" id="frameQuestionario" src='about:blank' frameborder="0" height="455" width="910"></iframe>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</cit:janelaPopup>
	<div class="modal hide fade in" id="POPUP_CADASTRO_ALTERACAO" aria-hidden="false" data-width='1098px'>
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
							<iframe name='frameCadastro' id='frameCadastro' src='about:blank' height="590" width="1098"></iframe>

						</div>

					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

	<div id="POPUP_AGENDAMENTO_OS" style="margin: 20px; width: 99%" title="<fmt:message key='citcorpore.comum.agendaOS'/>">
		<form name='formAgendamentoOS' method="POST" action="${ctx}/pages/atividadePeriodica/atividadePeriodica">
			<fieldset>
			<table >
				<tr>
	            <td class="campoEsquerda"><fmt:message key="citcorpore.comum.datainicio" />*:</td>
	            <td>
	           		<div><input  id="dataInicio" type='text' name="dataInicio" maxlength="10" class="Valid[Data] Description[Data Inicio] Format[Data] text" /></div>
	            </td>
	         </tr>
	         <tr>
	            <td class="campoEsquerda"><fmt:message key="citcorpore.comum.datafim" />*:</td>
	            <td>
	           		<input  id="dataFim" type='text' name="dataFim" maxlength="10"	class="Valid[Data] Description[Data Fim] Format[Data] text" />
	            </td>
	         </tr>
	        <!--  pesquisarAgendaOs() -->
				<tr>
					 <td class="campoEsquerda" colspan="2">

						<button type='button' id="btnPesquisarAgendaOs" name='btnPesquisarAgendaOs'  style="margin-top: 5px; margin-left: 3px" class="light img_icon has_text" onclick="pesquisarAgendaOs()">
							<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
						 <span style="font-size: 12px !important;">Pesquisar</span>
						</button>
						<button type='button' id="btnPesquisarAgendaOs" name='btnPesquisarAgendaOs' style="margin-top: 5px; margin-left: 3px" class="light img_icon has_text" onclick="limpar()">
							<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
							<span style="font-size: 12px !important;">Limpar Dados</span>
						</button>
					</td>
				</tr>
			</table>
			</fieldset>
			<div id="AgendamentoOS" style="width: 800px; margin: 10px; "></div>
			</form>
	</div>
	<div id="POPUP_REGISTRO_EXECUCAO" style="margin: 20px; width: 99%" title="<fmt:message key='contrato.gerarRA'/>">
	<form name='formRegistroExecucao'action='${ctx}/pages/os/os'>
		<input type='hidden' name='idOSPai' id='idOSPai'/>
			<fieldset>
				<table >
				<tr>
		            <td class="campoEsquerda">
		            		<label><fmt:message key="citcorpore.comum.datainicio" /><span class="campoObrigatorio"></span> </label>
		            	</td>
		            <td>
		           		<div><input  id="dataInicioExecucao" type='text' name="dataInicioExecucao" maxlength="10"  class="Format[Date] Valid[Required,Date] Description[Data Início] datepicker"/></div>
		            </td>
		         </tr>
		         <tr>
		            <td class="campoEsquerda"><label ><fmt:message key="citcorpore.comum.datafim" /> <span class="campoObrigatorio"></span></label> </td>
		            <td>
		           		<input  id="dataFimExecucao"  type='text' name="dataFimExecucao" maxlength="10"	 class="Format[Date] Valid[Required,Date] Description[Data Fim] datepicker"/>
		            </td>
		         </tr>
		         <tr>
		            <td class="campoEsquerda"><label ><fmt:message key="citcorpore.comum.quantidade"/><span class="campoObrigatorio"></span></label></td>
		            <td>
		           		<input  id="quantidade" type='text' name="quantidade" maxlength="6" size="10" class="Format[Numero] Valid[Required] Description[Quantidade]"/>
		            </td>
		         </tr>
					<tr>
						 <td class="campoEsquerda" colspan="2">
							<button type='button' id="btnGravaRegistroExecucao" name='btnGravaRegistroExecucao'  style="margin-top: 5px; margin-left: 3px" class="light img_icon has_text" onclick="gravaRegistroExecucao()">
								<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
							 <span style="font-size: 12px !important;"><fmt:message key="botaoacaovisao.gravar_dados"/></span>
							</button>

							<button type='button' id="btnLimparDados" name='btnLimparDados' style="margin-top: 5px; margin-left: 3px" class="light img_icon has_text" onclick="limparDados()">
								<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
								<span style="font-size: 12px !important;"><fmt:message key="botaoacaovisao.limpar_dados"/></span>
							</button>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<!-- o popup estava desalinhado a esquerda, isto resolve-->
	<div id="POPUP_NOTIFICACAO_SERVICO_CONTRATO" title="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key='notificacao.notificacao' /> "  style="margin-left: 50px; text-align: left" >
		<iframe id="iframeNotificacaoServicoContrato" name="iframeNotificacaoServicoContrato" width="99%" height="99%"> </iframe>
	</div>
	<div class="modal hide fade in" id="modal_lookupContrato" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="contrato.contrato" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name='formPesquisaContrato' style="width: 540px">
							<cit:findField formName='formPesquisaContrato'
							lockupName='LOOKUP_CONTRATOS' id='LOOKUP_CONTRATOS'
							top='0' left='0' len='530' heigth='200' javascriptCode='true'
							htmlCode='true' />
						</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>
</body>

	<script type="text/javascript">
			var properties = { parametroPortal : "${parametroPortal}", dataAtual: "${dataAtual}", caminho : "${caminho}", retorno : "${retorno}",
				ctx : "${ctx}"  };
	</script>
	<script type="text/javascript" src="./js/informacoesContrato.js"></script>
</html>
