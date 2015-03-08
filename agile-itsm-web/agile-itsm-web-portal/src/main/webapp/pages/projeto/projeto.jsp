<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<%
	response.setCharacterEncoding("ISO-8859-1");
%>
<html>

<head>
	<%@include file="/include/header.jsp"%>

    <%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/menu/menuConfig.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

	<link rel="stylesheet" type="text/css" href="./css/projeto.css" />
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>

<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<script type="text/javascript" src="./js/projeto.js"></script>

<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>
<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">
	<%@include file="/include/menu_horizontal.jsp"%>

		<div class="flat_area grid_16">
				<h2><fmt:message key="projeto.cadastroProjeto"/></h2>
		</div>
  <div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><fmt:message key="projeto.cadastroProjeto"/></a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top"><fmt:message key="citcorpore.comum.pesquisa"/></a>
				</li>
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
	 <div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="">
									<form name='form' action='${ctx}/pages/projeto/projeto'>
										 	<input type='hidden' name='idProjeto'/>
										 	<input type='hidden' name='idOs'/>
										 	<input type='hidden' name='idRequisicaoMudanca'/>
										 	<input type='hidden' name='idLiberacao'/>
										 	<input type='hidden' name='colRecursosSerialize'/>
										 	<input type='hidden' name='colAssinaturasSerialize'/>
										 	<input type='hidden' name='empregadoGrupos'/>
										  	<table id="tabFormulario" cellpadding="0" cellspacing="0">
										         <tr>
										            <td class="campoEsquerda" ><label class="campoObrigatorio"><fmt:message key="projeto.nomeProjeto" /></label></td>
										            <td colspan='2'>
										            	<input type='text'  name="nomeProjeto" maxlength="70" size="70" class="Valid[Required] Description[projeto.nomeProjeto]" />
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><label class=""><fmt:message key="projeto.siglaProjeto" /></label></td>
										            <td colspan='2'>
										            	<input type='text' name="sigla" maxlength="50" size="50" class="Description[projeto.siglaProjeto]" />
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><label class="campoObrigatorio"><fmt:message key="projeto.contrato" /></label></td>
										            <td colspan='2'>
										            	<select name='idContrato' class="Valid[Required] Description[projeto.contrato]">
										            	</select>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><label class="campoObrigatorio"><fmt:message key="projeto.emergencial" /></label></td>
										            <td colspan='2'>
										            	<select name='emergencial' class="Valid[Required] Description[projeto.emergencial]">
										            		<option value='N'><fmt:message key="citcorpore.comum.nao" /></option>
										            		<option value='S'><fmt:message key="citcorpore.comum.sim" /></option>
										            	</select>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><label class="campoObrigatorio"><fmt:message key="projeto.severidade" /></label></td>
										            <td colspan='2'>
										            	<input type='text' name="severidade" maxlength="1" size="1" class="Valid[Required] Description[projeto.severidade]" />
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><label class="campoObrigatorio"><fmt:message key="projeto.nomeGestor" /></label></td>
										            <td colspan='2'>
										            	<input type='text' name="nomeGestor" maxlength="50" size="50" class="Valid[Required] Description[projeto.nomeGestor]" />
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><label class=""><fmt:message key="projeto.mudanca" /></label></td>
										            <td>
										            	<%-- <cit:lookupField lockupName="LOOKUP_MUDANCA" heigth="400" left="100" len="700" htmlCode="true" top="50" javascriptCode="true" formName="form"></cit:lookupField> --%>
										            	<input type='text' id="adicionarMudanca" name="adicionarMudanca" maxlength="50" size="50" class="text" />
										            	<img src='${ctx}/imagens/borracha.png' border='0' onclick='limparMudanca()'/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><label class=""><fmt:message key="projeto.liberacao" /></label></td>
										            <td>
										            	<%-- <cit:lookupField lockupName="LOOKUP_LIBERACAO" heigth="400" left="120" len="700" htmlCode="true" top="50" javascriptCode="true" formName="form"></cit:lookupField> --%>
										            	<input type='text' id="adicionarLiberacao" name="adicionarLiberacao" maxlength="50" size="50" class="text" />
										            	<img src='${ctx}/imagens/borracha.png' border='0' onclick='limparLiberacao()'/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><label class="campoObrigatorio"><fmt:message key="projeto.situacao" /></label></td>
										            <td colspan='2'>
										            	<select name='situacao' class="Valid[Required] Description[projeto.situacao]">
										            	</select>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><label class="campoObrigatorio"><fmt:message key="projeto.valorExecucaoProjeto" /></label></td>
										            <td colspan='2'>
										            	<input type='text' name='valorEstimado' size="15" maxlength="15" class="Format[Moeda] Valid[Required] Description[projeto.valorExecucaoProjeto]"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><label class=""><fmt:message key="projeto.detalhesComplementar" /></label></td>
										            <td colspan='2'>
										            	<textarea name="detalhamento" cols='90' rows='5'></textarea>
										            </td>
										         </tr>
												<!-- 	Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 10:07 - ID Citsmart: 120948 -
														* Motivo/Comentário: Icone de adicionar dados na tabela fora do padrão/ inserido botão  -->
										         <tr>
										         	<td colspan='3'>
														<div class="col_100">
															<fieldset>
																<button type='button' id="addRecurso" style="cursor: pointer;" title="<fmt:message  key="citcorpore.comum.cliqueParaAdicinar" />">
																	<fmt:message key="citcorpore.comum.adicionar" /> <fmt:message key="projeto.recursos" /><%-- <img	src="${ctx}/imagens/add.png"> --%>
																</button>
																<div  id="gridRecursos">
																	<table class="table table-bordered table-striped" id="tabelaRecurso" style="display: none;">
																		<tr>
																			<th style="width: 10%;"></th>
																			<th style="width: 45%;"><fmt:message  key="citcorpore.comum.nome" /></th>
																			<th style="width: 40%;"><fmt:message  key="projeto.custo" /></th>
																		</tr>
																	</table>
																</div>
															</fieldset>
														</div>
										         	</td>
										         </tr>
										         <!-- 	Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 10:07 - ID Citsmart: 120948 -
														* Motivo/Comentário: Icone de adicionar dados na tabela fora do padrão/ inserido botão  -->
										         <tr>
										         	<td colspan='3'>
														<div class="col_100">
															<fieldset>
																<button type='button' id="addAssinaturasAprovacoes" style="cursor: pointer;" title="<fmt:message  key="citcorpore.comum.cliqueParaAdicinar" />">
																	<fmt:message key="citcorpore.comum.adicionar" /> <fmt:message key="projeto.assinaturasAprovacoes" /> <%-- <img src="${ctx}/imagens/add.png"> --%>
																</button>
																<div  id="gridAssinaturasAprovacoes">
																	<table class="table table-bordered table-striped" id="tabelaAssinaturasAprovacoes" style="display: none;">
																		<tr>
																			<th style="width: 5%;"></th>
																			<th style="width: 25%;"><fmt:message  key="citcorpore.comum.nome" /></th>
																			<th style="width: 20%;"><fmt:message  key="citcorpore.comum.papel" /></th>
																			<th style="width: 20%;" class='campoObrigatorio'><fmt:message  key="projeto.ordem" /></th>
																		</tr>
																	</table>
																</div>
															</fieldset>
														</div>
										         	</td>
										         </tr>
										         <tr>
										            <td colspan='3'>
										            	<input type='checkbox' id='vinculoOS' name='vinculoOS' value="S" onclick='verificaVinculoOS(this)'/><b><fmt:message  key="projeto.vincularOrdemServico" /></b>
										            </td>
										         </tr>
										         <tr>
										         	<td colspan='3'>
														<div class="col_100" id='divOS' style='display: none'>
															<table id="tabFormulario" cellpadding="0" cellspacing="0">
														         <tr>
														            <td class="campoEsquerda"><label class="campoObrigatorio"><fmt:message key="problema.servico" /></label></td>
														            <td>
														            	<select id="idServicoContrato" name='idServicoContrato' class="Description[problema.servico]"></select>
														            </td>
														         </tr>
														         <tr>
														            <td class="campoEsquerda" style="visibility:hidden; display: none;"><fmt:message key="citcorpore.comum.ano" />*:</td>
														            <td>
														            	<input type='text' style="visibility:hidden; display: none;" name='ano' size="4" maxlength="4" style="width: 80px !important;" class="Format[Numero] Description[citcorpore.comum.ano] text"/>
														            </td>
														         </tr>
														         <tr>
														            <td class="campoEsquerda"><label class="campoObrigatorio"><fmt:message key="citcorpore.comum.numero" /></label></td>
														            <td>
														            	<input type='text' name='numero' size="20" maxlength="20" style="width: 250px !important;" class="Description[citcorpore.comum.numero] text"/>
														            </td>
														         </tr>
														         <tr>
														            <td class="campoEsquerda"><fmt:message key="projeto.dataEmissao" /></td>
														            <td>
														            	<input type='text' name='dataEmissao' size="10" maxlength="10" style="width: 250px !important;" class="Format[Date] datepicker Valid[Date] Description[projeto.dataEmissao] text"/>
														            </td>
														         </tr>
														         <tr>
														            <td class="campoEsquerda"><label class="campoObrigatorio"><fmt:message key="citcorpore.comum.areaRequisitante" /></label></td>
														            <td>
														            	<input type='text' id='nomeAreaRequisitante' name='nomeAreaRequisitante' size="80" maxlength="80" style="width: 500px !important;" class="Description[citcorpore.comum.areaRequisitante] text"/>
														            </td>
														         </tr>
														         <tr>
														            <td class="campoEsquerda" style='vertical-align: middle;'><fmt:message key="citcorpore.ui.tabela.coluna.Tarefa_Demanda" />:</td>
														            <td>
														            	<textarea name="demanda" cols='120' rows='5' style="border: 1px solid black;"></textarea>
														            </td>
														         </tr>
														         <tr>
														            <td class="campoEsquerda" style='vertical-align: middle;'><fmt:message key="planoMelhoria.objetivo" />:</td>
														            <td>
														            	<textarea name="objetivo" cols='120' rows='5' style="border: 1px solid black;"></textarea>
														            </td>
														         </tr>
															</table>
														</div>								              	         	                                    		                                            														</div>
										         	</td>
										         </tr>
										         <tr>
										         	<td colspan='3'>
										         		<h4><fmt:message key="projeto.linhadebase" /></h4><br>
														<div class="col_100" id='divLinhasBase'>
														</div>
													</td>
												</tr>
										         <tr>
										         	<td colspan='3'>
														<button type='button' name='btnGravar' class="light" onclick='salvar();'>
															<img
																src="${ctx}/template_new/images/icons/small/grey/pencil.png">
															<span><fmt:message key="citcorpore.comum.gravar" />
															</span>
														</button>
														<button type="button" name='btnLimpar' class="light" onclick='limpar();'>
															<img
																src="${ctx}/template_new/images/icons/small/grey/clear.png">
															<span><fmt:message key="citcorpore.comum.limpar" />
															</span>
														</button>
														<button type="button" name="btnExcluir" class="light" onclick="excluir();">
										                   <img src="${ctx}/template_new/images/icons/small/grey/trashcan.png" />
										                   <span><fmt:message key="citcorpore.comum.excluir" />
										                   </span>
									                    </button>
									         		</td>
									         	</tr>
											</table>
									</form>
								</div>
							</div>
					<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_PROJETO' id='LOOKUP_PROJETO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
	 </div>
  </div>
 </div>
