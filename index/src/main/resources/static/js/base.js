var analysebus = '-1';
var analysefac = '-1';
var curyear;

var chart4_bscissa = []; //chart4的横坐标
var searchcitys = [];
var factor = '-1'; //代办业务

var a=[2,3,4,5,6];
//职业
var data00 = [{
    name: 'IT人员',
    value: 192581,
    percent: '30'
}, {
    name: '教师',
    value: 215635,
    percent: '14.08',
}, {
    name: '管理人员',
    value: 224585,
    percent: '14.67',
}, {
    name: '行政',
    value: 224585,
    percent: '14.67',
}, {
    name: '医生',
    value: 224585,
    percent: '14.67',
}];
//年龄
var data01 = [{
    name: '高于50岁',
    value: 192581,
    percent: '30'
}, {
    name: '30岁~50岁',
    value: 215635,
    percent: '14.08',
}, {
    name: '20岁~30岁',
    value: 224585,
    percent: '14.67',
}, {
    name: '低于20岁',
    value: 224585,
    percent: '14.67',
}];

//地址

var data02 = [{
    name: '一线城市',
    value: 192581,
    percent: '30'
}, {
    name: '二线城市',
    value: 215635,
    percent: '14.08',
}, {
    name: '三线城市',
    value: 224585,
    percent: '14.67',
}, {
    name: '其他',
    value: 224585,
    percent: '14.67',
}];

var data03 = [{
    name: '男性',
    value: 192581,
    percent: '40'
}, {
    name: '女性',
    value: 215635,
    percent: '60',
}];
var data10 = data00;
var data20 = data00;
var data30 = data00;
var data11 = data01;
var data21 = data01;
var data31 = data01;
var data12 = data02;
var data22 = data02;
var data32 = data02;
var data13 = data03;
var data23 = data03;
var data33 = data03;

//选择对应数据
function selectdata(num1, num2) {
    if (num2 == '-1') {
        //单独处理
    } else {
        if (num2 == '0') {
            return data00;
        } else if (num2 == '1') {
            return data01;
        } else if (num2 == '2') {
            return data02;
        } else if (num2 == '3') {
            return data03;
        }

    }

}


function fnW(str) {
    var num;
    str >= 10 ? num = str : num = "0" + str;
    return num;
}

//获取当前时间
var timer = setInterval(function () {
    var date = new Date();
    var year = date.getFullYear(); //当前年份
    var month = date.getMonth(); //当前月份
    var data = date.getDate(); //天
    var hours = date.getHours(); //小时
    var minute = date.getMinutes(); //分
    var second = date.getSeconds(); //秒
    var day = date.getDay(); //获取当前星期几
    var ampm = hours < 12 ? 'am' : 'pm';
    $('#time').html(fnW(hours) + ":" + fnW(minute) + ":" + fnW(second));
    $('#date').html('<span>' + year + '/' + (month + 1) + '/' + data + '</span><span>' + ampm + '</span><span>周' + day + '</span>')

}, 1000)

var title1_1; //标题1：分析 的第一个选择参数
var title1_2; //标题1：分析 的第二个选择参数
var title2_1; //标题2：预测 的第一个选择参数
/*按钮点击方法*/
$('.select-ul').on('click', 'li', function () {
    $(this).addClass('active').siblings('li').removeClass('active').parent().hide().siblings('.select-div').html($(this).html());
    var parentDiv = $(this).parent().parent().parent();
    //alert($(this).attr("data-value"));
})

//获取按钮的值  自己加了id便于检索

$('#select_business').on('click', 'li', function () {
    analysebus = $(this).attr("data-value");

    chart1();

})

$('#select_factor').on('click', 'li', function () {
    analysefac = $(this).attr("data-value");
    //mybigchart1.setOption(option);
    chart1();
})

// 分析模块 标题
$('#select_business').on('click', function () {
    //var ul = document.getElementById("analyli1_1").getElementsByTagName("ul")[0].getElementsByTagName("li");
    title1_1 = $("#analyli1_1").text();
    title1_2 = $("#analyli1_2").text();
    title1 = '业务指标分析';
    if (title1_1 != '业务指标' && title1_2 != '分析指标') {
        $("#title1").text(title1 + ": " + title1_1 + " - " + title1_2);
    }
});
$('#select_factor').on('click', function () {
    title1_1 = $("#analyli1_1").text();
    title1_2 = $("#analyli1_2").text();
    title1 = '业务指标分析';
    if (title1_1 != '业务指标' && title1_2 != '分析指标') {
        $("#title1").text(title1 + ": " + title1_1 + " - " + title1_2);
    }
});

// 预测模块 筛选按钮点击事件
$('#pre_select_business').on('click', function () {
    // 更改标题
    title2_1 = $("#analyli2_1").text();
    title2 = '业务指标预测';
    if (title2_1 != '预测指标') {
        $("#title2").text(title2 + ": " + title2_1);
        $("#big_title2").text('业务指标预测' + ": " + title2_1);
        $("#big_title_plan").text('计划配置' + ": " + title2_1);
    }
    $('#fil1Con').attr('style', 'visibility: hidden');
    draw_simple_pre(title2_1);
});

//鼠标滑动到按钮，按钮内容变成白色
var imgName;
$('.title-box').children('button').hover(function () {
    imgName = $(this).children('img').attr('src').split('.png')[0];
    $(this).children('img').attr('src', imgName + '_on.png');
}, function () {
    $(this).children('img').attr('src', imgName + '.png');

});


var startColor = ['#0e94eb', '#c440ef', '#efb013', '#2fda07', '#d8ef13', '#2e4af8', '#0eebc4', '#f129b1', '#17defc', '#f86363'];
var borderStartColor = ['#0077c5', '#a819d7', '#c99002', '#24bc00', '#b6cb04', '#112ee2', '#00bd9c', '#ce078f', '#00b2cd', '#ec3c3c'];


$('.select').on('blur', function () {
    $(this).find('.select-ul').hide();
})
//下拉框点击出现下拉框内容
$('.select-div').on('click', function () {
    if ($(this).siblings('.select-ul').is(":hidden")) {
        $(this).siblings('.select-ul').show();
    } else {
        $(this).siblings('.select-ul').hide();
    }
})
$('.select-ul').on('click', 'li', function () {
    $(this).addClass('active').siblings('li').removeClass('active').parent().hide().siblings('.select-div').html($(this).html());
    var parentDiv = $(this).parent().parent().parent();
})

//银行指标分析占比，带边框效果的饼图

// 点击其他位置收起选择框

function bigchart1() {

    var data;
    //data 为模拟数据
    data = data00
    var mybigchart1 = echarts.init(document.getElementById('bigchart1'));
    window.addEventListener('resize', function () {
        mybigchart1.resize();
    });

    var str = '';
    for (var i = 0; i < data.length; i++) {
        //这句可以删除百分数
        //str += '<p><span><i class="legend" style="background:' + startColor[i] + '"></i></span>' + data[i].name + '<span class="pie-number" style="color:' + startColor[i] + '">' + ""+ '</span></p>';
        str += '<p><span><i class="legend" style="background:' + startColor[i] + '"></i></span>' + data[i].name + '<span class="pie-number" style="color:' + startColor[i] + '">' + "" + '</span>' + Number(data[i].percent).toFixed(2) + '%</p>';
    }
    $('#pie1').html(str);


    function deepCopy(obj) {
        if (typeof obj !== 'object') {
            return obj;
        }
        var newobj = {};
        for (var attr in obj) {
            newobj[attr] = obj[attr];
        }
        return newobj;
    }

    var RealData = [];
    var borderData = [];
    RealData.splice(0, RealData.length);
    borderData.splice(0, borderData.length);
    data.map((item, index) => {
        var newobj = deepCopy(item);
        var newobj1 = deepCopy(item);
        RealData.push(newobj);
        borderData.push(newobj1);
    });
    RealData.map((item, index) => {
        item.itemStyle = {
            normal: {
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0,
                        color: startColor[index] // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: startColor[index] // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
            }
        }
    });
    borderData.map((item, index) => {
        item.itemStyle = {
            normal: {
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0,
                        color: borderStartColor[index] // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: borderStartColor[index] // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
            }
        }
    });
    var option = {
        tooltip: {
            trigger: 'item',
            //            position: ['30%', '50%'],
            confine: true,
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
            // 主要展示层的
            {
                radius: ['50%', '85%'],
                center: ['50%', '50%'],
                type: 'pie',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                name: "占比为:",
                data: RealData
            },
            // 边框的设置
            {
                radius: ['45%', '50%'],
                center: ['50%', '50%'],
                type: 'pie',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                animation: false,
                tooltip: {
                    show: false
                },
                data: borderData
            }
        ]
    };
    mybigchart1.clear();
    mybigchart1.setOption(option);
}

//点击筛选按钮end
bigchart1()

function bigchart2() {

    var data;
    //data 为模拟数据
    data = data01
    var mybigchart2 = echarts.init(document.getElementById('bigchart2'));
    window.addEventListener('resize', function () {
        mybigchart2.resize();
    });

    var str = '';
    for (var i = 0; i < data.length; i++) {
        //这句可以删除百分数
        //str += '<p><span><i class="legend" style="background:' + startColor[i] + '"></i></span>' + data[i].name + '<span class="pie-number" style="color:' + startColor[i] + '">' + ""+ '</span></p>';
        str += '<p><span><i class="legend" style="background:' + startColor[i] + '"></i></span>' + data[i].name + '<span class="pie-number" style="color:' + startColor[i] + '">' + "" + '</span>' + Number(data[i].percent).toFixed(2) + '%</p>';
    }
    $('#pie2').html(str);

    function deepCopy(obj) {
        if (typeof obj !== 'object') {
            return obj;
        }
        var newobj = {};
        for (var attr in obj) {
            newobj[attr] = obj[attr];
        }
        return newobj;
    }

    var RealData = [];
    var borderData = [];
    RealData.splice(0, RealData.length);
    borderData.splice(0, borderData.length);
    data.map((item, index) => {
        var newobj = deepCopy(item);
        var newobj1 = deepCopy(item);
        RealData.push(newobj);
        borderData.push(newobj1);
    });
    RealData.map((item, index) => {
        item.itemStyle = {
            normal: {
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0,
                        color: startColor[index] // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: startColor[index] // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
            }
        }
    });
    borderData.map((item, index) => {
        item.itemStyle = {
            normal: {
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0,
                        color: borderStartColor[index] // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: borderStartColor[index] // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
            }
        }
    });
    var option = {
        tooltip: {
            trigger: 'item',
            //            position: ['30%', '50%'],
            confine: true,
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
            // 主要展示层的
            {
                radius: ['50%', '85%'],
                center: ['50%', '50%'],
                type: 'pie',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                name: "占比为:",
                data: RealData
            },
            // 边框的设置
            {
                radius: ['45%', '50%'],
                center: ['50%', '50%'],
                type: 'pie',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                animation: false,
                tooltip: {
                    show: false
                },
                data: borderData
            }
        ]
    };
    mybigchart2.clear();
    mybigchart2.setOption(option);
}

//点击筛选按钮end
bigchart2()


