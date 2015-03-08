<%@page import="java.util.HashMap"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
	String nick = "";
	com.jsos.chat.LoginBean lb = (com.jsos.chat.LoginBean)session.getAttribute("nick");

	if (lb != null)
		nick = lb.getUserName();

	String prompt = "type your nick";

	UsuarioDTO user = (UsuarioDTO) request.getSession().getAttribute(br.com.citframework.util.Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
	/* String nome = "";
	nome +=((String) request.getSession().getAttribute("listaChat")) + ((String)request.getParameter("usuarioSelecionado"))+"#";
	request.getSession().setAttribute("listaChat",nome); */
	//System.out.println(request.getSession().getAttribute("listaChat"));
%>
<!doctype html public "">
<html>
<head>

<script>var URL_INITIAL = '${ctx}/';</script>
<script>var URL_SISTEMA = '${ctx}/';
</script>
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
<link
	href="${ctx}/novoLayout/common/bootstrap/extend/bootstrap-wysihtml5/css/bootstrap-wysihtml5-0.0.2.css"
	rel="stylesheet">
<link
	href="${ctx}/novoLayout/common/bootstrap/extend/bootstrap-select/bootstrap-select.css"
	rel="stylesheet" />
<link
	href="${ctx}/novoLayout/common/bootstrap/extend/bootstrap-toggle-buttons/static/stylesheets/bootstrap-toggle-buttons.css"
	rel="stylesheet" />


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
.widget-chat .chat-controls {
	position: absolute;
	height: 30px;
	padding: 15px 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: #fafafa;
	border-top: 1px solid #ebebeb;
	width: 100%;
}

.titulo {
	position: relative;
	/* width: 100%; */
	height: 36px;
	padding: 0 10px;
	line-height: 35px;
	background: #8ec657;
	-webkit-border-radius: 2px 2px 2px 2px;
	-moz-border-radius: 2px 2px 2px 2px;
	border-radius: 2px 2px 2px 2px;
	color: #F1F1F1;
}
</style>
<script type="text/javascript">
var MeuNick="<%=user.getLogin()%>";
var NomeUsuario = "<%=user.getNomeUsuario()%>";
var last=-1;
var verifica;
var codigoUsuarioChat;
//var NomeUsuarioChat = nick;
var UsuarioConversando = "<%= request.getParameter("usuarioSelecionado") %>";
	function sleep(milliseconds) {
		  var start = new Date().getTime();
		  for (var i = 0; i < 1e7; i++) {
		    if ((new Date().getTime() - start) > milliseconds){
		      break;
		    }
		  }
	}
	function ativa() {
		preencherListaTeladeUsuarioAberta();
		document.getElementById("Message").focus();
		verifica = setInterval(loop, 500);
	}

	function loop() {
		getMessages(0);
		//verificaUsuariosOnline(0);
	}

	function call(command, params) {
		var script = document.createElement("script");
		script.setAttribute("src",'http://'+document.location.host+'/citsmart/servlet/ChatEngine' + command + '' + params);
		script.setAttribute("type", "text/javascript");
		document.body.appendChild(script);
	}
	//Recebe as mensagens de uma sala
	function getMessages(idSala){
	   call('/messages/get','roomId='+idSala+'&sinceId='+last+'&callback=callbackRecebimentoMensagem');
	}
	//Envia mensagem para uma sala
	function sendMessage(from, txt,idSala){
		 MostraNaTela(txt,MeuNick);
		 var MensagemMontada = UsuarioConversando+","+MeuNick+","+txt;//estrutura: Nome de quem estou conversando| meu nome| Mensagem
	    call('/messages/create','roomId='+idSala+'&name='+from+'&txt='+MensagemMontada+'&callback=callbackEnvioMensagem');

	}
	//Limpar as mensagem da sala
	function limparMensagensSala(idSala){
		 call('/rooms/clear','id = '+idSala+'callback=callbackLimparTextoSala');
	}
	function callbackLimparTextoSala(){
		//alert("Limpar mensagens sala Status = "+o.code);
	}
	 function callbackRecebimentoMensagem(o)
	 {
	   if (o.code=='1')
	   {
	     var msg = eval("("+o.message+")");
	     var s = "";
		 //alert(array[0]);
	     for (var i=0; i<msg.length; i++)
	     {	var str =  msg[i].txt;
		 	var array = str.split(",");
	        /* if (MeuNick==msg[i].from.name && msg[i].txt != ""){ */
	         if (MeuNick == array[0] && msg[i].txt!= ""){
	   			 s += array[2];/* +"<br/>"; */
	         }


	        var id = eval("("+msg[i].id+")");

	        if (id>last)
	        	last=id;
	     }

	     if (s!='')
	     {
	    	 MostraNaTela(s,UsuarioConversando);
	    	 limparMensagensSala(0);
	      //$("<p align = 'left'><font color='red'>"+ s + "</font></p>").appendTo("#idMessage");
	      //$("#TextoChat").scrollTop($("#TextoChat")[0].scrollHeight);
	     }
	   }
	 }

	 function callbackEnvioMensagem(o)
	 {
	   var res = eval("("+o.code+")");

	   if (res<=0)
	   {
	     alert("Usuário não está mais logado");
	   }
	 }
	 function enviarMensagem(){
		 var text = $("#Message").val();
			if(text!=""){
				<%--  $("<p align = 'left'><font color='blue'>"+ "Você falou para "+<%= request.getParameter("usuarioSelecionado") %>+": "+texto+ "</font></p>").appendTo("#TextoChat");
		      	$("#TextoChat").scrollTop($("#TextoChat")[0].scrollHeight); --%>
			      sendMessage(MeuNick,text,0);
			     //sendMessage(MeuNick,text,0);
			}
			$("#Message").val("").focus();
	 }

	 function MostraNaTela(messagem,usuario){
			//var direction = $("#idDivMidia").parents('.widget-chat').find('.media:first blockquote').is('.pull-right') ? 'left' : 'right';
			if(usuario==MeuNick){
				var direction = 'left';
			}else{
				var direction = 'right';
			}
			var media = $("#idDivMidia").clone();

			// prepare media
			media.hide();
			media.find('blockquote small a.strong').text(usuario);

			// apply direction
			media.find('.media-body').removeClass('right').addClass(direction);
			media.find('blockquote').attr('class', '').addClass('pull-' + direction);
			media.find('.media-object').removeClass('pull-left pull-right').addClass('pull-' + direction);

			// apply message
			media.find('blockquote p').text(messagem);

			// jump slimScroll to top
			$("#idDivMidia").parents('.widget-chat:first').find('.slim-scroll').slimScroll({ scrollTo: '0' });

			// insert media in the conversation
			$("#idDivMidia").parents('.widget-chat:first').find('.chat-items').prepend(media).find('.media:hidden').slideDown();
			//sleep(500);
	 }

	 function preencherListaTeladeUsuarioAberta(){
		 <% String nome = "";
		 if((request.getSession().getAttribute("listaChat"))!=null){
			 System.out.println((String) request.getSession().getAttribute("listaChat"));
			 nome +=((String) request.getSession().getAttribute("listaChat")) + ((String)request.getParameter("usuarioSelecionado"))+"#";
			 System.out.println(nome);
		 }else{
			 nome =((String)request.getParameter("usuarioSelecionado"))+"#";
		 }
			request.getSession().setAttribute("listaChat",nome);
			System.out.println(request.getSession().getAttribute("listaChat"));%>
	 }
	 function retornoParaTelaPai(){
		document.getElementById("nomeUsuarioConversando").value ='<%=(String)request.getParameter("usuarioSelecionado")%>'
		document.form.fireEvent("fechaTelaChat");
	 }
</script>
</head>
<body class="widget-chat">
	<div class="titulo ative">ChatSmart</div>
	<!-- Slim Scroll -->
	<div class="slim-scroll chat-items" data-scroll-height="200px" style="padding: 10px;">

		<!-- Media item -->
		<div class="media" id="idDivMidia">
			<!-- <div class="media-object pull-left thumb">
				<img data-src="holder.js/51x51" alt="Image">
			</div> -->
			<div class="media-body">
				<blockquote>
					<small><a href="#" title="" class="strong">CITSmart</a> <cite></cite></small>
					<p></p>
				</blockquote>
			</div>
		</div>
		<!-- // Media item END -->
	</div>
	<!-- // Slim Scroll END -->
	<div class="chat-controls">
		<div class="innerLR ">
			 <form name='form' class="margin-none" id = "idform" action='${ctx}/pages/chatSmart/chatSmart'>
				<input type='hidden' name='nomeUsuarioConversando' id='nomeUsuarioConversando' />
				<div class="row-fluid">
					<div class="span9">
						<input type="text" id = "Message" name="Message"class="input-block-level margin-none" placeholder="Type your message .." />

						<input type="text" id = "solicitacao" name="solicitacao"class="input-block-level margin-none" placeholder="Número da Solicitação" style="display: none;" />
					</div>
					<div class="span3">
						<button type="Button" class="btn btn-block btn-inverse" onclick="enviarMensagem();">Enviar</button>
						<button type="Button" class="btn btn-block btn-inverse" style="display: none;">Vincular</button><!--deixar na tela este botao e o input solicitacao como style="display: none;" se nao o submit irá dar erro  -->
					</div>
				</div>
			 </form>
		</div>
	</div>
	<%@include file="/novoLayout/common/include/libRodape.jsp"%>
	<script type="text/javascript" src="js/chatSmart.js"></script>
	<script type="text/javascript">
		window.onload = ativa;
		window.onbeforeunload = retornoParaTelaPai;

		//ativa a tecla enter para enviar o que foi digitado
			$("#Message").keypress(
					function(event) {
						var keycode = (event.keyCode ? event.keyCode : event.which);
						if (keycode == '13') {
							enviarMensagem();
						}
				});
			/* $(window).bind('onbeforeunload', function(){
				retornoParaTelaPai();
				}); */
	</script>

</body>
</html>