/**
 * @author breno.guimaraes
 * @param iconeTimer ID do componente que servirá de botão para ativação do timer.
 */

function Temporizador(iconeTimer){
	var notificacaoAtiva = false;
	var esconderTempo = false;
	var self = this;
	//lista de objetos Solicitacao
	this.listaTimersAtivos = [];
	var iconeAtivacaoTimer = iconeTimer;
	
	this.init = function(){		
		document.getElementById(iconeAtivacaoTimer).addEventListener("click", this.ativarDesativarTimer, true);
	}
	
	this.ativarDesativarTimer = function(){
		notificacaoAtiva = !notificacaoAtiva;
		if(notificacaoAtiva){
			document.getElementById(iconeAtivacaoTimer).style.opacity = '1';
			notify();
		} else {
			document.getElementById(iconeAtivacaoTimer).style.opacity = '0.5';
			notifyEsconder();
		}
	
	}
	
	this.addOuvinte = function(solicitacao){
		for(var i = 0; i < this.listaTimersAtivos.length; i++){
			if(this.listaTimersAtivos[i] != null && this.listaTimersAtivos[i].idComponenteRelogio == solicitacao.idComponenteRelogio){
				this.listaTimersAtivos[i].idComponenteRelogio = null;
				this.listaTimersAtivos[i] = null;
				return;
			}
		}		
		this.listaTimersAtivos.push(solicitacao);
	}	
	
	
	
	var notify = function(){
		if(!notificacaoAtiva){
			return;
		}
		if(self.listaTimersAtivos == null){
			//alert("NULO");
			return;
		} else if(self.listaTimersAtivos.length <= 0) {
			//alert("VAZIO")
			return;
		} else {
			
		}
		for(var i = 0; i < self.listaTimersAtivos.length; i++){
			if(self.listaTimersAtivos[i] != null){
				self.listaTimersAtivos[i].atualizar();
			}
		}
		setTimeout(notify, 1000);
	}
	
	var notifyEsconder = function(){
	
		for(var i = 0; i < self.listaTimersAtivos.length; i++){
			if(self.listaTimersAtivos[i] != null){
				self.listaTimersAtivos[i].esconderCampo();
			}
		}
	}	
	
}

/**
 * @param idComponenteRelogio ID do componente onde será renderizado o relógio.
 * @param idComponenteProgressBar ID do componente onde será renderizada a barra de progresso.
 * @param dataHoraLimite Data limite para encerramento da solicitação.
 * @param dataHoraSolicitacao Data de abertura da solicitação.
 */
function Solicitacao(_idComponenteRelogio, _idComponenteProgressBar, _dataHoraSolicitacao, _dataHoraLimite){
	this.idComponenteRelogio = _idComponenteRelogio;
	this.idComponenteProgressBar = _idComponenteProgressBar;
	this.dataHoraLimite = _dataHoraLimite;
	this.dataHoraSolicitacao = _dataHoraSolicitacao;
	var self = this;
	
	this.atualizar = function(){
		self.contagemRegressiva(this);
	}
	
	this.esconderCampo = function(){
		self.esconderTodos(this);
	}
	/**
	 * As datas devem estar no formato: AAAA-MM-dd HH:mm:ss
	 * @param solicitacao Objeto Solicitacao
	 */
	this.contagemRegressiva = function(solicitacao){
		var dataCompleta;
		var horaCompleta;
		var hoje;
		var ano;
		var mes;
		var dia;
		var hora;
		var minuto;
		var segundo;
		var futuro;
		var dataAberturaSolicitacao;
		var ss; 
		var mm; 
		var hh; 
		var dd;
		var tempo;
		
		
		//Usa as variváveis para obter a data de abertura da solicitação
		dataCompleta = solicitacao.dataHoraSolicitacao.split(" ")[0];
		horaCompleta = solicitacao.dataHoraSolicitacao.split(" ")[1];
		ano = NumberUtil.toInteger(dataCompleta.split("-")[0]);
		mes = NumberUtil.toInteger(dataCompleta.split("-")[1]);
		dia = NumberUtil.toInteger(dataCompleta.split("-")[2]);
		hora = NumberUtil.toInteger(horaCompleta.split(":")[0]);
		minuto = NumberUtil.toInteger(horaCompleta.split(":")[1]);
		segundo =  NumberUtil.toInteger(horaCompleta.split(":")[2].substring(0, horaCompleta.split(":")[2].length-2));
		dataAberturaSolicitacao = new Date(ano, mes-1, dia, hora, minuto, segundo); 
		
		//Utiliza as mesmas variáveis para pegar a data limite para encerramento da solicitação
		hoje = new Date();
		//Thiago Fernandes. Caso seja uma solicitação sem data hora limite prenchido, não deve ser feito a contabilização da data hora solicitação e data hora limite para saber a diferença enrte as duas.
		if (solicitacao.dataHoraLimite != '--' && solicitacao.dataHoraLimite != '') {
			dataCompleta = solicitacao.dataHoraLimite.split(" ")[0];
			horaCompleta = solicitacao.dataHoraLimite.split(" ")[1];
			ano = NumberUtil.toInteger(dataCompleta.split("-")[0]);
			mes = NumberUtil.toInteger(dataCompleta.split("-")[1]);
			dia = NumberUtil.toInteger(dataCompleta.split("-")[2]);
			hora = NumberUtil.toInteger(horaCompleta.split(":")[0]);
			minuto = NumberUtil.toInteger(horaCompleta.split(":")[1]);
			segundo =  NumberUtil.toInteger(horaCompleta.split(":")[2].substring(0, horaCompleta.split(":")[2].length-2));
			futuro = new Date(ano, mes-1, dia, hora, minuto, segundo);
			
			//calcula a diferença entre horas para saber o tempo restante
			ss = parseInt((futuro - hoje) / 1000); 
			mm = parseInt(ss / 60); 
			hh = parseInt(mm / 60); 
			dd = parseInt(hh / 24);
			 
			ss = ss - (mm * 60); // Determina a quantidade de segundos
			mm = mm - (hh * 60); // Determina a quantidade de minutos
			hh = hh - (dd * 24); // Determina a quantidade de horas
		
			if(dd <= 0 && ss <= 0 && mm <= 0 && hh <= 0 ){
				tempo = "<font color='RED'>" + i18n_message("solicitacao.tempo_esgotado") + "</font>"; 
			} else {
				tempo = '';
				if (dd > 0){
					tempo = tempo + dd + "dia(s), ";
				}
				tempo = tempo + hh + ":" + mm + ":" + ss;
			}
			//calcula porcentagem para o fim do tempo e criar uma barra de progresso com jquery
			var tempoTotalRequisicao = parseInt(futuro - dataAberturaSolicitacao)/1000;
			var tempoTotalRestante = parseInt(futuro - hoje) / 1000; 
			var elementTemp = document.getElementById(solicitacao.idComponenteRelogio);
			if(elementTemp != null){
				document.getElementById(solicitacao.idComponenteRelogio).innerHTML = tempo;
			}
			//$( "#" + solicitacao.idComponenteProgressBar).progressbar({value: ((tempoTotalRequisicao - tempoTotalRestante) / tempoTotalRequisicao) * 100 });			
		}
		

	}
	
	this.esconderTodos = function(solicitacao){
			var elementProg = document.getElementById(solicitacao.idComponenteProgressBar);
//			alert("#" + solicitacao.idComponenteProgressBar);
//			$( "#" + solicitacao.idComponenteProgressBar).progressbar().hide();
			document.getElementById(solicitacao.idComponenteRelogio).innerHTML = null;
	}
}