function bigchart3() {

    var data;
    //data 为模拟数据
    data = data02
    var mybigchart3 = echarts.init(document.getElementById('bigchart3'));
    window.addEventListener('resize', function () {
        mybigchart3.resize();
    });
    var str = '';
    for (var i = 0; i < data.length; i++) {
        //这句可以删除百分数
        //str += '<p><span><i class="legend" style="background:' + startColor[i] + '"></i></span>' + data[i].name + '<span class="pie-number" style="color:' + startColor[i] + '">' + ""+ '</span></p>';
        str += '<p><span><i class="legend" style="background:' + startColor[i] + '"></i></span>' + data[i].name + '<span class="pie-number" style="color:' + startColor[i] + '">' + "" + '</span>' + Number(data[i].percent).toFixed(2) + '%</p>';
    }
    $('#pie3').html(str);

    function deepCopy(obj) {
        if (typeof obj !== 'object') {
            return obj;
        }
        var newobj = {};
        for (var attr in obj) {
            newobj[attr] = obj[attr];
        }
        return newobj;
    }

    var RealData = [];
    var borderData = [];
    RealData.splice(0, RealData.length);
    borderData.splice(0, borderData.length);
    data.map((item, index) => {
        var newobj = deepCopy(item);
        var newobj1 = deepCopy(item);
        RealData.push(newobj);
        borderData.push(newobj1);
    });
    RealData.map((item, index) => {
        item.itemStyle = {
            normal: {
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0,
                        color: startColor[index] // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: startColor[index] // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
            }
        }
    });
    borderData.map((item, index) => {
        item.itemStyle = {
            normal: {
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0,
                        color: borderStartColor[index] // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: borderStartColor[index] // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
            }
        }
    });
    var option = {
        tooltip: {
            trigger: 'item',
            //            position: ['30%', '50%'],
            confine: true,
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
            // 主要展示层的
            {
                radius: ['50%', '85%'],
                center: ['50%', '50%'],
                type: 'pie',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                name: "占比为:",
                data: RealData
            },
            // 边框的设置
            {
                radius: ['45%', '50%'],
                center: ['50%', '50%'],
                type: 'pie',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                animation: false,
                tooltip: {
                    show: false
                },
                data: borderData
            }
        ]
    };
    mybigchart3.clear();
    mybigchart3.setOption(option);
}

//点击筛选按钮end
bigchart3()


function bigchart4() {

    var data;
    //data 为模拟数据
    data = data03
    var mybigchart4 = echarts.init(document.getElementById('bigchart4'));
    window.addEventListener('resize', function () {
        mybigchart4.resize();
    });

    var str = '';
    for (var i = 0; i < data.length; i++) {
        //这句可以删除百分数
        //str += '<p><span><i class="legend" style="background:' + startColor[i] + '"></i></span>' + data[i].name + '<span class="pie-number" style="color:' + startColor[i] + '">' + ""+ '</span></p>';
        str += '<p><span><i class="legend" style="background:' + startColor[i] + '"></i></span>' + data[i].name + '<span class="pie-number" style="color:' + startColor[i] + '">' + "" + '</span>' + Number(data[i].percent).toFixed(2) + '%</p>';
    }
    $('#pie4').html(str);

    function deepCopy(obj) {
        if (typeof obj !== 'object') {
            return obj;
        }
        var newobj = {};
        for (var attr in obj) {
            newobj[attr] = obj[attr];
        }
        return newobj;
    }

    var RealData = [];
    var borderData = [];
    RealData.splice(0, RealData.length);
    borderData.splice(0, borderData.length);
    data.map((item, index) => {
        var newobj = deepCopy(item);
        var newobj1 = deepCopy(item);
        RealData.push(newobj);
        borderData.push(newobj1);
    });
    RealData.map((item, index) => {
        item.itemStyle = {
            normal: {
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0,
                        color: startColor[index] // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: startColor[index] // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
            }
        }
    });
    borderData.map((item, index) => {
        item.itemStyle = {
            normal: {
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0,
                        color: borderStartColor[index] // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: borderStartColor[index] // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
            }
        }
    });
    var option = {
        tooltip: {
            trigger: 'item',
            //            position: ['30%', '50%'],
            confine: true,
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
            // 主要展示层的
            {
                radius: ['50%', '85%'],
                center: ['50%', '50%'],
                type: 'pie',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                name: "占比为:",
                data: RealData
            },
            // 边框的设置
            {
                radius: ['45%', '50%'],
                center: ['50%', '50%'],
                type: 'pie',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                animation: false,
                tooltip: {
                    show: false
                },
                data: borderData
            }
        ]
    };
    mybigchart4.clear();
    mybigchart4.setOption(option);
}

//点击筛选按钮end
bigchart4()


var data;

// 泽瀚的分析界面，留存
let ana_simple_chart = echarts.init(document.getElementById('pie'));

function createRandomItemStyle() {
    return {
        normal: {
            color: 'rgb(' + [
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160)
            ].join(',') + ')'
        }
    };
}

async function anasimplechart() {
    ana_simple_chart.clear();
    let option = {
        title: {
            text: ''
        },
        tooltip: {},
        series: [{
            type: 'wordCloud',  //类型为字符云
            sizeRange: [1, 45],
            rotationRange: [-90, 90],
            rotationStep: 90,
            shape: 'pentagon',
            top: 0,
            left: 0,
            width: '98%',
            height: '90%',
            textStyle: {
                normal: {
                    fontFamily: 'sans-serif',
                    color: function () {
                        return 'rgb(' + [
                            Math.round(Math.random() * 255 + 100),
                            Math.round(Math.random() * 255 + 100),
                            Math.round(Math.random() * 255 + 100)
                        ].join(',') + ')';
                    }
                },
                emphasis: {
                    shadowBlur: 30,
                    shadowColor: '#333'
                }
            },
            data: []
        }]
    };
    let res = await wait_wordcloud();
    option.series[0].data = res;
    ana_simple_chart.setOption(option);
}

let wait_wordcloud = function () {
    return new Promise((resolve) => {
        $.ajax({
            type: 'post',
            contentType: "application/x-www-form-urlencoded",
            dataType: 'json',
            url: 'http://localhost:9000/getwordcloud',
            data: {period: 'month', business: '开卡数'},
            success: function (res) {
                resolve(res);
            }
        });//ajax
    });//promise
};


function chart1() {
    //data 为模拟数据

    //data 为模拟数据
    if (analysefac == '-1') {
        data = data00;
    } else data = selectdata(analysebus, analysefac);

    var myChart = echarts.init(document.getElementById('pie'));
    window.addEventListener('resize', function () {
        myChart.resize();
    });


    function deepCopy(obj) {
        if (typeof obj !== 'object') {
            return obj;
        }
        var newobj = {};
        for (var attr in obj) {
            newobj[attr] = obj[attr];
        }
        return newobj;
    }

    var xData = [],
        yData = [];
    data.map((a, b) => {
        xData.push(a.name);
        yData.push(a.value);
    });


    var RealData = [];
    var borderData = [];
    data.map((item, index) => {
        var newobj = deepCopy(item);
        var newobj1 = deepCopy(item);
        RealData.push(newobj);
        borderData.push(newobj1);
    });
    RealData.map((item, index) => {
        item.itemStyle = {
            normal: {
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0,
                        color: startColor[index] // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: startColor[index] // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
            }
        }
    });
    borderData.map((item, index) => {
        item.itemStyle = {
            normal: {
                color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                        offset: 0,
                        color: borderStartColor[index] // 0% 处的颜色
                    }, {
                        offset: 1,
                        color: borderStartColor[index] // 100% 处的颜色
                    }],
                    globalCoord: false // 缺省为 false
                },
            }
        }
    });
    var option = {
        tooltip: {
            trigger: 'item',
            //            position: ['30%', '50%'],
            confine: true,
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
            // 主要展示层的
            {
                radius: ['50%', '85%'],
                center: ['50%', '50%'],
                type: 'pie',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                name: "占比为:",
                data: RealData
            },
            // 边框的设置
            {
                radius: ['45%', '50%'],
                center: ['50%', '50%'],
                type: 'pie',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                animation: false,
                tooltip: {
                    show: false
                },
                data: borderData
            }
        ]
    };

    var str = '';
    for (var i = 0; i < data.length; i++) {
        //这句可以删除百分数
        //str += '<p><span><i class="legend" style="background:' + startColor[i] + '"></i></span>' + data[i].name + '<span class="pie-number" style="color:' + startColor[i] + '">' + ""+ '</span></p>';
        str += '<p><span><i class="legend" style="background:' + startColor[i] + '"></i></span>' + data[i].name + '<span class="pie-number" style="color:' + startColor[i] + '">' + "" + '</span>' + Number(data[i].percent).toFixed(2) + '%</p>';
    }
    $('.pie-data').html(str);
    myChart.clear();

    myChart.setOption(option);
}

$('#filBtn').on('click', function () {
    if ($('#filCon').is(":hidden")) {
        $('#filCon').attr('style', 'display:flex');
    } else {
        $('#filCon').hide();
    }
})
//点击筛选按钮end

// chart1()
// 分析模块：按钮变色功能
let ana_choose;
$('#ana_config_btn').on('mouseenter', function () {
    $('#ana_config_btn').attr('style', 'background: #4169E1')
})
$('#ana_config_btn').on('mouseleave', function () {
    if (ana_choose != 0)
        $('#ana_config_btn').attr('style', 'background: #0c1a2c')
})

$('#ana_chart_btn').on('mouseenter', function () {
    $('#ana_chart_btn').attr('style', 'background: #4169E1')
})
$('#ana_chart_btn').on('mouseleave', function () {
    if (ana_choose != 1)
        $('#ana_chart_btn').attr('style', 'background: #0c1a2c')
})
let ana_chart_id = 0;
$('#ana_test').on('click', function () {
    let color = $('#test_color').val();
    ana_chart_id = ana_chart_id + 1;
    if (ana_chart_id <= 4)
        $('#ana_chart_page').append('<div id = ana_chart_' + ana_chart_id + ' style="background-color: ' + color + '"></div>')
})
//----------------------业务指标分析占比内容end---------------

//------------业务预测数据内容---------------
//点击筛选按钮
$('#anlyfilBtn').on('click', function () {
    if ($('#fil2Con').is(":hidden")) {
        $('#fil2Con').attr('style', 'display:flex');
    } else {
        $('#fil2Con').hide();
    }
})

$('#fil1Btn').on('click', function () {
    if ($('#fil1Con').is(":hidden")) {
        $('#fil1Con').attr('style', 'display:flex');
    } else {
        $('#fil1Con').hide();
    }
})

// =====================放大的预测详细图
let pre_choose_plan;
let pre_choose_chart;
$('#pre_plan_btn').on('mouseenter', function () {
    $('#pre_plan_btn').attr('style', 'background: #4169E1')
})
$('#pre_plan_btn').on('mouseleave', function () {
    if (pre_choose_plan != 1)
        $('#pre_plan_btn').attr('style', 'background: #0c1a2c')
})

