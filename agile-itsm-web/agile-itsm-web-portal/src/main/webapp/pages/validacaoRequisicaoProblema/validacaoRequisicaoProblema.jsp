<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
    <%
        String id = request.getParameter("id");
    %>
	<%@include file="/include/header.jsp" %>
    <%@include file="/include/security/security.jsp" %>
    <title><fmt:message key="citcorpore.comum.title"/></title>
    <%@include file="/include/menu/menuConfig.jsp" %>
    <%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
    <script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
    <script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/ValidacaoRequisicaoProblemaDTO.js"></script>

    <script>
        addEvent(window, "load", load, false);
        function load(){
            document.form.afterLoad = function () {
            	parent.escondeJanelaAguarde();
            }
        }
        function validar() {
            return document.form.validate();
        }

        function getObjetoSerializado() {
            var obj = new CIT_ValidacaoRequisicaoProblemaDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            return ObjectUtils.serializeObject(obj);
        }


    </script>
<style>
    div#main_container {
        margin: 10px 10px 10px 10px;
        width: 100%;
    }
</style>
</head>

<body>
<div >
     <form name='form' action='${ctx}/pages/validacaoRequisicaoProblema/validacaoRequisicaoProblema'>
           <input type='hidden' name='idValidacaoRequisicaoProblema' id='idValidacaoRequisicaoProblema' />
            <input type='hidden' name='dataInicio' id='dataInicio' />
            <input type='hidden' name='idProblema' id='idProblema'/>
	           <div class="columns clearfix">
				<div class="col_66">
					<fieldset>
						<label><fmt:message key="problema.observacaoProblema" /></label>
						<div>
							 <textarea cols='100' rows='5' name="observacaoProblema" maxlength="500"></textarea>
						</div>
					</fieldset>
				</div>
			</div>
        </form>
</div>

</body>

</html>
