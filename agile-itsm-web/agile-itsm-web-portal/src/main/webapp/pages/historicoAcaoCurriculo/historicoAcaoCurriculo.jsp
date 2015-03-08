<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<html>
	<head>
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");
			String URL_SISTEMA = "";
			URL_SISTEMA = CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+'/';
			String idCurriculo = request.getParameter("idCurriculoPesquisa");

		%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<!-- <script src="../../novoLayout/common/include/js/historicoAcaoCurriculo.js"></script> -->
		<script>

		function gravar(acao){
			var indice = $('#indiceTriagem').val();
			document.form.acao.value = acao;
			document.form.idCurriculo.value = <%=idCurriculo%>;
			document.form.save();
			try {
				parent.atualizarGridPesquizaCurriculo();
			} catch (e){}
		}

		function contCaracteres(valor) {
		    quant = 200;
		    total = valor.length;
		    if(total <= quant)
		    {
		        resto = quant - total;
		        document.getElementById('cont').innerHTML = resto;
		    }
		    else
		    {
		        document.getElementById('complementoJustificativa').value = valor.substr(0,quant);
		    }
		}

		function ocultaModal(){
			try{
				parent.fecharModal();
			} catch(e){}
		}

		</script>
	<style type="text/css">
		.widget-activity ul.list li.double {
			height: 71px!important;
		}
		.widget-activity ul.list li.double > span.ellipsis {
			font-size: 12px!important;
		}
		.widget-activity ul.list li.double > span .meta {
			color: #64625F!important;
		}
		.widget-activity ul.list li > span.ellipsis {
			max-width: 90%!important;
			word-wrap: break-word!important;
			white-space: normal!important;
		}
		.widget-activity ul.list li.double > span {
			height: 65px!important;
			line-height: 45px;
		}
		.widget-activity ul.list li.double > span .meta span {
			font-style: normal!important;
		}


	</style>
	</head>
	<body>
		<div class="container-fluid fixed ">

			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
		<%if (iframe == null) {%>
			<div class="navbar main hidden-print">
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
			</div>
			<%}%>
			<div id="wrapper">

				<!-- Inicio conteudo -->
				<div id="">
					<div class='box-generic'>
						<form name='form' action='${ctx}/pages/historicoAcaoCurriculo/historicoAcaoCurriculo'>
							<input type="hidden" id='idHistoricoAcaoCurriculo' name='idHistoricoAcaoCurriculo'>
							<input type="hidden" id='idCurriculo' name='idCurriculo'>
							<input type="hidden" id='idUsuario' name='idUsuario'>
							<input type="hidden" id='acao' name='acao'>
							<input type="hidden" id='indiceTriagem' name='indiceTriagem'>
							<input type="hidden" id='tela' name='tela'>
							<div class="widget widget-tabs">


					<div class="widget-head">
						<ul>
							<li class="active"><a class="glyphicons coffe_cup" href="#tab-1" data-toggle="tab"><i></i><fmt:message key="rh.acaoCurriculo" /></a></li>
							<li class=""><a class="glyphicons list" href="#tab-2" data-toggle="tab"><i></i><fmt:message key="rh.historicoAcao" /></a></li>
						</ul>
					</div>

					<div class="widget-body">
						<div class="tab-content">

							<!-- Tab content -->
							<div class="tab-pane active" id="tab-1">
							<div class='row-fluid'>
								<div id='informacoesCandidato'></div>
								<div class='span12'>
									<label class='strong'><fmt:message key="rh.informeJustificativa" /></label>
									<select id='idJustificativaAcaoCurriculo' name='idJustificativaAcaoCurriculo' class='Valid[Required] Description[rh.informeJustificativa]'>
									</select>
								</div>
								<div class='span12'>
									<label class='strong'><fmt:message key="rh.informeComplementoJustificativa" /></label>
									<textarea rows="3" cols="70" class='span6 Valid[Required] Description[rh.informeComplementoJustificativa]' id='complementoJustificativa' name='complementoJustificativa' onkeyup="contCaracteres(this.value)" onkeydown="contCaracteres(this.value)" onFocus="contCaracteres(this.value)"> </textarea>
								</div>
								<div class='span8'>(<fmt:message key="citcorpore.comum.caracteresrestantes" /><span id="cont">200</span>)<br></div>

							</div>
							<div style="margin: 0;" class="form-actions">
								<div id='divInserirListaNegra'>
								<!-- S = Está na lista negra  -->
									<button type="button" id='btnInserirListaNegra' class='btn btn-warning' data-loading-text='Inserido.' onclick="gravar('S')"><fmt:message key="rh.inserirListaNegra" /></button>
								</div>
								<div id='divRetirarListaNegra' >
								<!-- N = Não está na lista negra  -->
									<button type="button" id='btnRetirarListaNegra' class='btn btn-primary' data-loading-text='Retirado.' onclick="gravar('N')"><fmt:message key="rh.removerListaNegra" /></button>
								</div>
							</div>
							</div>
							<div class="tab-pane" id="tab-2">
							<div class="widget widget-activity margin-none" data-toggle="collapse-widget" data-collapse-closed="false">
								<!-- <div class="widget-head">
									<h4 class="heading">Alterações do curriculo</h4>
									<span class=""></span>
								</div> -->
								<div class="widget-body collapse in">

									<div class='tab-content' >
										<div class='tab-pane active'>
											<ul class='list' id='divHistoricoAcoesCurriculo'>

											</ul>
										</div>
									</div>

							</div>
							</div>
							</div>
							</div>
						</form>
					</div>
				</div>
				<!--  Fim conteudo-->

				<%@include file="/novoLayout/common/include/rodape.jsp" %>

			</div>
		</div>
	</body>
</html>