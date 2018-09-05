<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.education.paper.entity.Paper" %>
<html>
<head>
    <!--让IE使用最新的渲染模式-->
    <meta http-equiv="X-UA-Compatible" content="IE-edge,chrome=1" >
    <!--页面宽度适应浏览器宽度，且不允许客户缩放-->
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>高校学生试卷分析系统</title>
    <link rel="stylesheet" href="../../css/YoukeUpload.css">
    <link rel="stylesheet" href="../../css/Reset.css">
    <script type="text/javascript" src="../../js/jquery-3.3.1.js"></script>
</head>
<body><!---->
<%
    String importMsg="";
    if(request.getSession().getAttribute("msg")!=null){
        importMsg=request.getSession().getAttribute("msg").toString();
    }
    request.getSession().setAttribute("msg", "");
%>
<header><!--页头-->
    <nav>
        <ul>
            <li><a href="/signUpOrIn">首页</a></li>
            <li><a href="#">系统说明</a></li>
            <li class="youke_head"><a href="/signUpOrIn">登录</a></li>
            <li class="youke_head"><a href="#">游客只支持生成试卷分析报告</a></li>
        </ul>
    </nav>
</header>
<div class="container">
    <div class="st-container">
        <!--下方导航开始-->
        <input type="radio" name="radio-set"  id="st-control-1">
        <a href="#st-panel-1">设置试卷信息</a>
        <input type="radio" name="radio-set" checked="checked" id="st-control-2">
        <a href="#st-panel-2" >录入学生成绩</a>
        <!--下方导航结束-->
        <!--中间内容开始-->
        <div class="st-scroll">
                <!--每一个导航对应的页面放到section中-->
                <section class="st-panel" id="st-panel-1">
                    <div class="st-desc" data-icon="H"></div>
                    <div id = "banner1">
                        <div class="inner">
                            <h1 class="shezhi">设置试卷信息</h1>
                            <form method="post" class="input-donghua" action="/youke/titleMessage">
                                <table cellpadding="2" cellspacing="15">
                                    <tr>
                                        <th>
                                            教师姓名：
                                            <input type="text"  placeholder="" style="width:150px;" name="username">
                                        </th>
                                        <th>
                                            课程名称：
                                            <input type="text"  placeholder=""  style="width:150px;" name="course">
                                        </th>
                                    </tr>
                                    <tr>
                                        <th>
                                            所在院校：
                                            <input type="text"  placeholder=""  style="width:150px;" name="college">
                                        </th>
                                        <th>
                                            专业年级：
                                            <input type="text"  placeholder=""  style="width:150px;" name="profession">
                                        </th>
                                    </tr>
                                    <tr>
                                        <th>
                                            学时学分：
                                            <input type="text"  placeholder=""  style="width:150px;" name="credit">
                                        </th>
                                        <th>
                                            试卷题数：
                                            <input type="text"  placeholder="默认5大题"  style="width:150px;" name="question_num">
                                        </th>
                                    </tr>
                                    <tr>
                                        <th>
                                            学年：
                                            <input type="text"  placeholder="" style="width:60px;" name="study_year">&nbsp;&nbsp;&nbsp;&nbsp;
                                            学期：
                                            <select style="width:50px;" name="term">
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                            </select>
                                        </th>
                                        <th>
                                            考试日期：
                                            <input type="date"  placeholder=""  style="width:150px;" name="exam_time">
                                        </th>
                                    </tr>
                                    <tr>
                                        <th colspan="2">
                                            <button type="submit" id="input-submit" class="input-submit">提交</button>
                                        </th>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                </section>
                <section class="st-panel st-color" id="st-panel-2">
                    <div class="st-desc" data-icon="2"></div>
                    <div id = "banner">
                        <div class="inner">
                            <h1>学生成绩录入</h1>
                            <div class="input-donghua">
                                    <a href="#" class="score_down" onclick="downloadExcel()">点击下载EXCEL模板</a>
                                <form action="/youke/youkeJisuan" method="post" enctype="multipart/form-data" class="divfile">
                                    <label id="div_file" for="excel_file">上传学生成绩</label>
                                    <input id="excel_file" type="file" name="filename" accept="xlsx"/>
                                    <div class="tishi" id="importMsg">请按照Excel模板格式上传<%=importMsg%></div>
                                   <%-- <a href="/youke/youkeJisuan" class="score_next" id="score_next">下一步</a>--%>
                                    <button type="submit" class="score_next" id="score_next">下一步</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </section>
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        $("#input-submit").mousedown(function(){
            $("#st-control-2").prop("checked",true);
        });
    });
    function downloadExcel(){
        var url="/youke/downloadMuban";
        window.open(url);
    }
    /*function check() {
        var excel_file = $("#excel_file").val();
        if (excel_file== "" || excel_file.length == 0) {
            alert("请选择文件路径！");
            return false;
        } else {
            return true;
        }
    }
    $(document).ready(function () {
        var msg="";
        if($("#importMsg").text()!=null){
            msg=$("#importMsg").text();
        }
        if(msg!=""){
            alert(msg);
        }
    });
*/
</script>
</body>
</html>