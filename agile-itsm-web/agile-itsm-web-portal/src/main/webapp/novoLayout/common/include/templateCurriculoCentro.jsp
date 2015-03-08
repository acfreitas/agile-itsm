<form name='formPesquisaCurriculo' id='formPesquisaCurriculo' action="${ctx}/pages/templateCurriculo/templateCurriculo">
	<input type="hidden" id="numeroPagina" name="numeroPagina" />
	<input type="hidden" id="idCandidato" name="idCandidato" />
	<input type="hidden" id="idPais" name="idPais" />
	<input type="hidden" id="hiddenIdUf" name="hiddenIdUf" />
	<input type="hidden" id="idCidade" name="idCidade" />
	<input type="hidden" id="idFormacaoAcademica" name="idFormacaoAcademica" />
	<input type='hidden' name='colTelefones_Serialize' id='colTelefones_Serialize'/>
	<input type='hidden' name='colTreinamento_Serialize' id='colTreinamento_Serialize'/>
	<input type='hidden' name='colEnderecos_Serialize' id='colEnderecos_Serialize'/>
	<input type='hidden' name='colEmail_Serialize' id='colEmail_Serialize'/>
	<input type='hidden' name='colFormacao_Serialize' id='colFormacao_Serialize'/>
	<input type='hidden' name='colExperienciaProfissional_Serialize' id='colExperienciaProfissional_Serialize'/>
	<input type='hidden' name='colCertificacao_Serialize' id='colCertificacao_Serialize'/>
	<input type='hidden' name='colIdioma_Serialize' id='colIdioma_Serialize'/>
	<input type='hidden' name='colCompetencias_Serialize' id='colCompetencias_Serialize'/>
	<input type='hidden' name='idCurriculo' id='idCurriculo'/>
	<input type='hidden' name='idSolicitacaoServicoCurriculo' id='idSolicitacaoServicoCurriculo'/>
	<input type="hidden" id="idUfNatal" name="idUfNatal" />
	<input type="hidden" id="cidadeNatalValor" name="cidadeNatalValor" />
	<input type='hidden' name='auxEmailPrincipal' id='auxEmailPrincipal'/>
	<input type='hidden' name='auxEnderecoPrincipal' id='auxEnderecoPrincipal'/>
	<input type='hidden' name='descricaoTreinamento' id='descricaoTreinamento'/>
	<input type='hidden' name='idTreinamento' id='idTreinamento'/>
	<input type='hidden' name='idCurso' id='idCurso'/>
	<input type='hidden' name='indexExp' id='indexExp'/>
	<input type='hidden' name='indexDesFunc' id='indexDesFunc'/>
	<input type='hidden' name='rowIndexEmail' id='rowIndexEmail'/>
	<input type='hidden' name='rowIndexTelefone' id='rowIndexTelefone'/>
	<input type='hidden' name='rowIndexEndereco' id='rowIndexEndereco'/>
	<input type='hidden' name='rowIndexFormacao' id='rowIndexFormacao'/>
	<input type='hidden' name='rowIndexIdioma' id='rowIndexIdioma'/>
	<input type='hidden' name='rowIndexCompetencia' id='rowIndexCompetencia'/>
	<input type='hidden' name='rowIndexCertificacao' id='rowIndexCertificacao'/>

	<div class="widget widget-tabs widget-tabs-double">
		<div class="widget-head" id="wizardCurriculo">
			<ul id="ulWizard">
				<li class="active" id="li1" onclick="paginacao1();"><a class="glyphicons user" href="#tab1"><i></i><span class="strong"><fmt:message key='rh.etapaUm'/></span><span><fmt:message key='colaborador.dadosPessoais'/></span></a></li>
				<li id="li2" onclick="paginacao2()"><a class="glyphicons pen" href="#tab2"><i></i><span class="strong"><fmt:message key='rh.etapaDois'/></span><span><fmt:message key='rh.formacaoIdiomaTreinamento'/></span></a></li>
				<li id="li3" onclick="paginacao3()"><a class="glyphicons nameplate" href="#tab3"><i></i><span class="strong"><fmt:message key='rh.etapaTres'/></span><span><fmt:message key='rh.experiencia'/></span></a></li>
				<li id="li4" onclick="paginacao4()"><a class="glyphicons certificate" href="#tab4"><i></i><span class="strong"><fmt:message key='rh.etapaQuatro'/></span><span><fmt:message key='rh.competenciasCertificacoes'/></span></a></li>
				<li id="li5" onclick="paginacao5()"><a class="glyphicons paperclip" href="#tab5"><i></i><span class="strong"><fmt:message key='rh.etapaCinco'/></span><span><fmt:message key='agenda.anexos'/></span></a></li>
			</ul><!-- #ulWizard -->
		</div><!-- #wizardCurriculo -->

		<div class="widget-body">
			<div class="tab-content clearfix">

				<!-- Etapa 1 -->
				<div id="tab1" class="tab-pane active">
					<div id="dadospessoais-container" class="box-container">
						<h2 class="heading">
							<fmt:message key='rh.dadosPessoais'/>
						</h2>

						<div class="row-fluid">
							<div id="foto">
								<div class="control-group">
									<label class="strong"><fmt:message key='rh.foto'/></label>

									<div id='divImgFoto'></div>

									<div style="text-align: center;">
										<a id="adicionar_foto"><i><fmt:message key='rh.adicionarFoto'/></i></a>
										<a id="remover_foto" style="display: none;"  href="#" onclick="delImgFoto()"><i><fmt:message key='rh.removerFoto'/></i></a>
									</div>
								</div><!-- .control-group -->
							</div><!-- #foto -->

							<div class="control-group" id="dadosPessoais">
								<div class="row-fluid">
									<div class="span12">
										<label class="strong campoObrigatorio " ><fmt:message key='candidato.nomeCompleto'/></label>
										<div class="controls">
											<input type="text" class="span12" value="" id="nome" name="nome" maxlength="100" onclick="<%= (request.getSession().getAttribute("CANDIDATO") == null  "$('#modal_candidato').modal('show');" : "") %>" />
										</div><!-- .controls -->
									</div><!-- .span12 -->
								</div><!-- .row-fluid -->

								<div class="row-fluid">
									<div class="span4">
										<label class="strong campoObrigatorio"><fmt:message key='citcorpore.comum.sexo'/></label>
										<div class="controls">
											<select class="span12 " name="sexo" id="sexo"></select>
										</div><!-- .controls -->
									</div><!-- .span4 -->

									<div class="span4">
										<label class="strong campoObrigatorio"><fmt:message key='rh.estadoCivil'/></label>
										<div class="controls">
											<select class="span12 " name='estadoCivil' id="estadoCivil">
											    <option value="0" ><fmt:message key='citcorpore.comum.selecione'/></option>
												<option value="1" ><fmt:message key='rh.solteiro'/></option>
												<option value="2" ><fmt:message key='rh.casado'/></option>
							                    <option value="3" ><fmt:message key='rh.Companheiro'/></option>
							                    <option value="4" ><fmt:message key='rh.uniaoEstavel'/></option>
												<option value="5" ><fmt:message key='rh.separado'/></option>
							                    <option value="6" ><fmt:message key='rh.divorciado'/></option>
												<option value="7" ><fmt:message key='rh.viuvo'/></option>
											</select>
										</div><!-- .controls -->
									</div><!-- .span4 -->

									<div class="span2">
										<label class="strong campoObrigatorio"><fmt:message key="rh.possuiFilhos" /></label>
										<div class="controls">
											<label class="radio inline">
												<input type="radio" id="filhosS" name="filhos" value="S" onclick="manipulaInputQtdeFilhos();" /> <fmt:message key="citcorpore.comum.sim" />
											</label>
											<label class="radio inline">
												<input type="radio" id="filhosN" name="filhos" value="N" onclick="manipulaInputQtdeFilhos();" /> <fmt:message key="citcorpore.comum.nao" />
											</label>
										</div><!-- .controls -->
									</div><!-- .span2 -->
									<div class="span2">
										<label class="strong campoObrigatorio"><fmt:message key='rh.quantosFilhos'/></label>
										<div class="controls">
											<input type="text" class="span12 Format[Numero]" name="qtdeFilhos" id="qtdeFilhos" value="" maxlength="2" />
										</div><!-- .controls -->
									</div><!-- .span2 -->
								</div><!-- .row-fluid -->

								<div class="row-fluid">
									<div class="span4">
										<label class="strong campoObrigatorio"><fmt:message key='rh.nacionalidade'/></label>
										<div class="controls">
											<input type="text" class="span12" name="nacionalidade" id="nacionalidade" maxlength="100" />
										</div><!-- .controls -->
									</div><!-- .span4 -->

									<div class="span4">
										<label class="strong campoObrigatorio"><fmt:message key='lookup.cpf'/></label>
										<div class="controls">
											<input type="text" class="span12" value="${cpf}" id="cpf" name="cpf" maxlength="14" />
										</div><!-- .controls -->
									</div><!-- .span4 -->

									<div class="span4">
										<label class="strong campoObrigatorio"><fmt:message key='citcorpore.comum.dataNascimento'/></label>
										<div class="controls">
											<input type="text" class="span12 Format[Date] Valid[Date] datepicker"  id="dataNascimento" name="dataNascimento" maxlength="10" />
										</div><!-- .controls -->
									</div><!-- .span4 -->
								</div><!-- .row-fluid -->
							</div><!-- #dadosPessoais -->
						</div><!-- .row-fluid -->

						<div class="row-fluid">
							<div class="span6">
								<h3 class="heading"><fmt:message key="curriculo.naturalidade" /></h3>
								<div class="row-fluid">
									<div class="span4">
										<label class="strong campoObrigatorio"><fmt:message key='rh.UF'/></label>
										<div class="controls">
											<select class="span12 " id="idEstadoNatal" name="idEstadoNatal"></select>
										</div><!-- .controls -->
									</div><!-- .span6 -->

									<div class="span8">
										<label class="strong campoObrigatorio"><fmt:message key='rh.cidadeNatal'/></label>
										<div class="controls">
											<select  class="span12" value="" id="idCidadeNatal" name="idCidadeNatal"></select>
										</div><!-- .controls -->
									</div><!-- .span6 -->
								</div><!-- .row-fluid -->
							</div><!-- .span6 -->

							<div class="span6">
								<h3 class="heading"><fmt:message key="rh.necessidadesEspeciaisNPE" /></h3>
								<div class="row-fluid">
									<div class="span4">
										<label class="strong campoObrigatorio"><fmt:message key="rh.possui" /></label>
										<div class="controls">
											<label class="radio inline">
												<input type='radio' class="Valid[Required]" id="portadorNecessidadeEspecialS" name="portadorNecessidadeEspecial" onclick="manipulaDivDeficiencia()" value="S" /> <fmt:message key="citcorpore.comum.sim" />
											</label>
											<label class="radio inline">
												<input type='radio' class="Valid[Required]" id="portadorNecessidadeEspecialN" name="portadorNecessidadeEspecial" onclick="manipulaDivDeficiencia()" value="N" /> <fmt:message key="citcorpore.comum.nao" />
											</label>
										</div><!-- .controls -->
									</div><!-- .span6 -->

									<div class="span8" id="divQualDeficiencia">
										<label class="strong campoObrigatorio"><fmt:message key="entrevista.qual" /></label>
										<div class="controls">
											 <select class="span12" name="idItemListaTipoDeficiencia">
					                             <option value=""><fmt:message key='citcorpore.comum.selecione'/> </option>
					                             <option value="1"><fmt:message key='rh.auditiva'/></option>
					                             <option value="2"><fmt:message key='rh.fisica'/></option>
					                             <option value="3"><fmt:message key='rh.menta'/></option>
					                             <option value="4"><fmt:message key='rh.multipla'/></option>
					                             <option value="5"><fmt:message key='rh.visual'/></option>
				                            </select>
										</div><!-- .controls -->
									</div><!-- .span6 -->
								</div><!-- .row-fluid -->
							</div><!-- .span6 -->
						</div><!-- .row-fluid -->

						<div class="row-fluid" hidden="true">
							<div class="span6">
								<label class="strong campoObrigatorio"><fmt:message key='entrevista.pretensaoSalarial'/></label>
								<div class="controls">
									<input type="text" class="span12"  id="pretensaoSalarial" name="pretensaoSalarial" maxlength="10" onKeyPress="return(MascaraMoeda(this,'.',',',event))"/>
								</div><!-- .controls -->
							</div><!-- .span4 -->
						</div><!-- .row-fluid -->
					</div><!-- #dadospessoais-container -->

					<div id="email-container" class="box-container">
						<h2 class="heading"><fmt:message key="citcorpore.comum.emails" /></h2>
						<div class="row-fluid">
							<div class="row-fluid">
								<div class="span8">
									<span onclick="addEmail()" class="btn-add-email btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='rh.adicionarEmail'/></span>

									<div class="input-email-container">
										<input type="text" class="span12" value="" id="email#descricaoEmail" name="email#descricaoEmail" maxlength="80" data-original-title=<fmt:message key='rh.digiteEmailContato'/> data-placement="top" data-toggle="tooltip">
									</div><!-- .input-append -->
								</div><!-- .span8 -->

								<div id="divPrincipal" class="tab-pane active span4">
									<label class="checkbox">
										<input type="checkbox" name="email#principal" id="email#principal" /> <fmt:message key='rh.emailPrincipal'/>
									</label>
								</div><!-- #divPrincipal -->
							</div><!-- .row-fluid -->
						</div><!-- .row-fluid -->

						<div class="row-fluid">
							<div class="widget">
								<div class="widget-head">
									<h3 class="heading"><fmt:message key='citcorpore.comum.emails'/></h3>
								</div><!-- .widget-head -->

								<div class="widget-body">
									<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
										<div class="row-fluid"></div>
										<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblEmail" aria-describedby="DataTables_Table_0_info">
											<thead>
												<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 85%;" aria-label="Platform(s): activate to sort column ascending"><fmt:message key='citcorpore.comum.email'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 10%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='rh.principal'/></th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
											 			style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
											</thead>

											<tbody role="alert" aria-live="polite" aria-relevant="all"></tbody>
										</table>
									</div><!-- .grid -->
								</div><!-- .widget-body -->
							</div><!-- .widget -->
						</div><!-- .row-fluid -->
					</div><!-- #email-container -->

					<div id="telefone-container" class="box-container">
						<h2 class="heading"><fmt:message key="rh.telefones" /></h2>
						<div class="row-fluid">
							<div class="span8">
								<span onclick="addTelefone()" class="btn btn-add-telefone btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='rh.adicionarTelefone'/></span>

								<div class="inputs-telefones-container">
									<div class="span2">
										<label class="strong campoObrigatorio"><fmt:message key='rh.DDD'/></label>
										<div class="controls">
											<input type="text" class="span12" value="" id="telefone#ddd" name="telefone#ddd" onkeypress="return somenteNumero(event);" maxlength="3" />
										</div><!-- .controls -->
									</div><!-- .span2 -->

									<div class="span6">
										<label class="strong campoObrigatorio"><fmt:message key='citcorpore.comum.numero'/></label>
										<div class="controls">
											<input type="text" class="span12" value="" id="telefone" name="telefone" maxlength="10" />
										</div><!-- .controls -->
									</div><!-- .span6 -->

									<div class="span4">
										<label class="strong campoObrigatorio"><fmt:message key='rh.tipoTelefone'/></label>
										<div class="controls">
											<select class="span12 " id="telefone#idTipoTelefone" name="telefone#idTipoTelefone">
												<option value=""><fmt:message key='citcorpore.comum.selecione'/></option>
												<option  value="1"><fmt:message key='rh.residencial'/></option>
												<option  value="3"><fmt:message key='rh.celular'/></option>
											</select>
										</div><!-- .controls -->
									</div><!-- .span4 -->
								</div><!-- .inputs-telefones-container -->
							</div><!-- .span8 -->
						</div><!-- .row-fluid -->

						<div class="row-fluid">
							<div class="widget">
								<div class="widget-head">
									<h3 class="heading"><fmt:message key='rh.telefones'/></h3>
								</div><!-- .widget-head -->

								<div class="widget-body">
									<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
										<div class="row-fluid"></div>
										<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblTelefones" aria-describedby="DataTables_Table_0_info">
											<thead>
												<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 10%;" aria-label="Platform(s): activate to sort column ascending"><fmt:message key='rh.DDD'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 63%;" aria-label="Eng. vers.: activate to sort column ascending"><fmt:message key='citcorpore.comum.numero'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 22%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='citcorpore.comum.tipo'/></th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
											</thead>
											<tbody role="alert" aria-live="polite" aria-relevant="all"></tbody>
										</table>
									</div><!-- .dataTables_wrapper -->
								</div><!-- .widget-body -->
							</div><!-- .widget -->
						</div><!-- .row-fluid -->
					</div><!-- #telefone-container -->

					<div id="endereco-container" class="box-container">
						<h2 class="heading"><fmt:message key="visao.enderecos" /></h2>

						<div class="row-fluid">
							<div class="row-fluid">
								<div class="span3">
									<label class="strong campoObrigatorio"><fmt:message key='rh.tipoEndereco'/></label>
									<div class="controls">
										<select class="span12 " id="endereco#idTipoEndereco" name="endereco#idTipoEndereco">
											<option value=""><fmt:message key='citcorpore.comum.selecione'/></option>
									        <option  value="1"><fmt:message key='rh.residencial'/></option>
											<option  value="2"><fmt:message key='rh.comercial'/></option>
										</select>
									</div><!-- .controls -->
								</div><!-- .span3 -->

								<div class="span3">
									<label class="strong campoObrigatorio"><fmt:message key='visao.cep'/></label>
									<div class="controls">
										<input type="text" class="span12" value="" id="cep" name="cep" maxlength="9" />
									</div><!-- .controls -->
								</div><!-- .span3 -->

								<div class="span3">
									<label class="strong campoObrigatorio"><fmt:message key='rh.pais'/></label>
									<div class="controls">
										<select class="span12 " name="pais" id="pais"></select>
									</div><!-- .controls -->
								</div><!-- .span3 -->

								<div class="span3">
									<label class="strong campoObrigatorio"><fmt:message key='rh.UF'/></label>
									<div class="controls">
										<select class="span12 " id="enderecoIdUF" name="enderecoIdUF"></select>
									</div><!-- .controls -->
								</div><!-- .span3 -->
							</div><!-- .row-fluid -->

							<div class="row-fluid">
								<div class="span3">
									<label class="strong campoObrigatorio"><fmt:message key='avaliacaoFonecedor.cidade'/></label>
									<div class="controls">
										<select class="span12 " id="enderecoIdCidade" name="enderecoIdCidade"></select>
									</div><!-- .controls -->
								</div><!-- .span3 -->

								<div class="span9">
									<label class="strong campoObrigatorio"><fmt:message key='unidade.logradouro'/></label>
										<input type="text" class="span12" value="" id="endereco#logradouro" name="endereco#logradouro" maxlength="100" data-toggle="tooltip" data-original-title="<fmt:message key='templateCurriculo.digiteEnderecoCompleto'/>" />
									<div class="controls">
									</div><!-- controls -->
								</div><!-- span9 -->
							</div><!-- .row-fluid -->

							<div class="row-fluid">
								<div class="span6">
									<label class="strong"><fmt:message key='localidade.complemento'/></label>
									<div class="controls">
										<input type="text" class="span12" value="" id="endereco#complemento" name="endereco#complemento" maxlength="100" />
									</div><!-- .controls -->
								</div><!-- .span6 -->

								<div class="span6">
									<label class="strong campoObrigatorio"><fmt:message key='localidade.bairro'/></label>
									<div class="controls">
										<input type="text" class="span12" value="" id="endereco#nomeBairro" name="endereco#nomeBairro" maxlength="80" />
									</div><!-- .controls -->
								</div><!-- .span6 -->
							</div><!-- .row-fluid -->

							<div class="row-fluid">
								<div class="btn-add-endereco-container btn-add">
									<span onclick="addEndereco()" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='rh.adicionarEndereco'/></span>
								</div>

								<div id="divEnderecoPrincipal" class="tab-pane active">
									<label class="checkbox">
										<input type="checkbox" name="principal" id="principal" /> <fmt:message key='visao.correspondencia'/>
									</label>
								</div><!-- #divEnderecoPrincipal -->
							</div><!-- .row-fluid -->
						</div><!-- .row-fluid -->

						<div class="row-fluid">
							<div class="widget">
								<div class="widget-head">
									<h3 class="heading"><fmt:message key='grupovisao.enderecos'/></h3>
								</div><!-- .widget-head -->

								<div class="widget-body">
									<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
										<table  style="table-layout: fixed;" class="dynamicTable table table-striped table-bordered table-enderecoCurriculo table-condensed dataTable" id="tblEnderecos" aria-describedby="DataTables_Table_0_info">
											<thead>
												<tr role="row">
													<th style="width: 96%; word-wrap: break-word;" class="sorting table-endereco" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"><fmt:message key='rh.endereco'/></th>
													<th style="width: 4%; word-wrap: break-word;" class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
											</thead>

											<tbody role="alert" aria-live="polite" aria-relevant="all"></tbody>
										</table>
									</div><!-- .dataTables_wrapper -->
								</div><!-- .widget-body -->
							</div><!-- .widget -->
						</div><!-- .row-fluid -->
					</div><!-- #endereco-container -->
				</div><!-- #tab1 -->

				<div id="tab2" class="tab-pane">
					<div id="formacao-container" class="box-container">
						<h2 class="heading"><fmt:message key='rh.formacaoTreinamento'/></h2>

						<div class="row-fluid">
							<div class="row-fluid">
								<div class="span6">
									<label class="strong campoObrigatorio"><fmt:message key='citcorpore.comum.tipo'/></label>
									<div class="controls">
										<select class="span12 " name='formacao#idTipoFormacao' id="formacao#idTipoFormacao">
											<option  value="" ><fmt:message key='citcorpore.comum.selecione'/></option>
									        <option  value="1" ><fmt:message key='rh.ensinoFundamental'/></option>
											<option  value="2" ><fmt:message key='rh.ensinoMedio'/></option>
											<option  value="3" ><fmt:message key='rh.graduacao'/></option>
											<option  value="4" ><fmt:message key='rh.posGraduacao'/></option>
											<option  value="5" ><fmt:message key='rh.mestrado'/></option>
											<option  value="6" ><fmt:message key='rh.doutorado'/></option>
											<option  value="7" ><fmt:message key='citcorpore.controleContrato.treinamento'/></option>
		  								</select>
									</div><!-- .controls -->
								</div><!-- .span4 -->

								<div class="span6">
									<label class="strong campoObrigatorio"><fmt:message key='categoriaProduto.categoria_situacao'/></label>
									<div class="controls">
										<select class="span12 " name='formacao#idSituacao'>
									        <option value=""><fmt:message key='citcorpore.comum.selecione'/></option>
									        <option  value="1"><fmt:message key='rh.cursou'/></option>
											<option  value="2"><fmt:message key='rh.cursando'/></option>
											<option  value="3"><fmt:message key='rh.trancadoInterrompido'/></option>
		 								</select>
									</div><!-- .controls -->
								</div><!-- .span4 -->
							</div><!-- .row-fluid -->

							<div class="row-fluid">
								<div class="span12">
									<label class="strong campoObrigatorio"><fmt:message key='rh.instituicaoEnsino'/></label>
									<div class="controls">
										<input type="text" class="span12" value="" id="formacao#instituicao" name="formacao#instituicao" maxlength="80">
									</div><!-- .controls -->
								</div><!-- .span4 -->
							</div><!-- .row-fluid -->

							<div class="row-fluid" id="divGraduacao">
								<label class="strong campoObrigatorio"><fmt:message key='rh.nomeDoCurso'/></label>
								<div class="controls">
