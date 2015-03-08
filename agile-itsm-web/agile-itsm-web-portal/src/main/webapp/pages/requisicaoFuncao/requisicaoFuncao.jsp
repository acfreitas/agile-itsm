<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/security/security.jsp"%>
<%@include file="/novoLayout/common/include/titulo.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<link type="text/css" rel="stylesheet" href="css/RequisicaoFuncao.css" />
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css" />
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css" />

<!-- <script src="js/RequisicaoViagem.js"></script> -->
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/PerspectivaComplexidadeDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/ExperienciaProfissionalCurriculoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/CompetenciasTecnicasDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/PerspectivaTecnicaDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/PerspectivaComportamentalFuncaoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/FormacaoAcademicaDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/CertificacaoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/CursoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/IdiomaDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/ConhecimentoDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/AtitudeIndividualDTO.js"></script>
<script type="text/javascript" src="${ctx}/cit/objects/RequisicaoFuncaoDTO.js"></script>



<script	src="${ctx}/novoLayout/common/include/js/jquery.autocomplete.js"></script>
<script src="js/RequisicaoFuncao.js"></script>

<title><fmt:message key="citcorpore.comum.title" /></title>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title=""
	style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body>

	<div class="nowrapper">

		<!-- Inicio conteudo -->
		<div id="content">
			<!-- 				<div id="tabs-2" class="box-generic" style="overflow: hidden;"> -->
			<form id="form" name="form"
				action="${ctx}/pages/requisicaoFuncao/requisicaoFuncao">
				<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
				<input type='hidden' name='idRequisicaoFuncao' id='idRequisicaoFuncao' />
				<input type='hidden' name='fase' id='fase' />
				<input type='hidden' name='idJustificativaFuncao' id='idJustificativaFuncao' />
				<input type='hidden' name='idFormacaoAcademica' id='idFormacaoAcademica' />
				<input type='hidden' name='idCertificacao' id='idCertificacao' />
				<input type='hidden' name='idCurso' id='idCurso' />
				<input type='hidden' name='idIdioma' id='idIdioma' />
				<input type='hidden' name='idConhecimento' id='idConhecimento' />
				<input type='hidden' name='idAtitudeIndividual' id='idAtitudeIndividual' />
				<input type='hidden' name='idCargo' id='idCargo' />


				<input type='hidden' name='colPerspectivaComportamentalSerialize' id='colPerspectivaComportamentalSerialize' />
				<input type='hidden' name='colPerspectivaComplexidadeSerialize' id='colPerspectivaComplexidadeSerialize' />
				<input type='hidden' name='colPerspectivaTecnicaFormacaoAcademicaSerialize' id='colPerspectivaTecnicaFormacaoAcademicaSerialize' />
				<input type='hidden' name='colPerspectivaTecnicaCertificacaoSerialize' id='colPerspectivaTecnicaCertificacaoSerialize' />
				<input type='hidden' name='colPerspectivaTecnicaCursoSerialize' id='colPerspectivaTecnicaCursoSerialize' />
				<input type='hidden' name='colPerspectivaTecnicaIdiomaSerialize' id='colPerspectivaTecnicaIdiomaSerialize' />
				<input type='hidden' name='colPerspectivaTecnicaExperienciaSerialize' id='colPerspectivaTecnicaExperienciaSerialize' />
				<input type='hidden' name='colCompetenciasTecnicasSerialize' id='colCompetenciasTecnicasSerialize' />

				<!-- Inicio etapa 1 -->
				<div id="etapa1" class="widget row-fluid" data-toggle="collapse-widget">
                        <div class="widget-head">
							<h4 class="heading"><fmt:message key="requisicaoFuncao.requisicaoFuncao"/></h4>
							<span class="collapse-toggle" onclick="controle_etapa1();"></span>
						</div>
						<div class="widget-body collapse in">
							<div class="row-fluid">
								<div class="span12">
									<div class="span6">
										<label class="strong campoObrigatorio"><fmt:message key="rh.funcao" /></label>
										<input id="nomeFuncao" name="nomeFuncao" maxlength="200" type="text" class="Description[rh.funcao]" required="required"/>
									</div>
									<div class="span3">
										<label class="strong campoObrigatorio"><fmt:message key="requisicaoFuncao.NumeroPessoas" /></label>
										<input id="numeroPessoas" name="numeroPessoas" type="text" maxlength="3" class="Valid[Required] Description[requisicaoFuncao.NumeroPessoas]"  />
									</div>
									<div class="span3">
										<label class="strong campoObrigatorio"> <fmt:message key="requisicaoFuncao.possuiSubordinados" /></label>
										<fieldset >
											<input type='radio' class="Valid[Required]" id="possuiSubordinados1" name="possuiSubordinados" value="S">
												<fmt:message key="citcorpore.comum.sim" />
											<input type='radio' class="Valid[Required]" id="possuiSubordinados2" name="possuiSubordinados" value="N">
												<fmt:message key="citcorpore.comum.nao" />
										</fieldset>
									</div>
								</div>
							</div>

							<div class="row-fluid">
								<div class="span12">
									<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.justificativa" /></label>
									<textarea name="justificativaFuncao" maxlength="500" id="justificativaFuncao" rows="10" cols="4"></textarea>
								</div>
							</div>

							<div class="row-fluid">
								<div class="span12">
									<label class="strong campoObrigatorio"><fmt:message key="requisicaoFuncao.resumoAtividades" /></label>
									<textarea name="resumoAtividades" maxlength="500" id="resumoAtividades" rows="10" cols="4"></textarea>
								</div>
							</div>
					</div>
				</div>
				<!-- Fim etapa 1 -->

				<!-- Inicio etapa 2 -->
				<div id="etapa2" class="widget row-fluid" data-toggle="collapse-widget" style="display: none;">
                        <div class="widget-head">
							<h4 class="heading"><fmt:message key="requisicaoFuncao.validarRequisicao"/></h4>
							<span class="collapse-toggle" onclick="controle_etapa2();"></span>
						</div>
						<div class="widget-body collapse in">
						<div class="row-fluid">
							<div class="span12">
								<div class="span3">
									<label class=" strong campoObrigatorio"> <fmt:message key="requisicaoFuncao.requisicaoValida" /></label>
									<fieldset >
										<input type='radio' id="requisicaoValida1" name="requisicaoValida" value="S" onclick="ocultaDiv1(this.value)">
										<fmt:message key="citcorpore.comum.sim" />
										<input type='radio' id="requisicaoValida2" name="requisicaoValida" value="N" onclick="ocultaDiv1(this.value)">
										<fmt:message key="citcorpore.comum.nao" />
									</fieldset>
								</div>
							</div>
						</div>
						<br>
						<div class="row-fluid" id="justificativa1">
							<div class="span12">
								<div class="span4">
									<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.justificativa" /></label>
									<select id="justificativaValidacao" name="justificativaValidacao"  class="Valid[Required] Description[citcorpore.comum.justificativa] span12"></select>
								</div>
								<div class="span8">
									<label class="strong"><fmt:message key="requisicaoFuncao.complementoJustificativa" /></label>
									<textarea name="complementoJustificativaValidacao" maxlength="500" id="complementoJustificativaValidacao" rows="10" cols="4"></textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- Fim etapa 2 -->

				<!-- Inicio etapa 3 -->
				<div class="widget" id="etapa3">
					<div class="widget-head">
						<h2>
							<fmt:message key="requisicaoFuncao.descricaoFuncao" />
						</h2>
					</div>

					<!-- Inicio etapa 3.1 -->
					<div id="etapa3_1" class="widget marginInterna" data-toggle="collapse-widget">
                        <div class="widget-head">
							<h4 class="heading"><fmt:message key="itemRequisicaoProduto.resumo" /></h4>
							<span class="collapse-toggle" onclick="controle_etapa3_1();"></span>
						</div>
						<div class="widget-body collapse in">
							<div class="row-fluid">
								<div class="span12">
									<div class="span4">
										<label class="strong campoObrigatorio"><fmt:message key="cargo.cargo" /></label>
										<input id="cargo" name="cargo" type="text" class="Valid[Required] Description[cargo.cargo]" maxlength="100" onclick="clearIdCargo()"/>
									</div>
									<div class="span4">
										<label class="strong campoObrigatorio"><fmt:message key="rh.funcao" /></label>
										<input id="funcao" name="funcao" type="text" class="Valid[Required] Description[rh.funcao]" maxlength="100" />
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span12">
									<label class="strong campoObrigatorio"><fmt:message key="requisicaoFuncao.resumoFuncao" /></label>
									<textarea name="resumoFuncao" maxlength="500" id="resumoFuncao" rows="10" cols="4"></textarea>
								</div>
							</div>
						</div>
					</div>
						<!-- Fim etapa 3.1 -->

						<!-- Inicio etapa 3.2 -->
						<div id="etapa3_2" class="widget marginInterna" data-toggle="collapse-widget">
	                        <div class="widget-head">
								<h4 class="heading"><fmt:message key="requisicaoFuncao.perspectivaComplexidade" /></h4>
								<span class="collapse-toggle" onclick="controle_etapa3_2();"></span>
							</div>
							<div class="widget-body collapse in">
								<div class="row-fluid">
									<div class="span12">
										<div class="span6">
											<label class="strong "><fmt:message key="citcorpore.comum.descricao" /></label>
											<input id="descricaoPerspectivaComplexidade" name="descricaoPerspectivaComplexidade" maxlength="200" type="text" class="Valid[Required] Description[citcorpore.comum.descricao]" />
										</div>
										<div class="span4">
											<label class="strong "><fmt:message key="citcorpore.comum.nivel" /></label>
											<select id="nivelPerspectivaComplexidade" name="nivelPerspectivaComplexidade" class="Description[citcorpore.comum.nivel]"></select>
										</div>
										<div class="spa2">
											<span onclick="addPerspectivaComplexidade()" id="addPerspectivaComplexidade" style="margin-top: 29px" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='citcorpore.comum.adicionar'/></span>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span12">
										<!-- Table  -->
											<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
												<div class="row-fluid">
												</div>
											<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblPerspectivaComplexidade" aria-describedby="DataTables_Table_0_info">
											<!-- Table heading -->
											<thead>
												<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 75%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='citcorpore.comum.descricao'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 20%;" aria-label="Platform(s): activate to sort column ascending"><fmt:message key='citcorpore.comum.nivel'/></th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
											 			style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
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
							</div>
						</div>
						<!-- Fim etapa 3.2 -->

						<!-- Inicio etapa 3.3 -->
						<div id="etapa3_3" class="widget marginInterna" data-toggle="collapse-widget">
	                        <div class="widget-head">
								<h4 class="heading"><fmt:message key="requisicaoFuncao.perspectivaTecnica" /></h4>
								<span class="collapse-toggle" onclick="controle_etapa3_3();"></span>
							</div>
							<div class="widget-body collapse in">
								<!-- Inicio Formacao academica -->
								<div class="row-fluid">
									<div class="span12">
										<div class="span8">
											<label class="strong "><fmt:message key="menu.nome.formacaoAcademica" /></label>
											<input id="descricaoFormacaoAcademica" name="descricaoFormacaoAcademica" type="text" class="Valid[Required] Description[menu.nome.formacaoAcademica]" />
										</div>
										<div class="span2 marginTop">
											<input type="checkbox" class="alignCheck" name="obrigatorioFormacao" id="obrigatorioFormacao" value="N" onclick="validaObrigatorio('obrigatorioFormacao')" />
											<label class="strong "><fmt:message key="citcorpore.comum.obrigatorio" /></label>
										</div>
										<div class="span1">
											<span onclick="addFormacaoAcademica()" id="addFormacaoAcademica" style="margin-top: 29px" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='citcorpore.comum.adicionar'/></span>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span12">
										<!-- Table  -->
											<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
												<div class="row-fluid">
												</div>
											<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblFormacaoAcademica" aria-describedby="DataTables_Table_0_info">
											<!-- Table heading -->
											<thead>
												<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 5%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='citcorpore.comum.obrigatorio'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 45%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='menu.nome.formacaoAcademica'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 45%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='produto.detalhes'/></th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
											 			style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
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
								<!-- Fim Formacao academica -->
								<br>
								<!-- Inicio certificações -->
								<div class="row-fluid">
									<div class="span12">
										<div class="span8">
											<label class="strong "><fmt:message key="menu.nome.certificacao" /></label>
											<input id="descricaoCertificacao" name="descricaoCertificacao" type="text" class="Valid[Required] Description[menu.nome.certificacao]" />
										</div>
										<div class="span2 marginTop">
											<input type="checkbox" class="alignCheck" name="obrigatorioCertificacao" id="obrigatorioCertificacao" value="N" onclick="validaObrigatorio('obrigatorioCertificacao')" />
											<label class="strong "><fmt:message key="citcorpore.comum.obrigatorio" /></label>
										</div>
										<div class="span1">
											<span onclick="addCertificacao()" id="addCertificacao" style="margin-top: 29px" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='citcorpore.comum.adicionar'/></span>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span12">
										<!-- Table  -->
											<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
												<div class="row-fluid">
												</div>
											<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblCertificacao" aria-describedby="DataTables_Table_0_info">
											<!-- Table heading -->
											<thead>
												<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 5%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='citcorpore.comum.obrigatorio'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 70%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='menu.nome.certificacao'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 20%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='produto.detalhes'/></th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
											 			style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
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
								<!-- Fim certificações -->
								<br>
								<!-- Inicio cursos -->
								<div class="row-fluid">
									<div class="span12">
										<div class="span8">
											<label class="strong "><fmt:message key="menu.nome.curso" /></label>
											<input id="descricaoCurso" name="descricaoCurso" type="text" class="Valid[Required] Description[menu.nome.curso]" />
										</div>
										<div class="span2 marginTop">
											<input type="checkbox" class="alignCheck" name="obrigatorioCurso" id="obrigatorioCurso" value="N" onclick="validaObrigatorio('obrigatorioCurso')" />
											<label class="strong "><fmt:message key="citcorpore.comum.obrigatorio" /></label>
										</div>
										<div class="span1">
											<span onclick="addCurso()" id="addCurso" style="margin-top: 29px" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='citcorpore.comum.adicionar'/></span>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span12">
										<!-- Table  -->
											<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
												<div class="row-fluid">
												</div>
											<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblCurso" aria-describedby="DataTables_Table_0_info">
											<!-- Table heading -->
											<thead>
												<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 5%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='citcorpore.comum.obrigatorio'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 45%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='menu.nome.curso'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 45%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='produto.detalhes'/></th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
											 			style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
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
								<!-- Fim cursos -->
								<br>
								<!-- Inicio idiomas -->
								<div class="row-fluid">
									<div class="span12">
										<div class="span8">
											<label class="strong "><fmt:message key="rh.idiomas" /></label>
											<input id="descricaoIdioma" name="descricaoIdioma" type="text" class="Valid[Required] Description[rh.idiomas]" />
										</div>
										<div class="span2 marginTop">
											<input type="checkbox" class="alignCheck" name="obrigatorioIdioma" id="obrigatorioIdioma" value="N" onclick="validaObrigatorio('obrigatorioIdioma')" />
											<label class="strong "><fmt:message key="citcorpore.comum.obrigatorio" /></label>
										</div>
										<div class="span1">
											<span onclick="addIdioma()" id="addIdioma" style="margin-top: 29px" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='citcorpore.comum.adicionar'/></span>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span12">
										<!-- Table  -->
											<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
												<div class="row-fluid">
												</div>
											<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblIdioma" aria-describedby="DataTables_Table_0_info">
											<!-- Table heading -->
											<thead>
												<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 5%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='citcorpore.comum.obrigatorio'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 45%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='rh.idiomas'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 45%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='produto.detalhes'/></th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
											 			style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
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
								<!-- Fim idiomas -->
								<br>
								<!-- Inicio experiencias -->
								<div class="row-fluid">
									<div class="span12">
										<div class="span8">
											<label class="strong "><fmt:message key="menu.nome.experiencia" /></label>
											<input id="descricaoExperiencia" name="descricaoExperiencia" type="text" class="Valid[Required] Description[solicitacaoCargo.experienciaAnterior]" />
										</div>
										<div class="span2 marginTop">
											<input type="checkbox" class="alignCheck" name="obrigatorioExperiencia" id="obrigatorioExperiencia" value="N" onclick="validaObrigatorio('obrigatorioExperiencia')" />
											<label class="strong "><fmt:message key="citcorpore.comum.obrigatorio" /></label>
										</div>
										<div class="span1">
											<span onclick="addExperiencia()" id="addExperiencia" style="margin-top: 29px" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='citcorpore.comum.adicionar'/></span>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span12">
										<!-- Table  -->
											<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
												<div class="row-fluid">
												</div>
											<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblExperiencia" aria-describedby="DataTables_Table_0_info">
											<!-- Table heading -->
											<thead>
												<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 5%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='citcorpore.comum.obrigatorio'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 45%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='solicitacaoCargo.experienciaAnterior'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 45%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='produto.detalhes'/></th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
											 			style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
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
								<!-- Fim experiencias -->


							</div>
						</div>
						<!-- Fim etapa 3.3 -->

						<!-- Inicio etapa 3.4 -->
						<div id="etapa3_4" class="widget marginInterna" data-toggle="collapse-widget">
	                        <div class="widget-head">
								<h4 class="heading"><fmt:message key="requisicaoFuncao.competenciasTecnicas" /></h4>
								<span class="collapse-toggle" onclick="controle_etapa3_4();"></span>
							</div>
							<div class="widget-body collapse in">
								<div class="row-fluid">
									<div class="span12">
										<div class="span6">
											<label class="strong "><fmt:message key="citcorpore.comum.descricao" /></label>
											<input id="descricaoCompetenciasTecnicas" maxlength="200" name="descricaoCompetenciasTecnicas" type="text" class="Valid[Required] Description[citcorpore.comum.descricao]" />
										</div>
										<div class="span4">
											<label class="strong "><fmt:message key="citcorpore.comum.nivel" /></label>
											<select id="nivelCompetenciasTecnicas" name="nivelCompetenciasTecnicas" class="Description[citcorpore.comum.nivel]"></select>
										</div>
										<div class="span2">
											<span onclick="addCompetenciasTecnicas()" id="addCompetenciasTecnicas" style="margin-top: 29px" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='citcorpore.comum.adicionar'/></span>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span12">
										<!-- Table  -->
											<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
												<div class="row-fluid">
												</div>
											<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblCompetenciasTecnicas" aria-describedby="DataTables_Table_0_info">
											<!-- Table heading -->
											<thead>
												<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 75%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='citcorpore.comum.descricao'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 20%;" aria-label="Platform(s): activate to sort column ascending"><fmt:message key='citcorpore.comum.nivel'/></th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
											 			style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
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
							</div>
						</div>
						<!-- Fim etapa 3.4 -->

						<!-- Inicio etapa 3.5 -->
						<div id="etapa3_5" class="widget marginInterna" data-toggle="collapse-widget">
	                        <div class="widget-head">
								<h4 class="heading"><fmt:message key="requisicaoFuncao.perspectivaComportamental" /></h4>
								<span class="collapse-toggle" onclick="controle_etapa3_5();"></span>
							</div>
							<div class="widget-body collapse in">
								<div class="row-fluid">
									<div class="span12">
										<div class="span6">
											<label class="strong "><fmt:message key="citcorpore.comum.descricao" /></label>
											<input id="descricaoPerspectivaComportamental" name="descricaoPerspectivaComportamental"  maxlength="200" type="text" class="Valid[Required] Description[citcorpore.comum.descricao]" />
										</div>
										<div class="span6">
											<label class="strong "><fmt:message key="produto.detalhes" /></label>
											<textarea rows="5" cols="" id="detalhePerspectivaComportamental" name="detalhePerspectivaComportamental" maxlength="500" class="Valid[Required] Description[produto.detalhes]"></textarea>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span12">
										<div class="span2">
											<span onclick="addPerspectivaComportamental();" id="addPerspectivaComportamental" style="margin-top: 29px" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><fmt:message key='citcorpore.comum.adicionar'/></span>
										</div>
									</div>
								</div>
								<br>
								<div class="row-fluid">
									<div class="span12">
										<!-- Table  -->
											<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
												<div class="row-fluid">
												</div>
											<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblPerspectivaComportamental" aria-describedby="DataTables_Table_0_info">
											<!-- Table heading -->
											<thead>
												<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 35%;" aria-label="Browser: activate to sort column ascending"><fmt:message key='citcorpore.comum.descricao'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
														style="width: 60%;" aria-label="Platform(s): activate to sort column ascending"><fmt:message key='produto.detalhes'/></th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
											 			style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
												</tr>
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
							</div>
						</div>
						<!-- Fim etapa 3.5 -->
				</div>
				<!-- Fim etapa 3 -->


						<!-- Inicio etapa 4 -->
						<div id="etapa4" class="widget row-fluid" data-toggle="collapse-widget">
	                        <div class="widget-head">
								<h4 class="heading"><fmt:message key="requisicaoFuncao.validarDescricaoFuncao" /></h4>
								<span class="collapse-toggle" onclick="controle_etapa4();"></span>
							</div>
							<div class="widget-body collapse in">
							<div class="row-fluid">
							<div class="span12">
								<div class="span3">
									<label class=" strong campoObrigatorio"> <fmt:message key="requisicaoFuncao.descricaoValida" /></label>
									<fieldset>
										<input type='radio' name="descricaoValida" value="S" onclick="ocultaDiv2(this.value)">
										<fmt:message key="citcorpore.comum.sim" />
										<input type='radio' name="descricaoValida" value="N" onclick="ocultaDiv2(this.value)">
										<fmt:message key="citcorpore.comum.nao" />
									</fieldset>
								</div>
							</div>
							</div>
							<br>
							<div class="row-fluid" id="justificativa2">
								<div class="span12">
									<div class="span4">
										<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.justificativa" /></label>
										<select id="justificativaDescricaoFuncao" name="justificativaDescricaoFuncao"  class="Valid[Required] Description[citcorpore.comum.justificativa] span12"></select>
									</div>
									<div class="span8">
										<label class="strong "><fmt:message key="requisicaoFuncao.complementoJustificativa" /></label>
										<textarea name="complementoJustificativaDescricaoFuncao" maxlength="500" id="complementoJustificativaDescricaoFuncao" rows="10" cols="4"></textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- Fim etapa 4 -->

			</form>
		</div>
	</div>

</body>
</html>