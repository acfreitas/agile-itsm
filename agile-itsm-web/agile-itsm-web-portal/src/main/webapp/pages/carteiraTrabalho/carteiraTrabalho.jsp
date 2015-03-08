<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA"%>
<%@page import="br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho"%>

<!doctype html public "">
<html>
	<head>
		<%
			String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO, "");
			if (PAGE_CADADTRO_SOLICITACAOSERVICO == null){
			    PAGE_CADADTRO_SOLICITACAOSERVICO = "";
			}
			PAGE_CADADTRO_SOLICITACAOSERVICO = PAGE_CADADTRO_SOLICITACAOSERVICO.trim();
			if (PAGE_CADADTRO_SOLICITACAOSERVICO.trim().equalsIgnoreCase("")){
			    PAGE_CADADTRO_SOLICITACAOSERVICO = "	/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load";
			}

			String login = "";
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			if (usuario != null)
				login = usuario.getLogin();
		%>
		<%@include file="/include/security/security.jsp" %>
		<%@include file="/include/header.jsp"%>

		<title><fmt:message key="citcorpore.comum.title"/></title>

		<link rel="stylesheet" type="text/css" href="${ctx}/css/slick.grid.css"/>
		<link type="text/css" rel="stylesheet" href="${ctx}/css/layout-default-latest.css"/>

    <!-- theme is last so will override defaults --->
    <link type="text/css" rel="stylesheet" href="${ctx}/css/jquery.ui.all.css"/>



	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/themeroller/Aristo.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jQueryGantt/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/slick.grid.css"/>
	<link type="text/css" rel="stylesheet" class="include" href="${ctx}/pages/graficos/jquery.jqplot.min.css" />

	<style title="" type="text/css">

    .ui-layout-center ,
    .ui-layout-east ,
    .ui-layout-east .ui-layout-content {
        padding:        0;
        overflow:       hidden;
    }

    h2.loading {
        border:         0;
        font-size:      24px;
        font-weight:    normal;
        margin:         30% 0 0 40%;
    }
    .cell-title {
        font-weight: bold;
    }
    .cell-effort-driven {
        text-align: center;
    }

	.toggler-west-closed		{ background: url(${ctx}/imagens/go-rt-off.gif) no-repeat center; }
	.toggler-west-closed:hover	{ background: url(${ctx}/imagens/go-rt-on.gif)  no-repeat center; }
	.toggler-east-closed		{ background: url(${ctx}/imagens/go-lt-off.gif) no-repeat center; }
	.toggler-east-closed:hover	{ background: url(${ctx}/imagens/go-lt-on.gif)  no-repeat center; }

	span.button-close-west			{ background: url(${ctx}/imagens/go-lt-off.gif) no-repeat center; }
	span.button-close-west:hover	{ background: url(${ctx}/imagens/go-lt-on.gif)  no-repeat center; }
	span.button-close-east			{ background: url(${ctx}/imagens/go-rt-off.gif) no-repeat center; }
	span.button-close-east:hover	{ background: url(${ctx}/imagens/go-rt-on.gif)  no-repeat center; }

	.table {
		border-left:1px solid #ddd;
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
	/* CSS Barra de pesquisa e botões */
	.T-I {
		-webkit-border-radius: 3px;
		-moz-border-radius: 3px;
		border-radius: 3px;
		cursor: default;
		font-size: 11px;
		font-weight: bold;
		text-align: center;
		white-space: nowrap;
		/* margin-right: 16px; */
		height: 27px;
		line-height: 27px;
		min-width: 54px;
		outline: 0;
		padding: 0 8px !important;
		text-shadow: none !important;
	}
	.J-J5-Ji { 	position: relative;	display: inline-block;}
	.TI .T-I-ax7,.z0 .T-I-ax7,.G-atb .T-I-ax7 {
		background-color: transparent;
		background-image: linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -moz-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -o-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -webkit-gradient(linear, left top, left bottom, from(whiteSmoke),	to(#F1F1F1) );
		background-image: -webkit-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -ms-linear-gradient(top, whiteSmoke, #F1F1F1);
	}
	.TI .T-I-ax7,.z0 .T-I-ax7,.G-atb .T-I-ax7 {
		background-color: transparent;
		background-image: linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -moz-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -o-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -webkit-gradient(linear, left top, left bottom, from(whiteSmoke),	to(#F1F1F1) );
		background-image: -webkit-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -ms-linear-gradient(top, whiteSmoke, #F1F1F1);
	}
	.TI .T-I-ax7:focus,.z0 .T-I-ax7:focus,.G-atb .T-I-ax7:focus { border: 1px solid #4D90FE !important; }
	.asa { display: inline-block; }
	.ask { width: 1px; margin-right: -1px; }
	.J-J5-Ji { position: relative;	display: inline-block;	}
	.T-I-ax7 .T-I-J3 {	opacity: .55; }
	.T-I .T-I-J3 { margin-top: -3px; vertical-align: middle; }
	.asf {	width: 21px; height: 21px;	}
	.T-I-ax7 {
		background-color: whiteSmoke !important;
		background-image: -webkit-linear-gradient(top, whiteSmoke, #F1F1F1)!important;
		background-image: -moz-linear-gradient(top, whiteSmoke, #F1F1F1) !important;
		background-image: -ms-linear-gradient(top, whiteSmoke, #F1F1F1) !important;
		background-image: -o-linear-gradient(top, whiteSmoke, #F1F1F1) !important;
		background-image: linear-gradient(top, whiteSmoke, #F1F1F1) !important;
		color: #000;
		box-shadow: 0 !important;
		border: 1px solid gainsboro !important;
		border: 1px solid rgba(0, 0, 0, 0.1) !important;
	}
	.asf { background: url(../../imagens/sprite_black2.png) -63px -21px no-repeat; }
	.asb { background-image: url('../../imagens/k1_a31af7ac.png');	background-size: 294px 45px; }
	.gbqfi { background-position: -33px 0;	display: inline-block !important; height: 13px; margin: 7px 19px !important; width: 14px; }
	.gbqfb { background-color: hsl(217, 99%, 65%) !important;
		background-image: -webkit-gradient(linear, left top, left bottom, from(hsl(217, 99%, 65%)), to(hsl(217, 82%, 60%) ) ) !important;
		background-image: -webkit-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 82%, 60%) ) !important;
		background-image: -moz-linear-gradient(top, hsl(217, 99%, 65%), hsl(217, 82%, 60%) ) !important;
		background-image: -ms-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 82%, 60%) ) !important;
		background-image: -o-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 82%, 60%) ) !important;
		background-image: linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 82%, 60%) ) !important;
		filter: progid:DXImageTransform.Microsoft.gradient(startColorStr='#4d90fe', EndColorStr='#4787ed' ) !important;
		border: 1px solid hsl(217, 84%, 56%) !important;
		color: white !important;
		margin: 0 0 !important;
	}

	.gbqfb:hover {
		background-color: hsl(217, 80%, 56%) !important;
		background-image: -webkit-gradient(linear, left top, left bottom, from(hsl(217, 99%, 65%)), to(hsl(217, 80%, 56%) ) ) !important;
		background-image: -webkit-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 80%, 56%) ) !important;
		background-image: -moz-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 80%, 56%) ) !important;
		background-image: -ms-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 80%, 56%) ) !important;
		background-image: -o-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 80%, 56%) ) !important;
		background-image: linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 80%, 56%) ) !important;
	}
	.gbqfb:focus { border-color: hsl(221, 59%, 45%) !important; }
	.asf,.ar8,.asl,.ar9,.ase { 	width: 21px; height: 21px; }
	.T-I-ax7:focus,.z0 .T-I-ax7:focus,.G-atb .T-I-ax7:focus { border: 1px solid #4D90FE; }
	.T-I-hvr:hover { background-color: #F8F8F8 !important;
		background-image: -webkit-linear-gradient(top, #F8F8F8, #F1F1F1) !important;
		background-image: -moz-linear-gradient(top, #F8F8F8, #F1F1F1) !important;
		background-image: -ms-linear-gradient(top, #F8F8F8, #F1F1F1) !important;
		background-image: -o-linear-gradient(top, #F8F8F8, #F1F1F1) !important;
		background-image: linear-gradient(top, #F8F8F8, #F1F1F1) !important;
		border: 1px solid #C6C6C6 !important;
		color: #333 !important;
	}
	.TI .T-I-ax7,.z0 .T-I-ax7,.G-atb .T-I-ax7 {
		background-color: transparent;
		background-image: linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -moz-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -o-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -webkit-gradient(linear, left top, left bottom, from(whiteSmoke), to(#F1F1F1) );
		background-image: -webkit-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -ms-linear-gradient(top, whiteSmoke, #F1F1F1);
	}
	.TI .T-I-ax7,.z0 .T-I-ax7,.G-atb .T-I-ax7 { border: 1px solid rgba(0, 0, 0, 0.1); color: #000 !important; }
	.T-I-ax7.T-I-Zf-aw2 { border: 1px solid gainsboro; }
	.T-I-ax7:focus { border: 1px solid #4D90FE !important; color: #000 !important; }
	.T-I-ax7 { background-color: whiteSmoke;
		background-image: -webkit-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -moz-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -ms-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -o-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: linear-gradient(top, whiteSmoke, #F1F1F1);
		color: #000;
		border: 1px solid gainsboro;
		border: 1px solid rgba(0, 0, 0, 0.1);
	}
	.J-J5-Ji { position: relative; display: inline-block; }
	.J-J5-Ji { position: relative; display: -moz-inline-box; display: inline-block; }
	.G-asx { background: url(../../imagens/sprite2.png) no-repeat -160px -80px; width: 12px; height: 12px; margin-left: 3px; }
	.G-asx2 { background: url(../../imagens/sprite3.png) no-repeat -84px 50%; width: 7px; height: 10px; }
	.space { padding: 5px 5px; }


    </style>

    <script type="text/javascript" src="${ctx}/js/jquery-latest.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-ui-latest.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.layout-latest.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.layout.slideOffscreen-1.1.js"></script>
    <script type="text/javascript" src="${ctx}/js/debug.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/uniform/jquery.uniform.min.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/autogrow/jquery.autogrowtextarea.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/multiselect/js/ui.multiselect.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/selectbox/jquery.selectBox.min.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/timepicker/jquery.timepicker.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/colorpicker/js/colorpicker.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/tiptip/jquery.tipTip.minified.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/validation/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/uitotop/js/jquery.ui.totop.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/custom/ui.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/custom/forms.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/jquery/jquery.maskedinput.js"></script>
	<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
	<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
	<script type="text/javascript" src="${ctx}/pages/graficos/jquery.jqplot.min.js"></script>
	<script type="text/javascript" src="${ctx}/pages/graficos/plugins/jqplot.pieRenderer.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/json2.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.rule-1.0.1.1-min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.event.drag.custom.js"></script>
    <script type="text/javascript" src="${ctx}/js/slick.core.js"></script>
    <script type="text/javascript" src="${ctx}/js/slick.editors.js"></script>
    <script type="text/javascript" src="${ctx}/js/slick.grid.js"></script>
    <script type="text/javascript" src="${ctx}/js/CollectionUtils.js"></script>

    <script type="text/javascript" src="${ctx}/js/raphael.2.1.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/justgage.1.0.1.min.js"></script>

		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<script>var URL_INITIAL = '${ctx}/';</script>
	    <script type="text/javascript">
		addEvent(window, "load", load, false);

		popup2 = new PopupManager(1084, 1084, "${ctx}/pages/");
		popup2.titulo = "<fmt:message key='citcorpore.comum.pesquisarapida' />";

		function load() {
			window.setTimeout(atualizarListaTarefas, 1000);
		}

	      function GrupoQtde(){
	      		this.id = '';
	      		this.qtde = 0;
	      }

	    //somente numeros
	      jQuery.fn.numbersOnly = function(){
	        var $teclas = {8:'backspace',9:'tab',13:'enter',48:0,49:1,50:2,51:3,52:4,53:5,54:6,55:7,56:8,57:9};
	        $(this).keypress(function(e){
	          var keyCode = e.keyCode?e.keyCode:e.which?e.which:e.charCode;
	          if(keyCode in $teclas){
	            return true;
	          }else{
	            return false;
	          }
	        });
	        return $(this);
	      }



		  AddBotoesTarefa = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var solicitacaoDto = tarefaDto.solicitacaoDto;
		  	if (solicitacaoDto != null && solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>")
		  		return value;

			var str = "";
		  	if (tarefaDto.executar == 'S') {
		  	  if (tarefaDto.responsavelAtual != '<%=login%>') {
		      	str += "<img src='" + URL_INITIAL + "imagens/pegar.png' style='cursor:pointer;' title='<fmt:message key='gerenciaservico.capturartarefa' />' onclick='capturarTarefa(\""+tarefaDto.responsavelAtual+"\","+tarefaDto.idItemTrabalho+")'>&nbsp;";
		      }
		      //str += "<img src='" + URL_INITIAL + "imagens/pegar.png' style='cursor:pointer;' title='Capturar e editar tarefa' onclick='prepararExecucaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idSolicitacaoServico+",\"I\")'>&nbsp;";
		      str += "<img src='" + URL_INITIAL + "imagens/executarTarefa.png' style='cursor:pointer;' title='<fmt:message key='gerenciaservico.iniciarexecutartarefa' />' onclick='prepararExecucaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idSolicitacaoServico+",\"E\")'>&nbsp;";
			}
		  	if (tarefaDto.delegar == 'S')
		      str += "<img src='" + URL_INITIAL + "imagens/share.png' style='cursor:pointer;' title='<fmt:message key='gerenciaservico.delegarcompartilhartarefa' />' onclick='exibirDelegacaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idSolicitacaoServico+",\""+tarefaDto.elementoFluxoDto.documentacao+"\")'>&nbsp;";

	         return str + value;
		  };

		  AddLinkSolicitacao = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var solicitacaoDto = tarefaDto.solicitacaoDto;
		  	if (solicitacaoDto != null) {
		  	  var str = "";
		  	  //if (solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>") {
	  	  	      str += "<img src='" + URL_INITIAL + "imagens/viewCadastro.png' style='cursor:pointer;' title='<fmt:message key='gerenciaservico.visualizarcadastrosolicitacao' />' \n onclick='visualizarSolicitacao("+solicitacaoDto.idSolicitacaoServico+")'>";
			  	  if (tarefaDto.executar == 'S') {
			  	  	//str += "&nbsp;<img src='" + URL_INITIAL + "imagens/grupo.gif' style='cursor:pointer;' title='Direcionar atendimento' onclick='editarSolicitacao("+solicitacaoDto.idSolicitacaoServico+")'>";
			  	  	str += "&nbsp;<img src='" + URL_INITIAL + "imagens/reclassificar.gif' style='cursor:pointer;' title='<fmt:message key='gerenciaservico.reclassificarsolicitacao' /> ' onclick='reclassificarSolicitacao("+solicitacaoDto.idSolicitacaoServico+")'>";
			  	  }

		  	  str += "&nbsp;&nbsp;" + value;
	          return str;
		    }else
		      return "";
		  };

		  AddSituacao = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var solicitacaoDto = tarefaDto.solicitacaoDto;
		  	if (solicitacaoDto != null) {
		  	  var str = solicitacaoDto.descrSituacao;
		  	  //var str = value;

		  	  if (parseFloat(solicitacaoDto.atrasoSLA) > parseFloat("0,00") && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>")
		      	 str += " <img src='" + URL_INITIAL + "imagens/exclamation02.gif' height='15px' width='20px' title='<fmt:message key='gerenciaservico.atrasada' />'>";
		  	  if (solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>" && tarefaDto.suspender == 'S')
			  	 str = "<img src='" + URL_INITIAL + "imagens/stop.png' style='cursor:pointer;' title='<fmt:message key='gerenciaservico.suspendersolicitacao' /> ' onclick='prepararSuspensao("+solicitacaoDto.idSolicitacaoServico+")'>&nbsp;" + str;
		  	  if (solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>" && tarefaDto.reativar == 'S')
		  	  	 str = "<img src='" + URL_INITIAL + "imagens/play.png' style='cursor:pointer;' title='<fmt:message key='gerenciaservico.reativarsolicitacao' /> ' onclick='reativarSolicitacao("+solicitacaoDto.idSolicitacaoServico+")'>&nbsp;" + str;
		  	  str = " <img src='" + URL_INITIAL + "imagens/agenda.png' style='cursor:pointer;' title='<fmt:message key='gerenciaservico.agendaratividade' />  ' onclick='agendaAtividade("+solicitacaoDto.idSolicitacaoServico+")'>" + str;
	          return str;
		    }else
		      return "";
		  };

		  AddAtraso = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var solicitacaoDto = tarefaDto.solicitacaoDto;
		  	var result = "";
		  	if (solicitacaoDto != null && parseFloat(solicitacaoDto.atrasoSLA) > parseFloat("0,00") && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>")
		      	result = '<font color="red">' + solicitacaoDto.atrasoSLAStr + '</font>';
	        return result;
		  };

		  AddSelTarefa = function(row, cell, value, columnDef, dataContext) {
	        return "<input type='radio' name='selTarefa' value='S'/>";
		  };

		  AddFarol = function(row, cell, value, columnDef, dataContext) {
			  	var tarefaDto = arrayTarefas[row];
			  	var solicitacaoDto = tarefaDto.solicitacaoDto;
			  	if (solicitacaoDto.atrasada == 'true' && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>"){
					return "<img src='${ctx}/imagens/bolavermelha.png' title='<fmt:message key='gerenciaservico.atrasada' />'/>";
				}else{
					if (solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>"){
						return "<img src='${ctx}/imagens/bolacinza2.png' title='<fmt:message key='citcorpore.comum.suspensa'/>'/>";
					}
					if (solicitacaoDto.falta1Hora == 'true'){
						return "<img src='${ctx}/imagens/bolaamarela.png' title='<fmt:message key='solicitacaoServico.menos1hora.desc'/>'/>";
					}else{
						return "<img src='${ctx}/imagens/bolaverde.png'/>";
					}
				}
		  };

		  AddBotaoMudancaSLA = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	        if (solicitacaoDto != null && solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>")
		  		return "";

	        if (solicitacaoDto != null && solicitacaoDto.situacaoSLA == "<%=SituacaoSLA.N%>")
	            return "Não iniciado";

	        if (solicitacaoDto != null && solicitacaoDto.situacaoSLA == "<%=SituacaoSLA.S%>")
	            return "Suspenso";

	        var solicitacaoDto = tarefaDto.solicitacaoDto;
		  	var aux = value;
		  	if (solicitacaoDto.slaACombinar == 'S'){
		  		aux = 'A comb.';
		  	}
		    return aux;
		  };

		  AddImgPrioridade = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var solicitacaoDto = tarefaDto.solicitacaoDto;
		  	if (solicitacaoDto.prioridade == '1'){
		    	return solicitacaoDto.prioridade + " <img src='" + URL_INITIAL + "imagens/b.gif' style='cursor:pointer;' title='<fmt:message key='gerenciaservico.prioridadealta' />'/> ";
		  	}else{
		  		return solicitacaoDto.prioridade;
		  	}
		  };

		  AddImgSolicitante = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var solicitacaoDto = tarefaDto.solicitacaoDto;
		  	if ((solicitacaoDto.emailcontato != '' && solicitacaoDto.emailcontato != undefined && solicitacaoDto.emailcontato != null)
		  	||  (solicitacaoDto.telefonecontato != '' && solicitacaoDto.telefonecontato != undefined && solicitacaoDto.telefonecontato != null)
		  	||  (solicitacaoDto.localizacaofisica != '' && solicitacaoDto.localizacaofisica != undefined && solicitacaoDto.localizacaofisica != null)){
		  		var strAux = '';
		  		strAux += '' + solicitacaoDto.solicitanteUnidade;
		  		if (solicitacaoDto.telefonecontato != '' && solicitacaoDto.telefonecontato != undefined && solicitacaoDto.telefonecontato != null){
		  			strAux += '\n<fmt:message key="citcorpore.comum.telefone" />: ' + solicitacaoDto.telefonecontato;
		  		}
		  		if (solicitacaoDto.emailcontato != '' && solicitacaoDto.emailcontato != undefined && solicitacaoDto.emailcontato != null){
		  			strAux += '\n<fmt:message key="citcorpore.comum.email" />: ' + solicitacaoDto.emailcontato;
		  		}
		  		if (solicitacaoDto.localizacaofisica != '' && solicitacaoDto.localizacaofisica != undefined && solicitacaoDto.localizacaofisica != null){
		  			strAux += '\n<fmt:message key="citcorpore.comum.localizacao" />: ' + solicitacaoDto.localizacaofisica;
		  		}
		    	return " <img src='" + URL_INITIAL + "imagens/cracha.png' style='cursor:pointer;' title='" + strAux + "'/> " + value;
		  	}else{
		  		return value;
		  	}
		  };

	    var	arrayTarefas   = []
	    ,   gridTarefa     = {}
	    ,   tarefas		   = []
	    ,   colunasTarefa = [
	           	{ id: "idSolicitacaoServico", name: "<fmt:message key='citcorpore.comum.numero' />", field: "idSolicitacaoServico"       	, width: 120,	formatter: AddLinkSolicitacao, resizable:true, sortable : true,  headerCssClass : "campoOrdenavel", toolTip:"<fmt:message key='citcorpore.comum.ordenar' />"	   }
	       	,   { id: "farol"			    , name: " "		, field: "farol"	       		, width: 20  , formatter: AddFarol }
	        ,   { id: "contrato"			, name: "<fmt:message key='contrato.contrato' />"		, field: "contrato"	       		, width: 100  , sortable : true , headerCssClass : "campoOrdenavel", toolTip:"<fmt:message key='citcorpore.comum.ordenar' />" }
		    ,   { id: "responsavel"			, name: "<fmt:message key='gerenciaservico.criado_por' />", field: "responsavel"	       	, width: 100 , sortable : true  , headerCssClass : "campoOrdenavel", toolTip:"<fmt:message key='citcorpore.comum.ordenar' />"	  }
	        ,   { id: "servico"				, name: "<fmt:message key='servico.servico' />"		, field: "servico"	       		, width: 150  , sortable : true , headerCssClass : "campoOrdenavel", toolTip:"<fmt:message key='citcorpore.comum.ordenar' />"	  }
	        ,   { id: "solicitanteUnidade"	, name: "<fmt:message key='solicitacaoServico.solicitante' />", field: "solicitanteUnidade"	       	, width: 180,    formatter: AddImgSolicitante, resizable:true , sortable : true  , headerCssClass : "campoOrdenavel", toolTip:"<fmt:message key='citcorpore.comum.ordenar' />"   }
	        ,   { id: "dataHoraSolicitacao"	, name: "<fmt:message key='solicitacaoServico.dataHoraCriacao' />", field: "dataHoraSolicitacao"	, width: 110  , sortable : true , headerCssClass : "campoOrdenavel", toolTip:"<fmt:message key='citcorpore.comum.ordenar' />" }
	        ,   { id: "prioridade"			, name: "<fmt:message key='gerenciaservico.pri' />", field: "prioridade"	       	, width: 50,    formatter: AddImgPrioridade, resizable:true , sortable : true , headerCssClass : "campoOrdenavel", toolTip:"<fmt:message key='citcorpore.comum.ordenar' />"   }
	        ,   { id: "sla"					, name: "<fmt:message key='gerenciaservico.sla' />", field: "sla"					, width: 85,    formatter: AddBotaoMudancaSLA, resizable:true   }
	        ,   { id: "dataHoraLimite"		, name: "<fmt:message key='solicitacaoServico.prazoLimite' />"	, field: "dataHoraLimite"		, width: 110 , sortable : true  , headerCssClass : "campoOrdenavel", toolTip:"<fmt:message key='citcorpore.comum.ordenar' />"  }
	        ,   { id: "atrasoSLA"       	, name: "<fmt:message key='tarefa.atraso' />", field: "atrasoSLA"           	, width: 70,   	formatter: AddAtraso, resizable:false 	, sortable : true , headerCssClass : "campoOrdenavel", toolTip:"<fmt:message key='citcorpore.comum.ordenar' />" 	}
	        ,   { id: "situacao"       		, name: "<fmt:message key='solicitacaoServico.situacao' />", field: "situacao"           	, width: 150,	formatter: AddSituacao, resizable:false , sortable : true , headerCssClass : "campoOrdenavel", toolTip:"<fmt:message key='citcorpore.comum.ordenar' />" 	}
	        ,   { id: "descricao"			, name: "<fmt:message key='tarefa.tarefa_atual' />", field: "descricao"    	 	, width: 250,   formatter: AddBotoesTarefa }
	        ,   { id: "grupoAtual"			, name: "<fmt:message key='citcorpore.comum.grupoExecutor' />", field: "grupoAtual"     		, width: 120, sortable : true, headerCssClass : "campoOrdenavel"   }
	        ,   { id: "responsavelAtual"	, name: "<fmt:message key='tarefa.responsavelatual' />", field: "responsavelAtual"  , width: 120    }
	        ,   { id: "compartilhamento"	, name: "<fmt:message key='tarefa.compartilhadacom' />", field: "compartilhamento"  , width: 120    }
	        ]
	    ,   gridOptions = {
	    		editable:               false,
		        asyncEditorLoading:     false,
		        enableAddRow:           false,
		        enableCellNavigation: true,
			    enableColumnReorder: false,
			    multiColumnSort: true
	        }
	    ;

		var dadosGrafico;
		var dadosGrafico2;
		var dadosGrafico4;
		var dadosGerais;
		var scriptTemposSLA = '';
		var temporizador;
		exibirTarefas = function(json_tarefas) {
			var tarefas = [];
			//json_tarefas = '';
			//$("#ajaxX").text(json_tarefas);
			var qtdeAtrasadas = 0;
			var qtdeSuspensas = 0;
			var qtdeEmAndamento = 0;
			var qtdePri1 = 0;
			var qtdePri2 = 0;
			var qtdePri3 = 0;
			var qtdePri4 = 0;
			var qtdePri5 = 0;
			var qtdeItens = 0;
			var colGrupoSol = new HashMap();
			scriptTemposSLA = "";

			//arrayTarefas = JSON.parse(json_tarefas);
			arrayTarefas = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(json_tarefas);
		    for(var i = 0; i < arrayTarefas.length; i++){
	            var tarefaDto = arrayTarefas[i];
	            tarefaDto.solicitacaoDto = ObjectUtils.deserializeObject(tarefaDto.solicitacao_serialize);
	            tarefaDto.elementoFluxoDto = ObjectUtils.deserializeObject(tarefaDto.elementoFluxo_serialize);
		    }


			var strTableTemposSLA = '';
			strTableTemposSLA += "<img width='20' height='20' ";
			strTableTemposSLA += "alt='"+  i18n_message('ativaotemporizador')+"' id='imgAtivaTimer' style='opacity:0.5;display:none' ";
			strTableTemposSLA += "title='"+ i18n_message('citcorpore.comum.ativadestemporizador') +"' ";
			strTableTemposSLA += "src='${ctx}/template_new/images/cronometro.png'/>";
			strTableTemposSLA += "<table class=\"table\" cellpadding=\"0\" cellspacing=\"0\">";
			strTableTemposSLA = strTableTemposSLA + "<tr><td><b><fmt:message key='gerenciaservico.slasandamento' /></b></td></tr>";
			//inicializarTemporizador();
			for(var i = 0; i < arrayTarefas.length; i++){
				var idSolicitacaoServico = "";
				var contrato = "";
				var responsavel = "";
				var servico = "";
				var solicitante = "";
				var prioridade = "";
				var situacao = "";
				var sla = "";
				var dataHoraSolicitacao = "";
				var dataHoraLimite = "";
				var grupoAtual = "";
				var farolAux = "";

				var tarefaDto = arrayTarefas[i];
				var solicitacaoDto = tarefaDto.solicitacaoDto;
				if (solicitacaoDto != null) {
					if (solicitacaoDto.prioridade == '1'){
						qtdePri1++;
					}
					if (solicitacaoDto.prioridade == '2'){
						qtdePri2++;
					}
					if (solicitacaoDto.prioridade == '3'){
						qtdePri3++;
					}
					if (solicitacaoDto.prioridade == '4'){
						qtdePri4++;
					}
					if (solicitacaoDto.prioridade == '5'){
						qtdePri5++;
					}
					var grupoNome = solicitacaoDto.grupoAtual;
					if (grupoNome == null){
						grupoNome = '-- '+ i18n_message("citcorpore.comum.aclassificar")+ '--';
					}
					var auxGrp = colGrupoSol.get(grupoNome);
					if (auxGrp != undefined){
						auxGrp.qtde++;
					}else{
						var grupoQtde = new GrupoQtde();
						grupoQtde.id = grupoNome;
						grupoQtde.qtde = 1;
						colGrupoSol.set(grupoNome, grupoQtde);
					}

					idSolicitacaoServico = ""+solicitacaoDto.idSolicitacaoServico;
					responsavel = ""+trocaBarra(solicitacaoDto.responsavel);
					contrato = ""+trocaBarra(solicitacaoDto.contrato);
					servico = ""+trocaBarra(solicitacaoDto.servico);
					solicitante = ""+trocaBarra(solicitacaoDto.solicitanteUnidade);
					if (solicitacaoDto.prazoHH < 10)
						sla = "0";
					sla += solicitacaoDto.prazoHH + ":";
					if (solicitacaoDto.prazoMM < 10)
						sla += "0";
					sla += solicitacaoDto.prazoMM;
					prioridade = ""+solicitacaoDto.prioridade;
					dataHoraSolicitacao = solicitacaoDto.dataHoraSolicitacaoStr;
					if (solicitacaoDto.situacaoSLA == "<%=SituacaoSLA.A%>") {
						dataHoraLimite = solicitacaoDto.dataHoraLimiteStr;
					}
					grupoAtual = trocaBarra(solicitacaoDto.grupoAtual);

					if (parseFloat(solicitacaoDto.atrasoSLA) > parseFloat("0,00") && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>"){
						qtdeAtrasadas++;
					}else if (solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>" && tarefaDto.reativar == 'S'){
						qtdeSuspensas++;
					}else {
						qtdeEmAndamento++;
						if (qtdeItens < 15){
							if (solicitacaoDto.slaACombinar && solicitacaoDto.slaACombinar != 'S' && solicitacaoDto.situacaoSLA == 'A'){
								scriptTemposSLA += "temporizador.addOuvinte(new Solicitacao('tempoRestante" + solicitacaoDto.idSolicitacaoServico + "', " + "'barraProgresso" + solicitacaoDto.idSolicitacaoServico + "', "
								    + "'" + solicitacaoDto.dataHoraInicioSLAStr + "', '" + solicitacaoDto.dataHoraLimiteToString + "'));";
								strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'></label>";
								strTableTemposSLA = strTableTemposSLA + "<div id='barraProgresso" + solicitacaoDto.idSolicitacaoServico + "'></div></td></tr>";
							}else if (solicitacaoDto.slaACombinar && solicitacaoDto.slaACombinar == 'S') {
								strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'><fmt:message key='citcorpore.comum.acombinar' /> </font></label>";
							}else if (solicitacaoDto.situacaoSLA == 'N'){
	                            strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'><fmt:message key='citcorpore.comum.naoIniciado' /> </font></label>";
							}else if (solicitacaoDto.situacaoSLA == 'S'){
	                            strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'><fmt:message key='citcorpore.comum.suspenso' /> </font></label>";
							}
						}
						qtdeItens++;
					}
				}
		        tarefas[i] = {
				        		 iniciar:			tarefaDto.executar
				        		,executar:			tarefaDto.executar
				        		,delegar:			tarefaDto.delegar
		        				,idSolicitacaoServico:		idSolicitacaoServico
		        			 	,contrato: 			contrato
		        			 	,responsavel: 		responsavel
		        			 	,servico: 			servico
		        			 	,solicitanteUnidade: 		solicitante
		        			 	,prioridade: 		prioridade
		        			 	,dataHoraSolicitacao: dataHoraSolicitacao
		        			 	,descricao: 		trocaBarra(tarefaDto.elementoFluxoDto.documentacao)
				        		,status:	 		""
					        	,atraso:			solicitacaoDto.atrasoSLA
				        		,sla:	 			sla
				        		,atrasoSLA:	 		""
		        			 	,dataHoraLimite: 	dataHoraLimite
		        			 	,responsavelAtual:  tarefaDto.responsavelAtual
		        			 	,compartilhamento:  tarefaDto.compartilhamento
		        			 	,grupoAtual:  grupoAtual
		        			}
			}
			strTableTemposSLA = strTableTemposSLA + '</table>';
			if (qtdeAtrasadas > 0 || qtdeSuspensas > 0 || qtdeEmAndamento > 0){
				var info = '';
				if (qtdeAtrasadas > 0){
					info += ' <font color="red"><b>' + qtdeAtrasadas + '</b> <fmt:message key="solicitacaoServico.solicitacoes_incidentes_atrasado" /></font><br>';
				}
				if (qtdeSuspensas > 0){
					info += ' <b>' + qtdeSuspensas + '</b> <fmt:message key="solicitacaoServico.solicitacoes_incidentes_suspenso" />';
				}
				info = ' <fmt:message key="solicitacaoServico.existem" /><br>' + info + '<br><div id="divTemposSLA" style="height:130px; overflow:auto; border: 2px solid #999999">' + strTableTemposSLA + '</div>';
				//if (document.getElementById('fraSolicitacaoServico').src == "about:blank"){
					//info = '<table cellpadding="0" cellspacing="0"><tr><td style="width:15px">&nbsp;</td><td style="vertical-align: top;">' + info + '</td><td><div id="divGrafico" ></div></td><td><div id="divGrafico2" ></div></td><td><div id="divGrafico3" ></div></td><td><div id="divGrafico4" ></div></td></tr></table>';
					//document.getElementById('tdAvisosSol').innerHTML = info;
					dadosGrafico = [['<fmt:message key="gerenciaservico.emandamento" />',qtdeEmAndamento],['<fmt:message key="gerenciaservico.suspensas" />',qtdeSuspensas],['<fmt:message key="gerenciaservico.atrasadas" />',qtdeAtrasadas]];
					dadosGrafico2 = [[' 1 ',qtdePri1],[' 2 ',qtdePri2],[' 3 ',qtdePri3],[' 4 ',qtdePri4],[' 5 ',qtdePri5]];
					window.setTimeout(atualizaGrafico, 1000);
					window.setTimeout(atualizaGrafico2, 1000);

					var colArray = colGrupoSol.toArray();
					dadosGerais = new Array();
					if (colArray){
						for (var iAux = 0; iAux < colArray.length; iAux++){
							dadosGerais[iAux] = [colArray[iAux].id, colArray[iAux].qtde];
						}
					}
					window.setTimeout(atualizaGrafico3, 1000);
					document.formPesquisa.quantidadeAtrasadas.value = qtdeAtrasadas;
					document.formPesquisa.quantidadeTotal.value = (qtdeEmAndamento + qtdeSuspensas + qtdeAtrasadas);
					window.setTimeout(atualizaGrafico4, 1000);
				//}
			}
	        //gridTarefa = new Slick.Grid( myLayout.contents.south,  tarefas,  colunasTarefa, gridOptions );
	        document.getElementById("divConteudoLista").innerHTML = "<div id=\"divConteudoListaInterior\" style=\"height: 100%; width: 100%\"></div>";
	        gridTarefa = new Slick.Grid($("#divConteudoListaInterior"), tarefas,  colunasTarefa, gridOptions );

	        gridTarefa.onSort.subscribe(function (e, args) {
				var cols = args.sortCols;
				var asc = cols[0].sortAsc;
				if (document.formPesquisa.ordenacaoAsc.value != ''){
					asc = document.formPesquisa.ordenacaoAsc.value;
				}
				document.formPesquisa.nomeCampoOrdenacao.value = cols[0].sortCol.id;
				document.formPesquisa.ordenacaoAsc.value = "" + asc;

				/*
				* Codigo Comentado pois compromete a performance do sistema  - RETORNADO POR EMAURI - 18/05*/
				document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'><fmt:message key='citcorpore.comum.aguardecarregando' /></div>"
				document.formPesquisa.fireEvent("exibeTarefas");
				//

		  	 });
		};

		function trocaBarra(txt){
			var x = new String(txt);
			x = x.replace(/{{BARRA}}/g,'\\');
			return x;
		}

		function setinha(){
			/*var headers = document.getElementsByClassName("slick-header");
			for(var i = 0; i < headers.length; i++){
				$("#" + headers[i].id).addClass(eval(document.formPesquisa.ordenacaoAsc.value) ? "slick-sort-indicator-desc" : "slick-sort-indicator-asc");
			}*/
			window.setTimeout(orgColunsSort, 500);
		}
		function orgColunsSort(){
			try{
				gridTarefa.setSortColumn(document.formPesquisa.nomeCampoOrdenacao.value, eval(document.formPesquisa.ordenacaoAsc.value));
			}catch(e){}
		}

		/*
		 * Funções de ordenação
		 */

		function encontraNovaPosicao(id, lista, indiceInicial){
			for(var i = indiceInicial; i < lista.length; i++){
				if(lista[i].idSolicitacaoServico == id){
					return i;
				}
			}
			return null;
		}

		function alternaPosicaoArray(indiceOrigem, indiceDestino, lista){
			var aux = lista[indiceDestino];

			lista[indiceDestino] = lista[indiceOrigem];
			lista[indiceOrigem] = aux;
		}

		//////////////

		function inicializarTemporizador(){
			if(temporizador == null){
				temporizador = new Temporizador("imgAtivaTimer");
			} else {
				temporizador = null;
				try{
					temporizador.listaTimersAtivos = [];
				}catch(e){}
				try{
					temporizador.listaTimersAtivos.length = 0;
				}catch(e){}
				temporizador = new Temporizador("imgAtivaTimer");
			}
		}

		function atualizaGrafico(){

		}
		function atualizaGrafico2(){

		}
		function atualizaGrafico3(){

		}
		function atualizaGrafico4(){

		}

		atualizarListaTarefas = function() {
			document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'><fmt:message key='citcorpore.comum.aguardecarregando' /></div>";
			document.form.numeroContratoSel.value = document.formPesquisa.numeroContratoSel.value;
			document.form.idSolicitacaoSel.value = document.formPesquisa.idSolicitacaoSel.value;
			document.form.descricaoSolicitacao.value = document.formPesquisa.descricaoSolicitacao.value;
			document.form.responsavelAtual.value = document.formPesquisa.responsavelAtual.value;
			document.form.idTipoDemandaServico.value = document.formPesquisa.idTipoDemandaServico.value;
			document.form.nomeCampoOrdenacao.value = document.formPesquisa.nomeCampoOrdenacao.value;
			document.form.ordenacaoAsc.value = document.formPesquisa.ordenacaoAsc.value == "false" ? "true" : "false";
			document.form.grupoAtual.value = document.formPesquisa.grupoAtual.value;
			document.form.solicitanteUnidade.value = document.formPesquisa.solicitanteUnidade.value;
			document.form.fireEvent('exibeTarefas')
		}

		var gauge = null;
			$(function() {
				$("#POPUP_EDITAR").dialog({
					autoOpen : false,
					width : 1000,
					height : 700,
					modal : true,
					buttons: {
						"Fechar": function() {
							$( this ).dialog( "close" );
							window.location = URL_INITIAL + '/pages/carteiraTrabalho/carteiraTrabalho.load';
						}
					},
					close: function() {
						reload();
						//document.formCarteira.clear();
				    }
				});

				$("#POPUP_SOL_SERV").dialog({
					autoOpen : false,
					width : 1200,
					height : 800,
					modal : true
				});

				$( "#POPUP_VISAO" ).dialog({
					title: '<fmt:message key="citcorpore.comum.visao" />',
					width: 900,
					height: 600,
					modal: true,
					autoOpen: false,
					resizable: false,
					show: "fade",
					hide: "fade"
					});
			});

			function reload(){
				window.location = URL_INITIAL + '/pages/carteiraTrabalho/carteiraTrabalho.load';
			}

			function editaAtividade(tarefa) {
				document.formCarteira.idRecursoTarefaLinBaseProj.value = tarefa.idRecursoTarefaLinBaseProj;
				document.getElementById('divInfoTimeSheet').innerHTML = 'Aguarde...';
				document.formCarteira.fireEvent('listTimeSheet');
				$("#POPUP_EDITAR").dialog("open");
			}

			function save(){
				if (document.formCarteira.qtdeHoras.value == '' || document.formCarteira.qtdeHoras.value == '0,00'){
					alert('Informe a quantidade de horas!');
					return;
				}
				if (document.formCarteira.percExecutado.value == '' || document.formCarteira.percExecutado.value == '0,00'){
					alert('Informe o percentual executado!');
					return;
				}
				document.formCarteira.save();
			}

			abrePopupPesquisa = function( ) {
				$( "#popupCadastroRapido" ).dialog({
					title: '<fmt:message key="citcorpore.comum.cadastrorapido" />',
					width: 1290,
					height: 570,
					modal: true,
					autoOpen: false,
					resizable: false,
					show: "fade",
					hide: "fade"

					});
				document.getElementById('popupCadastroRapido').style.overflow = "hidden";

				popup2.abrePopup('pesquisaSolicitacoesServicos','()');
			}

			prepararExecucaoTarefa = function(idTarefa,idSolicitacao,acao) {
				document.getElementById('fraSolicitacaoServico').src = "about:blank";
				document.form.idTarefa.value = idTarefa;
				document.form.acaoFluxo.value = acao;
				document.form.fireEvent('preparaExecucaoTarefa');
				$("#POPUP_SOL_SERV").dialog("open");
			};

			exibirVisao = function(titulo,idVisao,idFluxo,idTarefa,acao){
				document.getElementById('fraSolicitacaoServico').src = "about:blank";
				document.getElementById('fraSolicitacaoServico').src = "${ctx}/pages/dinamicViews/dinamicViews.load?modoExibicao=J&idVisao="+idVisao+"&idFluxo="+idFluxo+"&idTarefa="+idTarefa+"&acaoFluxo="+acao;
				$("#POPUP_SOL_SERV").dialog("open");
			};

			exibirUrl = function(titulo, url){
				document.getElementById('fraSolicitacaoServico').src = "about:blank";
				document.getElementById('fraSolicitacaoServico').src = "${ctx}/"+url;
				$("#POPUP_SOL_SERV").dialog("open");
			};

			fecharSolicitacao = function(){
				document.getElementById('fraSolicitacaoServico').src = "about:blank";
				$("#POPUP_SOL_SERV").dialog("close");
				document.form.fireEvent('exibeTarefas');
			};

			visualizarSolicitacao = function(idSolicitacao) {
				document.getElementById('fraSolicitacaoServico').src = "about:blank";
				document.getElementById('fraSolicitacaoServico').src = "${ctx}<%=PAGE_CADADTRO_SOLICITACAOSERVICO%>?idSolicitacaoServico="+idSolicitacao+"&escalar=N&alterarSituacao=N&editar=N";
				$("#POPUP_SOL_SERV").dialog("open");
			};

			reclassificarSolicitacao = function(idSolicitacao) {
				document.getElementById('fraSolicitacaoServico').src = "about:blank";
				document.getElementById('fraSolicitacaoServico').src = "${ctx}<%=PAGE_CADADTRO_SOLICITACAOSERVICO%>?idSolicitacaoServico="+idSolicitacao+"&reclassificar=S";
				$("#POPUP_SOL_SERV").dialog("open");
			};

			prepararSuspensao = function(idSolicitacao) {
				document.getElementById('fraVisao').src = "about:blank";
				document.getElementById('fraVisao').src = "${ctx}/pages/suspensaoSolicitacao/suspensaoSolicitacao.load?idSolicitacaoServico="+idSolicitacao;
				$( "#POPUP_VISAO" ).dialog({ height: 500 });
				$( "#POPUP_VISAO" ).dialog({ title: '<fmt:message key="gerenciaservico.suspendersolicitacao" />' });
				$( "#POPUP_VISAO" ).dialog( 'open' );
			};

			reativarSolicitacao = function(idSolicitacao) {
				if (!confirm(i18n_message("gerencia.confirm.reativacaoSolicitacao")))
					return;
				document.form.idSolicitacao.value = idSolicitacao;
				document.form.fireEvent('reativaSolicitacao');
			};

			agendaAtividade = function(idSolicitacao) {
				document.getElementById('fraVisao').src = "about:blank";
				document.getElementById('fraVisao').src = "${ctx}/pages/agendarAtividade/agendarAtividade.load?idSolicitacaoServico="+idSolicitacao;
				$( "#POPUP_VISAO" ).dialog({ height: 600 });
				$( "#POPUP_VISAO" ).dialog({ title: '<fmt:message key="gerenciaservico.agendaratividade" />' });
				$( "#POPUP_VISAO" ).dialog( 'open' );
			};

			exibirDelegacaoTarefa = function(idTarefa,idSolicitacao,nomeTarefa) {
				document.getElementById('fraVisao').src = "about:blank";
				document.getElementById('fraVisao').src = "${ctx}/pages/delegacaoTarefa/delegacaoTarefa.load?idSolicitacaoServico="+idSolicitacao+"&idTarefa="+idTarefa+"&nomeTarefa="+nomeTarefa;
				$( "#POPUP_VISAO" ).dialog({ height: 400 });
				$( "#POPUP_VISAO" ).dialog({ title: '<fmt:message key="gerenciaservico.delegarcompartilhartarefa" />' });
				$( "#POPUP_VISAO" ).dialog( 'open' );
			};

			capturarTarefa = function(responsavelAtual, idTarefa) {
				var msg = "";
				if (responsavelAtual == '')
					msg = i18n_message("gerencia.confirm.atribuicaotarefa") + " '<%=login%>'  ?";
				else
					msg = i18n_message("gerencia.confirm.atribuicaotarefa_1") +" " + responsavelAtual + " " + i18n_message("gerencia.confirm.atribuicaotarefa_2")  +" '<%=login%>' "+ i18n_message("gerencia.confirm.atribuicaotarefa_3");

				if (!confirm(msg))
					return;
				document.form.idTarefa.value = idTarefa;
				document.form.fireEvent('capturaTarefa');
			};

			indicaGauge = function(valor){
				if (valor != '0'){
			        gauge = new JustGage({
			            id: "divInfoGauge",
			            value: valor,
			            min: 0,
			            max: 100,
			            title: "",
			            label: ""
			          });
				}
			};

		</script>

	</head>
	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="carteiraTrabalho.carteiraTrabalho"/></h2>
				</div>
				<div id='divX'>
				</div>
				<div class="box grid_16 tabs">
					<table style="width: 100%!important;">
						<tr>
							<td>
								<div class="gantt" style="width: 800px; margin-left: 5px;"></div>
							</td>
							<td style='text-align: top; vertical-align: top;'>
								<div id="atrasadas" style="width: 400px; text-align: top;"></div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<!--  Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 15:54 - ID Citsmart: 120948 -
									* Motivo/Comentário: Retirado layout antigo gerenciamento de servicos/ Inserido novo layout -->
								<div style="z-index: 999 !important; width: 100%">

								    <iframe width="99%" height="800" src="${ctx}/pages/gerenciamentoServicos/gerenciamentoServicos.load?iframe=true"></iframe>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div id="POPUP_EDITAR" title="Editar" style="display: none">
			<form name="formCarteira" action='${ctx}/pages/carteiraTrabalho/carteiraTrabalho'>
			<input type='hidden' name='idRecursoTarefaLinBaseProj' id='idRecursoTarefaLinBaseProj'/>
			<div id='divInfoGauge' class="col_25" style="max-height: 200px">
			</div>
			<div class="col_75">
				<fieldset>
					<label style="margin-top: 5px;"><fmt:message key="carteiraTrabalho.informacoesTarefa" /></label>
						<div id='divInfoTarefa'>
						</div>
				</fieldset>
			</div>
			<div class="col_100">
			    <div class="col_25">
					<fieldset>
						<label style="margin-top: 5px;" class="campoObrigatorio"><fmt:message key="carteiraTrabalho.horaInicio" /></label>
							<div>
							  	<input type='text' name='hora' id='hora' size='5' maxlength="5" class='Format[Hora] Valid[Required,Hora] Description[carteiraTrabalho.horaInicio]'/>
							</div>
					</fieldset>
				</div>
				<div class="col_25">
					<fieldset>
						<label style="margin-top: 5px;" class="campoObrigatorio"><fmt:message key="citcorpore.comum.data" /></label>
							<div>
							  	<input type='text' name='data' id='data' size='10' maxlength="10" class='Format[Date] Valid[Required,Date] datepicker Description[citcorpore.comum.data]'/>
							</div>
					</fieldset>
				</div>

				<div class="col_25">
					<fieldset>
						<label style="margin-top: 5px;" class="campoObrigatorio"><fmt:message key="carteiraTrabalho.qtdeHoras" /></label>
							<div>
							  	<input type='text' name='qtdeHoras' id='qtdeHoras' size='6' maxlength="6" class='Format[Moeda] Valid[Required] Description[carteiraTrabalho.qtdeHoras]'/>
							</div>
					</fieldset>
				</div>
				<div class="col_25">
					<fieldset>
					<label style="margin-top: 5px;" class="campoObrigatorio"><fmt:message key="carteiraTrabalho.percentualExecutado" /></label>
							<div>
							  	<input type='text' name='percExecutado' id='percExecutado' size='6' maxlength="6" class='Format[Moeda] Valid[Required] Description[carteiraTrabalho.percentualExecutado]'/>
							</div>
					</fieldset>
				</div>
			</div>
			<div class="col_100">
				<fieldset>
				<label style="margin-top: 5px;" class=""><fmt:message key="carteiraTrabalho.detalhamento" /></label>
						<div>
						  	<textarea rows="3" cols="70" maxlength="5000" name="detalhamento" id="detalhamento"></textarea>
						</div>
				</fieldset>
			</div>
			<br><br>
			<button type='button' name='btnGravar' class="light"  onclick='save();'>
				<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
				<span><fmt:message key="citcorpore.comum.gravar"/></span>
			</button>
			<br><br><br>
			<div class="col_100">
				<div id='divInfoTimeSheet'>
				</div>
			</div>
			</form>
		</div>

	<form name='form' id='form' action='${ctx}/pages/gerenciamentoServicos_OLD/gerenciamentoServicos_OLD'>
		<input type='hidden' name='idFluxo'/>
		<input type='hidden' name='idVisao'/>
		<input type='hidden' name='idTarefa'/>
		<input type='hidden' name='acaoFluxo'/>
		<input type='hidden' name='idUsuarioDestino'/>
		<input type='hidden' name='numeroContratoSel'/>
		<input type='hidden' name='idSolicitacaoSel'/>
		<input type='hidden' name='idTipoDemandaServico'/>
		<input type='hidden' name='responsavelAtual'/>
		<input type='hidden' name='idSolicitacao' id='idSolicitacaoForm'/>
	    <input type="hidden" name="nomeCampoOrdenacao" id="nomeCampoOrdenacao2" />
	    <input type="hidden" name="ordenacaoAsc" id="ordenacaoAsc2" />
	    <input type="hidden" name="descricaoSolicitacao" id="descricaoSolicitacao" />
		<input type='hidden' name='grupoAtual'/>
		<input type='hidden' name='solicitanteUnidade'/>
	</form>

	<div id="popupCadastroRapido">
          <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
	</div>

	<div id="POPUP_SOL_SERV">
		<iframe id='fraSolicitacaoServico' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;'></iframe>
	</div>

	<div id="POPUP_VISAO" style="overflow: hidden">
		<iframe id='fraVisao' src='about:blank' width="100%" height="100%"></iframe>
	</div>

		<%@include file="/include/footer.jsp"%>
	</body>
</html>