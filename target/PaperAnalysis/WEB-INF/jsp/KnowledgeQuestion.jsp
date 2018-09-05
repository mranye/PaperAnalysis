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
    <link rel="stylesheet" href="../../css/KnowledgeQuestion.css">
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
    ArrayList<Knowledge> knowledgelistFirst = (ArrayList<Knowledge>)session.getAttribute("knowledgelistFirst");//该模块下所有一级知识点
    ArrayList<Knowledge> knowledgelistSecond = (ArrayList<Knowledge>)session.getAttribute("knowledgelistSecond");//该模块下所有二级知识点
    /*int secondNum = 0;
    if (knowledgeSecondList==null){

    }else{
        secondNum = knowledgeSecondList.size();
    }*/
    int[] littleNum = (int[])session.getAttribute("littleNum");//每道大题的小题数
    float[] questionSum = (float[])session.getAttribute("questionSum");//每道大题的满分
    int num = (int)session.getAttribute("knowledgeQuestionnum");//大题数目
    /*if (num==0){
        num=5;
    }*/
    String[] topQues = {"一","二","三","四","五","六","七","八","九"};
    Knowledge knowledgeOne = knowledgelistFirst.get(0);
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
                        <li><a href="/liKnowledgeInput">成绩录入</a></li>
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
                    <h1>知识点设置</h1>
                    <hr/>
                </div>
                <div class="edit-message"><!--信息填写-->
                    <form action="/knowledgeQuestionSubmit" method="post">
                        <table cellpadding="2" cellspacing="15">
                            <tr class="tr1">
                                <th class="th1">大题序号</th>
                                <th class="th2">小题序号</th>
                                <th class="th3">该题所用一级知识点</th>
                                <th class="th3">该题所用二级知识点</th>
                            </tr>
                            <%
                                for (int i = 0;i<num;i++){
                                    int m = i+1;
                            %>
                            <tr>
                                <td rowspan="<%=littleNum[i]%>" class="td1"><%=topQues[i]%></td>
                                <td>1</td>
                                <td><select style="width:150px;margin:5px 5px;" name="numOneKnowledge<%=i%>and0"  onchange="selectOne(this)" id="selectOne<%=i%>and0">
                                    <%
                                        for (int j = 0;j<knowledgelistFirst.size();j++){
                                            Knowledge knowledge = knowledgelistFirst.get(j);
                                    %>
                                    <option value="<%=knowledge.getKnowledge_num()%>"><%=knowledge.getKnowledgeName()%></option>
                                    <%
                                        }
                                    %>
                                </select></td>
                                <td><select style="width:150px;margin:5px 5px;" name="numKnowledge<%=i%>and0"  id="selectTwo<%=i%>and0">
                                    <%
                                        for (int j = 0;j<knowledgelistSecond.size();j++){
                                            Knowledge knowledgeTwo = knowledgelistSecond.get(j);
                                            if (knowledgeTwo.getTopindex2()==knowledgeOne.getKnowledge_num()){
                                    %>
                                    <option value="<%=knowledgeTwo.getKnowledge_num()%>"><%=knowledgeTwo.getKnowledgeName()%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select></td>
                            </tr>
                            <%
                                for (int n = 1;n<littleNum[i];n++) {
                                    int n1 = n+1;
                            %>
                                 <tr>
                                    <td><%=n1%></td>
                                    <td><select style="width:150px;margin:10px 5px; "  name="numOneKnowledge<%=i%>and<%=n%>" onchange="selectOne(this)" id="selectOne<%=i%>and<%=n%>">
                                        <%
                                            for (int j = 0;j<knowledgelistFirst.size();j++){
                                                Knowledge knowledge = knowledgelistFirst.get(j);
                                        %>
                                        <option value="<%=knowledge.getKnowledge_num()%>"><%=knowledge.getKnowledgeName()%></option>
                                        <%
                                            }
                                        %>
                                    </select></td>
                                     <td><select style="width:150px;margin:5px 5px;" name="numKnowledge<%=i%>and<%=n%>"  id="selectTwo<%=i%>and<%=n%>">
                                         <%
                                             for (int j = 0;j<knowledgelistSecond.size();j++){
                                                 Knowledge knowledgeTwo = knowledgelistSecond.get(j);
                                                 if (knowledgeTwo.getTopindex2()==knowledgeOne.getKnowledge_num()){
                                         %>
                                         <option value="<%=knowledgeTwo.getKnowledge_num()%>"><%=knowledgeTwo.getKnowledgeName()%></option>
                                         <%
                                                 }
                                             }
                                         %>
                                     </select></td>
                                </tr>
                            <%
                                }
                             }
                            %>
                        </table>
                        <button type="submit" class="tijiao">提交</button>
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

    function selectOne(ob) {

        var OneId=$(ob).attr("id");
        //alert(OneId);
        var OneIdVar = document.getElementById(OneId);
        var OneIdNum = OneIdVar.options[OneIdVar.selectedIndex].value;//获下拉选定的数据，即模块的知识点序号
        var TwoId = $(OneIdVar).parent().next().children().attr("id");;
        var TwoIdVar = document.getElementById(TwoId);
        //alert(OneIdNum);
        $.ajax({
            type: 'post',
            url:'/AjaxKnowledgeQuestion',
            data:{
                OneIdNum:OneIdNum
            },
            success : function(json) {//将查询到的一级知识点选项放在一级知识点的select标签框中
                //alert(json);
                if(json.length!=0){
                    var jsonReturn = JSON.parse(json);
                    $(TwoIdVar).find("option").remove();
                   /* $(TwoId).append("<option value='0'>"+"新建一级知识点"+"</option>");*/
                    jQuery.each(jsonReturn, function(i,item){
                        //alert(item.knowledge_num+","+item.knowledgeName);
                        $(TwoIdVar).append("<option value = "+item.knowledge_num+">"+item.knowledgeName+"</option>");
                    });
                }else{
                    $(TwoIdVar).find("option").remove();
                    document.getElementById(TwoId).innerHTML
                        = "<option value = "+""+">"+null+"</option>" ;
                    return;
                }
            }
        });
    }

</script>
</body>
</html>