$('#plan_chart_btn').on('mouseenter', function () {
    $('#plan_chart_btn').attr('style', 'background: #4169E1')
})
$('#plan_chart_btn').on('mouseleave', function () {
    if (pre_choose_chart != 1)
        $('#plan_chart_btn').attr('style', 'background: #0c1a2c')
})

var preBigChart = echarts.init(document.getElementById('chart11'));

function chart11() {
    // 基于准备好的dom，初始化echarts实例
    preBigChart.clear();
    option = {
        toolbox: {
            show: false,
            feature: {
                mark: {show: true},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        title: {
            text: ''
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (params) {
                // 自定义悬浮框，用于展示增量百分比
                if (params.length > 1 && params[0].seriesName.indexOf('预测') != -1) {
                    return params[0].name + "<br/>"
                        + params[0].seriesName + "：" + params[0].value + "<br/>"
                        + params[1].seriesName + "：" + params[1].value + "<br/>"
                        + "业务理论增量：" + ((((params[1].value - params[0].value) / params[0].value) * 100).toFixed(2)) + "%";
                }
                if (params.length > 1 && params[0].seriesName.indexOf('历史') != -1) {
                    return params[0].name + "<br/>"
                        + params[0].seriesName + "：" + params[0].value + "<br/>";
                }
                if (params.length = 1 && params[0].seriesName.indexOf('预测') != -1) {
                    return params[0].name + "<br/>"
                        + params[0].seriesName + "：" + params[0].value + "<br/>";
                }
                return params[0].name + "<br/>"
                    + params[0].seriesName + "：" + params[0].value + "<br/>"
                    + params[1].seriesName + "：" + params[1].value + "<br/>";
            }
        },
        legend: {
            data: ['历史', '预测', '计划'],
            textStyle: {
                color: '#fff'
            },
            top: '8%'
        },
        grid: {
            top: '20%',
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        color: ['#FF4949', '#FFA74D', '#FFEA51', '#4BF0FF', '#44AFF0', '#4E82FF', '#584BFF', '#BE4DFF', '#F845F1'],
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['7月', '8月', '9月', '10月', '11月', '12月'],
            splitLine: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: '#fff'
                }
            }
        },
        yAxis: {
            name: '张',
            type: 'value',
            splitLine: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: '#fff'
                }
            }
        },
        series: [
            {
                name: '历史',
                type: 'line',
                smooth: true, //平滑曲线显示
                data: [3961.88, 4233.63, 4183.14]
            },
            {
                name: '预测',
                type: 'line',
                smooth: true, //平滑曲线显示
                data: [, , 4183.14, 3633.01, 3704.47, 4204.47]
            }
            // 此处添加
        ]
    };
    preBigChart.setOption(option);
}

chart11('');

// 预测界面中  目标分析
$("#pre_ana_btn").on('click', function () {
    var options = preBigChart.getOption();
    var out = [options.series[0], options.series[1]];
    var target = $("#target").val();
    var pre = options.series[1].data;
    var plan = getplan(pre, target);
    var out_data = {name: '计划', type: 'line', smooth: true, data: plan}
    out.push(out_data);
    options.series = out;
    preBigChart.clear();
    preBigChart.setOption(options);
})

// 目标分析函数
function getplan(pre, target) {
    //console.log(target);
    var out = [];
    var i;
    for (i = 0; i < pre.length; i++) {
        out.push(pre[i]);
        if (pre[i] != null) break;
    }
    var now = out[i];
    var gap = (target - now) / (pre.length - i - 1);
    //console.log(gap);

    for (i = i + 1; i < pre.length; i++) {
        now = now + gap;
        out.push(now.toFixed(2));
    }
    //console.log(out);
    return out;
}

//----------------首页展示的简单图
//点击筛选按钮end
let preSimpleChart = echarts.init(document.getElementById('prechart'));

function chart2() {
    // 基于准备好的dom，初始化echarts实例
    option = {
        title: {
            text: ''
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (params) {
                if (params.length != 1) {
                    return params[0].name + "<br/>"
                        + params[1].marker + params[1].seriesName + "：" + params[1].value + "<br/>"
                } else {
                    return params[0].name + "<br/>"
                        + params[0].marker + params[0].seriesName + "：" + params[0].value + "<br/>"
                }
            }
        },
        legend: {
            selectedMode: false,
            enable: false,
            textStyle: {
                color: '#fff'
            },
            top: '5%'
        },
        grid: {
            top: '20%',
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        color: ['#FF4949', '#FFA74D', '#FFEA51', '#4BF0FF', '#44AFF0', '#4E82FF', '#584BFF', '#BE4DFF', '#F845F1'],
        xAxis: {
            type: 'category',
            boundaryGap: false,
            splitLine: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: '#fff'
                }
            }
        },
        yAxis: {
            name: '张',
            type: 'value',
            splitLine: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: '#fff'
                }
            }
        },
        series: []
    };
    preSimpleChart.clear();
    preSimpleChart.setOption(option);
}

chart2('');
// 界面加载就去获取默认的业务的预测值
// 后台返回预测数据的同时，然后前两个时间单位的历史数据
// [{bankName: "广州分行"
// business: "card"
// predictData: Array(8) [ 7888, 8000, 8031, … ]
// predictUnit: "season"},...]
let wait_default_predict = function () {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'post',
            contentType: "application/x-www-form-urlencoded",
            dataType: 'json',
            url: 'http://localhost:9000/getdefaultpredict',
            success: function (res) {
                resolve(res);
            }
        })//ajax
    })//promise
};

let wait_simple_predict = function (business) {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'post',
            contentType: "application/x-www-form-urlencoded",
            dataType: 'json',
            url: 'http://localhost:9000/getsimplepredict',
            data: {
                business: business
            },
            success: function (res) {
                resolve(res);
            }
        })//ajax
    })//promise
};

async function draw_default_pre() {
    preSimpleChart.showLoading({
        maskColor: 'rgba(68,120,255,0.7)'
    });
    let res = await wait_default_predict();
    updatePreSimpleChart(res);
    preSimpleChart.hideLoading();
}

async function draw_simple_pre(business) {
    preSimpleChart.showLoading({
        maskColor: 'rgba(68,120,255,0.7)'
    });
    let res = await wait_simple_predict(business);
    updatePreSimpleChart(res);
    preSimpleChart.hideLoading();
}

// 更新简单预测表的主要函数
function updatePreSimpleChart(res) {
    // 更换标题
    $("#title2").text('业务指标预测' + ": " + res[0].business);
    $("#analyli2_1").text(res[0].business);
    $("#big_title2").text('业务指标预测' + ": " + res[0].business);
    $("#big_title_plan").text('计划配置' + ": " + res[0].business);
    let config = preSimpleChart.getOption();
    // 更换横纵坐标轴
    let xAxis = generateX(res[0].predictUnit, res[0].predictData.length);
    config.xAxis[0].data = xAxis;
    if (res[0].business != '开卡数') config.yAxis[0].name = '元';
    else config.yAxis[0].name = '张';
    // 更换图例
    //config.legend.data = [res[0].banknames];
    // 更换数据
    config.series = [];
    config.series.push({
        name: res[0].bankName + '预测',
        type: 'line',
        smooth: true,
        data: res[0].predictData
    });
    let history = [];
    history.push(res[0].predictData[0]);
    history.push(res[0].predictData[1]);
    config.series.push({
        name: res[0].bankName + '历史',
        type: 'line',
        smooth: true,
        data: history
    });
    preSimpleChart.clear();
    preSimpleChart.setOption(config);
    preBigChart.clear();
    preBigChart.setOption(config);
}

// 生成横坐标轴
function generateX(unit, num) {
    let xAxis = [];
    let now = new Date();
    switch (unit) {
        case 'year':
            for (let i = -2; i < num - 2; i++) {
                xAxis.push((now.getMonth() - 2 + i) + '年');
            }
            break;
        case 'season':
            for (let i = -2; i < num - 2; i++) {
                xAxis.push(plan_getseason(i));
            }
            break;
        case 'month':
            for (let i = -2; i < num - 2; i++) {
                xAxis.push(plan_getmon(i));
            }
            break;
    }
    return xAxis;
}


// value的值代表，该城市的计划值，长度由预测时间长度决定
// 如[x,x,x,x]，即为当前时间接下去4个时间单位
var gd_city = [
    {
        name: '广州市',
        value: 0
    },
    {
        name: '韶关市',
        value: 0
    },
    {
        name: '深圳市',
        value: 0
    },
    {
        name: '珠海市',
        value: 0.0
    },
    {
        name: '汕头市',
        value: 0.0
    },
    {
        name: '佛山市',
        value: 0.0
    },
    {
        name: '江门市',
        value: 0.0
    },
    {
        name: '湛江市',
        value: 0.0
    },
    {
        name: '茂名市',
        value: 0.0
    },
    {
        name: '肇庆市',
        value: 0.0
    },
    {
        name: '惠州市',
        value: 0.0
    },
    {
        name: '梅州市',
        value: 0.0
    },
    {
        name: '汕尾市',
        value: 0.0
    },
    {
        name: '河源市',
        value: 0.0
    },
    {
        name: '阳江市',
        value: 0.0
    },
    {
        name: '清远市',
        value: 0.0
    },
    {
        name: '东莞市',
        value: 0
    },
    {
        name: '中山市',
        value: 0.0
    },
    {
        name: '潮州市',
        value: 0.0
    },
    {
        name: '揭阳市',
        value: 0.0
    },
    {
        name: '云浮市',
        value: 0.0
    }]

//变色
var pre_unit_choose;
var pre_city_choose;
$('#1mon').on('click', function () {
    pre_unit_choose = 0;
    $('#1mon').attr("style", "background-color: #b104ff;")
    $('#3mon').attr("style", "")
    $('#12mon').attr("style", "")
})
$('#3mon').on('click', function () {
    pre_unit_choose = 1;
    $('#1mon').attr("style", "")
    $('#3mon').attr("style", "background-color: #b104ff;")
    $('#12mon').attr("style", "")
})
$('#12mon').on('click', function () {
    pre_unit_choose = 2;
    $('#1mon').attr("style", "")
    $('#3mon').attr("style", "")
    $('#12mon').attr("style", "background-color: #b104ff;")
})

function plan_getmon(i) {
    var date = new Date();
    var year = date.getFullYear();
    var mon = date.getMonth();
    return parseInt(year + (mon + i) / 12) + '年 ' + (((mon + i) % 12) + 1) + '月';
}


function plan_getyear() {
    var date = new Date();
    var year = date.getFullYear();
    return year;
}

// 获取当前季度的后i个季度
function plan_getseason(i) {
    var date = new Date();
    var year = date.getFullYear();
    var mon = date.getMonth();
    var season;
    if (mon == 1 || mon == 2 || mon == 3) {
        season = 1;
    } else if (mon == 4 || mon == 5 || mon == 6) {
        season = 2;
    } else if (mon == 7 || mon == 8 || mon == 9) {
        season = 3;
    } else {
        season = 4;
    }
    return parseInt(year + (season + i) / 4) + '年 ' + (((season + i - 1) % 4) + 1) + '季度';
}

