<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<%@page import="br.com.centralit.bpm.util.Enumerados"%>
<!doctype html public "">
<html>
<head>
	<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
	<%
	    String id = request.getParameter("id");
	    String strRegistrosExecucao = (String)request.getAttribute("strRegistrosExecucao");
	    if (strRegistrosExecucao == null){
			strRegistrosExecucao = "";
	    }

	    String tarefaAssociada = (String)request.getAttribute("tarefaAssociada");
	    if (tarefaAssociada == null){
	    	tarefaAssociada = "N";
	    }

	    String nomeServico = request.getParameter("nomeServico");
	    if (nomeServico == null)
	    	nomeServico = "";
	    else
	    	nomeServico = " Serviço: " + nomeServico;
	%>
	<%@include file="/include/header.jsp"%>

    <%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/menu/menuConfig.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
	<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

	<style type="text/css">
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

		.linhaSubtituloGridOcorr
		{

		    font-size		:12px;
		    color			:#000000;
		    font-family		:Arial;
		    background-color: #d3d3d3;
		    BORDER-RIGHT: thin outset;
		    BORDER-TOP: thin outset;
		    BORDER-LEFT: thin outset;
		    BORDER-BOTTOM: thin outset;
			text-align: center;
			font-weight: bold;
			height: 15px;
			line-height: 15px;
		}

	</style>

	<script>


	 	function fecharPopup(popup){
			$(popup).dialog('close');
		}

		fechar = function(){
			$("#POPUP_INFO_INSERCAO").dialog("close");
			window.parent.fecharSolicitacao();
		}

		var oFCKeditor = new FCKeditor("descricao") ;
	    function onInitQuestionario(){
	        oFCKeditor.BasePath = '${ctx}/fckeditor/';
	        //oFCKeditor.Config['ToolbarStartExpanded'] = false ;

	        oFCKeditor.ToolbarSet   = 'Basic' ;
	        oFCKeditor.Width = '100%' ;
	        oFCKeditor.Height = '300' ;
	        oFCKeditor.ReplaceTextarea() ;
	    }
	    HTMLUtils.addEvent(window, "load", onInitQuestionario, false);

		function limpar() {
			document.form.clear();
			document.form.fireEvent("limpar");
	        var oEditor = FCKeditorAPI.GetInstance("descricao") ;
	        oEditor.SetData('');
		}

		function verificaContrato() {
			if (document.form.idContrato.value == ''){
				alert(i18n_message("solicitacaoservico.validacao.contrato"));
				return;
			}
		}

		function mostraMensagemInsercao(msg){
			document.getElementById('divMensagemInsercao').innerHTML = msg;
			$("#POPUP_INFO_INSERCAO").dialog("open");
		}


	</script>

	<style>
		div#main_container {
			margin: 10px 10px 10px 10px;
		}
		select#idItemConfiguracaoPai .corRed{
			background-color: #999999;
		}

	</style>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>
	<script type="text/javascript">
		function setDataEditor(){
			var oEditor = FCKeditorAPI.GetInstance("descricao") ;
		    oEditor.SetData(document.form.descricao.value);
		}
		function setValueToDataEditor(){
			window.setTimeout(function() {
        			setDataEditor();
    			},1000);
		}
		function informaDadosSolicitacao(data){
			if(data=='' || data == null || data==undefined || data=='0' )
            {
              console.log("data is null");
            }else
            {
                var data_string='';
                data_string = data.split(";");

                var nome 		= data_string[0];
                var email		= data_string[1];
                var telefone	= data_string[2];

                $('#nomecontato').val(nome);
                $('#emailcontato').val(email);
                $('#telefonecontato').val(telefone);
            }
		}
	    function salvar() {
			var oEditor = FCKeditorAPI.GetInstance("descricao") ;
			document.form.descricao.value = oEditor.GetXHTML();
			document.form.descricao.innerHTML = oEditor.GetXHTML();
			if (!document.form.validate()){
				return;
			}
			if(document.form.descricao.innerHTML == "<br />" || document.form.descricao.innerHTML == "&lt;br /&gt;"){
				alert('Informe a descrição!');
				return;
			}
			if (document.form.descricao.value == '' || document.form.descricao.value == '&nbsp;'
				|| document.form.descricao.value == '<p></p>'){
				alert('Informe a descrição!');
				return;
			}
			var desc = document.form.descricao.value;
			var string = "<%= nomeServico %>" + "<br/>" + desc;
			document.form.descricao.value = string;

			document.form.fireEvent('salvar');
		}

	    $(function() {
			$("#POPUP_INFO_INSERCAO").dialog({
				autoOpen : false,
				width : 400,
				height : 280,
				modal : true,
				close: function(event, ui) {
				}
			});
	    });

	    function mostraMensagemInsercao(msg){
			document.getElementById('divMensagemInsercao').innerHTML = msg;
			$("#POPUP_INFO_INSERCAO").dialog("open");
		}
	</script>
	<div id="wrapper">
		 <div id="main_container" class="main_container container_16 clearfix" style='margin: 10px 10px 10px 10px'>
			<div id='divTituloSolicitacao' class="flat_area grid_16">
				<h2><fmt:message key="solicitacaoServico.solicitacao"/></h2>
			</div>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div class="">

						<div id='divScript' style='display:none; width: 500px; height: 200px; border:1px solid black; overflow: auto'>
							<fmt:message key="citcorpore.comum.selecionescript" />
						</div>

						<form name='form' action='${ctx}/pages/solicitacaoServicoMultiContratosPortal2/solicitacaoServicoMultiContratosPortal2'>
							<div class="columns clearfix">
								<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
								<input type='hidden' name='idSolicitante' id='idSolicitante' />
								<input type='hidden' name='impacto' id='impacto' />
								<input type='hidden' name='urgencia' id='urgencia' />
								<input type='hidden' name='idTipoDemandaServico' id='idTipoDemandaServico' />
								<input type='hidden' name='idUnidade' id='idUnidade' />
								<input type='hidden' name='idServico' id='idServico' />
								<input type='hidden' name='idContrato' id='idContrato' />
								<input type='hidden' name='situacao' id='situacao' value='EmAndamento'/>
								<input type='hidden' name='registroexecucao' id='registroexecucao' value=''/>

								<div class="col_100">
									<fieldset>
										<label><fmt:message key="servico.servico" /></label>
										<div id="divServico"></div>
									</fieldset>
								</div>

								<div class="col_100">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="contrato.contrato" /></label>
										<div>
											<select name='idContrato' class=" Valid[Required] Description[Contrato]" onchange="document.form.fireEvent('carregaServico');"></select>
										</div>
									</fieldset>
								</div>

								<div class="col_100">
									<h2 class="section">
										<fmt:message key="solicitacaoServico.informacaoContato" />
									</h2>
								</div>
								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="solicitacaoServico.nomeDoContato" /></label>
											<div>
												<input id="nomecontato" name="nomecontato" readonly="readonly" size="40"/>

											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="solicitacaoServico.emailContato" /></label>
											<div><input id="emailcontato" name="emailcontato" readonly="readonly" size="35"/></div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><fmt:message key="solicitacaoServico.telefoneDoContato" /></label>
											<div><input id="telefonecontato" name="telefonecontato" readonly="readonly" size="30"/></div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message
												key="solicitacaoServico.descricao" /></label>
										<div>
											<textarea id="descricao" name="descricao" cols='70' rows='5' class="Valid[Required] Description[Descrição]"></textarea>
										</div>
									</fieldset>
								</div>
								<div class="box grid_16 tabs" id='divBotoes'>
									<button type='button' name='btnGravar' class="light" onclick='salvar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" /></span>
									</button>
							    </div>
							</div>
						</form>
					</div>
				</div>
			</div>

		</div>
	</div>

</body>
<div id="POPUP_INFO_INSERCAO"
	title=""
	style="overflow: hidden;">
	<div class="toggle_container">
		<div id='divMensagemInsercao' class="section" style="overflow: hidden; font-size: 24px;">

		</div>
		<button type="button" onclick='fechar()'>
			<fmt:message key="citcorpore.comum.fechar" />
		</button>
	</div>
</div>
</html>
