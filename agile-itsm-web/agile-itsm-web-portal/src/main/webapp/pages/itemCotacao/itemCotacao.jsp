<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao"%>
<!doctype html public "">
<html>
<head>

<%
			//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
			String iframe = "";
			iframe = request.getParameter("iframe");

			String idCotacao = (String)request.getAttribute("idCotacao");
			SituacaoCotacao situacao = (SituacaoCotacao)request.getAttribute("situacao");

%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>

<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
    <script type="text/javascript" src="../../cit/objects/FornecedorDTO.js"></script>
    <script type="text/javascript" src="${ctx}/js/boxover.js"></script>

<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
	}

    $(function() {
        $("#POPUP_ITENS_REQUISICAO").dialog({
            autoOpen : false,
            width : 1024,
            height : 570,
            modal : true
        });
        $("#POPUP_ITEM_COTACAO").dialog({
            autoOpen : false,
            width : 800,
            height : 420,
            modal : true
        });
    });

	function abrirPesquisaRequisicao() {
        HTMLUtils.deleteAllRows('tblItensRequisicao');
        document.getElementById('divSelecionarItens').style.display = 'none';
        document.formItensRequisicao.clear();
		$("#POPUP_ITENS_REQUISICAO").dialog("open");
	}

    function pesquisarRequisicoes() {
        document.formItensRequisicao.fireEvent('pesquisaItensParaCotacao');
    }

    function excluirItemCotacao(id) {
        if (confirm(i18n_message("citcorpore.comum.confirmaExclusao"))) {
            document.formItemCotacao.idItemCotacao.value = id;
            document.formItemCotacao.fireEvent('delete');
        }
    }

    function gerarSelecaoItemRequisicao(row, obj){
        obj.selecionado = 'N';
        var detalhes = "<table>";
        detalhes += "<tr><td style='padding:4px 4px;'><b><fmt:message key='calendario.descricao'/>:<b></td><td>"+obj.descricaoItem+"</td></tr>";
        if (obj.idProduto != '' && obj.codigoProduto != '')
            detalhes += "<tr><td style='padding:4px 4px;'><b><fmt:message key='centroResultado.codigo'/>:<b></td><td>"+obj.codigoProduto+"</td></tr>";
        if (obj.idProduto == '' && obj.marcaPreferencial != '')
           detalhes += "<tr><td style='padding:4px 4px;'><b><fmt:message key='itemRequisicaoProduto.marcaPreferencial'/>:<b></td><td>"+obj.marcaPreferencial+"</td></tr>";
        if (obj.especificacoes != '')
               detalhes += "<tr><td style='padding:4px 4px;'><b><fmt:message key='itemRequisicaoProduto.especificacoes'/>:<b></td><td>"+obj.especificacoes+"</td></tr>";
        detalhes += "</table>";
        row.cells[0].innerHTML = "<input type='checkbox' name='chkSel_"+obj.idItemRequisicaoProduto+"' id='chkSel_"+obj.idItemRequisicaoProduto+"' onclick='marcarDesmarcar(this,"+row.rowIndex+",\"tblItensRequisicao\")' title=\"header=[<fmt:message key='cotacao.detalhesDoItem'/>] body=[" + detalhes + "]\"/>";
                                // "<img style='cursor: pointer;' src='${ctx}/imagens/viewCadastro.png' title=\"header=[<fmt:message key='cotacao.detalhesDoItem'/>] body=[" + detalhes + "]\" />";
    }

    function marcarDesmarcar(chk, indice, tbl) {
        var obj = HTMLUtils.getObjectByTableIndex(tbl, indice);
        if (chk.checked)
            obj.selecionado = 'S';
        else
            obj.selecionado = 'N';
    }

    function marcarDesmarcarTodosItens(chk) {
        var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
        if (itensRequisicao == null)
            return;
        for(i=0;i<itensRequisicao.length;i++){
            var obj = itensRequisicao[i];
            if (chk.checked)
                obj.selecionado = 'S';
            else
                obj.selecionado = 'N';
            document.getElementById('chkSel_'+obj.idItemRequisicaoProduto).checked = chk.checked;
        }
    }

    function verificarInclusaoItensRequisicao() {
        var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
        if (itensRequisicao == null)
            return;
        var itensSelecionados = [];
        for(i=0;i<itensRequisicao.length;i++){
            var obj = itensRequisicao[i];
            if (obj.selecionado == 'S')
                itensSelecionados.push(obj);
        }
        if (itensSelecionados.length == 0) {
            alert(i18n_message("citcorpore.comum.nenhumaSelecao"));
            return;
        }
        if (!document.formItensRequisicao.tipoCriacao[0].checked && !document.formItensRequisicao.tipoCriacao[1].checked) {
            alert(i18n_message("itemCotacao.tipoCriacao") + " " + i18n_message("citcorpore.comum.naoInformado"));
            return;
        }
        if (document.formItensRequisicao.tipoCriacao[0].checked)
            document.formItemCotacao.tipoCriacaoItem.value = "1";
        else
            document.formItemCotacao.tipoCriacaoItem.value = "2";
        document.formItemCotacao.colItensRequisicao_Serialize.value = ObjectUtils.serializeObjects(itensSelecionados);
        document.formItemCotacao.fireEvent('verificaInclusaoItensRequisicao');
    }

    function exibirItemCotacao() {
        $("#POPUP_ITEM_COTACAO").dialog("open");
    }

    function incluirItensRequisicao() {
        if (StringUtils.isBlank(document.getElementById('idCategoriaProduto').value)){
	        alert(i18n_message("itemRequisicaoProduto.categoria")+" "+i18n_message("citcorpore.comum.naoInformado"));
	        document.getElementById('idCategoriaProduto').focus();
	        return false;
    	}
        document.formItemCotacao.fireEvent('incluiItensRequisicao');
        $("#POPUP_ITEM_COTACAO").dialog("close");
    }


    var offColor = '';
    function TrowOff(src){
        if (src == trCateg)
            return;
        src.style.background = offColor;
    }

    function TrowOn(src,OnColor){
        if (src == trCateg)
            return;
        //offColor = src.style.background;
        src.style.background = OnColor;
    }

    var trCateg = null;
    function posicionarCategoria(id) {
        if (trCateg != null)
            trCateg.style.background = '';
        trCateg = document.getElementById('trCateg_'+id);
        if (trCateg != null)
            trCateg.style.background = '#FFCC99';
    }

