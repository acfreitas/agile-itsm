<!-- http://gantt.twproject.com/distrib/gantt.html -->

<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="java.util.Collection"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
  	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

	<title><fmt:message key="citcorpore.comum.title" /></title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/themeroller/Aristo.css">
	<link rel=stylesheet href="${ctx}/pages/cronograma/platform.css" type="text/css">
	<link rel=stylesheet href="${ctx}/pages/cronograma/gantt.css" type="text/css">

	<script src="${ctx}/pages/cronograma/libs/jquery.min.js"></script>
	<script src="${ctx}/pages/cronograma/libs/jquery-ui.min.js"></script>

	<script src="${ctx}/pages/cronograma/libs/jquery.livequery.min.js"></script>
	<script src="${ctx}/pages/cronograma/libs/jquery.timers.js"></script>
	<script src="${ctx}/pages/cronograma/libs/platform.js"></script>
	<script src="${ctx}/pages/cronograma/libs/date.js"></script>
	<script src="${ctx}/pages/cronograma/libs/i18nJs.js"></script>
	<script src="${ctx}/pages/cronograma/libs/JST/jquery.JST.js"></script>

	<script src="${ctx}/pages/cronograma/libs/ganttUtilities.js"></script>
	<script src="${ctx}/pages/cronograma/libs/ganttTask.js"></script>
	<script src="${ctx}/pages/cronograma/libs/ganttDrawer.js"></script>
	<script src="${ctx}/pages/cronograma/libs/ganttGridEditor.js"></script>
	<script src="${ctx}/pages/cronograma/libs/ganttMaster.js"></script>

	<!-- Datepicker + Internacionalização + Inicialização -->
	<script type="text/javascript" src="${ctx}/js/jquery.ui.datepicker.js"></script>
	<script src="${ctx}/js/jquery.ui.datepicker-locale.js"></script>

</head>
<body style="background-color: #fff;">

<div id="POPUP_INFO_PROJETO"
	style="overflow: hidden;z-index: 100000; display: none">
	<div class="toggle_container">
		<div id='divMensagemInsercao' class="section" style="overflow: hidden; font-size: 24px;">

		</div>
	</div>
</div>

<div id="workSpace" style="padding:0px; overflow-y:auto; overflow-x:hidden;border:1px solid #e5e5e5;position:relative;margin:0 5px"></div>

<div id="taZone" style="display:none;">
 <form name='form' action='${ctx}/pages/cronograma/cronograma'>
  <input type='hidden' name='idProjeto' id='idProjeto'>
  <input type="hidden" name="idTemplateImpressao" id="idTemplateImpressao"/>
  <input type="hidden" name="idLinhaBaseProjeto" id="idLinhaBaseProjeto"/>
  <input type="hidden" name="situacaoLinhaBaseProjeto" id="situacaoLinhaBaseProjeto"/>
  <input type="hidden" name="situacaoLinhaBaseProjetoSelecionada" id="situacaoLinhaBaseProjetoSelecionada"/>
  <input type="hidden" name="novaLinhaBaseProjeto" id="novaLinhaBaseProjeto"/>
  <input type="hidden" name="marcosProjeto" id="marcosProjeto"/>
  <input type="hidden" name="podeModificar" id="podeModificar" value="S"/>
  <textarea rows="8" cols="150" id="ta" name="ta">
    {"tasks":[
    {"id":-1,"name":" ","code":"","description":"","level":0,"status":"STATUS_ACTIVE","start":1346623200000,"duration":0,"end":1348523999999,"startIsMilestone":true,"endIsMilestone":false,"depends":" ","idMarcoPagamentoPrj":" ","assigs":[], "prods":[]}
    ],"selectedRow":0,"deletedTaskIds":[],"canWrite":true,"canWriteOnParent":true }
  </textarea>
  <button onclick="loadGanttFromServer();">load</button>
 </form>
</div>

<style>
  .resEdit {
    padding: 15px;
  }

  .resLine {
    width: 95%;
    padding: 3px;
    margin: 5px;
    border: 1px solid #d0d0d0;
  }

  /*body {
    overflow: hidden;
  }*/

  .ui-datepicker .ui-datepicker-header {
	height: 30px !important;
  }
</style>

<form id="gimmeBack" style="display:none;" action="../gimmeBack.jsp" method="post" target="_blank"><input type="hidden" name="prj" id="gimBaPrj"></form>

<script type="text/javascript">

var ge;  //this is the hugly but very friendly global var for the gantt editor
$(function() {

  //load templates
  $("#ganttemplates").loadTemplates();

  // here starts gantt initialization
  ge = new GanttMaster();
  var workSpace = $("#workSpace");
  workSpace.css({width:$(window).width() - 20,height:$(window).height() - 140});
  ge.init(workSpace);

  //inject some buttons (for this demo only)
  $(".ganttButtonBar div").append("<button onclick='clearGantt();' class='button'><fmt:message key='dataManager.limpar' /></button>")
          .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
          .append("<button onclick='refreshProjeto();' class='button'><img src='${ctx}/imagens/refresh.png'></button>")
          .append("<button onclick='marcosPagProjeto();' class='button'><fmt:message key='cronograma.CronogramaPagamentos' /></button>")
          .append("<button onclick='printProjeto();' class='button'><fmt:message key='cronograma.imprimir' /></button>")
  		  .append("<button onclick='voltar();' class='button'><fmt:message key='citcorpore.comum.voltar' /></button>");
  //$(".ganttButtonBar h1").html("<img src='logo.png'>");
  $(".ganttButtonBar h1").html("<br>");
  $(".ganttButtonBar div").addClass('buttons');
  //overwrite with localized ones
  loadI18n();

  //simulate a data load from a server.
  loadGanttFromServer();


  //fill default Teamwork roles if any
  if (!ge.roles || ge.roles.length == 0) {
    //setRoles();
  }

  //fill default Resources roles if any
  if (!ge.resources || ge.resources.length == 0) {
    //setResource();
  }


  /*/debug time scale
  $(".splitBox2").mousemove(function(e){
    var x=e.clientX-$(this).offset().left;
    var mill=Math.round(x/(ge.gantt.fx) + ge.gantt.startMillis)
    $("#ndo").html(x+" "+new Date(mill))
  });*/

});


