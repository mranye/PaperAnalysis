<%@ page import="com.education.paper.entity.Knowledge" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.education.paper.entity.Paper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!--让IE使用最新的渲染模式-->
    <meta http-equiv="X-UA-Compatible" content="IE-edge,chrome=1,charset=UTF-8">
    <!--页面宽度适应浏览器宽度，且不允许客户缩放-->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>高校学生试卷分析系统</title>
    <link rel="stylesheet" href="../../css/Reset.css">
    <link rel="stylesheet" href="../../css/KnowledgeMessage.css">
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
    ArrayList<Knowledge> knowledgeOneIndex = (ArrayList<Knowledge>)session.getAttribute("knowledgeFirstList");
    int firstNum = 0;
    if (knowledgeOneIndex==null){

    }else{
        firstNum = knowledgeOneIndex.size();
    }
    /*int num = (int)session.getAttribute("userQuestionnum");//大题数目
    if (num==0){
        num=5;
    }*/
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
                <li onclick="gotoUser()">
                    <div class="link">信息设置</div>
                </li>
                <li ><div class="link" id="liopen">试卷分析</div>
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
                        <li><a href="/liKnowledgeMessage" id="active">信息设置</a></li>
                        <li><a href="/liKnowledgeInput" >成绩录入</a></li>
                        <li><a href="/liKnowledgeAnswer">填写分析</a></li>
                    </ul>
                </li>
                <li onclick="gotoPaper()">
                    <div class="link">报告管理</div>
                </li>
            </ul>
        </div>
        <div class="right" id="right"><!--右侧内容区-->
            <div class="right-content">
                <div class="right-title">
                    <h1>试卷信息设置</h1>
                    <hr/>
                </div>
                <div class="edit-message"><!--信息填写-->
                    <form action="/knowledgeMessageSubmit" method="post">
                        <table class="top-message" cellpadding="2" cellspacing="15">
                            <tr>
                                <th>专业年级</th>
                                <td><input type="text" placeholder="" name="profession"></td>
                            </tr>
                            <tr>
                                <th>考试日期</th>
                                <td><input type="date" placeholder="学时/学分" name="exam_time"></td>
                            </tr>
                            <tr>
                                <th>试卷题数</th>
                                <td><input type="text"  placeholder="默认5道大题" name="question_num"></td>
                            </tr>
                            <tr>
                                <th>知识点模块</th>
                                <td>
                                    <select class="myselect"  name="topindex">
                                        <%
                                            for (int i = 0;i<firstNum;i++){
                                                Knowledge knowledge = knowledgeOneIndex.get(i);
                                        %>
                                        <option value="<%=knowledge.getKnowledge_num()%>"><%=knowledge.getKnowledgeName()%></option>
                                        <%
                                            }
                                        %>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>学年</th>
                                <td><input type="text" placeholder="" name="study_year"></td>
                            </tr>
                            <tr>
                                <th>学期</th>
                                <td><select style="width:150px; padding-left: 30px" name="term">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                </select></td>
                            </tr>
                        </table>
                        <button type="submit" class="tijiao">下一步</button>
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
