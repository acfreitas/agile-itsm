	var objTab = null;
	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
		}
	}

	function gerarInformacoes(){
		if (document.form.validate()){
			document.getElementById('divInfo').innerHTML = '<b>Aguarde...</b>';
			document.form.fireEvent('avalia');
		}
	}
	
	function selecionaExternalConnection(){
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent('selecionaExternalConnection');
	}
	
	function selecionaOrigemDestinoDados(){
		document.form.fireEvent('getOrigemDestinoDados');
	}
	
	function mostrarOrigem(idParm){
		var ed = $('#tt').datagrid('getEditor', {index:idParm,field:'idorigem'});
		$(ed.target).combobox({
			data:origem,
			valueField:'id',
			textField:'text'
		});
	}
	function mostrarDestino(idParm){
		var ed = $('#tt').datagrid('getEditor', {index:idParm,field:'iddestino'});
		$(ed.target).combobox({
			data:destino,
			valueField:'id',
			textField:'text'
		});
	}
	function carregarDados(){
		$('#tt').datagrid('acceptChanges');
		var rowsMatriz = $('#tt').datagrid('getRows');
		var dadosStrMatriz = '';
		var jsonAuxMatriz = '';
		for (var j = 0; j < rowsMatriz.length; j++){
			var json_data = JSON.stringify(rowsMatriz[j]);
			if (dadosStrMatriz != ''){
				dadosStrMatriz = dadosStrMatriz + ',';
			}
			dadosStrMatriz = dadosStrMatriz + json_data;					
		}
		if (dadosStrMatriz != ''){
			jsonAuxMatriz = jsonAuxMatriz + '{"MATRIZ": [' + dadosStrMatriz + ']}';
		}			
		document.form.jsonMatriz.value = jsonAuxMatriz;	
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent('carregarDados');
	}
	function gravar(){
		$('#tt').datagrid('acceptChanges');
		var rowsMatriz = $('#tt').datagrid('getRows');
		var dadosStrMatriz = '';
		var jsonAuxMatriz = '';
		for (var j = 0; j < rowsMatriz.length; j++){
			var json_data = JSON.stringify(rowsMatriz[j]);
			if (dadosStrMatriz != ''){
				dadosStrMatriz = dadosStrMatriz + ',';
			}
			dadosStrMatriz = dadosStrMatriz + json_data;					
		}
		if (dadosStrMatriz != ''){
			jsonAuxMatriz = jsonAuxMatriz + '{"MATRIZ": [' + dadosStrMatriz + ']}';
		}			
		document.form.jsonMatriz.value = jsonAuxMatriz;			
		document.form.fireEvent('gravar');
	}
	function LOOKUP_IMPORTCONFIG_select(id,desc){
		document.form.idImportConfig.value = id;
		JANELA_AGUARDE_MENU.show();
		limpaGrid();
		document.form.restore({idImportConfig:document.form.idImportConfig.value});
		$( '#tabs' ).tabs('select', 0);
	}
	function adicionaLinha(orig,dest,scri){
		try{
			$('#tt').datagrid('endEdit', lastIndex);
		}catch(e){}
		$('#tt').datagrid('appendRow',{
			idorigem:orig,
			iddestino:dest,
			script:scri
		});		
	}
	function limpaGrid(){
		origem = null;
		destino = null;		
		$('#tt').datagrid('rejectChanges');
		var rowsMatriz = $('#tt').datagrid('getRows');
		while(rowsMatriz.length > 0){
			for (var j = 0; j < rowsMatriz.length; j++){
				try{
					var index = $('#tt').datagrid('getRowIndex', rowsMatriz[j]);
					$('#tt').datagrid('deleteRow', index);
				}catch(e){
				}
			}
			$('#tt').datagrid('acceptChanges');
			var rowsMatriz = $('#tt').datagrid('getRows');
		}
		geraGrid();
	}
	function validaTipo(obj){
		if (obj.value != 'J'){
			alert(i18n_message("importmanager.implementacao.futura"));
			HTMLUtils.setValue('tipo', 'J');
		}
	}

		var origem = null;
		var destino = null;
		var products = [
		    {productid:'FI-SW-01',name:'Koi'},
		    {productid:'K9-DL-01',name:'Dalmation'},
		    {productid:'RP-SN-01',name:'Rattlesnake'},
		    {productid:'RP-LI-02',name:'Iguana'},
		    {productid:'FL-DSH-01',name:'Manx'},
		    {productid:'FL-DLH-02',name:'Persian'},
		    {productid:'AV-CB-01',name:'Amazon Parrot'}
		];
		
		function productFormatter(value){
			for(var i=0; i<products.length; i++){
				if (products[i].productid == value) return products[i].name;
			}
			return value;
		}
		$(function(){
			geraGrid();
		});
		var lastIndex;
		function geraGrid(){
			$('#tt').datagrid({
				toolbar:[{
					text:'append',
					iconCls:'icon-add',
					handler:function(){
						$('#tt').datagrid('endEdit', lastIndex);
						$('#tt').datagrid('appendRow',{
							idorigem:'',
							iddestino:'',
							script:''
						});
						lastIndex = $('#tt').datagrid('getRows').length-1;
						$('#tt').datagrid('selectRow', lastIndex);
						$('#tt').datagrid('beginEdit', lastIndex);
						mostrarOrigem(lastIndex);
						mostrarDestino(lastIndex);
					}
				},'-',{
					text:'delete',
					iconCls:'icon-remove',
					handler:function(){
						var row = $('#tt').datagrid('getSelected');
						if (row){
							var index = $('#tt').datagrid('getRowIndex', row);
							$('#tt').datagrid('deleteRow', index);
						}
					}
				},'-',{
					text:'accept',
					iconCls:'icon-save',
					handler:function(){
						$('#tt').datagrid('acceptChanges');
					}
				},'-',{
					text:'reject',
					iconCls:'icon-undo',
					handler:function(){
						$('#tt').datagrid('rejectChanges');
					}
				},'-',{
					text:'GetChanges',
					iconCls:'icon-search',
					handler:function(){
						var rows = $('#tt').datagrid('getChanges');
						alert('changed rows: ' + rows.length + ' lines');
					}
				}],
				onBeforeLoad:function(){
					$(this).datagrid('rejectChanges');
				},
				onClickRow:function(rowIndex){
					if (lastIndex != rowIndex){
						$('#tt').datagrid('endEdit', lastIndex);
						$('#tt').datagrid('selectRow', rowIndex);
						$('#tt').datagrid('beginEdit', rowIndex);
						mostrarOrigem(rowIndex);
						mostrarDestino(rowIndex);						
					}
					lastIndex = rowIndex;
				}
			});
		}
