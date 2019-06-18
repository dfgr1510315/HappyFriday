    /* ChartJS
     * -------
     * Here we will create a few charts using ChartJS
     */


$(function () {
   /*---------------------
    ----- PIE CHART -----
    ---------------------*/
	if($('#sex')[0]){
		// Get context with jQuery - using jQuery's .get() method.
		let pieChartCanvas = $("#sex").get(0).getContext("2d");

		let config = {
			type: 'pie',
			data: {
				datasets: [{
					data: [
						11,
						14
					],
					backgroundColor: [
						"#36A2EB",
						"#FF6384"
					],
					label: 'My dataset' // for legend
				}],
				labels: [
					"男",
					"女"
				]
			},
			options: {
				responsive: true
			}
		};
	   let myPie = new Chart(pieChartCanvas, config);
	}

	if($('#grade')[0]){
		// Get context with jQuery - using jQuery's .get() method.
		let pieChartCanvas = $("#grade").get(0).getContext("2d");

		let config = {
			type: 'pie',
			data: {
				datasets: [{
					data: [
						11,
						14,
						25,
						14
					],
					backgroundColor: [
						"#36A2EB",
						"#4BC0C0",
						"#68B3C8",
						"#FF6384"
					],
					label: 'My dataset' // for legend
				}],
				labels: [
					"大一",
					"大二",
					"大三",
					"大四"
				]
			},
			options: {
				responsive: true
			}
		};
		let myPie = new Chart(pieChartCanvas, config);
	}

	if($('#class')[0]){
		// Get context with jQuery - using jQuery's .get() method.
		let pieChartCanvas = $("#class").get(0).getContext("2d");

		let config = {
			type: 'pie',
			data: {
				datasets: [{
					data: [
						11,
						14,
						25,
						14,
						22,
						50,
						33
					],
					backgroundColor: [
						"#36A2EB",
						"#4BC0C0",
						"#68B3C8",
						"#FF6384",
						"#FF8911",
						"#F16300",
						"#116300",
					],
					label: 'My dataset' // for legend
				}],
				labels: [
					"Maven",
					"课程测试",
					"数据结构",
					"计算机网络",
					"微机原理",
					"操作系统",
					"软件开发技术"
				]
			},
			options: {
				responsive: true
			}
		};
		let myPie = new Chart(pieChartCanvas, config);
	}
   /*---------------------
    ----- DOUGHNUT CHART -----
    ---------------------*/
/*	if($('#doughnutChart')[0]){
		// Get context with jQuery - using jQuery's .get() method.
		let doughnutChartCanvas = $("#doughnutChart").get(0).getContext("2d");

		let config = {
			type: 'doughnut',
			data: {
				datasets: [{
					data: [
						11,
						16,
						7,
						3,
						14
					],
					backgroundColor: [
						"#EB5E28",
						"#4BC0C0",
						"#68B3C8",
						"#7AC29A",
						"#F3BB45"
					],
					label: 'My dataset' // for legend

					
				}],
				labels: [
					"USA",
					"Germany",
					"Austalia",
					"Canada",
					"France"
				]
			},
			options: {
				responsive: true,
				legend: {
					display: false
				}
			}
		};
		
	   let myDoughnutChart = new Chart(doughnutChartCanvas, config);

	}	*/


   /*---------------------
    ----- LINE CHART -----
    ---------------------*/

	//let MONTHS = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];




   /*---------------------
    ----- AREA CHART -----
    ---------------------*/

	var config = {
		type: 'line',
		data: {
			labels: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
                datasets: [{
                    label: "当日所有课程总播放次数",
                    backgroundColor: "#FF8A80",
                    borderColor: "#FF8A80",
                    data: [
                        45, 
                        75, 
                        26, 
                        23, 
                        60, 
                        48, 
                        9,
                        45, 
                        75, 
                        26, 
                        23, 
                        60, 
                        48, 
                        9
                    ],
                    fill: true,
                }]
            },
		options: {
			responsive: true,
			title:{
				display:true,
				text:'各维度每日凌晨12:00更新前一日数据，此处展示最近30天数据'
			},
			tooltips: {
				mode: 'index',
				intersect: false,
			},
			hover: {
				mode: 'nearest',
				intersect: true
			},
			scales: {
				xAxes: [{
					display: true,
					scaleLabel: {
						display: true,
						labelString: '日期'
					}
				}],
				yAxes: [{
					display: true,
					scaleLabel: {
						display: true,
						labelString: '播放量'
					}
				}]
			}
		}
	};

	if($('#areaChart')[0]){
		
		// Get context with jQuery - using jQuery's .get() method.
		let areaChartCanvas = $("#areaChart").get(0).getContext("2d");
		
		//Create the line chart
		let areaChart = new Chart(areaChartCanvas, config);

	}
	
	
   /*---------------------
    ----- BAR CHART -----
    ---------------------*/
    
    
	let barChartData = {
		labels: ["January", "February", "March", "April", "May", "June", "July"],
		datasets: [{
			label: 'Dataset 1',
			backgroundColor: "#FF6384",
			borderColor: "#FF6384",
			borderWidth: 1,
			data: [
					45, 
					75, 
					26, 
					23, 
					60, 
					-48, 
					-9
			]
		}, {
			label: 'Dataset 2',
			backgroundColor: "#36A2EB",
			borderColor: "#36A2EB",
			borderWidth: 1,
			data: [
					-10, 
					16, 
					72, 
					93, 
					29, 
					-74, 
					64
			]
		}]

	};
    
    
	var config = {
					type: 'bar',
					data: barChartData,
					options: {
						responsive: true,
						legend: {
							position: 'top',
						},
						title: {
							display: true,
							text: 'Chart.js Bar Chart'
						}
					}
                }

    
	if($('#barChart')[0]){
		let barChartCanvas = $("#barChart").get(0).getContext("2d");
		let barChart = new Chart(barChartCanvas, config);

	}


   /*---------------------
    ----- BAR LINE COMBO CHART -----
    ---------------------*/
    
    
	let barlinecomboChartData = {
		labels: ["January", "February", "March", "April", "May", "June", "July"],
		
		datasets: [{
			type: 'line',
			label: 'Dataset 1',
			borderColor: "#4BC0C0",
			borderWidth: 2,
			fill: false,
			data: [
					-10, 
					16, 
					72, 
					93, 
					29, 
					-74, 
					64
			]
		}, {
			type: 'bar',
			label: 'Dataset 2',
			backgroundColor: "#FF6384",
			data: [
				45, 
				75, 
				26, 
				23, 
				60, 
				-48, 
				-9
			],
			borderColor: 'white',
			borderWidth: 2
		}, {
			type: 'bar',
			label: 'Dataset 3',
			backgroundColor: "#36A2EB",
			data: [
				-10, 
				16, 
				72, 
				93, 
				29, 
				-74, 
				64
			]
		}]

	};
    
    
	var config = {
					type: 'bar',
					data: barlinecomboChartData,
					options: {
						responsive: true,
						legend: {
							position: 'top',
						},
						title: {
							display: true,
							text: 'Chart.js Bar Chart'
						}
					}
                }

    
	if($('#barlinecomboChart')[0]){
		let barlinecomboChartCanvas = $("#barlinecomboChart").get(0).getContext("2d");
		let barlinecomboChart = new Chart(barlinecomboChartCanvas, config);

	}
	
});
