<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="java.util.Collection"%>

<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
    <%
        String id = request.getParameter("id");


        String src=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")+"/imagens/localizarLookup.gif"/>'

		pageContext.setAttribute("src", src);
    %>
	<%@include file="/include/header.jsp" %>
    <%@include file="/include/security/security.jsp" %>
    <title><fmt:message key="citcorpore.comum.title"/></title>
    <%@include file="/include/menu/menuConfig.jsp" %>
    <%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
    <script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
    <script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/RequisicaoProdutoDTO.js"></script>
   <link href="${ctx}/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />

    <style type="text/css">
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

         div#main_container {
            margin: 0px 0px 0px 0px;
        }

        .container_16
        {
            width: 100%;
            margin: 0px 0px 0px 0px;

            letter-spacing: -4px;
        }
    </style>

</head>

<body>
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='${ctx}/pages/acionamentoGarantia/acionamentoGarantia'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
                                <input type='hidden' name='editar' id='editar' />
                                <input type='hidden' name='acao' id='acao'/>

                               <div class="col_100">
                                    <div class="col_50">
                                        <fieldset >
                                            <label><fmt:message key="requisicaoProduto.finalidade" /></label>
                                            <div>
                                                <select name='finalidade' id='finalidade' disabled="disabled"></select>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col_50">
                                         <fieldset >
                                             <label ><fmt:message key="centroResultado.custo" /></label>
                                             <div>
                                                 <select name='idCentroCusto' disabled="disabled"></select>
                                             </div>
                                         </fieldset>
                                    </div>
								</div>
                                <div class="col_100">
                                    <div class="col_40">
                                         <fieldset >
                                             <label ><fmt:message key="requisicaoProduto.projeto" /></label>
                                             <div>
                                                 <select name='idProjeto' disabled="disabled"></select>
                                             </div>
                                         </fieldset>
                                    </div>
                                    <div class="col_60">
                                         <fieldset >
                                             <label ><fmt:message key="requisicaoProduto.enderecoEntrega" /></label>
                                             <div>
                                                 <select name='idEnderecoEntrega' disabled="disabled"></select>
                                             </div>
                                         </fieldset>
                                    </div>
								</div>

                                <div class="col_100">
                                    <div class="col_50">
                                        <h2 class="section">
                                            <fmt:message key="requisicaoProduto.itens" />
                                        </h2>
                                    </div>
                                </div>
                                <div class="col_100" style="overflow:auto; height:300px">
                                    <table id="tblItensRequisicao" class="table">
                                        <tr>
                                            <th width="1%">&nbsp;</th>
                                            <th ><fmt:message key="itemRequisicaoProduto.descricao" /></th>
                                            <th ><fmt:message key="coletaPreco.fornecedor" /></th>
                                            <th width="10%"><fmt:message key="pedidoCompra.numero" /></th>
                                            <th width="10%"><fmt:message key="itemRequisicaoProduto.quantidade" /></th>
                                            <th width="20%"><fmt:message key="pedidoCompra.dataEntrega" /></th>
                                            <th width="20%"><fmt:message key="citcorpore.comum.situacao" /></th>
                                        </tr>
                                    </table>
                                </div>
                        </form>
                    </div>
            </div>
        </div>

<div id="POPUP_ITEM_REQUISICAO" title="<fmt:message key="pedidoCompra.avaliacao" />"  style="overflow: auto;">
    <form name='formItemRequisicao'>
        <input type='hidden' name='item#index'/>
        <div class="section">
            <div class="col_100">
                 <fieldset>
	                 <label><fmt:message key="coletaPreco.item" />
	                 </label>
                    <div>
                      <input id="item#descricaoItem"  type='text'  name="item#descricaoItem" readonly="readonly" />
                    </div>
                 </fieldset>
            </div>
            <div class="col_100">
                 <div class="col_20">
                     <fieldset>
                        <label><fmt:message key="fornecedor.cpfcnpj" />
                        </label>
                        <div>
                          <input id="item#cpfCnpjFornecedor"  type='text'  name="item#cpfCnpjFornecedor" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
                 <div class="col_80">
                     <fieldset>
                        <label><fmt:message key="fornecedor" />
                        </label>
                        <div>
                          <input id="item#nomeFornecedor"  type='text'  name="item#nomeFornecedor" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
            </div>
            <div class="col_100">
                 <div class="col_20">
                    <fieldset>
                        <label style="cursor: pointer;"><fmt:message key="itemRequisicaoProduto.quantidade" /></label>
                        <div>
                            <input id="item#quantidadeEntregue" type="text"  name='item#quantidadeEntregue' class="Format[Moeda]" readonly="readonly" />
                        </div>
                    </fieldset>
                 </div>
                 <div class="col_40">
                     <fieldset>
                        <label><fmt:message key="pedidoCompra.numero" />
                        </label>
                        <div>
                          <input id="item#numeroPedido"  type='text'  name="item#numeroPedido" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
                 <div class="col_40">
                     <fieldset>
                        <label><fmt:message key="pedidoCompra.dataEntrega" />
                        </label>
                        <div>
                          <input id="item#dataEntrega"  type='text'  name="item#dataEntrega" class="Format[Data]" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
            </div>
	       <div style="display: block;" id="validacao">
	           <div class="col_100">
	               <h2 class="section">
	                  <fmt:message key="itemRequisicaoProduto.aprovacao" />
	               </h2>
	           </div>
	           <div class="col_100">
                   <div class="col_50">
                        <fieldset >
                            <label><fmt:message key="itemRequisicaoProduto.justificativa" /></label>
                            <div>
                                <select id='item#idJustificativa'  name='item#idJustificativa' disabled='disabled'></select>
                            </div>
                        </fieldset>
                   </div>
                   <div class="col_50">
                       <fieldset >
                           <label><fmt:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
                           <div>
                                <textarea id="item#complementoJustificativa" name="item#complementoJustificativa" cols='60' rows='2' readonly="readonly"></textarea>
                           </div>
                       </fieldset>
                   </div>
	               </div>
	            </div>
            </div>
            <div class="col_100" >
                    <fieldset>
                        <div>
                            <button type="button" onclick='$("#POPUP_ITEM_REQUISICAO").dialog("close")'>
                                <fmt:message key="citcorpore.comum.fechar" />
                            </button>
                        </div>
                    </fieldset>
            </div>
        </div>
    </form>
</div>

<script type="text/javascript">
	var src = "${src}";
</script>

<script type="text/javascript" src="${ctx}/js/acionamentoGarantia.js"></script>

</body>

</html>
