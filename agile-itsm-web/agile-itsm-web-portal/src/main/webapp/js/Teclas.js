/*
	TRATAMENTO DE TECLAS
*/
var TROCA_ENTER_POR_TAB = false;
var TECLA_F1 = 111;
var TECLA_F2 = 113;
var TECLA_F3 = 114;
var TECLA_F4 = 115;
var TECLA_F5 = 116;
var TECLA_F6 = 117;
var TECLA_F7 = 118;
var TECLA_F8 = 119;
var TECLA_F9 = 120;
var TECLA_F10 = 121;
var TECLA_ENTER = 13;
var TECLA_DEL = 46;

function TECLAS_trataTeclas(evt) {
	evt = (evt) ? evt : ((event) ? event : null);
	if (isIE){
		evt.cancelBubble = true;
	}
	
	var node   = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
	
	var	shift  = evt.shiftKey;
 	var	ctrl   = evt.ctrlKey;
 	var	alt    = evt.altKey;
 	
 	var	tecla  = (evt.keyCode) ? evt.keyCode : evt.which;
 	
	if (tecla == TECLA_ENTER) {
		try{
			if (TROCA_ENTER_POR_TAB) {
				TECLAS_trataEnter(evt);
			}	
		}catch(e){
		}
		try{
			pressionou_TECLA_ENTER(shift, ctrl, alt, node);
			return false;
		}catch(e){
		}		
	} 	
	if (tecla == TECLA_F1) {
		try{
			pressionou_TECLA_F1(shift, ctrl, alt, node);
			return false;
		}catch(e){
		}
	} 	
	if (tecla == TECLA_F2) {
		try{
			pressionou_TECLA_F2(shift, ctrl, alt, node);
			return false;
		}catch(e){
		}
	} 	
	if (tecla == TECLA_F3) {
		try{
			pressionou_TECLA_F3(shift, ctrl, alt, node);
			return false;
		}catch(e){
		}
	} 	
	if (tecla == TECLA_F4) {
		try{
			pressionou_TECLA_F4(shift, ctrl, alt, node);
			return false;
		}catch(e){
		}
	} 	
	if (tecla == TECLA_F5) {
		try{
			pressionou_TECLA_F5(shift, ctrl, alt, node);
			return false;
		}catch(e){
		}
	} 	
	if (tecla == TECLA_F6) {
		try{
			pressionou_TECLA_F6(shift, ctrl, alt, node);
			return false;
		}catch(e){
		}
	} 	
	if (tecla == TECLA_F7) {
		try{
			pressionou_TECLA_F7(shift, ctrl, alt, node);
			return false;
		}catch(e){
		}
	} 	
	if (tecla == TECLA_F8) {
		try{
			pressionou_TECLA_F8(shift, ctrl, alt, node);
			return false;
		}catch(e){
		}
	} 	
	if (tecla == TECLA_F9) {
		try{
			pressionou_TECLA_F9(shift, ctrl, alt, node);
			return false;
		}catch(e){
		}
	} 	
	if (tecla == TECLA_F10) {
		try{
			pressionou_TECLA_F10(shift, ctrl, alt, node);
			return false;
		}catch(e){
		}
	} 	
	if (tecla == TECLA_DEL) {
		try{
			pressionou_TECLA_DEL(shift, ctrl, alt, node);
			return false;
		}catch(e){
		}
	} 	
}
addEvent(document, "keydown", TECLAS_trataTeclas, false);

function TECLAS_trataEnter(evt) {
	evt = (evt) ? evt : ((event) ? event : null);
	//if (isIE){
	//	evt.cancelBubble = true;
	//}
	
	var node   = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
	
	var	shift  = evt.shiftKey;
 	var	ctrl   = evt.ctrlKey;
 	var	alt    = evt.altKey;
 	
 	var	tecla  = (evt.keyCode) ? evt.keyCode : evt.which;
	
	if ((tecla == 13) && 
		(node.type != "button") && 
		(node.type != "textarea")) {
		if (isIE){
			evt.keyCode = 9; 		//TAB
		}else{
			var element = null;
			if (!isIE){
				element = evt.target;
			}else{
				element = evt.srcElement;
			}
			var f = element.form;
			if (f != null && f != undefined){
				for(var i = 0; i < f.length; i++){
					var elem = f.elements[i];
					if (elem.name == element.name){
						if (elem.type == "radio"){
							while(elem.name == element.name){
								if (elem.value == element.value) break;
								elem = f.elements[++i];
							}
						}					
						for(var j = i+1; j < f.length; j++){
							try{
								if (!f.elements[j].disabled){
									f.elements[j].focus();
									return false;
								}
							}catch(ex){
								continue;
							}					
						}
					}
				}
			}
		}
		return false;
	}
/*	if (tecla == 112) { //F1
		showAjuda();
		evt.returnValue = false;
		return false;
	}
	if (tecla == 118) {
		preenchePendencias();
		return false;
	}	*/
}