<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO"%>

<!doctype html public "">
<html>
<head>
<%
	String idContrato = (String)request.getParameter("idContrato");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<style type="text/css">


body {
	background-color: white;
	background-image: url("");
	}
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

	.tableLess {
	  font-family: arial, helvetica !important;
	  font-size: 10pt !important;
	  cursor: default !important;
	  margin: 0 !important;
	  background: white !important;
	  border-spacing: 0  !important;
	  width: 100%  !important;
	}

	.tableLess tbody {
	  background: transparent  !important;
	}

	.tableLess * {
	  margin: 0 !important;
	  vertical-align: middle !important;
	  padding: 2px !important;
	}

	.tableLess thead th {
	  font-weight: bold  !important;
	  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
	  text-align: center  !important;
	}

	.tableLess tbody tr:ACTIVE {
	  background-color: #fff  !important;
	}

	.tableLess tbody tr:HOVER {
	  background-color: #e7e9f9!important ;
	  cursor: pointer;
	}

	.tableLess th {
	  border: 1px solid #BBB  !important;
	  padding: 6px  !important;
	}

	.tableLess td{
	  border: 1px solid #BBB  !important;
	  padding: 6px 10px  !important;
	}
	a{
		font-size: 11px!important;

	}
	a:HOVER {
		font-size: 11px!important;
		/* font-weight: bold!important; */
}
td{
font-size: 11px!important;

}

.frame{

	background-color: white!important;
	color: white;
	min-height: 99%!important;
}

.ui-icon-closethick{

display: none!important;
}



</style>

<script><!--

	$(function() {

 		$("#POPUP_CRIAR_QUEST").dialog({
			autoOpen : false,
			width : 1430,
			height : 800,
			modal : true,
			 buttons: {
				 <fmt:message key="citcorpore.comum.fechar" />: function() {
				 $( this ).dialog( "close" );
				 carregaCombo();
				}
			}
		});
	});

	$(function() {
	$( document ).tooltip();
	});

	function abreQuestionario(id){
		document.form.idRequisicaoQuestionario.value = "";
		document.getElementById('frameQuestionario').src ='${ctx}/pages/visualizacaoQuestionario/visualizacaoQuestionario.load?modo=edicao&idQuestionario='+id;
		chamaHistorico();
	}

	function gravar(){

		var idRequisicao = document.getElementById('idRequisicao').value;
		var idTarefa = document.getElementById('idTarefa').value;
		var idTipoRequisicao = document.getElementById('idTipoRequisicao').value;
		var idTipoAba = document.getElementById('idTipoAba').value;
		var idRequisicaoQuest =  document.form.idRequisicaoQuestionario.value;

		window.frames["frameQuestionario"].document.formQuestionario.action = '${ctx}/pages/requisicaoQuestionario/requisicaoQuestionario.load?idRequisicao='+idRequisicao+'&idTarefa='+idTarefa+'&idTipoRequisicao='+idTipoRequisicao+'&idTipoAba='+idTipoAba+'&idRequisicaoQuestionario='+idRequisicaoQuest;
		window.frames["frameQuestionario"].document.formQuestionario.idContrato.value = document.getElementById('idContrato').value;
		window.frames["frameQuestionario"].document.formQuestionario.submit();
	}

	function chamaHistorico(){

		document.getElementById('divHistRes_Conteudo').innerHTML = "";
		document.form.idQuestionario.value = $("#idQuestionario").val();
		document.form.fireEvent('listarRegistrosQuestionario');
	}

	function carregaCombo(){
		document.form.fireEvent('carregaComboQuest');
	}


	function chamaEdicaoQuestionario(idRequisicao, idQuestionario, idItemParm, idIdentificador, somenteLeitura, subForm, abaSusp, nomeQuest){

		if (idQuestionario == '0' || idQuestionario == '' || idQuestionario == 'null' || idQuestionario == ' '){
			alert('Não é possível editar o procedimento!\n\n\nNão existe questionário configurado para este procedimento!');
			return;
		}
		if (idIdentificador == undefined || idIdentificador == null){
			idIdentificador = '';
		}

		document.form.idRequisicaoQuestionario.value = "";
		var modo = '';
		modo = 'edicao';
			document.form.idRequisicaoQuestionario.value = idIdentificador;
			document.getElementById('frameQuestionario').src = '${ctx}/pages/visualizacaoQuestionario/visualizacaoQuestionario.load?modo=' + modo + '&idQuestionario=' + idQuestionario + '&idIdentificadorResposta=' + idIdentificador +  '&tabela100=false';

	}

	function gravaConfirmacao(id, val){
		document.form.idRequisicaoQuestionario.value = id;
		document.form.valorConfirmacao.value = val;
		document.form.fireEvent('gravaConfirmacaoQuestionario');
	}

	function criarQuestionario(){
		$('#POPUP_CRIAR_QUEST').dialog('open');
	 	document.getElementById('frameCriarQuestionario').src = '${ctx}/pages/questionario/questionario.load?iframe=true';

	}


	/* function imprimeQuestionario(idPessoa, idQuestionario, idItemParm, idIdentificador, somenteLeitura, subForm){
		if (idQuestionario == '0' || idQuestionario == '' || idQuestionario == 'null' || idQuestionario == ' '){
			alert('Não é possível editar o procedimento!\n\n\nNão existe questionário configurado para este procedimento!');
			return;
		}

			document.formImprimirFormulario.fireEvent('imprimir');
		}
	} */

	window.setTimeout(carregaCombo, 1000);

 --></script>
