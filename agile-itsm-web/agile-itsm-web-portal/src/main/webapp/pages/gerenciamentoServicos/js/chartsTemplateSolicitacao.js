/* ==========================================================
 * ErgoAdmin v1.2

 * charts.helper.js
 * 
 * http://www.mosaicpro.biz
 * Copyright MosaicPro
 *
 * Built exclusively for sale @Envato Marketplaces
 * ========================================================== */ 
var data;
var divGraf;
function plotaGrafico(dados, idDiv){
	
	data = dados;
	divGraf = idDiv;
}


var charts =  {
	// init charts on finances page
	initFinances: function()
	{
		// init simple chart
		//this.chart_simple.init();
	},
	
	// init charts on Charts page
	initCharts: function()
	{
		// init simple chart
		this.chart_simple.init();

		// init lines chart with fill & without points
		this.chart_lines_fill_nopoints.init();

		// init ordered bars chart
		this.chart_ordered_bars.init();


		// init pie chart
		this.chart_pie.init();

	},
	
	// init charts on dashboard
	initIndex: function()
	{
		// chart_ordered_bars
		//this.chart_ordered_bars.init();
		
		// init lines chart with fill & without points
		//this.chart_lines_fill_nopoints.init();

		// init traffic sources pie
		//this.traffic_sources_pie.init();
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
	},

	traffic_sources_pie: 
	{
		// data
		data: [
			{ label: "organic",  data: 60 },
			{ label: "direct",  data: 22.1 },
			{ label: "referral",  data: 16.9 },
			{ label: "cpc",  data: 1 }
		],
		
		// chart object
		plot: null,
		
		// chart options
		options: {
			series: {
	            pie: {
	                show: true,
	                redraw: true,
	                radius: 1,
	                tilt: 0.6,
	                label: {
	                    show: true,
	                    radius: 1,
	                    formatter: function(label, series){
	                        return '<div style="font-size:8pt;text-align:center;padding:5px;color:#fff;">'+Math.round(series.percent)+'%</div>';
	                    },
	                    background: { opacity: 0.8 }
	                }
	            }
	        },
	        legend: { show: true, backgroundColor: null, backgroundOpacity: 0 },
	        colors: [],
	        grid: { hoverable: true },
	        tooltip: true,
			tooltipOpts: {
				content: "<strong>%y% %s</strong>",
				dateFormat: "%y-%0m-%0d",
				shifts: {
					x: 10,
					y: 20
				},
				defaultTheme: false
			}
		},
		
		// initialize
		init: function()
		{
			// apply styling
			charts.utility.applyStyle(this);
			
			this.plot = $.plot($("#pie"), this.data, this.options);
		}
	},

	// traffic sources dataTables
	// we are now using Google Charts instead of Flot
	traffic_sources_dataTables:
	{
		// tables data
		data: 
		{
			tableSources:  
			{
				data: null,
				init: function()
				{
					var data = new google.visualization.DataTable();
			        data.addColumn('string', 'source');
			        data.addColumn('string', 'medium');
			        data.addColumn('number', 'visits');
			        data.addColumn('number', 'pg_views');
			        data.addColumn('string', 'avg_time');

			        data.addRows(7);
			        data.setCell(0, 0, 'google', null, {'style': 'text-align: center;'});
			        data.setCell(0, 1, 'organic', null, {'style': 'text-align: center;'});
			        data.setCell(0, 2, 89, null, {'style': 'text-align: center;'});
			        data.setCell(0, 3, 299, null, {'style': 'text-align: center;'});
			        data.setCell(0, 4, '00:01:48', null, {'style': 'text-align: center;'});
			        data.setCell(1, 0, '(direct)', null, {'style': 'text-align: center;'});
			        data.setCell(1, 1, '(none)', null, {'style': 'text-align: center;'});
			        data.setCell(1, 2, 14, null, {'style': 'text-align: center;'});
			        data.setCell(1, 3, 34, null, {'style': 'text-align: center;'});
			        data.setCell(1, 4, '00:03:15', null, {'style': 'text-align: center;'});
			        data.setCell(2, 0, 'yahoo', null, {'style': 'text-align: center;'});
			        data.setCell(2, 1, 'organic', null, {'style': 'text-align: center;'});
			        data.setCell(2, 2, 3, null, {'style': 'text-align: center;'});
			        data.setCell(2, 3, 3, null, {'style': 'text-align: center;'});
			        data.setCell(2, 4, '00:00:00', null, {'style': 'text-align: center;'});
			        data.setCell(3, 0, 'ask', null, {'style': 'text-align: center;'});
			        data.setCell(3, 1, 'organic', null, {'style': 'text-align: center;'});
			        data.setCell(3, 2, 1, null, {'style': 'text-align: center;'});
			        data.setCell(3, 3, 3, null, {'style': 'text-align: center;'});
			        data.setCell(3, 4, '00:01:34', null, {'style': 'text-align: center;'});
			        data.setCell(4, 0, 'bing', null, {'style': 'text-align: center;'});
			        data.setCell(4, 1, 'organic', null, {'style': 'text-align: center;'});
			        data.setCell(4, 2, 1, null, {'style': 'text-align: center;'});
			        data.setCell(4, 3, 1, null, {'style': 'text-align: center;'});
			        data.setCell(4, 4, '00:00:00', null, {'style': 'text-align: center;'});
			        data.setCell(5, 0, 'conduit', null, {'style': 'text-align: center;'});
			        data.setCell(5, 1, 'organic', null, {'style': 'text-align: center;'});
			        data.setCell(5, 2, 1, null, {'style': 'text-align: center;'});
			        data.setCell(5, 3, 1, null, {'style': 'text-align: center;'});
			        data.setCell(5, 4, '00:00:00', null, {'style': 'text-align: center;'});
			        data.setCell(6, 0, 'google', null, {'style': 'text-align: center;'});
			        data.setCell(6, 1, 'cpc', null, {'style': 'text-align: center;'});
			        data.setCell(6, 2, 1, null, {'style': 'text-align: center;'});
			        data.setCell(6, 3, 1, null, {'style': 'text-align: center;'});
			        data.setCell(6, 4, '00:00:00', null, {'style': 'text-align: center;'});

			        this.data = data;
			        return data;
				}
			},
			tableReffering:
			{
				data: null,
				init: function()
				{
					var data = new google.visualization.DataTable();
					data.addColumn('string', 'source');
			        data.addColumn('number', 'pg_views');
			        data.addColumn('string', 'avg_time');
			        data.addColumn('string', 'exits');
			        
					data.addRows(6);
					data.setCell(0, 0, 'google.ro');
					data.setCell(0, 1, 14, null, {'style': 'text-align: center;'});
					data.setCell(0, 2, '00:05:51', null, {'style': 'text-align: center;'});
					data.setCell(0, 3, '3', null, {'style': 'text-align: center;'});
					data.setCell(1, 0, 'search.sweetim.com');
					data.setCell(1, 1, 5, null, {'style': 'text-align: center;'});
					data.setCell(1, 2, '00:03:29', null, {'style': 'text-align: center;'});
					data.setCell(1, 3, '1', null, {'style': 'text-align: center;'});
					data.setCell(2, 0, 'start.funmoods.com');
					data.setCell(2, 1, 5, null, {'style': 'text-align: center;'});
					data.setCell(2, 2, '00:01:02', null, {'style': 'text-align: center;'});
					data.setCell(2, 3, '1', null, {'style': 'text-align: center;'});
					data.setCell(3, 0, 'google.md');
					data.setCell(3, 1, 2, null, {'style': 'text-align: center;'});
					data.setCell(3, 2, '00:03:56', null, {'style': 'text-align: center;'});
					data.setCell(3, 3, '1', null, {'style': 'text-align: center;'});
					data.setCell(4, 0, 'searchmobileonline.com');
					data.setCell(4, 1, 2, null, {'style': 'text-align: center;'});
					data.setCell(4, 2, '00:02:21', null, {'style': 'text-align: center;'});
					data.setCell(4, 3, '1', null, {'style': 'text-align: center;'});
					data.setCell(5, 0, 'google.com');
					data.setCell(5, 1, 1, null, {'style': 'text-align: center;'});
					data.setCell(5, 2, '00:00:00', null, {'style': 'text-align: center;'});
					data.setCell(5, 3, '1', null, {'style': 'text-align: center;'});
					
					this.data = data;
					return data;
				}
			}
		},
		
		// chart
		chart: 
		{
			tableSources: null,
			tableReffering: null
		},
		
		// options
		options: 
		{
			tableSources: 
			{
				page: 'enable',
				pageSize: 6,
				allowHtml: true,
				cssClassNames: {
					headerRow: 'tableHeaderRow',
					tableRow: 'tableRow',
					selectedTableRow: 'selectedTableRow',
					hoverTableRow: 'hoverTableRow'
				},
				width: '100%',
				alternatingRowStyle: false,
				pagingSymbols: { prev: '<span class="btn btn-inverse">prev</btn>', next: '<span class="btn btn-inverse">next</span>' }
			},
			
			tableReffering:
			{
				page: 'enable',
				pageSize: 6,
				allowHtml: true,
				cssClassNames: {
					headerRow: 'tableHeaderRow',
					tableRow: 'tableRow',
					selectedTableRow: 'selectedTableRow',
					hoverTableRow: 'hoverTableRow'
				},
				width: '100%',
				alternatingRowStyle: false,
				pagingSymbols: { prev: '<span class="btn btn-inverse">prev</btn>', next: '<span class="btn btn-inverse">next</span>' }
			}
		},
		
		// initialize
		init: function()
		{
			// data
			charts.traffic_sources_dataTables.data.tableSources.init();
			charts.traffic_sources_dataTables.data.tableReffering.init();
			
			// charts
			charts.traffic_sources_dataTables.drawTableSources();
			charts.traffic_sources_dataTables.drawTableReffering();
		},

		// draw Traffic Sources Table
		drawTableSources: function()
		{
			this.chart.tableSources = new google.visualization.Table(document.getElementById('dataTableSources'));
			this.chart.tableSources.draw(this.data.tableSources.data, this.options.tableSources);
		},

		// draw Refferals Table
		drawTableReffering: function()
		{
			this.chart.tableReffering = new google.visualization.Table(document.getElementById('dataTableReffering'));
			this.chart.tableReffering.draw(this.data.tableReffering.data, this.options.tableReffering);
		}
	},
	
	
	// lines chart with fill & without points
	

	

	// donut chart
	divGraf: {
		// chart data
		data: data,

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
			
			this.plot = $.plot($(div), this.data, this.options);
		}
	}



  };
