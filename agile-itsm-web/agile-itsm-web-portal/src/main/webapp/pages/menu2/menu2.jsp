<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/include/header.jsp"%>
    <%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>


<script>
    addEvent(window, "load", load, false);
    function load() {
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
    }

    $(function() {
    	$("#ordem").focusout(function (){
    		validacaoOrdem();
    	});
    	$("#btnTodos").css('display','none');
		$("#btnLimpar").css('display','none');
    });



    function validacaoOrdem(){
    		if($("#ordem").val() == ""){
    			alert(i18n_message("citcorpore.comum.campoOrdemMenusObrigatorio"));
    			return false;
    		}else{
    			if (!$("#ordem").val().match(/^\d{1,10}$/) ) {
        			alert(i18n_message("citcorpore.comum.campoOrdemMenus"));
        			return false;
        		}else{
        			return true;
        		}
    		}

    }

    function setaLingua(idioma){
    	document.getElementById("pesqLockupLOOKUP_MENU_idlingua").value = idioma;
    }
	function LOOKUP_MENU_select(id,desc){
		document.form.restore({idMenu:id});
	}
	function excluir() {
		if (document.getElementById("idMenu").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("update");
			}
		}
	}

	function gerar(){
		document.form.fireEvent("exportarMenuXml");
	}

	function atualizar(){
		document.form.fireEvent("atualizarMenuXml");
	}

	/**
	* @author rodrigo.oliveira
	*/
	function desativaMenuPai(){
		var combobox = document.getElementById("idMenuPai")
		combobox.selectedIndex = 0;
		combobox.disabled = true;
	}

	/**
	* @author rodrigo.oliveira
	*/
	function ativaMenuPai(){
		document.getElementById("idMenuPai").disabled = false;
	}

	function desativaMenuRapido(){
		var checkbox = document.getElementById("menuRapido")
		checkbox.checked = false;
		checkbox.disabled = true;
	}

	function ativaMenuRapido(){
		document.getElementById("menuRapido").disabled = false;
	}

	/**
	* @author rodrigo.oliveira
	*/
	function limpar(){
		document.getElementById("idMenuPai").disabled = false;
		document.form.clear();
	}

	function gravar(){
		if(!validacaoOrdem()){
			return;
		}
		document.form.save();
	}

</script>

</style>
</head>
<body>
<input type='hidden' id='pesqLockupLOOKUP_MENU_idlingua'/>