</head>
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>

	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%-- <%@include file="/include/menu_horizontal.jsp"%> --%>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="checklistQuestionario.registrarChecklist" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div class="block" >
						<div id="parametros">
							<form name='form' action='${ctx}/pages/checklistQuestionario/checklistQuestionario'>
								<div class="columns clearfix">
									<div class="col_25">
											<input type="hidden" id="idContrato" name="idContrato">
											<input type='hidden' name='idTarefa' id='idTarefa' />
											<input type='hidden' name='idRequisicao' id='idRequisicao' />
											<input type='hidden' name='idTipoRequisicao' id='idTipoRequisicao' />
											<input type='hidden' name='idTipoAba' id='idTipoAba' />
											<input type="hidden" name="idRequisicaoQuestionario" id="idRequisicaoQuestionario" >
											<input type="hidden" name="valorConfirmacao" id="valorConfirmacao" >

										<fieldset >
											<label><fmt:message key="menu.nome.questionario" /></label>
											<div >
												<select id="idQuestionario" name="idQuestionario" onchange="abreQuestionario(this.value)"></select>

											</div>
										</fieldset>


									</div>
									<%-- BOTÃO PARA CRIAR QUESTIONARIO --%>
									<div>
										<button type='button' name='btnCriar' class="light"
										onclick='criarQuestionario();'
										style="margin: 20px !important;">
										<img src="${ctx}/imagens/add.png" style="padding-left: 8px;">
										<span><fmt:message key="checklistQuestionario.criarQuestionario" /></span>
										</button>

										<button type='button' name='btnAtualizar' class="light"
										onclick='carregaCombo();'
										style="margin: 20px !important;">
										<img src="${ctx}/template_new/images/icons/small/grey/refresh_3.png" style="padding-left: 8px;">
										<span><fmt:message key="citcorpore.comum.atualizar" /></span>
										</button>
									</div>
								</div>
								<fieldset>
									<div id="quest" style="height: 600px">
										<div class="col_60" id='divInformacoesComplementares' style='display:block;'>
	                                		<iframe  id='frameQuestionario' name='frameQuestionario' class="frame" src='about:blank' height="598px" style='width: 99%; height: 598px; border:none;'></iframe>

	                            		</div>
	                            		<h2 class="section">
											<fmt:message key="citcorpore.comum.historico" />
										</h2>
	                            		<div class="col_40">
	                            			<div id="divHistRes_Conteudo" style="overflow: auto; height: 598px;"></div>
	                            		</div>
									</div>

								</fieldset>
								<div class="col_100">
									<fieldset>
									<button type='button' name='btnRelatorio' class="light"
											onclick='gravar();'
											style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/grey/pencil.png" style="padding-left: 8px;">
											<span><fmt:message key="citcorpore.comum.gravar" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light"
											onclick='limpar()' style="margin: 20px !important;">
											<img
												src="${ctx}/template_new/images/icons/small/grey/clear.png">
											<span><fmt:message key="citcorpore.comum.limpar" /></span>
										</button>

									</fieldset>
								</div>
							</form>
							<div id="POPUP_IMPRIMIR_QUEST">
								<form name='formImprimirFormulario' method="POST" action="${ctx}/pages/checklistQuestionario/checklistQuestionario">
									<input type='hidden' name='idSolicitacaoServicoQuestionario'/>
									<input type='hidden' name='idQuestionario' />

								 	<input type='hidden' name='parmCount'/>
								 	<input type='hidden' name='parm1'/>
								 	<input type='hidden' name='parm2'/>
								 	<input type='hidden' name='parm3'/>
								</form>
							</div>
							 <div id="POPUP_CRIAR_QUEST">

	                                <iframe  id='frameCriarQuestionario' name='frameCriarQuestionario' class="frame" src='about:blank' height="598px" style='width: 97%; height: 598px; border:none;'></iframe>

							</div>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
</body>
</html>