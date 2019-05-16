<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link href="${pageContext.request.contextPath }/user/css/style.css" rel='stylesheet' type='text/css' />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!--webfonts-->
		<!--//webfonts-->
		<script src="js/setDate.js" type="text/javascript"></script>
	</head>
	<script>
		function isUsernameExist() {

			var value = document.getElementById("username").value;
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function () {
			    if (xhr.readyState == 4 && xhr.status == 200){

			        var element = document.getElementById("username_message");

			        if (xhr.responseText == "exist") {
			            // alert(xhr.responseText);
			            element.innerText = "用户名存在了";
			            element.setAttribute("style","color:red");
					}else if(xhr.responseText == "ok"){
			            // alert(xhr.responseText);
                        element.setAttribute("style","color:yellow");
			            element.innerText = "用户名可用";
					}else if(xhr.responseText == "repeat"){
                        // alert(value);
                        // alert(xhr.responseText);
                        element.setAttribute("style","color:blue");
                        element.innerText = "非法输入";
					}
				}
			}

			xhr.open("get","/ajaxServlet?username="+value,true);
			xhr.send();

        }
	</script>
	<body>
		<div class="main" align="center">
			<div class="header">
				<h1>创建一个免费的新帐户！</h1>
			</div>
			<p></p>
			<form method="post" action="${pageContext.request.contextPath }/UserServlet">
				<input type="hidden" name="op" value="register" />
				<ul class="left-form">
					<span id="username_message"></span>
					<li>
						${msg.error.username }<br/>
						<input type="text" name="username" placeholder="用户名" id="username" onblur="isUsernameExist()" value="${msg.username}" required="required"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<span id="nickname_message"></span>
					<li>
						${msg.error.nickname }<br/>
						<input type="text" name="nickname" placeholder="昵称" id="nickname" onblur="isNicknameExist()" value="${msg.nickname}" required="required"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.email }<br/>
						<input type="text" name="email" placeholder="邮箱" value="${msg.email}" required="required"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.password }<br/>
						<input type="password" name="password" placeholder="密码" value="${msg.password}" required="required"/>
						<a href="#" class="icon into"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.birthday }<br/>
						<input type="text" placeholder="出生日期" name="birthday" value="${msg.birthday}" size="15" />
						<div class="clear"> </div>
					</li>
					<li>
						<input type="submit" value="创建账户">
						<div class="clear"> </div>
					</li>
			</ul>

			<div class="clear"> </div>

			</form>

		</div>
		<!-----start-copyright---->

		<!-----//end-copyright---->

	</body>

</html>