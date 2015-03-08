<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>

<!doctype html public "">
<html>
<head>
<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
<%
	String iframe = "";
	String idContrato = "";
	iframe = request.getParameter("iframe");
	if (iframe == null){
	    iframe = "false";
	}
%>
	<%@include file="/include/header.jsp"%>

	<title><fmt:message key="citcorpore.comum.title"/></title>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
	<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
    <script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/RequisicaoPessoalDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/EmailCurriculoDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/TelefoneCurriculoDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/CertificacaoCurriculoDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/EnderecoCurriculoDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/IdiomaCurriculoDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/FormacaoCurriculoDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/TriagemRequisicaoPessoalDTO.js"></script>
	<style type="text/css">
        .destacado {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
            font-size: 14px;
        }
        .table {
            border-left:1px solid #ddd;
        }

        .table th {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
        }

        .table td {
            border:1px solid #ddd;
            padding:4px 10px;
            border-top:none;
            border-left:none;
        }

        .table1 {
        }

        .table1 th {
            border:1px solid #ddd;
            padding:4px 10px;
            background:#eee;
        }

        .table1 td {
        }

         div#main_container {
            margin: 0px 0px 0px 0px;
        }

        .container_16
        {
            width: 100%;
            margin: 0px 0px 0px 0px;

            letter-spacing: -4px;
        }
    </style>
	<script>
		function carregouIFrame() {
	    	HTMLUtils.removeEvent(document.getElementById("frameUploadFoto"),"load", carregouIFrame);

	    	document.form.fireEvent('setaFoto');

	    	$("#POPUP_FOTO").dialog("close");
	    }

		function submitFormFoto(){
			addEvent(document.getElementById("frameUploadFoto"),"load", carregouIFrame);

		    document.formFoto.setAttribute("target","frameUploadFoto");
		    document.formFoto.setAttribute("method","post");
		    document.formFoto.setAttribute("enctype","multipart/form-data");
		    document.formFoto.setAttribute("encoding","multipart/form-data");

		    //submetendo
		    document.formFoto.submit();
		}

		/*Bruno.Aquino : Foi retirado o class de mascaras do campo cpf e inserido ao carregar a página a mascara do jquery para as linguas: Português e espanhol, para inglês não haverá mascara */
		function converteCpfParaPadraoIngles(){
			if('<%=request.getSession().getAttribute("locale")%>'=="en"){
				$("#cpf").unmask();
			}else{
				$("#cpf").mask("999.999.999-99");
			}
	    }

		function gravar(){
            var itens = HTMLUtils.getObjectsByTableId('tblTelefones');
            document.form.colTelefones_Serialize.value = ObjectUtils.serializeObjects(itens);

            itens = HTMLUtils.getObjectsByTableId('tblEmail');
            document.form.colEmail_Serialize.value = ObjectUtils.serializeObjects(itens);

            itens = HTMLUtils.getObjectsByTableId('tblFormacao');
            document.form.colFormacao_Serialize.value = ObjectUtils.serializeObjects(itens);

            itens = HTMLUtils.getObjectsByTableId('tblCertificacao');
            document.form.colCertificacao_Serialize.value = ObjectUtils.serializeObjects(itens);

            itens = HTMLUtils.getObjectsByTableId('tblIdioma');
            document.form.colIdioma_Serialize.value = ObjectUtils.serializeObjects(itens);

            itens = HTMLUtils.getObjectsByTableId('tblEnderecos');
            document.form.colEnderecos_Serialize.value = ObjectUtils.serializeObjects(itens);

			document.form.save();
		}

		function limpar(){
			document.getElementById('file_uploadAnexos').value = '';
			document.getElementById('divImgFoto').innerHTML = '';
			document.form.fireEvent("clear");
	    }

    	      function desabilitarTela() {
	          var f = document.form;
	          for(i=0;i<f.length;i++){
	              var el =  f.elements[i];
	              if (el.type != 'hidden') {
	                  if (el.disabled != null && el.disabled != undefined) {
	                      el.disabled = 'disabled';
		                  }
		              }
		          }
		      }


	      addEvent(window, "load", load, false);
	      function load(){
	    	  converteCpfParaPadraoIngles();
	          document.form.afterLoad = function () {
	          	  habilitarQtdeFilhos();
	          	  habilitarListaDeficiencias();
	          }
	          document.form.afterRestore = function () {
				$('.tabs').tabs('select', 0);
	          	habilitarQtdeFilhos();
	          	habilitarListaDeficiencias();
	          }
	      }

	     $(function() {
	        $("#POPUP_FICHA").dialog({
	            autoOpen : false,
	            width : 1000,
	            height : 600,
	            modal : true
	        });
	    });

	    $(function() {
	        $("#POPUP_CURRICULO").dialog({
	            autoOpen : false,
	            width : 1000,
	            height : 600,
	            modal : true
	        });
	    });
	    $(function() {
	        $("#POPUP_FOTO").dialog({
	            autoOpen : false,
	            width : 500,
	            height : 200,
	            modal : true
	        });
	    });

	    function gerarImgFicha(row, obj) {
            row.cells[0].innerHTML = '<img src="${ctx}/imagens/documents.png" onclick="abrePopup('+obj.idCurriculo+','+obj.idTriagem+', \'RH\')"/>'
            row.cells[1].innerHTML = '<img src="${ctx}/imagens/cracha.png" onclick="abrePopupCurriculo()"/>';
    	}

	    function abrePopup(idCurriculo, idTriagem, tipoEntrevista) {
	    	document.getElementById('fichaEntrevista').src = '${ctx}/pages/entrevistaCandidato/entrevistaCandidato.load?idCurriculo=' + idCurriculo + '&idTriagem=' + idTriagem + '&tipoEntrevista=' + tipoEntrevista;
	    	$("#POPUP_FICHA").dialog("open");
	    }

	    function abrePopupCurriculo() {
	    	$("#POPUP_CURRICULO").dialog("open");
	    }

	    function validar() {
        }

	    function getObjetoSerializado() {
            var obj = new CIT_RequisicaoPessoalDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            return ObjectUtils.serializeObject(obj);
     	}




	  //--------------------------------------- TELEFONE ---------------------------------------
     	marcaTelefone = function(row, obj){
			if (row.cells[0].innerHTML != '' && row.cells[0].innerHTML != '&nbsp;'){
				HTMLUtils.setValueColumnTable('tblTelefones', '&nbsp;', 1, null, 0);
				limpaTelefone();
			}else{
				HTMLUtils.setValueColumnTable('tblTelefones', '&nbsp;', 1, null, 0);
				row.cells[0].innerHTML = '<img src="${ctx}/imagens/excluirPeq.gif" border="0"/>';
			}
		};

		function LOOKUP_CURRICULOS_select(id,desc){
			document.form.restore({idCurriculo:id});
		}

		excluirTelefone = function(){
			var tbl = document.getElementById('tblTelefones');
			var iRowFimAux = tbl.rows.length;
			var b = false;
			for(var i = 1; i < iRowFimAux; i++){
				try{
					if (tbl.rows[i].cells[0].innerHTML != '' && tbl.rows[i].cells[0].innerHTML != "&nbsp;"){
						HTMLUtils.deleteRow('tblTelefones', i);
						b = true;
					}
				}catch(ex){
				}
			}
			if (!b){
				alert('Selecione o telefone que deseja excluir!');
			}
			limpaTelefone();
		};

		addTelefone = function(){
			var obj = new CIT_TelefoneCurriculoDTO();

			//Valida as Informacoes digitadas do Telefone
			if (StringUtils.isBlank(document.getElementById('telefone#idTipoTelefone').value)){
				alert('Informe o Tipo de Telefone!');
				document.getElementById('telefone#idTipoTelefone').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('telefone#ddd').value)){
				alert('Informe o DDD!');
				document.getElementById('telefone#ddd').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('telefone#numeroTelefone').value)){
				alert('Informe o Número do Telefone!');
				document.getElementById('telefone#numeroTelefone').focus();
				return;
			}

			obj.descricaoTipoTelefone = document.getElementById('telefone#idTipoTelefone').options[document.getElementById('telefone#idTipoTelefone').selectedIndex].text;
			obj.numeroTelefone = document.getElementById('telefone#numeroTelefone').value;
			obj.ddd = document.getElementById('telefone#ddd').value;

			HTMLUtils.addRow('tblTelefones', document.form, 'telefone', obj, ['', 'descricaoTipoTelefone', 'ddd', 'numeroTelefone'], null, '', null, marcaTelefone, null, false);
			HTMLUtils.applyStyleClassInAllCells('tblTelefones', 'celulaGrid');
			limpaTelefone();
			document.getElementById('telefone#idTipoTelefone').focus();
		};

		limpaTelefone = function() {
			document.getElementById('telefone#numeroTelefone').value = '';
			document.getElementById('telefone#ddd').value = '';
			document.getElementById('telefone#idTipoTelefone').value = '';
		}


		//--------------------------------------- EMAIL ---------------------------------------
		marcaEmail = function(row, obj){
			if (row.cells[0].innerHTML != '' && row.cells[0].innerHTML != '&nbsp;'){
				HTMLUtils.setValueColumnTable('tblEmail', '&nbsp;', 1, null, 0);
				document.getElementById('email#descricaoEmail').value = '';
			}else{
				HTMLUtils.setValueColumnTable('tblEmail', '&nbsp;', 1, null, 0);
				row.cells[0].innerHTML = '<img src="${ctx}/imagens/excluirPeq.gif" border="0"/>';
			}
			if (obj.principal == 'S'){
				document.getElementById('email#principal').checked = true;
			}else{
				document.getElementById('email#principal').checked = false;
			}
			HTMLUtils.setValues(document.form, 'email', obj);
		};

		excluirEmail = function(){
			var tbl = document.getElementById('tblEmail');
			var iRowFimAux = tbl.rows.length;
			var obj = new CIT_EmailCurriculoDTO();
			var b = false;
			for(var i = 1; i < iRowFimAux; i++){
				try{
					if (tbl.rows[i].cells[0].innerHTML != '' && tbl.rows[i].cells[0].innerHTML != "&nbsp;"){
						HTMLUtils.deleteRow('tblEmail', i);
						b = true;
					}
				}catch(ex){
				}
			}
			if (obj.principal == 'S'){
				document.getElementById('email#principal').checked = true;
			}else{
				document.getElementById('email#principal').checked = false;
			}
			if (!b){
				alert('Selecione o email que deseja excluir!');
			}

			validaPrincipal();

			document.getElementById('email#descricaoEmail').value = '';
		};

		addEmail = function(){
			var obj = new CIT_EmailCurriculoDTO();

			//Valida as Informacoes digitadas do Telefone
			if (StringUtils.isBlank(document.getElementById('email#descricaoEmail').value)){
				alert('Informe o email!');
				document.getElementById('email#descricaoEmail').focus();
				return;
			}

			if (document.getElementById('email#principal').checked){
				HTMLUtils.setValueColumnTable('tblEmail', '&nbsp;', 1, null, 1);
				obj.imagemEmailPrincipal = '<img src="${ctx}/imagens/ok.png" border="0"/>';
				obj.principal = 'S';
			}else{
				obj.imagemEmailPrincipal = '';
				obj.principal = 'N';
			}

			obj.descricaoEmail = document.getElementById('email#descricaoEmail').value;
			HTMLUtils.addRow('tblEmail', document.form, 'email', obj, ['', 'imagemEmailPrincipal', 'descricaoEmail'], null, '', null, marcaEmail, null, false);
			HTMLUtils.applyStyleClassInAllCells('tblEmail', 'celulaGrid');

			validaPrincipal();

			document.getElementById('email#descricaoEmail').value = '';

		};

		function validaPrincipal() {
			var objEmail = HTMLUtils.getObjectsByTableId('tblEmail');

				for(var i = 0; i < objEmail.length; i++){
					var obj = objEmail[i];
					if (obj.principal == 'S') {
						document.getElementById('divPrincipal').style.display = 'none';
						return;
					}
				}
				document.getElementById('divPrincipal').style.display = 'block';

		}




		//--------------------------------------- FORMAÇÃO ---------------------------------------
		marcaFormacao = function(row, obj){
			if (row.cells[0].innerHTML != '' && row.cells[0].innerHTML != '&nbsp;'){
				HTMLUtils.setValueColumnTable('tblFormacao', '&nbsp;', 1, null, 0);
				limpaFormacao();
			}else{
				HTMLUtils.setValueColumnTable('tblFormacao', '&nbsp;', 1, null, 0);
				row.cells[0].innerHTML = '<img src="${ctx}/imagens/excluirPeq.gif" border="0"/>';
			}
			HTMLUtils.setValues(document.form, 'formacao', obj);
		};

		excluirFormacao = function(){
			var tbl = document.getElementById('tblFormacao');
			var iRowFimAux = tbl.rows.length;
			var b = false;
			for(var i = 1; i < iRowFimAux; i++){
				try{
					if (tbl.rows[i].cells[0].innerHTML != '' && tbl.rows[i].cells[0].innerHTML != "&nbsp;"){
						HTMLUtils.deleteRow('tblFormacao', i);
						b = true;
					}
				}catch(ex){
				}
			}
			if (!b){
				alert('Selecione a Formação que deseja excluir!');
			}
			limpaFormacao();
			document.getElementById('formacao#idTipoFormacao').focus();
		};

		addFormacao = function(){
			var obj = new CIT_FormacaoCurriculoDTO();

			//Valida as Informacoes digitadas do Telefone
			if (StringUtils.isBlank(document.getElementById('formacao#idTipoFormacao').value)){
				alert('Informe o Tipo de Formação!');
				document.getElementById('formacao#idTipoFormacao').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('formacao#instituicao').value)){
				alert('Informe a Instituição!');
				document.getElementById('formacao#instituicao').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('formacao#idSituacao').value)){
				alert('Informe a Situação!');
				document.getElementById('formacao#idSituacao').focus();
				return;
			}

			obj.descricaoTipoFormacao = document.getElementById('formacao#idTipoFormacao').options[document.getElementById('formacao#idTipoFormacao').selectedIndex].text;
			obj.descricaoSituacao = document.getElementById('formacao#idSituacao').options[document.getElementById('formacao#idSituacao').selectedIndex].text;
			obj.descricao = document.getElementById('formacao#descricao').value;
			obj.instituicao = document.getElementById('formacao#instituicao').value;

			HTMLUtils.addRow('tblFormacao', document.form, 'formacao', obj, ['', 'descricaoTipoFormacao' , 'instituicao' , 'descricaoSituacao' , 'descricao'], null, '', null, marcaFormacao, null, false);
			HTMLUtils.applyStyleClassInAllCells('tblFormacao', 'celulaGrid');
			limpaFormacao();
			document.getElementById('formacao#idTipoFormacao').focus();
		};

		limpaFormacao = function() {
			document.getElementById('formacao#idTipoFormacao').value = '';
			document.getElementById('formacao#instituicao').value = '';
			document.getElementById('formacao#idSituacao').value = '';
		}



		//--------------------------------------- CERTIFICAÇÃO ---------------------------------------
		marcaCertificacao = function(row, obj){
			if (row.cells[0].innerHTML != '' && row.cells[0].innerHTML != '&nbsp;'){
				HTMLUtils.setValueColumnTable('tblCertificacao', '&nbsp;', 1, null, 0);
				limpaCertificacao();
			}else{
				HTMLUtils.setValueColumnTable('tblCertificacao', '&nbsp;', 1, null, 0);
				row.cells[0].innerHTML = '<img src="${ctx}/imagens/excluirPeq.gif" border="0"/>';
			}
			HTMLUtils.setValues(document.form, 'certificacao', obj);
		};

		excluirCertificacao = function(){
			var tbl = document.getElementById('tblCertificacao');
			var iRowFimAux = tbl.rows.length;
			var b = false;
			for(var i = 1; i < iRowFimAux; i++){
				try{
					if (tbl.rows[i].cells[0].innerHTML != '' && tbl.rows[i].cells[0].innerHTML != "&nbsp;"){
						HTMLUtils.deleteRow('tblCertificacao', i);
						b = true;
					}
				}catch(ex){
				}
			}
			if (!b){
				alert('Selecione a Certificação que deseja excluir!');
			}
			limpaCertificacao();
			document.getElementById('certificacao#descricao').focus();
		};

		addCertificacao = function(){
			var obj = new CIT_CertificacaoCurriculoDTO();

			//Valida as Informacoes digitadas do Telefone
			if (StringUtils.isBlank(document.getElementById('certificacao#descricao').value)){
				alert('Informe a Descrição da certificação!');
				document.getElementById('certificacao#descricao').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('certificacao#versao').value)){
				alert('Informe a Versão da certificação!');
				document.getElementById('certificacao#versao').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('certificacao#validade').value)){
				alert('Informe a Validade da certificação!');
				document.getElementById('certificacao#validade').focus();
				return;
			}

			obj.descricao = document.getElementById('certificacao#descricao').value;
			obj.versao = document.getElementById('certificacao#versao').value;
			obj.validade = document.getElementById('certificacao#validade').value;

			HTMLUtils.addRow('tblCertificacao', document.form, 'certificacao', obj, ['', 'descricao' , 'versao' , 'validade' ], null, '', null, marcaCertificacao, null, false);
			HTMLUtils.applyStyleClassInAllCells('tblCertificacao', 'celulaGrid');
			limpaCertificacao();
			document.getElementById('certificacao#descricao').focus();
		};

		limpaCertificacao = function() {
			document.getElementById('certificacao#descricao').value = '';
			document.getElementById('certificacao#versao').value = '';
			document.getElementById('certificacao#validade').value = '';
		}


		//--------------------------------------- IDIOMA ---------------------------------------
		addIdioma = function(){
			var obj = new CIT_IdiomaCurriculoDTO();

			//Valida as Informacoes digitadas do Endereco
			if (StringUtils.isBlank(document.getElementById('idioma#idIdioma').options[document.getElementById('idioma#idIdioma').selectedIndex].value)){
				alert('Informe o Idioma!');
				document.getElementById('idioma#idIdioma').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('idioma#idNivelEscrita').options[document.getElementById('idioma#idNivelEscrita').selectedIndex].value)){
				alert('Informe o Nível de escrita!');
				document.getElementById('idioma#idNivelEscrita').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('idioma#idNivelLeitura').options[document.getElementById('idioma#idNivelLeitura').selectedIndex].value)){
				alert('Informe o Nível de leitura!');
				document.getElementById('idioma#idNivelLeitura').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('idioma#idNivelConversa').options[document.getElementById('idioma#idNivelConversa').selectedIndex].value)){
				alert('Informe o Nível de conversação!');
				document.getElementById('idioma#idNivelConversa').focus();
				return;
			}
			obj.descricaoIdioma = document.getElementById('idioma#idIdioma').options[document.getElementById('idioma#idIdioma').selectedIndex].text;
			obj.nivelEscrita = document.getElementById('idioma#idNivelEscrita').options[document.getElementById('idioma#idNivelEscrita').selectedIndex].text;
			obj.nivelLeitura = document.getElementById('idioma#idNivelLeitura').options[document.getElementById('idioma#idNivelLeitura').selectedIndex].text;
			obj.nivelConversa = document.getElementById('idioma#idNivelConversa').options[document.getElementById('idioma#idNivelConversa').selectedIndex].text;

			var strNivel = '<table width="100%"><tr><td class="celulaGrid"><b>Escrita: </b>' + obj.nivelEscrita + '</td></tr>';
			strNivel = strNivel + '<tr><td class="celulaGrid"><b>Leitura: </b>' + obj.nivelLeitura + '</td></tr>';
			strNivel = strNivel + '<tr><td class="celulaGrid"><b>Conversação: </b>' + obj.nivelConversa + '</td></tr>';
			strNivel = strNivel + '</table>';

			obj.detalhamentoNivel = strNivel;

			HTMLUtils.addRow('tblIdioma', document.form, 'idioma', obj, ['', 'descricaoIdioma', 'detalhamentoNivel'], null, '', null, marcaIdioma, null, false);
			HTMLUtils.applyStyleClassInAllCells('tblIdioma', 'celulaGrid');

			document.getElementById('idioma#idIdioma').value = '';
			document.getElementById('idioma#idNivelEscrita').value = '';
			document.getElementById('idioma#idNivelLeitura').value = '';
			document.getElementById('idioma#idNivelConversa').value = '';
		};

		marcaIdioma = function(row, obj){
			if (row.cells[0].innerHTML != '' && row.cells[0].innerHTML != '&nbsp;'){
				HTMLUtils.setValueColumnTable('tblIdioma', '&nbsp;', 1, null, 0);
				limpaIdioma();
			}else{
				HTMLUtils.setValueColumnTable('tblIdioma', '&nbsp;', 1, null, 0);
				row.cells[0].innerHTML = '<img src="${ctx}/imagens/excluirPeq.gif" border="0"/>';
			}
			HTMLUtils.setValues(document.form, 'idioma', obj);
		};

		excluirIdioma = function(){
			var tbl = document.getElementById('tblIdioma');
			var iRowFimAux = tbl.rows.length;
			var b = false;
			for(var i = 1; i < iRowFimAux; i++){
				try{
					if (tbl.rows[i].cells[0].innerHTML != '' && tbl.rows[i].cells[0].innerHTML != "&nbsp;"){
						HTMLUtils.deleteRow('tblIdioma', i);
						b = true;
					}
				}catch(ex){
				}
			}
			if (!b){
				alert('Selecione o Idioma que deseja excluir!');
			}
			limpaIdioma();
			document.getElementById('idioma#idIdioma').focus();
		};

		limpaIdioma = function(){
			document.getElementById('idioma#idIdioma').value = '';
			document.getElementById('idioma#idNivelEscrita').value = '';
			document.getElementById('idioma#idNivelLeitura').value = '';
			document.getElementById('idioma#idNivelConversa').value = '';
		}


		//--------------------------------------- ENDEREÇO  ---------------------------------------
		addEndereco = function(){
			var obj = new CIT_EnderecoCurriculoDTO();

			//Valida as Informacoes digitadas do Endereco
			if (StringUtils.isBlank(document.getElementById('endereco#idTipoEndereco').options[document.getElementById('endereco#idTipoEndereco').selectedIndex].value)){
				alert('Informe o Tipo de Endereço!');
				document.getElementById('endereco#idTipoEndereco').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('endereco#logradouro').value)){
				alert('Informe o Logradouro!');
				document.getElementById('endereco#logradouro').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('endereco#idUF').options[document.getElementById('endereco#idUF').selectedIndex].value)){
				alert('Informe a UF!');
				document.getElementById('endereco#idUF').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('endereco#nomeCidade').value)){
				alert('Informe a Cidade!');
				document.getElementById('endereco#nomeCidade').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('endereco#nomeBairro').value)){
				alert('Informe o Bairro!');
				document.getElementById('endereco#nomeBairro').focus();
				return;
			}

			obj.descricaoTipoEndereco = document.getElementById('endereco#idTipoEndereco').options[document.getElementById('endereco#idTipoEndereco').selectedIndex].text;

			var strDetalhamento = '<table width="100%"><tr><td class="celulaGrid"><b>Endereço: </b>' + document.getElementById('endereco#logradouro').value + ' ' + document.getElementById('endereco#complemento').value + '</td></tr>';
			strDetalhamento = strDetalhamento + '<tr><td class="celulaGrid"><b>Bairro:</b> ' + document.getElementById('endereco#nomeBairro').value + ' <b>Cidade:</b> '+ document.getElementById('endereco#nomeCidade').value + ' - ' + document.getElementById('endereco#idUF').options[document.getElementById('endereco#idUF').selectedIndex].text + '</td></tr>';
			strDetalhamento = strDetalhamento + '<tr><td class="celulaGrid"><b>CEP:</b> ' + document.getElementById('endereco#cep').value + '</td></tr>';
			strDetalhamento = strDetalhamento + '</table>';

			obj.detalhamentoEndereco = strDetalhamento;

			HTMLUtils.addRow('tblEnderecos', document.form, 'endereco', obj, ['', 'principal', 'descricaoTipoEndereco', 'detalhamentoEndereco'], null, '', null, marcaEndereco, null, false);
			HTMLUtils.applyStyleClassInAllCells('tblEnderecos', 'celulaGrid');

			limpaEndereco();
			document.getElementById('endereco#idTipoEndereco').focus();
		};

		atuEndereco = function(){
			var obj = new CIT_EnderecoCurriculoDTO();
			//Valida as Informacoes digitadas do Endereco
			if (StringUtils.isBlank(document.getElementById('endereco#logradouro').value)){
				alert('Informe o Logradouro!');
				document.getElementById('endereco#logradouro').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('endereco#idUF').options[document.getElementById('endereco#idUF').selectedIndex].value)){
				alert('Informe a UF!');
				document.getElementById('endereco#idUF').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('endereco#nomeCidade').value)){
				alert('Informe a Cidade!');
				document.getElementById('endereco#nomeCidade').focus();
				return;
			}
			if (StringUtils.isBlank(document.getElementById('endereco#nomeBairro').value)){
				alert('Informe o Bairro!');
				document.getElementById('endereco#nomeBairro').focus();
				return;
			}

			obj.descricaoTipoEndereco = document.getElementById('endereco#idTipoEndereco').options[document.getElementById('endereco#idTipoEndereco').selectedIndex].text;


			var strDetalhamento = '<table width="100%"><tr><td class="celulaGrid"><b>Endereço: </b>' + document.getElementById('endereco#logradouro').value + ' ' + document.getElementById('endereco#complemento').value + '</td></tr>';
			strDetalhamento = strDetalhamento + '<tr><td class="celulaGrid"><b>Bairro:</b> ' + document.getElementById('endereco#nomeBairro').value + ' <b>Cidade:</b> '+ document.getElementById('endereco#nomeCidade').value + ' - ' + document.getElementById('endereco#idUF').options[document.getElementById('endereco#idUF').selectedIndex].text + '</td></tr>';
			strDetalhamento = strDetalhamento + '<tr><td class="celulaGrid"><b>CEP:</b> ' + document.getElementById('endereco#cep').value + '</td></tr>';
			strDetalhamento = strDetalhamento + '</table>';

			obj.detalhamentoEndereco = strDetalhamento;

			HTMLUtils.updateRow('tblEnderecos', document.form, 'endereco', obj, ['', 'principal', 'descricaoTipoEndereco', 'detalhamentoEndereco'], null, '', null, marcaEndereco, null, index, false);

			limpaEndereco();
			document.getElementById('divAdd').style.display = 'block';
			document.getElementById('divAltera').style.display = 'none';
			document.getElementById('endereco#idTipoEndereco').focus();
		};

		novoEndereco = function(){
			limpaEndereco();
			document.getElementById('divAdd').style.display = 'block';
			document.getElementById('divAltera').style.display = 'none';
			HTMLUtils.setValueColumnTable('tblEnderecos', '&nbsp;', 1, null, 0);
			document.getElementById('endereco#idTipoEndereco').focus();
		};

		limpaEndereco = function(){
			document.getElementById('endereco#logradouro').value='';
			document.getElementById('endereco#cep').value='';
			document.getElementById('endereco#complemento').value='';
			document.getElementById('endereco#idTipoEndereco').value='';
			document.getElementById('principal').value='';
			document.getElementById('endereco#nomeCidade').value='';
			document.getElementById('endereco#nomeBairro').value='';
			document.getElementById('endereco#idUF').value=' ';
		};

		marcaEndereco = function(row, obj){
			if (row.cells[0].innerHTML != '' && row.cells[0].innerHTML != '&nbsp;'){
				HTMLUtils.setValueColumnTable('tblEnderecos', '&nbsp;', 1, null, 0);
				limpaEndereco();
				document.getElementById('divAdd').style.display='block';
				document.getElementById('divAltera').style.display='none';
			}else{
				limpaEndereco();

				HTMLUtils.setValueColumnTable('tblEnderecos', '&nbsp;', 1, null, 0);
				row.cells[0].innerHTML = '<img src="${ctx}/imagens/excluirPeq.gif" border="0"/>';
				HTMLUtils.setValues(document.form, 'endereco', obj);
				if (obj.principal == 'S'){
					document.getElementById('principal').checked = true;
				}else{
					document.getElementById('principal').checked = false;
				}
				document.getElementById('divAdd').style.display = 'none';
				document.getElementById('divAltera').style.display = 'block';
			}
		};

		excluirEndereco = function(){
			var tbl = document.getElementById('tblEnderecos');
			var iRowFimAux = tbl.rows.length;
			var b = false;
			for(var i = 1; i < iRowFimAux; i++){
				try{
					if (tbl.rows[i].cells[0].innerHTML != '' && tbl.rows[i].cells[0].innerHTML != "&nbsp;"){
						HTMLUtils.deleteRow('tblEnderecos', i);
						b = true;
					}
				}catch(ex){
				}
			}
			if (!b){
				alert('Selecione o endereço que deseja excluir!');
			}
			limpaEndereco();
			document.getElementById('divAdd').style.display = 'block';
			document.getElementById('divAltera').style.display = 'none';
		};


     	habilitarListaDeficiencias = function()
		{
			if(document.form.portadorNecessidadeEspecial[1].checked)
				document.getElementById('divDeficiencias').style.display = 'block';
			else
				document.getElementById('divDeficiencias').style.display = 'none';
		}

		function habilitarQtdeFilhos()
		{
			if(document.form.filhos[1].checked)
				document.getElementById('divFilhos').style.display = 'block';
			else
				document.getElementById('divFilhos').style.display = 'none';
		}

		function habilitaDivGraduacao() {
			document.getElementById('divPos').style.display = 'none';
			document.getElementById('divGraduacao').style.display = 'block';
		}
		function habilitaDivPos() {
			document.getElementById('divPos').style.display = 'block';
			document.getElementById('divGraduacao').style.display = 'none';
		}
		function desabilitaDiv() {
			document.getElementById('divPos').style.display = 'none';
			document.getElementById('divGraduacao').style.display = 'none';
		}
		calculaIdade = function(){
			document.getElementById('spnIdade').innerHTML = '';
			if (!StringUtils.isBlank(document.getElementById('dataNascimento').value)){
				//document.form.fireEvent('calculaIdade');
			}
		};
		function LOOKUP_CURRICULOS_select(id, desc) {
			document.form.restore({
				idCurriculo : id
			});
		}

