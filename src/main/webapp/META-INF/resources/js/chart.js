function renderChart(data, label, labels) {
    var ctx = document.getElementById("chart").getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: label,
                data: data,
                backgroundColor: [
			      	'rgba(255, 99, 132, 0.2)',
			      	'rgba(255, 159, 64, 0.2)',
			      	'rgba(75, 192, 192, 0.2)'
	      		],
	      		borderColor: [
			      'rgb(255, 99, 132)',
			      'rgb(255, 159, 64)',
			      'rgb(75, 192, 192)'
			      ],
			    borderWidth: 1
            }]
        },
        options: {
        scales: {
            yAxes: [{
                display: true,
                ticks: {
                    beginAtZero: true,
                    stepSize: 1
                }
            }]
        }
    }
    });
}
