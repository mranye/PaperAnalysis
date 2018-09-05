<%@ page import="com.education.paper.entity.Paper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!--让IE使用最新的渲染模式-->
    <meta http-equiv="X-UA-Compatible" content="IE-edge,chrome=1,charset=UTF-8" >
    <!--页面宽度适应浏览器宽度，且不允许客户缩放-->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>高校学生试卷分析系统</title>
    <link rel="stylesheet" href="../../css/Reset.css">
    <link rel="stylesheet" href="../../css/PaperManagerShow.css">
    <script type="text/javascript" src="../../js/echarts.min.js"></script>
    <script type="text/javascript" src="../../js/jquery-3.3.1.js"></script>
</head>
<body>
<script>
    /*菜单抽屉效果*/
    $(function() {
        var Accordion = function(el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            var links = this.el.find('.link');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
        }
        Accordion.prototype.dropdown = function(e) {
            var $el = e.data.el;
            $this = $(this);
            $next = $this.next();

            $next.slideToggle();
            $this.parent().toggleClass('open');

            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
            };
        }
        var accordion = new Accordion($('#mymenu'), false);
    });
</script>
<%
    Paper paper = (Paper)session.getAttribute("showPaper");
    int num = paper.getQuestion_num();
    if (num==0){
        num = 5;
    }
    String[] type = new String[num];
    float[] scoreRate = new float[num];
    for (int i = 0;i<num;i++){
        if(i == 0){
            type[i] = paper.getQuestionType1();
            scoreRate[i] = paper.getQuestionScoreRate1();
        }else if (i==1){
            type[i] = paper.getQuestionType2();
            scoreRate[i] = paper.getQuestionScoreRate2();
        }else if (i==2){
            type[i] = paper.getQuestionType3();
            scoreRate[i] = paper.getQuestionScoreRate3();
        }else if (i==3){
            type[i] = paper.getQuestionType4();
            scoreRate[i] = paper.getQuestionScoreRate4();
        }else if (i==4){
            type[i] = paper.getQuestionType5();
            scoreRate[i] = paper.getQuestionScoreRate5();
        }else if (i==5){
            type[i] = paper.getQuestionType6();
            scoreRate[i] = paper.getQuestionScoreRate6();
        }else if (i==6){
            type[i] = paper.getQuestionType7();
            scoreRate[i] = paper.getQuestionScoreRate7();
        }else if (i==7){
            type[i] = paper.getQuestionType8();
            scoreRate[i] = paper.getQuestionScoreRate8();
        }else if (i==8){
            type[i] = paper.getQuestionType9();
            scoreRate[i] = paper.getQuestionScoreRate9();
        }
    }
