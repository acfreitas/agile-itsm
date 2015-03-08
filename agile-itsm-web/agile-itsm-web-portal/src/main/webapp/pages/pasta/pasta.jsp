<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/include/header.jsp"%>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<link rel="stylesheet" type="text/css" href="./css/pasta.css" />

		<script type="text/javascript" src="./js/pasta.js"></script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
	<body>
		<script type="text/javascript" src="../../cit/objects/PerfilAcessoPastaDTO.js"></script>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="pasta.pasta"/></h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="pasta.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><fmt:message key="pasta.pesquisa"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/pasta/pasta'>

									<div class="columns clearfix">
										<input type='hidden' name='id'/>
										<input type='hidden' name='dataInicio'/>
										<input type='hidden' name='perfisSerializados'/>
										<input type='hidden' name='idNotificacao' />
										<input type='hidden' name='dataInicio' id="dataInicio" />
										<input type='hidden' name='dataFim' id="dataFim" />
										<input type="hidden" name="usuariosSerializados">
										<input type="hidden" name="gruposSerializados">
										<input type="hidden" id="titulo" name="titulo">
										<input type="hidden" id="tipoNotificacao" name="tipoNotificacao">

										<div class="columns clearfix">

											<div class="col_33">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="pasta.pasta"/></label>
														<div>
														  	<input type='text' name="nome" maxlength="70" size="70" class="Valid[Required] Description[Nome da Pasta]" />
														</div>
												</fieldset>
											</div>
											<div class="col_33">
												<fieldset>
													<label ><fmt:message key="pasta.superior"/></label>
													<div>
													  	<select id="comboPastaPai" name="idPastaPai" style="margin-bottom: 3px;" onchange="ativarHerdarPemissoes();validarHerancadePermissao();"></select>
													</div>
												</fieldset>
											</div>
											<div class="col_15">
												<fieldset style="height: 55px; text-align: center;	">
												  	<label ><fmt:message key="baseconhecimento.notificacoes"/></label>
												  	<div>
												  		<img style="cursor: pointer; " title=<fmt:message key="pasta.notificacoes" /> onclick="abrirPopupNotificacao()"  src="${ctx}/imagens/notificacao.png">
												  	</div>
												</fieldset>
											</div>
										</div>
										<br>
										<h3><fmt:message key="pasta.controleAcesso"/></h3>
										<div id="mainHerdarPermissao" class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<div id="divHerdarPermissao">
													  	<span>
													  		<input type="checkbox" id="herdarPermissao" name="herdaPermissoes" style="margin-right: 7px !important;" value="S" onclick="exibirOcultarGridPerfilAcesso()"/>
													  		<label style="font-weight: bold;"><fmt:message key="pasta.herdaPermissoes" /></label>
													  	</span>
													</div>
												</fieldset>
											</div>
										</div>

										<div id="gridPerfilAcesso" class="columns clearfix" style="display: none;">
											<table class="table" id="tabelaPerfilAcesso" style="width: 100%">
												<tr>
													<th style="width: 1%;"></th>
													<th style="width: 40%;"><fmt:message key="citcorpore.comum.perfilAcesso"/></th>
													<th style="width: 30%;" align="center"><fmt:message key="pasta.permissoes"/></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.aprovaBaseConhecimento"/></th>
												</tr>
											</table>
										</div>
									</div>

									<div id="POPUP_NOTIFICACAO" >
										<div class="columns clearfix">
											<div class="col_50">
												<fieldset >
													<label class="campoObrigatorio"><fmt:message key="notificacao.titulo" /></label>
													<div >
														<input type='text' id="tituloNotificacao" name="tituloNotificacao" maxlength="256" class="Valid[Required] Description[notificacao.titulo]" />
													</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset style="height: 55px">
													<label class="campoObrigatorio"><fmt:message key="notificacao.tipoNotificacao" /></label>
													<div>
														<select id="tipo" name="tipo"></select>
													</div>
												</fieldset>
											</div>
										</div>
										<div class="col_100">
											<div class="col_50">
												<fieldset>
													<label id="addUsuario" style="cursor: pointer;"><fmt:message key="citcorpore.comum.usuario" /><img
														src="${ctx}/imagens/adicionarUsuario.png"></label>
													<div  id="gridUsuario">
														<table class="table" id="tabelaUsuario"
															style="display: none;">
															<tr>
																<th style="width: 1%;"></th>
																<th style="width: 98%;"><fmt:message key="citcorpore.comum.usuario" /></th>
															</tr>
														</table>
													</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label id="addGrupo" style="cursor: pointer;"><fmt:message key="grupo.grupo" /><img
														src="${ctx}/imagens/add2.png"></label>
													<div  id="gridGrupo">
														<table class="table" id="tabelaGrupo"  style="display: none;">
															<tr>
																<th style="width: 1%;"></th>
																<th style="width: 98%;"><fmt:message key="grupo.grupo" /></th>
															</tr>
														</table>
													</div>
												</fieldset>
											</div>
										</div>
										<div class="col_100">
											<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
											 <button type='button' name='btnGravarNotificacao' class="light"
												onclick='gravarNotificacao();'>
												<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
												<span><fmt:message key="citcorpore.comum.gravar" />
												</span>
											</button>
											<button type='button' name='btnGravarFechar' class="light"
												onclick='fecharNotificacao();'>
												<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
												<span><fmt:message key="citcorpore.comum.fechar" />
												</span>
											</button>
										</fieldset>
										</div>
   							 		</div>

									<br>
									<br>

									<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar"/></span>
									</button>
									<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
										<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
										<span><fmt:message key="citcorpore.comum.excluir"/></span>
									</button>

								</form>

							</div>
						</div>

						<div id="tabs-2" class="block">
							<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_PASTA' id='LOOKUP_PASTA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>


    <div id="POPUP_USUARIO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUsuario' style="width: 540px">
							<cit:findField formName='formPesquisaUsuario'
								lockupName='LOOKUP_USUARIO_CONHECIMENTO' id='LOOKUP_USUARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_GRUPO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaGrupo' style="width: 540px">
							<cit:findField formName='formPesquisaGrupo' lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</html>