//-------------------------------------------  Open a black popup for managing resources. This is only an axample of implementation (usually resources come from server) ------------------------------------------------------
function openPropertyEditor() {
	//document.getElementById('POPUP_INFO_PROJETO').style.display='block';
	$("#POPUP_INFO_PROJETO").dialog({
		autoOpen : false,
		width : 700,
		height : 420,
		modal : true,
		buttons: [
		    {
		        text: "Fechar",
		        click: function() { $(this).dialog("close"); }
		    }
		],
		close: function(event, ui) {
		}
	});
	$("#POPUP_INFO_PROJETO").dialog("open");
}

function loadGanttFromServer(taskId, callback) {

  //this is a simulation: load data from the local storage if you have already played with the demo or a textarea with starting demo data
  loadFromLocalStorage();

  //this is the real implementation
  /*
  //var taskId = $("#taskSelector").val();
  var prof = new Profiler("loadServerSide");
  prof.reset();

  $.getJSON("ganttAjaxController.jsp", {CM:"LOADPROJECT",taskId:taskId}, function(response) {
    //console.debug(response);
    if (response.ok) {
      prof.stop();

      ge.loadProject(response.project);
      ge.checkpoint(); //empty the undo stack

      if (typeof(callback)=="function") {
        callback(response);
      }
    } else {
      jsonErrorHandling(response);
    }
  });
  */
}


function saveGanttOnServer() {

	var saveEditor = $.JST.createFromTemplate({}, "SAVE_PROJECT");
	var ndo = createBlackPage(600, 300).append(saveEditor);

	if (document.form.podeModificar.value == 'N'){
		document.getElementById('divMsgSaveProjeto').style.display = 'block';
		document.getElementById('divSaveProjeto').style.display = 'none';
	}else{
		document.getElementById('divMsgSaveProjeto').style.display = 'none';
		document.getElementById('divSaveProjeto').style.display = 'block';
	}
	  var auxSit = '';
	  if (document.form.situacaoLinhaBaseProjeto.value != undefined){
		  auxSit = document.form.situacaoLinhaBaseProjeto.value;
	  }
	  if (auxSit != ''){
		  HTMLUtils.setValue('idSituacaoLinhaBase', auxSit);
	  }

	  validaSituacaoNewBL(document.getElementById('idSituacaoLinhaBase'));

  //this is a simulation: save data to the local storage or to the textarea
  //saveInLocalStorage();


  /*
  var prj = ge.saveProject();

  delete prj.resources;
  delete prj.roles;

  var prof = new Profiler("saveServerSide");
  prof.reset();

  if (ge.deletedTaskIds.length>0) {
    if (!confirm("TASK_THAT_WILL_BE_REMOVED\n"+ge.deletedTaskIds.length)) {
      return;
    }
  }

  $.ajax("ganttAjaxController.jsp", {
    dataType:"json",
    data: {CM:"SVPROJECT",prj:JSON.stringify(prj)},
    type:"POST",

    success: function(response) {
      if (response.ok) {
        prof.stop();
        if (response.project) {
          ge.loadProject(response.project); //must reload as "tmp_" ids are now the good ones
        } else {
          ge.reset();
        }
      } else {
        var errMsg="Errors saving project\n";
        if (response.message) {
          errMsg=errMsg+response.message+"\n";
        }

        if (response.errorMessages.length) {
          errMsg += response.errorMessages.join("\n");
        }

        alert(errMsg);
      }
    }

  });
  */
}


//-------------------------------------------  Create some demo data ------------------------------------------------------
function setRoles() {
	/*
  ge.roles = [
    {
      id:"tmp_1",
      name:"Project Manager"
    },
    {
      id:"tmp_2",
      name:"Worker"
    },
    {
      id:"tmp_3",
      name:"Stakeholder/Customer"
    }
  ];
	*/
}

function setPerfis(strPerf) {
	var auxStr = 'ge.roles = [' + strPerf + '];';
	eval(auxStr);
}

function setResource() {
  var res = [];
  for (var i = 1; i <= 10; i++) {
    res.push({id:"tmp_" + i,name:"Resource " + i});
  }
  ge.resources = res;
}


function clearGantt() {
  document.form.idLinhaBaseProjeto.value = '';
  document.form.idProjeto.value = '';
  document.form.situacaoLinhaBaseProjeto.value = '';
  ge.reset();
  HTMLUtils.setValue('idProjetoCombo', '');
}

