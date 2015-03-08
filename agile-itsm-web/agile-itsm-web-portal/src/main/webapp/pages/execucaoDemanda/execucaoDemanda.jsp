<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.bean.ExecucaoDemandaDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
%>
<html>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<head>
	<%@include file="/include/titleComum/titleComum.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
<cit:lookupField formName='formTransferir' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='100' left='184' len='590' heigth='500' javascriptCode='true' htmlCode='false' />
<cit:lookupField formName='formAtribuir' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO_ATRIBUIR' top='100' left='184' len='590' heigth='500' javascriptCode='true' htmlCode='false' />

<script>
	HTMLUtils.setColorOn('#48d1cc');

	addEvent(window, "load", load, false);
	function load(){

	}
</script>

<!-- Area de JavaScripts -->
<script>
	function LOOKUP_DEMANDA_select(id,desc){
		document.form.restore({idEmpregado:id});
	}
	function LOOKUP_EMPREGADO_select(id,desc){
	}
	function LOOKUP_EMPREGADO_ATRIBUIR_select(id,desc){
		document.formAtribuir.idEmpregadoExecutor.value = id;
	}
	function CHAMA_Opcoes(obj){
		document.getElementById('txtFluxo').value = obj.nomeFluxo;
		document.getElementById('txtEtapa').value = obj.nomeEtapa;
		document.getElementById('txtAtividade').value = obj.nomeAtividade;
		document.getElementById('txtDetalhamento').value = obj.detalhamento;

		document.getElementById('idExecucao').value = obj.idExecucao;
		document.getElementById('idFluxoSelecionado').value = obj.idFluxo;
		document.getElementById('idAtividade').value = obj.idAtividade;
		document.getElementById('idDemandaSelecionado').value = obj.idDemanda;
		document.getElementById('idProjetoSelecionado').value = obj.idProjeto;

		document.getElementById('txtPrazoFinal').value = obj.terminoPrevistoStr;
		document.getElementById('txtQtdeHorasPrevExecucao').value = obj.qtdeHorasStr;

		POPUP_OPCOES.showInYPosition({top:30});;
	}
	function paralisarDemandaCliente(){
		if (confirm('Deseja realmente paralisar a demanda (Motivo: Cliente)')){
			document.formDiversos.fireEvent('paralisarDemandaCliente');
		}
	}
	function paralisarDemandaInterno(){
		if (confirm('Deseja realmente paralisar a demanda (Motivo: Processo Interno)')){
			document.formDiversos.fireEvent('paralisarDemandaInterno');
		}
	}
	function emExecucao(){
		if (confirm('Deseja realmente alterar a situação para "Em Execução" ')){
			document.formDiversos.fireEvent('alterarSituacaoEmExecucao');
		}
	}
	function finalizar(){
		if (confirm('Deseja realmente Finalizar \n\nQuando você finalizar, será enviado para a próxima atividade do fluxo de trabalho!')){
			document.formDiversos.fireEvent('finalizar');
		}
	}
	function atribuirAtividade(){
		if (confirm('Confirma atribuição de Atividade ')){
			document.formAtribuir.fireEvent('atribuir');
		}
	}
	function verificaFinalizacao(){
		var ok = true;
		for(var i = 0; i < document.formFinalizarAtividade.chkCheckListFinalizar.length; i++){
			if (!document.formFinalizarAtividade.chkCheckListFinalizar[i].checked){
				ok = false;
			}
		}
		if (!ok){
			alert('Você não preencheu o Checklist de finalização, por favor, leia e preencha o checklist!');
			return;
		}
		finalizar();
	}
	function registrarTimeSheet(){
		if (confirm('Tem certeza que deseja registrar o Timesheet ')){
			if (document.formRegistrarTimeSheet.validate()){
				document.formRegistrarTimeSheet.fireEvent('registrarTimeSheet');
			}
		}
	}
	function registrarOcorrencia(){
		if (confirm('Tem certeza que deseja registrar a ocorrência ')){
			document.formRegistrarOcorrencia.fireEvent('registrarOcorrencia');
		}
	}
	function consultarTimeSheet(){
		document.formConsultarTimeSheet.fireEvent('consultarTimeSheet');
		POPUP_CONSULTAR_TIMESHEET.showInYPosition({top:100});
	}
	function consultarOcorrencia(){
		document.formConsultarOcorrencia.fireEvent('consultarOcorrencia');
		POPUP_CONSULTAR_OCORRENCIA.showInYPosition({top:100});
	}
	function consultarHistorico(){
		document.formConsultarHistorico.fireEvent('consultarHistorico');
		POPUP_CONSULTAR_HISTORICO.showInYPosition({top:100});
	}
	function CHAMA_AtualizaResposta(row, obj){
		document.getElementById('idOcorrenciaAtualizaResposta').value = obj.idOcorrencia;
		document.getElementById('idDemandaAtualizaResposta').value = obj.idDemanda;
		if (obj.respostaOcorrencia == null || obj.respostaOcorrencia == ''){
			POPUP_ATUALIZAR_RESPOSTA_OCORR.showInYPosition({top:100});
			document.getElementById('respostaOcorrenciaResposta').value = '';
			document.getElementById('respostaOcorrenciaResposta').focus();
		}else{
			alert('Esta ocorrência já está respondida! Não pode haver alteração na resposta!');
		}
	}
	function atualizaRespostaOcorrencia(){
		if (StringUtils.isBlank(document.getElementById('respostaOcorrenciaResposta').value)){
			alert('Preencha a reposta da ocorrência!');
			document.getElementById('respostaOcorrenciaResposta').focus();
			return;
		}
		if (confirm('Deseja realmente registrar a resposta \n\nApós registro da resposta não poderá haver alteração na ocorrência!')){
			document.formAtualizaOcorr.fireEvent('registrarRespostaOcorrencia');
		}
	}
	function atualizarLista(){
		JANELA_AGUARDE_MENU.show();
		window.location='${ctx}/pages/execucaoDemanda/execucaoDemanda.load';
	}