// 动态添加 时间配置，只是样式的改变，无涉及数据的传递
function draw_config() {
    $('.detail-container').empty();
    if (pre_unit_choose == 0) {
        for (var i = 0; i < $('#pre_period').val(); i++) {
            $('.detail-container').append('<div style="width:100%" class="pre_citybox">\n' +
                '                    <p ><span>' + plan_getmon(i) + '：</span></p>\n' +
                '                    <input id="' + pre_city_choose + plan_getmon(i) + '_input">\n' +
                '                </div>')
        }
    } else if (pre_unit_choose == 1) {
        for (var i = 0; i < $('#pre_period').val(); i++) {
            $('.detail-container').append('<div style="width:100%" class="pre_citybox">\n' +
                '                    <p ><span>' + plan_getseason(i) + '：</span></p>\n' +
                '                    <input id="' + pre_city_choose + plan_getseason(i) + '_input">\n' +
                '                </div>')
        }
    } else {
        for (var i = 0; i < $('#pre_period').val(); i++) {
            $('.detail-container').append('<div style="width:100%" class="pre_citybox">\n' +
                '                    <p ><span>' + (plan_getyear() + i) + '年：</span></p>\n' +
                '                    <input id="' + pre_city_choose + (plan_getyear() + i) + '_input">\n' +
                '                </div>')
        }
    }
    $('.detail-container').find("input").eq($('#pre_period').val() - 1).attr('readonly', 'readonly');
}

// 将gd_city中value的值赋给对应的input
function get_gd_city_data(id) {
    var temp = [];
    for (var i = 0; i < gd_city.length; i++) {
        if (gd_city[i].name == id) {
            temp = gd_city[i].value;
        }
    }
    for (var i = 0; i < $('#pre_period').val(); i++) {
        $('.detail-container').find('input').eq(i).val(temp[i].toFixed(0));
    }
}

function randomNum(minNum, maxNum) {
    return (Math.random() * (maxNum - minNum) + minNum).toFixed(2);
}

// 用于动态获取占比数据
let wait_scale = function () {
    return new Promise((resolve) => {
        $.ajax({
            type: 'post',
            contentType: "application/x-www-form-urlencoded",
            dataType: 'json',
            url: 'http://localhost:9000/getscale',
            data: {period: 'season', business: '开卡数'},
            success: function (res) {
                resolve(res);
            }
        });//ajax
    });//promise
};

//根据占比(ajax获得)进行分配
async function main_all() {
    var target = $('#广州target').val();
    var res = await wait_scale();
    var zhanbi = [1];
    var data = [];
    var base;
    for (let i = 0; i < res.length; i++) {
        if (res[i].bankName == '广州分行') {
            base = res[i].num;
            data.push(res[i].num);
            break;
        }
    }
    for (let i = 1; i < gd_city.length; i++) {
        for (let j = 0; j < res.length; j++) {
            if (res[j].bankName == gd_city[i].name.substring(0, gd_city[i].name.indexOf('市')) + '分行') {
                zhanbi.push(res[j].num / base);
                data.push(res[j].num);
                break;
            }
            if (j == res.length - 1) {
                zhanbi.push(0);
                data.push(0);
            }
        }
    }
    for (var i = 1; i <= gd_city.length; i++) {
        $('.city-container').find('input').eq(i - 1).val((target * zhanbi[i]).toFixed(0));
    }
    for (var i = 0; i < gd_city.length; i++) {
        var temp = [];
        var target_temp = target * zhanbi[i];
        var test_mon = data[i];
        var gap = ((target_temp - test_mon) / $('#pre_period').val()).toFixed(2);
        for (var j = 0; j < $('#pre_period').val(); j++) {
            temp.push(test_mon + gap * (j + 1));
        }
        gd_city[i].value = temp;
    }

}

// 动态添加
// 默认预测4个时间单位
$('#pre_period').val(4);
$('#gz_click').on('click', function () {
    if (pre_unit_choose != 0 && pre_unit_choose != 1 && pre_unit_choose != 2) {
        alert("请选择预测单位");
    } else {
        $('#pages-div').attr('style', 'visibility: visible');
        // 时间选择的点击事件，增减
        $('#pre_period').on('click', async function () {
            draw_config();
            await main_all();
            get_gd_city_data('广州市');
        });
        $('.container2').attr('style', 'visibility: visible').find('.pop-up1').eq(0).attr('style', 'visibility: visible');
        $('.pop-up1').find('h2').eq(0).text('广州计划配置');
        pre_city_choose = '广州市';
        draw_config();
        get_gd_city_data('广州市');
        $('.detail-container input').on('blur', function () {
            for (var i = 0; i < gd_city.length; i++) {
                if (gd_city[i].name == pre_city_choose) {
                    var temp = []
                    for (var j = 0; j < $('#pre_period').val(); j++) {
                        temp.push($('.detail-container').find('input').eq(j).val());
                    }
                    gd_city[i].value = temp;
                }
            }
        });
    }
});


// 动态添加 城市配置
$('#pre_all_btn').on('click', function () {
    var access = true;
    if ($('#广州target').val() == '') {
        alert("智能分配根据省会计划进行同比分配，请添加省会计划");
        access = false;
    } else if (pre_unit_choose != 0 && pre_unit_choose != 1 && pre_unit_choose != 2) {
        alert("请选择预测单位");
        access = false;
    } else if (typeof ($('#pre_period').val()) == 'undefined') {
        alert("请进入广州市配置界面设置时长");
        access = false;
    } else if (gd_city[0].value != 0) {
        access = confirm("智能分配会覆盖掉手动修改的值，是否继续")
    }
    if (access == true) {
        $('.city-container').empty();
        for (var i = 1; i < gd_city.length; i++) {
            $('.city-container').append('<div class="pre_citybox">\n' +
                '                    <p style="cursor: pointer" id="' + gd_city[i].name + '_p"><span>' + gd_city[i].name + '：</span></p>\n' +
                '                    <input id="' + gd_city[i].name + '_input">\n' +
                '                </div>');
            // 添加点击事件
            $('#' + gd_city[i].name + '_p').on('click', function (res) {
                $('#pages-div').attr('style', 'visibility: hidden');
                $('.container2').attr('style', 'visibility: visible').find('.pop-up1').eq(0).attr('style', 'visibility: visible');
                $('.pop-up1').find('h2').eq(0).text(res.currentTarget.innerText.slice(0, -2) + '分行计划配置');
                pre_city_choose = res.currentTarget.innerText.slice(0, -1);
                draw_config();
                get_gd_city_data(pre_city_choose);
                $('.detail-container input').on('blur', function () {
                    for (var i = 0; i < gd_city.length; i++) {
                        if (gd_city[i].name == pre_city_choose) {
                            var temp = []
                            for (var j = 0; j < $('#pre_period').val(); j++) {
                                temp.push($('.detail-container').find('input').eq(j).val());
                            }
                            gd_city[i].value = temp;
                        }
                    }
                });
            });

        }
        main_all();
    }
})
//------------指标预测end---------------

/* =========================地图模块图表============================*/
//地图地点选择
var myChart3 = echarts.init(document.getElementById('gdMap'));
var myChart33 = echarts.init(document.getElementById('gdMap_big'));
var myChart_gdmap_alert = echarts.init(document.getElementById('gdMap_alert'));

async function chart3(chartType) {
    var data = [
        {
            name: '广州市',
            value: 0.0
        },
        {
            name: '韶关市',
            value: 0.0
        },
        {
            name: '深圳市',
            value: 0.0
        },
        {
            name: '珠海市',
            value: 0.0
        },
        {
            name: '汕头市',
            value: 0.0
        },
        {
            name: '佛山市',
            value: 0.0
        },
        {
            name: '江门市',
            value: 0.0
        },
        {
            name: '湛江市',
            value: 0.0
        },
        {
            name: '茂名市',
            value: 0.0
        },
        {
            name: '肇庆市',
            value: 0.0
        },
        {
            name: '惠州市',
            value: 0.0
        },
        {
            name: '梅州市',
            value: 0.0
        },
        {
            name: '汕尾市',
            value: 0.0
        },
        {
            name: '河源市',
            value: 0.0
        },
        {
            name: '阳江市',
            value: 0.0
        },
        {
            name: '清远市',
            value: 0.0
        },
        {
            name: '东莞市',
            value: 0.0
        },
        {
            name: '中山市',
            value: 0.0
        },
        {
            name: '潮州市',
            value: 0.0
        },
        {
            name: '揭阳市',
            value: 0.0
        },
        {
            name: '云浮市',
            value: 0.0
        }]

    window.addEventListener('resize', function () {
        myChart3.resize();
        myChart33.resize();
    });
    var yMax = 0;
    for (var j = 0; j < data.length; j++) {
        if (yMax < data[j].value) {
            yMax = data[j].value;
        }
    }
    let option = {
        animation: true,
        tooltip: {
            show: true
        },
        visualMap: {
            show: false,
            min: 0,
            max: yMax,
            text: ['高', '低'],
            orient: 'horizontal',
            itemWidth: 15,
            itemHeight: 200,
            right: 0,
            bottom: 30,
            inRange: {
                color: ['#75ddff', '#0e94eb']
            },
            textStyle: {
                color: 'white'
            }
        },
        series: [
            {
                name: '数据名称',
                type: 'map',
                mapType: '广东',
                selectedMode: 'multiple',
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}<br/>{c}'
                },
                itemStyle: {
                    normal: {
                        borderWidth: 1,
                        borderColor: '#0e94eb',
                        label: {
                            show: false
                        }
                    },
                    emphasis: { // 也是选中样式
                        borderWidth: 1,
                        borderColor: '#fff',
                        backgroundColor: 'red',
                        label: {
                            show: true,
                            textStyle: {
                                color: '#fff'
                            }
                        }
                    }
                },
                data: data,
            }
        ]
    };
    let option_big = {
        animation: true,
        tooltip: {
            show: true
        },
        visualMap: {
            show: true,
            min: 0,
            max: yMax,
            text: ['高', '低'],
            orient: 'horizontal',
            itemWidth: 15,
            itemHeight: 200,
            right: 0,
            bottom: 30,
            inRange: {
                color: ['#75ddff', '#0e94eb']
            },
            textStyle: {
                color: 'white'
            }
        },
        series: [
            {
                name: '数据名称',
                type: 'map',
                mapType: '广东',
                selectedMode: 'multiple',
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}<br/>{c}'
                },
                itemStyle: {
                    normal: {
                        borderWidth: 1,
                        borderColor: '#0e94eb',
                        label: {
                            show: false
                        }
                    },
                    emphasis: { // 也是选中样式
                        borderWidth: 1,
                        borderColor: '#fff',
                        backgroundColor: 'red',
                        label: {
                            show: true,
                            textStyle: {
                                color: '#fff'
                            }
                        }
                    }
                },
                data: data,
            }
        ]
    };

    for (let i = 0; i < arealist.length; i++) {
        let d = option.series[0].data;
        let bigd = option_big.series[0].data
        for (let j = 0; j < d.length; j++) {
            if(d[j].name == arealist[i].bankname.slice(0,arealist[i].bankname.length-2)+'市'){
                d[j].selected = true;
                bigd[j].selected = true;
                break;
            }
        }
    }
    myChart3.setOption(option);
    myChart33.setOption(option_big);
}


