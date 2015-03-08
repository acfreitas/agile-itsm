<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>

<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript"
	src="${ctx}/cit/objects/EscalonamentoDTO.js"></script>

<link rel="stylesheet" type="text/css" href="./css/regraEscalonamento.css" />
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<script type="text/javascript" src="./js/regraEscalonamento.js"></script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="regraEscalonamento.regraEscalonamentoTitulo" />
				</h2>
			</div>

			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message
								key="regraEscalonamento.cadastro" /></a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message
								key="regraEscalonamento.pesquisa" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/regraEscalonamento/regraEscalonamento'>
								<div class="columns clearfix">
									<input type='hidden' id='idRegraEscalonamento'
										name='idRegraEscalonamento' /> <input type="hidden"
										id='idServico' name='idServico'> <input type="hidden"
										id='idGrupo' name='idGrupo'> <input type="hidden"
										id='idSolicitante' name='idSolicitante'>
									<div
										style='padding-top: 1px; padding-bottom: 15px; padding-right: 15px; padding-left: 15px;'>
										<fieldset>
											<strong><legend>
													<fmt:message key="citcorpore.comum.filtros" />
												</legend></strong>
											<div class="col_15">
												<label class="tooltip_bottom campoObrigatorio"
													style="cursor: pointer; width: 150px !important;"
													title="<fmt:message key="regraEscalonamento.tipoGerenciamentotool"/>">
													<fmt:message key="regraEscalonamento.tipoGerenciamento" />
												</label>
												<div>
													<select name='idTipoGerenciamento' id='idTipoGerenciamento'

														class="Valid[Required] Description[<fmt:message key='regraEscalonamento.tipoGerenciamento'/>]"></select>
												</div>
											</div>

											<div class="col_25">
												<label class=""><fmt:message
														key="contrato.contrato" /></label>
												<div>
													<select name='idContrato' style='width: 250px;'
														class="Description[<fmt:message key='contrato.contrato' />]">
													</select>
												</div>
											</div>

											<div class="col_30">
												<label class=""><fmt:message key="servico.servico" /></label>
												<div>
													<table width="100%">
														<tr>
															<td style="width: 98%;padding-top: 0 !important;vertical-align: top !important;"><input type="text"
																onfocus='abrePopupServico();' id="servico"
																name="servico" /></td>
															<td style="width: 2%;"><img
																src='${ctx}/imagens/borracha.png'
																border='0' style="cursor: pointer"
																onclick='LimparCampoServico();' /></td>
														</tr>
													</table>
												</div>
											</div>

											<div class="col_20">
												<label><fmt:message
														key="tipoLiberacao.nomeGrupoExecutor" /></label>
												<div>
													<table width="100%">
														<tr>
															<td style="width: 98%;padding-top: 0 !important;vertical-align: top !important;"><input type="text"
																onfocus='abrePopupGrupo();' id="grupo" name="grupo" /></td>
															<td style="width: 2%;"><img
																src='${ctx}/imagens/borracha.png'
																border='0' style='cursor: pointer'
																onclick='LimparCampoGrupo();' /></td>
														</tr>
													</table>
												</div>
											</div>

											<div class="col_20">
												<label><fmt:message
														key="usuario.usuarioSolicitante" /></label>
												<div>
													<table width="100%">
														<tr>
															<td style='width: 98%;padding-top: 0 !important;vertical-align: top !important;'><input type="text"
																onfocus='abrePopupUsuario();' id="nomeSolicitante"
																name="nomeSolicitante" /></td>
															<td style='width: 2%;'><img
																src='${ctx}/imagens/borracha.png'
																border='0' style='cursor: pointer'
																onclick='LimparCampoNomeSolicitante();' /></td>
														</tr>
													</table>
												</div>
											</div>

											<div class="col_15">
												<label><fmt:message
														key="tipoDemandaServico.tipoSolicitacao" /></label>
												<div>
													<select name='idTipoDemandaServico'
														class="Description[<fmt:message key='tipoDemandaServico.tipoSolicitacao' />]"
														>
													</select>
												</div>
											</div>


												<div id="divUrgencia" class="col_10">
													<label class=""><fmt:message
															key="solicitacaoServico.urgencia" /></label>
													<div>
														<select name='urgencia' id='urgencia'></select>
													</div>
												</div>

												<div id="divImpacto" class="col_10">
													<label class=""><fmt:message
															key="solicitacaoServico.impacto" /></label>
													<div>
														<select name='impacto' id='impacto'></select>
													</div>
												</div>

										</fieldset>
									</div>
									<div
										style='padding-top: 15px; padding-bottom: 15px; padding-right: 15px; padding-left: 15px;'>
										<fieldset>
											<strong><legend>
													<fmt:message
														key="regraEscalonamento.regraClassificacaoNotificacao" />
												</legend></strong>
											<div class="col_15">
												<label> <fmt:message
														key="regraEscalonamento.classificacaoEvento" />
												</label>
												<div>
													<input type='text'
														value='<fmt:message key="solicitacaoservico.vencendo"/>'
														readonly ></input>
												</div>
											</div>
											<div class="col_20">
												<label class="campoObrigatorio"> <fmt:message
														key="regraEscalonamento.prazoRestanteExecucao" />
												</label>
												<div>
													<input type='text' name='tempoExecucao' id='tempoExecucao'
														value='' class="Format[Numero]"
														></input>
												</div>
											</div>

											<div class="col_20">
												<label> <fmt:message
														key="regraEscalonamento.intervaloNotificacao" />
												</label>
												<div>
													<select name='intervaloNotificacao'
														id='intervaloNotificacao' ></select>
												</div>
											</div>
											<div class="">
												<div class="checkboxRegra">
													<input type="checkbox" name='enviarEmail' id='enviarEmail'
														value="S">
													<fmt:message key="regraEscalonamento.enviarEmail" />
												</div>
											</div>
											<div class="">
												<div class="checkboxRegra">
													<input type="checkbox" name="criaProblema"
														id="criaProblema" value="S" />
													<fmt:message key="tipoDemandaServico.criarProblema" />
												</div>
											</div>
											<div class="col_20">
												<label> <fmt:message
														key="regraEscalonamento.prazoCriarProblema" />
												</label>
												<div>
													<select name='prazoCriarProblema' id='prazoCriarProblema'
														></select>
												</div>
											</div>
											<div class="col_20">
												<label> <fmt:message
														key="regraEscalonamento.campoDataEscalonamento" />
												</label>
												<div>
													<select name='tipoDataEscalonamento'
														id='tipoDataEscalonamento'
														style='width: 150px !important;'></select>
												</div>
											</div>
										</fieldset>
									</div>
									<div
										style='padding-top: 15px; padding-bottom: 15px; padding-right: 15px; padding-left: 15px;'>
										<fieldset
											style='padding-top: 5px; padding-bottom: 5px; padding-right: 5px; padding-left: 5px;'>
											<strong><legend>
													<fmt:message key="regraEscalonamento.regraEscalonamento" />
												</legend></strong>
											<div class="col_25">
												<label class="tooltip_bottom campoObrigatorio"
													style="cursor: pointer;"
													title="<fmt:message key="regraEscalonamento.tipoGerenciamentotool"/>">
													<fmt:message key="citcorpore.comum.grupoExecutor" />
												</label>
												<div>
													<select name='idGrupoAtual' id='idGrupoAtual'
														></select>
												</div>
											</div>
											<div class="col_15">
												<label class="campoObrigatorio"><fmt:message
														key="citcorpore.comum.prazoExecucao" /></label>
												<div>
													<select name='prazoExecucao' id='prazoExecucao'
														></select>
												</div>
											</div>
											<div class="col_10">
												<label> <fmt:message
														key="regraEscalonamento.prioridade" />
												</label>
												<div>
													<select name='idPrioridade' id='idPrioridade'
														></select>
												</div>
											</div>
											<div class="col_25">
												<div style="margin-top: 26px">
													<button type='button' name='btnAddGrupo' class="light"
														onclick='addGrupoExecutor();'>
														<img
															src="${ctx}/template_new/images/icons/small/grey/pencil.png">
														<span><fmt:message
																key="citcorpore.comum.adicionar" /></span>
													</button>
												</div>
											</div>

											<!-- hiddens tabela de grupos  -->
											<input type="hidden" name="grupos_serialize"
												id="grupos_serialize" />


												<div class="formBody">
													<table id="tblGrupoExecutor" name="tblGrupoExecutor"
														class="table  table-bordered">
														<tr>
															<th height="10px" width="3%"></th>
															<th height="10px" width="13%"><fmt:message
																	key="grupo.idgrupo" /></th>
															<th height="10px" width="39%"><fmt:message
																	key="citcorpore.comum.grupoExecutor" /></th>
															<th height="10px" width="15%"><fmt:message
																	key="citcorpore.comum.prazoExecucao" /></th>
															<th height="10px" width="15%"><fmt:message
																	key="regraEscalonamento.idPrioridade" /></th>
															<th height="10px" width="15%"><fmt:message
																	key="regraEscalonamento.prioridade" /></th>
														</tr>
													</table>
												</div>

										</fieldset>
									</div>
									<br>
								</div>
								<br> <br>
								<button type='button' name='btnGravar' class="light"
									onclick='salvar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='limpar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" /></span>
								</button>
								<button type='button' name='btnUpDate' class="light"
									onclick='excluir();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message key="citcorpore.comum.excluir" /></span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_REGRAESCALONAMENTO'
									id='LOOKUP_REGRAESCALONAMENTO' top='0' left='0' len='550'
									heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>

<div id="POPUP_SERVICO" title="<fmt:message key="servico.servico" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formServico' style="width: 540px">
						<cit:findField formName='formServico' lockupName='LOOKUP_SERVICO'
							id='LOOKUP_SERVICO' top='0' left='0' len='550' heigth='400'
							javascriptCode='true' htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_GRUPO" title="<fmt:message key="grupo.grupo" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formGrupo' style="width: 540px">
						<cit:findField formName='formGrupo' lockupName='LOOKUP_GRUPO'
							id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400'
							javascriptCode='true' htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_SOLICITANTE"
	title="<fmt:message key="citcorpore.comum.pesquisacolaborador" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaUsuario' style="width: 540px">
						<cit:findField formName='formPesquisaUsuario'
							lockupName='LOOKUP_SOLICITANTE' id='LOOKUP_SOLICITANTE' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

</html>

