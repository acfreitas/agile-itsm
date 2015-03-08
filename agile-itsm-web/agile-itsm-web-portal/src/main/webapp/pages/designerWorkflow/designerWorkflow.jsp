<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@include file="/include/security/security.jsp" %>
<%@include file="/include/header.jsp"%>

<title>CIT Corpore</title>
<%@include file="/include/titleComum/titleComum.jsp" %>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

<script>
	$(document).ready(function() {
		$( "#POPUP_OBJ" ).dialog({
			title: 'Item de Workflow',
			width: 800,
			height: 400,
			modal: true,
			autoOpen: false,
			resizable: false,
			show: "fade",
			hide: "fade"
			});

		$("#POPUP_OBJ").hide();

		$( "#sortable" ).sortable({
			cancel: ".ui-state-disabled"
		});
		$( "#sortable" ).disableSelection();
	});

	function geraSortable(id){
		$( "#" + id ).sortable();
	}

	function mostraAddObj(){
		$( "#POPUP_OBJ" ).dialog( 'open' );
	}

	function adicionaItem(){
		document.formItem.fireEvent('addItem');
	}

	function selecionaItemWorkflow(obj){
		if (obj.value == '1'){
			document.getElementById('divDecisao').style.display = 'none';
		}
		if (obj.value == '2'){
			document.getElementById('divDecisao').style.display = 'block';
		}
	}
	function clickSaveWorkFlow(){
		$.ajax({
			  url: 'http://localhost:8080/designer/uuidRepository',
			  success: function( data ) {
			      alert(data);
			  }
			});
	}
</script>

<style>
	#sortable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
	#sortable li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; height: 100%; }
	#sortable li span { position: absolute; margin-left: -1.3em; }
</style>

</head>
<body>
<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>
<!-- Conteudo -->
	<div id="main_container" class="main_container container_16 clearfix">

		<div class="flat_area grid_16">
				<h2>Workflow</h2>
		</div>

	<form name='form' method="POST" action='${ctx}/pages/designerWorkflow/designerWorkflow'>
		<input type='button' name='btnSourceWrkFlw' value='Gravar' onclick='clickSaveWorkFlow()'/>
		<iframe src='http://localhost:8080/designer/editorprofile=jbpm&uuid=123456' width="100%" height="100%"></iframe>
	</form>
	</div>

<div id="POPUP_OBJ" style='width: 600px; height: 400px' >
	<form name='formItem' method="POST" action='${ctx}/pages/designerWorkflow/designerWorkflow'>
	<table>
		<tr>
			<td>
				Item de Workflow:
			</td>
			<td>
				<select name='type' onchange='selecionaItemWorkflow(this)'>
					<option value='1'>Passo</option>
					<option value='2'>Decisão</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				Descrição:
			</td>
			<td>
				<input type='text' name='nome'/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div id='divDecisao'>

				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<button name="btnSalvar" type='button' onclick='adicionaItem()'>OK</button>
			</td>
		</tr>
	</table>
	</form>
</div>

<!-- Fim da Pagina de Conteudo -->
</div>

<%@include file="/include/footer.jsp"%>


</body>
</html>
