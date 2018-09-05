<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!--让IE使用最新的渲染模式-->
    <meta http-equiv="X-UA-Compatible" content="IE-edge,chrome=1,charset=UTF-8" >
    <!--页面宽度适应浏览器宽度，且不允许客户缩放-->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>高校学生试卷分析系统</title>
    <link rel="stylesheet" href="../../css/Reset.css">
    <link rel="stylesheet" href="../../css/KnowledgeAnswer.css">
    <script type="text/javascript" src="../../js/jquery-3.3.1.js"></script>
</head>
<body>
<script>
    /*/!*菜单抽屉效果*!/*/
    $(function () {
        var Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            var links = this.el.find('.link');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
        }
        Accordion.prototype.dropdown = function (e) {
            var $el = e.data.el;
            $this = $(this);
            $next = $this.next();

            $next.slideToggle();
            $this.parent().toggleClass('open');

            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
            }
            ;
        }
        var accordion = new Accordion($('#mymenu'), false);
    });
</script>
<%
    float[] scorerate = (float[])session.getAttribute("knowledgeScoreRate");
    String[] knowledgeName = (String[])session.getAttribute("KnowledgeName");
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
                    <ul class="submenu2">
                        <li><a href="/liKnowledgeManager">知识点管理</a></li>
                        <li><a href="/liKnowledgeMessage">信息设置</a></li>
                        <li><a href="/liKnowledgeInput">成绩录入</a></li>
                        <li><a href="/liKnowledgeAnswer" id="active">填写分析</a></li>
                    </ul>
                </li>
                <li onclick="gotoPaper()"><div class="link">报告管理</div></li>
            </ul>
        </div>
        <div  class="right" id="right"><!--右侧内容区-->
            <div class="content-show">
                <div class="content-show-title">
                    <h1>填写报告分析</h1>
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
                            <td>${sessionScope.knowledgePaper.profession}</td>
                        </tr>
                        <tr>
                            <th>学年学期：</th>
                            <td>${sessionScope.knowledgePaper.study_year}学年${sessionScope.knowledgePaper.term}学期</td>
                            <th>实考人数：</th>
                            <td>${sessionScope.knowledgenumjoin}</td>
                        </tr>
                        <tr>
                            <th>试卷题数：</th>
                            <td>${sessionScope.knowledgeQuestionnum}</td>
                            <th>考试日期：</th>
                            <td>${sessionScope.knowledgePaper.exam_time}</td>
                        </tr>
                        <!-- <tr>
                            <th>实考人数：</th>
                            <td>xxx</td>
                            <th>知识点数目：</th>
                            <td>xxx</td>
                        </tr> -->
                    </table>
                </div>
                <div class="content-show-jisuan">
                    <table cellpadding="2" cellspacing="15">
                        <tr>
                            <th>
                                知识点
                            </th>
                            <th>
                                得分率
                            </th>
                        </tr>
                        <%
                            for (int i=0;i<knowledgeName.length;i++){
                                if (knowledgeName[i]!=null){
                        %>
                        <tr>
                            <td class="tdwidth">
                                <%=knowledgeName[i]%>
                            </td>
                            <td>
                                <%=scorerate[i]%>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                    </table>
                </div>
                <!-- 教师填写评价 -->
                <div class="content-evaluate">
                    <form method="post" action="/knowledgeAnswerSubmit">
                        <div>
                            <h5>1.试题结构分析 （命题的知识点符合考试大纲程度，覆盖面）</h5>
                            <textarea name="answer1"></textarea>
                        </div>
                        <div>
                            <h5>2.试卷分析（教学效果） （考生掌握程度、考生错误率较高的知识点分布及原因）</h5>
                            <textarea name="answer2"></textarea>
                        </div>
                        <div>
                            <h5>3.存在的问题、改进措施及建议 （着重分析教与学中的薄弱环节，并提出改进措施或建议）</h5>
                            <textarea name="answer3"></textarea>
                        </div>
                        <button type="submit">生成报告</button>
                    </form>
                </div>
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

</script>
</body>
</html>

