<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<%@include file="/include/header.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<meta charset="ISO-8859-1">
<title><fmt:message key="desenhoFluxo.titulo"/></title>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
<script type="text/javascript" src="${ctx}/js/Teclas.js"></script>

    <link type="text/css" rel="stylesheet" href="${ctx}/css/layout-default-latest.css"/>
    <link type="text/css" rel="stylesheet" href="css/desenho-fluxo.css"/>

    <link type="text/css" rel="stylesheet" href="${ctx}/css/jquery.ui.all.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css"/>

    <script type="text/javascript" src="${ctx}/js/jquery.layout-latest.js"></script>

    <script type="text/javascript" src="${ctx}/js/CollectionUtils.js"></script>

    <script type="text/javascript">
    	var ctx = "${ctx}";
    </script>
	<script type="text/javascript" src="js/desenho-fluxo.js"></script>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
</head>
<body>

	<h2 class="loading">
		<fmt:message key="citcorpore.comum.carregandoCarregando" />...
	</h2>

	<div class="ui-layout-west hidden">
		<h2 class="section">
			CITSmart Designer
		</h2>
		<div id="divAcoes" style='height:30px'>
			<table>
				<tr >
					<td class='tdIcone'><img src='imagens/voltar.gif' title=<fmt:message key="desenhoFluxo.msg.voltar"/> onclick='voltar()' class='icone' style='border: 1px solid #DDDDDD'></img></td>
					<td >&nbsp;</td>
					<td class='tdIcone' ><img id='imgCriarFluxo' src='imagens/add.png' title=<fmt:message key="desenhoFluxo.msg.novoFluxo"/> onclick='exibirNovoFluxo()' class='icone' style='border: 1px solid #DDDDDD'></img></td>
					<td class='tdIcone' ><img id='imgAlterarFluxo' style='display:none'src='imagens/edit.png' title=<fmt:message key="desenhoFluxo.msg.alterarFluxo"/> onclick='exibirCadastroFluxo()' class='icone' style='border: 1px solid #DDDDDD'></img></td>
					<td class='tdIcone' ><img id='imgExportarFluxo' style='display:none'src='imagens/export.png' title=<fmt:message key="desenhoFluxo.msg.exportarFluxo"/> onclick='exportarFluxo()' class='icone' style='border: 1px solid #DDDDDD'></img></td>
				</tr>
			</table>
		</div>
		<div id="divFluxos" >
		</div>
	</div>

	<div id='divCenter' class="ui-layout-center hidden">
		<div id="divElementos" style="position: absolute; margin: 0px;">
		</div>
		<div id="divCanvas" style="position: absolute; margin: 0px; overflow:auto;padding: 0px 0px 0px 0px !important">
			<canvas id="canvas"  style='border: 2px solid #DDDDDD; '>
			</canvas>
		</div>
		<div id="divPosicao" style="position: absolute; margin: 0px;padding: 0px 0px 0px 0px !important">
			<table style='width:100%'>
				<tr>
					<td >&nbsp;</td>
					<td style='width:24px;align:right;text-align:right;padding: 0px 0px 0px 0px !important' >
						<img id='imgDestacado' src='imagens/destacado.png'></img>
					</td>
					<td style='width:80px;align:right;font-size:9px;padding: 0px 0px 0px 0px !important'>
						&nbsp;&nbsp;<span id='spanSelecionado'></span>&nbsp;&nbsp;
					</td>
					<td  style='width:24px;align:right;text-align:right;padding: 0px 0px 0px 0px !important' >
						<img id='imgCentro' src='imagens/centro.png'></img>
					</td>
					<td style='width:80px;align:right;font-size:9px;padding: 0px 0px 0px 0px !important'>
						&nbsp;&nbsp;<span id='spanCentro'></span>&nbsp;&nbsp;
					</td>
					<td  style='width:24px;align:right;text-align:right;padding: 0px 0px 0px 0px !important' >
						<img id='imgPosicao' src='imagens/posicao.png'></img>
					</td>
					<td style='width:80px;align:right;font-size:9px;padding: 0px 0px 0px 0px !important'>
						&nbsp;&nbsp;<span id='spanPosicao'></span>&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>
	</div>

	<div id="POPUP_PROPRIEDADES" style="overflow: auto;">
		<form name='formPropriedades' id='formPropriedades' action='${ctx}/pages/desenhoFluxo/desenhoFluxo'>
			<input type='hidden' name='sequencia'/>
			<input type='hidden' name='elemento_serializado'/>
			<input type='hidden' name='idElemento' id='idElementoProp'/>
			<div id='divPropriedades' class=""></div>
		</form>
	</div>

	<div id="POPUP_FLUXO" title=<fmt:message key="desenhoFluxo.titulo.informacoesFluxo"/> style="overflow: auto;">
		<form name='form' id='form' method='post' action='${ctx}/pages/desenhoFluxo/desenhoFluxo'>
			<input type="hidden" name="locale" id="locale" value="" />
			<input type="hidden" name="idTipoFluxo" id="idTipoFluxoCad" />
			<input type="hidden" name="dataInicio" id="dataInicioCad" />
			<input type="hidden" name="dataFim" id="dataFimCad" />
			<input type="hidden" name="acao" id="acaoCad" />
			<input type='hidden' name='idFluxo'/>
	  	    <input type='hidden' name='elementos_serializados'/>
	  	    <input type='hidden' name='sequencias_serializadas'/>
	  	    <input type='hidden' name='linhaAtual'/>

			<div class="columns clearfix">
				<div class="col_100">
					<div class="col_33">
						<fieldset>
							<label class="campoObrigatorio">
								<fmt:message key="citcorpore.comum.nome" />
							</label>
							<div>
								<input type='text' name="nomeFluxo" maxlength="50" class="Valid[Required] Description[citcorpore.comum.nome]" />
							</div>
						</fieldset>
					</div>
					<div class="col_66">
                         <fieldset>
                             <label class="campoObrigatorio">
                                 <fmt:message key="citcorpore.comum.descricao" />
                             </label>
                             <div>
                                 <input type='text' name="descricao" class="Valid[Required] Description[citcorpore.comum.descricao]" />
                             </div>
                         </fieldset>
					</div>
				</div>
				<div class="col_100">
					<div class="col_80">
						<fieldset>
							<label class="campoObrigatorio">
								<fmt:message key="cadastroFluxo.nomeClasse" />
							</label>
							<div>
								<input type='text' name="nomeClasseFluxo" maxlength="255" class="Valid[Required] Description[cadastroFluxo.nomeClasse]" />
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label>
								<fmt:message key="login.versao" />
							</label>
							<div>
								<input type='text' readonly="readonly" name="versao" class="" maxlength="10" />
							</div>
						</fieldset>
					</div>
			</div>
			</div>
			<div class="col_100">
				<fieldset>
					<label>
						<fmt:message key="cadastroFLuxo.variaveis" />
					</label>
					<div>
						<textarea name="variaveis" rows="4" cols="50"></textarea>
					</div>
				</fieldset>
			</div>
			<div class="col_100">
			<button type='button' name='btnGravar' class="light"
				onclick='gravarCadastroFluxo();'>
				<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
					<span><fmt:message key="citcorpore.comum.gravar" /></span>
			</button>
			<button type='button' name='btnImportarFluxo' class="light"
				onclick='exibeImportacaoFluxo()'>
				<img src="imagens/import.png">
					<span><fmt:message key="desenhoFluxo.btn.importarFluxo" />
				</span>
			</button>
			<button type='button' name='btnExportarFluxo' class="light"
				onclick='exportarFluxo()'>
				<img src="imagens/export.png">
					<span><fmt:message key="desenhoFluxo.btn.exportarFluxo" />
				</span>
			</button>
			<button type='button' name='btnExcluirFluxo' class="light"
				onclick='excluirFluxo();'>
				<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
					<span><fmt:message key="citcorpore.comum.excluir" />
				</span>
			</button>
			</div>
		</form>
	</div>

	<div id="POPUP_IMPORTACAO" title=<fmt:message key="desenhoFluxo.titulo.selecionarArquivo"/> style="overflow: auto;">
	    <form name='formImportar' method='post' ENCTYPE="multipart/form-data" action='${ctx}/pages/desenhoFluxo/desenhoFluxo.load'>
			<input type='hidden' name='acao' id='acaoImportar' value='I'/>

			<div class="columns clearfix">
				<div class="col_100">
						<fieldset>
							<label class="campoObrigatorio">
								<fmt:message key="desenhoFluxo.label.selecioneArquivo" />
							</label>
							<div>
								<input type='file' name='fileImportar' size="50" value=''/>
							</div>
						</fieldset>
				</div>
			</div>
            <button type='button'class="light"
				onclick='importarFluxo();'>
				<span><fmt:message key="citcorpore.comum.confirmar" />
				</span>
			</button>
	    </form>
	</div>
</body>
</html>

