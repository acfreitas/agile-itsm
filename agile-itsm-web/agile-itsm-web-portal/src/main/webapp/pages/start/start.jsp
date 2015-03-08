<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.versao.Versao" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema"%>

<%
    String iframe = request.getParameter("iframe");
%>

<!doctype html public "">
<html>
<head>
    <%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
    <%@include file="/novoLayout/common/include/titulo.jsp" %>

    <link rel="stylesheet" type="text/css" href="${ctx}/novoLayout/common/include/css/template.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/pages/start/css/default.css" />

    <!-- Compatibilidade para simular telas do chrome no IE -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">

    <style type="text/css">
        #emailUsuario{
        font-size: 11px!important;
         text-overflow: ellipsis!important;
        }
        #sobre-container { display: -webkit-box;-webkit-box-orient: horizontal;display: -moz-box;-moz-box-orient: horizontal;display: box;box-orient: horizontal;margin-top: 10px;}
        #sobre-container h2, #versao-container h2 {font-size: 1.3em;margin-bottom: 0.4em;}
        #versao-container {margin-top: 30px;}

        #produto-descricao{margin-left: 10px;}
        #produto-container {line-height: 1.8em;margin-top: 100px;}
        #historico{display: none}
        .navbar.main .topnav > li.open .dropdown-menu{background:#363432;border:none;box-shadow:none;-webkit-box-shadow:none;-moz-box-shadow:none;right:0;width:283px;}
    </style>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;" />

<body>
    <div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">
        <!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
        <div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">
            <% if(iframe == null) { %>
            <%@include file="/novoLayout/common/include/cabecalhoInstalacao.jsp" %>
            <% } %>
        </div>

        <div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">
            <!-- Inicio conteudo -->
            <div id="content">
                <div class="browser-features" id="main">
                    <div class="compact marquee-side marquee-divider g-section g-tpl-nest g-split" id="marquee">
                        <div class="g-unit g-first marquee-copy g-col-8">
                            <div class="g-content" id="step">
                                <!-- STEP 1 -->
                                <div class="g-content-inner hide">
                                    <div class="row-fluid">
                                        <div class="span12 ">
                                            <h1 class="wrap"><fmt:message key="start.instalacao.bemVindo" /></h1>
                                            <section>
                                                <h3><fmt:message key="start.instalacao.termosServico" /></h3>
                                                <div>
                                                    <%
                                                    String locale_linguas_instalacao =UtilStrings.nullToVazio((String)request.getSession().getAttribute("locale"));
                                                    if(locale_linguas_instalacao==null || locale_linguas_instalacao.equals("")){
                                                        locale_linguas_instalacao = "pt-BR";
                                                    }
                                                    if(locale_linguas_instalacao.equals("pt-BR")) {
                                                    %>
                                                        <iframe class="termos" src="${ctx}/pages/start/termos_pt_BR.html"></iframe>
                                                    <%
                                                    } else if(locale_linguas_instalacao.equals("en")) {
                                                    %>
                                                        <iframe class="termos" src="${ctx}/pages/start/termos_en.html"></iframe>
                                                    <%
                                                    } else if(locale_linguas_instalacao.equals("es")) {
                                                    %>
                                                        <iframe class="termos" src="${ctx}/pages/start/termos_es.html"></iframe>
                                                    <%
                                                    } else {
                                                    %>
                                                        <iframe class="termos" src="${ctx}/pages/start/termos_pt_BR.html"></iframe>
                                                    <%
                                                    }
                                                    %>
                                                </div>
                                            </section>

                                            <div class="span4 widget-body uniformjs collapse in">
                                                <label class="checkbox">
                                                    <input type="checkbox" id="termos" name="termos" value="1" /><fmt:message key="start.instalacao.aceiteTermos" />
                                                </label>
                                            </div>
                                            <div class="span6">
                                                <input class="content first btn btn-primary" rel="step-2" type="button" value='<fmt:message key="start.instalacao.aceitarInstalar" />' />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="separator line bottom"></div>
                                </div>

                                <!-- STEP 2 -->
                                <div class="g-content-inner hide">
                                    <h1 class="wrap"><fmt:message key="start.instalacao.1passo" /></h1>
                                    <p><fmt:message key="start.instalacao.dadosConexao" /></p>
                                    <section>
                                        <h3><fmt:message key="start.instalacao.informacoesGerais" /></h3>
                                        <form class="form-horizontal" id="frmConexao" name="frmConexao" action="${ctx}/pages/start/start" method="post">
                                            <div class="row-fluid">
                                                <div class="span12">
                                                    <div class="control-group">
                                                        <input id="current" name="current" type="hidden" />
                                                        <label class="control-label" style="text-align:left;">
                                                            <fmt:message key="start.instalacao.driverConexao" />
                                                        </label>
                                                        <div class="controls">
                                                            <select name="driverConexao">
                                                                <option value="PostgreSQL" selected="selected"><fmt:message key="start.instalacao.postgresql" /></option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="separator line bottom"></div>
                                            <div class="form-actions">
                                                <input type="button" value="<fmt:message key="start.instalacao.anterior" />" class=" content btn btn-primary" rel="step-1">
                                                <input type="button" value="<fmt:message key="start.instalacao.proximo" />" id="install" class=" content fire btn btn-primary" rel="step-3" onclick="gerarCargaInicial(this);">
                                                <div id="Throbber" class="throbber"></div>
                                            </div>
                                        </form>
                                    </section>
                                </div>

                                <!-- STEP 3 -->
                                <div class="g-content-inner hide">
                                    <h1 class="wrap"><fmt:message key="start.instalacao.2passo" /></h1>
                                    <p><fmt:message key="start.instalacao.dadosEmpresa" /> </p>
                                    <section>
                                        <h3><fmt:message key="start.instalacao.informacoesGerais" /></h3>
                                        <form class="form-horizontal" id="frmEmpresa" name="frmEmpresa" action="${ctx}/pages/start/start" method="post" onsubmit="javascript:return false;">
                                        <input id="current" name="current" type="hidden" />
                                            <div class="row-fluid">
                                                <div class="span12">
                                                    <div class="control-group">
                                                        <label class="control-label campoObrigatorio"> <fmt:message key="empresa.empresa" />: </label>
                                                        <div class="controls">
                                                            <input id="nomeEmpresa" name="nomeEmpresa" type="text" size="60" maxlength="150">
                                                        </div>
                                                        <div class="separator line bottom"></div>
                                                    </div>
                                                </div>
                                                <div class="separator line bottom"></div>
                                                <label class="control-label"> <fmt:message key="empresa.detalhamento" />: </label>
                                                <div class="controls">
                                                    <textarea id="detalhamento" name="detalhamento" maxlength="2000" style="height: 116px; width: 391px;"></textarea>
                                                </div>
                                            </div>
                                            <div class="separator line bottom"></div>
                                            <div class="form-actions">
                                                <input type="button" value="<fmt:message key="start.instalacao.anterior" />" class=" content btn btn-primary" rel="step-2">
                                                <input type="button" value="<fmt:message key="start.instalacao.proximo" />" class=" content fire btn btn-primary" rel="step-4" onclick="cadastraEmpresa(this)">
                                            </div>
                                        </form>
                                    </section>
                                </div>

                                <!-- STEP 4 -->
                                <div class="g-content-inner hide">
                                    <h1 class="wrap"><fmt:message key="start.instalacao.3passo" /></h1>
                                    <p><fmt:message key="start.instalacao.configuracoesParametrizacoes" /></p>
                                    <section>
                                        <h3><fmt:message key="start.instalacao.autenticacao" /></h3>
                                        <form class="form-horizontal" id="frmLDAP" name="frmLDAP" action="${ctx}/pages/start/start" method="post">
                                            <input id="current" name="current" type="hidden" />
                                            <div class="row-fluid">
                                                <div class="span12">
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <input type="hidden" name="listAtributoLdapSerializado" id="idListAtributoLdapSerializado">
                                                        </div>
                                                        <div>
                                                            <div class="row-fluid">
                                                                <div class="span12">
                                                                    <label class="control-group">
                                                                        <fmt:message key="start.instalacao.metodoAutenticacao" />
                                                                        <select name="metodoAutenticacao" id="metodoAutenticacao">
                                                                            <option value="1"><fmt:message key="start.instalacao.proprio" /></option>
                                                                            <option value="2"><fmt:message key="start.instalacao.ldap" /></option>
                                                                        </select>
                                                                    </label>
                                                                </div>
                                                            </div>
                                                            <div id="LDAP">
                                                                <table class="tableLess" id="tabelaAtributosLdap">
                                                                    <thead>
                                                                    <tr>
                                                                        <th><fmt:message key="start.instalacao.atributo" /></th>
                                                                        <th><fmt:message key="start.instalacao.valor" /></th>
                                                                    </tr>
                                                                    </thead>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="separator line bottom"></div>
                                            <div class="form-actions">
                                                <input type="button" value="<fmt:message key="start.instalacao.anterior" />" class="content btn btn-primary" rel="step-3">
                                                <input type="button" value="<fmt:message key="start.instalacao.testarConexao" />" class=" btn btn-primary" id="testarLDAP" onclick="testaLDAP()">
                                                <input type="button" value="<fmt:message key="start.instalacao.proximo" />" class=" content fire btn btn-primary" rel="step-5" onclick="configuraLDAP(this)">
                                            </div>
                                        </form>
                                    </section>
                                </div>

                                <!-- STEP 5 -->
                                <div class="g-content-inner hide">
                                    <h1 class="wrap"><fmt:message key="start.instalacao.4passo" /></h1>
                                    <p><fmt:message key="start.instalacao.autenticacaoEmail" /></p>
                                    <section>
                                        <h3><fmt:message key="start.instalacao.informacoesGerais" /></h3>
                                        <form class="form-horizontal" id="frmEmail" name="frmEmail" action="${ctx}/pages/start/start" method="post">
                                            <input id="current" name="current" type="hidden" />
                                            <div class="row-fluid">
                                                <div class="span12">
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <input type="hidden" name="listAtributoEmailSerializado" id="idAtributoEmailSerializado">
                                                        </div>
                                                        <table class="tableLess m10" id="tabelaAtributosEmail">
                                                            <thead>
                                                                <tr>
                                                                    <th><fmt:message key="start.instalacao.atributo" /></th>
                                                                    <th><fmt:message key="start.instalacao.valor" /></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody></tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="separator line bottom"></div>
                                            <div class="form-actions">
                                                <input type="button" value="<fmt:message key="start.instalacao.anterior" />" class=" content btn btn-primary " rel="step-4">
                                                <input type="button" value="<fmt:message key="start.instalacao.proximo" />" class=" content fire btn btn-primary" rel="step-6" onclick="configuraEmail(this)">
                                            </div>
                                        </form>
                                    </section>
                                </div>

                                <!-- STEP 6 -->
                                <div class="g-content-inner hide">
                                    <form class="form-horizontal" id="frmLog" name="frmLog" action="${ctx}/pages/start/start.load" method="post" rel="step-7" onsubmit="configuraLog(this); return false;">
                                        <h1 class="wrap"><fmt:message key="start.instalacao.5passo" /></h1>
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <p><fmt:message key="start.instalacao.configuracoesParametrizacoes" /></p>
                                                    <section>
                                                        <h3><fmt:message key="start.instalacao.log" /></h3>
                                                        <div class="controls">
                                                            <input id="current" name="current" type="hidden" />
                                                            <input type="hidden" name="listAtributoLogSerializado" id="idAtributoLogSerializado">
                                                        </div>
                                                        <table class="tableLess m10" id="tabelaAtributosLog">
                                                            <thead>
                                                                <tr>
                                                                    <th><fmt:message key="start.instalacao.atributo" /></th>
                                                                    <th><fmt:message key="start.instalacao.valor" /></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody></tbody>
                                                        </table>
                                                    </section>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-actions">
                                            <input type="button" value="<fmt:message key="start.instalacao.anterior" />" class=" content btn btn-primary" rel="step-5">
                                            <input type="submit" value="<fmt:message key="start.instalacao.proximo" />" class=" content fire btn btn-primary">
                                        </div>
                                    </form>
                                </div>

                                <!-- STEP 7 -->
                                <div class="g-content-inner hide">
                                    <form class="form-horizontal" id="frmGed" rel="step-8" name="frmGed" action="${ctx}/pages/start/start.load" method="post" onsubmit="configuraGed(this); return false;">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <h1 class="wrap"><fmt:message key="start.instalacao.6passo" /></h1>
                                                    <p><fmt:message key="start.instalacao.configuracoesParametrizacoes" /></p>
                                                    <section>
                                                        <h3><fmt:message key="start.instalacao.ged" /></h3>
                                                        <div class="controls">
                                                            <input id="current" name="current" type="hidden" />
                                                            <input type="hidden" name="listAtributoGedSerializado" id="idAtributoGedSerializado">
                                                            <input id="diretorio" name="diretorio" type="hidden" />
                                                            <input id="campoDiretorio" name="campoDiretorio" type="hidden" />
                                                        </div>
                                                        <table class="tableLess m10" id="tabelaAtributosGed">
                                                            <thead>
                                                            <tr>
                                                                <th><fmt:message key="start.instalacao.atributo" /></th>
                                                                <th><fmt:message key="start.instalacao.valor" /></th>
                                                            </tr>
                                                            </thead>
                                                            <tbody></tbody>
                                                        </table>
                                                    </section>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-actions">
                                            <input type="button" value="<fmt:message key="start.instalacao.anterior" />" class=" content btn btn-primary" rel="step-6">
                                            <input type="submit" value="<fmt:message key="start.instalacao.proximo" />" class=" content fire btn btn-primary">
                                        </div>
                                    </form>
                                </div>

                                <!-- STEP 8 -->
                                <div class="g-content-inner hide">
                                    <form class="form-horizontal" id="frmSMTP" name="frmSMTP" action="${ctx}/pages/start/start" method="post">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <h1 class="wrap"><fmt:message key="start.instalacao.7passo" /></h1>
                                                    <p><fmt:message key="start.instalacao.configuracoesParametrizacoes" /></p>
                                                    <section>
                                                        <h3><fmt:message key="start.instalacao.smtp" /></h3>
                                                        <div class="controls">
                                                            <input id="current" name="current" type="hidden" />
                                                            <input type="hidden" name="listAtributoSMTPSerializado" id="idAtributoSMTPSerializado">
                                                        </div>
                                                        <table class="tableLess m10" id="tabelaAtributosSMTP">
                                                            <thead>
                                                                <tr>
                                                                    <th><fmt:message key="start.instalacao.atributo" /></th>
                                                                    <th><fmt:message key="start.instalacao.valor" /></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody></tbody>
                                                        </table>
                                                    </section>
                                                </div>
                                             </div>
                                        </div>
                                        <div class="form-actions">
                                            <input type="button" value="<fmt:message key="start.instalacao.anterior" />" class=" content btn btn-primary" rel="step-7">
                                            <input type="button" value="<fmt:message key="start.instalacao.proximo" />" class=" content fire btn btn-primary" rel="step-9" onclick="configuraSMTP(this)">
                                        </div>
                                    </form>
                                </div>
                            <%
                                if(!br.com.citframework.util.Util.isVersionFree(request)) {
                            %>
                                <!-- STEP 8 -->
                                <div class="g-content-inner hide">
                                    <form class="form-horizontal" id="frmIC" name="frmIC" action="${ctx}/pages/start/start.load" method="post" onsubmit="configuraParametrosIC(this); return false;" rel="step-10">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <h1 class="wrap"><fmt:message key="start.instalacao.8passo" /></h1>
                                                    <p><fmt:message key="start.instalacao.configuracoesParametrizacoes" /></p>
                                                    <section>
                                                        <h3><fmt:message key="start.instalacao.itemConfiguracao" /></h3>
                                                        <div class="controls">
                                                            <input id="current" name="current" type="hidden" />
                                                            <input type="hidden" name="listAtributoICSerializado" id="idAtributoICSerializado">
                                                        </div>
                                                        <table class="tableLess m10" id="tabelaAtributosIC">
                                                            <thead>
                                                                <tr>
                                                                    <th><fmt:message key="start.instalacao.atributo" /></th>
                                                                    <th><fmt:message key="start.instalacao.valor" /></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody></tbody>
                                                        </table>
                                                    </section>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-actions">
                                            <input type="button" value="<fmt:message key="start.instalacao.anterior" />" class=" content btn btn-primary" rel="step-8">
                                            <input type="submit" value="<fmt:message key="start.instalacao.proximo" />" class=" content fire btn btn-primary">
                                        </div>
                                    </form>
                                </div>

                                <!-- STEP 9 -->
                                <div class="g-content-inner hide">
                                    <form class="form-horizontal" id="frmBase" name="frmBase" action="${ctx}/pages/start/start.load" method="post" rel="step-11" onsubmit="configuraParametrosBase(this); return false;">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <h1 class="wrap"><fmt:message key="start.instalacao.9passo" /></h1>
                                                    <p><fmt:message key="start.instalacao.configuracoesParametrizacoes" /></p>
                                                    <section>
                                                        <h3><fmt:message key="start.instalacao.baseConhecimento" /></h3>
                                                        <div class="controls">
                                                            <input id="current" name="current" type="hidden" />
                                                            <input type="hidden" name="listAtributoBaseSerializado" id="idAtributoBaseSerializado">
                                                        </div>
                                                        <table class="tableLess m10" id="tabelaAtributosBase">
                                                            <thead>
                                                                <tr>
                                                                    <th><fmt:message key="start.instalacao.atributo" /></th>
                                                                    <th><fmt:message key="start.instalacao.valor" /></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody></tbody>
                                                        </table>
                                                    </section>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-actions">
                                            <input type="button" value="<fmt:message key="start.instalacao.anterior" />" class=" content btn btn-primary" rel="step-9">
                                            <input type="submit" value="<fmt:message key="start.instalacao.proximo" />" class=" content fire btn btn-primary">
                                        </div>
                                    </form>
                                </div>

                                <!-- STEP 10 -->
                                <div class="g-content-inner hide">
                                    <form class="form-horizontal" id="frmGeral" name="frmGeral" action="${ctx}/pages/start/start" method="post" onsubmit="configuraAplicacao(this); return false;">
                                        <input id="current" name="current" type="hidden" />
                                        <input type="hidden" name="listAtributoGeraisSerializado" id="idAtributoGeraisSerializado">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <h1 class="wrap"><fmt:message key="start.instalacao.10passo" /></h1>
                                                    <p><fmt:message key="start.instalacao.configuracoesParametrizacoes" /></p>
                                                    <section>
                                                        <h3><fmt:message key="start.instalacao.gerais" /></h3>
                                                        <table class="tableLess m10" id="tabelaAtributosGerais">
                                                            <thead>
                                                                <tr>
                                                                    <th><fmt:message key="start.instalacao.atributo" /></th>
                                                                    <th><fmt:message key="start.instalacao.valor" /></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody></tbody>
                                                        </table>
                                                    </section>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-actions">
                                            <input type="button" value="<fmt:message key="start.instalacao.anterior" />" class=" content btn btn-primary" rel="step-10">
                                            <input type="submit" id="concluir" value='<fmt:message key="start.instalacao.concluir" />' class=" content fire btn btn-primary"">
                                            <div id="Throbber_" class="throbber"></div>
                                        </div>
                                    </form>
                                </div>
                            <%
                                } else {
                            %>
                                <!-- STEP 9 -->
                                <div class="g-content-inner hide">
                                    <form class="form-horizontal" id="frmBase" name="frmBase" action="${ctx}/pages/start/start.load" method="post" rel="step-10" onsubmit="configuraParametrosBase(this); return false;">
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <h1 class="wrap"><fmt:message key="start.instalacao.8passo" /></h1>
                                                    <p><fmt:message key="start.instalacao.configuracoesParametrizacoes" /></p>
                                                    <section>
                                                        <h3><fmt:message key="start.instalacao.baseConhecimento" /></h3>
                                                        <div class="controls">
                                                            <input id="current" name="current" type="hidden" />
                                                            <input type="hidden" name="listAtributoBaseSerializado" id="idAtributoBaseSerializado">
                                                        </div>
                                                        <table class="tableLess m10" id="tabelaAtributosBase">
                                                            <thead>
                                                                <tr>
                                                                    <th><fmt:message key="start.instalacao.atributo" /></th>
                                                                    <th><fmt:message key="start.instalacao.valor" /></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody></tbody>
                                                        </table>
                                                    </section>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-actions">
                                            <input type="button" value="<fmt:message key="start.instalacao.anterior" />" class=" content btn btn-primary" rel="step-8">
                                            <input type="submit" value="<fmt:message key="start.instalacao.proximo" />" class=" content fire btn btn-primary">
                                        </div>
                                    </form>
                                </div>

                                <!-- STEP 10 -->
                                <div class="g-content-inner hide">
                                    <form class="form-horizontal" id="frmGeral" name="frmGeral" action="${ctx}/pages/start/start" method="post" onsubmit="configuraAplicacao(false); return false;">
                                        <input id="current" name="current" type="hidden" />
                                        <div class="row-fluid">
                                            <div class="span12">
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <input type="hidden" name="listAtributoGeraisSerializado" id="idAtributoGeraisSerializado">
                                                    </div>
                                                    <h1 class="wrap"><fmt:message key="start.instalacao.9passo" /></h1>
                                                    <p><fmt:message key="start.instalacao.configuracoesParametrizacoes" /></p>
                                                    <section>
                                                        <h3><fmt:message key="start.instalacao.gerais" /></h3>
                                                        <table class="tableLess m10" id="tabelaAtributosGerais">
                                                            <thead>
                                                                <tr>
                                                                    <th><fmt:message key="start.instalacao.atributo" /></th>
                                                                    <th><fmt:message key="start.instalacao.valor" /></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody></tbody>
                                                        </table>
                                                    </section>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-actions">
                                            <input type="button" value="<fmt:message key="start.instalacao.anterior" />" class=" content btn btn-primary" rel="step-9">
                                            <input type="submit" id="concluir" value='<fmt:message key="start.instalacao.concluir" />' class=" content fire btn btn-primary">
                                            <div id="Throbber_" class="throbber"></div>
                                        </div>
                                    </form>
                                </div>
                            <%
                                }
                            %>
                            </div>
                        </div>
                        <div class="g-unit marquee-image g-col-4"></div>
                    </div>
                </div>
            </div>
            <!-- Fim conteudo-->

            <div class="clearfix"></div>
            <!-- Inicio Rodape -->
            <% if(request.getParameter("iframe") == null) { %>
            <div id="footer" class="hidden-print">
                <!-- Copyright Line -->
                <div class="copy">© 2014 - <a target="_blank" href="http://www.citsmart.com.br"><fmt:message key="citcorpore.comum.title" /></a> - <fmt:message key="citcorpore.todosDireitosReservados" /> - <fmt:message key="login.versao" /> <b><%=Versao.getDataAndVersao()%> </div>
                <!-- End Copyright Line -->
            </div>
            <% } %>

            <%@include file="/novoLayout/common/include/libRodape.jsp" %>
            <!-- Fim Rodape -->

            <script type="text/javascript">
                $(document).ready(function() {
                    <% if(request.getAttribute("Script") != null) { out.print(request.getAttribute("Script").toString()); }%>
                });
                function buscaHistoricoPorVersao() {
                    document.formSobre.fireEvent("buscaHistoricoPorVersao");
                }
            </script>
            <script type="text/javascript" src="${ctx}/pages/start/js/default.js"></script>
        </div>
    </div>
</body>
</html>
