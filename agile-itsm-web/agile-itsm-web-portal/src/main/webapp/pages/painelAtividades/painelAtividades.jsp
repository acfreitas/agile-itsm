<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

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

<link type="text/css" rel="stylesheet"
	href="../../css/ui-lightness/jquery-ui-1.8.16.custom.css" />
<link type="text/css" rel="stylesheet" href="../../css/geral.css" />
<link type="text/css" rel="stylesheet" href="../../css/form.css" />

<script type="text/javascript" src="../../js/jquery-1.6.2.min.js"></script>
<script type="text/javascript"
	src="../../js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="../../js/efeitos.js"></script>
<script type="text/javascript" src="../../js/forms.js"></script>
<script type="text/javascript" src="../../js/jquery-ui-timepicker-addon.js"></script>
<script >
$(function() {
	   $('.datepicker').datepicker();
	  });
</script>
<style>
	<%@include file="../../css/painelAtividades.css"%>
</style>

<script type="text/javascript">
	<%@include file="../../js/painelAtividades.js"%>
</script>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;margin:0 auto;height:50px;position:relative;">
</cit:janelaAguarde>
<form action="" id="form_teste">
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
				<label>Você está aqui: &nbsp; Cadastro - Painel de Atividades</label>
			</div>
		</div>
		
		<div id="content">
			<div id="tab_container" class="ui-widget-content ui-corner-all">
				<ul>
					<li><a href="#tab_painel">Painel de Atividades</a></li>
					<li><a href="#tab_pesquisa">Pesquisa</a></li>
				</ul>
				<div id="tab_painel">
					<div class="demo">
						<div id="accordion">
							<h3>
								<a href="#">Atividades do Dia</a>
							</h3>
							<div>	
								<table style="border-spacing:0;" class="table">
									<tr>
									<th>Descrição</th>
									<th>Data Inicial</th>
									<th>Hora</th>
									<th>Duração</th>
									<th>Periodicidade</th>
									<th>Situação</th>
									<th>Ação</th>
									</tr>
									<tr id="tr_teste">
										<td>Verificação de temperatura do CPD A</td>
										<td>01/11/2011</td>
										<td>7:00</td>
										<td>2 dias</td>
										<td>Mensal</td>
										<td>Em aberto</td>
										<td><a href="#" onclick="carregar(this);" title="Registrar realização de Atividade."><img src="../../imagens/add2.png" /></a></td>
									<tr>
									<tr>
										<td>Verificação de temperatura do CPD B</td>
										<td>01/11/2011</td>
										<td>7:00</td>
										<td>2 dias</td>
										<td>Mensal</td>
										<td>Em aberto</td>
										<td>
											<a href="#" onclick="carregar(this);" title="Registrar realização de Atividade.">
												<img src="../../imagens/add2.png" 
													title="Registrar realização de Atividade." /></a>
										</td>
									<tr>
									<tr>
										<td>Verificação de temperatura do CPD C</td>
										<td>01/11/2011</td>
										<td>7:00</td>
										<td>2 dias</td>
										<td>Mensal</td>
										<td>Em aberto</td>
										<td><a href="#" onclick="carregar(this);"><img src="../../imagens/add2.png" /></a></td>
									</tr>
								</table>
							</div>
							<h3>
								<a href="#">Atividades em Aberto</a>
							</h3>
							<div>
								<table style="border-spacing:0;" class="table">
									<tr>
									<th>Descrição</th>
									<th>Data Inicial</th>
									<th>Hora</th>
									<th>Duração</th>
									<th>Periodicidade</th>
									<th>Situação</th>
									<th>Ação</th>
									</tr>
									<tr>
										<td>Verificação de temperatura do CPD X</td>
										<td>01/11/2011</td>
										<td>7:00</td>
										<td>2 dias</td>
										<td>Mensal</td>
										<td>Em aberto</td>
										<td><a href="#" onclick="carregar(this);"><img src="../../imagens/add2.png" /></a></td>
									<tr>
									<tr>
										<td>Verificação de temperatura do CPD X</td>
										<td>01/11/2011</td>
										<td>7:00</td>
										<td>2 dias</td>
										<td>Mensal</td>
										<td>Em aberto</td>
										<td><a href="#" onclick="carregar(this);"><img src="../../imagens/add2.png" /></a></td>
									<tr>
									<tr>
										<td>Verificação de temperatura do CPD D</td>
										<td>01/11/2011</td>
										<td>7:00</td>
										<td>2 dias</td>
										<td>Mensal</td>
										<td>Em aberto</td>
										<td><a href="#" onclick="carregar(this);"><img src="../../imagens/add2.png" /></a></td>
									</tr>
								</table>
							</div>
							<h3>
								<a href="#" id="container_realizacao_atividade">Realização Atividade</a>
							</h3>
							<div>
								<table id="table_cadastro_atividade">
									<tr>
										<td><label class="formLabel">Descrição:*</label></td>
										<td><input type='text' name="descricao" id="descricao" maxlength="100"
											style="width: 300px;"
											title="Informe a descrição da Atividade." />
										</td>
									</tr>
									<tr> 
										<td><label class="formLabel">Usuário:*</label></td>
										<td><input type='text' name="usuario" id="tags" maxlength="70"
											style="width: 300px;"
											title="Informe o usuário da Atividade." />
										</td>
									</tr>
									<tr>
										<td><label class="formLabel">Data:*</label></td>
										<td><input type='text' name="data" id="data" maxlength="70"
											class="Valid[Required,Data] Format[Data] datepicker"
											title="Informe a data da Atividade." />
										</td>
									</tr>
									<tr>
										<td><label class="formLabel">Hora:*</label></td>
										<td><input type='text' name="hora" id="hora" maxlength="5"
											style="width:80px;"
											class="timepicker" style="width: 300px;"
											title="Informe a hora da Atividade." />
										</td>
									</tr>
									<tr>
										<td><label class="formLabel">Evidência:*</label></td>
										<td><input type='text' name="evidencia" id="evidencia" maxlength="70"
											class="Valid[Required] Description[Descrição]" style="width: 300px;"
											title="Informe a evidência da Atividade." />
										</td>
									</tr>
									<tr>
										<td><label class="formLabel">Observação:</label></td>
										<td>
											<textarea name="observacao" id="observacao" style="width: 360px;"
											title="Informe a observação da Atividade."></textarea>
										</td>
									</tr>
									<tr>
										<td />
										<td class="demo">
											<input type="button" name="btnGravar" id="btnGravar"
												value="Gravar" onclick="document.form.save();" /> 
											<input type='button' name='btnLimpar' 
												value='Limpar'
											onclick="document.forms[0].clear();" />
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>					
				</div>
				
				<div id="tab_pesquisa">
					<table>
						<tr>
							<td><label class="formLabel">Descrição</label></td>
							<td>
								<input type="text" name="descricao" style="width:300px;" />
							</td>
						</tr>
						<tr>
							<td><label class="formLabel">Usuário</label></td>
							<td>
								<input type="text" name="usuario" style="width:300px;" />
							</td>
						</tr>
						<tr>
							<td><label class="formLabel">Período</label></td>
							<td>
								<input type="text" name="dataInicial" class="datepicker" /> - 
								<input type="text" name="dataFinal" class="datepicker" />
							</td>
						</tr>
						<tr>
							<td><label class="formLabel">Horário</label></td>
							<td>
								<input type="text" name="horaInicial" class="timepicker" /> - 
								<input type="text" name="horaFinal" class="timepicker" />
							</td>
						</tr>	
						<tr>
							<td></td>
							<td colspan="2" class="demo"><input type="button" value="Pesquisar" />
								<input type="button" value="Pesquisar Tudo" /> <input
								type="button" value="Limpar" />
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