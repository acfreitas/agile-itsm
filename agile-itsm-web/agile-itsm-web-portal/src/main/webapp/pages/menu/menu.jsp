<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="css/menu.css"/>
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
					<div class="separator top"></div>
					<div class="row-fluid">
						<div class="innerLR">
							<div class="widget">
								<div class="widget-head">
									<h4 class="heading"><fmt:message key="menu.menu"/></h4>
								</div>
								<div class="widget-body collapse in">
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="menu.cadastroMenu"/></a></li>
											<li><a href="#tab2-3" data-toggle="tab" ><fmt:message key="menu.pesquisaMenu"/></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name='form' action='${ctx}/pages/menu/menu'>
												<input type='hidden' name='idMenu' id='idMenu' />
												<input type='hidden' name='dataInicio' id='dataInicio' />

												<div class='row-fluid'>
													<div class='span12'>
														<div class='row-fluid'>
															<div class='span6'>
																<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome" /></label>
																<input id="nome" name="nome" type='text' maxlength="256" class="span12 Valid[Required] Description[citcorpore.comum.nome]" />
															</div>
															<div class='span6'>
																<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.descricao" /></label>
																<input id="descricao" type="text" name="descricao" maxlength="256" class="span12 Valid[Required] Description[citcorpore.comum.descricao]" />
															</div>
														</div>
													</div>
												</div>
												<div class='row-fluid'>
													<div class='span12'>
														<div class='row-fluid'>
															<div class='span4'>
																<label> <fmt:message key="menu.menuPai" /></label>
																<select id="idMenuPai" name='idMenuPai' onchange="if(this.value > '') ativaMenuRapido(); else desativaMenuRapido();" class="span12 Description[Unidade Pai]"></select>
															</div>
															<div class='span4'>
																<label><fmt:message key="menu.link" /> </label>
																<input id="link" type="text" name="link" maxlength="256" class="span12 Description[menu.link]" />
															</div>
															<div class='span4'>
																<label class="campoObrigatorio"><fmt:message key="menu.ordem" /> </label>
																<input id="ordem" type="text" name="ordem" maxlength="10" class="span12 Valid[Required]  Description[menu.ordem]" />
															</div>
														</div>
													</div>
												</div>
												<div class='row-fluid'>
													<div class='span12'>
														<div class="uniformjs">
															<label class="checkbox">
																<input value="S" type="checkbox" id="menuRapido" name="menuRapido" class="send_left Description[Menu Rápido]" />&nbsp;
																<fmt:message key="menu.menuRapido"/>
															</label>
														</div>
													</div>
												</div>
												<div class='row-fluid' id="painelImagens">
													<div class='span12'>
														<label><fmt:message key="menu.imagem" /></label>
														<div id="docs_icons" class="uniformjs">
															<a href="#" class="glyphicons no-js fire"><i></i>
															<label class="checkbox">
																<input type="radio" name="imagem" value="fire" />
															</label>
															</a>
															<a href="#" class="glyphicons no-js user_add"><i></i>
																<label class="checkbox">
																	<input type="radio" name="imagem" value="user_add" />
																</label>
															</a>
															<a href="#" class="glyphicons no-js charts"><i></i>
																<label class="checkbox">
																	<input type="radio" name="imagem" value="charts" />
																</label>
															</a>
															<a href="#" class="glyphicons no-js pie_chart"><i></i>
																<label class="checkbox">
																	<input type="radio" name="imagem" value="pie_chart" />
																</label>
															</a>
															<a href="#" class="glyphicons no-js group"><i></i>
																<label class="checkbox">
																	<input type="radio" name="imagem" value="group" />
																</label>
															</a>
															<a href="#" class="glyphicons no-js list"><i></i>
																<label class="checkbox">
																	<input type="radio" name="imagem" value="list" />
																</label>
															</a>
															<a href="#" class="glyphicons no-js show_thumbnails"><i></i>
																<label class="checkbox">
																	<input type="radio" name="imagem" value="show_thumbnails" />
																</label>
															</a>
															<a href="#" class="glyphicons no-js circle_info"><i></i>
																<label class="checkbox">
																	<input type="radio" name="imagem" value="circle_info" />
																</label>
															</a>
															<a href="#" class="glyphicons no-js stats"><i></i>
																<label class="checkbox">
																	<input type="radio" name="imagem" value="stats" />
																</label>
															</a>
															<a href="#" class="glyphicons no-js building"><i></i>
																<label class="checkbox">
																	<input type="radio" name="imagem" value="building" />
																</label>
															</a>
															<a href="#" class="glyphicons no-js cogwheel"><i></i>
																<label class="checkbox">
																	<input type="radio" name="imagem" value="cogwheel" />
																</label>
															</a>
															<a href="#" class="glyphicons no-js cargo"><i></i>
																<label class="checkbox">
																	<input type="radio" name="imagem" value="cargo" />
																</label>
															</a>
														</div>
													</div>
												</div>
												<div class='row-fluid'>
													<div class='span12'>
														<button type='button' name='btnGravar' class="lFloat btn btn-icon btn-primary" onclick='gravar();'>
															<i></i><fmt:message key="citcorpore.comum.gravar" />
														</button>
														<button type="button" name='btnLimpar' class="lFloat btn btn-icon btn-primary" onclick='limpar();'>
															<i></i><fmt:message key="citcorpore.comum.limpar" />
														</button>
														<button type='button' name='btnUpDate' class="lFloat btn btn-icon btn-primary" onclick='excluir();'>
															<i></i><fmt:message	key="citcorpore.comum.excluir" />
														</button>
														<button type='button' id='btnGerar' name='btnGerar' class="lFloat btn btn-icon btn-primary" onclick='gerar();'>
															<i></i><fmt:message	key="menu.gerar" />
														</button>
														<button type='button' id='btnAtualizar' name='btnAtualizar' class="lFloat btn btn-icon btn-primary" onclick='atualizar();'>
															<i></i><fmt:message	key="menu.atualizar" />
														</button>
													</div>
												</div>
											</form>
										</div>
										<div class="tab-pane" id="tab2-3">
											<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
												<form name='formPesquisa'>
													<cit:findField formName='formPesquisa' lockupName='LOOKUP_MENU' id='LOOKUP_MENU' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--  Fim conteudo-->
			</div>
		</div>
		<%@include file="/novoLayout/common/include/rodape.jsp" %>
		<script type="text/javascript" src="js/menu.js"></script>
	</body>
</html>