<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%
    response.setCharacterEncoding("ISO-8859-1");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
			
	String retorno = "${ctx}/pages/index/index.load";
%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<!DOCTYPE html>
<html>
<head>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<title>Gráficos</title>

<link class="include" rel="stylesheet" type="text/css" href="jquery.jqplot.min.css" />
<link rel="stylesheet" type="text/css" href="examples.min.css" />
<link type="text/css" rel="stylesheet" href="syntaxhighlighter/styles/shCoreDefault.min.css" />
<link type="text/css" rel="stylesheet" href="syntaxhighlighter/styles/shThemejqPlot.min.css" />

<!--[if lt IE 9]><script language="javascript" type="text/javascript" src="../excanvas.js"></script><![endif]-->
<script class="include" type="text/javascript" src="jquery.min.js"></script>
<!-- Example scripts go here -->
<style type="text/css">
	.jqplot-data-label { /*color: #444;*/
		/*      font-size: 1.1em;*/		
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
	TD.celulaGridPesq
	{
		FONT-SIZE: 12px;
	    BORDER-RIGHT: 1px solid;
	    PADDING-RIGHT: 0px;
	    BORDER-TOP: 1px solid;
	    PADDING-LEFT: 0px;
	    PADDING-BOTTOM: 0px;
	    MARGIN: 0px;
	    BORDER-LEFT: 1px solid;
	    PADDING-TOP: 0px;
	    BORDER-BOTTOM: 1px solid
	}		
</style>
<script type="text/javascript">
	function voltar(){
		window.location = '<%=retorno%>';
	}
	function atualiza(){
		document.getElementById('paiChart1').innerHTML = '<div id="chart1" style="height: 300px; width: 400px;"></div>';
		document.getElementById('paiChart2').innerHTML = '<div id="chart2" style="height: 300px; width: 400px;"></div>';
		document.getElementById('paiChart3').innerHTML = '<div id="chart3" style="height: 300px; width: 400px;"></div>';
		document.form.fireEvent('renderizaGraficos');
		setTimeout(atualiza, 20000);
	}
	function setEventoCarregar(){
		window.setTimeout(atualiza, 1000);
	}
	HTMLUtils.addEvent(window, "load", setEventoCarregar, false)
</script>
</head>
<body>
	<table>
		<tr>
			<td>
				Monitoramento de Incidentes - CITSmart
			</td>
			<td>
				&nbsp;
			</td>
			<td>
				<label class="voltar">
					<a href='#' onclick='voltar()'>[Voltar]</a>
				</label>
			</td>
		</tr>
	</table>
	<div class="example-content">
		<div id='paiChart1' style='position: absolute; top: 25px; left:1px'><div id="chart1" style="height: 300px; width: 400px;"></div></div>
		<pre class="code prettyprint brush: js"></pre>
		<div id='paiChart2' style='position: absolute; top: 25px; left:401px'><div id="chart2" style="height: 300px; width: 400px;"></div></div>
		<pre class="code prettyprint brush: js"></pre>
		<div id='paiChart3' style='position: absolute; top: 25px; left:801px'><div id="chart3" style="height: 300px; width: 400px;"></div></div>
		<pre class="code prettyprint brush: js"></pre>
		<div id='paiChart4' style='position: absolute; top: 325px; left:1px; height: 300px; width: 400px; overflow: auto'></div>
		<pre class="code prettyprint brush: js"></pre>				

		<script class="code" type="text/javascript">
			function plotaGrafico(dados, componente) {							
				var plot1 = jQuery.jqplot(componente, [ dados ], {
					seriesDefaults : {
						// Make this a pie chart.
						renderer : jQuery.jqplot.PieRenderer,
						rendererOptions : {
							// Put data labels on the pie slices.
							// By default, labels show the percentage of the slice.
							showDataLabels : true
						}
					},
					legend : {
						show : true,
						location : 'e'
					}
				});
			}
		</script>

		<script class="include" type="text/javascript" src="jquery.jqplot.min.js"></script>

		<script class="include" language="javascript" type="text/javascript" src="plugins/jqplot.pieRenderer.min.js"></script>

	</div>
	<form name='form' action='${ctx}/pages/graficos/graficos'></form>
</body>
</html>