<!-- 									<textarea rows="5" cols="100" class="span12" id="formacaoDescricao" name="formacaoDescricao" onkeypress="limita()" onblur="limita()"></textarea> -->
									<input type="text" class="span12" value="" id="formacaoDescricao" name="formacaoDescricao" maxlength="80">
								</div><!-- .controls -->
							</div><!-- .row-fluid -->

							<div class="row-fluid btn-add">
								<span onclick="addFormacao()" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='rh.adicionarFormacao'/></span>
							</div><!-- .row-fluid -->
						</div><!-- .row-fluid -->

						<div class="row-fluid">
							<div class="widget">
								<div class="widget-head">
									<h3 class="heading"><fmt:message key='rh.formacao'/></h3>
								</div><!-- .widget-head -->

								<div class="widget-body">
									<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
										<div class="row-fluid"></div>
										<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblFormacao" aria-describedby="DataTables_Table_0_info">
											<thead>
												<tr role="row">
												<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
													style="width: 15%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='citcorpore.comum.tipo'/></th>
												<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
													style="width: 15%;" aria-label="Eng. vers.: activate to sort column ascending"><fmt:message key='categoriaProduto.categoria_situacao'/></th>
												<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
													style="width: 15%;" aria-label="Platform(s): activate to sort column ascending"><fmt:message key='rh.instituicao'/></th>
												<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
													style="width: 50%;" aria-label="CSS grade: activate to sort column ascending"><fmt:message key='calendario.descricao'/></th>
												<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
													style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th></tr>
											</thead>
											<tbody role="alert" aria-live="polite" aria-relevant="all"></tbody>
										</table>
									</div><!-- .dataTables_wrapper -->
								</div><!-- .widget-body -->
							</div><!-- .widget -->
						</div><!-- .row-fluid -->
					</div><!-- #formacao-container -->

					<div id="idioma-container" class="box-container">
						<h2 class="heading"><fmt:message key="rh.idiomas" /></h2>

						<div class="row-fluid">
							<div class="span3">
								<label class="strong"><fmt:message key='lingua.lingua'/></label>
								<select name='idioma#idIdioma' id="idioma#idIdioma" class="span12 "></select>
		  					</div><!-- .span3 -->

		  					<div class="span3">
								<label class="strong"><fmt:message key='rh.escrita'/></label>
								<select name='idioma#idNivelEscrita' id="idioma#idNivelEscrita" class="span12 ">
							        <option  value=""><fmt:message key='citcorpore.comum.selecione'/></option>
							        <option  value="1"><fmt:message key='curriculo.idiomaBasico'/></option>
									<option  value="2"><fmt:message key='curriculo.idiomaIntermediario'/></option>
									<option  value="3"><fmt:message key='curriculo.idiomaAvancado'/></option>
								</select>
				  			</div><!-- .span3 -->

				  			<div class="span3">
								<label class="strong"><fmt:message key='rh.leitura'/></label>
								<select name='idioma#idNivelLeitura' id="idioma#idNivelLeitura" class="span12 ">
									<option  value=""><fmt:message key='citcorpore.comum.selecione'/></option>
							        <option  value="1"><fmt:message key='curriculo.idiomaBasico'/></option>
									<option  value="2"><fmt:message key='curriculo.idiomaIntermediario'/></option>
									<option  value="3"><fmt:message key='curriculo.idiomaAvancado'/></option>
								</select>
			  				</div><!-- .span3 -->

			  				<div class="span3">
								<label class="strong"><fmt:message key='rh.conversacao'/></label>
								 <select name='idioma#idNivelConversa' id="idioma#idNivelConversa" class="span12 ">
							       <option  value=""><fmt:message key='citcorpore.comum.selecione'/></option>
							        <option  value="1"><fmt:message key='curriculo.idiomaBasico'/></option>
									<option  value="2"><fmt:message key='curriculo.idiomaIntermediario'/></option>
									<option  value="3"><fmt:message key='curriculo.idiomaAvancado'/></option>
									<option  value="4"><fmt:message key='curriculo.idiomaFluente'/></option>
								</select>
				  			</div><!-- .span3 -->
						</div><!-- .row-fluid -->

						<div class="row-fluid btn-add">
							<span onclick="addIdioma()" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='rh.adicionarIdioma'/></span>
						</div>

						<div class="row-fluid">
							<div class="widget">
								<div class="widget-head">
									<h3 class="heading"><fmt:message key='menu.nome.idioma'/></h3>
								</div><!-- .widget-head -->

								<div class="widget-body">
									<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
										<div class="row-fluid"></div>
										<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblIdioma" aria-describedby="DataTables_Table_0_info">
											<thead>
												<tr role="row">
												<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
													style="width: 21%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='menu.nome.idioma'/></th>
												<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
													style="width: 21%;" aria-label="Platform(s): activate to sort column ascending"><fmt:message key='rh.escrita'/></th>
												<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
													style="width: 21%;" aria-label="Eng. vers.: activate to sort column ascending"><fmt:message key='rh.leitura'/></th>
												<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
													style="width: 22%;" aria-label="CSS grade: activate to sort column ascending"><fmt:message key='rh.conversacao'/></th>
												<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
													style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th></tr>
												</thead>
											<tbody role="alert" aria-live="polite" aria-relevant="all"></tbody>
										</table>
									</div><!-- .dataTables_wrapper -->
								</div><!-- .widget-body -->
							</div><!-- .widget -->
						</div><!-- .row-fluid -->
					</div><!-- #idioma-container -->

					<div id="treinamento-container" hidden="true">
						<!-- Treinamentos  -->
						<div class="row-fluid" hidden="true">
			  				<div>
								<input type="text" id="treinamento" name="treinamento" onclick="pesquisarCurso();" readonly="readonly"
									class="Description[<%= UtilI18N.internacionaliza(request, "rh.treinamentoCurso") %>]"
									style="width: 90% !important;" maxlength="70" size="70" />
								<img onclick="pesquisarCurso();" style="vertical-align: middle;"
									src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
							</div>
							<div class="row-fluid">
								<span onclick="addTreinamento()" style="margin-top: 25px" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='rh.adicionar.treinamento'/></span>
			  				</div>
			  				<br>
			  			</div>
						<!-- Fim Treinamentos -->

						<div class="widget" hidden="true">
							<div class="widget-head">
								<h4 class="heading"><fmt:message key='rh.treinamentoCurso'/></h4>
							</div>
							<div class="widget-body">
						<!-- Table  -->
							<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
								<div class="row-fluid">
								</div>
									<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblTreinamento" aria-describedby="DataTables_Table_0_info">

										<!-- Table heading -->
										<thead>
											<tr role="row">
											<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												style="width: 45%;" aria-label="Eng. vers.: activate to sort column ascending"><fmt:message key='rh.treinamentoCurso'/></th>
											<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												style="width: 50%;" aria-label="CSS grade: activate to sort column ascending"><fmt:message key='criterioAvaliacao.criterio_descricao'/></th>
											<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th></tr>
										</thead>
										<!-- // Table heading END -->

										<!-- Table body -->

										<!-- // Table body END -->

									<tbody role="alert" aria-live="polite" aria-relevant="all">
									</tbody>
								</table>
							</div>
						<!-- // Table END -->
							</div>
						</div>

					</div><!-- #treinamento-container -->
				</div><!-- #tab2 -->

				<div id="tab3" class="tab-pane">
					<h2 class="heading"><fmt:message key='rh.experiencia'/></h2>

					<div class="experiencia-nao-possui-container">
						<label class="checkbox inline">
							<input type="checkbox" id="experiencia-nao-possui" class="experiencia-nao-possui" name="experiencia-nao-possui" />&nbsp;<fmt:message key='rh.naoPossuiExperiencia'/>
						</label>
					</div>

					<div id="experiencia-container" class="experiencia-container">
						<div class="widget widget-experiencias">
							<div class="widget-head">
								<h3 class="heading"><fmt:message key='rh.empresa'/></h3>
							</div><!-- .widget-head -->

							<div class="widget-body">
								<div class="row-fluid">
									<div class="span6">
										<!-- nome da empresa -->
										<label class="strong campoObrigatorio"><fmt:message key='lookup.nomeEmpresa'/></label>
										<div class="controls">
											<input id="experiencia-empresa1" type="text" class="span12 experiencia-empresa" value="" name="experiencia-empresa[]" maxlength="80" />
										</div><!-- nome da empresa -->
									</div><!-- .span6 -->

									<div class="span6">
										<!-- localidade -->
										<label class="strong campoObrigatorio"><fmt:message key='menu.nome.localidade'/></label>
										<div class="controls">
											<input id="experiencia-localidade1" type="text" class="span12 experiencia-localidade" value="" name="experiencia-localidade[]" maxlength="80" />
										</div><!-- localidade -->
									</div><!-- .span6 -->
								</div><!-- .row-fluid -->

								<div class="widget-funcoes-container">
									<div class="widget widget-funcoes">
										<div class="widget-head">
											<h4 class="heading"><fmt:message key='rh.funcao'/></h4>
										</div><!-- .widget-head -->

										<div class="widget-body">
											<div class="row-fluid">
												<div class="">
													<label class="strong campoObrigatorio"><fmt:message key='rh.funcao'/></label>
													<div class="controls">
														<input id="experiencia-nomeFuncao1" type="text" class="span12 experiencia-nomeFuncao" value="" name="experiencia-nomeFuncao[]" maxlength="80" />
													</div><!-- .controls -->
												</div><!-- .span4 -->
												<div class="">
													<label class="strong campoObrigatorio"><fmt:message key='curriculo.descricaoFuncao'/></label>
													<div class="controls">
														<textarea id="experiencia-descricaoFuncao1" rows="" cols="" class="span12 experiencia-descricaoFuncao" value="" name="experiencia-descricaoFuncao[]" maxlength="600"></textarea>
													</div>
												</div><!-- .span4 -->
											</div><!-- .row-fluid -->

											<div class="row-fluid">
												<div class="row-fluid">
													<div class="span6">
														<label class="strong campoObrigatorio"><fmt:message key='citcorpore.texto.periodo'/></label>
													</div><!-- .span6 -->
													<div class="span6">
														<label class="checkbox inline diasAtuais-label">
															<input type="checkbox" name="diasAtuais" class="diasAtuais send_left" value="s" />&nbsp;<fmt:message key="rh.dias.atuais" />
														</label>
													</div><!-- .span6 -->
												</div><!-- .row-fluid -->

												<div class="row-fluid">
													<div class="span3">
														<label for="experiencia-mesInicio" class="strong campoObrigatorio"><fmt:message key='rh.mesInicio'/></label>
														<select id="experiencia-mesInicio1" name='experiencia-mesInicio[]' class="span12 experiencia-mesInicio">
															<option value=""><fmt:message key='citcorpore.comum.selecione'/></option>
															<option value="1"><fmt:message key='citcorpore.texto.mes.janeiro'/></option>
															<option value="2"><fmt:message key='citcorpore.texto.mes.fevereiro'/></option>
															<option value="3"><fmt:message key='citcorpore.texto.mes.marco'/></option>
															<option value="4"><fmt:message key='citcorpore.texto.mes.abril'/></option>
															<option value="5"><fmt:message key='citcorpore.texto.mes.maio'/></option>
															<option value="6"><fmt:message key='citcorpore.texto.mes.junho'/></option>
															<option value="7"><fmt:message key='citcorpore.texto.mes.julho'/></option>
															<option value="8"><fmt:message key='citcorpore.texto.mes.agosto'/></option>
															<option value="9"><fmt:message key='citcorpore.texto.mes.setembro'/></option>
															<option value="10"><fmt:message key='citcorpore.texto.mes.outubro'/></option>
															<option value="11"><fmt:message key='citcorpore.texto.mes.novembro'/></option>
															<option value="12"><fmt:message key='citcorpore.texto.mes.dezembro'/></option>
														</select>
													</div><!-- .span3 -->

													<div class="span2">
														<label for="experiencia-idMesFim" class="strong campoObrigatorio"><fmt:message key='rh.anoInicio'/></label>
														<input id="experiencia-anoInicio1" type="text" value="" onkeypress="return somenteNumero(event);" class="span12 experiencia-anoInicio datepicker-mm-aaaa" name="experiencia-anoInicio[]" maxlength="4" />
													</div><!-- .span12 -->

													<div class="escondedatafinal">
														<span></span><!-- So foi adicionado para alinhar o elemento .diasAtuais -->
														<div class="span1 div-a-container">
															<label for="div_a" class="span12"></label>
															<div class="span12" name='div_a' style="text-align: center;">a</div>
														</div><!-- .span1 -->
														<div class="span3">
															<label class="strong campoObrigatorio"><fmt:message key='rh.mesFim'/></label>
															<select id="experiencia-mesFim1" name='experiencia-mesFim[]' class="span12 experiencia-mesFim">
																<option value=""><fmt:message key='citcorpore.comum.selecione'/></option>
																<option value="1"><fmt:message key='citcorpore.texto.mes.janeiro'/></option>
																<option value="2"><fmt:message key='citcorpore.texto.mes.fevereiro'/></option>
																<option value="3"><fmt:message key='citcorpore.texto.mes.marco'/></option>
																<option value="4"><fmt:message key='citcorpore.texto.mes.abril'/></option>
																<option value="5"><fmt:message key='citcorpore.texto.mes.maio'/></option>
																<option value="6"><fmt:message key='citcorpore.texto.mes.junho'/></option>
																<option value="7"><fmt:message key='citcorpore.texto.mes.julho'/></option>
																<option value="8"><fmt:message key='citcorpore.texto.mes.agosto'/></option>
																<option value="9"><fmt:message key='citcorpore.texto.mes.setembro'/></option>
																<option value="10"><fmt:message key='citcorpore.texto.mes.outubro'/></option>
																<option value="11"><fmt:message key='citcorpore.texto.mes.novembro'/></option>
																<option value="12"><fmt:message key='citcorpore.texto.mes.dezembro'/></option>
															</select>
														</div><!-- .span3 -->

														<div class="span2">
															<label for="experiencia-anoFim" class="strong campoObrigatorio"><fmt:message key='rh.anoFim'/></label>
															<input id="experiencia-anoFim1" type="text" value="" class="span12 experiencia-anoFim"  onkeypress="return somenteNumero(event);" name="experiencia-anoFim[]" maxlength="4" />
														</div><!-- .span12 -->
													</div><!-- .escondedatafinal -->
												</div><!-- .row-fluid -->
											</div><!-- .row-fluid -->
										</div><!-- .widget-body -->
									</div><!-- .widget-funcoes -->
								</div><!-- .widget-funcoes-container -->
								<button id="add-funcoes-item" type="button" class="btn btn-small btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='rh.adicionarFuncao'/></button>
							</div><!-- .widget-body -->
						</div><!-- .widget-experiencias -->

						<button id="add-experiencias-item" type="button" class="btn btn-large btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='rh.adicionarEmpresa'/></button>
					</div><!-- .experiencia-container -->
				</div><!-- #tab3 -->

				<div id="tab4" class="tab-pane">
					<div id="competencias-container">
						<h2 class="heading"><fmt:message key='rh.competencias'/></h2>

						<div class="row-fluid">
							<span onclick="addCompetencia()" class="btn btn-add-competencia btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='rh.adicionarCompetencias'/></span>

							<div class="competencia-input-container">
								<div class="span8">
									<label class="strong"><fmt:message key='rh.competencia'/></label>
									<input type="text" class="span12" value="" id="competenciaDescricao" name="competenciaDescricao" maxlength="80" />
								</div><!-- .span8 -->

								<div class="span4">
									<label class="strong"><fmt:message key="citcorpore.comum.nivel" /></label>
									<select class="span12" id="competenciaNivel" name="competenciaNivel">
										<option  value=""><fmt:message key='citcorpore.comum.selecione'/></option>
								        <option  value="1"><fmt:message key='curriculo.idiomaBasico'/></option>
										<option  value="2"><fmt:message key='curriculo.idiomaIntermediario'/></option>
										<option  value="3"><fmt:message key='curriculo.idiomaAvancado'/></option>
									</select>
								</div><!-- .span4 -->
							</div><!-- .competencia-input-container -->
						</div><!-- .row-fluid -->

						<div class="row-fluid">
							<div class="widget">
								<div class="widget-head">
									<h3 class="heading"><fmt:message key='rh.competencias'/></h3>
								</div><!-- widget-head -->

								<div class="widget-body">
									<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
										<div class="row-fluid"></div>
										<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblCompetencia" aria-describedby="DataTables_Table_0_info">
											<thead>
												<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 80%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='rh.competencia'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 15%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="citcorpore.comum.nivel" /></th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
											</thead>

											<tbody role="alert" aria-live="polite" aria-relevant="all"></tbody>
										</table>
									</div><!-- .dataTables_wrapper -->
								</div><!-- widget-body -->
							</div><!-- widget -->
						</div><!-- .row-fluid -->
					</div><!-- #competencias-container -->

					<div id="certificacoes-container">
						<h2 class="heading"><fmt:message key='rh.certificacoes'/></h2>

						<div class="row-fluid">
							<span onclick="addCertificacao()" class="btn btn-add-certificacao btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='rh.adicionarCertificacao'/></span>

							<div class="certificacao-input-container">
								<div class="span7">
									<label class="strong"><fmt:message key='ManualFuncao.Certificacao'/></label>
									<div class="controls">
										<input type="text" class="span12" value="" id="certificacao#descricao" name="certificacao#descricao" maxlength="80" />
									</div>
								</div><!-- .span7 -->

								<div class="span2">
									<label class="strong"><fmt:message key='release.versao'/></label>
									<div class="controls">
										<input type="text" class="span12" value="" id="certificacao#versao" name="certificacao#versao" maxlength="80" />
									</div>
								</div><!-- .span2 -->

								<div class="span3">
									<label class="strong"><fmt:message key='rh.anoValidade'/></label>
									<div class="controls">
										<input type="text" class="span12" value="" onkeypress="return somenteNumero(event);" id="certificacao#validade" name="certificacao#validade" maxlength="4" />
									</div>
								</div><!-- .span3 -->
							</div><!-- .certificacoes-input-container -->
						</div><!-- .row-fluid -->

						<div class="row-fluid">
							<div class="widget">
								<div class="widget-head">
									<h3 class="heading"><fmt:message key='rh.certificacoesm'/></h3>
								</div><!-- widget-head -->

								<div class="widget-body">
									<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
										<div class="row-fluid"></div>
										<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblCertificacao" aria-describedby="DataTables_Table_0_info">
											<thead>
												<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 45%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='ManualFuncao.Certificacao'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 25%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='release.versao'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 25%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='rh.anoValidade'/></th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
											</thead>

											<tbody role="alert" aria-live="polite" aria-relevant="all"></tbody>
										</table>
									</div><!-- .dataTables_wrapper -->
								</div><!-- .widget-body -->
							</div><!-- .widget -->
						</div><!-- row-fluid -->
					</div><!-- #certificacoes-container -->
				</div><!-- #tab4 -->

				<div id="tab5" class="tab-pane">
					<div id="anexos-container">
						<h2 class="heading"><fmt:message key='rh.anexosAoCurriculo'/></h2>

						<div class="widget span9">
							<cit:uploadControl id="uploadAnexos" title="<fmt:message key='rh.anexo'/>" style="width: 99%;" form="document.formPesquisaCurriculo" action="/pages/upload/upload.load" disabled="false" />
							<font id="msgGravarDados" style="width: 99%; display:none" color="red"><fmt:message key="barraferramenta.validacao.solicitacao" /></font><br />
						</div><!-- .widget -->
					</div><!-- #anexos-container -->
				</div><!-- #tab5 -->

				<div class="pagination margin-bottom-none" style="text-align: center;">
					<ul style="width: 100%;">
						<li id="liPaginacao1" class="primary previous first disabled"><a href="javascript:primeiro();"><fmt:message key='citcorpore.texto.numeral.ordinal.Primeiro'/></a></li>
						<li id="liPaginacao2" class="primary previous disabled"><a href="javascript:retroceder();"><fmt:message key='citcorpore.comum.anterior'/></a></li>
						<li id="liPaginacao3" class="last primary"><a href="javascript:avancarUltimo();"><fmt:message key='citcorpore.texto.adjetivo.Ultimo'/></a></li>
					  	<li id="liPaginacao4" class="next primary"><a href="javascript:avancar();"><fmt:message key='citcorpore.comum.proximo'/></a></li>
						<li id="liPaginacao5" style="display: none;" class="next finish primary"><a href="javascript:gravar();"><fmt:message key='rh.finalizar'/></a></li>
						<li id="liPaginacao6" class="next primary next finish" ><a href="javascript:voltar();"><fmt:message key='citcorpore.comum.voltar'/></a></li>
					</ul>
				</div><!-- .pagination -->
			</div><!-- .tab-content -->
		</div><!-- .widget-body -->
	</div><!-- .widget-tabs -->