<!-- Fim da Pagina de Conteudo -->
</div>

	<div id="POPUP_EMPREGADO" title="<fmt:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaEmp'>
				<cit:findField formName='formPesquisaEmp' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>

	<div id="POPUP_EMPREGADO_ASSINATURA" title="<fmt:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaAssinaturaEmpregado'>
				<cit:findField formName='formPesquisaAssinaturaEmpregado' lockupName='LOOKUP_EMPREGADO_ASSINATURA' id='LOOKUP_EMPREGADO_ASSINATURA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
		<div id="POPUP_MUDANCA" title="<fmt:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaMud'>
				<cit:findField formName='formPesquisaMud' lockupName='LOOKUP_MUDANCA' id='LOOKUP_MUDANCA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
		<div id="POPUP_LIBERACAO" title="<fmt:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaLib'>
				<cit:findField formName='formPesquisaLib' lockupName='LOOKUP_lIBERACAO' id='LOOKUP_LIBERACAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>

	<div id="POPUP_REG_AUT_MUDANCA" title="Registrar Autorização de mudança">
		<form name='formAutorizacao' action='${ctx}/pages/projeto/projeto'>
			<input type='hidden' name='idProjetoAutorizacao' id='idProjetoAutorizacao'/>
			<input type='hidden' name='idLinhaBaseProjeto' id='idLinhaBaseProjeto'/>
			<table id="tabFormulario" cellpadding="0" cellspacing="0">
		         <tr>
		            <td class="campoEsquerda"><fmt:message key="projeto.justificativaMudanca" /></td>
		            <td>
		            	<textarea rows="4" cols="70" name="justificativaMudanca" id="justificativaMudanca"></textarea>
		            </td>
		         </tr>
		         <tr>
		         	<td colspan='2'>
						<button type='button' name='btnGravar' class="light" onclick='salvarAutMudanca();'>
							<img
								src="${ctx}/template_new/images/icons/small/grey/pencil.png">
							<span><fmt:message key="citcorpore.comum.gravar" />
							</span>
						</button>
	         		</td>
	         	</tr>
		    </table>
		</form>
	</div>

		<%@include file="/include/footer.jsp"%>
</body>
</html>


