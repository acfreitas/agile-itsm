<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoGrupoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoDTO"%>

<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>

<title><fmt:message key="citcorpore.comum.title"/></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

	<style type="text/css">
		.table {
			border-left:1px solid #ddd;
		}

		.table th {
			border:1px solid #ddd;
			padding:4px 10px;
			border-left:none;
			background:#eee;
		}

		.table td {
			border:1px solid #ddd;
			padding:4px 10px;
			border-top:none;
			border-left:none;
		}

		div#main_container {
			margin: 10px 10px 10px 10px;
		}
	</style>

	<script>
		var objTab = null;
		var contUsuario = 0;
		var contGrupo = 0;
		addEvent(window, "load", load, false);

		function load() {
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}

			$("#POPUP_USUARIO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

			$("#POPUP_GRUPO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
		}

		function LOOKUP_NOTIFICACAO_select(id, desc) {
			document.form.restore({
				idNotificacao : id
			});
		}

		function excluir() {
			if (document.getElementById("idNotificacao").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
				}
			}
		}

	 	$(function() {
			$("#addUsuario").click(function() {
				$("#POPUP_USUARIO").dialog("open");
			});
		});

	 	$(function() {
			$("#addGrupo").click(function() {
				$("#POPUP_GRUPO").dialog("open");
			});
		});

	 	function LOOKUP_USUARIO_select(id, desc) {
		 addLinhaTabelaUsuario(id, desc, true);

		}

		function exibirTabelaUsuario (){
			document.getElementById('tabelaUsuario').style.display = 'block';
		}

		function exibirTabelaGrupo (){
			document.getElementById('tabelaGrupo').style.display = 'block';
		}

		function addLinhaTabelaUsuario(id, desc, valida){
			var tbl = document.getElementById('tabelaUsuario');
			$('#tabelaUsuario').show();
			$('#gridUsuario').show();
			//tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaUsuario(lastRow, id)){
					return;
				}
			}
			var row = tbl.insertRow(lastRow);
			var coluna = row.insertCell(0);
			contUsuario++;
			coluna.innerHTML = '<img id="imgDelUsuario' + contUsuario + '" style="cursor: pointer;" title="Clique para excluir" src="${ctx}/imagens/delete.png" onclick="removeLinhaTabelaUsuario(\'tabelaUsuario\', this.parentNode.parentNode.rowIndex);">';
			coluna = row.insertCell(1);
			coluna.innerHTML = desc + '<input type="hidden" id="idUsuario' + contUsuario + '" name="idUsuario" value="' + id + '" />';
			$("#POPUP_USUARIO").dialog("close");
		}

		 function validaAddLinhaTabelaUsuario(lastRow, id){
			if (lastRow > 2){
				var arrayIdUsuario = document.form.idUsuario;
				for (var i = 0; i < arrayIdUsuario.length; i++){
					if (arrayIdUsuario[i].value == id){
						alert('Regristro já adicionado!');
						return false;
					}

				}
			} else if (lastRow == 2){
				var idUsuario = document.form.idUsuario;
				if (idUsuario.value == id){
					alert('Regristro já adicionado!');
					return false;
				}
			}
			return true;
		}

		function removeLinhaTabelaUsuario(idTabela, rowIndex) {
			if (confirm('Deseja realmente excluir')){
				HTMLUtils.deleteRow(idTabela, rowIndex);
				var tabela = document.getElementById(idTabela);
				if (tabela.rows.length == 1){
					if (idTabela == 'tabelaUsuario'){
						$('#gridUsuario').hide();
						return;
					}
					$('#tabelaUsuario').hide();
				}
			}
		}

		function LOOKUP_GRUPO_select(id, desc) {
			addLinhaTabelaGrupo(id, desc, true);

		}

		 function addLinhaTabelaGrupo(id, desc, valida){
				var tbl = document.getElementById('tabelaGrupo');
				$('#tabelaGrupo').show();
				$('#gridGrupo').show();
				var lastRow = tbl.rows.length;
				if (valida){
					if (!validaAddLinhaTabelaGrupo(lastRow, id)){
						return;
					}
				}
				var row = tbl.insertRow(lastRow);
				var coluna = row.insertCell(0);
				contGrupo++;
				coluna.innerHTML = '<img id="imgDelGrupo' + contGrupo + '" style="cursor: pointer;" title="Clique para excluir" src="${ctx}/imagens/delete.png" onclick="removeLinhaTabelaGrupo(\'tabelaGrupo\', this.parentNode.parentNode.rowIndex);">';
			coluna = row.insertCell(1);
			coluna.innerHTML = desc
					+ '<input type="hidden" id="idGrupo' + contGrupo + '" name="idGrupo" value="' + id + '" />';
			$("#POPUP_GRUPO").dialog("close");
		}

		function validaAddLinhaTabelaGrupo(lastRow, id) {
			if (lastRow > 2) {
				var arrayIdGrupo = document.form.idGrupo;
				for ( var i = 0; i < arrayIdGrupo.length; i++) {
					if (arrayIdGrupo[i].value == id) {
						alert('Regristro já adicionado!');
						return false;
					}

				}
			} else if (lastRow == 2) {
				var idGrupo = document.form.idGrupo;
				if (idGrupo.value == id) {
					alert('Regristro já adicionado!');
					return false;
				}
			}
			return true;
		}

		function removeLinhaTabelaGrupo(idTabela, rowIndex) {
			if (confirm('Deseja realmente excluir')) {
				HTMLUtils.deleteRow(idTabela, rowIndex);
				var tabela = document.getElementById(idTabela);
				if (tabela.rows.length == 1) {
					if (idTabela == 'tabelaGrupo') {
						$('#gridGrupo').hide();
						return;
					}
					$('#tabelaGrupo').hide();
				}
			}
		}

		function NotificacaoUsuarioDTO(_id, i){
	 		this.idUsuario = _id;
	 	}

		function serializaUsuario() {
			var tabela = document.getElementById('tabelaUsuario');
			var count = contUsuario + 1;
			var listaDeUsuario = [];
			for ( var i = 1; i < count; i++) {
				if (document.getElementById('idUsuario' + i) != ""
						&& document.getElementById('idUsuario' + i) != null) {
					var trObj = document.getElementById('idUsuario' + i).value;
					var usuario = new NotificacaoUsuarioDTO(trObj, i);
					listaDeUsuario.push(usuario);
				}
			}
			var serializaUsuario = ObjectUtils.serializeObjects(listaDeUsuario);
			document.form.usuariosSerializados.value = serializaUsuario;
		}

		function NotificacaoGrupoDTO(_id, i){
	 		this.idGrupo = _id;
	 	}

		function serializaGrupo() {
			var tabela = document.getElementById('tabelaGrupo');
			var count = contGrupo + 1;
			var listaDeGrupo = [];
			for ( var i = 1; i < count; i++) {
				if (document.getElementById('idGrupo' + i) != ""
						&& document.getElementById('idGrupo' + i) != null) {
					var trObj = document.getElementById('idGrupo' + i).value;
					var grupo = new NotificacaoGrupoDTO(trObj, i);
					listaDeGrupo.push(grupo);
				}
			}
			var serializaGrupo = ObjectUtils.serializeObjects(listaDeGrupo);
			document.form.gruposSerializados.value = serializaGrupo;
		}

		function gravar(){
			serializaUsuario();
			serializaGrupo();
			document.form.save();
		}

		function limpar(){
			 deleteAllRowsUsuario();
			  deleteAllRowsGrupo();
			document.form.clear();

		}

		function deleteAllRowsUsuario() {
			var tabela = document.getElementById('tabelaUsuario');
			var count = tabela.rows.length;

			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;

			}
			$('#gridUsuario').hide();
		}

		function deleteAllRowsGrupo() {
			var tabela = document.getElementById('tabelaGrupo');
			var count = tabela.rows.length;

			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;

			}
			$('#gridGrupo').hide();
		}

	</script>