// 警报界面地图
function chart_alert(chartType) {
    var data = [
        {
            name: '广州市',
            value: 5
        },
        {
            name: '韶关市',
            value: 4
        },
        {
            name: '深圳市',
            value: 1
        },
        {
            name: '珠海市',
            value: 21
        },
        {
            name: '汕头市',
            value: 19
        },
        {
            name: '佛山市',
            value: 17
        },
        {
            name: '江门市',
            value: 3
        },
        {
            name: '湛江市',
            value: 17
        },
        {
            name: '茂名市',
            value: 6
        },
        {
            name: '肇庆市',
            value: 26
        },
        {
            name: '惠州市',
            value: 14
        },
        {
            name: '梅州市',
            value: 19
        },
        {
            name: '汕尾市',
            value: 18
        },
        {
            name: '河源市',
            value: 24.4
        },
        {
            name: '阳江市',
            value: 7.6
        },
        {
            name: '清远市',
            value: 2.1
        },
        {
            name: '东莞市',
            value: 11.2
        },
        {
            name: '中山市',
            value: 12.4
        },
        {
            name: '潮州市',
            value: 15.6
        },
        {
            name: '揭阳市',
            value: 16.7
        },
        {
            name: '云浮市',
            value: 17.9
        }]

    window.addEventListener('resize', function () {
        myChart_gdmap_alert.resize();
    });
    var yMax = 0;
    for (var j = 0; j < data.length; j++) {
        if (yMax < data[j].value) {
            yMax = data[j].value;
        }
    }
    var option = {
        animation: true,
        tooltip: {
            show: true
        },
        visualMap: {
            show: false,
            min: 0,
            max: yMax,
            text: ['高', '低'],
            orient: 'horizontal',
            itemWidth: 15,
            itemHeight: 200,
            right: 0,
            bottom: 30,
            inRange: {
                color: ['#fff3f4', '#eb0a0a']
            },
            textStyle: {
                color: 'white'
            }
        },
        series: [
            {
                name: '数据名称',
                type: 'map',
                mapType: '广东',
                selectedMode: 'multiple',
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}<br/>{c}'
                },
                itemStyle: {

                    borderWidth: 1,
                    borderColor: '#eb696f'
                },
                label: {
                    show: false
                },
                emphasis: {
                    itemStyle: {
                        areaColor: '#c1ac13'
                    },
                    label: {
                        show: true,
                        textStyle: {
                            color: '#fff'
                        }
                    }
                },
                data: data,
            }
        ]
    };
    myChart_gdmap_alert.setOption(option);
}

chart_alert();

/* =========================地图模块图表节结束============================*/

//=============================悬浮窗相关样式

$('#main_hover').on('mouseenter', function () {
    $('#right_float_window').transition({
        x: -45
    }, 100, 'linear');
})
$('#right_float_window').on('mouseleave', function () {
    $('#right_float_window').transition({
        x: 0
    }, 100, 'linear');
})


//                                悬浮窗结束==============================================
// import './css/theme/index.css';

/*
     *   导出excel function
     * */
exportExcel_Doit = function(type, fn, dl) {
    var elt = document.getElementById('downloan-his');
    var wb = XLSX.utils.table_to_book(elt, {sheet: "Sheet JS"});
    return dl ?
        XLSX.write(wb, {bookType: type, bookSST: true, type: 'base64'}) :
        XLSX.writeFile(wb, fn || ('test.' + (type || 'xlsx')));
}
var vm = new Vue({
    el: '#app',
    data: {
        attrlist: null
    },
    created () {
        var _this = this;

        axios({
            method:'get',
            url:'http://localhost:9000/list2',
            headers:{'Access-Control-Allow-Origin':'*'}
        }).then(function (response) {
            _this.attrlist = response.data;
            //console.log(response);
        });


    },
    methods: {
        addSelected: function (event,index) {
            //获取点击对象
            var el = event.currentTarget;
            //alert("当前对象的内容：" + el.innerHTML);
            var text =  el.innerHTML
            //alert(index)
            //去重
            for (var i in vmtop.panlist.list){
                if (vmtop.panlist.list[i].panAttrValue===text) return ;
            }

            //选择列表增加
            //获取业务名字
            //在这里面编辑希望的json格式
            var pan = {
                panAttrName  : vm.attrlist[index].attrName,
                panAttrValue : text
            }

            vmtop.panlist.list.push(pan);
            this.sendSql();
        },
        sendSql:function () {
            axios({
                url:'http://localhost:9000/sendSql',
                method:'post',
                headers:{'Access-Control-Allow-Origin':'*',
                    'Content-Type': 'application/json'
                    // 'Content-Type': 'application/x-www-form-urlencoded'
                },
                transformRequest: [function (data) {
                    // 对 data 进行任意转换处理
                    //return Qs.stringify(data, {arrayFormat: 'repeat'})
                    data =  JSON.stringify(data)
                    return data
                }],
                data: {
                    //tablelist:["1","2"]
                    tablelist : vmtop.panlist
                }
            }).then(function (response) {
                //vm.panlist = response.data;
                // alert(response.data);
                vmtable.table = response.data;
                dowantable.table = response.data;
                console.log(response.data)
            });
        }

    }
})
var vmtop = new Vue({
    el: '#apptop',
    data: {
        panlist : {
            list :[]
        }

    },
    methods: {
        deleteSelected: function (index) {
            vmtop.panlist.list.splice(index,1);
            //1跟后台拿数据，参数是panlist
            //发送sql的参数
            this.sendSql();
            //2更新table
        },
        sendSql:function () {
            axios({
                url:'http://localhost:9000/sendSql',
                method:'post',
                headers:{'Access-Control-Allow-Origin':'*',
                    'Content-Type': 'application/json'
                    // 'Content-Type': 'application/x-www-form-urlencoded'
                },
                transformRequest: [function (data) {
                    // 对 data 进行任意转换处理
                    //return Qs.stringify(data, {arrayFormat: 'repeat'})
                    data =  JSON.stringify(data)
                    return data
                }],
                data: {
                    //tablelist:["1","2"]
                    tablelist : vmtop.panlist
                }
            }).then(function (response) {
                //vm.panlist = response.data;
                // alert(response.data);
                vmtable.table = response.data;
                dowantable.table = response.data;
            });
        }
    }
})
var vmtable = new Vue({
    el: '#apptable',
     data :{
        table:null
    }
})

var dowantable = new Vue({
    el: '#downloadTable',
    data :{
        table:null
    }
})

var Main = {
    data() {
        return {
            value1: '',
            // value2: ''
        };
    },methods:{
        chooseTimeRange(t) {
            alert(t);//结果为一个数组，如：["2018-08-04", "2018-08-06"]
            //去重
            //var flag = 0;
            for (var i in vmtop.panlist.list){
                if (vmtop.panlist.list[i].panAttrName==="日期"){
                    vmtop.panlist.list.splice(i,2);
                }
            }
            this.addTimeIfNo(t);

        },
        addTimeIfNo(t){
            var pans = {
                panAttrName  : "日期",
                panAttrValue : t
            }
            var pane={
                panAttrName  : "日期",
                panAttrValue : t[1]
            }
            vmtop.panlist.list.push(pans);
            vmtop.sendSql();
        }
    }
};
var Ctor = Vue.extend(Main)
new Ctor().$mount('#appdate')
/* =========================业务选择============================*/

//业务选择 按钮动态展示
var mouseleaveable_1 = true;
var mouseleaveable_2 = true;
var mouseleaveable_3 = true;
var mouseleaveable_4 = true;
$('#Jcardnum').css({y: -20}).transition({
    opacity: 1,
    y: 0
}, 1000, 'linear');
$('#Jcardnum').on('mouseover', function () {
    $('#Jcardnum').css({background: '#9400D3'})
});
$('#Jcardnum').on('mouseleave', function () {
    if (mouseleaveable_1)
        $('#Jcardnum').css({background: '#4169E1'})
});
$('#Jcardnum').on('click', function () {
    mouseleaveable_1 = false;
    mouseleaveable_2 = true;
    mouseleaveable_3 = true;
    mouseleaveable_4 = true;
    factor = '开卡数';

    $('#Jcardnum').css({background: '#9400D3'});
    $('#Jloan').css({background: '#4169E1'});
    $('#Jcash').css({background: '#4169E1'});
    $('#Jmiddle').css({background: '#4169E1'});
});

$('#Jloan').css({y: -20}).transition({
    opacity: 1,
    y: 0,
    delay: 250
}, 1000, 'linear');
$('#Jloan').on('mouseover', function () {
    $('#Jloan').css({background: '#9400D3'})
});
$('#Jloan').on('mouseleave', function () {
    if (mouseleaveable_2)
        $('#Jloan').css({background: '#4169E1'})
});
$('#Jloan').on('click', function () {
    mouseleaveable_1 = true;
    mouseleaveable_2 = false;
    mouseleaveable_3 = true;
    mouseleaveable_4 = true;

    factor = '贷款数';

    $('#Jcardnum').css({background: '#4169E1'});
    $('#Jloan').css({background: '#9400D3'});
    $('#Jcash').css({background: '#4169E1'});
    $('#Jmiddle').css({background: '#4169E1'});
});

$('#Jcash').css({y: -20}).transition({
    opacity: 1,
    y: 0,
    delay: 500
}, 1000, 'linear');
$('#Jcash').on('mouseover', function () {
    $('#Jcash').css({background: '#9400D3'})
});
$('#Jcash').on('mouseleave', function () {
    if (mouseleaveable_3)
        $('#Jcash').css({background: '#4169E1'})
});
$('#Jcash').on('click', function () {
    mouseleaveable_1 = true;
    mouseleaveable_2 = true;
    mouseleaveable_3 = false;
    mouseleaveable_4 = true;

    factor = '存款数';
    $('#Jcardnum').css({background: '#4169E1'});
    $('#Jloan').css({background: '#4169E1'});
    $('#Jcash').css({background: '#9400D3'});
    $('#Jmiddle').css({background: '#4169E1'});
});

$('#Jmiddle').css({y: -20}).transition({
    opacity: 1,
    y: 0,
    delay: 750
}, 1000, 'linear');
$('#Jmiddle').on('mouseover', function () {
    $('#Jmiddle').css({background: '#9400D3'})
});
$('#Jmiddle').on('mouseleave', function () {
    if (mouseleaveable_4)
        $('#Jmiddle').css({background: '#4169E1'})
});
$('#Jmiddle').on('click', function () {
    mouseleaveable_1 = true;
    mouseleaveable_2 = true;
    mouseleaveable_3 = true;
    mouseleaveable_4 = false;

    factor = '中间收入';
    $('#Jcardnum').css({background: '#4169E1'});
    $('#Jloan').css({background: '#4169E1'});
    $('#Jcash').css({background: '#4169E1'});
    $('#Jmiddle').css({background: '#9400D3'});
});

