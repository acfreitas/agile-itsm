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
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<script>
			var objTab = null;
		
			addEvent(window, "load", load, false);
			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}
		
			function LOOKUP_JORNADAEMPREGADO_select(id, desc) {
				document.form.restore({idJornada : id});
				$('.tabsbar a[href="#tab1-2"]').tab('show');
			}
			
			function excluir() {
				if (document.getElementById("idJornada").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("delete");
					}
				}
			}
			
			function limpar() {
				document.form.clear();
			}
		
		</script>
		<style type="text/css">
		
		</style>
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
									<h4 class="heading"><fmt:message key="rh.jornadaEmpregado" /></h4>
								</div>
								<div class="widget-body collapse in">		
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-2" data-toggle="tab"><fmt:message key="rh.cadastroJornadada" /></a></li>
											<li><a href="#tab2-2" data-toggle="tab" ><fmt:message key="rh.pesquisaJornadada" /></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-2">
											<form name='form' action='${ctx}/pages/jornadaEmpregado/jornadaEmpregado'>
												<input type='hidden' name='idJornada' id='idJornada' />		
																						
												<div class="innerLR">
													<div class="row-fluid">
														<div class="row-fluid">
															<div class="span6">
																<label class="campoObrigatorio strong"><fmt:message key="jornadaempregado.descricao" /></label>
																<input id="descricao" name="descricao" type='text' maxlength="150" class="Valid[Required] Description[jornadaempregado.descricao]" />
															</div>
														</div>
														<div class="row-fluid">
															<div class="span2">
																<div>
																	<label class="campoObrigatorio strong"><fmt:message key="jornadaempregado.escala" /></label>
																	<input id="escala" type="radio" name="escala" value="S" class="Valid[Required] Description[jornadaempregado.escala]" /><fmt:message key="menu.sim" />
																	<input id="escala" type="radio" name="escala" value="N" class="Valid[Required] Description[jornadaempregado.escala]" /><fmt:message key="menu.nao" />
																</div>
															</div>
															<div class="span2">
																<div>
																	<label class="campoObrigatorio strong"><fmt:message key="calendario.consideraFeriados" /></label>
																	<input id="considerarFeriados" type="radio" name="considerarFeriados" value="S" class="Valid[Required] Description[jornadaempregado.escala]" /><fmt:message key="menu.sim" />
																	<input id="considerarFeriados" type="radio" name="considerarFeriados" value="N" class="Valid[Required] Description[jornadaempregado.escala]" /><fmt:message key="menu.nao" />
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class='innerTB'>
													<div class='innerLR'>
														<div class='row-fluid'>
															<div class='span12'>
																<button type='button' name='btnGravar' class="lFloat btn btn-icon btn-primary" onclick='document.form.save();'>
																	<i></i><fmt:message key="citcorpore.comum.gravar" />
																</button>
																<button type="button" name='btnLimpar' class="lFloat btn btn-icon btn-primary" onclick='limpar();'>
																	<i></i><fmt:message key="citcorpore.comum.limpar" />
																</button>
																<button type='button' name='btnUpDate' class="lFloat btn btn-icon btn-primary" onclick='excluir();'>
																	<i></i><fmt:message	key="citcorpore.comum.excluir" />
																</button>
															</div>
													   </div>									
												   </div>									
											   </div>									
											</form>
										</div>
										<div class="tab-pane" id="tab2-2">
											<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
												<form name='formPesquisa'>
													<cit:findField formName='formPesquisa' lockupName='LOOKUP_JORNADAEMPREGADO' id='LOOKUP_JORNADAEMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>
    	<script type="text/javascript" src="${ctx}/cit/objects/RequisicaoPessoalDTO.js"></script>		
		<script src="${ctx}/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
	</body>
</html>
