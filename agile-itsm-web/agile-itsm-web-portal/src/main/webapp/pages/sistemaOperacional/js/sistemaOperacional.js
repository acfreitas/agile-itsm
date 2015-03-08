var objTab = null;
addEvent(window, "load", load, false);
function load(){
	document.form.afterRestore = function () {
		$('.tabs').tabs('select', 0);
	}
}

function LOOKUP_SISTEMAOPERACIONAL_select(id,desc){
	document.form.restore({id:id});
}
