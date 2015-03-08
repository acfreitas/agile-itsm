<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@ page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Relatorio Item Configuracao PacoteOffice</title>
	<%@ include file="/include/header.jsp" %>
	<%@ include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title" /></title>
	<%@ include file="/include/menu/menuConfig.jsp" %>

	<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="../../cit/objects/MidiaSoftwareChaveDTO.js"></script>
	<script type="text/javascript" src="./js/relatorioItemConfiguracaoPacoteOffice.js"></script>
	<link rel="stylesheet" type="text/css" href="./css/relatorioItemConfiguracaoPacoteOffice.css" />
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="RelatorioItemConfiguracaoPacoteOffice.RelatorioItemConfiguracaoPacoteOffice" /></a></li>
				</ul>
				<div class="toggle_container">
					<div class="block" >
						<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/relatorioItemConfiguracaoPacoteOffice/relatorioItemConfiguracaoPacoteOffice'>
								<input  type="hidden" name="idMidiaSoftware" id="idMidiaSoftware" />
								<input  type="hidden" name="midiaSoftwareChavesSerealizadas" id="midiaSoftwareChavesSerealizadas" />
								<div class="columns clearfix col_66">
									<div class="col_50">
										<fieldset>
											<label style="cursor: pointer;"><fmt:message key="itemConfiguracaoTree.midia"/></label>
											<div>
												<input id="nomeMidia" name="nomeMidia" type="text" readonly="readonly" onclick="abrePopupMidia()"  />
											</div>
										</fieldset>
									</div>
									<div class="col_25" id="softwares" style="display: none;">
										<fieldset id="softwaresL">
											<label style="cursor: pointer;">Softwares</label>
											<div>
												<label><input checked="checked" type="radio" value="S" name="contem" id="contem">Licenciados</label>
												<label><input type="radio" value="N" name="contem" id="contem">Não licenciados</label>
											</div>
										</fieldset>
									</div>
									<div class="col_25" id="duplicados" style="display: none;">
										<fieldset>
											<label style="cursor: pointer;">
												<input id="duplicado" name="duplicado" type="checkbox" value="S"  />
												Procurar chaves duplicadas
											</label>
										</fieldset>
									</div>
									<div class="col_66">
										<fieldset>
											<div id="contentChaves" style="display: none">

											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<fieldset>
									<button type='button' name='btnRelatorio' class="light"
											onclick="imprimirRelatorioPacoteOffice()"
											style="margin: 20px !important;">
											<img
												src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
											<span><fmt:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnRelatorio' class="light"
											onclick="imprimirRelatorioPacoteOfficeXls()"
											style="margin: 20px !important;">
											<img
												src="${ctx}/template_new/images/icons/small/util/excel.png">
											<span><fmt:message key="citcorpore.comum.gerarRelatorio" /></span>
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
							</div>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
	<div id="POPUP_MIDIASOFTWARE" title='<fmt:message key="itemConfiguracaoTree.pesquisaMidia"/>' style="display: none;">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaMidiaSoftware'>
								<cit:findField formName='formPesquisaMidiaSoftware'
								lockupName='LOOKUP_MIDIASOFTWARE'
								id='LOOKUP_MIDIASOFTWARE' top='0' left='0' len='550' heigth='400'
								javascriptCode='true'
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>

