<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.education.paper.entity.Paper" %>
<html>
<head>
    <%--<!--让IE使用最新的渲染模式-->
    <meta http-equiv="X-UA-Compatible" content="IE-edge,chrome=1,charset=UTF-8" >
    <!--页面宽度适应浏览器宽度，且不允许客户缩放-->
    <meta name="viewport" content="width=device-width,initial-scale=1">--%>
    <title>高校学生试卷分析系统</title>
    <link rel="stylesheet" href="../../css/UserManager.css">
    <link rel="stylesheet" href="../../css/Reset.css">
    <script type="text/javascript" src="../../js/jquery-3.3.1.js"></script>
</head>
<body>
<%
    Paper paper = (Paper)session.getAttribute("currentPaper");
    String xueshi = paper.getCredit();
    if (xueshi==null||xueshi.length()==0){
        xueshi = "学时/学分";
    }
    float qnumfloat = paper.getQuestion_num();
    String qnum = qnumfloat+"";
    if (qnumfloat==0||qnum.length()==0){
        qnum = "默认5大题";
    }
%>
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
                <li onclick="gotoUser()"><div class="link" id="activeTop">信息设置</div></li>
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
                <li onclick="gotoPaper()"><div class="link">报告管理</div></li>
            </ul>
        </div>
        <div  class="right" id="right"><!--右侧内容区-->
            <form action="/userMessageSubmit" method="post">
                <h1 class="title">信息设置</h1>
                <table cellpadding="2" cellspacing="15">
                    <tr>
                        <th>教师姓名</th>
                        <td><input type="text"  placeholder="${sessionScope.currentTeacher.teacher_name}" name="teacher_name"></td>
                    </tr>
                    <tr>
                        <th>所在院系</th>
                        <td><input type="text"  placeholder="${sessionScope.currentTeacher.college}" name="college"></td>
                    </tr>
                    <tr>
                        <th>课程名称</th>
                        <td><input type="text"  placeholder="${sessionScope.currentTeacher.course}" name="course"></td>
                    </tr>
                    <tr>
                        <th>学时学分</th>
                        <td><input type="text"  placeholder="<%=xueshi%>"  name="credit"></td>
                    </tr>
                   <%-- <tr>
                        <th>试卷题数</th>
                        <td><input type="text"  placeholder="<%=qnum%>" name="question_num"></td>
                    </tr>--%>
                </table>
                <button class="bianji">编辑信息</button>
                <button type="submit" class="tijiao">保存设置</button>
            </form>
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