function loadI18n() {
  GanttMaster.messages = {
    "CHANGE_OUT_OF_SCOPE":"NO_RIGHTS_FOR_UPDATE_PARENTS_OUT_OF_EDITOR_SCOPE",
    "START_IS_MILESTONE":"START_IS_MILESTONE",
    "END_IS_MILESTONE":"END_IS_MILESTONE",
    "TASK_HAS_CONSTRAINTS":"Esta tarefa possui restrição",
    "GANTT_ERROR_DEPENDS_ON_OPEN_TASK":"Existe dependência para uma tarefa em aberto",
    "GANTT_ERROR_DESCENDANT_OF_CLOSED_TASK":"GANTT_ERROR_DESCENDANT_OF_CLOSED_TASK",
    "TASK_HAS_EXTERNAL_DEPS":"TASK_HAS_EXTERNAL_DEPS",
    "GANTT_ERROR_LOADING_DATA_TASK_REMOVED":"GANTT_ERROR_LOADING_DATA_TASK_REMOVED",
    "ERROR_SETTING_DATES":"ERROR_SETTING_DATES",
    "CIRCULAR_REFERENCE":"CIRCULAR_REFERENCE",
    "CANNOT_DEPENDS_ON_ANCESTORS":"CANNOT_DEPENDS_ON_ANCESTORS",
    "CANNOT_DEPENDS_ON_DESCENDANTS":"CANNOT_DEPENDS_ON_DESCENDANTS",
    "INVALID_DATE_FORMAT":"INVALID_DATE_FORMAT",
    "TASK_MOVE_INCONSISTENT_LEVEL":"TASK_MOVE_INCONSISTENT_LEVEL",

    "GANTT_QUARTER_SHORT":"trim.",
    "GANTT_SEMESTER_SHORT":"sem."
  };
}


//-------------------------------------------  Open a black popup for managing resources. This is only an axample of implementation (usually resources come from server) ------------------------------------------------------
function openResourceEditor() {
  var editor = $("<div>");
  editor.append("<h2><fmt:message key='cronograma.editorRecursos' /></h2>");
  editor.addClass("resEdit");

  for (var i in ge.resources) {
    var res = ge.resources[i];
    var inp = $("<input type='text'>").attr("pos", i).addClass("resLine").val(res.name);
    editor.append(inp).append("<br>");
  }

  var sv = $("<div>save</div>").css("float", "right").addClass("button").click(function() {
    $(this).closest(".resEdit").find("input").each(function() {
      var el = $(this);
      var pos = el.attr("pos");
      ge.resources[pos].name = el.val();
    });
    ge.editor.redraw();
    closeBlackPopup();
  });
  editor.append(sv);

  var ndo = createBlackPage(1000, 700).append(editor);
}

//-------------------------------------------  Get project file as JSON (used for migrate project from gantt to Teamwork) ------------------------------------------------------
function getFile() {
  $("#gimBaPrj").val(JSON.stringify(ge.saveProject()));
  $("#gimmeBack").submit();
  $("#gimBaPrj").val("");

  /*  var uriContent = "data:text/html;charset=utf-8," + encodeURIComponent(JSON.stringify(prj));
   console.debug(uriContent);
   neww=window.open(uriContent,"dl");*/
}


//-------------------------------------------  LOCAL STORAGE MANAGEMENT (for this demo only) ------------------------------------------------------
Storage.prototype.setObject = function(key, value) {
  this.setItem(key, JSON.stringify(value));
};


Storage.prototype.getObject = function(key) {
  return this.getItem(key) && JSON.parse(this.getItem(key));
};

String.prototype.replaceAll = function(de, para){
    var str = this;
    var pos = str.indexOf(de);
    while (pos > -1){
		str = str.replace(de, para);
		pos = str.indexOf(de);
	}
    return (str);
}

