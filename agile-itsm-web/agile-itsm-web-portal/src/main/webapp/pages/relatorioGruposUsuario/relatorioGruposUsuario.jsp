<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<!doctype html public "">
<html>
<head>
<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_EMPREGADO_select(id, desc) {
		document.form.idColaborador.value = id;
		document.form.nomeColaborador.value = desc.split(" - ")[0];
		$("#POPUP_COLABORADOR").dialog("close");
	}

	$(function() {
	   $("#POPUP_COLABORADOR").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	   });
	});

	 $(function() {
			$("#addColaborador").click(function() {
				$("#POPUP_COLABORADOR").dialog("open");
			});
      });

      function imprimirRelatorioGruposUsuario(){
          document.form.formatoArquivoRelatorio.value = 'pdf';
		  document.form.fireEvent("imprimirRelatorioGruposUsuario");

		}

      function imprimirRelatorioGruposUsuarioXls(){
          document.form.formatoArquivoRelatorio.value = 'xls';
		  document.form.fireEvent("imprimirRelatorioGruposUsuario");

		}

  	function limpar() {
		document.form.clear();
	}

</script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="citcorpore.comum.relatoriogrupocolaborador" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message
								key="citcorpore.comum.relatoriogrupocolaborador" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/relatorioGruposUsuario/relatorioGruposUsuario'>
								<div class="columns clearfix">
									<input type='hidden' name='idColaborador'/>
								    <input type="hidden" id='formatoArquivoRelatorio' name='formatoArquivoRelatorio'>

									<div class="col_100">
										<fieldset style="height: 60px">
											<label><fmt:message key="menu.nome.colaborador" /></label>
											<div>
												<input class= "campoObrigatorio" type='text' name="nomeColaborador" id="addColaborador" maxlength="100" class="Valid[Required] Description[citcorpore.comum.colaborador]" />
											</div>
										</fieldset>
								   </div>
								</div>
								<div class="col_100">
									<fieldset>
									    <button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioGruposUsuario()' style="margin: 20px !important;">
										<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
										<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button>
										 <button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioGruposUsuarioXls()' style="margin: 20px !important;">
										<img src="${ctx}/template_new/images/icons/small/util/excel.png">
										<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light"  onclick='limpar()' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
											<span><fmt:message key="citcorpore.comum.limpar" /></span>
										</button>
									</fieldset>
								</div>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
<div id="POPUP_COLABORADOR" title="<fmt:message key="menu.nome.colaborador" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaColaborador' style="width: 540px">
						<cit:findField formName='formPesquisaColaborador'
							lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
 </div>
</html>