</script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da pagina
			if (iframe != null && iframe.equalsIgnoreCase("true")) {%>
<style>
	div#main_container {
		margin: 10px 10px 10px 10px;
	}
</style>
<%}%>
</head>
<body>
  <div id="wrapper">
		<%if (iframe == null || iframe.equalsIgnoreCase("false")) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
<!-- Conteudo -->
	<div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null || iframe.equalsIgnoreCase("false")) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
		<%}%>

		<div class="flat_area grid_16">
				<h2><fmt:message key="curriculo.cadastroCurriculo"/></h2>
		</div>
		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><fmt:message key="curriculo.cadastroCurriculo"/></a>
				</li>
				<li><a href="#tabs-2" class="round_top"><fmt:message key="curriculo.pesquisa" />
				</a></li>
			</ul>
			<a href="#" class="toggle">&nbsp;</a>
			<div class="toggle_container">
			    <div id="tabs-1" class="block">
				   <div class="section">
						<form name='form' action='${ctx}/pages/cadastroCurriculo/cadastroCurriculo'>
					        <input type='hidden' name='colTelefones_Serialize' id='colTelefones_Serialize'/>
							<input type='hidden' name='colEnderecos_Serialize' id='colEnderecos_Serialize'/>
							<input type='hidden' name='colIdioma_Serialize' id='colIdioma_Serialize'/>
							<input type='hidden' name='colEmail_Serialize' id='colEmail_Serialize'/>
							<input type='hidden' name='colFormacao_Serialize' id='colFormacao_Serialize'/>
							<input type='hidden' name='colExperienciaProfissional_Serialize' id='colExperienciaProfissional_Serialize'/>
							<input type='hidden' name='colCertificacao_Serialize' id='colCertificacao_Serialize'/>
							<input type='hidden' name='idCurriculo' id='idCurriculo'/>
				        	<div class="col_100">
				        		<div class="col_80">
				        	 		<div class="col_100">
						        	    <div class="col_15">
						        	         <fieldset style="height: 70px;">
						                         <label class="campoObrigatorio"><fmt:message key="CPF" /></label>
						                         <div>
						                              <input type='text' name='cpf' id="cpf" size='15' maxlength="14"/>
						                          </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_66">
						        	         <fieldset style="height: 70px;">
						                         <label class="campoObrigatorio"><fmt:message key="Nome" /></label>
						                         <div>
						                             <input type='text' name='nome' size="50" maxlength="100"  />
						                          </div>
						                     </fieldset>
						        	    </div>
							        	 <div class="col_15">
						        	         <fieldset style="height: 70px;">
						                         <label class="campoObrigatorio"><fmt:message key="Data de nascimento" /></label>
						                         <div>
						                             <input type='text' name='dataNascimento' size="10" maxlength="10" class='Format[Date] Description[Data de Nascimento]' onblur='calculaIdade()'  />
						                         </div>
						                         <div>
						                             <span id='spnIdade' style='color: red; font-weight: bold' style='display:none'></span>
						                         </div>
						                     </fieldset>
						        	    </div>
					        	    </div>
				        	 		<div class="col_100">
						        	    <div class="col_15">
						        	    	<fieldset style="height: 70px;">
						                         <label class="campoObrigatorio"><fmt:message key="Sexo" /></label>
						                         <div>
						                             <input type='radio' name='sexo' value='M'/>Masculino
						                         </div>
						                         <div>
						                             <input type='radio' name='sexo' value='F'/>Feminino
						                          </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_20">
						        	    	<fieldset style="height: 70px;">
						                         <label class="campoObrigatorio"><fmt:message key="Estado Civil" /></label>
						                         <div>
						                             <select name='estadoCivil'>
													    <option value="0" >-- Selecione --</option>
														<option value="1" >Solteiro(a)</option>
														<option value="2" >Casado(a)</option>
						                                <option value="3" >Companheiro(a)</option>
						                                <option value="4" >União estável</option>
														<option value="5" >Separado(a)</option>
						                                <option value="6" >Divorciado(a)</option>
														<option value="7" >Viuvo(a)</option>
														</select>
						                          </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_16">
						        	    	<fieldset style="height: 70px;">
						                         <label class="campoObrigatorio"><fmt:message key="Possui filho(s)" /></label>
						                         <div>
						                             <input type="radio" name="filhos" value="N"
						                                    class="Valid[Required] Description[Possui filho(s)]"
						                                    onclick="habilitarQtdeFilhos()"/>Não
						                         </div>
						                         <div>
						                             <input type="radio" name="filhos" value="S"
						                                    class="Valid[Required] Description[Possui filho(s)]"
						                                    onclick="habilitarQtdeFilhos()"/>Sim
						                         </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_15" id="divFilhos" style="display: none;">
						        	    	<fieldset style="height: 70px;">
						                         <label class="campoObrigatorio"><fmt:message key="Quantos" /></label>
						                         <div>
						                            <select name="qtdeFilhos">
							                         <option value="">-- Selecione -- </option>
							                         <option value="1">1</option>
							                         <option value="2">2</option>
							                         <option value="3">3</option>
							                         <option value="4">4</option>
							                         <option value="5">5 ou mais</option>
							                        </select>
						                          </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_30">
						        	    	<fieldset style="height: 70px;">
						                         <label class="campoObrigatorio"><fmt:message key="Naturalidade" /></label>
						                         <div>
						                              <select name='idNaturalidade'>
						  								</select>
						                          </div>
						                     </fieldset>
						        	    </div>
				        	    	</div>
						        	 <div class="col_100">
						        	    <div class="col_40">
						        	    	<fieldset style="height: 70px;">
						                         <label class="campoObrigatorio"><fmt:message key="Cidade Natal" /></label>
						                         <div>
						                              <input type='text' name='cidadeNatal' size="50" maxlength="80"  />
						                          </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_33">
						        	    	<fieldset style="height: 70px;">
						                         <label class="campoObrigatorio"><fmt:message key="Portador de necessidades especiais" /></label>
						                         <div>
						                             <input type="radio" name="portadorNecessidadeEspecial" value="N"
						                                    class="Valid[Required] Description[Portador de necessidade especial]"
						                                    onclick="habilitarListaDeficiencias()"/>Não
						                         </div>
						                         <div>
						                             <input type="radio" name="portadorNecessidadeEspecial" value="S"
						                                    class="Valid[Required] Description[Portador de necessidade especial]"
						                                    onclick="habilitarListaDeficiencias()"/>Sim
						                         </div>
						                     </fieldset>
						        	    </div>
						        	    <div class="col_20" id="divDeficiencias" style="display: none;">
						        	    	<fieldset style="height: 70px;">
						                         <label class="campoObrigatorio"><fmt:message key="Qual" /></label>
						                         <div>
						                            <select name="idItemListaTipoDeficiencia">
						                             <option value="">-- Selecione -- </option>
						                             <option value="1">Auditiva</option>
						                             <option value="2">Física</option>
						                             <option value="3">Mental</option>
						                             <option value="4">Múltipla</option>
						                             <option value="5">Visual</option>
						                            </select>
						                          </div>
						                     </fieldset>
						        	    </div>
				        	    	</div>
				        		</div>
				        	  	<div id='divFoto' class="col_20">
									  <input type='button' name='btnAddFoto' value='Definir Foto' style='width: 167px; text-align: center' onclick='$("#POPUP_FOTO").dialog("open");'/>
									  <div id='divImgFoto' style='border: 1px solid black; width: 165px; height: 170px;'>
								      </div>
								</div>
							 </div>
				        	 <div class="col_100">
				        	 	<div class="col_33">
				        	 	  <fieldset style="height: auto; border: solid 1px #ccc">
				  					<label class="campoObrigatorio" style='font-size:16px'><fmt:message key="Email's" /></label>
				  					<table style="width: 100%;">
				  						<tr>
				  							<td class="campoEsquerdaSemTamanho">
				  								E-mail:
				  							</td>
				  							<td>
				  								<input type='text' name='email#descricaoEmail' size='40' maxlength="50"  />
				  							</td>
				  						</tr>
				  						<tr>
				  							<td>
				  								&nbsp;
				  							</td>
				  							<td>
				  								<div id='divPrincipal' style='display:block;'>
				  								  <input type='checkbox' name='email#principal' value="S"/>E-mail principal
				  								</div>
				  							</td>
				  						</tr>
				  						<tr>
				  							<td colspan='6'>
				  								<br><br><br><br><br>
				  								<div style='height: auto; overflow: auto; border: 1px solid #ccc'>
					  								<table id='tblEmail' class="table" width='100%'>
					  									<tr>
					  										<th width="1%">
					  											&nbsp;
					  										</th>
					  										<th width="5%">
					  											Principal
					  										</th>
					  										<td>
					  											E-mail
					  										</th>
					  									</tr>
					  								</table>
				  								</div>
				  							</td>
				  							<td>
				  							   <img src="${ctx}/imagens/add.png"  onclick='addEmail();' style="cursor: pointer;" />
				  							</td>
				  							<td>
				  							   <img src="${ctx}/imagens/delete.png"  onclick='excluirEmail();' style="cursor: pointer;" />
				  							</td>
				  						</tr>
				  					</table>
				  				</fieldset>
				        	 	</div>

								<div class="col_33">
								  <fieldset style="height: 250px;">
				  					<label class="campoObrigatorio" style='font-size:16px'><fmt:message key="Telefones" /></label>
				  					<table style="width: 100%;">
				  						<tr>
				  							<td class="campoEsquerdaSemTamanho">
				  								Tipo:
				  							</td>
				  							<td>
				  								<select name='telefone#idTipoTelefone'>
				  								    <option value="">-- Selecione --</option>
													<option  value="1">Residencial</option>
													<option  value="2">Comercial</option>
													<option  value="3">Celular</option>
													<option  value="4">Fax</option>
													<option  value="5">Ramal</option>
													<option  value="6">Recado</option>
				  								</select>
				  							</td>
				  							<td class="campoEsquerdaSemTamanho">
				  								&nbsp;DDD:
				  							</td>
				  							<td>
				  								<input type='text' name='telefone#ddd' size='2' maxlength="2" class='Format[Numero]'/>
				  							</td>
				  							<td class="campoEsquerdaSemTamanho">
				  								&nbsp;Número:
				  							</td>
				  							<td>
				  								<input type='text' name='telefone#numeroTelefone' size='15' maxlength="15" class='Format[Numero]'></td>
				  							<td>
				  								&nbsp;
				  							</td>
				  						</tr>
				  						<tr>
				  							<td colspan='6'>
				  								<br><br><br><br><br><br>
				  								<div style='height: auto; overflow: auto; border: 1px solid #CCC'>
					  								<table id='tblTelefones' class="table" width='100%'>

					  									<tr>
					  										<th width="1%">
					  											&nbsp;
					  										</th>
					  										<th width="43%">
					  											Tipo
					  										</th>
					  										<th width="10%">
					  											DDD
					  										</th>
					  										<th>
					  											Número
					  										</th>
					  									</tr>
					  								</table>
				  								</div>
				  							</td>
				  							<td>
				  							   <img src="${ctx}/imagens/add.png"  onclick='addTelefone();' style="cursor: pointer;" />
				  							</td><td>
				  							   <img src="${ctx}/imagens/delete.png"  onclick='excluirTelefone();' style="cursor: pointer;" />
				  							</td>
				  						</tr>
				  					</table>
				  				</fieldset>
								</div>

				        	 	<div class="col_33">
				        	 	  <fieldset style="height: 250px;">
				  					<label style='font-size:16px'><fmt:message key="Certificações" /></label>
				  					<table style="width: 100%;">
					  						<tr>
					  							<td class="campoEsquerdaSemTamanho">
					  								Descrição:
					  							</td>
					  							<td>
					  								<input type='text' name='certificacao#descricao' size='40' maxlength="50"  />
					  							</td>
					  						</tr>
					  						<tr>
					  							<td class="campoEsquerdaSemTamanho">
					  								Versão:
					  							</td>
					  							<td>
					  								<input type='text' name='certificacao#versao' size='40' maxlength="50"  />
					  							</td>
					  						</tr>
					  						<tr>
					  							<td class="campoEsquerdaSemTamanho">
					  								Ano de validade:
					  							</td>
					  							<td>
					  								<input type='text' name='certificacao#validade' size="4" maxlength="4" class='Format[Numero]'  />
					  							</td>
					  						</tr>
					  						<tr>
					  							<td colspan='6'>
					  								<div style='height: auto; overflow: auto; border: 1px solid #CCC'>
						  								<table id='tblCertificacao' class="table" width='100%'>

						  									<tr>
						  										<th width="1%">
						  											&nbsp;
						  										</th>
						  										<th>
						  											Descrição
						  										</th>
						  										<th width="30%">
						  											Versão
						  										</th>
						  										<th width="25%">
						  											Validade
						  										</th>
						  									</tr>
						  								</table>
					  								</div>
					  							</td>
					  							<td>
				  							       <img src="${ctx}/imagens/add.png"  onclick='addCertificacao();' style="cursor: pointer;" />
				  							    </td>
				  							    <td>
				  							       <img src="${ctx}/imagens/delete.png"  onclick='excluirCertificacao();' style="cursor: pointer;" />
				  							    </td>
					  						</tr>
					  					</table>
					  				</fieldset>

				        	 	  </div>

				        	 </div>

				        	 <div class="col_100">
				        	    <div class="col_33">
				        	       <fieldset style="height: 430px;">
				  					<label class="campoObrigatorio" style='font-size:16px'><fmt:message key="Endereços" /></label>
				  					<table style="width: 100%;">
				 						<tr>
				 							<td class="campoEsquerdaSemTamanho">
				 								Tipo de Endereço:
				 							</td>
				 							<td>
				 								<table>
				 									<tr>
				 										<td>
						  								<select name='endereco#idTipoEndereco'>
													        <option value="">-- Selecione --</option>
													        <option  value="1">Residencial</option>
															<option  value="2">Comercial</option>
						  								</select>
				 										</td>
				 										<td class="campoEsquerdaSemTamanho">
				 											&nbsp;CEP:
				 										</td>
				 										<td>
				 											<input type='text' name='endereco#cep' size="9" maxlength="9" class='Format[Numero]'  />
				 										</td>
				 									</tr>
				 								</table>
				 							</td>
				 						</tr>
				 						<tr>
				 							<td>
				 								&nbsp;
				 							</td>
				 							  <td>
				 								 <input type='checkbox' name='principal' value="S"/>Endereço de correspondência
				 							  </td>
				 						</tr>
				 						<tr>
				 							<td class="campoEsquerdaSemTamanho">
				 								Endereço:
				 							</td>
				 							<td>
				 								<input type='text' name='endereco#logradouro' size="80" maxlength="80"  />
				 							</td>
				 						</tr>
				 						<tr>
				 							<td class="campoEsquerdaSemTamanho">
				 								Complemento:
				 							</td>
				 							<td>
				 								<input type='text' name='endereco#complemento' size="40" maxlength="40"  />
				 							</td>
											<td>
				 							  &nbsp;
				 							</td>
				 						</tr>
				 						<tr>
				 							<td class="campoEsquerdaSemTamanho">
				 								UF:
				 							</td>
				 							<td>
				 								<select name='endereco#idUF'></select>
				 							</td>
				 						</tr>
				 						<tr>
				 							<td class="campoEsquerdaSemTamanho">
				 								Cidade:
				 							</td>
				 							<td>
				 								<input type='text' name='endereco#nomeCidade' size="60" maxlength="60"  />
				 							</td>
				 						</tr>
				 						<tr>
				 							<td class="campoEsquerdaSemTamanho">
				 								Bairro:
				 							</td>
				 							<td>
				 								<table>
				 									<tr>
				 										<td>
				 											<input type='text' name='endereco#nomeBairro' size="62" maxlength="60"  />
				 										</td>
				 									</tr>
				 								</table>
				 							</td>
				 							<td>
				 							  &nbsp;
				 							</td>

				 						</tr>
				 						<tr>
				 							<td colspan='2'>
				 								<div style='height: 150px; overflow: auto; border: 1px solid black'>
				  								<table id='tblEnderecos' class="table" width='100%'>
				  									<tr>
				  										<th width="1%">
				  											&nbsp;
				  										</th>
				  										<th width="3%">
				  											Corresp.
				  										</th>
				  										<th width="15%">
				  											Tipo
				  										</th>
				  										<th >
				  											Endereço
				  										</th>
				  									</tr>
				  								</table>
				 								</div>
				 							</td>
											<td>
				  							   <div id='divAdd'>
				  							     <img src="${ctx}/imagens/add.png"  onclick='addEndereco();' style="cursor: pointer;" />
				  							   </div>
				  							   <div id='divAltera' style='display:none;'>
				  							   	 <img src="${ctx}/imagens/refresh.png"  onclick='atuEndereco();' style="cursor: pointer;" />
				  							   </div>
				  							</td>
				  							<td>
				  							    <img src="${ctx}/imagens/delete.png"  onclick='excluirEndereco();' style="cursor: pointer;" />
				  							</td>
				 						</tr>
				 					</table>
				 				</fieldset>
				        	    </div>

				    			<div class="col_33">
				    				<fieldset style="height: 430px;">
				  					<label  style='font-size:16px'><fmt:message key="Idiomas" /></label>
				  					<table style="width: 100%;" width='100%'>
				  						<tr>
				  							<td class="campoEsquerdaSemTamanho">
				  								Idioma:
				  							</td>
				  							<td>
				  								<select name='idioma#idIdioma'>
				  								</select>
				  							</td>
				  						</tr>
				  						<tr><td><br></td></tr>
				  						<tr>
				  						  <td class="campoEsquerda">
				  								Escrita:
				  							</td>
				  							<td>
											    <select name='idioma#idNivelEscrita'>
											        <option  value="">-- Selecione --</option>
											        <option  value="1">Não tem</option>
													<option  value="2">Intermediária</option>
													<option  value="3">Boa</option>
													<option  value="4">Avançada</option>
				  								</select>
				                            </td>
				  						</tr>
				  						<tr>
				  							<td class="campoEsquerda">
				  								Leitura:
				  							</td>
				  							<td>
											        <select name='idioma#idNivelLeitura'>
											        <option  value="">-- Selecione --</option>
											        <option  value="1">Não tem</option>
													<option  value="2">Intermediária</option>
													<option  value="3">Boa</option>
													<option  value="4">Avançada</option>
				  								</select>
				                            </td>
				  						</tr>
				  						<tr>
				  							<td class="campoEsquerda">
				  								Conversação:
				  							</td>
				  							<td>
											        <select name='idioma#idNivelConversa'>
											        <option  value="">-- Selecione --</option>
											        <option  value="1">Não tem</option>
													<option  value="2">Intermediária</option>
													<option  value="3">Boa</option>
													<option  value="4">Avançada</option>
				  								</select>
				                           </td>
				  						</tr>
				  						<tr>
				  							<td colspan='2'>
				  								<br><br><br><br><br><br><br><br>
				  								<div style='height: 150px; overflow: auto; border: 1px solid black'>
					  								<table id='tblIdioma' class="table" width='100%'>
					  									<tr>
					  										<th width="1%">
					  											&nbsp;
					  										</th>
					  										<th width="50%">
					  											Descrição
					  										</th>
					  										<th>
					  											Níveis de conhecimento
					  										</th>
					  									</tr>
					  								</table>
					  							</div>
				  							</td>
				  							<td>
				  							   <img src="${ctx}/imagens/add.png"  onclick='addIdioma();' style="cursor: pointer;" />
				  							</td>
				  							<td>
				  							   <img src="${ctx}/imagens/delete.png"  onclick='excluirIdioma();' style="cursor: pointer;" />
				  							</td>
				  						</tr>
				  					</table>
				  				</fieldset>
				    			</div>

				             	<div class="col_33">
				             		<fieldset style="height: 430px;">
				  					<label class="campoObrigatorio" style='font-size:16px'><fmt:message key="Formação" /></label>
				  					<table style="width: 100%;">
					  						<tr>
					  							<td class="campoEsquerdaSemTamanho">
					  								Formação:*
					  							</td>
					  							<td>
					  								<table>
					  									<tr>
					  										<td>
								  								<select name='formacao#idTipoFormacao'>
															        <option  value="" onclick="desabilitaDiv()">-- Selecione --</option>
															        <option  value="1" onclick="desabilitaDiv()">Ensino Fundamental</option>
																	<option  value="2" onclick="desabilitaDiv()">Ensino Médio</option>
																	<option  value="3" onclick="habilitaDivGraduacao()">Graduação</option>
																	<option  value="4" onclick="habilitaDivPos()">Pós-Graduação</option>
																	<option  value="5" onclick="habilitaDivPos()">Mestrado</option>
																	<option  value="6" onclick="habilitaDivPos()">Doutorado</option>
								  								</select>
					  										</td>
					  									</tr>
					  								</table>
					  							</td>
					  						</tr>
					  						<tr>
					  						  <td class="campoEsquerda">
													       Situação:*
													  </td>
													  <td>
															<select name='formacao#idSituacao'>
														        <option value="">-- Selecione --</option>
														        <option  value="1">Cursou</option>
																<option  value="2">Cursando</option>
																<option  value="3">Trancado/Interrompido</option>
							 								</select>
													  </td>
					  						</tr>
					  						<tr>
					  						  <td class="campoEsquerda">
															Instituição de ensino:*
													  </td>
													  <td>
															<input type='text' name='formacao#instituicao' size="50" maxlength="100"  />
													  </td>
					  						</tr>
					  						<tr>
					  							<td colspan='2'>
					  							    <div id='divGraduacao' style='display:none;'>
															  <table>
															      <tr>
															        <td class="campoEsquerda">
															          Curso:*
															        </td>
															        <td>
															         <input type='text' name='formacao#descricao' size="40" maxlength="40"  />
															        </td>
															      </tr>
															  </table>
														    </div>
														    <div id='divPos' style='display:none;'>
															  <table>
															      <tr>
															        <td class="campoEsquerda">
															          Descrição da formação:*
															        </td>
															        <td>
															         <input type='text' name='formacao#descricao' size="40" maxlength="40"  />
															        </td>
															      </tr>
															  </table>
														    </div>
														</td>
					  						   </tr>
					  						   <tr>
					  							<td colspan='2'>
					  								<br><br><br><br><br><br><br><br><br><br><br>
					  								<div style='height: 150px; overflow: auto; border: 1px solid black'>
						  								<table id='tblFormacao' class="table" width='100%'>
						  									<tr>
						  										<th width="1%">
						  											&nbsp;
						  										</th>
						  										<th width="15%">
						  											Formação&nbsp;
						  										</th>
						  										<th width="30%">
						  											&nbsp;Instituição&nbsp;
						  										</th>
						  										<th width="15%">
						  											&nbsp;Situação&nbsp;
						  										</th>
						  										<th>
						  											&nbsp;Descrição
						  										</th>
						  									</tr>
						  								</table>
					  								</div>
					  							</td>
					  							<td>
				  							      <img src="${ctx}/imagens/add.png"  onclick='addFormacao();' style="cursor: pointer;" />
				  							    </td>
				  							    <td>
				  							       <img src="${ctx}/imagens/delete.png"  onclick='excluirFormacao();' style="cursor: pointer;" />
				  							    </td>
					  						</tr>
					  					</table>
					  				</fieldset>
				             	</div>
				             </div>
				             <div class="col_100">
				               <fieldset style="height: 130px;">
				             	<label><fmt:message key="entrevista.observacoes" /></label>
				             	<textarea rows="5" cols="122" name='observacoesEntrevista'></textarea>
				               </fieldset>
				             </div>
		             		<div class="col_66">
								<div class="col_100">
										<fieldset>
										    <label  style='font-size:16px'><fmt:message key="Currículo"/></label>
											<cit:uploadControl  style="height:50%;width:100%; border-bottom:1px solid #DDDDDD ; border-top:1px solid #DDDDDD "  title="<fmt:message key='citcorpore.comum.anexos' />" id="uploadAnexos" form="document.form" action="/pages/upload/upload.load" disabled="false" />
										</fieldset>
								</div>
							</div>
							<div class="col_100">
				                <button type='button' name='btnGravar' class="light"  onclick='gravar();'>
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar"/></span>
								</button>
								<button type="button" name='btnLimpar' class="light" onclick='limpar();'>
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar"/></span>
								</button>
								<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
										<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
										<span><fmt:message key="citcorpore.comum.excluir" /></span>
								</button>
		                    </div>
		    			</form>
		    			<div id="POPUP_FOTO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
					    	<iframe name='frameUploadFoto' id='frameUploadFoto' src='about:blank' height="0" width="0"/></iframe>
							<form name='formFoto' method="post" ENCTYPE="multipart/form-data" action='${ctx}/pages/uploadFile/uploadFile.load'>
								<table>
									<tr>
										<td>
											Arquivo com a Foto:
										</td>
										<td>
											<input type='file' name='arquivo'/>
										</td>
									</tr>
									<tr>
										<td>
											<input type='button' name='btnEnviarImagem' value='Enviar' onclick='submitFormFoto()'/>
										</td>
									</tr>
								</table>
							</form>
				      </div>
				  </div>
		        </div>
		        <div id="tabs-2" class="block">
						<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'lockupName='LOOKUP_CURRICULOS' id='LOOKUP_CURRICULOS' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
				</div>
		    </div>
       </div>
    </div>
  </div>
</body>

<%@include file="/include/footer.jsp"%>
</html>
