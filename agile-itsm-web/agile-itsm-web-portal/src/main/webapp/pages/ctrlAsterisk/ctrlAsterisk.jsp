<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
	String PAGINA_SOLICITACAO_SERVICO = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO, "");

	String asteriskAtivo = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.SERVASTERISKATIVAR, "N");
	pageContext.setAttribute("asteriskAtivo", asteriskAtivo);
%>

<script type="text/javascript">
	var asterisk_ativo = "${asteriskAtivo}";
</script>

<div class="modal hide fade in" id="modal_ramalTelefone" aria-hidden="false" data-width='500'>
	<div class="modal-header">
	 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3><fmt:message key="citcorpore.comum.ramal"/></h3>
	</div>
	<div class="modal-body">
		<div class='slimScrollDiv'>
			<div class='slim-scroll' id='contentFrameOrigem'>
				<div id="conteudoframeRamalTelefone">
					<form id="formCtrlAsterisk" name="formCtrlAsterisk" action='${ctx}/pages/ctrlAsterisk/ctrlAsterisk'>
						<input type='hidden' id='listaChamadas' name='listaChamadas'/>
						<input autofocus="autofocus" type='text' name="ramalTelefone" id="ramalTelefone" maxlength="20" size="20" class="Description[citcorpore.comum.ramal.descricao]"/>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<div style="margin: 0;" class="form-actions">
			<a href="#" class="btn " onclick="gravarRamalTelefone();" data-dismiss="modal"><fmt:message key="citcorpore.comum.gravar" /></a>
		</div>
	</div>
</div>

<div class="modal hide fade in" id="modal_Telefonia" aria-hidden="false" data-width='500'>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3><fmt:message key="citcorpore.comum.telefone.vcRecebendoLigacao" /></h3>
	</div>
	<div class="modal-body">
		<div class='slimScrollDiv'>
			<div class='slim-scroll' id='contentFrameOrigem'>
				<div id="conteudoframeTelefonia"></div>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<div style="margin: 0;" class="form-actions"></div>
	</div>
</div>

<div class="modal hide fade in" id="modal_INCIDENTE" tabindex="-1" data-backdrop="static" data-keyboard="false">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3><fmt:message key='solicitacaoServico.solicitacao' /></h3>
	</div>
	<div class="modal-body">
		<div id="conteudoframeSolicitacaoServico"></div>
	</div>
</div>

<link rel="stylesheet" type="text/css" href="${ctx}/pages/ctrlAsterisk/css/asterisk.css" />

<script type="text/javascript" src="${ctx}/pages/ctrlAsterisk/js/asterisk.js"></script>