</script>

<%
Collection col = null;
col = (Collection)request.getAttribute("colecao");
%>
<div id="paginaTotal">
	<div id="areautil">
		<div id="formularioIndex">
       		<div id=conteudo>
				<table width="100%">
					<tr>
						<td width="100%">
							<div id='areaUtilAplicacao'>
								<!-- ## AREA DA APLICACAO ## -->
								<input type='button' name='btnAtualizar' value='Atualizar' onclick='atualizarLista()'/>
							  	<div class="tabber">
							    	<div class="tabbertab" id="tabCadastro">
										<h2>Minha Lista de Tarefas/Meus Grupos</h2>
										<table id='tblMinhasTarefas' width="98%">
											<tr>
												<td class="linhaSubtituloGrid">
												</td>
												<td class="linhaSubtituloGrid">
													<b>OS</b>
												</td>
												<td class="linhaSubtituloGrid">
													<b>Fluxo</b>
												</td>
												<td class="linhaSubtituloGrid">
													<b>Etapa</b>
												</td>
												<td class="linhaSubtituloGrid">
													<b>Atividade</b>
												</td>
												<td class="linhaSubtituloGrid">
													<b>Prazo Final</b>
												</td>
												<td class="linhaSubtituloGrid">
													<b>Prioridade</b>
												</td>
												<td class="linhaSubtituloGrid">
													<b>Projeto</b>
												</td>
												<td class="linhaSubtituloGrid">
													<b>Situa&ccedil;&atilde;o</b>
												</td>
												<td class="linhaSubtituloGrid">
													<b>Nome</b>
												</td>
												<td class="linhaSubtituloGrid">
													<b>Qtde Horas</b>
												</td>
											</tr>
											<%if (col != null){%>
												<%for(Iterator it = col.iterator(); it.hasNext();){ %>
													<%ExecucaoDemandaDTO execucaoDemandaDTO = (ExecucaoDemandaDTO)it.next();%>
													<tr>
														<td>
															<a href='#' onclick='CHAMA_Opcoes(ObjectUtils.deserializeObject("<%=execucaoDemandaDTO.getObjSerializado()%>"))'><%=execucaoDemandaDTO.getImagem()%></a>
														</td>
														<td>
															<%=UtilStrings.nullToVazio(execucaoDemandaDTO.getNumeroOS())%>/<%if (execucaoDemandaDTO.getAnoOS() != null) {out.print(execucaoDemandaDTO.getAnoOS());}%>
														</td>
														<td>
															<%=execucaoDemandaDTO.getNomeFluxo()%>
														</td>
														<td>
															<%=execucaoDemandaDTO.getNomeEtapaHTML()%>
														</td>
														<td>
															<%=execucaoDemandaDTO.getNomeAtividadeHTML()%>
														</td>
														<td>
															<%=UtilStrings.nullToVazio(execucaoDemandaDTO.getExpectativaFimStr())%>
														</td>
														<td>
															<%=execucaoDemandaDTO.getPrioridadeDescHTML()%>
														</td>
														<td>
															<%=UtilStrings.nullToVazio(execucaoDemandaDTO.getNomeProjeto())%>
														</td>
														<td>
															<%=execucaoDemandaDTO.getSituacaoDescHTML()%>
														</td>
														<td>
															<%=UtilStrings.nullToVazio(execucaoDemandaDTO.getNome())%>
														</td>
														<td>
															<%=UtilStrings.nullToVazio(execucaoDemandaDTO.getQtdeHorasStr())%>
														</td>
													</tr>
												<%}%>
											<%}%>
										</table>
									</div>
									<div class="tabbertab" id="tabListagem">
										<h2>Pesquisa de Tarefas</h2>
										 	<form name='formPesquisa' action='${ctx}/pages/execucaodemanda/execucaoDemanda'>
										 		<input type='hidden' name='idDemanda' id='idDemandaPesquisa'/>
											  	<table id="tabFormulario" cellpadding="0" cellspacing="0">
											         <tr>
											            <td class="campoEsquerda">Fluxo de Tratamento:*</td>
											            <td>
											            	<select name='idFluxo' class="Valid[Required] Description[Fluxo]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Cliente:*</td>
											            <td>
											            	<select name='idCliente' class="Valid[Required] Description[Clente]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Projeto:*</td>
											            <td>
											            	<select name='idProjeto' class="Valid[Required] Description[Projeto]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Tipo de Demanda:*</td>
											            <td>
											            	<select name='idTipoDemanda' class="Valid[Required] Description[Tipo de Demanda]">
											            	</select>
											            </td>
											         </tr>
												 <tr>
										            <td colspan="2" class="campoObrigatorio">* Campos com preenchimento obrigat&oacute;rio</td>
										         </tr>
										         <tr>
										         	<td colspan='2'>
									         		<input type='button' name='btnGravar' value='Gravar' onclick='document.form.save();'/>
									         		<input type='button' name='btnLimpar' value='Limpar' onclick='document.form.clear();'/>
									         	</td>
									         </tr>
										</table>
									</form>
								</div>
							</div>
							<!-- ## FIM - AREA DA APLICACAO ## -->
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<%@include file="../../include/rodape.jsp"%>
</div>

	<!-- DETALHAMENTO DOS SETORES -->
	<cit:janelaPopup id="POPUP_OPCOES" title="Op&ccedil;ões" style="display:none;top:200px;width:650px;left:200px;height:530px;position:absolute;">
		<form name='formDiversos' action='${ctx}/pages/execucaodemanda/execucaoDemanda'>
			<input type='hidden' name='idExecucao'/>
			<input type='hidden' name='idFluxoSelecionado'/>
			<input type='hidden' name='idAtividade'/>
			<input type='hidden' name='idDemanda' id='idDemandaSelecionado'/>
			<input type='hidden' name='idProjeto' id='idProjetoSelecionado'/>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td>
						Fluxo:
					</td>
					<td>
						<input type='text' name='txtFluxo' size='50' readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>
						Etapa:
					</td>
					<td>
						<input type='text' name='txtEtapa' size='50' readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>
						Atividade:
					</td>
					<td>
						<input type='text' name='txtAtividade' size='50' readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>
						Detalhamento da demanda:
					</td>
					<td>
						<textarea rows="6" name="txtDetalhamento" cols="70" readonly="readonly"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						Prazo Final:
					</td>
					<td>
						<input type='text' name='txtPrazoFinal' size='10' readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>
						Qtde Horas p/ Execu&ccedil;&atilde;o:
					</td>
					<td>
						<input type='text' name='txtQtdeHorasPrevExecucao' size='10' readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" style='text-align: center;' class="linhaSubtituloGrid">
						<b>Ações</b>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td style='width:300px'>
									<a href='#' onclick="document.formAtribuir.idExecucaoAtribuir.value = document.getElementById('idExecucao').value; document.formAtribuir.idFluxo.value = document.getElementById('idFluxoSelecionado').value; document.formAtribuir.idAtividade.value = document.getElementById('idAtividade').value; document.formAtribuir.idDemanda.value = document.getElementById('idDemandaSelecionado').value; POPUP_ATRIBUIR.showInYPosition({top:100});">Atribuir Execu&ccedil;&atilde;o</a>
								</td>
								<td>
									<a href='#' onclick="document.formConsultarHistorico.idDemanda.value = document.getElementById('idDemandaSelecionado').value; consultarHistorico()">Consultar Hist&oacute;rico</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						&nbsp;
					</td>
				</tr>
				<!-- Sera implementada no futuro
				<tr>
					<td colspan='2'>
						<input type='button' name='btnTransferirExecucao' value='Transferir Execução' onclick="POPUP_TRANFERIR.showInYPosition({top:100});"/>
					</td>
				</tr>
				 -->
				<tr>
					<td colspan='2'>
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td style='width:300px'>
									<a href='#' onclick='emExecucao()'>Alterar Situa&ccedil;&atilde;o p/ Em Execu&ccedil;&atilde;o</a>
								</td>
								<td>
									<a href='#' onclick="document.formConsultarOcorrencia.idDemanda.value = document.getElementById('idDemandaSelecionado').value; consultarOcorrencia()">Consultar Ocorr&ecirc;ncias</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td style='width:300px'>
									<a href='#' onclick='paralisarDemandaCliente()'>Paralisar - Aguardando Cliente</a>
								</td>
								<td>
									<a href='#' onclick="document.formConsultarTimeSheet.idDemanda.value = document.getElementById('idDemandaSelecionado').value; consultarTimeSheet()">Consultar TimeSheet</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<a href='#' onclick='paralisarDemandaInterno()'>Paralisar - Processo Interno</a>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<a href='#' onclick="document.formRegistrarTimeSheet.idDemanda.value = document.getElementById('idDemandaSelecionado').value; document.formRegistrarTimeSheet.idProjeto.value = document.getElementById('idProjetoSelecionado').value; POPUP_TIMESHEET.showInYPosition({top:100});">Registrar TimeSheet</a>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<a href='#' onclick="document.formRegistrarOcorrencia.idDemanda.value = document.getElementById('idDemandaSelecionado').value; document.formRegistrarOcorrencia.idProjeto.value = document.getElementById('idProjetoSelecionado').value; POPUP_OCORRENCIA.showInYPosition({top:100});">Registrar Ocorr&ecirc;ncia</a>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td style='width:300px'>
									<a href='#' onclick='POPUP_FINALIZAR_ATIVIDADE.showInYPosition({top:100});'>Finalizar Esta Atividade</a>
								</td>
								<td>
									<input type='button' name='btnFechar' value='Fechar' onclick="POPUP_OPCOES.hide()"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</cit:janelaPopup>

	<cit:janelaPopup id="POPUP_TRANFERIR" modal="true" title="Op&ccedil;ões" style="display:none;top:200px;width:600px;left:200px;height:400px;position:absolute;">
		<form name='formTransferir' action='${ctx}/pages/execucaodemanda/execucaoDemanda'>
			<table>
				<tr>
					<td>
						Transferir para:
					</td>
					<td>
						<cit:lookupField formName='formTransferir' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='100' left='184' len='590' heigth='500' javascriptCode='false' htmlCode='true' />
					</td>
				</tr>
				<tr>
					<td>
						Detalhamento:
					</td>
					<td>
						<textarea rows="14" name="txtDetalhamento" cols="70"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						Qtde Horas previstas p/ execu&ccedil;&atilde;o:
					</td>
					<td>
						<input type='text' name='txtQtdeHoras' size='4'/>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<input type='button' name='btnTranferir' value='Transferir'/>
						<input type='button' name='btnFecharTransferir' value='Fechar' onclick="POPUP_TRANFERIR.hide()"/>
					</td>
				</tr>
			</table>
		</form>
	</cit:janelaPopup>

	<cit:janelaPopup id="POPUP_ATRIBUIR" modal="true" title="Atribuir Execu&ccedil;&atilde;o" style="display:none;top:200px;width:630px;left:200px;height:200px;position:absolute;">
		<form name='formAtribuir' action='${ctx}/pages/execucaodemanda/execucaoDemanda'>
			<input type='hidden' name='idEmpregadoExecutor' class="Valid[Required]"/>
			<input type='hidden' name='idExecucaoAtribuir'/>
			<input type='hidden' name='idFluxo' id='idFluxoAtribuir'/>
			<input type='hidden' name='idAtividade' id='idAtividadeAtribuir'/>
			<input type='hidden' name='idDemanda' id='idDemandaAtribuir'/>
			<table>
				<tr>
					<td>
						Atribuir para:
					</td>
					<td>
						<cit:lookupField formName='formAtribuir' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO_ATRIBUIR' top='100' left='184' len='590' heigth='500' javascriptCode='false' htmlCode='true' />
					</td>
				</tr>
				<tr>
					<td>
						Qtde Horas previstas p/ execu&ccedil;&atilde;o:
					</td>
					<td>
						<input type='text' name='qtdeHoras' size='4' class="Format[Numero] Valid[Required]"/>
					</td>
				</tr>
				<tr>
					<td>
						Prazo Final:
					</td>
					<td>
						<input type='text' name='terminoPrevisto' size='10' maxlength="10" class="Valid[Required,Data] Format[Data] Description[Prazo Final]" />
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<input type='button' name='btnAtribuir' value='Atribuir' onclick="atribuirAtividade()" />
						<input type='button' name='btnFecharAtribuir' value='Fechar' onclick="POPUP_ATRIBUIR.hide()"/>
					</td>
				</tr>
			</table>
		</form>
	</cit:janelaPopup>

	<cit:janelaPopup id="POPUP_TIMESHEET" modal="true" title="Registrar TimeSheet" style="display:none;top:200px;width:620px;left:200px;height:300px;position:absolute;">
		<form name='formRegistrarTimeSheet' action='${ctx}/pages/timeSheet/timeSheet'>
			<input type='hidden' name='idProjeto' id='idProjetoTimeSheet'/>
			<input type='hidden' name='idDemanda' id='idDemandaTimeSheet'/>
			<table>
				<tr>
					<td>
						Data:
					</td>
					<td>
						<input type='text' name='data' id='dataTimeSheet' size='10' maxlength="10" class="Format[Data] Valid[Required,Data]"/>
					</td>
				</tr>
				<tr>
					<td>
						Qtde Horas:
					</td>
					<td>
						<input type='text' name='qtdeHoras' id='qtdeHorasTimeSheet' size='7' class="Format[MOEDA] Valid[Required]"/>
					</td>
				</tr>
				<tr>
					<td>
						Detalhamento:
					</td>
					<td>
						<textarea rows="5" cols="60" name='detalhamento' id='detalhamentoTimeSheet' class="Valid[Required]"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<input type='button' name='btnRegistrarTimeSheet' value='Registrar' onclick='registrarTimeSheet()'/>
						<input type='button' name='btnFecharRegistrar' value='Fechar' onclick="POPUP_TIMESHEET.hide()"/>
					</td>
				</tr>
			</table>
		</form>
	</cit:janelaPopup>

	<cit:janelaPopup id="POPUP_OCORRENCIA" modal="true" title="Registrar Ocorr&ecirc;ncia" style="display:none;top:200px;width:620px;left:200px;height:400px;position:absolute;">
		<form name='formRegistrarOcorrencia' action='${ctx}/pages/ocorrencia/ocorrencia'>
			<input type='hidden' name='idProjeto' id='idProjetoOcorrencia'/>
			<input type='hidden' name='idDemanda' id='idDemandaOcorrencia'/>
			<table>
				<tr>
					<td>
						Data:
					</td>
					<td>
						<input type='text' name='data' id='dataTimeSheet' size='10' maxlength="10" class="Format[Data]"/>
					</td>
				</tr>
				<tr>
					<td>
						Tipo de Ocorr&ecirc;ncia:
					</td>
					<td>
						<select name="tipoOcorrencia">
							<option value="O">Ocorr&ecirc;ncia Diversa</option>
							<option value="D">Dúvida</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						Ocorr&ecirc;ncia:
					</td>
					<td>
						<textarea name="ocorrencia" rows="5" cols="70"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						Resposta da Ocorr&ecirc;ncia:
					</td>
					<td>
						<textarea name="respostaOcorrencia" rows="5" cols="70"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<input type='button' name='btnRegistrarOcorrencia' value='Registrar' onclick='registrarOcorrencia()'/>
						<input type='button' name='btnFecharRegistrarOcorr' value='Fechar' onclick="POPUP_OCORRENCIA.hide()"/>
					</td>
				</tr>
			</table>
		</form>
	</cit:janelaPopup>

	<cit:janelaPopup id="POPUP_FINALIZAR_ATIVIDADE" modal="true" title="Finalizar Atividade" style="display:none;top:200px;width:600px;left:200px;height:200px;position:absolute;">
		<form name='formFinalizarAtividade'>
			<table>
				<tr>
					<td>
						<b>CheckList p/ finaliza&ccedil;&atilde;o de Atividade:</b>
					</td>
				</tr>
				<tr>
					<td>
						<input type='checkbox' name='chkCheckListFinalizar'> Voc&ecirc; j&aacute; registrou o timesheet corretamente para esta atividade
					</td>
				</tr>
				<tr>
					<td>
						<input type='checkbox' name='chkCheckListFinalizar'> Voc&ecirc; cumpriu todos os passos conforme a metodologia estabelece para este tipo de atividade
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<input type='button' name='btnFimFinalizar' value='Finalizar' onclick='verificaFinalizacao()'>
						<input type='button' name='btnFimFechar' value='Fechar' onclick='POPUP_FINALIZAR_ATIVIDADE.hide()'>
					</td>
				</tr>
			</table>
		</form>
	</cit:janelaPopup>

	<cit:janelaPopup id="POPUP_CONSULTAR_TIMESHEET" modal="true" title="Consultar TimeSheet da demanda" style="display:none;top:200px;width:600px;left:200px;height:400px;position:absolute;">
		<form name='formConsultarTimeSheet' action='${ctx}/pages/timeSheet/timeSheet'>
			<input type='hidden' name='idDemanda' id='idDemandaConsultaTimeSheet'/>
			<div id='divScrollConsultaTS' style='overflow:auto;height:380px;width:580px;'>
				<table id='tabelaConsultaTimeSheet' width="100%">
					<tr>
						<td>
							Data
						</td>
						<td>
							Qtde Horas
						</td>
						<td>
							Colaborador
						</td>
						<td>
							Detalhamento
						</td>
					</tr>
				</table>
			</div>
		</form>
	</cit:janelaPopup>

	<cit:janelaPopup id="POPUP_CONSULTAR_OCORRENCIA" modal="true" title="Consultar Ocorr&ecirc;ncias da demanda" style="display:none;top:200px;width:600px;left:200px;height:400px;position:absolute;">
		<form name='formConsultarOcorrencia' action='${ctx}/pages/ocorrencia/ocorrencia'>
			<input type='hidden' name='idDemanda' id='idDemandaConsultaOcorrencia'/>
			<div id='divScrollConsultaOcorr' style='overflow:auto;height:380px;width:580px;'>
				<table id='tabelaConsultaOcorr' width="100%">
					<tr>
						<td>
							Data
						</td>
						<td>
							Tipo de Ocorr&ecirc;ncia
						</td>
						<td>
							Ocorr&ecirc;ncia
						</td>
						<td>
							Resposta
						</td>
						<td>
							Colaborador
						</td>
					</tr>
				</table>
			</div>
		</form>
	</cit:janelaPopup>

	<cit:janelaPopup id="POPUP_CONSULTAR_HISTORICO" modal="true" title="Consultar Histórico da demanda" style="display:none;top:200px;width:600px;left:200px;height:400px;position:absolute;">
		<form name='formConsultarHistorico' action='${ctx}/pages/historicoExecucao/historicoExecucao'>
			<input type='hidden' name='idDemanda' id='idDemandaConsultaHistorico'/>
			<div id='divScrollConsultaHist' style='overflow:auto;height:380px;width:580px;'>
				<table id='tabelaConsultaHist' width="100%">
					<tr>
						<td>
							Data
						</td>
						<td>
							Hora
						</td>
						<td>
							Colaborador
						</td>
						<td>
							Detalhamento
						</td>
						<td>
							Situa&ccedil;&atilde;o
						</td>
					</tr>
				</table>
			</div>
		</form>
	</cit:janelaPopup>

	<cit:janelaPopup id="POPUP_ATUALIZAR_RESPOSTA_OCORR" modal="true" title="Atualizar Resposta da Ocorr&ecirc;ncia" style="display:none;top:200px;width:650px;left:200px;height:200px;position:absolute;">
		<form name='formAtualizaOcorr' action='${ctx}/pages/ocorrencia/ocorrencia'>
			<input type='hidden' name='idOcorrencia' id='idOcorrenciaAtualizaResposta'/>
			<input type='hidden' name='idDemanda' id='idDemandaAtualizaResposta'/>
				<table id='tabelaConsultaHist' width="100%">
					<tr>
						<td>
							Resposta:
						</td>
						<td>
							<textarea rows="5" cols="75" name="respostaOcorrencia" id="respostaOcorrenciaResposta"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan='2'>
							<input type='button' name='btnAtuOcorr' value='Registrar Resposta' onclick='atualizaRespostaOcorrencia()'>
							<input type='button' name='btnAtuOcorrFechar' value='Fechar' onclick='POPUP_ATUALIZAR_RESPOSTA_OCORR.hide()'>
						</td>
					</tr>
				</table>
		</form>
	</cit:janelaPopup>


</body>
</html>
