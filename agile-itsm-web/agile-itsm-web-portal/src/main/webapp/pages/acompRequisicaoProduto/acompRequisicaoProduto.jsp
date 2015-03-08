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
    <script type="text/javascript" src="${ctx}/js/boxover.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/RequisicaoProdutoDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/ItemRequisicaoProdutoDTO.js"></script>
    <script type="text/javascript" src="${ctx}/js/boxover.js"></script>

    <style type="text/css">
    	.tip_conteudo{
		    height: auto;
		    position: relative;
			float:left;
			padding-left:0.5em;
		    width: 500px;
			left: -23px;
		}
		.tip_conteudo_legenda{
			background-color: #F3F6F6;
		    border: 1px solid #D1D1D1;
		    border-radius: 5px 5px 5px 5px;
		    box-shadow: 0 0 8px -5px #000000;
		    display: block;
			font-family:Arial, Helvetica, sans-serif;
			font-size:12px;
			margin-left:1.4em;
			padding:0px 10px 0px 10px;
		}
		.tip_conteudo .seta {
		    background: url('<%=request.getContextPath()%>/imagens/seta.png') no-repeat scroll transparent;
		    display: block;
		    height: 37px;
		    position: absolute;
		    top: 0px;
		    width: 23px;
		    z-index: 45;
		}
		.tip_conteudo .opcoes {
		    background-color: #F3F6F6;
		    border: 1px solid #D1D1D1;
		    border-radius: 5px 5px 5px 5px;
		    box-shadow: 0 0 8px -5px #000000;
		    display: block;
		    width: 361px;
		}
		.tip_titulo{
			border-bottom: 1px solid #D8D8D8;
			font-size:14px;
			font-weight:bold;
			color:#FF0000;
			padding: 5px 0px 5px 0px;
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

    <script type="text/javascript">
        addEvent(window, "load", load, false);
        function load(){
            document.form.afterLoad = function () {
            	parent.escondeJanelaAguarde();
            }
        }

        $(function() {
            $("#POPUP_ITEM_REQUISICAO").dialog({
                autoOpen : false,
                width : 620,
                height : 630,
                modal : true
            });
            $("#POPUP_APROVADORES").dialog({
                autoOpen : false,
                width : 600,
                height : 250,
                modal : true
            });
        });

        function gerarImg (row, obj){
        	var title = 'title="header=[] body=['+
        	'<div class=\'tip_conteudo\'>'+
        	'	<div class=\'seta\'></div>'+
        	'	<div class=\'tip_conteudo_legenda\'>'+
        	'		<div class=\'tip_titulo\'>'+obj.descricaoItem+'</div>';
        	title += obj.descricaoFmtHtml;
        	title += '</div>'+
        	'</div>'+
        	']"';

            row.cells[0].innerHTML = '<img style="cursor: pointer;" src="${ctx}/imagens/localizarLookup.gif"/>';
            row.cells[1].innerHTML = '<span '+title+'>'+obj.descricaoItem+'</span>';
        };

        function posicionarCategoria(id) {
        	HTMLUtils.setValue('item#idCategoriaProduto', id);
        	document.form.idCategoriaProduto.value = id;
        }

        function editarItem(row, obj) {
            document.formItemRequisicao.clear();
            HTMLUtils.setValues(document.formItemRequisicao,'item',obj);
            document.getElementById('item#index').value = row.rowIndex;

            posicionarCategoria(obj.idCategoriaProduto);
            configuraJustificativa(obj);
            document.form.idCategoriaProduto.value = obj.idCategoriaProduto;
            document.form.idProduto.value = obj.idProduto;
            document.form.tipoIdentificacaoItem.value = obj.tipoIdentificacao;
            document.form.fireEvent('preparaTelaItemRequisicao');
        }

        function prepararTelaDigitacaoProduto() {
            document.getElementById('divProduto').style.display = 'none';
            document.getElementById('divDigitacao').style.display = 'block';
            document.form.tipoIdentificacaoItem.value = 'D';
        }

        function validar() {
            return document.form.validate();
        }

        function getObjetoSerializado() {
            var obj = new CIT_RequisicaoProdutoDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            obj.itensRequisicao_serialize = ObjectUtils.serializeObjects(itensRequisicao);
            return ObjectUtils.serializeObject(obj);
        }

        function configuraJustificativa(obj) {
        	document.getElementById('divValidacao').style.display = 'none';
            document.getElementById('divAutorizacao').style.display = 'none';
            if (obj.idParecerAutorizacao != '') {
                document.getElementById('divAutorizacao').style.display = 'block';
                document.getElementById('divJustificativa').style.display = 'none';
                document.getElementById('divAprovacao').style.display = 'none';
                var aprovado = document.formItemRequisicao['item#autorizado'];
	            if (obj.autorizado == 'S') {
	                aprovado[0].checked = true;
	                document.getElementById('divAprovacao').style.display = 'block';
	            }else if (obj.autorizado == 'N') {
	                aprovado[1].checked = true;
	                document.getElementById('divJustificativa').style.display = 'block';
	            }
            }else if (obj.idParecerValidacao != '') {
                document.getElementById('divValidacao').style.display = 'block';
                document.getElementById('divJustificativaValidacao').style.display = 'none';
                var aprovado = document.formItemRequisicao['item#validado'];
                if (obj.validado == 'S') {
                    aprovado[0].checked = true;
                }else if (obj.validado == 'N') {
                    aprovado[1].checked = true;
                    document.getElementById('divJustificativaValidacao').style.display = 'block';
                }
            }
        }

    </script>
</head>

<body>
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='${ctx}/pages/autorizacaoCotacao/autorizacaoCotacao'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
                                <input type='hidden' name='editar' id='editar' />
                                <input type='hidden' name='acao' id='acao'/>
                                <input type='hidden' name='idCategoriaProduto' id='idCategoriaProduto'/>
                                <input type='hidden' name='idProduto' id='idProduto'/>
                                <input type='hidden' name='tipoIdentificacaoItem' id='tipoIdentificacaoItem'/>

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
                                <div class="col_100" >
                                    <div class="col_70">
	                                    <fieldset>
	                                         <div style="padding:20px">
	                                            <label style='cursor:pointer'><input type='checkbox' value='S' name='rejeitada'/><b><fmt:message key="requisicaoProduto.rejeitarValidacao"/></b></label>
	                                         </div>
	                                    </fieldset>
                                    </div>
                                    <div class="col_30">
				                        <button type="button" onclick='$("#POPUP_APROVADORES").dialog("open")'>
				                            <fmt:message key="citcorpore.comum.visualizar" />&nbsp;<fmt:message key="requisicaoProduto.aprovadores" />
				                        </button>
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
                                            <th width="1px">&nbsp;</th>
                                            <th ><fmt:message key="itemRequisicaoProduto.descricao" /></th>
                                            <th width="7%"><fmt:message key="itemRequisicaoProduto.quantidade" /></th>
                                            <th width="30%"><fmt:message key="citcorpore.comum.situacao" /></th>
                                        </tr>
                                    </table>
                                </div>
                        </form>
                    </div>
            </div>
        </div>

<div id="POPUP_ITEM_REQUISICAO" title="<fmt:message key="requisicaoProduto.itens" />"  style="overflow: auto;">
    <form name='formItemRequisicao'>
        <input type='hidden' name='item#index'/>
        <input type='hidden' name='item#siglaUnidadeMedida'/>
        <input type='hidden' name='item#idProduto'/>
        <input type='hidden' name='item#idItemRequisicaoProduto'/>

		<div class="columns clearfix">
	        <div class="col_100">
				<fieldset>
                	<label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.categoria" /></label>
                    <div>
                    	<select id='item#idCategoriaProduto' name='item#idCategoriaProduto' disabled="disabled" ></select>
 					</div>
				</fieldset>
	        </div>
	        <div class="col_100">
	            <div class="col_80">
	                 <fieldset>
	                      <label><fmt:message key="itemRequisicaoProduto.produto" /></label>
	                     <div>
							<input type='text' name='item#descricaoItem' id='descricaoItem' readonly="readonly"/>
	                      </div>
	                  </fieldset>
				</div>

	            <div class="col_20">
	                <fieldset>
	                    <label><fmt:message key="itemRequisicaoProduto.quantidade" /></label>
	                    <div>
	                        <input id="item#quantidade" type="text"  maxlength="15" name='item#quantidade' class="Format[Moeda]" readonly="readonly"/>
	                    </div>
	                </fieldset>
	            </div>
			</div>
            <div id='divProduto' style='display:none' class="col_100">
                <div class="col_25">
                    <fieldset>
                        <div id='divImagemProduto' style='padding:10px 10px;'>
                        </div>
                    </fieldset>
                </div>
                <div class="col_50">
                    <fieldset>
                        <div id='divDetalhesProduto' style='padding:10px 10px;'>
                        </div>
                    </fieldset>
                </div>
                 <div class="col_25">
                        <fieldset>
                            <div id='divAcessorios' style='padding:10px 10px;'>
                            </div>
                        </fieldset>
                 </div>
            </div>
            <div id='divDigitacao' class="col_100">
                  <div class="col_100">
                      <fieldset>
                          <label><fmt:message key="itemRequisicaoProduto.especificacoes" /></label>
                          <div>
                               <textarea id="item#especificacoes" name="item#especificacoes" cols='60'  readonly="readonly"></textarea>
                          </div>
                      </fieldset>
                   </div>
                   <div class="col_100">
                       <div class="col_40">
                           <fieldset>
                               <label><fmt:message key="itemRequisicaoProduto.marcaPreferencial" /></label>
                               <div>
                                   <input id="item#marcaPreferencial"  type='text'  name="item#marcaPreferencial" maxlength="100" readonly="readonly"/>
                               </div>
                           </fieldset>
                       </div>
                       <div class="col_20">
                           <fieldset>
                               <label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.unidadeMedida" /></label>
                               <div>
                                   <select id='item#idUnidadeMedida'  name='item#idUnidadeMedida'  disabled="disabled"></select>
                               </div>
                           </fieldset>
                       </div>
                       <div class="col_40">
                           <fieldset>
                               <label><fmt:message key="itemRequisicaoProduto.precoAproximado" /></label>
                               <div>
                                   <input id="item#precoAproximado" type="text"  maxlength="15" name='item#precoAproximado' class="Format[Moeda]" readonly="readonly"/>
                               </div>
                           </fieldset>
                       </div>
                   </div>
                    <div class="col_100">
                        <div class="col_25" >
                            <fieldset>
                             <label>
                                 <fmt:message key="itemRequisicaoProduto.codigoProduto" />
                             </label>
                             <div>
                                   <input id="codigoProduto" type='text' name="item#codigoProduto" readonly="readonly"/>
                               </div>
                             </fieldset>
                        </div>
                        <div class="col_75" >
                              <fieldset>
                                  <label>
                                      <fmt:message key="itemRequisicaoProduto.descrProduto" />
                                  </label>
                                  <div>
                                      <input id="nomeProduto" type='text' name="item#nomeProduto" readonly="readonly"/>
                                  </div>
                             </fieldset>
                        </div>
					</div>
            </div>

	        <div id="divValidacao" class="col_100">
	                 <h2 class="section">
	                    <fmt:message key="itemRequisicaoProduto.validacao" />
	                 </h2>
	                <div class="col_100" >
	                    <fieldset >
	                        <input type='radio' name="item#validado" value="S" disabled="disabled"><fmt:message key="itemRequisicaoProduto.validado"/>
	                        <input type='radio' name="item#validado" value="N" disabled="disabled" ><fmt:message key="itemRequisicaoProduto.naoValidado"/>
	                    </fieldset>
	                </div>
	                <div id="divJustificativaValidacao" class="col_100" >
	                    <div class="col_50">
	                         <fieldset>
	                             <label><fmt:message key="itemRequisicaoProduto.justificativa" /></label>
	                             <div>
	                                 <select id='item#idJustificativaValidacao'  name='item#idJustificativaValidacao' disabled="disabled"></select>
	                             </div>
	                         </fieldset>
	                    </div>
	                    <div class="col_50">
	                        <fieldset>
	                            <label><fmt:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
	                            <div>
	                                 <textarea id="item#complemJustificativaValidacao" name="item#complemJustificativaValidacao" cols='60' rows='2' readonly="readonly"></textarea>
	                            </div>
	                        </fieldset>
	                    </div>
	                </div>
	        </div>

            <div class="col_100" id="divAutorizacao">
                <h2 class="section">
                    <fmt:message key="itemRequisicaoProduto.autorizacao" />
                 </h2>
                <div class="col_100" >
                    <fieldset >
                        <input type='radio' name="item#autorizado" value="S" disabled="disabled"><fmt:message key="itemRequisicaoProduto.autorizado"/>
                        <input type='radio' name="item#autorizado" value="N" disabled="disabled"><fmt:message key="itemRequisicaoProduto.naoAutorizado"/>
                    </fieldset>
                </div>
              	<div id="divJustificativa" class="col_100" >
                   	<div class="col_50">
                       <fieldset >
	                      <label><fmt:message key="itemRequisicaoProduto.justificativa" /></label>
	                      <div>
	                          <select id='item#idJustificativaAutorizacao'  name='item#idJustificativaAutorizacao' disabled="disabled"></select>
	                      </div>
                   		</fieldset>
                	</div>
	             	<div class="col_50">
		                 <fieldset >
		                     <label><fmt:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
		                     <div>
		                          <textarea id="item#complemJustificativaAutorizacao" name="item#complemJustificativaAutorizacao" cols='60' rows='2' readonly="readonly"></textarea>
		                     </div>
		                 </fieldset>
	             	</div>
        		</div>
                 <div id="divAprovacao" class="col_100" style='display:none'>
                    <div class="col_50">
                        <fieldset >
                            <label><fmt:message key="itemRequisicaoProduto.percVariacaoPreco" /></label>
                            <div>
                                 <input id="item#percVariacaoPreco" type="text"  maxlength="15" name='item#percVariacaoPreco' class="Valid[Required] Description[itemRequisicaoProduto.percVariacaoPreco] Format[Moeda]" readonly="readonly"/>
                            </div>
                        </fieldset>
                    </div>
                    <div class="col_50">
                        <fieldset >
                            <label><fmt:message key="itemRequisicaoProduto.qtdeAprovada" /></label>
                                    <div>
                                         <input id="item#qtdeAprovada" type="text"  maxlength="15" name='item#qtdeAprovada' class="Valid[Required] Description[itemRequisicaoProduto.qtdeAprovada] Format[Moeda]" readonly="readonly"/>
                                    </div>
                                </fieldset>
                         </div>
					</div>
				</div>
                    <div >
                        <button type="button" onclick='$("#POPUP_ITEM_REQUISICAO").dialog("close")'>
                            <fmt:message key="citcorpore.comum.fechar" />
                        </button>
                    </div>
        </div>
    </form>
</div>

<div id="POPUP_APROVADORES" title="<fmt:message key="requisicaoProduto.aprovadores" />"  style="overflow: auto;">
	<div class="columns clearfix">
         <div class="col_100" >
              <div id='divAprovadores' style="overflow:auto;">
              </div>
         </div>
         <div class="col_100" >
              <div id='divLoginAprovadores' style="overflow:auto;margin: 10px 10px 0px;">
              </div>
         </div>
	</div>
</div>

</body>

</html>
