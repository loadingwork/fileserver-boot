<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>登录</title>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<!-- 
<link type="text/css"  rel="stylesheet" href="${springMacroRequestContext.getContextUrl('/static/css/normalize.css')!''}"  />
 -->
<!-- 使用自定义宏与上面等价 -->
<@link  href="/static/css/normalize.css" />
<@link  href="/webjars/font-awesome/5.6.3/css/all.min.css" />
<@link  href="/static/css/login.css" />
</head>
<body>

	<div class="g-body">
		<div class="g-login-form">
		
			<div class="m-form-logo">
					<a href="/home.do"><img src="/static/images/logo.png" alt="logo"></a>
			</div>
			
			<div class="account-slogon">
			    <h2 class="account-slogon-main">欢迎回到后台</h2>
			    <p class="account-slogon-subhead">
			      <span>上传文件</span></p>
			</div>
			
			<div class="m-login-err">
				<div class="error">${error!''}</div>
			</div>
			
			<form id="m-form-content" action="/login.do" method="POST">
			
					<div class="m-login-item">
						<span class="input-prefix">
							<i class="far fa-user"></i>
						</span>
						<input type="text" class="input" name="username" value="${username!''}"  autocomplete="off" placeholder="用户名" />
					</div>
		
					<div class="m-login-item">
						<span class="input-prefix">
							<i class="fas fa-unlock"></i>
						</span>
						<input type="password" class="input" name="password" autocomplete="off" placeholder="密码" />
					</div>
		
					<div class="m-login-item">
						<button type="submit" class="btn-login" >登录</button>
					</div>
		
			</form>
			
		</div>
	</div>

</body>
</html>