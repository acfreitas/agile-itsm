<%@page import="br.com.centralit.citcorpore.util.Enumerados.TipoDate"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="java.util.Collection"%>

<!doctype html public "">
<html>
<head>
<%
			//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
			String iframe = "";
			iframe = request.getParameter("iframe");

            String idCotacao = (String)request.getAttribute("idCotacao");
%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>
<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>

<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
    <link href="${ctx}/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />


<script>
	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			document.form.idCotacao.value = '<%=idCotacao%>';
	        $('.tabs').tabs('select', 0);
	        habilitaDesabilitaCampos();
		}
	    document.form.afterLoad = function() {
            $('.tabs').tabs('select', 0);
            document.form.dataColeta.value = "<%=UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, new java.util.Date(), WebUtil.getLanguage(request))%>";
        }
	}

	function limpar(){
		var id = '';
        if (rowColeta != null)
            rowColeta.style.background = '';
        rowColeta = null;
        document.form.idCotacao.value = '<%=idCotacao%>';
		document.form.restore({idColetaPreco: id, idCotacao: document.form.idCotacao.value});
	}

    var rowColeta = null;
    function exibirColetaPrecos(row, obj) {
        if (rowColeta != null)
            rowColeta.style.background = '';
        rowColeta = row;
        row.style.background = '#FFCC99';
        var obj = HTMLUtils.getObjectByTableIndex("tblColetasPreco", row.rowIndex);
        var id = obj.idColetaPreco;
        if (id == '')
            return;
        document.form.idCotacao.value = '<%=idCotacao%>';
        document.form.restore({idColetaPreco: id, idCotacao: document.form.idCotacao.value});
    }

    function gerarImgAlteracaoColeta(row, obj) {
        row.cells[0].innerHTML = '<img style="cursor: pointer;" src="${ctx}/imagens/btnAlterarRegistro.gif" />';
    }

    function gravar() {
    	document.form.idCotacao.value = '<%=idCotacao%>';
        document.getElementById('idFornecedor').disabled = false;
        document.getElementById('idItemCotacao').disabled = false;
        document.form.save();
    }

    function excluir() {
         if (confirm(i18n_message("citcorpore.comum.deleta"))) {
             document.form.fireEvent("delete");
         }
    }

    function limpaDivCriterios() {
        document.getElementById("divCriterios").innerHTML = "";
        document.form.idCriterioColeta.value = "";
        document.form.pesoCriterioColeta.value = "";
    }

    function criaCampoCriterio(i, id, desc, valor) {
        var strCriterios = "";
        var div = null;
        div = document.getElementById("divCriterios");
        strCriterios = div.innerHTML;
        strCriterios += "<div class='col_50'>";
        strCriterios += "   <fieldset>";
        strCriterios += "       <label class='campoObrigatorio'>"+desc+"</label>";
        strCriterios += "       <div style='padding:5px 5px 5px 20px'>";
        strCriterios += "           <input type='hidden' name='idCriterioColeta' id='idCriterioColeta' value='"+id+"'/>";
        strCriterios += "           <input type='text' id='pesoCriterioColeta_"+id+"' name='pesoCriterioColeta' style='height:25px' maxlength='2' class='Valid[Required] Description["+desc+"] Format[Numero]'/>";
        strCriterios += "       </div>";
        strCriterios += "   </fieldset>";
        strCriterios += "</div>";
        div.innerHTML = strCriterios;
        DEFINEALLPAGES_generateConfiguracaoCampos();
        if (valor != '')
            eval("document.getElementById('pesoCriterioColeta_'+id).value = parseInt("+valor+");");
    }

    atualiza = function() {
        document.getElementById('idFornecedor').disabled = false;
        document.getElementById('idItemCotacao').disabled = false;
    	document.form.fireEvent('atualiza');
    }

    function habilitaDesabilitaCampos() {
	    if (!StringUtils.isBlank(document.form.idColetaPreco.value)) {
	        document.getElementById('idFornecedor').disabled = true;
	        document.getElementById('idItemCotacao').disabled = true;
	    }else{
	        document.getElementById('idFornecedor').disabled = false;
	        document.getElementById('idItemCotacao').disabled = false;
	        if (StringUtils.isBlank(document.form.dataColeta.value))
	        	document.form.dataColeta.value = "<%=UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, new java.util.Date(), WebUtil.getLanguage(request))%>";
	    }
    }

    montaItensFornecedor = function() {
    	document.form.fireEvent('montaItensFornecedor');
    }
