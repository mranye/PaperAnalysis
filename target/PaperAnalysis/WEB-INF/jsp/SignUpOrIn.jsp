<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>高校学生试卷分析系统</title>
    <link rel="stylesheet" href="../../css/SignUpOrIn.css">
    <link rel="stylesheet" href="../../css/Reset.css">
</head>
<body>
<header><!--页头-->
    <nav>
        <ul>
            <li class="youke"><a href="/youke/youkeInput">游客访问</a></li>
            <li class="youke"><a href="#">系统说明</a></li>
        </ul>
    </nav>
</header>
<div id = "banner"><!--内容-->
    <div class="inner">
        <h1>高校学生试卷分析系统</h1>
        <div class="login">
            <div class="switch_header">
                <div id="login_switch">
                    <a id="switch_btn_login" href="#1">登录</a>
                    <a id="switch_btn_sign" href="#2">注册</a>
                    <div id="switch_bottom"></div>
                </div>
            </div>
            <div id="loginorsign">
                <!--登录-->
                <div id="block_login">
                    <div class="login_form">
                        <form action="/login" name="login_form" accept-charset="utf-8"  method="post">
                            <div style="padding-top: 10px">
                                <input type="text"  placeholder="账号" name="username">
                            </div>
                            <div><input type="password" placeholder="密码" name="password"></div>
                            <div class="forget"><a href="/forgetPassword">忘记密码？</a></div>
                            <div><button class="login_btn" type="submit" >登录</button></div>
                            <div class="tip_message">${loginMessage}</div>
                        </form>
                    </div>
                </div>
                <!--注册-->
                <div id="block_sign">
                    <div class="sign_form">
                        <form action="/signup" name="sign_form" accept-charset="utf-8" method="post">
                            <div style="padding-top: 10px"><input type="text"  placeholder="账号" name="username"></div>
                            <div><input type="password" placeholder="密码" name="password"></div>
                            <div ><input type="password" placeholder="确认密码" name="again_password"></div>
                            <div><button class="sign_btn" type="submit">注册</button></div>
                            <div class="tip_message">${signupMessage}</div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    /*通过鼠标点击切换登录注册面板*/
    var denglu=document.getElementById("switch_btn_login");
    var zhuce=document.getElementById("switch_btn_sign");
    var line=document.getElementById("switch_bottom");

    var degnluContent=document.getElementById("block_login");
    var zhuceContent=document.getElementById("block_sign");

    denglu.onclick = function () {
        denglu.style.color="black";
        zhuce.style.color="#999";
        line.style.left="0px";
        degnluContent.style.display="block";
        zhuceContent.style.display="none";
    }
    zhuce.onclick = function () {
        denglu.style.color="#999";
        zhuce.style.color="black";
        line.style.left="136px";
        degnluContent.style.display="none";
        zhuceContent.style.display="block";
    }
</script>
</body>
</html>