function change_business_style(){
    if(arealist[0].business == '开卡数'){
        $('#Jcardnum').css({background: '#9400D3'});
        $('#Jloan').css({background: '#4169E1'});
        $('#Jcash').css({background: '#4169E1'});
        $('#Jmiddle').css({background: '#4169E1'});
        mouseleaveable_1 = false;
        mouseleaveable_2 = true;
        mouseleaveable_3 = true;
        mouseleaveable_4 = true;
    }else if(arealist[0].business == '贷款数'){
        $('#Jcardnum').css({background: '#4169E1'});
        $('#Jloan').css({background: '#9400D3'});
        $('#Jcash').css({background: '#4169E1'});
        $('#Jmiddle').css({background: '#4169E1'});
        mouseleaveable_1 = true;
        mouseleaveable_2 = false;
        mouseleaveable_3 = true;
        mouseleaveable_4 = true;
    }else if(arealist[0].business == '存款数'){
        $('#Jcardnum').css({background: '#4169E1'});
        $('#Jloan').css({background: '#4169E1'});
        $('#Jcash').css({background: '#9400D3'});
        $('#Jmiddle').css({background: '#4169E1'});
        mouseleaveable_1 = true;
        mouseleaveable_2 = true;
        mouseleaveable_3 = false;
        mouseleaveable_4 = true;
    }else {
        $('#Jcardnum').css({background: '#4169E1'});
        $('#Jloan').css({background: '#4169E1'});
        $('#Jcash').css({background: '#4169E1'});
        $('#Jmiddle').css({background: '#9400D3'});
        mouseleaveable_1 = true;
        mouseleaveable_2 = true;
        mouseleaveable_3 = true;
        mouseleaveable_4 = false;
    }
}

$('#gdMap').css({opacity: 0, scale: 0.2}).transition({
    opacity: 1,
    scale: 1
}, 1000, 'linear');
/* =========================业务选择结束============================*/

/* =========================历史查询============================*/
// 新增查询的时候，会自动更新，会进行存储，默认只显示前4条
$('').on('click', function () {
    var data1 = $('#historydata1').text();
    var data2 = $('#historydata2').text();
    $('.historydata').transition({
        opacity: 0
    }, 500);
    $('.historydata').transition({
        opacity: 1
    }, 500);
    setTimeout(function () {
        $('#historydata1').text(data2)
    }, 500);
});
/* =========================历史查询结束============================*/


/* =========================时间选择器============================*/
var startV = '-1';//开始年月字符串
var endV = '-1';//结束年月字符串
var startY = '';//开始年
var startM = '';//开始月
var endY = '';//结束年
var endM = '';//结束月
laydate.skin('danlan');
var startTime = {
    elem: '#startTime',
    format: 'YYYY-MM',
    min: '2009-01', //设定最小日期为当前日期
    max: '2019-08', //最大日期
    istime: false,
    istoday: false,
    fixed: false,
    choose: function (datas) {
	endTime.min = datas; //开始日选好后，重置结束日的最小日期
        startV = datas;
        startY = startV.slice(0, 4);
        startM = startV.slice(5, 7);
		startTime.start = $('#startTime').val()+'-01';

    },
	  testClear: function(){
        if($('#startTime').val() == ''){
		endTime.min = '2009-01';

		//alert($('#startTime').val())
		}
    }

};
var endTime = {
    elem: '#endTime',
    format: 'YYYY-MM',
	min:'2009-01',
    max: '2019-08',
    istime: false,
    istoday: false,
    fixed: false,
    choose: function (datas) {
		startTime.max = datas; //结束日选好后，重置开始日的最大日期
        endV = datas;
        endY = endV.slice(0, 4);
        endM = endV.slice(5, 7);
		endTime.start = $('#endTime').val()+'-01';

    },
	 testClear: function(){
       if($('#endTime').val() == ''){

			startTime.max = '2019-08';


		//alert($('#endTime').val())

		}
    }
};


laydate(startTime);
laydate(endTime);

//点击时间选择器的时候更改样式
$('#endTime').on('click', function () {


    dateCss();
});

$('#startTime').on('click', function () {


    dateCss();
});
$('#end').on('click', function () {

    dateCss();
})




//更改日期插件的样式
function dateCss() {
    var arr = $('#laydate_box').attr('style').split(';');
    var cssStr =
        'position:absolute;right:0;';
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].indexOf('top') != -1) {
            cssStr += arr[i];
        }
    }
    $('#laydate_box').attr('style', cssStr);
}

var sendajax = function () {
    return new Promise((resolve) => {
        $.ajax({
            // nginx 的url http://localhost/proxy/getdata
            url: "http://localhost:9000/getsinglebuss",
            data: JSON.stringify({
                "banknames": searchcitys,
                "business": factor,
                "starttime": startV,
                "endtime": endV
            }),
            type: 'post',
            contentType: 'application/json;charset=utf-8',
            success: function (res) {
                arealist = res;
                resolve();
            }
        });
    });
}
//开始发送啦
$('#commit').on('click', async function () {
    arealist = [];
	if($('#startTime').val() == ''){
		alert('请输入带查询初始年月');
	}
	else {
		startV = $('#startTime').val();
		startTime.start = $('#startTime').val()+'-01';
	}
	if($('#endTime').val() == ''){
		alert('请输入带查询终止年月');
	}
	else{
		endV = $('#endTime').val();
		endTime.start = $('#endTime').val()+'-01';
	}
    for (let i = 0; i < selectedCity.length; i++) {
        searchcitys[i] = parsearea(selectedCity[i]);
    }
    for (let i = 0; i < 1; i++) {
        if (searchcitys.length == 0) {
            alert("请选择你要查询的城市");
            break;
        } else if (factor == '-1') {
            alert("请选择你要查询的业务");
            break;
        }
    }
    await sendajax();
    draw_main_chart();
})
// 默认查询请求
var get_default_main = function () {
    return new Promise((resolve) => {
        $.ajax({
            // nginx 的url http://localhost/proxy/getdata
            url: "http://localhost:9000/getdefaultmain",
            type: 'post',
            dataType: 'json',
            success: function (res) {
                arealist = res;
                resolve();
            }
        });
    });
}

// 页面加载进行推荐查询
async function process_main(hasdata) {
    if(!hasdata)
        await get_default_main();
    //console.log(arealist)

    //console.log(arealist[0])
    // 自动注入地图
    chart3('');
    // 自动显示图表
    factor = arealist[0].business;
    startY = arealist[0].data[0].time.slice(0, 4);//开始年
    startM = '01'//开始月
    endY = arealist[0].data[arealist[0].data.length - 1].time.slice(0, 4);//结束年
	if(endY == '2019'){
		endM = '08';
	}
    else endM = '12';//结束月
    selectedCity = [];
    for (let i = 0; i < arealist.length; i++) {
        selectedCity.push(arealist[i].bankname.slice(0, arealist[i].bankname.length - 2) + '市')
    }
    // 自动更改业务样式
    change_business_style();
    // 更改时间选择器
    $('#endTime').val(endY+"-"+endM);
    $('#startTime').val(startY+"-"+startM);
	startTime.start = $('#startTime').val()+'-01';
	endTime.start = $('#endTime').val() +'-01';
	startTime.max = $('#endTime').val();
	endTime.min =  $('#startTime').val();




    // 更改图表
    draw_main_chart();
}


// 将画图功能抽取出来，方便其他地方调用
function draw_main_chart() {
    //console.log(arealist)

    let myChart4 = echarts.init(document.getElementById('chart4'));
    let options = myChart4.getOption();
    myChart4.clear();


    if (factor == '开卡数') {
        options.yAxis[0] = {
            type: 'value',
            splitLine: {
                show: false,
            },
            axisLine: {
                lineStyle: {
                    color: '#fff'
                }
            },
            name: '开卡数/张',
            axisLabel: {formatter: '{value} '}
        };
    } else if (factor == '贷款数') {
        options.yAxis[0] = {
            type: 'value',
            splitLine: {
                show: false,
            },
            axisLine: {
                lineStyle: {
                    color: '#fff'
                }
            },
            name: '贷款数/百万元',
            axisLabel: {formatter: '{value} '}
        }
    } else if (factor == '存款数') {
        options.yAxis[0] = {
            type: 'value',
            splitLine: {
                show: false,
            },
            axisLine: {
                lineStyle: {
                    color: '#fff'
                }
            },
            name: '存款数/百万元',
            axisLabel: {formatter: '{value} '}
        }
    } else if (factor == '中间收入') {
        options.yAxis[0] = {
            type: 'value',
            splitLine: {
                show: false,
            },
            axisLine: {
                lineStyle: {
                    color: '#fff'
                }
            },
            name: '中间收入/百万元',
            axisLabel: {formatter: '{value} '}
        }
    }
	
    options.legend[0].data = selectedCity;
    options.xAxis[0].data = getChart4_bscissa();
    // 加载数据
    var dataIndex = options.xAxis[0].data;
    options.series = []; // 先清空
    if (dataIndex[0].indexOf('号') != -1) {
        //点进来在日里面
        for (var i = 0; i < selectedCity.length; i++) {
            options.series.push({name: '', type: '', data: ''});
            options.series[i].name = selectedCity[i];
            options.series[i].type = showstyle;
            options.series[i].data = getmonthcount(
                selectedCity[i].slice(0, selectedCity[i].length - 1) + '分行',
                curyear, clickMonth);
        }
        //点进来在月里面
    } else if (dataIndex[0].indexOf('月') != -1) {
        for (var i = 0; i < selectedCity.length; i++) {
            options.series.push({name: '', type: '', data: ''});
            options.series[i].name = selectedCity[i];
            options.series[i].type = showstyle;
            options.series[i].data = getmonth(
                selectedCity[i].slice(0, selectedCity[i].length - 1) + '分行',
                parseInt(startY));
        }
    } else {//点进来在年里面
        for (var i = 0; i < selectedCity.length; i++) {
            options.series.push({name: '', type: '', data: ''});
            options.series[i].name = selectedCity[i];
            options.series[i].type = showstyle;
            options.series[i].data = getyear(selectedCity[i].slice(0, selectedCity[i].length - 1) + '分行');
        }
    }

    myChart4.setOption(options);
}

/* =========================时间选择器结束============================*/

