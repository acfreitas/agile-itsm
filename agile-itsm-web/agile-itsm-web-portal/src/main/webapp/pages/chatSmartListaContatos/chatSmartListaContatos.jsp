<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
	String nick = "";
	com.jsos.chat.LoginBean lb = (com.jsos.chat.LoginBean) session
			.getAttribute("nick");

	if (lb != null)
		nick = lb.getUserName();

	String prompt = "type your nick";

	UsuarioDTO user = (UsuarioDTO) request.getSession().getAttribute(br.com.citframework.util.Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");

%>
<!doctype html public "">
<html>
<head>
<script>var URL_CHAT = '${ctx}/';</script>
<script type="text/javascript" src="${ctx}/js/defines.js"></script>
<script type="text/javascript" src="${ctx}/js/Temporizador.js"></script>
<script type="text/javascript" src="${ctx}/js/tabber.js"></script>
<script type="text/javascript" src="${ctx}/js/LookupFind.js"></script>
<script type="text/javascript" src="${ctx}/js/ObjectUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/DateTimeUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/StringUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/NumberUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/AjaxUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/HTMLUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/FormatUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/Thread.js"></script>
<!-- -boottstrap -->
<link
	href="${ctx}/novoLayout/common/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="${ctx}/novoLayout/common/theme/css/glyphicons.min.css"
	rel="stylesheet" />

<!-- Bootstrap Extended -->
<link
	href="${ctx}/novoLayout/common/bootstrap/extend/jasny-bootstrap/css/jasny-bootstrap.min.css"
	rel="stylesheet">
<link
	href="${ctx}/novoLayout/common/bootstrap/extend/jasny-bootstrap/css/jasny-bootstrap-responsive.min.css"
	rel="stylesheet">

<!-- JQueryUI -->
<link
	href="${ctx}/novoLayout/common/theme/scripts/plugins/system/jquery-ui/css/smoothness/jquery-ui-1.9.2.custom.min.css"
	rel="stylesheet" />

<!-- Google Code Prettify Plugin -->
<link
	href="${ctx}/novoLayout/common/theme/scripts/plugins/other/google-code-prettify/prettify.css"
	rel="stylesheet" />


<!-- LESS.js Library -->
<script src="${ctx}/novoLayout/common/theme/scripts/plugins/system/less.min.js"></script>
<link type="text/css" rel="stylesheet" href="css/style-light.min.css" />
<link type="text/css" rel="stylesheet" href="css/responsive.min.css" />
<style type="text/css">
.barra-ferramentas {
	height: 30px;
}
</style>

<script type="text/javascript">

	//--------------------------------------------------------------------
    var nick="<%=user.getLogin()%>";
    var NomeUsuario = "<%=user.getNomeUsuario()%>";
	var last = -1;
	var verifica;
	var codigoUsuarioChat;
	var NomeUsuarioChat = nick;

	//ativa a escuta do cliente--------------
	 function ativa(){
		 verificaOnline = setInterval(loop,1000);
		 verificaVoltarALista = setInterval(voltarAoChat, (60000));
		 verificaVoltarALista = setInterval(limparMensagensDaSala, (60000)*10);
	 }
	 function voltarAoChat(){
		 sendMessage(nick,"",0);
	 }
	 function limparMensagensDaSala(){
		 limparMensagensSala(0);
	 }
	 function loop(){
		 //getMessages(0);
		 verificaUsuariosOnline(0);
		 //sendMessage(nick,"",0);
	 }
	//-------------------------------------------------------------------


	function call(command, params) {
		var script = document.createElement("script");
		script.setAttribute("src", 'http://' + document.location.host+ '/citsmart/servlet/ChatEngine' + command + '' + params);
		script.setAttribute("type", "text/javascript");
		document.body.appendChild(script);
	}
	//Entra em uma sala
	function joinChat(nickName, sala){
		call('/users/login', 'roomId=' + sala + '&name=' + NomeUsuarioChat + '&'+ 'callback=callbackEntrarChat');
	}
	//Envia mensagem para uma sala
	 function sendMessage(from, txt,idSala){
	   call('/messages/create','roomId='+idSala+'&name='+from+'&txt='+txt+'&callback=callbackEnvioMensagem');
	 }
	//Sai de uma sala
	function sairChat(){
	   var sala= 0;
	   call('/users/logout','roomId='+sala+'&name='+NomeUsuarioChat+'&'+'callback=callDesconectar');
	}
	//Lista Usuarios online em uma sala
	function verificaUsuariosOnline(idSala){
	   call('/users/list','roomId='+idSala+'&callback=callbackListaUsuariosOnline');
	}
	//Limpar as mensagem da sala
	function limparMensagensSala(idSala){
		 call('/rooms/clear','id = '+idSala+'callback=callbackLimparTextoSala');
	}
	//---------------------------------------------------------------------
	//CallBack's
	function callbackEntrarChat(o) {
		if (o.code == '1') {
			codigoUsuarioChat = o.message;
			//alert("loguei");
		} else {
			//alert("erro ao logar");
		}
	}
	function callbackLimparTextoSala(o){
		//alert("Limpar mensagens sala Status = "+o.code);
	}
	//abre tela de Conversa entre os usuários
	function abrirTelaConversa(id){
		//window.location.reload();
		var existe = 0;
		<%	String listaUsuarios = (String)request.getSession().getAttribute("listaChat");
			 if(listaUsuarios!=null && !listaUsuarios.trim().equalsIgnoreCase("")){
				String[] array = listaUsuarios.split("#");
				for(String nomeUsuario : array){System.out.println(nomeUsuario);%>
					if(id.firstChild.nodeValue == "<%=nomeUsuario%>"){

						existe = 1;
					}

				<%}
			 }%>
			 if(existe==1){
				 return;
			 }else{
				 <%-- alert('<%=request.getSession().getAttribute("listaChat")%>'); --%>
				 window.open(url+"usuarioSelecionado="+id.firstChild.nodeValue,'_blank',"toolbar=no,scrollbars=no,location=no,statusbar=yes,menubar=no,resizable=no,width=420,height=418").moveTo(( screen.width - document.body.clientWidth)/2 , (screen.height - document.body.clientHeight)/2 );
			 }
	}

	var url = URL_CHAT + "pages/chatSmart/chatSmart.load";
	function callbackListaUsuariosOnline(o){
		 var json = JSON.stringify(eval("(" + o.message + ")"));
		 var arr = JSON.parse(json);
		 $("#listaUsuarios").empty();
		 if(o.code == '1'){
			 if(arr.length>1){
				 for ( var i = 0; i <arr.length; i++) {
					  if(arr[i].name != NomeUsuarioChat){
						  $("#listaUsuarios").append("<li onClick = 'abrirTelaConversa(this);' id='liteste'>"+arr[i].name+"</li>");
						   //$("#listaUsuarios").append("<li>"+"<span class='ellipsis'> <a href='javascript:window.open(\"" + url + "\")" + "'_blank','toolbar=1,scrollbars=1,location=1,statusbar=0,menubar=1,resizable=1,width=262,height=380')</a>"+arr[i].name+"</span></li>");
					  }
				  }
			 }
		 }
	 }
	function callDesconectar(o){
			//alert("Sair da sala Status = "+o.code);
	}
	function callbackEnvioMensagem(o)
	 {
	   var res = eval("("+o.code+")");

	   if (res<=0)
	   {
		   joinChat(nick, 0);
	   }
	 }

	//---Metodos de entrada
	function entrarChat() {
		sairChat();
		ativa();
		joinChat(nick, 0);
	}

	/* function teste(){
		window.location.reload();
	} */
</script>
</head>
<body onmouseup="window.location.reload()">
	<!-- <div class="barra-ferramentas">
		<span>Flávio Júnior</span>
	</div> -->
	<div class="row-fluid">
		<div class="span12">
			<div class="input-append">
				<input class="span11" id="stringDigitada" type="text"
					placeholder="Buscar" onkeyup="filtroListaJs(this, '');" >
				<button class="btn btn-default" type="button" onclick="verificaUsuariosOnline(0);">
					<i class="icon-search"></i>
				</button>
			</div>
		</div>
	</div>
	<!-- Slim Scroll -->
	<div id="" class="slim-scroll widget-activity"
		data-scroll-height="320px">
		 <ul class="list" id="listaUsuarios">
			<!-- Activity item -->
			<%-- <li class="double"><span class="glyphicons activity-icon user"><i></i></span>
				<span class="ellipsis"> <a
					href="javascript:window.open('${ctx}/pages/chatSmart2/chatSmart2.load', '_blank','toolbar=1,scrollbars=1,location=1,statusbar=0,menubar=1,resizable=1,width=262,height=380')">John
						Doe</a> (john.doe@fake-email.net)
			</span>
				<div class="clearfix"></div></li> --%>

			<!-- // Activity item END -->

		</ul>
	</div>
	<%@include file="/novoLayout/common/include/libRodape.jsp"%>
	<script type="text/javascript" src="js/chatSmartListaContatos.js"></script>
	<script type="text/javascript">
		entrarChat();
		window.onbeforeunload = sairChat;
	</script>
</body>
</html>