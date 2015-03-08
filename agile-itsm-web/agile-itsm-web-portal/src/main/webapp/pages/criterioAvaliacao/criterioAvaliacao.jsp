<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UploadDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="./js/criterioAvaliacao.js"></script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="criterioAvaliacao.criterio_avaliacao" />
				</h2>
			</div>
			<div id="tabs" class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message	key="criterioAvaliacao.cadastro_criterio" /></a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="criterioAvaliacao.pesquisa_criterio" />	</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form id='form' name='form' action='${ctx}/pages/criterioAvaliacao/criterioAvaliacao'>
								<input type='hidden' name="idCriterio" id="idCriterio" />
								<div class="columns clearfix">
                                    <div class="col_100">
										<div class="col_66">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="criterioAvaliacao.criterio_descricao" />
												</label>
												<div>
													<input type='text' name="descricao" maxlength="100" class="Valid[Required] Description[criterioAvaliacao.criterio_descricao]" />
												</div>
											</fieldset>
	                                    </div>
                                        <div class="col_33">
                                            <fieldset>
                                                <label class="campoObrigatorio">
                                                    <fmt:message key="criterioAvaliacao.tipoAvaliacao" />
                                                </label>
                                                <div>
                                                    <select name='tipoAvaliacao' id='tipoAvaliacao' class="Valid[Required] Description[criterioAvaliacao.tipoAvaliacao]">
                                                        <option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
                                                        <option value='S'><fmt:message key="criterioAvaliacao.simNao" /></option>
                                                        <option value='A'><fmt:message key="criterioAvaliacao.aceitaNaoAceita" /></option>
                                                        <option value='C'><fmt:message key="criterioAvaliacao.conformeNaoConforme" /></option>
                                                        <option value='P'><fmt:message key="criterioAvaliacao.peso" /></option>
                                                    </select>
                                                </div>
                                            </fieldset>
                                        </div>
									</div>
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio">
												<fmt:message key="criterioAvaliacao.aplicavel_cotacao"/>
											</label>
											<div class="inline">
												<label>
													<input type='radio' id="aplicavelCotacao" name="aplicavelCotacao" value="S" class="Valid[Required] Description[criterioAvaliacao.aplicavel_cotacao]" checked="checked" />
													<fmt:message key="citcorpore.comum.sim"/>
												</label>
												<label>
													<input type='radio' id="aplicavelCotacao" name="aplicavelCotacao" value="N" class="Valid[Required] Description[criterioAvaliacao.aplicavel_cotacao]" />
													<fmt:message key="citcorpore.comum.nao"/>
												</label>
											</div>
										</fieldset>
								 	</div>
								 	<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio">
												<fmt:message key="criterioAvaliacao.aplicavel_avaliacao_solicitante"/>
											</label>
											<div class="inline">
												<label>
													<input type='radio' id="aplicavelAvaliacaoSolicitante" name="aplicavelAvaliacaoSolicitante" value="S" class="Valid[Required] Description[criterioAvaliacao.aplicavel_avaliacao_solicitante]" checked="checked" />
													<fmt:message key="citcorpore.comum.sim"/>
												</label>
												<label>
													<input type='radio' id="aplicavelAvaliacaoSolicitante" name="aplicavelAvaliacaoSolicitante" value="N" class="Valid[Required] Description[criterioAvaliacao.aplicavel_avaliacao_solicitante]" />
													<fmt:message key="citcorpore.comum.nao"/>
												</label>
											</div>
										</fieldset>
								 	</div>
								 	<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio">
												<fmt:message key="criterioAvaliacao.aplicavel_avaliacao_comprador"/>
											</label>
											<div class="inline">
												<label>
													<input type='radio' id="aplicavelAvaliacaoComprador" name="aplicavelAvaliacaoComprador" value="S" class="Valid[Required] Description[criterioAvaliacao.aplicavel_avaliacao_comprador]" checked="checked" />
													<fmt:message key="citcorpore.comum.sim"/>
												</label>
												<label>
													<input type='radio' id="aplicavelAvaliacaoComprador" name="aplicavelAvaliacaoComprador" value="N" class="Valid[Required] Description[criterioAvaliacao.aplicavel_avaliacao_comprador]" />
													<fmt:message key="citcorpore.comum.nao"/>
												</label>
											</div>
										</fieldset>
								 	</div>
                                    <div class="col_25">
                                        <fieldset>
                                            <label class="campoObrigatorio">
                                                <fmt:message key="criterioAvaliacao.aplicavel_qualificacao_fornecedor"/>
                                            </label>
                                            <div class="inline">
                                                <label>
                                                    <input type='radio' id="aplicavelQualificacaoFornecedor" name="aplicavelQualificacaoFornecedor" value="S" class="Valid[Required] Description[criterioAvaliacao.aplicavel_qualificacao_fornecedor]" checked="checked" />
                                                    <fmt:message key="citcorpore.comum.sim"/>
                                                </label>
                                                <label>
                                                    <input type='radio' id="aplicavelQualificacaoFornecedor" name="aplicavelQualificacaoFornecedor" value="N" class="Valid[Required] Description[criterioAvaliacao.aplicavel_qualificacao_fornecedor]" />
                                                    <fmt:message key="citcorpore.comum.nao"/>
                                                </label>
                                            </div>
                                        </fieldset>
                                    </div>
								</div>

								<br /><br />
								<button type='button' name='btnGravar' class="light"
									onclick='document.form.save();'>
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
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_CRITERIOAVALIACAO' id='LOOKUP_CRITERIOAVALIACAO' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>

	<%@include file="/include/footer.jsp"%>
</body>
</html>