</script>

<style>
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

    div#main_container {
        margin: 10px 10px 10px 10px;
        width: 100%;
    }
</style>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>
<div class="box grid_16 tabs" style='width:100%;margin: 0px 0px 0px 0px;'>
        <div class="section">
            <div class="col_100">
                <div class="col_75">
                    <label>&nbsp;</label>
                </div>
                <div class="col_25">
                       <button type='button' name='btnPesquisarRequisicao' id='btnPesquisarRequisicao'  class="light"
                           onclick='abrirPesquisaRequisicao();'>
                           <img
                               src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
                           <span><fmt:message key="cotacao.pesquisarRequisicaoProduto" />
                           </span>
                       </button>
                </div>
            </div>
            <h2 class="section">
                <fmt:message key="cotacao.itensCotacao" />
            </h2>
            <div id="divItensCotacao" class="col_100" style='height:200px;overflow:auto;'>
           </div>
        </div>
</div>

<div id="POPUP_ITENS_REQUISICAO" title="<fmt:message key="cotacao.pesquisarRequisicaoProduto" />"  style="overflow: hidden;">
    <form name='formItensRequisicao'
        action='${ctx}/pages/itemCotacao/itemCotacao'>

          <div class="columns clearfix">
              <h2 class="section">
                  <fmt:message key="cotacao.itensRequisicao" />
              </h2>
              <div class="col_100">
                  <div class="col_33">
                       <fieldset >
                           <label class="campoObrigatorio"><fmt:message key="cotacao.periodoRequisicao" /></label>
                           <div class="col_100">
                              <div class="col_33" style="margin-top:0px !important; text-align: center !important;vertical-align:middle !important">
                                      <input type='text' name="dataInicio" id="dataInicio" maxlength="10" size="15"
                                             class="Format[Data] datepicker" />
                                             </div>
                                    <div class="col_15" style="margin-top:11px; text-align: center !important;vertical-align:middle !important">
                                      &nbsp;<fmt:message key="citcorpore.comum.a" />&nbsp;
                                </div>
                                <div class="col_33" style="text-align: center !important;vertical-align:middle !important">
                                      <input type='text' name="dataFim" id="dataFim" maxlength="10" size="15"
                                             class="Format[Data] datepicker" />
                                    </div>
                                    </div>
                       </fieldset>
                  </div>
                  <div class="col_33">
                       <fieldset >
                           <label><fmt:message key="centroResultado.custo" /></label>
                           <div>
                               <select name='idCentroCusto' class="Valid[Required] Description[centroResultado.custo]"></select>
                           </div>
                       </fieldset>
                  </div>
                  <div class="col_33">
                       <fieldset >
                           <label><fmt:message key="requisicaoProduto.projeto" /></label>
                           <div>
                               <select name='idProjeto' class="Description[requisicaoProduto.projeto]"></select>
                           </div>
                       </fieldset>
                  </div>
              </div>
              <div class="col_100">
                     <div class="col_50">
                         <fieldset >
                             <label class="campoObrigatorio"><fmt:message key="requisicaoProduto.enderecoEntrega" /></label>
                             <div>
                                 <select name='idEnderecoEntrega' class="Description[requisicaoProduto.enderecoEntrega]"></select>
                             </div>
                         </fieldset>
                     </div>
                     <div class="col_25">
                          <fieldset >
                              <label ><fmt:message key="requisicaoProduto.numero" /></label>
                                 <div>
                                     <input type='text' name="idSolicitacaoServico" id="idSolicitacaoServico" maxlength="10" size="10"
                                            class="Format[Numero]" />
                                 </div>
                          </fieldset>
                     </div>
                     <div class="col_25">
                          <fieldset >
                               <label >&nbsp;</label>
                               <div>
                                <button type='button' name='btnPesquisarRequisicao' class="light"
                                    onclick='pesquisarRequisicoes();'>
                                    <img
                                        src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
                                    <span><fmt:message key="citcorpore.comum.pesquisar" />
                                    </span>
                                </button>
                               </div>
                          </fieldset>
                     </div>
               </div>
               <div class="col_100">
                   <h2 class="section">
                       <fmt:message key="cotacao.itensRequisicao" />
                   </h2>
               </div>
               <div class="col_100" style='height:200px;overflow:auto;border:1px solid'>
                   <table id="tblItensRequisicao" class="table">
                       <tr>
                           <th><input type='checkbox' name='chkSelTodosItens' onclick='marcarDesmarcarTodosItens(this)' /></th>
                           <th width="4%"><fmt:message key="citcorpore.comum.numero" /></th>
                           <th width="5%"><fmt:message key="citcorpore.comum.data" /></th>
                           <th width="15%"><fmt:message key="centroResultado.custo" /></th>
                           <th width="15%"><fmt:message key="requisicaoProduto.projeto" /></th>
						   <th width="15%"><fmt:message key="requisicaoProduto.enderecoEntrega" /></th>
						   <th colspan="2"><fmt:message key="itemRequisicaoProduto.produto" /></th>
                           <th width="5%"><fmt:message key="itemRequisicaoProduto.quantidade" /></th>
                       </tr>
                   </table>
               </div>
               <div id="divSelecionarItens" class="col_100">
                       <div class="col_80">
                           <fieldset >
                               <label ><fmt:message key="itemCotacao.tipoCriacao"/></label>
                                       <input type='radio' id="tipoCriacao" name="tipoCriacao" value="1" class="Description[itemCotacao.tipoCriacao]" />
                                           <fmt:message key="itemCotacao.criarItem"/><br><br>
                                      <input type='radio' id="tipoCriacao" name="tipoCriacao" value="2" class="Description[itemCotacao.tipoCriacao]" />
                                           <fmt:message key="itemCotacao.associarMesmoItem"/>
                           </fieldset>
                       </div>
                       <div class="col_20">
                           <fieldset >
                                <div style='padding: 25px 5px 5px 5px !important'>
		                           <button type='button' name='btnSelecionarItens' id='btnSelecionarItens' class="light"
		                               onclick='verificarInclusaoItensRequisicao();'>
		                               <img
		                                   src="${ctx}/template_new/images/icons/small/grey/create_write.png">
		                               <span><fmt:message key="citcorpore.comum.confirmar" />
		                               </span>
		                           </button>
                                </div>
                           </fieldset>
                       </div>
               </div>
         </div>
    </form>
