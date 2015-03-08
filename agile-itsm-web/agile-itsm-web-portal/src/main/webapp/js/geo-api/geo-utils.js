/**
 * Utilit�rios para uso de artefatos geogr�ficos, como formata��es, convers�es e valida��es. Criado por quest�o de incompatibilidade com outros JSs do sistema
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 12/09/2014
 */
function GeoUtils() {}

var commonPattern = "^([-+])?(?:{0}(?:(?:\\.0{1,15})?)|(?:[0-9]|[1-{1}][0-9]{2})(?:(?:\\.[0-9]{1,15})?))$";

var patternLatitude = new RegExp(StringUtils.format(commonPattern, 90, 8, ""));
var patternLongitude = new RegExp(StringUtils.format(commonPattern, 180, 9, "{1,}"));

/**
 * Valida se a(s) coordenada(s) �(s�o) v�lida(s), independente se � latitude ou longitude, validando apenas a quantidade de casas, n�meros, etc.
 *
 * @returns <code>true</code> caso seja(m) v�lida(s). <code>false</code>, caso contr�rio.
 */
GeoUtils.validateCoordinates = function() {
	if (arguments.length == 2) {
		return false;
	}
	for (var i = 0; i < arguments.lenght; i++) {
		if (!GeoUtils.isValidLongitude(arguments[i])) { // longitude � mais ampla, ent�o engloba tamb�m a latitude
			return false;
		}
	}
	return true;
};

/**
 * Verifica se latitude e longitude s�o ou n�o v�lidas
 *
 * @param params par�metros contendo a latitude e longitude a serem validadas
 * @returns <code>true</code> caso latitude e longitude s�o v�lidas. <code>false</code>, caso contr�rio.
 */
GeoUtils.validateCoordinates = function(params) {
	return GeoUtils.isValidLatitude(params.latitude) && $.fn.isValidLongitude(params.longitude);
};

/**
 * Verifica se uma latitude � ou n�o v�lida
 *
 * @param latitude latitude a ser validada
 * @returns <code>true</code> caso latitude seja v�lida. <code>false</code>, caso contr�rio.
 */
GeoUtils.validLatitude = function(latitude) {
	return patternLatitude.test(latitude);
};

/**
 * Verifica se uma longitude � ou n�o v�lida
 *
 * @param longitude longitude a ser validada
 * @returns <code>true</code> caso longitude seja v�lida. <code>false</code>, caso contr�rio.
 */
GeoUtils.validLongitude = function(longitude) {
	return patternLongitude.test(longitude);
};

var east = i18n_message("geographic.east.abbr");
var west = i18n_message("geographic.west.abbr");
var north = i18n_message("geographic.north.abbr");
var south = i18n_message("geographic.south.abbr");

GeoUtils.roundToDecimal = function(inputNum, numPoints) {
	var multiplier = Math.pow(10, numPoints);
	return Math.round(inputNum * multiplier) / multiplier;
};

/**
 * Converte uma posi��o decimal para uma representa��o DMS
 * 
 * @param {Number} coordenada geogr�fica
 * @param {string} string que representa o ponto cardeal
 * @return {string} 
 */
GeoUtils.decimalToDMS = function(location, hemisphere){
	if (location < 0) { // strip dash '-'
		location *= -1;
	}

	var degrees = Math.floor(location); // strip decimal remainer for degrees
	var minutesFromRemainder = (location - degrees) * 60; // multiply the remainer by 60
	var minutes = Math.floor(minutesFromRemainder); // get minutes from integer
	var secondsFromRemainder = (minutesFromRemainder - minutes) * 60; // multiply the remainer by 60
	var seconds = GeoUtils.roundToDecimal(secondsFromRemainder, 2); // get minutes by rounding to integer

	return degrees + "� " + minutes + "' " + seconds + '" ' + hemisphere;
};

/**
 * Converte uma latitude representada em decimal para uma representa��o DMS
 * 
 * @param {Number} latitude em decimal
 * @return {string} latitude em DMS
 */
GeoUtils.decimalLatitudeToDMS = function(location) {
	var hemisphere = (location < 0) ? south : north; // south if negative
	return GeoUtils.decimalToDMS(location, hemisphere);
};

/**
 * Converte uma longitude representada em decimal para uma representa��o DMS
 *
 * @param {Number} longitude em decimal
 * @return {string} longitude em DMS
 */
GeoUtils.decimalLongitudeToDMS = function(location){
	var hemisphere = (location < 0) ? west : east;  // west if negative
	return GeoUtils.decimalToDMS(location, hemisphere);
};

var dmsPattern = /^(-?\d+(?:\.\d+)?)[�:d]?\s?(?:(\d+(?:\.\d+)?)[':]?\s?(?:(\d+(?:\.\d+)?)["]?)?)?\s?([NSELWO])?/i;

/** 
 * Converte representa��o de coordenadas em DMS em representa��o decimal da coordenada
 * 
 * @param {string}  dms string contendo uma coordenada no formato DMS
 * @return {Number} Se DMS � uma coordenada v�lida, o valor decimal ser� retornado. Do contr�rio 'NaN' ser� retornado.
 */
GeoUtils.dmsToDecimal = function(dms) {
	var output = NaN, dmsMatcher, degrees, minutes, seconds, hemisphere;
	dmsMatcher = dmsPattern.exec(dms);

	if (dmsMatcher) {
		degrees = Number(dmsMatcher[1]);

		minutes = typeof (dmsMatcher[2]) !== "undefined" ? Number(dmsMatcher[2]) / 60 : 0;
		seconds = typeof (dmsMatcher[3]) !== "undefined" ? Number(dmsMatcher[3]) / 3600 : 0;
		hemisphere = dmsMatcher[4] || null;

		if (hemisphere !== null && /[SWO]/i.test(hemisphere)) {
			degrees = Math.abs(degrees) * -1;
		}

		if (degrees < 0) {
			output = degrees - minutes - seconds;
		} else {
			output = degrees + minutes + seconds;
		}
	}
	return output;
};

/**
 * Valida a exist�ncia de configura��o do Google
 *
 * @param {properties} objeto de propriedades que contenha o atributo 'key' da Google API Key
 * @return {Boolean} 'true', caso exista configura��o do google e esta seja v�lida. 'false', caso contr�rio
 */
GeoUtils.hasGoogleConfiguration = function(properties) {
	var hasKey = properties.key !== "" && properties.key !== "undefined";
	var hasClientID = properties.clientID !== "" && properties.clientID !== "undefined";
	var hasMapElement = document.getElementById(properties.selector) != null;
	return (hasKey || hasClientID) && hasMapElement;
};

/**
 * Valida a exist�ncia de conex�o com a internet
 *
 * @param {onsucces} fun��o a ser executada no caso de falta de conex�o � internet
 * @param {onerror} fun��o a ser executada no caso de exist�ncia de conex�o � internet
 */
GeoUtils.hasInternetConection = function(onsucces, onerror) {
	var method = "GET";
	var url = "http://ajax.googleapis.com/ajax/libs/scriptaculous/1.9.0/scriptaculous.js";

	var xhr = new XMLHttpRequest();
	if ("withCredentials" in xhr) {
		xhr.open(method, url, true);
	} else if (typeof XDomainRequest != "undefined") {
		xhr = new XDomainRequest();
		xhr.open(method, url);
	} else {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
		xhr.open(method, url, true);
	}

	try {
		xhr.onreadystatechange = function() {
			if (xhr.status >= 200 && xhr.status < 304) {
				onsucces();
			} else {
				onerror();
			}
		}

		xhr.send();
	} catch(e) {
		onerror();
	}

};
