<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.CentroResultadoDTO" %>
<%@page import="br.com.centralit.citcorpore.bean.AlcadaDTO"%>
<%@page import="java.util.Collection"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");

		    Collection<AlcadaDTO> colAlcadas = (Collection)request.getAttribute("colAlcadas");
		%>

		<%@ include file="/include/security/security.jsp" %>
		<%@include file="/include/header.jsp"%>

		<title>
			<fmt:message key="citcorpore.comum.title" />
		</title>

		<%@ include file="/include/menu/menuConfig.jsp" %>

		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
		<script type="text/javascript" src="../../cit/objects/AlcadaCentroResultadoDTO.js"></script>

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

		<script>
			var objTab = null;

			addEvent(window, "load", load, false);

			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				};
			}

			function LOOKUP_CENTRORESULTADO_select(id, desc) {
				document.form.restore({
					idCentroResultado: id
				});
			}

			function LOOKUP_CENTRORESULTADOPAI_select(id, desc) {
				document.form.idCentroResultadoPai.value = id;

	    		var valor = desc.split('-');
				var nomeConcatenado = "";

				for (var i = 0 ; i < valor.length; i++) {
					if (i == 0) {
						document.form.nomeCentroResultadoPai.value = valor[i];
					}
				}
				$("#POPUP_CENTRORESULTADOPAI").dialog("close");
			}

			function excluir() {
				var idCentroResultado = document.getElementById("idCentroResultado");

				if (idCentroResultado.value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
						document.form.fireEvent("delete");
					}
				} else {
					alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro") );
					return false;
				}
			}

			$(function() {
				$("#POPUP_CENTRORESULTADOPAI").dialog({
					autoOpen: false,
					width: 705,
					height: 500,
					modal: true
				});
				$('#POPUP_HIERARQUIA_CENTRORESULTADO').dialog({
					autoOpen: false,
					width: 705,
					height: 500,
					modal: true
				});
                $("#POPUP_EMPREGADO").dialog({
                    title: i18n_message("colaborador.pesquisacolaborador"),
                    autoOpen : false,
                    width : 600,
                    height : 400,
                    modal : true,
                    show: "fade",
                    hide: "fade"
                });
            });

			function consultarCentroResultadoPai() {
				$("#POPUP_CENTRORESULTADOPAI").dialog("open");
			}

			function visualizarHierarquiaCentrosResultado() {
				document.form.fireEvent("visualizarHierarquiaCentrosResultado");
				$('#POPUP_HIERARQUIA_CENTRORESULTADO').dialog('open');
			}

		    var seqAlcada = '';
		    incluirAlcada = function() {
		        GRID_ALCADAS.addRow();
		        seqAlcada = NumberUtil.zerosAEsquerda(GRID_ALCADAS.getMaxIndex(),5);
		        eval('document.form.idAlcada' + seqAlcada + '.focus()');
		    }

		    exibeAlcada = function(serializeAlcada) {
		        if (seqAlcada != '') {
		            if (!StringUtils.isBlank(serializeAlcada)) {
		                var alcadaDto = new CIT_AlcadaCentroResultadoDTO();
		                alcadaDto = ObjectUtils.deserializeObject(serializeAlcada);
		                try{
		                    eval('HTMLUtils.setValue("idAlcada' + seqAlcada + '",' + alcadaDto.idAlcada + ')');
		                }catch(e){
		                }
	                    eval('document.form.idEmpregado' + seqAlcada + '.value = ' + alcadaDto.idEmpregado);
		                eval('document.form.nomeEmpregado' + seqAlcada + '.value = "' + alcadaDto.nomeEmpregado + '"');
	                    //eval('document.form.dataInicio' + seqAlcada + '.value = "' + alcadaDto.dataInicioStr + '"');
                        //eval('document.form.dataFim' + seqAlcada + '.value = "' + alcadaDto.dataFimStr + '"');
		            }
		        }
		    }

		    getAlcada = function(seq) {
		        var alcadaDto = new CIT_AlcadaCentroResultadoDTO();

		        seqAlcada = NumberUtil.zerosAEsquerda(seq,5);
		        alcadaDto.sequencia = seq;
		        alcadaDto.idAlcada = parseInt(eval('document.form.idAlcada' + seqAlcada + '.value'));
		        alcadaDto.idEmpregado = eval('document.form.idEmpregado' + seqAlcada + '.value');
	            alcadaDto.dataInicio = eval('document.form.dataInicio' + seqAlcada + '.value');
	            alcadaDto.dataFim = eval('document.form.dataFim' + seqAlcada + '.value');
		        return alcadaDto;
		    }

		    verificarAlcada = function(seq) {
		        /*var idAlcada = eval('document.form.idAlcada' + seq + '.value');
		        var count = GRID_ALCADAS.getMaxIndex();
		        for (var i = 1; i <= count; i++){
		            if (parseInt(seq) != i) {
		                 var trObj = document.getElementById('GRID_ALCADAS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		                 if (!trObj){
		                    continue;
		                 }
		                 var idAux = eval('document.form.idAlcada' + NumberUtil.zerosAEsquerda(i,5) + '.value');
		                 if (idAux == idAlcada) {
		                      alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
		                      eval('document.form.idAlcada' + seq + '.focus()');
		                      return false;
		                 }
		            }
		        }   */
		        return true;
		    }

		    function tratarAlcadas(){
		        try{
		            var count = GRID_ALCADAS.getMaxIndex();
		            var contadorAux = 0;
		            var objs = new Array();
		            for (var i = 1; i <= count; i++){
		                var trObj = document.getElementById('GRID_ALCADAS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		                if (!trObj){
		                    continue;
		                }

		                var alcadaDto = getAlcada(i);
		                if (parseInt(alcadaDto.idAlcada) > 0) {
		                    if  (!verificarAlcada(NumberUtil.zerosAEsquerda(i,5))) {
		                        return false;
		                    }
		                    if (StringUtils.isBlank(alcadaDto.idEmpregado)){
		                        alert(i18n_message("centroResultado.informeNomeEmpregado"));
		                        eval('document.form.idEmpregado' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
		                        return false;
		                    }
                            if (StringUtils.isBlank(alcadaDto.dataInicio)){
                                alert(i18n_message("avaliacao.fornecedor.dataInicio"));
                                eval('document.form.dataInicio' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
                                return false;
                            }
		                    objs[contadorAux] = alcadaDto;
		                    contadorAux = contadorAux + 1;
		                }else{
		                    alert(i18n_message("centroResultado.selecioneAlcada"));
		                    eval('document.form.idAlcada' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
		                    return false;
		                }
		            }
		            document.form.colAlcadas_Serialize.value = ObjectUtils.serializeObjects(objs);
		            return true;
		        }catch(e){
		        }
		    }

		    function gravar() {
		        if (!tratarAlcadas()){
		            return;
		        }

		        document.form.save();
		    }

		    var seqAtual = '';
		    function adicionarEmpregado(seq) {
		    	seqAtual = seq;
		        $("#POPUP_EMPREGADO").dialog("open");
		    }

		    function LOOKUP_EMPREGADO_select(id, desc) {
		    	eval('document.form.idEmpregado' + seqAtual + '.value = ' + id);
	            eval('document.form.nomeEmpregado' + seqAtual + '.value = "' + desc + '"');
		        $("#POPUP_EMPREGADO").dialog("close");
		    }
		</script>

	<%
		// Se for chamado por iframe deixa apenas a parte de cadastro da página
		if (iframe != null) {
	%>
		<style>
			div#main_container {
				margin: 10px 10px 10px 10px;
			}
</style>
	<%
		}
	%>
	</head>
	<body>
		<div id="wrapper">
		<%
			if (iframe == null) {
		%>
			<%@ include file="/include/menu_vertical.jsp" %>
		<%
			}
		%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix">
			<%
				if (iframe == null) {
			%>
				<%@ include file="/include/menu_horizontal.jsp" %>
			<%
				}
			%>
				<div class="flat_area grid_16">
					<h2>
						<fmt:message key="centroResultado" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1">
								<fmt:message key="centroResultado.cadastro" />
							</a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top">
								<fmt:message key="centroResultado.pesquisa" />
							</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action="${ctx}/pages/centroResultadoAntigo/centroResultadoAntigo">
									<div class="columns clearfix">
										<input type="hidden" id="idCentroResultado" name="idCentroResultado" />
										<input type="hidden" id="idCentroResultadoPai" name="idCentroResultadoPai" />
                                        <input type='hidden' name='colAlcadas_Serialize' />

										<div class="col_60">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="citcorpore.comum.nome" />
												</label>
												<div>
													<input type="text" id="nomeCentroResultado" name="nomeCentroResultado" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label>
													<fmt:message key="centroResultado.superior" />
												</label>
												<div>
													<div>
														<input type="text" id="nomeCentroResultadoPai" name="nomeCentroResultadoPai" onclick="consultarCentroResultadoPai();"
															readonly="readonly" style="width: 90% !important;" maxlength="70" size="70" />
														<img onclick="consultarCentroResultadoPai();" style="vertical-align: middle;"
															src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
													</div>
												</div>
											</fieldset>
										</div>
										<div class="col_60">
											<fieldset>
												<label class = "campoObrigatorio">
													<fmt:message key="centroResultado.codigo" />
												</label>
												<div>
													<input type="text" id="codigoCentroResultado" name="codigoCentroResultado" maxlength="25" class="Valid[Required] Description[centroResultado.codigo]" />
												</div>
											</fieldset>
										</div>
										<div class="col_20">
											<fieldset>
												<label class="campoObrigatorio" style="height: 30px;">
													<fmt:message key="centroResultado.permitirRequisicaoProduto" />
												</label>
												<div>
													<input type="radio" id="permiteRequisicaoProdutoSim" name="permiteRequisicaoProduto" value="S" checked="checked" /><fmt:message key="citcorpore.comum.sim" />
													<input type="radio" id="permiteRequisicaoProdutoNao" name="permiteRequisicaoProduto" value="N" /><fmt:message key="citcorpore.comum.nao" />
												</div>
											</fieldset>
										</div>
										<div class="col_20">
											<fieldset>
												<label class="campoObrigatorio" style="height: 30px;">
													<fmt:message key="centroResultado.situacao" />
												</label>
												<div>
													<input type="radio" id="situacaoAtivo" name="situacao" value="A" checked="checked" /><fmt:message key="citcorpore.comum.ativo" />
													<input type="radio" id="situacaoInativo" name="situacao" value="I" /><fmt:message key="citcorpore.comum.inativo" />
												</div>
											</fieldset>
										</div>
	                                    <div class="col_100">
	                                        <h2 class="section">
	                                             <fmt:message key="centroResultado.responsaveis" />
	                                        </h2>
	                                        <div id='divNovoCriterio' class="col_100">
	                                            <div class="col_66">
	                                                 <label>&nbsp;</label>
	                                            </div>
	                                            <div class="col_33">
	                                                 <label  style="cursor: pointer;" onclick='incluirAlcada();'>
	                                                     <img  src="${ctx}/imagens/add.png" /><span><b><fmt:message key="centroResultado.incluirResponsavel" /></b></span>
	                                                 </label>
	                                            </div>
	                                        </div>
                                         </div>
                                         <div class="col_100">
                                             <fieldset style='height:200px'>
                                               <div style='width:800px;height:190px;overflow:auto;'>
                                               <cit:grid id="GRID_ALCADAS" columnHeaders="centroResultado.cabecalhoGridResponsaveis" styleCells="linhaGrid">
                                                   <cit:column idGrid="GRID_ALCADAS" number="001">
                                                       <select name='idAlcada#SEQ#' id='idAlcada#SEQ#' style='border:none; width: 150px' onchange='verificarAlcada("#SEQ#");'>
                                                           <option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
                                                           <%
                                                           if (colAlcadas != null){
                                                               for (AlcadaDTO alcadaDto : colAlcadas) {
                                                                   out.println("<option value='" + alcadaDto.getIdAlcada() + "'>" +
                                                                   alcadaDto.getNomeAlcada() + "</option>");
                                                               }
                                                           }
                                                           %>
                                                       </select>
                                                   </cit:column>
                                                   <cit:column idGrid="GRID_ALCADAS" number="002">
                                                       <input type="hidden" id="idEmpregado#SEQ#" name="idEmpregado#SEQ#" />
                                                       <input onclick='adicionarEmpregado("#SEQ#");' type="text" name="nomeEmpregado#SEQ#" id="nomeEmpregado#SEQ#" />
                                                   </cit:column>
                                                   <cit:column idGrid="GRID_ALCADAS" number="003">
                                                       <input  type='text' id="dataInicio#SEQ#"  name="dataInicio#SEQ#" maxlength="10" size="10"  class="Valid[Data] Format[Data] datepicker" />
                                                   </cit:column>
                                                   <cit:column idGrid="GRID_ALCADAS" number="004">
                                                       <input  type='text' id="dataFim#SEQ#"  name="dataFim#SEQ#" maxlength="10" size="10"  class="Valid[Data] Format[Data] datepicker" />
                                                   </cit:column>
                                               </cit:grid>
                                               </div>
                                             </fieldset>
                                         </div>
									</div>
									<br />
									<br />
									<button type="button" name="btnGravar" class="light" onclick="gravar();">
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png" />
										<span>
											<fmt:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type="button" name="btnLimpar" class="light" onclick='document.form.clear();document.form.fireEvent("load");'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png" />
										<span>
											<fmt:message key="citcorpore.comum.limpar" />
										</span>
									</button>
									<button type="button" name="btnExcluir" class="light" onclick="excluir();">
										<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png" />
										<span>
											<fmt:message key="citcorpore.comum.excluir" />
										</span>
									</button>
									<button type="button" name="btnVisualizarHierarquia" class="light" onclick="visualizarHierarquiaCentrosResultado();">
										<img src="${ctx}/template_new/images/icons/small/grey/preview.png" />
										<span>
											<fmt:message key="centroResultado.visualizar" />
										</span>
									</button>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section">
								<fmt:message key="citcorpore.comum.pesquisa" />
								<form name="formPesquisa">
									<cit:findField formName="formPesquisa"
										lockupName="LOOKUP_CENTRORESULTADO"
										id="LOOKUP_CENTRORESULTADO"
										top="0"
										left="0"
										len="550"
										heigth="400"
										javascriptCode="true"
										htmlCode="true" />
								</form>
							</div>
						</div>
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</div>
				</div>
			</div>
			<!-- Fim da Pagina de Conteudo -->
		</div>
		<div id="POPUP_CENTRORESULTADOPAI" class="POPUP_CENTRORESULTADOPAI" title="<fmt:message key="citcorpore.comum.pesquisa" />" >
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<div align="right">
							<label style="cursor: pointer;">
								<fmt:message key="citcorpore.comum.pesquisa" />
							</label>
							<form id="formCentroResultadoPai" name="formCentroResultadoPai" method="post"
								action="${ctx}/pages/centroResultadoAntigo/centroResultadoAntigo">
								<cit:findField id="LOOKUP_CENTRORESULTADOPAI"
									formName="formCentroResultadoPai"
									lockupName="LOOKUP_CENTRORESULTADOPAI"
									top="0"
									left="0"
									len="550"
									heigth="400"
									javascriptCode="true"
									htmlCode="true" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_HIERARQUIA_CENTRORESULTADO" title="<fmt:message key="centroResultado.hierarquia" />">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px;">
						<div id="divApresentacaoHierarquiaCentroResultado" title="<fmt:message key="centroResultado.hierarquia" />"></div>
					</div>
				</div>
			</div>
		</div>
        <div id="POPUP_EMPREGADO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
            <div class="box grid_16 tabs">
                <div class="toggle_container">
                    <div id="tabs-2" class="block">
                        <div class="section">
                            <form name='formPesquisaEmpregado' style="width: 540px">
                                <input type="hidden" id="isNotificacao" name="isNotificacao">
                                <cit:findField formName='formPesquisaEmpregado'  lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="/include/footer.jsp"%>
	</body>
</html>
