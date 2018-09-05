<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>高校学生试卷分析系统</title>
    <link rel="stylesheet" href="../../css/Reset.css">
    <link rel="stylesheet" href="../../css/YoukePaper.css">
    <script type="text/javascript" src="../../js/echarts.min.js"></script>
    <script type="text/javascript" src="../../js/jquery-3.3.1.js"></script>
</head>
<body>
<header><!--页头-->
    <nav>
        <ul>
            <li><a href="/signUpOrIn">首页</a></li>
            <li><a href="#">系统说明</a></li>
            <li class="logo"><a href="/signUpOrIn">登录</a></li>
            <li class="logo"><a href="#">游客只支持生成试卷分析报告</a></li>
        </ul>
    </nav>
</header>
<div class="content"><!--内容-->
    <div class="content-show">
        <div class="content-show-title">
            <h1>填写试卷分析</h1>
            <hr/>
        </div>
        <div class="content-show-message">
            <table cellpadding="2" cellspacing="15">
                <tr>
                    <th>教师姓名：</th>
                    <td>${sessionScope.youkeTitle.username}</td>
                    <th>课程名称：</th>
                    <td>${sessionScope.youkeTitleCourse}</td>
                </tr>
                <tr>
                    <th>所在院系：</th>
                    <td>${sessionScope.youkeTitleCollege}</td>
                    <th>专业年级：</th>
                    <td>${sessionScope.youkeTitle.profession}</td>
                </tr>
                <tr>
                    <th>学时学分：</th>
                    <td>${sessionScope.youkeTitle.credit}</td>
                    <th>试卷题数：</th>
                    <td>${sessionScope.youkeTitle.question_num}</td>
                </tr>
                <tr>
                    <th>学年学期：</th>
                    <td>${sessionScope.youkeTitle.study_year}学年${sessionScope.youkeTitle.term}学期</td>
                    <th>考试日期：</th>
                    <td>${sessionScope.youkeTitle.exam_time}</td>
                </tr>
            </table>
        </div>
        <div class="content-show-jisuan">
            <table cellpadding="2" cellspacing="15">
                <tr>
                    <%--<th>
                        应考人数
                    </th>--%>
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
                   <%-- <th>
                        试题难度分布率
                    </th>
                    <th>
                        区分度分布率
                    </th>--%>
                </tr>
                <tr>
                    <%--<td>
                        ${sessionScope.a}
                    </td>--%>
                    <td>
                        ${sessionScope.numjoin}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.score_high}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.score_lowest}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.rate_pass}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.score_average}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.score_variance}
                    </td>
                    <%--<td>
                        xxx
                    </td>
                    <td>
                        xxx
                    </td>--%>
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
                        ${sessionScope.jisuanPaper.people0to9}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.people10to19}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.people20to29}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.people30to39}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.people40to49}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.people50to59}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.people60to69}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.people70to79}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.people80to89}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.people90}
                    </td>
                </tr>
                <tr>
                    <th class="score-title">
                        百分比(%)
                    </th>
                    <td>
                        ${sessionScope.jisuanPaper.per0to9}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.per10to19}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.per20to29}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.per30to39}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.per40to49}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.per50to59}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.per60to69}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.per70to79}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.per80to89}
                    </td>
                    <td>
                        ${sessionScope.jisuanPaper.per90}
                    </td>
                </tr>
            </table>
        </div>
        <div id="content-show-picture">

        </div>
    </div>
    <!-- 教师填写评价 -->
    <div class="content-evaluate">
        <form method="post" action="/youke/youkeAnswers">
        <div>
            <h5>1.试题结构分析 （命题符合考试大纲程度，覆盖面、题型、题量、分值分布，试卷整体难易程度）</h5>
            <textarea name="answer1" class="mytextarea" placeholder=""></textarea>
        </div>
        <div>
            <h5>2.试卷分析（教学效果） （总成绩构成、成绩分布、考生掌握程度、考生错误率较高的知识点分布及原因）</h5>
            <textarea name="answer2" class="mytextarea"  placeholder=""></textarea>
        </div>
        <div>
            <h5>3.存在的问题、改进措施及建议 （着重分析教与学中的薄弱环节，并提出改进措施或建议）</h5>
            <textarea name="answer3" class="mytextarea"  placeholder=""></textarea>
        </div>
        <button type="submit">生成报告</button>
        </form>
    </div>
</div>
<script type="text/javascript">
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
            data: [${sessionScope.jisuanPaper.per0to9},  ${sessionScope.jisuanPaper.per10to19}, ${sessionScope.jisuanPaper.per20to29},
                ${sessionScope.jisuanPaper.per30to39},  ${sessionScope.jisuanPaper.per40to49},  ${sessionScope.jisuanPaper.per50to59},
                ${sessionScope.jisuanPaper.per60to69}, ${sessionScope.jisuanPaper.per70to79}, ${sessionScope.jisuanPaper.per80to89},
                ${sessionScope.jisuanPaper.per90}]
        }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>
</html>
