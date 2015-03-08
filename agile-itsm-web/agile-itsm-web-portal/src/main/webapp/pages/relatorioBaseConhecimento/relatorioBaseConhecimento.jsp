<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO"%>
<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="./js/relatorioBaseConhecimento.js"></script>


<link rel="stylesheet" type="text/css" href="./css/relatorioBaseConhecimento.css" />
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
            <%@include file="/include/menu_horizontal.jsp"%>
            <div class="box grid_16 tabs">
                <ul class="tab_header clearfix">
                    <li><a href="#tabs-1"><fmt:message key="relatorioBaseConhecimento.relatorioBaseConhecimento"/></a></li>
                </ul>
                <a href="#" class="toggle">&nbsp;</a>
                <div class="toggle_container">
                    <div id="tabs-1" class="block" >
                        <div class="section" >
                            <form name='form' action='${ctx}/pages/relatorioBaseConhecimento/relatorioBaseConhecimento'>
                                <input type="hidden" id="idUsuarioAcesso" name="idUsuarioAcesso">
                                <div class="columns clearfix">
                                <div class="col_100">
                                    <div class="col_40">
                                        <fieldset>
                                            <label><fmt:message key="citcorpore.comum.periodo"/></label>
                                            <div>
                                                <table>
                                                    <tr>
                                                        <td>
                                                            <select onchange="mudaDivPeriodo(this.value);">
                                                                <option value="criacao" selected="selected"><fmt:message key="relatorioBaseConhecimento.periodoCriacaoBaseConhecimento"/></option>
                                                                <option value="publicacao"><fmt:message key="relatorioBaseConhecimento.periodoPublicacaoBaseConhecimento"/></option>
                                                                <option value="expiracao"><fmt:message key="relatorioBaseConhecimento.periodoExpiracaoBaseConhecimento"/></option>
                                                                <option value="acesso"><fmt:message key="relatorioBaseConhecimento.periodoAcessoBaseConhecimento"/></option>
                                                            </select>
                                                        </td>
                                                        <td style="padding-left: 5px;">
                                                        <!-- Alterado por
														     desenvolvedor: rcs (Rafael César Soyer)
														     data: 06/01/2015
														 -->
                                                            <div id='divPeriodoCriacao'>
                                                            </div>
                                                            <div id='divPeriodoPublicacao'>
                                                            </div>
                                                            <div id='divPeriodoExpiracao'>
                                                            </div>
                                                            <div id='divPeriodoAcesso'>
                                                            </div>
                                                            <!-- rcs - fim alteração -->
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col_30">
                                        <fieldset >
                                            <label><fmt:message key="pasta.pasta"/></label>
                                            <div>
                                                <select onchange="document.form.fireEvent('preencherComboBaseConhecimentoPorPasta')" name="idPasta"></select>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col_30">
                                        <fieldset >
                                                <label><fmt:message key="baseConhecimento.baseConhecimento"/></label>
                                            <div>
                                                <select name="idBaseConhecimento"></select>
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                                    <div class="col_25">
                                        <fieldset >
                                                <label><fmt:message key="citcorpore.comum.status"/></label>
                                            <div>
                                                <select name="status"></select>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col_25" >
                                        <fieldset >
                                            <label><fmt:message key="relatorioBaseConhecimento.mediaAvaliacao"/></label>
                                            <div>
                                            <select id="termoPesquisaNota" name="termoPesquisaNota">
                                                <option value=""><fmt:message key="citcorpore.comum.todos"/></option>
                                                <option value="0.0">0.0</option>
                                                <option value="1.0">1.0</option>
                                                <option value="2.0">2.0</option>
                                                <option value="3.0">3.0</option>
                                                <option value="4.0">4.0</option>
                                                <option value="5.0">5.0</option>
                                                <option value="S"><fmt:message key="citcorpore.comum.semAvaliacao"/></option>
                                            </select>
                                            </div>
                                            </fieldset>
                                    </div>
                                    <div class="col_25" >
                                        <fieldset>
                                            <label><fmt:message key="citcorpore.comum.ordenacao"/>:</label>
                                            <div>
                                                <select id="acessado" name="acessado">
                                                    <option value="A"><fmt:message key="relatorioBaseConhecimento.ordenarPorQuantidadeAcessos"/></option>
                                                    <option value="M"><fmt:message key="relatorioBaseConhecimento.ordenarPorMediaAvaliacao"/></option>
                                                    <option value="V"><fmt:message key="relatorioBaseConhecimento.ordenarPorVersao"/></option>
                                                </select>
                                            </div>
                                        </fieldset>
                                    </div >
                                    <div class="col_25">
                                        <fieldset >
                                            <label ><fmt:message key="relatorioBaseConhecimento.exibirUltimoAcesso"/></label>
                                            <div>
                                                <select name="ultimoAcesso" id="ultimoAcesso" >
                                                <option value="N"><fmt:message key="citcorpore.comum.nao"/></option>
                                                <option value="S"><fmt:message key="citcorpore.comum.sim"/></option>
                                                </select>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col_25">
                                        <fieldset >
                                            <label ><fmt:message key="relatorioBaseConhecimento.ultimasVersoes"/></label>
                                            <div>
                                                <select name="ultimaVersao" id="ultimaVersao" >
                                                <option value="N"><fmt:message key="citcorpore.comum.nao"/></option>
                                                <option value="S"><fmt:message key="citcorpore.comum.sim"/></option>
                                                </select>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col_25 ">
                                        <fieldset style="height: 52px">
                                            <label style="display: block; float: left;padding-top: 15px;"><input  type="checkbox" checked="checked" name="ocultarConteudo" id="ocultarConteudo" value="S" /><fmt:message key="relatorioBaseConhecimento.ocultarConteudo"/></label>
                                        </fieldset>
                                    </div>
                                </div>
                                    <div class="col_100">
                                    <fieldset>
                                        <button type='button' name='btnRelatorio' class="light"
                                            onclick="imprimirRelatorioBaseConhecimento()"
                                            style="margin: 20px !important;">
                                            <img

                                                src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
                                            <span ><fmt:message key="citcorpore.comum.gerarrelatorio"/></span>
                                        </button>
                                        <button type='button' name='btnRelatorio' class="light"
                                            onclick="imprimirRelatorioBaseConhecimentoXls()"
                                            style="margin: 20px !important;">
                                            <img
                                                src="${ctx}/template_new/images/icons/small/util/excel.png">
                                            <span ><fmt:message key="citcorpore.comum.gerarrelatorio"/></span>
                                        </button>
                                        <button type='button' name='btnLimpar' class="light"
                                            onclick='limpar()' style="margin: 20px !important;">
                                            <img
                                                src="${ctx}/template_new/images/icons/small/grey/clear.png">
                                            <span><fmt:message key="citcorpore.comum.limpar"/></span>
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
    <%-- <div id="POPUP_USUARIO" title="<fmt:message key="citcorpore.comum.pesquisaresponsavel" />">
        <div class="box grid_16 tabs">
            <div class="toggle_container">
                <div id="tabs-2" class="block">
                    <div class="section">
                        <form name='formPesquisaResponsavel' style="width: 540px">
                            <cit:findField formName='formPesquisaResponsavel'
                                lockupName='LOOKUP_USUARIO_BASECONHECIMENTO' id='LOOKUP_USUARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div> --%>
</html>

