<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="css/manualFuncao.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">

			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">

				<% if(iframe == null) { %>
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
				<% } %>

			</div>

			<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">

				<!-- Inicio conteudo -->
				<div id="content">
					<div class="separator top"></div>
					<div class="row-fluid">
						<div class="innerLR">
							<div class="widget">
								<div class="widget-head">
									<h4 class="heading"><fmt:message key="ManualFuncao.Titulo" /></h4>
								</div>
								<div class="widget-body collapse in">
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="ManualFuncao.CadastroDeDescricaoDeCargosEFuncoes" /></a></li>
											<li><a href="#tab2-3" data-toggle="tab" ><fmt:message key="ManualFuncao.PesquisaDescricaoDeCargosEFuncoes" /></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name='form' action='${ctx}/pages/manualFuncao/manualFuncao'>
											 	<input type='hidden' name="idManualFuncao" id="idManualFuncao"/>
											 	<input type='hidden' name="idRequisicaoFuncao" id="idRequisicaoFuncao"/>
											 	<input type='hidden' name="idCargo" id="idCargo"/>
											 	<input type='hidden' name="idDescricao" id="idDescricao"/>
											 	<input type='hidden' name="idCertificacaoRA" id="idCertificacaoRA"/>
											 	<input type='hidden' name="idCertificacaoRF" id="idCertificacaoRF"/>
											 	<input type='hidden' name="idCursoRA" id="idCursoRA"/>
											 	<input type='hidden' name="idCursoRF" id="idCursoRF"/>
											 	<input type='hidden' name="idComportamento" id="idComportamento"/>
											 	<input type='hidden' name='colResponsabilidades_Serialize' id='colResponsabilidades_Serialize'/>
											 	<input type='hidden' name='colCertificacoes_Serialize' id='colCertificacoes_Serialize'/>
											 	<input type='hidden' name='colCursos_Serialize' id='colCursos_Serialize'/>
											 	<input type='hidden' name='colCertificacoesRF_Serialize' id='colCertificacoesRF_Serialize'/>
											 	<input type='hidden' name='colCursosRF_Serialize' id='colCursosRF_Serialize'/>
											 	<input type='hidden' name='colCompetencias_Serialize' id='colCompetencias_Serialize'/>
											 	<input type='hidden' name='colPerspectivaComportamental_Serialize' id='colPerspectivaComportamental_Serialize'/>

												<div class="innerLR">
													<div class='row-fluid'>
														<div class='span12'>
															<div class='row-fluid'>
																<div class='span8'>
																	<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.tituloFuncao" /></label>
																	<input id="tituloFuncao" name="tituloFuncao" type='text' placeholder="Click e selecione a função" maxlength="256" class="span12 Valid[Required] Description[ManualFuncao.tituloFuncao]" readonly="readonly"/>
																</div>
																<div class='span4'>
																	<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.Codigo" /></label>
																	<input id="codigo" type="text" name="codigo" placeholder="Digite o Código" maxlength="256" class="Valid[Required] Description[ManualFuncao.Codigo]" />
																</div>
															</div>
															<div class='row-fluid'>
																<div class='span8'>
																	<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.TituloCargo" /></label>
																	<input id="tituloCargo" name="tituloCargo" type='text' maxlength="256" class="span12 Valid[Required] Description[ManualFuncao.TituloCargo]" readonly="readonly"/>
																</div>
																<div class='span4'>
																	<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.CBO" /></label>
																	<input id="codCBO" type="text" name="codCBO"  placeholder="Digite o CBO" maxlength="256" class="Valid[Required] Description[ManualFuncao.CBO]" />
																</div>
															</div>
															<div class='row-fluid'>
																<div class='span12'>
																	<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.ResumoFuncao" /></label>
																	<textarea class="widthTextArea Valid[Required] Description[ManualFuncao.ResumoFuncao]" id="resumoFuncao" name="resumoFuncao" rows="4" cols="1" placeholder="Digite Resumo da Função" maxlength="500"></textarea>
																</div>
															</div>
														</div>
													</div>
												</div>

												<div class="widget">
													<div class="widget-body collapse in">
														<div class="tabsbar">
															<ul>
																<li class="active"><a href="#tab1-6" data-toggle="tab"><fmt:message key="ManualFuncao.PerspectivaComplexidade" /></a></li>
																<li><a href="#tab2-6" data-toggle="tab"><fmt:message key="ManualFuncao.PerspectivaTecnica" /></a></li>
																<li><a href="#tab3-6" data-toggle="tab"><fmt:message key="ManualFuncao.CompetenciaTecnica" /></a></li>
																<li><a href="#tab4-6" data-toggle="tab"><fmt:message key="ManualFuncao.PerspectivaComportamental" /></a></li>
																<li><a href="#tab5-6" data-toggle="tab"><fmt:message key="ManualFuncao.PesoDasPerspectiva" /></a></li>
																<li><a href="#tab6-6" data-toggle="tab"><fmt:message key="ManualFuncao.Historico" /></a></li>
															</ul>
														</div>
														<div class="tab-content">
														<!-- INICIO - Atribuições e Responsabilidades  -->
															<div class="tab-pane active" id="tab1-6">
																<div class="widget">
																	<div class="widget-head">
																		<h4 class="heading"><fmt:message key="ManualFuncao.AtribuicaoResponsabilidade" /></h4>
																	</div>
																	<div class="innerLR">
																		<div class='row-fluid'>
																			<div class='row-fluid'>
																				<div class='innerTB'>
																					<div class='row-fluid'>
																						<div class='span6'>
																							<div class='row-fluid'>
																							<div class='span6 divInline' >
																								<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.Descricao" /></label>

																								<button onclick="gravarManualFuncaoComplexidade()" type="button" class="strong btn btn-icon btn-primary botaoAdd">+</button>
																							</div>
																							</div>

																							<div class='row-fluid'>
																								<input id="descricaoPerspectivaComplexidade" name="descricaoPerspectivaComplexidade" type='text' placeholder="Digite a Descrição" maxlength="256" class="span12 " />
																							</div>
																						</div>


																						<div class='span2'>
																							<label style="margin-bottom: 11px" class="campoObrigatorio strong"><fmt:message key="ManualFuncao.Nivel" /></label>
																							<select class="span12" id="idNivel" name="idNivel"></select>
																						</div>
																						<div class='span2'>
																							<button style="margin-top: 31px" type="button" class="btn btn-icon btn-primary alinhaBotao" onclick="adicionarDescricaoAtribuicaoResponsabilidade()" >Adicionar</button>
																						</div>
																					</div>
																					<div class="row-fluid">
														                            	<div class="span9">
														                                    <table id="tblResponsabilidades" class="table table-condensed" style="overflow: auto">
														                                        <tr>
														                                        	<th width="40%"><fmt:message key="ManualFuncao.Descricao" /></th>
														                                        	<th width="10%"><fmt:message key="ManualFuncao.Nivel" /></th>
														                                            <th width="5%" ><fmt:message key="rh.acoes" /></th>
														                                        </tr>
														                                    </table>
														                               </div>
														                            </div>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<!-- FIM - Atribuições e Responsabilidades  -->

															<!-- INICIO - Perspectiva Tecnica  -->
															<div class="tab-pane" id="tab2-6">
																<div class="tab-pane active" id="tab1-4">
																	<div class="widget">
																		<div class="widget-head">
																			<h4 class="heading"><fmt:message key="ManualFuncao.RequisitosAcesso" /></h4>
																		</div>
																		<div class="innerTB">
																			<div class="innerLR">
																				<div class='row-fluid'>
																					<div class='row-fluid'>
																						<div class="span12">
																							<div class='span4'>
																								<div class="row-fluid">
																									<div class="span12">
																										<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.FormacaoAcademica" /></label>
																										<select class="span8" id="idFormacaoRA" name="idFormacaoRA"></select>
																									</div>
																								</div>
																								<div class="row-fluid">
																									<div class="span12">
																										<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.Idioma" /></label>
																										<select class="span8" id="idIdiomaRA" name="idIdiomaRA" ></select>
																									</div>
																								</div>
																								<div class="row-fluid">
																									<div class="span12">
																										<label class="strong"><fmt:message key='rh.escrita'/></label>
																										<select class="span8" name='idNivelEscritaRA' id="idNivelEscritaRA">
																									        <option  value="0"><fmt:message key='citcorpore.comum.selecione'/></option>
																									        <option  value="1"><fmt:message key='rh.naoTem'/></option>
																											<option  value="2"><fmt:message key='citcorpore.comum.complexidadeIntermediaria'/></option>
																											<option  value="3"><fmt:message key='rh.boa'/></option>
																											<option  value="4"><fmt:message key='rh.avancada'/></option>
																		  								</select>
																						  			</div>
																					  			</div>
																								<div class="row-fluid">
																									<div class="span12">
																										<label class="strong"><fmt:message key='rh.leitura'/></label>
																										<select class="span8"name='idNivelLeituraRA' id="idNivelLeituraRA">
																									        <option  value=""><fmt:message key='citcorpore.comum.selecione'/></option>
																									        <option  value="1"><fmt:message key='rh.naoTem'/></option>
																											<option  value="2"><fmt:message key='citcorpore.comum.complexidadeIntermediaria'/></option>
																											<option  value="3"><fmt:message key='rh.boa'/></option>
																											<option  value="4"><fmt:message key='rh.avancada'/></option>
																		  								</select>
																						  			</div>
																					  			</div>
																								<div class="row-fluid">
																									<div class="span12">
																										<label class="strong"><fmt:message key='rh.conversacao'/></label>
																										 <select class="span8" name='idNivelConversaRA' id="idNivelConversaRA">
																									        <option  value=""><fmt:message key='citcorpore.comum.selecione'/></option>
																									        <option  value="1"><fmt:message key='rh.naoTem'/></option>
																											<option  value="2"><fmt:message key='citcorpore.comum.complexidadeIntermediaria'/></option>
																											<option  value="3"><fmt:message key='rh.boa'/></option>
																											<option  value="4"><fmt:message key='rh.avancada'/></option>
																		  								</select>
																						  			</div>
																					  			</div>
																					  			<div class="row-fluid">
																						  			<div class="span12">
																										<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.ExperienciaAnterior" /></label>
																										<input class="span8" type="text" id="expAnteriorRA" name="expAnteriorRA" placeholder="Digite a Experiência Anterior" maxlength="256"/>
																									</div>
																								</div>
																							</div>
																							<div class='span4'>
																								<div class="row-fluid">
																									<div class='span8'>
																										<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.Certificacoes" /></label>
																										<input class="span12" type="text" id="CertificacaoRA" name="CertificacaoRA" placeholder="Digite a Certificação" maxlength="256"/>
																									</div>
																									<div class='span1'>
																										<button onclick="adicionaCertificacao()" class="btn btn-icon btn-primary alinhaBotao" type="button" id="addCertificacaoRA" name="addCertificacaoRA">Adicionar</button>
																									</div>
																								</div>
																								<div class="row-fluid">
																									<div class='span8'>
																										  <table id="tblCertificacoesRA" class="table table-condensed" style="overflow: auto">
																	                                        <tr>
																	                                        	<th width="30%"><fmt:message key="ManualFuncao.Certificacao" /></th>
																	                                        	<th width="5%" ><fmt:message key="rh.acoes" /></th>
																	                                        </tr>
																	                                    </table>
																									</div>
																								</div>
																							</div>
																							<div class='span4'>
																								<div class="row-fluid">
																									<div class='span8'>
																										<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.Cursos" /></label>
																										<input class="span12" type="text" id="cursoRA" name="cursoRA" placeholder="Digite o Curso" maxlength="256"/>
																									</div>
																									<div class='span1'>
																										<button onclick="adicionarCurso()" class="btn btn-icon btn-primary alinhaBotao" type="button" id="addCursosRA" name="addCursosRA">Adicionar</button>
																									</div>
																								</div>
																								<div class="row-fluid">
																									<div class='span8'>
																										  <table id="tblCursosRA" class="table table-condensed" style="overflow: auto">
																	                                        <tr>
																	                                        	<th width="30%"><fmt:message key="ManualFuncao.Cursos" /></th>
																	                                        	<th width="5%" ><fmt:message key="rh.acoes" /></th>
																	                                        </tr>
																	                                    </table>
																									</div>
																								</div>
																							</div>
																						</div>
																					</div>
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="widget">
																		<div class="widget-head">
																			<h4 class="heading"><fmt:message key="ManualFuncao.RequistosDaFuncao" /></h4>
																		</div>
																		<div class="innerTB">
																			<div class="innerLR">
																				<div class='row-fluid'>
																				<div class='row-fluid'>
																						<div class="span12">
																							<div class='span4'>
																								<div class="row-fluid">
																									<div class="span12">
																										<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.FormacaoAcademica" /></label>
																										<select class="span8" id="idFormacaoRF" name="idFormacaoRF"></select>
																									</div>
																								</div>
																								<div class="row-fluid">
																									<div class="span12">
																										<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.Idioma" /></label>
																										<select class="span8" id="idIdiomaRF" name="idIdiomaRF" ></select>
																									</div>
																								</div>
																								<div class="row-fluid">
																									<div class="span12">
																										<label class="strong"><fmt:message key='rh.escrita'/></label>
																										<select class="span8" name='idNivelEscritaRF' id="idNivelEscritaRF">
																									        <option  value=""><fmt:message key='citcorpore.comum.selecione'/></option>
																									        <option  value="1"><fmt:message key='rh.naoTem'/></option>
																											<option  value="2"><fmt:message key='citcorpore.comum.complexidadeIntermediaria'/></option>
																											<option  value="3"><fmt:message key='rh.boa'/></option>
																											<option  value="4"><fmt:message key='rh.avancada'/></option>
																		  								</select>
																						  			</div>
																					  			</div>
																								<div class="row-fluid">
																									<div class="span12">
																										<label class="strong"><fmt:message key='rh.leitura'/></label>
																										<select class="span8"name='idNivelLeituraRF' id="idNivelLeituraRF">
																									        <option  value=""><fmt:message key='citcorpore.comum.selecione'/></option>
																									        <option  value="1"><fmt:message key='rh.naoTem'/></option>
																											<option  value="2"><fmt:message key='citcorpore.comum.complexidadeIntermediaria'/></option>
																											<option  value="3"><fmt:message key='rh.boa'/></option>
																											<option  value="4"><fmt:message key='rh.avancada'/></option>
																		  								</select>
																						  			</div>
																					  			</div>
																								<div class="row-fluid">
																									<div class="span12">
																										<label class="strong"><fmt:message key='rh.conversacao'/></label>
																										 <select class="span8" name='idNivelConversaRF' id="idNivelConversaRF">
																									        <option  value=""><fmt:message key='citcorpore.comum.selecione'/></option>
																									        <option  value="1"><fmt:message key='rh.naoTem'/></option>
																											<option  value="2"><fmt:message key='citcorpore.comum.complexidadeIntermediaria'/></option>
																											<option  value="3"><fmt:message key='rh.boa'/></option>
																											<option  value="4"><fmt:message key='rh.avancada'/></option>
																		  								</select>
																						  			</div>
																					  			</div>
																					  			<div class="row-fluid">
																						  			<div class="span12">
																										<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.ExperienciaAnterior" /></label>
																										<input class="span8" type="text" id="expAnteriorRF" name="expAnteriorRF" placeholder="Digite a Experiência Anterior" maxlength="256"/>
																									</div>
																								</div>
																							</div>
																							<div class='span4'>
																								<div class="row-fluid">
																									<div class='span8'>
																										<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.Certificacoes" /></label>
																										<input class="span12" type="text" id="CertificacaoRF" name="CertificacaoRF" placeholder="Digite a Certificação" maxlength="256"/>
																									</div>
																									<div class='span1'>
																										<button onclick="adicionaCertificacaoRF()" class="btn btn-icon btn-primary alinhaBotao" type="button" id="addCertificacaoRF" name="addCertificacaoRF">Adicionar</button>
																									</div>
																								</div>
																								<div class="row-fluid">
																									<div class='span8'>
																										  <table id="tblCertificacoesRF" class="table table-condensed" style="overflow: auto">
																	                                        <tr>
																	                                        	<th width="30%"><fmt:message key="ManualFuncao.Certificacao" /></th>
																	                                        	<th width="5%" ><fmt:message key="rh.acoes" /></th>
																	                                        </tr>
																	                                    </table>
																									</div>
																								</div>
																							</div>
																							<div class='span4'>
																								<div class="row-fluid">
																									<div class='span8'>
																										<label class="campoObrigatorio strong">Cursos</label>
																										<input class="span12" type="text" id="cursoRF" name="cursoRF" placeholder="Digite o Curso" maxlength="256"/>
																									</div>
																									<div class='span1'>
																										<button onclick="adicionaCursoRF()" class="btn btn-icon btn-primary alinhaBotao" type="button" id="addCursosRF" name="addCursosRF">Adicionar</button>
																									</div>
																								</div>
																								<div class="row-fluid">
																									<div class='span8'>
																										  <table id="tblCursoRF" class="table table-condensed" style="overflow: auto">
																	                                        <tr>
																	                                        	<th width="30%"><fmt:message key="ManualFuncao.Cursos" /></th>
																	                                        	<th width="5%" ><fmt:message key="rh.acoes" /></th>
																	                                        </tr>
																	                                    </table>
																									</div>
																								</div>
																							</div>
																						</div>
																					</div>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<!-- FIM - Perspectiva Tecnica  -->

															<!-- INICIO - Competências Técnicas  -->
															<div class="tab-pane" id="tab3-6">
																<div class="widget">
																	<div class="widget-head">
																		<h4 class="heading"><fmt:message key="ManualFuncao.CompetenciaTecnicas" /></h4>
																	</div>
																	<div class="innerTB">
																		<div class="innerLR">
																			<div class='row-fluid'>
																				<div class='span8'>
																					<div class='row-fluid'>
																						<div class='span12 divInline'>
																							<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.CompetenciaTecnicas" /></label>
																							<button onclick="gravarCompetenciaTecniva()" type="button" class="strong btn btn-icon btn-primary botaoAdd">+</button>
																						</div>
																					</div>

																					<input type='hidden' name="idCompetenciaTecnica" id="idCompetenciaTecnica"/>
																					<input class="span12" type="text" id="competencia" name="competencia" placeholder="Digite a Competência Tecnica" maxlength="256"/>
																				</div>
																			</div>

																			<div class='row-fluid'>
																				<div class='span12'>
																					<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.NivelCompetenciaAcesso" /></label>
																					<select class="span8" id="idNivelCompetenciaAcesso" name="idNivelCompetenciaAcesso" ></select>
																				</div>
																			</div>
																			<div class='row-fluid'>
																				<div class='span12'>
																					<label class="campoObrigatorio strong"><fmt:message key="ManualFuncao.NivelCompetenciaFuncao" /></label>
																					<select class="span8" id="idNivelCompetenciaFuncao" name="idNivelCompetenciaFuncao" ></select>
																				</div>
																			</div>
																			<div class='row-fluid'>
																				<div class='span12'>
																					<button onclick="adicionaCompetencia()" class="btn btn-icon btn-primary alinhaBotao" type="button" id="addCompetencias" name="addCompetencias">Adicionar</button>
																				</div>
																			</div>
																			<div class='row-fluid'>
																				<div class='span8 alinhaBotao'>
																					  <table id="tblCompetencias" class="table table-condensed" style="overflow: auto">
												                                        <tr>
												                                        	<th width="30%"><fmt:message key="ManualFuncao.CompetenciaTecnicas" /></th>
												                                        	<th width="30%"><fmt:message key="ManualFuncao.NivelCompetenciaAcesso" /></th>
												                                        	<th width="30%"><fmt:message key="ManualFuncao.NivelCompetenciaFuncao" /></th>
												                                        	<th width="5%" ><fmt:message key="rh.acoes" /></th>
												                                        </tr>
												                                    </table>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<!-- FIM - Competências Técnicas  -->

															<!-- INICIO - Perspectiva Comportamental  -->
															<div class="tab-pane" id="tab4-6">
																<div class="widget">
																	<div class="widget-head">
																		<h4 class="heading"><fmt:message key="ManualFuncao.PerspectivaComportamental" /></h4>
																	</div>
																	<div class="innerTB">
																		<div class="innerLR">
																			<div class='row-fluid'>
																				<div class="span12">
																					<div class="span4">
																						<label class="strong campoObrigatorio"><fmt:message key="ManualFuncao.CompetenciaComportamental" /></label>
																						 <select style="margin-top: 8px;" class="span12" name='cmbCompetenciaComportamental' id="cmbCompetenciaComportamental">
																					        <option  value=""><fmt:message key='citcorpore.comum.selecione'/></option>
																					        <option  value="1"><fmt:message key="ManualFuncao.Comprometimento" /></option>
																							<option  value="2"><fmt:message key="ManualFuncao.Organizacional" /></option>
														  								</select>
																		  			</div>
																					<div class="span4">
																						<div class='row-fluid'>
																							<div class="span12 divInline">
																								<label class="strong campoObrigatorio"><fmt:message key="ManualFuncao.Comportamento" /></label>
																								<button onclick="gravarComportamento()" type="button" class="strong btn btn-icon btn-primary botaoAdd">+</button>
																							</div>
																						</div>
																						<input class="span12" type="text" id="comportamento" name="comportamento" placeholder="Digite o Comportamento" maxlength="256"/>
																		  			</div>

																					<div class="span1">
																						<button style="margin-top: 31px;" onclick="adicionaPerspectivaComportamental()"class="btn btn-icon btn-primary alinhaBotao" type="button" id="addCompetencia" name="addCompetencia"><fmt:message key="ManualFuncao.Adicionar" /></button>
																		  			</div>
																	  			</div>
																	  			<div class='row-fluid'>
																					<div class='span8 alinhaBotao'>
																						  <table id="tblPerspectivaComportamental" class="table table-condensed" style="overflow: auto">
													                                        <tr>
													                                        	<th width="30%" ><fmt:message key="ManualFuncao.CompetenciaComportamental" /></th>
													                                        	<th width="30%"><fmt:message key="ManualFuncao.Comportamento" /></th>
													                                        	<th width="5%"><fmt:message key="rh.acoes" /></th>
													                                        </tr>
													                                    </table>
																					</div>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<!-- FIM - Perspectiva Comportamental  -->

															<!-- INICIO - Peso das Perspectiva  -->
															<div class="tab-pane" id="tab5-6">
																<div class="widget">
																	<div class="widget-head">
																		<h4 class="heading"><fmt:message key="ManualFuncao.PesoPerspectiva" /></h4>
																	</div>
																	<div class="innerTB">
																		<div class="innerLR">
																			<div class='row-fluid'>
																				<div class='row-fluid'>
																					<div class='span10'>
																						<div class='span1'>
																							<label class="strong"><fmt:message key="ManualFuncao.Perspectiva" /></label>
																						</div>
																						<div class='span1'>
																							<label class="strong"><fmt:message key="ManualFuncao.Peso" /></label>
																						</div>
																					</div>
																				</div>
																				<div class='row-fluid'>
																					<div class='span10'>
																						<div class='span1'>
																							<label><fmt:message key="ManualFuncao.Complexidade" /></label>
																						</div>
																						<div class='span1'>
																							<input class="span12" type="text" id="pesoComplexidade" name="pesoComplexidade" maxlength="256"/>
																						</div>
																					</div>
																				</div>
																				<div class='row-fluid'>
																					<div class='span10'>
																						<div class='span1'>
																							<label><fmt:message key="ManualFuncao.Tecnica" /></label>
																						</div>
																						<div class='span1'>
																							<input class="span12" type="text" id="pesoTecnica" name="pesoTecnica" maxlength="256"/>
																						</div>
																					</div>
																				</div>
																				<div class='row-fluid'>
																					<div class='span10'>
																						<div class='span1'>
																							<label><fmt:message key="ManualFuncao.Comportamental" /></label>
																						</div>
																						<div class='span1'>
																							<input class="span12" type="text" id="pesoComportamental" name="pesoComportamental" maxlength="256"/>
																						</div>
																					</div>
																				</div>
																				<div class='row-fluid'>
																					<div class='span10'>
																						<div class='span1'>
																							<label><fmt:message key="ManualFuncao.Resultados" /></label>
																						</div>
																						<div class='span1'>
																							<input class="span12" type="text" id="pesoResultados" name="pesoResultados" maxlength="256"/>
																						</div>
																					</div>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<!-- FIM - Peso das Perspectiva  -->

															<!-- INICIO - Histórico  -->
															<div class="tab-pane" id="tab6-6">
																<div class="widget">
																	<div class="widget-head">
																		<h4 class="heading"><fmt:message key="ManualFuncao.Historico" /></h4>
																	</div>
																	<div class="innerTB">
																		<div class="innerLR">
																			<div class='row-fluid'>
																				<div class='span12'>
																						<div class='row-fluid'>
																							<div class='span12 alinhaBotao'>
																								  <table id="tblHistoricoVersoes" class="table table-condensed" style="overflow: auto">
															                                        <tr>

																                                        	<th width="40%"><fmt:message key="ManualFuncao.TituloCargo" /></th>
																                                        	<th width="40%"><fmt:message key="ManualFuncao.tituloFuncao" /></th>
																                                            <th width="5%" ><fmt:message key="CBO" /></th>
																                                            <th width="5%" ><fmt:message key="ManualFuncao.Codigo" /></th>
																                                            <th width="5%" ><fmt:message key="citcorpore.comum.versao" /></th>
																                                            <th width="5%" ><fmt:message key="Visualizar" /></th>
															                                        </tr>
															                                    </table>
																							</div>
																						</div>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<!-- FIM - Histórico  -->
														</div>
													</div>
												</div>

												<div class='row-fluid'>
													<div class='span12'>
														<button type='button' name='btnGravar' class="lFloat btn btn-icon btn-primary" onclick='gravar();'>
															<i></i><fmt:message key="citcorpore.comum.gravar" />
														</button>
														<button type="button" name='btnLimpar' class="lFloat btn btn-icon btn-primary" onclick='limpar();'>
															<i></i><fmt:message key="citcorpore.comum.limpar" />
														</button>
													</div>
											   </div>
											</form>
										</div>
										<div class="tab-pane" id="tab2-3">
											<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
												<form name='formPesquisa'>
													<cit:findField formName='formPesquisa' lockupName='LOOKUP_MANUALFUNCAO' id='LOOKUP_MANUALFUNCAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="modal hide fade in" id="modal_HistoricoVersoes" tabindex="-1" data-backdrop="static" data-keyboard="false">
					<!-- Modal heading -->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
							<h3><fmt:message key="citcorpore.comum.ocorrencia" /></h3>
						</div>
						<!-- // Modal heading END -->
						<!-- Modal body -->
						<div class="modal-body">
							<div id="conteudoHistoricoVersoes"></div>
						</div>
						<!-- // Modal body END -->
						<!-- Modal footer -->
						<div class="modal-footer">
							<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
						</div>
						<!-- // Modal footer END -->
					</div>

					<div id="POPUP_REQUISICAOFUNCAO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
						<div class="box grid_16 tabs"  style="width: 570px !important;">
							<div class="toggle_container">
								<div id="tabs-2" class="block">
									<div class="section">
										<form name='formPesquisaReq' style="width: 540px !important;">
											<cit:findField formName='formPesquisaReq'
												lockupName='LOOKUP_REQUISICAOFUNCAO' id='LOOKUP_REQUISICAOFUNCAO' top='0'
												left='0' len='1050' heigth='400' javascriptCode='true'
												htmlCode='true' />
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
				<!--  Fim conteudo-->
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>
		<script type="text/javascript" src="${ctx}/cit/objects/AtribuicaoResponsabilidadeDTO.js"></script>
		<script type="text/javascript" src="${ctx}/cit/objects/CertificacaoDTO.js"></script>
		<script type="text/javascript" src="${ctx}/cit/objects/CursoDTO.js"></script>
		<script type="text/javascript" src="${ctx}/cit/objects/ManualCompetenciaTecnicaDTO.js"></script>
		<script type="text/javascript" src="${ctx}/cit/objects/AtitudeIndividualDTO.js"></script>
		<script type="text/javascript" src="js/manualFuncao.js"></script>
		<script>
		$(window).load(function(){
			$('#conteudoHistoricoVersoes').html('<iframe id="iframeHistoricoVersoes" src="about:blank" width="99%" height="800" style="overflow: auto" class="iframeSemBorda"></iframe>');
		});
		</script>
	</body>
</html>