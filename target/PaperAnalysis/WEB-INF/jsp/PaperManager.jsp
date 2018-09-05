<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.education.paper.entity.Paper" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <!--让IE使用最新的渲染模式-->
    <meta http-equiv="X-UA-Compatible" content="IE-edge,chrome=1,charset=UTF-8" >
    <!--页面宽度适应浏览器宽度，且不允许客户缩放-->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>高校学生试卷分析系统</title>
    <link rel="stylesheet" href="../../css/Reset.css">
    <link rel="stylesheet" href="../../css/PaperManager.css">
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
            <div>
                <h1 class="title">分析报告管理</h1>
                <form action="" method="post">
                    <table cellpadding="2" cellspacing="5">
                        <%
                            ArrayList<Paper> paperList = (ArrayList<Paper>)session.getAttribute("paperlist");
                            if (paperList==null){
                        %>
                                <div style="margin-top: 50px">您还未生成报告</div>
                        <%
                            } else {
                        %>
                        <tr>
                            <th class="th1">ID</th>
                            <th class="th2">报告名称</th>
                            <th class="th3">报告生成日期</th>
                            <th class="th4">删除</th>
                        </tr>
                        <%
                            int id = 0;
                            for (int i=0;i<paperList.size();i++) {
                               id = i+1;
                               String papername = null;
                               Paper paper = paperList.get(i);
                               String study_yearif =  paper.getStudy_year();
                               if (study_yearif==null||"".equals(study_yearif)){
                                   papername = paper.getExam_time()+"  "+
                                           paper.getProfession()+"  试卷分析报告";
                               }else {
                                   papername = paper.getStudy_year()+"学年"+paper.getTerm()+"学期 "+
                                           paper.getProfession()+"  试卷分析报告";
                               }
                               String paperdate = paper.getAnalysis_time();
                        %>
                        <tr class="contenttr">
                            <td><%=id %></td>
                            <td class="leftalign"><a href="/paperlistShow?id=<%=id%>"><%=papername %></a></td>
                            <td><%=paperdate %></td>
                            <td><a href="/paperlistDelete?id=<%=id%>" class="delete">删除</a> </td>
                        </tr>
                        <%
                            }
                        }
                        %>
                    </table>
                </form>
                <div class="page">

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
