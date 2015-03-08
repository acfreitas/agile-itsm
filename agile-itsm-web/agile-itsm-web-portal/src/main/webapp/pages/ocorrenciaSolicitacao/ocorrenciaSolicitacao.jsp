<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor"%>

<!DOCTYPE html>
<compress:html
	enabled="true"
	jsCompressor="closure"
	compressCss="true"
	compressJavaScript="true"
	removeComments="true"
	removeMultiSpaces="true">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="../../pages/ocorrenciaSolicitacao/css/ocorrenciaSolicitacao.css"/>
		<script type="text/javascript" src="/citsmart/js/jquery.js"></script>
		<script type="text/javascript" src="js/ocorrenciaSolicitacao.js"></script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">

			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">

				<% if(iframe == null) { %>
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
				<% } %>

			</div>

			<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">

				<!-- Inicio conteudo -->
				<div id="content">
					<h3><fmt:message key="solicitacaoServico.solicitacao"/>&nbsp;N&deg;&nbsp;${idSolicitacaoServico}</h3>
					<div class="box-generic">
						<form name="formOcorrenciaSolicitacao" method="post"
								action="${ctx}/pages/ocorrenciaSolicitacao/ocorrenciaSolicitacao">
								<input type="hidden" id="notificarSolicitante" name="notificarSolicitante" />
								<input type="hidden" id="notificarResponsavel" name="notificarResponsavel" />
								<input type="hidden" id="idSolicitacaoOcorrencia" name="idSolicitacaoOcorrencia" value="${idSolicitacaoServico}"/>
								<input type="hidden" id="idCategoriaOcorrencia" name="idCategoriaOcorrencia" />
								<input type="hidden" id="idOrigemOcorrencia" name="idOrigemOcorrencia" />
								<input type="hidden" name="idOcorrencia" />
								<input type="hidden" id="isPortal" name="isPortal" value="false"/>
							<div style='overflow-x: scroll;' class="box-generic" >

								<!-- Tabs Heading -->
								<div class="tabsbar">
									<ul>
										<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="solicitacaoServico.ocorrenciasRegistradas" /> </a></li>
										<li class="" id='tabCadastroOcorrencia'><a href="#tab2-3" data-toggle="tab"><fmt:message key="solicitacaoServico.cadastroOcorrencia" /> </a></li>
									</ul>
								</div>
								<!-- // Tabs Heading END -->
								<div class="tab-content">
									<div class='tab-pane active' id='tab1-3'>
										<div id="divRelacaoOcorrencias"></div>
									</div>
									<div class='tab-pane' id='tab2-3'>
										<div class="">
												<div class="input-append">
													<label  class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.categoria" /></label>
												  	<input class="span5 Valid[Required] Description[citcorpore.comum.categoria]"  type="text" name="nomeCategoriaOcorrencia" id="nomeCategoriaOcorrencia" placeholder="" onclick="abreLookupCategoriaOcorrencia()">
												  	<button class="btn btn-default btn-disabilitado-categoria" type="button" id="buscaCategoriaOcorrencia" onclick="abreLookupCategoriaOcorrencia()"><i class="icon-search"></i></button>
													<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus btn-disabilitado-categoria" id="cadastraCategoriaOcorrencia" href="#modal_cadastroCategoriaOcorrencia" onclick="abrirModalCadastroCategoriaOcorrencia()"><i></i> <fmt:message key="citcorpore.comum.cadastroCategoria" /></span>
												</div>
												<div class="input-append">
													<label  class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.origem" /></label>
												  	<input class="span5 Valid[Required] Description[citcorpore.comum.origem]"  type="text" name="nomeOrigemOcorrencia" id="nomeOrigemOcorrencia" placeholder="" onclick="abreLookupOrigemOcorrencia()">
												  	<button class="btn btn-default btn-disabilitado-origem" type="button" id="buscaOrigemOcorrencia" onclick="abreLookupOrigemOcorrencia()" ><i class="icon-search"></i></button>
													<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus btn-disabilitado-origem" id="cadastraOrigemOcorrencia" href="#modal_cadastroOrigemOcorrencia" onclick="abrirModalCadastroOrigemOcorrencia()"><i></i><fmt:message key="citcorpore.comum.cadastroOrigem" /></span>
												</div>
										</div>
										<div class="row-fluid">
												<div class="span3">
													<label  class="strong  campoObrigatorio"><fmt:message key="citcorpore.comum.tempogasto" /></label>
													<input type="text" class="span3 Valid[Required] Description[ocorrenciaSolicitacao.tempoGasto]" onkeypress='return somenteNumero(event)' name="tempoGasto" id="tempoGasto" maxlength="6"></input>
													<span ><fmt:message key="citcorpore.texto.tempo.minutos" /></span>
												</div>
												<div class="span6">
														<label  class="strong campoObrigatorio"><fmt:message key="ocorrenciaLiberacao.registradopor" /></label>
														<input type="text" class="span12 Valid[Required] Description[ocorrenciaSolicitacao.registradopor]" id="registradopor" name="registradopor"></input>
												</div>
										</div>
										<div class="controls">
											<label  class="strong campoObrigatorio"><fmt:message key="solicitacaoServico.descricao" /></label>
												<div class="controls">
													<textarea  class="span10 Valid[Required] Description[pesquisa.descricao]" rows="2" id="descricao" name="descricao" maxlength ="200" ></textarea>
												</div>
											</div>
										<div class="controls">
											<label  class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.ocorrencia" /></label>
												<div class="controls">
													<textarea  class="span10 Valid[Required] Description[Ocorrência]" rows="4" id="ocorrencia" name="ocorrencia"></textarea>
										</div>
													</div>
										<div class="controls">
											<label  class="strong campoObrigatorio"><fmt:message key="solicitacaoServico.informacaoContato" /></label>
												<div class="controls">
													<textarea id="taInformacoesContato"  class="span10 Valid[Required] Description[solicitacaoServico.informacaoContato]" rows="4" id="informacoesContato"  name="informacoesContato"></textarea>
												</div>
										</div>

									<div class="controls" id="divCheckNotificarSolicitante">
										<label class="checkbox">
											<input type="checkbox" class="radio" name="checkNotificarSolicitante" id="checkNotificarSolicitante"/>
											<fmt:message key="ocorrenciaSolicitacao.notificarSolicitante" />
										</label><br/>
									</div>

									<div class="controls" id="divCheckNotificarResponsavel">
										<label class="checkbox">
											<input type="checkbox" class="radio" name="checkNotificarResponsavel" id="checkNotificarResponsavel"/>
											<fmt:message key="ocorrenciaSolicitacao.notificarResponsavel"  />
										</label><br/>
									</div>

									<button id="" class="btn btn-icon btn-primary glyphicons circle_ok" type="button" onclick="gravarOcorrencia();"><i></i><fmt:message key="citcorpore.comum.gravar" /> </button>
										<button id="" class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick="limparCamposOcorrencia();"><i></i><fmt:message key="dataManager.limpar" /></button>

									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!--  Fim conteudo-->
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>

		<!-- MODAL CADASTRO DE CATEGORIA DE OCORRENCIA ... -->
			<div class="modal hide fade in" id="modal_cadastroCategoriaOcorrencia" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="solicitacaoServico.cadastroCategoriaOcorrencia" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoCadastroCategoriaOcorrencia">

						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

					<!-- MODAL CADASTRO DE ORIGEM DE OCORRENCIA ... -->
			<div class="modal hide fade in" id="modal_cadastroOrigemOcorrencia" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="solicitacaoServico.cadastroOrigemOcorrencia" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoCadastroOrigemOcorrencia">

						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<div class="modal hide fade in" id="modal_lookupCategoriaOcorrencia" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name='formPesquisaCategoriaOcorrencia' style="width: 540px">
						<cit:findField formName='formPesquisaCategoriaOcorrencia'
							lockupName='LOOKUP_CATEGORIA_OCORRENCIA' id='LOOKUP_CATEGORIA_OCORRENCIA' top='0'
							left='0' len='550' heigth='200' javascriptCode='true'
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

			<div class="modal hide fade in" id="modal_lookupOrigemOcorrencia" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name='formPesquisaOrigemOcorrencia' style="width: 540px">
						<cit:findField formName='formPesquisaOrigemOcorrencia'
							lockupName='LOOKUP_ORIGEM_OCORRENCIA' id='LOOKUP_ORIGEM_OCORRENCIA' top='0'
							left='0' len='550' heigth='200' javascriptCode='true'
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
</html>
</compress:html>
