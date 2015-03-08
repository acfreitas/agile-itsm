$(function(){
				
	$("#chat-h").css("height", $("body").height());
	$(window).scroll(function() {
			$("#chatf").css("height",$(window).height()  + $(window).scrollTop() - 20)
	});
	/*
	 * Chat widget
	 */
/*	if ($('.widget-chat').length)
	{
		$('.widget-chat form').submit(function(e)
		{
			e.preventDefault();
			
			var direction = $(this).parents('.widget-chat').find('.media:first blockquote').is('.pull-right') ? 'left' : 'right';
			var media = $(this).parents('.widget-chat').find('.media:first').clone();
			var message = $(this).find('[name="message"]');
			
			// prepare media
			media.hide();
			media.find('blockquote small a.strong').text('Mr.Awesome');
			
			// apply direction
			media.find('.media-body').removeClass('right').addClass(direction);
			media.find('blockquote').attr('class', '').addClass('pull-' + direction);
			media.find('.media-object').removeClass('pull-left pull-right').addClass('pull-' + direction);
			
			// apply message
			media.find('blockquote p').text(message.val());
			
			// reset input
			message.val('');
			
			// jump slimScroll to top
			$(this).parents('.widget-chat:first').find('.slim-scroll').slimScroll({ scrollTo: '0' });
			
			// insert media in the conversation
			$(this).parents('.widget-chat:first').find('.chat-items').prepend(media).find('.media:hidden').slideDown();
		});
	}*/
	
});/*
//----------------------------------------------------------------------------------------
var nick="<%=user.getLogin()%>";
var NomeUsuario = "<%=user.getNomeUsuario()%>";
var last=-1;
var verifica;
var codigoUsuarioChat;
var NomeUsuarioChat = nick;

window.load = ativa;

	function ativa() {
		verifica = setInterval(loop, 500);
	}

	function loop() {
		getMessages(0);
		//verificaUsuariosOnline(0);
	}
	
	function call(command, params) {
		var script = document.createElement("script");
		script.setAttribute("src",'http://'+document.location.host+'/citsmart/servlet/ChatEngine' + command + '?' + params);
		script.setAttribute("type", "text/javascript");
		document.body.appendChild(script);
	}
	//Recebe as mensagens de uma sala 
	function getMessages(idSala){
	   call('/messages/get','roomId='+idSala+'&sinceId='+last+'&callback=callbackRecebimentoMensagem');
	}
	//Envia mensagem para uma sala
	function sendMessage(from, txt,idSala){
	   txt = NomeUsuario+" Falou: " +txt;
	   call('/messages/create','roomId='+idSala+'&id='+from+'&txt='+txt+'&callback=callbackEnvioMensagem');
	   //limparMensagensSala();
	}

	 function callbackRecebimentoMensagem(o)
	 {
	   if (o.code=='1')
	   {
	     var msg = eval("("+o.message+")");
	     var s = "";
	             
	     for (var i=0; i<msg.length; i++)
	     {
	        if (NomeUsuarioChat==msg[i].from.name)
				s += msg[i].txt+"<br/>";
				
	        var id = eval("("+msg[i].id+")");
	        
	        if (id>last)
	        	last=id;      
	     } 

	     if (s!='')
	     {
	      $("<p align = 'left'><font color='red'>"+ s + "</font></p>").appendTo("#idMessage");
	      //$("#TextoChat").scrollTop($("#TextoChat")[0].scrollHeight);
	     }
	   }
	 }

	 function callbackEnvioMensagem(o)
	 {
	   var res = eval("("+o.code+")");

	   if (res<=0)
	   {
	     alert("Usuário não está logado");
	   }
	 }
	 function enviarMensagem(){
		 var texto = $("#idMessage").val();
			if(text!="")
				 $("<p align = 'left'><font color='blue'>"+ "Você falou para "+<%= request.getParameter("usuarioSelecionado") %>+": "+texto+ "</font></p>").appendTo("#TextoChat");
		      	$("#TextoChat").scrollTop($("#TextoChat")[0].scrollHeight);
				sendMessage(<%= request.getParameter("usuarioSelecionado") %>,texto,0);
			}
			$("#idMessage").val("").focus(); 
		 //$("#idMessage").text();
		 //sendMessage();
	 }*/