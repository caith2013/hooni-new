
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/hooni/css/styles.css" />
<!--[if IE]><style>@import url("/hooni/css/ie.css");</style><![endif]-->
<script type="text/javascript" src="/hooni/js/jquery.js"></script>
<script type="text/javascript" src="/hooni/js/aes_no_decrypt.js"></script>
<script type="text/javascript" src="/hooni/js/login.js"></script>
</head>
<body>
<div id="container">



<div class="page_title">
Login
		
</div>

<div class="form_div">
		 
		 <form name="login_form" action="#" method="post" id="login_form">
		 <#if redirect??>
		 <input type="hidden" name="redirect" value="${redirect}" />
		 </#if>
		 			 <div class="login_item"><div class="label">Username:</div><input name="userName" type="text" value="" class="input_field"/></div>
		 			 <div class="login_item"><div class="label">Password</div><input name="passwd" type="password" class="input_field" value=""/></div>
		 			 <div class="login_item"><div class="label">Security Codes:</div><img src="http://www.hooni.org/key" alt="" id="sc_key" /></div>
					<div class="login_item"><div class="label">Verify Security Codes:</div><input name="vsc" id="vsc" type="text" class="input_field" value=""/></div>
		 			 <div class="login_item"><div class="submit_button"><input type="submit" value="Login" class="button"/></div></div>
		 			 <div class="login_item">Are you new user? Please <a href="register">Register</a>.</div>
		 </form>
		 <form name="login_dummy" action="login" method="post" id="login_dummy">
		 			 <#if redirect??>
		 			 <input type="hidden" name="redirect" value="${redirect}" />
		 			 </#if>
		</form>
</div>
</div><!-- end of container -->

</body>
</html>
