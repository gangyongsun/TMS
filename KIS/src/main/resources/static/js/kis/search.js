$(function() {
	$('.file-box').each(function () {
		animationHover(this, 'pulse');
	 });

	/**
	 * 回车事件绑定
	 */
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) {
			searchTerminology();
		}
	}

	// 请求饼图summary
	$.ajax({
		type : "POST",
		url : 'summary',
		success : function(data) {
			var keyArray = [];
			var keyValueMapJsonArray = [];
			for ( var key in data) {
				keyArray.push(key);
				keyValueMapJsonArray.push({
					value : data[key],
					name : key
				});
			}
			init_pieChart(keyArray, keyValueMapJsonArray);//初始化饼图
		},
		error : function(data) {
			console.log("饼图初始化失败!");
		}
	});
	
	// 请求accessSummary
	$.ajax({
		type : "POST",
		url : 'accessSummary',
		success : function(data) {
			var keyArray = [];
			var keyValueMapJsonArray = [];
			for ( var key in data) {
				keyArray.push(key);
				keyValueMapJsonArray.push({
					value : data[key],
					name : key
				});
			}
			init_funnel(keyArray, keyValueMapJsonArray);//初始化漏斗图
		},
		error : function(data) {
			console.log("漏斗图初始化失败!");
		}
	});
	
});


/**
 * 分页查询术语
 * 
 * @param obj
 * @returns
 */
function searchTerminology(obj) {
	var t=$("#termTable");
	t.bootstrapTable('destroy');
	// 获取类型
	var termTypeValue = "";
	if (null != obj) {
		var termTypeValue = $(obj).attr("value");
	}
	// 获取搜索关键词
	var findContentVal = $("#findContent").val();
	var t = t.bootstrapTable({
		url: '/kis/search',
        method: 'get',
        dataType: "json",
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        detailView:true,
        detailFormatter:"showTermDetailView",
        undefinedText: "空",//当数据为undefined时显示的字符
        pagination: true, //启用分页
        pageNumber: 1,//分页首页页码
        pageSize: 10,//分页页面数据条数
        pageList: [10, 20, 50, 100],//设置可供选择的页面数据条数；设置为All则显示所有记录
        paginationFirstText: "首页",//指定“首页”按钮的图标或文字
        paginationPreText: '上一页',//指定“上一页”按钮的图标或文字
        paginationNextText: '下一页',//指定“下一页”按钮的图标或文字
        paginationLastText: "末页",//指定“末页”按钮的图标或文字
        data_local: "zh-US",//表格汉化
        sidePagination: "server", //服务端处理分页
        queryParamsType:'',//默认值为'limit',在默认情况下传给服务端的参数为：offset,limit,sort;设置为'',在这种情况下传给服务器的参数为：pageSize,pageNumber
        queryParams:function (params) {
	        //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
	        var temp = {   
	        	pageNumber: params.pageNumber,//当前页(开始页)
	    	    pageSize: params.pageSize,//每页的数量
	    	    termType : termTypeValue,
				findContent : findContentVal
	        };
	        return temp;
	    },
	    onClickRow:function (row,element) {
	    	showDetail(row.id);
        },
      	idField: "id",//指定主键列
        columns: [
            {
                visible:false,
                field: 'id'
            },
            {
                title: '中文',
                field: 'chinese',
                align: 'left',
                valign: 'middle'
            },
            {
                title: '英文',
                field: 'english',
                align: 'left',
                valign: 'middle'
            }
        ]
    });
    t.on('load-success.bs.table', function (data) {//table加载成功后的监听函数
        console.log("load success");
        $("#hotTermPanel").hide();
        $("#chartPanel").hide();
        $(".pull-right").css("display", "block");
    });
}

function showTermDetailView(index, row) {
	var html = []
	//html.push('<p><b>分类信息:</b> ' + row.classification1 +'>'+row.classification2+'>'+row.classification3+ '</p>');

	if (row.definationCN != "") {
		html.push('<p><b>中文定义:</b> ' + row.definationCN + '</p>');
	}
	if (row.definationEN != "") {
		html.push('<p><b>English Defination:</b> ' + row.definationEN + '</p>');
	}
	html.push('<p><b>热度系数:</b> ' + row.totalClick + '</p>');

	return html.join('');
}

/**
 * 显示详情
 * 
 * @param id
 * @returns
 */
function showDetail(id) {
	$.ajax({
		type : "POST",
		url : 'showDetail',
		data : {
			id : id
		},
		success : function(data) {
			$("#myModal").html(data);
			$('#myModal').modal('show');
		},
		error : function(data) {
			console.log("搜索失败！");
		}
	});
}

/**
 * 查看更多热词
 * 
 * @returns
 */
function showMoreHotTerms(obj){
	if (null != obj) {
		var num = $(obj).attr("id");
	}else{
		var num=9;
	}
	$.ajax({
		type : "POST",
		url : 'showMoreHotTerms',
		data : {
			number : num*2
		},
		success : function(data) {
			$("#hotTermArea").html(data);
		},
		error : function(data) {
			console.log("搜索更多热词失败！");
		}
	});
}

/**
 * 初始化饼图
 */
function init_pieChart(keyData, displayData) {
	var pieChart = echarts.init(document.getElementById("echarts-pie-chart"));
	var pieoption = {
		title : {
			text : '',
			subtext : '术语比例',
			x : 'center'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			x : 'left',
			data : keyData
		},
		calculable : true,
		series : [ {
			name : '比例',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			data : displayData
		} ]
	};
	pieChart.setOption(pieoption);
	$(window).resize(pieChart.resize);
}

/**
 * 初始化漏斗图
 * @param keyData
 * @param displayData
 * @returns
 */
function init_funnel(keyData, displayData){
	var funnelChart = echarts.init(document.getElementById("echarts-funnel-chart"));
    var funneloption = {
        title : {
            text: '',
            subtext: '各类术语点击量'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{b} : {c}"
        },
        legend: {
            data : keyData
        },
        calculable : true,
        series : [
            {
                name:'点击量',
                type:'funnel',
                width: '40%',
                data: displayData
            },
            {
                name:'点击量',
                type:'funnel',
                x : '50%',
                sort : 'ascending',
                itemStyle: {
                    normal: {
                        // color: 各异,
                        label: {
                            position: 'right'
                        }
                    }
                },
                data:displayData
            }
        ]
    };

    funnelChart.setOption(funneloption);
    $(window).resize(funnelChart.resize);
}