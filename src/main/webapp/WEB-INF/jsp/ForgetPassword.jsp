<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>高校学生试卷分析系统</title>
    <link rel="stylesheet" href="../../css/ForgetPassword.css">
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
                    <a id="switch_btn_find" href="#">重置密码</a>
                    <div id="switch_bottom"></div>
                </div>
            </div>
            <div id="loginorsign">
                <!--重置密码-->
                <div id="block_sign">
                    <div class="sign_form">
                        <form action="/updatePassword" name="sign_form" accept-charset="utf-8" method="post">
                            <div style="padding-top: 10px"><input type="text"  placeholder="账号" name="username"></div>
                            <div><input type="password" placeholder="密码" name="password"></div>
                            <div ><input type="password" placeholder="确认密码" name="again_password"></div>
                            <div><button class="sign_btn" type="submit">重置密码</button></div>
                            <div class="tip_message">${forgetMessage}</div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

</script>
</body>
</html>