</form><!-- #formPesquisaCurriculo -->

<div class="modal hide fade in" id="PESQUISA_CURSOS" aria-hidden="false">
	<div class="modal-header">
		<h3><fmt:message key='candidato.candidato'/></h3>
	</div><!-- .modal-header -->

	<div class="modal-body" style="width:500px;">
		<form name='formPesquisaCurso'>
			<cit:findField formName='formPesquisaCurso'
				lockupName='LOOKUP_CURSOS' id='LOOKUP_PESQUISA_CURSOS' top='0'
				left='0' len='500' heigth='200' javascriptCode='true'
				htmlCode='true' />
		</form><!-- formPesquisaCurso -->
	</div><!-- .modal-body -->

	<div class="modal-footer">
		<%-- <a href="#" class="btn btn-primary" data-dismiss="modal" onclick="gravarAnexo();"><fmt:message key="citcorpore.comum.gravar" /></a> --%>
		<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
	</div><!-- .modal-footer -->
</div><!-- #PESQUISA_CURSOS -->

<!-- MODAL FOTO ... -->
<div class="modal hide fade in" id="modal_foto" aria-hidden="false" data-width="700">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3><fmt:message key='rh.adicionarFoto'/></h3>
	</div><!-- .modal-header -->

	<div class="modal-body">
    	<iframe name='frameUploadFoto' id='frameUploadFoto' src='about:blank' height="0" width="0"></iframe>
		<form name='formFoto' method="post" ENCTYPE="multipart/form-data" action='${ctx}/pages/uploadFile/uploadFile.load'>
			<table>
				<tr>
					<td>
						<fmt:message key='rh.arquivoFoto'/>:
					</td>
					<td>
						<input type='file' name='arquivo'/>
					</td>
				</tr>
				<tr>
					<td>
						<input type='button' id="inserir" name='btnEnviarImagem' value='<fmt:message key='citSmart.comum.enviar'/>' onclick='submitFormFoto()'/>
					</td>
				</tr>
			</table>
		</form><!-- formFoto -->
 	</div><!-- .modal-body -->

	<div class="modal-footer">
		<%-- <a href="#" class="btn btn-primary" data-dismiss="modal" onclick="gravarAnexo();"><fmt:message key="citcorpore.comum.gravar" /></a> --%>
		<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
	</div><!-- .modal-footer -->
