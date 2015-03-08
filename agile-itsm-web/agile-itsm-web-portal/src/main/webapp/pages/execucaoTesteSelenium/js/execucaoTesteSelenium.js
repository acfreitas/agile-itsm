addEvent(window, "load", load, false);

/**
 * Inicia a tela
 */
function load() {
	
	//Esconde as divs
	$("#divCadastrosGerais").hide('slow');
	$("#divGerenciaDeCatalogoDeServico").hide('slow');
	$("#divGerenciaDeConfiguracao").hide('slow');
	$("#divGerenciaDeConhecimento").hide('slow');
	$("#divGerenciaDeIncidente").hide('slow');
	$("#divGestaoContrato").hide('slow');
	
	var charts = 
	{
		chart_donut:
		{
		// chart data
			data: [
			    { label: "USA",  data: 38 },
			    { label: "Brazil",  data: 23 },
			    { label: "India",  data: 15 },
			    { label: "Turkey",  data: 9 },
			    { label: "France",  data: 7 },
			    { label: "China",  data: 5 },
			    { label: "Germany",  data: 3 }
			],
	
			// will hold the chart object
			plot: null,

			// chart options
			options: 
			{
				series: {
					pie: { 
						show: true,
						innerRadius: 0.4,
						highlight: {
							opacity: 0.1
						},
						radius: 1,
						stroke: {
							color: '#fff',
							width: 8
						},
						startAngle: 2,
					    combine: {
		                    color: '#EEE',
		                    threshold: 0.05
		                },
		                label: {
		                    show: true,
		                    radius: 1,
		                    formatter: function(label, series){
		                        return '<div class="label label-inverse">'+label+'&nbsp;'+Math.round(series.percent)+'%</div>';
		                    }
		                }
					},
					grow: {	active: false}
				},
				legend:{show:false},
				grid: {
		            hoverable: true,
		            clickable: true,
		            backgroundColor : { }
		        },
		        colors: [],
		        tooltip: true,
				tooltipOpts: {
					content: "%s : %y.1"+"%",
					shifts: {
						x: -30,
						y: -50
					},
					defaultTheme: false
				}
			},
		
			// initialize
			init: function()
			{
				// apply styling
				charts.utility.applyStyle(this);
				
				this.plot = $.plot($("#chart_donut"), this.data, this.options);
			}
		},
		// utility class
		utility:
		{
			chartColors: [ themerPrimaryColor, "#444", "#777", "#999", "#DDD", "#EEE" ],
			chartBackgroundColors: ["transparent", "transparent"],

			applyStyle: function(that)
			{
				that.options.colors = charts.utility.chartColors;
				that.options.grid.backgroundColor = { colors: charts.utility.chartBackgroundColors };
				that.options.grid.borderColor = charts.utility.chartColors[0];
				that.options.grid.color = charts.utility.chartColors[0];
			},
			
			// generate random number for charts
			randNum: function()
			{
				return (Math.floor( Math.random()* (1+40-20) ) ) + 20;
			}
		}
	}
	
	if (typeof charts != 'undefined') 
		charts.chart_donut.init();
	
}

function iniciarGrafico() {
	
	//if (typeof charts != 'undefined') 
		charts.chart_donut.init();
	
}


var primaryColor = '#8ec657',
	dangerColor = '#b55151',
	successColor = '#609450',
	warningColor = '#ab7a4b',
	inverseColor = '#45484d';

var themerPrimaryColor = primaryColor;

function mostrarDiv(){
	
	esconderDiv();
	
	switch($("#pacotesDeTeste option:selected").val()) {
	
		case "0":
			$("#divCadastrosGerais").show('slow');
			break;
		case "1":
			$("#divGerenciaDeCatalogoDeServico").show('slow');
			break;
		case "2":
			$("#divGerenciaDeConfiguracao").show('slow');
			break;
		case "3":
			$("#divGerenciaDeConhecimento").show('slow');
			break;
		case "4":
			$("#divGerenciaDeIncidente").show('slow');
			break;
		case "5":
			$("#divGestaoContrato").show('slow');
			break;
	}
	
}

function esconderDiv(){
	
	//sleep(1000);
	
	$("#divCadastrosGerais").hide('slow');
	$("#divGerenciaDeCatalogoDeServico").hide('slow');
	$("#divGerenciaDeConfiguracao").hide('slow');
	$("#divGerenciaDeConhecimento").hide('slow');
	$("#divGerenciaDeIncidente").hide('slow');
	$("#divGestaoContrato").hide('slow');
	
}

function sleep(milliseconds) {
	
	var start = new Date().getTime();
	
	for (var i = 0; i < 1e7; i++) {
		
		if ((new Date().getTime() - start) > milliseconds){
			break;
		}
	}
}

function limparFormulario() {
	
	document.form.clear();
	mostrarDiv();
	document.form.fireEvent("load");
	
}

function executarRotina() {
	JANELA_AGUARDE_MENU.show();
	document.form.fireEvent("executarRotina");
}

function atualizarDadosGrafico(total, sucesso, falha, totalPct, sucessoPct, falhaPct) {
	
	$(".totalQtd").text(total);
	$(".sucessoQtd").text(sucesso);
	$(".falhaQtd").text(falha);
	
	$(".totalPct").width(totalPct+"%");
	$(".sucessoPct").width(sucessoPct+"%");
	$(".falhaPct").width(falhaPct+"%");

	$('.tabsbar a[href="#tab2-2"]').tab('show');
	
}

function atualizaLabel(labelSucesso, labelFalha){

	$('#testesExecutadosComSucesso').text(labelSucesso);
	$('#testesExecutadosComFalha').text(labelFalha);
	
}