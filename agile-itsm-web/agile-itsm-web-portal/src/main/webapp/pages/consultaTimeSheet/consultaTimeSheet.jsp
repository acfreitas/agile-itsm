<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
	response.setCharacterEncoding("ISO-8859-1");
%>
<html>

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
	function pesquisar(){
		if (document.form.validate()){
			HTMLUtils.deleteAllRows('tblMeuTimeSheet');
			HTMLUtils.addRow('tblMeuTimeSheet',
							document.form,
							'',
								{dataStr:'',
								nomeCliente:'<font color="red"><b>Aguarde...</b></font>',
								nomeProjeto:'',
								detalhamentoDemanda:'',
								qtdeHorasStr:''},
							["dataStr", "nomeCliente", "nomeProjeto", "detalhamentoDemanda", "qtdeHorasStr"],
							null,
							"Já existe registrado esta demanda na tabela",
							null,
							null,
							null);

			document.form.fireEvent('pesquisaTimeSheet');
		}
	}

	function ExecutaInsercaoTabela(row, obj){
		row.bgColor = '#d4d4d4';
	}
</script>

<script>
	<cit:Menu/>
	writeMenus();
</script>

<div id="paginaTotal">
	<%@include file="../../include/cabecalho.jsp"%>
	<div id="areautil">
		<div id="menu1">
		</div>
		<div id="formularioIndex">
       		<div id=conteudo>
	    		<div id=migalha>
		       		Voc&ecirc; est&aacute; aqui: <b>CITCorpore</b>
		       	</div>
		       	<div id=titulo>
		       		&nbsp;<img src="../../imagens/seta_azul.gif" border="0"/>&nbsp;&nbsp;&nbsp;<span id='spanTitulo'>Consulta Meu TimeSheet</span>
				</div>
				<table width="100%">
					<tr>
						<td width="100%">
							<div id='areaUtilAplicacao'>
								<!-- ## AREA DA APLICACAO ## -->
									<form name='form' action='${ctx}/pages/consultaMeuTimeSheet/consultaMeuTimeSheet'>
										<table>
											<tr>
												<td>
													Per&iacute;odo:
												</td>
												<td>
													<input type='text' name='dataInicio' size="10" maxlength="10" class="Valid[Required] Format[Data] Description[Data Início]"/> a <input type='text' name='dataFim' size="10" maxlength="10" class="Valid[Required] Format[Data] Description[Data Fim]"/>
												</td>
											</tr>
											<tr>
												<td colspan='2'>
													<input type='button' name='btnConsultar' value='Consultar' onclick='pesquisar()'/>
												</td>
											</tr>
										</table>
										<table id='tblMeuTimeSheet' width="100%">
											<tr>
												<td>
													Data
												</td>
												<td>
													Cliente
												</td>
												<td>
													Projeto
												</td>
												<td>
													Demanda
												</td>
												<td>
													Qtde Horas
												</td>
											</tr>
										</table>
									</form>
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

</body>
</html>
