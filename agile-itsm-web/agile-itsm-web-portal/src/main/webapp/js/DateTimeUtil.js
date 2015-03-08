var MONTH_NAMES=new Array('Janeiro','Fevereiro','Marï¿½o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro','Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez');
var DAY_NAMES=new Array('Domingo','Segunda','Terï¿½a','Quarta','Quinta','Sexta','Sabï¿½do','Dom','Seg','Ter','Qua','Qui','Sex','Sab');
function LZ(x) {return(x<0||x>9?"":"0")+x}

/*******************************************
	Esta Script depende de: NumberUtil.js
********************************************/
function DateTimeUtil(){}

/*
 * Pega apenas o dia de uma data string
 * Se o locale for igual a '' ou 'pt' ou 'es' o formato da data é 'dd/mm/yyyy'
 * Se o locale for igual a 'en' o formato da data é 'mm/dd/yyyy'
 */
DateTimeUtil.dia = function(Data){
	if (typeof(locale) === "undefined") locale = '';
	
	var r = '';
	var string_data = Data.toString();
	var dataSplit = string_data.split("/");
	
	if (locale == '' || locale == 'pt' || locale == 'es') {
		r = dataSplit[0];
	} else if (locale == 'en') {
		r = dataSplit[1];
	}
	
	if (r != '') {
		return r;
	} else {
		return false;
	}
}

/**
 * Retorna string com a data atual
 */
DateTimeUtil.getStringDataHoraHoje = function(){
	var currentTime = new Date();
	var month = currentTime.getMonth() + 1;
	var day = currentTime.getDate();
	var year = currentTime.getFullYear();
	return (day + "/" + month + "/" + year + " " + currentTime.getHours() + ":" + currentTime.getMinutes() + ":" + currentTime.getSeconds());
	
}

/*
 * Pega apenas o mês de uma data string
 * Se o locale for igual a '' ou 'pt' ou 'es' o formato da data é 'dd/mm/yyyy'
 * Se o locale for igual a 'en' o formato da data é 'mm/dd/yyyy'
 */
DateTimeUtil.mes = function(Data){
	if (typeof(locale) === "undefined") locale = '';
	
	var r = '';
	var string_data = Data.toString();
	var dataSplit = string_data.split("/");
	
	if (locale == '' || locale == 'pt' || locale == 'es') {
		r = dataSplit[1];
	} else if (locale == 'en') {
		r = dataSplit[0];
	}
	
	if (r != '') {
		return r;
	} else {
		return false;
	}
}

/*
 * Pega apenas o ano de uma data string
 * Se o locale for igual a '' ou 'pt' ou 'es' o formato da data é 'dd/mm/yyyy'
 * Se o locale for igual a 'en' o formato da data é 'mm/dd/yyyy'
 */
DateTimeUtil.ano = function(Data) {
	var r = '';
	var string_data = Data.toString();
	var dataSplit = string_data.split("/");
	
	r = dataSplit[2];
	
	if (r != '') {
		return r;
	} else {
		return false;
	}
}

/* Calcula a diferenca em dias de dois objetos Date */
DateTimeUtil.diferencaEmDias = function(dataInicio, dataFim){
	var diferencaAux = dataFim.getTime() - dataInicio.getTime();
	var diferenca = Math.floor(diferencaAux / (1000 * 60 * 60 * 24));
	return diferenca;
}

/* Calcula a diferenca em Minutos de dois objetos Date */
DateTimeUtil.diferencaEmMinutos = function(dataInicio, dataFim){
	var diferencaAux = dataFim.getTime() - dataInicio.getTime();
	var diferenca = Math.floor(diferencaAux / (1000 * 60));
	return diferenca;
}

/* Calcula a diferenca em Segundos de dois objetos Date */
DateTimeUtil.diferencaEmSegundos = function(dataInicio, dataFim){
	var diferencaAux = dataFim.getTime() - dataInicio.getTime();
	var diferenca = Math.floor(diferencaAux / 1000);
	return diferenca;
}
/* Formata um total de minutos no formato HH:MM. Exemplo 165 minutos eh igual a 02:45 */
DateTimeUtil.formataTotalMinutosEmHHMM = function(tempoEmMinutos){
   var tempoInteiro = Math.floor(tempoEmMinutos / 60);
   var tempoDecimal = tempoEmMinutos / 60;
   var difDecimalInteiro = tempoDecimal - tempoInteiro;
     
   var minutos = difDecimalInteiro * 60;
   
   minutos = Math.round(minutos);
   
   return NumberUtil.zerosAEsquerda(tempoInteiro,2) + ':' + NumberUtil.zerosAEsquerda(minutos, 2);	
}