%>
<div>
    <nav><!--页头-->
        <ul>
            <li><a href="/signUpOrIn">首页</a></li>
            <li><a href="#">系统说明</a></li>
            <li class="logo"><a href="#">退出</a></li>
            <li class="logo"><a href="#">${sessionScope.currentTeacher.username}</a></li>
        </ul>
    </nav>
    <div class="content"><!--内容-->
        <div class="left"><!--左侧菜单栏-->
            <ul class="mymenu" id="mymenu">
                <li onclick="gotoUser()" ><div class="link">信息设置</div></li>
                <li ><div class="link">试卷分析</div>
                    <ul class="submenu" id="usermanager">
                        <li><a href="/liAnalysisMessage">信息设置</a></li>
                        <li><a href="/liAnalysisInput" >数据录入</a></li>
                        <li><a href="/liAnalysisQuestion">题目设置</a></li>
                        <li><a href="/liAnalysisAnswer">填写分析</a></li>
                    </ul>
                </li>
                <li ><div class="link" id="liknowledge">知识点分析</div>
                    <ul class="submenu">
                        <li><a href="/liKnowledgeManager">知识点管理</a></li>
                        <li><a href="/liKnowledgeMessage" >信息设置</a></li>
                        <li><a href="/liKnowledgeInput">成绩录入</a></li>
                        <li><a href="/liKnowledgeAnswer">填写分析</a></li>
                    </ul>
                </li>
                <li onclick="gotoPaper()"><div class="link" id="activeBottom">报告管理</div></li>
            </ul>
        </div>
        <div  class="right" id="right"><!--右侧内容区-->
            <div class="content-show">
                <div class="content-show-title">
                    <h1>试卷分析报告</h1>
                    <hr/>
                </div>
                <div class="content-show-message">
                    <table cellpadding="2" cellspacing="15">
                        <tr>
                            <th>教师姓名：</th>
                            <td>${sessionScope.currentTeacher.teacher_name}</td>
                            <th>课程名称：</th>
                            <td>${sessionScope.currentTeacher.course}</td>
                        </tr>
                        <tr>
                            <th>所在院系：</th>
                            <td>${sessionScope.currentTeacher.college}</td>
                            <th>专业年级：</th>
                            <td>${sessionScope.showPaper.profession}</td>
                        </tr>
                        <tr>
                            <th>学时学分：</th>
                            <td>${sessionScope.showPaper.credit}</td>
                            <th>试卷题数：</th>
                            <td>${sessionScope.showPaper.question_num}</td>
                        </tr>
                        <tr>
                            <th>学年学期：</th>
                            <td>${sessionScope.showPaper.study_year}学年${sessionScope.showPaper.term}学期</td>
                            <th>考试日期：</th>
                            <td>${sessionScope.showPaper.exam_time}</td>
                        </tr>
                    </table>
                </div>
                <div class="content-show-jisuan">
                    <table cellpadding="2" cellspacing="15">
                        <tr>
                            <th>
                                实考人数
                            </th>
                            <th>
                                最高分
                            </th>
                            <th>
                                最低分
                            </th>
                            <th>
                                及格率
                            </th>
                            <th>
                                平均分
                            </th>
                            <th>
                                均方差
                            </th>
                            <th>
                                试题难度分布率
                            </th>
                            <th>
                                区分度分布率
                            </th>
                        </tr>
                        <tr>
                            <td>
                                ${sessionScope.showPaper.number_join}
                            </td>
                            <td>
                                ${sessionScope.showPaper.score_high}
                            </td>
                            <td>
                                ${sessionScope.showPaper.score_lowest}
                            </td>
                            <td>
                                ${sessionScope.showPaper.rate_pass}
                            </td>
                            <td>
                                ${sessionScope.showPaper.score_average}
                            </td>
                            <td>
                                ${sessionScope.showPaper.score_variance}
                            </td>
                            <td>
                                ${sessionScope.showPaper.rate_difficulty}
                            </td>
                            <td>
                                ${sessionScope.showPaper.rate_distribution}
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="content-show-jisuan">
                    <table cellpadding="2" cellspacing="15">
                        <tr>
                            <th class="score-title">
                                题型
                            </th>
                            <%
                                for (int i=0;i<num;i++) {
                            %>
                            <th>
                                <%=type[i] %>
                            </th>
                            <%
                                }
                            %>
                        </tr>
                        <tr>
                            <th class="score-title">
                                得分率
                            </th>
                            <%
                                for (int i=0;i<num;i++) {
                            %>
                            <td>
                                <%=scoreRate[i]%>
                            </td>
                            <%
                                }
                            %>
                        </tr>
                    </table>
                </div>
                <div class="content-show-score">
                    <table cellpadding="2" cellspacing="15">
                        <tr>
                            <th class="score-title">
                                分数段
                            </th>
                            <th>
                                0-9分
                            </th>
                            <th>
                                10-19分
                            </th>
                            <th>
                                20-29分
                            </th>
                            <th>
                                30-39分
                            </th>
                            <th>
                                40-49分
                            </th>
                            <th>
                                50-59分
                            </th>
                            <th>
                                60-69分
                            </th>
                            <th>
                                70-79分
                            </th>
                            <th>
                                80-89分
                            </th>
                            <th>
                                >=90分
                            </th>
                        </tr>
                        <tr>
                            <th class="score-title">
                                人数
                            </th>
                            <td>
                                ${sessionScope.showPaper.people0to9}
                            </td>
                            <td>
                                ${sessionScope.showPaper.people10to19}
                            </td>
                            <td>
                                ${sessionScope.showPaper.people20to29}
                            </td>
                            <td>
                                ${sessionScope.showPaper.people30to39}
                            </td>
                            <td>
                                ${sessionScope.showPaper.people40to49}
                            </td>
                            <td>
                                ${sessionScope.showPaper.people50to59}
                            </td>
                            <td>
                                ${sessionScope.showPaper.people60to69}
                            </td>
                            <td>
                                ${sessionScope.showPaper.people70to79}
                            </td>
                            <td>
                                ${sessionScope.showPaper.people80to89}
                            </td>
                            <td>
                                ${sessionScope.showPaper.people90}
                            </td>
                        </tr>
                        <tr>
                            <th class="score-title">
                                百分比(%)
                            </th>
                            <td>
                                ${sessionScope.showPaper.per0to9}
                            </td>
                            <td>
                                ${sessionScope.showPaper.per10to19}
                            </td>
                            <td>
                                ${sessionScope.showPaper.per20to29}
                            </td>
                            <td>
                                ${sessionScope.showPaper.per30to39}
                            </td>
                            <td>
                                ${sessionScope.showPaper.per40to49}
                            </td>
                            <td>
                                ${sessionScope.showPaper.per50to59}
                            </td>
                            <td>
                                ${sessionScope.showPaper.per60to69}
                            </td>
                            <td>
                                ${sessionScope.showPaper.per70to79}
                            </td>
                            <td>
                                ${sessionScope.showPaper.per80to89}
                            </td>
                            <td>
                                ${sessionScope.showPaper.per90}
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="content-show-picture">

                </div>
            </div>
            <!-- 教师填写评价 -->
            <div class="content-evaluate">
                    <div>
                        <h5>1.试题结构分析 （命题符合考试大纲程度，覆盖面、题型、题量、分值分布，试卷整体难易程度）</h5>
                        <div class="answer">
                            ${sessionScope.showPaper.answer1}
                        </div>
                    </div>
                    <div>
                        <h5>2.试卷分析（教学效果） （总成绩构成、成绩分布、考生掌握程度、考生错误率较高的知识点分布及原因）</h5>
                        <div class="answer">
                            ${sessionScope.showPaper.answer2}
                        </div>
                    </div>
                    <div>
                        <h5>3.存在的问题、改进措施及建议 （着重分析教与学中的薄弱环节，并提出改进措施或建议）</h5>
                        <div class="answer">
                            ${sessionScope.showPaper.answer3}
                        </div>
                    </div>
                <form method="post" action="/paperlistShowDownload" target="_blank" onsubmit="downloadPaperSubmit()">
                    <input type="hidden" name="barBase64Info" id="hidden" value=""/>
                    <button type="submit">分析报告下载</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function gotoUser(){
        window.location.href = "/userManagerMessage";
    }
    function gotoPaper(){
        window.location.href = "/liPaperManager";
    }

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('content-show-picture'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '分数段百分比折线柱状图'
        },
        tooltip: {
            trigger:'axis',
            axisPointer:{
                type:'cross'
            }
        },
        toolbox:{
            feature:{
                //dataView:{show:true,readOnly:true},
                restore:{show:true},
                magicType:{show:true,type:['line','bar']},
                saveAsImage:{show:true}
            }
        },
        legend: {
            data:['百分比']
        },
        xAxis: {
            name: '分数段',
            data: ["0-9分","10-19分","20-29分","30-39分","40-49分","50-59分","60-69分","70-79分","80-89分",">=90分"],
        },
        yAxis: [{
            name: '百分比(%)'
        }],
        series: [
            {
                name: '百分比(%)',
                type: 'bar',
                data: [${sessionScope.showPaper.per0to9},  ${sessionScope.showPaper.per10to19}, ${sessionScope.showPaper.per20to29},
                    ${sessionScope.showPaper.per30to39},  ${sessionScope.showPaper.per40to49},  ${sessionScope.showPaper.per50to59},
                    ${sessionScope.showPaper.per60to69}, ${sessionScope.showPaper.per70to79}, ${sessionScope.showPaper.per80to89},
                    ${sessionScope.showPaper.per90}]
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    //js对象转换成JSON字符串
    var barBase64Info = JSON.stringify(barBase64Infovar);

    function downloadPaperSubmit() {
        //定义柱状图图片base64编码的全局变量
        var barBase64Infovar;
        //用getDataURL()将柱状图转义成base64编码赋给全局变量
        barBase64Infovar = myChart.getDataURL();
        document.getElementById('hidden').value = barBase64Infovar;
    }
</script>
</body>
</html>