</div><!-- #modal_foto -->

<div class="modal hide fade in" id="modal_candidato" aria-hidden="false">
	<div class="modal-header">
		<h3><fmt:message key='candidato.candidato'/></h3>
	</div><!-- .modal-header -->

	<div class="modal-body">
		<form name='formPesquisaCandidato'>
			<cit:findField formName='formPesquisaCandidato'
				lockupName='LOOKUP_CANDIDATO' id='LOOKUP_CANDIDATO' top='0'
				left='0' len='550' heigth='200' javascriptCode='true'
				htmlCode='true' />
		</form><!-- formPesquisaCandidato -->
	</div><!-- .modal-body -->

	<div class="modal-footer">
		<%-- <a href="#" class="btn btn-primary" data-dismiss="modal" onclick="gravarAnexo();"><fmt:message key="citcorpore.comum.gravar" /></a> --%>
		<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
	</div><!-- .modal-footer -->
</div><!-- #modal_candidato -->

<div class="modal hide fade in" id="modal_funcaocandidato" aria-hidden="false">
	<div class="modal-header">
		<h3>Função</h3>
	</div><!-- .modal-header -->

	<div class="modal-body">
		<form name='formExperienciaProfissional' id='formExperienciaProfissional' action='${ctx}/pages/experienciaProfissionalCurriculo/experienciaProfissionalCurriculo'>
			<input type='hidden' name='idExperienciaProfissional' id='idExperienciaProfissional'/>
			<input type='hidden' name='colFuncaoExperiencia_Serialize' id='colFuncaoExperiencia_Serialize'/>
			<input type='hidden' name='descricaoEmpresa' id='descricaoEmpresa'/>
			<input type='hidden' name='localidade' id='localidade'/>
			<input type='hidden' name='periodo' id='periodo'/>

			<div class="span6">
