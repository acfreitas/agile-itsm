<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="java.util.Collection"%>
<!doctype html public "">
<html>
<head>
<%
			//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
			String iframe = "";
			iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>

<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
    <script type="text/javascript" src="${ctx}/js/boxover.js"></script>

<script>
$(function() {
    $("#POPUP_RESULTADO").dialog({
        autoOpen : false,
        width : 600,
        height : 400,
        modal : true
    });
});

	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterLoad = function() {
			$('.tabs').tabs('select', 0);
		}
	}

    function limpar() {
    	document.form.clear();
        $('.tabs').tabs('select', 0);
    }

    function login() {
        JANELA_AGUARDE_MENU.show();
    	document.form.fireEvent("autentica");
    }

    function exibeResultado(result) {
    	document.getElementById("divResultado").innerHTML = result;
        $("#POPUP_RESULTADO").dialog("open");
    }
</script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<style>
    div#main_container {
        margin: 10px 10px 10px 10px;
        width: 100%;
    }
</style>
<%}%>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix" style="height: auto !important;">
			<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
				<h2>
					Execução de operação Rest
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1">Teste
					</a>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/testeOperacaoRest/testeOperacaoRest'>
								<div class="columns clearfix">

                                    <div class="col_100">
                                       <div class="col_60">
											<fieldset style='height:60px'>
												<label>URL
												</label>
		                                           <div>
		                                               <input id="url" type="text" name="url" />
		                                           </div>
											</fieldset>
										</div>
	                                    <div class="col_20">
                                            <fieldset style='height:60px'>
                                                <label >Formato de saída
                                                </label>
                                                   <div>
                                                       <select name='formatoSaida' class="Valid[Required] Description[Formato]"></select>
                                                   </div>
                                            </fieldset>
								         </div>
								    </div>

                                    <div class="col_100">
                                         <div class="col_20">
                                            <fieldset style='height:60px'>
                                                <label >Usuário
                                                </label>
                                                   <div>
                                                       <input id="loginUsuario" type='text' name="loginUsuario" />
                                                   </div>
                                            </fieldset>
                                         </div>
                                         <div class="col_20">
											<fieldset style='height:60px'>
												<label>Senha
												</label>
		                                           <div>
		                                               <input id="senha" type="password" name="senha"  />
		                                           </div>
											</fieldset>
								         </div>
	                                    <div class="col_10">
	                                        <fieldset style='height:60px'>
		                                        <div style="padding:20px">
					                                <button type='button' name='btnLogin' class="light"
					                                    onclick='login();'>
					                                    <img
					                                        src="${ctx}/template_new/images/icons/small/grey/pencil.png">
					                                    <span>Autenticar
					                                    </span>
					                                </button>
		                                        </div>
	                                        </fieldset>
	                                    </div>
                                         <div class="col_30">
											<fieldset style='height:60px'>
												<label>ID da Sessão
												</label>
		                                           <div>
		                                               <input id="idSessao" type="text" name="idSessao" readonly="readonly" />
		                                           </div>
											</fieldset>
								         </div>
	                                </div>
	                                <div class="col_100">
							            <div class="tabs" style="width: 100%;">
							                <ul class="tab_header clearfix">
                                                <li><a href="#tabs-2" class="round_top" onclick=''>AddServiceRequest
                                                </a>
                                                <li><a href="#tabs-3" class="round_top" onclick=''>Mobile
                                                </a>
							                </ul>
                                            <a href="#" class="toggle">&nbsp;</a>
							                <div class="toggle_container">
                                                <div id="tabs-2" class="block">
                                                    <div class="columns clearfix">
                                                    	<div class="col_100">
					                                         <div class="col_30">
					                                            <fieldset style='height:60px'>
					                                                <label >Solicitante
					                                                </label>
					                                                   <div>
					                                                       <select name='loginSolicitante' class="Valid[Required] Description[Solicitante]"></select>
					                                                   </div>
					                                            </fieldset>
					                                         </div>
					                                         <div class="col_50">
					                                            <fieldset style='height:60px'>
					                                                <label >Serviço
					                                                </label>
					                                                   <div>
					                                                       <select name='idServico' class="Valid[Required] Description[Serviço]"></select>
					                                                   </div>
					                                            </fieldset>
					                                         </div>
					                                    </div>
                                                    	<div class="col_100">
                                                    		<div class="col_30">
					                                            <fieldset style='height:60px'>
					                                                <label >Número
					                                                </label>
					                                                   <div>
					                                                       <input id="numero" type="text" name="numero" class="Valid[Required] Description[Número]" />
					                                                   </div>
					                                            </fieldset>
					                                         </div>
					                                         <div class="col_25">
					                                            <fieldset style='height:60px'>
					                                                <label >Impacto
					                                                </label>
					                                                   <div>
					                                                       <select name='impacto' class="Valid[Required] Description[Impacto]"></select>
					                                                   </div>
					                                            </fieldset>
					                                         </div>
					                                         <div class="col_25">
					                                            <fieldset style='height:60px'>
					                                                <label >Urgência
					                                                </label>
					                                                   <div>
					                                                       <select name='urgencia' class="Valid[Required] Description[Urgência]"></select>
					                                                   </div>
					                                            </fieldset>
					                                         </div>
						                                    <div class="col_20">
						                                        <fieldset style='height:60px'>
							                                        <div style="padding:20px">
										                                <button type='button' name='btnSave' class="light"
										                                    onclick='JANELA_AGUARDE_MENU.show();document.form.save();'>
										                                    <img
										                                        src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										                                    <span>Testar
										                                    </span>
										                                </button>
							                                        </div>
						                                        </fieldset>
						                                    </div>
					                                     </div>
                                                   		<div class="col_100">
					                                         <div class="col_30">
					                                            <fieldset style='height:100px'>
					                                                <label >Tipo
					                                                </label>
					                                                   <div>
					                                                       <select name='tipo' class="Valid[Required] Description[Tipo]"></select>
					                                                   </div>
					                                            </fieldset>
					                                         </div>
					                                         <div class="col_50">
					                                            <fieldset style='height:100px'>
					                                                <label >Descrição
					                                                </label>
					                                                   <div>
					                                                       <textarea name="descricao" id="descricao" cols='200' rows='4' class="Valid[Required] Description[Descrição]"></textarea>
					                                                   </div>
					                                            </fieldset>
					                                         </div>
						                                    <div class="col_20">
						                                        <fieldset style='height:100px'>
							                                        <div style="padding:20px">
										                                <button type='button' name='btnSave' class="light"
										                                    onclick='JANELA_AGUARDE_MENU.show();document.form.fireEvent("saveVersaoAnterior");'>
										                                    <img
										                                        src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										                                    <span>Testar versão antiga
										                                    </span>
										                                </button>
							                                        </div>
						                                        </fieldset>
						                                    </div>
						                                 </div>
					                               </div>
                                                </div>
                                                <div id="tabs-3" class="block">
                                                    <div class="columns clearfix">
                                                    	<div class="col_100">
					                                         <div class="col_50">
																<fieldset style='height:60px'>
																	<label>Tipos de solicitação</label>
																	<div style="padding:20px">
																		<input value='0' type='radio' name='tipoListagem' checked='checked'/>Todas
																		<input value='1' type='radio' name='tipoListagem' />Compras
																		<input value='2' type='radio' name='tipoListagem' />Viagens
																		<input value='3' type='radio' name='tipoListagem' />RH
																		<input value='4' type='radio' name='tipoListagem' />Incidentes
																		<input value='5' type='radio' name='tipoListagem' />Outras requisições
																	</div>
																</fieldset>
					                                         </div>
					                                         <div class="col_30">
																<fieldset style='height:60px'>
																	<label>Somente em aprovação</label>
																	<div style="padding:20px">
																		<input value='1' type='radio' name='somenteEmAprovacao' checked='checked' />Sim
																		<input value='0' type='radio' name='somenteEmAprovacao' />Não
																	</div>
																</fieldset>
					                                         </div>
					                                         <div class="col_20">
						                                        <fieldset style='height:60px'>
							                                        <div style="padding:20px">
										                                <button type='button' name='btnSave1' class="light"
										                                    onclick='JANELA_AGUARDE_MENU.show();document.form.fireEvent("notification_getByUser");'>
										                                    <img
										                                        src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										                                    <span>getByUser
										                                    </span>
										                                </button>
							                                        </div>
						                                        </fieldset>
						                                    </div>
						                                 </div>
                                                    	<div class="col_100">
					                                         <div class="col_20">
					                                            <fieldset style='height:60px'>
					                                                <label >Id da tarefa
					                                                </label>
					                                                   <div>
					                                                       <input id="idTarefa" type="text" name="idTarefa" />
					                                                   </div>
					                                            </fieldset>
					                                         </div>
					                                         <div class="col_20">
					                                            <fieldset style='height:60px'>
					                                            	<label>Feedback</label>
																	<div style="padding:20px">
																		<input value='0' type='radio' name='feedback' />Negativo
																		<input value='1' type='radio' name='feedback' />Positivo
																	</div>
					                                            </fieldset>
					                                         </div>
					                                         <div class="col_30">
					                                            <fieldset style='height:60px'>
					                                                <label >Justificativa
					                                                </label>
					                                                   <div>
					                                                       <select name='idJustificativa' class="Valid[Required] Description[Justificativa]"></select>
					                                                   </div>
					                                            </fieldset>
					                                         </div>
						                                    <div class="col_10">
						                                        <fieldset style='height:60px'>
							                                        <div style="padding:20px">
										                                <button type='button' name='btnSave2' class="light"
										                                    onclick='JANELA_AGUARDE_MENU.show();document.form.fireEvent("notification_getById");'>
										                                    <img
										                                        src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										                                    <span>getById
										                                    </span>
										                                </button>
							                                        </div>
						                                        </fieldset>
						                                    </div>
						                                    <div class="col_10">
						                                        <fieldset style='height:60px'>
							                                        <div style="padding:20px">
										                                <button type='button' name='btnSave3' class="light"
										                                    onclick='JANELA_AGUARDE_MENU.show();document.form.fireEvent("notification_getReasons");'>
										                                    <img
										                                        src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										                                    <span>getReasons
										                                    </span>
										                                </button>
							                                        </div>
						                                        </fieldset>
						                                    </div>
						                                    <div class="col_10">
						                                        <fieldset style='height:60px'>
							                                        <div style="padding:20px">
										                                <button type='button' name='btnSave4' class="light"
										                                    onclick='JANELA_AGUARDE_MENU.show();document.form.fireEvent("notification_feedback");'>
										                                    <img
										                                        src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										                                    <span>feedback
										                                    </span>
										                                </button>
							                                        </div>
						                                        </fieldset>
						                                    </div>
						                                  </div>
						                              </div>
                                                    	<div class="col_100">
					                                         <div class="col_80">
					                                            <fieldset style='height:100px'>
					                                                <label >Descrição
					                                                </label>
					                                                   <div>
					                                                       <textarea name="descricaoPortal" id="descricaoPortal" cols='200' rows='4' class="Valid[Required] Description[Descrição]"></textarea>
					                                                   </div>
					                                            </fieldset>
					                                         </div>
						                                    <div class="col_20">
						                                        <fieldset style='height:100px'>
							                                        <div style="padding:20px">
										                                <button type='button' name='btnSave3' class="light"
										                                    onclick='JANELA_AGUARDE_MENU.show();document.form.fireEvent("notification_new");'>
										                                    <img
										                                        src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										                                    <span>new
										                                    </span>
										                                </button>
							                                        </div>
						                                        </fieldset>
						                                    </div>
						                                 </div>
						                              </div>                                                 </div>
                                            </div>
                                       		</div>
                                        </div>
                                    </div>
                                    <div style="clear: both"></div>
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

      <div id="POPUP_RESULTADO" title="Resultado"  style="overflow: hidden;">
            <div id='divResultado' class="columns clearfix">
           </div>
      </div>

	<%@include file="/include/footer.jsp"%>
</body>

</html>