function loadProjectFromTextArea(strPerf, strRec, strProd){
	var auxStr = 'ge.roles = [' + strPerf + '];';
	eval(auxStr);
	auxStr = 'ge.resources = [' + strRec + '];';
	eval(auxStr);
	auxStr = 'ge.products = [' + strProd + '];';
	eval(auxStr);
	var retAux = document.getElementById('ta').value;
	//tratemento de barra inversa.
	retAux = retAux.replaceAll("\\","");
	//tratamento necessário graças a um problema nos navegadores ao carregar os id's 10 e 13 no setValue do textArea, estavam gerando caracteres aleatórios
	var ret = retAux.replaceAll("#32","");
	ret = ret.replace("32#","\"10\"");
	ret = ret.replace("32#","\"13\"");
	ret = ret.replaceAll("32#","");
	//ret = ret.replaceAll(" ", "");

	var objs = null;
	if (ret != ''){
		try{
			objs = JSON.parse(ret);
		}catch(e){
			//alert(e);
			//alert(ret);
		}
	}
	objs.roles = ge.roles;
	objs.resources = ge.resources;
	objs.products = ge.products;
	ge.loadProject(objs);
	ge.checkpoint(); //empty the undo stack
}
function loadFromLocalStorage() {
  var ret;
  if (localStorage) {
    if (localStorage.getObject("teamworkGantDemo")) {
      ret = localStorage.getObject("teamworkGantDemo");
    }
  } else {
    $("#taZone").show();
  }
  if (!ret || !ret.tasks || ret.tasks.length == 0){
    ret = JSON.parse($("#ta").val());


    //actualiza data
    var offset=new Date().getTime()-ret.tasks[0].start;
    for (var i=0;i<ret.tasks.length;i++)
      ret.tasks[i].start=ret.tasks[i].start+offset;


  }
  ge.loadProject(ret);
  ge.checkpoint(); //empty the undo stack
}
var novaLinhaBaseProjeto = 'N';
function validaCheckNewBL(obj){
	if (obj.checked){
		novaLinhaBaseProjeto = 'S';
	}else{
		novaLinhaBaseProjeto = 'N';
	}
}
function validaSituacaoNewBL(obj){
	document.form.situacaoLinhaBaseProjetoSelecionada.value = obj.value;
}
function saveInLocalStorage() {
  if (document.form.idProjeto.value == '' || document.form.idProjeto.value == ' '){
	  alert('<fmt:message key="projeto.informeProjeto" />');
	  return;
  }
  var auxSit = '';
  if (document.form.situacaoLinhaBaseProjeto.value != undefined){
	  auxSit = document.form.situacaoLinhaBaseProjeto.value;
  }
  if (auxSit != ''){
	  if (auxSit != 'A' && novaLinhaBaseProjeto != 'S'){
			if (!confirm('<fmt:message key="cronograma.linhaJaModificada"/>')){
				return;
			}
	  }
  }
  var prj = ge.saveProject();
  /*
  if (localStorage) {
    localStorage.setObject("teamworkGantDemo", prj);
  } else {
    $("#ta").val(JSON.stringify(prj));
  }
  */
  $("#ta").val(JSON.stringify(prj));
  document.form.novaLinhaBaseProjeto.value = novaLinhaBaseProjeto;
  document.form.save();
}
function printProjeto(){
	var printEditor = $.JST.createFromTemplate({}, "PRINT_PROJECT");
	var ndo = createBlackPage(600, 300).append(printEditor);
	document.form.fireEvent('carregaTemplatesPrintProjeto');
	//document.form.fireEvent('printProjeto');
}
var marcosPag = [];
function marcosPagProjeto(){
	if (document.form.idProjeto.value == ''){
		alert('<fmt:message key="cronograma.projetoSelecionado"/>');
		return;
	}
	if (document.form.podeModificar.value == 'N'){
		 alert('<fmt:message key="cronograma.linhaJaExecucao" />');
		return;
	}
	var pagEditor = $.JST.createFromTemplate({}, "MARCOPROJETO");

	  var assigsTable = pagEditor.find("#pagsTable");
	  assigsTable.find("[pagAssigId]").remove();
	  // --
	  for (var i = 0; i < marcosPag.length; i++) {
	    var marco = marcosPag[i];
	    var assigRow = $.JST.createFromTemplate({marco:marco}, "MARCOPROJETO_ROW");
	    assigsTable.append(assigRow);
	  }

	  pagEditor.find("#addPags").click(function () {
		var assigsTable = pagEditor.find("#pagsTable");
		var assigRow = $.JST.createFromTemplate({marco:{id:"tmp_" + new Date().getTime(), nomeMarcoPag:'', dataPrevisaoPag:''}}, "MARCOPROJETO_ROW");
		assigsTable.append(assigRow);

		/*
		 * Seta o datepicker para os inputs com as classes .citdatepicker e .datepicker
		 */
		$('.citdatepicker').datepicker();
		$('.datepicker').datepicker();
		$('.dataNascimento').datepicker();
		$('.dtpicker').datepicker();
		$('.date').datepicker();

		/*
		 * Passar por todos os inputs com as classes .citdatepicker e .datepicker e adicionar as classes de formatação e validação do framework se não existirem
		 */
		$('.citdatepicker, .datepicker, .dataNascimento, .dtpicker, .date').each(function(e) {
			if (!$(this).hasClass('Format[Date]')) $(this).addClass('Format[Date]');
			if (!$(this).hasClass('Valid[Date]')) $(this).addClass('Valid[Date]');
		});
	  });

	  pagEditor.find("#saveMarcosButton").click(function () {
		  var i = 0;
		  marcosPag = [];
		  var passou = true;
		  pagEditor.find("tr[pagAssigId]").each(function () {
			  var trAss = $(this);
		      var assId = trAss.attr("pagAssigId");
		      var nomeMarco = trAss.find("[name=nomeMarcoPag]").val();
		      var dataMarco = trAss.find("[name=dataPrevisaoPag]").val();

			  	if(StringUtils.isBlank(nomeMarco)){
			  		 alert('<fmt:message key="cronograma.preencherDescricao"/>');
			  		passou = false;
					return; //Deixa passar em branco. O valida Required que trata isso.
				}

			  	if(StringUtils.isBlank(dataMarco)){
			  		alert(i18n_message("cronograma.dataPagamento") +': ' + i18n_message("citcorpore.validacao.dataInvalida"));
			  		passou = false;
					return; //Deixa passar em branco. O valida Required que trata isso.
				}
				var ret = DateTimeUtil.isValidDate(dataMarco);
				if (!ret){
					alert(i18n_message("cronograma.dataPagamento") +': ' + i18n_message("citcorpore.validacao.dataInvalida"));
					passou = false;
					return;
				}

		      marcosPag[i] = {id:assId, nomeMarcoPag:nomeMarco, dataPrevisaoPag:dataMarco};
		      i++;
		  });
		  if (!passou){
			 return;
		  }
		  var txtPag = JSON.stringify(marcosPag);
		  document.form.marcosProjeto.value = txtPag;
		  document.form.fireEvent('saveMarcosPag');
	  });

	var ndo = createBlackPage(900, 500).append(pagEditor);

	/*
	 * Seta o datepicker para os inputs com as classes .citdatepicker e .datepicker
	 */
	$('.citdatepicker').datepicker();
	$('.datepicker').datepicker();
	$('.dataNascimento').datepicker();
	$('.dtpicker').datepicker();
	$('.date').datepicker();

	/*
	 * Passar por todos os inputs com as classes .citdatepicker e .datepicker e adicionar as classes de formatação e validação do framework se não existirem
	 */
	$('.citdatepicker, .datepicker, .dataNascimento, .dtpicker, .date').each(function(e) {
		if (!$(this).hasClass('Format[Date]')) $(this).addClass('Format[Date]');
		if (!$(this).hasClass('Valid[Date]')) $(this).addClass('Valid[Date]');
	});
}
function geraMarcos(strMarcos){
	var auxStr = 'marcosPag = [' + strMarcos + '];';
	eval(auxStr);
}
function printProjetoTemplate(){
	var valueId = HTMLUtils.getValue('idTemplate');
	if (valueId == '' || valueId == ' ' || valueId == '0'){
		 alert('<fmt:message key="cronograma.informeTemplate"/>');
		return;
	}
	document.form.idTemplateImpressao.value = valueId;
	document.form.fireEvent('printProjeto');
}
function mudaIdProjeto(idProjetoCombo){
	if (!confirm('<fmt:message key="cronograma.mudarProjeto"/>')){
		return;
	}
	if (idProjetoCombo.value == ''){
		clearGantt();
	}
	document.form.idLinhaBaseProjeto.value = '';
	document.form.situacaoLinhaBaseProjeto.value = '';
	document.form.idProjeto.value = idProjetoCombo.value;
	document.form.fireEvent('abreProjeto');
}
function refreshProjeto(){
	if (!confirm('<fmt:message key="cronograma.refreshProjeto"/>')){
		return;
	}
	ge.reset();
	document.form.idLinhaBaseProjeto.value = '';
	document.form.situacaoLinhaBaseProjeto.value = '';
	if (document.form.idProjeto.value == ''){
		alert('<fmt:message key="cronograma.projetoSelecionado"/>');
		return;
	}
	document.form.fireEvent('abreProjeto');
}
function voltar(){
	window.location = '${ctx}/pages/index/index.load';
}

