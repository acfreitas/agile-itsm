<%@page import="br.com.centralit.citcorpore.util.Enumerados.TipoDate"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.citframework.util.UtilDatas"%>

<!doctype html public "">
<%@include file="/include/security/security.jsp"%>
<html lang="en-us" class="no-js">
<head>
<%@include file="/include/header.jsp"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");

	UsuarioDTO usuario = WebUtil.getUsuario(request);

    String dataAvaliacao = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, new java.util.Date(), WebUtil.getLanguage(request));
    String idResponsavel = usuario.getIdEmpregado().toString();
    String nomeResponsavel = usuario.getNomeUsuario();
%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<link type="text/css" rel="stylesheet" href="css/avaliacaoFornecedorAll.css"/>

<script type="text/javascript">
	var dataAvaliacao = "${dataAvaliacao}";
	var idResponsavel = "${idResponsavel}";
	var nomeResponsavel = "${nomeResponsavel}";
	var caminho = "${ctx}";


</script>

<script type="text/javascript" src="js/avaliacaoFornecedor.js"></script>
<%
	//se for chamado por iframe deixa apenas a parte de cadastro da página
	if (iframe != null) {
%>
<link type="text/css" rel="stylesheet" href="css/avaliacaoFornecedorIframe.css"/>
<%
	}
%>