<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>
<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">
	<%@include file="/include/menu_horizontal.jsp"%>

		<div class="flat_area grid_16">
				<h2><fmt:message key="menu.menu"/></h2>
		</div>
  <div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><fmt:message key="menu.cadastroMenu"/></a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top"><fmt:message key="menu.pesquisaMenu"/></a>
				</li>
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
	 <div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="">
							<form name='form'
							action='${ctx}/pages/menu2/menu2'>
								<div class="columns clearfix">
									<input type='hidden' name='idMenu' id='idMenu' /> <input
										type='hidden' name='dataInicio' id='dataInicio' />
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome" />
											</label>
											<div >
												<input id="nome" name="nome" type='text' maxlength="80"
													class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.descricao" />
											</label>
											<div>
												<input id="descricao" type="text" name="descricao"
													maxlength="50"
													class="Valid[Required] Description[citcorpore.comum.descricao]" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset style="height: 61px">
											<label> <fmt:message key="menu.menuPai" />
											</label>
											<div>
												<select id="idMenuPai" name='idMenuPai' onchange="if(this.value > '') ativaMenuRapido(); else desativaMenuRapido();" class="Description[Unidade Pai]"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><fmt:message key="menu.link" /> </label>
											<div>
												<input id="link" type="text" name="link" maxlength="255"
													class=" Description[menu.link]" />
											</div>
										</fieldset>
									</div>
									<!-- Campo Ordem de Menus não utilizado mais -->
									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="menu.ordem" /> </label>
											<div>
												<input   id="ordem" type="text" name="ordem" maxlength="10"
													class="Valid[Required]  Description[menu.ordem]" />
											</div>
										</fieldset>
									</div>
									<div class="col_100">
										<div>
											<label ><fmt:message key="menu.imagem" /></label>
										</div>
										<label><img style="vertical-align: top;"
											src="${ctx}/template_new/images/icons/small/grey/home.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="home.png" /> <label><img style="vertical-align: top;"
											src="${ctx}/template_new/images/icons/small/grey/pencil.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="pencil.png" /> <label><img style="vertical-align: top;"
											src="${ctx}/template_new/images/icons/small/grey/documents.png" />
										</label> <input
											class=""
											type="radio" name="imagem" value="documents.png" /> <label><img style="vertical-align: top;"
											src="${ctx}/template_new/images/icons/small/grey/month_calendar.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="month_calendar.png" /> <label><img style="vertical-align: top;"
											src="${ctx}/template_new/images/icons/small/grey/list_w_images.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="list_w_images.png" /> <label><img style="vertical-align: top;"
											src="${ctx}/template_new/images/icons/small/grey/money.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="money.png" /> <label><img style="vertical-align: top;"
											src="${ctx}/template_new/images/icons/small/grey/users.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="users.png" /> <label><img style="vertical-align: top;"
											src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="magnifying_glass.png" /> <label><img style="vertical-align: top;"
											src="${ctx}/template_new/images/icons/small/grey/graph.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="graph.png" /> <label><img style="vertical-align: top;"
											height="25"
											src="${ctx}/template_new/images/icons/large/grey/cog_3.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="cog_3.png" /> <label><img style="vertical-align: top;"
											src="${ctx}/template_new/images/icons/small/grey/alert_2.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="alert_2.png" /> <label><img style="vertical-align: top;"
											src="${ctx}/template_new/images/icons/small/grey/strategy.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="strategy.png" /> <label><img style="vertical-align: top;"
											height="25"
											src="${ctx}/template_new/images/icons/large/grey/box_outgoing_2.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="box_outgoing_2.png" /> <label><img style="vertical-align: top;"
											src="${ctx}/template_new/images/icons/small/grey/user_comment.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="user_comment.png" /> <label><img style="vertical-align: top;"
											height="25"
											src="${ctx}/template_new/images/icons/small/grey/books_2.png" />
										</label><input
											class=""
											type="radio" name="imagem" value="books_2.png" />
									</div>
									<%-- <div  class="col_100">
										<label class="campoObrigatorio" ><fmt:message key="menu.horizontal"/> </label>
											 <input value="S" type="radio" id="horizontal" name="horizontal" class="Valid[Required] Description[Selecione Sim ou Não]" onclick="desativaMenuPai()" /><fmt:message key="menu.sim"/>
											 <input value="N" type="radio" id="horizontal" name="horizontal"  class="Valid[Required] Description[Selecione Sim ou Não]" onclick="ativaMenuPai()" /><fmt:message key="menu.nao"/>

										<label><fmt:message key="menu.menuRapido"/></label>
										<input value="S" type="checkbox" id="menuRapido" name="menuRapido" class="Description[Menu Rápido]" />
									</div> --%>
								</div>
								<div >
								<button type='button' name='btnGravar' class="light" onclick='gravar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type="button" name='btnLimpar' class="light" onclick='limpar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
									<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message	key="citcorpore.comum.excluir" />
									</span>
								</button>
								<button type='button' id='btnGerar' name='btnGerar' class="light" onclick='gerar();'>
									<span><fmt:message	key="menu.gerar" /></span>
								</button>
								<button type='button' id='btnAtualizar' name='btnAtualizar' class="light" onclick='atualizar();'>
									<span><fmt:message	key="menu.atualizar" /></span>
								</button>
							   </div>
								<div id="popupCadastroRapido">
			                           <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
		                        </div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_MENU' id='LOOKUP_MENU' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
	 </div>
  </div>
 </div>
<!-- Fim da Pagina de Conteudo -->
</div>
		<%@include file="/include/footer.jsp"%>
</body>
</html>
