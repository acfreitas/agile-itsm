<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="/include/cssComuns/cssComuns.jsp"%>

<link type="text/css" rel="stylesheet"
	href="../../css/ui-lightness/jquery-ui-1.8.16.custom.css" />
<link type="text/css" rel="stylesheet" href="../../css/default.css" />
<link type="text/css" rel="stylesheet" href="../../css/geral.css" />
<link type="text/css" rel="stylesheet" href="../../css/form.css" />

<script type="text/javascript" src="../../js/jquery-1.6.2.min.js"></script>
<script type="text/javascript"
	src="../../js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="../../js/efeitos.js"></script>
<script type="text/javascript" src="../../js/forms.js"></script>

<script type="text/javascript">

function editar(link){
	
	var cells = link.parentNode.parentNode.cells;
	
	document.getElementById('nomeGrupoAcesso').value = cells[0].innerHTML;
	$("[href='#tab_cadastro']")[0].click();
}

</script>

</head>

<body>
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
					<label>Você está aqui: &nbsp; Cadastro - Grupo de Acesso</label>
				</div>
			</div>
			<div id="content">
				<div id="tab_container" class="ui-widget-content ui-corner-all">
					<ul>
						<li><a href="#tab_cadastro">Cadastro</a></li>
						<li><a href="#tab_pesquisa">Pesquisa</a></li>
					</ul>

					<div id="tab_cadastro">
						<table border="0">
							<tr>
								<td><label class="campoEsquerda">Nome:</label></td>
								<td><input type='text' name="nomeGrupoAcesso" id="nomeGrupoAcesso"
									maxlength="20" size="20"
									title="Informe o nome do novo grupo de acesso." />
								</td>
							</tr>
							<tr>
								<td><label class="campoEsquerda">Usuarios:</label></td>
								<td>
									<input id="tab_cadastro_usuario" class="ui-autocomplete-input autocomplete_usuarios"
									type="text" />
									<a href="javascript:adicionar_item('tab_cadastro_linha_usuarios' ,'tab_cadastro_tabela_usuarios', 'tab_cadastro_usuario')">
										<img src="../../imagens/add.png">
									</a>
								</td>
							</tr>
							
							<tr id="tab_cadastro_linha_usuarios" style="display: none;">
								<td />
								<td>
									<table id="tab_cadastro_tabela_usuarios" class="tableInterna">
									</table>
								</td>
							</tr>

							<tr>
								<td><label class="campoEsquerda">Funcionalidades:</label></td>
								<td><input id="tab_cadastro_funcionalidades" class="ui-autocomplete-input autocomplete_funcionalidades"
									type="text" />
									<a href="javascript:adicionar_item('tab_cadastro_linha_funcionalidades' ,'tab_cadastro_tabela_funcionalidades', 'tab_cadastro_funcionalidades')">
										<img src="../../imagens/add.png">
									</a>
								</td>
							</tr>
							<tr id="tab_cadastro_linha_funcionalidades" style="display: none;">
								<td />
								<td>
									<table id="tab_cadastro_tabela_funcionalidades" class="tableInterna">
									</table>
								</td>
							</tr>
							<tr>
								<td></td>
								<td class="demo">
									<input type="submit" value="Cadastrar" /> 
								</td>
							</tr>
						</table>
					</div>
					
					<div id="tab_pesquisa">
						<table>
							<tr>
								<td><label for="grupo">Grupo:</label>
								</td>
								<td><input id="tab_pesquisa_grupo" class="ui-autocomplete-input autocomplete_grupos"
									type="text" />
								</td>
							</tr>
							<tr>
								<td></td>
								<td class="demo">
									<input type="submit" value="Pesquisar" />
								</td>
							</tr>
						</table>
						
						<table class="tabela_retorno_pesquisa">
							<tr>
								<th>Nome do Grupo</th>
								<th>Usuarios do Grupo</th>
								<th>Funcionalidades do Grupo</th>
							</tr>
							<tr>
								<td>Administradores</td>
								<td>
									<table>
										<tr><td>
											ARTUR BARBOSA COELHO <br />
											FRANCISCO UEMURA JUNQUEIRA<br />
											ADRIENE MOREIRA LEAO<br />
											</td>
										</tr>
									</table>
								</td>
								<td>Acesso à todas as páginas</td>
								<td><a href="#" onclick="editar(this);"><img src="../../imagens/edit.png"></a></td>
								<td><img src="../../imagens/delete.png"></td>
							</tr>
							<tr>
								<td>Departamento Financeiro</td>
								<td>	
									<table>
										<tr>
										<td>
											GABRIELA MARIA RIBEIRO DE ARAUJO <br />
											DEBORA JULIANA DOS SANTOS<br />
											REBECA AMANCIO BERTOLLI VENANCIO<br />
											</td>
										</tr>
									</table>
								</td>
								<td>Páginas do financeiro</td>
								<td><a href="#" onclick="editar(this);"><img src="../../imagens/edit.png"></a></td>
								<td><img src="../../imagens/delete.png"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<%@include file="../../include/footer.jsp"%>
		</div>
	</form>

</body>
</html>