var objTab = null;
addEvent(window, "load", load, false);
function load(){
	document.form.afterRestore = function () {
	}
}
function gravarForm(){
	JANELA_AGUARDE_MENU.show();
	document.form.save();
}
var checkedFlag = false;
function marcaTodas(){
	var arrayElements = document.getElementsByName('idServicoCopiarPara');

 	if(checkedFlag == false){
		for(var i=0;i<arrayElements.length;i++){
			if(arrayElements[i].type='checkbox'){
				arrayElements[i].checked = true;
			}
		}
		checkedFlag = true;
		return;
 	}else{
 		for(var i=0;i<arrayElements.length;i++){
			if(arrayElements[i].type='checkbox'){
				arrayElements[i].checked = false;
			}
		}
 		checkedFlag = false;
 		return;
 	}

}