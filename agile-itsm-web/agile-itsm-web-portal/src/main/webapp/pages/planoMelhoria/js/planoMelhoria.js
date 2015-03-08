
	function abrirPopup(id, text){

	}
	function gravarPlano(){
		if (!document.form.validate()){
			return;
		}
		document.form.fireEvent('gravarPlano');		
	}
	function imprimirDocumentoPlanoMelhoria(){
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent('imprimirDocumentoPlanoDeMelhoria');	
	}
	function gravarObjetivo(){
		if (!document.formObj.validate()){
			return;
		}		
		document.formObj.fireEvent('gravarObjetivo');
	}
	function gravarAcao(){
		if (!document.formAcao.validate()){
			return;
		}			
		document.formAcao.fireEvent('gravarAcao');
	}
	function gravarMonitoramento(){
		if (!document.formMonitoramento.validate()){
			return;
		}		
		document.formMonitoramento.fireEvent('gravarMonitoramento');
	}
	function novo(){
		JANELA_AGUARDE_MENU.show();
		document.form.idPlanoMelhoria.value = '';
		document.form.clear();
		document.getElementById('divCadastroPlano').style.display = 'block';
		document.getElementById('divAcoes').style.display = 'none';
		document.getElementById('divObjetivos').style.display = 'none';	
		document.getElementById('divMonitoramento').style.display = 'none';
		JANELA_AGUARDE_MENU.hide();
	}
	function editaContrato(id){
		JANELA_AGUARDE_MENU.show();
		document.form.idPlanoMelhoria.value = id;
		document.getElementById('divCadastroPlano').style.display = 'block';
		document.getElementById('divAcoes').style.display = 'none';
		document.getElementById('divObjetivos').style.display = 'none';	
		document.getElementById('divMonitoramento').style.display = 'none';
		document.form.fireEvent('editaPlano');
	}
	function editaObjetivo(id){
		JANELA_AGUARDE_MENU.show();
		document.formObj.idObjetivoPlanoMelhoria.value = id;
		document.getElementById('divCadastroPlano').style.display = 'none';
		document.getElementById('divAcoes').style.display = 'none';
		document.getElementById('divObjetivos').style.display = 'block';	
		document.getElementById('divMonitoramento').style.display = 'none';
		document.formObj.fireEvent('editaObjetivo');		
	}
	function editaAcao(id){
		JANELA_AGUARDE_MENU.show();
		document.formAcao.idAcaoPlanoMelhoria.value = id;
		document.getElementById('divCadastroPlano').style.display = 'none';
		document.getElementById('divAcoes').style.display = 'block';
		document.getElementById('divObjetivos').style.display = 'none';	
		document.getElementById('divMonitoramento').style.display = 'none';
		document.formAcao.fireEvent('editaAcao');		
	}
	function editaMonitoramento(id){
		JANELA_AGUARDE_MENU.show();
		document.formMonitoramento.idObjetivoMonitoramento.value = id;
		document.getElementById('divCadastroPlano').style.display = 'none';
		document.getElementById('divAcoes').style.display = 'none';
		document.getElementById('divObjetivos').style.display = 'none';	
		document.getElementById('divMonitoramento').style.display = 'block';
		document.formMonitoramento.fireEvent('editaMonitoramento');			
	}
	function novoObjetivo(id){
		JANELA_AGUARDE_MENU.show();
		document.formObj.clear();
		document.formObj.idPlanoMelhoriaAux1.value = id;
		document.getElementById('divCadastroPlano').style.display = 'none';
		document.getElementById('divAcoes').style.display = 'none';
		document.getElementById('divObjetivos').style.display = 'block';
		document.getElementById('divMonitoramento').style.display = 'none';
		JANELA_AGUARDE_MENU.hide();
	}
	function novaAcao(id){
		JANELA_AGUARDE_MENU.show();
		document.formAcao.clear();
		document.formAcao.idObjetivoPlanoMelhoria.value = id;
		document.getElementById('divCadastroPlano').style.display = 'none';
		document.getElementById('divAcoes').style.display = 'block';
		document.getElementById('divObjetivos').style.display = 'none';
		document.getElementById('divMonitoramento').style.display = 'none';
		JANELA_AGUARDE_MENU.hide();		
	}
	function novoMonitoramento(id){
		JANELA_AGUARDE_MENU.show();
		document.formMonitoramento.clear();
		document.formMonitoramento.idObjetivoPlanoMelhoria.value = id;
		document.getElementById('divCadastroPlano').style.display = 'none';
		document.getElementById('divAcoes').style.display = 'none';
		document.getElementById('divObjetivos').style.display = 'none';
		document.getElementById('divMonitoramento').style.display = 'block';
		JANELA_AGUARDE_MENU.hide();		
	}	
	function carregaContratos(){
		document.form.fireEvent('carregaContratos');		
	}