/* =========================核心查询图表============================*/
var myChart4 = echarts.init(document.getElementById('chart4'));
var showstyle = 'bar'
myChart4.on('magictypechanged',function(obj){

	if(myChart4.getOption().series[0].type =='bar'){
		showstyle = 'bar';
	}
	else {
		showstyle = 'line';
	}
});
//xAxis
function chart4() {
    // 基于准备好的dom，初始化echarts实例
    myChart4.clear();
    option = {
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
            }
        },
        dataZoom: {
            zoomOnMouseWheel: true,
            moveOnMouseMove: true,
            type: 'slider',
            //type:'inside',
            xAxisIndex: 0,
            filterMode: 'weakFilter',
            height: 20,
            bottom: 0,
            start: 0,
            end: 26,
            handleIcon: 'M10.7,11.9H9.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4h1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
            handleSize: '80%',
            show: true,
            realtime: true,
            y: '90%',
            height: 20,
            //backgroundColor: '#5F9EA0',
            dataBackgroundColor: 'rgb(0,0,0,0)',
            //fillerColor: '#00008B',
            //handleColor: '#00008B',
            textStyle: {
                color: '#fff',
            },
            start: 0,
            end: 100
        },
        color: ['#00008B', '#4169E1', '#5F9EA0', '#8A2BE2', '#44AFF0', '#4E82FF', '#584BFF', '#BE4DFF', '#F845F1'],
        calculable: true,
        legend: {
            data: ['开卡数', '贷款数', '存款数'],
            textStyle: {
                color: '#fff'
            },
        },
        xAxis: [
            {
                axisLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                },
                splitLine: {
                    show: false
                },
                type: 'category',

            }
        ],
        yAxis: [
            {
                type: 'value',
                splitLine: {
                    show: false,
                },
                axisLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                },


            }

        ],
        series: [
            /*
            {
                name:'广州市',
                type:'bar',
                data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: "#00FFE3"
                        },
                        {
                            offset: 1,
                            color: "#4693EC"
                        }
                        ])
                    }
                },
            } */
        ]
    };
    myChart4.setOption(option);
}

// 3D版本的柱形图，如果需要使用将函数名改为chart4即可
function chart4x() {

    option = {
        tooltip: {},
        visualMap: {
            show: false,
            max: 15,
            inRange: {
                color: ['#313695', '#4575b4', '#74add1', '#abd9e9', '#e0f3f8', '#ffffbf', '#fee090', '#fdae61', '#f46d43', '#d73027', '#a50026']
            }
        },
        xAxis3D: {
            name: '',
            nameGap: 1,
            type: 'category',
            data: ['2009年', '2010年', '2011年', '2012年', '2013年', '2014年', '2015年', '2016年', '2017年', '2018年', '2019年']
        },
        yAxis3D: {
            name: '',
            type: 'category',
            data: ['开卡数', '贷款书', '存款数', '中间收入']
        },
        zAxis3D: {
            name: '',
            type: 'value'
        },
        grid3D: {
            show: true,//是否显示三维迪卡尔坐标
            boxWidth: 200,//三维场景高度
            // boxHeight:200,//三维场景高度
            boxDepth: 100,//三维笛卡尔坐标系组件在三维场景中的深度。
            axisLine: {//坐标轴轴线(线)控制
                show: true,//该参数需设为true
                // interval:200,//x,y坐标轴刻度标签的显示间隔，在类目轴中有效。
                lineStyle: {//坐标轴样式
                    color: 'red',
                    opacity: 1,//(单个刻度不会受影响)
                    width: 2//线条宽度
                }
            }
        },
        series: [{
            name: '广州',
            type: 'bar3D',
            data: [[0, 0, 12], [1, 0, 11], [2, 0, 3], [3, 0, 15], [4, 0, 13], [5, 0, 11], [6, 0, 7], [7, 0, 18], [8, 0, 12], [9, 0, 15], [10, 0, 6],
                [0, 1, 12], [1, 1, 11], [2, 1, 3], [3, 1, 15], [4, 1, 13], [5, 1, 11], [6, 1, 7], [7, 1, 18], [8, 1, 12], [9, 1, 15], [10, 1, 6],
                [0, 2, 12], [1, 2, 11], [2, 2, 3], [3, 2, 15], [4, 2, 13], [5, 2, 11], [6, 2, 7], [7, 2, 11], [8, 2, 12], [9, 2, 15], [10, 2, 6],
                [0, 3, 4], [1, 3, 5], [2, 3, 13], [3, 3, 5], [4, 3, 7], [5, 3, 6], [6, 3, 16], [7, 3, 8], [8, 3, 12], [9, 3, 15], [10, 3, 6]],
            shading: 'lambert',
            stack: 'stack',
            label: {
                textStyle: {
                    fontSize: 16,
                    borderWidth: 1
                }
            },
            emphasis: {
                label: {
                    textStyle: {
                        fontSize: 20,
                    }
                },
            }
        },
            {
                name: '深圳',
                type: 'bar3D',
                stack: 'stack',
                data: [[0, 0, 12], [1, 0, 11], [2, 0, 3], [3, 0, 15], [4, 0, 13], [5, 0, 11], [6, 0, 7], [7, 0, 18], [8, 0, 12], [9, 0, 15], [10, 0, 6],
                    [0, 1, 12], [1, 1, 11], [2, 1, 3], [3, 1, 15], [4, 1, 13], [5, 1, 11], [6, 1, 7], [7, 1, 18], [8, 1, 12], [9, 1, 15], [10, 1, 6],
                    [0, 2, 12], [1, 2, 11], [2, 2, 3], [3, 2, 15], [4, 2, 13], [5, 2, 11], [6, 2, 7], [7, 2, 11], [8, 2, 12], [9, 2, 15], [10, 2, 6],
                    [0, 3, 4], [1, 3, 5], [2, 3, 13], [3, 3, 5], [4, 3, 7], [5, 3, 6], [6, 3, 16], [7, 3, 8], [8, 3, 12], [9, 3, 15], [10, 3, 6]],
                shading: 'lambert',
                label: {
                    textStyle: {
                        fontSize: 16,
                        borderWidth: 1
                    }
                },
                emphasis: {
                    label: {
                        textStyle: {
                            fontSize: 20,
                        }
                    },
                }
            }
        ]
    }
    myChart4.setOption(option);

}

chart4('');


/*==================数据获取的地方==================*/
/*横坐标参数，年月日信息
其中年和月静态，日由函数getday()动态获取*/
var year = ['2009年', '2010年', '2011年', '2012年', '2013年', '2014年', '2015年', '2016年', '2017年', '2018年', '2019年']
var month = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];
var day = [];

//计算一个月有多少天
function getday(params) {
    var list = [];
    if (params.indexOf('1') != -1 || params.indexOf('3') != -1 ||
        params.indexOf('5') != -1 || params.indexOf('7') != -1 ||
        params.indexOf('8') != -1 || params.indexOf('10') != -1 ||
        params.indexOf('12') != -1) {
        for (var i = 1; i <= 31; i++) {
            list.push(i + '号');
        }
        return list;
    } else if (params.indexOf('2') != -1) {//2月

        if (((curyear % 4 == 0) && (curyear % 100 != 0)) || (curyear % 400 == 0)) {
            for (var i = 1; i <= 29; i++) {
                list.push(i + '号');
            }

        } else {
            for (var i = 1; i <= 28; i++) {
                list.push(i + '号');
            }
        }
        return list;
    } else {
        for (var i = 1; i <= 30; i++) {
            list.push(i + '号');
        }
        return list;
    }
    return list;
};


/*纵坐标参数，测试的时候数据为静态，动态化给一下变量进行赋值即可
主要接收的json数据格式
var jasondata =
[
    {
        {
            {
                {},{},{},{} 4中业务数据
            },{},{}...{} 29/30/31天
        },{},{}...{} 12个月
    },{},{}...{} 1997-2019
    ]*/


/*===========================数据获取结束============================*/
/*滑动监听事件*/
myChart4.on('datazoom', function (params) {
    var options = myChart4.getOption();
    var startValue = options.dataZoom[0].startValue;
    var endValue = options.dataZoom[0].endValue;

    var refleshXAxisData = [];
    var refleshSeriesData = [];
    if (endValue - startValue == 0) { //滑动到只有一个数据
        var dataIndex = options.xAxis[0].data;
        if (dataIndex[0].indexOf('年') != -1) { //在年里面
            curyear = 2009 + startValue;
        } else if (dataIndex[0].indexOf('月') != -1) { //在月里面
            options.dataZoom[0].start = 0;
            options.dataZoom[0].end = 100;

        }
    }
})

// 双击跳到上一层  号--月--年
$('#chart4').on('dblclick', function (params) {
    var options = myChart4.getOption();
    var dataIndex = options.xAxis[0].data;

    if (dataIndex != null) {
        if (dataIndex[0].indexOf('号') != -1) {

            if (startY == endY) {
                options.xAxis[0].data = getChart4_bscissa();
                for (var i = 0; i < selectedCity.length; i++) {
                    options.series[i].data = getmonth(
                        selectedCity[i].slice(0, selectedCity[i].length - 1) + '分行',
                        parseInt(startY));
                }
            } else {
                options.xAxis[0].data = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];
                for (var i = 0; i < selectedCity.length; i++) {
                    options.series[i].data = getmonth(
                        selectedCity[i].slice(0, selectedCity[i].length - 1) + '分行',
                        curyear);
                }

            }
            //console.log(getChart4_bscissa())


            options.dataZoom[0].start = 0;
            options.dataZoom[0].end = 100;
            myChart4.clear();
            myChart4.setOption(options)
        }
        if (dataIndex[0].indexOf('月') != -1) {
            if (startY == endY) {
                alert("操作失败")
            } else {
                options.xAxis[0].data = chart4_bscissa;
                for (var i = 0; i < selectedCity.length; i++) {
                    options.series[i].data = getyear(
                        selectedCity[i].slice(0, selectedCity[i].length - 1) + '分行');

                }
            }
            options.dataZoom[0].start = 0;
            options.dataZoom[0].end = 100;
            myChart4.clear();
            myChart4.setOption(options)
        }
    }
});

var selectedCity = [];
var clickMonth;

/*实现点击进入具体某一年或者某一月的功能*/
myChart4.getZr().on('click', params => {
    // console.log(params);
    //console.log(selectedCity);
    const pointInPixel = [params.offsetX, params.offsetY];
    if (myChart4.containPixel('grid', pointInPixel)) {
        let xIndex = myChart4.convertFromPixel({seriesIndex: 0}, [params.offsetX, params.offsetY])[0];
        /*事件处理代码书写位置*/
        if (xIndex != null) {
            var options = myChart4.getOption();
            var dataIndex = options.xAxis[0].data;

            /*如果是某一天的图形点击事件*/
            if (dataIndex[0].indexOf('号') != -1) {

            } //点击*月  进入日
            else if (dataIndex[0].indexOf('月') != -1) {
                // 获取当前点击月份 进入了clickMonth月
                //clickMonth = xIndex + 1;
                if (startY == endY) {
                    clickMonth = xIndex + parseInt(startM);
                    curyear = startY;
                } else {
                    clickMonth = xIndex + 1;
                }
                options.xAxis[0].data = getday(clickMonth + '月');

                for (var i = 0; i < selectedCity.length; i++) {
                    options.series[i].data = getmonthcount(
                        selectedCity[i].slice(0, selectedCity[i].length - 1) + '分行',
                        curyear, clickMonth);
                }


                options.dataZoom[0].start = 0;
                options.dataZoom[0].end = 100;
                myChart4.clear();
                myChart4.setOption(options)
            } else {
                // 获取当前点击年份
                //var clickMonth = xIndex + 1997;
                curyear = xIndex + parseInt(chart4_bscissa[0]);


                options.xAxis[0].data = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];


                for (var i = 0; i < selectedCity.length; i++) {
                    options.series[i].data = getmonth(
                        selectedCity[i].slice(0, selectedCity[i].length - 1) + '分行',
                        curyear);
                }
                options.dataZoom[0].start = 0;
                options.dataZoom[0].end = 100;
                myChart4.clear();
                myChart4.setOption(options)
            }
        }

    }
});

