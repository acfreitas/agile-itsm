/**
 * 
 */

var objTab = null;

var tabberOptions = {

        'manualStartup':true,

        /*---- Gera um evento click ----*/
        'onClick': function(argsObj) {

          var t = argsObj.tabber; /* Tabber object */
          var id = t.id; /* ID of the main tabber DIV */
          var i = argsObj.index; /* Which tab was clicked (0
                          is the first tab) */
          var e = argsObj.event; /* Event object */


        },

        'addLinkId': true
    };

 $(document).ready(function() {

	$("#POPUP_REGISTRO1").dialog({
		autoOpen : false,
		width : 900,
		height : 500,
		modal : true
	});

	$("#POPUP_NOVOMOTIVOSUSPENSAOATIVIDADE").dialog({
		autoOpen : false,
		width : 1000,
		height : 450,
		modal : true,
		close: function() {
			document.form.fireEvent('carregarComboMotivo');
		}
	});

	$("#POPUP_ORIENTACAO").dialog({
		title: i18n_message("scripts.orientacaoTecnica"),
		autoOpen : false,
		width : 700,
		height : 450,
		modal : true
	});
});

	$(document).ready(function() {

		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			selectable: false,

			selectHelper: false,

			select: function(start, end, allDay) {
				var title = prompt('Evento:');
				if (title) {
					calendar.fullCalendar('renderEvent',
						{
							title: title,
							start: start,
							end: end,
							allDay: allDay
						},
						false // make the event "stick"
					);
				}
				calendar.fullCalendar('unselect');
			},
			editable: false,

			events: ctx+"/pages/eventos/eventos.load",

			eventDrop: function(event, delta) {
			},

			loading: function(bool) {
				if (bool) $('#loading').show();
				else $('#loading').hide();
			}
		});

		$("#POPUP_REGISTRO1").dialog('close')
	});

	function validaEvento(idExecucaoAtividadePeriodicaParm, idAtv, idProg, titulo, data,numeroOS, descricaoAtividadeOS, hora){
        document.form.clear();
        document.formUpload.clear();
        uploadAnexos.clear();
        uploadAnexos.refresh();
		if (idExecucaoAtividadePeriodicaParm == '0'){
			document.form.idExecucaoAtividadePeriodica.value = '';
		}else{
			document.form.idExecucaoAtividadePeriodica.value = idExecucaoAtividadePeriodicaParm;
		}


		document.form.idAtividadePeriodica.value = idAtv;
		document.form.idProgramacaoAtividade.value = idProg;
		document.form.dataProgramada.value = data;
		document.form.horaProgramada.value = hora;
		document.form.titulo.value = titulo;
		if(descricaoAtividadeOS!= "null"){
			document.form.descricaoAtividadeOS.value = descricaoAtividadeOS;
			document.form.numeroOS.value = numeroOS;
			document.getElementById('atividadeOS').style.display = 'block';
		}else{
			document.getElementById('atividadeOS').style.display = 'none';
		}
		document.form.restore({idExecucaoAtividadePeriodica:idExecucaoAtividadePeriodicaParm});
		$("#POPUP_REGISTRO1").dialog('open')
/* 		$("#POPUP_REGISTRO").toggle();
		$("#POPUP_REGISTRO").animate({height: "toggle"}, { duration: 500 }); */
		//POPUP_REGISTRO.showInYPosition({top:100});
	}

	function gravarForm(){
		document.form.save();
	}

	function refresh(){
		//$('#calendar').fullCalendar( 'rerenderEvents' );
		window.location = ctx+'/pages/agendaAtvPeriodicas/agendaAtvPeriodicas.load?noVoltar='+noVoltar;
	}

	function refreshEvents(){
		$('#calendar').fullCalendar("refetchEvents");
		$('#calendar').fullCalendar( 'rerenderEvents' );
		$("#POPUP_REGISTRO1").dialog('close')
	}

	function voltar(){
		//$('#calendar').fullCalendar( 'rerenderEvents' );
		window.location = ctx+'/pages/index/index.load';
	}

	function mudaGrupo(idGrp){
		document.formParm.fireEvent('mudaGrupo');
	}

	function mudaGrupoPesquisa(idGrp){
		if (document.getElementById('idGrupoAtvPeriodica').value != '') {
			document.formParm.fireEvent('mudaGrupo');
		}else{
			alert('Informe o Grupo de Atividades!');
		}
	}

	function mudaPesquisa(idGrp){
		document.formParm.fireEvent('mudaPesquisa');
	}

	function visualizarOrientacoes(){
		document.form.fireEvent('visualizarOrientacoes');
	}

	function configuraMotivoSuspensao(motivo) {
		if (motivo == 'S')
		   document.getElementById('divMotivoSuspensao').style.display = 'block';
		else
		   document.getElementById('divMotivoSuspensao').style.display = 'none';
	}

	 function setSelectGrupo(elem) {
        var setSelectGrupoPes = document.getElementById("idGrupoPesquisa");
        setSelectGrupoPes.value = elem;
    }

	function abrirPopupMotivoSuspensaoAtividade() {
		document.getElementById('iframeNovoMotivoSuspensaoAtividade').src = ctx+"/pages/motivoSuspensaoAtividade/motivoSuspensaoAtividade.load?iframe=true";
		$("#POPUP_NOVOMOTIVOSUSPENSAOATIVIDADE").dialog("open");
	}