/* Converte Data em formato String para Data em formato Date */
DateTimeUtil.converteData = function(data){
	if(data == null || data.length==0) return null;
	
	if (!DateTimeUtil.isValidDate(data)){
		return null;
	}
	
	var dia = DateTimeUtil.dia(data);
	var mes = DateTimeUtil.mes(data);
	var ano = DateTimeUtil.ano(data);
	
	mes = Math.floor(mes) - 1;
	
	var dateRetorno = new Date(ano, mes, dia);
	return dateRetorno;
}
/* Compara a Data Inicial com a Data Final e em caso de incoerencia apresenta a mensagem */
DateTimeUtil.comparaDatas = function(dataInic, dataFim, mensagem){	
	var d1 = DateTimeUtil.converteData(dataInic.value);
	var d2 = DateTimeUtil.converteData(dataFim.value);
	
	if(d1 == null || d2 == null){
		return true;
	}
	
	if(d1.getTime() > d2.getTime()){
		alert(mensagem);
		return false;
	}else{
		return true;
	}
}

/* Converte Hora em formato String para Hora em formato Object Date */
DateTimeUtil.converteHora = function(horaHHMMStr){
	if(horaHHMMStr==null || horaHHMMStr.length==0) return null;
	
	var hh = DateTimeUtil.hh(horaHHMMStr);
	var mm = DateTimeUtil.mm(horaHHMMStr);
	
	var horaRetorno = new Date(0, 0, 0, hh, mm, 0);
	return horaRetorno;
}

/* Converte Tempo em Minutos p/ Hora em formato String HH:MM */
DateTimeUtil.converteMinutosEmHHMM = function(tempoEmMinutos){
    var tempoInteiro = Math.floor(tempoEmMinutos / 60);
    var tempoDecimal = tempoEmMinutos / 60;
    var difDecimalInteiro = tempoDecimal - tempoInteiro;
      	
    var minutos = difDecimalInteiro * 60;
      	
    return NumberUtil.zerosAEsquerda(tempoInteiro, 2) + ':' + NumberUtil.zerosAEsquerda(minutos, 2);
}
/* Faz a comparacao entre 2 horas no formato String HH:MM e verifique se a hora inicial eh maior que a hora final */
DateTimeUtil.comparaHora = function(horarioInicial, horarioFinal){
	if((horarioInicial!='')&&(horarioFinal!='')){
		var horaInicial = DateTimeUtil.hh(horarioInicial);
		var minutoInicial = DateTimeUtil.mm(horarioInicial);
		var horaFinal = DateTimeUtil.hh(horarioFinal);
		var minutoFinal = DateTimeUtil.mm(horarioFinal);

		if(horaFinal<horaInicial){
			// hora Menor
			return false;
		}else if(horaFinal==horaInicial){
			if(minutoFinal<minutoInicial){
				//'Hora Igual=> minuto inferior
				return false;
			}
		}
	}
	return true;
}
/* Pega apenas as horas de uma hora string no formato hh:mm */
DateTimeUtil.hh = function(Hora_HHMM){
	var string_hora = Hora_HHMM.toString();
	var posicao_barra = string_hora.indexOf(":");
	if (posicao_barra!= -1)
	{
		var hh = string_hora.substring(0,posicao_barra);
		return hh;
	}
	else
	{
		return false;
	}
}
/* Pega apenas os minutos de uma hora string no formato hh:mm */
DateTimeUtil.mm = function(Hora_HHMM){
	var string_hora = Hora_HHMM.toString();
	var posicao_barra = string_hora.indexOf(":");
	if (posicao_barra!= -1)
	{
		var hh = string_hora.substr(posicao_barra+1);
		return hh;
	}
	else
	{
		return false;
	}
}
/* Verifica se eh Data */
DateTimeUtil.isDate = function(data) {	
  if (typeof(data) == 'string'){ //Se for string, verifica
	return DateTimeUtil.isValidDate(data);  	
  }else{ //Se for objeto, verifica tambem.
	return (data && data.toUTCString) ? true : false;
  }
}
/* Verifica se é uma data válida */
DateTimeUtil.isValidDate = function(Data) {
  	if (!DateTimeUtil.ano(Data)) return false;
  	if (!DateTimeUtil.mes(Data)) return false;
  	if (!DateTimeUtil.dia(Data)) return false;
	var day, month, year;
	
	day = DateTimeUtil.dia(Data);
	month = DateTimeUtil.mes(Data);
	year = DateTimeUtil.ano(Data);
	
    if (month < 1 || month > 12) {
        return false;
    }
    if (day < 1 || day > 31) {
        return false;
    }
    if ((month == 4 || month == 6 || month == 9 || month == 11) &&
        (day == 31)) {
        return false;
    }
    if (month == 2) {
        var leap = (year % 4 == 0 &&
           (year % 100 != 0 || year % 400 == 0));
        if (day>29 || (day == 29 && !leap)) {
            return false;
        }
    }
    if (year < 1900) {
    	return false;
    }
    return true;
}
/*
 * Verifica se uma hora eh Valida.
 *          Exemplo: passe como parametro 19:00
 *                   DateTimeUtil.isValidTime('19:00');
 */
