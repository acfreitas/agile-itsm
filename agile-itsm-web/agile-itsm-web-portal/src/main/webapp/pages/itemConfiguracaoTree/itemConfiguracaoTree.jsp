<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO"%>
<html>
<head>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setHeader("Content-Language","lang");

	String iframe = "";
	iframe = request.getParameter("iframe"); %> <link rel="stylesheet" type="text/css" href="${ctx}/css/IC.css">

<link href="${ctx}/pages/itemConfiguracaoTree/css/itemConfiguracaoTree.css" rel="stylesheet" />
<script  charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
<script type="text/javascript" src="../../cit/objects/CaracteristicaDTO.js"></script>
<script type="text/javascript" src="../../cit/objects/HistoricoItemConfiguracaoDTO.js"></script>
</head>

	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
		title=""
		style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
	</cit:janelaAguarde>

	<body id="bodyInf">

		<div class="tabs">
			<ul class="tab_header clearfix">
				<li><a href="#tabs-1" onclick="showRelacionamentos()"><fmt:message	key="itemConfiguracao.cadastro"  /></a></li>
				<li><a href="#tabs-2" onclick="verificaImpactos()" class="round_top"><fmt:message key="itemConfiguracaoTree.impactos" /></a></li>
				<li><a href="#tabs-3" class="round_top"><fmt:message key="inventario.invetario" /></a></li>
				<li><a href="#tabs-4" onclick="verificaHistoricoAlteracao()" class="round_top" ><fmt:message key="citcorpore.comum.auditoria"/></a></li>

			</ul>
			<!-- <a href="#" class="toggle">&nbsp;</a> -->
			<div class="toggle_container">
				<div id="tabs-1" class="block">
					<div class="section">
		<form name='form'  id='form' action='${ctx}/pages/itemConfiguracaoTree/itemConfiguracaoTree'>
			<input type='hidden' id='idItemConfiguracao' name='idItemConfiguracao'/>
			<input type='hidden' id="idTipoItemConfiguracao" name='idTipoItemConfiguracao'/>
			<input type='hidden' id="idItemConfiguracaoPai" name='idItemConfiguracaoPai'/>
			<input type='hidden' id="idGrupoItemConfiguracao" name='idGrupoItemConfiguracao'/>
			<input type='hidden' name='dataInicio'/>
			<input type='hidden' name='dataFim'/>
			<input type='hidden' id="caracteristicasSerializadas" name='caracteristicasSerializadas'/>
			<input type='hidden' id='idIncidente' name='idIncidente'/>
			<input type='hidden' id='idProblema' name='idProblema'/>
			<input type='hidden' id='idMudanca' name='idMudanca'/>
			<input type='hidden' id='idProprietario' name='idProprietario'/>
			<input type='hidden' id='idMidiaSoftware' name='idMidiaSoftware'/>
			<input type='hidden' id='idLiberacao' name='idLiberacao'/>
			<input type='hidden' id='idResponsavel' name='idResponsavel'/>
			<input type='hidden' id='idGrupoResponsavel' name='idGrupoResponsavel'/>
			<input type='hidden' id="dataInicioHistorico" name="dataInicioHistorico" />
			<input type='hidden' id="dataFimHistorico" name="dataFimHistorico" />

			<!-- (flag) Elemento que  oculta o janela aguarde quando submetido o form -->
			<input type='hidden' id='ocultaJanelaAguardeParent' name='ocultaJanelaAguardeParent'/>
			<!-- Não é necessário pois a o uso do janela aguarde é apenas no frame -->
			<!-- <input type='hidden' id='ocultaJanelaAguarde' name='ocultaJanelaAguarde'/> -->

			<div id="principalInf" style="display: none;"></div>
				<div id="itemConfiguracaoCorpo">
				 	<table>
						<tr>
							<td>
								<h3 id="titleITem"></h3>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					<!--
					* Alterado por luiz.borges dia 27/11/2013 às 10:30 hrs
					* Motivo: Colocar o botão no layout padrão.
					 -->
					<div id="itemPai" style="line-height: 20px;display: none;">
						<button type='button' name='addItemConfiguracaoPai' id="addItemConfiguracaoPai" class="light"  style="margin: 10px !important; text-align: right;float: left;" >
						<span>
							<fmt:message key="itemConfiguracaoTree.mudarItemRelacionado"/>
						</span>
						</button>
						<div style="padding: 14px;">
							<span>
								<h2 class="padd10 lFloat" id="nomeItemConfiguracaoPai"></h2>
							</span>
						</div>
					</div>
					<!-- Fim da alteração luiz.borges -->
				</div>
				<div class="columns clearfix">

				    <div class="col_66">
						<fieldset>
							<label><fmt:message key="contrato.contrato" /></label>
								<div>
								   <select id="idContrato" name="idContrato" class="Description[<fmt:message key="contrato.contrato"/>]"></select>
								</div>
						</fieldset>
					</div>

					<div class="col_50">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.identificacao"/></label>
								<div>
								  	<input type='text' name="identificacao" class="Valid[Required] Description[<fmt:message key="citcorpore.comum.identificacao"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome"/></label>
								<div>
								  	<input type='text' name="nome" class="Valid[Required] Description[<fmt:message key="citcorpore.comum.nome"/>]" maxlength="70" size="40" onfocus="copiaIdentificacaoParaNome()"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label><fmt:message key="itemConfiguracaoTree.familia"/></label>
								<div>
								  	<input type='text' name="familia" class="Description[<fmt:message key="itemConfiguracaoTree.familia"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label><fmt:message key="itemConfiguracaoTree.classe"/></label>
								<div>
								  	<input type='text' name="classe" class="Description[<fmt:message key="itemConfiguracaoTree.classe"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.localidade"/></label>
								<div>
								  	<input type='text' name="localidade" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.localidade"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>

					<div class="col_20">
						<fieldset>
							<label><fmt:message key="itemConfiguracaoTree.dataExpiracao"/></label>
								<div>
								  	<input type='text' name="dataExpiracao" class="Description[<fmt:message key="itemConfiguracaoTree.dataExpiracao"/>] Format[Date] Valid[Date] datepicker" maxlength="30" size="30"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;"><fmt:message key="colaborador.colaborador"/></label>
							<div>
								<input id="nomeUsuario" type='text' readonly="readonly" name="nomeUsuario" onfocus='abrePopupUsuario();' style="width: 70% !important;" maxlength="80" class="Description[<fmt:message key="colaborador.colaborador"/>]" />
								<img onclick="abrePopupUsuario()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
								<!-- /**
								* Botão de limpar Lookup
								* @autor flavio.santana
								* 25/10/2013 10:50
								*/ -->
								<img border="0" title="<fmt:message key="botaoacaovisao.limpar_dados"/>" onclick="limparColaborador()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label><fmt:message key="itemConfiguracaoTree.versao"/></label>
								<div>	<input type='text' name="versao" class="Description[<fmt:message key="itemConfiguracaoTree.versao"/>]" maxlength="30" size="30"/>	</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.nSerie"/></label>
								<div>	<input type='text' name="numeroSerie" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.nSerie"/>]" maxlength="30" size="30"/>	</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.criticidadeDoServico"/></label>
								<div>	<select name="criticidade" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.criticidadeDoServico"/>]"></select>	</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.urgencia"/></label>
							<div>
								<select id="urgencia" name="urgencia" class="Description[<fmt:message key="itemConfiguracaoTree.urgencia"/>]">
								</select>
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.impacto"/></label>
							<div>
								<select id="impacto" name="impacto" class="Description[<fmt:message key="itemConfiguracaoTree.impacto"/>]">
								</select>
							</div>
						</fieldset>
					</div>
				</div>
				<div class="columns clearfix">
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.status"/></label>
								<div>
								  	<select  name="status" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.status"/>]"></select>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="tipoItemConfiguracao.tipoItemConfiguracao"/></label>
							<div>
							  	<input class="Valid[Required] Description[<fmt:message key="tipoItemConfiguracao.tipoItemConfiguracao"/>]" onclick="consultarTipoItemConfiguracao()" readonly="readonly" style="width: 70% !important;" type='text' name="nomeTipoItemConfiguracao" maxlength="70" size="70"  />
								<img onclick="consultarTipoItemConfiguracao()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
								<!-- /**
								* Botão de limpar Lookup
								* @autor flavio.santana
								* 25/10/2013 10:50
								*/ -->
								<img border="0" title="<fmt:message key="botaoacaovisao.limpar_dados"/>" onclick="limparTipoItemConfiguracao()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;"><fmt:message key="itemConfiguracaoTree.midia"/></label>
							<div>
								<input id="nomeMidia" type='text' readonly="readonly" name="nomeMidia" onfocus='abrePopupMidia();' style="width: 70% !important;" maxlength="80" class="Description[<fmt:message key="itemConfiguracaoTree.midia"/>]" />
								<img onclick="abrePopupMidia()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
								<img border="0" title="<fmt:message key="botaoacaovisao.limpar_dados"/>" onclick="limparMidiaSoftware()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;"><fmt:message key="itemConfiguracaoTree.incidenteRequisicao"/></label>
							<div>
								<input id="numeroIncidente" type='text' readonly="readonly" name="numeroIncidente" onfocus='abrePopupIncidente();' style="width: 70% !important;" maxlength="80" class="Description[<fmt:message key="itemConfiguracaoTree.incidenteRequisicao"/>]" />
								<img onclick="abrePopupIncidente()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
								<img border="0" title="<fmt:message key="botaoacaovisao.limpar_dados"/>" onclick="limparSolicitacaoServico()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;"><fmt:message key="itemConfiguracaoTree.problema"/></label>
							<div>
								<input id="numeroProblema" type='text' readonly="readonly" name="numeroProblema" onfocus='abrePopupProblema();' style="width: 70% !important;" maxlength="80" class="Description[<fmt:message key="itemConfiguracaoTree.problema"/>]" />
								<img onclick="abrePopupProblema()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
								<img border="0" title="<fmt:message key="botaoacaovisao.limpar_dados"/>" onclick="limparProblema()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label id="Mudancarequired" style="cursor: pointer;" ><fmt:message key="itemConfiguracaoTree.mudanca"/></label>
							<div>
								<input id="numeroMudanca" type='text' readonly="readonly" name="numeroMudanca" onfocus='abrePopupMudanca();' style="width: 70% !important;" maxlength="80" class="Description[<fmt:message key="itemConfiguracaoTree.mudanca"/>]" />
								<img onclick="abrePopupMudanca()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
								<img border="0" title="<fmt:message key="botaoacaovisao.limpar_dados"/>" onclick="limparRequisicaoMudanca()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label id="liberacao" style="cursor: pointer;" ><fmt:message key="liberacao"/></label>
							<div>
								<input id="tituloLiberacao" type='text' readonly="readonly" name="tituloLiberacao" onfocus='abrePopupLiberacao();' style="width: 70% !important;" maxlength="80" class="Description[<fmt:message key="liberacao"/>]" />
								<img onclick="abrePopupLiberacao()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
								<img border="0" title="<fmt:message key="botaoacaovisao.limpar_dados"/>" onclick="limparRequisicaoLiberacao()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					<div class="col_20" id="divGrupoItemConfiguracao">
						<fieldset>
							<label><fmt:message key="itemConfiguracaoTree.grupo"/></label>
								<div>
								  	<input onclick="abrePopupGrupoItemConfiguracao()" readonly="readonly" style="width: 70% !important;" type='text' name="nomeGrupoItemConfiguracao" maxlength="70" size="70"  />
									<img onclick="abrePopupGrupoItemConfiguracao()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
									<!-- /**
								* Botão de limpar Lookup
								* @autor flavio.santana
								* 25/10/2013 10:50
								*/ -->
									<img border="0" title="<fmt:message key="botaoacaovisao.limpar_dados"/>" onclick="limparGrupoItemConfiguracao()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label><fmt:message key="itemConfiguracaoTree.ativofixo"/></label>
								<div>
								  	<input type='text' name="ativoFixo" class="Description[<fmt:message key="itemConfiguracaoTree.ativofixo"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.tipoResponsavel"/></label>
								<div>
								  	<select  name="tipoResponsavel" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.tipoResponsavel"/>]" onchange="limparResponsavel()" size="1.5px"></select>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio" style="cursor: pointer;"><fmt:message key="citcorpore.comum.responsavel"/></label>
							<div>
								<input id="nomeResponsavel" type='text' readonly="readonly" name="nomeResponsavel" onfocus='abrePopupResponsavel();' style="width: 70% !important;" maxlength="80" class="Valid[Required] Description[<fmt:message key="citcorpore.comum.responsavel"/>]" />
								<img onclick="abrePopupResponsavel()" style=" vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
								<!-- /**
								* Botão de limpar Lookup
								* @autor flavio.santana
								* 25/10/2013 10:50
								*/ -->
								<img border="0" title="<fmt:message key="botaoacaovisao.limpar_dados"/>" onclick="limparResponsavel()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					<div class="col_100">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="itemConfiguracaoTree.informacoesAdicionais"/></label>
								<div>
									<textarea type='text' name="informacoesAdicionais" class="Valid[Required] Description[<fmt:message key="itemConfiguracaoTree.informacoesAdicionais"/>]" rows="8" maxlength="4000" size="30"></textarea>
								</div>
						</fieldset>
					</div>
				</div>
				<br>
				<div id="gridCaracteristica" class="columns clearfix" style="display: none;">
					<h2><fmt:message key="itemConfiguracaoTree.caracteristicas"/></h2>
					<table class="table table-bordered table-striped" id="tabelaCaracteristica" style="width: 100%">
						<tr>
							<th style="width: 20%;"><fmt:message key="citcorpore.comum.caracteristica"/></th>
							<th style="width: 30%;"><fmt:message key="citcorpore.comum.descricao"/></th>
							<th style="width: 20%;"><fmt:message key="citcorpore.comum.valor"/></th>
						</tr>
					</table>
				</div>
				<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
					<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
					<span><fmt:message key="citcorpore.comum.gravar"/></span>
				</button>
				<button type="button" name="btnLimpar" class="light img_icon has_text" onclick="limpar();" id="btnLimpar">
					<img src="/citsmart/template_new/images/icons/small/grey/clear.png">
					<span><fmt:message key="botaoacaovisao.limpar_dados"/></span>
				</button>

		</form>
				<div class="formRelacionamentos" id="relacionamentos">
			<div id="tabs">
				<ul>
					<li><a href="#relacionaIcs"><fmt:message key="itemConfiguracaoTree.itensConfiguracao"/></a></li>
					<li><a href="#relacionarIncidentes"><fmt:message key="itemConfiguracaoTree.incidentesRequisicoes"/></a></li>
					<li><a href="#relacionarProblemas"><fmt:message key="itemConfiguracaoTree.problemas"/></a></li>
					<li><a href="#relacionarMudancas"><fmt:message key="itemConfiguracaoTree.mudancas"/></a></li>
					<li><a href="#relacionarLiberacoes"><fmt:message key="itemConfiguracaoTree.liberacoes"/></a></li>
					<li><a href="#relacionarBC"><fmt:message key="itemConfiguracaoTree.baseConhecimento"/></a></li>
				</ul>
				<div id="relacionaIcs">
					<form name='formBaseline' id='formBaseline' action='${ctx}/pages/historicoItemConfiguracao/historicoItemConfiguracao'>
						<input type='hidden' id='idHistoricoIC' name='idHistoricoIC'/>
						<input type='hidden' id='idItemConfiguracao' name='idItemConfiguracao'/>
						<input type='hidden' id="baselinesSerializadas" name='baselinesSerializadas'/>
						<div id="contentBaseline">

						</div>
						<fieldset>
							<button onclick="gravarBaseline();" class="light img_icon has_text" name="btnGravarBaseLine" type="button" id="btnGravarBaseLine">
								<img src="/citsmart/template_new/images/icons/small/grey/pencil.png">
								<span><fmt:message key="itemConfiguracaoTree.gravarBaselines"/></span>
							</button>
						</fieldset>
					</form>
				</div>
				<div id="relacionarIncidentes">

				</div>
				<div id="relacionarProblemas">

				</div>
				<div id="relacionarMudancas">

				</div>
				<div id="relacionarLiberacoes">

				</div>
				<div id="relacionarBC">

				</div>
			</div>
		</div>
		</div>
		</div>
			<div id="tabs-2" class="block" >
				<div class="section">
					<form><div id='divImpactos'  style='width: 600px'></div></form>
				</div>
			</div>

			<div id="tabs-3" class="block" >
				<div class="section" id="divInventario">
					<iframe  width="100%" height="90%" src="${ctx}/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=<%=request.getParameter("idItemConfiguracao")%>&mostraItensVinculados=false"></iframe>
				</div>
			</div>

			<div id="tabs-4" class="block" >
				<div class="section">
					<form id="formHistoricoAlteracao" name="formHistoricoAlteracao">
						<div id='divHistoricoAlteracao'  style='width: 1000px'>
							<h2><fmt:message key="itemConfiguracaoTree.auditoriaItemConfiguracao"/></h2>
							<div id="gridPesquisaHistorio" class="columns clearfix" style="display: block;">
									<div class="col_33">
										<fieldset>
											<label style="cursor: pointer;"><fmt:message key="citcorpore.comum.datainicio"/></label>
											<div>
												<input type='text' id="dataInicioH" name="dataInicioH" class="Description[<fmt:message key="citcorpore.comum.datainicio"/>] Format[Date] Valid[Date] datepicker" maxlength="10" size="30"/>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label style="cursor: pointer;"><fmt:message key="citcorpore.comum.datafim"/></label>
											<div>
												<input type='text' id="dataFimH" name="dataFimH" class="Description[<fmt:message key="citcorpore.comum.datainicio"/>] Format[Date] Valid[Date] datepicker" maxlength="10" size="30"/>
											</div>
										</fieldset>
									</div>
									<div class="col_100">
										<fieldset>
											<label style="cursor: pointer;"></label>
												<button type='button' name='btnHistoricoItemConfiguracao' class="light"  onclick='pesquisiarHistoricoItemConfiguracao();'>
														<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
														<span><fmt:message key="citcorpore.comum.pesquisar"/></span>
												</button>
												<button type="button" name="btnLimpar" class="light img_icon has_text" onclick="limparDadosAuditoria();" id="btnLimpar">
													<img src="/citsmart/template_new/images/icons/small/grey/clear.png">
													<span><fmt:message key="botaoacaovisao.limpar_dados"/></span>
												</button>
										</fieldset>
								</div>
							</div>
							<div id="historicoAlteracaoItemConfiguracao">

							</div>

						</div>
					</form>
				</div>
			</div>

		</div>
	</div>

	<div id="popupCadastroRapido">
		<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="99%"></iframe>
	</div>
		<div id="POPUP_GRUPOITEMCONFIGURACAO" class='popup' title="<fmt:message key="itemConfiguracaoTree.consultaGrupoItemConfiguracao"/>">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style="padding: 33px;">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="itemConfiguracaoTree.grupoItemConfiguracao"/>
									<img src="${ctx}/imagens/add.png" onclick="abrePopupNovoGrupoItemConfiguracao()">
								</label>
							</div>
							<form name='formPesquisaGrupoItemConfiguracao'>
								<cit:findField formName='formPesquisaGrupoItemConfiguracao'
								lockupName='LOOKUP_GRUPOITEMCONFIGURACAO'
								id='LOOKUP_GRUPOITEMCONFIGURACAO' top='0' left='0' len='550' heigth='400'
								javascriptCode='true'
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="POPUP_ITEMCONFIGPAI" class='popup' title="<fmt:message key="citcorpore.comum.identificacao" />">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaItemConfiguracaoPai' style="width: 540px">
								<cit:findField formName='formPesquisaItemConfiguracaoPai'
		 							lockupName='LOOKUP_PESQUISAITEMCONFIGURACAO' id='LOOKUP_PESQUISAITEMCONFIGURACAO' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="POPUP_TIPOITEMCONFIGURACAO" class='popup' title="<fmt:message key="itemConfiguracaoTree.consultaTipoItemConfiguracao"/>" style="display: none;">
			<div class="box grid_16 tabs">
					<div class="toggle_container">
						<div id="tabs-2" class="block">
							<div class="section">
								<div  align="right">
									<label  style="cursor: pointer; ">
										<fmt:message key="itemConfiguracaoTree.tipoItemConfiguracao"/>
										<img src="${ctx}/imagens/add.png" onclick="abrePopupNovoTipoItemConfiguracao()">
									</label>
								</div>
								<form name='formPesquisaTipoItemConfiguracao'>
									<cit:findField formName='formPesquisaTipoItemConfiguracao'
									lockupName='LOOKUP_TIPOITEMCONFIGURACAO'
									id='LOOKUP_TIPOITEMCONFIGURACAO' top='0' left='0' len='550' heigth='600'
									javascriptCode='true'
									htmlCode='true' />
								</form>
							</div>
						</div>
					</div>
				</div>

		</div>

		<div id="POPUP_MIDIASOFTWARE" class='popup' title='<fmt:message key="itemConfiguracaoTree.pesquisaMidia"/>' style="display: none;">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="itemConfiguracaoTree.midia"/>
									<img src="${ctx}/imagens/add.png" onclick="abrePopupNovaMidia()">
								</label>
							</div>
							<form name='formPesquisaMidiaSoftware'>
								<cit:findField formName='formPesquisaMidiaSoftware'
								lockupName='LOOKUP_MIDIASOFTWARE'
								id='LOOKUP_MIDIASOFTWARE' top='0' left='0' len='550' heigth='400'
								javascriptCode='true'
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="POPUP_EMPREGADO" class='popup' title='<fmt:message key="itemConfiguracaoTree.pesquisaEmpregado"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="colaborador.colaborador"/>
									<img src="${ctx}/imagens/add.png" onclick="abrePopupNovoUsuario()">
								</label>
							</div>
							<form name='formPesquisaEmp' style="width: 540px">
								<cit:findField formName='formPesquisaEmp'
									lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="POPUP_RESPONSAVEL" class='popup' title='<fmt:message key="citcorpore.comum.responsavel"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="colaborador.colaborador"/>
									<img src="${ctx}/imagens/add.png" onclick="abrePopupNovoUsuario()">
								</label>
							</div>
							<form name='formPesquisaRes' style="width: 540px">
								<cit:findField formName='formPesquisaRes'
									lockupName='LOOKUP_RESPONSAVEL_ITEM' id='LOOKUP_RESPONSAVEL' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="POPUP_RESPONSAVEL_GRUPO" class='popup' title='<fmt:message key="controle.grupo"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="controle.grupo"/>
									<img src="${ctx}/imagens/add.png" onclick="abrePopupNovoGrupo()">
								</label>
							</div>
							<form name='formPesquisaResGrupo' style="width: 540px">
								<cit:findField formName='formPesquisaResGrupo'
									lockupName='LOOKUP_GRUPO' id='LOOKUP_RESPONSAVEL_GRUPO' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="POPUP_INCIDENTE" class='popup' title="<fmt:message key="solicitacaoServico.solicitacao" />">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style='overflow: auto'>
							<div  align="right">
								<label  style="cursor: pointer; ">
										<fmt:message key="solicitacaoServico.solicitacao" />
										<!--
										* Adicionado titulo a popup
										* @autor flavio.santana
										* 25/10/2013 10:50
										-->
										<img id='botaoSolicitante' src="${ctx}/imagens/add.png" onclick="abrePopupSolicitacaoServico();">
								</label>
							</div>
							<form name='formPesquisaSolicitacaoServico' style="width: 540px">
								<cit:findField formName='formPesquisaSolicitacaoServico'
									lockupName='LOOKUP_SOLICITACAOSERVICO' id='LOOKUP_SOLICITACAOSERVICO' top='0'
									left='0' len='550' heigth='280' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="POPUP_PROBLEMA" class='popup' title='<fmt:message key="itemConfiguracaoTree.pesquisaProblema"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<!--
									* Adicionado titulo a popup
									* @autor flavio.santana
									* 25/10/2013 10:50
									-->
									<fmt:message key="itemConfiguracaoTree.problemas"/>
									<img src="${ctx}/imagens/add.png"
									onclick="abrePopupNovoProblema()">
								</label>
							</div>
							<form name='formPesquisaProblema' style="width: 540px">
								<cit:findField formName='formPesquisaProblema'
									lockupName='LOOKUP_PROBLEMA' id='LOOKUP_PROBLEMA' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_MUDANCA" class='popup' title='<fmt:message key="itemConfiguracaoTree.pesquisaMudanca"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="itemConfiguracaoTree.mudancas"/>
									<!--
									* Adicionado titulo a popup
									* @autor flavio.santana
									* 25/10/2013 10:50
									-->
									<img src="${ctx}/imagens/add.png"
										onclick="abrePopupNovaMudanca()">
								</label>
							</div>
							<form name='formPesquisaMudanca' style="width: 540px">
								<cit:findField formName='formPesquisaMudanca'
									lockupName='LOOKUP_MUDANCA' id='LOOKUP_MUDANCA' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_LIBERACAO" class='popup' title='<fmt:message key="gerenciarequisicao.pesquisaliberacao"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<fmt:message key="liberacao"/>
									<!--
									* Adicionado titulo a popup
									* @autor flavio.santana
									* 25/10/2013 10:50
									-->
									<img src="${ctx}/imagens/add.png"
										onclick="abrePopupNovaLiberacao()">
								</label>
							</div>
							<form name='formPesquisaLiberacao' style="width: 540px">
								<cit:findField formName='formPesquisaLiberacao'
									lockupName='LOOKUP_LIBERACAO' id='LOOKUP_LIBERACAO' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="POPUP_IMPACTO" class='popup' title="<fmt:message key="itemConfiguracaoTree.impactos"/>">
			<div id='divImpactos'  style='width: 100%'>
			</div>
		</div>

		<div id="POPUP_EDITARPROBLEMA" class='popup' style="overflow: hidden;" title="<fmt:message key="problema.problema"/>">
		<iframe id='iframeEditarProblema' src='about:blank' width="100%" height="99%" style='width: 100%; height: 100%; border:none;'></iframe>
	</div>
	<!--
		Adicionado o script para carregamento no final da página
		@autor flavio.santana
		28/10/2013
	 -->
	<script type="text/javascript" src="js/itemConfiguracaoTree.js"></script>
	<script type="text/javascript">
		function reload(idItem) {
			<%
		    //se for chamado por iframe deixa apenas a parte de cadastro da página
		    if (iframe == null) {
			%>
				parent.reloadItem(idItem);
			<%
		    }
			%>
		}
		function ocultaGrid() {
			<%
		    //se for chamado por iframe deixa apenas a parte de cadastro da página
		    if (iframe == null) {
			%>
				document.getElementById('gridCaracteristica').style.display = 'none';
			<%
		    }
			%>
		}

		function copiaIdentificacaoParaNome() {
			//alert(document.getElementById('identificacao').value);
			document.getElementById('nome').value = document.getElementById('identificacao').value
		}
		function changeSelectedItem(item){
			document.getElementById('tipoResponsavel').value = item;
		}
	</script>

	</body>
</html>
