$(function () {
	$.ajax({
		type : "POST",
		url : 'summary',
		success : function(data) {
			var keyArray=[];
			var keyValueMapJsonArray=[];
			for (var key in data) { 
				keyArray.push(key);
				keyValueMapJsonArray.push({value:data[key],name:key});
	        }
			init_pieChart(keyArray,keyValueMapJsonArray);
		},
		error : function(data) {
			console.log("summary failure!");
		}
	});
	
	function init_pieChart(keyData,displayData){
	    var pieChart = echarts.init(document.getElementById("echarts-pie-chart"));
	    var pieoption = {
	        title : {
	            text: '术语构成比例',
	            subtext: '数据统计',
	            x:'center'
	        },
	        tooltip : {
	            trigger: 'item',
	            formatter: "{a} <br/>{b} : {c} ({d}%)"
	        },
	        legend: {
	            orient : 'vertical',
	            x : 'left',
	            data:keyData
	        },
	        calculable : true,
	        series : [
	            {
	                name:'比例',
	                type:'pie',
	                radius : '55%',
	                center: ['50%', '60%'],
	                data:displayData
	            }
	        ]
	    };
	    pieChart.setOption(pieoption);
	    $(window).resize(pieChart.resize);
	}
});
