<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.centralit.citcorpore.bean.NivelAutoridadeDTO"%>
<%@page import="br.com.centralit.bpm.dto.TipoFluxoDTO"%>
<%@page import="java.util.Collection"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");
		%>

		<%@ include file="/include/security/security.jsp" %>
		<%@include file="/include/header.jsp"%>
		<title>
			<fmt:message key="citcorpore.comum.title" />
		</title>

		<%@ include file="/include/menu/menuConfig.jsp" %>
		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

		<link rel="stylesheet" type="text/css" href="./css/delegacaoCentroResultado.css" />

		<script type="text/javascript" src="./js/delegacaoCentroResultado.js"></script>

	<%
		// Se for chamado por iframe deixa apenas a parte de cadastro da página
		if (iframe != null) {
	%>
		<link rel="stylesheet" type="text/css" href="./css/delegacaoCentroResultadoIFrame.css">
	<%
		}
	%>
	</head>
	<body>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>

		<div id="wrapper">
		<%
			if (iframe == null) {
		%>
			<%@ include file="/include/menu_vertical.jsp" %>
		<%
			}
		%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix">
			<%
				if (iframe == null) {
			%>
				<%@ include file="/include/menu_horizontal.jsp" %>
			<%
				}
			%>
				<div class="flat_area grid_16">
					<h2>
						<fmt:message key="delegacaoCentroResultado" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1">
								<fmt:message key="delegacaoCentroResultado.cadastro" />
							</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action="${ctx}/pages/delegacaoCentroResultado/delegacaoCentroResultado">
									<input type='hidden' name='idResponsavel' id='idResponsavel'/>
									<input type='hidden' name='idDelegacaoCentroResultado' id='idDelegacaoCentroResultado'/>

									<div class="columns clearfix">
		                                <div class="col_100">
		                                    <div class="col_50">
		                                         <fieldset>
		                                             <label class="campoObrigatorio"><fmt:message key="centroResultado" /></label>
		                                             <div>
		                                                 <select name='idCentroResultado' class="Description[centroResultado.custo]"></select>
		                                             </div>
		                                         </fieldset>
		                                    </div>
		                                    <div class="col_50">
                                               	 <br><button type='button' class='light img_icon has_text'  onclick='pesquisar();'>
                                                 <fmt:message key="citcorpore.comum.pesquisar" />
                                                 </button>
		                                    </div>
		                                </div>

		                                <div class="col_100">
	                                        <h2 class="section">
	                                            <fmt:message key="delegacaoCentroResultado.responsaveis" />
	                                        </h2>
		                                </div>
		                                <div id="divResponsaveis" class="col_100" style="overflow:auto;">
		                                </div>

                                        <div class="col_100">
	                                        <div class="col_80">
		                                        <h2 class="section">
		                                             <fmt:message key="delegacaoCentroResultado.delegacoes" />
		                                        </h2>
	                                        </div>
                                            <div class="col_20">
                                                 <button type='button' class='light img_icon has_text'  onclick='delegar();'>
                                                 <fmt:message key="delegacaoCentroResultado.delegar" />
                                                 </button>
                                            </div>
                                        </div>
                                        </div>
		                                <div id="divDelegacoes" class="col_100" style="overflow:auto;">
		                                </div>

										<br />
										<br />
										<button type="button" name="btnLimpar" class="light" onclick='document.form.clear();document.form.fireEvent("load");'>
											<img src="${ctx}/template_new/images/icons/small/grey/clear.png" />
											<span>
												<fmt:message key="citcorpore.comum.limpar" />
											</span>
										</button>
									</div>
								</form>
							</div>
						</div>
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</div>
				</div>
			</div>
			<!-- Fim da Pagina de Conteudo -->
		</div>

		<div id="POPUP_DELEGACAO" title="<fmt:message key="delegacaoCentroResultado.delegacao" />" style="overflow: hidden;">
		    <form name='formDelegacao' action="${ctx}/pages/delegacaoCentroResultado/delegacaoCentroResultado">
		        <input type='hidden' name='idEmpregado'/>
		        <input type='hidden' name='idCentroResultado' id='idCentroResultadoDeleg'/>
		        <input type='hidden' name='idResponsavel' id='idResponsavelDeleg'/>

				<div class="columns clearfix">
			        <div class="col_100">
				        <div class="col_50">
							<fieldset>
								<label class="campoObrigatorio"><fmt:message key="delegacaoCentroResultado.empregado" /></label>
								<div class="inline">
									<input onclick="adicionarEmpregado();" type="text" name="nomeEmpregado" id="nomeEmpregado" readonly='readonly' class="Valid[Required] Description[delegacaoCentroResultado.empregado]" />
								</div>
							</fieldset>
			            </div>
	                    <div class="col_25">
	                         <fieldset>
	                             <label class="campoObrigatorio"><fmt:message key="delegacaoCentroResultado.inicio" /></label>
	                             <div>
	                                <input type='text' name="dataInicio" id="dataInicio" maxlength="10" size="10"
	                                       readonly='readonly' class="Valid[Required] Description[delegacaoCentroResultado.inicio] Format[Data]" />
	                             </div>
	                         </fieldset>
	                    </div>
	                    <div class="col_25">
	                         <fieldset>
	                             <label class="campoObrigatorio"><fmt:message key="delegacaoCentroResultado.termino" /></label>
	                             <div>
	                                <input type='text' name="dataFim" id="dataFim" maxlength="10" size="10"
	                                       class="Valid[Required] Description[delegacaoCentroResultado.termino] Format[Data] datepicker" />
	                             </div>
	                         </fieldset>
	                    </div>
                    </div>
                    <div class="col_100">
			        	<h2 class="section">
			            	<fmt:message key="limiteAprovacao.processosNegocio" />
						</h2>
                    </div>
					<div id="divProcessos" class="col_100">
					</div>
                    <div class="col_100">
			        	<h2 class="section">
			            	<fmt:message key="delegacaoCentroResultado.abrangencia" />
						</h2>
                    </div>
                    <div class="col_100">
                    	<div class="col_40">
							<fieldset>
								<div>
									<input type="radio" id="abrangencia" name="abrangencia" value="E" class="Valid[Required] Description[delegacaoCentroResultado.abrangencia]" onclick="document.getElementById('divRequisicoes').style.display = 'none';"/><fmt:message key="delegacaoCentroResultado.abrangencia.novasEEmAndamento" /><br>
									<input type="radio" id="abrangencia" name="abrangencia" value="N" class="Valid[Required] Description[delegacaoCentroResultado.abrangencia]" onclick="document.getElementById('divRequisicoes').style.display = 'none';"/><fmt:message key="delegacaoCentroResultado.abrangencia.novas" /><br>
									<input type="radio" id="abrangencia" name="abrangencia" value="R" class="Valid[Required] Description[delegacaoCentroResultado.abrangencia]" onclick="document.getElementById('divRequisicoes').style.display = 'block';"/><fmt:message key="delegacaoCentroResultado.abrangencia.especificas" />
								</div>
							</fieldset>
						</div>
                    	<div class="col_60" id='divRequisicoes' style='display:none'>
							<fieldset>
								<label>&nbsp;</label>
								<div>
									<input type='text' name="requisiçoes" id="requisiçoes"/>
								</div>
							</fieldset>
						</div>
					</div>

                    <div class="col_100">
			             <div class="col_60">
			                <label>&nbsp;</label>
			             </div>
			             <div class="col_40">
			                 <button type="button" class="light" id='btnGravarDelegacao' name='btnGravarDelegacao' onclick='gravarDelegacao()'>
			                     <fmt:message key="citcorpore.comum.gravar" />
			                 </button>
			                 <button type="button" class="light" onclick='$("#POPUP_DELEGACAO").dialog("close");'>
			                     <fmt:message key="citcorpore.comum.fechar" />
			                 </button>
			             </div>
					</div>
		         </div>
		    </form>
		</div>

		<div id="POPUP_EMPREGADO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaEmpregado' style="width: 540px">
								<input type="hidden" id="isNotificacao" name="isNotificacao">
								<cit:findField formName='formPesquisaEmpregado'  lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

        <%@include file="/include/footer.jsp"%>
	</body>
</html>


