<%@page import="br.com.citframework.util.UtilStrings"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<%
	response.setCharacterEncoding("ISO-8859-1");

	String noVoltar = request.getParameter("noVoltar");
	if (noVoltar == null){
		noVoltar = "false";
	}
	noVoltar = UtilStrings.nullToVazio(noVoltar);
	pageContext.setAttribute("noVoltar", noVoltar);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/titleComum/titleComum.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

	<link type="text/css" rel="stylesheet" href="css/agendaAtvPeriodicas.css"/>



<%-- <script type='text/javascript' src='${ctx}/fullcalendar/jquery/jquery-1.5.2.min.js'></script>
<script type='text/javascript' src='${ctx}/fullcalendar/jquery/jquery-ui-1.8.11.custom.min.js'></script> --%>
<script type='text/javascript' src='${ctx}/fullcalendar/fullcalendar.min.js'></script>
<script type='text/javascript' src='${ctx}/fullcalendar/fullcalendar.js'></script>
<script type="text/javascript" src='${ctx}/js/UploadUtils.js'></script>



<link rel='stylesheet' type='text/css' href='${ctx}/fullcalendar/fullcalendar.css' />
<link rel='stylesheet' type='text/css' href='${ctx}/fullcalendar/fullcalendar.print.css' media='print' />
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>


<!-- Area de JavaScripts -->

<h2 style="background-color: white !important;"><fmt:message key="agendaAtividade.agendaAtividade" /> </h2>
<div id='loading' style='display:none; background-color: red; color: white'><b><fmt:message key="citcorpore.comum.aguardecarregando" /></b></div>
	<form name='formParm' action='${ctx}/pages/agendaAtvPeriodicas/agendaAtvPeriodicas'>
	<div style="background-color: white !important;">
		<table>
	         <tr>
	            <td class="campoEsquerdaSemTamanho"><fmt:message key="gerenciaservico.agendaratividade.grupo" />*</td>
	            <td>
	            	<select name='idGrupoAtvPeriodica' id='idGrupoAtvPeriodica' onchange='mudaGrupo(this.value)'>
	            	</select>

	            </td>
	          	<td>
	            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            </td>
	            <td align="center"><fmt:message key="gerenciaservico.grupoPesquisa" /></td>
	            <td>
	            	<select size="1" name="idGrupoPesquisa" id="idGrupoPesquisa" onchange='mudaGrupoPesquisa(this.value)'>
	            		<option selected value="0">			<fmt:message key="citcorpore.comum.todos"/></option>
	            		<option 		 value="1">		   	<fmt:message key="gerenciaIncidente"/></option>
	            		<option 		 value="2">		   	<fmt:message key="requisicaoMudanca"/></option>
	            		<option 		 value="3">		   	<fmt:message key="requisicaoLiberacao"/></option>
	            		<option 		 value="4">		   	<fmt:message key="menu.nome.gerenciaProblema"/></option>
	            	</select>
	            </td>
	            <td>
	            	&nbsp;
	            </td>
	            <td>
	            	<img src="${ctx}/imagens/refresh.png" border="0" style='cursor:pointer' onclick='refreshEvents()' title="refresh" />
	            </td>
	            <%if (!noVoltar.equalsIgnoreCase("true")){%>
                <td width='700px' style='text-align:right'>
                   <img border="0" style=" cursor:pointer; " onclick="voltar()" alt="Voltar" title="Retornar ao menu principal" src="${ctx}/imagens/btnvoltar.gif">&nbsp;
                </td>
                <%}%>
	         </tr>
         </table>
	</div>
	</form>
	<div id='calendar' style='background-color: white'></div>

	<%@include file="../../include/rodape.jsp"%>