</script>
<style>

    .linhaSubtituloGrid
    {
        color           :#000000;
        background-color: #d3d3d3;
        BORDER-RIGHT: thin outset;
        BORDER-TOP: thin outset;
        BORDER-LEFT: thin outset;
        BORDER-BOTTOM: thin outset;
        FONT-WEIGHT: bold;
        padding: 5px 0px 5px 5px;

    }
    .linhaGrid{
        border: 1px solid black;
        background-color:  #F2F2F2;
        vertical-align: middle;
    }
        .table {
            border-left:1px solid #ddd;
            width: 100%;
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

</style>
<style>
    div#main_container {
        margin: 10px 10px 10px 10px;
        width: 100%;
    }
</style>
</head>
<body>
<div class="box grid_16 tabs">
	<form name='form'
		action='${ctx}/pages/coletaPreco/coletaPreco'>
		<div class="columns clearfix">
            <input type='hidden' name='idColetaPreco' id='idColetaPreco'/>
            <input type='hidden' name='idCotacao' id='idCotacao' value='<%=idCotacao%>'/>
            <input type='hidden' name='idResponsavel' id='idResponsavel'/>
            <input type='hidden' name='situacao' id='situacao'/>
            <input type='hidden' name='idCriterioColeta' id='situacao'/>
            <input type='hidden' name='pesoCriterioColeta' id='pesoCriterioColeta'/>

            <div class="section">
              <div class="col_40">
	                 <h2 class="section">
	                     <fmt:message key="coletaPreco.coletasIncluidas" />
	                 </h2>
                     <div class="col_100" style='height:300px;overflow:auto' >
                         <table id="tblColetasPreco" class="table">
                             <tr>
                                 <th width="1%"></th>
                                 <th width="5%"><fmt:message key="coletaPreco.numero" /></th>
                                 <th width="40%"><fmt:message key="coletaPreco.item" /></th>
                                 <th ><fmt:message key="coletaPreco.fornecedor" /></th>
                             </tr>
                         </table>
                     </div>
              </div>
              <div class="col_60">
                     <h2 class="section">
                         <fmt:message key="cotacao.coletaPreco" />
                     </h2>
				    <ul class="tab_header clearfix">
				        <li><a href="#tabs-1"><fmt:message key="coletaPreco.identificacao" />
				        </a>
				        </li>
				        <li><a href="#tabs-2"><fmt:message key="coletaPreco.avaliacao" />
				        </a>
				        </li>
				    </ul>
				    <div class="toggle_container">
				        <div id="tabs-1" class="block">
				            <div class="section">
       	                        <div class="col_100">
					                 <fieldset>
					                     <label class="campoObrigatorio" style="cursor: pointer;"><fmt:message key="fornecedor" /></label>
					                     <div>
					                         <select name='idFornecedor' id='idFornecedor' class="Valid[Required] Description[fornecedor]" onchange="montaItensFornecedor()"></select>
					                     </div>
					                 </fieldset>
					             </div>
					             <div class="col_100">
					                  <fieldset>
					                      <label class="campoObrigatorio" style="cursor: pointer;"><fmt:message key="coletaPreco.item" /></label>
					                      <div>
					                          <select name='idItemCotacao' id='idItemCotacao' class="Valid[Required] Description[coletaPreco.item]" onchange="document.form.fireEvent('exibeCriteriosVariaveis');document.form.fireEvent('exibeEspecificacoesItem')"></select>
					                      </div>
					                  </fieldset>
					             </div>
                                 <div class="col_100">
                                     <fieldset>
                                         <label><fmt:message key="coletaPreco.especificacoes" />
                                         </label>
                                         <div>
                                             <textarea name="especificacoes" id="especificacoes" cols='200' rows='4' ></textarea>
                                         </div>
                                     </fieldset>
                                 </div>
                                <div class="col_50">
                                    <fieldset>
                                        <label class="campoObrigatorio"><fmt:message key="coletaPreco.dataColeta" />
                                        </label>
                                            <div>
                                                <input type='text' name="dataColeta" id="dataColeta" maxlength="10" size="10"
                                                    class="Valid[Required] Description[cotacao.dataColeta] Format[Data] datepicker" />
                                            </div>
                                    </fieldset>
                                </div>
                                <div class="col_50">
                                    <fieldset>
                                        <label><fmt:message key="coletaPreco.dataValidade" />
                                        </label>
                                        <div>
                                             <input type='text' name="dataValidade" id="dataValidade" maxlength="10" size="10"
                                                    class="Format[Data] datepicker" />
                                        </div>
                                    </fieldset>
                                </div>
                                 <div class="col_100">
                                     <fieldset>
	                                     <label ><h2><fmt:message key="citcorpore.comum.anexos"/></h2></label>
	                                         <cit:uploadControl  style="height:10%;width:100%; border-bottom:1px solid #DDDDDD ; border-top:1px solid #DDDDDD "  title="<fmt:message key='citcorpore.comum.anexos' />" id="uploadAnexos" form="document.form" action="/pages/upload/upload.load" disabled="false"/>
                                     </fieldset>
                                 </div>
                            </div>
                        </div>
                        <div id="tabs-2" class="block">
                            <div class="section">
                                <div class="col_50">
                                    <fieldset>
                                        <label class="campoObrigatorio"><fmt:message key="coletaPreco.quantidadeCotada" />
                                        </label>
                                        <div>
                                           <input id="quantidadeCotada" type='text' name="quantidadeCotada" class="Format[Numero] Valid[Required] Description[coletaPreco.quantidadeCotada]" />
                                        </div>
                                    </fieldset>
                                </div>
					            <div class="col_50">
					                <fieldset>
					                    <label class="campoObrigatorio"><fmt:message key="coletaPreco.preco" />
					                    </label>
					                    <div>
					                       <input id="preco" type='text' name="preco" class="Format[Moeda] Valid[Required] Description[coletaPreco.preco]" />
					                    </div>
					                </fieldset>
					            </div>
					            <div class="col_50">
					                <fieldset>
					                    <label class="campoObrigatorio"><fmt:message key="coletaPreco.valorFrete" />
					                    </label>
					                    <div>
					                       <input id="valorFrete" type='text' name="valorFrete" class="Format[Moeda] Valid[Required] Description[coletaPreco.valorFrete]" />
					                    </div>
					                </fieldset>
					            </div>
					            <div class="col_50">
					                <fieldset>
					                    <label class="campoObrigatorio"><fmt:message key="coletaPreco.valorDesconto" />
					                    </label>
					                    <div>
					                       <input id="valorDesconto" type='text' name="valorDesconto" class="Format[Moeda] Valid[Required] Description[coletaPreco.valorDesconto]" />
					                    </div>
					                </fieldset>
					            </div>
					            <div class="col_50">
					                <fieldset>
					                    <label class="campoObrigatorio"><fmt:message key="coletaPreco.prazoEntrega" />
					                    </label>
					                    <div>
					                       <input id="prazoEntrega" type='text' name="prazoEntrega" class="Format[Numero] Valid[Required] Description[coletaPreco.prazoEntrega]" />
					                    </div>
					                </fieldset>
					            </div>
					            <div class="col_50">
					                <fieldset>
					                    <label class="campoObrigatorio"><fmt:message key="coletaPreco.prazoPagto" />
					                    </label>
					                    <div>
					                       <input id="prazoMedioPagto" type='text' name="prazoMedioPagto" class="Format[Numero] Valid[Required] Description[coletaPreco.prazoPagto]" />
					                    </div>
					                </fieldset>
					            </div>
					            <div class="col_50">
					                <fieldset>
					                    <label class="campoObrigatorio"><fmt:message key="coletaPreco.taxaJuros" />
					                    </label>
					                    <div>
					                       <input id="taxaJuros" type='text' name="taxaJuros" class="Format[Moeda] Valid[Required] Description[coletaPreco.taxaJuros]" />
					                    </div>
					                </fieldset>
					            </div>
					            <div class="col_50">
					                <fieldset>
					                    <label class="campoObrigatorio"><fmt:message key="coletaPreco.prazoGarantia" />
					                    </label>
					                    <div>
					                       <input id="prazoGarantia" type='text' name="prazoGarantia" class="Format[Numero] Valid[Required] Description[coletaPreco.prazoGarantia]" />
					                    </div>
					                </fieldset>
					            </div>
                                <div id="divCriterios" class="col_100">
                                </div>
                            </div>
                        </div>
                    </div>
	              <div class="col_100">
	                   <button type='button' name='btnGravar' id='btnGravar' class="light" style='margin-top: 15px !important'
	                       onclick='gravar();'>
	                       <img
	                           src="${ctx}/template_new/images/icons/small/grey/create_write.png">
	                       <span><fmt:message key="citcorpore.comum.confirmar" />
	                       </span>
	                   </button>
	                   <button type='button' name='btnLimpar' class="light" style='margin-top: 15px !important'
	                       onclick='limpar();'>
	                       <img
	                           src="${ctx}/template_new/images/icons/small/grey/clear.png">
	                       <span><fmt:message key="citcorpore.comum.limpar" />
	                       </span>
	                   </button>
                       <button type='button' name='btnExcluir' id='btnExcluir' class="light" style='margin-top: 15px !important'
                           onclick='excluir();'>
                           <img
                               src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
                           <span><fmt:message key="citcorpore.comum.excluir" />
                           </span>
                       </button>
	              </div>
              </div>
            </div>
        </div>
    </form>
</div>
</body>

</html>