</head>

<body>
	<div id="wrapper">
		<div id="main_container" class="main_container container_16 clearfix">
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="notificacao.notificacao" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="notificacao.cadastronotificacao" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/notificacao/notificacao'>
								<input type='hidden' name='idBaseConhecimento' id='idBaseConhecimento'/>
								<input type='hidden' name='idNotificacao' />
								<input type='hidden' name='dataInicio' id="dataInicio" />
								<input type='hidden' name='dataFim' id="dataFim" />
								<input type="hidden" name="usuariosSerializados">
								<input type="hidden" name="gruposSerializados">

								<div class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="notificacao.titulo" /></label>
											<div>
												<input type='text' name="titulo" maxlength="256" class="Valid[Required] Description[notificacao.titulo]" />
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="notificacao.tipoNotificacao" /></label>
											<div>
												<select id="tipoNotificacao" name="tipoNotificacao"></select>
											</div>
										</fieldset>
									</div>
								</div>
								<div  class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label id="addUsuario" style="cursor: pointer;"><fmt:message key="citcorpore.comum.usuario" /><img
												src="${ctx}/imagens/add2.png"></label>
											<div  id="gridUsuario">
												<table class="table" id="tabelaUsuario"
													style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 98%;"><fmt:message key="citcorpore.comum.usuario" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label id="addGrupo" style="cursor: pointer;"><fmt:message key="grupo.grupo" /><img
												src="${ctx}/imagens/add2.png"></label>
											<div  id="gridGrupo">
												<table class="table" id="tabelaGrupo"  style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 98%;"><fmt:message key="grupo.grupo" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light"
									onclick='gravar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='limpar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' id="btnExcluir"
									class="light" onclick='excluir();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message key="citcorpore.comum.excluir" />
									</span>
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

	<div id="POPUP_USUARIO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUsuario' style="width: 540px">
							<cit:findField formName='formPesquisaUsuario'
								lockupName='LOOKUP_USUARIO_NOTIFICACAO' id='LOOKUP_USUARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_GRUPO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaGrupo' style="width: 540px">
							<cit:findField formName='formPesquisaGrupo' lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</html>
