<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
	<head>	
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
	</head>
	<body>
		<div class="container-fluid fixed ">
			
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar main hidden-print">
			
				<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
				
				<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>										
				
			</div>
	
			<div id="wrapper">
					
				<!-- Inicio conteudo -->
				<div id="content">
								
				</div>
				<!--  Fim conteudo-->
			
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>
	</body>
</html>