var Main1 = {
    data() {
        return {
            cities: [{
                value: 'Guangzhou',
                label: '广州分行'
            },{
                value: 'Shaoguan',
                label: '韶关分行'
            },{
                value: 'Shenzhen',
                label: '深圳分行'
            },{
                value: 'Zhuhai',
                label: '珠海分行'
            },{
                value: 'Shantou',
                label: '汕头分行'
            },{
                value: 'Foshan',
                label: '佛山分行'
            },{
                value: 'Jiangmen',
                label: '江门分行'
            },{
                value: 'Zhanjiang',
                label: '湛江分行'
            },{
                value: 'Maoming',
                label: '茂名分行'
            },{
                value: 'Zhaoqing',
                label: '肇庆分行'
            },{
                value: 'Huizhou',
                label: '惠州分行'
            },{
                value: 'Meizhou',
                label: '梅州分行'
            },{
                value: 'Shangwei',
                label: '汕尾分行'
            },{
                value: 'Heyuan',
                label: '河源分行'
            },{
                value: 'Yangjiang',
                label: '阳江分行'
            },{
                value: 'Qingyuan',
                label: '清远分行'
            },{
                value: 'Dongguan',
                label: '东莞分行'
            },{
                value: 'Zhongshan',
                label: '中山分行'
            },{
                value: 'Chaozhou',
                label: '潮州分行'
            },{
                value: 'Jieyang',
                label: '揭阳分行'
            },{
                value: 'Yunfu',
                label: '云浮分行'
            }
            ],			zexianvalue:'',

            hzfangshiList:[{
                hzfangshi:'求和'
            },{
                hzfangshi:'求平均'
            },{
                hzfangshi:'求中位数'
            },{
                hzfangshi:'求最大值'
            },{
                hzfangshi:'求最小值'
            },{
                hzfangshi:'求标准差'
            },{
                hzfangshi:'求方差'
            }]
            ,
            jgshaixuanList:[{
                jgshaixuan:'汇总方式（求和）'
            },{
                jgshaixuan:'过滤'
            }],
            zblist :[{
                id:1,
                label:'今日存款金额',
                isL:1
            }
            // ,{
            //     id:2,
            //     label:'昨日存款金额',
            //     isL:1
            // },{
            //     id:3,
            //     label:'上月同日存款金额',
            //     isL:1
            // }
            ],
            spanList:[],
            currentPage: 1, // 当前页码
            total: 20, // 总条数
            pageSize: 2, // 每页的数据条数
            tableIndex:1,
            tableData4: [{
                bankName: '深圳分行',
                zb1: '6887500',
                zb2: '6650000',
                zb3: '5031600',
                zb4: '3.57%',
            }, {
                bankName: '广州分行',
                zb1: '6758900',
                zb2: '6013800',
                zb3: '7362700',
                zb4: '1.63%',
            }, {
                bankName: '汕尾分行',
                zb1: '6243200',
                zb2: '4092800',
                zb3: '6785000',
                zb4: '3.81%',
            }, {
                bankName: '佛山分行',
                zb1: '3887500',
                zb2: '3720000',
                zb3: '4545600',
                zb4: '4.50%',
            }, {
                bankName: '东莞分行',
                zb1: '3286000',
                zb2: '3489200',
                zb3: '4808200',
                zb4: '-5.82%',
            }, {
                bankName: '中山分行',
                zb1: '4866400',
                zb2: '3737600',
                zb3: '5624400',
                zb4: '30.20%',
            }, {
                bankName: '珠海分行',
                zb1: '2130700',
                zb2: '1814500',
                zb3: '3428800',
                zb4: '17.42%',
            }, {
                bankName: '江门分行',
                zb1: '4576000',
                zb2: '3737600',
                zb3: '5624400',
                zb4: '22.43%',
            }, {
                bankName: '肇庆分行',
                zb1: '4347900',
                zb2: '4284000',
                zb3: '4414400',
                zb4: '1.49%',
            }, {
                bankName: '惠州分行',
                zb1: '2089600',
                zb2: '2844800',
                zb3: '3608000',
                zb4: '-26.54%',
            }, {
                bankName: '汕头分行',
                zb1: '3652600',
                zb2: '4622700',
                zb3: '3504200',
                zb4: '-20.98%',
            }, {
                bankName: '潮州分行',
                zb1: '4529400',
                zb2: '4995000',
                zb3: '4919300',
                zb4: '-9.32%',
            }, {
                bankName: '揭阳分行',
                zb1: '2917700',
                zb2: '2957800',
                zb3: '2700200',
                zb4: '2.43%',
            }, {
                bankName: '湛江分行',
                zb1: '2452000',
                zb2: '2628400',
                zb3: '3088200',
                zb4: '-1.35%',
            }, {
                bankName: '茂名分行',
                zb1: '3016400',
                zb2: '3166800',
                zb3: '3953500',
                zb4: '-4.74%',
            }, {
                bankName: '阳江分行',
                zb1: '2619400',
                zb2: '2508800',
                zb3: '2624400',
                zb4: '4.40%',
            }, {
                bankName: '云浮分行',
                zb1: '2390000',
                zb2: '1881100',
                zb3: '2333600',
                zb4: '27.05%',
            }, {
                bankName: '韶关分行',
                zb1: '1596600',
                zb2: '2901600',
                zb3: '1964300',
                zb4: '-44.97%',
            }, {
                bankName: '清远分行',
                zb1: '1149000',
                zb2: '1384000',
                zb3: '1442900',
                zb4: '-16.97%',
            }, {
                bankName: '梅州分行',
                zb1: '4186900',
                zb2: '4265000',
                zb3: '4206700',
                zb4: '-1.83%',
            }, {
                bankName: '河源分行',
                zb1: '1056000',
                zb2: '1231600',
                zb3: '1964300',
                zb4: '14.25%',
            }],
            treedata: [{
                label: '数值字段',
                children: [{
                    id:1,
                    label: '今日存款金额',
                    isL:1
                },{
                    id:2,
                    label: '昨日存款金额',
                    isL:1
                }]

            }],
            treedata: [{
                label: '数值字段',
                children: [{
                    id:1,
                    label: '今日存款金额',
                    isL:1
                },{
                    id:2,
                    label: '昨日存款金额',
                    isL:1
                }]

            }],
            checkList: [],
            value1: '',
            options: [{
                value: '开卡数',
                label: '开卡表'
            }, {
                value: '中间收入',
                label: '中间业务表'
            }, {
                value: '存款数',
                label: '存款表'
            }, {
                value: '贷款数',
                label: '贷款表'
            }],
            value: '',
            value1: '开卡表',
            value2: '存款表',
            value3: '贷款表',
            value4: '中间业务表',
            value6: '',
            check: "存款数",
            check2: "今日存款金额",
            fenlei: [{
                value: '存款数',
                label: '存款数'
            }],
            zhizhou: [{
                value: '今日存款金额',
                label: '今日存款金额'
            },{
                value: '昨日存款金额',
                label: '昨日存款金额'
            },{
                value:'上月同日存款金额',
                label:'上月同日存款金额'
            }],
            dialogTableVisible: false,
            dialogFormVisible: false,
            outerVisible: false,
            innerVisible: false,
            gongshiVisible: false,
            biaogeIsView: 0,
            zhexianIsView:1,
            zhuzhuangIsView:1,
            binIsView:1,
            form: {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: ''
            },
            dateform:{
                startTime:'',
                endTime:''
            },
            formLabelWidth: '120px',
            defaultProps: {
                children: 'children',
                label: 'label'
            }
        };
    },methods: {
        aclick(){
            var el = event.currentTarget;
            var v =  el.id;
            if (v==='biaogeV'){
                if (this.biaogeIsView===0){//0是打开了 1是没打开
                    document.getElementById("biaogeView").style.display = "none";
                    this.biaogeIsView = 1;
                }else {
                    document.getElementById("biaogeView").style.display = '';
                    document.getElementById("zhexianView").style.display = "none";
                    document.getElementById("zhuzhuangView").style.display = "none";
                    document.getElementById("binView").style.display = "none";
                    this.biaogeIsView = 0;
                    this.zhexianIsView = 1;
                    this.zhuzhuangIsView = 1;
                    this.binIsView = 1;
                }
            }else if(v==='zhexianV'){//0是打开了 1是没打开
                if (this.zhexianIsView===0){
                    document.getElementById("zhexianView").style.display = "none";
                    this.zhexianIsView = 1;
                }else {
                    document.getElementById("zhexianView").style.display = '';
                    document.getElementById("biaogeView").style.display = "none";
                    document.getElementById("zhuzhuangView").style.display = "none";
                    document.getElementById("binView").style.display = "none";
                    this.zhexianIsView = 0;
                    this.biaogeIsView = 1;
                    this.zhuzhuangIsView = 1;
                    this.binIsView = 1;
                }
            }else if(v==='zhuzhuangV'){
                if (this.zhuzhuangIsView===0){
                    document.getElementById("zhuzhuangView").style.display = "none";
                    this.zhuzhuangIsView = 1;
                }else {
                    document.getElementById("zhuzhuangView").style.display = '';
                    document.getElementById("biaogeView").style.display = "none";
                    document.getElementById("zhexianView").style.display = "none";
                    document.getElementById("binView").style.display = "none";
                    this.zhuzhuangIsView = 0;
                    this.biaogeIsView = 1;
                    this.zhexianIsView = 1;
                    this.binIsView = 1;
                }
            }else if(v==='binV'){
                if (this.binIsView===0){
                    document.getElementById("binView").style.display = "none";
                    this.binIsView = 1;
                }else {
                    document.getElementById("binView").style.display = '';
                    document.getElementById("biaogeView").style.display = "none";
                    document.getElementById("zhexianView").style.display = "none";
                    document.getElementById("zhuzhuangView").style.display = "none";
                    this.binIsView = 0;
                    this.biaogeIsView = 1;
                    this.zhuzhuangIsView = 1;
                    this.zhexianIsView = 1;
                }
            }
        },
        handleSizeChange(val) {
            //console.log(`每页 ${val} 条`);
            this.currentPage = 1;
            this.pageSize = val;
        },
        handleCurrentChange(val) {
            // console.log(`当前页: ${val}`);
            this.currentPage = val;
        },
        deleteRow(index, rows) {
            rows.splice(index, 1);
        },

        chooseSelectChange(t) {
            // alert(t);//打印选择器的选择值
        },
        add() {
            var zbName = this.form.name;
            if (zbName !== '') {
                var newItem = {
                    label: zbName
                }
                this.zblist.push(newItem)
            }
            this.form = {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: ''
            };

            this.dialogFormVisible = false
            var string = document.getElementById("gsDiv1").innerText
            // // alert(document.getElementById("gsDiv1").innerText);
            // var fields = {
            //     '今日存款金额': 3219890,
            //     '昨日存款金额': 3022122,
            // };
            // alert(this.rightzze(string, fields));
            if(this.tableIndex<4){
                let str1 = "table"+this.tableIndex;
                document.getElementById(str1).style.display="none";
                this.tableIndex++;
                let str2 = "table"+this.tableIndex;
                document.getElementById(str2).style.display='';
                console.log(str1+str2);

            }
        },
        rightzze(string, obj) {
            // 剔除空白符
            string = string.replace(/\s/g, '');

            // 错误情况，空字符串
            if ("" === string) {
                return false;
            }

            // 错误情况，运算符连续
            if (/[\+\-\*\/]{2,}/.test(string)) {
                return false;
            }

            // 空括号
            if (/\(\)/.test(string)) {
                return false;
            }

            // 错误情况，括号不配对
            var stack = [];
            for (var i = 0, item; i < string.length; i++) {
                item = string.charAt(i);
                if ('(' === item) {
                    stack.push('(');
                } else if (')' === item) {
                    if (stack.length > 0) {
                        stack.pop();
                    } else {
                        return false;
                    }
                }
            }
            if (0 !== stack.length) {
                return false;
            }

            // 错误情况，(后面是运算符
            if (/\([\+\-\*\/]/.test(string)) {
                return false;
            }

            // 错误情况，)前面是运算符
            if (/[\+\-\*\/]\)/.test(string)) {
                return false;
            }

            // 错误情况，(前面不是运算符
            if (/[^\+\-\*\/]\(/.test(string)) {
                return false;
            }

            // 错误情况，)后面不是运算符
            if (/\)[^\+\-\*\/]/.test(string)) {
                return false;
            }

            // 错误情况，变量没有来自“待选公式变量”
            var stringlast = string;
            var tmpStr = string.replace(/[\(\)\+\-\*\/0123456789]{1,}/g, '`');
            var array = tmpStr.split('`');
            for (var i = 0, item; i < array.length; i++) {
                item = array[i];
                if (/[A-Z]/i.test(item) && 'undefined' === typeof(obj[item])) {<!-- i是不区分大小写 g是多匹配-->
                    return false;
                }
            }
            //打印出含数字的结果
            for (var i = 0, item; i < array.length; i++) {
                item = array[i];//文字
                if (item!==''){
                    var o = obj[item];//对应的数字
                    stringlast = stringlast.replace(item, o);
                }
            }
            var result = eval(stringlast);
            result = result.toFixed(3); //取小数点三位
            // alert(stringlast + ' = ' + result);
            return true;
        },
        quxiao() {
            this.form = {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: ''
            };
            this.dialogFormVisible = false
        },
        innerquxiao() {
            this.form = {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: ''
            };
            this.innerVisible = false
            document.getElementById("gongshiDiv").style.display = "none";
        },
        zbWaysChange(v) {
            if (v === 'chaxun') {
                document.getElementById("gongshiDiv").style.display = "none";
                this.innerVisible = true;
            }
            if (v === 'gongshi') {
                document.getElementById("gongshiDiv").style.display = "";
            }

        },
        addcxDate(t) {
            // this.dateform.startTime = t[0];
            // this.dateform.endTime = t[1];
            // if(this.tableIndex<4){
            //     let str1 = "table"+this.tableIndex;
            //     document.getElementById(str1).style.display="none";
            //     this.tableIndex++;
            //     let str2 = "table"+this.tableIndex;
            //     document.getElementById(str2).style.display='';
            //     console.log(str1+str2);
            //
            // }
           this.innerVisible=false;


        },
        getTreeChecked(data, node) {
            if (data.isL === 1) {
                //alert(data.id);
                //alert(data.label);
                //调用div增加span方法
                var t = {
                    fuhao: 'no',
                    label: data.label
                }
                this.spanList.push(t)
                // vmspan.addToDiv(data.label);
                this.$forceUpdate();
                //alert('2');
            }
        },
        iclick() {
            var el = event.currentTarget;
            var t = {
                fuhao: 'yes',
                label: el.id
            }
            this.spanList.push(t);
        },
        clicktianjia(){
            this.form = {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: ''
            };
            this.dialogFormVisible = true;
            document.getElementById("gongshiDiv").style.display = "none";
            this.spanList=[]
        },
        tableRowClassName ({row, rowIndex}) {
            //把每一行的索引放进row
            row.index = rowIndex;
        },
        rowClick(row,event,column){
            // alert(row.index);
        },
        getDate(t) {
            let myChart = echarts.init(document.getElementById('main'));
            let options = myChart.getOption();
            myChart.clear();
            var getDate = function(str) {
                var tempDate = new Date();
                var list = str.split("-");
                tempDate.setFullYear(list[0]);
                tempDate.setMonth(list[1] - 1);
                tempDate.setDate(list[2]);
                return tempDate;
            }
            var date1 = getDate(t[0]);
            var date2 = getDate(t[1]);
            if (date1 > date2) {
                var tempDate = date1;
                date1 = date2;
                date2 = tempDate;
            }
            date1.setDate(date1.getDate() + 1);
            var dateArr = [];
            var i = 0;
            while (!(date1.getFullYear() == date2.getFullYear()
                && date1.getMonth() == date2.getMonth() && date1.getDate() == date2
                    .getDate())) {
                var dayStr =date1.getDate().toString();
                if(dayStr.length ==1){
                    dayStr="0"+dayStr;
                }
                var monthStr = (date1.getMonth() + 1).toString();
                if(monthStr.length ==1){
                    monthStr="0"+monthStr;
                }
                dateArr[i] = date1.getFullYear() + "-" + monthStr + "-"
                    + dayStr;
                i++;
                date1.setDate(date1.getDate() + 1);
            }
            dateArr.splice(0,0,t[0])
            dateArr.push(t[1]);
            console.log(dateArr);
            options.xAxis[0].data = dateArr;
            var coldata = [];
            var min = Math.ceil(20);
            var max = Math.floor(40);
            for(var i=0;i<dateArr.length;i++){

                coldata.push(Math.floor(Math.random() * (max - min)) + min); //不含最大值，含最小值
            }
            console.log(coldata);
            options.yAxis[0].name = "开卡数/张";
            options.series[0].data = coldata;
            myChart.setOption(options);
        },

        changebar(d){
            let myChartbar = echarts.init(document.getElementById('zhuzhuang'));
            let options = myChartbar.getOption();
            myChartbar.clear();
            let bardata = [];
            let min = Math.ceil(10000);
            let max = Math.floor(100000);
            for(let i=0;i<21;i++){

                bardata.push(Math.floor(Math.random() * (max - min)) + min); //不含最大值，含最小值
            }
            console.log(bardata);
            options.yAxis[0].name = "存款数/元";
            options.series[0].data = bardata;
            myChartbar.setOption(options);
        }




    }
};
var Ctor1 = Vue.extend(Main1)
new Ctor1().$mount('#appdate1')

