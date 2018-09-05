<%@ page import="com.education.paper.entity.Knowledge" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!--让IE使用最新的渲染模式-->
    <meta http-equiv="X-UA-Compatible" content="IE-edge,chrome=1,charset=UTF-8">
    <!--页面宽度适应浏览器宽度，且不允许客户缩放-->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>高校学生试卷分析系统</title>
    <link rel="stylesheet" href="../../css/Reset.css">
    <link rel="stylesheet" href="../../css/KnowledgeManager.css">
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
    ArrayList<Knowledge> knowledgeSecondIndex = (ArrayList<Knowledge>)session.getAttribute("knowledgeSecondList");
    int secondNum = 0;
    if (knowledgeSecondIndex==null){

    }else{
        secondNum = knowledgeSecondIndex.size();
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
                        <li><a href="/liKnowledgeManager" id="active">知识点管理</a></li>
                        <li><a href="/liKnowledgeMessage">信息设置</a></li>
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
                    <h1>知识点管理</h1>
                    <hr/>
                </div>
                <div class="add-knowledge"><!--搜索、添加知识点-->
                    <form action="/knowledgeManagerSubmit" method="post">
                        知识点名称：<input type="text" name="knowledgeName"/>
                        知识点模块：
                        <select class="myselect"  name="topindex" onchange="selectOne()" id="topindex">
                            <option value="0">新建模块</option>
                            <%
                                for (int i = 0;i<firstNum;i++){
                                    int i1 = i+1;
                                    Knowledge knowledge = knowledgeOneIndex.get(i);
                            %>
                            <option value="<%=knowledge.getKnowledge_num()%>"><%=knowledge.getKnowledgeName()%></option>
                            <%
                                }
                            %>
                        </select>
                        上级知识点：
                        <select class="myselect"  name="topindex2" id="topindex2" style="min-width: 150px">
                            <%--<option value="0">新建一级知识点</option>--%>
                        </select>
                        <button type="submit" class="add-button">添加</button>
                    </form>
                </div>
                <div class="knowledge-list"><!--知识点展示区域-->
                    <%
                        for (int i=0;i<firstNum;i++){
                            Knowledge knowledge = knowledgeOneIndex.get(i);
                            int firstIndexNum = knowledge.getKnowledge_num();
                            int num = 0;
                            for (int j = 0;j<secondNum;j++){
                                Knowledge knowledgeSecond = knowledgeSecondIndex.get(j);
                                int secondTopindex = knowledgeSecond.getTopindex();
                                if (secondTopindex==firstIndexNum){
                                    num++;
                                }

                            }
                    %>
                    <div class="onelist">
                        <div class="onelist-content">
                            <div class="onelist-font"><%=knowledge.getKnowledgeName()%></div>
                            <%
                                if (num!=0){
                            %>
                            <a href="#">-</a>
                            <%
                                }
                            %>
                        </div>
                        <%
                            if (num !=0){
                        %>
                        <div class="sublist">
                            <ul>
                        <%
                                //int num2 = 0;//二级知识点个数
                                for (int j = 0;j<secondNum;j++){
                                    Knowledge knowledgeSecond = knowledgeSecondIndex.get(j);
                                    int secondTopindex = knowledgeSecond.getTopindex();
                                    int secondTopindex2 = knowledgeSecond.getTopindex2();
                                    /*if (secondTopindex2!=0){
                                        if (secondTopindex == firstIndexNum){
                                            num2++;
                                        }
                                    }*/
                                    if (secondTopindex2==0){
                                        if (secondTopindex == firstIndexNum) {
                        %>
                                <li class="sublist-content"><%=knowledgeSecond.getKnowledgeName()%></li>
                                <%
                                    int num2 = 0;
                                    for (int m = 0;m<secondNum;m++){
                                        Knowledge knowledgeSecond2 = knowledgeSecondIndex.get(m);
                                        int sTopindex2 = knowledgeSecond2.getTopindex2();
                                        int topnum = knowledgeSecond.getKnowledge_num();
                                        if (sTopindex2==topnum){
                                            num2++;
                                        }
                                    }
                                    if (num2!=0){
                                %>
                                <ul>
                                    <%
                                        for (int m = 0;m<secondNum;m++){
                                            Knowledge knowledgeSecond2 = knowledgeSecondIndex.get(m);
                                            int sTopindex2 = knowledgeSecond2.getTopindex2();
                                            int topnum = knowledgeSecond.getKnowledge_num();
                                            if (sTopindex2==topnum){
                                    %>
                                    <li class="content2" style="list-style:none;text-align: left;font-size:16px;padding:20px 7px 7px 10px;width: 150px;margin-left: 44%;">
                                        <%=knowledgeSecond2.getKnowledgeName()%>
                                    </li>
                                    <%
                                            }
                                        }
                                    %>
                                </ul>
                                <%

                                    }
                                %>
                        <%
                                        }
                                    }
                                }
                        %>
                            </ul>
                        </div>
                        <%
                            }
                        %>
                    </div>
                    <%
                        }
                    %>
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
    
    function selectOne() {
        var selectTopindex = document.getElementById("topindex");
        var selectTopindex2 = document.getElementById("topindex2");
        var TopKnowledgeNum = selectTopindex.options[selectTopindex.selectedIndex].value;//获下拉选定的数据，即模块的知识点序号
        $.ajax({
            type: 'post',
            url:'/AjaxKnowledgeSelect',
            /*dataType:'json',*/
            data:{
                TopKnowledgeNum:TopKnowledgeNum
            },
            success : function(json) {//将查询到的一级知识点选项放在一级知识点的select标签框中
                //alert(json);
                if(json.length!=0){
                    var jsonReturn = JSON.parse(json);
                    $("#topindex2").find("option").remove();
                    $("#topindex2").append("<option value='0'>"+"新建一级知识点"+"</option>");
                    jQuery.each(jsonReturn, function(i,item){
                        //alert(item.knowledge_num+","+item.knowledgeName);
                        $("#topindex2").append("<option value = "+item.knowledge_num+">"+item.knowledgeName+"</option>");
                    });
                }else{
                    $("#topindex2").find("option").remove();
                    $("#topindex2").append("<option value='0'>"+"新建一级知识点"+"</option>");
                    document.getElementById("topindex2").innerHTML
                        = "<option value = "+""+">"+null+"</option>" ;
                    return;
                }
            }
        });
    }

</script>
</body>
</html>
