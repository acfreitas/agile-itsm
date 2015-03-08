<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<link rel="stylesheet" type="text/css" href="./css/controleRendimentoExecucao.css" />
<script type="text/javascript" src="./js/controleRendimentoExecucao.js"></script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="controle.titulo" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message
								key="controle.titulo" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message
								key="controle.apurarMes" />
					</a></li>
					<li><a href="#tabs-3" class="round_top"><fmt:message
								key="controle.relatorios" />
					</a></li>

				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/controleRendimentoExecucao/controleRendimentoExecucao.load'>
								<div class="columns clearfix">
									<input type='hidden' name='idGrupo' id='idGrupo' />
									<input type='hidden' name='idGrupoExecucao' id='idGrupoExecucao' />
									<input type='hidden' name='idGrupoRelatorio' id='idGrupoRelatorio' />
									<input type='hidden' name='idPessoa' id='idPessoa' />
									<input type='hidden' name='ano' id='ano' />
									<input type='hidden' name='mes' id='mes' />
									<input type='hidden' name='qtdSolicitacoes' id='qtdSolicitacoes' />
									<input type='hidden' name='qtdTotalPontos' id='qtdTotalPontos' />
									<input type='hidden' name='qtdPontosPositivos' id='qtdPontosPositivos' />
									<input type='hidden' name='qtdPontosNegativos' id='qtdPontosNegativos' />
									<input type='hidden' name='mediaRelativa' id='mediaRelativa' />
									<input type='hidden' name='variavelAuxiliarParaFecharMes' id='variavelAuxiliarParaFecharMes' />

									<div class="col_15">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="controle.pesquisa"/></label>
											<div  >
												<table>
													<tr>
														<td>
															<input type='text' name='dataInicio' id='dataInicio' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
														<td>
															<fmt:message key="citcorpore.comum.a" />
														</td>
														<td>
															<input type='text' name='dataFim' id='dataFim' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="controle.grupo" /></label>
											<div>
												<input type='text' name="nomeGrupoExecucao" id="addGrupoExecucao" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="controle.pessoa" /></label>
											<div>
												<select name="comboPessoa" id="comboPessoa"/></select>
											</div>
										</fieldset>
									</div>
									<br>
								</div>
								<br>
								<br>
								<button type='button' name='btnPesquisar' class="light"  onclick='filtrarExecucao();'>
											<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
											<span><fmt:message key="citcorpore.comum.pesquisar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<div class='col_100'>
										<fieldset>
										<legend><fmt:message key="controle.rendimentoGrupo" /></legend>
											<div class="col_100" id="divrendimentoGrupoExecucao" style="height: 200px;width: 65%; overflow: auto">
											<table class="tableLess" id="tblrendimentoGrupoExecucao" style="width: 65%">
												<thead>
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 5%;"><fmt:message key="controle.sla" /></th>
														<th style="width: 10%;"><fmt:message key="controle.qtdSolicitacoes" /></th>
														<th style="width: 10%;"><fmt:message key="controle.qtdPontos" /></th>
														<th style="width: 10%;"><fmt:message key="controle.valoresPositivos" /></th>
														<th style="width: 10%;"><fmt:message key="controle.valoresNegativos" /></th>
														<th style="width: 10%;"><fmt:message key="controle.media" /></th>
													</tr>
												</thead>
											</table>
											</div>
									</fieldset>
									</div>
									<div class='col_100' id='divResultadoGrupo' style='font-family: Arial; font-weight: bold'>
									</div>
									<br>
									<br>
								<div class='col_100' id="divTotalRendimentoPessoa">
										<fieldset>
										<legend><fmt:message key="controle.rendimentoPessoa" /></legend>
											<div class="col_100" id="divrendimentoPessoaExecucao" style="height: 200px;width: 65%; overflow: auto">
											<table class="tableLess" id="tblrendimentoPessoaExecucao" style="width: 65%">
												<thead>
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 5%;"><fmt:message key="citcorpore.comum.nome" /></th>
														<th style="width: 10%;"><fmt:message key="controle.qtdPontos" /></th>
														<th style="width: 10%;"><fmt:message key="controle.aprovacao" /></th>
													</tr>
												</thead>
											</table>
											</div>
									</fieldset>
								</div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="controle.ano" /></label>
											<div>
												<select name="comboAno" id="comboAno"/></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="controle.mes" /></label>
											<div>
												<select name="comboMes" id="comboMes"/></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="controle.grupo" /></label>
											<div>
												<input type='text' name="nomeGrupo" id="addGrupo" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>

						<div class="block">
							<button type='button' name='btnPesquisar' class="light"  onclick='filtrar();'>
											<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
											<span><fmt:message key="citcorpore.comum.pesquisar" /></span>
							</button>
						</div>
						<div class='col_100'>
										<fieldset>
										<legend><fmt:message key="controle.rendimentoGrupo" /></legend>
											<div class="col_100" id="divrendimentoGrupo" style="height: 200px;width: 65%; overflow: auto">
											<table class="tableLess" id="tblrendimentoGrupo" style="width: 65%">
												<thead>
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 5%;"><fmt:message key="controle.sla" /></th>
														<th style="width: 10%;"><fmt:message key="controle.qtdSolicitacoes" /></th>
														<th style="width: 10%;"><fmt:message key="controle.qtdPontos" /></th>
														<th style="width: 10%;"><fmt:message key="controle.valoresPositivos" /></th>
														<th style="width: 10%;"><fmt:message key="controle.valoresNegativos" /></th>
														<th style="width: 10%;"><fmt:message key="controle.media" /></th>
													</tr>
												</thead>
											</table>
											</div>
									</fieldset>
						</div>
						<div class='col_100' id="divTotalRendimentoPessoa">
										<fieldset>
										<legend><fmt:message key="controle.rendimentoPessoa" /></legend>
											<div class="col_100" id="divrendimentoPessoa" style="height: 200px;width: 65%; overflow: auto">
											<table class="tableLess" id="tblrendimentoPessoa" style="width: 65%">
												<thead>
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 5%;"><fmt:message key="citcorpore.comum.nome" /></th>
														<th style="width: 10%;"><fmt:message key="controle.qtdPontos" /></th>
														<th style="width: 10%;"><fmt:message key="controle.aprovacao" /></th>
													</tr>
												</thead>
											</table>
											</div>
									</fieldset>
						</div>
						<div class="block">
							<button type='button' name='btnFecharMes' class="light"  onclick='fecharMes();'>
											<img src="${ctx}/template_new/images/icons/small/grey/note_book.png">
											<span><fmt:message key="controle.fecharMes" /></span>
							</button>
						</div>
						</div>
					</div>
					<div id="tabs-3" class="block">
						<div class="section">
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="controle.ano" /></label>
											<div>
												<select name="comboAnoRelatorio" id="comboAnoRelatorio"/></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="controle.mes" /></label>
											<div>
												<select name="comboMesRelatorio" id="comboMesRelatorio"/></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="controle.grupo" /></label>
											<div>
												<input type='text' name="nomeGrupoRelatorio" id="addGrupoRelatorio" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
						<div class="block">&nbsp;<h3 ><fmt:message key="controle.relatorios" /></h3></div>
						<div class="col_25">
							<fieldset>
								<label><fmt:message key="controle.melhorFuncionario" /></label>
								<button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioFuncionarioMaisEficiente()' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
											<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
								</button >
							</fieldset>
						</div>
						<div class="col_25">
							<fieldset>
								<label><fmt:message key="controle.piorFuncionario" /></label>
								<button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioFuncionarioMenosEficiente()' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
											<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
								</button >
							</fieldset>
						</div>
						<div class="col_25">
							<fieldset>
								<label><fmt:message key="controle.melhoresFuncionarios" /></label>
								<button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioMelhoresFuncionarios()' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
											<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
								</button >
							</fieldset>
						</div>
						<div class="block"></div>
						<div class="col_25">
							<fieldset>
								<label><fmt:message key="controle.rendimentoGrupo" /></label>
								<button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioPorGrupo()' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
											<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
								</button >
							</fieldset>
						</div>
						<div class="col_25">
							<fieldset>
								<label><fmt:message key="controle.rendimentoPessoa" /></label>
								<button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioPorPessoa()' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
											<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
								</button >
							</fieldset>
						</div>
						<div class="col_25">
							<fieldset>
								<label><fmt:message key="controle.mediaAtrasoEquipe" /></label>
								<button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioMediaAtraso()' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
											<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
								</button >
							</fieldset>
						</div>
						</div>
						</div>

					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<div id="POPUP_GRUPO_EXECUCAO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
			<div class="box grid_16 tabs" style="width: 570px;">
				<div class="toggle_container">
						<div class="section">
							<form name='formPesquisaGrupo1' style="width: 540px">
								<cit:findField formName='formPesquisaGrupo1'
									lockupName='LOOKUP_GRUPO_RENDIMENTO_EXECUCAO' id='LOOKUP_GRUPO_RENDIMENTO_EXECUCAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
				</div>
			</div>
		</div>
		<div id="POPUP_GRUPO_RELATORIO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
			<div class="box grid_16 tabs" style="width: 570px;">
				<div class="toggle_container">
						<div class="section">
							<form name='formPesquisaGrupo3' style="width: 540px">
								<cit:findField formName='formPesquisaGrupo3'
									lockupName='LOOKUP_GRUPO_RELATORIO' id='LOOKUP_GRUPO_RELATORIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
				</div>
			</div>
		</div>
		<div id="POPUP_GRUPO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
			<div class="box grid_16 tabs" style="width: 570px;">
				<div class="toggle_container">
						<div class="section">
							<form name='formPesquisaGrupo2' style="width: 540px">
								<cit:findField formName='formPesquisaGrupo2'
									lockupName='LOOKUP_GRUPO_RENDIMENTO' id='LOOKUP_GRUPO_RENDIMENTO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
				</div>
			</div>
		</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>