// 基于准备好的dom，初始化echarts实例
var myCharthan = echarts.init(document.getElementById('main'));
myCharthan.clear();
option = {
    title : {

    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['开卡数/张']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['bar', 'line']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,

        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLabel : {
                formatter: '{value}'
            }
        }
    ],
    series : [
        {
            name:'开卡数',
            type:'line',

            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        }

    ]
};

myCharthan.setOption(option);

//柱状图
var myChartbar = echarts.init(document.getElementById('zhuzhuang'));
let cityName= [ '广州分行','韶关分行','深圳分行', '珠海分行','汕头分行','佛山分行','江门分行','湛江分行','茂名分行','肇庆分行','惠州分行'
    ,'梅州分行', '汕尾分行','河源分行','阳江分行', '清远分行','东莞分行', '中山分行', '潮州分行','揭阳分行','云浮分行'];
var xAxisData = [];
var data1 = [];

for (var i = 0; i < 21; i++) {
    xAxisData.push('银行' + (i+1));
    data1.push(Math.random() * 2);
}

var itemStyle = {
    normal: {
        color: '#526cdf'
    },
    emphasis: {
        barBorderWidth: 1,
        shadowBlur: 10,
        shadowOffsetX: 0,
        shadowOffsetY: 0,
        shadowColor: 'rgba(0,0,0,0.5)'
    }
};

option = {
    backgroundColor: '#ffffff',
    legend: {
        data: ['银行','数额'],
        align: 'left',
        left: 10
    },
    toolbox: {
        feature: {
            magicType: {
                type: ['stack', 'tiled']
            },
            dataView: {}
        }
    },
    tooltip: {},
    xAxis: {
        data: cityName,
        name: 'X Axis',
        silent: false,
        axisLine: {onZero: true},
        splitLine: {show: false},
        splitArea: {show: false}
    },
    yAxis: {
        splitArea: {show: false}
    },
    grid: {
        left: 100
    },
    series: [
        {
            name: '金额',
            type: 'bar',
            // stack: 'one',
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            itemStyle: itemStyle,
            data: data1
        }
    ]
};

myChartbar.setOption(option);