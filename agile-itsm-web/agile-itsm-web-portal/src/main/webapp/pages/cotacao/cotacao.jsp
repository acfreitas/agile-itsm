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
%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
    <script type="text/javascript" src="${ctx}/js/boxover.js"></script>
<script type="text/javascript">
    var ctx = "${ctx}";
</script>
<script type="text/javascript" src="./js/cotacao.js"></script>
<link rel="stylesheet" type="text/css" href="./css/cotacao.css" />
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
            <link rel="stylesheet" type="text/css" href="./css/cotacaoIFrame.css">
<%}%>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix" style="height: auto !important;">
			<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="cotacao" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="cotacao.gerenciamento" />
					</a>
					</li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="cotacao.pesquisaAbertas" />
					</a>
					</li>
					<li><a href="#tabs-11" class="round_top"><fmt:message key="cotacao.pesquisaEncerradas" />
					</a>
					</li>
                    <li><a href="#tabs-3" class="round_top"><fmt:message key="cotacao.pesquisaRequisicoes" />
                    </a>
                    </li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/cotacao/cotacao'>
								<div class="columns clearfix">
                                    <input type='hidden' name='idItemCotacao' id='idItemCotacao'/>
                                    <input type='hidden' name='idEmpresa' id='idEmpresa'/>
                                    <input type='hidden' name='idResponsavel' id='idResponsavel'/>
                                    <input type='hidden' name='situacao' id='situacao'/>
                                    <input type='hidden' name='dataHoraCadastro' id='dataHoraCadastro'/>

                                    <div class="col_100">
                                         <div class="col_15">
                                            <fieldset>
                                                <label ><fmt:message key="cotacao.numero" />
                                                </label>
                                                   <div>
                                                       <input id="idCotacao" type='text' name="idCotacao" readonly="readonly" />
                                                   </div>
                                            </fieldset>
                                         </div>
                                         <div class="col_60">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="cotacao.identificacao" />
												</label>
		                                           <div>
		                                               <input id="identificacao" type='text' name="identificacao" maxlength="100" class="Valid[Required] Description[cotacao.identificacao]" />
		                                           </div>
											</fieldset>
								         </div>
                                         <div class="col_25">
                                              <fieldset>
                                                  <label class="campoObrigatorio"><fmt:message key="cotacao.dataFinal" /></label>
                                                  <div>
                                                     <input type='text' name="dataFinalPrevista" id="dataFinalPrevista" maxlength="10" size="10"
                                                            class="Valid[Required] Description[cotacao.dataFinal] Format[Data] datepicker" />
                                                  </div>
                                              </fieldset>
                                         </div>
                                    </div>
                                    <div class="col_50">
                                        <fieldset>
                                            <label><fmt:message key="cotacao.observacoes" />
                                            </label>
                                            <div>
                                                <textarea name="observacoes" id="observacoes" cols='200' rows='3' ></textarea>
                                            </div>
                                        </fieldset>
	                                </div>
                                    <div class="col_20">
                                       <fieldset>
                                           <label><fmt:message key="citcorpore.comum.situacao" />
                                           </label>
                                              <div>
                                                  <input id="situacaoStr" type='text' name="situacaoStr" readonly="readonly" />
                                              </div>
                                       </fieldset>
                                    </div>
                                    <div class="col_20">
                                        <fieldset>
	                                        <div style="padding:20px !important;margin-top: 15px !important">
				                                <button type='button' name='btnGravar' class="light"
				                                    onclick='document.form.save();'>
				                                    <img
				                                        src="${ctx}/template_new/images/icons/small/grey/pencil.png">
				                                    <span><fmt:message key="citcorpore.comum.gravar" />
				                                    </span>
				                                </button>
				                                <button type='button' name='btnLimpar' class="light"
				                                    onclick='limpar();'>
				                                    <img
				                                        src="${ctx}/template_new/images/icons/small/grey/clear.png">
				                                    <span><fmt:message key="citcorpore.comum.limpar" />
				                                    </span>
				                                </button>
	                                        </div>
                                        </fieldset>
                                    </div>
                                    <div id="divEncerramento" class="col_10" style='display:none'>
                                        <fieldset>
	                                        <div style="padding:20px !important;margin-top: 15px !important">
	                                            <button type='button' name='btnEncerrar' class="light"
	                                                onclick='encerrarCotacao();'>
	                                                <img
	                                                    src="${ctx}/template_new/images/icons/small/grey/locked_2.png">
	                                                <span><fmt:message key="cotacao.encerrarCotacao" />
	                                                </span>
	                                            </button>
	                                        </div>
                                        </fieldset>
                                    </div>
                                    <div class="col_100">
							            <div class="tabs" style="width: 100%;">
							                <ul class="tab_header clearfix">
                                                <li><a href="#tabs-4" class="round_top" onclick=''><fmt:message key="cotacao.itens" />
                                                </a>
                                                </li>
                                                <li><a href="#tabs-5" class="round_top" onclick=''><fmt:message key="cotacao.fornecedores" />
                                                </a>
                                                </li>
                                                <li><a href="#tabs-6" class="round_top" onclick='exibeColetasPrecos(); '><fmt:message key="cotacao.coletasPrecos" />
                                                </a>
                                                </li>
                                                <li><a href="#tabs-7" class="round_top" onclick='exibeResultadoCotacao();'><fmt:message key="cotacao.resultados" />
                                                </a>
                                                </li>
                                                <li><a href="#tabs-8" class="round_top" onclick='exibeAprovacao();'><fmt:message key="cotacao.aprovacoes" />
                                                </a>
                                                </li>
                                                <li><a href="#tabs-9" class="round_top" onclick='exibePedidos();'><fmt:message key="cotacao.pedidos" />
                                                </a>
                                                </li>
                                                <li><a href="#tabs-10" class="round_top" onclick='exibeEntregas();'><fmt:message key="cotacao.entregas" />
                                                </a>
                                                </li>
							                </ul>
                                            <a href="#" class="toggle">&nbsp;</a>
							                <div class="toggle_container">
                                                <div id="tabs-4" class="block">
                                                    <div class="columns clearfix">
                                                        <div class="col_100" style='height:640px;'>
                                                            <iframe id='fraItemCotacao' name='fraItemCotacao' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="tabs-5" class="block">
                                                    <div class="columns clearfix">
                                                        <div class="col_100" style='height:700px;'>
                                                            <iframe id='fraFornecedorCotacao' name='fraFornecedorCotacao' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                        </div>
                                                    </div>
                                                </div>

                                               <div id="tabs-6" class="block">
                                                   <div class="columns clearfix">
                                                       <div class="col_100" style='height:1000px;'>
                                                           <iframe id='fraColetaPreco' name='fraColetaPreco' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                       </div>
                                                   </div>
                                               </div>
                                               <div id="tabs-7" class="block">
                                                   <div class="columns clearfix">
                                                       <div class="col_100" style='height:500px;'>
                                                           <iframe id='fraResultadoCotacao' name='fraResultadoCotacao' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                       </div>
                                                   </div>
                                                </div>
                                               <div id="tabs-8" class="block">
                                                   <div class="columns clearfix">
                                                       <div class="col_100" style='height:500px;'>
                                                           <iframe id='fraAprovacao' name='fraAprovacao' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                       </div>
                                                   </div>
                                                </div>
                                                <div id="tabs-9" class="block">
                                                   <div class="columns clearfix">
                                                       <div class="col_100" style='height:1000px;'>
                                                           <iframe id='fraPedido' name='fraPedido' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                       </div>
                                                   </div>
                                                </div>
                                                <div id="tabs-10" class="block">
                                                   <div class="columns clearfix">
                                                       <div class="col_100" style='height:800px;'>
                                                           <iframe id='fraEntrega' name='fraEntrega' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                       </div>
                                                   </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div style="clear: both"></div>
    							</div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_COTACAO_ABERTAS' id='LOOKUP_COTACAO' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
					<div id="tabs-11" class="block">
						<div class="section">
							<form name='formPesquisaEncerradas'>
								<cit:findField formName='formPesquisaEncerradas'
									lockupName='LOOKUP_COTACAO_ENCERRADAS' id='LOOKUP_COTACAO_ENCERRADAS' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
					<div id="tabs-3" class="block">
                        <div class="section">
						    <form name='formItensRequisicao'
						        action='${ctx}/pages/itemCotacao/itemCotacao'>

						          <div class="columns clearfix">
						              <h2 class="section">
						                  <fmt:message key="cotacao.itensRequisicao" />
						              </h2>
						              <div class="col_100">
						                  <div class="col_20">
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
						                  <div class="col_40">
						                       <fieldset>
						                           <label style="cursor: pointer;"><fmt:message key="centroResultado.custo" /></label>
						                           <div>
						                               <select name='idCentroCusto' class="Valid[Required] Description[centroResultado.custo]"></select>
						                           </div>
						                       </fieldset>
						                  </div>
                                          <div class="col_40">
                                               <fieldset>
                                                   <label style="cursor: pointer;"><fmt:message key="requisicaoProduto.projeto" /></label>
                                                   <div>
                                                       <select name='idProjeto' class="Description[requisicaoProduto.projeto]"></select>
                                                   </div>
                                               </fieldset>
                                          </div>
						              </div>
						              <div class="col_100">
		                                     <div class="col_50">
		                                         <fieldset >
		                                             <label style="cursor: pointer;"><fmt:message key="requisicaoProduto.enderecoEntrega" /></label>
		                                             <div>
		                                                 <select name='idEnderecoEntrega' class="Description[requisicaoProduto.enderecoEntrega]"></select>
		                                             </div>
		                                         </fieldset>
		                                     </div>
						                     <div class="col_25">
						                          <fieldset>
						                              <label ><fmt:message key="requisicaoProduto.numero" /></label>
						                                 <div>
						                                     <input type='text' name="idSolicitacaoServico" id="idSolicitacaoServico" maxlength="10" size="10"
						                                            class="Format[Numero]" />
						                                 </div>
						                          </fieldset>
						                     </div>
						                     <div class="col_25">
						                          <fieldset>
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
						               <div class="col_100" style='height:300px;overflow:auto;'>
						                   <table id="tblItensRequisicao" class="table">
						                       <tr>
						                           <th width="20px">&nbsp;</th>
						                           <th width="4%"><fmt:message key="citcorpore.comum.numero" /></th>
						                           <th width="5%"><fmt:message key="citcorpore.comum.data" /></th>
						                           <th width="20%"><fmt:message key="centroResultado.custo" /></th>
						                           <th width="20%"><fmt:message key="requisicaoProduto.projeto" /></th>
						                           <th width="20%"><fmt:message key="requisicaoProduto.enderecoEntrega" /></th>
						                           <th colspan="2"><fmt:message key="itemRequisicaoProduto.produto" /></th>
						                           <th width="5%"><fmt:message key="itemRequisicaoProduto.quantidade" /></th>
						                       </tr>
						                   </table>
						               </div>
                                       <div id="divSelecionarItens"></div>
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

</html>