</div>

<div id="POPUP_ITEM_COTACAO" title="<fmt:message key="itemCotacao.tituloEspecificacao"/>"  style="overflow: hidden;">
    <form name='formItemCotacao'
        action='${ctx}/pages/itemCotacao/itemCotacao'>
          <input type='hidden' name='idCotacao' id='idCotacao' value='<%=idCotacao%>'/>
          <input type='hidden' name='idItemCotacao' id='idItemCotacao'/>
          <input type='hidden' name='tipoCriacaoItem' id='tipoCriacaoItem'/>
          <input type='hidden' name='tipoIdentificacao' id='tipoIdentificacao'/>
          <input type='hidden' name='idProjeto' id='idProjeto'/>
          <input type='hidden' name='idCentroCusto' id='idCentroCusto'/>
          <input type='hidden' name='colItensRequisicao_Serialize' />

         <div class="columns clearfix">
             <div class="section">
        		 <div class="col_100">
						<fieldset>
		                	<label class="campoObrigatorio" ><fmt:message key="itemRequisicaoProduto.categoria" /></label>
		                    <div>
		                    	<select id='idCategoriaProduto' name='idCategoriaProduto' ></select>
		 					</div>
						</fieldset>
			      </div>
                     <div class="col_100">
                         <fieldset >
                             <label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.produto" /></label>
                             <div>
                                 <input id="descricaoItem"  type='text'  name="descricaoItem" maxlength="200" />
                             </div>
                         </fieldset>
                     </div>
                     <div class="col_100">
                         <fieldset >
                             <label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.especificacoes" /></label>
                             <div>
                                  <textarea id="especificacoes" name="especificacoes" cols='60' rows='2' ></textarea>
                             </div>
                         </fieldset>
                      </div>
                      <div class="col_100">
                          <div class="col_50">
                              <fieldset >
                                  <label><fmt:message key="itemRequisicaoProduto.marcaPreferencial" /></label>
                                  <div>
                                      <input id="marcaPreferencial"  type='text'  name="marcaPreferencial" maxlength="100" />
                                  </div>
                              </fieldset>
                          </div>
                          <div class="col_25">
                              <fieldset >
                                  <label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.unidadeMedida" /></label>
                                  <div>
                                      <select id='idUnidadeMedida'  name='idUnidadeMedida' ></select>
                                  </div>
                              </fieldset>
                          </div>
                          <div class="col_25">
                              <fieldset >
                                  <label><fmt:message key="itemRequisicaoProduto.precoAproximado" /></label>
                                  <div>
                                      <input id="precoAproximado" type="text"  maxlength="15" name='precoAproximado' class="Format[Moeda]"/>
                                  </div>
                              </fieldset>
                          </div>
                      </div>
			      	<div class="col_100">
                             <div class="col_80">
                                 <label>&nbsp;</label>
                             </div>
                             <div class="col_20">
				                <button type="button" onclick='incluirItensRequisicao()'>
				                    <fmt:message key="citcorpore.comum.confirmar" />
				                </button>
				                <button type="button" onclick='$("#POPUP_ITEM_COTACAO").dialog("close");'>
				                    <fmt:message key="citcorpore.comum.fechar" />
				                </button>
			       			</div>
                  </div>
             </div>
        </div>
    </form>
</div>


</body>

</html>