<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<script type="text/javascript">
function buscaHistoricoPorVersao(){
	document.form.fireEvent("buscaHistoricoPorVersao");
}
</script>
<form  name='form'  action='${ctx}/pages/index/index'>
<div class="flat_area grid_16" style='opacity: 1; position: relative; width: 98% !important; height: 100% !important; letter-spacing: normal;'>
		<h2><fmt:message key="citcorpore.comum.bemvindo"/></h2>
		<h3><fmt:message key="release.historicoAtualizacoes"/></h3>
		<div style="width: 30%" >
		<label style='font-weight:bold;'><fmt:message key="release.versao"/></label>
			<select   name="versao" id="versao" onchange="buscaHistoricoPorVersao()"> </select>
		</div>
		<div  id="divRelease" style="width: 30%;   overflow: auto ; height: 542px ">
		</div>
</div>
</form>
