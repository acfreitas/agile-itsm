<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO"%>
<%
    request.setCharacterEncoding("UTF-8");
    response.setHeader("Content-Language", "lang");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/include/header.jsp"%>

	<%@include file="/include/security/security.jsp"%>
	<title><fmt:message key="citcorpore.comum.title" /></title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="perfil.perfil" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="perfil.cadastro" /></a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="perfil.pesquisa" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="">
							<form name='form' action='${ctx}/pages/perfilAcesso/perfilAcesso'>
								<div class="columns clearfix">
									<input type='hidden' name='idPerfilAcesso' id='idPerfilAcesso' />
									<input type='hidden' name='dataFim' id='dataFim' />
									<input type='hidden' name='dataInicio' id='dataInicio' />
									<input type='hidden' name='acessoMenuSerializados' id="acessoMenuSerializados" value=""/>
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome" />
											</label>
											<div>
												<input id="nomePerfilAcesso" name="nomePerfilAcesso" type='text' maxlength="256" class="Valid[Required] Description[citcorpore.comum.nome]" onkeypress="return tratarEnter(this, event);"/>
											</div>
										</fieldset>
									</div>
								</div>
								<div>
								<div id="principalInf" style="margin-left: 20px; margin-bottom: 30px;"></div>
								</div>

								<div style="margin-left: 20px;">
									<label><b><fmt:message key="citcorpore.comum.acessoCitsmart" />:</b></label>
									<fmt:message key="citcorpore.comum.sim" /><input type="radio" name="acessoSistemaCitsmart" id="acessoSistemaCitsmartS" value="S" checked="checked">
									<fmt:message key="citcorpore.comum.nao" /><input type="radio" name="acessoSistemaCitsmart" id="acessoSistemaCitsmartN" value="N" onclick="acessarSistemaCitsmart();">
								</div>

								<div style="margin-bottom: 30px; margin-left: 20px;">
									<br>
									<table>
										<tr>
											<td>
												<b><fmt:message key="perfil.acessoPorSituacaoOS" /></b><br>
												<input type='checkbox' name='situacaoos' id='situacaoos1' value='1'/>&nbsp;<fmt:message key="perfil.criacao" /><br>
												<input type='checkbox' name='situacaoos' id='situacaoos2' value='2'/>&nbsp;<fmt:message key="perfil.solicitada" /><br>
												<input type='checkbox' name='situacaoos' id='situacaoos3' value='3'/>&nbsp;<fmt:message key="perfil.autorizada" /><br>
												<input type='checkbox' name='situacaoos' id='situacaoos4' value='4'/>&nbsp;<fmt:message key="perfil.aprovada" /><br>
												<input type='checkbox' name='situacaoos' id='situacaoos5' value='5'/>&nbsp;<fmt:message key="perfil.execucao" /><br>
												<input type='checkbox' name='situacaoos' id='situacaoos6' value='6'/>&nbsp;<fmt:message key="perfil.executada" /><br>
												<input type='checkbox' name='situacaoos' id='situacaoos7' value='7'/>&nbsp;<fmt:message key="perfil.cancelada" /><br>
											</td>
											<td style="padding-left: 50px;">
												<b><fmt:message key="perfil.acessoPorSituacaoFatura" /></b><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura1' value='1'/>&nbsp;<fmt:message key="perfil.criacao" /><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura2' value='2'/>&nbsp;<fmt:message key="perfil.aguardandoAprovacao" /><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura3' value='3'/>&nbsp;<fmt:message key="perfil.aprovada" /><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura4' value='4'/>&nbsp;<fmt:message key="perfil.rejeitada" /><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura5' value='5'/>&nbsp;<fmt:message key="perfil.recebimento" /><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura6' value='6'/>&nbsp;<fmt:message key="perfil.recebida" /><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura7' value='7'/>&nbsp;<fmt:message key="perfil.cancelada" /><br>
											</td>
										</tr>
									</table>
								</div>
								<div  style="margin-left: 20px; margin-bottom: 30px;">
									<button type='button' name='btnGravar' class="light" onclick='gravar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" /></span>
									</button>
									<button type="button" name='btnLimpar' class="light" onclick='document.form.clear();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar" /></span>
									</button>
									<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
										<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
										<span><fmt:message key="citcorpore.comum.excluir" /></span>
									</button>
								</div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
						<form name='formPesquisa'>
							<cit:findField formName='formPesquisa' lockupName='LOOKUP_PERFILACESSO' id='LOOKUP_PERFILACESSO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
		<!-- Fim POPUP ITEM DE CONFIGURǇÃO -->
		
	<script type="text/javascript" src="js/perfilAcesso.js"></script>
		
</body>
</html>