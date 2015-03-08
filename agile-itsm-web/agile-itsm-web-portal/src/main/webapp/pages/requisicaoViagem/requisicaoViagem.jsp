<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
		<%@include file="/novoLayout/common/include/titulo.jsp"%>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		
		<link type="text/css" rel="stylesheet" href="css/RequisicaoViagem.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
				
		<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>		
		<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/EmpregadoDTO.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/RequisicaoViagemDTO.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/IntegranteViagemDTO.js"></script>
	    
	    	<%
	        response.setHeader("Cache-Control", "no-cache"); 
	        response.setHeader("Pragma", "no-cache");
	        response.setDateHeader("Expires", -1);
    	%>
	    
	    <title><fmt:message key="citcorpore.comum.title"/></title>

	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div class="nowrapper">
			<!-- Inicio conteudo -->
			<div id="content">
				<form id="form" name="form" action="${ctx}/pages/requisicaoViagem/requisicaoViagem">
					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
                    <input type='hidden' name='editar' id='editar' /> 
                    <input type='hidden' name='acao' id='acao'/> 
                    <input type='hidden' name='idCategoriaProduto' id='idCategoriaProduto'/> 
                    <input type='hidden' name='idProduto' id='idProdutoForm'/> 
                    <input type='hidden' name='tipoIdentificacaoItem' id='tipoIdentificacaoItem'/> 
                    <input type='hidden' name='identificacao' id='identificacaoItemForm'/>
                    <input type='hidden' name='nomeMarca' id='nomeMarca'/>
                    <input type='hidden' name='precoMercado' id='precoMercado'/>
                    <input type='hidden' name='idUnidadeMedida' id='idUnidadeMedida'/>
                    <input type='hidden' name='siglaUnidadeMedida' id='siglaUnidadeMedida'/>
                    <input type='hidden' name='detalhes' id='detalhes'/>
                    <input type='hidden' name='itemIndex'/>
                    <input type='hidden' name='integranteViagemSerialize'/>
                    <input type='hidden' name='estado'/>
                    <input type='hidden' name='idCidadeOrigem'/>
                    <input type='hidden' name='idCidadeDestino'/>
                    <input type='hidden' name='idIntegranteAux' id="idIntegranteAux"/>
                    <input type='hidden' name='nomeCidade' id="nomeCidade"/>
                    <input type='hidden' name='idEmpregado' id="idEmpregado"/>
                    <input type='hidden' name='idRespPrestacaoContas' id="idRespPrestacaoContas"/>
                    <input type='hidden' name='idNaoFuncionario' id="idNaoFuncionario"/>
                    <input type='hidden' name='nomeNaoFuncionarioAux' id="nomeNaoFuncionarioAux"/>
                    <input type='hidden' name='infoNaoFuncionarioAux' id="infoNaoFuncionarioAux"/>
                    
                    <input type='hidden' name='rowIndexIntegrante' id="rowIndexIntegrante"/>
                    
                    <div class="widget">
                    	<div class="widget-head">
                        	<h2 class="heading"><fmt:message key="requisicaoViagem.solicitacaoViagem" /></h2>
                        </div><!-- .widget-head -->
		                         	
	                      	<div class="widget-body">
	                      		<div class="widget row-fluid">
	                      			<div class="widget-head">
			                        	<h3 class="heading"><fmt:message key="requisicaoViagem.dadosGerais" /></h3>
			                        </div><!-- .widget-head -->
			                        
				                    <div class="widget-body">
				                    	<div class="row-fluid">
			                            	<div class="span6" id='divNomeDaCidadeOrigem'>
			                           			<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.origem" /></label>	
			                            		<input id="nomeCidadeEUfOrigem" name="nomeCidadeOrigem" class="span12" type="text" required="required"/>
			                            	</div><!-- .span6 -->
			                            	<div class="span6">
			                            		<label class='strong campoObrigatorio'><fmt:message key="importmanager.destino" /></label>
			                            		<input id="nomeCidadeEUfDestino" name="nomeCidadeDestino" class="span12" type="text" required="required"/>
			                            	</div><!-- .span6 -->
			                           	</div><!-- .row-fluid -->
			                           
			                            <div class="row-fluid">
			                            	 <div class="span4">
			                            	 	<label for="dataInicioViagem" class="strong campoObrigatorio"><fmt:message key="itemControleFinanceiroViagem.ida"/></label>
			                            	 	<input id="dataInicioViagem" name="dataInicioViagem" maxlength="10" type="text" class="Format[Date] Description[Ida] Valid[Date] text datepicker span12" onchange="calcularQuantidadeDias(this)" />
			                            	 </div><!-- .span4 -->
			                            	 <div class="span4">
			                            	 	<label for="dataFimViagem" class="strong"><fmt:message key="itemControleFinanceiroViagem.volta"/></label>
			                            	 	<input id="dataFimViagem" name="dataFimViagem" maxlength="10" type="text" class="Format[Date] Description[Volta] text datepicker span12" onchange="calcularQuantidadeDias(this)" />
			                            	 </div><!-- .span4 -->
			                            	 <div class="span4">
			                            	 	<label for="qtdeDias" class="strong"><fmt:message key="requisicaoViagem.quantidadeDias"/></label>
			                            	 	<input id="qtdeDias" name="qtdeDias" maxlength="10" type="text" readonly="readonly" class="span12"/>
			                            	 </div><!-- .span4 -->
			                            </div><!-- .row-fluid -->
			                           
			                            <div class="row-fluid">
			                            	<div class="span4">
			                            		<label for="idCentroCusto" class="strong campoObrigatorio"><fmt:message key="centroResultado"/></label>
			                            	 	<select id='idCentroCusto' name='idCentroCusto' class="Valid[Required] Description[centroResultado.custo] span12"></select>
			                            	</div><!-- .span4 -->
			                            	<div class="span4">
			                            		<label for="idProjeto" class="strong campoObrigatorio"><fmt:message key="requisicaoProduto.projeto"/></label>
			                            	 	<select name='idProjeto' class="Valid[Required] Description[requisicaoProduto.projeto] span12"></select>
			                            	</div><!-- .span4 -->
			                            	<div class="span4">
			                            		<label for="idMotivoViagem" class="strong campoObrigatorio"><fmt:message key="requisicaoViagem.justificativa"/></label>
			                            	 	<select name='idMotivoViagem' class="Valid[Required] Description[requisicaoViagem.justificativa] span12"></select>
			                            	</div><!-- .span4 -->
			                            </div><!-- .row-fluid -->
			                           
			                            <div class="row-fluid">
			                            	<div class="span12">
			                            		<label for="descricaoMotivo" class="strong campoObrigatorio"><fmt:message key="requisicaoViagem.motivo"/></label>
			                            		<textarea name="descricaoMotivo" id="descricaoMotivo" cols='200' rows='5' maxlength = "2000" class="span12"></textarea>
			                            	</div><!-- .span12 -->
			                            </div><!-- .row-fluid -->
				                    </div><!-- widget-body -->
	                      		
	                      		</div><!-- widget row-fluid -->
	                      		
	                      		<div class="widget row-fluid">
	                      			<div class="widget-head">
			                        	<h3 class="heading"><fmt:message key="requisicaoViagem.integrantes" /></h3>
			                        </div><!-- .widget-head -->
			                        
				                    <div class="widget-body">
				                    	<div class="row-fluid">
			                           		<div class="span12" id='divEmpregadoOuNao' hidden="true">
												<label class="campoObrigatorio strong">
													<fmt:message key="requisicaoViagem.integranteFuncionario" />?
												</label>
												
												<div>
													<label class="radio inline strong">
														<input type="radio" id="integranteFuncionario" name="integranteFuncionario" value="S" checked="checked" /> <fmt:message key="citcorpore.comum.sim" />
													</label>
													<label class="radio inline strong">
														<input type="radio" id="integranteNaoFuncionario" name="integranteFuncionario" value="N" /> <fmt:message key="citcorpore.comum.nao" />
													</label>
												</div>
			                            	</div><!-- #divEmpregadoOuNao -->
			                           	</div><!-- .row-fluid -->
			                           	
			                            <div class="row-fluid">
			                            	
											<div class="input-container">
												<div class="row-fluid">
													<div>
								                        <div class="span6" id='divEmpregado'>
								                        	<label for="nomeEmpregado" class="strong campoObrigatorio"><fmt:message key="visao.nome" /></label>	
								                            <input id="nomeEmpregado" name="nomeEmpregado" type="text" required="required" class="span12"/>
								                        </div><!-- #divEmpregado -->
								                    </div>
								                    
								                    <div>    
								                        <div class="span6" id='divNaoFuncionario'>
								                        	<label for="nomeNaoFuncionario" class="strong campoObrigatorio"><fmt:message key="visao.nome" /></label>	
								                        	<input id="nomeNaoFuncionario" name="nomeNaoFuncionario" type="text" required="required" class="span12" onblur="validaCampos()" />
								                        </div><!-- #divNaoFuncionario -->
						                   			</div>
					                            	
					                            	<div class="span6" id='divResponsavelEmpregado' >
					                           			<label for="respPrestacaoContas" class="strong campoObrigatorio"><fmt:message key="requisicaoViagem.responsavelPrestacaoDeContas" /></label>	
					                            		<input id="respPrestacaoContas" name="respPrestacaoContas" type="text" class="span12" />
					                            	</div><!-- #divResponsavelEmpregado -->
					                            </div><!-- .row-fluid -->
					                            
					                            <div class="row-fluid" id="divInfoNaoFuncionario">
						                        	<label for="infoNaoFuncionario" class="strong "><fmt:message key="requisicaoViagem.informacoesAdicionaisNaoFuncionario" /></label>	
						                            <textarea id="infoNaoFuncionario" name="infoNaoFuncionario" type="text" class="span12" rows="4"></textarea>
					                            </div><!-- .row-fluid -->
			                            	</div><!-- .input-container -->
			                           	</div><!-- .row-fluid -->
			
			                            <div class="row-fluid">
			                            	<div class="innerTB span6">
			                            		<button type='button' name='btnAddIntegranteViagem' class="lFloat btn btn-icon btn-primary" onclick='adicionarEmpregado();'>
													<i></i><fmt:message key="citcorpore.comum.adicionar" />
												</button>
			                            	</div><!-- .btn-container -->
			                            </div><!-- .row-fluid -->
			                           
			                           	<div class="row-fluid">
			                            	<div class="span12">
			                                    <table id="tblIntegranteViagem" class="table table-condensed table-striped table-hover table-bordered" style="overflow: auto">
			                                        <tr>
			                                        	<th width="25%"><fmt:message key="itemControleFinanceiroViagem.nome" /></th>
			                                        	<th width="5%"><fmt:message key="candidato.funcionario" /></th>
			                                        	<th width="25%"><fmt:message key="citcorpore.comum.responsavel" /></th>
			                                            <th width="10%" ><fmt:message key="" /></th>
			                                        </tr>
			                                    </table>
			                              	</div><!-- .span12 -->
			                           	</div><!-- .row-fluid -->
				                    </div><!-- widget-body -->
	                      		</div><!-- widget row-fluid -->
							</div><!-- .widget-body -->
                   	</div><!-- .widget -->
				</form>
			</div><!-- #content -->
		</div><!-- #nowrapper -->
		
		<%@include file="/novoLayout/common/include/libRodape.jsp" %>
		<script src="${ctx}/novoLayout/common/include/js/jquery.autocomplete.js"></script>
		<script src="js/RequisicaoViagem.js"></script>
	</body>
</html>