<div id="POPUP_REGISTRO1" title="<fmt:message key='citcorpore.atividadePeriodica.registroAtividade'/>">
	<form name='form' action='${ctx}/pages/execucaoAtividadePeriodica/execucaoAtividadePeriodica'>
		<input type='hidden' name='idExecucaoAtividadePeriodica'/>
		<input type='hidden' name='idAtividadePeriodica'/>
        <input type='hidden' name='idProgramacaoAtividade'/>
        <div class="tabber" id='tabTela'>
             <div id="tabRegistro">
       			<h4><fmt:message key="agenda.registroExecucaoAtividade" /></h4>
       			<div class="columns clearfix">
       			  <div id="atividadeOS" style="display: none;">
  					<div class="col_66">
  						<div class='col_50'>
							<label  class="" style="cursor: pointer;"><fmt:message key="agenda.numeroOS" /></label>
							<div>
								<input type='text' name='numeroOS' size="90" readonly="readonly" class="text"/>
							</div>
						</div>
						<div class='col_50'>
							<label  class="" style="cursor: pointer;"><fmt:message key="agenda.descricaoAtividadeOS" /></label>
							<div>
								<input type='text' name='descricaoAtividadeOS' size="90" readonly="readonly" class="text"/>
							</div>
						</div>
					</div>
				  </div>
				  <div class="col_100">
				  	<label  class="" style="cursor: pointer;"><fmt:message key="agenda.atividade" /></label>
						<div>
							<input type='text' name='titulo' size="90" readonly="readonly" class="text"/>
						</div>
				  </div>
				  <div class="col_33" >
				  	<label  class="" style="cursor: pointer;"><fmt:message key="agenda.dataProgramada" /></label>
						<div>
                         <!-- Maycon 30/10/2013 - 09:55 -  retirada o class datepicker para ter somente visualização -->
							 <input type='text' name='dataProgramada' style="width: 120px !important;"  size="10" maxlength="10" class="Format[Date]" readonly='readonly' />
						</div>
				  </div>
				    <div class="col_33" >
				    	<label  class="" style="cursor: pointer;"> <fmt:message key="agenda.horaProgramada" /></label>
						<div>
							 <input type='text' name='horaProgramada' style="width: 100px !important;"  size="5" maxlength="5" class='Format[Hora] text' readonly='readonly'/>&nbsp;&nbsp;
						</div>

				  </div>
				    <div class="col_33" >
				    <label  >&nbsp;</label>
					    <div>
					    	<button type='button' id="btnVerDetTec" name='btnVerDetTec' class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" onclick="visualizarOrientacoes()">
								<%-- <img src="${ctx}/template_new/images/icons/small/grey/pencil.png"> --%>
								<span style="font-size: 12px !important; font-color: white;"><fmt:message key="scripts.orientacaoTecnica" /></span>
							</button>
						</div>

				  </div>
				  	<div class='col_33'>
						<label  class="" style="cursor: pointer;"><fmt:message key="agenda.situacao" /></label>
						<div>
							<label><input type='radio' name='situacao' value="E" onclick="configuraMotivoSuspensao('E')"/> <fmt:message key="citcorpore.comum.emExecucao" /> </label>
							<label> <input type='radio' name='situacao' value="S" onclick="configuraMotivoSuspensao('S')"/> <fmt:message key="citcorpore.comum.suspenso" /> </label>
							<label>  <input type='radio' name='situacao' value="F" onclick="configuraMotivoSuspensao('F')"/> <fmt:message key="cronograma.executado" /></label>
						</div>
					</div>
					<div class='col_50'>
					<label  class="" style="cursor: pointer;"><fmt:message key="agenda.detalhamento" /></label>
						<div>
							<textarea rows="3" name="detalhamento" cols="70" style="border: 1px solid black"></textarea>
						</div>

					</div>
					<!-- Desenvolvedor: Pedro Lino - Data: 23/10/2013 - Horário: 17:38 - ID Citsmart: 120948 -
						* Motivo/Comentário: Campo motivo com layout antigo, retirado style -->
					 <div id='divMotivoSuspensao' style='display:none'>
                         <div class='col_50'>
                            <label style="cursor: pointer;" onclick="abrirPopupMotivoSuspensaoAtividade();">
								<fmt:message key="agenda.motivo"/>
								<img  src="<%=br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" />
							</label>

                                <div>
                                	<select name="idMotivoSuspensao" id="idMotivoSuspensao" size="1px"></select>
                                </div>
                         </div>
                         <div class='col_50'>
                             <label class="campoEsquerdaSemTamanho"><fmt:message key="agenda.complemento" /> </label>
                             <div>
                                  <textarea rows="3" name="complementoMotivoSuspensao" cols="70" style="border: 1px solid black"></textarea>
                             </div>
                         </div>

		           </div>
		             <div class="col_25" >
				  	<label  class="campoObrigatorio" style="cursor: pointer;"><fmt:message key="agenda.dataExecucao" /></label>
						<div>
							 <input type='text' name='dataExecucao' style="width: 100px !important;" size="10" maxlength="10" class="Format[Date] Valid[Required,Date] Description[citcorpore.comum.dataRealExecucao] text datepicker"/>
						</div>
				  </div>
				    <div class="col_25" >
				    	<label  class="campoObrigatorio" style="cursor: pointer;"><fmt:message key="agenda.horaExecucao" /></label>
						<div>
							 <input type='text' name='horaExecucao' style="width: 100px !important;" size="5" maxlength="5" class='Format[Hora] Valid[Required,Hora] Description[citcorpore.comum.horaRealExecucao] text'/>
						</div>

				  </div>
       			</div>
				 <table>

                </table>
            </div>
        </div>

	</form>
	<form name='formUpload' action='${ctx}/pages/execucaoAtividadePeriodica/execucaoAtividadePeriodica' enctype="multipart/form-data">
            <div id="tabAnexos" style="border: 1px solid black !important;">
            <%-- 	<table>
            		<tr>
            			<td>
            				<img src='${ctx}/imagens/file.png' width="16" height="16" border="0"/>
            			</td>
            			<td>
                			<b><fmt:message key="agenda.anexos" /></b>
            			</td>
            		</tr>
            	</table> --%>
                <div id="anexos">
                    <div id="anexoGeral">
                        <cit:uploadControl style="height:80px;width:670px;border:12px solid white"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/upload/upload.load" disabled="false"/>
                    </div>
                </div>
            </div>
	</form>
	   <table cellpadding='0' cellspacing='0' width='100%'>
           <tr>
               <td style='text-align:right'>
                   <!--  <input type='button' name='btnGravar' value='Gravar' onclick='gravarForm();'/>
                    <input type='button' name='btnFechar' value='Fechar' onclick='$("#POPUP_REGISTRO").hide();'/> -->

                    <button type='button' id="btnGravar" name='btnGravar' style="margin-top: 5px; margin-left: 3px;" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" onclick="gravarForm()">
				<%-- 	<img src="${ctx}/template_new/images/icons/small/grey/pencil.png"> --%>
						<span style="font-size: 12px !important; "><fmt:message key="citcorpore.comum.gravar" /></span>
					</button>

				<button type='button' id="btnVerDetTec" name='btnVerDetTec' style="margin-top: 5px; margin-left: 3px; " class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" onclick='$("#POPUP_REGISTRO1").dialog("close");'">
				  <%-- <img src="${ctx}/template_new/images/icons/small/grey/clear.png"> --%>
					<span style="font-size: 12px !important; "><fmt:message key="citcorpore.ui.botao.rotulo.Fechar" /></span>
				</button>
               </td>
           </tr>
        </table>
</div>

<div id="POPUP_ORIENTACAO" >
	<div id='divOrientacao' style='width: 100%; height: 390px; overflow: auto'>
	</div>
</div>

<div id="POPUP_NOVOMOTIVOSUSPENSAOATIVIDADE"  style="overflow: hidden;" title="<fmt:message key="motivoSuspensaoAtividade.motivoSuspensaoAtividade"/>">
	<iframe id='iframeNovoMotivoSuspensaoAtividade' src='about:blank' width="100%" height="100%">
	</iframe>
</div>

<script type="text/javascript">
	var noVoltar = "${noVoltar}";
</script>

<script type="text/javascript" src="js/agendaAtvPeriodicas.js"></script>
</body>
</html>
