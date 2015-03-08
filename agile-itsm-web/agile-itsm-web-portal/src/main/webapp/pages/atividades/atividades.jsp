<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/tags/cit" prefix="cit"%>

<%
	response.setCharacterEncoding("ISO-8859-1");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/include/titleComum/titleComum.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<%@include file="/include/cssComuns/cssComuns.jsp"%>

<link type="text/css" rel="stylesheet" href="../../css/ui-lightness/jquery-ui-1.8.16.custom.css"  />
<link type="text/css" rel="stylesheet" href="../../css/geral.css" />
<link type="text/css" rel="stylesheet" href="../../css/form.css" />
<link type="text/css" rel="stylesheet" href="../../css/login.css" />

<script type="text/javascript" src="../../js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="../../js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="../../js/efeitos.js"></script>
<script type="text/javascript" src="../../js/forms.js"></script>

<script type="text/javascript" src="../../js/jquery.ui.datepicker.js"></script>
<script src="../../js/jquery.ui.datepicker-locale.js"></script>
<script src="${ctx}/js/jquery.ui.datepicker-init.js"></script>

</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
		title="Aguarde... Processando..."
		style="display:none;top:100px;margin:0 auto;height:50px;position:relative;">
	</cit:janelaAguarde>
	<form action="">
		<div id="wrap">
			<div id="header">
				<%@include file="../../include/menu2011/menuConfig.jsp"%>
				<div id="menu" style="position:absolute; z-index: 10;">
					<%@include file="/include/menu2011/menuNovo.jsp"%>
				</div>
				<br />
				<br />
				<br />
				<div id="migalha">
					<label>Voc� est� aqui: &nbsp; Cadastro - Atividades</label>
				</div>
			</div>
			<div id="content">
				<div id="tab_container" class="ui-widget-content ui-corner-all">
					<ul>
						<li><a href="#tab_cadastro">Cadastro</a></li>
						<li><a href="#tab_pesquisa">Pesquisa</a></li>
					</ul>
					<div id="tab_cadastro">
						<table>
							<tr>
								<td><label class="formLabel">Descri��o:*</label></td>
								<td>
									<input type="text" name="descricao" maxlength="70"
									class="Valid[Required] Description[Descri��o]" style="width: 300px;"
									title="Informe a descri��o da Atividade." />
								</td>
							</tr>
							<tr>
								<td><label class="formLabel">Grupo:*</label></td>
								<td>
									<select name="grupo"
									class="Valid[Required] Description[Grupo]" style="min-width: 200px;"
									title="Informe o Grupo respons�vel por essa Atividade.">
									</select>
								</td>
							</tr>
							<tr>
								<td><label class="formLabel">Data de In�cio:*</label></td>
								<td>
									<input type="text" name="dataInicio" class="Valid[Required,Data] Format[Data] datepicker" />
								</td>
							</tr>
							<tr>
								<td><label class="formLabel">Periodicidade:*</label></td>
								<td>
									<input type="text" name="periodicidade" maxlength="5"
									class="Valid[Required] Description[Periodicidade]" style="width: 50px;"
									title="Informe a periodicidade da Atividade." />
								</td>
							</tr>
							<tr>
								<td><label class="formLabel">Dura��o:*</label></td>
								<td>
									<input type='text' name="duracao" maxlength="70"
									class="Valid[Required] Description[Dura��o]" style="width: 50px;"
									title="Informe a dura��o da Atividade." />
								</td>
							</tr>
							<tr>
								<td><label class="formLabel">Dia �til:*</label></td>
								<td>
									<input type="checkbox" name="duracao"
									class="Valid[Required] Description[Dura��o]"
									title="Informe o indicador de dia �til." />
								</td>
							</tr>
							<tr>
								<td><label class="formLabel">Observa��o</label></td>
								<td>
									<textarea name="observacao" style="width:350px;"
										class="Valid[Required] Description[Observa��o]"
										title="Informe uma observa��o a ser lembrada na execu��o da Atividade.">
									</textarea>
								</td>
							</tr>
							<tr>
								<td />
								<td class="demo">
									<input type='button' name='btnGravar' value='Gravar'
										onclick='document.form.save();' />
									<input type='button' name='btnLimpar' value='Limpar'
										onclick='document.form.clear();' />
								</td>
							</tr>
						</table>
					</div>
					<div id="tab_pesquisa">
						<table>
							<tr>
								<td><label class="formLabel">Nome:*</label></td>
								<td><input type="text" name="parametroBusca" style="width:300px;" /></td>
							</tr>
							<tr>
								<td></td>
								<td class="demo">
									<input type="button" value="Pesquisar" />
									<input type="button" value="Pesquisar Tudo" />
									<input type="button" value="Limpar" />
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div id="mask"></div>
	</form>
</body>
</html>