</head>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<div id="main_container" class="main_container container_16 clearfix">
			<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="avaliacaoFornecedor.avaliacaoFornecedor" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message
								key="avaliacaoFornecedor.cadastro" /> </a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message
								key="avaliacaoFornecedor.pesquisa" /> </a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/avaliacaoFornecedor/avaliacaoFornecedor'>
								<input type='hidden' name='idAvaliacaoFornecedor' />
								<input type='hidden' name='idFornecedor' id="idFornecedor" />
								<input type='hidden' name='idResponsavel' id="idResponsavel" />
								<input type="hidden" name="listCriteriosQualidadeSerializado" id="listCriteriosQualidadeSerializado"/>
								<input type="hidden" name="listAprovacaoReferenciaSerializado" id="listAprovacaoReferenciaSerializado"/>
                                <input type="hidden" name="decisaoQualificacao" id="decisaoQualificacao"/>

								<div class="col_100">
									<h2 class="section">
										<fmt:message key="avaliacaoFonecedor.dadosFornecedor" />
									</h2>
									<div class="col_100">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="avaliacaoFonecedor.razaoSocial" /></label>
											<div>
												<input  type='text' id="razaoSocial" name="razaoSocial" maxlength="256" class="Valid[Required] Description[avaliacaoFonecedor.razaoSocial]" />
											</div>
										</fieldset>
									</div>
									<div class="col_100">
										<fieldset>
											<label><fmt:message key="avaliacaoFonecedor.endereco" /></label>
											<div>
												<input readonly="readonly" type='text' id="enderecoStr" name="enderecoStr" maxlength="256"  />
											</div>
										</fieldset>
									</div>
									<div class="col_100">
										<div class="col_40">
											<fieldset>
												<label><fmt:message key="avaliacaoFonecedor.cidade" /></label>
												<div>
													<input readonly="readonly" type='text' id="nomeCidade" name="nomeCidade" maxlength="256"  />
												</div>
											</fieldset>
										</div>

										<div class="col_10">
											<fieldset>
												<label><fmt:message key="avaliacaoFonecedor.uf" /></label>
												<div>
													<input readonly="readonly" type='text' id="siglaUf" name="siglaUf" maxlength="256"  />
												</div>
											</fieldset>
										</div>

										<div class="col_25">
											<fieldset>
												<label><fmt:message key="avaliacaoFonecedor.cep" /></label>
												<div>
													<input readonly="readonly" type='text' id="cep" name="cep" maxlength="256"  />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label><fmt:message key="avaliacaoFonecedor.telefone" /></label>
												<div>
													<input readonly="readonly" type='text' id="telefone" name="telefone" maxlength="256"  />
												</div>
											</fieldset>
										</div>
									</div>

									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="avaliacaoFonecedor.cnpj" /></label>
												<div>
													<input readonly="readonly" type='text' id="cnpj" name="cnpj" maxlength="256" />
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="avaliacaoFonecedor.inscricaoEstadual" /></label>
												<div>
													<input readonly="readonly" type='text' id="inscricaoEstadual" name="inscricaoEstadual" maxlength="256" />
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_100">
											<fieldset>
												<label><fmt:message key="avaliacaoFonecedor.email" /></label>
												<div>
													<input readonly="readonly" type='text' id="email" name="email" maxlength="256"  />
												</div>
											</fieldset>
									</div>
									<div class="col_100">
										<fieldset>
											<label><fmt:message key="avaliacaoFonecedor.nomeDoscontatos" /></label>
											<div id="divContatos">
												<input type="text" name="contato" id="contato" maxlength="245">
											</div>
										</fieldset>
									</div>
								    <div class="col_100">
										<fieldset>
											<label><fmt:message key="avaliacaoFonecedor.escopo" /></label>
											<div id="divEscopo">

											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<h2 class="section">
										<fmt:message key="avaliacaoFonecedor.criterioQualidade" />
									</h2>
									<div class="col_100">
										<button type="button"
													id="btnAddCriterio"
													name="btnAddCriterio"
													style="margin-top: 5px; margin-left: 3px; float: left;"
													class="light img_icon has_text" onclick="adicionarCriterio()">
													<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
													<span style="font-size: 12px !important;"><fmt:message key='avaliacaoFornecedor.adicionarCriterio'/></span>
										</button>
									</div>
									<div class="col_100">
										<fieldset>
											<div id="divCriterio">
												<div id="divOS"	style="height: 120px; width: 100%; overflow: auto; border: 1px solid black;">
													<table id="tblCriterio" cellpadding="0" cellspacing="0" width="100%" class="table table-bordered table-striped">
														<tr>
															<th style="text-align: center" class="linhaSubtituloGrid" width="16px" >&nbsp;</th>
															<th></th>
													 		<th class="linhaSubtituloGrid"><fmt:message key='citcorpore.comum.requisito'/></th>
															<th class="linhaSubtituloGrid"><fmt:message key='colaborador.observacao'/></th>
														</tr>
													</table>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<h2 class="section">
										<fmt:message key="avaliacaoFonecedor.aprovacaoReferencia" />
									</h2>
									<div class="col_100">
										<button type="button"
													id="btnAddAprovacao"
													name="btnAddAprovacao"
													style="margin-top: 5px; margin-left: 3px; float: left;"
													class="light img_icon has_text">
													<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
													<span style="font-size: 12px !important;"><fmt:message key='avaliacaoFornecedor.adicionarAprovacaoReferencia'/></span>
												</button>

									</div>
									<div class="col_100">
										<fieldset>
											<div id="divAprovacaoReferencia">
											<div id="divOS"	style="height: 120px; width: 100%; overflow: auto; border: 1px solid black;">
													<table id="tblAprovacao" cellpadding="0" cellspacing="0" width="100%" class="table table-bordered table-striped">
														<tr>
															<th style="text-align: center" class="linhaSubtituloGrid" width="16px">&nbsp;</th>
															<th></th>
													 		<th class="linhaSubtituloGrid"><fmt:message key='cronograma.nome'/></th>
															<th class="linhaSubtituloGrid"><fmt:message key='fornecedor.telefone'/></th>
															<th class="linhaSubtituloGrid"><fmt:message key='colaborador.observacao'/></th>
														</tr>
													</table>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<h2 class="section">
										<fmt:message key="avaliacaoFornecedor.conclusao" />
									</h2>
									<div class="col_100">
											<fieldset>
												<label ><fmt:message key="avaliacaoFonecedor.observacao" /></label>
												<div class="inline">
													<textarea name="observacoesAvaliacaoFornecedor" id="obsAvaliacaoFornecedor" ></textarea>
												</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
								<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="avaliacaoFonecedor.responsavel" /></label>
												<div class="inline">
													<input onclick="adicionarResponsavel();" type="text" name="nomeResponsavel" id="nomeResponsavel" class="Valid[Required] Description[avaliacaoFonecedor.responsavel]" />
												</div>
										</fieldset>
									</div>
									<div class="col_16">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="avaliacaoFonecedor.dataAvaliacao"/></label>
												<div class="inline">
												<input  type='text'  id="dataAvaliacao"  name="dataAvaliacao" maxlength="10" size="10"  class="Valid[Data,Required] Format[Data] datepicker Description[avaliacaoFonecedor.dataAvaliacao]" />
												</div>
										</fieldset>
									</div>
								</div>
								<br> <br>
								<button type='button' name='btnGravar' class="light"
									onclick='gravar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" /> </span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='limpar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" /> </span>
								</button>
								<button type='button' name='btnExcluir' id="btnExcluir"
									class="light" onclick='excluir();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message key="citcorpore.comum.excluir" /> </span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_AVALIACAOFORNECEDOR' id='LOOKUP_AVALIACAOFORNECEDOR' top='0' left='0'
									len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
					<div id="POPUP_CRITERIO" title='<fmt:message key="citcorpore.ui.janela.popup.titulo.Adicionar_OS" />'>
						<form name="formCriterio" action="${ctx}/pages/criterioAvaliacao/criterioAvaliacao">
							<input type="hidden" name="idCriterio" id="idCriterio" />
							<input type="hidden" name="sequencia" id="sequencia" />
								<div class="col_100">
									<div class="col_100">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="avaliacaoFornecedor.criterio"/></label>
											<div>
												<input readonly="readonly" type='text' onclick="adicionarDescricao();" id="descricao" name="descricao" maxlength="256" class="Valid[Required] Description[avaliacaoFornecedor.criterio]" />
											</div>
										</fieldset>
									</div>
									<div class="col_100">
											<fieldset>
												<label><fmt:message key="citcorpore.comum.observacoes"/></label>
												<div>
													<textarea  id="obsCriterio" name="obs"  rows="4" cols="2"></textarea>
												</div>
											</fieldset>
									</div>
									<br> <br>
									<button type='button' name='btnGravar' class="light"
										onclick='document.formCriterio.fireEvent("atualizaGridCriterio");'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" /> </span>
									</button>
									<button type='button' name='btnLimpar' class="light"
										onclick='document.formCriterio.clear();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar" /> </span>
									</button>

									<button type='button' name='btnLimpar' class="light"
										onclick='fechaCriterio();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/alert.png">
										<span><fmt:message key="citcorpore.comum.fechar" /> </span>
									</button>
							</div>
						</form>
					</div>
					<div id="POPUP_APROVACAO" title='<fmt:message key="citcorpore.ui.janela.popup.titulo.Adicionar_OS" />'>
						<form name="formAprovacao" action="${ctx}/pages/avaliacaoReferenciaFornecedor/avaliacaoReferenciaFornecedor">
							<input type="hidden" name="idEmpregado" id="idEmpregado" />
							<input type="hidden" name="sequencia" id="sequencia" />
							<div class="col_100">
								<div class="col_100">
									<fieldset>
										<label class="campoObrigatorio" ><fmt:message key="citcorpore.comum.nome"/></label>
										<div>
											<input readonly="readonly" type='text' onclick="adicionarNome();" id="nome" name="nome" maxlength="256" class="Valid[Required] Description[citcorpore.comum.nome]"  />
										</div>
									</fieldset>
								</div>

								<div class="col_100">
									 <div class="col_40">
										<fieldset style='height:100px'>
											<label><fmt:message key="citcorpore.comum.telefone"/></label>
											<div>
												<input type='text' id="telefone" name="telefone" maxlength="256" class="Valid[Required] Description[citcorpore.comum.telefone]" />
											</div>
										</fieldset>
									</div>
									<div class="col_60">
											<fieldset style='height:100px'>
												<label><fmt:message key="colaborador.observacao" /></label>
												<div class="inline">
													<textarea id="obsAvaliacaoReferencia" name="observacoes"></textarea>
												</div>
											</fieldset>
									</div>
								</div>
									<button type='button' name='btnGravar' class="light"
										onclick='document.formAprovacao.fireEvent("atualizaGridAvaliacao");'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" /> </span>
									</button>
									<button type='button' name='btnLimpar' class="light"
										onclick='document.formAprovacao.clear();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar" /> </span>
									</button>
									<button type='button' name='btnLimpar' class="light"
										onclick='fechaAprovacao();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/alert.png">
										<span><fmt:message key="citcorpore.comum.fechar" /> </span>
									</button>
							</div>
						</form>
					</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	<%@include file="/include/footer.jsp"%>

	<div id="POPUP_FORNECEDOR" title="<fmt:message key="avaliacaoFornecedor.fornecedor" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaFornecedor' style="width: 540px">
							<cit:findField formName='formPesquisaFornecedor'
								lockupName='LOOKUP_FORNECEDOR' id='LOOKUP_FORNECEDOR' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_CRITERIOAVALIACAO" title="<fmt:message key="criterioAvaliacao.criterio_avaliacao"/>">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaCriterio' style="width: 540px">
							<cit:findField formName='formPesquisaCriterio'
								lockupName='LOOKUP_CRITERIOAVALIACAO_FORNECEDOR' id='LOOKUP_CRITERIOAVALIACAO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_EMPREGADOS" title="Cliente ou Gestor">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaAprovacao' style="width: 540px">
								<cit:findField formName='formPesquisaAprovacao'
									lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_RESPONSAVEL" title="<fmt:message key="citcorpore.comum.pesquisar" />">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaUsuario' style="width: 540px">
								<input type="hidden" id="isNotificacao" name="isNotificacao">
								<cit:findField formName='formPesquisaUsuario'  lockupName='LOOKUP_EMPREGADO' id='LOOKUP_RESPONSAVEL' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>