var arealist = [];//用来存放地点list 的array
//--------------------------------------------------------------------------------------------------
//点击地图中的一个地方，发送第一次发送一个post请求，
//测试用
//arealist.push(testdata);
//arealist.push(testdata2);
//arealist.push(testdata3);
var area = '';//发送post请求时候的地点参数
var flag = '0' //判断arealist中是否有刚刚点击的地点数据的标志


//得到某一年，某个月，日的集合  city：**分行
function getmonthcount(city, year, month) { //year：2009  month：6
    year = year + '年'
    month = month + '月'
    var array = [];
	var array1 = [];
    var templist = cachicatch(city); //x
    var templist1 = templist;
    if (startY == endY) {
        templist1 = templist1.data[0].data;
        //console.log(month)
        var i = parseInt(startM);//6    678
        var j = parseInt(endM) + 1;//9
        for (; i < j; i++) {
            if (templist1[i - 1].time == month) {
                templist = templist1[i - 1];
            }
        }
    } else {
        for (var i = 0; i < templist.data.length; i++) {
            if (templist.data[i].time == year) {
                templist = templist.data[i];
            }
        }
        //console.log(templist)
        for (var i = 0; i < templist.data.length; i++) {
            if (templist.data[i].time == month) {
                templist = templist.data[i];
            }
        }
    }
	array1 =[].concat(templist.data);
	if(factor != '开卡数'){
		return parsearray(array1)
	}
   else return array1;
    
}

//得到一个集合的和
function arraysum(arr) {
    var sum = 0;
    for (var i = 0; i < arr.length; i++) {
		
        sum += arr[i];
    }
    return sum;
}

//针对钱来处理的函数：除以100000再保留两位小数
function parsearray(array){
	for(var i=0;i<array.length;i++){
		array[i] /= 1000000.0;
		array[i] = array[i].toFixed(2);
		array[i] = parseFloat(array[i]);
	}
	return array;
}

//得到某个地区某一年的集合(月的集合)
function getmonth(city, year) {
    year = year + '年'
    var array = [];
    var templist = cachicatch(city);

    if (startY == endY) {
        var i = parseInt(startM);
        var count = parseInt(endM) - i + 1;
        templist = templist.data[0].data;
        for (var k = 0; k < count; k++) {
            array[k] = arraysum(templist[i - 1].data);
            i++;
        }

    } else {
        for (var i = 0; i < templist.data.length; i++) {
            if (templist.data[i].time == year) {
                templist = templist.data[i];
            }
        }
        for (var i = 0; i < templist.data.length; i++) {
            array[i] = arraysum(templist.data[i].data);
        }
    }
	 var array1 = [].concat(array);
	 if(factor!='开卡数'){
		return parsearray(array1);
	 }
	 else return array1;

}

//得到某个地区的集合
function getyear(city) {

    var array = [];
    var array1 = [];
    var templist = cachicatch(city);
    for (var i = 0; i < templist.data.length; i++) {
        array1[i] = templist.data[i].time.slice(0, name.length - 1);
    }

    for (var i = 0; i < templist.data.length; i++) {
        array[i] = arraysum(getmonth(city, array1[i]))
    }
    if(factor != '开卡数'){
        for(var i=0;i<array.length;i++){
            array[i] = array[i].toFixed(2);
            array[i] = parseFloat(array[i]);
        }
    }
	return array;
}


function parsearea(name) {
    name = name.slice(0, name.length - 1);
    name = name + '分行';
    return name;
}

//返回bankname为area的整个list
function cachicatch(city) {
    for (var i = 0; i < arealist.length; i++) {
        if (arealist[i].bankname == city) {
            return arealist[i];
        }

    }
}

function getChart4_bscissa() {
    chart4_bscissa = [];
    let templist = arealist[0];
    for (let i = 0; i < templist.data.length; i++) {
        chart4_bscissa[i] = templist.data[i].time;
    }

    if (chart4_bscissa.length == 1) {
        chart4_bscissa = [];
        templist = templist.data[0].data;
        let i = parseInt(startM);
        let count = parseInt(endM) - i + 1;
        for (let k = 0; k < count; k++) {
            chart4_bscissa[k] = templist[i - 1].time;
            i++;
        }
    }
    return chart4_bscissa;
}

/*获取选择的地点，并赋予到查询表的图例中*/
myChart3.on('mapselectchanged', function (params) {
        selectedCity = []; // 清空数组  被选中的城市集合
        //赋上地点变量
        area = params.batch[0].name;
        area = parsearea(area);
        var citys = params.batch[0].selected;//全部城市:true/false
        var keys = Object.keys(citys);//全部城市名
        for (var i = 0; i < keys.length; i++) {
            if (citys[keys[i]] == true) {
                selectedCity.push(keys[i]);//被选中的城市集合
            }
        }

    }
);

/*点击方法区域*/
$('.close-pop').on('click', function () {
    $(this).parent().parent().hide();
    $('.pop-up1').attr('style', 'visibility: hidden');
    $('#pages-div').attr('style', 'visibility: hidden');
    $('#ana_config_page').attr('style', 'visibility: hidden');
    $('#ana_chart_page').attr('style', 'visibility: hidden');

});
// 分析界面标题 点击放大
$('#title1').on('click', function () {
    // 泽涵的eq0保存，现在放大别的界面
    //$('.container').attr('style', 'visibility: visible').find('.pop-up').eq(0).attr('style', 'visibility: visible').siblings().attr('style', 'visibility: hidden');
    //$('#filCon').hide();
    $('.container').attr('style', 'visibility: visible').find('.pop-up').eq(5).attr('style', 'visibility: visible').siblings().attr('style', 'visibility: hidden');
    $('#ana_config_btn').attr('style', 'background: #4169E1');
    $('#ana_config_page').attr('style', 'visibility: visible');
    $('#ana_chart_page').attr('style', 'visibility: hidden');
    ana_choose = 0;
});

$('#ana_config_btn').on('click', function () {
    $('#ana_config_btn').attr('style', 'background: #4169E1');
    $('#ana_chart_btn').attr('style', 'background: #0c1a2c');
    $('#ana_config_page').attr('style', 'visibility: visible');
    $('#ana_chart_page').attr('style', 'visibility: hidden');
    ana_choose = 0;
})

$('#ana_chart_btn').on('click', function () {
    $('#ana_config_btn').attr('style', 'background: #0c1a2c');
    $('#ana_chart_btn').attr('style', 'background: #4169E1');
    $('#ana_config_page').attr('style', 'visibility: hidden');
    $('#ana_chart_page').attr('style', 'visibility: visible');
    ana_choose = 1;
})
// 预测界面标题点击放大
$('#title2').on('click', function () {
    $('.container').attr('style', 'visibility: visible').find('.pop-up').eq(1).attr('style', 'visibility: visible').siblings().attr('style', 'visibility: hidden');
    $('#pre_chart_btn').attr('style', 'background: #4169E1');
    pre_choose_chart = 1;
    pre_choose_plan = 0;
});
// 查询历史标题 点击放大
$('#tohistory').on('click', function () {
    $('.container').attr('style', 'visibility: visible').find('.pop-up').eq(2).attr('style', 'visibility: visible').siblings().attr('style', 'visibility: hidden');
});
// 地图界面放大
$("#tobigmap").on('click', function () {
    $('.container').attr('style', 'visibility: visible').find('.pop-up').eq(3).attr('style', 'visibility: visible').siblings().attr('style', 'visibility: hidden');
});

$('#pre_plan_btn').on('click', function () {
    $('.container').attr('style', 'visibility: visible').find('.pop-up').eq(4).attr('style', 'visibility: visible').siblings().attr('style', 'visibility: hidden');
    pre_choose_chart = 0;
    pre_choose_plan = 1;
    $('#plan_plan_btn').attr('style', 'background: #4169E1');
    $('#plan_chart_btn').attr('style', 'background: #0c1a2c');
    $('#pre_plan_btn').attr('style', 'background: #0c1a2c');
    $('#pre_chart_btn').attr('style', 'background: #0c1a2c');
});

$('#plan_chart_btn').on('click', function () {
    $('.container').attr('style', 'visibility: visible').find('.pop-up').eq(1).attr('style', 'visibility: visible').siblings().attr('style', 'visibility: hidden');
    $('#plan_plan_btn').attr('style', 'background: #0c1a2c');
    $('#plan_chart_btn').attr('style', 'background: #0c1a2c');
    $('#pre_plan_btn').attr('style', 'background: #0c1a2c');
    $('#pre_chart_btn').attr('style', 'background: #4169E1');
    pre_choose_chart = 1;
    pre_choose_plan = 0;
});

//放大历史保存excel页面
$('#his-save').on('click', function () {
    $('.container').attr('style', 'visibility: visible').find('.ana-pop-up').eq(1).attr('style', 'visibility: visible').siblings().attr('style', 'visibility: hidden');
});

// 查询历史放大图表
var historychat = echarts.init(document.getElementById('historychat'));

function chart_history() {
    historychat.clear();
    option = {
        title: {
            text: '统计信息',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            x: 'center',
            y: 'bottom',
            data: ['查询', '分析', '预测']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        series: [
            {
                name: '占比',
                type: 'pie',
                radius: [30, 110],
                //center : ['75%', 200],
                roseType: 'area',
                //x: '50%',               // for funnel
                sort: 'ascending',     // for funnel
                data: [
                    {value: 10, name: '查询'},
                    {value: 5, name: '分析'},
                    {value: 15, name: '预测'}
                ]
            }
        ]
    };
    historychat.setOption(option);
}

chart_history();

$('.savehistory').on('click', function () {
    $(".table3").table2excel({
        exclude: ".excludeThisClass",
        name: "查询历史",
        filename: "history" //do not include extension
    });
})

// websocket 相关代码
// 默认加载websocket服务
//websocket 全局变量
var stompClient;

function connect(token) {
    var socket = new SockJS('http://localhost:9000/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected:' + frame);
        // 订阅公有业务
        stompClient.subscribe('/topic/public', function (response) {
            showResponse(JSON.parse(response.body).responseMessage);
        });
        // 订阅私有业务
        stompClient.subscribe('/topic/' + token, function (response) {
            arealist = JSON.parse(response.body);
            process_main(true)
        });
        stompClient.subscribe('/topic/test', function (res) {
            // console.log(response.body);
            arealist = JSON.parse(res.body);
            process_main(true)
        });
    });
};


$(window).on('unload', function () {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    //console.log('Disconnected');
});

function showResponse(message) {
    //console.log(message);
    //$("#").html(message);
}

function showCallback(message) {
    //console.log(message);
    //$("#").html(message);
}

function registerwebsock() {
    $.ajax({
        url: "http://localhost:9000/registerwebsocket",
        type: "get",
        success: function (token) {
            connect(token);
        }
    });

}

// 页面加载的时候运行的
draw_default_pre(); //默认预测
anasimplechart(); //默认分析
process_main(false); //默认查询
