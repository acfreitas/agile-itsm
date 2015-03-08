<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
	<head>	
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="conteudoFaq.css"/>
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
					<!-- <div id=conteudoFaq> -->
						<div class="widget-body"><!-- Inicio da div class="widget-body" -->
							<div class="tab-content"><!-- Inicio da div class="tab-content" -->
							<h3><fmt:message key="faq.faq"/></h3>
								<div id="tabAll" class="tab-pane active"><!-- Inicio da div id="tabAll" -->
									<div class="accordion accordion-2" id="accordion"><!-- Inicio da div id="accordion" -->
										<div id="grupo" class="accordion-group"><!-- Inicio da div id="grupo" -->
											
											<!-- Conteudo "Faq.java" -->
																				    	
										</div><!-- Fim da div id="grupo" -->
									</div><!-- Fim da div id="accordion" -->
								</div><!-- Fim da div id="tabAll" -->
							</div><!-- Fim da div class="tab-content" -->		
						</div><!-- Fim da div class="widget-body" -->
					<!-- </form> -->
					<!-- </div>	 -->			
				</div>
				<!--  Fim conteudo-->
			
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>
	</body>
</html>