<!-- 			<label class="strong campoObrigatorio"><fmt:message key='rh.funcao'/></label> -->
<!-- 			<div class="controls"> -->
<!-- 				<input type="text" class="span6" value="" id="experiencia#nomeFuncao" name="experiencia#nomeFuncao" maxlength="80"> -->
<!-- 			</div> -->

<!-- 			<label class="strong campoObrigatorio"><fmt:message key='curriculo.descricaoFuncao'/></label> -->
<!-- 			<div class="controls"> -->
<!-- 				<textarea rows="" cols="" class="span6" value="" id="experiencia#descricaoFuncao" name="experiencia#descricaoFuncao" maxlength="80"></textarea> -->
<!-- 			</div> -->

<!-- 			<div class="row-fluid"> -->
<!-- 				<div class="input-append span12"> -->
<!-- 					<span onclick="addDescFuncao()" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='curriculo.adicionarFuncao'/></span> -->
<!-- 				</div> -->
			</div>

			<div class="row-fluid">
				<div class="widget span12">
					<div class="widget-head">
						<h4 class="heading"><fmt:message key='curriculo.funcoes'/></h4>
					</div><!-- .widget-head -->

					<div class="widget-body">
						<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
							<div class="row-fluid">
							</div>
								<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblDescFuncaoPopUp" aria-describedby="DataTables_Table_0_info">
									<thead>
										<tr role="row">
											<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												style="width: 30%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='rh.funcao'/></th>
											<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												style="width: 60%;" aria-label="Platform(s): activate to sort column ascending"><fmt:message key='calendario.descricao'/></th>
											<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												style="width: 10%;" aria-label="Platform(s): activate to sort column ascending"></th>
										</tr>
									</thead>
								<tbody role="alert" aria-live="polite" aria-relevant="all">
								</tbody>
							</table>
						</div>
					</div><!-- .widget-body -->
				</div><!-- .widget -->
			</div><!-- .row-fluid -->
		</form><!-- #formExperienciaProfissional -->
	</div><!-- .modal-body -->

	<div class="modal-footer">
<!-- 		<a href="#" class="btn btn-primary" data-dismiss="modal" onclick="gravarDescFuncao();"><fmt:message key="citcorpore.comum.gravar" /></a>	 -->
		<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
	</div><!-- .modal-footer -->
</div><!-- #modal_funcaocandidato -->