DateTimeUtil.isValidTime = function(horaStr){
     var hora = horaStr;
     if(hora == null || hora.length == 0){
         return true;
     }
     if(hora.length != 5){
         alert('Formato de hora invï¿½lido (hh:mm)');
         return false;
     }
     
     var h  = hora.substring(0,2);
     var m  = hora.substring(3,5);
     
     if(h > 23 || h < 0){
         alert('Hora invï¿½lida(de 00 a 23)');
         return false;
     }
      
     if(m>59 || m<0){
         alert('Minuto invï¿½lido(de 00 a 59)');
         return false;
     }
     
     return true;
}
/*
 * Formata um objeto Date em String conforme passado como parametro.
 *    Exemplo:  var dateObj = new Date();
 *              DateTimeUtil.formatDate(dateObj, 'dd/MM/yyyy');
 *              retorna-->: 05/04/2007 (data do dia formatada).
 */
DateTimeUtil.formatDate = function(date,format) {
	format=format+"";
	var result="";
	var i_format=0;
	var c="";
	var token="";
	var y=date.getYear()+"";
	var M=date.getMonth()+1;
	var d=date.getDate();
	var E=date.getDay();
	var H=date.getHours();
	var m=date.getMinutes();
	var s=date.getSeconds();
	var yyyy,yy,MMM,MM,dd,hh,h,mm,ss,ampm,HH,H,KK,K,kk,k;
	// Convert real date parts into formatted versions
	var value=new Object();
	if (y.length < 4) {y=""+(y-0+1900);}
	value["y"]=""+y;
	value["yyyy"]=y;
	value["yy"]=y.substring(2,4);
	value["M"]=M;
	value["MM"]=LZ(M);
	value["MMM"]=MONTH_NAMES[M-1];
	value["NNN"]=MONTH_NAMES[M+11];
	value["d"]=d;
	value["dd"]=LZ(d);
	value["E"]=DAY_NAMES[E+7];
	value["EE"]=DAY_NAMES[E];
	value["H"]=H;
	value["HH"]=LZ(H);
	if (H==0){value["h"]=12;}
	else if (H>12){value["h"]=H-12;}
	else {value["h"]=H;}
	value["hh"]=LZ(value["h"]);
	if (H>11){value["K"]=H-12;} else {value["K"]=H;}
	value["k"]=H+1;
	value["KK"]=LZ(value["K"]);
	value["kk"]=LZ(value["k"]);
	if (H > 11) { value["a"]="PM"; }
	else { value["a"]="AM"; }
	value["m"]=m;
	value["mm"]=LZ(m);
	value["s"]=s;
	value["ss"]=LZ(s);
	while (i_format < format.length) {
		c=format.charAt(i_format);
		token="";
		while ((format.charAt(i_format)==c) && (i_format < format.length)) {
			token += format.charAt(i_format++);
			}
		if (value[token] != null) { result=result + value[token]; }
		else { result=result + token; }
	}
	return result;
}
