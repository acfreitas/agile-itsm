<%@page import="br.com.centralit.bpm.dto.TipoFluxoDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.citframework.util.UtilHTML"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.GrupoDTO"%>
<%@ page import="br.com.centralit.citcorpore.free.Free" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
    String iframe = "";
    iframe = request.getParameter("iframe");

    Collection tiposFluxos = (Collection)request.getAttribute("tiposFluxos");
    if (tiposFluxos == null){
		tiposFluxos = new ArrayList();
    }
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include  file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script charset="ISO-8859-1"  type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

<script type="text/javascript" src="../../cit/objects/GrupoEmpregadoDTO.js"></script>

<link rel="stylesheet" type="text/css" href="./css/grupo.css" />

<%
    //se for chamado por iframe deixa apenas a parte de cadastro da pÃ¡gina
    if (iframe != null) {
%>
<link rel="stylesheet" type="text/css" href="./css/grupo.css" />

<%
    }
%>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title=""
	style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
	<!-- 	<script type="text/javascript" src="../../cit/objects/EmpregadoDTO.js"></script> -->
	<div id="wrapper">
		<%
		    if (iframe == null) {
		%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%
		    }
		%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%
			    if (iframe == null) {
			%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%
			    }
			%>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="grupo.grupo" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="grupo.cadastroDeGrupo" /></a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="grupo.pesquisaGrupo" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="">
							<form name='form'
								action='${ctx}/pages/grupo/grupo'>
								<div class="columns clearfix">
									<input type='hidden' name='idGrupo' />
									<input type='hidden' name='idEmpresa'/>
									<input type="hidden" id="dataInicio" name="dataInicio" />
									<input type="hidden" id="dataFim" name="dataFim" />
									<input type="hidden" id="empregadosSerializados" name="empregadosSerializados" />
									<input type="hidden" id="empregadosSerializadosAux" name="empregadosSerializadosAux" />
									<input type="hidden" id="emailsSerializados" name="emailsSerializados" />
									<input type="hidden" id="empregadoGrupos" name="empregadoGrupos" />
									<input type="hidden" id="emailGrupos" name="emailGrupos" />
									<input type='hidden' name='paginaSelecionadaColaborador' id='paginaSelecionadaColaborador'/>
									<input type="hidden" id="iddEmpregado" name="iddEmpregado" />
									<input type="hidden" id="descEmpregado" name="descEmpregado" />
									<input type="hidden" id="AllEmpregadosCheckados" name="AllEmpregadosCheckados" />


									<div class="col_100">
									<div class="col_25">
										<fieldset>
											<label  class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome"/>
											</label>
											<div>
												<input type='text' name="nome" maxlength="255" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio"> <fmt:message key="grupo.perfilAcesso" /> </label>
											<div>
												<select id='idPerfilAcessoGrupo' name='idPerfilAcessoGrupo' class=" Description[grupo.perfilAcesso]">
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio"><fmt:message key="grupo.serviceDesk" /></label>
											<div>
												<select id='serviceDesk' name='serviceDesk' class=" Valid[Required] Description[grupo.serviceDesk]">
													<option><fmt:message key="citcorpore.comum.selecione" /></option>
													<option value='N'><fmt:message key="citcorpore.comum.nao" /></option>
													<option value='S'><fmt:message key="citcorpore.comum.sim" /></option>
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 61px">
											<label><fmt:message key="grupo.comiteConsultivoMudanca" /></label>
											<div>
												<%
													if(br.com.citframework.util.Util.isVersionFree(request)){
												%>
													<%=Free.getMsgCampoIndisponivel(request)%>
												<%
													} else {
												%>
													<select id='comiteConsultivoMudanca' name='comiteConsultivoMudanca'>
														<option value=""><fmt:message key="citcorpore.comum.selecione" /></option>
														<option value='N'><fmt:message key="citcorpore.comum.nao" /></option>
														<option value='S'><fmt:message key="citcorpore.comum.sim" /></option>
													</select>
												<%
													}
												%>
											</div>
										</fieldset>
									</div>
									</div>
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="grupo.sigla" />
											</label>
											<div>
												<input type='text' name="sigla" id="siglagrupo" maxlength="20" class="Valid[Required] Description[grupo.sigla]" />
											</div>
										</fieldset>
									</div>
									<div class="col_66">
										<fieldset>
											<label><fmt:message key="citcorpore.comum.descricao" />
											</label>
											<div>
												<textarea name="descricao" cols='200' rows='5' maxlength = "2147483647"></textarea>
											</div>
										</fieldset>
									</div>
									<div class="col_66">
										<fieldset>
											<div>
												<label><b><fmt:message key="grupo.suspensaoReativacaodeMultiplasSolicitacoes" /></b></label>
												<fmt:message key="citcorpore.comum.sim" /><input type="radio" name="permiteSuspensaoReativacao" id="permiteSuspensaoReativacao" value="S">
												<fmt:message key="citcorpore.comum.nao" /><input type="radio" name="permiteSuspensaoReativacao" id="permiteSuspensaoReativacao" value="N" checked="checked">
											</div>

											<label><fmt:message key="grupo.permissoesfluxos" />
											</label>
											<br />
											<input  style="float:left; margin-left:20px" onclick='marcarTodos();' type='checkbox' name='checkboxCheckAll' id='checkboxCheckAll'  />
											<div>
											<label for="checkboxCheckAll" ><fmt:message key="citcorpore.comum.MarcarTodos" />
											</label>
											</div>
											<div>
												<table id="marcarTodos" style="line-height: 0.3!important;">
													<tr align='center'>
													    <td></td>
													    <td style='width: 40px;'><fmt:message key="citcorpore.comum.criar" /></td>
													    <td style='width: 60px;'><fmt:message key="citcorpore.comum.executar" /></td>
													    <td style='width: 60px;'><fmt:message key="citcorpore.comum.delegar" /></td>
													    <td style='width: 72px;'><fmt:message key="citcorpore.comum.suspender" /></td>
													    <td style='width: 60px;'><fmt:message key="citcorpore.comum.reativar" /></td>
													    <td style='width: 74px;'><fmt:message key="citcorpore.comum.alterarSLA" /></td>
													    <td style='width: 50px;'><fmt:message key="citcorpore.comum.reabrir" /></td>
													    <td style='width: 50px;'><fmt:message key="citcorpore.comum.cancelar" />*</td>
												    </tr>
												<%
												for (Iterator it = tiposFluxos.iterator(); it.hasNext();){
												    TipoFluxoDTO  tipoFluxoDto = (TipoFluxoDTO)it.next();
												    out.print("<tr>");
												    out.print("<td style='vertical-align: middle;'>" + tipoFluxoDto.getNomeFluxo() + ": </td>");

												    if (br.com.citframework.util.Util.isVersionFree(request)
												    		&& (tipoFluxoDto.getNomeFluxo().equals("RequisicaodeMudancaNormal")
												    			|| tipoFluxoDto.getNomeFluxo().equals("RequisicaoMudancaEmergencial")
												    			|| tipoFluxoDto.getNomeFluxo().equals("RequisicaoMudancaPadrao"))) {
													    out.print("<td colspan='7'>" + Free.getMsgCampoIndisponivel(request) + "</td>");
												    } else {

													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='C_" + tipoFluxoDto.getIdTipoFluxo() + "' id='C_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='E_" + tipoFluxoDto.getIdTipoFluxo() + "' id='E_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='D_" + tipoFluxoDto.getIdTipoFluxo() + "' id='D_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='S_" + tipoFluxoDto.getIdTipoFluxo() + "' id='S_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='R_" + tipoFluxoDto.getIdTipoFluxo() + "' id='R_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='A_" + tipoFluxoDto.getIdTipoFluxo() + "' id='A_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='X_" + tipoFluxoDto.getIdTipoFluxo() + "' id='X_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='N_" + tipoFluxoDto.getIdTipoFluxo() + "' id='N_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
												    }
												    out.print("</tr>");
												}
												%>

												</table>
												<h6 class="cancelar">*<fmt:message key="grupo.observacaoCancelar" /></h6>
											</div>
										</fieldset>
									</div>

									<div class="col_66">
										<fieldset>
											<label><fmt:message key="grupo.NotificacoesDeE-mail" /></label>
											<div>
												<table>
													<tr><td><input value='S' type='checkbox' name='abertura' id='Abertura'/></td><td><fmt:message key="grupo.abertura" /></td></tr>
													<tr><td><input value='S' type='checkbox' name='andamento' id='Andamento'/></td><td><fmt:message key="grupo.andamento" /></td></tr>
													<tr><td><input value='S' type='checkbox' name='encerramento' id='Encerramento'/></td><td><fmt:message key="grupo.encerramento" /></td></tr>
												</table>
											</div>
										</fieldset>
									</div>

										<div class="col_100" id='divListaContratos'>
											<fieldset id='fldListaContratos'>
											</fieldset>
										</div>

								</div>
								<!-- 	Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 19:45 - ID Citsmart: 120948 -
								* Motivo/Comentário: Alterado largura das colunas das tables e inserido class no padrao novo  -->
								<div  class="columns clearfix">
									<div class="col_100">
										<div id="">
											<label class='span3' id="addEmpregado" style="cursor: pointer;" title="<fmt:message  key="citcorpore.comum.cliqueParaAdicinar" />">
												<fmt:message key="colaborador.colaborador" />
												<img src="${ctx}/imagens/add.png">
											</label>
											<button type='button' name='removerSelecionados' class='light' onclick='removerEmpregadosSelecionados();' > <fmt:message key="grupo.empregados.excluirSelecionados"/></button>
											<button type='button' name='removerTodosColaboradores' class='light' onclick='removerTodosEmpregados();'>  <fmt:message key="grupo.empregados.excluirTodos"/></button>
										</div>
										<fieldset>
											<div  id="gridEmpregados">
												<table class="table table-bordered table-striped" id="tabelaEmpregado"
													style="display: none;">
													<tr>
														<th style="width: 1%;"><input type="checkbox" onclick="checkboxAllEmpregados(this)" value="" name="selecionarTodosDaPagina" style="width: 20px !important; align: center;"></th>
														<th style="width: 90%;"><fmt:message key="colaborador.colaborador" /></th>
														<th style="width: 9%; text-align: left;"><fmt:message  key="citcorpore.comum.email" /><input value='S' type='checkbox' name='email' id='emailTodos' onclick="check()"/></th>
													</tr>
												</table>
											</div>
											<div class="col_100">
						               		   <div class="col_40"><br></div>
						               		   <div id="paginas" class="col_50" align="center" style="height: 50px;" ></div>
						                    </div>
										</fieldset>

									</div>
								</div>
									<div  class="columns clearfix">
									<div class="col_100">
												<fieldset>
											<label id="addEmail" style="cursor: pointer;"
												title="<fmt:message  key="citcorpore.comum.cliqueParaAdicinar" />"><fmt:message  key="citcorpore.comum.email" /><img	src="${ctx}/imagens/add.png"></label>
											<div  id="gridEmails">
												<table class="table table-bordered table-striped" id="tabelaEmail"
													style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 49,5%;"><fmt:message  key="citcorpore.comum.nome" /></th>
														<th style="width: 49,5%;"><fmt:message  key="citcorpore.comum.email" /></th>
														<th style="width: 0%;"> <fmt:message  key="citcorpore.comum.tipo" />	</th>
													</tr>
												</table>
											</div>
										</fieldset>
								</div>
								</div>
								<br>
								<br>
								<fieldset>
									<label>
										<button type='button' name='btnGravar' class="light" onclick="gravar();return false;" >
											<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
											<span><fmt:message key="citcorpore.comum.gravar" /></span>
										</button>
										<button type="button" name='btnLimpar' class="light" onclick="limpar();return false;">
											<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
											<span><fmt:message key="citcorpore.comum.limpar" /></span>
										</button>
										<button type='button' name='btnUpDate' class="light" onclick='excluir();return false;'>
											<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
											<span><fmt:message key="citcorpore.comum.excluir" /></span>
										</button>
									</label>
								</fieldset>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
	<!-- POPUP EMPREGADO -->
	<div id="POPUP_EMPREGADO" title="<fmt:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaEmp' style="width: 540px">
				<cit:findField formName='formPesquisaEmp' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
	<!-- Fim POPUP EMPREGADO -->
		<!-- POPUP EMAIL -->
	<div id="POPUP_EMAIL" title="<fmt:message  key="grupo.pesquisaEmail" />">
		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
			<li><a href="#tabs-1"><fmt:message  key="email.cadastroEmail" /></a></li>
			<li><a href="#tabs-2" class="round_top"><fmt:message  key="email.pesquisaColaborador" />
			</a></li>
			</ul>
			<a href="#" class="toggle">&nbsp;</a>
		<div class="toggle_container">

		<div id="tabs-1" class="block">
		<div class="section">
		<form id="formEmail" name="formEmail" action="${ctx}/pages/grupoEmail/grupoEmail">
		<input type = "hidden" name = "idEmpregado" />
		<input type = "hidden" name = "idGrupo" />
		<label><fmt:message  key="citcorpore.comum.nome" /></label>
		<div>
			<input type="text" id="nomeEmail" name="nome" maxlength = "80" />
		</div>

		<label><fmt:message  key="citcorpore.comum.email" /></label>
			<div>
				<input type="text" id="emailExtra" name="email" size="300" maxlength="200"/>
			</div>
			<br>
			<br>
			<button type='button' name='btnGravar' class="light" onclick="gravarEmail();" >
					<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
					<span><fmt:message key="citcorpore.comum.gravar" /></span>
			</button>
			<button type="button" name='btnLimpar' class="light" onclick="limparEmail();">
				<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
					<span><fmt:message key="citcorpore.comum.limpar" /></span>
			</button>
			</form>
		</div>
		</div>
		<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisaEmail' style="width: 540px">
								<cit:findField formName='formPesquisaEmail'  lockupName='LOOKUP_EMPREGADO_EMAIL' id='LOOKUP_EMPREGADO_EMAIL' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
		</div>
		</div>
	</div>
	<!-- Fim POPUP EMAIL -->
	<script type="text/javascript">
		var ctx = "${ctx}";
	</script>
	<script type="text/javascript" src="./js/grupo.js"></script>

</html>