</script>

<!-- Desenvolvedor: Bruno Rodrigues  Data: 28/10/2013 Horário: 14h04min  ID Citsmart: 120948  Motivo/Comentário: Internacionalização dos title da div  -->
<div id="gantEditorTemplates" style="display:none;">
  <div class="__template__" type="GANTBUTTONS"><!--
  <div class="ganttButtonBar">
    <h1 style="float:left">task tree/gantt</h1>
    <div class="buttons">
    <fmt:message key="cronograma.selecioneProjeto"/>: <select name="idProjetoCombo" id="idProjetoCombo" onchange="mudaIdProjeto(this)">
  	</select><br>
    <button onclick="$('#workSpace').trigger('undo.gantt');" class="button textual" title=<fmt:message key="projeto.desfazer" />><span class="teamworkIcon">&#39;</span></button>
    <button onclick="$('#workSpace').trigger('redo.gantt');" class="button textual" title=<fmt:message key="projeto.refazer" />><span class="teamworkIcon">&middot;</span></button>
    <span class="ganttButtonSeparator"></span>
    <button onclick="$('#workSpace').trigger('addAboveCurrentTask.gantt');" class="button textual" title=<fmt:message key="projeto.inserirAcima" />><span class="teamworkIcon">l</span></button>
    <button onclick="$('#workSpace').trigger('addBelowCurrentTask.gantt');" class="button textual" title=<fmt:message key="projeto.inserirAbaixo" />><span class="teamworkIcon">X</span></button>
    <span class="ganttButtonSeparator"></span>
    <button onclick="$('#workSpace').trigger('indentCurrentTask.gantt');" class="button textual" title=<fmt:message key="projeto.recuarTarefa" />><span class="teamworkIcon">.</span></button>
    <button onclick="$('#workSpace').trigger('outdentCurrentTask.gantt');" class="button textual" title=<fmt:message key="projeto.desrecuarTarefa" />><span class="teamworkIcon">:</span></button>
    <span class="ganttButtonSeparator"></span>
    <button onclick="$('#workSpace').trigger('moveUpCurrentTask.gantt');" class="button textual" title=<fmt:message key="projeto.moverCima" />><span class="teamworkIcon">k</span></button>
    <button onclick="$('#workSpace').trigger('moveDownCurrentTask.gantt');" class="button textual" title=<fmt:message key="projeto.moverBaixo" />><span class="teamworkIcon">j</span></button>
    <span class="ganttButtonSeparator"></span>
    <button onclick="$('#workSpace').trigger('zoomMinus.gantt');" class="button textual" title=<fmt:message key="projeto.diminuirZoom" />><span class="teamworkIcon">)</span></button>
    <button onclick="$('#workSpace').trigger('zoomPlus.gantt');" class="button textual" title=<fmt:message key="projeto.aumentarZoom" />><span class="teamworkIcon">(</span></button>
    <span class="ganttButtonSeparator"></span>
    <button onclick="$('#workSpace').trigger('deleteCurrentTask.gantt');" class="button textual" title=<fmt:message key="projeto.deletar" />><span class="teamworkIcon">&cent;</span></button>
      &nbsp; &nbsp; &nbsp; &nbsp;
      <button onclick="saveGanttOnServer();" class="button first big" title=<fmt:message key="projeto.salvar" />><fmt:message key="citcorpore.comum.gravar"/></button>
    </div></div>
  --></div>

  <div class="__template__" type="TASKSEDITHEAD"><!--
  <table class="gdfTable" cellspacing="0" cellpadding="0">
    <thead>
    <tr style="height:40px">
      <th class="gdfColHeader" style="width:35px;"></th>
      <th class="gdfColHeader" style="width:25px;"></th>
      <th class="gdfColHeader gdfResizable" style="width:30px;"><fmt:message key="cronograma.abreviado"/></th>
      <th class="gdfColHeader gdfResizable" style="width:30px;">% <fmt:message key="cronograma.executado"/></th>
      <th class="gdfColHeader gdfResizable" style="width:10px;"></th>
      <th class="gdfColHeader gdfResizable" style="width:200px;"><fmt:message key="cronograma.tarefa"/></th>
      <th class="gdfColHeader gdfResizable" style="width:80px;"><fmt:message key="cronograma.inicio"/></th>
      <th class="gdfColHeader gdfResizable" style="width:80px;"><fmt:message key="cronograma.fim"/></th>
      <th class="gdfColHeader gdfResizable" style="width:50px;"><fmt:message key="cronograma.duracaoDias"/></th>
      <th class="gdfColHeader gdfResizable" style="width:50px;">dep.</th>
      <th class="gdfColHeader gdfResizable" style="width:200px;"><fmt:message key="cronograma.recursos"/></th>
      <th class="gdfColHeader gdfResizable" style="width:200px;"><fmt:message key="cronograma.produtos"/></th>
    </tr>
    </thead>
  </table>
  --></div>

  <div class="__template__" type="TASKROW"><!--
  <tr taskId="(#=obj.id#)" class="taskEditRow" level="(#=level#)">
    <th class="gdfCell edit" align="right" style="cursor:pointer;"><span class="taskRowIndex">(#=obj.getRow()+1#)</span> <span class="teamworkIcon" style="font-size:12px;" >e</span></th>
    <td class="gdfCell" align="center"><div class="taskStatus cvcColorSquare" status="(#=obj.status#)"></div></td>
    <td class="gdfCell"><input type="text" maxlength="40" name="code" value="(#=obj.codeobj.code:''#)"></td>
    <td class="gdfCell"><input type="text" name="progress" value="(#=obj.progressobj.progress:''#)" disabled="true"></td>
    <td class="gdfCell"><span>-</span></td>

    <td class="gdfCell indentCell" style="padding-left:(#=obj.level*10#)px;"><input type="text" maxlength="4000" name="name" value="(#=obj.name#)" style="(#=obj.level>0'border-left:2px dotted orange':''#)"></td>

    <td class="gdfCell"><input type="text" name="start"  value="" class="date"></td>
    <td class="gdfCell"><input type="text" name="end" value="" class="date"></td>
    <td class="gdfCell"><input type="text" name="duration" value="(#=obj.duration#)"></td>
    <td class="gdfCell"><input type="text" name="depends" value="(#=obj.depends#)" (#=obj.hasExternalDep"readonly":""#)></td>
    <td class="gdfCell taskAssigs">(#=obj.getAssigsString()#)</td>
    <td class="gdfCell taskProds">(#=obj.getProdsString()#)</td>
  </tr>
  --></div>

  <div class="__template__" type="TASKEMPTYROW"><!--
  <tr class="taskEditRow emptyRow" >
    <th class="gdfCell" align="right"></th>
    <td class="gdfCell" align="center"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
    <td class="gdfCell"></td>
  </tr>
  --></div>

  <div class="__template__" type="TASKBAR"><!--
  <div class="taskBox" taskId="(#=obj.id#)" >
    <div class="layout (#=obj.hasExternalDep'extDep':''#)">
      <div class="taskStatus" status="(#=obj.status#)"></div>
      <div class="taskProgress" style="width:(#=obj.progress>100100:obj.progress#)%; background-color:(#=obj.progress>100'red':'rgb(153,255,51);'#);"></div>
      <div class="milestone (#=obj.startIsMilestone'active':''#)" ></div>

      <div class="taskLabel"></div>
      <div class="milestone end (#=obj.endIsMilestone'active':''#)" ></div>
    </div>
  </div>
  --></div>


  <div class="__template__" type="CHANGE_STATUS"><!--
    <div class="taskStatusBox">
      <div class="taskStatus cvcColorSquare" status="STATUS_ACTIVE" title="active"></div>
      <div class="taskStatus cvcColorSquare" status="STATUS_DONE" title="completed"></div>
      <div class="taskStatus cvcColorSquare" status="STATUS_FAILED" title="failed"></div>
      <div class="taskStatus cvcColorSquare" status="STATUS_SUSPENDED" title="suspended"></div>
      <div class="taskStatus cvcColorSquare" status="STATUS_UNDEFINED" title="undefined"></div>
    </div>
  --></div>


  <div class="__template__" type="TASK_EDITOR"><!--
  <div class="ganttTaskEditor">
  <form name="formTask" action="${ctx}/pages/cronograma/cronograma">
  <table width="100%">
    <tr>
      <td>
        <table cellpadding="5">
          <tr>
            <td><label for="code"><fmt:message key="cronograma.abreviado"/></label><br><input type="text" maxlength="40" name="code" id="code" value="" class="formElements"></td>
           </tr><tr>
            <td><label for="name"><fmt:message key="cronograma.nome"/></label><br><input type="text" maxlength="4000" name="name" id="name" value=""  size="35" class="formElements"></td>
          </tr>
          <tr></tr>
            <td>
              <label for="description"><fmt:message key="cronograma.detalhamento"/></label><br>
              <textarea rows="3" cols="40" maxlength="500" id="description" name="description" class="formElements"></textarea>
            </td>
          </tr>
        </table>
      </td>
      <td valign="top">
        <table cellpadding="5">
          <tr>
          <td colspan="2"><label for="status"><fmt:message key="cronograma.status"/></label><br><div id="status" class="taskStatus" status=""></div></td>
          <tr>
          <td colspan="2"><label for="progress"><fmt:message key="cronograma.progresso"/></label><br><input type="text" name="progress" id="progress" value="" size="3" class="formElements" readonly="readonly"></td>
          </tr>
          <tr>
          <td><label for="start"><fmt:message key="cronograma.inicio"/></label><br><input type="text" name="start" id="start"  value="" class="date" size="10" class="formElements"><input type="checkbox" id="startIsMilestone"> </td>
          <td rowspan="2" class="graph" style="padding-left:50px"><label for="duration"><fmt:message key="cronograma.duracaoDias"/></label><br><input type="text" name="duration" id="duration" value=""  size="5" class="formElements"></td>
        </tr>
         <tr>
          <td><label for="end"><fmt:message key="cronograma.fim"/></label><br><input type="text" name="end" id="end" value="" class="date"  size="10" class="formElements"><input type="checkbox" id="endIsMilestone"></td>
         </tr>
        </table>
      </td>
    </tr>
    </table>

    <table>
         <tr>
         	<td>
         		<b><fmt:message key="cronograma.pagamentoEm"/></b>
         	</td>
         	<td>
         		<td ><select type="select" name="idMarcoPagamentoPrj" id="idMarcoPagamentoPrj" class="formElements"></select></td>
         	</td>
         </tr>
    </table>

    <h2><fmt:message key="cronograma.recursos"/></h2>
  <table  cellspacing="1" cellpadding="0" width="100%" id="assigsTable">
    <tr>
      <th style="width:100px;"><fmt:message key="cronograma.nome"/></th>
      <th style="width:70px;"><fmt:message key="cronograma.perfil"/></th>
      <th style="width:30px;"><fmt:message key="cronograma.trabalho"/></th>
      <th style="width:30px;"><fmt:message key="cronograma.trabalhoPorOS"/></th>
      <th style="width:30px;" id="addAssig"><span class="teamworkIcon" style="cursor: pointer">+</span></th>
    </tr>
  </table>

    <h2><fmt:message key="cronograma.produtos"/></h2>
  <table  cellspacing="1" cellpadding="0" width="100%" id="prodTable">
    <tr>
      <th style="width:100px;"><fmt:message key="cronograma.nome"/></th>
      <th style="width:30px;" id="addProd"><span class="teamworkIcon" style="cursor: pointer">+</span></th>
    </tr>
  </table>

  <div style="text-align: right; padding-top: 20px"><button type="button" id="saveButton" class="button big">OK</button></div>
  </form>
  </div>
  --></div>

  <div class="__template__" type="MARCOPROJETO"><!--
  <div>
  	<form name="formMarcoFin" action="${ctx}/pages/cronograma/cronograma">
	   <h2>Cronograma Físico/Financeiro</h2>
	  <table  cellspacing="1" cellpadding="0" width="100%" id="pagsTable">
	    <tr>
	      <th style="width:100px;"><fmt:message key="cronograma.descricaoReferencia"/></th>
	      <th style="width:100px;"><fmt:message key="cronograma.dataPagamento"/></th>
	      <th style="width:30px;" id="addPags"><span class="teamworkIcon" style="cursor: pointer">+</span></th>
	    </tr>
	  </table>
	  <div style="text-align: right; padding-top: 20px"><button type="button" id="saveMarcosButton" class="button big">OK</button></div>
	</form>
  </div>
  --></div>

  <div class="__template__" type="MARCOPROJETO_ROW"><!--
  <tr taskId="(#=obj.marco.id#)" pagAssigId="(#=obj.marco.id#)" class="assigEditRow" >
    <td><input type="text" name="nomeMarcoPag" value="(#=obj.marco.nomeMarcoPag#)" size="80" maxlength="150" class="formElements"/></td>
    <td><input type="text" name="dataPrevisaoPag" id="dataPrevisaoPag_(#=obj.marco.id#)" value="(#=obj.marco.dataPrevisaoPag#)" size="10" maxlength="10" class="date"/></td>
    <td align="center"><span class="teamworkIcon delAssig" style="cursor: pointer">d</span></td>
  </tr>
  --></div>

  <div class="__template__" type="ASSIGNMENT_ROW"><!--
  <tr taskId="(#=obj.task.id#)" assigId="(#=obj.assig.id#)" class="assigEditRow" >
    <td ><select name="resourceId"  class="formElements" (#=obj.assig.id.indexOf("tmp_")==0"":"disabled"#) ></select></td>
    <td ><select type="select" name="roleId"  class="formElements"></select></td>
    <td ><input type="text" name="effort" value="(#=getMillisInHoursMinutes(obj.assig.effort)#)" size="5" maxlength="5" class="formElements"></td>
    <td ><input type="text" name="esforcoPorOS" value="(#=(obj.assig.esforcoPorOS)#)" size="5" maxlength="5" class="formElements"></td>
    <td align="center"><span class="teamworkIcon delAssig" style="cursor: pointer">d</span></td>
  </tr>
  --></div>

  <div class="__template__" type="PRODUCT_ROW"><!--
  <tr taskId="(#=obj.task.id#)" prodAssigId="(#=obj.assig.id#)" class="assigEditRow" >
    <td ><select name="productId"  class="formElements" (#=obj.assig.id.indexOf("tmp_")==0"":"disabled"#) ></select></td>
    <td align="center"><span class="teamworkIcon delAssig" style="cursor: pointer">d</span></td>
  </tr>
  --></div>

  <div class="__template__" type="SAVE_PROJECT"><!--
  <div>
  	<div id='divSaveProjeto'>
	  	<table>
	  		<tr>
	  			<td>
	  				<input type='checkbox' name='chkLinhaBase' id='chkLinhaBase' onclick='validaCheckNewBL(this)'/>
	  			</td>
	  			<td>
	  			<fmt:message key="cronograma.salvarNovaLinha"/>
	  			</td>
	  		</tr>
	  		<tr>
	  			<td>
	  				<fmt:message key="citcorpore.comum.situacao"/>:
	  			</td>
	  			<td>
	  				<select name='idSituacaoLinhaBase' id='idSituacaoLinhaBase' onclick='validaSituacaoNewBL(this)' onchange='validaSituacaoNewBL(this)'>
	  					<option value='A'><fmt:message key="cronograma.elaboracao"/></option>
	  					<option value='E'><fmt:message key="cronograma.execucao"/></option>
	  				</select>
	  			</td>
	  		</tr>
	  		<tr>
	  			<td colspan='2'>
			  		<button onclick="saveInLocalStorage();" class="button first big" title="save"><fmt:message key="citcorpore.comum.gravar"/></button>
	  			</td>
	  		</tr>
	  	</table>
  	</div>
  	<div id='divMsgSaveProjeto' style='display:none'>
  		<fmt:message key="cronograma.linhaJaExecucao"/>
  	</div>
  </div>
  --></div>

  <div class="__template__" type="PRINT_PROJECT"><!--
  <div>
  	<table>
  		<tr>
  			<td>
  				<fmt:message key="citcorpore.comum.situacao"/>:
  			</td>
  		</tr>
  		<tr>
  			<td>
  				<select name='idTemplate' id='idTemplate' size='8'>
  				</select>
  			</td>
  		</tr>
  		<tr>
  			<td colspan='2'>
		  		<button onclick="printProjetoTemplate();" class="button first big" title="<fmt:message key='cronograma.imprimir'/>"><fmt:message key="cronograma.imprimir"/></button>
  			</td>
  		</tr>
  	</table>
  </div>
  --></div>

</div>

<script type="text/javascript">
  $.JST.loadDecorator("MARCOPROJETO_ROW", function(assigTr, taskAssig) {
      assigTr.find("[name=dataPrevisaoPag]").keydown(function(e) {
	  		var element;
			if (isMozilla){
				element = e.target;
			}else{
				element = e.srcElement;
			}
    	  	FormatUtils.formataCampo(element.form, element.name, '99/99/9999', e);
        });

      assigTr.find(".delAssig").click(function() {
        var tr = $(this).closest("[pagAssigId]").fadeOut(200, function() {
          $(this).remove();
        });
      });
  });

  $.JST.loadDecorator("ASSIGNMENT_ROW", function(assigTr, taskAssig) {
     assigTr.find("[name=effort]").keydown(function(e) {
  		var element;
		if (isMozilla){
			element = e.target;
		}else{
			element = e.srcElement;
		}
 	  	FormatUtils.formataCampo(element.form, element.name, '99:99', e);
     });

//      assigTr.find("[name=esforcoPorOS]").keydown(function(e) {
//    		var element;
//  		if (isMozilla){
//  			element = e.target;
//  		}else{
//  			element = e.srcElement;
//  		}
//   	  //	FormatUtils.formataCampo(element.form, element.name, '99:99', e);
//       });

    var resEl = assigTr.find("[name=resourceId]");
    for (var i in taskAssig.task.master.resources) {
      var res = taskAssig.task.master.resources[i];
      var opt = $("<option>");
      opt.val(res.id).html(res.name);
      if (taskAssig.assig.resourceId == res.id)
        opt.attr("selected", "true");
      resEl.append(opt);
    }


    var roleEl = assigTr.find("[name=roleId]");
    for (var i in taskAssig.task.master.roles) {
      var role = taskAssig.task.master.roles[i];
      var optr = $("<option>");
      optr.val(role.id).html(role.name);
      if (taskAssig.assig.roleId == role.id)
        optr.attr("selected", "true");
      roleEl.append(optr);
    }

    if (taskAssig.task.master.canWrite) {
      assigTr.find(".delAssig").click(function() {
        var tr = $(this).closest("[assigId]").fadeOut(200, function() {
          $(this).remove();
        });
      });
    }
  });

  $.JST.loadDecorator("PRODUCT_ROW", function(assigTr, taskAssig) {
	    var resEl = assigTr.find("[name=productId]");
	    for (var i in taskAssig.task.master.products) {
	      var res = taskAssig.task.master.products[i];
	      var opt = $("<option>");
	      opt.val(res.id).html(res.name);
	      if (taskAssig.assig.productId == res.id)
	        opt.attr("selected", "true");
	      resEl.append(opt);
	    }

	    if (taskAssig.task.master.canWrite) {
	      assigTr.find(".delAssig").click(function() {
	        var tr = $(this).closest("[prodAssigId]").fadeOut(200, function() {
	          $(this).remove();
	        });
	      });
	    }
	  });

	/*
	 * Seta o datepicker para os inputs com as classes .citdatepicker e .datepicker
	 */
	$('.citdatepicker').datepicker();
	$('.datepicker').datepicker();
	$('.dataNascimento').datepicker();
	$('.dtpicker').datepicker();
	$('.date').datepicker();

	/*
	 * Passar por todos os inputs com as classes .citdatepicker e .datepicker e adicionar as classes de formatação e validação do framework se não existirem
	 */
	$('.citdatepicker, .datepicker, .dataNascimento, .dtpicker, .date').each(function(e) {
		if (!$(this).hasClass('Format[Date]')) $(this).addClass('Format[Date]');
		if (!$(this).hasClass('Valid[Date]')) $(this).addClass('Valid[Date]');
	});
</script>



</body>
</html>